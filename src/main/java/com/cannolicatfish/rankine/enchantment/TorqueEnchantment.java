package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantmentTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class TorqueEnchantment extends Enchantment {
    public TorqueEnchantment(Rarity p_i46721_1_, EquipmentSlot... p_i46721_2_) {
        super(p_i46721_1_, RankineEnchantmentTypes.CROWBAR, p_i46721_2_);
    }

    public int getMinCost(int p_77321_1_) {
        return 1 + 10 * (p_77321_1_ - 1);
    }

    public int getMaxCost(int p_223551_1_) {
        return super.getMinCost(p_223551_1_) + 50;
    }

    public int getMaxLevel() {
        return 5;
    }

}
