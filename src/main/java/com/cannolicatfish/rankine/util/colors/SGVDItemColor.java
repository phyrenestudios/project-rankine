package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.items.ShulkerGasVacuumItem;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class SGVDItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (stack.getItem() instanceof ShulkerGasVacuumItem && tintIndex == 0) {
            return ShulkerGasVacuumItem.getColor(stack);
        }
        return 16777215;
    }
}
