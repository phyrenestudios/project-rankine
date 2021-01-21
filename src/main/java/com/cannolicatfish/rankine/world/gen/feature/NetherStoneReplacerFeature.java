package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NetherStoneReplacerFeature extends Feature<StoneReplacerFeatureConfig> {


    public NetherStoneReplacerFeature(Codec<StoneReplacerFeatureConfig> configFactoryIn) {
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
                double noise = Biome.INFO_NOISE.noiseAt((double) x / 60, (double) z / 60, false);
                double noise1 = Biome.INFO_NOISE.noiseAt((double) x / 60, (double) z / 60, false);
                double noise2 = Biome.INFO_NOISE.noiseAt((double) x / 60, (double) z / 60, false);

                for (int y = 0; y <= 16 + (int) Math.round((noise / 0.1D)); ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        reader.setBlockState(new BlockPos(x, y, z), Blocks.BLACKSTONE.getDefaultState(), 2);
                    }
                }
                for (int y = 16 + (int) Math.round((noise / 0.1D)); y <= 18 + (int) Math.round((noise / 0.1D));  ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        reader.setBlockState(new BlockPos(x, y, z), ModBlocks.BLACK_SAND.getDefaultState(), 2);
                    }
                }
                for (int y = 18 + (int) Math.round((noise / 0.1D)) -2; y <= 30 + (int) Math.round((noise1 / 0.1D));  ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        reader.setBlockState(new BlockPos(x, y, z), ModBlocks.PURPLE_PORPHYRY.getDefaultState(), 2);
                    }
                }
                for (int y = 100 + (int) Math.round((noise1 / 0.1D)) -2; y <= 115 + (int) Math.round((noise2 / 0.1D));  ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        reader.setBlockState(new BlockPos(x, y, z), ModBlocks.PORPHYRY.getDefaultState(), 2);
                    }
                }
                for (int y = 115 + (int) Math.round((noise2 / 0.1D)); y <= 127; ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        reader.setBlockState(new BlockPos(x, y, z), ModBlocks.PEROVSKITE.getDefaultState(), 2);
                    }
                }
            }
        }
        return true;
    }

}
