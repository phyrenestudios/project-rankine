package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
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

import net.minecraft.block.AbstractBlock.Properties;

public class UnagedCheeseBlock extends Block {
    public UnagedCheeseBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    }


   @SuppressWarnings( "deprecation" )
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
       super.tick(state, worldIn, pos, rand);
       World world = worldIn.getLevel();
        if (rand.nextFloat() < Config.GENERAL.CHEESE_AGE_CHANCE.get() && !worldIn.isClientSide) {
            worldIn.setBlock(pos, RankineBlocks.AGED_CHEESE.get().defaultBlockState(),2);
            worldIn.neighborChanged(pos, RankineBlocks.AGED_CHEESE.get(), pos);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}
