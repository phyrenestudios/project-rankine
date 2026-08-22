package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.recipe.helper.AlloyColorHelper;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

/**
 * The inventory/hand tint for every alloy item. Behaviourally unchanged; the body moved to
 * {@link AlloyColorHelper#getTintARGB(ItemStack)} so the worn-armor layer
 * ({@code AlloyArmorClientExtensions}) reads the identical value and the two cannot drift.
 */
public class AlloyItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return AlloyColorHelper.getTintARGB(stack);
    }
}
