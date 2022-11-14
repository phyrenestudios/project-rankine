package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FloodGateBlock extends Block implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FloodGateBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.FALSE));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }


    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    public static boolean placeFluid(World worldIn, BlockPos pos, BlockState bs) {
        if (worldIn.getBlockState(pos.down()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.down(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.north()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.north(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.east()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.east(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.south()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.south(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.west()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.west(),bs,3);
            return true;
        }
        return false;
    }
/*
    public static boolean inInfiniteSource(World worldIn, BlockPos pos) {
        int waterSides = 0;
        for (Direction d : Direction.values()) {
            if (worldIn.getBlockState(pos.offset(d)).matchesBlock(Blocks.WATER)) {
                waterSides += 1;
            }
        }
        if (waterSides > 1) {
            if (Config.GENERAL.DISABLE_WATER.get()) {
                return pos.getY() > WorldgenUtils.waterTableHeight(worldIn, pos);
            } else {
                return true;
            }
        }
        return false;
    }

 */

}
