package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModRecipes;
import net.minecraft.item.ItemStack;

public class InductionFurnaceRecipes {

    private static final InductionFurnaceRecipes INSTANCE = new InductionFurnaceRecipes();

    public static InductionFurnaceRecipes getInstance() {
        return INSTANCE;
    }

    public ItemStack getTripleAlloyResult(ItemStack input1, ItemStack input2, ItemStack input3,ItemStack input4, ItemStack input5) {
        return ModRecipes.getTripleAlloyOutput(input1,input2,input3,input4,input5);
    }
}
