package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.GsonHelper;

public class ExactCompositionPredicate extends ItemPredicate {

    String str;
    Item item;

    public ExactCompositionPredicate(String str, Item itemIn) {
        this.str = str;
        this.item = itemIn;
    }

    public ExactCompositionPredicate(JsonObject jsonObject) {
        this(GsonHelper.getAsString(jsonObject, "comp"),GsonHelper.getAsItem(jsonObject,"item"));
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyItem && stack.getItem() == item) {
            return IAlloyItem.getAlloyComposition(stack).equals(str);
        }
        return false;
    }
}
