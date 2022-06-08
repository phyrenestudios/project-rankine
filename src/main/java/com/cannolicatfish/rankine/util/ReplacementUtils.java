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

import java.util.Random;

public class ReplacementUtils {

    public static void performRetrogenReplacement(ChunkAccess chunk) {
        if (chunk.getWorldForge() == null) return;
        LevelAccessor levelIn = chunk.getWorldForge();
        
        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                
                double noise = Biome.BIOME_INFO_NOISE.getValue((double) x / Config.WORLDGEN.SOIL_NOISE_SCALE.get(), (double) z / Config.WORLDGEN.SOIL_NOISE_SCALE.get(), false);
                double sandNoise = Biome.BIOME_INFO_NOISE.getValue((double) x / 80, (double) z / 80, false);
                Random rand = levelIn.getRandom();
                Holder<Biome> BIOME = levelIn.getBiome(new BlockPos(x, chunk.getMaxBuildHeight(), z));
                ResourceLocation TARGET_BIOME = BIOME.value().getRegistryName();

                for (int y = chunk.getMinBuildHeight(); y <= chunk.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z); ++y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    BlockState TARGET_BS = chunk.getBlockState(TARGET_POS);
                    Block TARGET = TARGET_BS.getBlock();

                    if (chunk.getBlockState(TARGET_POS).is(Tags.Blocks.ORES_COAL)) {
                        if (chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE) && rand.nextFloat() < 0.7) {
                            chunk.setBlockState(TARGET_POS.below(), RankineBlocks.FIRE_CLAY.get().defaultBlockState(), false);
                            continue;
                        }
                    }

