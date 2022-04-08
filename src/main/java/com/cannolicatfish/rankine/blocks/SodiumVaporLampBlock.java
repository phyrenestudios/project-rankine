package com.cannolicatfish.rankine.blocks;


import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SodiumVaporLampBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalDirectionalBlock.FACING;

    public SodiumVaporLampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(HANGING, false).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockState blockstate = this.defaultBlockState();

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction == Direction.UP) {
                blockstate = this.defaultBlockState().setValue(HANGING,true);
            }
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
                    return blockstate.setValue(HORIZONTAL_FACING, direction1).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(HANGING)) {
            switch (state.getValue(HORIZONTAL_FACING)) {
                case EAST:
                case WEST:
                    return Block.box(4.0D, 10.0D, 2.0D, 12.0D, 16.0D, 14.0D);
                case SOUTH:
                case NORTH:
                default:
                    return Block.box(2.0D, 10.0D, 4.0D, 14.0D, 16.0D, 12.0D);
            }
        } else {
            switch (state.getValue(HORIZONTAL_FACING)) {
                case WEST:
                    return Block.box(7.0D, 7.0D, 2.0D, 16.0D, 16.0D, 14.0D);
                case EAST:
                    return Block.box(0.0D, 7.0D, 2.0D, 9.0D, 16.0D, 14.0D);
                case SOUTH:
                    return Block.box(2.0D, 7.0D, 0.0D, 14.0D, 16.0D, 9.0D);
                case NORTH:
                default:
                    return Block.box(2.0D, 7.0D, 7.0D, 14.0D, 16.0D, 16.0D);
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING,HANGING,WATERLOGGED);
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return false;
    }


}

