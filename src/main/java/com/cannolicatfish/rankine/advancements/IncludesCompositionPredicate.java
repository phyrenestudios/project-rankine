package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.JSONUtils;

public class IncludesCompositionPredicate extends ItemPredicate {

    String str;
    Item item;
    public IncludesCompositionPredicate(String str, Item itemIn) {
        this.str = str;
        this.item = itemIn;
    }

    public IncludesCompositionPredicate(JsonObject jsonObject) {
        this(JSONUtils.getString(jsonObject, "comp"),JSONUtils.getItem(jsonObject,"item"));
    }

    @Override
    public boolean test(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyItem && stack.getItem() == item) {
            return ((IAlloyItem) stack.getItem()).getAlloyComposition(stack).contains(str);
        }
        return false;
    }
}
