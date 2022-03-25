package com.cannolicatfish.rankine.blocks.alloys;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AlloyBlock extends Block {
    public AlloyBlock(Properties properties) {
        super(properties);
    }


    public boolean triggerEvent(BlockState state, Level worldIn, BlockPos pos, int id, int param) {
        super.triggerEvent(state, worldIn, pos, id, param);
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @org.jetbrains.annotations.Nullable LivingEntity placer, ItemStack stack) {
        if (!IAlloyItem.getAlloyComposition(stack).isEmpty()) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof AlloyBlockTile && stack.getTag() != null) {
                ((AlloyBlockTile) tileentity).writeAlloyData(stack.getTag());
            }
        }
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (!worldIn.isClientSide && !player.isCreative() && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
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
