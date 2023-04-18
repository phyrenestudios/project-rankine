package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class TreetappingRecipe implements Recipe<Container> {

    protected Ingredient ingredient;
    protected FluidStack result;
    protected final ResourceLocation id;
    protected int tapTime;

    public TreetappingRecipe(ResourceLocation id, Ingredient input, FluidStack output, int tapTimeIn) {
        this.id = id;
        this.ingredient = input;
        this.result = output;
        this.tapTime = tapTimeIn;
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
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess registryAccess) {
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
    public ItemStack getResultItem(RegistryAccess registryAccess) {
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
    public ResourceLocation getId() {
        return this.id;
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

        Block block = BuiltInRegistries.BLOCK.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonParseException("Unknown block '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return BlockRecipeHelper.getBlockItemStack(object);
        }
    }

    public static class Serializer implements RecipeSerializer<TreetappingRecipe> {

        @Override
        public TreetappingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int w = json.has("tapTime") ? json.get("tapTime").getAsInt() : 400;
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
            FluidStack result = FluidHelper.getFluidStack(GsonHelper.getAsJsonObject(json, "result"));
            return new TreetappingRecipe(recipeId,ingredient,result,w);
        }

        @Nullable
        @Override
        public TreetappingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

            Ingredient input = Ingredient.fromNetwork(buffer);

            FluidStack output = buffer.readFluidStack();

            int time = buffer.readInt();
            return new TreetappingRecipe(recipeId,input,output,time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TreetappingRecipe recipe) {
            recipe.getIngredient().toNetwork(buffer);
            buffer.writeFluidStack(recipe.getResult());
            buffer.writeInt(recipe.getTapTime());
        }
    }

}
