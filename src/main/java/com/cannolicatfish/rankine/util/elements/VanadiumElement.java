package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class VanadiumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.VANADIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(16 + 16*(x/10f));
    }


    @Override
    public float getDamageFromPercent(int x) {
        return 1;
    }

    @Override
    public float getAttackSpeedFromPercent(int x) {
        return 0;
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        return 0;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return 0;
    }

    @Override
    public float getCorrResistFromPercent(int x) {

        if (x >= 30) {
            return x/80f - 0.25f;
        }else{
            return 0.1f;
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
    public float getElectrodePotentialFromPercent(int x) {
        return -1.13f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
