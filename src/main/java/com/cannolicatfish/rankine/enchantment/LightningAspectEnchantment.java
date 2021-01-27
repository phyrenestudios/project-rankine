package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.items.tools.HammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class LightningAspectEnchantment extends Enchantment {
    public LightningAspectEnchantment(Enchantment.Rarity p_i46730_1_, EquipmentSlotType... p_i46730_2_) {
        super(p_i46730_1_, EnchantmentType.create("hammer", (itemIn) -> {
            return itemIn instanceof HammerItem; }), p_i46730_2_);
    }

    public int getMinEnchantability(int p_77321_1_) {
        return 10 + 20 * (p_77321_1_ - 1);
    }

    public int getMaxEnchantability(int p_223551_1_) {
        return super.getMinEnchantability(p_223551_1_) + 50;
    }

    public int getMaxLevel() {
        return 1;
    }
}