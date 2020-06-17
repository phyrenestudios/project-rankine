package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;

public class SulfurElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return null;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return 0;
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        return 0;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return 0;
    }
}
