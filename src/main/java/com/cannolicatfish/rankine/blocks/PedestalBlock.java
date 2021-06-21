package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class PedestalBlock extends Block {

    public PedestalBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.or(
                Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
                Block.makeCuboidShape(3.0D, 2.0D, 3.0D, 13.0D, 5.0D, 13.0D),
                Block.makeCuboidShape(4.0D, 5.0D, 4.0D, 12.0D, 11.0D, 12.0D),
                Block.makeCuboidShape(3.0D, 11.0D, 3.0D, 13.0D, 14.0D, 13.0D),
                Block.makeCuboidShape(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D)
        );
    }
}
