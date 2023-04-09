package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class WillowBranchletTopBlock extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(1.0D, 8.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public WillowBranchletTopBlock(Properties p_i241194_1_) {
        super(p_i241194_1_, Direction.DOWN, SHAPE, false, 0.1D);
    }

    protected int getBlocksToGrowWhenBonemealed(Random p_230332_1_) {
        return NetherVines.getBlocksToGrowWhenBonemealed(p_230332_1_);
    }

    protected Block getBodyBlock() {
        return RankineBlocks.WILLOW_BRANCHLET_PLANT.get();
    }

    protected boolean canGrowInto(BlockState p_230334_1_) {
        return NetherVines.isValidGrowthState(p_230334_1_);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        return reader.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(RankineBlocks.WEEPING_WILLOW.getLeaves()) || reader.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(getBodyBlock());
    }
}
