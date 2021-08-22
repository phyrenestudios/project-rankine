package com.cannolicatfish.rankine.blocks.asphalt;

import com.cannolicatfish.rankine.blocks.states.AsphaltStates;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class WeatheredAsphaltBlock extends BaseAsphaltBlock {

    public WeatheredAsphaltBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.01) {
            worldIn.setBlockState(pos, RankineBlocks.CRACKED_ASPHALT.get().getDefaultState().with(LINE_TYPE, state.get(LINE_TYPE)).with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING)));
        }
    }
}
