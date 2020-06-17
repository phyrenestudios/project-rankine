package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;

public class ManganeseElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.MANGANESE;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x >= 10)
        {
            return 100;
        } else {
            return x*10;
        }
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        return (-x/100f);
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x >= 50)
        {
            return -4;
        } else
        {
            return -2;
        }
    }
}
