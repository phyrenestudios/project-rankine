package com.cannolicatfish.rankine.events.handlers.client;

import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 1.21.1: RecipeManager#getAllRecipesFor returns List&lt;RecipeHolder&lt;T&gt;&gt;; RecipeTypes are
 * DeferredHolders now, so the lookups go through {@code .get()} and the holders are unwrapped.
 */
public class RecipesUpdatedHandler {
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        ClientLevel world = Minecraft.getInstance().level;
        if (world != null) {
            List<AlloyingRecipe> alloyingRecipes = world.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ALLOYING.get())
                    .stream().map(RecipeHolder::value).collect(Collectors.toList());
            AlloyCustomHelper.setAlloyingRecipes(alloyingRecipes);

            List<ElementRecipe> elementRecipes = world.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ELEMENT.get())
                    .stream().map(RecipeHolder::value).collect(Collectors.toList());
            AlloyCustomHelper.setElementRecipes(elementRecipes);

            List<AlloyCraftingRecipe> alloyCraftingRecipes = world.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING).stream()
                    .map(RecipeHolder::value)
                    .filter((iCraftingRecipe -> iCraftingRecipe instanceof AlloyCraftingRecipe))
                    .map(AlloyCraftingRecipe.class::cast)
                    .collect(Collectors.toList());
            AlloyCustomHelper.setCraftingRecipes(alloyCraftingRecipes);

            for (Item item : RankineLists.ALLOY_TOOLS) {
                AlloyCustomHelper.getItemstackForSearching(item,world);
            }

        }
    }
}
