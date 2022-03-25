package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantmentTypes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class WitheringCurseEnchantment extends Enchantment {

    public WitheringCurseEnchantment(Enchantment.Rarity p_i46721_1_, EquipmentSlot... p_i46721_2_) {
        super(p_i46721_1_, RankineEnchantmentTypes.ALLOYTOOL, p_i46721_2_);
    }

    public int getMinCost(int p_77321_1_) {
        return 10 + 7 * (p_77321_1_ - 1);
    }

    public int getMaxCost(int p_223551_1_) {
        return super.getMinCost(p_223551_1_) + 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return EnchantmentCategory.BREAKABLE.canEnchant(stack.getItem());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    protected boolean checkCompatibility(Enchantment ench) {
        return super.checkCompatibility(ench);
    }

    @Override
    public boolean isCurse() {
        return true;
    }
}
