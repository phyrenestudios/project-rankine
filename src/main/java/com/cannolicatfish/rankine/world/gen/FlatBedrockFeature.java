package com.cannolicatfish.rankine.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Random;

public class FlatBedrockFeature extends Feature<ReplacerFeatureConfig> {
    public FlatBedrockFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    @Override
    public boolean place(FeaturePlaceContext<ReplacerFeatureConfig> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();
        ReplacerFeatureConfig config = p_159749_.config();
        ChunkAccess chunk = reader.getChunk(pos);
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
            }
        }

        return true;
    }
}
