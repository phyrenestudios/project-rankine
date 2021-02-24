package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class PotassiumElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.POTASSIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(x*0.4f);
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
        return x/50f;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x/5f);
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
        return  0;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -2.931f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
