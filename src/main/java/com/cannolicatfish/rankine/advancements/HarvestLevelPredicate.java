package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtilsEnum;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.nbt.INBT;
import net.minecraft.util.JSONUtils;

public class HarvestLevelPredicate extends ItemPredicate {

    int level;

    public HarvestLevelPredicate(int level) {
        this.level = level;
    }

    public HarvestLevelPredicate(JsonObject jsonObject) {
        this(JSONUtils.getInt(jsonObject, "level"));
    }

    @Override
    public boolean test(ItemStack item) {
        if (item.getItem() == RankineItems.BRONZE_PICKAXE.get()) {
            INBT nbt = AlloyItem.getComposition(item).getCompound(0).get("comp");
            if (nbt != null)
            {
                IAlloyTool e = (IAlloyTool) item.getItem();
                AlloyUtils alloyUtils = null;
                for (AlloyUtilsEnum s: AlloyUtilsEnum.values())
                {
                    if (s.getMaterial() == ((TieredItem) item.getItem()).getTier()) {
                        alloyUtils = s;
                    }
                }
                if (alloyUtils == null)
                {
                    alloyUtils = AlloyUtilsEnum.BRONZE;
                }
                return e.getAlloyHarvestLevel(item) >= level;
            }
        }
        return false;
    }
}
