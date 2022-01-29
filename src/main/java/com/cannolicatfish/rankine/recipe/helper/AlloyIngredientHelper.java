package com.cannolicatfish.rankine.recipe.helper;

import com.cannolicatfish.rankine.items.alloys.*;
import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AlloyIngredientHelper {

    public static Ingredient deserialize(@Nullable JsonElement json, @Nullable String alloyComp, @Nullable String alloyRecipe, @Nullable String name) {
        if (json != null && !json.isJsonNull()) {
            Ingredient ret = net.minecraftforge.common.crafting.CraftingHelper.getIngredient(json);
            if (alloyRecipe != null)
            {
                List<ItemStack> stacks = new ArrayList<>();
                for (ItemStack s : ret.getMatchingStacks())
                {
                    IAlloyItem.createDirectAlloyNBT(s,alloyComp,alloyRecipe,name);
                    stacks.add(s);
                }
                ret = Ingredient.fromStacks(stacks.toArray(new ItemStack[0]));
            }

            return ret;
        } else {
            throw new JsonSyntaxException("Item cannot be null");
        }
    }

    public static Ingredient deserialize(@Nullable JsonElement json, @Nullable String alloyComp, @Nullable String alloyRecipe, @Nullable String name, int color) {
        if (json != null && !json.isJsonNull()) {
            Ingredient ret = net.minecraftforge.common.crafting.CraftingHelper.getIngredient(json);
            if (alloyComp != null || alloyRecipe != null || name != null || color != 16777215)
            {
                List<ItemStack> stacks = new ArrayList<>();
                for (ItemStack s : ret.getMatchingStacks())
                {
                    IAlloyItem.createDirectAlloyNBT(s,alloyComp,alloyRecipe,name);
                    IAlloyItem.addColorNBT(s,color);
                    stacks.add(s);
                }
                ret = Ingredient.fromStacks(stacks.toArray(new ItemStack[0]));
            }

            return ret;
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
                System.out.println("AlloyData detected in recipe!: " + JSONUtils.getString(json, "alloyData"));
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



    public static ItemStack getItemStack(JsonObject json, boolean readNBT)
    {
        return getItemStack(json, readNBT, true);
    }

    public static ItemStack getItemStack(JsonObject json, boolean readNBT, boolean includeCount)
    {
        String itemName = JSONUtils.getString(json, "item");

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));

        if (item == null)
            throw new JsonSyntaxException("Unknown item '" + itemName + "'");


        if (readNBT && json.has("nbt"))
        {
            try
            {
                JsonElement element = json.get("nbt");
                CompoundNBT nbt;
                if(element.isJsonObject())
                    nbt = JsonToNBT.getTagFromJson(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(element));
                else
                    nbt = JsonToNBT.getTagFromJson(JSONUtils.getString(element, "nbt"));

                CompoundNBT tmp = new CompoundNBT();
                if (nbt.contains("ForgeCaps"))
                {
                    tmp.put("ForgeCaps", nbt.get("ForgeCaps"));
                    nbt.remove("ForgeCaps");
                }

                tmp.put("tag", nbt);
                tmp.putString("id", itemName);
                if (includeCount) {
                    tmp.putInt("Count", JSONUtils.getInt(json, "count", 1));
                } else {
                    tmp.putInt("Count", 1);
                }
                return ItemStack.read(tmp);
            }
            catch (CommandSyntaxException e)
            {
                throw new JsonSyntaxException("Invalid NBT Entry: " + e.toString());
            }
        }

        ItemStack ret = new ItemStack(item, JSONUtils.getInt(json, "count", 1));
        if (json.has("alloyComp") || json.has("alloyRecipe"))
        {

            String alloyComp = json.has("alloyComp") ? JSONUtils.getString(json, "alloyComp") : "";
            String alloyRecipe = json.has("alloyRecipe") ? JSONUtils.getString(json, "alloyRecipe") : "";
            //System.out.println("AlloyData detected in recipe!: " + JSONUtils.getString(json, "alloyData"));
            IAlloyItem.createDirectAlloyNBT(ret,alloyComp,alloyRecipe,null,ret.getItem() instanceof IAlloyTool);
        }
        return ret;
    }

}
