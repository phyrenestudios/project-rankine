package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class FumaroleDepositBlock extends Block {
    public FumaroleDepositBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextFloat()<0.1 && worldIn.getBlockState(pos.up()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.up(), RankineBlocks.GWIHABAITE_CRYSTAL.get().getDefaultState(),2);
        }

        super.randomTick(state, worldIn, pos, random);
    }
}
