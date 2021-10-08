package com.cannolicatfish.rankine.blocks.fusionfurnace;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FusionFurnaceTile {
    FluidTank inputTank;
    FluidTank outputTank;
    protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
}
