package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineBlocks;
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
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IBeehiveOvenRecipe implements IRecipe<IInventory> {
    public static final IBeehiveOvenRecipe.Serializer SERIALIZER = new IBeehiveOvenRecipe.Serializer();
    public static final IRecipeType<IBeehiveOvenRecipe> RECIPE_TYPE = new IRecipeType<IBeehiveOvenRecipe>() {

    };
    protected Ingredient ingredient;
    protected Ingredient alloy;
    protected ItemStack result;
    protected final ResourceLocation id;
    protected float experience;
    protected int cookTime;
    AbstractMap.SimpleEntry<Float, Float> primary;
    AbstractMap.SimpleEntry<Float, Float> secondary;
    float req;
    AbstractMap.SimpleEntry<Float, Float> other;
    private final NonNullList<Ingredient> inputs;

    public IBeehiveOvenRecipe(ResourceLocation id, ItemStack output, Ingredient input) {
        this.id = id;
        this.ingredient = input;
        this.result = output;
        this.inputs = NonNullList.create();
        this.inputs.addAll(Collections.singletonList(ingredient));
    }

    @Override
    public boolean matches(IInventory inv, @Nonnull World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull IInventory inv) {
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

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return RECIPE_TYPE;
    }

    @Nonnull
    public String getGroup() {
        return "beeoven";
    }

    @Nonnull
    public ItemStack getIcon() {
        return new ItemStack(RankineBlocks.BEEHIVE_OVEN_PIT.get());
    }

    @Nonnull
    public List<ItemStack> getOutputs() {
        return Collections.singletonList(result);
    }


    public Ingredient getInput() {
        return ingredient;
    }


    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IBeehiveOvenRecipe> {

        @Override
        public IBeehiveOvenRecipe read(ResourceLocation recipeId, JsonObject json) {
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            NonNullList<Ingredient> list = NonNullList.create();
            list.addAll(Arrays.asList(ingredient));
            return new IBeehiveOvenRecipe(recipeId, result, ingredient);
        }

        @Nullable
        @Override
        public IBeehiveOvenRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

            ItemStack output = buffer.readItemStack();

            Ingredient[] inputs = new Ingredient[buffer.readVarInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.read(buffer);
            }
            NonNullList<Ingredient> list = NonNullList.create();
            list.addAll(Arrays.asList(inputs));
            return new IBeehiveOvenRecipe(recipeId, output, inputs[0]);
        }

        @Override
        public void write(PacketBuffer buffer, IBeehiveOvenRecipe recipe) {

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