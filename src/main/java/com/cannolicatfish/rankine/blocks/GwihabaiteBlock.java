package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class GwihabaiteBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected static final VoxelShape[] UP_SHAPES = new VoxelShape[] {Block.box(5.0D, 0.0D, 5.0D, 11.0D, 3.0D, 11.0D)};
    protected static final VoxelShape[] DOWN_SHAPES = new VoxelShape[] {Block.box(5.0D, 13.0D, 5.0D, 11.0D, 16.0D, 11.0D)};
    protected static final VoxelShape[] NORTH_SHAPES = new VoxelShape[] {Block.box(5.0D, 5.0D, 13.0D, 11.0D, 11.0D, 16.0D)};
    protected static final VoxelShape[] SOUTH_SHAPES = new VoxelShape[] {Block.box(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 3.0D)};
    protected static final VoxelShape[] WEST_SHAPES = new VoxelShape[] {Block.box(13.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D)};
    protected static final VoxelShape[] EAST_SHAPES = new VoxelShape[] {Block.box(0.0D, 5.0D, 5.0D, 3.0D, 11.0D, 11.0D)};

    public GwihabaiteBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(WATERLOGGED, false));
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch(state.getValue(FACING)) {
            case UP:
                return UP_SHAPES[0];
            case DOWN:
                return DOWN_SHAPES[0];
            case EAST:
                return EAST_SHAPES[0];
            case WEST:
                return WEST_SHAPES[0];
            case NORTH:
                return NORTH_SHAPES[0];
            default:
                return SOUTH_SHAPES[0];
        }
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos().relative(direction.getOpposite()));
        return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, fluidState.is(FluidTags.WATER));
    }

    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return isSideSolidForDirection(worldIn, pos, state.getValue(FACING).getOpposite());
    }

    public static boolean isSideSolidForDirection(LevelReader reader, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.relative(direction);
        return reader.getBlockState(blockpos).isFaceSturdy(reader, blockpos, direction.getOpposite());
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return type == PathComputationType.WATER && worldIn.getFluidState(pos).is(FluidTags.WATER);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.getBlock() == this || super.skipRendering(state, adjacentBlockState, side);
    }
}
