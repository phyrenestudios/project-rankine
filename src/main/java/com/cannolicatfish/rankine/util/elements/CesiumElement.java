package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class CesiumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.CESIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(x/1.6f);
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
        return (float) Math.pow(2,x/25f);
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x/6f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return -x/100f;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return -x/125f;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return 0;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -2.336f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
