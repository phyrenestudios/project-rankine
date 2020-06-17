package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;

public class GoldElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.GOLD;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        if (x >= 60)
        {
            return 8*(x/10 - 5);
        } else
        {
            return 0;
        }
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        if (x >= 50)
        {
            return 4.5f + 1.5f*(x/10f - 5);
        } else
        {
            return x/10f;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x >= 50)
        {
            return 12 + (x/5 - 5);
        } else
        {
            return x/5;
        }
    }
}
