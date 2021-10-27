package com.cannolicatfish.rankine.blocks.buildingmodes;

import net.minecraft.util.IStringSerializable;

public enum BricksBuildingStates implements IStringSerializable {
    NORMAL("normal"),
    VERTICAL_NORMAL("vertical_normal");

    private final String name;

    BricksBuildingStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}