package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForagingRecipe implements Recipe<Container> {

    public static final ForagingRecipe.Serializer SERIALIZER = new ForagingRecipe.Serializer();
    private final ResourceLocation id;
    private final List<String> biomes;
    private final NonNullList<Ingredient> recipeOutputs;
    private final NonNullList<Float> weights;
    private final NonNullList<Boolean> enchantments;

    public ForagingRecipe(ResourceLocation id, List<String> biomes, NonNullList<Ingredient> recipeOutputsIn, NonNullList<Float> weights, NonNullList<Boolean> enchantments) {
        this.id = id;
        this.biomes = biomes;
        this.recipeOutputs = recipeOutputsIn;
        this.weights = weights;
        this.enchantments = enchantments;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return false;
    }

    @Override
    public ItemStack assemble(Container inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }


    public Float getChance(int index) {
        float in = getWeights().get(index);
        return (in/getWeights().stream().reduce(0f, Float::sum));
    }

    public NonNullList<Float> getWeights() {
        return this.weights;
    }
    public NonNullList<Boolean> getEnchantments() {
        return this.enchantments;
    }
    public NonNullList<Ingredient> getOutputs() {
        return this.recipeOutputs;
    }
    public List<String> getBiomes() {
        return this.biomes;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    public static ForagingRecipe getForagingRecipe(Level levelIn, ResourceLocation biomeName) {
        if (levelIn != null) {
            for (ForagingRecipe recipe : levelIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.FORAGING)) {
                if (recipe.biomes.contains(biomeName.toString())) {
                    return recipe;
                }
            }
        }
        return null;
    }

    public ItemStack getForagingResult(Level levelIn, boolean enchantmentPresent) {
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        Random rand = levelIn.getRandom();
        for (int i = 0; i < this.recipeOutputs.size(); i++) {
            if (this.enchantments.get(i) && !enchantmentPresent) continue;
            ItemStack[] curOut = this.recipeOutputs.get(i).getItems();
            col.add(this.weights.get(i),new ItemStack(curOut[rand.nextInt(curOut.length)].getItem()));
        }
        return col.getRandomElement().copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.FORAGING;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = GsonHelper.getAsString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ForagingRecipe> {
        @Override
        public ForagingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonElement biomesArray = json.get("biomes");
            List<String> biomes = new ArrayList<>();

            if (biomesArray.isJsonObject()) {
                JsonObject object = biomesArray.getAsJsonObject();
                if (object.has("biome")){
                    biomes.add(object.get("biome").getAsString());
                }
                if (object.has("biomeTag")){
                    for (Biome b : ForgeRegistries.BIOMES.tags().getTag(ForgeRegistries.BIOMES.tags().createTagKey(new ResourceLocation(object.get("biomeTag").getAsString()))).stream().toList()) {
                        biomes.add(b.getRegistryName().toString());
                    }
                }
            }



            JsonArray outputsArray = GsonHelper.getAsJsonArray(json, "outputs");
            NonNullList<Ingredient> stacks = NonNullList.withSize(outputsArray.size(), Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(outputsArray.size(), 0f);
            NonNullList<Boolean> enchantements = NonNullList.withSize(outputsArray.size(), false);

            int i = 0;
            for (JsonElement element : outputsArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    stacks.set(i,Ingredient.fromJson(object));
                    if (object.has("weight")){
                        weights.set(i,object.get("weight").getAsFloat());
                    }
                    if (object.has("enchantmentRequired")){
                        enchantements.set(i,object.get("enchantmentRequired").getAsBoolean());
                    }
                }
                i++;
            }

            return new ForagingRecipe(recipeId,biomes,stacks,weights,enchantements);
        }

        @Nullable
        @Override
        public ForagingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int biomesSize = buffer.readInt();
            List<String> biomes = new ArrayList<>();
            for (int i = 0; i < biomesSize; i++) {
                biomes.set(i,buffer.readUtf());
            }

            int t = buffer.readVarInt();
            NonNullList<Ingredient> stacks = NonNullList.withSize(t, Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(t, 0f);
            NonNullList<Boolean> enchantements = NonNullList.withSize(t, false);

            for (int i = 0; i < stacks.size(); i++) {
                stacks.set(i,Ingredient.fromNetwork(buffer));
                weights.set(i,buffer.readFloat());
                enchantements.set(i,buffer.readBoolean());
            }

            return new ForagingRecipe(recipeId, biomes, stacks, weights, enchantements);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ForagingRecipe recipe) {
            buffer.writeInt(recipe.getBiomes().size());
            for (int i = 0; i < recipe.getBiomes().size(); i++) {
                buffer.writeUtf(recipe.getBiomes().get(i));
            }

            buffer.writeVarInt(recipe.getOutputs().size());
            for (int i = 0; i < recipe.getOutputs().size(); i++) {
                recipe.getOutputs().get(i).toNetwork(buffer);
                buffer.writeFloat(recipe.getWeights().get(i));
                buffer.writeBoolean(recipe.getEnchantments().get(i));
            }
        }
    }

}
