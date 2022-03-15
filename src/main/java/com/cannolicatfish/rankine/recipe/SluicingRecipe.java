package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.util.WeightedCollection;
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
import java.util.Random;

public class SluicingRecipe implements IRecipe<IInventory> {

    private final int total;
    private final int cooldownTicks;
    private final Ingredient ingredient;
    private final Ingredient tool;
    private final NonNullList<Ingredient> recipeOutputs;
    private final ResourceLocation id;
    private final NonNullList<Float> weights;
    private final NonNullList<Integer> mins;
    private final NonNullList<Integer> maxes;

    public static final SluicingRecipe.Serializer SERIALIZER = new SluicingRecipe.Serializer();

    public SluicingRecipe(ResourceLocation idIn, int totalIn, Ingredient ingredientIn, Ingredient itemIn, NonNullList<Ingredient> recipeOutputsIn, NonNullList<Float> weightsIn, NonNullList<Integer> minsIn, NonNullList<Integer> maxesIn, int cooldownTicksIn) {
        this.total = totalIn;
        this.id = idIn;
        this.ingredient = ingredientIn;
        this.tool = itemIn;
        this.recipeOutputs = recipeOutputsIn;
        this.weights = weightsIn;
        this.mins = minsIn;
        this.maxes = maxesIn;
        this.cooldownTicks = cooldownTicksIn;
    }


    public String getGroup() {
        return "";
    }

    public Ingredient getTool() {
        return tool;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.ingredient, this.tool);
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public Float getChance(int index) {
        float in = getWeights().get(index);
        return (in/getWeights().stream().reduce(0f, Float::sum));
    }

    public NonNullList<Float> getWeights() {
        return this.weights;
    }

    public NonNullList<Ingredient> getOutputs() {
        return this.recipeOutputs;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.getIngredient().test(inv.getItem(0)) && this.getTool().test(inv.getItem(1));
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public ItemStack getSluicingResult(World worldIn) {
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        Random rand = worldIn.getRandom();
        for (int i = 0; i < this.recipeOutputs.size(); i++) {
            ItemStack[] curOut = this.recipeOutputs.get(i).getItems();
            col.add(this.weights.get(i),new ItemStack(curOut[rand.nextInt(curOut.length)].getItem(), this.maxes.get(i).equals(this.mins.get(i)) ? this.maxes.get(i) : worldIn.getRandom().nextInt(this.maxes.get(i) - this.mins.get(i)) + this.mins.get(i)));
        }
        return col.getRandomElement().copy();
    }

    public int getCooldownTicks() {
        return this.cooldownTicks;
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
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = JSONUtils.getAsString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getAsInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    @Override
    public IRecipeType<?> getType() {
        return RankineRecipeTypes.SLUICING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<SluicingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "sluicing");
        public SluicingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int t = json.get("total").getAsInt();
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "input"));
            Ingredient it = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "tool"));
            NonNullList<Ingredient> stacks = NonNullList.withSize(t, Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(t, 0f);
            NonNullList<Integer> mins = NonNullList.withSize(t, 1);
            NonNullList<Integer> maxes = NonNullList.withSize(t, 1);
            for (int i = 0; i < t; i++) {
                String output = "output" + (i+1);
                if (json.has(output)) {
                    JsonObject object = JSONUtils.getAsJsonObject(json, output);
                    stacks.set(i,Ingredient.fromJson(JSONUtils.getAsJsonObject(json, output)));
                    if (object.has("weight")){
                        weights.set(i,object.get("weight").getAsFloat());
                    } else {
                        weights.set(i,0f);
                    }

                    if (object.has("min")){
                        mins.set(i,object.get("min").getAsInt());
                    } else {
                        mins.set(i,1);
                    }

                    if (object.has("max")){
                        maxes.set(i,object.get("max").getAsInt());
                    } else {
                        maxes.set(i,1);
                    }


                }
            }
            int ticks = json.has("cooldown") ? json.get("cooldown").getAsInt() : 10;
            return new SluicingRecipe(recipeId, t, ingredient, it, stacks, weights, mins,maxes,ticks);
        }

        public SluicingRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            int t = buffer.readInt();
            int ticks = buffer.readInt();
            Ingredient input = Ingredient.fromNetwork(buffer);
            Ingredient it = Ingredient.fromNetwork(buffer);
            NonNullList<Ingredient> stacks = NonNullList.withSize(t, Ingredient.EMPTY);
            for(int k = 0; k < stacks.size(); ++k) {
                stacks.set(k, Ingredient.fromNetwork(buffer));
            }

            NonNullList<Float> weights = NonNullList.withSize(t, 0f);
            for(int k = 0; k < weights.size(); ++k) {
                weights.set(k, buffer.readFloat());
            }


            NonNullList<Integer> mins = NonNullList.withSize(t,1);
            for(int k = 0; k < mins.size(); ++k) {
                mins.set(k, buffer.readInt());
            }

            NonNullList<Integer> maxes = NonNullList.withSize(t,1);
            for(int k = 0; k < maxes.size(); ++k) {
                maxes.set(k, buffer.readInt());
            }

            return new SluicingRecipe(recipeId, t, input, it, stacks, weights, mins, maxes,ticks);
        }

        public void toNetwork(PacketBuffer buffer, SluicingRecipe recipe) {
            buffer.writeInt(recipe.total);
            buffer.writeInt(recipe.cooldownTicks);
            recipe.getIngredient().toNetwork(buffer);
            recipe.getTool().toNetwork(buffer);
            int count = 0;
            for(Ingredient stack : recipe.recipeOutputs) {
                stack.toNetwork(buffer);
                count++;
            }
            while (count < recipe.total) {
                Ingredient.EMPTY.toNetwork(buffer);
                count++;
            }

            count = 0;
            for (float chance : recipe.weights) {
                buffer.writeFloat(chance);
                count++;
            }
            while (count < recipe.total) {
                buffer.writeFloat(0f);
                count++;
            }

            count = 0;
            for (int add : recipe.mins) {
                buffer.writeInt(add);
                count++;
            }
            while (count < recipe.total) {
                buffer.writeInt(1);
                count++;
            }

            count = 0;
            for (int add : recipe.maxes) {
                buffer.writeInt(add);
                count++;
            }
            while (count < recipe.total) {
                buffer.writeInt(1);
                count++;
            }
        }
    }

}
