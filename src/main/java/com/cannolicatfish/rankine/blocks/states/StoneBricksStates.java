package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.IStringSerializable;

public enum StoneBricksStates implements IStringSerializable {
    BRICKS("bricks"),
    SMALL_BRICKS("small_bricks");

    private final String name;

    StoneBricksStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}