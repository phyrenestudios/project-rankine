package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class PrismarineElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.PRISMARINE;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x < 20) {
            return Math.round(Math.abs(x - 10));
        } else {
            return x - 10;
        }

    }

    @Override
    public float getDamageFromPercent(int x) {
        return Math.min(1,x/25f);
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
        return Math.round(x/10f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return Math.min(1,x/25f);
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
        return 0f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        if (x >= 20) {
            return Enchantments.KNOCKBACK;
        } else {
            return null;
        }
    }
}
