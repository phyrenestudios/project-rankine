package com.cannolicatfish.rankine.util.alloys;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.TextFormatting;

public interface AlloyUtils {

        IItemTier getMaterial();

        int getDurabilityBonus();

        float getMiningSpeedBonus();

        int getMiningLevelBonus();

        int getEnchantabilityBonus();

        float getCorrResistBonus();

        float getHeatResistBonus();

        float getAttackSpeedBonus();

        float getAttackDamageBonus();

        float getToughnessBonus();

        Enchantment getEnchantmentBonus(Item item);

        int getEnchantmentLevel(Enchantment en, int enchantability);

        String getDefComposition();

        TextFormatting getAlloyGroupColor();


}
