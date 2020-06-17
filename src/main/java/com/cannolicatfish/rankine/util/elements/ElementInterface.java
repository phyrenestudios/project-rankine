package com.cannolicatfish.rankine.util.elements;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;

public interface ElementInterface {

    public PeriodicTableUtils.Element getReference();

    public int getDurabilityFromPercent(int x);

    public float getMiningSpeedFromPercent(int x);

    public int getEnchantabilityFromPercent(int x);


}
