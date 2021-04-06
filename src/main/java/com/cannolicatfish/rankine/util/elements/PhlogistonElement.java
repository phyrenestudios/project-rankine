package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class PhlogistonElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.PHLOGISTON;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return 0;
    }

    @Override
    public float getDamageFromPercent(int x) {
        return 0;
    }

    @Override
    public float getAttackSpeedFromPercent(int x) {
        return Math.min(x/20f,0.5f);
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        return (x/10f);
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x/10f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return 0;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        return Math.min(1,x/10f);
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return 0;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return 0;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        return Enchantments.FIRE_ASPECT;
    }
}
