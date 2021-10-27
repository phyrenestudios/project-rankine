package com.cannolicatfish.rankine.blocks.buildingmodes;

import net.minecraft.util.IStringSerializable;

public enum BookshelvesBuildingStates implements IStringSerializable {
    NORMAL("normal"),
    VERTICAL("vertical");

    private final String name;

    BookshelvesBuildingStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}