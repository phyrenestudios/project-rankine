package com.cannolicatfish.rankine.worldgen.features.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ErythrinaTreeFeature extends Feature<TreeConfiguration> {

    public ErythrinaTreeFeature(Codec<TreeConfiguration> config) {
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
                            if (!isAirOrLeaves(reader, blockpos$mutableblockpos.set(l, j, i1))) {
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
                int branchPoint = (int) Math.round(trunkHeight * 0.3);
                for(int i = 0; i <= branchPoint; ++i) {
                    if (isAirOrLeaves(reader, pos.above(i))) {
                        this.placeLogAt(reader, pos.above(i), rand, config, Direction.Axis.Y);
                    }
                }

                int dir = rand.nextInt(8);
                for (int branch = 1; branch < 4; ++branch) {
                    erythrinaBranch(reader,pos.above(branchPoint),rand,config, trunkHeight-branchPoint,dir,0);
                    dir = dir + rand.nextInt(2)+2;
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
        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-1,0,-1),pos.offset(1,0,1))) {
            leaves.add(b.immutable());
        }
        leaves.add(pos.below(1));
        leaves.add(pos.below(1).north());
        leaves.add(pos.below(1).east());
        leaves.add(pos.below(1).west());
        leaves.add(pos.below(1).south());
        leaves.add(pos.above(2));
        leaves.add(pos.above(1));
        leaves.add(pos.above(1).south());
        leaves.add(pos.above(1).east());
        leaves.add(pos.above(1).west());
        leaves.add(pos.above(1).north());

        for (BlockPos b : leaves) {
            if (isAirOrLeaves(reader, b)) {
                this.placeLeafAt(reader, b, rand, config);
            }
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

    private void erythrinaBranch(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config, int branchHeight, int dir, int tier) {
        int topHeight = rand.nextInt(branchHeight)+1;
        int split = rand.nextInt(2)+1;
        BlockPos b = pos;
        for (int i = 0; i<topHeight; ++i) {
            b = WorldgenUtils.eightBlockDirection(b,dir,1);
            if (isAirOrLeaves(reader, b.above(i))) {
                placeLogAt(reader,b.above(i),rand,config, Direction.Axis.Y);
            }
            if (isAirOrLeaves(reader, b.above(i+1))) {
                placeLogAt(reader,b.above(i+1),rand,config, Direction.Axis.Y);
            }
            if (i == split && branchHeight-i-1 > 0) {
                erythrinaBranch(reader, b.above(i+1), rand, config, branchHeight-i-1, dir-2, tier+1);
            }

            dir = (rand.nextBoolean() ? 0 : 1) + dir;
        }
        magnoliaLeaves(reader, b.above(topHeight+1), rand, config);

    }


    private void placeLogAt(LevelWriter reader, BlockPos pos, RandomSource rand, TreeConfiguration config, Direction.Axis axis) {
        this.setLogState(reader, pos, config.trunkProvider.getState(rand, pos).setValue(BlockStateProperties.AXIS, axis));
    }

    private void placeLeafAt(LevelSimulatedRW world, BlockPos pos, RandomSource rand, TreeConfiguration config) {
        if (isAirOrLeaves(world, pos)) {
            this.setLogState(world, pos, config.foliageProvider.getState(rand, pos).setValue(LeavesBlock.DISTANCE, 1));
        }
    }

    protected final void setLogState(LevelWriter reader, BlockPos pos, BlockState state) {
        reader.setBlock(pos, state, 18);
    }

    public static boolean isAir(LevelSimulatedReader reader, BlockPos pos) {
        if (!(reader instanceof net.minecraft.world.level.BlockGetter)) {
            return reader.isStateAtPosition(pos, BlockState::isAir);
        }
        else {
            return reader.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir);
        }
    }

    public static boolean isAirOrLeaves(LevelSimulatedReader reader, BlockPos pos) {
        /*if (reader instanceof LevelReader) {
            return reader.isStateAtPosition(pos, state -> state.canBeReplacedByLeaves((LevelReader) reader, pos));
        }*/
        return reader.isStateAtPosition(pos, (state) -> {
            return state.isAir() || state.is(BlockTags.LEAVES);
        });
    }

    public static void setDirtAt(LevelAccessor reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, RankineBlocks.ERYTHRINA.getSapling());
    }
}
