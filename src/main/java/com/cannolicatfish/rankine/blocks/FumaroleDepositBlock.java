package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class FumaroleDepositBlock extends Block {
    public FumaroleDepositBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextFloat()<0.1 && worldIn.getBlockState(pos.below()).is(Blocks.AIR) && worldIn.getBlockState(pos.below()).is(Blocks.WATER)) {
            worldIn.setBlock(pos.below(), RankineBlocks.GWIHABAITE_CRYSTAL.get().defaultBlockState().setValue(GwihabaiteBlock.FACING, Direction.DOWN),3);
        }

        super.randomTick(state, worldIn, pos, random);
    }
}
