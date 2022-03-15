package com.cannolicatfish.rankine.items;

import net.minecraft.tags.FluidTags;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import net.minecraft.item.Item.Properties;

public class MetalCanteenItem extends CanteenItem {
    public MetalCanteenItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return true;
    }
}
