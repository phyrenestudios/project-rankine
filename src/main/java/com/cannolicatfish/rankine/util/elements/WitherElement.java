package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public class WitherElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.WITHER;
    }

    @Override
    public int getDurabilityFromPercent(int x) {

        return (int) Math.round(Math.pow(x/2f,2)/2);
    }

    @Override
    public float getDamageFromPercent(int x) {
        if (x <= 50)
        {
            return x/20f;
        } else {
            return 2.5f;
        }
    }

    @Override
    public float getAttackSpeedFromPercent(int x) {
        if (x <= 40)
        {
            return x/20f;
        } else {
            return 2;
        }
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        return -x/10f;
    }

    @Override
    public int getMiningLevelFromPercent(int x) {
        if (x >= 10)
        {
            return 4;
        } else if (x >= 0)
        {
            return 3;
        } else {
            return 0;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return Math.round(x*0.20f);
    }

    @Override
    public float getCorrResistFromPercent(int x) {
        return 0;
    }

    @Override
    public float getHeatResistFromPercent(int x) {
        if (x <= 40)
        {
            return x/40f;
        } else {
            return 1;
        }
    }

    @Override
    public float getToughnessFromPercent(int x) {
        return x/625f;
    }

    @Override
    public float getElectrodePotentialFromPercent(int x) {
        return 0f;
    }

    @Override
    public Enchantment getEnchantments(int x) {
        if (x > 5) {
            return RankineEnchantments.WITHERING_CURSE;
        } else {
            return null;
        }
    }
}
