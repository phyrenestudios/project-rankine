package com.cannolicatfish.rankine.items;

import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.Item.Properties;

public class MetalCanteenItem extends CanteenItem {
    public MetalCanteenItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return true;
    }
}
