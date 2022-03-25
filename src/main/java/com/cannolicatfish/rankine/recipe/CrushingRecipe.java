package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrushingRecipe implements Recipe<Container> {

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
        return this.recipeItems.get(0).getItems().clone();
    }
    @Override
    public boolean matches(Container inv, Level worldIn) {
        return this.recipeItems.get(0).test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public List<ItemStack> getResults(int harvestLevel, Level worldIn) {
        Random random = worldIn.getRandom();
        List<ItemStack> outputs = new ArrayList<>();
        int check = Math.min(harvestLevel + 1, 6);
        for (int i = 0; i < check; i++) {
            float c = harvestLevel == i ? (this.chances.get(i) + this.additional.get(i)) : this.chances.get(i);
            if (random.nextFloat() < c) {
                outputs.add(this.recipeOutputs.get(i));
            } else {
                outputs.add(ItemStack.EMPTY);
            }
        }
        return outputs;
    }

    public List<ItemStack> getPossibleResults(int harvestLevel, Level worldIn) {
        List<ItemStack> outputs = new ArrayList<>();
        int check = Math.min(harvestLevel + 1, 6);
        for (int i = 0; i < check; i++) {
            outputs.add(this.recipeOutputs.get(i));
        }
        return outputs;
    }

    public ItemStack getSpecificResult(int harvestLevel, int index, Level worldIn) {
        Random random = worldIn.getRandom();
        ItemStack outputs = ItemStack.EMPTY;
        float c = harvestLevel == index ? (this.chances.get(index) + this.additional.get(index)) : this.chances.get(index);
        if (random.nextFloat() < c) {
            outputs = this.recipeOutputs.get(index);
        }
        return outputs;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = GsonHelper.getAsString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = GsonHelper.getAsInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.CRUSHING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>>  implements RecipeSerializer<CrushingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "crushing");
        public CrushingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(1,Ingredient.EMPTY);
            nonnulllist.set(0, AlloyIngredientHelper.deserialize(json.get("input"),null, null,null));

            NonNullList<ItemStack> stacks = NonNullList.withSize(6, ItemStack.EMPTY);
            NonNullList<Float> chances = NonNullList.withSize(6, 0f);
            NonNullList<Float> additional = NonNullList.withSize(6, 0f);
            for (int i = 0; i < 6; i++) {
                String output = "output" + (i+1);
                if (json.has(output)) {
                    JsonObject object = GsonHelper.getAsJsonObject(json, output);
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

        public CrushingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(1, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(buffer));
            }

            NonNullList<ItemStack> stacks = NonNullList.withSize(6, ItemStack.EMPTY);
            for(int k = 0; k < stacks.size(); ++k) {
                stacks.set(k, buffer.readItem());
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

        public void toNetwork(FriendlyByteBuf buffer, CrushingRecipe recipe) {
            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.toNetwork(buffer);
            }

            int count = 0;
            for(ItemStack stack : recipe.recipeOutputs) {
                buffer.writeItem(stack);
                count++;
            }
            while (count < 6) {
                buffer.writeItem(ItemStack.EMPTY);
                count++;
            }

            count = 0;
            for (float chance : recipe.chances) {
                buffer.writeFloat(chance);
                count++;
            }
            while (count < 6) {
                buffer.writeFloat(0f);
                count++;
            }

            count = 0;
            for (float add : recipe.additional) {
                buffer.writeFloat(add);
                count++;
            }
            while (count < 6) {
                buffer.writeFloat(0f);
                count++;
            }
        }
    }

}
