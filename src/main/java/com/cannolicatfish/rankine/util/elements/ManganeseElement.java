package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class ManganeseElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.MANGANESE;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x <= 2)
        {
            return 50*x;
        } else if (x <= 10) {
            return 100 - x*25;
        } else if (x <= 12){
            return (x-10)*200 -150;
        } else if (x <= 16){
            return 250 - 10*x;
        } else {
            return 90 - x/2;
        }
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
        if (x <= 2)
        {
            return x;
        } else if (x <= 10) {
            return 2 - x/5f;
        } else if (x <= 12){
            return x/10f * 2f;
        } else if (x <= 16){
            return 2.4f - x/10f;
        } else {
            return 0.8f - x/150f;
        }
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        if (x >= 10 && x <= 16)
        {
            return 2;
        } else if (x > 0 && x <= 4)
        {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(-x/5f);
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
        return -1.185f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
