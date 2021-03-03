package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.WGConfig;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class NetherStoneReplacerFeature extends Feature<NoFeatureConfig> {


    public NetherStoneReplacerFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }
    public static final int NOISE_SCALE = WGConfig.LAYERS.NOISE_SCALE.get();
    public static final int NOISE_OFFSET = WGConfig.LAYERS.NOISE_OFFSET.get();


    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                layering(WGConfig.LAYERS.NETHER_STONE_LIST.get(), WGConfig.LAYERS.NETHER_HEIGHT.get(), reader, x, z, 127);
            }
        }
        return true;
    }

    private static int getLayerHeights(int x, int z, int index, int height) {
        double noise = Biome.INFO_NOISE.noiseAt((double)x / NOISE_SCALE + index * NOISE_OFFSET, (double)z / NOISE_SCALE + index * NOISE_OFFSET, false);
        return height + (int) Math.round((noise/0.25));
    }


    private static void layering(List<String> blockList, int avgHeight, ISeedReader reader, int x, int z, int endY) {
        int stoneCount = blockList.size();
        for (int i = 0; i < stoneCount; ++i) {
            if (i == 0) {
                replaceStone(reader, x, z, 0, getLayerHeights(x,z,i,avgHeight/stoneCount * (i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            } else if (i < stoneCount-1) {
                replaceStone(reader, x, z, getLayerHeights(x,z,i-1,avgHeight/stoneCount * (i)), getLayerHeights(x,z,i,avgHeight/stoneCount * (i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            } else {
                replaceStone(reader, x, z, getLayerHeights(x,z,i-1,avgHeight/stoneCount * (i)), endY, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            }
        }
    }

    private static void replaceStone(ISeedReader reader, int x, int z, int StartY, int EndY, BlockState Block) {
        for (int y = StartY; y <= EndY; ++y) {
            switch (WGConfig.LAYERS.NETHER_STONE_LAYERS.get()) {
                case 1:
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock().getDefaultState() == Blocks.NETHERRACK.getDefaultState()) {
                        reader.setBlockState(new BlockPos(x, y, z), Block, 2);
                    }
                case 2:
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock().getTags().contains(new ResourceLocation("forge:base_stone_nether"))) {
                        reader.setBlockState(new BlockPos(x, y, z), Block, 2);
                    }
            }
        }
    }

}
