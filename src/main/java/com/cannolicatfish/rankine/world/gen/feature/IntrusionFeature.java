package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class IntrusionFeature extends Feature<ReplacerFeatureConfig> {
    public IntrusionFeature(Function<Dynamic<?>, ? extends ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {
        IChunk chunk = worldIn.getChunk(pos);
        int startX = chunk.getPos().getXStart() + rand.nextInt(6);
        int startZ = chunk.getPos().getZStart() + rand.nextInt(6);
        int endX = chunk.getPos().getXEnd() - rand.nextInt(6);
        int endZ = chunk.getPos().getZEnd() - rand.nextInt(6);
        int startY;
        int endY;
        if (worldIn.getDimension().getType() == DimensionType.OVERWORLD)
        {
            startY = config.bottomBound;
            endY = config.topBound - rand.nextInt(10);
        } else {
            startY = config.bottomBound + rand.nextInt(60);
            endY = config.topBound;
        }


        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                for (int y = startY; y <= endY; ++y) {
                    if (y == startY && y - 1> 0 && worldIn.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1/2f)) {
                        worldIn.setBlockState(new BlockPos(x, y - 1, z), config.state, 2);
                        if (y - 2> 0 && worldIn.getBlockState(new BlockPos(x, y - 2, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1/2f)) {
                            worldIn.setBlockState(new BlockPos(x, y - 2, z), config.state, 2);
                        }
                    }
                    if (y == endY && y + 1 < 128 && worldIn.getBlockState(new BlockPos(x, y + 1, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1/2f)) {
                        worldIn.setBlockState(new BlockPos(x, y + 1, z), config.state, 2);
                        if (y + 2 < 128 && worldIn.getBlockState(new BlockPos(x, y + 2, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1/2f)) {
                            worldIn.setBlockState(new BlockPos(x, y + 2, z), config.state, 2);
                        }
                    }
                    if (config.target.getBlock() == ModBlocks.KIMBERLITE) {
                        if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                            if (rand.nextInt(6) != 1) {
                                worldIn.setBlockState(new BlockPos(x, y, z), config.state, 2);
                            }
                        }
                    } else {
                        if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                            if (rand.nextInt(4) != 1) {
                                worldIn.setBlockState(new BlockPos(x, y, z), config.state, 2);
                            }
                        }
                    }




                }
            }
        }

        return true;
    }
}
