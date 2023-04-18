package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class StrippingRecipe implements Recipe<Container> {

    protected Ingredient ingredient;
    protected ItemStack result;
    protected final ResourceLocation id;
    protected float chance;

    public StrippingRecipe(ResourceLocation id, Ingredient input, ItemStack output, float chance) {
        this.id = id;
        this.ingredient = input;
        this.result = output;
        this.chance = chance;
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
    public ItemStack assemble(Container inv, RegistryAccess registryAccess) {
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
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    public ItemStack getResult() {
        return this.result.copy();
    }

    public float getChance() {
        return chance;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.AXE_STRIPPING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.AXE_STRIPPING.get();
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = GsonHelper.getAsString(object, "item");
        Item item = BuiltInRegistries.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            //int i = JSONUtils.getInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    public static class Serializer implements RecipeSerializer<StrippingRecipe> {

        @Override
        public StrippingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            float w = json.has("chance") ? json.get("chance").getAsFloat() : 0.0f;
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
            ItemStack result = deserializeItem(GsonHelper.getAsJsonObject(json, "result"));
            return new StrippingRecipe(recipeId,ingredient,result,w);
        }

        @Nullable
        @Override
        public StrippingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

            Ingredient input = Ingredient.fromNetwork(buffer);

            ItemStack output = buffer.readItem();

            int chance = buffer.readInt();
            return new StrippingRecipe(recipeId,input,output,chance);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, StrippingRecipe recipe) {
            recipe.getIngredient().toNetwork(buffer);
            buffer.writeItem(recipe.getResult());
            buffer.writeFloat(recipe.chance);
        }
    }

}
