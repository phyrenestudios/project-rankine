package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantmentTypes;
import com.cannolicatfish.rankine.items.alloys.AlloyCrowbarItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class EndotoxinEnchantment extends Enchantment {
    public EndotoxinEnchantment(Enchantment.Rarity p_i46721_1_, EquipmentSlot... p_i46721_2_) {
        super(p_i46721_1_, RankineEnchantmentTypes.ENDER_AMALGAM_CROWBAR, p_i46721_2_);
    }

    public int getMinCost(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 20;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            return rs.equals(new ResourceLocation("rankine:alloying/ender_amalgam_alloying")) && stack.getItem() instanceof AlloyCrowbarItem;
        }
        return RankineEnchantmentTypes.ENDER_AMALGAM_CROWBAR.canEnchant(stack.getItem());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            return rs.equals(new ResourceLocation("rankine:alloying/ender_amalgam_alloying")) && stack.getItem() instanceof AlloyCrowbarItem;
        }
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }
}
