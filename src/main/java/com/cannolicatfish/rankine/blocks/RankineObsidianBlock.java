package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class RankineObsidianBlock extends NonpushableBlock {
    public static final MapCodec<RankineObsidianBlock> CODEC = simpleCodec(RankineObsidianBlock::new);

    @Override
    public MapCodec<? extends RankineObsidianBlock> codec() {
        return CODEC;
    }

    public RankineObsidianBlock(Properties builder) {
        super(builder);
    }

    @Override
    public boolean isPortalFrame(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }
}
