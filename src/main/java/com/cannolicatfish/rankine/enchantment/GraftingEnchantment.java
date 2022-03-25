package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantmentTypes;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;

public class GraftingEnchantment extends Enchantment {
    public GraftingEnchantment(Enchantment.Rarity p_i46721_1_, EquipmentSlot... p_i46721_2_) {
        super(p_i46721_1_, RankineEnchantmentTypes.KNIFE, p_i46721_2_);
    }

    public int getMinCost(int p_77321_1_) {
        return 10;
    }

    public int getMaxCost(int p_223551_1_) {
        return super.getMinCost(p_223551_1_) + 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean checkCompatibility(Enchantment enchantment) {

        return super.checkCompatibility(enchantment);
    }
}