                    if (WorldgenUtils.GEN_BIOMES.contains(TARGET_BIOME)) {
                        Block Olayer;
                        Block Alayer;
                        Block Blayer;
                        int genBiomesIndex = WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME);
                        boolean dirtFlag = TARGET.equals(Blocks.DIRT);
                        if (noise > 0.3) {
                            Olayer = TARGET.equals(Blocks.GRASS_BLOCK) || TARGET.equals(Blocks.DIRT_PATH) ? WorldgenUtils.O1.get(genBiomesIndex) : Blocks.AIR;
                            Alayer = dirtFlag && !chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE) || TARGET.equals(Blocks.MYCELIUM) || TARGET.equals(Blocks.PODZOL) || TARGET.equals(Blocks.COARSE_DIRT) ? WorldgenUtils.A1.get(genBiomesIndex) : Blocks.AIR;
                            Blayer = dirtFlag && chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE) ? WorldgenUtils.B1.get(genBiomesIndex) : Blocks.AIR;
                        } else {
                            Olayer = TARGET.equals(Blocks.GRASS_BLOCK) || TARGET.equals(Blocks.DIRT_PATH) ? WorldgenUtils.O2.get(genBiomesIndex) : Blocks.AIR;
                            Alayer = dirtFlag && !chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE) || TARGET.equals(Blocks.MYCELIUM) || TARGET.equals(Blocks.PODZOL) || TARGET.equals(Blocks.COARSE_DIRT) ? WorldgenUtils.A2.get(genBiomesIndex) : Blocks.AIR;
                            Blayer = dirtFlag && chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE) ? WorldgenUtils.B2.get(genBiomesIndex) : Blocks.AIR;
                        }

                        if (Config.WORLDGEN.SOIL_GEN.get()) {
                            if (TARGET.equals(Blocks.GRASS_BLOCK)) {
                                if (Olayer instanceof SnowyDirtBlock) {
                                    chunk.setBlockState(TARGET_POS, Olayer.defaultBlockState().setValue(BlockStateProperties.SNOWY, TARGET_BS.getValue(BlockStateProperties.SNOWY)), false);
                                } else {
                                    chunk.setBlockState(TARGET_POS, Olayer.defaultBlockState(), false);
                                }
                            } else if (dirtFlag) {
                                if (chunk.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE)) {
                                    //if (RankineLists.SOIL_BLOCKS.contains(Blayer) && WorldgenUtils.isWet(levelIn, TARGET_POS)) {
                                   //     chunk.setBlockState(TARGET_POS, RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Blayer)).defaultBlockState(), false);
                                    //} else {
                                        chunk.setBlockState(TARGET_POS, Blayer.defaultBlockState(), false);
                                    //}
                                } else {
                                    //if (RankineLists.SOIL_BLOCKS.contains(Alayer) && WorldgenUtils.isWet(levelIn, TARGET_POS)) {
                                    //    chunk.setBlockState(TARGET_POS, RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), false);
                                    //} else {
                                        chunk.setBlockState(TARGET_POS, Alayer.defaultBlockState(), false);
                                    //}
                                }
                            } else if (TARGET.equals(Blocks.DIRT_PATH)) {
                                if (VanillaIntegration.pathBlocks_map.containsKey(Olayer)) {
                                    chunk.setBlockState(TARGET_POS, VanillaIntegration.pathBlocks_map.get(Olayer).defaultBlockState(), false);
                                }
                            } else if (TARGET.equals(Blocks.MYCELIUM) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                                chunk.setBlockState(TARGET_POS, RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), false);
                            } else if (TARGET.equals(Blocks.PODZOL) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                                chunk.setBlockState(TARGET_POS, RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), false);
                            } else if (TARGET.equals(Blocks.COARSE_DIRT) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                                chunk.setBlockState(TARGET_POS, RankineLists.COARSE_SOIL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), false);
                            }
                        }

                        if (TARGET.equals(Blocks.GRAVEL)) {
                            if (WorldgenUtils.GRAVELS.get(genBiomesIndex) != Blocks.AIR) {
                                chunk.setBlockState(TARGET_POS, WorldgenUtils.GRAVELS.get(genBiomesIndex).defaultBlockState(), false);
                            }
                        } else if (TARGET.equals(Blocks.SAND)) {
                            if (WorldgenUtils.SANDS.get(genBiomesIndex) != Blocks.AIR) {
                                chunk.setBlockState(TARGET_POS, WorldgenUtils.SANDS.get(genBiomesIndex).defaultBlockState(), false);
                            } else if (Config.WORLDGEN.WHITE_SAND_GEN.get() && sandNoise > 0.5) {
                                chunk.setBlockState(TARGET_POS, RankineBlocks.WHITE_SAND.get().defaultBlockState(),false);
                            }
                        } else if (TARGET.equals(Blocks.SANDSTONE)) {
                            if (WorldgenUtils.SANDSTONES.get(genBiomesIndex) != Blocks.AIR) {
                                chunk.setBlockState(TARGET_POS, WorldgenUtils.SANDSTONES.get(genBiomesIndex).defaultBlockState(), false);
                            } else if (Config.WORLDGEN.WHITE_SAND_GEN.get() && sandNoise > 0.5) {
                                chunk.setBlockState(TARGET_POS,RankineBlocks.WHITE_SANDSTONE.get().defaultBlockState(),false);
                            }
                        } else if (TARGET.equals(Blocks.SMOOTH_SANDSTONE) || TARGET.equals(Blocks.SMOOTH_RED_SANDSTONE)) {
                            if (WorldgenUtils.SANDSTONES.get(genBiomesIndex) != Blocks.AIR && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(WorldgenUtils.SANDSTONES.get(genBiomesIndex).getRegistryName().toString().replace(":",":smooth_"))) != null) {
                                chunk.setBlockState(TARGET_POS, ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(WorldgenUtils.SANDSTONES.get(genBiomesIndex).getRegistryName().toString().replace(":",":smooth_"))).defaultBlockState(), false);
                            }
                        } else if (TARGET.equals(Blocks.TUFF)) {
                            if (BIOME.is(BiomeTags.IS_OCEAN)) {
                                chunk.setBlockState(TARGET_POS, RankineBlocks.BASALTIC_TUFF.get().defaultBlockState(), false);
                            } else if (BIOME.is(BiomeTags.IS_BADLANDS) || BIOME.is(Biomes.DESERT)) {
                                chunk.setBlockState(TARGET_POS, RankineBlocks.RHYOLITIC_TUFF.get().defaultBlockState(), false);
                            } else if (BIOME.is(BiomeTags.IS_MOUNTAIN) || BIOME.is(BiomeTags.IS_HILL)) {
                                chunk.setBlockState(TARGET_POS, RankineBlocks.ANDESITIC_TUFF.get().defaultBlockState(), false);
                            }
                        } else if (TARGET.equals(Blocks.INFESTED_STONE) || TARGET.equals(Blocks.INFESTED_DEEPSLATE)) {
                            BlockState stone = WorldReplacerFeature.getStone(TARGET_BIOME, x,y,z);
                            if (stone != null && RankineLists.STONES.contains(stone.getBlock())) {
                                chunk.setBlockState(TARGET_POS, RankineLists.INFESTED_STONES.get(RankineLists.STONES.indexOf(stone.getBlock())).defaultBlockState(),false);
                            }
                        } else if (TARGET_BS.getBlock() instanceof RankineOreBlock && TARGET_BS.getValue(RankineOreBlock.TYPE) == 0) {
                            BlockState stone = WorldReplacerFeature.getStone(TARGET_BIOME, x,y,z);

                            if (stone != null && RankineLists.STONES.contains(stone.getBlock())) {
                                chunk.setBlockState(TARGET_POS, TARGET_BS.setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(stone.getBlock())),false);
                            }

                        }


                    }


                }
                
                
            }
        }
        chunk.setUnsaved(true);
    }
}
