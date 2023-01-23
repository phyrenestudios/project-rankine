package com.cannolicatfish.rankine.client.integration.jei.recipes;

import com.google.common.base.Preconditions;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.List;

public class BatteryRecipe implements IBatteryRecipe{
    private final List<ItemStack> inputs;
    private final int charge;

    public BatteryRecipe(Collection<ItemStack> input, int charge) {
        Preconditions.checkArgument(charge > 0, "charge must be greater than 0");
        this.inputs = List.copyOf(input);
        this.charge = charge;
    }

    @Override
    public List<ItemStack> getInputs() {
        return inputs;
    }

    @Override
    public int getBatteryCharge() {
        return charge;
    }
}
