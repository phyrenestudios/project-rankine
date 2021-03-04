package com.cannolicatfish.rankine.init;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class RankineAttributes {

    public static final AttributeModifier GRASS_PATH_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fa0"), "rankine:grass_path_ms", Config.GENERAL.MOVEMENT_GRASS_PATH.get(), AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier SAND_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fa1"), "rankine:sand_ms", Config.GENERAL.MOVEMENT_SAND.get(), AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier BRICKS_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fa2"), "rankine:bricks_ms", Config.GENERAL.MOVEMENT_BRICKS.get(), AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier ROMAN_CONCRETE_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fa3"), "rankine:roman_concrete_ms", Config.GENERAL.MOVEMENT_ROMAN_CONCRETE.get(), AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier DIRT_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fa4"), "rankine:dirt_ms", Config.GENERAL.MOVEMENT_DIRT.get(), AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier POLISHED_STONE_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fa5"), "rankine:polished_stone_ms", Config.GENERAL.MOVEMENT_POLISHED_STONE.get(), AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier WOODEN_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fa6"), "rankine:wooden_ms", Config.GENERAL.MOVEMENT_WOODEN.get(), AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier CONCRETE_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fa7"), "rankine:concrete_ms", Config.GENERAL.MOVEMENT_CONCRETE.get(), AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier SNOW_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fa8"), "rankine:snow_ms", Config.GENERAL.MOVEMENT_SNOW.get(), AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier DUNE_WALKER = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fa9"), "rankine:dune_walker", 0.1, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier SNOW_DRIFTER = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fb0"), "rankine:snowshoes", 0.1, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier SPEED_SKATER = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fb1"), "rankine:speed_skater", 0.1, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier SPEED_PENDANT_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fb2"), "rankine:speed_pendant_ms", 0.075, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEALTH_PENDANT = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fb3"), "rankine:health_pendant_ms", 20.0, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier MUD_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fb4"), "rankine:mud_ms", Config.GENERAL.MOVEMENT_MUD.get(), AttributeModifier.Operation.ADDITION);

    public static final AttributeModifier MERCURY_MS = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe1aa0"), "rankine:mercury_mod", -0.04D, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier MERCURY_HEALTH = new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe1aa1"), "rankine:mercury_mod", -2D, AttributeModifier.Operation.ADDITION);
}

