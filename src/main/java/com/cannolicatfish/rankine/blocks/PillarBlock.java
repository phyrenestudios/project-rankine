package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class PillarBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    public static final IntegerProperty SIZE = IntegerProperty.create("size",1,8);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SIZE, 8).setValue(WATERLOGGED, false).setValue(AXIS, Direction.Axis.Y));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SIZE, WATERLOGGED, AXIS);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int size = state.getValue(SIZE);
        switch (state.getValue(AXIS)) {
            case X:
                return Block.box(0,8-size,8-size,16,8+size,8+size);
            case Z:
                return Block.box(8-size,8-size,0,8+size,8+size,16);
            default:
            case Y:
                return Block.box(8-size,0,8-size,8+size,16,8+size);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;

        ItemStack heldItem = context.getPlayer().getOffhandItem();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.defaultBlockState().setValue(SIZE, BuildingToolItem.getBuildingMode(heldItem)+1).setValue(WATERLOGGED, flag).setValue(AXIS, context.getClickedFace().getAxis());
        }
        return this.defaultBlockState().setValue(WATERLOGGED, flag).setValue(AXIS, context.getClickedFace().getAxis());
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }


}
