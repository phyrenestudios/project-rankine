package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class CarbonElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.CARBON;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x >= 84) {
            return (int) Math.round(Math.pow((x - 80) * 2,2) - 39);
        }
        else if (x < 6)
        {
            return 20*x;
        } else {
            return 0;
        }
    }

    @Override
    public float getDamageFromPercent(int x) {
        if ((x < 6 && x > 1) || x >= 80) {
            return 1;
        } else if (x == 1){
            return 0.5f;
        } else {
            return 0;
        }
    }

    @Override
    public float getAttackSpeedFromPercent(int x) {
        return 0;
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        if (x >= 80) {
            return x/10f - 2;
        } else if (x < 6){
            return x/100f;
        } else {
            return 0;
        }
    }

    @Override
    public int getMiningLevelFromPercent(int x) {

        if (x >= 80) {
            return Math.round(x/10f) - 7;
        } else {
            return 0;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x >= 80) {
            return Math.round((x - 80)/2f);
        } else {
            return 0;
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
        return 0.52f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
