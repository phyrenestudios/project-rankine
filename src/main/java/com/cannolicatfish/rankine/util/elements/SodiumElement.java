package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class SodiumElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.SODIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(x*0.5f);
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
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x/7f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return -x/125f;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return -x/125f;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return  0;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -2.71f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
