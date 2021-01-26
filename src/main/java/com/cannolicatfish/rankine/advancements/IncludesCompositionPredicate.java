package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.JSONUtils;

public class IncludesCompositionPredicate extends ItemPredicate {

    String str;

    public IncludesCompositionPredicate(String str) {
        this.str = str;
    }

    public IncludesCompositionPredicate(JsonObject jsonObject) {
        this(JSONUtils.getString(jsonObject, "comp"));
    }

    @Override
    public boolean test(ItemStack item) {
        INBT nbt = AlloyItem.getComposition(item).getCompound(0).get("comp");
        if (nbt != null)
        {
            return nbt.getString().contains(str);
        }
        return false;
    }
}
