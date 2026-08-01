package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SluicingRecipe implements Recipe<RecipeInput> {

    private final int cooldownTicks;
    private final Ingredient ingredient;
    private final Ingredient tool;
    private final NonNullList<Ingredient> recipeOutputs;
    private final NonNullList<Float> weights;
    private final NonNullList<Integer> mins;
    private final NonNullList<Integer> maxes;

    public SluicingRecipe(Ingredient ingredientIn, Ingredient itemIn, NonNullList<Ingredient> recipeOutputsIn,
                          NonNullList<Float> weightsIn, NonNullList<Integer> minsIn, NonNullList<Integer> maxesIn, int cooldownTicksIn) {
        this.ingredient = ingredientIn;
        this.tool = itemIn;
        this.recipeOutputs = recipeOutputsIn;
        this.weights = weightsIn;
        this.mins = minsIn;
        this.maxes = maxesIn;
        this.cooldownTicks = cooldownTicksIn;
    }


    @Override
    public String getGroup() {
        return "";
    }

    public Ingredient getTool() {
        return tool;
    }

    public NonNullList<Integer> getMins() {
        return mins;
    }

    public NonNullList<Integer> getMaxes() {
        return maxes;
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
    public boolean matches(RecipeInput inv, Level worldIn) {
        return this.getIngredient().test(inv.getItem(0)) && this.getTool().test(inv.getItem(1));
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

    public ItemStack getSluicingResult(Level worldIn) {
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        RandomSource rand = worldIn.getRandom();
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
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.SLUICING_RECIPE_SERIALIZER.get();
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

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.SLUICING.get();
    }

    public static class Serializer implements RecipeSerializer<SluicingRecipe> {

        public static final MapCodec<SluicingRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.fixedKeys("input","tool","outputs","cooldown"));

        public static final StreamCodec<RegistryFriendlyByteBuf, SluicingRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<SluicingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SluicingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static SluicingRecipe fromJson(JsonObject json) {
            Ingredient ingredient = AlloyIngredientHelper.ingredientFromJson(json.get("input"));
            Ingredient it = AlloyIngredientHelper.ingredientFromJson(json.get("tool"));

            JsonArray outputsArray = GsonHelper.getAsJsonArray(json, "outputs");
            int outputsArraySize = outputsArray.size();
            NonNullList<Ingredient> stacks = NonNullList.withSize(outputsArraySize, Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(outputsArraySize, 0f);
            NonNullList<Integer> mins = NonNullList.withSize(outputsArraySize, 1);
            NonNullList<Integer> maxes = NonNullList.withSize(outputsArraySize, 1);

            int i = 0;
            for (JsonElement element : outputsArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    stacks.set(i, AlloyIngredientHelper.ingredientFromJson(object));
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
            return new SluicingRecipe(ingredient, it, stacks, weights, mins,maxes,ticks);
        }

        public static void toJson(SluicingRecipe recipe, JsonObject json) {
            json.add("input", AlloyIngredientHelper.ingredientToJson(recipe.getIngredient()));
            json.add("tool", AlloyIngredientHelper.ingredientToJson(recipe.getTool()));
            JsonArray outputs = new JsonArray();
            for (int i = 0; i < recipe.getOutputs().size(); i++) {
                JsonElement encoded = AlloyIngredientHelper.ingredientToJson(recipe.getOutputs().get(i));
                JsonObject object = encoded.isJsonObject() ? encoded.getAsJsonObject() : new JsonObject();
                object.addProperty("weight", recipe.getWeights().get(i));
                object.addProperty("min", recipe.getMins().get(i));
                object.addProperty("max", recipe.getMaxes().get(i));
                outputs.add(object);
            }
            json.add("outputs", outputs);
            json.addProperty("cooldown", recipe.getCooldownTicks());
        }

        public static SluicingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int ticks = buffer.readInt();
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient it = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            int t = buffer.readVarInt();
            NonNullList<Ingredient> stacks = NonNullList.withSize(t, Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(t, 0f);
            NonNullList<Integer> mins = NonNullList.withSize(t,1);
            NonNullList<Integer> maxes = NonNullList.withSize(t,1);

            for (int i = 0; i < stacks.size(); i++) {
                stacks.set(i,Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
                weights.set(i,buffer.readFloat());
                mins.set(i,buffer.readInt());
                maxes.set(i,buffer.readInt());
            }

            return new SluicingRecipe(input, it, stacks, weights, mins, maxes,ticks);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, SluicingRecipe recipe) {
            buffer.writeInt(recipe.cooldownTicks);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getTool());

            buffer.writeVarInt(recipe.getOutputs().size());
            for (int i = 0; i < recipe.getOutputs().size(); i++) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getOutputs().get(i));
                buffer.writeFloat(recipe.getWeights().get(i));
                buffer.writeInt(recipe.getMins().get(i));
                buffer.writeInt(recipe.getMaxes().get(i));
            }
        }
    }

}
