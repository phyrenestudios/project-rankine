package com.cannolicatfish.rankine.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class BandageItem extends Item {

    public BandageItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.getHealth() < playerIn.getMaxHealth()) {
            ItemStack itemstack = playerIn.getItemInHand(handIn);
            worldIn.playSound((PlayerEntity)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundCategory.NEUTRAL, 0.1F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            playerIn.getCooldowns().addCooldown(this, 60);

            if (!worldIn.isClientSide && !playerIn.abilities.instabuild) {
                playerIn.heal(2.0f);
                itemstack.shrink(1);
            }
        }

        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        World worldIn = playerIn.getCommandSenderWorld();
        if (target.getHealth() < target.getMaxHealth()) {
            ItemStack itemstack = playerIn.getItemInHand(hand);
            worldIn.playSound((PlayerEntity)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundCategory.NEUTRAL, 0.1F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            playerIn.getCooldowns().addCooldown(this, 60);

            if (!worldIn.isClientSide) {
                target.heal(2.0f);
                itemstack.shrink(1);
            }
        }

        return super.interactLivingEntity(stack,playerIn,target,hand);
    }
}
