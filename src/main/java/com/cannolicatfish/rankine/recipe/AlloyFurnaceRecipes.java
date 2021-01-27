package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipes;
import net.minecraft.item.ItemStack;

public class AlloyFurnaceRecipes {

    private static final AlloyFurnaceRecipes INSTANCE = new AlloyFurnaceRecipes();

    public static AlloyFurnaceRecipes getInstance() {
        return INSTANCE;
    }

    public ItemStack getAlloyResult(ItemStack input1, ItemStack input2, ItemStack input3) {
        return RankineRecipes.getAlloyOutput(input1,input2,input3);
    }
}
