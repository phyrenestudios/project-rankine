package com.cannolicatfish.rankine.client.integration.jei;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;

import javax.annotation.Nullable;
import java.util.List;

public class RankineJEIRecipes {

    private final RecipeManager recipeManager;

    public RankineJEIRecipes() {
        ClientWorld world = Minecraft.getInstance().world;
        checkNotNull(world, "minecraft world");
        this.recipeManager = world.getRecipeManager();
    }

    public List<CrushingRecipe> getCrushingRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.CRUSHING);
    }

    public List<AlloyingRecipe> getAlloyingRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.ALLOYING);
    }

    public static <T> void checkNotNull(@Nullable T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " must not be null.");
        }
    }
}
