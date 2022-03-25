package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class EnderShiroBlock extends Block {
    public EnderShiroBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (enderShiroGrowth(worldIn, pos)) {
            for(int i = 0; i < 4; ++i) {
                BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                if (worldIn.getBlockState(blockpos).getBlock().equals(Blocks.END_STONE) && enderShiroGrowth(worldIn, blockpos)) {
                    worldIn.setBlockAndUpdate(blockpos, RankineBlocks.ENDER_SHIRO.get().defaultBlockState());
                }
            }
        } else {
            worldIn.setBlockAndUpdate(pos, Blocks.END_STONE.defaultBlockState());
        }

    }

    private boolean enderShiroGrowth(ServerLevel worldIn, BlockPos pos) {
        return !worldIn.getBlockState(pos.above()).is(this) && !worldIn.getBlockState(pos.above()).is(Blocks.END_STONE);
    }

}
