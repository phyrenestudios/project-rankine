package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.WallBlock;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineWallBlock extends WallBlock {
    public static final MapCodec<RankineWallBlock> CODEC = simpleCodec(RankineWallBlock::new);

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<WallBlock> codec() {
        return (MapCodec<WallBlock>) (MapCodec<?>) CODEC;
    }

    public RankineWallBlock(Properties properties) {
        super(properties);
    }
}
