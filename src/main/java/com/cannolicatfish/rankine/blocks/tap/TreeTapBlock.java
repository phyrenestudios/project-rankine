package com.cannolicatfish.rankine.blocks.tap;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class TreeTapBlock extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    protected static final VoxelShape TAP_WEST_AABB = Block.box(5.0D, 0.0D, 4.0D, 16.0D, 13.0D, 12.0D);
    protected static final VoxelShape TAP_EAST_AABB = Block.box(0.0D, 0.0D, 4.0D, 11.0D, 13.0D, 12.0D);
    protected static final VoxelShape TAP_NORTH_AABB = Block.box(4.0D, 0.0D, 5.0D, 12.0D, 13.0D, 16.0D);
    protected static final VoxelShape TAP_SOUTH_AABB = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 13.0D, 11.0D);

    public TreeTapBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.getValue(FACING)) {
            case NORTH:
                return TAP_NORTH_AABB;
            case SOUTH:
                return TAP_SOUTH_AABB;
            case WEST:
                return TAP_WEST_AABB;
            case EAST:
            default:
                return TAP_EAST_AABB;
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TreeTapTile();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (!world.isClientSide) {
            LazyOptional<IFluidHandlerItem> item = FluidUtil.getFluidHandler(player.getItemInHand(hand));
            TreeTapTile treeTapTile = (TreeTapTile) world.getBlockEntity(pos);
            if (item.isPresent() && treeTapTile != null) {
                FluidActionResult fillContainerAndStowOutput = FluidUtil.tryFillContainerAndStow(player.getItemInHand(hand), treeTapTile.outputTank, new InvWrapper(player.inventory), treeTapTile.outputTank.getFluidAmount(), player, true);
                if (fillContainerAndStowOutput.isSuccess()) {
                    player.setItemInHand(hand, fillContainerAndStowOutput.getResult());
                    world.blockEntityChanged(pos, treeTapTile);
                    return ActionResultType.CONSUME;
                }
            }
            return ActionResultType.CONSUME;
        } else {
            return ActionResultType.FAIL;
        }
    }

    private boolean canAttachTo(IBlockReader blockReader, BlockPos pos, Direction direction) {
        BlockState blockstate = blockReader.getBlockState(pos);
        return blockstate.isFaceSturdy(blockReader, pos, direction);
    }

    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return this.canAttachTo(worldIn, pos.relative(direction.getOpposite()), direction);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing.getOpposite() == stateIn.getValue(FACING) && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
