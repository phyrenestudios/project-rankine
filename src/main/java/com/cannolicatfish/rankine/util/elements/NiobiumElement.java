package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class NiobiumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.NIOBIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return 32 + 24*(x/10);
    }

    @Override
    public int getDamageFromPercent(int x) {
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
        if (x >= 6)
        {
            return 2;
        } else if (x >= 2)
        {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x/50f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return x/250f;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return x/200f;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return 0;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -1.099f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
