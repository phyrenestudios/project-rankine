package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class StrippingRecipe implements IRecipe<IInventory> {

    public static final StrippingRecipe.Serializer SERIALIZER = new StrippingRecipe.Serializer();
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
    public boolean isDynamic() {
        return true;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
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
    public ItemStack getRecipeOutput() {
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
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RankineRecipeTypes.STRIPPING;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = JSONUtils.getString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            //int i = JSONUtils.getInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<StrippingRecipe> {

        @Override
        public StrippingRecipe read(ResourceLocation recipeId, JsonObject json) {
            float w = json.has("chance") ? json.get("chance").getAsFloat() : 0.0f;
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
            ItemStack result = deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new StrippingRecipe(recipeId,ingredient,result,w);
        }

        @Nullable
        @Override
        public StrippingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

            Ingredient input = Ingredient.read(buffer);

            ItemStack output = buffer.readItemStack();

            int chance = buffer.readInt();
            return new StrippingRecipe(recipeId,input,output,chance);
        }

        @Override
        public void write(PacketBuffer buffer, StrippingRecipe recipe) {
            recipe.getIngredient().write(buffer);
            buffer.writeItemStack(recipe.getResult());
            buffer.writeFloat(recipe.chance);
        }
    }

}
