package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class NonpushableBlock extends Block {

    public NonpushableBlock(Properties builder) {
        super(builder);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_60584_) {
        return PushReaction.BLOCK;
    }
}
