package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.items.tools.ItemSpear;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class PunctureEnchantment extends Enchantment {
    public PunctureEnchantment(Enchantment.Rarity p_i46721_1_, EquipmentSlotType... p_i46721_2_) {
        super(p_i46721_1_, EnchantmentType.create("puncture", (itemIn) -> {
            return itemIn instanceof ItemSpear; }), p_i46721_2_);
    }

    public int getMinEnchantability(int p_77321_1_) {
        return 1 + 10 * (p_77321_1_ - 1);
    }

    public int getMaxEnchantability(int p_223551_1_) {
        return super.getMinEnchantability(p_223551_1_) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity)
        {
            LivingEntity ent = (LivingEntity) target;
            ent.attackEntityFrom(DamageSource.MAGIC, level*0.5f);
        }
    }
}

