package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;

public class IronElement implements ElementInterface{
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.IRON;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        return 16 + 32*(x/10);
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        if (x >= 50)
        {
            return x/10f - 6;
        } else
        {
            return 0;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x >= 50)
        {
            return 4 + 2*(x/10 - 5);
        } else
        {
            if (x/10 - 1 <= 0)
            {
                return 0;
            } else
            {
                return x/10 - 1;
            }
        }
    }
}
