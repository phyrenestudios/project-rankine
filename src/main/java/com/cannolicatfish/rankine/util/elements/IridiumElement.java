package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class IridiumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.IRIDIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(3*x/10f);
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
        return x/20f;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x<13)
        {
            return Math.round(Math.abs(6-x));
        } else {
            return Math.round(x/10f + 5);
        }
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        if (x <= 50)
        {
            return x/50f;
        } else {
            return 1;
        }
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        if (x <= 50)
        {
            return x/50f;
        } else {
            return 1;
        }
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return -x/175f;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return 0.87f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
