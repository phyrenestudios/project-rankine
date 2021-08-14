package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.IStringSerializable;

public enum AsphaltStates implements IStringSerializable {
    NONE("none"),
    WHITE_LINE("white_line"),
    WHITE_DASHED_LINE("white_dashed_line"),
    YELLOW_LINE("yellow_line"),
    YELLOW_DOUBLE_LINE("yellow_double_line"),
    YELLOW_DASHED_LINE("yellow_dashed_line"),
    YELLOW_DASHED_DOUBLE_LINE("yellow_dashed_double_line"),
    CROSSWALK("crosswalk"),
    MANHOLE("manhole");

    private final String name;

    AsphaltStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}