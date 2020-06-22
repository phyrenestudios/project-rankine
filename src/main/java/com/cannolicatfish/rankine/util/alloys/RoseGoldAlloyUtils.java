package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;

import java.util.Arrays;
import java.util.List;

public class RoseGoldAlloyUtils implements AlloyUtils {
    public PeriodicTableUtils elem;
    public List<String> restrict = Arrays.asList("Fe", "Pt", "W", "Ta");
    public List<Integer> emin = Arrays.asList(50,0,0);
    public List<Integer> emax = Arrays.asList(80,50,50);
    public RankineToolMaterials material = RankineToolMaterials.ROSE_GOLD;

    @Override
    public int getDurabilityBonus() {
        return 0;
    }

    @Override
    public float getMiningSpeedBonus() {
        return 0;
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
        if (item instanceof PickaxeItem)
        {
            return Enchantments.EFFICIENCY;
        } else if (item instanceof SwordItem)
        {
            return Enchantments.SHARPNESS;
        } else
        {
            return null;
        }

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
        return "75Au-22Cu-3Ni";
    }
}
