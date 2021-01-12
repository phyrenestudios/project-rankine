package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AlloyIngredientHelper {

    public static Ingredient deserialize(@Nullable JsonElement json) {
        if (json != null && !json.isJsonNull()) {
            Ingredient ret = net.minecraftforge.common.crafting.CraftingHelper.getIngredient(json);
            if (ret != null) return ret;
            if (json.isJsonObject()) {
                return Ingredient.fromItemListStream(Stream.of(deserializeItemList(json.getAsJsonObject())));
            } else if (json.isJsonArray()) {
                JsonArray jsonarray = json.getAsJsonArray();
                if (jsonarray.size() == 0) {
                    throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
                } else {
                    return Ingredient.fromItemListStream(StreamSupport.stream(jsonarray.spliterator(), false).map((element) -> {
                        return deserializeItemList(JSONUtils.getJsonObject(element, "item"));
                    }));
                }
            } else {
                throw new JsonSyntaxException("Expected item to be object or array of objects");
            }
        } else {
            throw new JsonSyntaxException("Item cannot be null");
        }
    }

    public static Ingredient.IItemList deserializeItemList(JsonObject json) {
        if (json.has("item") && json.has("tag")) {
            throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
        } else if (json.has("item")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(JSONUtils.getString(json, "item"));
            Item item = Registry.ITEM.getOptional(resourcelocation1).orElseThrow(() -> {
                return new JsonSyntaxException("Unknown item '" + resourcelocation1 + "'");
            });
            ItemStack ret = new ItemStack(item);
            if (json.has("alloyData"))
            {
                AlloyItem.addAlloy(ret,new AlloyData(JSONUtils.getString(json, "alloyData")));
            }
            return new Ingredient.SingleItemList(ret);
        } else if (json.has("tag")) {
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(json, "tag"));
            ITag<Item> itag = TagCollectionManager.getManager().getItemTags().get(resourcelocation);
            if (itag == null) {
                throw new JsonSyntaxException("Unknown item tag '" + resourcelocation + "'");
            } else {
                return new Ingredient.TagList(itag);
            }
        } else {
            throw new JsonParseException("An ingredient entry needs either a tag or an item");
        }
    }
}
