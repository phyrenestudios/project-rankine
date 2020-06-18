package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;

public class TinElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.TIN;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return x/2;
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        if (x >= 50)
        {
            return 4f;
        } else
        {
            return (x-5)/10f;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x >= 50)
        {
            return 5;
        } else
        {
            return 3;
        }
    }
}

