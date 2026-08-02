package com.cannolicatfish.rankine.data.builders;

import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Datagen builder for {@code rankine:alloy_crafting} recipes.
 *
 * <p>PORT NOTE (1.21.1): {@code FinishedRecipe} is gone — {@link RecipeOutput} takes a real
 * {@code Recipe} instance. The builder still assembles the historical JSON layout (that is the
 * format the serializer's codec reads back) and then hands it to
 * {@link AlloyCraftingRecipe.Serializer#fromJson(JsonObject)} to obtain the recipe object, so the
 * ingredient/alloy-component baking stays identical to the runtime load path.
 */
public class AlloyCraftingRecipeBuilder {
    private final Item result;
    private final int count;
    private final boolean inherit;
    private final String inheritRecipe;
    private final List<String> pattern = Lists.newArrayList();
    private final Map<Character, AlloyIngredient> key = Maps.newLinkedHashMap();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
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

    public static AlloyCraftingRecipeBuilder shapedRecipe(ItemLike resultIn) {
        return shapedRecipe(resultIn, 1);
    }

    public static AlloyCraftingRecipeBuilder shapedRecipe(ItemLike resultIn, int countIn) {
        return shapedRecipe(resultIn, countIn, false, "", "", 16777215);
    }

    public static AlloyCraftingRecipeBuilder shapedRecipe(ItemLike resultIn, int countIn, boolean inheritIn) {
        return new AlloyCraftingRecipeBuilder(resultIn, countIn, inheritIn, "", "", 16777215);
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

    public AlloyCraftingRecipeBuilder key(Character symbol, TagKey<Item> tagIn) {
        return this.key(symbol, Ingredient.of(tagIn));
    }

    public AlloyCraftingRecipeBuilder key(Character symbol, ItemLike itemIn) {
        return this.key(symbol, Ingredient.of(itemIn));
    }

    public AlloyCraftingRecipeBuilder alloyKey(Character symbol, TagKey<Item> tagIn, String compositionReqsIn, ResourceLocation alloyRecipeIn, String langNameIn, int colorIn) {
        return this.key(symbol, new AlloyIngredient(Ingredient.of(tagIn), compositionReqsIn, alloyRecipeIn, langNameIn, colorIn));
    }

    public AlloyCraftingRecipeBuilder alloyKey(Character symbol, ItemLike itemIn, String compositionReqsIn, ResourceLocation alloyRecipeIn, String langNameIn, int colorIn) {
        return this.key(symbol, new AlloyIngredient(Ingredient.of(itemIn), compositionReqsIn, alloyRecipeIn, langNameIn, colorIn));
    }

    public AlloyCraftingRecipeBuilder directAlloyKey(Character symbol, AlloyIngredient alloyIngredientIn) {
        return this.key(symbol, alloyIngredientIn);
    }

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
            this.key.put(symbol, new AlloyIngredient(ingredientIn, null, null, null, 16777215));
            return this;
        }
    }

    public AlloyCraftingRecipeBuilder patternLine(String patternIn) {
        if (!this.pattern.isEmpty() && patternIn.length() != this.pattern.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.pattern.add(patternIn);
            return this;
        }
    }

    public AlloyCraftingRecipeBuilder addCriterion(String name, Criterion<?> criterionIn) {
        this.criteria.put(name, criterionIn);
        return this;
    }

    public AlloyCraftingRecipeBuilder setGroup(String groupIn) {
        this.group = groupIn;
        return this;
    }

    public Item getResult() {
        return this.result;
    }

    public void build(RecipeOutput output) {
        this.build(output, BuiltInRegistries.ITEM.getKey(this.result));
    }

    public void build(RecipeOutput output, String save) {
        ResourceLocation resourcelocation = BuiltInRegistries.ITEM.getKey(this.result);
        ResourceLocation id = ResourceLocation.parse(save);
        if (id.equals(resourcelocation)) {
            throw new IllegalStateException("Shaped Recipe " + save + " should remove its 'save' argument");
        } else {
            this.build(output, id);
        }
    }

    public void build(RecipeOutput output, ResourceLocation id) {
        this.validate(id);

        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        AlloyCraftingRecipe recipe = AlloyCraftingRecipe.Serializer.fromJson(this.toJson());
        recipe.bindId(id);
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/misc/")));
    }

    /** The historical 1.18.2 JSON layout, which is exactly what the serializer's codec reads. */
    private JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (this.group != null && !this.group.isEmpty()) {
            json.addProperty("group", this.group);
        }

        json.addProperty("inherit", this.inherit);
        if (this.color != 16777215) {
            json.addProperty("color", this.color);
        }
        if (this.langName != null && !this.langName.isEmpty()) {
            json.addProperty("langName", this.langName);
        }
        if (this.inheritRecipe != null && !this.inheritRecipe.isEmpty()) {
            json.addProperty("inheritRecipe", this.inheritRecipe);
        }

        JsonArray jsonarray = new JsonArray();
        for (String s : this.pattern) {
            jsonarray.add(s);
        }
        json.add("pattern", jsonarray);

        JsonObject keys = new JsonObject();
        for (Map.Entry<Character, AlloyIngredient> entry : this.key.entrySet()) {
            keys.add(String.valueOf(entry.getKey()),
                    alloySerializer(AlloyIngredientHelper.ingredientToJson(entry.getValue().getIngredient()), entry.getValue()));
        }
        json.add("key", keys);

        JsonObject resultJson = new JsonObject();
        resultJson.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
        if (this.count > 1) {
            resultJson.addProperty("count", this.count);
        }
        json.add("result", resultJson);
        return json;
    }

    private JsonElement alloySerializer(JsonElement element, AlloyIngredient ingredient) {
        if (!(element instanceof JsonObject object)) {
            return element;
        }
        if (ingredient.getCompositonReqs() != null && !ingredient.getCompositonReqs().isEmpty()) {
            object.addProperty("alloyComp", ingredient.getCompositonReqs());
        }
        if (ingredient.getAlloyRecipe() != null) {
            object.addProperty("alloyRecipe", ingredient.getAlloyRecipe().toString());
        }
        if (ingredient.getLangName() != null && !ingredient.getLangName().isEmpty()) {
            object.addProperty("langName", ingredient.getLangName());
        }
        if (ingredient.getColor() != 16777215) {
            object.addProperty("color", ingredient.getColor());
        }
        return object;
    }

    private void validate(ResourceLocation id) {
        if (this.pattern.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
        }
        Set<Character> set = Sets.newHashSet(this.key.keySet());
        set.remove(' ');

        for (String s : this.pattern) {
            for (int i = 0; i < s.length(); ++i) {
                char c0 = s.charAt(i);
                if (!this.key.containsKey(c0) && c0 != ' ') {
                    throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c0 + "'");
                }
                set.remove(c0);
            }
        }

        if (!set.isEmpty()) {
            throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
        } else if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
