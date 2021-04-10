package com.cannolicatfish.rankine.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class GasProtectionEnchantment extends Enchantment {
    public GasProtectionEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
        super(rarityIn, EnchantmentType.ARMOR_HEAD, slots);
    }
    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 10;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return EnchantmentType.ARMOR_HEAD.canEnchantItem(stack.getItem());
    }
    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel() {
        return 1;
    }

}
