package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.items.tools.HammerItem;
import net.minecraft.enchantment.EnchantmentType;

public class RankineEnchantmentTypes {
    public EnchantmentType HAMMER = EnchantmentType.create("hammer", (itemIn) -> {
        return itemIn instanceof HammerItem; });
    
}
