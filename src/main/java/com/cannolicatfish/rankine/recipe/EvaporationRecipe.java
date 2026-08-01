package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import com.cannolicatfish.rankine.recipe.helper.RankineMapCodec;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class EvaporationRecipe implements Recipe<RecipeInput> {

    private final int processTime;
    private final FluidStack fluid;
    private final boolean consumeFluid;
    private final List<String> biomes;
    private final List<String> biomeTags;
    private final NonNullList<Ingredient> recipeOutputs;
    private final NonNullList<Float> weights;

    public EvaporationRecipe(int timeIn, FluidStack fluidIn, boolean consumeFluid, List<String> biomesIn, List<String> biomesTagsIn, NonNullList<Ingredient> recipeOutputsIn, NonNullList<Float> weightsIn) {
        this.processTime = timeIn;
        this.fluid = fluidIn;
        this.consumeFluid = consumeFluid;
        this.biomes = biomesIn;
        this.biomeTags = biomesTagsIn;
        this.recipeOutputs = recipeOutputsIn;
        this.weights = weightsIn;
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.EVAPORATION.get();
    }

    @Override
    public String getGroup() {
        return "";
    }

    public int getProcessTime() {
        return processTime;
    }
    public FluidStack getFluid() {
        return this.fluid.copy();
    }
    public boolean getConsumeFluid() {
        return this.consumeFluid;
    }
    public List<String> getBiomes() {
        return this.biomes;
    }
    public List<String> getBiomeTags() {
        return this.biomeTags;
    }
    public NonNullList<Ingredient> getOutputs() {
        return this.recipeOutputs;
    }
    public NonNullList<Float> getWeights() {
        return weights;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(1,Ingredient.EMPTY);
    }

    public Float getChance(int index) {
        float in = getWeights().get(index);
        return (in/getWeights().stream().reduce(0f, Float::sum));
    }

    @Override
    public boolean matches(RecipeInput inv, Level worldIn) {
        return false;
    }

    public boolean fluidMatch(Fluid fluidIn) {
        return fluidIn.isSame(this.getFluid().getFluid());
    }

    @Override
    public ItemStack assemble(RecipeInput inv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }


    public ItemStack getEvaporationResult(Level levelIn, ResourceLocation biomeName) {
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        if (this.getBiomes().contains(biomeName.toString()) || biomeTagCheck(levelIn, biomeName, this.getBiomeTags())) {
            for (int i = 0; i < this.getOutputs().size(); i++) {
                ItemStack[] curOut = this.getOutputs().get(i).getItems();
                if (curOut.length > 0) {
                    col.add(this.getWeights().get(i), new ItemStack(curOut[levelIn.getRandom().nextInt(curOut.length)].getItem()));
                } else {
                    col.add(this.getWeights().get(i), ItemStack.EMPTY);
                }
            }
        }
        return col.getEntries().isEmpty() ? ItemStack.EMPTY : col.getRandomElement().copy();
    }

    private static boolean biomeTagCheck(Level levelIn, ResourceLocation biomeName, List<String> recipeBiomes) {
        for (String b : recipeBiomes) {
            ResourceLocation RS = ResourceLocation.tryParse(b);
            if (RS != null) {
                TagKey<Biome> biomeTagKey = TagKey.create(Registries.BIOME, RS);
                Registry<Biome> reg = levelIn.registryAccess().registryOrThrow(Registries.BIOME);
                Biome biome = reg.getOptional(biomeName).orElseThrow();
                if (reg.getHolderOrThrow(reg.getResourceKey(biome).orElseThrow()).is(biomeTagKey)) {
                    return true;
                }
            }
        }
        return false;
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
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.EVAPORATION_RECIPE_SERIALIZER.get();
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = GsonHelper.getAsString(object, "item");
        BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    public static class Serializer implements RecipeSerializer<EvaporationRecipe> {

        public static final MapCodec<EvaporationRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.fixedKeys("processTime","input","consumeFluid","biomes","outputs"));

        public static final StreamCodec<RegistryFriendlyByteBuf, EvaporationRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<EvaporationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, EvaporationRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static EvaporationRecipe fromJson(JsonObject jsonObject) {
            int processTime = jsonObject.has("processTime") ? jsonObject.get("processTime").getAsInt() : 20000;
            FluidStack fluid = FluidHelper.getFluidStack(GsonHelper.getAsJsonObject(jsonObject, "input"));
            boolean consumeFluid = GsonHelper.getAsBoolean(jsonObject, "consumeFluid", false);

            JsonElement biomesElement = jsonObject.has("biomes") ? jsonObject.get("biomes") : null;
            List<String> biomes = new ArrayList<>();
            List<String> biomeTags = new ArrayList<>();
            if (biomesElement != null && biomesElement.isJsonObject()) {
                JsonObject biomesObject = biomesElement.getAsJsonObject();
                if (biomesObject.has("biomes")) {
                    for (JsonElement b : GsonHelper.getAsJsonArray(biomesObject, "biomes")) {
                        biomes.add(b.getAsString());
                    }
                } else if (biomesObject.has("biomeTags")) {
                    for (JsonElement b : GsonHelper.getAsJsonArray(biomesObject, "biomeTags")) {
                        biomeTags.add(b.getAsString());
                    }
                }
            }

            JsonArray outputsArray = GsonHelper.getAsJsonArray(jsonObject, "outputs");
            NonNullList<Ingredient> stacks = NonNullList.withSize(outputsArray.size(), Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(outputsArray.size(), 0f);

            int i = 0;
            for (JsonElement element : outputsArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    if (object.has("item") || object.has("tag")) {
                        stacks.set(i, AlloyIngredientHelper.ingredientFromJson(object));
                    } else {
                        stacks.set(i, Ingredient.EMPTY);
                    }
                    if (object.has("weight")){
                        weights.set(i,object.get("weight").getAsFloat());
                    } else {
                        weights.set(i,0F);
                    }
                }
                i++;
            }

            return new EvaporationRecipe(processTime, fluid, consumeFluid, biomes, biomeTags, stacks, weights);
        }

        public static void toJson(EvaporationRecipe recipe, JsonObject json) {
            json.addProperty("processTime", recipe.getProcessTime());
            json.add("input", FluidHelper.fluidStackToJson(recipe.fluid));
            json.addProperty("consumeFluid", recipe.getConsumeFluid());
            if (!recipe.getBiomes().isEmpty() || !recipe.getBiomeTags().isEmpty()) {
                JsonObject biomesObject = new JsonObject();
                if (!recipe.getBiomes().isEmpty()) {
                    JsonArray arr = new JsonArray();
                    recipe.getBiomes().forEach(arr::add);
                    biomesObject.add("biomes", arr);
                } else {
                    JsonArray arr = new JsonArray();
                    recipe.getBiomeTags().forEach(arr::add);
                    biomesObject.add("biomeTags", arr);
                }
                json.add("biomes", biomesObject);
            }
            JsonArray outputs = new JsonArray();
            for (int i = 0; i < recipe.getOutputs().size(); i++) {
                Ingredient out = recipe.getOutputs().get(i);
                JsonObject object;
                if (out.isEmpty()) {
                    object = new JsonObject();
                } else {
                    JsonElement encoded = AlloyIngredientHelper.ingredientToJson(out);
                    object = encoded.isJsonObject() ? encoded.getAsJsonObject() : new JsonObject();
                }
                object.addProperty("weight", recipe.getWeights().get(i));
                outputs.add(object);
            }
            json.add("outputs", outputs);
        }

        public static EvaporationRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int processTime = buffer.readInt();
            FluidStack fluid = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            boolean consumeFluid = buffer.readBoolean();

            int biomesSize = buffer.readInt();
            List<String> biomes = new ArrayList<>();
            if (biomesSize > 0) {
                for (int i = 0; i < biomesSize; i++) {
                    biomes.add(buffer.readUtf());
                }
            }

            int biomeTagsSize = buffer.readInt();
            List<String> biomeTags = new ArrayList<>();
            if (biomeTagsSize > 0) {
                for (int i = 0; i < biomeTagsSize; i++) {
                    biomeTags.add(buffer.readUtf());
                }
            }

            int t = buffer.readVarInt();
            NonNullList<Ingredient> stacks = NonNullList.withSize(t, Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(t, 0f);

            for (int i = 0; i < stacks.size(); i++) {
                stacks.set(i,Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
                weights.set(i,buffer.readFloat());
            }
            return new EvaporationRecipe(processTime, fluid, consumeFluid, biomes, biomeTags, stacks, weights);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, EvaporationRecipe recipe) {
            buffer.writeInt(recipe.getProcessTime());
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.getFluid());
            buffer.writeBoolean(recipe.getConsumeFluid());

            buffer.writeInt(recipe.getBiomes().size());
            for (int i = 0; i < recipe.getBiomes().size(); i++) {
                buffer.writeUtf(recipe.getBiomes().get(i));
            }
            buffer.writeInt(recipe.getBiomeTags().size());
            for (int i = 0; i < recipe.getBiomeTags().size(); i++) {
                buffer.writeUtf(recipe.getBiomeTags().get(i));
            }

            buffer.writeVarInt(recipe.getOutputs().size());
            for (int i = 0; i < recipe.getOutputs().size(); i++) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getOutputs().get(i));
                buffer.writeFloat(recipe.getWeights().get(i));
            }
        }
    }

}
