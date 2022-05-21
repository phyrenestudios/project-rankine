package com.cannolicatfish.rankine.items.tools;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;

public class MaceItem extends TieredItem {
    public MaceItem(Tier p_43308_, Properties p_43309_) {
        super(p_43308_, p_43309_);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.BOW;
    }
    
}
