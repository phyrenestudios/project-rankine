package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class PlatinumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.PLATINUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x >= 80)
        {
            return (int) Math.round(Math.pow(2,x/10f - 4) - 10);
        }
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
        return x/20f * 3/2f;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x >= 50)
        {
            return Math.round(3 * x/10f + 10);
        } else
        {
            return Math.round(x/2f);
        }
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
        return 0;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
