package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.JSONUtils;

public class ExactCompositionPredicate extends ItemPredicate {

    String str;
    Item item;

    public ExactCompositionPredicate(String str, Item itemIn) {
        this.str = str;
        this.item = itemIn;
    }

    public ExactCompositionPredicate(JsonObject jsonObject) {
        this(JSONUtils.getAsString(jsonObject, "comp"),JSONUtils.getAsItem(jsonObject,"item"));
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyItem && stack.getItem() == item) {
            return IAlloyItem.getAlloyComposition(stack).equals(str);
        }
        return false;
    }
}
