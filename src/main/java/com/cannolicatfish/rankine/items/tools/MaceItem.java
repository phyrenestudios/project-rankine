package com.cannolicatfish.rankine.items.tools;

import net.minecraft.world.item.*;

import net.minecraft.world.item.Item.Properties;

public class MaceItem extends TieredItem implements Vanishable {
    public MaceItem(Tier p_43308_, Properties p_43309_) {
        super(p_43308_, p_43309_);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.BOW;
    }
    
}
