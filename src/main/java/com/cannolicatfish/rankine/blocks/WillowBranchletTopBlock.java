package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlockHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class WillowBranchletTopBlock extends AbstractTopPlantBlock {
    protected static final VoxelShape SHAPE = Block.box(1.0D, 8.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public WillowBranchletTopBlock(Properties p_i241194_1_) {
        super(p_i241194_1_, Direction.DOWN, SHAPE, false, 0.1D);
    }

    protected int getBlocksToGrowWhenBonemealed(Random p_230332_1_) {
        return PlantBlockHelper.getBlocksToGrowWhenBonemealed(p_230332_1_);
    }

    protected Block getBodyBlock() {
        return RankineBlocks.WILLOW_BRANCHLET_PLANT.get();
    }

    protected boolean canGrowInto(BlockState p_230334_1_) {
        return PlantBlockHelper.isValidGrowthState(p_230334_1_);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
        return reader.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(RankineBlocks.WEEPING_WILLOW_LEAVES.get()) || reader.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(getBodyBlock());
    }
}
