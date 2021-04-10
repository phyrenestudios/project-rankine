package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import java.util.Arrays;

public class ManaElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.MANA;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x == 21 || x == 34 || x == 55 || x == 89) {
            return x * 4;
        } else if (x == 1 || x == 2 || x == 3 || x == 5 || x == 8 || x == 13) {
            return (int) Math.pow(x,2);
        } else {
            return x * 2;
        }
    }

    @Override
    public float getDamageFromPercent(int x) {
        if (x == 5 || x == 8 || x == 24) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public float getAttackSpeedFromPercent(int x) {
        if (x % 10 == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        if (x % 4 == 0) {
            return x/10f;
        } else {
            return x/25f;
        }
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        if (x == 50 || x == 82) {
           return 2;
        } else if (x == 2 || x == 8 || x == 28)  {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return (int) Math.round(3 + Math.pow(x/16f,2));
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
        return -2.28f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return Enchantments.MENDING;
    }
}
