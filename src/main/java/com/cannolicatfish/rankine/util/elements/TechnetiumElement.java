package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class TechnetiumElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.TECHNETIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return x;
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
        return x/10f;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 1;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(-x/10f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return 0;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        if (x <= 20)
        {
            return x/100f;
        } else {
            return 0.2f + x/500f;
        }
    }

    @Override
    public float getToughnessFromPercent(int x) {
        if (x <= 20)
        {
            return x/200f;
        } else {
            return 0.1f + x/500f;
        }
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return 0.200f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
