package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

public class RankineStoneBlock extends Block {
    public static final MapCodec<RankineStoneBlock> CODEC = simpleCodec(RankineStoneBlock::new);

    @Override
    public MapCodec<? extends RankineStoneBlock> codec() {
        return CODEC;
    }

    public RankineStoneBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }


}
