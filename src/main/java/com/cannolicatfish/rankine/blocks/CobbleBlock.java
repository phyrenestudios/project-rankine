package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class CobbleBlock extends Block implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CobbleBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 3.0F).harvestLevel(0));
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.FALSE));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D);
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.isValidPosition(worldIn, currentPos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            if (stateIn.get(WATERLOGGED)) {
                worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            }

            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate1 = this.getDefaultState();
        IWorldReader iworldreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());

        if (blockstate1.isValidPosition(iworldreader, blockpos)) {
            return blockstate1.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
        }


        return null;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).isNormalCube(worldIn,pos.down());
    }

    public OffsetType getOffsetType() {
        return OffsetType.XYZ;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
