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
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.client.event.RecipesUpdatedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipesUpdatedHandler {
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        ClientLevel world = Minecraft.getInstance().level;
        if (world != null) {
            List<AlloyingRecipe> alloyingRecipes = new ArrayList<>(world.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ALLOYING));
            AlloyCustomHelper.setAlloyingRecipes(alloyingRecipes);

            List<ElementRecipe> elementRecipes = new ArrayList<>(world.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ELEMENT));
            AlloyCustomHelper.setElementRecipes(elementRecipes);

            List<AlloyCraftingRecipe> alloyCraftingRecipes = world.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING).stream()
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
