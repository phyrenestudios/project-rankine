package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class UnagedCheeseBlock extends Block {
    public UnagedCheeseBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    }


   @SuppressWarnings( "deprecation" )
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
       super.tick(state, worldIn, pos, rand);
       World world = worldIn.getWorld();
        if (rand.nextFloat() < 0.05 && !worldIn.isRemote) {
            worldIn.setBlockState(pos, RankineBlocks.AGED_CHEESE.get().getDefaultState(),2);
            worldIn.neighborChanged(pos, RankineBlocks.AGED_CHEESE.get(), pos);
        }
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }
}
