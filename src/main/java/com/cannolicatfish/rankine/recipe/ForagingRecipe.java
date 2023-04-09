package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
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
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForagingRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    private final List<String> biomes;
    private final List<String> biomeTags;
    private final Ingredient ingredient;
    private final NonNullList<Ingredient> recipeOutputs;
    private final NonNullList<Float> weights;
    private final NonNullList<Boolean> enchantments;
    private final boolean allBiomes;

    public ForagingRecipe(ResourceLocation id, List<String> biomes, List<String> biomeTags, Ingredient ingredientIn, NonNullList<Ingredient> recipeOutputsIn, NonNullList<Float> weights, NonNullList<Boolean> enchantments, boolean allBiomes) {
        this.id = id;
        this.biomes = biomes;
        this.biomeTags = biomeTags;
        this.ingredient = ingredientIn;
        this.recipeOutputs = recipeOutputsIn;
        this.weights = weights;
        this.enchantments = enchantments;
        this.allBiomes = allBiomes;
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

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(1,this.ingredient);
    }

    public boolean isAllBiomes() {
        return allBiomes;
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
    public List<String> getBiomeTags() {
        return this.biomeTags;
    }
    public Ingredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    public static ItemStack getForagingResult(Level levelIn, ResourceLocation biomeName, BlockState state, boolean enchantmentPresent) {
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        Random rand = levelIn.getRandom();
        for (ForagingRecipe recipe : getValidRecipes(levelIn, biomeName, state)) {
            for (int i = 0; i < recipe.getOutputs().size(); i++) {
                if (recipe.getEnchantments().get(i) && !enchantmentPresent) continue;
                ItemStack[] curOut = recipe.getOutputs().get(i).getItems();
                if (curOut.length > 0) {
                    col.add(recipe.getWeights().get(i), new ItemStack(curOut[rand.nextInt(curOut.length)].getItem()));
                } else {
                    col.add(recipe.getWeights().get(i), ItemStack.EMPTY);
                }
            }
        }
        return col.getEntries().isEmpty() ? ItemStack.EMPTY : col.getRandomElement().copy();
    }

    public static List<ForagingRecipe> getValidRecipes(Level levelIn, ResourceLocation biomeName, BlockState state) {
        if (levelIn != null) {
            List<ForagingRecipe> recipes = new ArrayList<>();
            for (ForagingRecipe recipe : levelIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.FORAGING)) {
                if ((recipe.allBiomes || recipe.getBiomes().contains(biomeName.toString()) || biomeTagCheck(levelIn, biomeName, recipe.getBiomeTags())) && recipe.getIngredient().test(new ItemStack(state.getBlock().asItem()))) {
                    recipes.add(recipe);
                }
            }
            return recipes;
        }
        return null;
    }

    private static boolean biomeTagCheck(Level levelIn, ResourceLocation biomeName, List<String> recipeBiomes) {

        for (String b : recipeBiomes) {
            ResourceLocation RS = ResourceLocation.tryParse(b);
            if (RS != null) {
                TagKey<Biome> biomeTagKey = TagKey.create(Registry.BIOME_REGISTRY, RS);
                var reg = levelIn.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);
                Biome biome = reg.getOptional(biomeName).orElseThrow();
                if (reg.getHolderOrThrow(reg.getResourceKey(biome).orElseThrow()).is(biomeTagKey)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.FORAGING_RECIPE_SERIALIZER.get();
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


    public static class Serializer implements RecipeSerializer<ForagingRecipe> {
        @Override
        public ForagingRecipe fromJson(ResourceLocation recipeId, JsonObject jsonObject) {
            JsonElement biomesArray = jsonObject.has("biomes") ? jsonObject.get("biomes") : null;
            List<String> biomes = new ArrayList<>();
            List<String> biomeTags = new ArrayList<>();
            boolean allBiomes = true;
            if (biomesArray != null && biomesArray.isJsonObject()) {
                allBiomes = false;
                JsonObject object = biomesArray.getAsJsonObject();
                if (object.has("biome")){
                    biomes.add(object.get("biome").getAsString());
                } else if (object.has("biomeTag")) {
                    biomeTags.add(object.get("biomeTag").getAsString());
                }
            }

            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "input"));


            JsonArray outputsArray = GsonHelper.getAsJsonArray(jsonObject, "outputs");
            NonNullList<Ingredient> stacks = NonNullList.withSize(outputsArray.size(), Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(outputsArray.size(), 0f);
            NonNullList<Boolean> enchantements = NonNullList.withSize(outputsArray.size(), false);

            int i = 0;
            for (JsonElement element : outputsArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    if (object.has("item") || object.has("tag")) {
                        stacks.set(i, Ingredient.fromJson(object));
                    } else {
                        stacks.set(i, Ingredient.EMPTY);
                    }
                    if (object.has("weight")){
                        weights.set(i,object.get("weight").getAsFloat());
                    } else {
                        weights.set(i,1F);
                    }
                    if (object.has("enchantmentRequired")){
                        enchantements.set(i,object.get("enchantmentRequired").getAsBoolean());
                    } else {
                        enchantements.set(i, false);
                    }
                }
                i++;
            }

            return new ForagingRecipe(recipeId,biomes,biomeTags,ingredient,stacks,weights,enchantements,allBiomes);
        }

        @Nullable
        @Override
        public ForagingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int biomesSize = buffer.readInt();
            List<String> biomes = new ArrayList<>();
            boolean allBiomes = true;
            if (biomesSize > 0) {
                allBiomes = false;
                for (int i = 0; i < biomesSize; i++) {
                    biomes.add(buffer.readUtf());
                }
            }

            int biomeTagsSize = buffer.readInt();
            List<String> biomeTags = new ArrayList<>();
            if (biomeTagsSize > 0) {
                allBiomes = false;
                for (int i = 0; i < biomeTagsSize; i++) {
                    biomes.add(buffer.readUtf());
                }
            }


            Ingredient input = Ingredient.fromNetwork(buffer);

            int t = buffer.readVarInt();
            NonNullList<Ingredient> stacks = NonNullList.withSize(t, Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(t, 0f);
            NonNullList<Boolean> enchantements = NonNullList.withSize(t, false);

            for (int i = 0; i < stacks.size(); i++) {
                stacks.set(i,Ingredient.fromNetwork(buffer));
                weights.set(i,buffer.readFloat());
                enchantements.set(i,buffer.readBoolean());
            }

            return new ForagingRecipe(recipeId, biomes, biomeTags, input, stacks, weights, enchantements, allBiomes);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ForagingRecipe recipe) {
            buffer.writeInt(recipe.getBiomes().size());
            if (recipe.getBiomes().size() > 0) {
                for (int i = 0; i < recipe.getBiomes().size(); i++) {
                    buffer.writeUtf(recipe.getBiomes().get(i));
                }
            }
            buffer.writeInt(recipe.getBiomeTags().size());
            if (recipe.getBiomeTags().size() > 0) {
                for (int i = 0; i < recipe.getBiomeTags().size(); i++) {
                    buffer.writeUtf(recipe.getBiomeTags().get(i));
                }
            }

            recipe.getIngredient().toNetwork(buffer);

            buffer.writeVarInt(recipe.getOutputs().size());
            for (int i = 0; i < recipe.getOutputs().size(); i++) {
                recipe.getOutputs().get(i).toNetwork(buffer);
                buffer.writeFloat(recipe.getWeights().get(i));
                buffer.writeBoolean(recipe.getEnchantments().get(i));
            }
        }
    }

}
