package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class EndStoneReplacerFeature extends Feature<StoneReplacerFeatureConfig> {
    public EndStoneReplacerFeature(Codec<StoneReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, StoneReplacerFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                double noise = Biome.INFO_NOISE.noiseAt((double) x / 50, (double) z / 50, false);
                double noise1 = Biome.INFO_NOISE.noiseAt((double) x / 50 + 64, (double) z / 50 + 64, false);
                for (int y = 0; y <= 30 + (int) Math.round((noise / 0.1D)); ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        reader.setBlockState(new BlockPos(x, y, z), ModBlocks.METEORITE.getDefaultState(), 2);
                    }
                }
                for (int y = 30 + (int) Math.round((noise / 0.1D)); y <= 30 + (int) Math.round((noise1 / 0.1D)); ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        reader.setBlockState(new BlockPos(x, y, z), ModBlocks.ENSTATITE.getDefaultState(), 2);
                    }
                }
            }
        }
        return true;
    }
}
