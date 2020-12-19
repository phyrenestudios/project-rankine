package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class GermaniumElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.GERMANIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x<=10)
        {
            return (int) Math.round(Math.pow(x,2)/20f);
        } else {
            return 5;
        }
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
        return (float) (Math.sqrt(x) + 1);
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x <= 10)
        {
            return Math.round(3*x/2f);
        } else {
            return 15;
        }
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        if (x <= 20)
        {
            return x/200f;
        } else {
            return 0.1f;
        }
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return (float) (Math.sqrt(x)/50f);
    }

    @Override
    public float getToughnessFromPercent(int x) {
        if (x <= 25)
        {
            return -x/50f;
        } else {
            return -0.5f;
        }
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return 0.12f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        if (x>=6)
        {
            return Enchantments.MENDING;
        } else{
            return null;
        }
    }
}
