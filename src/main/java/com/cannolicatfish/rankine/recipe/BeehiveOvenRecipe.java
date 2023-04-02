package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class BeehiveOvenRecipe implements Recipe<Container> {

    protected Ingredient ingredient;
    protected ItemStack result;
    protected final ResourceLocation id;
    protected final int minCookTime;
    protected final int maxCookTime;

    public BeehiveOvenRecipe(ResourceLocation id, Ingredient input, ItemStack output, int minCookTimeIn, int maxCookTimeIn) {
        this.id = id;
        this.ingredient = input;
        this.result = output;
        this.minCookTime = minCookTimeIn;
        this.maxCookTime = maxCookTimeIn;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(1,ingredient);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getMinCookTime() {
        return minCookTime;
    }

    public int getMaxCookTime() {
        return maxCookTime;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.BEEHIVE_OVEN_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.BEEHIVE;
    }

    public static ItemStack deserializeBlock(JsonObject object) {
        String s = GsonHelper.getAsString(object, "item");

        Block block = Registry.BLOCK.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonParseException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return BlockRecipeHelper.getBlockItemStack(object);
        }
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<BeehiveOvenRecipe> {

        @Override
        public BeehiveOvenRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int min = json.has("minCookTime") ? json.get("minCookTime").getAsInt() : 1600;
            int max = json.has("maxCookTime") ? json.get("maxCookTime").getAsInt() : 8000;
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));

            if (!json.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack result;
            if (json.get("result").isJsonObject()) {
                result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            } else {
                String s1 = GsonHelper.getAsString(json, "result");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                result = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                    return new IllegalStateException("Item: " + s1 + " does not exist");
                }));
            }
            return new BeehiveOvenRecipe(recipeId,ingredient,result,min,max);
        }

        @Nullable
        @Override
        public BeehiveOvenRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int min = buffer.readInt();
            int max = buffer.readInt();
            Ingredient input = Ingredient.fromNetwork(buffer);

            ItemStack output = buffer.readItem();

            return new BeehiveOvenRecipe(recipeId,input,output,min,max);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BeehiveOvenRecipe recipe) {
            buffer.writeInt(recipe.getMinCookTime());
            buffer.writeInt(recipe.getMaxCookTime());
            recipe.getIngredient().toNetwork(buffer);
            buffer.writeItem(recipe.getResultItem());
        }
    }

}
