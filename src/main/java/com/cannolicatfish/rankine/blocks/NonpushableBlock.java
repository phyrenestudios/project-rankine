package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class NonpushableBlock extends Block {
    public static final MapCodec<NonpushableBlock> CODEC = simpleCodec(NonpushableBlock::new);

    @Override
    public MapCodec<? extends NonpushableBlock> codec() {
        return CODEC;
    }

    public NonpushableBlock(Properties builder) {
        super(builder);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_60584_) {
        return PushReaction.BLOCK;
    }
}
