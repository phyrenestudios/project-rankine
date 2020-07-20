package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;

public interface ElementInterface {

    public PeriodicTableUtils.Element getReference();

    public int getDurabilityFromPercent(int x);

    public int getDamageFromPercent(int x);

    public float getAttackSpeedFromPercent(int x);

    public float getMiningSpeedFromPercent(int x);

    public int getMiningLevelFromPercent(int x);

    public int getEnchantabilityFromPercent(int x);

    public float getCorrResistFromPercent(int x);

    public float getHeatResistFromPercent(int x);

    public float getToughnessFromPercent(int x);

    public Enchantment getEnchantments(int x);


}
