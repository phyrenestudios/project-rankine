package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FusionFurnaceRecipe implements IRecipe<IInventory> {

    //public static final FusionFurnaceRecipe.Serializer SERIALIZER = new FusionFurnaceRecipe.Serializer();
    protected int cookTime;
    protected FluidStack fluidIn;
    protected ItemStack gasIn;
    protected Ingredient ingredient1;
    protected Ingredient ingredient2;
    protected FluidStack fluidOut;
    protected ItemStack gasOut;
    protected ItemStack result1;
    protected ItemStack result2;

    protected final ResourceLocation id;
    public static final FusionFurnaceRecipe.Serializer SERIALIZER = new FusionFurnaceRecipe.Serializer();

    public FusionFurnaceRecipe(ResourceLocation id, FluidStack fluidIn, ItemStack gasIn, Ingredient input1, Ingredient input2, FluidStack fluidOut, ItemStack gasOut, ItemStack output1,ItemStack output2,int cookTimeIn) {
        this.id = id;
        this.ingredient1 = input1;
        this.ingredient2 = input2;
        this.fluidIn = fluidIn;
        this.gasIn = gasIn;
        this.fluidOut = fluidOut;
        this.gasOut = gasOut;
        this.result1 = output1;
        this.result2 = output2;
        this.cookTime = cookTimeIn;
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
        return false;
    }

    public boolean matchesRecipe(IInventory inv, FluidTank tankIn, FluidTank tankOut, World worldIn) {
        boolean fluidInCheck = this.fluidIn.isEmpty() || tankIn.getFluid().containsFluid(this.fluidIn);
        boolean fluidOutCheck = this.fluidIn.isEmpty() || tankOut.isFluidValid(this.fluidOut);
        return (this.ingredient1.test(inv.getStackInSlot(0)) || this.ingredient1.test(inv.getStackInSlot(1)))
                && (this.ingredient2.test(inv.getStackInSlot(1)) || this.ingredient2.test(inv.getStackInSlot(0)))
                && inv.getStackInSlot(3).isItemEqual(this.gasIn) && fluidInCheck && fluidOutCheck;
    }

    public Ingredient getIngredient1() {
        return ingredient1;
    }

    public Ingredient getIngredient2() {
        return ingredient2;
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
        return NonNullList.from(Ingredient.EMPTY,this.ingredient1,this.ingredient2,Ingredient.fromStacks(this.gasIn));
    }

    public NonNullList<ItemStack> getRecipeOutputs() {
        return NonNullList.from(ItemStack.EMPTY,this.result1,this.result2,this.gasOut);
    }

    public NonNullList<ItemStack> getRecipeOutputsJEI() {
        if (this.getRecipeOutputs().contains(ItemStack.EMPTY))
        {
            NonNullList<ItemStack> newJeiCopy = this.getRecipeOutputs();
            newJeiCopy.replaceAll(stack -> {
                if (stack == ItemStack.EMPTY)
                {
                    return new ItemStack(RankineItems.ELEMENT.get());
                } else {
                    return stack;
                }
            });
            return newJeiCopy;
        }
        return this.getRecipeOutputs();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    public ItemStack getGasIn() {
        return gasIn;
    }

    public ItemStack getGasOut() {
        return gasOut;
    }

    public FluidStack getFluidOut() {
        return fluidOut;
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
        return RankineRecipeTypes.FUSION_FURNACE;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = JSONUtils.getString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    public List<ItemStack> getResults() {
        return Arrays.asList(this.result1,this.result2,this.gasOut);
    }

    public FluidStack getFluidIn() {
        return fluidIn;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<FusionFurnaceRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "fusion_furnace");
        public FusionFurnaceRecipe read(ResourceLocation recipeId, JsonObject json) {
            int w = json.has("cookTime") ? json.get("cookTime").getAsInt() : 400;
            FluidStack fIn = json.has("fluidInput") ? FluidHelper.getFluidStack(JSONUtils.getJsonObject(json, "fluidInput")) : FluidStack.EMPTY;
            ItemStack gIn = json.has("gasInput") ? deserializeItem(JSONUtils.getJsonObject(json,"gasInput")) : ItemStack.EMPTY;
            Ingredient in1 = json.has("input1") ? Ingredient.deserialize(JSONUtils.getJsonObject(json, "input1")) : Ingredient.EMPTY;
            Ingredient in2 = json.has("input2") ? Ingredient.deserialize(JSONUtils.getJsonObject(json, "input2")) : Ingredient.EMPTY;
            ItemStack output1 = json.has("result1") ? deserializeItem(JSONUtils.getJsonObject(json,"result1")) : ItemStack.EMPTY;
            ItemStack output2 = json.has("result2") ? deserializeItem(JSONUtils.getJsonObject(json,"result2")) : ItemStack.EMPTY;
            ItemStack gOut = json.has("gasOutput") ? deserializeItem(JSONUtils.getJsonObject(json,"gasOutput")) : ItemStack.EMPTY;
            FluidStack fOut = json.has("fluidOutput") ? FluidHelper.getFluidStack(JSONUtils.getJsonObject(json, "fluidOutput")) : FluidStack.EMPTY;

            return new FusionFurnaceRecipe(recipeId,fIn,gIn,in1,in2,fOut,gOut,output1,output2,w);
        }

        public FusionFurnaceRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int w = buffer.readInt();
            FluidStack fIn = buffer.readFluidStack();
            ItemStack gIn = buffer.readItemStack();
            Ingredient in1 = Ingredient.read(buffer);
            Ingredient in2 = Ingredient.read(buffer);
            ItemStack output1 = buffer.readItemStack();
            ItemStack output2 = buffer.readItemStack();
            ItemStack gOut = buffer.readItemStack();
            FluidStack fOut = buffer.readFluidStack();
            return new FusionFurnaceRecipe(recipeId,fIn,gIn,in1,in2,fOut,gOut,output1,output2,w);
        }

        public void write(PacketBuffer buffer, FusionFurnaceRecipe recipe) {
            buffer.writeInt(recipe.cookTime);
            buffer.writeFluidStack(recipe.fluidIn);
            buffer.writeItemStack(recipe.gasIn);
            recipe.ingredient1.write(buffer);
            recipe.ingredient2.write(buffer);
            buffer.writeItemStack(recipe.result1);
            buffer.writeItemStack(recipe.result2);
            buffer.writeItemStack(recipe.gasOut);
            buffer.writeFluidStack(recipe.fluidOut);
        }
    }

}

