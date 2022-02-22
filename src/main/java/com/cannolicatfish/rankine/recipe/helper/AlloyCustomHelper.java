package com.cannolicatfish.rankine.recipe.helper;

import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlloyCustomHelper {
    private static List<AlloyCraftingRecipe> CRAFTING_RECIPE_LIST = Collections.emptyList();
    private static List<AlloyingRecipe> ALLOY_RECIPE_LIST = Collections.emptyList();
    private static List<ElementRecipe> ELEMENT_RECIPE_LIST = Collections.emptyList();
    private static List<Item> ALLOY_ITEM_LIST = Collections.emptyList();

    private static final Map<Item, List<ItemStack>> CRAFTING_ITEM_LISTS = new HashMap<>();
    private static final Map<Item, List<ItemStack>> ALLOY_ITEM_LISTS = new HashMap<>();
    private static final Map<Item, ItemStack> ALLOY_SEARCH_LISTS = new HashMap<>();
    private static Map<Item, Tuple<ElementRecipe,Integer>> ELEMENT_ITEM_LISTS = new HashMap<>();

    public static void setCraftingRecipes(List<AlloyCraftingRecipe> recipes) {
        CRAFTING_RECIPE_LIST = recipes;
        CRAFTING_ITEM_LISTS.clear();
    }

    public static void setAlloyingRecipes(List<AlloyingRecipe> recipes) {
        ALLOY_RECIPE_LIST = recipes;
        ALLOY_ITEM_LISTS.clear();
    }

    public static void setElementRecipes(List<ElementRecipe> recipes) {
        ELEMENT_RECIPE_LIST = recipes;
        ELEMENT_ITEM_LISTS.clear();
        setItemsFromElements();
    }

    public static List<ElementRecipe> getElementRecipeList() {
        return ELEMENT_RECIPE_LIST;
    }

    public static List<AlloyingRecipe> getAlloyRecipeList() {
        return ALLOY_RECIPE_LIST;
    }

    public static void setAlloyingItemRecipes(List<Item> items) {
        ALLOY_ITEM_LIST = items;
        ALLOY_SEARCH_LISTS.clear();
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

    public static void setItemsFromElements() {
        for (ElementRecipe recipe :  ELEMENT_RECIPE_LIST) {
            List<Ingredient> ing = recipe.getIngredients();
            for (int x = 0; x < ing.size(); x++) {
                ItemStack[] stacks = ing.get(x).getMatchingStacks();
                for (ItemStack stack : stacks) {
                    Item item = stack.getItem();
                    if (ELEMENT_ITEM_LISTS.containsKey(item)) {
                        System.out.println(item + " already exists in recipe " + ELEMENT_ITEM_LISTS.get(item).getA().getId() + "!");
                    }
                    ELEMENT_ITEM_LISTS.put(item, new Tuple<>(recipe, recipe.getValues().get(x)));
                }

            }

        }
    }

    public static boolean hasElement(Item item) {
        return ELEMENT_ITEM_LISTS.containsKey(item);
    }

    public static Tuple<ElementRecipe,Integer> getEntryForElementItem(Item item) {
        return ELEMENT_ITEM_LISTS.get(item);
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

    public static ItemStack getItemstackForSearching(Item item, @Nullable World worldIn) {
        ItemStack list = ALLOY_SEARCH_LISTS.get(item);
        if (list == null && item instanceof IAlloyTool) {
            IAlloyTool tool = (IAlloyTool) item;
            list = new ItemStack(item);
            tool.createAlloyNBT(list,worldIn,tool.getDefaultComposition(),tool.getDefaultRecipe(),null);
            ALLOY_SEARCH_LISTS.put(item, list);
        }
        return list;
    }
}
