package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantmentTypes;
import com.cannolicatfish.rankine.items.alloys.AlloyBlunderbussItem;
import com.cannolicatfish.rankine.items.alloys.AlloyKnifeItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EndplayEnchantment extends Enchantment {
    public EndplayEnchantment(Rarity p_i46721_1_, EquipmentSlotType... p_i46721_2_) {
        super(p_i46721_1_, RankineEnchantmentTypes.ENDER_AMALGAM_BLUNDERBUSS, p_i46721_2_);
    }

    public int getMinEnchantability(int p_77321_1_) {
        return 10 + 7 * (p_77321_1_ - 1);
    }

    public int getMaxEnchantability(int p_223551_1_) {
        return super.getMinEnchantability(p_223551_1_) + 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            return rs.equals(new ResourceLocation("rankine:alloying/ender_amalgam_alloying")) && stack.getItem() instanceof AlloyBlunderbussItem;
        }
        return RankineEnchantmentTypes.ENDER_AMALGAM_BLUNDERBUSS.canEnchantItem(stack.getItem());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            return rs.equals(new ResourceLocation("rankine:alloying/ender_amalgam_alloying")) && stack.getItem() instanceof AlloyBlunderbussItem;
        }
        return false;
    }

    @Override
    public boolean canGenerateInLoot() {
        return false;
    }

}
