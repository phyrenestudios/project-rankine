package com.cannolicatfish.rankine.blocks.asphalt;

import com.cannolicatfish.rankine.blocks.states.AsphaltStates;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class CrackedAsphaltBlock extends BaseAsphaltBlock {

    public CrackedAsphaltBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.05 && worldIn.isRainingAt(pos.up())) {
            worldIn.setBlockState(pos, RankineBlocks.POTHOLE.get().getDefaultState());
        }
    }
}
