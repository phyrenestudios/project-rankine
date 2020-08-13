package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IAlloyRecipe implements IRecipe<IInventory> {
    public static final IAlloyRecipe.Serializer SERIALIZER = new IAlloyRecipe.Serializer();
    public static final IRecipeType<IAlloyRecipe> RECIPE_TYPE = new IRecipeType<IAlloyRecipe>() {

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

    public IAlloyRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> input, AbstractMap.SimpleEntry<Float, Float> primary,
                        AbstractMap.SimpleEntry<Float, Float> secondary, AbstractMap.SimpleEntry<Float, Float> other, float req) {
        this.id = id;
        this.inputs = input;
        this.result = output;
        this.primary = primary;
        this.secondary = secondary;
        this.other = other;
        this.req = req;
    }
    @Override
    public boolean matches(IInventory inv, @Nonnull World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0)) && this.ingredient.test(inv.getStackInSlot(1)) && this.ingredient.test(inv.getStackInSlot(2));
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
        return "alloy";
    }

    @Nonnull
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.ALLOY_FURNACE);
    }

    @Nonnull
    public List<ItemStack> getOutputs() {
        return Collections.singletonList(result);
    }


    public List<Ingredient> getInputs() {
        return inputs;
    }

    public AbstractMap.SimpleEntry<Float,Float> getPrimary()
    {
        return primary;
    }

    public AbstractMap.SimpleEntry<Float,Float> getSecondary()
    {
        return secondary;
    }

    public AbstractMap.SimpleEntry<Float,Float> getOther()
    {
        return other;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IAlloyRecipe> {

        @Override
        public IAlloyRecipe read(ResourceLocation recipeId, JsonObject json) {
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            float req = JSONUtils.getFloat(json,"requirement",1.0f);
            NonNullList<Ingredient> list = NonNullList.create();
            list.addAll(Arrays.asList(ingredient));
            return new IAlloyRecipe(recipeId,result,list,new AbstractMap.SimpleEntry<>(.5f,.8f),new AbstractMap.SimpleEntry<>(.1f,.5f),new AbstractMap.SimpleEntry<>(.0f,.2f),req);
        }

        @Nullable
        @Override
        public IAlloyRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

            ItemStack output = buffer.readItemStack();

            Ingredient[] inputs = new Ingredient[buffer.readVarInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.read(buffer);
            }
            NonNullList<Ingredient> list = NonNullList.create();
            list.addAll(Arrays.asList(inputs));
            return new IAlloyRecipe(recipeId,output,list,new AbstractMap.SimpleEntry<>(.5f,.8f),new AbstractMap.SimpleEntry<>(.1f,.5f),new AbstractMap.SimpleEntry<>(.0f,.2f),1f);
        }

        @Override
        public void write(PacketBuffer buffer, IAlloyRecipe recipe) {

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

