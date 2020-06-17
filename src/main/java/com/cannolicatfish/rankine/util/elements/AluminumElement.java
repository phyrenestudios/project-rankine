package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;

public class AluminumElement implements ElementInterface{


    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.ALUMINUM;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return 0;
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        return x/10f;
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        return 0;
    }
}
