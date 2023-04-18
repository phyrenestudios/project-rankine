package com.cannolicatfish.rankine.world.gen.trees;

import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import javax.annotation.Nullable;

public class PetrifiedChorusTreeFeature extends Feature<TreeConfiguration> {

    public PetrifiedChorusTreeFeature(Codec<TreeConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        RandomSource rand = reader.getRandom();
        TreeConfiguration config = p_159749_.config();
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

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

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

    private void branch(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config, int tier) {
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

    private static boolean areAllNeighborsEmpty(LevelReader worldIn, BlockPos pos, @Nullable Direction excludingSide) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (direction != excludingSide && !worldIn.isEmptyBlock(pos.relative(direction))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidGround(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).is(RankineTags.Blocks.BASE_STONE_END);
    }
}
