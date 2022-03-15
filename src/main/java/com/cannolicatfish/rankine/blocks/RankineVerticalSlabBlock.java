package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.states.VerticalSlabStates;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class RankineVerticalSlabBlock extends Block implements IWaterLoggable {
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.FACING;
    public static final EnumProperty<VerticalSlabStates> TYPE = EnumProperty.create("type", VerticalSlabStates .class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape STRAIGHT_N = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape STRAIGHT_S = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape STRAIGHT_W = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape STRAIGHT_E = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape INNER_N = Block.box(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape INNER_S = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape INNER_E = Block.box(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape INNER_W = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);


    public RankineVerticalSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, VerticalSlabStates.STRAIGHT).setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return state.getValue(TYPE) != VerticalSlabStates.DOUBLE;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction facing = state.getValue(HORIZONTAL_FACING);
        switch(state.getValue(TYPE)) {
            case DOUBLE:
                return VoxelShapes.block();
            case STRAIGHT:
                switch(facing) {
                    case NORTH:
                        return STRAIGHT_N;
                    case SOUTH:
                        return STRAIGHT_S;
                    case WEST:
                        return STRAIGHT_W;
                    case EAST:
                        return STRAIGHT_E;
                }
            case INNER:
                switch(facing) {
                    case SOUTH:
                        return INNER_S;
                    case WEST:
                        return INNER_W;
                    case EAST:
                        return INNER_E;
                    case NORTH:
                        return INNER_N;
                }
            case OUTER:
                switch(facing) {
                    case SOUTH:
                        return VoxelShapes.or(STRAIGHT_S,INNER_S);
                    case WEST:
                        return VoxelShapes.or(STRAIGHT_W,INNER_W);
                    case EAST:
                        return VoxelShapes.or(STRAIGHT_E,INNER_E);
                    case NORTH:
                        return VoxelShapes.or(STRAIGHT_N,INNER_N);
                }
        }
        return VoxelShapes.block();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = context.getLevel().getBlockState(blockpos);
        if (blockstate.is(this)) {
            return blockstate.setValue(TYPE, VerticalSlabStates.DOUBLE).setValue(WATERLOGGED, Boolean.FALSE);
        } else {
            //blockstate1 = getType(context.getWorld(),context.getPos(),context.getPlacementHorizontalFacing());
            FluidState fluidstate = context.getLevel().getFluidState(blockpos);
            BlockState blockstate1 = this.defaultBlockState().setValue(TYPE, VerticalSlabStates.STRAIGHT).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
            Direction direction = context.getClickedFace();
            if (direction.getAxis() == Direction.Axis.Y) {
                Vector3d vec = context.getClickLocation().subtract(new Vector3d(blockpos.getX(), blockpos.getY(), blockpos.getZ())).subtract(0.5, 0, 0.5);
                double angle = Math.atan2(vec.x, vec.z) * -180.0 / Math.PI;
                blockstate1 = blockstate1.setValue(HORIZONTAL_FACING, Direction.fromYRot(angle));
            } else {
                blockstate1 = blockstate1.setValue(HORIZONTAL_FACING, direction.getOpposite());
            }

            return blockstate1;
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
        ItemStack itemstack = useContext.getItemInHand();
        VerticalSlabStates slabtype = state.getValue(TYPE);
        if (slabtype != VerticalSlabStates.DOUBLE && itemstack.getItem() == this.asItem()) {
            if (useContext.replacingClickedOnBlock()) {
                return useContext.getClickedFace() == state.getValue(HORIZONTAL_FACING).getOpposite();
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        if (facing != Direction.DOWN && facing != Direction.UP && facing != stateIn.getValue(HORIZONTAL_FACING).getOpposite()) {
            return stateIn;
        } else {
            return stateIn;
        }
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(HORIZONTAL_FACING, rot.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, HORIZONTAL_FACING, WATERLOGGED);
    }


    public BlockState getType(World worldIn, BlockPos pos, Direction facing) {

        BlockState forwardBS = worldIn.getBlockState(pos.relative(facing));
        BlockState backwardBS = worldIn.getBlockState(pos.relative(facing.getOpposite()));
        BlockState leftBS = worldIn.getBlockState(pos.relative(facing.getCounterClockWise().getCounterClockWise().getCounterClockWise()));
        BlockState rightBS = worldIn.getBlockState(pos.relative(facing.getCounterClockWise()));

        boolean forward = forwardBS.is(this);
        boolean backward = backwardBS.is(this);
        boolean left = leftBS.is(this);
        boolean right = rightBS.is(this);

        if (left && right) {
            return this.defaultBlockState().setValue(TYPE, VerticalSlabStates.STRAIGHT).setValue(HORIZONTAL_FACING, facing);
        } else if (backward && left) {
            return this.defaultBlockState().setValue(TYPE, VerticalSlabStates.OUTER).setValue(HORIZONTAL_FACING, facing);
        } else if (backward && right) {
            return this.defaultBlockState().setValue(TYPE, VerticalSlabStates.OUTER).setValue(HORIZONTAL_FACING, facing).mirror(Mirror.LEFT_RIGHT);
        }

/*
        if (forward) {
            if (forwardBS.get(HORIZONTAL_FACING).equals(RDir)) {
                return this.getDefaultState().with(TYPE, VerticalSlabStates.OUTER).with(HORIZONTAL_FACING, facing);
            } else if (forwardBS.get(HORIZONTAL_FACING).equals(LDir)) {
                return this.getDefaultState().with(TYPE, VerticalSlabStates.OUTER).with(HORIZONTAL_FACING, facing.rotateY());
            }
        } else if (backward) {
            if (backwardBS.get(HORIZONTAL_FACING).equals(RDir)) {
                return this.getDefaultState().with(TYPE, VerticalSlabStates.INNER).with(HORIZONTAL_FACING, facing);
            } else if (backwardBS.get(HORIZONTAL_FACING).equals(LDir)) {
                return this.getDefaultState().with(TYPE, VerticalSlabStates.INNER).with(HORIZONTAL_FACING, facing.rotateY());
            }
        } else {
            return this.getDefaultState().with(TYPE, VerticalSlabStates.STRAIGHT).with(HORIZONTAL_FACING, facing);

        }

 */


        return this.defaultBlockState().setValue(TYPE, VerticalSlabStates.STRAIGHT).setValue(HORIZONTAL_FACING, facing);

    }


}
