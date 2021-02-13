package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class ChromiumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.CHROMIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return 4*(x);
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
        return Math.round(x/25f);
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        if (x >= 18)
        {
            return 3;
        }
        else if (x >= 12)
        {
            return 2;
        } else if (x >= 6)
        {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x >= 50)
        {
            return Math.round(4 + 2*(x/10f - 5));
        } else
        {
            return Math.max(x / 10 - 1, 0);
        }
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        if (x <= 25) {
            return (float) (0.15f * Math.log10(x) + 0.3f)/2f;
        } else {
            return 0.2f + x/125f;
        }
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return 0;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        if (x < 10)
        {
            return x/200f;
        } else {
            return 0.05f;
        }
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -0.74f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
