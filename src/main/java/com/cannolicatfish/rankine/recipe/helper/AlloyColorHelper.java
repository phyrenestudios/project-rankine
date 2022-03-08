package com.cannolicatfish.rankine.recipe.helper;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;

import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class AlloyColorHelper {

    public static int getColor(ItemStack stack, int tintIndex) {
        if (stack.getTag() != null && stack.getTag().contains("color")) {
            return stack.getTag().getInt("color");
        }
        return 16777215;
    }

}
