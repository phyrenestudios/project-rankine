package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class SilverElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.SILVER;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return (int) Math.round(Math.log(4f*x + 1)*x/10f);
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
        return 2*x/25f;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x/5f);
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
        return 0.7996f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        if (x >= 50)
        {
            return Enchantments.SMITE;
        }
        return null;
    }
}
