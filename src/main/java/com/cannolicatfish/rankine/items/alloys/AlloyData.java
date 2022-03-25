package com.cannolicatfish.rankine.items.alloys;

import net.minecraft.util.WeighedRandom;

public class AlloyData extends WeighedRandom.WeighedRandomItem {
    public final String alloyComposition;

    public AlloyData(String comp) {
        super(4);
        this.alloyComposition = comp;
    }
}
