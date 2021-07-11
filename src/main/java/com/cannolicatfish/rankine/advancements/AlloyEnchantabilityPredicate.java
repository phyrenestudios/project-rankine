package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class AlloyEnchantabilityPredicate extends ItemPredicate {

    int ench;
    Item item;
    public AlloyEnchantabilityPredicate(int ench, Item itemIn) {
        this.ench = ench;
        this.item = itemIn;
    }

    public AlloyEnchantabilityPredicate(JsonObject jsonObject) {
        this(JSONUtils.getInt(jsonObject, "ench"),JSONUtils.getItem(jsonObject,"item"));
    }

    @Override
    public boolean test(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyTool && stack.getItem() == item) {
            return ((IAlloyTool) stack.getItem()).getAlloyEnchantability(stack) >= ench;
        }


        return false;
    }
}
