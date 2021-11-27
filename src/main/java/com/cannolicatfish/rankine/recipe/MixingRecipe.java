package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MixingRecipe implements IRecipe<IInventory> {

    private final FluidStack fluid;
    private final NonNullList<Ingredient> recipeItems;
    private final int mixTime;
    private final int ingredientTotal;
    private final int outputTotal;
    private final NonNullList<Integer> ingredientGroups;
    private final NonNullList<Boolean> required;
    private final NonNullList<Integer> countMod;
    private final NonNullList<Integer> mixTimeMod;
    private final ResourceLocation id;
    private final NonNullList<ItemStack> recipeOutputs;
    private final NonNullList<Float> weights;
    private final NonNullList<Integer> mins;
    private final NonNullList<Integer> maxes;

    public static final MixingRecipe.Serializer SERIALIZER = new MixingRecipe.Serializer();

    public MixingRecipe(ResourceLocation idIn, int mixTimeIn, int ingredientTotalIn, int outputTotalIn, FluidStack fluidIn, NonNullList<Ingredient> recipeItemsIn, NonNullList<Integer> ingredientGroupsIn, NonNullList<Boolean> requiredIn, NonNullList<Integer> countModIn,
                        NonNullList<Integer> mixTimeModIn, NonNullList<ItemStack> recipeOutputsIn, NonNullList<Float> weightsIn, NonNullList<Integer> minsIn, NonNullList<Integer> maxesIn) {
        this.id = idIn;
        this.fluid = fluidIn;
        this.ingredientGroups = ingredientGroupsIn;
        this.mixTime = mixTimeIn;
        this.ingredientTotal = ingredientTotalIn;
        this.outputTotal = outputTotalIn;
        this.required = requiredIn;
        this.countMod = countModIn;
        this.mixTimeMod = mixTimeModIn;
        this.recipeItems = recipeItemsIn;
        this.recipeOutputs = recipeOutputsIn;
        this.weights = weightsIn;
        this.mins = minsIn;
        this.maxes = maxesIn;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }


    public List<Integer> getIndexList(int val) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < this.getIngredients().size(); i++) {
            if (this.getIngredientGroups().get(i) == val || ((val < 0 || val > 3) && (this.getIngredientGroups().get(i) < 0 || this.getIngredientGroups().get(i) > 3))) {
                ret.add(i);
            }
        }
        return ret;
    }

    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return !getMixingResult(inv,worldIn).isEmpty();
    }

    public ItemStack getMixingResult(IInventory inv, World worldIn) {
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        ItemStack output;
        if (this.recipeOutputs.size() == 0) {
            return ItemStack.EMPTY;
        } else if (this.recipeOutputs.size() == 1) {
            output = new ItemStack(this.recipeOutputs.get(0).getItem(),this.maxes.get(0).equals(this.mins.get(0)) ? this.maxes.get(0) : worldIn.getRandom().nextInt(this.maxes.get(0) - this.mins.get(0)) + this.mins.get(0));
        } else {
            for (int i = 0; i < this.recipeOutputs.size(); i++) {
                ItemStack curOut = this.recipeOutputs.get(i);
                col.add(this.weights.get(i),new ItemStack(curOut.getItem(), this.maxes.get(i).equals(this.mins.get(i)) ? this.maxes.get(i) : worldIn.getRandom().nextInt(this.maxes.get(i) - this.mins.get(i)) + this.mins.get(i)));
            }
            output = col.getRandomElement().copy();
        }

        List<Integer> groupsUsed = new ArrayList<>();
        for (int s = 0; s < 4; s++) {
            ItemStack stack = inv.getStackInSlot(s);
            int workingIndex = -1;
            for (int i = 0; i < this.getIngredients().size(); i++) {
                if (this.getIngredients().get(i).test(stack)) {
                    workingIndex = i;
                    break;
                }
            }
            if (workingIndex == -1) {
                return ItemStack.EMPTY;
            }
            output.grow(this.getCountMod().get(workingIndex));
            groupsUsed.add(this.getIngredientGroups().get(workingIndex));
            if (!(this.getIngredientGroups().get(workingIndex) == -1) && !(Collections.frequency(groupsUsed,this.getIngredientGroups().get(workingIndex)) <= 1)) {
                return ItemStack.EMPTY;
            }
        }

        return output;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    public int getMixTime() {
        return mixTime;
    }

    public int getRecipeMixTime(IInventory inv) {
        int mix = this.getMixTime();
        for (int s = 0; s < 4; s++) {
            ItemStack stack = inv.getStackInSlot(s);
            int workingIndex = -1;
            for (int i = 0; i < this.getIngredients().size(); i++) {
                if (this.getIngredients().get(i).test(stack)) {
                    workingIndex = i;
                    break;
                }
            }
            mix += this.getMixMod().get(workingIndex);
        }
        return mix;
    }

    public int getIngredientTotal() {
        return ingredientTotal;
    }

    public int getOutputTotal() {
        return outputTotal;
    }

    public NonNullList<Boolean> getRequired() {
        return required;
    }

    public NonNullList<Ingredient> getRecipeItems() {
        return recipeItems;
    }

    public NonNullList<Integer> getCountMod() {
        return countMod;
    }

    public NonNullList<Integer> getMixMod() {
        return mixTimeMod;
    }

    public Float getChance(int index) {
        float in = getWeights().get(index);
        return (in/getWeights().stream().reduce(0f, Float::sum));
    }

    public NonNullList<Float> getWeights() {
        return this.weights;
    }

    public NonNullList<ItemStack> getOutputs() {
        return this.recipeOutputs;
    }

    public NonNullList<Integer> getMins() {
        return mins;
    }

    public NonNullList<Integer> getMaxes() {
        return maxes;
    }

    public List<Ingredient> getCondensedIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<ItemStack> stacks = new ArrayList<>();
            for (int s : this.getIndexList(i)) {
                stacks.addAll(Arrays.asList(this.getIngredients().get(s).getMatchingStacks()));
            }
            if (stacks.isEmpty()) {
                stacks.add(new ItemStack(RankineItems.ELEMENT.get()));
            }
            ingredients.add(Ingredient.fromStacks(stacks.toArray(new ItemStack[0])));
        }
        for (int s : this.getIndexList(-1)) {
            ingredients.add(this.getIngredients().get(s));
        }
        return ingredients;
    }

    public NonNullList<Integer> getIngredientGroups() {
        return ingredientGroups;
    }


    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = JSONUtils.getString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true, false);
        }
    }

    @Override
    public IRecipeType<?> getType() {
        return RankineRecipeTypes.MIXING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<MixingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "mixing");
        public MixingRecipe read(ResourceLocation recipeId, JsonObject json) {
            int mixT = json.get("mixTime").getAsInt();
            int ingT = json.get("ingredientTotal").getAsInt();
            int outT = json.get("outputTotal").getAsInt();
            FluidStack fluidInput = FluidHelper.getFluidStack(JSONUtils.getJsonObject(json, "fluidInput"));
            NonNullList<Boolean> requiredbool = NonNullList.withSize(4,false);

            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingT,Ingredient.EMPTY);
            NonNullList<Integer> groups = NonNullList.withSize(ingT,-1);
            NonNullList<Integer> countMods = NonNullList.withSize(ingT,0);
            NonNullList<Integer> mixTimeMods = NonNullList.withSize(ingT,0);

            JsonArray req = JSONUtils.getJsonArray(json,"required");
            for (int i = 0; i < req.size(); i++) {
                requiredbool.set(i,req.get(i).getAsBoolean());
            }
            for (int i = 0; i < ingT; i++) {
                String input = "input" + (i+1);
                if (json.has(input)) {
                    JsonObject object = JSONUtils.getJsonObject(json, input);
                    ingredients.set(i, AlloyIngredientHelper.deserialize(object,null,null,null));
                    if (object.has("group")) {
                        groups.set(i,object.get("group").getAsInt());
                    }
                    if (object.has("countMod")) {
                        countMods.set(i,object.get("countMod").getAsInt());
                    }
                    if (object.has("mixTimeMod")) {
                        mixTimeMods.set(i,object.get("mixTimeMod").getAsInt());
                    }
                }
            }

            NonNullList<ItemStack> stacks = NonNullList.withSize(outT, ItemStack.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(outT, 1f);
            NonNullList<Integer> mins = NonNullList.withSize(outT, 1);
            NonNullList<Integer> maxes = NonNullList.withSize(outT, 1);
            for (int i = 0; i < outT; i++) {
                String output = "output" + (i+1);
                if (json.has(output)) {
                    JsonObject object = JSONUtils.getJsonObject(json, output);
                    stacks.set(i,AlloyCraftingRecipe.deserializeItem(JSONUtils.getJsonObject(json, output)));
                    if (object.has("weight")){
                        weights.set(i,object.get("weight").getAsFloat());
                    } else {
                        weights.set(i,0f);
                    }

                    if (object.has("min")){
                        mins.set(i,object.get("min").getAsInt());
                    } else {
                        mins.set(i,1);
                    }

                    if (object.has("max")){
                        maxes.set(i,object.get("max").getAsInt());
                    } else {
                        maxes.set(i,1);
                    }


                }
            }
            return new MixingRecipe(recipeId,mixT,ingT,outT,fluidInput,ingredients, groups, requiredbool, countMods, mixTimeMods, stacks, weights, mins,maxes);
        }

        public MixingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int mixT = buffer.readInt();
            int ingT = buffer.readInt();
            int resT = buffer.readInt();
            FluidStack input = buffer.readFluidStack();
            NonNullList<Boolean> req = NonNullList.withSize(4, false);

            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingT, Ingredient.EMPTY);
            NonNullList<Integer> groups = NonNullList.withSize(ingT,-1);
            NonNullList<Integer> countMods = NonNullList.withSize(ingT,0);
            NonNullList<Integer> mixTimeMods = NonNullList.withSize(ingT,0);

            for (int i = 0; i < 4; i++) {
                req.set(i,buffer.readBoolean());
            }

            for (int i = 0; i < ingT; i++) {
                ingredients.set(i,Ingredient.read(buffer));
                groups.set(i,buffer.readInt());
                countMods.set(i,buffer.readInt());
                mixTimeMods.set(i,buffer.readInt());

            }

            NonNullList<ItemStack> stacks = NonNullList.withSize(resT, ItemStack.EMPTY);
            for(int k = 0; k < stacks.size(); ++k) {
                stacks.set(k, buffer.readItemStack());
            }

            NonNullList<Float> weights = NonNullList.withSize(resT, 0f);
            for(int k = 0; k < weights.size(); ++k) {
                weights.set(k, buffer.readFloat());
            }


            NonNullList<Integer> mins = NonNullList.withSize(resT,1);
            for(int k = 0; k < mins.size(); ++k) {
                mins.set(k, buffer.readInt());
            }

            NonNullList<Integer> maxes = NonNullList.withSize(resT,1);
            for(int k = 0; k < maxes.size(); ++k) {
                maxes.set(k, buffer.readInt());
            }

            return new MixingRecipe(recipeId,mixT,ingT,resT,input,ingredients, groups, req, countMods, mixTimeMods, stacks, weights, mins,maxes);
        }

        public void write(PacketBuffer buffer, MixingRecipe recipe) {
            buffer.writeInt(recipe.getMixTime());
            buffer.writeInt(recipe.getIngredientTotal());
            buffer.writeInt(recipe.getOutputTotal());
            buffer.writeFluidStack(recipe.getFluid());
            for (int i = 0; i < 4; i++) {
                if (i < recipe.getRequired().size()) {
                    buffer.writeBoolean(recipe.getRequired().get(i));
                } else {
                    buffer.writeBoolean(false);
                }
            }
            for (int i = 0; i < recipe.getIngredientTotal(); i++) {
                recipe.getIngredients().get(i).write(buffer);
                buffer.writeInt(recipe.getIngredientGroups().get(i));
                buffer.writeInt(recipe.getCountMod().get(i));
                buffer.writeInt(recipe.getMixMod().get(i));


            }

            int count = 0;
            for(ItemStack stack : recipe.recipeOutputs) {
                buffer.writeItemStack(stack);
                count++;
            }
            while (count < recipe.outputTotal) {
                buffer.writeItemStack(ItemStack.EMPTY);
                count++;
            }

            count = 0;
            for (float chance : recipe.weights) {
                buffer.writeFloat(chance);
                count++;
            }
            while (count < recipe.outputTotal) {
                buffer.writeFloat(0f);
                count++;
            }

            count = 0;
            for (int add : recipe.mins) {
                buffer.writeInt(add);
                count++;
            }
            while (count < recipe.outputTotal) {
                buffer.writeInt(1);
                count++;
            }

            count = 0;
            for (int add : recipe.maxes) {
                buffer.writeInt(add);
                count++;
            }
            while (count < recipe.outputTotal) {
                buffer.writeInt(1);
                count++;
            }

        }
    }
}
