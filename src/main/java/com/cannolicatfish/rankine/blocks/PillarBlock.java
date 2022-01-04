package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PillarBlock extends RotatedPillarBlock implements IWaterLoggable {
    public static final IntegerProperty SIZE = IntegerProperty.create("size",1,8);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PillarBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SIZE, 8).with(WATERLOGGED, false).with(AXIS, Direction.Axis.Y));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE, WATERLOGGED, AXIS);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int size = state.get(SIZE);
        switch (state.get(AXIS)) {
            case X:
                return Block.makeCuboidShape(0,8-size,8-size,16,8+size,8+size);
            case Z:
                return Block.makeCuboidShape(8-size,8-size,0,8+size,8+size,16);
            default:
            case Y:
                return Block.makeCuboidShape(8-size,0,8-size,8+size,16,8+size);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = fluidstate.getFluid() == Fluids.WATER;

        ItemStack heldItem = context.getPlayer().getHeldItemOffhand();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(SIZE, BuildingToolItem.getBuildingMode(heldItem)+1).with(WATERLOGGED, flag).with(AXIS, context.getFace().getAxis());
        }
        return this.getDefaultState().with(WATERLOGGED, flag).with(AXIS, context.getFace().getAxis());
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }


}
