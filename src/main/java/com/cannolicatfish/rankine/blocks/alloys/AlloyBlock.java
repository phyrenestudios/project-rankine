package com.cannolicatfish.rankine.blocks.alloys;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @org.jetbrains.annotations.Nullable LivingEntity placer, ItemStack stack) {
        if (AlloyItem.getComposition(stack).size() > 0) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            INBT nbt = AlloyItem.getComposition(stack).getCompound(0).get("comp");
            if (tileentity instanceof AlloyBlockTile && nbt != null) {
                ((AlloyBlockTile)tileentity).setComp(nbt.getString());
            }
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
/*
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof AlloyBlockTile) {
            AlloyBlockTile alloyBlockTile = (AlloyBlockTile)tileentity;
            if (!worldIn.isRemote && !player.isCreative() && canHarvestBlock(state,worldIn,pos,player)) {
                //ItemStack itemstack = getColoredItemStack(this.getColor());
                ItemStack itemstack = new ItemStack(this);
                String composition = alloyBlockTile.getComp();
                System.out.println(composition);
                if (!composition.isEmpty()) {
                    AlloyItem.addAlloy(itemstack,new AlloyData(composition));
                }

                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }*/
}
