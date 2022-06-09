package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.world.gen.WorldReplacerFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Function;

public class ReplacementUtils {
    public static final int NOISE_SCALE = Config.WORLDGEN.NOISE_SCALE.get();
    public static final int LAYER_THICKNESS = Config.WORLDGEN.LAYER_THICKNESS.get();
    public static final double LAYER_BEND = Config.WORLDGEN.LAYER_BEND.get();
    public static final List<ResourceLocation> GEN_BIOMES = WorldgenUtils.GEN_BIOMES;
    public static final List<List<String>> LAYER_LISTS = WorldgenUtils.LAYER_LISTS;
    private static double NOISE = 0;
    private static double SAND_NOISE = 0;
    private static Holder<Biome> BIOME;
    private static final Map<Block, Function<Integer,Block>> map = new HashMap<>();
    static {
        map.put(Blocks.GRASS_BLOCK, index -> Config.WORLDGEN.SOIL_GEN.get() ? (NOISE > 0.3 ? WorldgenUtils.O1.get(index) : WorldgenUtils.O2.get(index)) : Blocks.AIR);
        map.put(Blocks.DIRT_PATH, index -> Config.WORLDGEN.SOIL_GEN.get() ? (NOISE > 0.3 ? VanillaIntegration.pathBlocks_map.getOrDefault(WorldgenUtils.O1.get(index),Blocks.AIR) : VanillaIntegration.pathBlocks_map.getOrDefault(WorldgenUtils.O2.get(index),Blocks.AIR)) : Blocks.AIR);
        map.put(Blocks.COARSE_DIRT, index -> {
            if (!Config.WORLDGEN.SOIL_GEN.get()) {
                return Blocks.AIR;
            }
            Block b = NOISE > 0.3 ? WorldgenUtils.A1.get(index) : WorldgenUtils.A2.get(index);
            if (RankineLists.SOIL_BLOCKS.contains(b)) {
                return RankineLists.COARSE_SOIL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(b));
            }
            return Blocks.AIR;
        });
        map.put(Blocks.MYCELIUM, index -> {
            if (!Config.WORLDGEN.SOIL_GEN.get()) {
                return Blocks.AIR;
            }
            Block b = NOISE > 0.3 ? WorldgenUtils.A1.get(index) : WorldgenUtils.A2.get(index);
            if (RankineLists.SOIL_BLOCKS.contains(b)) {
                return RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(b));
            }
            return Blocks.AIR;
        });
        map.put(Blocks.PODZOL, index -> {
            if (!Config.WORLDGEN.SOIL_GEN.get()) {
                return Blocks.AIR;
            }
            Block b = NOISE > 0.3 ? WorldgenUtils.A1.get(index) : WorldgenUtils.A2.get(index);
            if (RankineLists.SOIL_BLOCKS.contains(b)) {
                return RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(b));
            }
            return Blocks.AIR;
        });
        map.put(Blocks.GRAVEL,index -> WorldgenUtils.GRAVELS.get(index) != Blocks.AIR ? WorldgenUtils.GRAVELS.get(index) : Blocks.AIR);
        map.put(Blocks.SAND,index -> (WorldgenUtils.SANDS.get(index) != Blocks.AIR) ? WorldgenUtils.SANDS.get(index) : SAND_NOISE > 0.5f ? RankineBlocks.WHITE_SAND.get() : Blocks.AIR);
        map.put(Blocks.SANDSTONE,index -> (WorldgenUtils.SANDSTONES.get(index) != Blocks.AIR) ? WorldgenUtils.SANDSTONES.get(index) : SAND_NOISE > 0.5f ? RankineBlocks.WHITE_SANDSTONE.get() : Blocks.AIR);
        map.put(Blocks.SMOOTH_SANDSTONE, index -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(WorldgenUtils.SANDSTONES.get(index).getRegistryName().toString().replace(":",":smooth_"))));
        map.put(Blocks.SMOOTH_RED_SANDSTONE, index -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(WorldgenUtils.SANDSTONES.get(index).getRegistryName().toString().replace(":",":smooth_"))));
        map.put(Blocks.TUFF, index -> BIOME.is(BiomeTags.IS_OCEAN) ? RankineBlocks.BASALTIC_TUFF.get() :
                (BIOME.is(BiomeTags.IS_BADLANDS) || BIOME.is(Biomes.DESERT)) ? RankineBlocks.RHYOLITIC_TUFF.get() :
                        (BIOME.is(BiomeTags.IS_MOUNTAIN) || BIOME.is(BiomeTags.IS_HILL)) ? RankineBlocks.ANDESITIC_TUFF.get() : Blocks.AIR);
    }

    public static void performRetrogenReplacement(ChunkAccess chunk) {

        if (chunk.getWorldForge() == null) return;
        LevelAccessor levelIn = chunk.getWorldForge();
        Random rand = levelIn.getRandom();
        BlockState returnedBlock;
        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                
                NOISE = Biome.BIOME_INFO_NOISE.getValue((double) x / NOISE_SCALE, (double) z / NOISE_SCALE, false);
                SAND_NOISE = Biome.BIOME_INFO_NOISE.getValue((double) x / 80, (double) z / 80, false);
                BIOME = levelIn.getBiome(new BlockPos(x, chunk.getMaxBuildHeight(), z));
                ResourceLocation TARGET_BIOME = BIOME.value().getRegistryName();
                int genBiomesIndex = GEN_BIOMES.indexOf(TARGET_BIOME);
                List<String> blockList = genBiomesIndex != -1 ? LAYER_LISTS.get(genBiomesIndex) : Collections.emptyList();

                for (int y = chunk.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z); y >= chunk.getMinBuildHeight(); --y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    BlockState TARGET_BS = chunk.getBlockState(TARGET_POS);
                    if (TARGET_BS.isAir()) continue;
                    Block TARGET = TARGET_BS.getBlock();

                    if (chunk.getBlockState(TARGET_POS).is(Tags.Blocks.ORES_COAL)) {
                        if (chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE) && rand.nextFloat() < 0.7) {
                            chunk.setBlockState(TARGET_POS.below(), RankineBlocks.FIRE_CLAY.get().defaultBlockState(), false);
                            continue;
                        }
                    }

                    if (genBiomesIndex != -1) {
                        if (map.containsKey(TARGET)) {
                            returnedBlock = map.get(TARGET).apply(genBiomesIndex).defaultBlockState();
                            if (returnedBlock != Blocks.AIR.defaultBlockState()) {
                                chunk.setBlockState(TARGET_POS, returnedBlock, false);
                            }
                        } else if (TARGET.equals(Blocks.DIRT) && Config.WORLDGEN.SOIL_GEN.get()) {
                            if (chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE)) {
                                //if (RankineLists.SOIL_BLOCKS.contains(Blayer) && WorldgenUtils.isWet(levelIn, TARGET_POS)) {
                                //     chunk.setBlockState(TARGET_POS, RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Blayer)).defaultBlockState(), false);
                                //} else {
                                chunk.setBlockState(TARGET_POS, NOISE > 0.3 ? WorldgenUtils.B1.get(genBiomesIndex).defaultBlockState() : WorldgenUtils.B2.get(genBiomesIndex).defaultBlockState(), false);
                                //}
                            } else {
                                //if (RankineLists.SOIL_BLOCKS.contains(Alayer) && WorldgenUtils.isWet(levelIn, TARGET_POS)) {
                                //    chunk.setBlockState(TARGET_POS, RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), false);
                                //} else {
                                chunk.setBlockState(TARGET_POS, NOISE > 0.3 ? WorldgenUtils.A1.get(genBiomesIndex).defaultBlockState() : WorldgenUtils.A2.get(genBiomesIndex).defaultBlockState(), false);
                                //}
                            }
                        } else if (TARGET_BS.getBlock() instanceof RankineOreBlock && TARGET_BS.getValue(RankineOreBlock.TYPE) == 0) {
                            BlockState stone = getStone(blockList,y,NOISE);

                            if (stone != null && RankineLists.STONES.contains(stone.getBlock())) {
                                chunk.setBlockState(TARGET_POS, TARGET_BS.setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(stone.getBlock())),false);
                            }
                        } else if (TARGET.equals(Blocks.INFESTED_STONE) || TARGET.equals(Blocks.INFESTED_DEEPSLATE)) {
                            BlockState stone = getStone(blockList,y,NOISE);
                            if (stone != null && RankineLists.STONES.contains(stone.getBlock())) {
                                chunk.setBlockState(TARGET_POS, RankineLists.INFESTED_STONES.get(RankineLists.STONES.indexOf(stone.getBlock())).defaultBlockState(),false);
                            }
                        }

                    }


                }
                
                
            }
        }
        chunk.setUnsaved(true);
    }

    public static BlockState getStone(List<String> blockList,int y,double stoneNoise) {
        try {
            return ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(blockList.get((int) Mth.clamp(Math.floor((y+stoneNoise/LAYER_BEND+80)/LAYER_THICKNESS),0,blockList.size()-1)))).defaultBlockState();
        } catch (Exception e) {
            return null;
        }
    }
}
