package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.IStringSerializable;

public enum MetalPoleStates implements IStringSerializable {
    BOTTOM("bottom"),
    MIDDLE("middle"),
    TOP("top");

    private final String name;

    MetalPoleStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}