package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.util.WeightedRemovableCollection;
import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CrushingRecipe implements Recipe<Container> {

    private final NonNullList<Ingredient> recipeItems;
    private final NonNullList<ItemStack> recipeOutputs;
    private final NonNullList<Boolean> recipeConstants;
    private final ResourceLocation id;
    private final int maxRolls;
    private final NonNullList<Float> weights;
    private final NonNullList<Tier> tiers;
    private final NonNullList<Tuple<ItemStack,Tier>> guaranteedOutputs;

    public CrushingRecipe(ResourceLocation idIn, NonNullList<Ingredient> recipeItemsIn, NonNullList<Tuple<ItemStack,Tier>> guaranteedOutputs, NonNullList<ItemStack> recipeOutputsIn,
                          NonNullList<Float> weightsIn, NonNullList<Tier> tiersIn, NonNullList<Boolean> recipeConstantsIn, int maxRollsIn) {
        this.id = idIn;
        this.recipeItems = recipeItemsIn;
        this.recipeOutputs = recipeOutputsIn;
        this.recipeConstants = recipeConstantsIn;
        this.weights = weightsIn;
        this.tiers = tiersIn;
        this.guaranteedOutputs = guaranteedOutputs;
        this.maxRolls = maxRollsIn;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public NonNullList<Float> getWeights() {
        return weights;
    }

    public int getMaxRolls() {
        return maxRolls;
    }

    public NonNullList<Tier> getTiers() {
        return tiers;
    }

    public NonNullList<ItemStack> getRecipeOutputs() {
        return recipeOutputs;
    }

    public NonNullList<Boolean> getRecipeConstants() {
        return recipeConstants;
    }

    public NonNullList<Tuple<ItemStack, Tier>> getGuaranteedOutputs() {
        return guaranteedOutputs;
    }

    public List<Tuple<ItemStack, Tier>> getGuaranteedOutputsAsItemStack() {
        return guaranteedOutputs.stream().map(tuple -> new Tuple<>(tuple.getA(), tuple.getB())).collect(Collectors.toList());
    }

    public Float getChance(int index) {
        float in = getWeights().get(index);
        return (in/getWeights().stream().reduce(0f, Float::sum));
    }

    public Float getChance(Tier tierIn, int index) {
        float in = getWeightsByTier(tierIn).get(index);
        return (in/getWeightsByTier(tierIn).stream().reduce(0f, Float::sum));
    }

    public ItemStack[] getIngredientAsStackList() {
        return this.recipeItems.get(0).getItems().clone();
    }
    @Override
    public boolean matches(Container inv, Level worldIn) {
        return this.recipeItems.get(0).test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public List<ItemStack> getResults(Tier harvestLevel,Random random, int rolls) {
        int totalRolls = Math.min(rolls,maxRolls);
        List<ItemStack> outputs = new ArrayList<>();
        for (Tuple<ItemStack, Tier> tuple : getGuaranteedOutputsAsItemStack()) {
            if (!TierSortingRegistry.getTiersLowerThan(tuple.getB()).contains(harvestLevel))  {
                outputs.add(tuple.getA());
            }
        }
        WeightedRemovableCollection<ItemStack> possibleResults = getPossibleResults(harvestLevel,random);
        for (int i = 0; i < totalRolls; i++) {
            if (possibleResults.getMap().isEmpty()) {
                outputs.removeIf(itemStack -> itemStack.is(Items.AIR) || itemStack.equals(ItemStack.EMPTY));
                return outputs;
            }
            outputs.add(possibleResults.getRandomElement().copy());
        }
        outputs.removeIf(itemStack -> itemStack.is(Items.AIR) || itemStack.equals(ItemStack.EMPTY));
        return outputs;
    }

    public Tuple<List<ItemStack>,Integer> getAtomizeResults(Tier harvestLevel,Random random, int rolls) {
        int totalRolls = Math.min(rolls,maxRolls);
        List<ItemStack> guaranteed = new ArrayList<>();
        for (Tuple<ItemStack, Tier> tuple : getGuaranteedOutputsAsItemStack()) {
            if (!TierSortingRegistry.getTiersLowerThan(tuple.getB()).contains(harvestLevel))  {
                guaranteed.add(tuple.getA());
            }
        }
        List<ItemStack> outputs = new ArrayList<>();
        WeightedRemovableCollection<ItemStack> possibleResults = getPossibleResults(harvestLevel,random);
        for (int i = 0; i < totalRolls; i++) {
            if (possibleResults.getMap().isEmpty()) {
                outputs.removeIf(itemStack -> itemStack.is(Items.AIR) || itemStack.equals(ItemStack.EMPTY));
                return new Tuple<>(guaranteed,outputs.size());
            }
            outputs.add(possibleResults.getRandomElement().copy());
        }
        outputs.removeIf(itemStack -> itemStack.is(Items.AIR) || itemStack.equals(ItemStack.EMPTY));
        return new Tuple<>(guaranteed,outputs.size());
    }

    public WeightedRemovableCollection<ItemStack> getPossibleResults(Tier harvestLevel, Random random) {
        WeightedRemovableCollection<ItemStack> outputs = new WeightedRemovableCollection<>(random);
        for (int i = 0; i < this.getRecipeOutputs().size(); i++) {
            if (!TierSortingRegistry.getTiersLowerThan(this.tiers.get(i)).contains(harvestLevel)) {
                outputs.add(this.weights.get(i),this.getRecipeOutputs().get(i),this.getRecipeConstants().get(i));
            }
        }
        return outputs;
    }

    public List<Float> getWeightsByTier(Tier harvestLevel) {
        List<Float> retWeights = new ArrayList<>();
        for (int i = 0; i < this.getRecipeOutputs().size(); i++) {
            if (!TierSortingRegistry.getTiersLowerThan(this.tiers.get(i)).contains(harvestLevel)) {
                retWeights.add(this.weights.get(i));
            } else {
                retWeights.add(0f);
            }
        }
        return retWeights;
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
        return RankineRecipeSerializers.CRUSHING_RECIPE_SERIALIZER.get();
    }


    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.CRUSHING;
    }


    public static ItemStack deserializeCrushingItem(JsonObject object) {

        if (!object.has("item")) {
            return ItemStack.EMPTY;
        }
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

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>>  implements RecipeSerializer<CrushingRecipe> {
        public CrushingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

            NonNullList<Ingredient> input = NonNullList.of(Ingredient.EMPTY, Ingredient.fromJson(json.get("input")));

            JsonArray guaranteedArray = new JsonArray();

            int totalRolls = json.has("maxRolls") ? json.get("maxRolls").getAsInt() : 10;

            if (json.has("guaranteed")) {
                guaranteedArray = GsonHelper.getAsJsonArray(json, "guaranteed");
            }
            int guaranteedArraySize = Math.max(guaranteedArray.size(),1);

            JsonArray outputsArray = new JsonArray();

            if (json.has("outputs")) {
                outputsArray = GsonHelper.getAsJsonArray(json, "outputs");
            }
            int outputsArraySize = Math.max(outputsArray.size(),1);

            NonNullList<Tuple<ItemStack,Tier>> guaranteedOutputs = NonNullList.withSize(guaranteedArraySize, new Tuple<>(ItemStack.EMPTY, Tiers.WOOD));
            int gindex = 0;
            for (JsonElement element : guaranteedArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    Tier currentTier = Tiers.WOOD;
                    if (object.has("tier")) {
                        Tier curTier = TierSortingRegistry.byName(new ResourceLocation(object.get("tier").getAsString()));
                        if (curTier != null) {
                            currentTier = curTier;
                        }
                    }
                    guaranteedOutputs.set(gindex, new Tuple<>(deserializeCrushingItem(object),currentTier));
                }
                gindex++;
            }


            NonNullList<ItemStack> outputs = NonNullList.withSize(outputsArraySize, ItemStack.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(outputsArraySize, 1f);
            NonNullList<Tier> minTiers = NonNullList.withSize(outputsArraySize, Tiers.WOOD);
            NonNullList<Boolean> constants = NonNullList.withSize(outputsArraySize, true);

            int index = 0;
            for (JsonElement element : outputsArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    outputs.set(index,deserializeCrushingItem(object));

                    if (object.has("weight")) {
                        weights.set(index, object.get("weight").getAsFloat());
                    }

                    if (object.has("tier")) {
                        Tier curTier = TierSortingRegistry.byName(new ResourceLocation(object.get("tier").getAsString()));
                        if (curTier != null) {
                            minTiers.set(index, curTier);
                        }
                    }

                    constants.set(index,!object.has("remove") || object.get("remove").getAsBoolean());

                }
                index++;
            }

            return new CrushingRecipe(recipeId, input, guaranteedOutputs, outputs, weights, minTiers, constants,totalRolls);
        }

        public CrushingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> input = NonNullList.of(Ingredient.EMPTY,Ingredient.fromNetwork(buffer));
            int totalRolls = buffer.readInt();
            int glen = buffer.readVarInt();
            NonNullList<Tuple<ItemStack,Tier>> guaranteedOutputs = NonNullList.withSize(glen, new Tuple<>(ItemStack.EMPTY,Tiers.WOOD));
            for (int i = 0; i < glen; i++) {
                guaranteedOutputs.set(i,new Tuple<>(buffer.readItem(),TierSortingRegistry.byName(buffer.readResourceLocation())));
            }

            int len = buffer.readVarInt();
            NonNullList<ItemStack> outputs = NonNullList.withSize(len, ItemStack.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(len, 1f);
            NonNullList<Tier> minTiers = NonNullList.withSize(len, Tiers.WOOD);
            NonNullList<Boolean> removals = NonNullList.withSize(len,true);
            for (int i = 0; i < len; i++) {
                outputs.set(i,buffer.readItem());
                weights.set(i,buffer.readFloat());
                minTiers.set(i,TierSortingRegistry.byName(buffer.readResourceLocation()));
                removals.set(i,buffer.readBoolean());
            }

            return new CrushingRecipe(recipeId,input,guaranteedOutputs,outputs,weights,minTiers,removals,totalRolls);
        }

        public void toNetwork(FriendlyByteBuf buffer, CrushingRecipe recipe) {
            for(Ingredient i: recipe.getIngredients()) {
                i.toNetwork(buffer);
            }
            buffer.writeInt(recipe.maxRolls);
            buffer.writeVarInt(recipe.getGuaranteedOutputs().size());
            for (int i = 0; i < recipe.getGuaranteedOutputs().size(); i++) {
                buffer.writeItemStack(recipe.getGuaranteedOutputs().get(i).getA(),true);
                if (TierSortingRegistry.getName(recipe.getGuaranteedOutputs().get(i).getB()) != null) {
                    buffer.writeResourceLocation(TierSortingRegistry.getName(recipe.getGuaranteedOutputs().get(i).getB()));
                } else {
                    buffer.writeResourceLocation(TierSortingRegistry.getName(Tiers.WOOD));
                }


            }

            buffer.writeVarInt(recipe.getRecipeOutputs().size());
            for (int i = 0; i < recipe.getRecipeOutputs().size(); i++) {
                buffer.writeItemStack(recipe.getRecipeOutputs().get(i),true);
                buffer.writeFloat(recipe.getWeights().get(i));
                if (TierSortingRegistry.getName(recipe.getTiers().get(i)) != null) {
                    buffer.writeResourceLocation(TierSortingRegistry.getName(recipe.getTiers().get(i)));
                } else {
                    buffer.writeResourceLocation(TierSortingRegistry.getName(Tiers.WOOD));
                }

                buffer.writeBoolean(recipe.getRecipeConstants().get(i));
            }

        }
    }

}