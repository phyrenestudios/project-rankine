package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class SilverElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.SILVER;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return (int) Math.round(Math.log(x/2f + 1)*x/10f);
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
        return 2*x/25f;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(3*x/10f * 2/3f);
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
    public Enchantment getEnchantments(int x) {
        return null;
    }
}
