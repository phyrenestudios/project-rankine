package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;

public class ZincElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.ZINC;
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
        if (x >= 10)
        {
            return 2;
        } else
        {
            return x/5;
        }
    }
}
