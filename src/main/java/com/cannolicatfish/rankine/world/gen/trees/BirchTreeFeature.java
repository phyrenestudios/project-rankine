package com.cannolicatfish.rankine.world.gen.trees;

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
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;

public class BirchTreeFeature extends Feature<TreeConfiguration> {

    public BirchTreeFeature(Codec<TreeConfiguration> config) {
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
                int branchCount = 3;
                int dir = rand.nextInt(8);
                for(int i = 0; i <= trunkHeight; ++i) {
                    if (isAirOrLeaves(reader, pos.above(i))) {
                        this.placeLogAt(reader, pos.above(i), rand, config, Direction.Axis.Y);
                    }
                    if (branchCount > 0 && i > trunkHeight*0.4f && i < trunkHeight*0.8f) {
                        birchBranch(reader,pos.above(i),rand,config, trunkHeight-i,dir);
                        dir = (dir + rand.nextInt(2)+2) % 8;
                        branchCount--;
                    }

                }
                birchLeaves(reader, pos.above(trunkHeight), rand, config);

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

    private void birchLeaves(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config) {
        List<BlockPos> leaves = new ArrayList<>();

        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-1,-1,-1),pos.offset(1,1,1))) {
            leaves.add(b.immutable());
        }
        leaves.add(pos.below(2));
        leaves.add(pos.below(2).north());
        leaves.add(pos.below(2).east());
        leaves.add(pos.below(2).west());
        leaves.add(pos.below(2).south());
        leaves.add(pos.above(3));
        leaves.add(pos.above(2));
        leaves.add(pos.above(2).south());
        leaves.add(pos.above(2).east());
        leaves.add(pos.above(2).west());
        leaves.add(pos.above(2).north());

        for (BlockPos b : leaves) {
            if (isAirOrLeaves(reader, b)) {
                this.placeLeafAt(reader, b, rand, config);
            }
        }
    }

    private void birchBranch(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config, int branchHeight, int dir) {

        if (isAirOrLeaves(reader, WorldgenUtils.eightBlockDirection(pos,dir,1))) {
            placeLogAt(reader,WorldgenUtils.eightBlockDirection(pos,dir,1),rand,config, Direction.Axis.Y);
        }
        if (isAirOrLeaves(reader, WorldgenUtils.eightBlockDirection(pos,dir,1).above())) {
            placeLogAt(reader,WorldgenUtils.eightBlockDirection(pos,dir,1).above(),rand,config, Direction.Axis.Y);
        }
        int topHeight = rand.nextInt(branchHeight)+3;
        for (int i = 1; i<topHeight; ++i) {
            if (isAirOrLeaves(reader, WorldgenUtils.eightBlockDirection(pos,dir,2).above(i))) {
                placeLogAt(reader,WorldgenUtils.eightBlockDirection(pos,dir,2).above(i),rand,config, Direction.Axis.Y);
            }
        }
        birchLeaves(reader, WorldgenUtils.eightBlockDirection(pos,dir,2).above(topHeight), rand, config);

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
        /*if (reader instanceof net.minecraft.world.level.LevelReader) {
            return reader.isStateAtPosition(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.level.LevelReader) reader, pos));
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
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.BLACK_BIRCH.getSapling());
    }
}
