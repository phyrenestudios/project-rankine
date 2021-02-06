package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class SulfurElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.SULFUR;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x < 5)
        {
            return -50 * x;
        } else
        {
            return -200 + 2*x + 1;
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
        if (x < 10)
        {
            return -0.2f * x;
        } else{
            return -2f;
        }

    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.min(x, 10);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return -x/100f;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return 0;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return 0;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return 0.50f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        if (x >= 4)
        {
            return Enchantments.VANISHING_CURSE;
        } else {
            return null;
        }
    }
}
