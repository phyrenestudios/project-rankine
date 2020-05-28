package com.cannolicatfish.rankine.util;

import net.minecraft.item.crafting.Ingredient;

public interface AlloyUtils {
        public int getDurability(String c);

        public float getEfficiency(String c);

        public float getAttackSpeedMod(String c);

        public float getAttackDamageMod(String c);

        public int getEnchantability(String c);

        public float getCorrResistance(String c);

        public float getHeatResistance(String c);

        public String getDefComposition();


}
