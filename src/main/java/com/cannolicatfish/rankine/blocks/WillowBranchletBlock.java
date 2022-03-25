package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WillowBranchletBlock extends GrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public WillowBranchletBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
    }
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) RankineBlocks.WILLOW_BRANCHLET.get();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        return reader.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(RankineBlocks.WEEPING_WILLOW_LEAVES.get()) || reader.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(this) || reader.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(getHeadBlock());
    }
}
