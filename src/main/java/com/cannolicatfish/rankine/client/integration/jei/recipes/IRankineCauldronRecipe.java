package com.cannolicatfish.rankine.client.integration.jei.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

public interface IRankineCauldronRecipe {

    /**
     * @return the fluid input for the recipe
     */
    @Unmodifiable
    FluidStack getInputFluid();

    /**
     * @return the item used to gain the item output (bucket or bottle)
     */
    ItemStack getFluidHolderItem();

    /**
     * @return the output if bottled/bucketed
     */
    @Unmodifiable
    ItemStack getHolderOutputItem();

    /**
     * @return the fluid output for the recipe if dried
     */
    @Nullable
    @Unmodifiable
    FluidStack getOutputFluid();

    /**
     * @return the item output for the recipe if dried
     */
    @Nullable
    @Unmodifiable
    ItemStack getOutputItem();
}
