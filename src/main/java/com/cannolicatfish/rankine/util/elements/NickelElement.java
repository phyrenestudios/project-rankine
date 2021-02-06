package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class NickelElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.NICKEL;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return (int) Math.round(Math.pow(x/10f,2.5));
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
        if (x >= 50)
        {
            return (x/10f - 2)*x/50f - 1;
        } else {
            return 2f*x/50f;
        }

    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(4f*x/25f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return x/200f * 3/2f;
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
        return -0.25f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }


}
