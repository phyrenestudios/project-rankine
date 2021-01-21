package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Map;

public class AlloyRecipe implements IRecipe<AlloyFurnaceTile> {

    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;
    private final boolean advanced;

    public AlloyRecipe(ResourceLocation idIn, String groupIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn, boolean advanced) {
        this.id = idIn;
        this.group = groupIn;
        this.recipeItems = recipeItemsIn;
        this.recipeOutput = recipeOutputIn;
        this.advanced = advanced;
    }

    @Override
    public boolean matches(AlloyFurnaceTile inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(AlloyFurnaceTile inv) {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }


}
