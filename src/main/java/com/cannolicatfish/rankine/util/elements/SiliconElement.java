package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class SiliconElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.SILICON;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return 0;
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
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return 0;
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return x/200f;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return Math.abs(x-20)/100f - 0.2f;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return x/200f;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -0.91f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
