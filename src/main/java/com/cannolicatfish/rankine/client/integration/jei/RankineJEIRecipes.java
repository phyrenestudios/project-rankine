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
        ClientWorld world = Minecraft.getInstance().level;
        checkNotNull(world, "minecraft world");
        this.recipeManager = world.getRecipeManager();
    }

    public List<CrushingRecipe> getCrushingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.CRUSHING);
    }

    public List<AlloyingRecipe> getAlloyingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ALLOYING);
    }

    public List<AlloyingRecipe> getAlloyFurnaceRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ALLOYING).stream().filter(recipe -> (recipe.getTier() & 1) != 0 && recipe.getTier() != -1).collect(Collectors.toList());
    }

    public List<FusionFurnaceRecipe> getFusionFurnaceRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.FUSION_FURNACE);
    }

    public List<AlloyingRecipe> getInductionFurnaceRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ALLOYING).stream().filter(recipe -> (recipe.getTier() & 2) != 0 && recipe.getTier() != -1).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getIntrusiveGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.INTRUSIVE_IGNEOUS))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getExtrusiveGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getSedimentaryGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.SEDIMENTARY))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getMetamorphicGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.METAMORPHIC))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getVolcanicGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.VOLCANIC))).collect(Collectors.toList());
    }

    public List<TreetappingRecipe> getTreetappingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.TREETAPPING);
    }

    public List<StrippingRecipe> getStrippingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.STRIPPING);
    }

    public List<AirDistillationRecipe> getAirDistillationRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.AIR_DISTILLATION);
    }

    public List<BeehiveOvenRecipe> getBeehiveRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.BEEHIVE);
    }

    public List<SluicingRecipe> getSluicingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.SLUICING);
    }

    public List<MixingRecipe> getMixingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.MIXING);
    }

    public List<ElementRecipe> getElementRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ELEMENT);
    }

    public List<CrucibleRecipe> getCrucibleRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.CRUCIBLE);
    }

    public List<EvaporationRecipe> getEvaporationRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.EVAPORATION);
    }

    public static <T> void checkNotNull(@Nullable T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " must not be null.");
        }
    }
}
