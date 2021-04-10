package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class GhastiumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.GHASTIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x % 2 == 0) {
            return Math.round(x*8f);
        } else {
            return Math.round(x*6f);
        }
    }

    @Override
    public float getDamageFromPercent(int x) {
        return 0;
    }

    @Override
    public float getAttackSpeedFromPercent(int x) {
        return 0;
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        return 0;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x % 2 == 0) {
            return Math.round(x/10f);
        } else {
            return Math.round(x/5f);
        }
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return -x/400f;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return x/250f;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return x/200f;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return 0f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        if (x >= 10) {
            return RankineEnchantments.GHAST_REGENERATION;
        } else {
            return null;
        }
    }
}
