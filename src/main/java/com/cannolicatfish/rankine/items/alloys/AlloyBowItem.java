package com.cannolicatfish.rankine.items.alloys;

import net.minecraft.item.BowItem;
import net.minecraft.util.ResourceLocation;

public class AlloyBowItem extends BowItem implements IAlloyTool {
    public AlloyBowItem(Properties builder) {
        super(builder);
    }

    @Override
    public String getDefaultComposition() {
        return null;
    }

    @Override
    public ResourceLocation getDefaultRecipe() {
        return null;
    }
}
