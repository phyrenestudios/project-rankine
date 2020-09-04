package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

import javax.xml.bind.Element;

public class CopperElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.COPPER;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        /*if (x >= 50)
        {
            return (4+(x/5 - 10))^2;
        } else
        {
            return x/10;
        }*/
        return (int) Math.round(Math.pow(2,x/13f) - 1f);
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
            return 2.5f;
        } else
        {
            return x/20f;
        }
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        if (x >= 20)
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
            return 7;
        } else
        {
            return Math.round(x/10f + 3);
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
        return 0.520f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
