package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.IStringSerializable;

public enum PlanksBuildingStates implements IStringSerializable {
    NORMAL("normal"),
    VERTICAL("vertical");

    private final String name;

    PlanksBuildingStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}