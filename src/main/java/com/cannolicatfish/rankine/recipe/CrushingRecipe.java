package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.RankineMapCodec;
import com.cannolicatfish.rankine.util.WeightedRemovableCollection;
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
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CrushingRecipe implements Recipe<RecipeInput> {

    private final NonNullList<Ingredient> recipeItems;
    private final NonNullList<ItemStack> recipeOutputs;
    private final NonNullList<Boolean> recipeConstants;
    private final int maxRolls;
    private final NonNullList<Float> weights;
    private final NonNullList<Tier> tiers;
    private final NonNullList<ResourceLocation> tierNames;
    private final NonNullList<Tuple<ItemStack,Tier>> guaranteedOutputs;
    private final NonNullList<ResourceLocation> guaranteedTierNames;

    // ------------------------------------------------------------------
    // PORT-TODO: TierSortingRegistry was removed in NeoForge 1.21. Tier names from recipe JSONs are
    // resolved through this lookup instead. Vanilla names are pre-registered; other mods'/rankine
    // custom tiers can be added via registerTier(...) during common setup (before recipes load).
    // ------------------------------------------------------------------
    private static final Map<ResourceLocation, Tier> TIER_BY_NAME = new ConcurrentHashMap<>();
    private static final Map<Tier, ResourceLocation> NAME_BY_TIER = new ConcurrentHashMap<>();
    private static final Map<Tier, Integer> TIER_LEVELS = new ConcurrentHashMap<>();

    public static void registerTier(ResourceLocation name, Tier tier, int level) {
        TIER_BY_NAME.put(name, tier);
        NAME_BY_TIER.put(tier, name);
        TIER_LEVELS.put(tier, level);
    }

    static {
        registerTier(ResourceLocation.withDefaultNamespace("wood"), Tiers.WOOD, 0);
        registerTier(ResourceLocation.withDefaultNamespace("gold"), Tiers.GOLD, 0);
        registerTier(ResourceLocation.withDefaultNamespace("stone"), Tiers.STONE, 1);
        registerTier(ResourceLocation.withDefaultNamespace("iron"), Tiers.IRON, 2);
        registerTier(ResourceLocation.withDefaultNamespace("diamond"), Tiers.DIAMOND, 3);
        registerTier(ResourceLocation.withDefaultNamespace("netherite"), Tiers.NETHERITE, 4);
    }

    public static Tier tierByName(ResourceLocation name) {
        return TIER_BY_NAME.get(name);
    }

    public static ResourceLocation nameOfTier(Tier tier) {
        return NAME_BY_TIER.get(tier);
    }

    /** Harvest-level ordering used to replace TierSortingRegistry comparisons. */
    public static int tierLevel(Tier tier) {
        Integer registered = TIER_LEVELS.get(tier);
        if (registered != null) {
            return registered;
        }
        // Fall back to matching the incorrect-blocks tag against the vanilla tags.
        var tag = tier.getIncorrectBlocksForDrops();
        if (tag == net.minecraft.tags.BlockTags.INCORRECT_FOR_WOODEN_TOOL || tag == net.minecraft.tags.BlockTags.INCORRECT_FOR_GOLD_TOOL) return 0;
        if (tag == net.minecraft.tags.BlockTags.INCORRECT_FOR_STONE_TOOL) return 1;
        if (tag == net.minecraft.tags.BlockTags.INCORRECT_FOR_IRON_TOOL) return 2;
        if (tag == net.minecraft.tags.BlockTags.INCORRECT_FOR_DIAMOND_TOOL) return 3;
        if (tag == net.minecraft.tags.BlockTags.INCORRECT_FOR_NETHERITE_TOOL) return 4;
        return 0;
    }

    /** Replacement for {@code !TierSortingRegistry.getTiersLowerThan(required).contains(harvest)}. */
    private static boolean tierQualifies(Tier harvestLevel, Tier required) {
        return tierLevel(harvestLevel) >= tierLevel(required);
    }

    public CrushingRecipe(NonNullList<Ingredient> recipeItemsIn, NonNullList<Tuple<ItemStack,Tier>> guaranteedOutputs, NonNullList<ItemStack> recipeOutputsIn,
                          NonNullList<Float> weightsIn, NonNullList<Tier> tiersIn, NonNullList<Boolean> recipeConstantsIn, int maxRollsIn) {
        this.recipeItems = recipeItemsIn;
        this.recipeOutputs = recipeOutputsIn;
        this.recipeConstants = recipeConstantsIn;
        this.weights = weightsIn;
        this.tiers = tiersIn;
        this.guaranteedOutputs = guaranteedOutputs;
        this.maxRolls = maxRollsIn;
        this.tierNames = NonNullList.withSize(tiersIn.size(), ResourceLocation.withDefaultNamespace("wood"));
        for (int i = 0; i < tiersIn.size(); i++) {
            ResourceLocation name = nameOfTier(tiersIn.get(i));
            this.tierNames.set(i, name != null ? name : ResourceLocation.withDefaultNamespace("wood"));
        }
        this.guaranteedTierNames = NonNullList.withSize(guaranteedOutputs.size(), ResourceLocation.withDefaultNamespace("wood"));
        for (int i = 0; i < guaranteedOutputs.size(); i++) {
            ResourceLocation name = nameOfTier(guaranteedOutputs.get(i).getB());
            this.guaranteedTierNames.set(i, name != null ? name : ResourceLocation.withDefaultNamespace("wood"));
        }
    }

    @Override
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
    public boolean matches(RecipeInput inv, Level worldIn) {
        return this.recipeItems.get(0).test(inv.getItem(0));
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

    public List<ItemStack> getResults(Tier harvestLevel, RandomSource random, int rolls) {
        int totalRolls = Math.min(rolls,maxRolls);
        List<ItemStack> outputs = new ArrayList<>();
        for (Tuple<ItemStack, Tier> tuple : getGuaranteedOutputsAsItemStack()) {
            if (tierQualifies(harvestLevel, tuple.getB()))  {
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

    public Tuple<List<ItemStack>,Integer> getAtomizeResults(Tier harvestLevel, RandomSource random, int rolls) {
        int totalRolls = Math.min(rolls,maxRolls);
        List<ItemStack> guaranteed = new ArrayList<>();
        for (Tuple<ItemStack, Tier> tuple : getGuaranteedOutputsAsItemStack()) {
            if (tierQualifies(harvestLevel, tuple.getB()))  {
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

    public WeightedRemovableCollection<ItemStack> getPossibleResults(Tier harvestLevel, RandomSource random) {
        WeightedRemovableCollection<ItemStack> outputs = new WeightedRemovableCollection<>(new java.util.Random(random.nextLong()));
        for (int i = 0; i < this.getRecipeOutputs().size(); i++) {
            if (tierQualifies(harvestLevel, this.tiers.get(i))) {
                outputs.add(this.weights.get(i),this.getRecipeOutputs().get(i),this.getRecipeConstants().get(i));
            }
        }
        return outputs;
    }

    public List<Float> getWeightsByTier(Tier harvestLevel) {
        List<Float> retWeights = new ArrayList<>();
        for (int i = 0; i < this.getRecipeOutputs().size(); i++) {
            if (tierQualifies(harvestLevel, this.tiers.get(i))) {
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
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.CRUSHING_RECIPE_SERIALIZER.get();
    }


    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.CRUSHING.get();
    }


    public static ItemStack deserializeCrushingItem(JsonObject object) {

        if (!object.has("item")) {
            return ItemStack.EMPTY;
        }
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

    public static class Serializer implements RecipeSerializer<CrushingRecipe> {

        public static final MapCodec<CrushingRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.fixedKeys("input","maxRolls","guaranteed","outputs"));

        public static final StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<CrushingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static CrushingRecipe fromJson(JsonObject json) {

            NonNullList<Ingredient> input = NonNullList.of(Ingredient.EMPTY, AlloyIngredientHelper.ingredientFromJson(json.get("input")));

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
                        Tier curTier = tierByName(ResourceLocation.parse(object.get("tier").getAsString()));
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
                        Tier curTier = tierByName(ResourceLocation.parse(object.get("tier").getAsString()));
                        if (curTier != null) {
                            minTiers.set(index, curTier);
                        }
                    }

                    constants.set(index,!object.has("remove") || object.get("remove").getAsBoolean());

                }
                index++;
            }

            return new CrushingRecipe(input, guaranteedOutputs, outputs, weights, minTiers, constants,totalRolls);
        }

        public static void toJson(CrushingRecipe recipe, JsonObject json) {
            json.add("input", AlloyIngredientHelper.ingredientToJson(recipe.getIngredients().get(0)));
            json.addProperty("maxRolls", recipe.getMaxRolls());
            JsonArray guaranteed = new JsonArray();
            for (int i = 0; i < recipe.getGuaranteedOutputs().size(); i++) {
                Tuple<ItemStack,Tier> tuple = recipe.getGuaranteedOutputs().get(i);
                JsonObject object = AlloyIngredientHelper.itemStackToJson(tuple.getA());
                object.addProperty("tier", recipe.guaranteedTierNames.get(i).toString());
                guaranteed.add(object);
            }
            json.add("guaranteed", guaranteed);
            JsonArray outputs = new JsonArray();
            for (int i = 0; i < recipe.getRecipeOutputs().size(); i++) {
                JsonObject object = AlloyIngredientHelper.itemStackToJson(recipe.getRecipeOutputs().get(i));
                object.addProperty("weight", recipe.getWeights().get(i));
                object.addProperty("tier", recipe.tierNames.get(i).toString());
                object.addProperty("remove", recipe.getRecipeConstants().get(i));
                outputs.add(object);
            }
            json.add("outputs", outputs);
        }

        public static CrushingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            NonNullList<Ingredient> input = NonNullList.of(Ingredient.EMPTY,Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            int totalRolls = buffer.readInt();
            int glen = buffer.readVarInt();
            NonNullList<Tuple<ItemStack,Tier>> guaranteedOutputs = NonNullList.withSize(glen, new Tuple<>(ItemStack.EMPTY,Tiers.WOOD));
            for (int i = 0; i < glen; i++) {
                ItemStack stack = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
                Tier tier = tierByName(buffer.readResourceLocation());
                guaranteedOutputs.set(i,new Tuple<>(stack, tier != null ? tier : Tiers.WOOD));
            }

            int len = buffer.readVarInt();
            NonNullList<ItemStack> outputs = NonNullList.withSize(len, ItemStack.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(len, 1f);
            NonNullList<Tier> minTiers = NonNullList.withSize(len, Tiers.WOOD);
            NonNullList<Boolean> removals = NonNullList.withSize(len,true);
            for (int i = 0; i < len; i++) {
                outputs.set(i,ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer));
                weights.set(i,buffer.readFloat());
                Tier tier = tierByName(buffer.readResourceLocation());
                minTiers.set(i, tier != null ? tier : Tiers.WOOD);
                removals.set(i,buffer.readBoolean());
            }

            return new CrushingRecipe(input,guaranteedOutputs,outputs,weights,minTiers,removals,totalRolls);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, CrushingRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredients().get(0));
            buffer.writeInt(recipe.maxRolls);
            buffer.writeVarInt(recipe.getGuaranteedOutputs().size());
            for (int i = 0; i < recipe.getGuaranteedOutputs().size(); i++) {
                ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.getGuaranteedOutputs().get(i).getA());
                buffer.writeResourceLocation(recipe.guaranteedTierNames.get(i));
            }

            buffer.writeVarInt(recipe.getRecipeOutputs().size());
            for (int i = 0; i < recipe.getRecipeOutputs().size(); i++) {
                ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.getRecipeOutputs().get(i));
                buffer.writeFloat(recipe.getWeights().get(i));
                buffer.writeResourceLocation(recipe.tierNames.get(i));
                buffer.writeBoolean(recipe.getRecipeConstants().get(i));
            }

        }
    }

}
