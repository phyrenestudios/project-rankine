package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.blocks.RankinePointedDripstoneBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
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
import net.minecraft.world.level.block.state.BlockState;
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
    private static final Map<Block, Function<Integer,Block>> replacerMap = new HashMap<>();
    private static final Map<Block, Block> vanillaOresMap = new HashMap<>();
    static {
        vanillaOresMap.put(Blocks.IRON_ORE, RankineBlocks.IRON_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_IRON_ORE, RankineBlocks.IRON_ORE.get());
        vanillaOresMap.put(Blocks.COAL_ORE, RankineBlocks.COAL_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_COAL_ORE, RankineBlocks.COAL_ORE.get());
        vanillaOresMap.put(Blocks.COPPER_ORE, RankineBlocks.COPPER_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_COPPER_ORE, RankineBlocks.COPPER_ORE.get());
        vanillaOresMap.put(Blocks.REDSTONE_ORE, RankineBlocks.REDSTONE_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_REDSTONE_ORE, RankineBlocks.REDSTONE_ORE.get());
        vanillaOresMap.put(Blocks.LAPIS_ORE, RankineBlocks.LAPIS_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_LAPIS_ORE, RankineBlocks.LAPIS_ORE.get());
        vanillaOresMap.put(Blocks.GOLD_ORE, RankineBlocks.GOLD_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_GOLD_ORE, RankineBlocks.GOLD_ORE.get());
        vanillaOresMap.put(Blocks.DIAMOND_ORE, RankineBlocks.DIAMOND_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_DIAMOND_ORE, RankineBlocks.DIAMOND_ORE.get());
        vanillaOresMap.put(Blocks.EMERALD_ORE, RankineBlocks.EMERALD_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_EMERALD_ORE, RankineBlocks.EMERALD_ORE.get());
        vanillaOresMap.put(Blocks.NETHER_QUARTZ_ORE, RankineBlocks.NETHER_QUARTZ_ORE.get());
        vanillaOresMap.put(Blocks.NETHER_GOLD_ORE, RankineBlocks.NETHER_GOLD_ORE.get());



        replacerMap.put(Blocks.GRASS_BLOCK, index -> Config.WORLDGEN.SOIL_GEN.get() ? (NOISE > 0.3 ? WorldgenUtils.O1.get(index) : WorldgenUtils.O2.get(index)) : Blocks.AIR);
        replacerMap.put(Blocks.DIRT_PATH, index -> Config.WORLDGEN.SOIL_GEN.get() ? (NOISE > 0.3 ? VanillaIntegration.pathBlocks_map.getOrDefault(WorldgenUtils.O1.get(index),Blocks.AIR) : VanillaIntegration.pathBlocks_map.getOrDefault(WorldgenUtils.O2.get(index),Blocks.AIR)) : Blocks.AIR);
        replacerMap.put(Blocks.COARSE_DIRT, index -> {
            if (!Config.WORLDGEN.SOIL_GEN.get()) {
                return Blocks.AIR;
            }
            Block b = NOISE > 0.3 ? WorldgenUtils.A1.get(index) : WorldgenUtils.A2.get(index);
            if (RankineLists.SOIL_BLOCKS.contains(b)) {
                return RankineLists.COARSE_SOIL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(b));
            }
            return Blocks.AIR;
        });
        replacerMap.put(Blocks.MYCELIUM, index -> {
            if (!Config.WORLDGEN.SOIL_GEN.get()) {
                return Blocks.AIR;
            }
            Block b = NOISE > 0.3 ? WorldgenUtils.A1.get(index) : WorldgenUtils.A2.get(index);
            if (RankineLists.SOIL_BLOCKS.contains(b)) {
                return RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(b));
            }
            return Blocks.AIR;
        });
        replacerMap.put(Blocks.PODZOL, index -> {
            if (!Config.WORLDGEN.SOIL_GEN.get()) {
                return Blocks.AIR;
            }
            Block b = NOISE > 0.3 ? WorldgenUtils.A1.get(index) : WorldgenUtils.A2.get(index);
            if (RankineLists.SOIL_BLOCKS.contains(b)) {
                return RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(b));
            }
            return Blocks.AIR;
        });
        replacerMap.put(Blocks.GRAVEL, index -> WorldgenUtils.GRAVELS.get(index) != Blocks.AIR ? WorldgenUtils.GRAVELS.get(index) : Blocks.AIR);
        replacerMap.put(Blocks.SAND, index -> (WorldgenUtils.SANDS.get(index) != Blocks.AIR) ? WorldgenUtils.SANDS.get(index) : SAND_NOISE > 0.5f ? RankineBlocks.WHITE_SAND.get() : Blocks.AIR);
        replacerMap.put(Blocks.SANDSTONE, index -> (WorldgenUtils.SANDSTONES.get(index) != Blocks.AIR) ? WorldgenUtils.SANDSTONES.get(index) : SAND_NOISE > 0.5f ? RankineBlocks.WHITE_SANDSTONE.get() : Blocks.AIR);
        replacerMap.put(Blocks.SMOOTH_SANDSTONE, index -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(WorldgenUtils.SANDSTONES.get(index).getRegistryName().toString().replace(":",":smooth_"))));
        replacerMap.put(Blocks.SMOOTH_RED_SANDSTONE, index -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(WorldgenUtils.SANDSTONES.get(index).getRegistryName().toString().replace(":",":smooth_"))));
        replacerMap.put(Blocks.TUFF, index -> BIOME.is(BiomeTags.IS_OCEAN) ? RankineBlocks.BASALTIC_TUFF.get() :
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
                            if (genBiomesIndex != -1 && TARGET_BS.getBlock() instanceof RankineOreBlock && TARGET_BS.getValue(RankineOreBlock.TYPE) == 0) {
                                BlockState stone = getStone(blockList,y,NOISE);

                                if (stone != null && RankineLists.STONES.contains(stone.getBlock())) {
                                    chunk.setBlockState(TARGET_POS, TARGET_BS.setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(stone.getBlock())),false);
                                }
                            }
                            continue;
                        }
                    }

                    if (genBiomesIndex != -1) {
                        if (replacerMap.containsKey(TARGET)) {
                            returnedBlock = replacerMap.get(TARGET).apply(genBiomesIndex).defaultBlockState();
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

                            if (stone != null && WorldgenUtils.ORE_STONES.contains(stone.getBlock())) {
                                chunk.setBlockState(TARGET_POS, TARGET_BS.setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(stone.getBlock())),false);
                            }
                        } else if (vanillaOresMap.containsKey(TARGET)) {
                            if (!Config.WORLDGEN.REPLACE_VANILLA_ORES.get()) continue;
                            BlockState stone = getStone(blockList,y,NOISE);

                            if (stone != null && WorldgenUtils.ORE_STONES.contains(stone.getBlock())) {
                                chunk.setBlockState(TARGET_POS, vanillaOresMap.get(TARGET).defaultBlockState().setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(stone.getBlock())),false);
                            }

                        } else if (TARGET.equals(Blocks.INFESTED_STONE) || TARGET.equals(Blocks.INFESTED_DEEPSLATE)) {
                            BlockState stone = getStone(blockList,y,NOISE);
                            if (stone != null && RankineLists.STONES.contains(stone.getBlock())) {
                                chunk.setBlockState(TARGET_POS, RankineLists.INFESTED_STONES.get(RankineLists.STONES.indexOf(stone.getBlock())).defaultBlockState(),false);
                            }
                        } else if (TARGET_BS.is(Blocks.POINTED_DRIPSTONE)) {
                            Block DRIP = WorldgenUtils.DRIPSTONES.get(GEN_BIOMES.indexOf(TARGET_BIOME));
                            if (DRIP != Blocks.AIR) {
                                chunk.setBlockState(TARGET_POS, DRIP.withPropertiesOf(TARGET_BS), false);
                            }
                        } else if (TARGET_BS.is(Blocks.DRIPSTONE_BLOCK)) {
                            Block DRIP = WorldgenUtils.DRIPSTONES.get(GEN_BIOMES.indexOf(TARGET_BIOME));
                            if (DRIP != Blocks.AIR && DRIP instanceof RankinePointedDripstoneBlock) {
                                chunk.setBlockState(TARGET_POS, ((RankinePointedDripstoneBlock) DRIP).getParentDripstone().defaultBlockState(), false);
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
