package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class CobaltElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.COBALT;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(30*(x/8f));
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
        return x/20f;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        if (x >= 30)
        {
            return 3;
        }
        else if (x >= 10)
        {
            return 2;
        } else if (x >= 5)
        {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(3*x/40f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return 0;
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
        return -0.28f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
