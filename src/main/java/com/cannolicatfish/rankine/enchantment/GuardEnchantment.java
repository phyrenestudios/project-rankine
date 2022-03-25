package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantmentTypes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;

public class GuardEnchantment extends Enchantment {

    public GuardEnchantment(Enchantment.Rarity p_i46721_1_, EquipmentSlot... p_i46721_2_) {
        super(p_i46721_1_, EnchantmentCategory.ARMOR, p_i46721_2_);
    }

    public int getMinCost(int enchantmentLevel) {
        return 10 * enchantmentLevel;
    }

    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 30;
    }
}
