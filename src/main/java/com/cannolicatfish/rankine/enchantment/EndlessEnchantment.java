package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantmentTypes;
import com.cannolicatfish.rankine.items.alloys.AlloyKnifeItem;
import com.cannolicatfish.rankine.items.alloys.AlloyPickaxeItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class EndlessEnchantment extends Enchantment {
    public EndlessEnchantment(Rarity p_i46721_1_, EquipmentSlot... p_i46721_2_) {
        super(p_i46721_1_, RankineEnchantmentTypes.ENDER_AMALGAM_PICKAXE, p_i46721_2_);
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
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            return rs.equals(new ResourceLocation("rankine:alloying/ender_amalgam_alloying")) && stack.getItem() instanceof AlloyPickaxeItem;
        }
        return RankineEnchantmentTypes.ENDER_AMALGAM_PICKAXE.canEnchant(stack.getItem());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            return rs.equals(new ResourceLocation("rankine:alloying/ender_amalgam_alloying")) && stack.getItem() instanceof AlloyPickaxeItem;
        }
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

}
