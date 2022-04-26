package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.google.gson.*;
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

    private final int cooldownTicks;
    private final Ingredient ingredient;
    private final Ingredient tool;
    private final NonNullList<Ingredient> recipeOutputs;
    private final ResourceLocation id;
    private final NonNullList<Float> weights;
    private final NonNullList<Integer> mins;
    private final NonNullList<Integer> maxes;

    public static final SluicingRecipe.Serializer SERIALIZER = new SluicingRecipe.Serializer();

    public SluicingRecipe(ResourceLocation idIn,Ingredient ingredientIn, Ingredient itemIn, NonNullList<Ingredient> recipeOutputsIn,
                          NonNullList<Float> weightsIn, NonNullList<Integer> minsIn, NonNullList<Integer> maxesIn, int cooldownTicksIn) {
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
        return NonNullList.from(Ingredient.EMPTY, this.ingredient, this.tool);
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

    public NonNullList<Integer> getMins() {
        return mins;
    }

    public NonNullList<Integer> getMaxes() {
        return maxes;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.getIngredient().test(inv.getStackInSlot(0)) && this.getTool().test(inv.getStackInSlot(1));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    public ItemStack getSluicingResult(World worldIn) {
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        Random rand = worldIn.getRandom();
        for (int i = 0; i < this.recipeOutputs.size(); i++) {
            ItemStack[] curOut = this.recipeOutputs.get(i).getMatchingStacks();
            col.add(this.weights.get(i),new ItemStack(curOut[rand.nextInt(curOut.length)].getItem(), this.maxes.get(i).equals(this.mins.get(i)) ? this.maxes.get(i) : worldIn.getRandom().nextInt(this.maxes.get(i) - this.mins.get(i)) + this.mins.get(i)));
        }
        return col.getRandomElement().copy();
    }

    public int getCooldownTicks() {
        return this.cooldownTicks;
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
        return RankineRecipeTypes.SLUICING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<SluicingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "sluicing");
        public SluicingRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
            Ingredient it = Ingredient.deserialize(JSONUtils.getJsonObject(json, "tool"));

            JsonArray outputsArray = JSONUtils.getJsonArray(json, "outputs");
            int outputsArraySize = outputsArray.size();
            NonNullList<Ingredient> stacks = NonNullList.withSize(outputsArraySize, Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(outputsArraySize, 0f);
            NonNullList<Integer> mins = NonNullList.withSize(outputsArraySize, 1);
            NonNullList<Integer> maxes = NonNullList.withSize(outputsArraySize, 1);

            int i = 0;
            for (JsonElement element : outputsArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    stacks.set(i,Ingredient.deserialize(object));
                    if (object.has("weight")){
                        weights.set(i,object.get("weight").getAsFloat());
                    }

                    if (object.has("min")){
                        mins.set(i,object.get("min").getAsInt());
                    }

                    if (object.has("max")){
                        maxes.set(i,object.get("max").getAsInt());
                    }
                }
                i++;
            }
            int ticks = json.has("cooldown") ? json.get("cooldown").getAsInt() : 10;
            return new SluicingRecipe(recipeId,ingredient, it, stacks, weights, mins,maxes,ticks);
        }

        public SluicingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int ticks = buffer.readInt();
            Ingredient input = Ingredient.read(buffer);
            Ingredient it = Ingredient.read(buffer);
            int t = buffer.readVarInt();
            NonNullList<Ingredient> stacks = NonNullList.withSize(t, Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(t, 0f);
            NonNullList<Integer> mins = NonNullList.withSize(t,1);
            NonNullList<Integer> maxes = NonNullList.withSize(t,1);

            for (int i = 0; i < t; i++) {
                stacks.add(Ingredient.read(buffer));
                weights.add(buffer.readFloat());
                mins.add(buffer.readInt());
                maxes.add(buffer.readInt());
            }

            return new SluicingRecipe(recipeId, input, it, stacks, weights, mins, maxes,ticks);
        }

        public void write(PacketBuffer buffer, SluicingRecipe recipe) {
            buffer.writeInt(recipe.cooldownTicks);
            recipe.getIngredient().write(buffer);
            recipe.getTool().write(buffer);

            int total = recipe.getOutputs().size();
            buffer.writeVarInt(total);
            for (int i = 0; i < total; i++) {
                recipe.getOutputs().get(i).write(buffer);
                buffer.writeFloat(recipe.getWeights().get(i));
                buffer.writeInt(recipe.getMins().get(i));
                buffer.writeInt(recipe.getMaxes().get(i));
            }
        }
    }

}
