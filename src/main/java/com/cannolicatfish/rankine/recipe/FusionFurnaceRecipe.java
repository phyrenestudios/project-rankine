package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class FusionFurnaceRecipe implements IRecipe<IInventory> {

    //public static final FusionFurnaceRecipe.Serializer SERIALIZER = new FusionFurnaceRecipe.Serializer();
    protected FluidStack fluidIn;
    protected ItemStack gasIn;
    protected Ingredient ingredient1;
    protected Ingredient ingredient2;
    protected FluidStack fluidOut;
    protected ItemStack gasOut;
    protected ItemStack result;

    protected final ResourceLocation id;

    public FusionFurnaceRecipe(ResourceLocation id, FluidStack fluidIn, ItemStack gasIn, Ingredient input1, Ingredient input2, FluidStack fluidOut, ItemStack gasOut, ItemStack output) {
        this.id = id;
        this.ingredient1 = input1;
        this.ingredient2 = input2;
        this.result = output;
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
        return (this.ingredient1.test(inv.getStackInSlot(0)) || this.ingredient1.test(inv.getStackInSlot(1)))
                && (this.ingredient2.test(inv.getStackInSlot(1)) || this.ingredient2.test(inv.getStackInSlot(0)));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.from(Ingredient.EMPTY,this.ingredient1,this.ingredient2);
    }


    @Override
    public ItemStack getRecipeOutput() {
        return result;
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
        return RankineRecipeTypes.FUSION;
    }


}

