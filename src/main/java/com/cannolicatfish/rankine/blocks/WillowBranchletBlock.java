package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;

public class WillowBranchletBlock extends AbstractBodyPlantBlock {
    public static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public WillowBranchletBlock(AbstractBlock.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
    }
    protected AbstractTopPlantBlock getTopPlantBlock() {
        return (AbstractTopPlantBlock) RankineBlocks.WILLOW_BRANCHLET.get();
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader reader, BlockPos pos) {
        return reader.getBlockState(pos.offset(this.growthDirection.getOpposite())).matchesBlock(RankineBlocks.WEEPING_WILLOW_LEAVES.get()) || reader.getBlockState(pos.offset(this.growthDirection.getOpposite())).matchesBlock(this) || reader.getBlockState(pos.offset(this.growthDirection.getOpposite())).matchesBlock(getTopPlantBlock());
    }
}
