package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.states.VerticalSlabStates;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RankineVerticalSlabBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalDirectionalBlock.FACING;
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

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(HORIZONTAL_FACING);
        switch(state.getValue(TYPE)) {
            case DOUBLE:
                return Shapes.block();
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
                        return Shapes.or(STRAIGHT_S,INNER_S);
                    case WEST:
                        return Shapes.or(STRAIGHT_W,INNER_W);
                    case EAST:
                        return Shapes.or(STRAIGHT_E,INNER_E);
                    case NORTH:
                        return Shapes.or(STRAIGHT_N,INNER_N);
                }
        }
        return Shapes.block();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
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
                Vec3 vec = context.getClickLocation().subtract(new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ())).subtract(0.5, 0, 0.5);
                double angle = Math.atan2(vec.x, vec.z) * -180.0 / Math.PI;
                blockstate1 = blockstate1.setValue(HORIZONTAL_FACING, Direction.fromYRot(angle));
            } else {
                blockstate1 = blockstate1.setValue(HORIZONTAL_FACING, direction.getOpposite());
            }

            return blockstate1;
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
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

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, HORIZONTAL_FACING, WATERLOGGED);
    }


    public BlockState getType(Level worldIn, BlockPos pos, Direction facing) {

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
