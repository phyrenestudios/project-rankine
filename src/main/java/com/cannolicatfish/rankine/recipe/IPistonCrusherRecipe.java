package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
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
import java.util.*;

public class IPistonCrusherRecipe implements IRecipe<IInventory> {
    public static final Serializer SERIALIZER = new Serializer();
    public static final IRecipeType<IPistonCrusherRecipe> RECIPE_TYPE = new IRecipeType<IPistonCrusherRecipe>() {

    };
    protected Ingredient ingredient;
    protected ItemStack result;
    protected ItemStack secondary;
    protected final ResourceLocation id;
    protected float chance;
    protected float experience;
    protected int cookTime;
    private final NonNullList<Ingredient> inputs;

    public IPistonCrusherRecipe(ResourceLocation id, ItemStack[] outputs, Ingredient input, float chance) {
        this.id = id;

        this.ingredient = input;
        this.inputs = NonNullList.create();
        this.inputs.addAll(Collections.singletonList(ingredient));
        this.result = outputs[0];
        this.secondary = outputs[1];
        this.chance = chance;
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
        return "crushing";
    }

    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.PISTON_CRUSHER);
    }


    public List<ItemStack> getOutputs() {
        return Arrays.asList(result,secondary);
    }

    public List<ItemStack> getOutputs(List<ItemStack> inputs) {
        return getOutputs();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IPistonCrusherRecipe> {

        @Override
        public IPistonCrusherRecipe read(ResourceLocation recipeId, JsonObject json) {

            /*IPistonCrusherRecipe recipe = new IPistonCrusherRecipe(recipeId);
            recipe.result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            recipe.secondary = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "secondary"));
            recipe.chance = JSONUtils.getFloat(json,"chance",1.0f);
            recipe.cookTime = JSONUtils.getInt(json, "process_time", 200);
            recipe.experience = JSONUtils.getFloat(json, "xp", 0.0F);
            recipe.ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            return recipe;*/

            ItemStack[] outputs = new ItemStack[2];
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            ItemStack secondary = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "secondary"));
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            float chance = JSONUtils.getFloat(json,"chance",1.0f);
            outputs[0] = result;
            outputs[1] = secondary;
            return new IPistonCrusherRecipe(recipeId,outputs,ingredient,chance);
        }

        @Nullable
        @Override
        public IPistonCrusherRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            /*IPistonCrusherRecipe recipe = new IPistonCrusherRecipe(recipeId);
            recipe.cookTime = buffer.readVarInt();
            recipe.result = buffer.readItemStack();
            recipe.secondary = buffer.readItemStack();
            recipe.chance = buffer.readFloat();
            recipe.ingredient = Ingredient.read(buffer);
            return recipe;*/

            Ingredient input = Ingredient.read(buffer);

            ItemStack[] outputs = new ItemStack[buffer.readVarInt()];
            for (int i = 0; i < outputs.length; i++) {
                outputs[i] = buffer.readItemStack();
            }

            float chance = buffer.readFloat();
            return new IPistonCrusherRecipe(recipeId,outputs,input,chance);
        }

        @Override
        public void write(PacketBuffer buffer, IPistonCrusherRecipe recipe) {
            /*buffer.writeVarInt(recipe.cookTime);
            buffer.writeItemStack(recipe.result);
            buffer.writeItemStack(recipe.secondary);
            buffer.writeFloat(recipe.chance);
            buffer.writeFloat(recipe.experience);
            recipe.ingredient.write(buffer);*/

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
