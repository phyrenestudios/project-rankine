package com.cannolicatfish.rankine.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Item.Properties;

public class FuelItem extends Item {
    int fuelValue;
    public FuelItem(Properties properties, int fuelValue) {
        super(properties);
        this.fuelValue = fuelValue;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return fuelValue;
    }
}
