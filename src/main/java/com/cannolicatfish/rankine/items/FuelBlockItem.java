package com.cannolicatfish.rankine.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Item.Properties;

public class FuelBlockItem extends BlockItem {
    int fuelValue;
    public FuelBlockItem(Block blockIn, Properties builder, int fuelValue) {
        super(blockIn, builder);
        this.fuelValue = fuelValue;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return fuelValue;
    }
}
