package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class BerylliumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.BERYLLIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(3*x/2f);
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
        return x/25f;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x/25f + 1);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return x/250f;
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
        return -1.847f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
