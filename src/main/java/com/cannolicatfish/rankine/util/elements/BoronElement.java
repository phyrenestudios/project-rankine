package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class BoronElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.BORON;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return (int) Math.round(50*Math.log(x));
    }

    @Override
    public float getDamageFromPercent(int x) {

        if (x <= 15) {
            return x/25f;
        } else {
            return 0.6f;
        }
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
        return 0;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return x/100f;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -0.89f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
