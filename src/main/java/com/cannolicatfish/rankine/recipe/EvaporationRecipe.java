package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class EvaporationRecipe implements IRecipe<IInventory> {

    private final int total;
    private final int time;
    private final FluidStack fluid;
    private final Ingredient bucket;
    private final List<String> biomes;
    private final NonNullList<ItemStack> recipeOutputs;
    private final ResourceLocation id;
    private final NonNullList<Float> weights;
    private final NonNullList<Integer> mins;
    private final NonNullList<Integer> maxes;
    private final boolean large;

    public static final EvaporationRecipe.Serializer SERIALIZER = new EvaporationRecipe.Serializer();

    public EvaporationRecipe(ResourceLocation idIn, boolean largeIn, int totalIn, int timeIn, FluidStack fluidIn, Ingredient bucketIn, List<String> biomesIn, NonNullList<ItemStack> recipeOutputsIn, NonNullList<Float> weightsIn, NonNullList<Integer> minsIn, NonNullList<Integer> maxesIn) {
        this.large = largeIn;
        this.total = totalIn;
        this.time = timeIn;
        this.id = idIn;
        this.fluid = fluidIn;
        this.bucket = bucketIn;
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
        return NonNullList.withSize(1,this.bucket);
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

    public NonNullList<Ingredient> getJEIIngredient() {
        return NonNullList.withSize(1,this.bucket);
    }

    public List<Biome> getBiomeList() {
        List<Biome> biomeList = new ArrayList<>();
        for (String s : this.getBiomes()) {
            if (s.contains("C#")) {
                for (Biome b : ForgeRegistries.BIOMES) {
                    if (b.getCategory().getString().equalsIgnoreCase(s.split("C#")[1])) {
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
                    if (b.getCategory().getString().equalsIgnoreCase(s.split("C#")[1]) && !list.contains(b.getCategory().getString().toUpperCase(Locale.ROOT).replace("_", " "))) {
                        list.add(b.getCategory().getString().toUpperCase(Locale.ROOT).replace("_", " "));
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
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    public boolean fluidMatch(Fluid input) {
        return input.equals(this.fluid.getFluid());
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    public NonNullList<Integer> getMaxes() {
        return maxes;
    }

    public ItemStack getEvaporationResult(World worldIn, ResourceLocation biome) {
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

    public static FluidStack deserializeFluid(JsonObject object) {
        String s = JSONUtils.getString(object, "block");

        Block block = Registry.BLOCK.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonParseException("Unknown block '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return BlockRecipeHelper.getBlockFluidStack(object);
        }
    }

    @Override
    public IRecipeType<?> getType() {
        return RankineRecipeTypes.EVAPORATION;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<EvaporationRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "evaporation");
        public EvaporationRecipe read(ResourceLocation recipeId, JsonObject json) {
            int t = json.get("total").getAsInt();
            int w = json.has("cookTime") ? json.get("cookTime").getAsInt() : 6400;
            boolean l = !json.has("large") || json.get("large").getAsBoolean();
            FluidStack fluid = deserializeFluid(JSONUtils.getJsonObject(json, "input"));
            Ingredient bucket = Ingredient.deserialize(JSONUtils.getJsonObject(json, "bucket"));

            JsonArray b = json.has("biomes") ? JSONUtils.getJsonArray(json, "biomes") : new JsonArray();
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
                    JsonObject object = JSONUtils.getJsonObject(json, output);
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

            return new EvaporationRecipe(recipeId,l, t, w, fluid, bucket, biomes, stacks, weights, mins,maxes);
        }

        public EvaporationRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

            boolean l = buffer.readBoolean();
            int t = buffer.readInt();
            int w = buffer.readInt();
            FluidStack input = buffer.readFluidStack();
            Ingredient b = Ingredient.read(buffer);

            int biomesSize = buffer.readInt();
            String[] biomeArray = new String[biomesSize];
            for (int i = 0; i < biomesSize; i++) {
                biomeArray[i] = buffer.readString();
            }

            List<String> biomes = Arrays.asList(biomeArray);

            NonNullList<ItemStack> stacks = NonNullList.withSize(t, ItemStack.EMPTY);
            for(int k = 0; k < stacks.size(); ++k) {
                stacks.set(k, buffer.readItemStack());
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

            return new EvaporationRecipe(recipeId, l, t, w,input,b, biomes, stacks, weights, mins, maxes);
        }

        public void write(PacketBuffer buffer, EvaporationRecipe recipe) {
            buffer.writeBoolean(recipe.isLarge());
            buffer.writeInt(recipe.total);
            buffer.writeInt(recipe.time);
            buffer.writeFluidStack(recipe.fluid);
            recipe.bucket.write(buffer);

            buffer.writeInt(recipe.getBiomes().size());
            for (int i = 0; i < recipe.getBiomes().size(); i++) {
                buffer.writeString(recipe.getBiomes().get(i));
            }

            int count = 0;
            for(ItemStack stack : recipe.recipeOutputs) {
                buffer.writeItemStack(stack);
                count++;
            }
            while (count < recipe.total) {
                buffer.writeItemStack(ItemStack.EMPTY);
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
