package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import com.cannolicatfish.rankine.recipe.helper.RankineMapCodec;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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

public class TreetappingRecipe implements Recipe<RecipeInput> {

    protected Ingredient ingredient;
    protected FluidStack result;
    protected int tapTime;

    public TreetappingRecipe(Ingredient input, FluidStack output, int tapTimeIn) {
        this.ingredient = input;
        this.result = output;
        this.tapTime = tapTimeIn;
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
        return this.ingredient.test(inv.getItem(0));
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
        return NonNullList.withSize(1,ingredient);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public FluidStack getResult() {
        return this.result.copy();
    }

    public FluidStack getFilledResult() {
        FluidStack res = this.result.copy();
        res.setAmount(1000);
        return res;
    }

    public int getTapTime() {
        return tapTime;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.TREETAPPING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.TREETAPPING.get();
    }

    public static ItemStack deserializeBlock(JsonObject object) {
        String s = GsonHelper.getAsString(object, "block");

        BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(s)).orElseThrow(() -> {
            return new JsonParseException("Unknown block '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return BlockRecipeHelper.getBlockItemStack(object);
        }
    }

    public static class Serializer implements RecipeSerializer<TreetappingRecipe> {

        public static final MapCodec<TreetappingRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.fixedKeys("tapTime","input","result"));

        public static final StreamCodec<RegistryFriendlyByteBuf, TreetappingRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<TreetappingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TreetappingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static TreetappingRecipe fromJson(JsonObject json) {
            int w = json.has("tapTime") ? json.get("tapTime").getAsInt() : 400;
            Ingredient ingredient = AlloyIngredientHelper.ingredientFromJson(json.get("input"));
            FluidStack result = FluidHelper.getFluidStack(GsonHelper.getAsJsonObject(json, "result"));
            return new TreetappingRecipe(ingredient,result,w);
        }

        public static void toJson(TreetappingRecipe recipe, JsonObject json) {
            json.addProperty("tapTime", recipe.getTapTime());
            json.add("input", AlloyIngredientHelper.ingredientToJson(recipe.getIngredient()));
            json.add("result", FluidHelper.fluidStackToJson(recipe.result));
        }

        public static TreetappingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            FluidStack output = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            int time = buffer.readInt();
            return new TreetappingRecipe(input,output,time);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, TreetappingRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());
            FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.getResult());
            buffer.writeInt(recipe.getTapTime());
        }
    }

}
