package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class PostWorldReplacerFeature extends Feature<NoneFeatureConfiguration> {
    public PostWorldReplacerFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();

        ChunkAccess chunk = reader.getChunk(pos);
        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                int endY = reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
                double noise = Biome.BIOME_INFO_NOISE.getValue((double) x / Config.MISC_WORLDGEN.SOIL_NOISE_SCALE.get(), (double) z / Config.MISC_WORLDGEN.SOIL_NOISE_SCALE.get(), false);

                for (int y = 0; y < endY; ++y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    Block TARGET = reader.getBlockState(TARGET_POS).getBlock();
                    ResourceLocation TARGET_BIOME = reader.getBiome(TARGET_POS).getRegistryName();

                    if (WorldgenUtils.GEN_BIOMES.contains(TARGET_BIOME)) {
                        Block Olayer;
                        Block Alayer;
                        Block Blayer;
                        int genBiomesIndex = WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME);
                        boolean dirtFlag = TARGET.equals(Blocks.DIRT);
                        if (noise > 0.3) {
                            Olayer = TARGET.equals(Blocks.GRASS_BLOCK) || TARGET.equals(Blocks.DIRT_PATH) ? WorldgenUtils.O1.get(genBiomesIndex) : Blocks.AIR;
                            Alayer = dirtFlag && !Tags.Blocks.STONE.contains(reader.getBlockState(TARGET_POS.below()).getBlock()) || TARGET.equals(Blocks.MYCELIUM) || TARGET.equals(Blocks.PODZOL) || TARGET.equals(Blocks.COARSE_DIRT) ? WorldgenUtils.A1.get(genBiomesIndex) : Blocks.AIR;
                            Blayer = dirtFlag && Tags.Blocks.STONE.contains(reader.getBlockState(TARGET_POS.below()).getBlock()) ? WorldgenUtils.B1.get(genBiomesIndex) : Blocks.AIR;
                        } else {
                            Olayer = TARGET.equals(Blocks.GRASS_BLOCK) || TARGET.equals(Blocks.DIRT_PATH) ? WorldgenUtils.O2.get(genBiomesIndex) : Blocks.AIR;
                            Alayer = dirtFlag && !Tags.Blocks.STONE.contains(reader.getBlockState(TARGET_POS.below()).getBlock()) || TARGET.equals(Blocks.MYCELIUM) || TARGET.equals(Blocks.PODZOL) || TARGET.equals(Blocks.COARSE_DIRT) ? WorldgenUtils.A2.get(genBiomesIndex) : Blocks.AIR;
                            Blayer = dirtFlag && Tags.Blocks.STONE.contains(reader.getBlockState(TARGET_POS.below()).getBlock()) ? WorldgenUtils.B2.get(genBiomesIndex) : Blocks.AIR;
                        }
                        if (TARGET.equals(Blocks.GRASS_BLOCK)) {
                            if (Olayer instanceof SnowyDirtBlock) {
                                if (reader.getBlockState(TARGET_POS).getValue(BlockStateProperties.SNOWY)) {
                                    reader.setBlock(TARGET_POS, Olayer.defaultBlockState().setValue(BlockStateProperties.SNOWY, true), 2);
                                } else if (RankineLists.GRASS_BLOCKS.contains(Olayer) && WorldgenUtils.isWet(reader,TARGET_POS)) {
                                    reader.setBlock(TARGET_POS,RankineLists.MUD_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(Olayer)).defaultBlockState(),2);
                                } else {
                                    reader.setBlock(TARGET_POS,Olayer.defaultBlockState(),2);
                                }
                            } else {
                                reader.setBlock(TARGET_POS, Olayer.defaultBlockState(), 2);
                            }
                        } else if (dirtFlag) {
                            if (reader.getBlockState(TARGET_POS.below()).equals(Tags.Blocks.STONE)) {
                                if (RankineLists.SOIL_BLOCKS.contains(Blayer) && WorldgenUtils.isWet(reader,TARGET_POS)) {
                                    reader.setBlock(TARGET_POS,RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Blayer)).defaultBlockState(),2);
                                } else {
                                    reader.setBlock(TARGET_POS,Blayer.defaultBlockState(),2);
                                }
                            } else {
                                if (RankineLists.SOIL_BLOCKS.contains(Alayer) && WorldgenUtils.isWet(reader,TARGET_POS)) {
                                    reader.setBlock(TARGET_POS,RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(),2);
                                } else {
                                    reader.setBlock(TARGET_POS,Alayer.defaultBlockState(),2);
                                }
                            }
                        } else if (TARGET.equals(Blocks.DIRT_PATH)) {
                            if (VanillaIntegration.pathBlocks_map.containsKey(Olayer)) {
                                reader.setBlock(TARGET_POS, VanillaIntegration.pathBlocks_map.get(Olayer).defaultBlockState(), 2);
                            }
                        } else if (TARGET.equals(Blocks.MYCELIUM) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                            reader.setBlock(TARGET_POS, RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), 2);
                        } else if (TARGET.equals(Blocks.PODZOL) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                            reader.setBlock(TARGET_POS, RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), 2);
                        } else if (TARGET.equals(Blocks.COARSE_DIRT) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                            reader.setBlock(TARGET_POS, RankineLists.COARSE_SOIL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), 2);
                        } else if (TARGET.equals(Blocks.GRAVEL)) {
                            if (WorldgenUtils.GRAVELS.get(genBiomesIndex) != Blocks.AIR) {
                                reader.setBlock(TARGET_POS, WorldgenUtils.GRAVELS.get(genBiomesIndex).defaultBlockState(), 2);
                            }
                        } else if (TARGET.equals(Blocks.SAND)) {
                            if (WorldgenUtils.SANDS.get(genBiomesIndex) != Blocks.AIR) {
                                reader.setBlock(TARGET_POS, WorldgenUtils.SANDS.get(genBiomesIndex).defaultBlockState(), 2);
                            }
                        } else if (TARGET.equals(Blocks.SANDSTONE)) {
                            if (WorldgenUtils.SANDSTONES.get(genBiomesIndex) != Blocks.AIR) {
                                reader.setBlock(TARGET_POS, WorldgenUtils.SANDSTONES.get(genBiomesIndex).defaultBlockState(), 2);
                            }
                        } else if (TARGET.equals(Blocks.SMOOTH_SANDSTONE) || TARGET.equals(Blocks.SMOOTH_RED_SANDSTONE)) {
                            if (WorldgenUtils.SANDSTONES.get(genBiomesIndex) != Blocks.AIR && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(WorldgenUtils.SANDSTONES.get(genBiomesIndex).getRegistryName().toString().replace(":",":smooth_"))) != null) {
                                reader.setBlock(TARGET_POS, ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(WorldgenUtils.SANDSTONES.get(genBiomesIndex).getRegistryName().toString().replace(":",":smooth_"))).defaultBlockState(), 2);
                            }
                        } else if (Tags.Blocks.ORES_COAL.contains(TARGET)) {
                            if (Tags.Blocks.STONE.contains(reader.getBlockState(TARGET_POS.below()).getBlock()) && rand.nextFloat()<0.7) {
                                reader.setBlock(TARGET_POS.below(), RankineBlocks.FIRE_CLAY.get().defaultBlockState(), 2);
                            }
                        }

                    }


                }

            }
        }
        return true;
    }


}


