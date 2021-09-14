package com.cannolicatfish.rankine.blocks.buildingmodes;

import net.minecraft.util.IStringSerializable;

public enum PolishedStonesBuildingStates implements IStringSerializable {
    NORMAL("normal"),
    OFFSET("offset"),
    VERTICAL_OFFSET("vertical_offset");

    private final String name;

    PolishedStonesBuildingStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}