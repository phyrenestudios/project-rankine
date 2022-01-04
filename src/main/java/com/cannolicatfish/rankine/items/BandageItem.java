package com.cannolicatfish.rankine.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class BandageItem extends Item {

    public BandageItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.getHealth() < playerIn.getMaxHealth()) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.NEUTRAL, 0.1F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            playerIn.getCooldownTracker().setCooldown(this, 60);

            if (!worldIn.isRemote && !playerIn.abilities.isCreativeMode) {
                playerIn.heal(2.0f);
                itemstack.shrink(1);
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        World worldIn = playerIn.getEntityWorld();
        if (target.getHealth() < target.getMaxHealth()) {
            ItemStack itemstack = playerIn.getHeldItem(hand);
            worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.NEUTRAL, 0.1F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            playerIn.getCooldownTracker().setCooldown(this, 60);

            if (!worldIn.isRemote) {
                target.heal(2.0f);
                itemstack.shrink(1);
            }
        }

        return super.itemInteractionForEntity(stack,playerIn,target,hand);
    }
}
