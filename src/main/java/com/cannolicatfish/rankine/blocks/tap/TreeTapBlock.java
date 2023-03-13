package com.cannolicatfish.rankine.blocks.tap;

import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nullable;

public class TreeTapBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");
    protected static final VoxelShape TAP_WEST_AABB = Block.box(7.0D, 3.0D, 6.0D, 16.0D, 13.0D, 10.0D);
    protected static final VoxelShape TAP_EAST_AABB = Block.box(0.0D, 3.0D, 6.0D, 9.0D, 13.0D, 10.0D);
    protected static final VoxelShape TAP_NORTH_AABB = Block.box(6.0D, 3.0D, 7.0D, 10.0D, 13.0D, 16.0D);
    protected static final VoxelShape TAP_SOUTH_AABB = Block.box(6.0D, 3.0D, 0.0D, 10.0D, 13.0D, 9.0D);

    public TreeTapBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CONNECTED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, CONNECTED);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
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

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (world.isClientSide) return super.use(state, world, pos, player, hand, result);

        LazyOptional<IFluidHandlerItem> item = FluidUtil.getFluidHandler(player.getItemInHand(hand));
        TreeTapTile treeTapTile = (TreeTapTile) world.getBlockEntity(pos);
        if (item.isPresent() && treeTapTile != null) {
            if (FluidUtil.interactWithFluidHandler(player, hand, treeTapTile.outputTank)) {
                world.blockEntityChanged(pos);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, result);
    }

    private boolean canAttachTo(BlockGetter blockReader, BlockPos pos, Direction direction) {
        BlockState blockstate = blockReader.getBlockState(pos);
        return blockstate.isFaceSturdy(blockReader, pos, direction);
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return this.canAttachTo(worldIn, pos.relative(direction.getOpposite()), direction);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(CONNECTED, context.getLevel().getBlockState(context.getClickedPos().below().relative(context.getHorizontalDirection().getOpposite())).is(RankineBlocks.TAP_LINE.get()));
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing.getOpposite() == stateIn.getValue(FACING) && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn.setValue(CONNECTED, worldIn.getBlockState(currentPos.below()).is(RankineBlocks.TAP_LINE.get()));
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }


    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new TreeTapTile(p_153215_,p_153216_);
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockStateIn, BlockEntityType<T> blockEntityTypeIn) {
        return worldIn.isClientSide ? null : createTickerHelper(blockEntityTypeIn, RankineBlockEntityTypes.TREE_TAP.get(), TreeTapTile::tick);
    }
}
