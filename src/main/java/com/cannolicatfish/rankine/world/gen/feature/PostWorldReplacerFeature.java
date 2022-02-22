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
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        for (int x = chunk.getPos().getXStart(); x <= chunk.getPos().getXEnd(); ++x) {
            for (int z = chunk.getPos().getZStart(); z <= chunk.getPos().getZEnd(); ++z) {
                int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
                double noise = Biome.INFO_NOISE.noiseAt((double) x / Config.MISC_WORLDGEN.SOIL_NOISE_SCALE.get(), (double) z / Config.MISC_WORLDGEN.SOIL_NOISE_SCALE.get(), false);

                for (int y = 0; y < endY; ++y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    Block TARGET = reader.getBlockState(TARGET_POS).getBlock();
                    ResourceLocation TARGET_BIOME = reader.getBiome(TARGET_POS).getRegistryName();

                    if (WorldgenUtils.GEN_BIOMES.contains(TARGET_BIOME)) {
                        Block Olayer;
                        Block Alayer;
                        Block Blayer;
                        int genBiomesIndex = WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME);
                        boolean dirtFlag = TARGET.matchesBlock(Blocks.DIRT);
                        if (noise > 0.3) {
                            Olayer = TARGET.matchesBlock(Blocks.GRASS_BLOCK) || TARGET.matchesBlock(Blocks.GRASS_PATH) ? WorldgenUtils.O1.get(genBiomesIndex) : Blocks.AIR;
                            Alayer = (dirtFlag && !reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE)) || TARGET.matchesBlock(Blocks.MYCELIUM) || TARGET.matchesBlock(Blocks.PODZOL) || TARGET.matchesBlock(Blocks.COARSE_DIRT) ? WorldgenUtils.A1.get(genBiomesIndex) : Blocks.AIR;
                            Blayer = dirtFlag && reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE) ? WorldgenUtils.B1.get(genBiomesIndex) : Blocks.AIR;
                        } else {
                            Olayer = TARGET.matchesBlock(Blocks.GRASS_BLOCK) || TARGET.matchesBlock(Blocks.GRASS_PATH) ? WorldgenUtils.O2.get(genBiomesIndex) : Blocks.AIR;
                            Alayer = (dirtFlag && !reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE)) || TARGET.matchesBlock(Blocks.MYCELIUM) || TARGET.matchesBlock(Blocks.PODZOL) || TARGET.matchesBlock(Blocks.COARSE_DIRT) ? WorldgenUtils.A2.get(genBiomesIndex) : Blocks.AIR;
                            Blayer = dirtFlag && reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE) ? WorldgenUtils.B2.get(genBiomesIndex) : Blocks.AIR;
                        }
                        if (TARGET.matchesBlock(Blocks.GRASS_BLOCK)) {
                            if (Olayer instanceof SnowyDirtBlock) {
                                if (reader.getBlockState(TARGET_POS).get(BlockStateProperties.SNOWY)) {
                                    reader.setBlockState(TARGET_POS, Olayer.getDefaultState().with(BlockStateProperties.SNOWY, true), 2);
                                } else if (RankineLists.GRASS_BLOCKS.contains(Olayer) && WorldgenUtils.isWet(reader,TARGET_POS)) {
                                    reader.setBlockState(TARGET_POS,RankineLists.MUD_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(Olayer)).getDefaultState(),2);
                                } else {
                                    reader.setBlockState(TARGET_POS,Olayer.getDefaultState(),2);
                                }
                            } else {
                                reader.setBlockState(TARGET_POS, Olayer.getDefaultState(), 2);
                            }
                        } else if (dirtFlag) {
                            if (reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE)) {
                                if (RankineLists.SOIL_BLOCKS.contains(Blayer) && WorldgenUtils.isWet(reader,TARGET_POS)) {
                                    reader.setBlockState(TARGET_POS,RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Blayer)).getDefaultState(),2);
                                } else {
                                    reader.setBlockState(TARGET_POS,Blayer.getDefaultState(),2);
                                }
                            } else {
                                if (RankineLists.SOIL_BLOCKS.contains(Alayer) && WorldgenUtils.isWet(reader,TARGET_POS)) {
                                    reader.setBlockState(TARGET_POS,RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).getDefaultState(),2);
                                } else {
                                    reader.setBlockState(TARGET_POS,Alayer.getDefaultState(),2);
                                }
                            }
                        } else if (TARGET.matchesBlock(Blocks.GRASS_PATH)) {
                            if (VanillaIntegration.pathBlocks_map.containsKey(Olayer)) {
                                reader.setBlockState(TARGET_POS, VanillaIntegration.pathBlocks_map.get(Olayer).getDefaultState(), 2);
                            }
                        } else if (TARGET.matchesBlock(Blocks.MYCELIUM) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                            reader.setBlockState(TARGET_POS, RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).getDefaultState(), 2);
                        } else if (TARGET.matchesBlock(Blocks.PODZOL) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                            reader.setBlockState(TARGET_POS, RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).getDefaultState(), 2);
                        } else if (TARGET.matchesBlock(Blocks.COARSE_DIRT) && RankineLists.SOIL_BLOCKS.contains(Alayer)) {
                            reader.setBlockState(TARGET_POS, RankineLists.COARSE_SOIL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(Alayer)).getDefaultState(), 2);
                        } else if (TARGET.matchesBlock(Blocks.GRAVEL)) {
                            if (WorldgenUtils.GRAVELS.get(genBiomesIndex) != Blocks.AIR) {
                                reader.setBlockState(TARGET_POS, WorldgenUtils.GRAVELS.get(genBiomesIndex).getDefaultState(), 2);
                            }
                        } else if (TARGET.matchesBlock(Blocks.SAND)) {
                            if (WorldgenUtils.SANDS.get(genBiomesIndex) != Blocks.AIR) {
                                reader.setBlockState(TARGET_POS, WorldgenUtils.SANDS.get(genBiomesIndex).getDefaultState(), 2);
                            }
                        } else if (TARGET.matchesBlock(Blocks.SANDSTONE)) {
                            if (WorldgenUtils.SANDSTONES.get(genBiomesIndex) != Blocks.AIR) {
                                reader.setBlockState(TARGET_POS, WorldgenUtils.SANDSTONES.get(genBiomesIndex).getDefaultState(), 2);
                            }
                        } else if (TARGET.matchesBlock(Blocks.SMOOTH_SANDSTONE) || TARGET.matchesBlock(Blocks.SMOOTH_RED_SANDSTONE)) {
                            if (WorldgenUtils.SANDSTONES.get(genBiomesIndex) != Blocks.AIR && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(WorldgenUtils.SANDSTONES.get(genBiomesIndex).getRegistryName().toString().replace(":",":smooth_"))) != null) {
                                reader.setBlockState(TARGET_POS, ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(WorldgenUtils.SANDSTONES.get(genBiomesIndex).getRegistryName().toString().replace(":",":smooth_"))).getDefaultState(), 2);
                            }
                        } else if (TARGET.isIn(Tags.Blocks.ORES_COAL)) {
                            if (reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE) && rand.nextFloat()<0.7) {
                                reader.setBlockState(TARGET_POS.down(), RankineBlocks.FIRE_CLAY.get().getDefaultState(), 2);
                            }
                        }

                    }


                }

            }
        }
        return true;
    }


}


