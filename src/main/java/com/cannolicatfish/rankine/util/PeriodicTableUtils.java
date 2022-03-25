package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public final class PeriodicTableUtils {

    private static final PeriodicTableUtils INSTANCE = new PeriodicTableUtils();

    public static PeriodicTableUtils getInstance() {
        return INSTANCE;
    }


    public boolean hasElementRecipe(ItemStack stack, Level world) {
        return world != null && getElementRecipe(stack,world) != null;
    }

    public ElementRecipe getElementRecipe(ItemStack stack, Level world) {
        SimpleContainer temp = new SimpleContainer(stack);
        return world.getRecipeManager().getRecipeFor(RankineRecipeTypes.ELEMENT, temp, world).orElse(null);
    }

    public AlloyingRecipe getAlloyRecipe(ItemStack stack, Level world) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            Recipe<?> recipe = world.getRecipeManager().byKey(rs).orElse(null);
            if (recipe instanceof AlloyingRecipe) {
                return (AlloyingRecipe) recipe;
            }
        }
        return null;
    }
}
