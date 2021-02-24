package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class MagnesiumElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.MAGNESIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(x*1.2f);
    }

    @Override
    public float getDamageFromPercent(int x) {
        return 0;
    }

    @Override
    public float getAttackSpeedFromPercent(int x) {
        if (x <= 5)
        {
            return x/10f;
        } else {
            return 0.5f;
        }
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        return 2*x/12.5f;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x/9f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return -x/200f;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return -x/125f;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return (float) (Math.log10(x + 1)/5f);
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -2.7f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
