package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.items.AlloyTemplateItemOld;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class TemplateItemColor implements IItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (stack.getItem() instanceof AlloyTemplateItemOld) {
            return AlloyTemplateItemOld.getColor(stack);
        }
        return 16777215;
    }
}
