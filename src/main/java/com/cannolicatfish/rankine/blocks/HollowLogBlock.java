package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HollowLogBlock extends RotatedPillarBlock implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public HollowLogBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS,WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(AXIS)) {
            case X:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(0,2,2,16,14,14), IBooleanFunction.ONLY_FIRST);
            case Z:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(2,2,0,14,14,16), IBooleanFunction.ONLY_FIRST);
            case Y:
            default:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(2,0,2,14,16,14), IBooleanFunction.ONLY_FIRST);
        }
    }

    /*
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        switch (state.get(AXIS)) {
            case X:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(0,2,2,16,14,14), IBooleanFunction.ONLY_FIRST);
            case Z:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(2,2,0,14,14,16), IBooleanFunction.ONLY_FIRST);
            case Y:
            default:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(2,0,2,14,16,14), IBooleanFunction.ONLY_FIRST);
        }
    }

     */

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        switch (state.get(AXIS)) {
            case X:
                return makeCuboidShape(0,2,2,16,14,14);
            case Z:
                return makeCuboidShape(2,2,0,14,14,16);
            case Y:
            default:
                return makeCuboidShape(2,0,2,14,16,14);
        }
    }


    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 20;
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (fallDistance > 1.0f && worldIn.getRandom().nextFloat() < 0.2 && !worldIn.isRemote) {
            worldIn.destroyBlock(pos,true);
        }
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = fluidstate.getFluid() == Fluids.WATER;
        return super.getStateForPlacement(context).with(WATERLOGGED, flag);
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

}
