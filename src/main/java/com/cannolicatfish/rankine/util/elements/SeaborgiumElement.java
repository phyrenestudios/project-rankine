package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class SeaborgiumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.SEABORGIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return (int) Math.round(Math.pow(2,x/900f));
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
        return (float) Math.log(x/10f + 1);
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 1;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x/10f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return x/400f;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return x/400f;
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return x/400f;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return -2.5f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
