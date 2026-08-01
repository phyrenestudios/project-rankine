package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineWoodenVerticalSlabBlock extends RankineVerticalSlabBlock {
    public static final MapCodec<RankineWoodenVerticalSlabBlock> CODEC = simpleCodec(RankineWoodenVerticalSlabBlock::new);

    @Override
    public MapCodec<? extends RankineWoodenVerticalSlabBlock> codec() {
        return CODEC;
    }

    public RankineWoodenVerticalSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 20;
    }
}
