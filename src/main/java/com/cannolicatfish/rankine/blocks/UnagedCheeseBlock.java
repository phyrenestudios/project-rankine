package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UnagedCheeseBlock extends Block {
    public UnagedCheeseBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    }


   @SuppressWarnings( "deprecation" )
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
       super.tick(state, worldIn, pos, rand);
       Level world = worldIn.getLevel();
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
