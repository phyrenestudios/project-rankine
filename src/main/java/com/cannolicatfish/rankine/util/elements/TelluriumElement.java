package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class TelluriumElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.TELLURIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return (int) Math.round(Math.max(Math.log(x) + 10, x));
    }

    @Override
    public float getDamageFromPercent(int x) {
        return 0;
    }

    @Override
    public float getAttackSpeedFromPercent(int x) {
        return Math.min(1.5f,x/10f);
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
        if (x <= 10)
        {
            return Math.round(0.5f * x);
        } else {
            return 5;
        }
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
        return 0.01f;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return 0.74f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
