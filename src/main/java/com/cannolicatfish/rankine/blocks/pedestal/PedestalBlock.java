package com.cannolicatfish.rankine.blocks.pedestal;

import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerTile;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class PedestalBlock extends BaseEntityBlock {

    public PedestalBlock() {
        super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 10.0F));
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
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(handIn != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;
        PedestalTile tile = (PedestalTile) world.getBlockEntity(pos);
        if(!world.isClientSide && tile != null) {

            if (tile.getItem(0) != null && player.getItemInHand(handIn).isEmpty()) {
                if(world.getBlockState(pos.above()).getMaterial() != Material.AIR)
                    return InteractionResult.SUCCESS;
                ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), tile.getItem(0));
                world.addFreshEntity(item);
                tile.clearContent();
            } else if (!player.getInventory().getSelected().isEmpty()) {
                if(tile.getItem(0) != null){
                    ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), tile.getItem(0));
                    world.addFreshEntity(item);
                }
                ItemStack stack = player.getItemInHand(handIn).copy();
                stack.setCount(1);
                tile.setItem(0,stack);
                player.getItemInHand(handIn).shrink(1);
            }
            world.sendBlockUpdated(pos, state, state, 2);
        }
        return  InteractionResult.SUCCESS;
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

    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new PedestalTile(p_153215_,p_153216_);
    }

}
