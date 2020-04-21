package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.items.tools.ItemHammer;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;

public class ModEnchantmentType {
    public EnchantmentType HAMMER = EnchantmentType.create("hammer", (itemIn) -> {
        return itemIn instanceof ItemHammer; });
    
}
