package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelTile;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
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
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MixingRecipe implements Recipe<Container> {

    public static final MixingRecipe.Serializer SERIALIZER = new MixingRecipe.Serializer();

    private final ResourceLocation id;
    private final int mixTime;
    private final int ingredientTotal;
    private final float matScale;
    private final FluidStack fluid;
    private final NonNullList<Ingredient> recipeItems;
    private final NonNullList<Boolean> required;
    private final NonNullList<Float> mins;
    private final NonNullList<Float> maxes;
    private final ItemStack recipeOutput;

    public MixingRecipe(ResourceLocation idIn, int mixTimeIn, float matScaleIn, int ingredientTotalIn, FluidStack fluidIn,
                        NonNullList<Ingredient> recipeItemsIn, NonNullList<Boolean> requiredIn, NonNullList<Float> minsIn, NonNullList<Float> maxesIn, ItemStack outputIn) {
        this.id = idIn;
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

    @Override
    public boolean matches(Container inv, Level worldIn) {
        if (inv instanceof MixingBarrelTile) {
            return ((MixingBarrelTile)inv).getInputTank().getFluid().containsFluid(getOutputFluidReq(inv)) && !getMixingResult(inv,worldIn).isEmpty();
        } else {
            return !getMixingResult(inv,worldIn).isEmpty();
        }
    }

    @Override
    public ItemStack assemble(Container inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem() {
        return this.recipeOutput.copy();
    }

    public int getMixTime() {
        return mixTime;
    }

    public int getOutputMixTime(Container inv) {
        int sum = 0;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            sum += inv.getItem(i).getCount();
        }
        return Math.round(mixTime * (sum*getMatScale()));
    }

    public FluidStack getOutputFluidReq(Container inv) {
        int sum = 0;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            sum += inv.getItem(i).getCount();
        }

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
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.MIXING;
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

    public ItemStack getMixingResult(Container inv, Level worldIn) {
        List<Ingredient> currentIngredients = new ArrayList<>();
        List<Integer> currentMaterial = new ArrayList<>();
        for (int i = 0; i < inv.getContainerSize(); i++) {
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


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MixingRecipe> {

        @Override
        public MixingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
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
            return new MixingRecipe(recipeId,mixT,matS,ingT,fluidInput,ingredients,reqs,mins,maxes,result);
        }

        @Nullable
        @Override
        public MixingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int mixT = buffer.readInt();
            int ingT = buffer.readInt();
            float matS = buffer.readFloat();
            FluidStack fluidInput = buffer.readFluidStack();

            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingT, Ingredient.EMPTY);
            NonNullList<Float> mins = NonNullList.withSize(ingT,0f);
            NonNullList<Float> maxes = NonNullList.withSize(ingT,0f);
            NonNullList<Boolean> reqs = NonNullList.withSize(ingT, false);

            for (int i = 0; i < ingT; i++) {
                ingredients.set(i,Ingredient.fromNetwork(buffer));
                mins.set(i, buffer.readFloat());
                maxes.set(i, buffer.readFloat());
                reqs.set(i, buffer.readBoolean());

            }

            ItemStack result = buffer.readItem();

            return new MixingRecipe(recipeId,mixT,matS,ingT,fluidInput,ingredients,reqs,mins,maxes,result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MixingRecipe recipe) {
            buffer.writeInt(recipe.getMixTime());
            buffer.writeInt(recipe.getIngredientTotal());
            buffer.writeFloat(recipe.getMatScale());
            buffer.writeFluidStack(recipe.getFluid());

            for (int i = 0; i < recipe.getIngredientTotal(); i++) {
                recipe.getIngredients().get(i).toNetwork(buffer);
                buffer.writeFloat(recipe.getMins().get(i));
                buffer.writeFloat(recipe.getMaxes().get(i));
                buffer.writeBoolean(recipe.getRequired().get(i));
            }

            buffer.writeItem(recipe.getResultItem());
        }
    }

}
