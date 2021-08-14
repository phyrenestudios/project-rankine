package com.cannolicatfish.rankine.blocks.asphalt;

import com.cannolicatfish.rankine.blocks.states.AsphaltStates;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class PotholeBlock extends Block {

    public PotholeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    }
}
