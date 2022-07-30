package com.cannolicatfish.rankine.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class HollowLogBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty MOSSY = BooleanProperty.create("mossy");

    public HollowLogBlock() {
        super(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1.0F, 1.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(MOSSY, false).setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS,MOSSY,WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(AXIS)) {
            case X:
                return Shapes.join(Shapes.block(),box(0,2,2,16,14,14), BooleanOp.ONLY_FIRST);
            case Z:
                return Shapes.join(Shapes.block(),box(2,2,0,14,14,16), BooleanOp.ONLY_FIRST);
            case Y:
            default:
                return Shapes.join(Shapes.block(),box(2,0,2,14,16,14), BooleanOp.ONLY_FIRST);
        }
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter worldIn, BlockPos pos) {
        switch (state.getValue(AXIS)) {
            case X:
                return box(0,2,2,16,14,14);
            case Z:
                return box(2,2,0,14,14,16);
            case Y:
            default:
                return box(2,0,2,14,16,14);
        }
    }


    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 20;
    }

    @Override
    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        if (fallDistance > 1.0f && worldIn.getRandom().nextFloat() < 0.2 && !worldIn.isClientSide) {
            worldIn.destroyBlock(pos,true);
        }
        super.fallOn(worldIn, state, pos, entityIn, fallDistance);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(context).setValue(MOSSY,false).setValue(WATERLOGGED, flag);
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
        }
        if (facing.equals(Direction.UP)) {
            return super.updateShape(stateIn.setValue(MOSSY, facingState.is(Blocks.MOSS_BLOCK) || facingState.is(Blocks.MOSS_CARPET)), facing, facingState, worldIn, currentPos, facingPos);
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

}
