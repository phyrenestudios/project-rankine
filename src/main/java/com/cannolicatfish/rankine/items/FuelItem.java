package com.cannolicatfish.rankine.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

public class FuelItem extends Item {
    int fuelValue;
    public FuelItem(Properties properties, int fuelValue) {
        super(properties);
        this.fuelValue = fuelValue;
    }
}
