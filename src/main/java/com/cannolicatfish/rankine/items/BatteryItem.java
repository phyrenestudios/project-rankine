package com.cannolicatfish.rankine.items;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class BatteryItem extends Item {
    public BatteryItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 65535;
    }

    private int getTier() {
        return 0;
    }

    public static int getTier(ItemStack stack) {
        if (stack.getItem() instanceof BatteryItem) {
            return ((BatteryItem) stack.getItem()).getTier();
        } else {
            return 0;
        }
    }

    public static boolean hasPowerRequired(ItemStack stack, int powerRequired) {
        if (stack.getItem() instanceof BatteryItem) {
            return stack.getDamageValue() + powerRequired <= stack.getMaxDamage();
        } else {
            return false;
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (stack.getDamageValue() > 0) {
            if (entityIn instanceof LivingEntity living && living.hasEffect(MobEffects.CONDUIT_POWER)) {
                stack.setDamageValue(stack.getDamageValue() - 1);
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
