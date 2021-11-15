package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
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
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StoneReplacerFeature extends Feature<NoFeatureConfig> {


    public StoneReplacerFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public static final int NOISE_SCALE = Config.MISC_WORLDGEN.NOISE_SCALE.get();
    public static final int NOISE_OFFSET = Config.MISC_WORLDGEN.NOISE_OFFSET.get();

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
        return height + (int) Math.round((noise/Config.MISC_WORLDGEN.LAYER_WIDTH.get()));
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

    private static void replaceStone(ISeedReader reader, Biome targetBiome, int x, int z, int StartY, int EndY, BlockState Block) {
        for (int y = StartY; y <= EndY; ++y) {
            BlockPos TARGET_POS = new BlockPos(x,y,z);
            if (targetBiome.getCategory() == Biome.Category.NETHER) {
                List<net.minecraft.block.Block> TOPS = Arrays.asList(Blocks.CRIMSON_NYLIUM,Blocks.WARPED_NYLIUM,Blocks.AIR);
                BlockState target = reader.getBlockState(TARGET_POS);
                if (target == Blocks.NETHERRACK.getDefaultState()) {
                    if (targetBiome.getRegistryName() != Biomes.BASALT_DELTAS.getRegistryName()) {
                        if (reader.getBlockState(TARGET_POS.up(1)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.up(2)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.up(3)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.up(4)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.down(1)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.down(2)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.down(3)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(TARGET_POS.down(4)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
                            reader.setBlockState(TARGET_POS, RankineBlocks.SOUL_SANDSTONE.get().getDefaultState(), 2);
                        } else if (!TOPS.contains(reader.getBlockState(TARGET_POS.up(1)).getBlock()) && !TOPS.contains(reader.getBlockState(TARGET_POS.up(2)).getBlock()) && !TOPS.contains(reader.getBlockState(TARGET_POS.up(3)).getBlock())) {
                            reader.setBlockState(TARGET_POS, Block, 2);
                        }
                    } else {
                        reader.setBlockState(TARGET_POS, Block, 2);
                    }
                }
            } else if (targetBiome.getCategory() == Biome.Category.THEEND) {
                if (reader.getBlockState(TARGET_POS).isIn(RankineTags.Blocks.BASE_STONE_END)) {
                    reader.setBlockState(TARGET_POS, Block, 2);
                }
            } else {
                if (reader.getBlockState(TARGET_POS).isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                    reader.setBlockState(TARGET_POS, Block, 2);
                }
            }
        }
    }
}