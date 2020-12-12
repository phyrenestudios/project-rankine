package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class NetheriteElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.NETHERITE;
    }

    @Override
    public int getDurabilityFromPercent(int x) {

        return (int) Math.round(Math.pow(45,x/100f + 1) + 6);
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
        if (x >= 70)
        {
            return Math.round(x/10f - 1);
        } else
        {
            return 0;
        }
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        if (x >= 10)
        {
            return 4;
        } else if (x >= 0)
        {
            return 3;
        } else {
            return 0;
        }
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
        return x/100f;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return 0;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -0.04f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
