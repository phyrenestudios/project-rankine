package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.blocks.SoilBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class SoilReplacerFeature extends Feature<NoFeatureConfig> {

    public SoilReplacerFeature(Codec<NoFeatureConfig> configFactoryIn) {
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
                for (int y = endY; y > 0; --y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    Block TARGET = reader.getBlockState(TARGET_POS).getBlock();
                    ResourceLocation TARGET_BIOME = reader.getBiome(TARGET_POS).getRegistryName();

                    Block O1 = WorldgenUtils.O1.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block A1 = WorldgenUtils.A1.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block B1 = WorldgenUtils.B1.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block O2 = WorldgenUtils.O2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block A2 = WorldgenUtils.A2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block B2 = WorldgenUtils.B2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    double noise = Biome.INFO_NOISE.noiseAt((double) x / 70, (double) z / 70, false);

                    if (WorldgenUtils.GEN_BIOMES.contains(TARGET_BIOME)) {
                        if (noise > 0.3) {
                            if (TARGET.matchesBlock(Blocks.GRASS_BLOCK)) {
                                if (O2 instanceof SnowyDirtBlock) {
                                    if (reader.getBlockState(TARGET_POS).get(BlockStateProperties.SNOWY)) {
                                        reader.setBlockState(TARGET_POS, O2.getDefaultState().with(BlockStateProperties.SNOWY, true), 2);
                                    } else if (RankineLists.GRASS_BLOCKS.contains(O2) && WorldgenUtils.isWet(reader,TARGET_POS)) {
                                        reader.setBlockState(TARGET_POS,RankineLists.MUD_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(O2)).getDefaultState(),2);
                                    } else {
                                        reader.setBlockState(TARGET_POS,O2.getDefaultState(),2);
                                    }
                                } else {
                                    reader.setBlockState(TARGET_POS, O2.getDefaultState(), 2);
                                }
                            } else if (TARGET.matchesBlock(Blocks.DIRT)) {
                                if (reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE) && RankineLists.SOIL_BLOCKS.contains(B2) && WorldgenUtils.isWet(reader,TARGET_POS.down())) {
                                        reader.setBlockState(TARGET_POS.down(),RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(B2)).getDefaultState(),2);
                                } else {
                                    reader.setBlockState(TARGET_POS.down(),B2.getDefaultState(),2);
                                }
                                if (reader.getBlockState(TARGET_POS.down(2)).isIn(Tags.Blocks.STONE) && RankineLists.SOIL_BLOCKS.contains(B2) && WorldgenUtils.isWet(reader,TARGET_POS.down(2))) {
                                    reader.setBlockState(TARGET_POS.down(2),RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(B2)).getDefaultState(),2);
                                } else {
                                    reader.setBlockState(TARGET_POS.down(2),B2.getDefaultState(),2);
                                }
                                if (RankineLists.SOIL_BLOCKS.contains(A2) && WorldgenUtils.isWet(reader,TARGET_POS)) {
                                    reader.setBlockState(TARGET_POS,RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(A2)).getDefaultState(),2);
                                } else {
                                    reader.setBlockState(TARGET_POS,A2.getDefaultState(),2);
                                }
                            } else if (TARGET.matchesBlock(Blocks.GRASS_PATH)) {
                                if (VanillaIntegration.pathBlocks_map.containsKey(O2)) {
                                    reader.setBlockState(TARGET_POS, VanillaIntegration.pathBlocks_map.get(O2).getDefaultState(), 2);
                                }
                            } else if (TARGET.matchesBlock(Blocks.MYCELIUM) && RankineLists.SOIL_BLOCKS.contains(A2)) {
                                reader.setBlockState(TARGET_POS, RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(A2)).getDefaultState(), 2);
                            } else if (TARGET.matchesBlock(Blocks.PODZOL) && RankineLists.SOIL_BLOCKS.contains(A2)) {
                                reader.setBlockState(TARGET_POS, RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(A2)).getDefaultState(), 2);
                            }
                            //COARSE DIRT
                        } else {
                            if (TARGET.matchesBlock(Blocks.GRASS_BLOCK)) {
                                if (O1 instanceof SnowyDirtBlock) {
                                    if (reader.getBlockState(TARGET_POS).get(BlockStateProperties.SNOWY)) {
                                        reader.setBlockState(TARGET_POS, O1.getDefaultState().with(BlockStateProperties.SNOWY, true), 2);
                                    } else if (RankineLists.GRASS_BLOCKS.contains(O1) && WorldgenUtils.isWet(reader,TARGET_POS)) {
                                        reader.setBlockState(TARGET_POS,RankineLists.MUD_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(O1)).getDefaultState(),2);
                                    } else {
                                        reader.setBlockState(TARGET_POS,O1.getDefaultState(),2);
                                    }
                                } else {
                                    reader.setBlockState(TARGET_POS, O1.getDefaultState(), 2);
                                }
                            } else if (TARGET.matchesBlock(Blocks.DIRT)) {
                                if (reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE) && RankineLists.SOIL_BLOCKS.contains(B1) && WorldgenUtils.isWet(reader,TARGET_POS.down())) {
                                    reader.setBlockState(TARGET_POS.down(),RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(B1)).getDefaultState(),2);
                                } else {
                                    reader.setBlockState(TARGET_POS.down(),B1.getDefaultState(),2);
                                }
                                if (reader.getBlockState(TARGET_POS.down(2)).isIn(Tags.Blocks.STONE) && RankineLists.SOIL_BLOCKS.contains(B1) && WorldgenUtils.isWet(reader,TARGET_POS.down(2))) {
                                    reader.setBlockState(TARGET_POS.down(2),RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(B1)).getDefaultState(),2);
                                } else {
                                    reader.setBlockState(TARGET_POS.down(2),B1.getDefaultState(),2);
                                }
                                if (RankineLists.SOIL_BLOCKS.contains(A1) && WorldgenUtils.isWet(reader,TARGET_POS)) {
                                    reader.setBlockState(TARGET_POS,RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(A1)).getDefaultState(),2);
                                } else {
                                    reader.setBlockState(TARGET_POS,A1.getDefaultState(),2);
                                }
                            } else if (TARGET.matchesBlock(Blocks.GRASS_PATH)) {
                                if (VanillaIntegration.pathBlocks_map.containsKey(O1)) {
                                    reader.setBlockState(TARGET_POS, VanillaIntegration.pathBlocks_map.get(O1).getDefaultState(), 2);
                                }
                            } else if (TARGET.matchesBlock(Blocks.MYCELIUM) && RankineLists.SOIL_BLOCKS.contains(A1)) {
                                reader.setBlockState(TARGET_POS, RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(A1)).getDefaultState(), 2);
                            } else if (TARGET.matchesBlock(Blocks.PODZOL) && RankineLists.SOIL_BLOCKS.contains(A1)) {
                                reader.setBlockState(TARGET_POS, RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(A1)).getDefaultState(), 2);
                            }
                            //COARSE DIRT
                        }
                        if (TARGET.matchesBlock(Blocks.GRAVEL)) {
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
                        } else if (TARGET.isIn(Tags.Blocks.STONE) && reader.getBlockState(TARGET_POS.up()).isIn(Tags.Blocks.ORES_COAL)) {
                            reader.setBlockState(TARGET_POS, RankineBlocks.FIRE_CLAY.get().getDefaultState(), 2);
                            if (rand.nextFloat()<0.5 && reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE)) {
                                reader.setBlockState(TARGET_POS.down(), RankineBlocks.FIRE_CLAY.get().getDefaultState(), 2);
                            }
                        } else if (TARGET == Blocks.COAL_ORE) {
                            reader.setBlockState(TARGET_POS,RankineBlocks.COAL_ORE.get().getDefaultState(), 2);
                        } else if (TARGET == Blocks.IRON_ORE) {
                            reader.setBlockState(TARGET_POS,RankineBlocks.IRON_ORE.get().getDefaultState(), 2);
                        } else if (TARGET == Blocks.DIAMOND_ORE) {
                            reader.setBlockState(TARGET_POS,RankineBlocks.DIAMOND_ORE.get().getDefaultState(), 2);
                        } else if (TARGET == Blocks.LAPIS_ORE) {
                            reader.setBlockState(TARGET_POS,RankineBlocks.LAPIS_ORE.get().getDefaultState(), 2);
                        } else if (TARGET == Blocks.REDSTONE_ORE) {
                            reader.setBlockState(TARGET_POS,RankineBlocks.REDSTONE_ORE.get().getDefaultState(), 2);
                        } else if (TARGET == Blocks.GOLD_ORE) {
                            reader.setBlockState(TARGET_POS,RankineBlocks.GOLD_ORE.get().getDefaultState(), 2);
                        } else if (TARGET == Blocks.EMERALD_ORE) {
                            reader.setBlockState(TARGET_POS,RankineBlocks.EMERALD_ORE.get().getDefaultState(), 2);
                        } else if (TARGET == Blocks.NETHER_GOLD_ORE) {
                            reader.setBlockState(TARGET_POS,RankineBlocks.NETHER_GOLD_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 6), 2);
                        } else if (TARGET == Blocks.NETHER_QUARTZ_ORE) {
                            reader.setBlockState(TARGET_POS,RankineBlocks.NETHER_QUARTZ_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 6), 2);
                        }

                    }


                }
            }
        }
        return true;
    }

}


