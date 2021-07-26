package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.IStringSerializable;

public enum StoneBricksStates implements IStringSerializable {
    LARGE("large"),
    VERTICAL_LARGE("vertical_large"),
    SMALL("small"),
    VERTICAL_SMALL("vertical_small"),
    SPECIAL("special");

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