package com.cannolicatfish.rankine.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

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
            return stack.getDamageValue() + powerRequired < stack.getMaxDamage();
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
}
