package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.UnaryOperator;

public class CrushingRecipe implements IRecipe<IInventory> {

    private final NonNullList<Ingredient> recipeItems;
    private final NonNullList<ItemStack> recipeOutputs;
    private final ResourceLocation id;
    private final NonNullList<Float> chances;
    private final NonNullList<Float> additional;

    public static final CrushingRecipe.Serializer SERIALIZER = new CrushingRecipe.Serializer();

    public CrushingRecipe(ResourceLocation idIn, NonNullList<Ingredient> recipeItemsIn, NonNullList<ItemStack> recipeOutputsIn, NonNullList<Float> chancesIn, NonNullList<Float> additionalIn) {
        this.id = idIn;
        this.recipeItems = recipeItemsIn;
        this.recipeOutputs = recipeOutputsIn;
        this.chances = chancesIn;
        this.additional = additionalIn;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public NonNullList<Float> getChances() {
        return chances;
    }

    public NonNullList<Float> getAdditional() {
        return additional;
    }

    public NonNullList<ItemStack> getRecipeOutputs() {
        return recipeOutputs;
    }

    public NonNullList<ItemStack> getRecipeOutputsJEI() {
        if (recipeOutputs.contains(ItemStack.EMPTY))
        {
            NonNullList<ItemStack> newJeiCopy = recipeOutputs;
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
        return recipeOutputs;
    }

    public ItemStack[] getIngredientAsStackList() {
        return this.recipeItems.get(0).getMatchingStacks().clone();
    }
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    public List<ItemStack> getResults(int harvestLevel, World worldIn) {
        Random random = worldIn.getRandom();
        List<ItemStack> outputs = new ArrayList<>();
        int check = Math.min(harvestLevel + 1, 6);
        for (int i = 0; i < check; i++) {
            float c = harvestLevel == i ? (this.chances.get(i) + this.additional.get(i)) : this.chances.get(i);
            if (random.nextFloat() < c) {
                outputs.add(this.recipeOutputs.get(i));
            }
        }
        return outputs;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
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

    @Override
    public IRecipeType<?> getType() {
        return RankineRecipeTypes.CRUSHING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<CrushingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "crushing");
        public CrushingRecipe read(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(1,Ingredient.EMPTY);
            nonnulllist.set(0,AlloyIngredientHelper.deserialize(json.get("input"),null));

            NonNullList<ItemStack> stacks = NonNullList.withSize(6, ItemStack.EMPTY);
            NonNullList<Float> chances = NonNullList.withSize(6, 0f);
            NonNullList<Float> additional = NonNullList.withSize(6, 0f);
            for (int i = 0; i < 6; i++) {
                String output = "output" + (i+1);
                if (json.has(output)) {
                    JsonObject object = JSONUtils.getJsonObject(json, output);
                    stacks.set(i,CrushingRecipe.deserializeItem(object));
                    if (object.has("chance")){
                        chances.set(i,object.get("chance").getAsFloat());
                    } else {
                        chances.set(i,0f);
                    }

                    if (object.has("additional")){
                        additional.set(i,object.get("additional").getAsFloat());
                    } else {
                        additional.set(i,0f);
                    }

                }
            }

            return new CrushingRecipe(recipeId, nonnulllist, stacks, chances, additional);
        }

        public CrushingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(1, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.read(buffer));
            }

            NonNullList<ItemStack> stacks = NonNullList.withSize(6, ItemStack.EMPTY);
            for(int k = 0; k < stacks.size(); ++k) {
                stacks.set(k, buffer.readItemStack());
            }

            NonNullList<Float> chances = NonNullList.withSize(6, 0f);
            for(int k = 0; k < chances.size(); ++k) {
                chances.set(k, buffer.readFloat());
            }


            NonNullList<Float> additional = NonNullList.withSize(6,0f);
            for(int k = 0; k < additional.size(); ++k) {
                additional.set(k, buffer.readFloat());
            }

            return new CrushingRecipe(recipeId, nonnulllist, stacks, chances, additional);
        }

        public void write(PacketBuffer buffer, CrushingRecipe recipe) {
            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.write(buffer);
            }

            int count = 0;
            for(ItemStack stack : recipe.recipeOutputs) {
                buffer.writeItemStack(stack);
                count++;
            }
            while (count < 6) {
                buffer.writeItemStack(ItemStack.EMPTY);
                count++;
            }

            count = 0;
            for (float chance : recipe.chances) {
                buffer.writeFloat(chance);
            }
            while (count < 6) {
                buffer.writeFloat(0f);
                count++;
            }

            count = 0;
            for (float add : recipe.additional) {
                buffer.writeFloat(add);
            }
            while (count < 6) {
                buffer.writeFloat(0f);
                count++;
            }
        }
    }

}
