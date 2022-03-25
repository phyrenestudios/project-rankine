package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.GsonHelper;

public class HarvestLevelPredicate extends ItemPredicate {

    int level;
    Item item;

    public HarvestLevelPredicate(int level, Item itemIn) {
        this.level = level;
        this.item = itemIn;
    }

    public HarvestLevelPredicate(JsonObject jsonObject) {
        this(GsonHelper.getAsInt(jsonObject, "level"),GsonHelper.getAsItem(jsonObject,"item"));
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyTool && stack.getItem() == item) {
            return ((IAlloyTool) stack.getItem()).getAlloyHarvestLevel(stack) == level;
        }
        return false;
    }
}
