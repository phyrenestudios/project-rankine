package com.cannolicatfish.rankine.client.integration.jei;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.*;
import com.cannolicatfish.rankine.util.RockGeneratorUtils;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<AlloyingRecipe> getAlloyFurnaceRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.ALLOYING).stream().filter(recipe -> (recipe.getTier() & 1) != 0).collect(Collectors.toList());
    }

    public List<AlloyingRecipe> getInductionFurnaceRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.ALLOYING).stream().filter(recipe -> (recipe.getTier() & 2) != 0).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getIntrusiveGeneratorRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.INTRUSIVE_IGNEOUS))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getExtrusiveGeneratorRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getSedimentaryGeneratorRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.SEDIMENTARY))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getMetamorphicGeneratorRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.METAMORPHIC))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getVolcanicGeneratorRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.VOLCANIC))).collect(Collectors.toList());
    }

    public List<BeehiveOvenRecipe> getBeehiveRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.BEEHIVE);
    }

    public List<SluicingRecipe> getSluicingRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.SLUICING);
    }

    public List<ElementRecipe> getElementRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.ELEMENT);
    }

    public List<CrucibleRecipe> getCrucibleRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.CRUCIBLE);
    }

    public List<EvaporationRecipe> getEvaporationRecipes() {
        return recipeManager.getRecipesForType(RankineRecipeTypes.EVAPORATION);
    }

    public static <T> void checkNotNull(@Nullable T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " must not be null.");
        }
    }
}
