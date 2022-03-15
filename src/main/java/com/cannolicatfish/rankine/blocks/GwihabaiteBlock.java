package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import net.minecraft.block.AbstractBlock.Properties;

public class GwihabaiteBlock extends Block implements IWaterLoggable {
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
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

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getClickedFace();
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos().relative(direction.getOpposite()));
        return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, fluidState.is(FluidTags.WATER));
    }

    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return isSideSolidForDirection(worldIn, pos, state.getValue(FACING).getOpposite());
    }

    public static boolean isSideSolidForDirection(IWorldReader reader, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.relative(direction);
        return reader.getBlockState(blockpos).isFaceSturdy(reader, blockpos, direction.getOpposite());
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return type == PathType.WATER && worldIn.getFluidState(pos).is(FluidTags.WATER);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.getBlock() == this || super.skipRendering(state, adjacentBlockState, side);
    }
}
