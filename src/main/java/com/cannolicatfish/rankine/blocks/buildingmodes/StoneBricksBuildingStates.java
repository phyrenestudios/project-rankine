package com.cannolicatfish.rankine.blocks.buildingmodes;

import net.minecraft.util.IStringSerializable;

public enum StoneBricksBuildingStates implements IStringSerializable {
    LARGE("large"),
    VERTICAL_LARGE("vertical_large");

    private final String name;

    StoneBricksBuildingStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}