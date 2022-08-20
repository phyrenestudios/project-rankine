package com.cannolicatfish.rankine.data.builders;

import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class BeehiveOvenRecipeBuilder {
    private final Ingredient input;
    private final Item result;
    private final int minCookTime;
    private final int maxCookTime;
    private final RecipeSerializer<? extends BeehiveOvenRecipe> serializer = BeehiveOvenRecipe.SERIALIZER;

    public BeehiveOvenRecipeBuilder(Ingredient inputIn,ItemLike resultIn, int minCookTimeIn, int maxCookTimeIn) {
        this.input = inputIn;
        this.result = resultIn.asItem();
        this.minCookTime = minCookTimeIn;
        this.maxCookTime = maxCookTimeIn;
    }

    public static BeehiveOvenRecipeBuilder beehiveOvenRecipe(Ingredient inputIn,ItemLike resultIn, int minCookTimeIn, int maxCookTimeIn) {
        return new BeehiveOvenRecipeBuilder(inputIn, resultIn, minCookTimeIn, maxCookTimeIn);
    }

    public static BeehiveOvenRecipeBuilder beehiveOvenRecipe(Ingredient inputIn,ItemLike resultIn) {
        return beehiveOvenRecipe(inputIn, resultIn, 300, 600);
    }


    public Item getResult() {
        return this.result;
    }

    public void save(Consumer<FinishedRecipe> p_126263_, ResourceLocation p_126264_) {
        p_126263_.accept(new BeehiveOvenRecipeBuilder.Result(p_126264_, this.input, this.result, this.minCookTime, this.maxCookTime, this.serializer));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final Item result;
        private final int minCookTime;
        private final int maxCookTime;
        private final RecipeSerializer<? extends BeehiveOvenRecipe> serializer;

        public Result(ResourceLocation idIn, Ingredient inputIn, Item resultIn, int minCookTimeIn, int maxCookTimeIn, RecipeSerializer<? extends BeehiveOvenRecipe> serializerIn) {
            this.id = idIn;
            this.ingredient = inputIn;
            this.result = resultIn;
            this.minCookTime = minCookTimeIn;
            this.maxCookTime = maxCookTimeIn;
            this.serializer = serializerIn;
        }

        public void serializeRecipeData(JsonObject p_126297_) {
            p_126297_.add("input", this.ingredient.toJson());
            p_126297_.addProperty("result", Registry.ITEM.getKey(this.result).toString());
            p_126297_.addProperty("minCookTime", this.minCookTime);
            p_126297_.addProperty("maxCookTime", this.maxCookTime);
        }

        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

}
