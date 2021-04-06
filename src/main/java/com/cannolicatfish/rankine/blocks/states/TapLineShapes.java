package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.IStringSerializable;

public enum TapLineShapes implements IStringSerializable {
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

    public String getString() {
        return this.name;
    }
}