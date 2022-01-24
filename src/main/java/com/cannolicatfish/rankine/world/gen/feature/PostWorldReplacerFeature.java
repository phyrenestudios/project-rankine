package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.Config;
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

import java.util.Random;

public class PostWorldReplacerFeature extends Feature<NoFeatureConfig> {
    public static final int NOISE_SCALE = Config.MISC_WORLDGEN.NOISE_SCALE.get();
    public static final int NOISE_OFFSET = Config.MISC_WORLDGEN.NOISE_OFFSET.get();
    public PostWorldReplacerFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
                for (int y = 0; y < endY; ++y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    Block TARGET = reader.getBlockState(TARGET_POS).getBlock();
                    ResourceLocation TARGET_BIOME = reader.getBiome(TARGET_POS).getRegistryName();

                    if (WorldgenUtils.GEN_BIOMES.contains(TARGET_BIOME)) {
                        Block Olayer;
                        Block Alayer;
                        Block Blayer;
                        if (Biome.INFO_NOISE.noiseAt((double) x / 70, (double) z / 70, false) > 0.3) {
                            Olayer = WorldgenUtils.O1.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                            Alayer = WorldgenUtils.A1.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                            Blayer = WorldgenUtils.B1.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                        } else {
                            Olayer = WorldgenUtils.O2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                            Alayer = WorldgenUtils.A2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                            Blayer = WorldgenUtils.B2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
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
                        } else if (TARGET.matchesBlock(Blocks.DIRT)) {
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
                            if (WorldgenUtils.GRAVELS.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME)) != Blocks.AIR) {
                                reader.setBlockState(TARGET_POS, WorldgenUtils.GRAVELS.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME)).getDefaultState(), 2);
                            }
                        } else if (TARGET.matchesBlock(Blocks.SAND)) {
                            if (WorldgenUtils.SANDS.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME)) != Blocks.AIR) {
                                reader.setBlockState(TARGET_POS, WorldgenUtils.SANDS.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME)).getDefaultState(), 2);
                            }
                        } else if (TARGET.matchesBlock(Blocks.SANDSTONE)) {
                            if (WorldgenUtils.SANDSTONES.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME)) != Blocks.AIR) {
                                reader.setBlockState(TARGET_POS, WorldgenUtils.SANDSTONES.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME)).getDefaultState(), 2);
                            }
                        }

                    }


                }

            }
        }
        return true;
    }


}


