package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class ZincElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.ZINC;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(x/2f);
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
        if(x >= 50)
        {
            return -1 + (x/10f - 5)*0.4f;
        } else
        {
            return -(x/50f);
        }
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x/20f);
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
        return -0.7618f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
