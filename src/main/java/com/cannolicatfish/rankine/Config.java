package com.cannolicatfish.rankine;


import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "generalSettings";
    public static final String CATEGORY_ALLOYS = "alloySettings";
    public static final String CATEGORY_WORLDGEN = "worldgenSettings";

    public static final String SUBCATEGORY_EVENTS = "event";
    public static final String SUBCATEGORY_ALLOYS = "alloys";
    public static final String SUBCATEGORY_OREGEN = "oregen";
    public static final String SUBCATEGORY_OVERWORLD = "overworldOres";
    public static final String SUBCATEGORY_NETHER = "netherOres";
    public static final String SUBCATEGORY_END = "endOres";


    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue DISABLE_WOODEN_SWORD;
    public static ForgeConfigSpec.BooleanValue DISABLE_WOODEN_AXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_WOODEN_SHOVEL;
    public static ForgeConfigSpec.BooleanValue DISABLE_WOODEN_PICKAXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_WOODEN_HOE;
    public static ForgeConfigSpec.BooleanValue DISABLE_STONE_SWORD;
    public static ForgeConfigSpec.BooleanValue DISABLE_STONE_AXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_STONE_SHOVEL;
    public static ForgeConfigSpec.BooleanValue DISABLE_STONE_PICKAXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_STONE_HOE;
    public static ForgeConfigSpec.BooleanValue DISABLE_IRON_SWORD;
    public static ForgeConfigSpec.BooleanValue DISABLE_IRON_AXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_IRON_SHOVEL;
    public static ForgeConfigSpec.BooleanValue DISABLE_IRON_PICKAXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_IRON_HOE;
    public static ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_SWORD;
    public static ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_AXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_SHOVEL;
    public static ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_PICKAXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_HOE;
    public static ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_SWORD;
    public static ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_AXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_SHOVEL;
    public static ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_PICKAXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_HOE;
    public static ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_SWORD;
    public static ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_AXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_SHOVEL;
    public static ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_PICKAXE;
    public static ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_HOE;

    public static ForgeConfigSpec.BooleanValue ALLOY_CORROSION;
    public static ForgeConfigSpec.BooleanValue ALLOY_HEAT;
    public static ForgeConfigSpec.BooleanValue ALLOY_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue ALLOY_WEAR_MINING_AMT;
    public static ForgeConfigSpec.DoubleValue ALLOY_WEAR_DAMAGE_AMT;
    public static ForgeConfigSpec.BooleanValue AMALGAM_EXTRAS;

    public static ForgeConfigSpec.BooleanValue ENABLE_AMALGAM_TOOLS;
    public static ForgeConfigSpec.BooleanValue ENABLE_CUPRONICKEL_TOOLS;
    public static ForgeConfigSpec.BooleanValue ENABLE_STERLING_SILVER_TOOLS;
    public static ForgeConfigSpec.BooleanValue ENABLE_BRASS_TOOLS;
    public static ForgeConfigSpec.BooleanValue ENABLE_NICKEL_SILVER_TOOLS;
    public static ForgeConfigSpec.BooleanValue ENABLE_CAST_IRON_TOOLS;
    public static ForgeConfigSpec.BooleanValue ENABLE_DURALUMIN_TOOLS;
    public static ForgeConfigSpec.BooleanValue ENABLE_MAGNESIUM_ALLOY_TOOLS;
    public static ForgeConfigSpec.BooleanValue ENABLE_ROSE_METAL_TOOLS;
    public static ForgeConfigSpec.BooleanValue ENABLE_GALINSTAN_TOOLS;
    public static ForgeConfigSpec.BooleanValue ENABLE_ALNICO_TOOLS;

    public static ForgeConfigSpec.BooleanValue TOTAL_CONVERSION;
    public static ForgeConfigSpec.DoubleValue T1_BEEHIVE_OVEN_CHANCE;
    public static ForgeConfigSpec.DoubleValue T2_BEEHIVE_OVEN_CHANCE;
    public static ForgeConfigSpec.DoubleValue T3_BEEHIVE_OVEN_CHANCE;




    public static ForgeConfigSpec.BooleanValue RANKINE_ORES_O;
    public static ForgeConfigSpec.BooleanValue RANKINE_ORES_N;
    public static ForgeConfigSpec.BooleanValue RANKINE_ORES_E;
    public static ForgeConfigSpec.IntValue CHARCOAL_PIT_SPEED;
    public static ForgeConfigSpec.IntValue CHARCOAL_PIT_RADIUS;
    public static ForgeConfigSpec.IntValue CHARCOAL_PIT_HEIGHT;
    public static ForgeConfigSpec.BooleanValue COLOR_WORLD;
    public static ForgeConfigSpec.BooleanValue FUEL_VALUES;
    public static ForgeConfigSpec.DoubleValue FLINT_DROP_CHANCE;
    public static ForgeConfigSpec.DoubleValue FORAGING_CHANCE;
    public static ForgeConfigSpec.BooleanValue MANDATORY_AXE;
    public static ForgeConfigSpec.BooleanValue STARTING_BOOK;
    public static ForgeConfigSpec.BooleanValue VILLAGER_TRADES;
    public static ForgeConfigSpec.BooleanValue WANDERING_TRADE_SPECIAL;
    public static ForgeConfigSpec.BooleanValue IGNEOUS_COBBLE_GEN;
    public static ForgeConfigSpec.DoubleValue DIAMON_CHANCE;
    public static ForgeConfigSpec.DoubleValue ILMENITE_CHANCE;
    public static ForgeConfigSpec.DoubleValue INTERSPINIFEX_CHANCE;
    public static ForgeConfigSpec.DoubleValue GLOBAL_BREAK_EXHAUSTION;
    public static ForgeConfigSpec.BooleanValue FLAT_BEDROCK;
    public static ForgeConfigSpec.IntValue BEDROCK_LAYERS;
    public static ForgeConfigSpec.IntValue NOISE_SCALE;
    public static ForgeConfigSpec.IntValue NOISE_OFFSET;
    public static ForgeConfigSpec.IntValue METEOR_CHANCE;
    public static ForgeConfigSpec.IntValue EVAPORATION_TOWER_SPEED;
    public static ForgeConfigSpec.BooleanValue RANKINE_FAUNA;
    public static ForgeConfigSpec.BooleanValue END_METEORITE;
    public static ForgeConfigSpec.DoubleValue KIMBERLITE_INTRUSION_CHANCE;
    public static ForgeConfigSpec.DoubleValue OVERWORLD_INTRUSION_CHANCE;
    public static ForgeConfigSpec.DoubleValue NETHER_INTRUSION_CHANCE;
    public static ForgeConfigSpec.DoubleValue NUGGET_CHANCE;
    public static ForgeConfigSpec.IntValue NUGGET_DISTANCE;
    public static ForgeConfigSpec.IntValue PROSPECTING_STICK_RANGE;
    public static ForgeConfigSpec.IntValue ORE_DETECTOR_RANGE;
    public static ForgeConfigSpec.BooleanValue ORE_DETECTOR_MSG;
    public static ForgeConfigSpec.DoubleValue BRICKS_HARDNESS_MULT;
    public static ForgeConfigSpec.DoubleValue BRICKS_RESISTANCE_MULT;
    public static ForgeConfigSpec.DoubleValue ICE_BREAK;
    public static ForgeConfigSpec.IntValue LASER_QUARRY_RANGE;
    public static ForgeConfigSpec.IntValue LASER_QUARRY_SPEED;


    public static ForgeConfigSpec.BooleanValue MOVEMENT_MODIFIERS;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_SAND;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_GRASS_PATH;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_BRICKS;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_ROMAN_CONCRETE;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_DIRT;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_POLISHED_STONE;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_WOODEN;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_CONCRETE;
    public static ForgeConfigSpec.DoubleValue MOVEMENT_SNOW;



    public static ForgeConfigSpec.BooleanValue SPEED_PENDANT_RECIPE;
    public static ForgeConfigSpec.BooleanValue HASTE_PENDANT_RECIPE;
    public static ForgeConfigSpec.BooleanValue HEALTH_PENDANT_RECIPE;
    public static ForgeConfigSpec.BooleanValue LEVITATION_PENDANT_RECIPE;
    public static ForgeConfigSpec.BooleanValue REPULSION_PENDANT_RECIPE;
    public static ForgeConfigSpec.BooleanValue LUCK_PENDANT_RECIPE;


    public static ForgeConfigSpec.BooleanValue MAGNETITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue MAGNETITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue MAGNETITE_ORE_END;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MAGNETITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue CASSITERITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue CASSITERITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue CASSITERITE_ORE_END;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue CASSITERITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue MALACHITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue MALACHITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue MALACHITE_ORE_END;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MALACHITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue BAUXITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue BAUXITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue BAUXITE_ORE_END;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue BAUXITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue SPHALERITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue SPHALERITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue SPHALERITE_ORE_END;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue SPHALERITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue CINNABAR_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue CINNABAR_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue CINNABAR_ORE_END;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_COUNT;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_HL;
    public static ForgeConfigSpec.BooleanValue CINNABAR_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue PENTLANDITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue PENTLANDITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue PENTLANDITE_ORE_END;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PENTLANDITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue MAGNESITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue MAGNESITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue MAGNESITE_ORE_END;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MAGNESITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue GALENA_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue GALENA_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue GALENA_ORE_END;
    public static ForgeConfigSpec.IntValue GALENA_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue GALENA_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue GALENA_ORE_SIZE;
    public static ForgeConfigSpec.IntValue GALENA_ORE_COUNT;
    public static ForgeConfigSpec.IntValue GALENA_ORE_HL;
    public static ForgeConfigSpec.BooleanValue GALENA_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue VANADINITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue VANADINITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue VANADINITE_ORE_END;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue VANADINITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue BISMUTHINITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue BISMUTHINITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue BISMUTHINITE_ORE_END;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue BISMUTHINITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue ACANTHITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue ACANTHITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue ACANTHITE_ORE_END;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue ACANTHITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue PYROLUSITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue PYROLUSITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue PYROLUSITE_ORE_END;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PYROLUSITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue CHROMITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue CHROMITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue CHROMITE_ORE_END;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue CHROMITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue MOLYBDENITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue MOLYBDENITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue MOLYBDENITE_ORE_END;
    public static ForgeConfigSpec.IntValue MOLYBDENITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MOLYBDENITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MOLYBDENITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MOLYBDENITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MOLYBDENITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MOLYBDENITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue ILMENITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue ILMENITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue ILMENITE_ORE_END;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue ILMENITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue COLUMBITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue COLUMBITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue COLUMBITE_ORE_END;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue COLUMBITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue WOLFRAMITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue WOLFRAMITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue WOLFRAMITE_ORE_END;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue WOLFRAMITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue TANTALITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue TANTALITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue TANTALITE_ORE_END;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue TANTALITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue PLUMBAGO_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue PLUMBAGO_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue PLUMBAGO_ORE_END;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PLUMBAGO_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue MOISSANITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue MOISSANITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue MOISSANITE_ORE_END;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MOISSANITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue SPERRYLITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue SPERRYLITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue SPERRYLITE_ORE_END;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue SPERRYLITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue LIGNITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue LIGNITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue LIGNITE_ORE_END;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue LIGNITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue SUBBITUMINOUS_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue SUBBITUMINOUS_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue SUBBITUMINOUS_ORE_END;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_SIZE;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_COUNT;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_HL;
    public static ForgeConfigSpec.BooleanValue SUBBITUMINOUS_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue BITUMINOUS_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue BITUMINOUS_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue BITUMINOUS_ORE_END;
    public static ForgeConfigSpec.IntValue BITUMINOUS_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue BITUMINOUS_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue BITUMINOUS_ORE_SIZE;
    public static ForgeConfigSpec.IntValue BITUMINOUS_ORE_COUNT;
    public static ForgeConfigSpec.IntValue BITUMINOUS_ORE_HL;
    public static ForgeConfigSpec.BooleanValue BITUMINOUS_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue ANTHRACITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue ANTHRACITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue ANTHRACITE_ORE_END;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue ANTHRACITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue LAZURITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue LAZURITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue LAZURITE_ORE_END;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue LAZURITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue DIAMOND_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue DIAMOND_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue DIAMOND_ORE_END;
    public static ForgeConfigSpec.IntValue DIAMOND_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue DIAMOND_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue DIAMOND_ORE_SIZE;
    public static ForgeConfigSpec.IntValue DIAMOND_ORE_COUNT;
    public static ForgeConfigSpec.IntValue DIAMOND_ORE_HL;
    public static ForgeConfigSpec.BooleanValue DIAMOND_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue GREENOCKITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue GREENOCKITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue GREENOCKITE_ORE_END;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue GREENOCKITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue EMERALD_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue EMERALD_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue EMERALD_ORE_END;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_COUNT;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_HL;
    public static ForgeConfigSpec.BooleanValue EMERALD_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue AQUAMARINE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue AQUAMARINE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue AQUAMARINE_ORE_END;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue AQUAMARINE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_COPPER_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_COPPER_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_COPPER_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_COPPER_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_TIN_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_TIN_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_TIN_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_TIN_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_GOLD_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_GOLD_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_GOLD_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_GOLD_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_ALUMINUM_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_ALUMINUM_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_ALUMINUM_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_ALUMINUM_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_LEAD_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_LEAD_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_LEAD_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_LEAD_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_SILVER_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_SILVER_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_SILVER_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_SILVER_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_ARSENIC_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_ARSENIC_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_ARSENIC_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_ARSENIC_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_BISMUTH_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_BISMUTH_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_BISMUTH_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_BISMUTH_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_SULFUR_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_SULFUR_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_SULFUR_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_SULFUR_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_GALLIUM_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_GALLIUM_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_GALLIUM_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_GALLIUM_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_INDIUM_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_INDIUM_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_INDIUM_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_INDIUM_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_TELLURIUM_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_TELLURIUM_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_TELLURIUM_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_TELLURIUM_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NATIVE_SELENIUM_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NATIVE_SELENIUM_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NATIVE_SELENIUM_ORE_END;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_SELENIUM_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue NETHER_GOLD_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue NETHER_GOLD_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue NETHER_GOLD_ORE_END;
    public static ForgeConfigSpec.IntValue NETHER_GOLD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NETHER_GOLD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NETHER_GOLD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NETHER_GOLD_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NETHER_GOLD_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NETHER_GOLD_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue QUARTZ_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue QUARTZ_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue QUARTZ_ORE_END;
    public static ForgeConfigSpec.IntValue QUARTZ_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue QUARTZ_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue QUARTZ_ORE_SIZE;
    public static ForgeConfigSpec.IntValue QUARTZ_ORE_COUNT;
    public static ForgeConfigSpec.IntValue QUARTZ_ORE_HL;
    public static ForgeConfigSpec.BooleanValue QUARTZ_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue OPAL_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue OPAL_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue OPAL_ORE_END;
    public static ForgeConfigSpec.IntValue OPAL_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue OPAL_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue OPAL_ORE_SIZE;
    public static ForgeConfigSpec.IntValue OPAL_ORE_COUNT;
    public static ForgeConfigSpec.IntValue OPAL_ORE_HL;
    public static ForgeConfigSpec.BooleanValue OPAL_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue FLUORITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue FLUORITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue FLUORITE_ORE_END;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue FLUORITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue URANINITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue URANINITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue URANINITE_ORE_END;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue URANINITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue STIBNITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue STIBNITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue STIBNITE_ORE_END;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue STIBNITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue XENOTIME_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue XENOTIME_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue XENOTIME_ORE_END;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_SIZE;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_COUNT;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_HL;
    public static ForgeConfigSpec.BooleanValue XENOTIME_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue HALITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue HALITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue HALITE_ORE_END;
    public static ForgeConfigSpec.IntValue HALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue HALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue HALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue HALITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue HALITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue HALITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue PINK_HALITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue PINK_HALITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue PINK_HALITE_ORE_END;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PINK_HALITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue INTERSPINIFEX_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue INTERSPINIFEX_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue INTERSPINIFEX_ORE_END;
    public static ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_SIZE;
    public static ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_COUNT;
    public static ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_HL;
    public static ForgeConfigSpec.BooleanValue INTERSPINIFEX_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue PETALITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue PETALITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue PETALITE_ORE_END;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PETALITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue COBALTITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue COBALTITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue COBALTITE_ORE_END;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue COBALTITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue MAJORITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue MAJORITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue MAJORITE_ORE_END;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MAJORITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue CELESTINE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue CELESTINE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue CELESTINE_ORE_END;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue CELESTINE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue CRYOLITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue CRYOLITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue CRYOLITE_ORE_END;
    public static ForgeConfigSpec.IntValue CRYOLITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CRYOLITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CRYOLITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CRYOLITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue CRYOLITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue CRYOLITE_ORE_GENTYPE;
    public static ForgeConfigSpec.BooleanValue PYRITE_ORE_OVERWORLD;
    public static ForgeConfigSpec.BooleanValue PYRITE_ORE_NETHER;
    public static ForgeConfigSpec.BooleanValue PYRITE_ORE_END;
    public static ForgeConfigSpec.IntValue PYRITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PYRITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PYRITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PYRITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PYRITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PYRITE_ORE_GENTYPE;




    public static ForgeConfigSpec.IntValue RED_GRANITE_HL;
    public static ForgeConfigSpec.DoubleValue RED_GRANITE_HARD;
    public static ForgeConfigSpec.DoubleValue RED_GRANITE_RESIST;
    public static ForgeConfigSpec.IntValue GRANODIORITE_HL;
    public static ForgeConfigSpec.DoubleValue GRANODIORITE_HARD;
    public static ForgeConfigSpec.DoubleValue GRANODIORITE_RESIST;
    public static ForgeConfigSpec.IntValue HORNBLENDE_ANDESITE_HL;
    public static ForgeConfigSpec.DoubleValue HORNBLENDE_ANDESITE_HARD;
    public static ForgeConfigSpec.DoubleValue HORNBLENDE_ANDESITE_RESIST;
    public static ForgeConfigSpec.IntValue THOLEIITIC_BASALT_HL;
    public static ForgeConfigSpec.DoubleValue THOLEIITIC_BASALT_HARD;
    public static ForgeConfigSpec.DoubleValue THOLEIITIC_BASALT_RESIST;
    public static ForgeConfigSpec.IntValue GABBRO_HL;
    public static ForgeConfigSpec.DoubleValue GABBRO_HARD;
    public static ForgeConfigSpec.DoubleValue GABBRO_RESIST;
    public static ForgeConfigSpec.IntValue ANORTHOSITE_HL;
    public static ForgeConfigSpec.DoubleValue ANORTHOSITE_HARD;
    public static ForgeConfigSpec.DoubleValue ANORTHOSITE_RESIST;
    public static ForgeConfigSpec.IntValue RHYOLITE_HL;
    public static ForgeConfigSpec.DoubleValue RHYOLITE_HARD;
    public static ForgeConfigSpec.DoubleValue RHYOLITE_RESIST;
    public static ForgeConfigSpec.IntValue LIMESTONE_HL;
    public static ForgeConfigSpec.DoubleValue LIMESTONE_HARD;
    public static ForgeConfigSpec.DoubleValue LIMESTONE_RESIST;
    public static ForgeConfigSpec.IntValue MARBLE_HL;
    public static ForgeConfigSpec.DoubleValue MARBLE_HARD;
    public static ForgeConfigSpec.DoubleValue MARBLE_RESIST;
    public static ForgeConfigSpec.IntValue GNEISS_HL;
    public static ForgeConfigSpec.DoubleValue GNEISS_HARD;
    public static ForgeConfigSpec.DoubleValue GNEISS_RESIST;
    public static ForgeConfigSpec.IntValue SCHIST_HL;
    public static ForgeConfigSpec.DoubleValue SCHIST_HARD;
    public static ForgeConfigSpec.DoubleValue SCHIST_RESIST;
    public static ForgeConfigSpec.IntValue SLATE_HL;
    public static ForgeConfigSpec.DoubleValue SLATE_HARD;
    public static ForgeConfigSpec.DoubleValue SLATE_RESIST;
    public static ForgeConfigSpec.IntValue SHALE_HL;
    public static ForgeConfigSpec.DoubleValue SHALE_HARD;
    public static ForgeConfigSpec.DoubleValue SHALE_RESIST;
    public static ForgeConfigSpec.IntValue IRONSTONE_HL;
    public static ForgeConfigSpec.DoubleValue IRONSTONE_HARD;
    public static ForgeConfigSpec.DoubleValue IRONSTONE_RESIST;
    public static ForgeConfigSpec.IntValue BRECCIA_HL;
    public static ForgeConfigSpec.DoubleValue BRECCIA_HARD;
    public static ForgeConfigSpec.DoubleValue BRECCIA_RESIST;
    public static ForgeConfigSpec.IntValue PUMICE_HL;
    public static ForgeConfigSpec.DoubleValue PUMICE_HARD;
    public static ForgeConfigSpec.DoubleValue PUMICE_RESIST;
    public static ForgeConfigSpec.IntValue SCORIA_HL;
    public static ForgeConfigSpec.DoubleValue SCORIA_HARD;
    public static ForgeConfigSpec.DoubleValue SCORIA_RESIST;
    public static ForgeConfigSpec.IntValue PERIDOTITE_HL;
    public static ForgeConfigSpec.DoubleValue PERIDOTITE_HARD;
    public static ForgeConfigSpec.DoubleValue PERIDOTITE_RESIST;
    public static ForgeConfigSpec.IntValue KIMBERLITE_HL;
    public static ForgeConfigSpec.DoubleValue KIMBERLITE_HARD;
    public static ForgeConfigSpec.DoubleValue KIMBERLITE_RESIST;
    public static ForgeConfigSpec.IntValue KOMATIITE_HL;
    public static ForgeConfigSpec.DoubleValue KOMATIITE_HARD;
    public static ForgeConfigSpec.DoubleValue KOMATIITE_RESIST;
    public static ForgeConfigSpec.IntValue RINGWOODITE_HL;
    public static ForgeConfigSpec.DoubleValue RINGWOODITE_HARD;
    public static ForgeConfigSpec.DoubleValue RINGWOODITE_RESIST;
    public static ForgeConfigSpec.IntValue WADSLEYITE_HL;
    public static ForgeConfigSpec.DoubleValue WADSLEYITE_HARD;
    public static ForgeConfigSpec.DoubleValue WADSLEYITE_RESIST;
    public static ForgeConfigSpec.IntValue BRIDGMANITE_HL;
    public static ForgeConfigSpec.DoubleValue BRIDGMANITE_HARD;
    public static ForgeConfigSpec.DoubleValue BRIDGMANITE_RESIST;
    public static ForgeConfigSpec.IntValue FERROPERICLASE_HL;
    public static ForgeConfigSpec.DoubleValue FERROPERICLASE_HARD;
    public static ForgeConfigSpec.DoubleValue FERROPERICLASE_RESIST;
    public static ForgeConfigSpec.IntValue PEROVSKITE_HL;
    public static ForgeConfigSpec.DoubleValue PEROVSKITE_HARD;
    public static ForgeConfigSpec.DoubleValue PEROVSKITE_RESIST;
    public static ForgeConfigSpec.IntValue QUARTZ_SANDSTONE_HL;
    public static ForgeConfigSpec.DoubleValue QUARTZ_SANDSTONE_HARD;
    public static ForgeConfigSpec.DoubleValue QUARTZ_SANDSTONE_RESIST;
    public static ForgeConfigSpec.IntValue ARKOSE_SANDSTONE_HL;
    public static ForgeConfigSpec.DoubleValue ARKOSE_SANDSTONE_HARD;
    public static ForgeConfigSpec.DoubleValue ARKOSE_SANDSTONE_RESIST;
    public static ForgeConfigSpec.IntValue BLACK_DACITE_HL;
    public static ForgeConfigSpec.DoubleValue BLACK_DACITE_HARD;
    public static ForgeConfigSpec.DoubleValue BLACK_DACITE_RESIST;
    public static ForgeConfigSpec.IntValue RED_DACITE_HL;
    public static ForgeConfigSpec.DoubleValue RED_DACITE_HARD;
    public static ForgeConfigSpec.DoubleValue RED_DACITE_RESIST;
    public static ForgeConfigSpec.IntValue MUDSTONE_HL;
    public static ForgeConfigSpec.DoubleValue MUDSTONE_HARD;
    public static ForgeConfigSpec.DoubleValue MUDSTONE_RESIST;
    public static ForgeConfigSpec.IntValue CHALK_HL;
    public static ForgeConfigSpec.DoubleValue CHALK_HARD;
    public static ForgeConfigSpec.DoubleValue CHALK_RESIST;
    public static ForgeConfigSpec.IntValue PORPHYRY_HL;
    public static ForgeConfigSpec.DoubleValue PORPHYRY_HARD;
    public static ForgeConfigSpec.DoubleValue PORPHYRY_RESIST;
    public static ForgeConfigSpec.IntValue PURPLE_PORPHYRY_HL;
    public static ForgeConfigSpec.DoubleValue PURPLE_PORPHYRY_HARD;
    public static ForgeConfigSpec.DoubleValue PURPLE_PORPHYRY_RESIST;






    static {
        COMMON_BUILDER.comment("General Settings").push(CATEGORY_GENERAL);
        setupFirstBlockConfig();
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Alloy Settings").push(CATEGORY_ALLOYS);
        setupSecondBlockConfig();
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Worldgen Settings").push(CATEGORY_WORLDGEN);
        setupThirdBlockConfig();
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupFirstBlockConfig() {
        COMMON_BUILDER.comment("General Mod Settings").push(SUBCATEGORY_EVENTS);

            COLOR_WORLD = COMMON_BUILDER.comment("If enabled, dyes can be used on blocks in-world to dye them (includes concrete, concrete powder, terracotta, glazed terracotta, stained glass, stained glass panes, leds, wool)")
                    .define("colorWorld",true);
            BRICKS_HARDNESS_MULT = COMMON_BUILDER.comment("A multiplier to determine how much higher the bricks variant hardness is than the stone.")
                    .defineInRange("bricksHardnessMultiplier", 2.0D, 0.0D, 20.0D);
            BRICKS_RESISTANCE_MULT = COMMON_BUILDER.comment("A multiplier to determine how much higher the bricks variant resistance is than the stone.")
                    .defineInRange("bricksHardnessMultiplier", 1.5D, 0.0D, 20.0D);
            STARTING_BOOK = COMMON_BUILDER.comment("Enables the Rankine Journal (a guide to the mod)")
                    .define("startingBook",true);
            MANDATORY_AXE = COMMON_BUILDER.comment("An axe is required to harvest logs")
                    .define("axesOnly",false);
            FUEL_VALUES = COMMON_BUILDER.comment("Change the fuel values of items for realism.")
                    .define("fuelValuesChange",true);
            CHARCOAL_PIT_RADIUS = COMMON_BUILDER.comment("Maximum radius the charcoal pit can convert logs.")
                    .defineInRange("charcoalPitRadius", 7, 3, 15);
            CHARCOAL_PIT_SPEED = COMMON_BUILDER.comment("Every X number of random ticks the charcoal pit will go")
                    .defineInRange("charcoalPitSpeed", 10, 1, 200);
            CHARCOAL_PIT_HEIGHT = COMMON_BUILDER.comment("Maximum height a charcoal pile can be")
                    .defineInRange("charcoalPitHeight", 5, 1, 10);
            FLINT_DROP_CHANCE = COMMON_BUILDER.comment("Chance for a stone block to drop a flint")
                    .defineInRange("flintDropChance", 0.15D, 0.00D, 1.00D);
            FORAGING_CHANCE = COMMON_BUILDER.comment("Chance for a dirt block to drop a vegetable")
                    .defineInRange("foragingChance", 0.15D, 0.00D, 1.00D);
            IGNEOUS_COBBLE_GEN = COMMON_BUILDER.comment("Change the output of a cobblestone generator from cobblestone to random igneous rocks.")
                    .define("igneousGen",true);
            VILLAGER_TRADES = COMMON_BUILDER.comment("Adds trades for Project Rankine to Villagers and the Wandering Trader.")
                    .define("villageTrades",true);
            WANDERING_TRADE_SPECIAL = COMMON_BUILDER.comment("Adds a trade to the Wandering Trader for a random tool which is not restricted by alloy constraints. May be unbalanced due to complete randomness.")
                    .define("wanderingSpecial",false);
            GLOBAL_BREAK_EXHAUSTION = COMMON_BUILDER.comment("Amount of additional exhaustion when breaking a block")
                    .defineInRange("breakExhaustion", 0.00D, 0.00D, 1.00D);
            TOTAL_CONVERSION = COMMON_BUILDER.comment("Enable for the Beehive Oven to convert all the blocks at once rather than one by one.")
                    .define("beehiveOvenConversion",false);
            T1_BEEHIVE_OVEN_CHANCE = COMMON_BUILDER.comment("Chance on random tick for the beehive oven (T1) to cook a block")
                    .defineInRange("t1BeehiveOvenTime", 0.40D, 0.00D, 1.00D);
            T2_BEEHIVE_OVEN_CHANCE = COMMON_BUILDER.comment("Chance on random tick for the magnesium beehive oven (T2) to cook a block")
                    .defineInRange("t2BeehiveOvenTime", 0.7D, 0.00D, 1.00D);
            T3_BEEHIVE_OVEN_CHANCE = COMMON_BUILDER.comment("Chance on random tick for the zircon beehive oven (T3) to cook a block")
                    .defineInRange("t3BeehiveOvenTime", 1.0D, 0.00D, 1.00D);
            EVAPORATION_TOWER_SPEED = COMMON_BUILDER.comment("Speed (in ticks) at which the evaporation tower generates resources.")
                    .defineInRange("evaporationTowerSpeed", 800, 20, 12000);
            PROSPECTING_STICK_RANGE = COMMON_BUILDER.comment("Number of blocks away that the Prospecting Stick can detect ore.")
                    .defineInRange("prospectingStickRange", 16, 0, 64);
            ORE_DETECTOR_RANGE = COMMON_BUILDER.comment("Number of blocks away that the Ore Detector can detect ore.")
                    .defineInRange("oreDetectorRange", 32, 0, 64);
            ORE_DETECTOR_MSG = COMMON_BUILDER.comment("Set to false to disable the ore detector from outputting the block found.")
                    .define("oreDetectorMessage",true);
            ICE_BREAK = COMMON_BUILDER.comment("Chance for ice to break when walking on it.")
                    .defineInRange("iceBreak", 0.002D, 0.0D, 1.0D);
            LASER_QUARRY_RANGE = COMMON_BUILDER.comment("Max range of the laser quarry.")
                    .defineInRange("laserQuarryRange", 7, 0, 15);
            LASER_QUARRY_SPEED = COMMON_BUILDER.comment("Max speed of the laser quarry in ticks.")
                    .defineInRange("laserQuarrySpeed", 10, 10, 300);

            COMMON_BUILDER.comment("Movement speed modifiers").push("movementModifiers");
                MOVEMENT_MODIFIERS = COMMON_BUILDER.comment("Set to false to disable movement speed modifiers.")
                        .define("movementModifiersEnabled",true);
                MOVEMENT_SAND = COMMON_BUILDER.comment("Movement speed modifier for walking on Sand blocks.")
                        .defineInRange("movementSand", -0.02D, -1.0D, 1.0D);
                MOVEMENT_BRICKS = COMMON_BUILDER.comment("Movement speed modifier for walking on Brick / Stone Bricks and variants.")
                        .defineInRange("movementBricks", 0.05D, -1.0D, 1.0D);
                MOVEMENT_GRASS_PATH = COMMON_BUILDER.comment("Movement speed modifier for walking on Grass Paths.")
                        .defineInRange("movementGrassPath", 0.02D, -1.0D, 1.0D);
                MOVEMENT_ROMAN_CONCRETE = COMMON_BUILDER.comment("Movement speed modifier for walking on Roman Cooncrete.")
                        .defineInRange("movementRomanConcrete", 0.1D, -1.0D, 1.0D);
                MOVEMENT_DIRT = COMMON_BUILDER.comment("Movement speed modifier for walking on Dirt / Grass blocks.")
                        .defineInRange("movementDirt", 0.0D, -1.0D, 1.0D);
                MOVEMENT_POLISHED_STONE = COMMON_BUILDER.comment("Movement speed modifier for walking on #forge:polished_stone blocks.")
                        .defineInRange("movementPolishedStone", 0.02D, -1.0D, 1.0D);
                MOVEMENT_WOODEN = COMMON_BUILDER.comment("Movement speed modifier for walking on Planks and wooden variants.")
                        .defineInRange("movementWooden", 0.02D, -1.0D, 1.0D);
                MOVEMENT_CONCRETE = COMMON_BUILDER.comment("Movement speed modifier for walking on Concrete / Gravel Concrete.")
                        .defineInRange("movementConcrete", 0.05D, -1.0D, 1.0D);
                MOVEMENT_SNOW = COMMON_BUILDER.comment("Movement speed modifier for walking on Snow.")
                        .defineInRange("movementSnow", -0.02D, -1.0D, 1.0D);
            COMMON_BUILDER.pop();


            COMMON_BUILDER.comment("Vanilla Tools").push("vanillaTools");
                COMMON_BUILDER.comment("Wooden Tools").push("woodenTools");
                    DISABLE_WOODEN_SWORD = COMMON_BUILDER.comment("Disable the use of the wooden sword (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableWoodenSword",true);
                    DISABLE_WOODEN_AXE = COMMON_BUILDER.comment("Disable the use of the wooden axe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableWoodenAxe",true);
                    DISABLE_WOODEN_SHOVEL = COMMON_BUILDER.comment("Disable the use of the wooden shovel (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableWoodenShovel",true);
                    DISABLE_WOODEN_PICKAXE = COMMON_BUILDER.comment("Disable the use of the wooden pickaxe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableWoodenPickaxe",true);
                    DISABLE_WOODEN_HOE = COMMON_BUILDER.comment("Disable the use of the wooden hoe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableWoodenHoe",true);
                COMMON_BUILDER.pop();
                
                COMMON_BUILDER.comment("Stone Tools").push("stoneTools");
                    DISABLE_STONE_SWORD = COMMON_BUILDER.comment("Disable the use of the stone sword (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableStoneSword",true);
                    DISABLE_STONE_AXE = COMMON_BUILDER.comment("Disable the use of the stone axe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableStoneAxe",true);
                    DISABLE_STONE_SHOVEL = COMMON_BUILDER.comment("Disable the use of the stone shovel (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableStoneShovel",true);
                    DISABLE_STONE_PICKAXE = COMMON_BUILDER.comment("Disable the use of the stone pickaxe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableStonePickaxe",true);
                    DISABLE_STONE_HOE = COMMON_BUILDER.comment("Disable the use of the stone hoe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableStoneHoe",true);
                COMMON_BUILDER.pop();

                COMMON_BUILDER.comment("Iron Tools").push("ironTools");
                    DISABLE_IRON_SWORD = COMMON_BUILDER.comment("Disable the use of the iron sword (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableIronSword",true);
                    DISABLE_IRON_AXE = COMMON_BUILDER.comment("Disable the use of the iron axe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableIronAxe",true);
                    DISABLE_IRON_SHOVEL = COMMON_BUILDER.comment("Disable the use of the iron shovel (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableIronShovel",true);
                    DISABLE_IRON_PICKAXE = COMMON_BUILDER.comment("Disable the use of the iron pickaxe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableIronPickaxe",true);
                    DISABLE_IRON_HOE = COMMON_BUILDER.comment("Disable the use of the iron hoe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableIronHoe",true);
                COMMON_BUILDER.pop();
        
                COMMON_BUILDER.comment("Gold Tools").push("goldTools");
                    DISABLE_GOLDEN_SWORD = COMMON_BUILDER.comment("Disable the use of the gold sword (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableGoldSword",true);
                    DISABLE_GOLDEN_AXE = COMMON_BUILDER.comment("Disable the use of the gold axe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableGoldAxe",true);
                    DISABLE_GOLDEN_SHOVEL = COMMON_BUILDER.comment("Disable the use of the gold shovel (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableGoldShovel",true);
                    DISABLE_GOLDEN_PICKAXE = COMMON_BUILDER.comment("Disable the use of the gold pickaxe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableGoldPickaxe",true);
                    DISABLE_GOLDEN_HOE = COMMON_BUILDER.comment("Disable the use of the gold hoe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableGoldHoe",true);
                COMMON_BUILDER.pop();

                COMMON_BUILDER.comment("Diamond Tools").push("diamondTools");
                    DISABLE_DIAMOND_SWORD = COMMON_BUILDER.comment("Disable the use of the diamond sword (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableDiamondSword",true);
                    DISABLE_DIAMOND_AXE = COMMON_BUILDER.comment("Disable the use of the diamond axe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableDiamondAxe",true);
                    DISABLE_DIAMOND_SHOVEL = COMMON_BUILDER.comment("Disable the use of the diamond shovel (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableDiamondShovel",true);
                    DISABLE_DIAMOND_PICKAXE = COMMON_BUILDER.comment("Disable the use of the diamond pickaxe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableDiamondPickaxe",true);
                    DISABLE_DIAMOND_HOE = COMMON_BUILDER.comment("Disable the use of the diamond hoe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableDiamondHoe",true);
                COMMON_BUILDER.pop();

                COMMON_BUILDER.comment("Netherite Tools").push("netheriteTools");
                    DISABLE_NETHERITE_SWORD = COMMON_BUILDER.comment("Disable the use of the netherite sword (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableNetheriteSword",true);
                    DISABLE_NETHERITE_AXE = COMMON_BUILDER.comment("Disable the use of the netherite axe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableNetheriteAxe",true);
                    DISABLE_NETHERITE_SHOVEL = COMMON_BUILDER.comment("Disable the use of the netherite shovel (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableNetheriteShovel",true);
                    DISABLE_NETHERITE_PICKAXE = COMMON_BUILDER.comment("Disable the use of the netherite pickaxe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableNetheritePickaxe",true);
                    DISABLE_NETHERITE_HOE = COMMON_BUILDER.comment("Disable the use of the netherite hoe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableNetheriteHoe",true);
                COMMON_BUILDER.pop();
            COMMON_BUILDER.pop();
        

            COMMON_BUILDER.comment("Pendant Recipes").push("pendantRecipes");
                SPEED_PENDANT_RECIPE = COMMON_BUILDER.comment("Enable the recipe for speed pendant.")
                        .define("speedPendantRecipe",true);
                HASTE_PENDANT_RECIPE = COMMON_BUILDER.comment("Enable the recipe for haste pendant.")
                        .define("hastePendantRecipe",true);
                HEALTH_PENDANT_RECIPE = COMMON_BUILDER.comment("Enable the recipe for health pendant.")
                        .define("healthPendantRecipe",true);
                LEVITATION_PENDANT_RECIPE = COMMON_BUILDER.comment("Enable the recipe for levitation pendant.")
                        .define("levitationPendantRecipe",true);
                REPULSION_PENDANT_RECIPE = COMMON_BUILDER.comment("Enable the recipe for repulsion pendant.")
                        .define("repulsionPendantRecipe",true);
                LUCK_PENDANT_RECIPE = COMMON_BUILDER.comment("Enable the recipe for luck pendant.")
                        .define("luckPendantRecipe",true);
            COMMON_BUILDER.pop();

        COMMON_BUILDER.pop();
    }

    private static void setupSecondBlockConfig() {
        COMMON_BUILDER.comment("Alloy Settings").push(SUBCATEGORY_ALLOYS);

            ALLOY_CORROSION = COMMON_BUILDER.comment("Enables the corrosion negative modifier for alloy tools (chance to consume extra points of durability in water and rain)")
                    .define("alloyCorrosion",true);
            ALLOY_HEAT = COMMON_BUILDER.comment("Enables the heat negative modifier for alloy tools (chance to consume extra points of durability in hot environments and lava)")
                    .define("alloyHeat",true);
            ALLOY_TOUGHNESS = COMMON_BUILDER.comment("Enables the toughness negative modifier for alloy tools (chance to consume/resist loss of an extra point of durability)")
                    .define("alloyToughness",true);
            ALLOY_WEAR_MINING_AMT = COMMON_BUILDER.comment("Modifies the severity of the wear effect on mining speed (ex. 0.25 means mining speed will be reduced to 75% of the original value as durability is lost)")
                    .defineInRange("alloyWearMiningAmount", 0.25D, 0.00D, 0.99D);
            ALLOY_WEAR_DAMAGE_AMT = COMMON_BUILDER.comment("Modifies the severity of the wear effect on damage (ex. 0.25 means damage will be reduced to 75% of the original value as durability is lost)")
                    .defineInRange("alloyWearDamageAmount", 0.25D, 0.00D, 0.99D);
            AMALGAM_EXTRAS = COMMON_BUILDER.comment("Enables the disabled metals for amalgam alloy (Fe, Pt, W, Ta)")
                    .define("amalgamExtras",false);
            ENABLE_AMALGAM_TOOLS = COMMON_BUILDER.comment("Enables the construction of Amalgam Tools in the Coal Forge.")
                    .define("amalgamToolsEnabled",true);

            COMMON_BUILDER.comment("Experimental Alloy Tools").push("experimental");
                ENABLE_CUPRONICKEL_TOOLS = COMMON_BUILDER.comment("Enables the construction of Cupronickel Tools in the Coal Forge.")
                        .define("cupronickelToolsEnabled",true);
                ENABLE_STERLING_SILVER_TOOLS = COMMON_BUILDER.comment("Enables the construction of Sterling Silver Tools in the Coal Forge.")
                    .define("sterlingSilverToolsEnabled",true);
                ENABLE_BRASS_TOOLS = COMMON_BUILDER.comment("Enables the construction of Brass Tools in the Coal Forge.")
                        .define("brassToolsEnabled",true);
                ENABLE_NICKEL_SILVER_TOOLS = COMMON_BUILDER.comment("Enables the construction of Nickel-silver Tools in the Coal Forge.")
                        .define("nickelSilverToolsEnabled",true);
                ENABLE_CAST_IRON_TOOLS = COMMON_BUILDER.comment("Enables the construction of Cast Iron Tools in the Coal Forge.")
                        .define("castIronToolsEnabled",true);
                ENABLE_DURALUMIN_TOOLS = COMMON_BUILDER.comment("Enables the construction of Duralumin Tools in the Coal Forge.")
                        .define("duraluminToolsEnabled",true);
                ENABLE_MAGNESIUM_ALLOY_TOOLS = COMMON_BUILDER.comment("Enables the construction of Magnesium Alloy Tools in the Coal Forge.")
                    .define("magnesiumAlloyToolsEnabled",true);
                ENABLE_ROSE_METAL_TOOLS = COMMON_BUILDER.comment("Enables the construction of Rose's Metal Tools in the Coal Forge.")
                        .define("roseMetalToolsEnabled",true);
                ENABLE_GALINSTAN_TOOLS = COMMON_BUILDER.comment("Enables the construction of Galinstan Tools in the Coal Forge.")
                        .define("galinstanToolsEnabled",true);
                ENABLE_ALNICO_TOOLS = COMMON_BUILDER.comment("Enables the construction of Alnico Tools in the Coal Forge.")
                        .define("alnicoToolsEnabled",true);
            COMMON_BUILDER.pop();


        COMMON_BUILDER.pop();
    }

    private static void setupThirdBlockConfig() {
        COMMON_BUILDER.comment("Bedrock Generation").push("bedrock");
        FLAT_BEDROCK = COMMON_BUILDER.comment("Generates with a flat bedrock layer (includes the Nether)")
                .define("flatBedrock",false);
        BEDROCK_LAYERS = COMMON_BUILDER.comment("Layers of bedrock to generate if flatBedrock is true")
                .defineInRange("bedrockLayers", 1, 0, 5);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Stone Layer Generation").push("layeringNoise");
        NOISE_SCALE = COMMON_BUILDER.comment("This determines how smooth stone layers generate. Larger values means smoother. Default value is 125.")
                .defineInRange("noiseScale", 125, 1, 1000);
        NOISE_OFFSET = COMMON_BUILDER.comment("This determines how close the overlap of noise layers is. A value of 0 means all layers are shaped identically. Default value is 256")
                .defineInRange("noiseOffset", 256, 0, 16*64);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Meteorite Generation").push("meteorite");
        METEOR_CHANCE = COMMON_BUILDER.comment("The 1 in X chunks a meteroite will spawn in the Overworld. Set to 0 to disable.")
                .defineInRange("meteorChance", 85, 0, 1000);
        END_METEORITE = COMMON_BUILDER.comment("Replaces the bottom of end islands with meteorite and ores")
                .define("endMeteorite",true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Intrusion Settings").push("intrusionGen");
        DIAMON_CHANCE = COMMON_BUILDER.comment("Chance for an kimberlite intrusion block to be replaced by a diamond ore")
                .defineInRange("diamondOreChance", 0.045D, 0.00D, 1.00D);
        ILMENITE_CHANCE = COMMON_BUILDER.comment("Chance for an kimberlite intrusion block to be replaced by an ilmenite ore")
                .defineInRange("ilmeniteOreChance", 0.007D, 0.00D, 1.00D);
        INTERSPINIFEX_CHANCE = COMMON_BUILDER.comment("Chance for an komatiite intrusion block to be replaced by an interspinifex ore")
                .defineInRange("interspinifexOreChance", 0.07D, 0.00D, 1.00D);
        KIMBERLITE_INTRUSION_CHANCE = COMMON_BUILDER.comment("Chance for kimberlite intrusions to spawn in overworld. Separate chance from overworldIntrusionsChance as the main source of diamond.")
                .defineInRange("overworldIntrusionChance", 0.1D, 0.00D, 1.00D);
        OVERWORLD_INTRUSION_CHANCE = COMMON_BUILDER.comment("Chance for overworld intrusions to spawn (includes granite, red_granite, diorite, granodiorite, porphyry)")
                .defineInRange("overworldIntrusionChance", 0.4D, 0.00D, 1.00D);
        NETHER_INTRUSION_CHANCE = COMMON_BUILDER.comment("Chance for nether intrusions to spawn")
                .defineInRange("netherIntrusionChance", 0.12D, 0.00D, 1.00D);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Rankine Fauna Generation").push("rankineFauna");
        RANKINE_FAUNA = COMMON_BUILDER.comment("Enable/Disable Project Rankine trees and berry bushes in world.")
                .define("generateFauna",true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Nuggets Around Ores").push("oreNuggets");
        NUGGET_CHANCE = COMMON_BUILDER.comment("Chance for a rankine stone block to drop a nugget of a nearby ore.")
                .defineInRange("nuggetChance", 0.15D, 0.00D, 1.00D);
        NUGGET_DISTANCE = COMMON_BUILDER.comment("")
                .defineInRange("nuggetRange", 7, 1, 64);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Stone Properties").push("stoneProperties");
            COMMON_BUILDER.comment("Red Granite Properties").push("redGranite");
            RED_GRANITE_HL = COMMON_BUILDER.comment("Harvest Level of Red Granite.")
                    .defineInRange("redGraniteHL", 0, 0, 10);
            RED_GRANITE_HARD = COMMON_BUILDER.comment("Hardness of Red Granite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("redGraniteHardness", 2.5D, 0.0D, 100.0D);
            RED_GRANITE_RESIST = COMMON_BUILDER.comment("Resistance of Red Granite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("redGraniteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Granodiorite Properties").push("granodiorite");
            GRANODIORITE_HL = COMMON_BUILDER.comment("Harvest Level of Granodiorite.")
                    .defineInRange("granodioriteHL", 0, 0, 10);
            GRANODIORITE_HARD = COMMON_BUILDER.comment("Hardness of Granodiorite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("granodioriteHardness", 2.5D, 0.0D, 100.0D);
            GRANODIORITE_RESIST = COMMON_BUILDER.comment("Resistance of Granodiorite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("granodioriteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Hornblende Andesite Properties").push("hornblendeAndesite");
            HORNBLENDE_ANDESITE_HL = COMMON_BUILDER.comment("Harvest Level of Hornblende Andesite.")
                    .defineInRange("hornblendeAndesiteHL", 0, 0, 10);
            HORNBLENDE_ANDESITE_HARD = COMMON_BUILDER.comment("Hardness of Hornblende Andesite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("hornblendeAndesiteHardness", 2.5D, 0.0D, 100.0D);
            HORNBLENDE_ANDESITE_RESIST = COMMON_BUILDER.comment("Resistance of Hornblende Andesite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("hornblendeAndesiteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Tholeiitic Basalt Properties").push("tholeiiticBasalt");
            THOLEIITIC_BASALT_HL = COMMON_BUILDER.comment("Harvest Level of Tholeiitic Basalt.")
                    .defineInRange("tholeiiticBasaltHL", 0, 0, 10);
            THOLEIITIC_BASALT_HARD = COMMON_BUILDER.comment("Hardness of Tholeiitic Basalt (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("tholeiiticBasaltHardness", 2.5D, 0.0D, 100.0D);
            THOLEIITIC_BASALT_RESIST = COMMON_BUILDER.comment("Resistance of Tholeiitic Basalt (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("tholeiiticBasaltResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Gabbro Properties").push("gabbro");
            GABBRO_HL = COMMON_BUILDER.comment("Harvest Level of Gabbro.")
                    .defineInRange("gabbroHL", 0, 0, 10);
            GABBRO_HARD = COMMON_BUILDER.comment("Hardness of Gabbro (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("gabbroHardness", 2.5D, 0.0D, 100.0D);
            GABBRO_RESIST = COMMON_BUILDER.comment("Resistance of Gabbro (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("gabbroResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Anorthosite Properties").push("anorthosite");
            ANORTHOSITE_HL = COMMON_BUILDER.comment("Harvest Level of Anorthosite.")
                    .defineInRange("anorthositeHL", 0, 0, 10);
            ANORTHOSITE_HARD = COMMON_BUILDER.comment("Hardness of Anorthosite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("anorthositeHardness", 2.5D, 0.0D, 100.0D);
            ANORTHOSITE_RESIST = COMMON_BUILDER.comment("Resistance of Anorthosite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("anorthositeResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Rhyolite Properties").push("rhyolite");
            RHYOLITE_HL = COMMON_BUILDER.comment("Harvest Level of Rhyolite.")
                    .defineInRange("rhyoliteHL", 0, 0, 10);
            RHYOLITE_HARD = COMMON_BUILDER.comment("Hardness of Rhyolite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("rhyoliteHardness", 2.5D, 0.0D, 100.0D);
            RHYOLITE_RESIST = COMMON_BUILDER.comment("Resistance of Rhyolite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("rhyoliteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Limestone Properties").push("limestone");
            LIMESTONE_HL = COMMON_BUILDER.comment("Harvest Level of Limestone.")
                    .defineInRange("limestoneHL", 0, 0, 10);
            LIMESTONE_HARD = COMMON_BUILDER.comment("Hardness of Limestone (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("limestoneHardness", 1.5D, 0.0D, 100.0D);
            LIMESTONE_RESIST = COMMON_BUILDER.comment("Resistance of Limestone (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("limestoneResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Marble Properties").push("marble");
            MARBLE_HL = COMMON_BUILDER.comment("Harvest Level of Marble.")
                    .defineInRange("marbleHL", 0, 0, 10);
            MARBLE_HARD = COMMON_BUILDER.comment("Hardness of Marble (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("marbleHardness", 2.5D, 0.0D, 100.0D);
            MARBLE_RESIST = COMMON_BUILDER.comment("Resistance of Marble (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("marbleResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Gneiss Properties").push("gneiss");
            GNEISS_HL = COMMON_BUILDER.comment("Harvest Level of Gneiss.")
                    .defineInRange("gneissHL", 0, 0, 10);
            GNEISS_HARD = COMMON_BUILDER.comment("Hardness of Gneiss (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("gneissHardness", 2.5D, 0.0D, 100.0D);
            GNEISS_RESIST = COMMON_BUILDER.comment("Resistance of Gneiss (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("gneissResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Schist Properties").push("schist");
            SCHIST_HL = COMMON_BUILDER.comment("Harvest Level of Schist.")
                    .defineInRange("schistHL", 0, 0, 10);
            SCHIST_HARD = COMMON_BUILDER.comment("Hardness of Schist (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("schistHardness", 2.5D, 0.0D, 100.0D);
            SCHIST_RESIST = COMMON_BUILDER.comment("Resistance of Schist (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("schistResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Slate Properties").push("slate");
            SLATE_HL = COMMON_BUILDER.comment("Harvest Level of Slate.")
                    .defineInRange("slateHL", 0, 0, 10);
            SLATE_HARD = COMMON_BUILDER.comment("Hardness of Slate (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("slateHardness", 2.5D, 0.0D, 100.0D);
            SLATE_RESIST = COMMON_BUILDER.comment("Resistance of Slate (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("slateResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Shale Properties").push("shale");
            SHALE_HL = COMMON_BUILDER.comment("Harvest Level of Shale.")
                    .defineInRange("shaleHL", 0, 0, 10);
            SHALE_HARD = COMMON_BUILDER.comment("Hardness of Shale (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("shaleHardness", 1.5D, 0.0D, 100.0D);
            SHALE_RESIST = COMMON_BUILDER.comment("Resistance of Shale (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("shaleResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Ironstone Properties").push("ironstone");
            IRONSTONE_HL = COMMON_BUILDER.comment("Harvest Level of Ironstone.")
                    .defineInRange("ironstoneHL", 1, 0, 10);
            IRONSTONE_HARD = COMMON_BUILDER.comment("Hardness of Ironstone (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("ironstoneHardness", 1.5D, 0.0D, 100.0D);
            IRONSTONE_RESIST = COMMON_BUILDER.comment("Resistance of Ironstone (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("ironstoneResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Breccia Properties").push("breccia");
            BRECCIA_HL = COMMON_BUILDER.comment("Harvest Level of Breccia.")
                    .defineInRange("brecciaHL", 0, 0, 10);
            BRECCIA_HARD = COMMON_BUILDER.comment("Hardness of Breccia (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("brecciaHardness", 1.5D, 0.0D, 100.0D);
            BRECCIA_RESIST = COMMON_BUILDER.comment("Resistance of Breccia (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("brecciaResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Pumice Properties").push("pumice");
            PUMICE_HL = COMMON_BUILDER.comment("Harvest Level of Pumice.")
                    .defineInRange("pumiceHL", 0, 0, 10);
            PUMICE_HARD = COMMON_BUILDER.comment("Hardness of Pumice (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("pumiceHardness", 1.5D, 0.0D, 100.0D);
            PUMICE_RESIST = COMMON_BUILDER.comment("Resistance of Pumice (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("pumiceResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Scoria Properties").push("scoria");
            SCORIA_HL = COMMON_BUILDER.comment("Harvest Level of Scoria.")
                    .defineInRange("scoriaHL", 0, 0, 10);
            SCORIA_HARD = COMMON_BUILDER.comment("Hardness of Scoria (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("scoriaHardness", 1.5D, 0.0D, 100.0D);
            SCORIA_RESIST = COMMON_BUILDER.comment("Resistance of Scoria (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("scoriaResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Peridotite Properties").push("peridotite");
            PERIDOTITE_HL = COMMON_BUILDER.comment("Harvest Level of Peridotite.")
                    .defineInRange("peridotiteHL", 2, 0, 10);
            PERIDOTITE_HARD = COMMON_BUILDER.comment("Hardness of Peridotite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("peridotiteHardness", 2.5D, 0.0D, 100.0D);
            PERIDOTITE_RESIST = COMMON_BUILDER.comment("Resistance of Peridotite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("peridotiteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Kimberlite Properties").push("kimberlite");
            KIMBERLITE_HL = COMMON_BUILDER.comment("Harvest Level of Kimberlite.")
                    .defineInRange("kimberliteHL", 2, 0, 10);
            KIMBERLITE_HARD = COMMON_BUILDER.comment("Hardness of Kimberlite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("kimberliteHardness", 2.5D, 0.0D, 100.0D);
            KIMBERLITE_RESIST = COMMON_BUILDER.comment("Resistance of Kimberlite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("kimberliteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Komatiite Properties").push("komatiite");
            KOMATIITE_HL = COMMON_BUILDER.comment("Harvest Level of Komatiite.")
                    .defineInRange("komatiiteHL", 2, 0, 10);
            KOMATIITE_HARD = COMMON_BUILDER.comment("Hardness of Komatiite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("komatiiteHardness", 2.5D, 0.0D, 100.0D);
            KOMATIITE_RESIST = COMMON_BUILDER.comment("Resistance of Komatiite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("komatiiteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Ringwoodite Properties").push("ringwoodite");
            RINGWOODITE_HL = COMMON_BUILDER.comment("Harvest Level of Ringwoodite.")
                    .defineInRange("ringwooditeHL", 2, 0, 10);
            RINGWOODITE_HARD = COMMON_BUILDER.comment("Hardness of Ringwoodite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("ringwooditeHardness", 2.5D, 0.0D, 100.0D);
            RINGWOODITE_RESIST = COMMON_BUILDER.comment("Resistance of Ringwoodite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("ringwooditeResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Wadsleyite Properties").push("wadsleyite");
            WADSLEYITE_HL = COMMON_BUILDER.comment("Harvest Level of Wadsleyite.")
                    .defineInRange("wadsleyiteHL", 2, 0, 10);
            WADSLEYITE_HARD = COMMON_BUILDER.comment("Hardness of Wadsleyite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("wadsleyiteHardness", 2.5D, 0.0D, 100.0D);
            WADSLEYITE_RESIST = COMMON_BUILDER.comment("Resistance of Wadsleyite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("wadsleyiteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Bridgmanite Properties").push("bridgmanite");
            BRIDGMANITE_HL = COMMON_BUILDER.comment("Harvest Level of Bridgmanite.")
                    .defineInRange("bridgmaniteHL", 2, 0, 10);
            BRIDGMANITE_HARD = COMMON_BUILDER.comment("Hardness of Bridgmanite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("bridgmaniteHardness", 2.5D, 0.0D, 100.0D);
            BRIDGMANITE_RESIST = COMMON_BUILDER.comment("Resistance of Bridgmanite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("bridgmaniteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Ferropericlase Properties").push("ferropericlase");
            FERROPERICLASE_HL = COMMON_BUILDER.comment("Harvest Level of Ferropericlase.")
                    .defineInRange("ferropericlaseHL", 2, 0, 10);
            FERROPERICLASE_HARD = COMMON_BUILDER.comment("Hardness of Ferropericlase (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("ferropericlaseHardness", 2.5D, 0.0D, 100.0D);
            FERROPERICLASE_RESIST = COMMON_BUILDER.comment("Resistance of Ferropericlase (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("ferropericlaseResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Perovskite Properties").push("perovskite");
            PEROVSKITE_HL = COMMON_BUILDER.comment("Harvest Level of Perovskite.")
                    .defineInRange("perovskiteHL", 2, 0, 10);
            PEROVSKITE_HARD = COMMON_BUILDER.comment("Hardness of Perovskite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("perovskiteHardness", 2.5D, 0.0D, 100.0D);
            PEROVSKITE_RESIST = COMMON_BUILDER.comment("Resistance of Perovskite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("perovskiteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Quartz Sandstone Properties").push("quartzSandstone");
            QUARTZ_SANDSTONE_HL = COMMON_BUILDER.comment("Harvest Level of Quartz Sandstone.")
                    .defineInRange("quartzSandstoneHL", 0, 0, 10);
            QUARTZ_SANDSTONE_HARD = COMMON_BUILDER.comment("Hardness of Quartz Sandstone (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("quartzSandstoneHardness", 1.5D, 0.0D, 100.0D);
            QUARTZ_SANDSTONE_RESIST = COMMON_BUILDER.comment("Resistance of Quartz Sandstone (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("quartzSandstoneResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Arkose Sandstone Properties").push("arkoseSandstone");
            ARKOSE_SANDSTONE_HL = COMMON_BUILDER.comment("Harvest Level of Arkose Sandstone.")
                    .defineInRange("arkoseSandstoneHL", 0, 0, 10);
            ARKOSE_SANDSTONE_HARD = COMMON_BUILDER.comment("Hardness of Arkose Sandstone (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("arkoseSandstoneHardness", 1.5D, 0.0D, 100.0D);
            ARKOSE_SANDSTONE_RESIST = COMMON_BUILDER.comment("Resistance of Arkose Sandstone (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("arkoseSandstoneResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Black Dacite Properties").push("blackDacite");
            BLACK_DACITE_HL = COMMON_BUILDER.comment("Harvest Level of Black Dacite.")
                    .defineInRange("blackDaciteHL", 0, 0, 10);
            BLACK_DACITE_HARD = COMMON_BUILDER.comment("Hardness of Black Dacite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("blackDaciteHardness", 2.5D, 0.0D, 100.0D);
            BLACK_DACITE_RESIST = COMMON_BUILDER.comment("Resistance of Black Dacite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("blackDaciteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Red Dacite Properties").push("redDacite");
            RED_DACITE_HL = COMMON_BUILDER.comment("Harvest Level of Red Dacite.")
                    .defineInRange("redDaciteHL", 0, 0, 10);
            RED_DACITE_HARD = COMMON_BUILDER.comment("Hardness of Red Dacite (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("redDaciteHardness", 2.5D, 0.0D, 100.0D);
            RED_DACITE_RESIST = COMMON_BUILDER.comment("Resistance of Red Dacite (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("redDaciteResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Mudstone Properties").push("mudstone");
            MUDSTONE_HL = COMMON_BUILDER.comment("Harvest Level of Mudstone.")
                    .defineInRange("mudstoneHL", 0, 0, 10);
            MUDSTONE_HARD = COMMON_BUILDER.comment("Hardness of Mudstone (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("mudstoneHardness", 1.5D, 0.0D, 100.0D);
            MUDSTONE_RESIST = COMMON_BUILDER.comment("Resistance of Mudstone (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("mudstoneResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Chalk Properties").push("chalk");
            CHALK_HL = COMMON_BUILDER.comment("Harvest Level of Chalk.")
                    .defineInRange("chalkHL", 0, 0, 10);
            CHALK_HARD = COMMON_BUILDER.comment("Hardness of Chalk (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("chalkHardness", 1.5D, 0.0D, 100.0D);
            CHALK_RESIST = COMMON_BUILDER.comment("Resistance of Chalk (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("chalkResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Pophyry Properties").push("pophyry");
            PORPHYRY_HL = COMMON_BUILDER.comment("Harvest Level of Pophyry.")
                    .defineInRange("pophyryHL", 0, 0, 10);
            PORPHYRY_HARD = COMMON_BUILDER.comment("Hardness of Pophyry (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("pophyryHardness", 1.5D, 0.0D, 100.0D);
            PORPHYRY_RESIST = COMMON_BUILDER.comment("Resistance of Pophyry (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("pophyryResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.comment("Purple Pophyry Properties").push("purplePophyry");
            PURPLE_PORPHYRY_HL = COMMON_BUILDER.comment("Harvest Level of Purple Pophyry.")
                    .defineInRange("pophyryHL", 0, 0, 10);
            PURPLE_PORPHYRY_HARD = COMMON_BUILDER.comment("Hardness of Purple Pophyry (Obsidian is 50 and vanilla stone is 2).")
                    .defineInRange("pophyryHardness", 1.5D, 0.0D, 100.0D);
            PURPLE_PORPHYRY_RESIST = COMMON_BUILDER.comment("Resistance of Purple Pophyry (Obsidian is 1200 and vanilla stone is 6).")
                    .defineInRange("pophyryResistance", 6.0D, 0.00D, 2000.0D);
            COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();







        //Oregen config
        COMMON_BUILDER.comment("Oregen Settings").push(SUBCATEGORY_OREGEN);
        
        RANKINE_ORES_O = COMMON_BUILDER.comment("Enable/Disable Project Rankine ores in world.")
                .define("generateOverworldOres",true);
        RANKINE_ORES_N = COMMON_BUILDER.comment("Enable/Disable Project Rankine ores in Nether.")
                .define("generateNetherOres",true);
        RANKINE_ORES_E = COMMON_BUILDER.comment("Enable/Disable Project Rankine ores in End.")
                .define("generateEndOres",true);


        COMMON_BUILDER.comment("Magnetite Settings").push("MagnetiteOre");
        MAGNETITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Magnetite in the Overworld)")
                .define("MagnetiteOreOverworld",true);
        MAGNETITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Magnetite in the Nether)")
                .define("MagnetiteOreNether",false);
        MAGNETITE_ORE_END = COMMON_BUILDER.comment("Enables Magnetite in the End)")
                .define("MagnetiteOreEnd",false);
        MAGNETITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Magnetite at (make sure it is less than the maximum)")
                .defineInRange("MagnetiteOreMin", 20, 0, 256);
        MAGNETITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Magnetite at (make sure it is greater than the minimum)")
                .defineInRange("MagnetiteOreMax", 50, 0, 256);
        MAGNETITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, MagnetiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("MagnetiteOreGentype",true);
        MAGNETITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Magnetite vein")
                .defineInRange("MagnetiteOreSize", 24, 0, 256);
        MAGNETITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Magnetite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("MagnetiteOreCount", 1, 0, 256);
        MAGNETITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Magnetite")
                .defineInRange("MagnetiteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Cassiterite Settings").push("CassiteriteOre");
        CASSITERITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Cassiterite in the Overworld)")
                .define("CassiteriteOreOverworld",true);
        CASSITERITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Cassiterite in the Nether)")
                .define("CassiteriteOreNether",false);
        CASSITERITE_ORE_END = COMMON_BUILDER.comment("Enables Cassiterite in the End)")
                .define("CassiteriteOreEnd",false);
        CASSITERITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cassiterite at (make sure it is less than the maximum)")
                .defineInRange("CassiteriteOreMin", 20, 0, 256);
        CASSITERITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cassiterite at (make sure it is greater than the minimum)")
                .defineInRange("CassiteriteOreMax", 50, 0, 256);
        CASSITERITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, CassiteriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("CassiteriteOreGentype",true);
        CASSITERITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Cassiterite vein")
                .defineInRange("CassiteriteOreSize", 24, 0, 256);
        CASSITERITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Cassiterite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("CassiteriteOreCount", 2, 0, 256);
        CASSITERITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Cassiterite")
                .defineInRange("CassiteriteOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Malachite Settings").push("MalachiteOre");
        MALACHITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Malachite in the Overworld)")
                .define("MalachiteOreOverworld",true);
        MALACHITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Malachite in the Nether)")
                .define("MalachiteOreNether",false);
        MALACHITE_ORE_END = COMMON_BUILDER.comment("Enables Malachite in the End)")
                .define("MalachiteOreEnd",false);
        MALACHITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Malachite at (make sure it is less than the maximum)")
                .defineInRange("MalachiteOreMin", 20, 0, 256);
        MALACHITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Malachite at (make sure it is greater than the minimum)")
                .defineInRange("MalachiteOreMax", 50, 0, 256);
        MALACHITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, MalachiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("MalachiteOreGentype",true);
        MALACHITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Malachite vein")
                .defineInRange("MalachiteOreSize", 24, 0, 256);
        MALACHITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Malachite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("MalachiteOreCount", 2, 0, 256);
        MALACHITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Malachite")
                .defineInRange("MalachiteOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Bauxite Settings").push("BauxiteOre");
        BAUXITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Bauxite in the Overworld)")
                .define("BauxiteOreOverworld",true);
        BAUXITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Bauxite in the Nether)")
                .define("BauxiteOreNether",false);
        BAUXITE_ORE_END = COMMON_BUILDER.comment("Enables Bauxite in the End)")
                .define("BauxiteOreEnd",false);
        BAUXITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Bauxite at (make sure it is less than the maximum)")
                .defineInRange("BauxiteOreMin", 20, 0, 256);
        BAUXITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Bauxite at (make sure it is greater than the minimum)")
                .defineInRange("BauxiteOreMax", 50, 0, 256);
        BAUXITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, BauxiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("BauxiteOreGentype",true);
        BAUXITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Bauxite vein")
                .defineInRange("BauxiteOreSize", 24, 0, 256);
        BAUXITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Bauxite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("BauxiteOreCount", 2, 0, 256);
        BAUXITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Bauxite")
                .defineInRange("BauxiteOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Sphalerite Settings").push("SphaleriteOre");
        SPHALERITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Sphalerite in the Overworld)")
                .define("SphaleriteOreOverworld",true);
        SPHALERITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Sphalerite in the Nether)")
                .define("SphaleriteOreNether",false);
        SPHALERITE_ORE_END = COMMON_BUILDER.comment("Enables Sphalerite in the End)")
                .define("SphaleriteOreEnd",false);
        SPHALERITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Sphalerite at (make sure it is less than the maximum)")
                .defineInRange("SphaleriteOreMin", 20, 0, 256);
        SPHALERITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Sphalerite at (make sure it is greater than the minimum)")
                .defineInRange("SphaleriteOreMax", 50, 0, 256);
        SPHALERITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, SphaleriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("SphaleriteOreGentype",true);
        SPHALERITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Sphalerite vein")
                .defineInRange("SphaleriteOreSize", 24, 0, 256);
        SPHALERITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Sphalerite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("SphaleriteOreCount", 2, 0, 256);
        SPHALERITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Sphalerite")
                .defineInRange("SphaleriteOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Cinnabar Settings").push("CinnabarOre");
        CINNABAR_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Cinnabar in the Overworld)")
                .define("CinnabarOreOverworld",true);
        CINNABAR_ORE_NETHER = COMMON_BUILDER.comment("Enables Cinnabar in the Nether)")
                .define("CinnabarOreNether",false);
        CINNABAR_ORE_END = COMMON_BUILDER.comment("Enables Cinnabar in the End)")
                .define("CinnabarOreEnd",false);
        CINNABAR_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cinnabar at (make sure it is less than the maximum)")
                .defineInRange("CinnabarOreMin", 20, 0, 256);
        CINNABAR_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cinnabar at (make sure it is greater than the minimum)")
                .defineInRange("CinnabarOreMax", 50, 0, 256);
        CINNABAR_ORE_GENTYPE = COMMON_BUILDER.comment("If true, CinnabarOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("CinnabarOreGentype",false);
        CINNABAR_ORE_SIZE = COMMON_BUILDER.comment("Size of Cinnabar vein")
                .defineInRange("CinnabarOreSize", 15, 0, 256);
        CINNABAR_ORE_COUNT = COMMON_BUILDER.comment("Number of Cinnabar veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("CinnabarOreCount", 3, 0, 256);
        CINNABAR_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Cinnabar")
                .defineInRange("CinnabarOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Pentlandite Settings").push("PentlanditeOre");
        PENTLANDITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Pentlandite in the Overworld)")
                .define("PentlanditeOreOverworld",true);
        PENTLANDITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Pentlandite in the Nether)")
                .define("PentlanditeOreNether",false);
        PENTLANDITE_ORE_END = COMMON_BUILDER.comment("Enables Pentlandite in the End)")
                .define("PentlanditeOreEnd",false);
        PENTLANDITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pentlandite at (make sure it is less than the maximum)")
                .defineInRange("PentlanditeOreMin", 20, 0, 256);
        PENTLANDITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pentlandite at (make sure it is greater than the minimum)")
                .defineInRange("PentlanditeOreMax", 50, 0, 256);
        PENTLANDITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, PentlanditeOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("PentlanditeOreGentype",true);
        PENTLANDITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Pentlandite vein")
                .defineInRange("PentlanditeOreSize", 24, 0, 256);
        PENTLANDITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Pentlandite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("PentlanditeOreCount", 2, 0, 256);
        PENTLANDITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Pentlandite")
                .defineInRange("PentlanditeOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Magnesite Settings").push("MagnesiteOre");
        MAGNESITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Magnesite in the Overworld)")
                .define("MagnesiteOreOverworld",true);
        MAGNESITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Magnesite in the Nether)")
                .define("MagnesiteOreNether",false);
        MAGNESITE_ORE_END = COMMON_BUILDER.comment("Enables Magnesite in the End)")
                .define("MagnesiteOreEnd",false);
        MAGNESITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Magnesite at (make sure it is less than the maximum)")
                .defineInRange("MagnesiteOreMin", 20, 0, 256);
        MAGNESITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Magnesite at (make sure it is greater than the minimum)")
                .defineInRange("MagnesiteOreMax", 50, 0, 256);
        MAGNESITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, MagnesiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("MagnesiteOreGentype",true);
        MAGNESITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Magnesite vein")
                .defineInRange("MagnesiteOreSize", 24, 0, 256);
        MAGNESITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Magnesite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("MagnesiteOreCount", 2, 0, 256);
        MAGNESITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Magnesite")
                .defineInRange("MagnesiteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Galena Settings").push("GalenaOre");
        GALENA_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Galena in the Overworld)")
                .define("GalenaOreOverworld",true);
        GALENA_ORE_NETHER = COMMON_BUILDER.comment("Enables Galena in the Nether)")
                .define("GalenaOreNether",false);
        GALENA_ORE_END = COMMON_BUILDER.comment("Enables Galena in the End)")
                .define("GalenaOreEnd",false);
        GALENA_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Galena at (make sure it is less than the maximum)")
                .defineInRange("GalenaOreMin", 20, 0, 256);
        GALENA_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Galena at (make sure it is greater than the minimum)")
                .defineInRange("GalenaOreMax", 50, 0, 256);
        GALENA_ORE_GENTYPE = COMMON_BUILDER.comment("If true, GalenaOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("GalenaOreGentype",true);
        GALENA_ORE_SIZE = COMMON_BUILDER.comment("Size of Galena vein")
                .defineInRange("GalenaOreSize", 24, 0, 256);
        GALENA_ORE_COUNT = COMMON_BUILDER.comment("Number of Galena veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("GalenaOreCount", 2, 0, 256);
        GALENA_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Galena")
                .defineInRange("GalenaOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Vanadinite Settings").push("VanadiniteOre");
        VANADINITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Vanadinite in the Overworld)")
                .define("VanadiniteOreOverworld",true);
        VANADINITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Vanadinite in the Nether)")
                .define("VanadiniteOreNether",false);
        VANADINITE_ORE_END = COMMON_BUILDER.comment("Enables Vanadinite in the End)")
                .define("VanadiniteOreEnd",false);
        VANADINITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Vanadinite at (make sure it is less than the maximum)")
                .defineInRange("VanadiniteOreMin", 20, 0, 256);
        VANADINITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Vanadinite at (make sure it is greater than the minimum)")
                .defineInRange("VanadiniteOreMax", 50, 0, 256);
        VANADINITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, VanadiniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("VanadiniteOreGentype",true);
        VANADINITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Vanadinite vein")
                .defineInRange("VanadiniteOreSize", 16, 0, 256);
        VANADINITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Vanadinite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("VanadiniteOreCount", 2, 0, 256);
        VANADINITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Vanadinite")
                .defineInRange("VanadiniteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Bismuthinite Settings").push("BismuthiniteOre");
        BISMUTHINITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Bismuthinite in the Overworld)")
                .define("BismuthiniteOreOverworld",false);
        BISMUTHINITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Bismuthinite in the Nether)")
                .define("BismuthiniteOreNether",true);
        BISMUTHINITE_ORE_END = COMMON_BUILDER.comment("Enables Bismuthinite in the End)")
                .define("BismuthiniteOreEnd",false);
        BISMUTHINITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Bismuthinite at (make sure it is less than the maximum)")
                .defineInRange("BismuthiniteOreMin", 45, 0, 256);
        BISMUTHINITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Bismuthinite at (make sure it is greater than the minimum)")
                .defineInRange("BismuthiniteOreMax", 90, 0, 256);
        BISMUTHINITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, BismuthiniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("BismuthiniteOreGentype",true);
        BISMUTHINITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Bismuthinite vein")
                .defineInRange("BismuthiniteOreSize", 20, 0, 256);
        BISMUTHINITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Bismuthinite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("BismuthiniteOreCount", 2, 0, 256);
        BISMUTHINITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Bismuthinite")
                .defineInRange("BismuthiniteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Acanthite Settings").push("AcanthiteOre");
        ACANTHITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Acanthite in the Overworld)")
                .define("AcanthiteOreOverworld",true);
        ACANTHITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Acanthite in the Nether)")
                .define("AcanthiteOreNether",false);
        ACANTHITE_ORE_END = COMMON_BUILDER.comment("Enables Acanthite in the End)")
                .define("AcanthiteOreEnd",false);
        ACANTHITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Acanthite at (make sure it is less than the maximum)")
                .defineInRange("AcanthiteOreMin", 20, 0, 256);
        ACANTHITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Acanthite at (make sure it is greater than the minimum)")
                .defineInRange("AcanthiteOreMax", 50, 0, 256);
        ACANTHITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, AcanthiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("AcanthiteOreGentype",true);
        ACANTHITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Acanthite vein")
                .defineInRange("AcanthiteOreSize", 24, 0, 256);
        ACANTHITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Acanthite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("AcanthiteOreCount", 2, 0, 256);
        ACANTHITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Acanthite")
                .defineInRange("AcanthiteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Pyrolusite Settings").push("PyrolusiteOre");
        PYROLUSITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Pyrolusite in the Overworld)")
                .define("PyrolusiteOreOverworld",true);
        PYROLUSITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Pyrolusite in the Nether)")
                .define("PyrolusiteOreNether",false);
        PYROLUSITE_ORE_END = COMMON_BUILDER.comment("Enables Pyrolusite in the End)")
                .define("PyrolusiteOreEnd",false);
        PYROLUSITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pyrolusite at (make sure it is less than the maximum)")
                .defineInRange("PyrolusiteOreMin", 20, 0, 256);
        PYROLUSITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pyrolusite at (make sure it is greater than the minimum)")
                .defineInRange("PyrolusiteOreMax", 50, 0, 256);
        PYROLUSITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, PyrolusiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("PyrolusiteOreGentype",true);
        PYROLUSITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Pyrolusite vein")
                .defineInRange("PyrolusiteOreSize", 24, 0, 256);
        PYROLUSITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Pyrolusite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("PyrolusiteOreCount", 2, 0, 256);
        PYROLUSITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Pyrolusite")
                .defineInRange("PyrolusiteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Chromite Settings").push("ChromiteOre");
        CHROMITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Chromite in the Overworld)")
                .define("ChromiteOreOverworld",true);
        CHROMITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Chromite in the Nether)")
                .define("ChromiteOreNether",false);
        CHROMITE_ORE_END = COMMON_BUILDER.comment("Enables Chromite in the End)")
                .define("ChromiteOreEnd",false);
        CHROMITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Chromite at (make sure it is less than the maximum)")
                .defineInRange("ChromiteOreMin", 0, 0, 256);
        CHROMITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Chromite at (make sure it is greater than the minimum)")
                .defineInRange("ChromiteOreMax", 20, 0, 256);
        CHROMITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, ChromiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("ChromiteOreGentype",true);
        CHROMITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Chromite vein")
                .defineInRange("ChromiteOreSize", 16, 0, 256);
        CHROMITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Chromite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("ChromiteOreCount", 2, 0, 256);
        CHROMITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Chromite")
                .defineInRange("ChromiteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Molybdenite Settings").push("MolybdeniteOre");
        MOLYBDENITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Molybdenite in the Overworld)")
                .define("MolybdeniteOreOverworld",false);
        MOLYBDENITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Molybdenite in the Nether)")
                .define("MolybdeniteOreNether",false);
        MOLYBDENITE_ORE_END = COMMON_BUILDER.comment("Enables Molybdenite in the End)")
                .define("MolybdeniteOreEnd",true);
        MOLYBDENITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Molybdenite at (make sure it is less than the maximum)")
                .defineInRange("MolybdeniteOreMin", 0, 0, 256);
        MOLYBDENITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Molybdenite at (make sure it is greater than the minimum)")
                .defineInRange("MolybdeniteOreMax", 128, 0, 256);
        MOLYBDENITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, MolybdeniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("MolybdeniteOreGentype",false);
        MOLYBDENITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Molybdenite vein")
                .defineInRange("MolybdeniteOreSize", 9, 0, 256);
        MOLYBDENITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Molybdenite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("MolybdeniteOreCount", 4, 0, 256);
        MOLYBDENITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Molybdenite")
                .defineInRange("MolybdeniteOreHL", 4, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Ilmenite Settings").push("IlmeniteOre");
        ILMENITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Ilmenite in the Overworld)")
                .define("IlmeniteOreOverworld",false);
        ILMENITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Ilmenite in the Nether)")
                .define("IlmeniteOreNether",true);
        ILMENITE_ORE_END = COMMON_BUILDER.comment("Enables Ilmenite in the End)")
                .define("IlmeniteOreEnd",false);
        ILMENITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Ilmenite at (make sure it is less than the maximum)")
                .defineInRange("IlmeniteOreMin", 90, 0, 256);
        ILMENITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Ilmenite at (make sure it is greater than the minimum)")
                .defineInRange("IlmeniteOreMax", 128, 0, 256);
        ILMENITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, IlmeniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("IlmeniteOreGentype",true);
        ILMENITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Ilmenite vein")
                .defineInRange("IlmeniteOreSize", 24, 0, 256);
        ILMENITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Ilmenite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("IlmeniteOreCount", 2, 0, 256);
        ILMENITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Ilmenite")
                .defineInRange("IlmeniteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Columbite Settings").push("ColumbiteOre");
        COLUMBITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Columbite in the Overworld)")
                .define("ColumbiteOreOverworld",false);
        COLUMBITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Columbite in the Nether)")
                .define("ColumbiteOreNether",true);
        COLUMBITE_ORE_END = COMMON_BUILDER.comment("Enables Columbite in the End)")
                .define("ColumbiteOreEnd",false);
        COLUMBITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Columbite at (make sure it is less than the maximum)")
                .defineInRange("ColumbiteOreMin", 0, 0, 256);
        COLUMBITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Columbite at (make sure it is greater than the minimum)")
                .defineInRange("ColumbiteOreMax", 45, 0, 256);
        COLUMBITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, ColumbiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("ColumbiteOreGentype",true);
        COLUMBITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Columbite vein")
                .defineInRange("ColumbiteOreSize", 16, 0, 256);
        COLUMBITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Columbite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("ColumbiteOreCount", 2, 0, 256);
        COLUMBITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Columbite")
                .defineInRange("ColumbiteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Wolframite Settings").push("WolframiteOre");
        WOLFRAMITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Wolframite in the Overworld)")
                .define("WolframiteOreOverworld",false);
        WOLFRAMITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Wolframite in the Nether)")
                .define("WolframiteOreNether",true);
        WOLFRAMITE_ORE_END = COMMON_BUILDER.comment("Enables Wolframite in the End)")
                .define("WolframiteOreEnd",false);
        WOLFRAMITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Wolframite at (make sure it is less than the maximum)")
                .defineInRange("WolframiteOreMin", 90, 0, 256);
        WOLFRAMITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Wolframite at (make sure it is greater than the minimum)")
                .defineInRange("WolframiteOreMax", 128, 0, 256);
        WOLFRAMITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, WolframiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("WolframiteOreGentype",true);
        WOLFRAMITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Wolframite vein")
                .defineInRange("WolframiteOreSize", 24, 0, 256);
        WOLFRAMITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Wolframite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("WolframiteOreCount", 2, 0, 256);
        WOLFRAMITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Wolframite")
                .defineInRange("WolframiteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Tantalite Settings").push("TantaliteOre");
        TANTALITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Tantalite in the Overworld)")
                .define("TantaliteOreOverworld",false);
        TANTALITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Tantalite in the Nether)")
                .define("TantaliteOreNether",true);
        TANTALITE_ORE_END = COMMON_BUILDER.comment("Enables Tantalite in the End)")
                .define("TantaliteOreEnd",false);
        TANTALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Tantalite at (make sure it is less than the maximum)")
                .defineInRange("TantaliteOreMin", 0, 0, 256);
        TANTALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Tantalite at (make sure it is greater than the minimum)")
                .defineInRange("TantaliteOreMax", 45, 0, 256);
        TANTALITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, TantaliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("TantaliteOreGentype",true);
        TANTALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Tantalite vein")
                .defineInRange("TantaliteOreSize", 16, 0, 256);
        TANTALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Tantalite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("TantaliteOreCount", 2, 0, 256);
        TANTALITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Tantalite")
                .defineInRange("TantaliteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Plumbago Settings").push("PlumbagoOre");
        PLUMBAGO_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Plumbago in the Overworld)")
                .define("PlumbagoOreOverworld",true);
        PLUMBAGO_ORE_NETHER = COMMON_BUILDER.comment("Enables Plumbago in the Nether)")
                .define("PlumbagoOreNether",false);
        PLUMBAGO_ORE_END = COMMON_BUILDER.comment("Enables Plumbago in the End)")
                .define("PlumbagoOreEnd",false);
        PLUMBAGO_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Plumbago at (make sure it is less than the maximum)")
                .defineInRange("PlumbagoOreMin", 0, 0, 256);
        PLUMBAGO_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Plumbago at (make sure it is greater than the minimum)")
                .defineInRange("PlumbagoOreMax", 30, 0, 256);
        PLUMBAGO_ORE_GENTYPE = COMMON_BUILDER.comment("If true, PlumbagoOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("PlumbagoOreGentype",true);
        PLUMBAGO_ORE_SIZE = COMMON_BUILDER.comment("Size of Plumbago vein")
                .defineInRange("PlumbagoOreSize", 20, 0, 256);
        PLUMBAGO_ORE_COUNT = COMMON_BUILDER.comment("Number of Plumbago veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("PlumbagoOreCount", 2, 0, 256);
        PLUMBAGO_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Plumbago")
                .defineInRange("PlumbagoOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Moissanite Settings").push("MoissaniteOre");
        MOISSANITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Moissanite in the Overworld)")
                .define("MoissaniteOreOverworld",false);
        MOISSANITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Moissanite in the Nether)")
                .define("MoissaniteOreNether",true);
        MOISSANITE_ORE_END = COMMON_BUILDER.comment("Enables Moissanite in the End)")
                .define("MoissaniteOreEnd",false);
        MOISSANITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Moissanite at (make sure it is less than the maximum)")
                .defineInRange("MoissaniteOreMin", 45, 0, 256);
        MOISSANITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Moissanite at (make sure it is greater than the minimum)")
                .defineInRange("MoissaniteOreMax", 90, 0, 256);
        MOISSANITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, MoissaniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("MoissaniteOreGentype",true);
        MOISSANITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Moissanite vein")
                .defineInRange("MoissaniteOreSize", 20, 0, 256);
        MOISSANITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Moissanite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("MoissaniteOreCount", 2, 0, 256);
        MOISSANITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Moissanite")
                .defineInRange("MoissaniteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Sperrylite Settings").push("SperryliteOre");
        SPERRYLITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Sperrylite in the Overworld)")
                .define("SperryliteOreOverworld",false);
        SPERRYLITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Sperrylite in the Nether)")
                .define("SperryliteOreNether",true);
        SPERRYLITE_ORE_END = COMMON_BUILDER.comment("Enables Sperrylite in the End)")
                .define("SperryliteOreEnd",false);
        SPERRYLITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Sperrylite at (make sure it is less than the maximum)")
                .defineInRange("SperryliteOreMin", 0, 0, 256);
        SPERRYLITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Sperrylite at (make sure it is greater than the minimum)")
                .defineInRange("SperryliteOreMax", 45, 0, 256);
        SPERRYLITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, SperryliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("SperryliteOreGentype",true);
        SPERRYLITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Sperrylite vein")
                .defineInRange("SperryliteOreSize", 24, 0, 256);
        SPERRYLITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Sperrylite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("SperryliteOreCount", 2, 0, 256);
        SPERRYLITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Sperrylite")
                .defineInRange("SperryliteOreHL", 4, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Lignite Settings").push("LigniteOre");
        LIGNITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Lignite in the Overworld)")
                .define("LigniteOreOverworld",true);
        LIGNITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Lignite in the Nether)")
                .define("LigniteOreNether",false);
        LIGNITE_ORE_END = COMMON_BUILDER.comment("Enables Lignite in the End)")
                .define("LigniteOreEnd",false);
        LIGNITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Lignite at (make sure it is less than the maximum)")
                .defineInRange("LigniteOreMin", 50, 0, 256);
        LIGNITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Lignite at (make sure it is greater than the minimum)")
                .defineInRange("LigniteOreMax", 80, 0, 256);
        LIGNITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, LigniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("LigniteOreGentype",true);
        LIGNITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Lignite vein")
                .defineInRange("LigniteOreSize", 24, 0, 256);
        LIGNITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Lignite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("LigniteOreCount", 1, 0, 256);
        LIGNITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Lignite")
                .defineInRange("LigniteOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Subbituminous Settings").push("SubbituminousOre");
        SUBBITUMINOUS_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Subbituminous in the Overworld)")
                .define("SubbituminousOreOverworld",true);
        SUBBITUMINOUS_ORE_NETHER = COMMON_BUILDER.comment("Enables Subbituminous in the Nether)")
                .define("SubbituminousOreNether",false);
        SUBBITUMINOUS_ORE_END = COMMON_BUILDER.comment("Enables Subbituminous in the End)")
                .define("SubbituminousOreEnd",false);
        SUBBITUMINOUS_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Subbituminous at (make sure it is less than the maximum)")
                .defineInRange("SubbituminousOreMin", 30, 0, 256);
        SUBBITUMINOUS_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Subbituminous at (make sure it is greater than the minimum)")
                .defineInRange("SubbituminousOreMax", 45, 0, 256);
        SUBBITUMINOUS_ORE_GENTYPE = COMMON_BUILDER.comment("If true, SubbituminousOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("SubbituminousOreGentype",true);
        SUBBITUMINOUS_ORE_SIZE = COMMON_BUILDER.comment("Size of Subbituminous vein")
                .defineInRange("SubbituminousOreSize", 24, 0, 256);
        SUBBITUMINOUS_ORE_COUNT = COMMON_BUILDER.comment("Number of Subbituminous veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("SubbituminousOreCount", 1, 0, 256);
        SUBBITUMINOUS_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Subbituminous")
                .defineInRange("SubbituminousOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Bituminous Settings").push("BituminousOre");
        BITUMINOUS_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Bituminous in the Overworld)")
                .define("BituminousOreOverworld",true);
        BITUMINOUS_ORE_NETHER = COMMON_BUILDER.comment("Enables Bituminous in the Nether)")
                .define("BituminousOreNether",false);
        BITUMINOUS_ORE_END = COMMON_BUILDER.comment("Enables Bituminous in the End)")
                .define("BituminousOreEnd",false);
        BITUMINOUS_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Bituminous at (make sure it is less than the maximum)")
                .defineInRange("BituminousOreMin", 10, 0, 256);
        BITUMINOUS_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Bituminous at (make sure it is greater than the minimum)")
                .defineInRange("BituminousOreMax", 25, 0, 256);
        BITUMINOUS_ORE_GENTYPE = COMMON_BUILDER.comment("If true, BituminousOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("BituminousOreGentype",true);
        BITUMINOUS_ORE_SIZE = COMMON_BUILDER.comment("Size of Bituminous vein")
                .defineInRange("BituminousOreSize", 24, 0, 256);
        BITUMINOUS_ORE_COUNT = COMMON_BUILDER.comment("Number of Bituminous veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("BituminousOreCount", 1, 0, 256);
        BITUMINOUS_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Bituminous")
                .defineInRange("BituminousOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Anthracite Settings").push("AnthraciteOre");
        ANTHRACITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Anthracite in the Overworld)")
                .define("AnthraciteOreOverworld",false);
        ANTHRACITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Anthracite in the Nether)")
                .define("AnthraciteOreNether",true);
        ANTHRACITE_ORE_END = COMMON_BUILDER.comment("Enables Anthracite in the End)")
                .define("AnthraciteOreEnd",false);
        ANTHRACITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Anthracite at (make sure it is less than the maximum)")
                .defineInRange("AnthraciteOreMin", 45, 0, 256);
        ANTHRACITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Anthracite at (make sure it is greater than the minimum)")
                .defineInRange("AnthraciteOreMax", 90, 0, 256);
        ANTHRACITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, AnthraciteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("AnthraciteOreGentype",false);
        ANTHRACITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Anthracite vein")
                .defineInRange("AnthraciteOreSize", 16, 0, 256);
        ANTHRACITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Anthracite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("AnthraciteOreCount", 3, 0, 256);
        ANTHRACITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Anthracite")
                .defineInRange("AnthraciteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Lazurite Settings").push("LazuriteOre");
        LAZURITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Lazurite in the Overworld)")
                .define("LazuriteOreOverworld",true);
        LAZURITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Lazurite in the Nether)")
                .define("LazuriteOreNether",false);
        LAZURITE_ORE_END = COMMON_BUILDER.comment("Enables Lazurite in the End)")
                .define("LazuriteOreEnd",false);
        LAZURITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Lazurite at (make sure it is less than the maximum)")
                .defineInRange("LazuriteOreMin", 20, 0, 256);
        LAZURITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Lazurite at (make sure it is greater than the minimum)")
                .defineInRange("LazuriteOreMax", 80, 0, 256);
        LAZURITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, LazuriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("LazuriteOreGentype",false);
        LAZURITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Lazurite vein")
                .defineInRange("LazuriteOreSize", 14, 0, 256);
        LAZURITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Lazurite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("LazuriteOreCount", 2, 0, 256);
        LAZURITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Lazurite")
                .defineInRange("LazuriteOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Diamond Settings").push("DiamondOre");
        DIAMOND_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Diamond in the Overworld)")
                .define("DiamondOreOverworld",false);
        DIAMOND_ORE_NETHER = COMMON_BUILDER.comment("Enables Diamond in the Nether)")
                .define("DiamondOreNether",false);
        DIAMOND_ORE_END = COMMON_BUILDER.comment("Enables Diamond in the End)")
                .define("DiamondOreEnd",false);
        DIAMOND_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Diamond at (make sure it is less than the maximum)")
                .defineInRange("DiamondOreMin", 0, 0, 256);
        DIAMOND_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Diamond at (make sure it is greater than the minimum)")
                .defineInRange("DiamondOreMax", 128, 0, 256);
        DIAMOND_ORE_GENTYPE = COMMON_BUILDER.comment("If true, DiamondOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("DiamondOreGentype",true);
        DIAMOND_ORE_SIZE = COMMON_BUILDER.comment("Size of Diamond vein")
                .defineInRange("DiamondOreSize", 30, 0, 256);
        DIAMOND_ORE_COUNT = COMMON_BUILDER.comment("Number of Diamond veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("DiamondOreCount", 2, 0, 256);
        DIAMOND_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Diamond")
                .defineInRange("DiamondOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Greenockite Settings").push("GreenockiteOre");
        GREENOCKITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Greenockite in the Overworld)")
                .define("GreenockiteOreOverworld",false);
        GREENOCKITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Greenockite in the Nether)")
                .define("GreenockiteOreNether",true);
        GREENOCKITE_ORE_END = COMMON_BUILDER.comment("Enables Greenockite in the End)")
                .define("GreenockiteOreEnd",false);
        GREENOCKITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Greenockite at (make sure it is less than the maximum)")
                .defineInRange("GreenockiteOreMin", 90, 0, 256);
        GREENOCKITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Greenockite at (make sure it is greater than the minimum)")
                .defineInRange("GreenockiteOreMax", 128, 0, 256);
        GREENOCKITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, GreenockiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("GreenockiteOreGentype",true);
        GREENOCKITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Greenockite vein")
                .defineInRange("GreenockiteOreSize", 16, 0, 256);
        GREENOCKITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Greenockite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("GreenockiteOreCount", 2, 0, 256);
        GREENOCKITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Greenockite")
                .defineInRange("GreenockiteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Emerald Settings").push("EmeraldOre");
        EMERALD_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Emerald in the Overworld)")
                .define("EmeraldOreOverworld",true);
        EMERALD_ORE_NETHER = COMMON_BUILDER.comment("Enables Emerald in the Nether)")
                .define("EmeraldOreNether",false);
        EMERALD_ORE_END = COMMON_BUILDER.comment("Enables Emerald in the End)")
                .define("EmeraldOreEnd",false);
        EMERALD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Emerald at (make sure it is less than the maximum)")
                .defineInRange("EmeraldOreMin", 0, 0, 256);
        EMERALD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Emerald at (make sure it is greater than the minimum)")
                .defineInRange("EmeraldOreMax", 80, 0, 256);
        EMERALD_ORE_GENTYPE = COMMON_BUILDER.comment("If true, EmeraldOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("EmeraldOreGentype",false);
        EMERALD_ORE_SIZE = COMMON_BUILDER.comment("Size of Emerald vein")
                .defineInRange("EmeraldOreSize",  5, 0, 256);
        EMERALD_ORE_COUNT = COMMON_BUILDER.comment("Number of Emerald veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("EmeraldOreCount", 2, 0, 256);
        EMERALD_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Emerald")
                .defineInRange("EmeraldOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Aquamarine Settings").push("AquamarineOre");
        AQUAMARINE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Aquamarine in the Overworld)")
                .define("AquamarineOreOverworld",true);
        AQUAMARINE_ORE_NETHER = COMMON_BUILDER.comment("Enables Aquamarine in the Nether)")
                .define("AquamarineOreNether",false);
        AQUAMARINE_ORE_END = COMMON_BUILDER.comment("Enables Aquamarine in the End)")
                .define("AquamarineOreEnd",false);
        AQUAMARINE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Aquamarine at (make sure it is less than the maximum)")
                .defineInRange("AquamarineOreMin", 0, 0, 256);
        AQUAMARINE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Aquamarine at (make sure it is greater than the minimum)")
                .defineInRange("AquamarineOreMax", 80, 0, 256);
        AQUAMARINE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, AquamarineOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("AquamarineOreGentype",false);
        AQUAMARINE_ORE_SIZE = COMMON_BUILDER.comment("Size of Aquamarine vein")
                .defineInRange("AquamarineOreSize", 5, 0, 256);
        AQUAMARINE_ORE_COUNT = COMMON_BUILDER.comment("Number of Aquamarine veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("AquamarineOreCount", 2, 0, 256);
        AQUAMARINE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Aquamarine")
                .defineInRange("AquamarineOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Copper Settings").push("nativeCopperOre");
        NATIVE_COPPER_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Copper in the Overworld)")
                .define("nativeCopperOreOverworld",true);
        NATIVE_COPPER_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Copper in the Nether)")
                .define("nativeCopperOreNether",false);
        NATIVE_COPPER_ORE_END = COMMON_BUILDER.comment("Enables Native Copper in the End)")
                .define("nativeCopperOreEnd",false);
        NATIVE_COPPER_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Copper at (make sure it is less than the maximum)")
                .defineInRange("nativeCopperOreMin", 50, 0, 256);
        NATIVE_COPPER_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Copper at (make sure it is greater than the minimum)")
                .defineInRange("nativeCopperOreMax", 75, 0, 256);
        NATIVE_COPPER_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeCopperOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeCopperOreGentype",false);
        NATIVE_COPPER_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Copper vein")
                .defineInRange("nativeCopperOreSize", 10, 0, 256);
        NATIVE_COPPER_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Copper veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeCopperOreCount", 5, 0, 256);
        NATIVE_COPPER_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Copper")
                .defineInRange("nativeCopperOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Tin Settings").push("nativeTinOre");
        NATIVE_TIN_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Tin in the Overworld)")
                .define("nativeTinOreOverworld",true);
        NATIVE_TIN_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Tin in the Nether)")
                .define("nativeTinOreNether",false);
        NATIVE_TIN_ORE_END = COMMON_BUILDER.comment("Enables Native Tin in the End)")
                .define("nativeTinOreEnd",false);
        NATIVE_TIN_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Tin at (make sure it is less than the maximum)")
                .defineInRange("nativeTinOreMin", 50, 0, 256);
        NATIVE_TIN_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Tin at (make sure it is greater than the minimum)")
                .defineInRange("nativeTinOreMax", 75, 0, 256);
        NATIVE_TIN_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeTinOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeTinOreGentype",false);
        NATIVE_TIN_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Tin vein")
                .defineInRange("nativeTinOreSize", 10, 0, 256);
        NATIVE_TIN_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Tin veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeTinOreCount", 5, 0, 256);
        NATIVE_TIN_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Tin")
                .defineInRange("nativeTinOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Gold Settings").push("nativeGoldOre");
        NATIVE_GOLD_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Gold in the Overworld)")
                .define("nativeGoldOreOverworld",true);
        NATIVE_GOLD_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Gold in the Nether)")
                .define("nativeGoldOreNether",false);
        NATIVE_GOLD_ORE_END = COMMON_BUILDER.comment("Enables Native Gold in the End)")
                .define("nativeGoldOreEnd",false);
        NATIVE_GOLD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Gold at (make sure it is less than the maximum)")
                .defineInRange("nativeGoldOreMin", 20, 0, 256);
        NATIVE_GOLD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Gold at (make sure it is greater than the minimum)")
                .defineInRange("nativeGoldOreMax", 75, 0, 256);
        NATIVE_GOLD_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeGoldOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeGoldOreGentype",false);
        NATIVE_GOLD_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Gold vein")
                .defineInRange("nativeGoldOreSize", 9, 0, 256);
        NATIVE_GOLD_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Gold veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeGoldOreCount", 4, 0, 256);
        NATIVE_GOLD_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Gold")
                .defineInRange("nativeGoldOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Aluminum Settings").push("nativeAluminumOre");
        NATIVE_ALUMINUM_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Aluminum in the Overworld)")
                .define("nativeAluminumOreOverworld",true);
        NATIVE_ALUMINUM_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Aluminum in the Nether)")
                .define("nativeAluminumOreNether",false);
        NATIVE_ALUMINUM_ORE_END = COMMON_BUILDER.comment("Enables Native Aluminum in the End)")
                .define("nativeAluminumOreEnd",false);
        NATIVE_ALUMINUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Aluminum at (make sure it is less than the maximum)")
                .defineInRange("nativeAluminumOreMin", 50, 0, 256);
        NATIVE_ALUMINUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Aluminum at (make sure it is greater than the minimum)")
                .defineInRange("nativeAluminumOreMax", 75, 0, 256);
        NATIVE_ALUMINUM_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeAluminumOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeAluminumOreGentype",false);
        NATIVE_ALUMINUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Aluminum vein")
                .defineInRange("nativeAluminumOreSize", 10, 0, 256);
        NATIVE_ALUMINUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Aluminum veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeAluminumOreCount", 4, 0, 256);
        NATIVE_ALUMINUM_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Aluminum")
                .defineInRange("nativeAluminumOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Lead Settings").push("nativeLeadOre");
        NATIVE_LEAD_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Lead in the Overworld)")
                .define("nativeLeadOreOverworld",true);
        NATIVE_LEAD_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Lead in the Nether)")
                .define("nativeLeadOreNether",false);
        NATIVE_LEAD_ORE_END = COMMON_BUILDER.comment("Enables Native Lead in the End)")
                .define("nativeLeadOreEnd",false);
        NATIVE_LEAD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Lead at (make sure it is less than the maximum)")
                .defineInRange("nativeLeadOreMin", 50, 0, 256);
        NATIVE_LEAD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Lead at (make sure it is greater than the minimum)")
                .defineInRange("nativeLeadOreMax", 75, 0, 256);
        NATIVE_LEAD_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeLeadOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeLeadOreGentype",false);
        NATIVE_LEAD_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Lead vein")
                .defineInRange("nativeLeadOreSize", 10, 0, 256);
        NATIVE_LEAD_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Lead veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeLeadOreCount", 3, 0, 256);
        NATIVE_LEAD_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Lead")
                .defineInRange("nativeLeadOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Silver Settings").push("nativeSilverOre");
        NATIVE_SILVER_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Silver in the Overworld)")
                .define("nativeSilverOreOverworld",true);
        NATIVE_SILVER_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Silver in the Nether)")
                .define("nativeSilverOreNether",false);
        NATIVE_SILVER_ORE_END = COMMON_BUILDER.comment("Enables Native Silver in the End)")
                .define("nativeSilverOreEnd",false);
        NATIVE_SILVER_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Silver at (make sure it is less than the maximum)")
                .defineInRange("nativeSilverOreMin", 50, 0, 256);
        NATIVE_SILVER_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Silver at (make sure it is greater than the minimum)")
                .defineInRange("nativeSilverOreMax", 75, 0, 256);
        NATIVE_SILVER_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeSilverOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeSilverOreGentype",false);
        NATIVE_SILVER_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Silver vein")
                .defineInRange("nativeSilverOreSize", 10, 0, 256);
        NATIVE_SILVER_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Silver veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeSilverOreCount", 3, 0, 256);
        NATIVE_SILVER_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Silver")
                .defineInRange("nativeSilverOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Arsenic Settings").push("nativeArsenicOre");
        NATIVE_ARSENIC_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Arsenic in the Overworld)")
                .define("nativeArsenicOreOverworld",false);
        NATIVE_ARSENIC_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Arsenic in the Nether)")
                .define("nativeArsenicOreNether",true);
        NATIVE_ARSENIC_ORE_END = COMMON_BUILDER.comment("Enables Native Arsenic in the End)")
                .define("nativeArsenicOreEnd",false);
        NATIVE_ARSENIC_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Arsenic at (make sure it is less than the maximum)")
                .defineInRange("nativeArsenicOreMin", 30, 0, 256);
        NATIVE_ARSENIC_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Arsenic at (make sure it is greater than the minimum)")
                .defineInRange("nativeArsenicOreMax", 90, 0, 256);
        NATIVE_ARSENIC_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeArsenicOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeArsenicOreGentype",false);
        NATIVE_ARSENIC_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Arsenic vein")
                .defineInRange("nativeArsenicOreSize", 10, 0, 256);
        NATIVE_ARSENIC_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Arsenic veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeArsenicOreCount", 4, 0, 256);
        NATIVE_ARSENIC_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Arsenic")
                .defineInRange("nativeArsenicOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Bismuth Settings").push("nativeBismuthOre");
        NATIVE_BISMUTH_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Bismuth in the Overworld)")
                .define("nativeBismuthOreOverworld",true);
        NATIVE_BISMUTH_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Bismuth in the Nether)")
                .define("nativeBismuthOreNether",false);
        NATIVE_BISMUTH_ORE_END = COMMON_BUILDER.comment("Enables Native Bismuth in the End)")
                .define("nativeBismuthOreEnd",false);
        NATIVE_BISMUTH_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Bismuth at (make sure it is less than the maximum)")
                .defineInRange("nativeBismuthOreMin", 50, 0, 256);
        NATIVE_BISMUTH_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Bismuth at (make sure it is greater than the minimum)")
                .defineInRange("nativeBismuthOreMax", 75, 0, 256);
        NATIVE_BISMUTH_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeBismuthOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeBismuthOreGentype",false);
        NATIVE_BISMUTH_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Bismuth vein")
                .defineInRange("nativeBismuthOreSize", 10, 0, 256);
        NATIVE_BISMUTH_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Bismuth veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeBismuthOreCount", 2, 0, 256);
        NATIVE_BISMUTH_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Bismuth")
                .defineInRange("nativeBismuthOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Sulfur Settings").push("nativeSulfurOre");
        NATIVE_SULFUR_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Sulfur in the Overworld)")
                .define("nativeSulfurOreOverworld",false);
        NATIVE_SULFUR_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Sulfur in the Nether)")
                .define("nativeSulfurOreNether",true);
        NATIVE_SULFUR_ORE_END = COMMON_BUILDER.comment("Enables Native Sulfur in the End)")
                .define("nativeSulfurOreEnd",false);
        NATIVE_SULFUR_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Sulfur at (make sure it is less than the maximum)")
                .defineInRange("nativeSulfurOreMin", 30, 0, 256);
        NATIVE_SULFUR_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Sulfur at (make sure it is greater than the minimum)")
                .defineInRange("nativeSulfurOreMax", 90, 0, 256);
        NATIVE_SULFUR_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeSulfurOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeSulfurOreGentype",false);
        NATIVE_SULFUR_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Sulfur vein")
                .defineInRange("nativeSulfurOreSize", 10, 0, 256);
        NATIVE_SULFUR_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Sulfur veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeSulfurOreCount", 4, 0, 256);
        NATIVE_SULFUR_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Sulfur")
                .defineInRange("nativeSulfurOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Gallium Settings").push("nativeGalliumOre");
        NATIVE_GALLIUM_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Gallium in the Overworld)")
                .define("nativeGalliumOreOverworld",false);
        NATIVE_GALLIUM_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Gallium in the Nether)")
                .define("nativeGalliumOreNether",false);
        NATIVE_GALLIUM_ORE_END = COMMON_BUILDER.comment("Enables Native Gallium in the End)")
                .define("nativeGalliumOreEnd",true);
        NATIVE_GALLIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Gallium at (make sure it is less than the maximum)")
                .defineInRange("nativeGalliumOreMin", 0, 0, 256);
        NATIVE_GALLIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Gallium at (make sure it is greater than the minimum)")
                .defineInRange("nativeGalliumOreMax", 128, 0, 256);
        NATIVE_GALLIUM_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeGalliumOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeGalliumOreGentype",false);
        NATIVE_GALLIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Gallium vein")
                .defineInRange("nativeGalliumOreSize", 9, 0, 256);
        NATIVE_GALLIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Gallium veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeGalliumOreCount", 5, 0, 256);
        NATIVE_GALLIUM_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Gallium")
                .defineInRange("nativeGalliumOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Indium Settings").push("nativeIndiumOre");
        NATIVE_INDIUM_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Indium in the Overworld)")
                .define("nativeIndiumOreOverworld",false);
        NATIVE_INDIUM_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Indium in the Nether)")
                .define("nativeIndiumOreNether",false);
        NATIVE_INDIUM_ORE_END = COMMON_BUILDER.comment("Enables Native Indium in the End)")
                .define("nativeIndiumOreEnd",true);
        NATIVE_INDIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Indium at (make sure it is less than the maximum)")
                .defineInRange("nativeIndiumOreMin", 0, 0, 256);
        NATIVE_INDIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Indium at (make sure it is greater than the minimum)")
                .defineInRange("nativeIndiumOreMax", 128, 0, 256);
        NATIVE_INDIUM_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeIndiumOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeIndiumOreGentype",false);
        NATIVE_INDIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Indium vein")
                .defineInRange("nativeIndiumOreSize", 9, 0, 256);
        NATIVE_INDIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Indium veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeIndiumOreCount", 5, 0, 256);
        NATIVE_INDIUM_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Indium")
                .defineInRange("nativeIndiumOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Tellurium Settings").push("nativeTelluriumOre");
        NATIVE_TELLURIUM_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Tellurium in the Overworld)")
                .define("nativeTelluriumOreOverworld",false);
        NATIVE_TELLURIUM_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Tellurium in the Nether)")
                .define("nativeTelluriumOreNether",false);
        NATIVE_TELLURIUM_ORE_END = COMMON_BUILDER.comment("Enables Native Tellurium in the End)")
                .define("nativeTelluriumOreEnd",true);
        NATIVE_TELLURIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Tellurium at (make sure it is less than the maximum)")
                .defineInRange("nativeTelluriumOreMin", 0, 0, 256);
        NATIVE_TELLURIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Tellurium at (make sure it is greater than the minimum)")
                .defineInRange("nativeTelluriumOreMax", 128, 0, 256);
        NATIVE_TELLURIUM_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeTelluriumOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeTelluriumOreGentype",true);
        NATIVE_TELLURIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Tellurium vein")
                .defineInRange("nativeTelluriumOreSize", 9, 0, 256);
        NATIVE_TELLURIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Tellurium veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeTelluriumOreCount", 5, 0, 256);
        NATIVE_TELLURIUM_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Tellurium")
                .defineInRange("nativeTelluriumOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Selenium Settings").push("nativeSeleniumOre");
        NATIVE_SELENIUM_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Native Selenium in the Overworld)")
                .define("nativeSeleniumOreOverworld",false);
        NATIVE_SELENIUM_ORE_NETHER = COMMON_BUILDER.comment("Enables Native Selenium in the Nether)")
                .define("nativeSeleniumOreNether",false);
        NATIVE_SELENIUM_ORE_END = COMMON_BUILDER.comment("Enables Native Selenium in the End)")
                .define("nativeSeleniumOreEnd",true);
        NATIVE_SELENIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Selenium at (make sure it is less than the maximum)")
                .defineInRange("nativeSeleniumOreMin", 0, 0, 256);
        NATIVE_SELENIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Selenium at (make sure it is greater than the minimum)")
                .defineInRange("nativeSeleniumOreMax", 128, 0, 256);
        NATIVE_SELENIUM_ORE_GENTYPE = COMMON_BUILDER.comment("If true, nativeSeleniumOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("nativeSeleniumOreGentype",false);
        NATIVE_SELENIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Selenium vein")
                .defineInRange("nativeSeleniumOreSize", 9, 0, 256);
        NATIVE_SELENIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Selenium veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("nativeSeleniumOreCount", 5, 0, 256);
        NATIVE_SELENIUM_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native Selenium")
                .defineInRange("nativeSeleniumOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Quartz Settings").push("QuartzOre");
        QUARTZ_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Quartz in the Overworld)")
                .define("QuartzOreOverworld",false);
        QUARTZ_ORE_NETHER = COMMON_BUILDER.comment("Enables Quartz in the Nether)")
                .define("QuartzOreNether",false);
        QUARTZ_ORE_END = COMMON_BUILDER.comment("Enables Quartz in the End)")
                .define("QuartzOreEnd",false);
        QUARTZ_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Quartz at (make sure it is less than the maximum)")
                .defineInRange("QuartzOreMin", 0, 0, 256);
        QUARTZ_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Quartz at (make sure it is greater than the minimum)")
                .defineInRange("QuartzOreMax", 128, 0, 256);
        QUARTZ_ORE_GENTYPE = COMMON_BUILDER.comment("If true, QuartzOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("QuartzOreGentype",true);
        QUARTZ_ORE_SIZE = COMMON_BUILDER.comment("Size of Quartz vein")
                .defineInRange("QuartzOreSize", 30, 0, 256);
        QUARTZ_ORE_COUNT = COMMON_BUILDER.comment("Number of Quartz veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("QuartzOreCount", 2, 0, 256);
        QUARTZ_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Quartz")
                .defineInRange("QuartzOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Opal Settings").push("OpalOre");
        OPAL_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Opal in the Overworld)")
                .define("OpalOreOverworld",false);
        OPAL_ORE_NETHER = COMMON_BUILDER.comment("Enables Opal in the Nether)")
                .define("OpalOreNether",false);
        OPAL_ORE_END = COMMON_BUILDER.comment("Enables Opal in the End)")
                .define("OpalOreEnd",false);
        OPAL_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Opal at (make sure it is less than the maximum)")
                .defineInRange("OpalOreMin", 0, 0, 256);
        OPAL_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Opal at (make sure it is greater than the minimum)")
                .defineInRange("OpalOreMax", 128, 0, 256);
        OPAL_ORE_GENTYPE = COMMON_BUILDER.comment("If true, OpalOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("OpalOreGentype",true);
        OPAL_ORE_SIZE = COMMON_BUILDER.comment("Size of Opal vein")
                .defineInRange("OpalOreSize", 30, 0, 256);
        OPAL_ORE_COUNT = COMMON_BUILDER.comment("Number of Opal veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("OpalOreCount", 2, 0, 256);
        OPAL_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Opal")
                .defineInRange("OpalOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Fluorite Settings").push("FluoriteOre");
        FLUORITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Fluorite in the Overworld)")
                .define("FluoriteOreOverworld",false);
        FLUORITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Fluorite in the Nether)")
                .define("FluoriteOreNether",false);
        FLUORITE_ORE_END = COMMON_BUILDER.comment("Enables Fluorite in the End)")
                .define("FluoriteOreEnd",true);
        FLUORITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Fluorite at (make sure it is less than the maximum)")
                .defineInRange("FluoriteOreMin", 0, 0, 256);
        FLUORITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Fluorite at (make sure it is greater than the minimum)")
                .defineInRange("FluoriteOreMax", 128, 0, 256);
        FLUORITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, FluoriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("FluoriteOreGentype",false);
        FLUORITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Fluorite vein")
                .defineInRange("FluoriteOreSize", 16, 0, 256);
        FLUORITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Fluorite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("FluoriteOreCount", 5, 0, 256);
        FLUORITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Fluorite")
                .defineInRange("FluoriteOreHL", 4, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Uraninite Settings").push("UraniniteOre");
        URANINITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Uraninite in the Overworld)")
                .define("UraniniteOreOverworld",false);
        URANINITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Uraninite in the Nether)")
                .define("UraniniteOreNether",false);
        URANINITE_ORE_END = COMMON_BUILDER.comment("Enables Uraninite in the End)")
                .define("UraniniteOreEnd",true);
        URANINITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Uraninite at (make sure it is less than the maximum)")
                .defineInRange("UraniniteOreMin", 0, 0, 256);
        URANINITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Uraninite at (make sure it is greater than the minimum)")
                .defineInRange("UraniniteOreMax", 128, 0, 256);
        URANINITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, UraniniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("UraniniteOreGentype",false);
        URANINITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Uraninite vein")
                .defineInRange("UraniniteOreSize", 10, 0, 256);
        URANINITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Uraninite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("UraniniteOreCount", 5, 0, 256);
        URANINITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Uraninite")
                .defineInRange("UraniniteOreHL", 4, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Stibnite Settings").push("StibniteOre");
        STIBNITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Stibnite in the Overworld)")
                .define("StibniteOreOverworld",true);
        STIBNITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Stibnite in the Nether)")
                .define("StibniteOreNether",false);
        STIBNITE_ORE_END = COMMON_BUILDER.comment("Enables Stibnite in the End)")
                .define("StibniteOreEnd",false);
        STIBNITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Stibnite at (make sure it is less than the maximum)")
                .defineInRange("StibniteOreMin", 50, 0, 256);
        STIBNITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Stibnite at (make sure it is greater than the minimum)")
                .defineInRange("StibniteOreMax", 80, 0, 256);
        STIBNITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, StibniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("StibniteOreGentype",false);
        STIBNITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Stibnite vein")
                .defineInRange("StibniteOreSize", 4, 0, 256);
        STIBNITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Stibnite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("StibniteOreCount", 3, 0, 256);
        STIBNITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Stibnite")
                .defineInRange("StibniteOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Xenotime Settings").push("XenotimeOre");
        XENOTIME_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Xenotime in the Overworld)")
                .define("XenotimeOreOverworld",false);
        XENOTIME_ORE_NETHER = COMMON_BUILDER.comment("Enables Xenotime in the Nether)")
                .define("XenotimeOreNether",false);
        XENOTIME_ORE_END = COMMON_BUILDER.comment("Enables Xenotime in the End)")
                .define("XenotimeOreEnd",true);
        XENOTIME_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Xenotime at (make sure it is less than the maximum)")
                .defineInRange("XenotimeOreMin", 0, 0, 256);
        XENOTIME_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Xenotime at (make sure it is greater than the minimum)")
                .defineInRange("XenotimeOreMax", 128, 0, 256);
        XENOTIME_ORE_GENTYPE = COMMON_BUILDER.comment("If true, XenotimeOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("XenotimeOreGentype",false);
        XENOTIME_ORE_SIZE = COMMON_BUILDER.comment("Size of Xenotime vein")
                .defineInRange("XenotimeOreSize", 8, 0, 256);
        XENOTIME_ORE_COUNT = COMMON_BUILDER.comment("Number of Xenotime veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("XenotimeOreCount", 5, 0, 256);
        XENOTIME_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Xenotime")
                .defineInRange("XenotimeOreHL", 4, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Halite Settings").push("HaliteOre");
        HALITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Halite in the Overworld)")
                .define("HaliteOreOverworld",true);
        HALITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Halite in the Nether)")
                .define("HaliteOreNether",false);
        HALITE_ORE_END = COMMON_BUILDER.comment("Enables Halite in the End)")
                .define("HaliteOreEnd",false);
        HALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Halite at (make sure it is less than the maximum)")
                .defineInRange("HaliteOreMin", 30, 0, 256);
        HALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Halite at (make sure it is greater than the minimum)")
                .defineInRange("HaliteOreMax", 80, 0, 256);
        HALITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, HaliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("HaliteOreGentype",true);
        HALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Halite vein")
                .defineInRange("HaliteOreSize", 20, 0, 256);
        HALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Halite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("HaliteOreCount", 2, 0, 256);
        HALITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Halite")
                .defineInRange("HaliteOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Pink Halite Settings").push("pinkHaliteOre");
        PINK_HALITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Pink Halite in the Overworld)")
                .define("pinkHaliteOreOverworld",true);
        PINK_HALITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Pink Halite in the Nether)")
                .define("pinkHaliteOreNether",false);
        PINK_HALITE_ORE_END = COMMON_BUILDER.comment("Enables Pink Halite in the End)")
                .define("pinkHaliteOreEnd",false);
        PINK_HALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pink Halite at (make sure it is less than the maximum)")
                .defineInRange("pinkHaliteOreMin", 70, 0, 256);
        PINK_HALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pink Halite at (make sure it is greater than the minimum)")
                .defineInRange("pinkHaliteOreMax", 140, 0, 256);
        PINK_HALITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, pinkHaliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("pinkHaliteOreGentype",true);
        PINK_HALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Pink Halite vein")
                .defineInRange("pinkHaliteOreSize", 20, 0, 256);
        PINK_HALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Pink Halite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("pinkHaliteOreCount", 2, 0, 256);
        PINK_HALITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Pink Halite")
                .defineInRange("pinkHaliteOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Interspinifex Settings").push("InterspinifexOre");
        INTERSPINIFEX_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Interspinifex in the Overworld)")
                .define("InterspinifexOreOverworld",false);
        INTERSPINIFEX_ORE_NETHER = COMMON_BUILDER.comment("Enables Interspinifex in the Nether)")
                .define("InterspinifexOreNether",false);
        INTERSPINIFEX_ORE_END = COMMON_BUILDER.comment("Enables Interspinifex in the End)")
                .define("InterspinifexOreEnd",false);
        INTERSPINIFEX_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Interspinifex at (make sure it is less than the maximum)")
                .defineInRange("InterspinifexOreMin", 0, 0, 256);
        INTERSPINIFEX_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Interspinifex at (make sure it is greater than the minimum)")
                .defineInRange("InterspinifexOreMax", 128, 0, 256);
        INTERSPINIFEX_ORE_GENTYPE = COMMON_BUILDER.comment("If true, InterspinifexOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("InterspinifexOreGentype",true);
        INTERSPINIFEX_ORE_SIZE = COMMON_BUILDER.comment("Size of Interspinifex vein")
                .defineInRange("InterspinifexOreSize", 30, 0, 256);
        INTERSPINIFEX_ORE_COUNT = COMMON_BUILDER.comment("Number of Interspinifex veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("InterspinifexOreCount", 2, 0, 256);
        INTERSPINIFEX_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Interspinifex")
                .defineInRange("InterspinifexOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Petalite Settings").push("PetaliteOre");
        PETALITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Petalite in the Overworld)")
                .define("PetaliteOreOverworld",false);
        PETALITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Petalite in the Nether)")
                .define("PetaliteOreNether",true);
        PETALITE_ORE_END = COMMON_BUILDER.comment("Enables Petalite in the End)")
                .define("PetaliteOreEnd",false);
        PETALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Petalite at (make sure it is less than the maximum)")
                .defineInRange("PetaliteOreMin", 45, 0, 256);
        PETALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Petalite at (make sure it is greater than the minimum)")
                .defineInRange("PetaliteOreMax", 90, 0, 256);
        PETALITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, PetaliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("PetaliteOreGentype",true);
        PETALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Petalite vein")
                .defineInRange("PetaliteOreSize", 20, 0, 256);
        PETALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Petalite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("PetaliteOreCount", 2, 0, 256);
        PETALITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Petalite")
                .defineInRange("PetaliteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Cobaltite Settings").push("CobaltiteOre");
        COBALTITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Cobaltite in the Overworld)")
                .define("CobaltiteOreOverworld",false);
        COBALTITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Cobaltite in the Nether)")
                .define("CobaltiteOreNether",true);
        COBALTITE_ORE_END = COMMON_BUILDER.comment("Enables Cobaltite in the End)")
                .define("CobaltiteOreEnd",false);
        COBALTITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cobaltite at (make sure it is less than the maximum)")
                .defineInRange("CobaltiteOreMin", 45, 0, 256);
        COBALTITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cobaltite at (make sure it is greater than the minimum)")
                .defineInRange("CobaltiteOreMax", 90, 0, 256);
        COBALTITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, CobaltiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("CobaltiteOreGentype",true);
        COBALTITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Cobaltite vein")
                .defineInRange("CobaltiteOreSize", 20, 0, 256);
        COBALTITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Cobaltite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("CobaltiteOreCount", 2, 0, 256);
        COBALTITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Cobaltite")
                .defineInRange("CobaltiteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Majorite Settings").push("MajoriteOre");
        MAJORITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Majorite in the Overworld)")
                .define("MajoriteOreOverworld",true);
        MAJORITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Majorite in the Nether)")
                .define("MajoriteOreNether",false);
        MAJORITE_ORE_END = COMMON_BUILDER.comment("Enables Majorite in the End)")
                .define("MajoriteOreEnd",false);
        MAJORITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Majorite at (make sure it is less than the maximum)")
                .defineInRange("MajoriteOreMin", 0, 0, 256);
        MAJORITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Majorite at (make sure it is greater than the minimum)")
                .defineInRange("MajoriteOreMax", 80, 0, 256);
        MAJORITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, MajoriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("MajoriteOreGentype",false);
        MAJORITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Majorite vein")
                .defineInRange("MajoriteOreSize", 5, 0, 256);
        MAJORITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Majorite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("MajoriteOreCount", 1, 0, 256);
        MAJORITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Majorite")
                .defineInRange("MajoriteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Celestine Settings").push("CelestineOre");
        CELESTINE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Celestine in the Overworld)")
                .define("CelestineOreOverworld",true);
        CELESTINE_ORE_NETHER = COMMON_BUILDER.comment("Enables Celestine in the Nether)")
                .define("CelestineOreNether",false);
        CELESTINE_ORE_END = COMMON_BUILDER.comment("Enables Celestine in the End)")
                .define("CelestineOreEnd",false);
        CELESTINE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Celestine at (make sure it is less than the maximum)")
                .defineInRange("CelestineOreMin", 20, 0, 256);
        CELESTINE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Celestine at (make sure it is greater than the minimum)")
                .defineInRange("CelestineOreMax", 50, 0, 256);
        CELESTINE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, CelestineOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("CelestineOreGentype",true);
        CELESTINE_ORE_SIZE = COMMON_BUILDER.comment("Size of Celestine vein")
                .defineInRange("CelestineOreSize", 12, 0, 256);
        CELESTINE_ORE_COUNT = COMMON_BUILDER.comment("Number of Celestine veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("CelestineOreCount", 2, 0, 256);
        CELESTINE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Celestine")
                .defineInRange("CelestineOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Cryolite Settings").push("CryoliteOre");
        CRYOLITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Cryolite in the Overworld)")
                .define("CryoliteOreOverworld",true);
        CRYOLITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Cryolite in the Nether)")
                .define("CryoliteOreNether",false);
        CRYOLITE_ORE_END = COMMON_BUILDER.comment("Enables Cryolite in the End)")
                .define("CryoliteOreEnd",false);
        CRYOLITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cryolite at (make sure it is less than the maximum)")
                .defineInRange("CryoliteOreMin", 0, 0, 256);
        CRYOLITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cryolite at (make sure it is greater than the minimum)")
                .defineInRange("CryoliteOreMax", 30, 0, 256);
        CRYOLITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, CryoliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("CryoliteOreGentype",true);
        CRYOLITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Cryolite vein")
                .defineInRange("CryoliteOreSize", 24, 0, 256);
        CRYOLITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Cryolite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("CryoliteOreCount", 4, 0, 256);
        CRYOLITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Cryolite")
                .defineInRange("CryoliteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Pyrite Settings").push("PyriteOre");
        PYRITE_ORE_OVERWORLD = COMMON_BUILDER.comment("Enables Pyrite in the Overworld)")
                .define("PyriteOreOverworld",true);
        PYRITE_ORE_NETHER = COMMON_BUILDER.comment("Enables Pyrite in the Nether)")
                .define("PyriteOreNether",false);
        PYRITE_ORE_END = COMMON_BUILDER.comment("Enables Pyrite in the End)")
                .define("PyriteOreEnd",false);
        PYRITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pyrite at (make sure it is less than the maximum)")
                .defineInRange("PyriteOreMin", 20, 0, 256);
        PYRITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pyrite at (make sure it is greater than the minimum)")
                .defineInRange("PyriteOreMax", 80, 0, 256);
        PYRITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, PyriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("PyriteOreGentype",false);
        PYRITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Pyrite vein")
                .defineInRange("PyriteOreSize", 9, 0, 256);
        PYRITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Pyrite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("PyriteOreCount", 3, 0, 256);
        PYRITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Pyrite")
                .defineInRange("PyriteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();


        //end oregen
        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }


}
