package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ISluicingRecipe implements IRecipe<IInventory> {
    public static final ISluicingRecipe.Serializer SERIALIZER = new ISluicingRecipe.Serializer();
    public static final IRecipeType<ISluicingRecipe> RECIPE_TYPE = new IRecipeType<ISluicingRecipe>() {

    };
    protected Ingredient ingredient;
    protected List<ItemStack> result;
    protected ItemStack secondary;
    protected final ResourceLocation id;
    protected float chance;
    protected float experience;
    protected List<Float> weights;
    protected int cookTime;
    private final NonNullList<Ingredient> inputs;

    public ISluicingRecipe(ResourceLocation id, List<ItemStack> outputs, Ingredient input, List<Float> weights) {
        this.id = id;

        this.ingredient = input;
        this.inputs = NonNullList.create();
        this.inputs.addAll(Collections.singletonList(ingredient));
        this.result = outputs;
        this.weights = weights;
    }
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.get(0).copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result.get(0);
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }


    public ItemStack getSecondaryOutput() {
        return secondary;
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
        return "sluicing";
    }

    public ItemStack getIcon() {
        return new ItemStack(ModItems.STEEL_GOLD_PAN);
    }


    public List<ItemStack> getOutputs() {
        return result;
    }

    public List<Float> getWeights() {
        return weights;
    }

    public List<ItemStack> getOutputs(List<ItemStack> inputs) {
        return getOutputs();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ISluicingRecipe> {

        @Override
        public ISluicingRecipe read(ResourceLocation recipeId, JsonObject json) {
            List<ItemStack> outputs = new ArrayList<>();
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            ItemStack secondary = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "secondary"));
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            return new ISluicingRecipe(recipeId,outputs,ingredient,Collections.emptyList());
        }

        @Nullable
        @Override
        public ISluicingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

            Ingredient input = Ingredient.read(buffer);

            ItemStack[] outputs = new ItemStack[buffer.readVarInt()];
            for (int i = 0; i < outputs.length; i++) {
                outputs[i] = buffer.readItemStack();
            }

            return new ISluicingRecipe(recipeId,Collections.emptyList(),input, Collections.emptyList());
        }

        @Override
        public void write(PacketBuffer buffer, ISluicingRecipe recipe) {

            buffer.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.write(buffer);
            }
            buffer.writeVarInt(recipe.getOutputs().size());
            for (ItemStack output : recipe.getOutputs()) {
                buffer.writeItemStack(output, false);
            }
        }
    }
}
