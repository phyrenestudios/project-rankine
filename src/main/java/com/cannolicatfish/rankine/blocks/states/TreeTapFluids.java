package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.StringRepresentable;

public enum TreeTapFluids implements StringRepresentable {
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

    public String getSerializedName() {
        return this.name;
    }
}