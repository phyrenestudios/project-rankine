package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
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
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StoneColumnBlock extends Block implements IWaterLoggable {
    public static final IntegerProperty SIZE = IntegerProperty.create("size",1,7);
    //public static final IntegerProperty STABILITY = IntegerProperty.create("stability",0,24);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public StoneColumnBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SIZE, 1).with(WATERLOGGED, false));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int size = state.get(SIZE);
        return Block.makeCuboidShape(8-size,0,8-size,8+size,16,8+size);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).isSolid() || worldIn.getBlockState(pos.down()).getBlock() == state.getBlock() || worldIn.getBlockState(pos.up()).isSolid() || worldIn.getBlockState(pos.up()).getBlock() == state.getBlock();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = fluidstate.getFluid() == Fluids.WATER;

        ItemStack heldItem = context.getPlayer().getHeldItemOffhand();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(SIZE, Math.min(7,BuildingToolItem.getBuildingMode(heldItem)+1)).with(WATERLOGGED, flag);
        }
        return this.getDefaultState().with(WATERLOGGED, flag);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        boolean flagU = false;
        boolean flagD = false;
        if (!isValidPosition(stateIn,worldIn,currentPos)) {
            return Blocks.AIR.getDefaultState();
        }

        if (facing == Direction.UP || facing == Direction.DOWN) {
            int i = 1;
            while (worldIn.getBlockState(currentPos.down(i)).matchesBlock(this)) {
                ++i;
            }
            if (!worldIn.getBlockState(currentPos.down(i)).isSolid()) {
                flagD = true;
            }
            int j = 1;
            while (worldIn.getBlockState(currentPos.up(j)).matchesBlock(this)) {
                ++j;
            }
            if (!worldIn.getBlockState(currentPos.up(j)).isSolid()) {
                flagU = true;
            }
            if (flagD && flagU) {
                for (int k = 1; k <= j; ++k) {
                    worldIn.destroyBlock(currentPos.up(k), true);
                }
                for (int k = 1; k <= i; ++k) {
                    worldIn.destroyBlock(currentPos.down(k), true);
                }
                return Blocks.AIR.getDefaultState();
            }
        }

        return  stateIn;
        //return facingState.matchesBlock(this) ? stateIn.with(SIZE, Math.max(1,facingState.get(SIZE)-1)) : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

}
