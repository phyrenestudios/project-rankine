package com.cannolicatfish.rankine.world.gen.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PinyonPineTreeFeature extends Feature<TreeConfiguration> {

    public PinyonPineTreeFeature(Codec<TreeConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();
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
                int branchPoint = rand.nextInt(3)+2;
                for(int i = 0; i <= branchPoint; ++i) {
                    WorldgenUtils.checkLog(reader, pos.above(i), rand, config, Direction.Axis.Y);
                }

                int dir = rand.nextInt(8);
                for (int branch = 1; branch <= 3; ++branch) {
                    juniperBranch(reader,pos.above(branchPoint),rand,config, trunkHeight-branchPoint-1,dir);
                    dir = rand.nextInt(8);
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

    private void pinyonPineLeaves(WorldGenLevel reader, BlockPos pos, Random rand, TreeConfiguration config) {
        List<BlockPos> leaves = new ArrayList<>();
        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-2,0,-2),pos.offset(2,0,2))) {
            if (WorldgenUtils.inRadiusCenter(pos.above(b.getY()-pos.getY()),b,2.1D)) leaves.add(b.immutable());
        }
        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-2,1,-2),pos.offset(2,1,2))) {
            if (WorldgenUtils.inRadiusCenter(pos.above(b.getY()-pos.getY()),b,2.5D)) leaves.add(b.immutable());
        }
        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-2,2,-2),pos.offset(2,2,2))) {
            if (WorldgenUtils.inRadiusCenter(pos.above(b.getY()-pos.getY()),b,2.1D)) leaves.add(b.immutable());
        }
        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-1,3,-1),pos.offset(1,3,1))) {
            leaves.add(b.immutable());
        }
        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-1,4,-1),pos.offset(1,4,1))) {
            if (WorldgenUtils.inRadiusCenter(pos.above(b.getY()-pos.getY()),b,1.1D)) leaves.add(b.immutable());
        }
        leaves.add(pos.above(5));
        for (BlockPos b : leaves) {
            WorldgenUtils.placeLeafAt(reader, b, rand, config);
        }
    }

    private void juniperBranch(WorldGenLevel reader, BlockPos pos, Random rand, TreeConfiguration config, int branchHeight, int dir) {
        int topHeight = rand.nextInt(branchHeight)+1;
        BlockPos b = pos;
        for (int i = 0; i<topHeight; ++i) {
            b = WorldgenUtils.eightBlockDirection(b,dir,1);
            WorldgenUtils.checkLog(reader,b.above(i),rand,config, Direction.Axis.Y);
            WorldgenUtils.checkLog(reader,b.above(i+1),rand,config, Direction.Axis.Y);
            dir = ((rand.nextBoolean() ? 0 : 1) + dir) % 8;
        }
        pinyonPineLeaves(reader, b.above(topHeight), rand, config);
    }

    public static void setDirtAt(LevelAccessor reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, RankineBlocks.PINYON_PINE.getSapling());
    }
}
