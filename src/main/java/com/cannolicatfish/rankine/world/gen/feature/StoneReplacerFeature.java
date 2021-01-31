package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StoneReplacerFeature extends Feature<StoneReplacerFeatureConfig> {


    public StoneReplacerFeature(Codec<StoneReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public static final int NOISE_SCALE = Config.NOISE_SCALE.get();
    public static final int NOISE_OFFSET = Config.NOISE_OFFSET.get();

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, StoneReplacerFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
                if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.OCEAN && !Config.OCEAN_STONE_LIST.get().isEmpty()) {
                    layering(Config.OCEAN_STONE_LIST.get(), 50, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.MUSHROOM && !Config.MUSHROOM_STONE_LIST.get().isEmpty()) {
                    layering(Config.MUSHROOM_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.DESERT && !Config.DESERT_STONE_LIST.get().isEmpty()) {
                    layering(Config.DESERT_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.MESA && !Config.MESA_STONE_LIST.get().isEmpty()) {
                    layering(Config.MESA_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.SAVANNA && !Config.SAVANNA_STONE_LIST.get().isEmpty()) {
                    layering(Config.SAVANNA_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.EXTREME_HILLS && !Config.MOUNTAIN_STONE_LIST.get().isEmpty()) {
                    layering(Config.MOUNTAIN_STONE_LIST.get(), 80, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.JUNGLE && !Config.JUNGLE_STONE_LIST.get().isEmpty()) {
                    layering(Config.JUNGLE_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.PLAINS && !Config.PLAINS_STONE_LIST.get().isEmpty()) {
                    layering(Config.PLAINS_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.ICY && !Config.ICY_STONE_LIST.get().isEmpty()) {
                    layering(Config.ICY_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.SWAMP && !Config.SWAMP_STONE_LIST.get().isEmpty()) {
                    layering(Config.SWAMP_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.BEACH && !Config.BEACH_STONE_LIST.get().isEmpty()) {
                    layering(Config.BEACH_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.RIVER && !Config.RIVER_STONE_LIST.get().isEmpty()) {
                    layering(Config.RIVER_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else if (reader.getBiome(new BlockPos(x,0,z)).getCategory() == Biome.Category.FOREST && !Config.FOREST_STONE_LIST.get().isEmpty()) {
                    layering(Config.FOREST_STONE_LIST.get(), 65, reader, config, x, z, endY);
                } else {
                    layering(Config.DEFAULT_STONE_LIST.get(), 65, reader, config, x, z, endY);
                }
            }
        }
        return true;
    }


    private static int getLayerHeights(int x, int z, int index, int height) {
        double noise = Biome.INFO_NOISE.noiseAt((double)x / NOISE_SCALE + index * NOISE_OFFSET, (double)z / NOISE_SCALE + index * NOISE_OFFSET, false);
        return height + (int) Math.round((noise/0.25));
    }


    private static void layering(List<String> blockList, int avgHeight, ISeedReader reader, StoneReplacerFeatureConfig config, int x, int z, int endY) {
        int stoneCount = blockList.size();
        for (int i = 0; i < stoneCount; ++i) {
            if (i == 0) {
                replaceStone(reader, config, x, z, 0, getLayerHeights(x,z,i,avgHeight/stoneCount * (i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            } else if (i < stoneCount-1) {
                replaceStone(reader, config, x, z, getLayerHeights(x,z,i-1,avgHeight/stoneCount * (i)), getLayerHeights(x,z,i,avgHeight/stoneCount * (i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            } else {
                replaceStone(reader, config, x, z, getLayerHeights(x,z,i-1,avgHeight/stoneCount * (i)), endY, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            }
        }
    }

    private static void replaceStone(ISeedReader reader, StoneReplacerFeatureConfig config, int x, int z, int StartY, int EndY, BlockState Block) {
        for (int y = StartY; y <= EndY; ++y) {
            if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                reader.setBlockState(new BlockPos(x, y, z), Block, 2);
            }
        }
    }

    private static void replaceAll(ISeedReader reader, StoneReplacerFeatureConfig config, int x, int z, int StartY, int EndY, BlockState Block) {
        for (int y = StartY; y <= EndY; ++y) {
            if (reader.getBlockState(new BlockPos(x, y, z)).getBlock().getTags().contains(new ResourceLocation("forge:stone"))) {
                reader.setBlockState(new BlockPos(x, y, z), Block, 2);
            }
        }
    }
}
