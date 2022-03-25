package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.StringRepresentable;

public enum TapLineShapes implements StringRepresentable {
    LINE("line"),
    JOINT("joint"),
    TEE("tee");

    private final String name;

    TapLineShapes(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}