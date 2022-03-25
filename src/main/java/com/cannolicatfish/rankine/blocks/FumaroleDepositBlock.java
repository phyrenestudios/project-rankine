package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FumaroleDepositBlock extends Block {
    public FumaroleDepositBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (random.nextFloat()<0.1 && worldIn.getBlockState(pos.below()).is(Blocks.AIR) && worldIn.getBlockState(pos.below()).is(Blocks.WATER)) {
            worldIn.setBlock(pos.below(), RankineBlocks.GWIHABAITE_CRYSTAL.get().defaultBlockState().setValue(GwihabaiteBlock.FACING, Direction.DOWN),3);
        }

        super.randomTick(state, worldIn, pos, random);
    }
}
