package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;

public class WillowBranchletBlock extends AbstractBodyPlantBlock {
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public WillowBranchletBlock(AbstractBlock.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
    }
    protected AbstractTopPlantBlock getHeadBlock() {
        return (AbstractTopPlantBlock) RankineBlocks.WILLOW_BRANCHLET.get();
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
        return reader.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(RankineBlocks.WEEPING_WILLOW_LEAVES.get()) || reader.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(this) || reader.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(getHeadBlock());
    }
}
