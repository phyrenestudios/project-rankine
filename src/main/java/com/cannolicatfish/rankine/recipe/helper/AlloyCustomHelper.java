package com.cannolicatfish.rankine.recipe.helper;

import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlloyCustomHelper {
    private static List<AlloyCraftingRecipe> CRAFTING_RECIPE_LIST = Collections.emptyList();
    private static List<AlloyingRecipe> ALLOY_RECIPE_LIST = Collections.emptyList();

    private static final Map<Item, List<ItemStack>> CRAFTING_ITEM_LISTS = new HashMap<>();
    private static final Map<Item, List<ItemStack>> ALLOY_ITEM_LISTS = new HashMap<>();

    public static void setCraftingRecipes(List<AlloyCraftingRecipe> recipes) {
        CRAFTING_RECIPE_LIST = recipes;
        CRAFTING_ITEM_LISTS.clear();
    }

    public static void setAlloyingRecipes(List<AlloyingRecipe> recipes) {
        ALLOY_RECIPE_LIST = recipes;
        ALLOY_ITEM_LISTS.clear();
    }

    public static List<ItemStack> getItemsFromAlloyCrafting(Item item) {
        List<ItemStack> list =  CRAFTING_ITEM_LISTS.get(item);
        if (list == null) {
            ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
            for (AlloyCraftingRecipe recipe :  CRAFTING_RECIPE_LIST) {
                if (item.equals(recipe.getRecipeOutput().getItem())) {
                    ItemStack stack = recipe.getRecipeOutput().copy();
                    stack.setCount(1);
                    builder.add(stack);
                }
            }
            list = builder.build();
            CRAFTING_ITEM_LISTS.put(item, list);
        }
        return list;
    }

    public static List<ItemStack> getItemsFromAlloying(Item item) {
        List<ItemStack> list = ALLOY_ITEM_LISTS.get(item);
        if (list == null) {
            ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
            for (AlloyingRecipe recipe : ALLOY_RECIPE_LIST) {
                if (item.equals(recipe.getRecipeOutput().getItem())) {
                    ItemStack stack = recipe.getRecipeOutput().copy();
                    stack.setCount(1);
                    builder.add(stack);
                }
            }
            list = builder.build();
            ALLOY_ITEM_LISTS.put(item, list);
        }
        return list;
    }
}
