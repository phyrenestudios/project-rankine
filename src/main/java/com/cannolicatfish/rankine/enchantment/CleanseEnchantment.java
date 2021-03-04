package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.items.alloys.AlloySwordItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.util.alloys.AlloyUtilsEnum;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;

import java.util.Iterator;

public class CleanseEnchantment extends Enchantment {
    public CleanseEnchantment(Enchantment.Rarity p_i46721_1_, EquipmentSlotType... p_i46721_2_) {
        super(p_i46721_1_, EnchantmentType.create("stainless", (itemIn) -> itemIn instanceof AlloySwordItem && ((IAlloyTool) itemIn).returnAlloyUtils().equals(AlloyUtilsEnum.STAINLESS)), p_i46721_2_);
    }

    public int getMinEnchantability(int p_77321_1_) {
        return 15;
    }

    public int getMaxEnchantability(int p_223551_1_) {
        return super.getMinEnchantability(p_223551_1_) + 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_);
    }

}
