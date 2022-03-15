package com.cannolicatfish.rankine.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

public class FlatNetherBedrockFeature extends Feature<ReplacerFeatureConfig> {
    public FlatNetherBedrockFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {
        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getMinBlockX();
        int startZ = chunk.getPos().getMinBlockZ();
        int endX = chunk.getPos().getMaxBlockX();
        int endZ = chunk.getPos().getMaxBlockZ();
        int startY = config.bottomBound;
        int endY = config.topBound;

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                for (int y = startY; y < endY; ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        reader.setBlock(new BlockPos(x, y, z), config.state, 2);
                    }
                }
                for (int y = endY; y <= 10; ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.state.getBlock()) {
                        reader.setBlock(new BlockPos(x, y, z), config.target, 2);
                    }
                }

                for (int y = 128 - endY; y < 128; ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        reader.setBlock(new BlockPos(x, y, z), config.state, 2);
                    }
                }
                for (int y = 118; y < 128 - endY; ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.state.getBlock()) {
                        reader.setBlock(new BlockPos(x, y, z), config.target, 2);
                    }
                }
            }
        }

        return true;
    }

}
