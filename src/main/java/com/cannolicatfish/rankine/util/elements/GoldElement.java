package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class GoldElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.GOLD;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x >= 60)
        {
            return Math.round(8*(x/10f - 6));
        } else
        {
            return 0;
        }
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
        if (x >= 50)
        {
            return 4.5f + 1.5f*(x/10f - 5);
        } else
        {
            return x/10f;
        }
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x >= 50)
        {
            return Math.round(7 + (x/5f - 5));
        } else
        {
            return Math.round(x/5f);
        }
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return x/100f;
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
        return 1.83f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
