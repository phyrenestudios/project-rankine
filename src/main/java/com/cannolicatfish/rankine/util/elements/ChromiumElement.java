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
    public int getDamageFromPercent(int x) {
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
        return x/200f * 3/2f;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return 0;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return 0;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
