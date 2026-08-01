package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MetronomeBlock extends Block {
    public static final MapCodec<MetronomeBlock> CODEC = simpleCodec(MetronomeBlock::new);

    @Override
    public MapCodec<? extends MetronomeBlock> codec() {
        return CODEC;
    }

    public MetronomeBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }
}
