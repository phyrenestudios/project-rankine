package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeehiveOvenRecipe implements IRecipe<IInventory> {

    public static final BeehiveOvenRecipe.Serializer SERIALIZER = new BeehiveOvenRecipe.Serializer();
    protected Ingredient ingredient;
    protected ItemStack result;
    protected final ResourceLocation id;

    public BeehiveOvenRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        this.id = id;
        this.ingredient = input;
        this.result = output;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(IInventory inv) {
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
    public ItemStack getResultItem() {
        return result;
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
        return RankineRecipeTypes.BEEHIVE;
    }

    public static ItemStack deserializeBlock(JsonObject object) {
        String s = JSONUtils.getAsString(object, "block");

        Block block = Registry.BLOCK.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonParseException("Unknown block '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return BlockRecipeHelper.getBlockItemStack(object);
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BeehiveOvenRecipe> {

        @Override
        public BeehiveOvenRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "input"));
            ItemStack result = deserializeBlock(JSONUtils.getAsJsonObject(json, "result"));
            return new BeehiveOvenRecipe(recipeId,ingredient,result);
        }

        @Nullable
        @Override
        public BeehiveOvenRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {

            Ingredient input = Ingredient.fromNetwork(buffer);

            ItemStack output = buffer.readItem();

            return new BeehiveOvenRecipe(recipeId,input,output);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, BeehiveOvenRecipe recipe) {
            recipe.getIngredient().toNetwork(buffer);
            buffer.writeItem(recipe.getResultItem());
        }
    }

}
