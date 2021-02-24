package com.cannolicatfish.rankine.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PowerCellItem extends Item {
    int tier;
    public PowerCellItem(int tierIn, Properties properties) {
        super(properties);
        this.tier = tierIn;
    }

    private int getTier() {
        return this.tier;
    }

    public static int getTier(ItemStack stack) {
        if (stack.getItem() instanceof PowerCellItem) {
            return ((PowerCellItem) stack.getItem()).getTier();
        } else {
            return 0;
        }
    }
}
