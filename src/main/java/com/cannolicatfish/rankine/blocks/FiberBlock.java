package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FiberBlock extends HorizontalDirectionalBlock {
    // HorizontalDirectionalBlock#codec() is abstract in 1.21.1, so the MapCodec below is mandatory.
    public static final com.mojang.serialization.MapCodec<FiberBlock> CODEC = simpleCodec(FiberBlock::new);

    @Override
    public com.mojang.serialization.MapCodec<? extends FiberBlock> codec() {
        return CODEC;
    }

    public FiberBlock(Properties properties) {
        super(properties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
