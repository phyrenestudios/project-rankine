package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModRecipes;
import net.minecraft.item.ItemStack;

import java.util.AbstractMap;

public class AlloyFurnaceRecipes {

    private static final AlloyFurnaceRecipes INSTANCE = new AlloyFurnaceRecipes();

    public static AlloyFurnaceRecipes getInstance() {
        return INSTANCE;
    }

    public ItemStack getAlloyResult(ItemStack input1, ItemStack input2, ItemStack input3) {
        return ModRecipes.getAlloyOutput(input1,input2,input3);
    }
}
