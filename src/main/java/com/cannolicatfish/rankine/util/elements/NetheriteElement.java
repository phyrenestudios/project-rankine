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

        return Math.round(20.31f*x);
    }

    @Override
    public float getDamageFromPercent(int x) {
        if (x <= 20)
        {
            return x/10f;
        } else {
            return 2;
        }
    }

    @Override
    public float getAttackSpeedFromPercent(int x) {
        if (x <= 20)
        {
            return x/20f;
        } else {
            return 1;
        }
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        return x/10f - 1;
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
        return Math.round(x*0.15f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return -x/100f;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        if (x <= 25)
        {
            return x/25f;
        } else {
            return 1;
        }
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
