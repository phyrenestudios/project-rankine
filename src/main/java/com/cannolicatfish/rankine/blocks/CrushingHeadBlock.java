package com.cannolicatfish.rankine.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CrushingHeadBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CrushingHeadBlock() {
        super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion().dynamicShape());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(POWER, 0).setValue(WATERLOGGED, Boolean.FALSE));
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case DOWN:
                return Shapes.or(Block.box(1, 14, 1, 15, 16, 15), Block.box(6, 4, 6, 10, 14, 10), Block.box(3, 1, 3, 13, 4, 13), Block.box(4, 0, 4, 12, 1, 12));
            case EAST:
                return Shapes.or(Block.box(0, 1, 1, 2, 15, 15), Block.box(2, 6, 6, 12, 10, 10), Block.box(12, 3, 3, 15, 13, 13), Block.box(15, 4, 4, 16, 12, 12));
            case WEST:
                return Shapes.or(Block.box(14, 1, 1, 16, 15, 15), Block.box(4, 6, 6, 14, 10, 10), Block.box(1, 3, 3, 4, 13, 13), Block.box(0, 4, 4, 1, 12, 12));
            case SOUTH:
                return Shapes.or(Block.box(1, 1, 0, 15, 15, 2), Block.box(6, 6, 2, 10, 10, 12), Block.box(3, 3, 12, 13, 13, 15), Block.box(4, 4, 15, 12, 12, 16));
            case NORTH:
                return Shapes.or(Block.box(1, 1, 14, 15, 15, 16), Block.box(6, 6, 4, 10, 10, 14), Block.box(3, 3, 1, 13, 13, 4), Block.box(4, 4, 0, 12, 12, 1));
            case UP:
            default:
                return Shapes.or(Block.box(1, 0, 1, 15, 2, 15), Block.box(6, 2, 6, 10, 12, 10), Block.box(3, 12, 3, 13, 15, 13), Block.box(4, 15, 4, 12, 16, 12));
        }

    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing.getOpposite() == stateIn.getValue(FACING) && !stateIn.canSurvive(worldIn, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (stateIn.getValue(WATERLOGGED)) {
                worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
            }

            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        return this.defaultBlockState().setValue(FACING, direction).setValue(POWER, 0).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWER, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
