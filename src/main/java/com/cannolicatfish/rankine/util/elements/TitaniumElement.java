package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class TitaniumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.TITANIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(16 + 32*(x/10f));
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
        return x/16f;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        if (x >= 8)
        {
            return 2;
        } else if (x >= 4)
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
            return 1;
        } else
        {
            return 0;
        }
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return (float) Math.pow(x/10f, 2)/100;
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
    public float getElectrodePotentialFromPercent(int x) {
        return -1.63f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
