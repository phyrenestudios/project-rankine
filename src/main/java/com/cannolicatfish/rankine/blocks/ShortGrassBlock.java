package com.cannolicatfish.rankine.blocks;


import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ShortGrassBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D);

    public ShortGrassBlock(Block.Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND;
    }

    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XYZ;
    }
}
