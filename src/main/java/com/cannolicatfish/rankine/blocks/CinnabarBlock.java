package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class CinnabarBlock extends Block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public CinnabarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.FALSE));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(LIT, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isClientSide) {
            boolean flag = state.getValue(LIT);
            if (flag != worldIn.hasNeighborSignal(pos)) {
                if (flag) {
                    worldIn.getBlockTicks().willTickThisTick(pos, this);
                } else {
                    worldIn.setBlock(pos, state.cycle(LIT), 2);
                }
            }

        }
    }

    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        if (state.getValue(LIT) && !worldIn.hasNeighborSignal(pos)) {
            worldIn.setBlock(pos, state.cycle(LIT), 2);
        }

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}