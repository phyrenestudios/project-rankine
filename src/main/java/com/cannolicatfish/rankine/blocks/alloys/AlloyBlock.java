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
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.AbstractBlock.Properties;

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

    public boolean triggerEvent(BlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.triggerEvent(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @org.jetbrains.annotations.Nullable LivingEntity placer, ItemStack stack) {
        if (!IAlloyItem.getAlloyComposition(stack).isEmpty()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof AlloyBlockTile && stack.getTag() != null) {
                ((AlloyBlockTile) tileentity).writeAlloyData(stack.getTag());
            }
        }
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isClientSide && !player.isCreative() && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof AlloyBlockTile) {
                AlloyBlockTile alloyBlockTile = (AlloyBlockTile)tileentity;
                if (canHarvestBlock(state,worldIn,pos,player)) {
                    ItemStack itemstack = new ItemStack(this);
                    itemstack.setTag(alloyBlockTile.getAlloyData());
                    ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemstack);
                    itementity.setDefaultPickUpDelay();
                    worldIn.addFreshEntity(itementity);
                }
            }
        }


        //super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Collections.emptyList();
    }
}
