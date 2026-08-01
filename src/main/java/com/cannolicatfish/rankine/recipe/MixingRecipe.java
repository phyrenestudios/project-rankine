package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import com.cannolicatfish.rankine.recipe.helper.RankineMapCodec;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MixingRecipe implements Recipe<RecipeInput> {

    private final int mixTime;
    private final int ingredientTotal;
    private final float matScale;
    private final FluidStack fluid;
    private final NonNullList<Ingredient> recipeItems;
    private final NonNullList<Boolean> required;
    private final NonNullList<Float> mins;
    private final NonNullList<Float> maxes;
    private final ItemStack recipeOutput;

    public MixingRecipe(int mixTimeIn, float matScaleIn, int ingredientTotalIn, FluidStack fluidIn,
                        NonNullList<Ingredient> recipeItemsIn, NonNullList<Boolean> requiredIn, NonNullList<Float> minsIn, NonNullList<Float> maxesIn, ItemStack outputIn) {
        this.mixTime = mixTimeIn;
        this.matScale = matScaleIn;
        this.ingredientTotal = ingredientTotalIn;
        this.fluid = fluidIn;
        this.recipeItems = recipeItemsIn;
        this.required = requiredIn;
        this.mins = minsIn;
        this.maxes = maxesIn;
        this.recipeOutput = outputIn;
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public FluidStack getFluid() {
        return this.fluid.copy();
    }

    public FluidStack getFluidFilled() {
        FluidStack fluidStack = this.fluid.copy();
        fluidStack.setAmount(1000);
        return fluidStack;
    }

    /**
     * PORT NOTE: the 1.18.2 version additionally verified the mixing barrel's input tank fluid when the
     * inventory was a MixingBarrelTile. With RecipeInput wrappers the recipe can no longer see the tank;
     * the mixing barrel block entity must check {@link #getOutputFluidReq} against its tank itself.
     */
    @Override
    public boolean matches(RecipeInput inv, Level worldIn) {
        return !getMixingResult(inv,worldIn).isEmpty();
    }

    @Override
    public ItemStack assemble(RecipeInput inv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.recipeOutput.copy();
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return this.recipeOutput.copy();
    }

    public int getMixTime() {
        return mixTime;
    }

    public int getOutputMixTime(RecipeInput inv) {
        int sum = 0;
        for (int i = 0; i < inv.size(); i++) {
            sum += inv.getItem(i).getCount();
        }
        return Math.round(mixTime * (sum*getMatScale()));
    }

    public int getOutputMixTime(int count) {
        return Math.round(mixTime * (count*getMatScale()));
    }

    public FluidStack getOutputFluidReq(RecipeInput inv) {
        int sum = 0;
        for (int i = 0; i < inv.size(); i++) {
            sum += inv.getItem(i).getCount();
        }

        int count = Math.round(fluid.getAmount()*(sum));
        return new FluidStack(fluid.getFluid(),count);
    }

    public FluidStack getOutputFluidReq(int sum) {
        int count = Math.round(fluid.getAmount()*(sum));
        return new FluidStack(fluid.getFluid(),count);
    }

    public int getIngredientTotal() {
        return ingredientTotal;
    }

    public NonNullList<Boolean> getRequired() {
        return required;
    }

    public float getMatScale() {
        return matScale;
    }

    public NonNullList<Float> getMins() {
        return mins;
    }

    public NonNullList<Float> getMaxes() {
        return maxes;
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
        return RankineRecipeSerializers.MIXING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.MIXING.get();
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

    public ItemStack getMixingResult(RecipeInput inv, Level worldIn) {
        List<Ingredient> currentIngredients = new ArrayList<>();
        List<Integer> currentMaterial = new ArrayList<>();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                boolean flag = false;
                Optional<Ingredient> c = getIngredients().stream().filter(ingredient -> ingredient.test(stack)).findFirst();
                if (c.isPresent()) {
                    Ingredient curIng = c.get();
                    if (!currentIngredients.contains(curIng)) {
                        currentIngredients.add(curIng);
                        currentMaterial.add(stack.getCount());
                    } else {
                        currentMaterial.set(currentIngredients.indexOf(curIng),currentMaterial.get(currentIngredients.indexOf(curIng)) + stack.getCount());
                    }
                    flag = true;
                }
                if (!flag) {
                    return ItemStack.EMPTY;
                }
            }
        }

        int sum = currentMaterial.stream().mapToInt(Integer::intValue).sum();
        float matFactor = getMatScale();

        if (currentIngredients.size() > 1 && (Math.round(sum*matFactor) > 64 || Math.round(sum*matFactor) < 1)){
            return ItemStack.EMPTY;
        }

        for (int j = 0; j < currentIngredients.size(); j++) {
            Ingredient curIng = currentIngredients.get(j);
            int curPer = Math.round(currentMaterial.get(j) * 100f/sum);
            int windex = getIngredients().indexOf(curIng);
            if (Math.round(getMins().get(windex) * 100) > curPer || Math.round(getMaxes().get(windex) * 100) < curPer) {
                return ItemStack.EMPTY;
            }
        }

        return new ItemStack(this.recipeOutput.copy().getItem(),Math.round(sum*matFactor));
    }


    public static class Serializer implements RecipeSerializer<MixingRecipe> {

        public static final MapCodec<MixingRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.withNumbered(RankineMapCodec.fixedKeys("mixTime","ingredientTotal","matScale","fluidInput","result"), "input", 64));

        public static final StreamCodec<RegistryFriendlyByteBuf, MixingRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<MixingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MixingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static MixingRecipe fromJson(JsonObject json) {
            int mixT = json.get("mixTime").getAsInt();
            int ingT = json.get("ingredientTotal").getAsInt();
            float matS = json.has("matScale") ? json.get("matScale").getAsFloat() : 1;
            FluidStack fluidInput = FluidHelper.getFluidStack(GsonHelper.getAsJsonObject(json, "fluidInput"));


            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingT,Ingredient.EMPTY);
            NonNullList<Float> mins = NonNullList.withSize(ingT,0f);
            NonNullList<Float> maxes = NonNullList.withSize(ingT,0f);
            NonNullList<Boolean> reqs = NonNullList.withSize(ingT, false);
            for (int i = 0; i < ingT; i++) {
                String input = "input" + (i+1);
                if (json.has(input)) {
                    JsonObject object = GsonHelper.getAsJsonObject(json, input);
                    ingredients.set(i, AlloyIngredientHelper.deserialize(object,null,null,null));
                    if (object.has("min")){
                        mins.set(i,Math.min(Math.max(object.get("min").getAsFloat(),0f),1f));
                    }

                    if (object.has("max")){
                        maxes.set(i,Math.min(Math.max(object.get("max").getAsFloat(),0f),1f));
                    }

                    if (object.has("required")){
                        reqs.set(i,object.get("required").getAsBoolean());
                    }
                }
            }
            ItemStack result = json.has("result") ? deserializeItem(GsonHelper.getAsJsonObject(json,"result")) : ItemStack.EMPTY;
            return new MixingRecipe(mixT,matS,ingT,fluidInput,ingredients,reqs,mins,maxes,result);
        }

        public static void toJson(MixingRecipe recipe, JsonObject json) {
            json.addProperty("mixTime", recipe.getMixTime());
            json.addProperty("ingredientTotal", recipe.getIngredientTotal());
            json.addProperty("matScale", recipe.getMatScale());
            json.add("fluidInput", FluidHelper.fluidStackToJson(recipe.fluid));
            for (int i = 0; i < recipe.getIngredientTotal(); i++) {
                Ingredient ing = recipe.getIngredients().get(i);
                if (ing.isEmpty()) {
                    continue;
                }
                com.google.gson.JsonElement encoded = AlloyIngredientHelper.ingredientToJson(ing);
                JsonObject object = encoded.isJsonObject() ? encoded.getAsJsonObject() : new JsonObject();
                object.addProperty("min", recipe.getMins().get(i));
                object.addProperty("max", recipe.getMaxes().get(i));
                object.addProperty("required", recipe.getRequired().get(i));
                json.add("input" + (i+1), object);
            }
            if (!recipe.recipeOutput.isEmpty()) {
                json.add("result", AlloyIngredientHelper.itemStackToJson(recipe.recipeOutput));
            }
        }

        public static MixingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int mixT = buffer.readInt();
            int ingT = buffer.readInt();
            float matS = buffer.readFloat();
            FluidStack fluidInput = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingT, Ingredient.EMPTY);
            NonNullList<Float> mins = NonNullList.withSize(ingT,0f);
            NonNullList<Float> maxes = NonNullList.withSize(ingT,0f);
            NonNullList<Boolean> reqs = NonNullList.withSize(ingT, false);

            for (int i = 0; i < ingT; i++) {
                ingredients.set(i,Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
                mins.set(i, buffer.readFloat());
                maxes.set(i, buffer.readFloat());
                reqs.set(i, buffer.readBoolean());

            }

            ItemStack result = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new MixingRecipe(mixT,matS,ingT,fluidInput,ingredients,reqs,mins,maxes,result);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, MixingRecipe recipe) {
            buffer.writeInt(recipe.getMixTime());
            buffer.writeInt(recipe.getIngredientTotal());
            buffer.writeFloat(recipe.getMatScale());
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.getFluid());

            for (int i = 0; i < recipe.getIngredientTotal(); i++) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredients().get(i));
                buffer.writeFloat(recipe.getMins().get(i));
                buffer.writeFloat(recipe.getMaxes().get(i));
                buffer.writeBoolean(recipe.getRequired().get(i));
            }

            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.recipeOutput);
        }
    }

}
