package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.world.level.material.Fluid;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class EvaporationRecipe implements Recipe<Container> {

    private final int total;
    private final int time;
    private final FluidStack fluid;
    private final List<String> biomes;
    private final NonNullList<ItemStack> recipeOutputs;
    private final ResourceLocation id;
    private final NonNullList<Float> weights;
    private final NonNullList<Integer> mins;
    private final NonNullList<Integer> maxes;
    private final boolean large;

    public static final EvaporationRecipe.Serializer SERIALIZER = new EvaporationRecipe.Serializer();

    public EvaporationRecipe(ResourceLocation idIn, boolean largeIn, int totalIn, int timeIn, FluidStack fluidIn, List<String> biomesIn, NonNullList<ItemStack> recipeOutputsIn, NonNullList<Float> weightsIn, NonNullList<Integer> minsIn, NonNullList<Integer> maxesIn) {
        this.large = largeIn;
        this.total = totalIn;
        this.time = timeIn;
        this.id = idIn;
        this.fluid = fluidIn;
        this.biomes = biomesIn;
        this.recipeOutputs = recipeOutputsIn;
        this.weights = weightsIn;
        this.mins = minsIn;
        this.maxes = maxesIn;
    }

    public int getTime() {
        return time;
    }

    public String getGroup() {
        return "";
    }

    public boolean isLarge() {
        return this.large;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(1,Ingredient.EMPTY);
    }

    public FluidStack getFluid() {
        return this.fluid.copy();
    }

    public List<String> getBiomes() {
        return this.biomes;
    }

    public NonNullList<ItemStack> getRecipeOutputs() {
        return recipeOutputs;
    }


    public List<Biome> getBiomeList() {
        List<Biome> biomeList = new ArrayList<>();
        for (String s : this.getBiomes()) {
            if (s.contains("C#")) {
                for (Biome b : ForgeRegistries.BIOMES) {
                    if (b.getBiomeCategory().getSerializedName().equalsIgnoreCase(s.split("C#")[1])) {
                        biomeList.add(b);
                    }
                }
            } else if (s.contains("B#")) {
                for (Biome b : ForgeRegistries.BIOMES) {
                    if (b.getRegistryName() != null && b.getRegistryName().equals(new ResourceLocation(s.split("C#")[1]))) {
                        biomeList.add(b);
                    }
                }
            }
        }
        return biomeList;
    }

    public List<String> getBiomeString() {
        List<String> list = new ArrayList<>();
        for (String s : this.getBiomes()) {
            if (s.contains("C#")) {
                for (Biome b : ForgeRegistries.BIOMES) {
                    if (b.getBiomeCategory().getSerializedName().equalsIgnoreCase(s.split("C#")[1]) && !list.contains(b.getBiomeCategory().getSerializedName().toUpperCase(Locale.ROOT).replace("_", " "))) {
                        list.add(b.getBiomeCategory().getSerializedName().toUpperCase(Locale.ROOT).replace("_", " "));
                    }
                }
            } else if (s.contains("B#")) {
                for (Biome b : ForgeRegistries.BIOMES) {
                    if (b.getRegistryName() != null && b.getRegistryName().equals(new ResourceLocation(s.split("C#")[1])) && !list.contains(b.getRegistryName().getPath().toUpperCase(Locale.ROOT).replace("_", " "))) {
                        list.add(b.getRegistryName().getPath().toUpperCase(Locale.ROOT).replace("_", " "));
                    }
                }
            }
        }
        return list;
    }

    public Float getChance(int index) {
        float in = getWeights().get(index);
        return (in/getWeights().stream().reduce(0f, Float::sum));
    }

    public NonNullList<Float> getWeights() {
        return weights;
    }

    public NonNullList<ItemStack> getOutputs() {
        return this.recipeOutputs;
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return false;
    }

    public boolean fluidMatch(Fluid input) {
        return input.equals(this.fluid.getFluid());
    }

    @Override
    public ItemStack assemble(Container inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public NonNullList<Integer> getMaxes() {
        return maxes;
    }

    public ItemStack getEvaporationResult(Level worldIn, ResourceLocation biome) {
        if (this.getBiomeList().isEmpty()) {
            WeightedCollection<ItemStack> col = new WeightedCollection<>();
            for (int i = 0; i < this.recipeOutputs.size(); i++) {
                col.add(this.weights.get(i),new ItemStack(this.recipeOutputs.get(i).getItem(), this.maxes.get(i).equals(this.mins.get(i)) ? this.maxes.get(i) : worldIn.getRandom().nextInt(this.maxes.get(i) - this.mins.get(i)) + this.mins.get(i)));
            }
            return col.getRandomElement().copy();
        } else {
            for (Biome b : getBiomeList()) {
                if (b.getRegistryName() != null && b.getRegistryName().equals(biome)) {
                    WeightedCollection<ItemStack> col = new WeightedCollection<>();
                    for (int i = 0; i < this.recipeOutputs.size(); i++) {
                        col.add(this.weights.get(i),new ItemStack(this.recipeOutputs.get(i).getItem(), this.maxes.get(i).equals(this.mins.get(i)) ? this.maxes.get(i) : worldIn.getRandom().nextInt(this.maxes.get(i) - this.mins.get(i)) + this.mins.get(i)));
                    }
                    return col.getRandomElement().copy();
                }
            }
            return ItemStack.EMPTY;
        }
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
        return RankineRecipeTypes.EVAPORATION;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>>  implements RecipeSerializer<EvaporationRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "evaporation");
        public EvaporationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int t = json.get("total").getAsInt();
            int w = json.has("cookTime") ? json.get("cookTime").getAsInt() : 6400;
            boolean l = !json.has("large") || json.get("large").getAsBoolean();
            FluidStack fluid = FluidHelper.getFluidStack(GsonHelper.getAsJsonObject(json, "input"));

            JsonArray b = json.has("biomes") ? GsonHelper.getAsJsonArray(json, "biomes") : new JsonArray();
            List<String> biomes = new ArrayList<>();
            for (int i = 0; i < b.size(); i++) {
                biomes.add(b.get(i).getAsString());
            }
            NonNullList<ItemStack> stacks = NonNullList.withSize(t, ItemStack.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(t, 0f);
            NonNullList<Integer> mins = NonNullList.withSize(t, 1);
            NonNullList<Integer> maxes = NonNullList.withSize(t, 1);
            for (int i = 0; i < t; i++) {
                String output = "output" + (i+1);
                if (json.has(output)) {
                    JsonObject object = GsonHelper.getAsJsonObject(json, output);
                    stacks.set(i, EvaporationRecipe.deserializeItem(object));
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

            return new EvaporationRecipe(recipeId,l, t, w, fluid, biomes, stacks, weights, mins,maxes);
        }

        public EvaporationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

            boolean l = buffer.readBoolean();
            int t = buffer.readInt();
            int w = buffer.readInt();
            FluidStack input = buffer.readFluidStack();

            int biomesSize = buffer.readInt();
            String[] biomeArray = new String[biomesSize];
            for (int i = 0; i < biomesSize; i++) {
                biomeArray[i] = buffer.readUtf();
            }

            List<String> biomes = Arrays.asList(biomeArray);

            NonNullList<ItemStack> stacks = NonNullList.withSize(t, ItemStack.EMPTY);
            for(int k = 0; k < stacks.size(); ++k) {
                stacks.set(k, buffer.readItem());
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

            return new EvaporationRecipe(recipeId, l, t, w,input,biomes, stacks, weights, mins, maxes);
        }

        public void toNetwork(FriendlyByteBuf buffer, EvaporationRecipe recipe) {
            buffer.writeBoolean(recipe.isLarge());
            buffer.writeInt(recipe.total);
            buffer.writeInt(recipe.time);
            buffer.writeFluidStack(recipe.fluid);

            buffer.writeInt(recipe.getBiomes().size());
            for (int i = 0; i < recipe.getBiomes().size(); i++) {
                buffer.writeUtf(recipe.getBiomes().get(i));
            }

            int count = 0;
            for(ItemStack stack : recipe.recipeOutputs) {
                buffer.writeItem(stack);
                count++;
            }
            while (count < recipe.total) {
                buffer.writeItem(ItemStack.EMPTY);
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
