package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class AluminumElement implements ElementInterface {


    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.ALUMINUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(2*x);
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
        return x/5f;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
            return Math.round(x/11f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        if (x >= 50)
        {
            return 1f;
        } else
        {
            return x/50f;
        }
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
