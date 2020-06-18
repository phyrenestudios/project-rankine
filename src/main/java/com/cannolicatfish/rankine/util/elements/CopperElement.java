package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;

import javax.xml.bind.Element;

public class CopperElement implements ElementInterface {
    @Override
    public PeriodicTableUtils.Element getReference() {
        return PeriodicTableUtils.Element.COPPER;
    }

    @Override
    public int getDurabilityFromPercent(int x) {
        /*if (x >= 50)
        {
            return (4+(x/5 - 10))^2;
        } else
        {
            return x/10;
        }*/
        long e = Math.round(Math.pow(2,x/13f) - 1f);
        return (int) e;
    }

    @Override
    public float getMiningSpeedFromPercent(int x) {
        if (x >= 50)
        {
            return 2.5f;
        } else
        {
            return x/20f;
        }
    }

    @Override
    public int getEnchantabilityFromPercent(int x) {
        if (x >= 50)
        {
            return 7;
        } else
        {
            return Math.round(x/10f + 3);
        }
    }
}
