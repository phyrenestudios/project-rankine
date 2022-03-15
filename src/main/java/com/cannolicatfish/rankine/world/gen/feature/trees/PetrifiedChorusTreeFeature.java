package com.cannolicatfish.rankine.world.gen.feature.trees;

import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class PetrifiedChorusTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public PetrifiedChorusTreeFeature(Codec<BaseTreeFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BaseTreeFeatureConfig config) {
        int trunkHeight = rand.nextInt(11) + 10;


        boolean flag = true;
        if (pos.getY() >= 1 && pos.getY() + trunkHeight + 1 <= reader.getMaxBuildHeight()) {
            for(int j = pos.getY(); j <= pos.getY() + 1 + trunkHeight; ++j) {
                int k = 1;
                if (j == pos.getY()) {
                    k = 0;
                }

                if (j >= pos.getY() + 1 + trunkHeight - 2) {
                    k = 2;
                }

                BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

                for(int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l) {
                    for(int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1) {
                        if (j >= 0 && j < reader.getMaxBuildHeight()) {
                            if (!WorldgenUtils.isAir(reader, blockpos$mutableblockpos.set(l, j, i1))) {
                                flag = false;
                            }
                        }
                        else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else if (isValidGround(reader, pos.below()) && pos.getY() < reader.getMaxBuildHeight() - trunkHeight - 1) {
                WorldgenUtils.checkLog(reader, pos, rand, config, Direction.Axis.Y);
                branch(reader,pos,rand,config,0);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    private void branch(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, int tier) {
        tier += 1;
        int height = rand.nextInt(3)+2;
        for (int i = 1; i <= height; ++i) {
            WorldgenUtils.checkLog(reader, pos.above(i), rand, config, Direction.Axis.Y);
        }
        if (tier > 5) return;
        for (Direction dir : Direction.values()) {
            if (dir.getAxis().equals(Direction.Axis.Y)) continue;
            if (rand.nextFloat() < 0.3) {
                int length = rand.nextInt(2)+1;
                for (int i = 1; i <= length; ++i) {
                    if (areAllNeighborsEmpty(reader,pos.above(height).relative(dir,i),dir.getOpposite())) {
                        WorldgenUtils.checkLog(reader, pos.above(height).relative(dir,i), rand, config, dir.getAxis());
                    } else {
                        break;
                    }
                }
                branch(reader, pos.above(height).relative(dir,length), rand, config, tier);
            }
        }
    }

    private static boolean areAllNeighborsEmpty(IWorldReader worldIn, BlockPos pos, @Nullable Direction excludingSide) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (direction != excludingSide && !worldIn.isEmptyBlock(pos.relative(direction))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidGround(IWorld world, BlockPos pos) {
        return world.getBlockState(pos).is(RankineTags.Blocks.BASE_STONE_END);
    }
}
