package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class ThoriumElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.THORIUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return Math.round(x/6f + x);
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
        return 0;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return 5;
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
        return -1.899f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        if (x >= 50)
        {
            return RankineEnchantments.LIGHTNING_ASPECT;
        }
        return null;
    }
}
