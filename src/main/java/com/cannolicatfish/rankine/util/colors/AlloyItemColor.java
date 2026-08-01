package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.init.RankineDataComponents;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class AlloyItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        Integer color = stack.get(RankineDataComponents.COLOR.get());
        return color != null ? color : 16777215;
    }
}
