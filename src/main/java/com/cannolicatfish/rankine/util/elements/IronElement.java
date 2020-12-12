package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class IronElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.IRON;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(25*(x/10f));
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
        if (x >= 50)
        {
            return Math.round(x/10f - 4);
        } else
        {
            return 0;
        }
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        if (x >= 15)
        {
            return 2;
        } else if (x >= 5)
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
    public float getElectrodePotentialFromPercent(int x) {
        return 0.44f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }

}
