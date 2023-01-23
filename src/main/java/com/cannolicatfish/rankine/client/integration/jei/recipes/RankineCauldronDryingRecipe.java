package com.cannolicatfish.rankine.client.integration.jei.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

public class RankineCauldronDryingRecipe implements IRankineCauldronRecipe {

    FluidStack inputFluid;
    ItemStack fluidHolderItem;
    ItemStack holderOutputItem;
    FluidStack outputFluid;
    ItemStack outputItem;


    public RankineCauldronDryingRecipe(FluidStack input, ItemStack holder, ItemStack holderOutput, ItemStack output) {
        this.inputFluid = input;
        this.holderOutputItem = holderOutput;
        this.fluidHolderItem = holder;
        this.outputItem = output;
        this.outputFluid = null;
    }

    public RankineCauldronDryingRecipe(FluidStack input, ItemStack holder, ItemStack holderOutput, FluidStack output) {
        this.inputFluid = input;
        this.holderOutputItem = holderOutput;
        this.fluidHolderItem = holder;
        this.outputFluid = output;
        this.outputItem = null;
    }
    @Override
    public @Unmodifiable FluidStack getInputFluid() {
        return this.inputFluid;
    }

    @Override
    public ItemStack getFluidHolderItem() {
        return this.fluidHolderItem;
    }

    @Override
    public @Unmodifiable ItemStack getHolderOutputItem() {
        return this.holderOutputItem;
    }

    @Override
    public @Nullable @Unmodifiable FluidStack getOutputFluid() {
        return this.outputFluid;
    }

    @Override
    public @Nullable @Unmodifiable ItemStack getOutputItem() {
        return this.outputItem;
    }
}
