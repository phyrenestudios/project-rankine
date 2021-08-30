package com.cannolicatfish.rankine.blocks.states;

import net.minecraft.util.IStringSerializable;

public enum TilledSoilTypes implements IStringSerializable {
    DIRT("dirt"),
    COARSE_DIRT("coarse_dirt"),
    SOUL_SOIL("soul_soil"),
    END_SOIL("end_soil"),
    LOAM("loam"),
    HUMUS("humus"),
    CLAY_LOAM("clay_loam"),
    SANDY_LOAM("sandy_loam"),
    SILTY_LOAM("silty_loam"),
    SILTY_CLAY("silty_clay"),
    SANDY_CLAY("sandy_clay"),
    LOAMY_SAND("loamy_sand"),
    SILTY_CLAY_LOAM("silty_clay_loam"),
    SANDY_CLAY_LOAM("sandy_clay_loam");

    private final String name;

    TilledSoilTypes(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}