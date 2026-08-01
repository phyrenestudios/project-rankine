package com.cannolicatfish.rankine.recipe.helper;

import com.cannolicatfish.rankine.init.RankineDataComponents;
import net.minecraft.world.item.ItemStack;

public class AlloyColorHelper {

    public static int getColor(ItemStack stack, int tintIndex) {
        Integer color = stack.get(RankineDataComponents.COLOR.get());
        if (color != null) {
            return color;
        }
        return 16777215;
    }

}
