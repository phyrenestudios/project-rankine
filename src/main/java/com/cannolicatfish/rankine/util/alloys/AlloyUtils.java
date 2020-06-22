package com.cannolicatfish.rankine.util.alloys;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

public interface AlloyUtils {

        public int getDurabilityBonus();

        public float getMiningSpeedBonus();

        public int getEnchantabilityBonus();

        public float getCorrResistBonus();

        public float getHeatResistBonus();

        public Enchantment getEnchantmentBonus(Item item);

        public int getEnchantmentLevel(Enchantment en, int enchantability);

        public float getAttackSpeedMod(String c);

        public float getAttackDamageMod(String c);

        public float getCorrResistance(String c);

        public float getHeatResistance(String c);

        public String getDefComposition();


}
