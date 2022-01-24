package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldReplacerFeature extends Feature<NoFeatureConfig> {
    public static final int NOISE_SCALE = Config.MISC_WORLDGEN.NOISE_SCALE.get();
    public static final int NOISE_OFFSET = Config.MISC_WORLDGEN.NOISE_OFFSET.get();
    public WorldReplacerFeature(Codec<NoFeatureConfig> configFactoryIn) {
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
                /*
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
                */

                //STONE REPLACER
                Biome BIOME = reader.getBiome(new BlockPos(x, 0, z));
                int height;
                float biomeHeight = BIOME.getDepth();
                if (BIOME.getCategory() == Biome.Category.NETHER) {
                    height = 127;
                } else if (BIOME.getCategory() == Biome.Category.THEEND) {
                    height = 65;
                } else {
                    if (biomeHeight < -0.5) {
                        height = 50;
                    } else if (biomeHeight <= 0.0) {
                        height = 60;
                    } else if (biomeHeight < 0.20) {
                        height = 70;
                    } else {
                        height = 85;
                    }
                }
                if (WorldgenUtils.GEN_BIOMES.contains(BIOME.getRegistryName())) {
                    layering(BIOME,WorldgenUtils.LAYER_LISTS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())), height, reader, x, z, endY);
                }

            }
        }
        return true;
    }




    private static int getLayerHeights(int x, int z, int index, int height) {
        double noise = Biome.INFO_NOISE.noiseAt((double)x / NOISE_SCALE + index * NOISE_OFFSET, (double)z / NOISE_SCALE + index * NOISE_OFFSET, false);
        return height + (int) Math.round((noise/ Config.MISC_WORLDGEN.LAYER_WIDTH.get()));
    }


    private static void layering( Biome targetBiome, List<String> blockList, int avgHeight, ISeedReader reader, int x, int z, int endY) {
        int stoneCount = blockList.size();
        for (int i = 0; i < stoneCount; ++i) {
            if (i == 0) {
                replaceStone(reader, targetBiome, x, z, 0, getLayerHeights(x,z,i,avgHeight/stoneCount * (i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            } else if (i < stoneCount-1) {
                replaceStone(reader, targetBiome, x, z, getLayerHeights(x,z,i-1,avgHeight/stoneCount * (i)), getLayerHeights(x,z,i,avgHeight/stoneCount * (i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            } else {
                replaceStone(reader, targetBiome, x, z, getLayerHeights(x,z,i-1,avgHeight/stoneCount * (i)), endY, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            }
        }
    }

    private static void replaceStone(ISeedReader reader, Biome targetBiome, int x, int z, int StartY, int EndY, BlockState StoneBS) {
        for (int y = StartY; y <= EndY; ++y) {
            BlockPos TARGET_POS = new BlockPos(x,y,z);
            BlockState TARGET_BS = reader.getBlockState(TARGET_POS);
            Block TARGET_BLOCK = TARGET_BS.getBlock();

            if (targetBiome.getCategory() == Biome.Category.NETHER) {
                List<net.minecraft.block.Block> TOPS = Arrays.asList(Blocks.CRIMSON_NYLIUM,Blocks.WARPED_NYLIUM,Blocks.AIR);
                if (TARGET_BS == Blocks.NETHERRACK.getDefaultState()) {
                    if (reader.getBlockState(TARGET_POS.up(1)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.up(2)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.up(3)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.down(1)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.down(2)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.down(3)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
                        reader.setBlockState(TARGET_POS, RankineBlocks.SOUL_SANDSTONE.get().getDefaultState(), 3);
                    } else if (targetBiome.getRegistryName().toString().equals(Biomes.WARPED_FOREST.getLocation().toString()) || targetBiome.getRegistryName().toString().equals(Biomes.CRIMSON_FOREST.getLocation().toString())) {
                        if (!TOPS.contains(reader.getBlockState(TARGET_POS.up(1)).getBlock()) && !TOPS.contains(reader.getBlockState(TARGET_POS.up(2)).getBlock()) && !TOPS.contains(reader.getBlockState(TARGET_POS.up(3)).getBlock())) {
                            reader.setBlockState(TARGET_POS, StoneBS, 3);
                        }
                    } else {
                        reader.setBlockState(TARGET_POS, StoneBS, 3);
                    }
                }
            } else if (targetBiome.getCategory() == Biome.Category.THEEND) {
                if (reader.getBlockState(TARGET_POS).isIn(RankineTags.Blocks.BASE_STONE_END)) {
                    reader.setBlockState(TARGET_POS, StoneBS, 3);
                }
            } else {
                if (TARGET_BLOCK instanceof RankineOreBlock && TARGET_BS.get(RankineOreBlock.TYPE) == 0 && WorldgenUtils.ORE_STONES.contains(StoneBS.getBlock())) {
                    reader.setBlockState(TARGET_POS, TARGET_BLOCK.getDefaultState().with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(StoneBS.getBlock())), 2);
                } else if (TARGET_BLOCK.isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                    reader.setBlockState(TARGET_POS, StoneBS, 3);
                } else if (TARGET_BLOCK.matchesBlock(Blocks.INFESTED_STONE) && RankineLists.STONES.contains(StoneBS.getBlock())) {
                    reader.setBlockState(TARGET_POS, ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate("rankine:infested_"+StoneBS.getBlock().getRegistryName().getPath())).getDefaultState(), 3);
                }
            }
        }
    }

}


