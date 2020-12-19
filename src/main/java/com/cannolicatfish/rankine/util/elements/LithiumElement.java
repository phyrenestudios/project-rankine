package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class LithiumElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.LITHIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return 0;
    }

    @Override
    public float getDamageFromPercent(int x) {
        return 0;
    }

    @Override
    public float getAttackSpeedFromPercent(int x) {
        if (x <= 10)
        {
            return x/10f;
        } else {
            return 1;
        }
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        return (float) Math.sqrt(x);
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
        if (x/25 >= 1)
        {
            return -1;
        } else {
            return -x/25f;
        }
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return (float) (-Math.sqrt(x)/10f);
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return Math.min(x / 50f, 0.5f);
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -3.0401f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        if (x >= 6)
        {
            return Enchantments.UNBREAKING;
        } else {
            return null;
        }
    }
}
