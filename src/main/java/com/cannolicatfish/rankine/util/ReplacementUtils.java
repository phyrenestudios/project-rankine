package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.blocks.RankinePointedDripstoneBlock;
import com.cannolicatfish.rankine.init.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
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
    private static double STONE_NOISE = 0;
    private static double SAND_NOISE = 0;
    private static Holder<Biome> BIOME;
    private static final Map<Block, Function<Integer,Block>> replacerMap = new HashMap<>();
    private static final Map<Block, Block> vanillaOresMap = new HashMap<>();
    private static final Map<Block, Block> mudMap = new HashMap<>();
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

        mudMap.put(RankineBlocks.LOAM.get(), RankineBlocks.LOAM_MUD.get());
        mudMap.put(RankineBlocks.COARSE_LOAM.get(), RankineBlocks.LOAM_MUD.get());
        mudMap.put(RankineBlocks.LOAM_GRASS_BLOCK.get(), RankineBlocks.LOAM_MUD.get());
        mudMap.put(RankineBlocks.LOAM_PODZOL.get(), RankineBlocks.LOAM_MUD.get());
        mudMap.put(RankineBlocks.LOAM_MYCELIUM.get(), RankineBlocks.LOAM_MUD.get());
        mudMap.put(RankineBlocks.HUMUS.get(), RankineBlocks.HUMUS_MUD.get());
        mudMap.put(RankineBlocks.COARSE_HUMUS.get(), RankineBlocks.HUMUS_MUD.get());
        mudMap.put(RankineBlocks.HUMUS_GRASS_BLOCK.get(), RankineBlocks.HUMUS_MUD.get());
        mudMap.put(RankineBlocks.HUMUS_PODZOL.get(), RankineBlocks.HUMUS_MUD.get());
        mudMap.put(RankineBlocks.HUMUS_MYCELIUM.get(), RankineBlocks.HUMUS_MUD.get());
        mudMap.put(RankineBlocks.CLAY_LOAM.get(), RankineBlocks.CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.COARSE_CLAY_LOAM.get(), RankineBlocks.CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.CLAY_LOAM_GRASS_BLOCK.get(), RankineBlocks.CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.CLAY_LOAM_PODZOL.get(), RankineBlocks.CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.CLAY_LOAM_MYCELIUM.get(), RankineBlocks.CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SANDY_CLAY_LOAM.get(), RankineBlocks.SANDY_CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.COARSE_SANDY_CLAY_LOAM.get(), RankineBlocks.SANDY_CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SANDY_CLAY_LOAM_GRASS_BLOCK.get(), RankineBlocks.SANDY_CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SANDY_CLAY_LOAM_PODZOL.get(), RankineBlocks.SANDY_CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SANDY_CLAY_LOAM_MYCELIUM.get(), RankineBlocks.SANDY_CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SILTY_CLAY_LOAM.get(), RankineBlocks.SILTY_CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.COARSE_SILTY_CLAY_LOAM.get(), RankineBlocks.SILTY_CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SILTY_CLAY_LOAM_GRASS_BLOCK.get(), RankineBlocks.SILTY_CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SILTY_CLAY_LOAM_PODZOL.get(), RankineBlocks.SILTY_CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SILTY_CLAY_LOAM_MYCELIUM.get(), RankineBlocks.SILTY_CLAY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SILTY_LOAM.get(), RankineBlocks.SILTY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.COARSE_SILTY_LOAM.get(), RankineBlocks.SILTY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SILTY_LOAM_GRASS_BLOCK.get(), RankineBlocks.SILTY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SILTY_LOAM_PODZOL.get(), RankineBlocks.SILTY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SILTY_LOAM_MYCELIUM.get(), RankineBlocks.SILTY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SANDY_LOAM.get(), RankineBlocks.SANDY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.COARSE_SANDY_LOAM.get(), RankineBlocks.SANDY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SANDY_LOAM_GRASS_BLOCK.get(), RankineBlocks.SANDY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SANDY_LOAM_PODZOL.get(), RankineBlocks.SANDY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.SANDY_LOAM_MYCELIUM.get(), RankineBlocks.SANDY_LOAM_MUD.get());
        mudMap.put(RankineBlocks.LOAMY_SAND.get(), RankineBlocks.LOAMY_SAND_MUD.get());
        mudMap.put(RankineBlocks.COARSE_LOAMY_SAND.get(), RankineBlocks.LOAMY_SAND_MUD.get());
        mudMap.put(RankineBlocks.LOAMY_SAND_GRASS_BLOCK.get(), RankineBlocks.LOAMY_SAND_MUD.get());
        mudMap.put(RankineBlocks.LOAMY_SAND_PODZOL.get(), RankineBlocks.LOAMY_SAND_MUD.get());
        mudMap.put(RankineBlocks.LOAMY_SAND_MYCELIUM.get(), RankineBlocks.LOAMY_SAND_MUD.get());
        mudMap.put(RankineBlocks.SANDY_CLAY.get(), RankineBlocks.SANDY_CLAY_MUD.get());
        mudMap.put(RankineBlocks.COARSE_SANDY_CLAY.get(), RankineBlocks.SANDY_CLAY_MUD.get());
        mudMap.put(RankineBlocks.SANDY_CLAY_GRASS_BLOCK.get(), RankineBlocks.SANDY_CLAY_MUD.get());
        mudMap.put(RankineBlocks.SANDY_CLAY_PODZOL.get(), RankineBlocks.SANDY_CLAY_MUD.get());
        mudMap.put(RankineBlocks.SANDY_CLAY_MYCELIUM.get(), RankineBlocks.SANDY_CLAY_MUD.get());
        mudMap.put(RankineBlocks.SILTY_CLAY.get(), RankineBlocks.SILTY_CLAY_MUD.get());
        mudMap.put(RankineBlocks.COARSE_SILTY_CLAY.get(), RankineBlocks.SILTY_CLAY_MUD.get());
        mudMap.put(RankineBlocks.SILTY_CLAY_GRASS_BLOCK.get(), RankineBlocks.SILTY_CLAY_MUD.get());
        mudMap.put(RankineBlocks.SILTY_CLAY_PODZOL.get(), RankineBlocks.SILTY_CLAY_MUD.get());
        mudMap.put(RankineBlocks.SILTY_CLAY_MYCELIUM.get(), RankineBlocks.SILTY_CLAY_MUD.get());

        //replacerMap.put(Blocks.GRASS_BLOCK, index -> Config.WORLDGEN.SOIL_GEN.get() ? (NOISE > 0.3 ? WorldgenUtils.O1.get(index) : WorldgenUtils.O2.get(index)) : Blocks.AIR);
        replacerMap.put(Blocks.DIRT_PATH, index -> Config.WORLDGEN.SOIL_GEN.get() ? (STONE_NOISE > 0.3 ? VanillaIntegration.pathBlocks_map.getOrDefault(WorldgenUtils.O1.get(index),Blocks.AIR) : VanillaIntegration.pathBlocks_map.getOrDefault(WorldgenUtils.O2.get(index),Blocks.AIR)) : Blocks.AIR);
        replacerMap.put(Blocks.COARSE_DIRT, index -> {
            if (!Config.WORLDGEN.SOIL_GEN.get()) {
                return Blocks.AIR;
            }
            Block b = STONE_NOISE > 0.3 ? WorldgenUtils.A1.get(index) : WorldgenUtils.A2.get(index);
            if (RankineLists.SOIL_BLOCKS.contains(b)) {
                return RankineLists.COARSE_SOIL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(b));
            }
            return Blocks.AIR;
        });
        replacerMap.put(Blocks.MYCELIUM, index -> {
            if (!Config.WORLDGEN.SOIL_GEN.get()) {
                return Blocks.AIR;
            }
            Block b = STONE_NOISE > 0.3 ? WorldgenUtils.A1.get(index) : WorldgenUtils.A2.get(index);
            if (RankineLists.SOIL_BLOCKS.contains(b)) {
                return RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(b));
            }
            return Blocks.AIR;
        });
        replacerMap.put(Blocks.PODZOL, index -> {
            if (!Config.WORLDGEN.SOIL_GEN.get()) {
                return Blocks.AIR;
            }
            Block b = STONE_NOISE > 0.3 ? WorldgenUtils.A1.get(index) : WorldgenUtils.A2.get(index);
            if (RankineLists.SOIL_BLOCKS.contains(b)) {
                return RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(b));
            }
            return Blocks.AIR;
        });
        replacerMap.put(Blocks.GRAVEL, index -> WorldgenUtils.GRAVELS.get(index) != Blocks.AIR ? WorldgenUtils.GRAVELS.get(index) : Blocks.AIR);
        replacerMap.put(Blocks.SAND, index -> (WorldgenUtils.SANDS.get(index) != Blocks.AIR) ? WorldgenUtils.SANDS.get(index) : SAND_NOISE > 0.5f ? RankineBlocks.WHITE_SAND.get() : Blocks.AIR);
        replacerMap.put(RankineBlocks.SOUL_SANDSTONE.getSandstone(), index -> (WorldgenUtils.SANDSTONES.get(index) != Blocks.AIR) ? WorldgenUtils.SANDSTONES.get(index) : SAND_NOISE > 0.8f ? RankineBlocks.BLACK_SANDSTONE.getSandstone() : Blocks.AIR);
        replacerMap.put(Blocks.SANDSTONE, index -> (WorldgenUtils.SANDSTONES.get(index) != Blocks.AIR) ? WorldgenUtils.SANDSTONES.get(index) : SAND_NOISE > 0.5f ? RankineBlocks.WHITE_SANDSTONE.getSandstone() : Blocks.AIR);
        replacerMap.put(Blocks.SMOOTH_SANDSTONE, index -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(ForgeRegistries.BLOCKS.getKey(WorldgenUtils.SANDSTONES.get(index)).toString().replace(":",":smooth_"))));
        replacerMap.put(Blocks.SMOOTH_RED_SANDSTONE, index -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(ForgeRegistries.BLOCKS.getKey(WorldgenUtils.SANDSTONES.get(index)).toString().replace(":",":smooth_"))));
        replacerMap.put(Blocks.TUFF, index -> BIOME.is(BiomeTags.IS_OCEAN) ? RankineBlocks.BASALTIC_TUFF.get() :
                (BIOME.is(BiomeTags.IS_BADLANDS) || BIOME.is(Biomes.DESERT)) ? RankineBlocks.RHYOLITIC_TUFF.get() :
                        (BIOME.is(BiomeTags.IS_MOUNTAIN) || BIOME.is(BiomeTags.IS_HILL)) ? RankineBlocks.ANDESITIC_TUFF.get() : Blocks.AIR);
    }

    public static void performRetrogenReplacement(ChunkAccess chunk) {

        if (chunk.getWorldForge() == null) return;
        PerlinSimplexNoise NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(4321L)), ImmutableList.of(0));

        LevelAccessor levelIn = chunk.getWorldForge();
        RandomSource rand = levelIn.getRandom();
        BlockState returnedBlock;
        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                
                STONE_NOISE = NOISE.getValue((double) x / NOISE_SCALE, (double) z / NOISE_SCALE, false);
                SAND_NOISE = NOISE.getValue((double) x / 80, (double) z / 80, false);
                BIOME = levelIn.getBiome(new BlockPos(x, chunk.getMaxBuildHeight(), z));
                ResourceLocation TARGET_BIOME = ForgeRegistries.BIOMES.getKey(BIOME.value());
                int genBiomesIndex = GEN_BIOMES.indexOf(TARGET_BIOME);
                List<String> blockList = genBiomesIndex != -1 ? LAYER_LISTS.get(genBiomesIndex) : Collections.emptyList();
                int surfaceHeight = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                for (int y = surfaceHeight; y >= chunk.getMinBuildHeight(); --y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    BlockState TARGET_BS = chunk.getBlockState(TARGET_POS);
                    if (TARGET_BS.isAir()) continue;
                    Block TARGET = TARGET_BS.getBlock();

                    if (Config.WORLDGEN.FIRE_CLAY_GEN.get() && chunk.getBlockState(TARGET_POS).is(Tags.Blocks.ORES_COAL)) {
                        if (chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE) && rand.nextFloat() < 0.7) {
                            chunk.setBlockState(TARGET_POS.below(), RankineBlocks.FIRE_CLAY.get().defaultBlockState(), false);
                            if (genBiomesIndex != -1 && TARGET_BS.getBlock() instanceof RankineOreBlock && TARGET_BS.getValue(RankineOreBlock.TYPE) == 0) {
                                BlockState stone = getStone(blockList,y,STONE_NOISE);

                                if (stone != null && WorldgenUtils.ORE_STONES.contains(stone.getBlock())) {
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
                        } else if (TARGET.equals(Blocks.GRASS_BLOCK) && Config.WORLDGEN.SOIL_GEN.get()) {
                            Block grass = STONE_NOISE > 0.3 ? WorldgenUtils.O1.get(genBiomesIndex) : WorldgenUtils.O2.get(genBiomesIndex);
                            if (grass == Blocks.AIR ) continue;
                            /*if (mudMap.containsKey(grass) && WorldgenUtils.isWet(chunk, TARGET_POS)) {
                                chunk.setBlockState(TARGET_POS, RankineLists.MUD_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(grass)).defaultBlockState(), false);
                            } else */
                            if (grass instanceof GrassySoilBlock) {
                                chunk.setBlockState(TARGET_POS, grass.withPropertiesOf(TARGET_BS), false);
                            } else {
                                chunk.setBlockState(TARGET_POS, grass.defaultBlockState(), false);
                            }
                        } else if (TARGET.equals(Blocks.DIRT) && Config.WORLDGEN.SOIL_GEN.get()) {
                            chunk.setBlockState(TARGET_POS, STONE_NOISE > 0.3 ? WorldgenUtils.A1.get(genBiomesIndex).defaultBlockState() : WorldgenUtils.A2.get(genBiomesIndex).defaultBlockState(), false);
                            if (chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE)) {
                                chunk.setBlockState(TARGET_POS.below(), STONE_NOISE > 0.3 ? WorldgenUtils.B1.get(genBiomesIndex).defaultBlockState() : WorldgenUtils.B2.get(genBiomesIndex).defaultBlockState(), false);
                            }
                        } else if (TARGET_BS.getBlock() instanceof RankineOreBlock && TARGET_BS.getValue(RankineOreBlock.TYPE) == 0) {
                            BlockState stone = getStone(blockList,y,STONE_NOISE);
                            boolean flag = false;
                            if (stone != null && WorldgenUtils.ORE_STONES.contains(stone.getBlock())) {
                                if (BIOME.is(Biomes.CRIMSON_FOREST) || BIOME.is(Biomes.WARPED_FOREST)) {
                                    for (int i = 1; i <=Config.WORLDGEN.NETHERRACK_LAYER_THICKNESS.get(); i++) {
                                        if (chunk.getBlockState(TARGET_POS.above(i)).is(RankineTags.Blocks.NETHER_TOPS)) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (flag) {
                                        chunk.setBlockState(TARGET_POS, TARGET_BS.setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(Blocks.NETHERRACK)),false);
                                        continue;
                                    }
                                }
                                chunk.setBlockState(TARGET_POS, TARGET_BS.setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(stone.getBlock())),false);
                            }
                        } else if (vanillaOresMap.containsKey(TARGET)) {
                            if (!Config.WORLDGEN.REPLACE_VANILLA_ORES.get()) continue;
                            BlockState stone = getStone(blockList,y,STONE_NOISE);
                            boolean flag = false;
                            if (stone != null && WorldgenUtils.ORE_STONES.contains(stone.getBlock())) {
                                if (BIOME.is(Biomes.CRIMSON_FOREST) || BIOME.is(Biomes.WARPED_FOREST)) {
                                    for (int i = 1; i <=Config.WORLDGEN.NETHERRACK_LAYER_THICKNESS.get(); i++) {
                                        if (chunk.getBlockState(TARGET_POS.above(i)).is(RankineTags.Blocks.NETHER_TOPS)) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (flag) {
                                        chunk.setBlockState(TARGET_POS, vanillaOresMap.get(TARGET).defaultBlockState().setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(Blocks.NETHERRACK)),false);
                                        continue;
                                    }
                                }
                                chunk.setBlockState(TARGET_POS, vanillaOresMap.get(TARGET).defaultBlockState().setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(stone.getBlock())),false);
                            }

                        //} else if (TARGET.equals(Blocks.INFESTED_STONE) || TARGET.equals(Blocks.INFESTED_DEEPSLATE)) {
                        //    BlockState stone = getStone(blockList,y,STONE_NOISE);
                        //    if (stone != null && VanillaIntegration.RankineStonesMap.containsKey(stone.getBlock())) {
                        //        chunk.setBlockState(TARGET_POS, VanillaIntegration.RankineStonesMap.get(stone.getBlock()).getInfested().defaultBlockState(),false);
                        //    }
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
