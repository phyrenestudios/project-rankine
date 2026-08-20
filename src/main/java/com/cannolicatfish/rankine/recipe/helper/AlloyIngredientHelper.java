package com.cannolicatfish.rankine.recipe.helper;

import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AlloyIngredientHelper {

    /** 1.21.1 replacement for {@code CraftingHelper.getIngredient(json)}. */
    public static Ingredient ingredientFromJson(@Nullable JsonElement json) {
        if (json == null || json.isJsonNull()) {
            throw new JsonSyntaxException("Item cannot be null");
        }
        return Ingredient.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(JsonSyntaxException::new);
    }

    /** Serializes an ingredient back to its JSON representation (encode side of the codecs). */
    public static JsonElement ingredientToJson(Ingredient ingredient) {
        return Ingredient.CODEC.encodeStart(JsonOps.INSTANCE, ingredient).getOrThrow(IllegalStateException::new);
    }

    /**
     * Encode counterpart of {@link #deserialize}: writes an ingredient back out together with the
     * per-key alloy constraints ({@code alloyComp}, {@code alloyRecipe}, {@code langName},
     * {@code color}) that {@code deserialize} baked into its example stacks.
     *
     * <p>{@link #ingredientToJson} alone loses them: vanilla's {@code Ingredient.CODEC} encodes an
     * {@code ItemValue} through {@code ItemStack.SIMPLE_ITEM_CODEC}, which keeps the item id and
     * drops every data component. Since {@code AlloyCraftingRecipe.checkMatch} reads the constraint
     * off {@code ingredient.getItems()[0]}, dropping it on encode silently widens the recipe to any
     * stack of that item — which is what happened to the 40 tier-3 tool recipes that 1.18.2
     * restricted to steel rods.
     */
    public static JsonElement alloyIngredientToJson(Ingredient ingredient) {
        JsonElement element = ingredientToJson(ingredient);
        ItemStack sample = constraintSample(ingredient);
        if (sample == null) {
            return element;
        }
        if (!(element instanceof JsonObject object)) {
            // Only the single-value {"item": ...} form has somewhere to hang the constraint; the
            // list form has no per-entry slot for it (1.18.2 had the same limitation — its
            // deserializer only read alloyComp/alloyRecipe off a JSON object). Fail loudly rather
            // than write a file that silently accepts any alloy.
            throw new IllegalStateException("Alloy constraint on an ingredient that does not encode "
                    + "to a JSON object (" + element + "); it cannot be expressed in the recipe key");
        }
        String composition = IAlloyItem.getAlloyComposition(sample);
        if (!composition.isEmpty()) {
            object.addProperty("alloyComp", composition);
        }
        ResourceLocation alloyRecipe = IAlloyItem.getAlloyRecipe(sample);
        if (alloyRecipe != null) {
            object.addProperty("alloyRecipe", alloyRecipe.toString());
        }
        String name = IAlloyItem.getNameOverride(sample);
        if (!name.isEmpty()) {
            object.addProperty("langName", name);
        }
        int color = IAlloyItem.getColorNBT(sample);
        if (color != 16777215) {
            object.addProperty("color", color);
        }
        return object;
    }

    /**
     * The example stack an ingredient's alloy constraint lives on, or null if it carries none.
     *
     * <p>Reads {@code getValues()} rather than {@code getItems()} on purpose: {@code getItems()}
     * expands tag ingredients against the item registry's tag bindings, which do not exist during
     * datagen, and caches whatever it found.
     */
    @Nullable
    private static ItemStack constraintSample(Ingredient ingredient) {
        if (ingredient.isCustom()) {
            return null;
        }
        Ingredient.Value[] values = ingredient.getValues();
        ItemStack sample = null;
        for (Ingredient.Value value : values) {
            if (value instanceof Ingredient.ItemValue itemValue && carriesAlloyData(itemValue.item())) {
                sample = itemValue.item();
                break;
            }
        }
        if (sample != null && values.length != 1) {
            throw new IllegalStateException("Alloy constraint on a multi-value ingredient; the recipe "
                    + "key format can only express it on a single item");
        }
        return sample;
    }

    private static boolean carriesAlloyData(ItemStack stack) {
        return !IAlloyItem.getAlloyComposition(stack).isEmpty()
                || IAlloyItem.getAlloyRecipe(stack) != null
                || !IAlloyItem.getNameOverride(stack).isEmpty()
                || IAlloyItem.getColorNBT(stack) != 16777215;
    }

    /**
     * Serializes an ItemStack to the legacy {@code {"item": id, "count": n}} layout.
     * Stacks carrying non-default data components are written using the vanilla
     * {@code {"id","count","components"}} layout instead (accepted back by {@link #getItemStack}).
     */
    public static JsonObject itemStackToJson(ItemStack stack) {
        JsonObject json = new JsonObject();
        if (stack.isEmpty()) {
            return json;
        }
        if (!stack.getComponentsPatch().isEmpty()) {
            JsonElement encoded = ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, stack).result().orElse(null);
            if (encoded != null && encoded.isJsonObject()) {
                return encoded.getAsJsonObject();
            }
        }
        json.addProperty("item", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
        if (stack.getCount() != 1) {
            json.addProperty("count", stack.getCount());
        }
        return json;
    }

    public static Ingredient deserialize(@Nullable JsonElement json, @Nullable String alloyComp, @Nullable String alloyRecipe, @Nullable String name) {
        if (json != null && !json.isJsonNull()) {
            Ingredient ret = ingredientFromJson(json);
            if (alloyRecipe != null)
            {
                List<ItemStack> stacks = new ArrayList<>();
                for (ItemStack s : ret.getItems())
                {
                    IAlloyItem.createDirectAlloyNBT(s,alloyComp,alloyRecipe,name);
                    stacks.add(s);
                }
                ret = Ingredient.of(stacks.toArray(new ItemStack[0]));
            }

            return ret;
        } else {
            throw new JsonSyntaxException("Item cannot be null");
        }
    }

    public static Ingredient deserialize(@Nullable JsonElement json, @Nullable String alloyComp, @Nullable String alloyRecipe, @Nullable String name, int color) {
        if (json != null && !json.isJsonNull()) {
            Ingredient ret = ingredientFromJson(json);
            if (alloyComp != null || alloyRecipe != null || name != null || color != 16777215)
            {
                List<ItemStack> stacks = new ArrayList<>();
                for (ItemStack s : ret.getItems())
                {
                    IAlloyItem.createDirectAlloyNBT(s,alloyComp,alloyRecipe,name);
                    IAlloyItem.addColorNBT(s,color);
                    stacks.add(s);
                }
                ret = Ingredient.of(stacks.toArray(new ItemStack[0]));
            }

            return ret;
        } else {
            throw new JsonSyntaxException("Item cannot be null");
        }
    }



    public static ItemStack getItemStack(JsonObject json, boolean readNBT)
    {
        return getItemStack(json, readNBT, true);
    }

    public static ItemStack getItemStack(JsonObject json, boolean readNBT, boolean includeCount)
    {
        // New-style stacks written with the vanilla codec layout ("id"/"count"/"components").
        if (!json.has("item") && json.has("id")) {
            return ItemStack.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(JsonSyntaxException::new);
        }

        String itemName = GsonHelper.getAsString(json, "item");

        Item item = BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(itemName))
                .orElseThrow(() -> new JsonSyntaxException("Unknown item '" + itemName + "'"));

        int count = includeCount ? GsonHelper.getAsInt(json, "count", 1) : 1;
        ItemStack ret = new ItemStack(item, count);

        if (json.has("components")) {
            // 1.21+ data component syntax on the legacy "item" layout.
            JsonObject copy = new JsonObject();
            copy.addProperty("id", itemName);
            copy.addProperty("count", count);
            copy.add("components", json.get("components"));
            return ItemStack.CODEC.parse(JsonOps.INSTANCE, copy).getOrThrow(JsonSyntaxException::new);
        }

        if (readNBT && json.has("nbt"))
        {
            // PORT-TODO: 1.18.2 arbitrary stack NBT no longer exists (data components replaced it).
            // Best effort: map the rankine-specific keys that recipes historically used
            // (comp/recipe/color/nameOverride); any other keys are ignored.
            JsonElement element = json.get("nbt");
            if (element.isJsonObject()) {
                JsonObject nbt = element.getAsJsonObject();
                String comp = nbt.has("comp") ? nbt.get("comp").getAsString() : null;
                String recipe = nbt.has("recipe") ? nbt.get("recipe").getAsString() : null;
                String nameOverride = nbt.has("nameOverride") ? nbt.get("nameOverride").getAsString() : null;
                if (comp != null || recipe != null) {
                    IAlloyItem.createDirectAlloyNBT(ret, comp, recipe, nameOverride, ret.getItem() instanceof IAlloyTool);
                } else if (nameOverride != null) {
                    ret.set(RankineDataComponents.NAME_OVERRIDE.get(), nameOverride);
                }
                if (nbt.has("color")) {
                    IAlloyItem.addColorNBT(ret, nbt.get("color").getAsInt());
                }
            }
            return ret;
        }

        if (json.has("alloyComp") || json.has("alloyRecipe"))
        {

            String alloyComp = json.has("alloyComp") ? GsonHelper.getAsString(json, "alloyComp") : "";
            String alloyRecipe = json.has("alloyRecipe") ? GsonHelper.getAsString(json, "alloyRecipe") : "";
            IAlloyItem.createDirectAlloyNBT(ret,alloyComp,alloyRecipe,null,ret.getItem() instanceof IAlloyTool);
        }
        return ret;
    }

}
