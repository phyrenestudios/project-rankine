package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.ModEnchantments;
import com.cannolicatfish.rankine.items.tools.ItemHammer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ExcavateEnchantment extends Enchantment {
    public ExcavateEnchantment(Enchantment.Rarity p_i46721_1_, EquipmentSlotType... p_i46721_2_) {
        super(p_i46721_1_, EnchantmentType.create("hammer", (itemIn) -> {
            return itemIn instanceof ItemHammer; }), p_i46721_2_);
    }

    public int getMinEnchantability(int p_77321_1_) {
        return 1 + 10 * (p_77321_1_ - 1);
    }

    public int getMaxEnchantability(int p_223551_1_) {
        return super.getMinEnchantability(p_223551_1_) + 50;
    }

    public int getMaxLevel() {
        return 2;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        if (ench == ModEnchantments.BLAST)
        {
            return false;
        }
        return super.canApplyTogether(ench);
    }
}
