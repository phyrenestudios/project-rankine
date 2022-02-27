package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantmentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class EndotoxinEnchantment extends Enchantment {
    public EndotoxinEnchantment(Enchantment.Rarity p_i46721_1_, EquipmentSlotType... p_i46721_2_) {
        super(p_i46721_1_, RankineEnchantmentTypes.ENDER_AMALGAM_CROWBAR, p_i46721_2_);
    }

    public int getMinEnchantability(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 20;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return RankineEnchantmentTypes.ENDER_AMALGAM_CROWBAR.canEnchantItem(stack.getItem());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canGenerateInLoot() {
        return false;
    }
}
