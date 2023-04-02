package com.cannolicatfish.rankine.data.builders;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class AlloyCraftingRecipeBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Item result;
    private final int count;
    private final boolean inherit;
    private final String inheritRecipe;
    private final List<String> pattern = Lists.newArrayList();
    private final Map<Character, AlloyIngredient> key = Maps.newLinkedHashMap();
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();
    private String group;
    private final String langName;
    private final int color;

    public AlloyCraftingRecipeBuilder(ItemLike resultIn, int countIn, boolean inheritIn, String inheritRecipeIn, String langNameIn, int colorIn) {
        this.result = resultIn.asItem();
        this.count = countIn;
        this.inherit = inheritIn;
        this.inheritRecipe = inheritRecipeIn;
        this.langName = langNameIn;
        this.color = colorIn;
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static AlloyCraftingRecipeBuilder shapedRecipe(ItemLike resultIn) {
        return shapedRecipe(resultIn, 1);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static AlloyCraftingRecipeBuilder shapedRecipe(ItemLike resultIn, int countIn) {
        return shapedRecipe(resultIn, countIn, false, "","", 16777215);
    }

    public static AlloyCraftingRecipeBuilder shapedRecipe(ItemLike resultIn, int countIn, boolean inheritIn) {
        return new AlloyCraftingRecipeBuilder(resultIn, countIn, inheritIn, "","", 16777215);
    }

    public static AlloyCraftingRecipeBuilder shapedRecipe(ItemLike resultIn, int countIn, boolean inheritIn, String inheritRecipe) {
        return new AlloyCraftingRecipeBuilder(resultIn, countIn, inheritIn, inheritRecipe, "", 16777215);
    }

    public static AlloyCraftingRecipeBuilder shapedRecipe(ItemLike resultIn, int countIn, boolean inheritIn, String inheritRecipe, String langNameIn) {
        return new AlloyCraftingRecipeBuilder(resultIn, countIn, inheritIn, inheritRecipe, langNameIn, 16777215);
    }

    public static AlloyCraftingRecipeBuilder shapedRecipe(ItemLike resultIn, int countIn, boolean inheritIn, String inheritRecipe, String langNameIn, int color) {
        return new AlloyCraftingRecipeBuilder(resultIn, countIn, inheritIn, inheritRecipe, langNameIn, color);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public AlloyCraftingRecipeBuilder key(Character symbol, TagKey<Item> tagIn) {
        return this.key(symbol, Ingredient.of(tagIn));
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public AlloyCraftingRecipeBuilder key(Character symbol, ItemLike itemIn) {
        return this.key(symbol, Ingredient.of(itemIn));
    }

    public AlloyCraftingRecipeBuilder alloyKey(Character symbol, TagKey<Item> tagIn, String compositionReqsIn, ResourceLocation alloyRecipeIn,String langNameIn,int colorIn) {
        return this.key(symbol, new AlloyIngredient(Ingredient.of(tagIn),compositionReqsIn,alloyRecipeIn,langNameIn,colorIn));
    }

    public AlloyCraftingRecipeBuilder alloyKey(Character symbol, ItemLike itemIn, String compositionReqsIn, ResourceLocation alloyRecipeIn,String langNameIn,int colorIn) {
        return this.key(symbol, new AlloyIngredient(Ingredient.of(itemIn),compositionReqsIn,alloyRecipeIn,langNameIn,colorIn));
    }

    public AlloyCraftingRecipeBuilder directAlloyKey(Character symbol, AlloyIngredient alloyIngredientIn) {
        return this.key(symbol, alloyIngredientIn);
    }



    /**
     * Adds a key to the recipe pattern.
     */
    public AlloyCraftingRecipeBuilder key(Character symbol, AlloyIngredient ingredientIn) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredientIn);
            return this;
        }
    }

    public AlloyCraftingRecipeBuilder key(Character symbol, Ingredient ingredientIn) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, new AlloyIngredient(ingredientIn,null,null,null,16777215));
            return this;
        }
    }

    /**
     * Adds a new entry to the patterns for this recipe.
     */
    public AlloyCraftingRecipeBuilder patternLine(String patternIn) {
        if (!this.pattern.isEmpty() && patternIn.length() != this.pattern.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.pattern.add(patternIn);
            return this;
        }
    }

    /**
     * Adds a criterion needed to unlock the recipe.
     */
    public AlloyCraftingRecipeBuilder addCriterion(String name, CriterionTriggerInstance criterionIn) {
        this.advancementBuilder.addCriterion(name, criterionIn);
        return this;
    }

    public AlloyCraftingRecipeBuilder setGroup(String groupIn) {
        this.group = groupIn;
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        this.build(consumerIn, Registry.ITEM.getKey(this.result));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result);
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Shaped Recipe " + save + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, new ResourceLocation(save));
        }
    }


    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        this.validate(id);
        this.advancementBuilder.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        consumerIn.accept(new AlloyCraftingRecipeBuilder.Result(id, this.result, this.count, this.inherit,this.inheritRecipe,this.group == null ? "" : this.group, this.pattern, this.key, this.advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + id.getPath()),this.langName,this.color));
    }

    /**
     * Makes sure that this recipe is valid and obtainable.
     */
    private void validate(ResourceLocation id) {
        if (this.pattern.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');

            for(String s : this.pattern) {
                for(int i = 0; i < s.length(); ++i) {
                    char c0 = s.charAt(i);
                    if (!this.key.containsKey(c0) && c0 != ' ') {
                        throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c0 + "'");
                    }

                    set.remove(c0);
                }
            }

            if (!set.isEmpty()) {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
            } else if (this.advancementBuilder.getCriteria().isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + id);
            }
        }
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final boolean inherit;
        private final String inheritRecipe;
        private final String group;
        private final List<String> pattern;
        private final String langName;
        private final int color;
        private final Map<Character, AlloyIngredient> key;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation idIn, Item resultIn, int countIn, boolean inheritIn, String inheritRecipeIn, String groupIn, List<String> patternIn, Map<Character, AlloyIngredient> keyIn, Advancement.Builder advancementBuilderIn, ResourceLocation advancementIdIn, String langNameIn, int colorIn) {
            this.id = idIn;
            this.result = resultIn;
            this.count = countIn;
            this.inherit = inheritIn;
            this.inheritRecipe = inheritRecipeIn;
            this.group = groupIn;
            this.pattern = patternIn;
            this.key = keyIn;
            this.advancementBuilder = advancementBuilderIn;
            this.advancementId = advancementIdIn;
            this.langName = langNameIn;
            this.color = colorIn;
        }

        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }

            json.addProperty("inherit", this.inherit);
            if (color != 16777215) {
                json.addProperty("color",this.color);
            }
            if(!this.langName.isEmpty()) {
                json.addProperty("langName", this.langName);
            }

            if(!this.inheritRecipe.isEmpty()) {
                json.addProperty("inheritRecipe", this.inheritRecipe);
            }
            JsonArray jsonarray = new JsonArray();

            for(String s : this.pattern) {
                jsonarray.add(s);
            }

            json.add("pattern", jsonarray);
            JsonObject jsonobject = new JsonObject();

            for(Map.Entry<Character, AlloyIngredient> entry : this.key.entrySet()) {
                jsonobject.add(String.valueOf(entry.getKey()), alloySerializer(entry.getValue().getIngredient().toJson(),entry.getValue()));
            }

            json.add("key", jsonobject);
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("item", Registry.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject1.addProperty("count", this.count);
            }

            json.add("result", jsonobject1);
        }

        private JsonElement alloySerializer(JsonElement array, AlloyIngredient ingredient) {
            if (ingredient.getAlloyRecipe() != null) {
                ((JsonObject) array).addProperty("alloyRecipe",ingredient.getAlloyRecipe().toString());
            }
            if (ingredient.getLangName() != null && !ingredient.getLangName().isEmpty()) {
                ((JsonObject) array).addProperty("langName",ingredient.getLangName());
            }
            if (ingredient.getColor() != 16777215) {
                ((JsonObject) array).addProperty("color",ingredient.getColor());
            }
            return array;
        }

        public RecipeSerializer<?> getType() {
            return RankineRecipeSerializers.ALLOY_CRAFTING_RECIPE_SERIALIZER.get();
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getId() {
            return this.id;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancementBuilder.serializeToJson();
        }


        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}

