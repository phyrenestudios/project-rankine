package com.cannolicatfish.rankine.events.handlers.client;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.client.event.RecipesUpdatedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipesUpdateHandler {
    public static void onRecipesUpdated( RecipesUpdatedEvent event) {
        ClientWorld world = Minecraft.getInstance().world;
        if (world != null) {
            List<AlloyingRecipe> alloyingRecipes = new ArrayList<>(world.getRecipeManager().getRecipesForType(RankineRecipeTypes.ALLOYING));
            AlloyCustomHelper.setAlloyingRecipes(alloyingRecipes);

            List<ElementRecipe> elementRecipes = new ArrayList<>(world.getRecipeManager().getRecipesForType(RankineRecipeTypes.ELEMENT));
            AlloyCustomHelper.setElementRecipes(elementRecipes);

            List<AlloyCraftingRecipe> alloyCraftingRecipes = world.getRecipeManager().getRecipesForType(IRecipeType.CRAFTING).stream()
                                                                  .filter((iCraftingRecipe -> iCraftingRecipe instanceof AlloyCraftingRecipe))
                                                                  .map(AlloyCraftingRecipe.class::cast)
                                                                  .collect(Collectors.toList());
            AlloyCustomHelper.setCraftingRecipes(alloyCraftingRecipes);
        }
    }
}
