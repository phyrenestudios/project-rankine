package com.cannolicatfish.rankine.data.builders;

import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

/**
 * Datagen builder for {@code rankine:beehive_oven} recipes. 1.21.1: emits a real
 * {@link BeehiveOvenRecipe} through {@link RecipeOutput}; there is no advancement for these.
 */
public class BeehiveOvenRecipeBuilder {
    private final Ingredient input;
    private final Item result;
    private final int minCookTime;
    private final int maxCookTime;

    public BeehiveOvenRecipeBuilder(Ingredient inputIn, ItemLike resultIn, int minCookTimeIn, int maxCookTimeIn) {
        this.input = inputIn;
        this.result = resultIn.asItem();
        this.minCookTime = minCookTimeIn;
        this.maxCookTime = maxCookTimeIn;
    }

    public static BeehiveOvenRecipeBuilder beehiveOvenRecipe(Ingredient inputIn, ItemLike resultIn, int minCookTimeIn, int maxCookTimeIn) {
        return new BeehiveOvenRecipeBuilder(inputIn, resultIn, minCookTimeIn, maxCookTimeIn);
    }

    public static BeehiveOvenRecipeBuilder beehiveOvenRecipe(Ingredient inputIn, ItemLike resultIn) {
        return beehiveOvenRecipe(inputIn, resultIn, 300, 600);
    }

    public Item getResult() {
        return this.result;
    }

    public void save(RecipeOutput output, ResourceLocation id) {
        output.accept(id, new BeehiveOvenRecipe(this.input, new ItemStack(this.result), this.minCookTime, this.maxCookTime), null);
    }

    public void save(RecipeOutput output, String id) {
        this.save(output, ResourceLocation.parse(id));
    }
}
