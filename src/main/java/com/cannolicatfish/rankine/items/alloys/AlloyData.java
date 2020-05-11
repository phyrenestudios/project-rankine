package com.cannolicatfish.rankine.items.alloys;

import net.minecraft.util.WeightedRandom;

public class AlloyData extends WeightedRandom.Item {
    public final String alloyComposition;

    public AlloyData(String comp) {
        super(4);
        this.alloyComposition = comp;
    }
}
