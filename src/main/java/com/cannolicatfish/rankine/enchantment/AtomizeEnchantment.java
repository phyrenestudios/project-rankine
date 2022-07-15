package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantmentTypes;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantments;

public class AtomizeEnchantment extends Enchantment {
    public AtomizeEnchantment(Enchantment.Rarity p_i46721_1_, EquipmentSlot... p_i46721_2_) {
        super(p_i46721_1_, RankineEnchantmentTypes.HAMMER, p_i46721_2_);
    }

    public int getMinCost(int p_77321_1_) {
        return 15;
    }

    public int getMaxCost(int p_223551_1_) {
        return super.getMinCost(p_223551_1_) + 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean checkCompatibility(Enchantment p_77326_1_) {
        if (p_77326_1_.equals(Enchantments.BLOCK_FORTUNE)) {
            return false;
        }
        return super.checkCompatibility(p_77326_1_);
    }
}