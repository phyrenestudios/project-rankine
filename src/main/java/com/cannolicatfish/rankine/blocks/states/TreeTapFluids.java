package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.IStringSerializable;

public enum TreeTapFluids implements IStringSerializable {
    NONE("none"),
    EMPTY("empty"),
    WATER("water"),
    LAVA("lava"),
    SAP("sap"),
    MAPLE_SAP("maple_sap"),
    RESIN("resin"),
    LATEX("latex"),
    JUGLONE("juglone");

    private final String name;

    TreeTapFluids(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}