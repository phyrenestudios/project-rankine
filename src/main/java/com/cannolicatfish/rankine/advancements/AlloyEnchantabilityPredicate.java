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
    ResourceLocation tag;
    public AlloyEnchantabilityPredicate(int ench, ResourceLocation tagIn) {
        this.ench = ench;
        this.tag = tagIn;
    }

    public AlloyEnchantabilityPredicate(JsonObject jsonObject) {
        this(JSONUtils.getInt(jsonObject, "ench"),new ResourceLocation(JSONUtils.getString(jsonObject,"tag")));
    }

    @Override
    public boolean test(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyTool && stack.getItem().getTags().contains(tag)) {
            return ((IAlloyTool) stack.getItem()).getAlloyEnchantability(stack) >= ench;
        }


        return false;
    }
}
