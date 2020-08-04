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
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random rand, BlockPos pos, ReplacerFeatureConfig config) {
        IChunk chunk = worldIn.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();
        int startY = config.bottomBound;
        int endY = config.topBound;

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                for (int y = startY; y < endY; ++y) {
                    if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        worldIn.setBlockState(new BlockPos(x, y, z), config.state, 2);
                    }
                }
                for (int y = endY; y <= 10; ++y) {
                    if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.state.getBlock()) {
                        worldIn.setBlockState(new BlockPos(x, y, z), config.target, 2);
                    }
                }

                for (int y = 128 - endY; y < 128; ++y) {
                    if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        worldIn.setBlockState(new BlockPos(x, y, z), config.state, 2);
                    }
                }
                for (int y = 118; y < 128 - endY; ++y) {
                    if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.state.getBlock()) {
                        worldIn.setBlockState(new BlockPos(x, y, z), config.target, 2);
                    }
                }
            }
        }

        return true;
    }
}
