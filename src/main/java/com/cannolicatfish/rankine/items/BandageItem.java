package com.cannolicatfish.rankine.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class BandageItem extends Item {

    public BandageItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (playerIn.getHealth() < playerIn.getMaxHealth()) {
            ItemStack itemstack = playerIn.getItemInHand(handIn);
            worldIn.playSound((Player)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 0.1F, 0.4F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
            playerIn.getCooldowns().addCooldown(this, 60);

            if (!worldIn.isClientSide && !playerIn.getAbilities().instabuild) {
                playerIn.heal(2.0f);
                itemstack.shrink(1);
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        Level worldIn = playerIn.getCommandSenderWorld();
        if (target.getHealth() < target.getMaxHealth()) {
            ItemStack itemstack = playerIn.getItemInHand(hand);
            worldIn.playSound((Player)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 0.1F, 0.4F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
            playerIn.getCooldowns().addCooldown(this, 60);

            if (!worldIn.isClientSide) {
                target.heal(2.0f);
                itemstack.shrink(1);
            }
        }

        return super.interactLivingEntity(stack,playerIn,target,hand);
    }
}
