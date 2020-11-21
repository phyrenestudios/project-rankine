package com.cannolicatfish.rankine.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;
import java.util.function.Function;

public class ReplacerFeature extends Feature<ReplacerFeatureConfig> {
    public ReplacerFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {
        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();
        int endY = config.topBound;
        int startY = config.bottomBound;

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                for (int y = startY; y <= endY; ++y) {
                    if (y == startY && y - 1> 0 && reader.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1/2f)) {
                        reader.setBlockState(new BlockPos(x, y - 1, z), config.state, 2);
                        if (y - 2> 0 && reader.getBlockState(new BlockPos(x, y - 2, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1/2f)) {
                            reader.setBlockState(new BlockPos(x, y - 2, z), config.state, 2);
                        }
                    }
                    if (y == endY && y + 1 < 256 && reader.getBlockState(new BlockPos(x, y + 1, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1/2f)) {
                        reader.setBlockState(new BlockPos(x, y + 1, z), config.state, 2);
                        if (y + 2 < 128 && reader.getBlockState(new BlockPos(x, y + 2, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1/2f)) {
                            reader.setBlockState(new BlockPos(x, y + 2, z), config.state, 2);
                        }
                    }
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        reader.setBlockState(new BlockPos(x, y, z), config.state, 2);
                    }
                }
            }
        }

        return true;
    }
}
