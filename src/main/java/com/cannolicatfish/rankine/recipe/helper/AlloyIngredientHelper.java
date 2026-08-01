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
