package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.google.gson.JsonArray;
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
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class AirDistillationRecipe implements IRecipe<IInventory> {

    private final List<String> dims;
    private final List<String> biomes;
    private final NonNullList<ItemStack> recipeOutputs;
    private final ResourceLocation id;
    private final NonNullList<Float> chances;

    public static final AirDistillationRecipe.Serializer SERIALIZER = new AirDistillationRecipe.Serializer();

    public AirDistillationRecipe(ResourceLocation idIn, NonNullList<ItemStack> recipeOutputsIn, NonNullList<Float> chancesIn,
                                 List<String> dimsIn, List<String> biomesIn) {
        this.id = idIn;
        this.recipeOutputs = recipeOutputsIn;
        this.chances = chancesIn;
        this.dims = dimsIn;
        this.biomes = biomesIn;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(1,Ingredient.EMPTY);
    }

    public NonNullList<Float> getChances() {
        return chances;
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

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    public List<String> getBiomes() {
        return this.biomes;
    }

    public List<String> getDims() {
        return this.dims;
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

    public boolean matchesDistillationRecipe(ResourceLocation biome, ResourceLocation dim) {
        if (this.getBiomeList().isEmpty() && (getDims().isEmpty() || getDims().contains(dim.toString()))) {
            return true;
        } else {
            for (Biome b : getBiomeList()) {
                if (b.getRegistryName() != null && b.getRegistryName().equals(biome) && (getDims().isEmpty() || getDims().contains(dim.toString()))) {
                    return true;
                }
            }
            return false;
        }
    }

    public ItemStack getDistillationResult(int level, ResourceLocation biome, ResourceLocation dim) {
        if (this.getBiomeList().isEmpty() && (getDims().isEmpty() || getDims().contains(dim.toString()))) {
            return recipeOutputs.get(level-1);
        } else {
            for (Biome b : getBiomeList()) {
                if (b.getRegistryName() != null && b.getRegistryName().equals(biome) && (getDims().isEmpty() || getDims().contains(dim.toString()))) {
                    return recipeOutputs.get(level-1);
                }
            }
            return ItemStack.EMPTY;
        }
    }

    public ItemStack getDistillationWithChances(World worldIn, int level, ResourceLocation biome, ResourceLocation dim) {
        return worldIn.getRandom().nextFloat() < chances.get(level-1) ? getDistillationResult(level, biome, dim) : ItemStack.EMPTY;
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
        return RankineRecipeTypes.AIR_DISTILLATION;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<AirDistillationRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "air_distillation");
        public AirDistillationRecipe read(ResourceLocation recipeId, JsonObject json) {

            JsonArray b = json.has("biomes") ? JSONUtils.getJsonArray(json, "biomes") : new JsonArray();
            List<String> biomes = new ArrayList<>();
            for (int i = 0; i < b.size(); i++) {
                biomes.add(b.get(i).getAsString());
            }

            JsonArray d = json.has("dimensions") ? JSONUtils.getJsonArray(json, "dimensions") : new JsonArray();
            List<String> dims = new ArrayList<>();
            for (int i = 0; i < d.size(); i++) {
                dims.add(d.get(i).getAsString());
            }

            NonNullList<ItemStack> stacks = NonNullList.withSize(10, ItemStack.EMPTY);
            NonNullList<Float> chances = NonNullList.withSize(10, 0f);
            for (int i = 0; i < 10; i++) {
                String output = "output" + (i+1);
                if (json.has(output)) {
                    JsonObject object = JSONUtils.getJsonObject(json, output);
                    stacks.set(i, AirDistillationRecipe.deserializeItem(object));
                    if (object.has("chance")){
                        chances.set(i,object.get("chance").getAsFloat());
                    } else {
                        chances.set(i,0f);
                    }

                }
            }

            return new AirDistillationRecipe(recipeId, stacks, chances, dims, biomes);
        }

        public AirDistillationRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

            NonNullList<ItemStack> stacks = NonNullList.withSize(10, ItemStack.EMPTY);
            for(int k = 0; k < stacks.size(); ++k) {
                stacks.set(k, buffer.readItemStack());
            }

            NonNullList<Float> chances = NonNullList.withSize(10, 0f);
            for(int k = 0; k < chances.size(); ++k) {
                chances.set(k, buffer.readFloat());
            }

            int dimsSize = buffer.readInt();
            String[] dimArray = new String[dimsSize];
            for (int i = 0; i < dimsSize; i++) {
                dimArray[i] = buffer.readString();
            }

            List<String> dims = Arrays.asList(dimArray);

            int biomesSize = buffer.readInt();
            String[] biomeArray = new String[biomesSize];
            for (int i = 0; i < biomesSize; i++) {
                biomeArray[i] = buffer.readString();
            }

            List<String> biomes = Arrays.asList(biomeArray);


            return new AirDistillationRecipe(recipeId, stacks, chances,dims,biomes);
        }

        public void write(PacketBuffer buffer, AirDistillationRecipe recipe) {

            int count = 0;
            for(ItemStack stack : recipe.recipeOutputs) {
                buffer.writeItemStack(stack);
                count++;
            }
            while (count < 10) {
                buffer.writeItemStack(ItemStack.EMPTY);
                count++;
            }

            count = 0;
            for (float chance : recipe.chances) {
                buffer.writeFloat(chance);
                count++;
            }
            while (count < 10) {
                buffer.writeFloat(0f);
                count++;
            }

            buffer.writeInt(recipe.getBiomes().size());
            for (int i = 0; i < recipe.getBiomes().size(); i++) {
                buffer.writeString(recipe.getBiomes().get(i));
            }

            buffer.writeInt(recipe.getDims().size());
            for (int i = 0; i < recipe.getDims().size(); i++) {
                buffer.writeString(recipe.getDims().get(i));
            }
        }
    }

}
