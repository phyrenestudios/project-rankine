package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
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

public class TreetappingRecipe implements IRecipe<IInventory> {

    public static final TreetappingRecipe.Serializer SERIALIZER = new TreetappingRecipe.Serializer();
    protected Ingredient ingredient;
    protected FluidStack result;
    protected final ResourceLocation id;
    protected int tapTime;

    public TreetappingRecipe(ResourceLocation id, Ingredient input, FluidStack output, int tapTimeIn) {
        this.id = id;
        this.ingredient = input;
        this.result = output;
        this.tapTime = tapTimeIn;
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
        return null;
    }

    public FluidStack getResult() {
        return this.result;
    }

    public int getCookTime() {
        return tapTime;
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
        return RankineRecipeTypes.TREETAPPING;
    }

    public static ItemStack deserializeBlock(JsonObject object) {
        String s = JSONUtils.getString(object, "block");

        Block block = Registry.BLOCK.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonParseException("Unknown block '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return BlockRecipeHelper.getBlockItemStack(object);
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TreetappingRecipe> {

        @Override
        public TreetappingRecipe read(ResourceLocation recipeId, JsonObject json) {
            int w = json.has("tapTime") ? json.get("tapTime").getAsInt() : 400;
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
            FluidStack result = FluidHelper.getFluidStack(JSONUtils.getJsonObject(json, "result"));
            return new TreetappingRecipe(recipeId,ingredient,result,w);
        }

        @Nullable
        @Override
        public TreetappingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

            Ingredient input = Ingredient.read(buffer);

            FluidStack output = buffer.readFluidStack();

            int time = buffer.readInt();
            return new TreetappingRecipe(recipeId,input,output,time);
        }

        @Override
        public void write(PacketBuffer buffer, TreetappingRecipe recipe) {
            recipe.getIngredient().write(buffer);
            buffer.writeFluidStack(recipe.getResult());
            buffer.writeInt(recipe.getCookTime());
        }
    }

}
