package com.cannolicatfish.rankine.blocks.block_enums;

import java.util.Locale;

public enum StoneType {

    IGNEOUS(),
    METAMORPHIC(),
    SEDIMENTARY(),
    MANTLE();

    StoneType() {}

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }


}
