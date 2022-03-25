package com.cannolicatfish.rankine.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

public class FuelBlockItem extends BlockItem {
    int fuelValue;
    public FuelBlockItem(Block blockIn, Properties builder, int fuelValue) {
        super(blockIn, builder);
        this.fuelValue = fuelValue;
    }
}
