package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.IStringSerializable;

public enum VerticalSlabStates implements IStringSerializable {
    STRAIGHT("straight"),
    DOUBLE("double"),
    INNER("inner"),
    OUTER("outer");

    private final String name;

    VerticalSlabStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}