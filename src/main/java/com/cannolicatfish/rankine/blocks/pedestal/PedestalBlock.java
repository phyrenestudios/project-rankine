package com.cannolicatfish.rankine.blocks.pedestal;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class PedestalBlock extends BaseEntityBlock {
    public static final MapCodec<PedestalBlock> CODEC = simpleCodec(PedestalBlock::new);

    @Override
    protected MapCodec<PedestalBlock> codec() {
        return CODEC;
    }

    public PedestalBlock() {
        this(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 10.0F));
    }

    public PedestalBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.or(
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
                Block.box(3.0D, 2.0D, 3.0D, 13.0D, 5.0D, 13.0D),
                Block.box(4.0D, 5.0D, 4.0D, 12.0D, 11.0D, 12.0D),
                Block.box(3.0D, 11.0D, 3.0D, 13.0D, 14.0D, 13.0D),
                Block.box(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D)
        );
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level levelIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        InteractionResult result = interactPedestal(state, levelIn, pos, player, handIn);
        if (result == InteractionResult.SUCCESS) {
            return ItemInteractionResult.SUCCESS;
        } else if (result == InteractionResult.CONSUME) {
            return ItemInteractionResult.CONSUME;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level levelIn, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionResult result = interactPedestal(state, levelIn, pos, player, InteractionHand.MAIN_HAND);
        return result == null ? super.useWithoutItem(state, levelIn, pos, player, hit) : result;
    }

    // Shared logic of the old Block#use — handles both take-out (empty hand) and put-in (held item).
    @javax.annotation.Nullable
    private InteractionResult interactPedestal(BlockState state, Level levelIn, BlockPos pos, Player player, InteractionHand handIn) {
        PedestalTile tile = (PedestalTile) levelIn.getBlockEntity(pos);
        if (tile == null) return null;
        //if (levelIn.isClientSide) return InteractionResult.CONSUME;
        ItemStack tileStack = tile.getItem(0);
        ItemStack playerStack = player.getItemInHand(handIn).copy();
        boolean flag = false;
        if (!tile.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(levelIn, tile.getBlockPos().getX() + 0.5, tile.getBlockPos().getY() + 1.0, tile.getBlockPos().getZ() + 0.5, tileStack);
            itemEntity.setDefaultPickUpDelay();
            levelIn.addFreshEntity(itemEntity);
            tile.clearContent();
            flag = true;
        }
        if (!playerStack.isEmpty()) {
            playerStack.setCount(1);
            tile.setItem(0, playerStack);
            player.getItemInHand(handIn).shrink(1);
            flag = true;
        }
        if (flag) {
            levelIn.sendBlockUpdated(pos, state, state, 3);
            levelIn.updateNeighbourForOutputSignal(pos, this);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof PedestalTile) {
                Containers.dropContents(worldIn, pos, (PedestalTile)tileentity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    protected RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new PedestalTile(p_153215_,p_153216_);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState p_48700_) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState p_48702_, Level p_48703_, BlockPos p_48704_) {
        BlockEntity tileentity = p_48703_.getBlockEntity(p_48704_);
        if (tileentity instanceof PedestalTile) {
            return ((PedestalTile)tileentity).getComparatorSignalLevel();
        }
        return 0;
    }
    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState stateIn, Direction side, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return stateIn;
    }

}
