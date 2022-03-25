package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FusionFurnaceRecipe implements Recipe<Container> {

    //public static final FusionFurnaceRecipe.Serializer SERIALIZER = new FusionFurnaceRecipe.Serializer();
    protected int cookTime;
    protected FluidStack fluidIn;
    protected ItemStack gasIn;
    protected Ingredient ingredient1;
    protected Ingredient ingredient2;
    protected FluidStack fluidOut;
    protected ItemStack gasOut;
    protected ItemStack result1;
    protected ItemStack result2;

    protected final ResourceLocation id;
    public static final FusionFurnaceRecipe.Serializer SERIALIZER = new FusionFurnaceRecipe.Serializer();

    public FusionFurnaceRecipe(ResourceLocation id, FluidStack fluidIn, ItemStack gasIn, Ingredient input1, Ingredient input2, FluidStack fluidOut, ItemStack gasOut, ItemStack output1,ItemStack output2,int cookTimeIn) {
        this.id = id;
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

    public String getGroup() {
        return "";
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return false;
    }

    public boolean matchesRecipe(Container inv, FluidTank tankIn, FluidTank tankOut, Level worldIn) {
        boolean fluidInCheck = this.fluidIn.isEmpty() || tankIn.getFluid().containsFluid(this.fluidIn);
        boolean fluidOutCheck = this.fluidIn.isEmpty() || tankOut.isFluidValid(this.fluidOut);
        return (this.ingredient1.test(inv.getItem(0)) || this.ingredient1.test(inv.getItem(1)))
                && (this.ingredient2.test(inv.getItem(1)) || this.ingredient2.test(inv.getItem(0)))
                && inv.getItem(3).sameItem(this.gasIn) && fluidInCheck && fluidOutCheck;
    }

    public Ingredient getIngredient1() {
        return ingredient1;
    }

    public Ingredient getIngredient2() {
        return ingredient2;
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

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.FUSION_FURNACE;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = GsonHelper.getAsString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = GsonHelper.getAsInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    public List<ItemStack> getResults() {
        return Arrays.asList(this.result1,this.result2,this.gasOut);
    }

    public FluidStack getFluidIn() {
        return fluidIn;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>>  implements RecipeSerializer<FusionFurnaceRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "fusion_furnace");
        public FusionFurnaceRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int w = json.has("cookTime") ? json.get("cookTime").getAsInt() : 400;
            FluidStack fIn = json.has("fluidInput") ? FluidHelper.getFluidStack(GsonHelper.getAsJsonObject(json, "fluidInput")) : FluidStack.EMPTY;
            ItemStack gIn = json.has("gasInput") ? deserializeItem(GsonHelper.getAsJsonObject(json,"gasInput")) : ItemStack.EMPTY;
            Ingredient in1 = json.has("input1") ? Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input1")) : Ingredient.EMPTY;
            Ingredient in2 = json.has("input2") ? Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input2")) : Ingredient.EMPTY;
            ItemStack output1 = json.has("result1") ? deserializeItem(GsonHelper.getAsJsonObject(json,"result1")) : ItemStack.EMPTY;
            ItemStack output2 = json.has("result2") ? deserializeItem(GsonHelper.getAsJsonObject(json,"result2")) : ItemStack.EMPTY;
            ItemStack gOut = json.has("gasOutput") ? deserializeItem(GsonHelper.getAsJsonObject(json,"gasOutput")) : ItemStack.EMPTY;
            FluidStack fOut = json.has("fluidOutput") ? FluidHelper.getFluidStack(GsonHelper.getAsJsonObject(json, "fluidOutput")) : FluidStack.EMPTY;

            return new FusionFurnaceRecipe(recipeId,fIn,gIn,in1,in2,fOut,gOut,output1,output2,w);
        }

        public FusionFurnaceRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int w = buffer.readInt();
            FluidStack fIn = buffer.readFluidStack();
            ItemStack gIn = buffer.readItem();
            Ingredient in1 = Ingredient.fromNetwork(buffer);
            Ingredient in2 = Ingredient.fromNetwork(buffer);
            ItemStack output1 = buffer.readItem();
            ItemStack output2 = buffer.readItem();
            ItemStack gOut = buffer.readItem();
            FluidStack fOut = buffer.readFluidStack();
            return new FusionFurnaceRecipe(recipeId,fIn,gIn,in1,in2,fOut,gOut,output1,output2,w);
        }

        public void toNetwork(FriendlyByteBuf buffer, FusionFurnaceRecipe recipe) {
            buffer.writeInt(recipe.cookTime);
            buffer.writeFluidStack(recipe.fluidIn);
            buffer.writeItem(recipe.gasIn);
            recipe.ingredient1.toNetwork(buffer);
            recipe.ingredient2.toNetwork(buffer);
            buffer.writeItem(recipe.result1);
            buffer.writeItem(recipe.result2);
            buffer.writeItem(recipe.gasOut);
            buffer.writeFluidStack(recipe.fluidOut);
        }
    }

}

