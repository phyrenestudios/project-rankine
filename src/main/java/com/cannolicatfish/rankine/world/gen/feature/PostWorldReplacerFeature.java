package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class PostWorldReplacerFeature extends Feature<NoFeatureConfig> {
    public PostWorldReplacerFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
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
                        boolean dirtFlag = TARGET.is(Blocks.DIRT);
                        if (noise > 0.3) {
                            Olayer = TARGET.is(Blocks.GRASS_BLOCK) || TARGET.is(Blocks.GRASS_PATH) ? WorldgenUtils.O1.get(genBiomesIndex) : Blocks.AIR;
                            Alayer = (dirtFlag && !reader.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE)) || TARGET.is(Blocks.MYCELIUM) || TARGET.is(Blocks.PODZOL) || TARGET.is(Blocks.COARSE_DIRT) ? WorldgenUtils.A1.get(genBiomesIndex) : Blocks.AIR;
                            Blayer = dirtFlag && reader.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE) ? WorldgenUtils.B1.get(genBiomesIndex) : Blocks.AIR;
                        } else {
                            Olayer = TARGET.is(Blocks.GRASS_BLOCK) || TARGET.is(Blocks.GRASS_PATH) ? WorldgenUtils.O2.get(genBiomesIndex) : Blocks.AIR;
                            Alayer = (dirtFlag && !reader.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE)) || TARGET.is(Blocks.MYCELIUM) || TARGET.is(Blocks.PODZOL) || TARGET.is(Blocks.COARSE_DIRT) ? WorldgenUtils.A2.get(genBiomesIndex) : Blocks.AIR;
                            Blayer = dirtFlag && reader.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE) ? WorldgenUtils.B2.get(genBiomesIndex) : Blocks.AIR;
                        }
                        if (TARGET.is(Blocks.GRASS_BLOCK)) {
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
                            if (reader.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE)) {
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
                        } else if (TARGET.is(Blocks.GRASS_PATH)) {
                            if (VanillaIntegration.pathBlocks_map.containsKey(Olayer)) {
                                reader.setBlock(TARGET_POS, VanillaIntegration.pathBlocks_map.get(Olayer).defaultBlockState(), 2);
                            }
                        } else if (TARGET.is(Blocks.MYCELIUM) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                            reader.setBlock(TARGET_POS, RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), 2);
                        } else if (TARGET.is(Blocks.PODZOL) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                            reader.setBlock(TARGET_POS, RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), 2);
                        } else if (TARGET.is(Blocks.COARSE_DIRT) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                            reader.setBlock(TARGET_POS, RankineLists.COARSE_SOIL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).defaultBlockState(), 2);
                        } else if (TARGET.is(Blocks.GRAVEL)) {
                            if (WorldgenUtils.GRAVELS.get(genBiomesIndex) != Blocks.AIR) {
                                reader.setBlock(TARGET_POS, WorldgenUtils.GRAVELS.get(genBiomesIndex).defaultBlockState(), 2);
                            }
                        } else if (TARGET.is(Blocks.SAND)) {
                            if (WorldgenUtils.SANDS.get(genBiomesIndex) != Blocks.AIR) {
                                reader.setBlock(TARGET_POS, WorldgenUtils.SANDS.get(genBiomesIndex).defaultBlockState(), 2);
                            }
                        } else if (TARGET.is(Blocks.SANDSTONE)) {
                            if (WorldgenUtils.SANDSTONES.get(genBiomesIndex) != Blocks.AIR) {
                                reader.setBlock(TARGET_POS, WorldgenUtils.SANDSTONES.get(genBiomesIndex).defaultBlockState(), 2);
                            }
                        } else if (TARGET.is(Blocks.SMOOTH_SANDSTONE) || TARGET.is(Blocks.SMOOTH_RED_SANDSTONE)) {
                            if (WorldgenUtils.SANDSTONES.get(genBiomesIndex) != Blocks.AIR && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(WorldgenUtils.SANDSTONES.get(genBiomesIndex).getRegistryName().toString().replace(":",":smooth_"))) != null) {
                                reader.setBlock(TARGET_POS, ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(WorldgenUtils.SANDSTONES.get(genBiomesIndex).getRegistryName().toString().replace(":",":smooth_"))).defaultBlockState(), 2);
                            }
                        } else if (TARGET.is(Tags.Blocks.ORES_COAL)) {
                            if (reader.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE) && rand.nextFloat()<0.7) {
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


