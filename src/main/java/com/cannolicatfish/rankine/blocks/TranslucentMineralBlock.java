package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.HalfTransparentBlock;

public class TranslucentMineralBlock extends HalfTransparentBlock {
    public static final MapCodec<TranslucentMineralBlock> CODEC = simpleCodec(TranslucentMineralBlock::new);

    @Override
    public MapCodec<? extends TranslucentMineralBlock> codec() {
        return CODEC;
    }

    public TranslucentMineralBlock(Properties p_53640_) {
        super(p_53640_);
    }

}
