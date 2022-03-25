package com.cannolicatfish.rankine.world.gen.feature.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeepingWillowTreeFeature extends Feature<TreeConfiguration> {

    public WeepingWillowTreeFeature(Codec<TreeConfiguration> config) {
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
                int branchPoint = (int) Math.round(trunkHeight * 0.5);
                for(int i = 0; i <= trunkHeight; ++i) {
                    WorldgenUtils.checkLog(reader, pos.above(i), rand, config, Direction.Axis.Y);
                }

                int dir = rand.nextInt(8);
                for (int branch = 1; branch < 5; ++branch) {
                    willowBranch(reader,pos.above(branchPoint),rand,config, trunkHeight-branchPoint,dir,0);
                    dir = dir+2;
                }
                willowLeaves(reader, pos.above(trunkHeight+1), rand, config);
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

    private static boolean areAllNeighborsEmpty(LevelReader worldIn, BlockPos pos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (!worldIn.isEmptyBlock(pos.relative(direction))) {
                return false;
            }
        }
        return true;
    }

    private void willowLeaves(WorldGenLevel reader, BlockPos pos, Random rand, TreeConfiguration config) {
        List<BlockPos> leaves = new ArrayList<>();
        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-3,-1,-3),pos.offset(3,2,3))) {
            if (WorldgenUtils.inRadiusCenter(pos,b,2.5)) {
                leaves.add(b.immutable());
            }
        }

        for (BlockPos b : leaves) {
            if (rand.nextFloat() < 0.7 &&  WorldgenUtils.isAir(reader,b.below()) && areAllNeighborsEmpty(reader,b.below())) {
                WorldgenUtils.placeLeafAt(reader, b.below(), rand, config);
                if (rand.nextFloat() < 0.7 &&  WorldgenUtils.isAir(reader,b.below(2)) && areAllNeighborsEmpty(reader,b.below(2))) {
                    WorldgenUtils.placeLeafAt(reader, b.below(2), rand, config);
                    placeBranchlet(b.below(2),reader,rand);
                }
            }
            WorldgenUtils.placeLeafAt(reader, b, rand, config);
        }
    }

    private static void placeBranchlet(BlockPos pos, LevelAccessor world, Random rand) {
        BlockPos.MutableBlockPos blockpos$mutable = pos.mutable().move(Direction.DOWN);
        if (world.isEmptyBlock(blockpos$mutable)) {
            int i = Mth.nextInt(rand, 2, 5);
            if (rand.nextInt(7) == 0) {
                i *= 2;
            }
            WillowBranchletFeature.placeWeepingVinesColumn(world, rand, blockpos$mutable, i, 23, 25);
        }
    }

    private void willowBranch(WorldGenLevel reader, BlockPos pos, Random rand, TreeConfiguration config, int branchHeight, int dir, int tier) {
        int topHeight = rand.nextInt(branchHeight)+1;
        int split = rand.nextInt(2)+1;
        BlockPos b = pos;
        for (int i = 0; i<topHeight; ++i) {
            b = WorldgenUtils.eightBlockDirection(b,dir,1);
            WorldgenUtils.checkLog(reader,b.above(i),rand,config, Direction.Axis.Y);
            WorldgenUtils.checkLog(reader,b.above(i+1),rand,config, Direction.Axis.Y);
            if (i == split && branchHeight-i-1 > 0) {
                willowBranch(reader, b.above(i+1), rand, config, branchHeight-i-1, dir-2, tier+1);
            }

            dir = (rand.nextBoolean() ? 0 : 1) + dir;
        }
        willowLeaves(reader, b.above(topHeight+1), rand, config);

    }

    public static void setDirtAt(LevelAccessor reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.WEEPING_WILLOW_SAPLING.get());
    }
}
