package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.StringRepresentable;

public enum TripleBlockSection implements StringRepresentable {
    BOTTOM("bottom"),
    MIDDLE("middle"),
    TOP("top");

    private final String name;

    TripleBlockSection(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}