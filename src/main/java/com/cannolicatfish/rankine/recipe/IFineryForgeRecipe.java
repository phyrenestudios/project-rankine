package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IFineryForgeRecipe implements IRecipe<IInventory> {
    public static final IFineryForgeRecipe.Serializer SERIALIZER = new IFineryForgeRecipe.Serializer();
    public static final IRecipeType<IFineryForgeRecipe> RECIPE_TYPE = new IRecipeType<IFineryForgeRecipe>() {

    };
    protected Ingredient ingredient;
    protected ItemStack intermediate;
    protected ItemStack result;
    protected ItemStack secondary;
    protected final ResourceLocation id;
    protected float chance;
    protected float experience;
    protected int cookTime;
    private final NonNullList<Ingredient> inputs;

    public IFineryForgeRecipe(ResourceLocation id, ItemStack[] outputs, Ingredient input, float chance) {
        this.id = id;

        this.ingredient = input;
        this.inputs = NonNullList.create();
        this.inputs.addAll(Collections.singletonList(ingredient));
        this.intermediate = outputs[0];
        this.result = outputs[1];
        this.secondary = outputs[2];
    }
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    public ItemStack getSecondaryOutput() {
        return secondary;
    }

    public ItemStack getRecipeIntermediate() {
        return intermediate;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RECIPE_TYPE;
    }

    public String getGroup() {
        return "finery";
    }

    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.FINERY_FORGE);
    }


    public List<ItemStack> getOutputs() {
        return Arrays.asList(intermediate,result,secondary);
    }

    public List<ItemStack> getOutputs(List<ItemStack> inputs) {
        return getOutputs();
    }

    public float getSecondaryChance() {
        return chance;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IFineryForgeRecipe> {

        @Override
        public IFineryForgeRecipe read(ResourceLocation recipeId, JsonObject json) {

            ItemStack[] outputs = new ItemStack[3];
            ItemStack intermediate = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "intermediate"));
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            ItemStack secondary = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "secondary"));
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            float chance = JSONUtils.getFloat(json,"chance",1.0f);
            outputs[0] = intermediate;
            outputs[1] = result;
            outputs[2] = secondary;
            return new IFineryForgeRecipe(recipeId,outputs,ingredient,chance);
        }

        @Nullable
        @Override
        public IFineryForgeRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

            Ingredient input = Ingredient.read(buffer);

            ItemStack[] outputs = new ItemStack[buffer.readVarInt()];
            for (int i = 0; i < outputs.length; i++) {
                outputs[i] = buffer.readItemStack();
            }

            float chance = buffer.readFloat();
            return new IFineryForgeRecipe(recipeId,outputs,input,chance);
        }

        @Override
        public void write(PacketBuffer buffer, IFineryForgeRecipe recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.write(buffer);
            }
            buffer.writeVarInt(recipe.getOutputs().size());
            for (ItemStack output : recipe.getOutputs()) {
                buffer.writeItemStack(output, false);
            }
            buffer.writeFloat(recipe.chance);
        }
    }
}
