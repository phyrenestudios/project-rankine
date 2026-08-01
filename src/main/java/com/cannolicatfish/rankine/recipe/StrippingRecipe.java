package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.RankineMapCodec;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
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

public class StrippingRecipe implements Recipe<RecipeInput> {

    protected Ingredient ingredient;
    protected ItemStack result;
    protected float chance;

    public StrippingRecipe(Ingredient input, ItemStack output, float chance) {
        this.ingredient = input;
        this.result = output;
        this.chance = chance;
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

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public ItemStack getResult() {
        return this.result.copy();
    }

    public float getChance() {
        return chance;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.AXE_STRIPPING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.STRIPPING.get();
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = GsonHelper.getAsString(object, "item");
        BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    public static class Serializer implements RecipeSerializer<StrippingRecipe> {

        public static final MapCodec<StrippingRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.fixedKeys("chance","input","result"));

        public static final StreamCodec<RegistryFriendlyByteBuf, StrippingRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<StrippingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, StrippingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static StrippingRecipe fromJson(JsonObject json) {
            float w = json.has("chance") ? json.get("chance").getAsFloat() : 0.0f;
            Ingredient ingredient = AlloyIngredientHelper.ingredientFromJson(json.get("input"));
            ItemStack result = deserializeItem(GsonHelper.getAsJsonObject(json, "result"));
            return new StrippingRecipe(ingredient,result,w);
        }

        public static void toJson(StrippingRecipe recipe, JsonObject json) {
            json.addProperty("chance", recipe.getChance());
            json.add("input", AlloyIngredientHelper.ingredientToJson(recipe.getIngredient()));
            json.add("result", AlloyIngredientHelper.itemStackToJson(recipe.result));
        }

        public static StrippingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            float chance = buffer.readFloat();
            return new StrippingRecipe(input,output,chance);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, StrippingRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeFloat(recipe.chance);
        }
    }

}
