package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class RheniumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.RHENIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(x/1.5f);
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
        return 0;
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return 0;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        if (x <= 20)
        {
            return x/50f;
        } else {
            return 0.4f + x/500f;
        }
    }

    @Override
    public float getToughnessFromPercent(int x) {
        if (x <= 20)
        {
            return x/100f;
        } else {
            return 0.2f + x/625f;
        }
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return 0.300f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
