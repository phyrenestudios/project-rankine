package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.blocks.block_enums.SoilBlocks;
import com.cannolicatfish.rankine.init.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static final Map<Block, Function<SoilBlocks,Block>> soilReplacerMap = new HashMap<>();
    private static final Map<Block, Block> vanillaOresMap = new HashMap<>();

    static {

        soilReplacerMap.put(Blocks.GRASS_BLOCK, soil -> {
            return soil.getGrassBlock();
        });
        soilReplacerMap.put(Blocks.DIRT, soil -> {
            return soil.getSoilBlock();
        });
        soilReplacerMap.put(Blocks.DIRT_PATH, soil -> {
            return soil.getPathBlock();
        });
        soilReplacerMap.put(Blocks.MUD, soil -> {
            return soil.getMudBlock();
        });
        soilReplacerMap.put(Blocks.COARSE_DIRT, soil -> {
            return soil.getCoarseSoilBlock();
        });
        soilReplacerMap.put(Blocks.MYCELIUM, soil -> {
            return soil.getMyceliumBlock();
        });
        soilReplacerMap.put(Blocks.PODZOL, soil -> {
            return soil.getPodzolBlock();
        });
        soilReplacerMap.put(Blocks.ROOTED_DIRT, soil -> {
            return soil.getRootedSoilBlock();
        });
        soilReplacerMap.put(Blocks.FARMLAND, soil -> {
            return soil.getFarmlandBlock();
        });


        /*
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

         */

        /*
        replacerMap.put(Blocks.GRAVEL, index -> WorldgenUtils.GRAVELS.get(index) != Blocks.AIR ? WorldgenUtils.GRAVELS.get(index) : Blocks.AIR);
        replacerMap.put(Blocks.SAND, index -> (WorldgenUtils.SANDS.get(index) != Blocks.AIR) ? WorldgenUtils.SANDS.get(index) : SAND_NOISE > 0.5f ? RankineBlocks.WHITE_SAND.get() : Blocks.AIR);
        replacerMap.put(RankineBlocks.SOUL_SANDSTONE.getSandstone(), index -> (WorldgenUtils.SANDSTONES.get(index) != Blocks.AIR) ? WorldgenUtils.SANDSTONES.get(index) : SAND_NOISE > 0.8f ? RankineBlocks.BLACK_SANDSTONE.getSandstone() : Blocks.AIR);
        replacerMap.put(Blocks.SANDSTONE, index -> (WorldgenUtils.SANDSTONES.get(index) != Blocks.AIR) ? WorldgenUtils.SANDSTONES.get(index) : SAND_NOISE > 0.5f ? RankineBlocks.WHITE_SANDSTONE.getSandstone() : Blocks.AIR);
        replacerMap.put(Blocks.SMOOTH_SANDSTONE, index -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(ForgeRegistries.BLOCKS.getKey(WorldgenUtils.SANDSTONES.get(index)).toString().replace(":",":smooth_"))));
        replacerMap.put(Blocks.SMOOTH_RED_SANDSTONE, index -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(ForgeRegistries.BLOCKS.getKey(WorldgenUtils.SANDSTONES.get(index)).toString().replace(":",":smooth_"))));
        replacerMap.put(Blocks.TUFF, index -> BIOME.is(BiomeTags.IS_OCEAN) ? RankineBlocks.BASALTIC_TUFF.get() :
                (BIOME.is(BiomeTags.IS_BADLANDS) || BIOME.is(Biomes.DESERT)) ? RankineBlocks.RHYOLITIC_TUFF.get() :
                        (BIOME.is(BiomeTags.IS_MOUNTAIN) || BIOME.is(BiomeTags.IS_HILL)) ? RankineBlocks.ANDESITIC_TUFF.get() : Blocks.AIR);

         */
    }

    public static void performRetrogenReplacement(ChunkAccess chunk) {

        if (chunk.getWorldForge() == null) return;
        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                SoilBlocks soil = getSoilType(x,z);
                for (int y = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z); y >= chunk.getMinBuildHeight(); --y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    BlockState TARGET_BS = chunk.getBlockState(TARGET_POS);
                    if (TARGET_BS.isAir()) continue;
                    Block TARGET = TARGET_BS.getBlock();

                    if (soilReplacerMap.containsKey(TARGET) && soil != null) {
                        chunk.setBlockState(TARGET_POS, soilReplacerMap.get(TARGET).apply(soil).defaultBlockState(), false);
                        if (TARGET_BS.is(Blocks.DIRT) && chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE)) {
                            chunk.setBlockState(TARGET_POS.below(), soil.getCoarseSoilBlock().defaultBlockState(), false);
                        }
                    }
                }



/*
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
                        if (soilReplacerMap.containsKey(TARGET)) {
                            returnedBlock = soilReplacerMap.get(TARGET).apply(genBiomesIndex).defaultBlockState();
                            if (returnedBlock != Blocks.AIR.defaultBlockState()) {
                                chunk.setBlockState(TARGET_POS, returnedBlock, false);
                            }
                        } else if (TARGET.equals(Blocks.GRASS_BLOCK) && Config.WORLDGEN.SOIL_GEN.get()) {
                            Block grass = STONE_NOISE > 0.3 ? WorldgenUtils.O1.get(genBiomesIndex) : WorldgenUtils.O2.get(genBiomesIndex);
                            if (grass == Blocks.AIR ) continue;
                            /*if (mudMap.containsKey(grass) && WorldgenUtils.isWet(chunk, TARGET_POS)) {
                                chunk.setBlockState(TARGET_POS, RankineLists.MUD_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(grass)).defaultBlockState(), false);
                            } else
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

                */
                
                
            }
        }
        chunk.setUnsaved(true);
    }

    private static SoilBlocks getSoilType(int x, int z) {
        return SoilBlocks.LOAM;
    }

}
