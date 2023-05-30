package com.cannolicatfish.rankine.worldgen.features.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MagnoliaTreeFeature extends Feature<TreeConfiguration> {

    public MagnoliaTreeFeature(Codec<TreeConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        RandomSource rand = reader.getRandom();
        TreeConfiguration config = p_159749_.config();
        int trunkHeight = config.trunkPlacer.getTreeHeight(rand);
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
                            if (!WorldgenUtils.isAirOrLeaves(reader, blockpos$mutableblockpos.set(l, j, i1))) {
                                flag = false;
                            }
                        }
                        else {
                            flag = false;
                        }
                    }
                }
            }

            //build tree
            if (!flag) {
                return false;
            } else if (isValidGround(reader, pos.below()) && pos.getY() < reader.getMaxBuildHeight() - trunkHeight - 1) {
                setDirtAt(reader, pos.below());
                int branchPoint = (int) Math.round(trunkHeight * 0.5) - 1;
                for(int i = 0; i <= branchPoint; ++i) {
                    WorldgenUtils.checkLog(reader, pos.above(i), rand, config, Direction.Axis.Y);
                }

                int dir = rand.nextInt(8);
                for (int branch = 1; branch < 4; ++branch) {
                    magnoliaBranch(reader,pos.above(branchPoint),rand,config, trunkHeight-branchPoint,dir);
                    dir = (dir + rand.nextInt(2)+2) % 8;
                }
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

    private void magnoliaLeaves(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config) {
        List<BlockPos> leaves = new ArrayList<>();

        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-1,-1,-1),pos.offset(1,0,1))) {
            leaves.add(b.immutable());
        }
        leaves.add(pos.offset(-2,-1,-1));
        leaves.add(pos.offset(-2,-1,0));
        leaves.add(pos.offset(-2,-1,1));
        leaves.add(pos.offset(2,-1,-1));
        leaves.add(pos.offset(2,-1,0));
        leaves.add(pos.offset(2,-1,1));
        leaves.add(pos.offset(-1,-1,-2));
        leaves.add(pos.offset(0,-1,-2));
        leaves.add(pos.offset(1,-1,-2));
        leaves.add(pos.offset(-1,-1,2));
        leaves.add(pos.offset(0,-1,2));
        leaves.add(pos.offset(1,-1,2));

        for (BlockPos b : leaves) {
            if (rand.nextFloat() < 0.7 &&  WorldgenUtils.isAir(reader,b.below()) && areAllNeighborsEmpty(reader,b.below())) {
                WorldgenUtils.placeLeafAt(reader, b.below(), rand, config);
            }
             WorldgenUtils.placeLeafAt(reader, b, rand, config);
        }
    }

    private static boolean areAllNeighborsEmpty(LevelReader worldIn, BlockPos pos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (!worldIn.isEmptyBlock(pos.relative(direction))) {
                return false;
            }
        }

        return true;
    }

    private void magnoliaBranch(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config, int branchHeight, int dir) {
        int topHeight = rand.nextInt(branchHeight)+1;
        BlockPos b = pos;
        for (int i = 0; i<topHeight; ++i) {
            b = WorldgenUtils.eightBlockDirection(b,dir,1);
            WorldgenUtils.checkLog(reader,b.above(i),rand,config, Direction.Axis.Y);
            WorldgenUtils.checkLog(reader,b.above(i+1),rand,config, Direction.Axis.Y);
            dir = ((rand.nextBoolean() ? 0 : 1) + dir) % 8;
        }
        magnoliaLeaves(reader, b.above(topHeight+2), rand, config);

    }

    public static void setDirtAt(LevelAccessor reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, RankineBlocks.MAGNOLIA.getSapling());
    }
}
