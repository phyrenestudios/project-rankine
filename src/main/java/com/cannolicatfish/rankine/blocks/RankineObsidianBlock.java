package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class RankineObsidianBlock extends Block {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RankineObsidianBlock(Properties properties) {
        super(properties);
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public boolean isPortalFrame(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }
}
