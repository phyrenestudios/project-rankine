package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class PalladiumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.PALLADIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return (int) Math.round(Math.pow(1.6f,x) - 1);
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
        return x/20f;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x >= 60)
        {
            return (int) Math.round(Math.log(x)*3 + 3*(x/10f - 6));
        } else
        {
            return (int) Math.round(Math.log(x)*3);
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
