package com.cannolicatfish.rankine.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BatteryItem extends Item {
    public BatteryItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
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
}
