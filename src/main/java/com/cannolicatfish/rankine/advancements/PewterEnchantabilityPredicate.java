package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtilsEnum;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.JSONUtils;

public class PewterEnchantabilityPredicate extends ItemPredicate {

    int ench;

    public PewterEnchantabilityPredicate(int ench) {
        this.ench = ench;
    }

    public PewterEnchantabilityPredicate(JsonObject jsonObject) {
        this(JSONUtils.getInt(jsonObject, "ench"));
    }

    @Override
    public boolean test(ItemStack item) {
        if (item.getItem() instanceof IAlloyTool) {
            IAlloyTool it = (IAlloyTool) item.getItem();
            INBT nbt = AlloyItem.getComposition(item).getCompound(0).get("comp");
            if (nbt != null)
            {
                IAlloyTool e = (IAlloyTool) item.getItem();
                AlloyUtils alloyUtils = it.returnAlloyUtils();
                return alloyUtils.equals(AlloyUtilsEnum.PEWTER) && e.getAlloyEnchantability(nbt.getString(), alloyUtils) >= ench;
            }
        }

        return false;
    }
}
