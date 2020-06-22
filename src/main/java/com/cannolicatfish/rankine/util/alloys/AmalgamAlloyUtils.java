package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class AmalgamAlloyUtils implements AlloyUtils {
    public PeriodicTableUtils elem;
    public List<String> restrict = Arrays.asList("Fe", "Pt", "W", "Ta");
    public List<Integer> emin = Arrays.asList(50,0,0);
    public List<Integer> emax = Arrays.asList(80,50,50);
    public RankineToolMaterials material = RankineToolMaterials.AMALGAM;

    @Override
    public int getDurabilityBonus() {
        return 0;
    }

    @Override
    public float getMiningSpeedBonus() {
        return 0f;
    }

    @Override
    public int getEnchantabilityBonus() {
        return 0;
    }

    @Override
    public float getCorrResistBonus() {
        return 0;
    }

    @Override
    public float getHeatResistBonus() {
        return 0;
    }

    @Override
    public Enchantment getEnchantmentBonus(Item item) {
        return null;
    }

    @Override
    public int getEnchantmentLevel(Enchantment en, int enchantability) {
        if (enchantability >= 25 && en.getMaxLevel() >= 2)
        {
            return 2;
        }
        return 1;
    }

    @Override
    public float getAttackSpeedMod(String c) {
        return 0;
    }

    @Override
    public float getAttackDamageMod(String c) {
        return 0;
    }

    @Override
    public float getCorrResistance(String c) {
        return 0;
    }

    @Override
    public float getHeatResistance(String c) {
        return 0;
    }

    @Override
    public String getDefComposition() {
        return "80Hg-20Au";
    }
}
