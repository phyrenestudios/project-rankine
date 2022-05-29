package com.cannolicatfish.rankine.blocks.pedestal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class PedestalBlock extends BaseEntityBlock {

    public PedestalBlock() {
        super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 10.0F));
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.or(
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
                Block.box(3.0D, 2.0D, 3.0D, 13.0D, 5.0D, 13.0D),
                Block.box(4.0D, 5.0D, 4.0D, 12.0D, 11.0D, 12.0D),
                Block.box(3.0D, 11.0D, 3.0D, 13.0D, 14.0D, 13.0D),
                Block.box(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D)
        );
    }


    @Override
    public InteractionResult use(BlockState state, Level levelIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        //if(handIn != handIn.MAIN_HAND)
          //  return ActionResultType.FAIL;
        PedestalTile tile = (PedestalTile) levelIn.getBlockEntity(pos);
        if(tile != null) {
            if (!levelIn.isClientSide) {
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
            }
            return InteractionResult.CONSUME;
        }
        return super.use(state, levelIn, pos, player, handIn, hit);
    }

    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof PedestalTile) {
                Containers.dropContents(worldIn, pos, (PedestalTile)tileentity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153277_, BlockState p_153278_) {
        return new PedestalTile(p_153277_, p_153278_);
    }

    public boolean hasAnalogOutputSignal(BlockState p_48700_) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState p_48702_, Level p_48703_, BlockPos p_48704_) {
        BlockEntity tileentity = p_48703_.getBlockEntity(p_48704_);
        if (tileentity instanceof PedestalTile) {
            return ((PedestalTile)tileentity).getComparatorSignalLevel();
        }
        return 0;
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction side, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return stateIn;
    }
}
