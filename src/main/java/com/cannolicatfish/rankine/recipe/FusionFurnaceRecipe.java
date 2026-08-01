package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineItems;
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
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.Arrays;
import java.util.List;

public class FusionFurnaceRecipe implements Recipe<RecipeInput> {

    protected int cookTime;
    protected FluidStack fluidIn;
    protected ItemStack gasIn;
    protected Ingredient ingredient1;
    protected Ingredient ingredient2;
    protected FluidStack fluidOut;
    protected ItemStack gasOut;
    protected ItemStack result1;
    protected ItemStack result2;

    public FusionFurnaceRecipe(FluidStack fluidIn, ItemStack gasIn, Ingredient input1, Ingredient input2, FluidStack fluidOut, ItemStack gasOut, ItemStack output1,ItemStack output2,int cookTimeIn) {
        this.ingredient1 = input1;
        this.ingredient2 = input2;
        this.fluidIn = fluidIn;
        this.gasIn = gasIn;
        this.fluidOut = fluidOut;
        this.gasOut = gasOut;
        this.result1 = output1;
        this.result2 = output2;
        this.cookTime = cookTimeIn;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public boolean matches(RecipeInput inv, Level worldIn) {
        return false;
    }

    /**
     * Slot layout matches the 1.18.2 fusion furnace container: 0 and 1 are the item inputs, 3 is the gas input.
     */
    public boolean matchesRecipe(RecipeInput inv, FluidTank tankIn, FluidTank tankOut, Level worldIn) {
        boolean fluidInCheck = this.fluidIn.isEmpty() || tankIn.getFluid().containsFluid(this.fluidIn);
        boolean fluidOutCheck = this.fluidIn.isEmpty() || tankOut.isFluidValid(this.fluidOut);
        return ((this.ingredient2.test(inv.getItem(0)) && this.ingredient1.test(inv.getItem(1)))
                || (this.ingredient1.test(inv.getItem(0)) && this.ingredient2.test(inv.getItem(1))))
                && (this.gasIn.isEmpty() || ItemStack.isSameItem(inv.getItem(3), this.gasIn)) && fluidInCheck && fluidOutCheck;
    }

    public Ingredient getIngredient1() {
        return ingredient1;
    }

    public Ingredient getIngredient2() {
        return ingredient2;
    }

    @Override
    public ItemStack assemble(RecipeInput inv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY,this.ingredient1,this.ingredient2,Ingredient.of(this.gasIn));
    }

    public NonNullList<ItemStack> getRecipeOutputs() {
        return NonNullList.of(ItemStack.EMPTY,this.result1,this.result2,this.gasOut);
    }

    public NonNullList<ItemStack> getRecipeOutputsJEI() {
        if (this.getRecipeOutputs().contains(ItemStack.EMPTY))
        {
            NonNullList<ItemStack> newJeiCopy = this.getRecipeOutputs();
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
        return this.getRecipeOutputs();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public ItemStack getGasIn() {
        return gasIn;
    }

    public ItemStack getGasOut() {
        return gasOut;
    }

    public FluidStack getFluidOut() {
        return fluidOut;
    }

    public ItemStack getResult1() {
        return result1;
    }

    public ItemStack getResult2() {
        return result2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.FUSION_FURNACE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.FUSION_FURNACE.get();
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

    public List<ItemStack> getResults() {
        return Arrays.asList(this.result1,this.result2,this.gasOut);
    }

    public FluidStack getFluidIn() {
        return fluidIn;
    }

    public static class Serializer implements RecipeSerializer<FusionFurnaceRecipe> {

        public static final MapCodec<FusionFurnaceRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.fixedKeys("cookTime","fluidInput","gasInput","input1","input2","result1","result2","gasOutput","fluidOutput"));

        public static final StreamCodec<RegistryFriendlyByteBuf, FusionFurnaceRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<FusionFurnaceRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FusionFurnaceRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static FusionFurnaceRecipe fromJson(JsonObject json) {
            int w = json.has("cookTime") ? json.get("cookTime").getAsInt() : 400;
            FluidStack fIn = json.has("fluidInput") ? FluidHelper.getFluidStack(GsonHelper.getAsJsonObject(json, "fluidInput")) : FluidStack.EMPTY;
            ItemStack gIn = json.has("gasInput") ? deserializeItem(GsonHelper.getAsJsonObject(json,"gasInput")) : ItemStack.EMPTY;
            Ingredient in1 = json.has("input1") ? AlloyIngredientHelper.ingredientFromJson(json.get("input1")) : Ingredient.EMPTY;
            Ingredient in2 = json.has("input2") ? AlloyIngredientHelper.ingredientFromJson(json.get("input2")) : Ingredient.EMPTY;
            ItemStack output1 = json.has("result1") ? deserializeItem(GsonHelper.getAsJsonObject(json,"result1")) : ItemStack.EMPTY;
            ItemStack output2 = json.has("result2") ? deserializeItem(GsonHelper.getAsJsonObject(json,"result2")) : ItemStack.EMPTY;
            ItemStack gOut = json.has("gasOutput") ? deserializeItem(GsonHelper.getAsJsonObject(json,"gasOutput")) : ItemStack.EMPTY;
            FluidStack fOut = json.has("fluidOutput") ? FluidHelper.getFluidStack(GsonHelper.getAsJsonObject(json, "fluidOutput")) : FluidStack.EMPTY;

            return new FusionFurnaceRecipe(fIn,gIn,in1,in2,fOut,gOut,output1,output2,w);
        }

        public static void toJson(FusionFurnaceRecipe recipe, JsonObject json) {
            json.addProperty("cookTime", recipe.cookTime);
            if (!recipe.fluidIn.isEmpty()) json.add("fluidInput", FluidHelper.fluidStackToJson(recipe.fluidIn));
            if (!recipe.gasIn.isEmpty()) json.add("gasInput", AlloyIngredientHelper.itemStackToJson(recipe.gasIn));
            if (!recipe.ingredient1.isEmpty()) json.add("input1", AlloyIngredientHelper.ingredientToJson(recipe.ingredient1));
            if (!recipe.ingredient2.isEmpty()) json.add("input2", AlloyIngredientHelper.ingredientToJson(recipe.ingredient2));
            if (!recipe.result1.isEmpty()) json.add("result1", AlloyIngredientHelper.itemStackToJson(recipe.result1));
            if (!recipe.result2.isEmpty()) json.add("result2", AlloyIngredientHelper.itemStackToJson(recipe.result2));
            if (!recipe.gasOut.isEmpty()) json.add("gasOutput", AlloyIngredientHelper.itemStackToJson(recipe.gasOut));
            if (!recipe.fluidOut.isEmpty()) json.add("fluidOutput", FluidHelper.fluidStackToJson(recipe.fluidOut));
        }

        public static FusionFurnaceRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int w = buffer.readInt();
            FluidStack fIn = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack gIn = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            Ingredient in1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient in2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output1 = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack output2 = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack gOut = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            FluidStack fOut = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            return new FusionFurnaceRecipe(fIn,gIn,in1,in2,fOut,gOut,output1,output2,w);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, FusionFurnaceRecipe recipe) {
            buffer.writeInt(recipe.cookTime);
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.fluidIn);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.gasIn);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient2);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.result1);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.result2);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.gasOut);
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.fluidOut);
        }
    }

}
