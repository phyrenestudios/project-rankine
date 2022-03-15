package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class LEDBlock extends Block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public LEDBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.FALSE));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(LIT, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isClientSide) {
            boolean flag = state.getValue(LIT);
            if (flag != worldIn.hasNeighborSignal(pos)) {
                if (flag) {
                    worldIn.getBlockTicks().scheduleTick(pos, this, 4);
                } else {
                    worldIn.setBlock(pos, state.cycle(LIT), 2);
                }
            }

        }
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.getValue(LIT) && !worldIn.hasNeighborSignal(pos)) {
            worldIn.setBlock(pos, state.cycle(LIT), 2);
        }
    }


    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}