package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class BeehiveOvenRecipe implements Recipe<RecipeInput> {

    protected Ingredient ingredient;
    protected ItemStack result;
    protected final int minCookTime;
    protected final int maxCookTime;

    public BeehiveOvenRecipe(Ingredient input, ItemStack output, int minCookTimeIn, int maxCookTimeIn) {
        this.ingredient = input;
        this.result = output;
        this.minCookTime = minCookTimeIn;
        this.maxCookTime = maxCookTimeIn;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public boolean matches(RecipeInput inv, Level worldIn) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeInput inv, HolderLookup.Provider provider) {
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
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.BEEHIVE_OVEN_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.BEEHIVE.get();
    }

    public static ItemStack deserializeBlock(JsonObject object) {
        String s = GsonHelper.getAsString(object, "item");

        BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(s)).orElseThrow(() -> {
            return new JsonParseException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return BlockRecipeHelper.getBlockItemStack(object);
        }
    }

    public static class Serializer implements RecipeSerializer<BeehiveOvenRecipe> {

        public static final MapCodec<BeehiveOvenRecipe> CODEC = com.cannolicatfish.rankine.recipe.helper.RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                com.cannolicatfish.rankine.recipe.helper.RankineMapCodec.fixedKeys("minCookTime","maxCookTime","input","result"));

        public static final StreamCodec<RegistryFriendlyByteBuf, BeehiveOvenRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<BeehiveOvenRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BeehiveOvenRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static BeehiveOvenRecipe fromJson(JsonObject json) {
            int min = json.has("minCookTime") ? json.get("minCookTime").getAsInt() : 1600;
            int max = json.has("maxCookTime") ? json.get("maxCookTime").getAsInt() : 8000;
            Ingredient ingredient = AlloyIngredientHelper.ingredientFromJson(json.get("input"));

            if (!json.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack result;
            if (json.get("result").isJsonObject()) {
                result = AlloyIngredientHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);
            } else {
                String s1 = GsonHelper.getAsString(json, "result");
                ResourceLocation resourcelocation = ResourceLocation.parse(s1);
                result = new ItemStack(BuiltInRegistries.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                    return new IllegalStateException("Item: " + s1 + " does not exist");
                }));
            }
            return new BeehiveOvenRecipe(ingredient,result,min,max);
        }

        public static void toJson(BeehiveOvenRecipe recipe, JsonObject json) {
            json.addProperty("minCookTime", recipe.getMinCookTime());
            json.addProperty("maxCookTime", recipe.getMaxCookTime());
            json.add("input", AlloyIngredientHelper.ingredientToJson(recipe.getIngredient()));
            json.add("result", AlloyIngredientHelper.itemStackToJson(recipe.result));
        }

        public static BeehiveOvenRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int min = buffer.readInt();
            int max = buffer.readInt();
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new BeehiveOvenRecipe(input,output,min,max);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, BeehiveOvenRecipe recipe) {
            buffer.writeInt(recipe.getMinCookTime());
            buffer.writeInt(recipe.getMaxCookTime());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.getResultItem());
        }
    }

}
