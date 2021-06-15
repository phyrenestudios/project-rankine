package com.cannolicatfish.rankine.blocks;


import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class SodiumVaporLampBlock extends Block implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;

    public SodiumVaporLampBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(HANGING, false).with(WATERLOGGED, Boolean.FALSE));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        BlockState blockstate = this.getDefaultState();

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction == Direction.UP) {
                blockstate = this.getDefaultState().with(HANGING,true);
            }
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                if (blockstate.isValidPosition(context.getWorld(), context.getPos())) {
                    return blockstate.with(HORIZONTAL_FACING, direction1).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(HANGING)) {
            switch (state.get(HORIZONTAL_FACING)) {
                case EAST:
                case WEST:
                    return Block.makeCuboidShape(4.0D, 10.0D, 2.0D, 12.0D, 16.0D, 14.0D);
                case SOUTH:
                case NORTH:
                default:
                    return Block.makeCuboidShape(2.0D, 10.0D, 4.0D, 14.0D, 16.0D, 12.0D);
            }
        } else {
            switch (state.get(HORIZONTAL_FACING)) {
                case WEST:
                    return Block.makeCuboidShape(7.0D, 7.0D, 2.0D, 16.0D, 16.0D, 14.0D);
                case EAST:
                    return Block.makeCuboidShape(0.0D, 7.0D, 2.0D, 9.0D, 16.0D, 14.0D);
                case SOUTH:
                    return Block.makeCuboidShape(2.0D, 7.0D, 0.0D, 14.0D, 16.0D, 9.0D);
                case NORTH:
                default:
                    return Block.makeCuboidShape(2.0D, 7.0D, 7.0D, 14.0D, 16.0D, 16.0D);
            }
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING,HANGING,WATERLOGGED);
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }


}

