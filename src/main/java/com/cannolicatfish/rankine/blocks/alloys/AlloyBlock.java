package com.cannolicatfish.rankine.blocks.alloys;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class AlloyBlock extends Block {
    public AlloyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AlloyBlockTile();
    }

    public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @org.jetbrains.annotations.Nullable LivingEntity placer, ItemStack stack) {
        if (!IAlloyItem.getAlloyComposition(stack).isEmpty()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof AlloyBlockTile && stack.getTag() != null) {
                ((AlloyBlockTile) tileentity).writeAlloyData(stack.getTag());
            }
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote && !player.isCreative() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof AlloyBlockTile) {
                AlloyBlockTile alloyBlockTile = (AlloyBlockTile)tileentity;
                if (canHarvestBlock(state,worldIn,pos,player)) {
                    ItemStack itemstack = new ItemStack(this);
                    itemstack.setTag(alloyBlockTile.getAlloyData());
                    ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemstack);
                    itementity.setDefaultPickupDelay();
                    worldIn.addEntity(itementity);
                }
            }
        }


        //super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        if (world.getTileEntity(pos) instanceof AlloyBlockTile) {
            AlloyBlockTile alloyBlockTile = (AlloyBlockTile) world.getTileEntity(pos);
            if (alloyBlockTile != null) {
                ItemStack itemstack = new ItemStack(state.getBlock());
                itemstack.setTag(alloyBlockTile.getAlloyData());
                return itemstack;
            }
        }
        return super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Collections.emptyList();
    }
}
