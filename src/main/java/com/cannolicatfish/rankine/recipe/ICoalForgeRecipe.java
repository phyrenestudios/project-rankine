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

public class ICoalForgeRecipe implements IRecipe<IInventory> {
    public static final ICoalForgeRecipe.Serializer SERIALIZER = new ICoalForgeRecipe.Serializer();
    public static final IRecipeType<ICoalForgeRecipe> RECIPE_TYPE = new IRecipeType<ICoalForgeRecipe>() {

    };
    protected Ingredient ingredient;
    protected Ingredient alloy;
    protected Ingredient template;
    protected ItemStack result;
    protected ItemStack secondary;
    protected final ResourceLocation id;
    protected float experience;
    protected int cookTime;
    private final NonNullList<Ingredient> inputs;

    public ICoalForgeRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> input) {
        this.id = id;
        this.ingredient = input.get(0);
        this.alloy = input.get(1);
        this.template = input.get(2);
        this.inputs = input;
        this.result = output;
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
        return "forging";
    }

    @Nonnull
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.COAL_FORGE);
    }

    @Nonnull
    public List<ItemStack> getOutputs() {
        return Collections.singletonList(result);
    }


    public List<Ingredient> getInputs() {
        return inputs;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ICoalForgeRecipe> {

        @Override
        public ICoalForgeRecipe read(ResourceLocation recipeId, JsonObject json) {
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            Ingredient alloy = Ingredient.deserialize(JSONUtils.getJsonObject(json, "alloy"));
            Ingredient template = Ingredient.deserialize(JSONUtils.getJsonObject(json, "template"));
            NonNullList<Ingredient> list = NonNullList.create();
            list.addAll(Arrays.asList(ingredient,alloy,template));
            return new ICoalForgeRecipe(recipeId,result,list);
        }

        @Nullable
        @Override
        public ICoalForgeRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

            ItemStack output = buffer.readItemStack();

            Ingredient[] inputs = new Ingredient[buffer.readVarInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.read(buffer);
            }
            NonNullList<Ingredient> list = NonNullList.create();
            list.addAll(Arrays.asList(inputs[0],inputs[1],inputs[2]));
            return new ICoalForgeRecipe(recipeId,output,list);
        }

        @Override
        public void write(PacketBuffer buffer, ICoalForgeRecipe recipe) {

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
