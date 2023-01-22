package com.cannolicatfish.rankine.client.integration.jei.recipes;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Unmodifiable;

import javax.annotation.Nonnegative;
import java.util.List;

public interface IBatteryRecipe {

    /**
     * @return the inputs that act as a battery
     */
    @Unmodifiable
    List<ItemStack> getInputs();

    /**
     * @return the battery's duration in ticks. Always greater than 0.
     */
    @Nonnegative
    int getBatteryCharge();
}
