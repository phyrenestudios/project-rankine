package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.AlloyPickaxe;
import com.cannolicatfish.rankine.items.alloys.AlloyToolInterface;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.JSONUtils;

public class ExactCompositionPredicate extends ItemPredicate {

    String str;

    public ExactCompositionPredicate(String str) {
        this.str = str;
    }

    public ExactCompositionPredicate(JsonObject jsonObject) {
        this(JSONUtils.getString(jsonObject, "comp"));
    }

    @Override
    public boolean test(ItemStack item) {
        INBT nbt = AlloyItem.getComposition(item).getCompound(0).get("comp");
        if (nbt != null)
        {
            return nbt.getString().equals(str);
        }
        return false;
    }
}
