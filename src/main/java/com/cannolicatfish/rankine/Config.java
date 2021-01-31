package com.cannolicatfish.rankine;


import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static ForgeConfigSpec.IntValue ALNICO_MAGNET_RANGE;
    public static ForgeConfigSpec.IntValue RARE_MAGNET_RANGE;
    public static ForgeConfigSpec.BooleanValue ELECTROMAGNET_MATERIAL_REQ;
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

    public static ForgeConfigSpec.BooleanValue NATIVE_COPPER_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_COPPER_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_COPPER_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_COPPER_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_TIN_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_TIN_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_TIN_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_TIN_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_GOLD_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_GOLD_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_GOLD_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_GOLD_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_ALUMINUM_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_ALUMINUM_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_ALUMINUM_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_ALUMINUM_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_LEAD_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_LEAD_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_LEAD_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_LEAD_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_SILVER_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_SILVER_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SILVER_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SILVER_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_ARSENIC_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_ARSENIC_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_ARSENIC_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_ARSENIC_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_BISMUTH_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_BISMUTH_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_BISMUTH_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_BISMUTH_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_SULFUR_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_SULFUR_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SULFUR_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SULFUR_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_GALLIUM_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_GALLIUM_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_GALLIUM_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_GALLIUM_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_INDIUM_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_INDIUM_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_INDIUM_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_INDIUM_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_TELLURIUM_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_TELLURIUM_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_TELLURIUM_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_TELLURIUM_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue NATIVE_SELENIUM_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_COUNT;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_HL;
    public static ForgeConfigSpec.BooleanValue NATIVE_SELENIUM_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SELENIUM_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SELENIUM_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue MALACHITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MALACHITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> MALACHITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> MALACHITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue CASSITERITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue CASSITERITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> CASSITERITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> CASSITERITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue BAUXITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue BAUXITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> BAUXITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> BAUXITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue SPHALERITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue SPHALERITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> SPHALERITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> SPHALERITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue CINNABAR_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_COUNT;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_HL;
    public static ForgeConfigSpec.BooleanValue CINNABAR_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> CINNABAR_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> CINNABAR_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue MAGNETITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MAGNETITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> MAGNETITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> MAGNETITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue PENTLANDITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PENTLANDITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> PENTLANDITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> PENTLANDITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue MAGNESITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MAGNESITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> MAGNESITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> MAGNESITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue GALENA_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue GALENA_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue GALENA_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue GALENA_ORE_SIZE;
    public static ForgeConfigSpec.IntValue GALENA_ORE_COUNT;
    public static ForgeConfigSpec.IntValue GALENA_ORE_HL;
    public static ForgeConfigSpec.BooleanValue GALENA_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> GALENA_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> GALENA_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue VANADINITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue VANADINITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> VANADINITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> VANADINITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue BISMUTHINITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue BISMUTHINITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> BISMUTHINITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> BISMUTHINITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue ACANTHITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue ACANTHITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> ACANTHITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> ACANTHITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue PYROLUSITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PYROLUSITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> PYROLUSITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> PYROLUSITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue CHROMITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue CHROMITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> CHROMITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> CHROMITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue MOLYBDENITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue MOLYBDENITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MOLYBDENITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MOLYBDENITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MOLYBDENITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MOLYBDENITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MOLYBDENITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOLYBDENITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOLYBDENITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue ILMENITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue ILMENITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> ILMENITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> ILMENITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue COLUMBITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue COLUMBITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> COLUMBITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> COLUMBITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue WOLFRAMITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue WOLFRAMITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> WOLFRAMITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> WOLFRAMITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue TANTALITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue TANTALITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> TANTALITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> TANTALITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue PLUMBAGO_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PLUMBAGO_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> PLUMBAGO_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> PLUMBAGO_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue MOISSANITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MOISSANITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOISSANITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOISSANITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue SPERRYLITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue SPERRYLITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> SPERRYLITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> SPERRYLITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue LIGNITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue LIGNITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> LIGNITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> LIGNITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue SUBBITUMINOUS_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_SIZE;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_COUNT;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_HL;
    public static ForgeConfigSpec.BooleanValue SUBBITUMINOUS_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> SUBBITUMINOUS_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> SUBBITUMINOUS_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue BITUMINOUS_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue BITUMINOUS_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue BITUMINOUS_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue BITUMINOUS_ORE_SIZE;
    public static ForgeConfigSpec.IntValue BITUMINOUS_ORE_COUNT;
    public static ForgeConfigSpec.IntValue BITUMINOUS_ORE_HL;
    public static ForgeConfigSpec.BooleanValue BITUMINOUS_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> BITUMINOUS_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> BITUMINOUS_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue ANTHRACITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue ANTHRACITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> ANTHRACITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> ANTHRACITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue LAZURITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue LAZURITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> LAZURITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> LAZURITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue DIAMOND_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue DIAMOND_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue DIAMOND_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue DIAMOND_ORE_SIZE;
    public static ForgeConfigSpec.IntValue DIAMOND_ORE_COUNT;
    public static ForgeConfigSpec.IntValue DIAMOND_ORE_HL;
    public static ForgeConfigSpec.BooleanValue DIAMOND_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> DIAMOND_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> DIAMOND_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue GREENOCKITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue GREENOCKITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> GREENOCKITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> GREENOCKITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue EMERALD_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_COUNT;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_HL;
    public static ForgeConfigSpec.BooleanValue EMERALD_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> EMERALD_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> EMERALD_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue AQUAMARINE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue AQUAMARINE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> AQUAMARINE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> AQUAMARINE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue QUARTZ_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue QUARTZ_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue QUARTZ_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue QUARTZ_ORE_SIZE;
    public static ForgeConfigSpec.IntValue QUARTZ_ORE_COUNT;
    public static ForgeConfigSpec.IntValue QUARTZ_ORE_HL;
    public static ForgeConfigSpec.BooleanValue QUARTZ_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> QUARTZ_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> QUARTZ_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue OPAL_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue OPAL_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue OPAL_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue OPAL_ORE_SIZE;
    public static ForgeConfigSpec.IntValue OPAL_ORE_COUNT;
    public static ForgeConfigSpec.IntValue OPAL_ORE_HL;
    public static ForgeConfigSpec.BooleanValue OPAL_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> OPAL_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> OPAL_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue MAJORITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue MAJORITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> MAJORITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> MAJORITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue FLUORITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue FLUORITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> FLUORITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> FLUORITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue URANINITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue URANINITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> URANINITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> URANINITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue STIBNITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue STIBNITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> STIBNITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> STIBNITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue XENOTIME_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_SIZE;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_COUNT;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_HL;
    public static ForgeConfigSpec.BooleanValue XENOTIME_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> XENOTIME_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> XENOTIME_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue HALITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue HALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue HALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue HALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue HALITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue HALITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue HALITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> HALITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> HALITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue PINK_HALITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PINK_HALITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> PINK_HALITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> PINK_HALITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue INTERSPINIFEX_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_SIZE;
    public static ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_COUNT;
    public static ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_HL;
    public static ForgeConfigSpec.BooleanValue INTERSPINIFEX_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> INTERSPINIFEX_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> INTERSPINIFEX_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue PETALITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PETALITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> PETALITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> PETALITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue COBALTITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue COBALTITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> COBALTITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> COBALTITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue CRYOLITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue CRYOLITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CRYOLITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CRYOLITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CRYOLITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue CRYOLITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue CRYOLITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> CRYOLITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> CRYOLITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue PYRITE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue PYRITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PYRITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PYRITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PYRITE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue PYRITE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue PYRITE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> PYRITE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> PYRITE_ORE_DIMENSION_LIST;
    public static ForgeConfigSpec.BooleanValue CELESTINE_ORE_STONE_SPECIFIC;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_COUNT;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_HL;
    public static ForgeConfigSpec.BooleanValue CELESTINE_ORE_GENTYPE;
    public static ForgeConfigSpec.ConfigValue<List<String>> CELESTINE_BLOCK_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> CELESTINE_ORE_DIMENSION_LIST;

    public static ForgeConfigSpec.ConfigValue<List<String>> OCEAN_STONE_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> DESERT_STONE_LIST;



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
                    .defineInRange("bricksHardnessMultiplier", 1.5D, 0.0D, 20.0D);
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
            CHARCOAL_PIT_SPEED = COMMON_BUILDER.comment("The number of random ticks it takes the Charcoal Pit to process")
                    .defineInRange("charcoalPitSpeed", 5, 1, 200);
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
            ALNICO_MAGNET_RANGE = COMMON_BUILDER.comment("Range for the Alnico Magnet's pickup radius and the Alnico Electromagnet range.")
                    .defineInRange("alnicoMagnetRange",5,1,10);
            RARE_MAGNET_RANGE = COMMON_BUILDER.comment("Range for the Rare Earth Magnet's pickup radius and the Rare Earth Electromagnet range.")
                    .defineInRange("rareEarthMagnetRange",10,1,15);
            ELECTROMAGNET_MATERIAL_REQ = COMMON_BUILDER.comment("Require the material of the block to be Material.IRON in order for the electromagnet to pull the block. If disabled, it will pick up any block as long as it is not a FluidBlock, Tile Entity, or in the rankine:magnet_banned tag (these blocks are also banned if this value is true).")
                    .define("electromagnetMaterialReq",true);
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
                .defineInRange("meteorChance", 100, 0, 1000);
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


        COMMON_BUILDER.comment("Stone Layers").push("stoneLayers");
        OCEAN_STONE_LIST = COMMON_BUILDER.comment("Blocks to generate in Ocean Biomes. Layers generate from bottom to top.")
                .define("OceanBlockList", new ArrayList<>(Arrays.asList("rankine:peridotite","rankine:gabbro","rankine:tholeiitic_basalt","rankine:slate","rankine:limestone","rankine:breccia","rankine:shale")));
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


        COMMON_BUILDER.comment("Native_copper Settings").push("Native_copperOre");
        NATIVE_COPPER_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_copper Ore in (overworld / nether / end)")
                .define("Native_copperOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        NATIVE_COPPER_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_copperBlockList to determine what blocks Native_copper Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_copperOreStoneSpecific",false);
        NATIVE_COPPER_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_copper Ore in if Native_copperOreStoneSpecific is enabled.")
                .define("Native_copperBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_COPPER_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_copper at (make sure it is less than the maximum)")
                .defineInRange("Native_copperOreMin", 60, 0, 256);
        NATIVE_COPPER_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_copper at (make sure it is greater than the minimum)")
                .defineInRange("Native_copperOreMax", 85, 0, 256);
        NATIVE_COPPER_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_copperOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_copperOreGentype",false);
        NATIVE_COPPER_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_copper vein")
                .defineInRange("Native_copperOreSize", 9, 0, 256);
        NATIVE_COPPER_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_copper veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_copperOreCount", 5, 0, 256);
        NATIVE_COPPER_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_copper")
                .defineInRange("Native_copperOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_tin Settings").push("Native_tinOre");
        NATIVE_TIN_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_tin Ore in (overworld / nether / end)")
                .define("Native_tinOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        NATIVE_TIN_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_tinBlockList to determine what blocks Native_tin Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_tinOreStoneSpecific",false);
        NATIVE_TIN_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_tin Ore in if Native_tinOreStoneSpecific is enabled.")
                .define("Native_tinBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_TIN_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_tin at (make sure it is less than the maximum)")
                .defineInRange("Native_tinOreMin", 60, 0, 256);
        NATIVE_TIN_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_tin at (make sure it is greater than the minimum)")
                .defineInRange("Native_tinOreMax", 85, 0, 256);
        NATIVE_TIN_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_tinOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_tinOreGentype",false);
        NATIVE_TIN_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_tin vein")
                .defineInRange("Native_tinOreSize", 9, 0, 256);
        NATIVE_TIN_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_tin veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_tinOreCount", 5, 0, 256);
        NATIVE_TIN_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_tin")
                .defineInRange("Native_tinOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_gold Settings").push("Native_goldOre");
        NATIVE_GOLD_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_gold Ore in (overworld / nether / end)")
                .define("Native_goldOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        NATIVE_GOLD_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_goldBlockList to determine what blocks Native_gold Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_goldOreStoneSpecific",false);
        NATIVE_GOLD_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_gold Ore in if Native_goldOreStoneSpecific is enabled.")
                .define("Native_goldBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_GOLD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_gold at (make sure it is less than the maximum)")
                .defineInRange("Native_goldOreMin", 25, 0, 256);
        NATIVE_GOLD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_gold at (make sure it is greater than the minimum)")
                .defineInRange("Native_goldOreMax", 85, 0, 256);
        NATIVE_GOLD_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_goldOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_goldOreGentype",false);
        NATIVE_GOLD_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_gold vein")
                .defineInRange("Native_goldOreSize", 9, 0, 256);
        NATIVE_GOLD_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_gold veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_goldOreCount", 4, 0, 256);
        NATIVE_GOLD_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_gold")
                .defineInRange("Native_goldOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_aluminum Settings").push("Native_aluminumOre");
        NATIVE_ALUMINUM_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_aluminum Ore in (overworld / nether / end)")
                .define("Native_aluminumOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        NATIVE_ALUMINUM_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_aluminumBlockList to determine what blocks Native_aluminum Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_aluminumOreStoneSpecific",false);
        NATIVE_ALUMINUM_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_aluminum Ore in if Native_aluminumOreStoneSpecific is enabled.")
                .define("Native_aluminumBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_ALUMINUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_aluminum at (make sure it is less than the maximum)")
                .defineInRange("Native_aluminumOreMin", 60, 0, 256);
        NATIVE_ALUMINUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_aluminum at (make sure it is greater than the minimum)")
                .defineInRange("Native_aluminumOreMax", 85, 0, 256);
        NATIVE_ALUMINUM_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_aluminumOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_aluminumOreGentype",false);
        NATIVE_ALUMINUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_aluminum vein")
                .defineInRange("Native_aluminumOreSize", 9, 0, 256);
        NATIVE_ALUMINUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_aluminum veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_aluminumOreCount", 5, 0, 256);
        NATIVE_ALUMINUM_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_aluminum")
                .defineInRange("Native_aluminumOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_lead Settings").push("Native_leadOre");
        NATIVE_LEAD_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_lead Ore in (overworld / nether / end)")
                .define("Native_leadOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        NATIVE_LEAD_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_leadBlockList to determine what blocks Native_lead Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_leadOreStoneSpecific",false);
        NATIVE_LEAD_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_lead Ore in if Native_leadOreStoneSpecific is enabled.")
                .define("Native_leadBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_LEAD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_lead at (make sure it is less than the maximum)")
                .defineInRange("Native_leadOreMin", 60, 0, 256);
        NATIVE_LEAD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_lead at (make sure it is greater than the minimum)")
                .defineInRange("Native_leadOreMax", 85, 0, 256);
        NATIVE_LEAD_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_leadOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_leadOreGentype",false);
        NATIVE_LEAD_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_lead vein")
                .defineInRange("Native_leadOreSize", 9, 0, 256);
        NATIVE_LEAD_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_lead veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_leadOreCount", 3, 0, 256);
        NATIVE_LEAD_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_lead")
                .defineInRange("Native_leadOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_silver Settings").push("Native_silverOre");
        NATIVE_SILVER_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_silver Ore in (overworld / nether / end)")
                .define("Native_silverOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        NATIVE_SILVER_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_silverBlockList to determine what blocks Native_silver Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_silverOreStoneSpecific",false);
        NATIVE_SILVER_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_silver Ore in if Native_silverOreStoneSpecific is enabled.")
                .define("Native_silverBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_SILVER_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_silver at (make sure it is less than the maximum)")
                .defineInRange("Native_silverOreMin", 60, 0, 256);
        NATIVE_SILVER_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_silver at (make sure it is greater than the minimum)")
                .defineInRange("Native_silverOreMax", 85, 0, 256);
        NATIVE_SILVER_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_silverOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_silverOreGentype",false);
        NATIVE_SILVER_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_silver vein")
                .defineInRange("Native_silverOreSize", 9, 0, 256);
        NATIVE_SILVER_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_silver veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_silverOreCount", 3, 0, 256);
        NATIVE_SILVER_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_silver")
                .defineInRange("Native_silverOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_arsenic Settings").push("Native_arsenicOre");
        NATIVE_ARSENIC_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_arsenic Ore in (overworld / nether / end)")
                .define("Native_arsenicOreDimList", new ArrayList<>(Arrays.asList("nether")));
        NATIVE_ARSENIC_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_arsenicBlockList to determine what blocks Native_arsenic Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_arsenicOreStoneSpecific",false);
        NATIVE_ARSENIC_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_arsenic Ore in if Native_arsenicOreStoneSpecific is enabled.")
                .define("Native_arsenicBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_ARSENIC_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_arsenic at (make sure it is less than the maximum)")
                .defineInRange("Native_arsenicOreMin", 45, 0, 256);
        NATIVE_ARSENIC_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_arsenic at (make sure it is greater than the minimum)")
                .defineInRange("Native_arsenicOreMax", 90, 0, 256);
        NATIVE_ARSENIC_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_arsenicOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_arsenicOreGentype",false);
        NATIVE_ARSENIC_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_arsenic vein")
                .defineInRange("Native_arsenicOreSize", 9, 0, 256);
        NATIVE_ARSENIC_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_arsenic veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_arsenicOreCount", 4, 0, 256);
        NATIVE_ARSENIC_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_arsenic")
                .defineInRange("Native_arsenicOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_bismuth Settings").push("Native_bismuthOre");
        NATIVE_BISMUTH_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_bismuth Ore in (overworld / nether / end)")
                .define("Native_bismuthOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        NATIVE_BISMUTH_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_bismuthBlockList to determine what blocks Native_bismuth Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_bismuthOreStoneSpecific",false);
        NATIVE_BISMUTH_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_bismuth Ore in if Native_bismuthOreStoneSpecific is enabled.")
                .define("Native_bismuthBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_BISMUTH_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_bismuth at (make sure it is less than the maximum)")
                .defineInRange("Native_bismuthOreMin", 60, 0, 256);
        NATIVE_BISMUTH_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_bismuth at (make sure it is greater than the minimum)")
                .defineInRange("Native_bismuthOreMax", 85, 0, 256);
        NATIVE_BISMUTH_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_bismuthOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_bismuthOreGentype",false);
        NATIVE_BISMUTH_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_bismuth vein")
                .defineInRange("Native_bismuthOreSize", 7, 0, 256);
        NATIVE_BISMUTH_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_bismuth veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_bismuthOreCount", 2, 0, 256);
        NATIVE_BISMUTH_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_bismuth")
                .defineInRange("Native_bismuthOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_sulfur Settings").push("Native_sulfurOre");
        NATIVE_SULFUR_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_sulfur Ore in (overworld / nether / end)")
                .define("Native_sulfurOreDimList", new ArrayList<>(Arrays.asList("nether")));
        NATIVE_SULFUR_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_sulfurBlockList to determine what blocks Native_sulfur Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_sulfurOreStoneSpecific",false);
        NATIVE_SULFUR_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_sulfur Ore in if Native_sulfurOreStoneSpecific is enabled.")
                .define("Native_sulfurBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_SULFUR_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_sulfur at (make sure it is less than the maximum)")
                .defineInRange("Native_sulfurOreMin", 45, 0, 256);
        NATIVE_SULFUR_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_sulfur at (make sure it is greater than the minimum)")
                .defineInRange("Native_sulfurOreMax", 90, 0, 256);
        NATIVE_SULFUR_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_sulfurOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_sulfurOreGentype",false);
        NATIVE_SULFUR_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_sulfur vein")
                .defineInRange("Native_sulfurOreSize", 9, 0, 256);
        NATIVE_SULFUR_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_sulfur veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_sulfurOreCount", 4, 0, 256);
        NATIVE_SULFUR_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_sulfur")
                .defineInRange("Native_sulfurOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_gallium Settings").push("Native_galliumOre");
        NATIVE_GALLIUM_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_gallium Ore in (overworld / nether / end)")
                .define("Native_galliumOreDimList", new ArrayList<>(Arrays.asList("end")));
        NATIVE_GALLIUM_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_galliumBlockList to determine what blocks Native_gallium Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_galliumOreStoneSpecific",false);
        NATIVE_GALLIUM_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_gallium Ore in if Native_galliumOreStoneSpecific is enabled.")
                .define("Native_galliumBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_GALLIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_gallium at (make sure it is less than the maximum)")
                .defineInRange("Native_galliumOreMin", 0, 0, 256);
        NATIVE_GALLIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_gallium at (make sure it is greater than the minimum)")
                .defineInRange("Native_galliumOreMax", 100, 0, 256);
        NATIVE_GALLIUM_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_galliumOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_galliumOreGentype",false);
        NATIVE_GALLIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_gallium vein")
                .defineInRange("Native_galliumOreSize", 9, 0, 256);
        NATIVE_GALLIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_gallium veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_galliumOreCount", 5, 0, 256);
        NATIVE_GALLIUM_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_gallium")
                .defineInRange("Native_galliumOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_indium Settings").push("Native_indiumOre");
        NATIVE_INDIUM_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_indium Ore in (overworld / nether / end)")
                .define("Native_indiumOreDimList", new ArrayList<>(Arrays.asList("end")));
        NATIVE_INDIUM_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_indiumBlockList to determine what blocks Native_indium Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_indiumOreStoneSpecific",false);
        NATIVE_INDIUM_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_indium Ore in if Native_indiumOreStoneSpecific is enabled.")
                .define("Native_indiumBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_INDIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_indium at (make sure it is less than the maximum)")
                .defineInRange("Native_indiumOreMin", 0, 0, 256);
        NATIVE_INDIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_indium at (make sure it is greater than the minimum)")
                .defineInRange("Native_indiumOreMax", 100, 0, 256);
        NATIVE_INDIUM_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_indiumOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_indiumOreGentype",false);
        NATIVE_INDIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_indium vein")
                .defineInRange("Native_indiumOreSize", 9, 0, 256);
        NATIVE_INDIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_indium veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_indiumOreCount", 5, 0, 256);
        NATIVE_INDIUM_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_indium")
                .defineInRange("Native_indiumOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_tellurium Settings").push("Native_telluriumOre");
        NATIVE_TELLURIUM_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_tellurium Ore in (overworld / nether / end)")
                .define("Native_telluriumOreDimList", new ArrayList<>(Arrays.asList("end")));
        NATIVE_TELLURIUM_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_telluriumBlockList to determine what blocks Native_tellurium Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_telluriumOreStoneSpecific",false);
        NATIVE_TELLURIUM_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_tellurium Ore in if Native_telluriumOreStoneSpecific is enabled.")
                .define("Native_telluriumBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_TELLURIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_tellurium at (make sure it is less than the maximum)")
                .defineInRange("Native_telluriumOreMin", 0, 0, 256);
        NATIVE_TELLURIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_tellurium at (make sure it is greater than the minimum)")
                .defineInRange("Native_telluriumOreMax", 100, 0, 256);
        NATIVE_TELLURIUM_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_telluriumOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_telluriumOreGentype",false);
        NATIVE_TELLURIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_tellurium vein")
                .defineInRange("Native_telluriumOreSize", 9, 0, 256);
        NATIVE_TELLURIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_tellurium veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_telluriumOreCount", 5, 0, 256);
        NATIVE_TELLURIUM_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_tellurium")
                .defineInRange("Native_telluriumOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Native_selenium Settings").push("Native_seleniumOre");
        NATIVE_SELENIUM_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Native_selenium Ore in (overworld / nether / end)")
                .define("Native_seleniumOreDimList", new ArrayList<>(Arrays.asList("end")));
        NATIVE_SELENIUM_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Native_seleniumBlockList to determine what blocks Native_selenium Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Native_seleniumOreStoneSpecific",false);
        NATIVE_SELENIUM_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Native_selenium Ore in if Native_seleniumOreStoneSpecific is enabled.")
                .define("Native_seleniumBlockList", new ArrayList<>(Arrays.asList()));
        NATIVE_SELENIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native_selenium at (make sure it is less than the maximum)")
                .defineInRange("Native_seleniumOreMin", 0, 0, 256);
        NATIVE_SELENIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native_selenium at (make sure it is greater than the minimum)")
                .defineInRange("Native_seleniumOreMax", 100, 0, 256);
        NATIVE_SELENIUM_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Native_seleniumOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Native_seleniumOreGentype",false);
        NATIVE_SELENIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native_selenium vein")
                .defineInRange("Native_seleniumOreSize", 9, 0, 256);
        NATIVE_SELENIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native_selenium veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Native_seleniumOreCount", 5, 0, 256);
        NATIVE_SELENIUM_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Native_selenium")
                .defineInRange("Native_seleniumOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Malachite Settings").push("MalachiteOre");
        MALACHITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Malachite Ore in (overworld / nether / end)")
                .define("MalachiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        MALACHITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use MalachiteBlockList to determine what blocks Malachite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("MalachiteOreStoneSpecific",false);
        MALACHITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Malachite Ore in if MalachiteOreStoneSpecific is enabled.")
                .define("MalachiteBlockList", new ArrayList<>(Arrays.asList()));
        MALACHITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Malachite at (make sure it is less than the maximum)")
                .defineInRange("MalachiteOreMin", 40, 0, 256);
        MALACHITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Malachite at (make sure it is greater than the minimum)")
                .defineInRange("MalachiteOreMax", 60, 0, 256);
        MALACHITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, MalachiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("MalachiteOreGentype",true);
        MALACHITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Malachite vein")
                .defineInRange("MalachiteOreSize", 24, 0, 256);
        MALACHITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Malachite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("MalachiteOreCount", 2, 0, 256);
        MALACHITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Malachite")
                .defineInRange("MalachiteOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Cassiterite Settings").push("CassiteriteOre");
        CASSITERITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Cassiterite Ore in (overworld / nether / end)")
                .define("CassiteriteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        CASSITERITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use CassiteriteBlockList to determine what blocks Cassiterite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("CassiteriteOreStoneSpecific",false);
        CASSITERITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Cassiterite Ore in if CassiteriteOreStoneSpecific is enabled.")
                .define("CassiteriteBlockList", new ArrayList<>(Arrays.asList()));
        CASSITERITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cassiterite at (make sure it is less than the maximum)")
                .defineInRange("CassiteriteOreMin", 40, 0, 256);
        CASSITERITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cassiterite at (make sure it is greater than the minimum)")
                .defineInRange("CassiteriteOreMax", 60, 0, 256);
        CASSITERITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, CassiteriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("CassiteriteOreGentype",true);
        CASSITERITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Cassiterite vein")
                .defineInRange("CassiteriteOreSize", 24, 0, 256);
        CASSITERITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Cassiterite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("CassiteriteOreCount", 2, 0, 256);
        CASSITERITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Cassiterite")
                .defineInRange("CassiteriteOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Bauxite Settings").push("BauxiteOre");
        BAUXITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Bauxite Ore in (overworld / nether / end)")
                .define("BauxiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        BAUXITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use BauxiteBlockList to determine what blocks Bauxite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("BauxiteOreStoneSpecific",false);
        BAUXITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Bauxite Ore in if BauxiteOreStoneSpecific is enabled.")
                .define("BauxiteBlockList", new ArrayList<>(Arrays.asList()));
        BAUXITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Bauxite at (make sure it is less than the maximum)")
                .defineInRange("BauxiteOreMin", 40, 0, 256);
        BAUXITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Bauxite at (make sure it is greater than the minimum)")
                .defineInRange("BauxiteOreMax", 60, 0, 256);
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
        SPHALERITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Sphalerite Ore in (overworld / nether / end)")
                .define("SphaleriteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        SPHALERITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use SphaleriteBlockList to determine what blocks Sphalerite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("SphaleriteOreStoneSpecific",false);
        SPHALERITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Sphalerite Ore in if SphaleriteOreStoneSpecific is enabled.")
                .define("SphaleriteBlockList", new ArrayList<>(Arrays.asList()));
        SPHALERITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Sphalerite at (make sure it is less than the maximum)")
                .defineInRange("SphaleriteOreMin", 40, 0, 256);
        SPHALERITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Sphalerite at (make sure it is greater than the minimum)")
                .defineInRange("SphaleriteOreMax", 60, 0, 256);
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
        CINNABAR_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Cinnabar Ore in (overworld / nether / end)")
                .define("CinnabarOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        CINNABAR_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use CinnabarBlockList to determine what blocks Cinnabar Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("CinnabarOreStoneSpecific",false);
        CINNABAR_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Cinnabar Ore in if CinnabarOreStoneSpecific is enabled.")
                .define("CinnabarBlockList", new ArrayList<>(Arrays.asList()));
        CINNABAR_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cinnabar at (make sure it is less than the maximum)")
                .defineInRange("CinnabarOreMin", 20, 0, 256);
        CINNABAR_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cinnabar at (make sure it is greater than the minimum)")
                .defineInRange("CinnabarOreMax", 40, 0, 256);
        CINNABAR_ORE_GENTYPE = COMMON_BUILDER.comment("If true, CinnabarOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("CinnabarOreGentype",false);
        CINNABAR_ORE_SIZE = COMMON_BUILDER.comment("Size of Cinnabar vein")
                .defineInRange("CinnabarOreSize", 16, 0, 256);
        CINNABAR_ORE_COUNT = COMMON_BUILDER.comment("Number of Cinnabar veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("CinnabarOreCount", 2, 0, 256);
        CINNABAR_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Cinnabar")
                .defineInRange("CinnabarOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Magnetite Settings").push("MagnetiteOre");
        MAGNETITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Magnetite Ore in (overworld / nether / end)")
                .define("MagnetiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        MAGNETITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use MagnetiteBlockList to determine what blocks Magnetite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("MagnetiteOreStoneSpecific",false);
        MAGNETITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Magnetite Ore in if MagnetiteOreStoneSpecific is enabled.")
                .define("MagnetiteBlockList", new ArrayList<>(Arrays.asList()));
        MAGNETITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Magnetite at (make sure it is less than the maximum)")
                .defineInRange("MagnetiteOreMin", 20, 0, 256);
        MAGNETITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Magnetite at (make sure it is greater than the minimum)")
                .defineInRange("MagnetiteOreMax", 50, 0, 256);
        MAGNETITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, MagnetiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("MagnetiteOreGentype",false);
        MAGNETITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Magnetite vein")
                .defineInRange("MagnetiteOreSize", 24, 0, 256);
        MAGNETITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Magnetite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("MagnetiteOreCount", 2, 0, 256);
        MAGNETITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Magnetite")
                .defineInRange("MagnetiteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Pentlandite Settings").push("PentlanditeOre");
        PENTLANDITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Pentlandite Ore in (overworld / nether / end)")
                .define("PentlanditeOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        PENTLANDITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use PentlanditeBlockList to determine what blocks Pentlandite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("PentlanditeOreStoneSpecific",false);
        PENTLANDITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Pentlandite Ore in if PentlanditeOreStoneSpecific is enabled.")
                .define("PentlanditeBlockList", new ArrayList<>(Arrays.asList()));
        PENTLANDITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pentlandite at (make sure it is less than the maximum)")
                .defineInRange("PentlanditeOreMin", 20, 0, 256);
        PENTLANDITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pentlandite at (make sure it is greater than the minimum)")
                .defineInRange("PentlanditeOreMax", 40, 0, 256);
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
        MAGNESITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Magnesite Ore in (overworld / nether / end)")
                .define("MagnesiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        MAGNESITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use MagnesiteBlockList to determine what blocks Magnesite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("MagnesiteOreStoneSpecific",false);
        MAGNESITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Magnesite Ore in if MagnesiteOreStoneSpecific is enabled.")
                .define("MagnesiteBlockList", new ArrayList<>(Arrays.asList()));
        MAGNESITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Magnesite at (make sure it is less than the maximum)")
                .defineInRange("MagnesiteOreMin", 20, 0, 256);
        MAGNESITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Magnesite at (make sure it is greater than the minimum)")
                .defineInRange("MagnesiteOreMax", 40, 0, 256);
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
        GALENA_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Galena Ore in (overworld / nether / end)")
                .define("GalenaOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        GALENA_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use GalenaBlockList to determine what blocks Galena Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("GalenaOreStoneSpecific",false);
        GALENA_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Galena Ore in if GalenaOreStoneSpecific is enabled.")
                .define("GalenaBlockList", new ArrayList<>(Arrays.asList()));
        GALENA_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Galena at (make sure it is less than the maximum)")
                .defineInRange("GalenaOreMin", 20, 0, 256);
        GALENA_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Galena at (make sure it is greater than the minimum)")
                .defineInRange("GalenaOreMax", 40, 0, 256);
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
        VANADINITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Vanadinite Ore in (overworld / nether / end)")
                .define("VanadiniteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        VANADINITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use VanadiniteBlockList to determine what blocks Vanadinite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("VanadiniteOreStoneSpecific",false);
        VANADINITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Vanadinite Ore in if VanadiniteOreStoneSpecific is enabled.")
                .define("VanadiniteBlockList", new ArrayList<>(Arrays.asList()));
        VANADINITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Vanadinite at (make sure it is less than the maximum)")
                .defineInRange("VanadiniteOreMin", 20, 0, 256);
        VANADINITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Vanadinite at (make sure it is greater than the minimum)")
                .defineInRange("VanadiniteOreMax", 40, 0, 256);
        VANADINITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, VanadiniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("VanadiniteOreGentype",true);
        VANADINITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Vanadinite vein")
                .defineInRange("VanadiniteOreSize", 24, 0, 256);
        VANADINITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Vanadinite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("VanadiniteOreCount", 2, 0, 256);
        VANADINITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Vanadinite")
                .defineInRange("VanadiniteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Bismuthinite Settings").push("BismuthiniteOre");
        BISMUTHINITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Bismuthinite Ore in (overworld / nether / end)")
                .define("BismuthiniteOreDimList", new ArrayList<>(Arrays.asList("nether")));
        BISMUTHINITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use BismuthiniteBlockList to determine what blocks Bismuthinite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("BismuthiniteOreStoneSpecific",false);
        BISMUTHINITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Bismuthinite Ore in if BismuthiniteOreStoneSpecific is enabled.")
                .define("BismuthiniteBlockList", new ArrayList<>(Arrays.asList()));
        BISMUTHINITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Bismuthinite at (make sure it is less than the maximum)")
                .defineInRange("BismuthiniteOreMin", 45, 0, 256);
        BISMUTHINITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Bismuthinite at (make sure it is greater than the minimum)")
                .defineInRange("BismuthiniteOreMax", 90, 0, 256);
        BISMUTHINITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, BismuthiniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("BismuthiniteOreGentype",true);
        BISMUTHINITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Bismuthinite vein")
                .defineInRange("BismuthiniteOreSize", 24, 0, 256);
        BISMUTHINITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Bismuthinite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("BismuthiniteOreCount", 2, 0, 256);
        BISMUTHINITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Bismuthinite")
                .defineInRange("BismuthiniteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Acanthite Settings").push("AcanthiteOre");
        ACANTHITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Acanthite Ore in (overworld / nether / end)")
                .define("AcanthiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        ACANTHITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use AcanthiteBlockList to determine what blocks Acanthite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("AcanthiteOreStoneSpecific",false);
        ACANTHITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Acanthite Ore in if AcanthiteOreStoneSpecific is enabled.")
                .define("AcanthiteBlockList", new ArrayList<>(Arrays.asList()));
        ACANTHITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Acanthite at (make sure it is less than the maximum)")
                .defineInRange("AcanthiteOreMin", 20, 0, 256);
        ACANTHITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Acanthite at (make sure it is greater than the minimum)")
                .defineInRange("AcanthiteOreMax", 40, 0, 256);
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
        PYROLUSITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Pyrolusite Ore in (overworld / nether / end)")
                .define("PyrolusiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        PYROLUSITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use PyrolusiteBlockList to determine what blocks Pyrolusite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("PyrolusiteOreStoneSpecific",false);
        PYROLUSITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Pyrolusite Ore in if PyrolusiteOreStoneSpecific is enabled.")
                .define("PyrolusiteBlockList", new ArrayList<>(Arrays.asList()));
        PYROLUSITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pyrolusite at (make sure it is less than the maximum)")
                .defineInRange("PyrolusiteOreMin", 20, 0, 256);
        PYROLUSITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pyrolusite at (make sure it is greater than the minimum)")
                .defineInRange("PyrolusiteOreMax", 40, 0, 256);
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
        CHROMITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Chromite Ore in (overworld / nether / end)")
                .define("ChromiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        CHROMITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use ChromiteBlockList to determine what blocks Chromite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("ChromiteOreStoneSpecific",false);
        CHROMITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Chromite Ore in if ChromiteOreStoneSpecific is enabled.")
                .define("ChromiteBlockList", new ArrayList<>(Arrays.asList()));
        CHROMITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Chromite at (make sure it is less than the maximum)")
                .defineInRange("ChromiteOreMin", 0, 0, 256);
        CHROMITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Chromite at (make sure it is greater than the minimum)")
                .defineInRange("ChromiteOreMax", 20, 0, 256);
        CHROMITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, ChromiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("ChromiteOreGentype",true);
        CHROMITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Chromite vein")
                .defineInRange("ChromiteOreSize", 24, 0, 256);
        CHROMITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Chromite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("ChromiteOreCount", 3, 0, 256);
        CHROMITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Chromite")
                .defineInRange("ChromiteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Molybdenite Settings").push("MolybdeniteOre");
        MOLYBDENITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Molybdenite Ore in (overworld / nether / end)")
                .define("MolybdeniteOreDimList", new ArrayList<>(Arrays.asList("end")));
        MOLYBDENITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use MolybdeniteBlockList to determine what blocks Molybdenite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("MolybdeniteOreStoneSpecific",false);
        MOLYBDENITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Molybdenite Ore in if MolybdeniteOreStoneSpecific is enabled.")
                .define("MolybdeniteBlockList", new ArrayList<>(Arrays.asList()));
        MOLYBDENITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Molybdenite at (make sure it is less than the maximum)")
                .defineInRange("MolybdeniteOreMin", 0, 0, 256);
        MOLYBDENITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Molybdenite at (make sure it is greater than the minimum)")
                .defineInRange("MolybdeniteOreMax", 100, 0, 256);
        MOLYBDENITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, MolybdeniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("MolybdeniteOreGentype",false);
        MOLYBDENITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Molybdenite vein")
                .defineInRange("MolybdeniteOreSize", 9, 0, 256);
        MOLYBDENITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Molybdenite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("MolybdeniteOreCount", 5, 0, 256);
        MOLYBDENITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Molybdenite")
                .defineInRange("MolybdeniteOreHL", 4, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Ilmenite Settings").push("IlmeniteOre");
        ILMENITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Ilmenite Ore in (overworld / nether / end)")
                .define("IlmeniteOreDimList", new ArrayList<>(Arrays.asList("nether")));
        ILMENITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use IlmeniteBlockList to determine what blocks Ilmenite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("IlmeniteOreStoneSpecific",false);
        ILMENITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Ilmenite Ore in if IlmeniteOreStoneSpecific is enabled.")
                .define("IlmeniteBlockList", new ArrayList<>(Arrays.asList()));
        ILMENITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Ilmenite at (make sure it is less than the maximum)")
                .defineInRange("IlmeniteOreMin", 90, 0, 256);
        ILMENITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Ilmenite at (make sure it is greater than the minimum)")
                .defineInRange("IlmeniteOreMax", 127, 0, 256);
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
        COLUMBITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Columbite Ore in (overworld / nether / end)")
                .define("ColumbiteOreDimList", new ArrayList<>(Arrays.asList("nether")));
        COLUMBITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use ColumbiteBlockList to determine what blocks Columbite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("ColumbiteOreStoneSpecific",false);
        COLUMBITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Columbite Ore in if ColumbiteOreStoneSpecific is enabled.")
                .define("ColumbiteBlockList", new ArrayList<>(Arrays.asList()));
        COLUMBITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Columbite at (make sure it is less than the maximum)")
                .defineInRange("ColumbiteOreMin", 0, 0, 256);
        COLUMBITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Columbite at (make sure it is greater than the minimum)")
                .defineInRange("ColumbiteOreMax", 45, 0, 256);
        COLUMBITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, ColumbiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("ColumbiteOreGentype",true);
        COLUMBITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Columbite vein")
                .defineInRange("ColumbiteOreSize", 20, 0, 256);
        COLUMBITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Columbite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("ColumbiteOreCount", 2, 0, 256);
        COLUMBITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Columbite")
                .defineInRange("ColumbiteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Wolframite Settings").push("WolframiteOre");
        WOLFRAMITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Wolframite Ore in (overworld / nether / end)")
                .define("WolframiteOreDimList", new ArrayList<>(Arrays.asList("nether")));
        WOLFRAMITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use WolframiteBlockList to determine what blocks Wolframite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("WolframiteOreStoneSpecific",false);
        WOLFRAMITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Wolframite Ore in if WolframiteOreStoneSpecific is enabled.")
                .define("WolframiteBlockList", new ArrayList<>(Arrays.asList()));
        WOLFRAMITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Wolframite at (make sure it is less than the maximum)")
                .defineInRange("WolframiteOreMin", 90, 0, 256);
        WOLFRAMITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Wolframite at (make sure it is greater than the minimum)")
                .defineInRange("WolframiteOreMax", 127, 0, 256);
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
        TANTALITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Tantalite Ore in (overworld / nether / end)")
                .define("TantaliteOreDimList", new ArrayList<>(Arrays.asList("nether")));
        TANTALITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use TantaliteBlockList to determine what blocks Tantalite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("TantaliteOreStoneSpecific",false);
        TANTALITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Tantalite Ore in if TantaliteOreStoneSpecific is enabled.")
                .define("TantaliteBlockList", new ArrayList<>(Arrays.asList()));
        TANTALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Tantalite at (make sure it is less than the maximum)")
                .defineInRange("TantaliteOreMin", 0, 0, 256);
        TANTALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Tantalite at (make sure it is greater than the minimum)")
                .defineInRange("TantaliteOreMax", 45, 0, 256);
        TANTALITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, TantaliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("TantaliteOreGentype",true);
        TANTALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Tantalite vein")
                .defineInRange("TantaliteOreSize", 20, 0, 256);
        TANTALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Tantalite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("TantaliteOreCount", 2, 0, 256);
        TANTALITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Tantalite")
                .defineInRange("TantaliteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Plumbago Settings").push("PlumbagoOre");
        PLUMBAGO_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Plumbago Ore in (overworld / nether / end)")
                .define("PlumbagoOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        PLUMBAGO_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use PlumbagoBlockList to determine what blocks Plumbago Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("PlumbagoOreStoneSpecific",false);
        PLUMBAGO_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Plumbago Ore in if PlumbagoOreStoneSpecific is enabled.")
                .define("PlumbagoBlockList", new ArrayList<>(Arrays.asList()));
        PLUMBAGO_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Plumbago at (make sure it is less than the maximum)")
                .defineInRange("PlumbagoOreMin", 0, 0, 256);
        PLUMBAGO_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Plumbago at (make sure it is greater than the minimum)")
                .defineInRange("PlumbagoOreMax", 20, 0, 256);
        PLUMBAGO_ORE_GENTYPE = COMMON_BUILDER.comment("If true, PlumbagoOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("PlumbagoOreGentype",true);
        PLUMBAGO_ORE_SIZE = COMMON_BUILDER.comment("Size of Plumbago vein")
                .defineInRange("PlumbagoOreSize", 24, 0, 256);
        PLUMBAGO_ORE_COUNT = COMMON_BUILDER.comment("Number of Plumbago veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("PlumbagoOreCount", 3, 0, 256);
        PLUMBAGO_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Plumbago")
                .defineInRange("PlumbagoOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Moissanite Settings").push("MoissaniteOre");
        MOISSANITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Moissanite Ore in (overworld / nether / end)")
                .define("MoissaniteOreDimList", new ArrayList<>(Arrays.asList("nether")));
        MOISSANITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use MoissaniteBlockList to determine what blocks Moissanite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("MoissaniteOreStoneSpecific",false);
        MOISSANITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Moissanite Ore in if MoissaniteOreStoneSpecific is enabled.")
                .define("MoissaniteBlockList", new ArrayList<>(Arrays.asList()));
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
                .defineInRange("MoissaniteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Sperrylite Settings").push("SperryliteOre");
        SPERRYLITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Sperrylite Ore in (overworld / nether / end)")
                .define("SperryliteOreDimList", new ArrayList<>(Arrays.asList("nether")));
        SPERRYLITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use SperryliteBlockList to determine what blocks Sperrylite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("SperryliteOreStoneSpecific",false);
        SPERRYLITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Sperrylite Ore in if SperryliteOreStoneSpecific is enabled.")
                .define("SperryliteBlockList", new ArrayList<>(Arrays.asList()));
        SPERRYLITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Sperrylite at (make sure it is less than the maximum)")
                .defineInRange("SperryliteOreMin", 0, 0, 256);
        SPERRYLITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Sperrylite at (make sure it is greater than the minimum)")
                .defineInRange("SperryliteOreMax", 45, 0, 256);
        SPERRYLITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, SperryliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("SperryliteOreGentype",true);
        SPERRYLITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Sperrylite vein")
                .defineInRange("SperryliteOreSize", 20, 0, 256);
        SPERRYLITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Sperrylite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("SperryliteOreCount", 2, 0, 256);
        SPERRYLITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Sperrylite")
                .defineInRange("SperryliteOreHL", 4, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Lignite Settings").push("LigniteOre");
        LIGNITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Lignite Ore in (overworld / nether / end)")
                .define("LigniteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        LIGNITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use LigniteBlockList to determine what blocks Lignite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("LigniteOreStoneSpecific",false);
        LIGNITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Lignite Ore in if LigniteOreStoneSpecific is enabled.")
                .define("LigniteBlockList", new ArrayList<>(Arrays.asList()));
        LIGNITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Lignite at (make sure it is less than the maximum)")
                .defineInRange("LigniteOreMin", 60, 0, 256);
        LIGNITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Lignite at (make sure it is greater than the minimum)")
                .defineInRange("LigniteOreMax", 90, 0, 256);
        LIGNITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, LigniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("LigniteOreGentype",false);
        LIGNITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Lignite vein")
                .defineInRange("LigniteOreSize", 20, 0, 256);
        LIGNITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Lignite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("LigniteOreCount", 1, 0, 256);
        LIGNITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Lignite")
                .defineInRange("LigniteOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Subbituminous Settings").push("SubbituminousOre");
        SUBBITUMINOUS_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Subbituminous Ore in (overworld / nether / end)")
                .define("SubbituminousOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        SUBBITUMINOUS_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use SubbituminousBlockList to determine what blocks Subbituminous Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("SubbituminousOreStoneSpecific",false);
        SUBBITUMINOUS_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Subbituminous Ore in if SubbituminousOreStoneSpecific is enabled.")
                .define("SubbituminousBlockList", new ArrayList<>(Arrays.asList()));
        SUBBITUMINOUS_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Subbituminous at (make sure it is less than the maximum)")
                .defineInRange("SubbituminousOreMin", 40, 0, 256);
        SUBBITUMINOUS_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Subbituminous at (make sure it is greater than the minimum)")
                .defineInRange("SubbituminousOreMax", 60, 0, 256);
        SUBBITUMINOUS_ORE_GENTYPE = COMMON_BUILDER.comment("If true, SubbituminousOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("SubbituminousOreGentype",false);
        SUBBITUMINOUS_ORE_SIZE = COMMON_BUILDER.comment("Size of Subbituminous vein")
                .defineInRange("SubbituminousOreSize", 20, 0, 256);
        SUBBITUMINOUS_ORE_COUNT = COMMON_BUILDER.comment("Number of Subbituminous veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("SubbituminousOreCount", 1, 0, 256);
        SUBBITUMINOUS_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Subbituminous")
                .defineInRange("SubbituminousOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Bituminous Settings").push("BituminousOre");
        BITUMINOUS_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Bituminous Ore in (overworld / nether / end)")
                .define("BituminousOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        BITUMINOUS_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use BituminousBlockList to determine what blocks Bituminous Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("BituminousOreStoneSpecific",false);
        BITUMINOUS_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Bituminous Ore in if BituminousOreStoneSpecific is enabled.")
                .define("BituminousBlockList", new ArrayList<>(Arrays.asList()));
        BITUMINOUS_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Bituminous at (make sure it is less than the maximum)")
                .defineInRange("BituminousOreMin", 0, 0, 256);
        BITUMINOUS_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Bituminous at (make sure it is greater than the minimum)")
                .defineInRange("BituminousOreMax", 30, 0, 256);
        BITUMINOUS_ORE_GENTYPE = COMMON_BUILDER.comment("If true, BituminousOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("BituminousOreGentype",false);
        BITUMINOUS_ORE_SIZE = COMMON_BUILDER.comment("Size of Bituminous vein")
                .defineInRange("BituminousOreSize", 24, 0, 256);
        BITUMINOUS_ORE_COUNT = COMMON_BUILDER.comment("Number of Bituminous veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("BituminousOreCount", 2, 0, 256);
        BITUMINOUS_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Bituminous")
                .defineInRange("BituminousOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Anthracite Settings").push("AnthraciteOre");
        ANTHRACITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Anthracite Ore in (overworld / nether / end)")
                .define("AnthraciteOreDimList", new ArrayList<>(Arrays.asList("nether")));
        ANTHRACITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use AnthraciteBlockList to determine what blocks Anthracite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("AnthraciteOreStoneSpecific",false);
        ANTHRACITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Anthracite Ore in if AnthraciteOreStoneSpecific is enabled.")
                .define("AnthraciteBlockList", new ArrayList<>(Arrays.asList()));
        ANTHRACITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Anthracite at (make sure it is less than the maximum)")
                .defineInRange("AnthraciteOreMin", 45, 0, 256);
        ANTHRACITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Anthracite at (make sure it is greater than the minimum)")
                .defineInRange("AnthraciteOreMax", 90, 0, 256);
        ANTHRACITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, AnthraciteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("AnthraciteOreGentype",false);
        ANTHRACITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Anthracite vein")
                .defineInRange("AnthraciteOreSize", 24, 0, 256);
        ANTHRACITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Anthracite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("AnthraciteOreCount", 2, 0, 256);
        ANTHRACITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Anthracite")
                .defineInRange("AnthraciteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Lazurite Settings").push("LazuriteOre");
        LAZURITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Lazurite Ore in (overworld / nether / end)")
                .define("LazuriteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        LAZURITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use LazuriteBlockList to determine what blocks Lazurite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("LazuriteOreStoneSpecific",false);
        LAZURITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Lazurite Ore in if LazuriteOreStoneSpecific is enabled.")
                .define("LazuriteBlockList", new ArrayList<>(Arrays.asList()));
        LAZURITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Lazurite at (make sure it is less than the maximum)")
                .defineInRange("LazuriteOreMin", 0, 0, 256);
        LAZURITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Lazurite at (make sure it is greater than the minimum)")
                .defineInRange("LazuriteOreMax", 40, 0, 256);
        LAZURITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, LazuriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("LazuriteOreGentype",false);
        LAZURITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Lazurite vein")
                .defineInRange("LazuriteOreSize", 20, 0, 256);
        LAZURITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Lazurite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("LazuriteOreCount", 1, 0, 256);
        LAZURITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Lazurite")
                .defineInRange("LazuriteOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Diamond Settings").push("DiamondOre");
        DIAMOND_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Diamond Ore in (overworld / nether / end)")
                .define("DiamondOreDimList", new ArrayList<>(Arrays.asList()));
        DIAMOND_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use DiamondBlockList to determine what blocks Diamond Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("DiamondOreStoneSpecific",false);
        DIAMOND_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Diamond Ore in if DiamondOreStoneSpecific is enabled.")
                .define("DiamondBlockList", new ArrayList<>(Arrays.asList()));
        DIAMOND_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Diamond at (make sure it is less than the maximum)")
                .defineInRange("DiamondOreMin", 0, 0, 256);
        DIAMOND_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Diamond at (make sure it is greater than the minimum)")
                .defineInRange("DiamondOreMax", 40, 0, 256);
        DIAMOND_ORE_GENTYPE = COMMON_BUILDER.comment("If true, DiamondOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("DiamondOreGentype",false);
        DIAMOND_ORE_SIZE = COMMON_BUILDER.comment("Size of Diamond vein")
                .defineInRange("DiamondOreSize", 24, 0, 256);
        DIAMOND_ORE_COUNT = COMMON_BUILDER.comment("Number of Diamond veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("DiamondOreCount", 2, 0, 256);
        DIAMOND_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Diamond")
                .defineInRange("DiamondOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Greenockite Settings").push("GreenockiteOre");
        GREENOCKITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Greenockite Ore in (overworld / nether / end)")
                .define("GreenockiteOreDimList", new ArrayList<>(Arrays.asList("nether")));
        GREENOCKITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use GreenockiteBlockList to determine what blocks Greenockite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("GreenockiteOreStoneSpecific",false);
        GREENOCKITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Greenockite Ore in if GreenockiteOreStoneSpecific is enabled.")
                .define("GreenockiteBlockList", new ArrayList<>(Arrays.asList()));
        GREENOCKITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Greenockite at (make sure it is less than the maximum)")
                .defineInRange("GreenockiteOreMin", 90, 0, 256);
        GREENOCKITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Greenockite at (make sure it is greater than the minimum)")
                .defineInRange("GreenockiteOreMax", 127, 0, 256);
        GREENOCKITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, GreenockiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("GreenockiteOreGentype",true);
        GREENOCKITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Greenockite vein")
                .defineInRange("GreenockiteOreSize", 24, 0, 256);
        GREENOCKITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Greenockite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("GreenockiteOreCount", 3, 0, 256);
        GREENOCKITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Greenockite")
                .defineInRange("GreenockiteOreHL", 4, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Emerald Settings").push("EmeraldOre");
        EMERALD_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Emerald Ore in (overworld / nether / end)")
                .define("EmeraldOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        EMERALD_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use EmeraldBlockList to determine what blocks Emerald Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("EmeraldOreStoneSpecific",false);
        EMERALD_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Emerald Ore in if EmeraldOreStoneSpecific is enabled.")
                .define("EmeraldBlockList", new ArrayList<>(Arrays.asList()));
        EMERALD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Emerald at (make sure it is less than the maximum)")
                .defineInRange("EmeraldOreMin", 0, 0, 256);
        EMERALD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Emerald at (make sure it is greater than the minimum)")
                .defineInRange("EmeraldOreMax", 60, 0, 256);
        EMERALD_ORE_GENTYPE = COMMON_BUILDER.comment("If true, EmeraldOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("EmeraldOreGentype",false);
        EMERALD_ORE_SIZE = COMMON_BUILDER.comment("Size of Emerald vein")
                .defineInRange("EmeraldOreSize", 6, 0, 256);
        EMERALD_ORE_COUNT = COMMON_BUILDER.comment("Number of Emerald veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("EmeraldOreCount", 2, 0, 256);
        EMERALD_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Emerald")
                .defineInRange("EmeraldOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Aquamarine Settings").push("AquamarineOre");
        AQUAMARINE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Aquamarine Ore in (overworld / nether / end)")
                .define("AquamarineOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        AQUAMARINE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use AquamarineBlockList to determine what blocks Aquamarine Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("AquamarineOreStoneSpecific",false);
        AQUAMARINE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Aquamarine Ore in if AquamarineOreStoneSpecific is enabled.")
                .define("AquamarineBlockList", new ArrayList<>(Arrays.asList()));
        AQUAMARINE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Aquamarine at (make sure it is less than the maximum)")
                .defineInRange("AquamarineOreMin", 0, 0, 256);
        AQUAMARINE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Aquamarine at (make sure it is greater than the minimum)")
                .defineInRange("AquamarineOreMax", 60, 0, 256);
        AQUAMARINE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, AquamarineOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("AquamarineOreGentype",false);
        AQUAMARINE_ORE_SIZE = COMMON_BUILDER.comment("Size of Aquamarine vein")
                .defineInRange("AquamarineOreSize", 6, 0, 256);
        AQUAMARINE_ORE_COUNT = COMMON_BUILDER.comment("Number of Aquamarine veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("AquamarineOreCount", 2, 0, 256);
        AQUAMARINE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Aquamarine")
                .defineInRange("AquamarineOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Quartz Settings").push("QuartzOre");
        QUARTZ_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Quartz Ore in (overworld / nether / end)")
                .define("QuartzOreDimList", new ArrayList<>(Arrays.asList("nether")));
        QUARTZ_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use QuartzBlockList to determine what blocks Quartz Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("QuartzOreStoneSpecific",false);
        QUARTZ_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Quartz Ore in if QuartzOreStoneSpecific is enabled.")
                .define("QuartzBlockList", new ArrayList<>(Arrays.asList()));
        QUARTZ_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Quartz at (make sure it is less than the maximum)")
                .defineInRange("QuartzOreMin", 0, 0, 256);
        QUARTZ_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Quartz at (make sure it is greater than the minimum)")
                .defineInRange("QuartzOreMax", 127, 0, 256);
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
        OPAL_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Opal Ore in (overworld / nether / end)")
                .define("OpalOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        OPAL_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use OpalBlockList to determine what blocks Opal Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("OpalOreStoneSpecific",false);
        OPAL_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Opal Ore in if OpalOreStoneSpecific is enabled.")
                .define("OpalBlockList", new ArrayList<>(Arrays.asList("rankine:ironstone")));
        OPAL_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Opal at (make sure it is less than the maximum)")
                .defineInRange("OpalOreMin", 0, 0, 256);
        OPAL_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Opal at (make sure it is greater than the minimum)")
                .defineInRange("OpalOreMax", 60, 0, 256);
        OPAL_ORE_GENTYPE = COMMON_BUILDER.comment("If true, OpalOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("OpalOreGentype",false);
        OPAL_ORE_SIZE = COMMON_BUILDER.comment("Size of Opal vein")
                .defineInRange("OpalOreSize", 6, 0, 256);
        OPAL_ORE_COUNT = COMMON_BUILDER.comment("Number of Opal veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("OpalOreCount", 2, 0, 256);
        OPAL_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Opal")
                .defineInRange("OpalOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Majorite Settings").push("MajoriteOre");
        MAJORITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Majorite Ore in (overworld / nether / end)")
                .define("MajoriteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        MAJORITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use MajoriteBlockList to determine what blocks Majorite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("MajoriteOreStoneSpecific",false);
        MAJORITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Majorite Ore in if MajoriteOreStoneSpecific is enabled.")
                .define("MajoriteBlockList", new ArrayList<>(Arrays.asList()));
        MAJORITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Majorite at (make sure it is less than the maximum)")
                .defineInRange("MajoriteOreMin", 0, 0, 256);
        MAJORITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Majorite at (make sure it is greater than the minimum)")
                .defineInRange("MajoriteOreMax", 60, 0, 256);
        MAJORITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, MajoriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("MajoriteOreGentype",false);
        MAJORITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Majorite vein")
                .defineInRange("MajoriteOreSize", 6, 0, 256);
        MAJORITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Majorite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("MajoriteOreCount", 2, 0, 256);
        MAJORITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Majorite")
                .defineInRange("MajoriteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Fluorite Settings").push("FluoriteOre");
        FLUORITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Fluorite Ore in (overworld / nether / end)")
                .define("FluoriteOreDimList", new ArrayList<>(Arrays.asList("end")));
        FLUORITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use FluoriteBlockList to determine what blocks Fluorite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("FluoriteOreStoneSpecific",false);
        FLUORITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Fluorite Ore in if FluoriteOreStoneSpecific is enabled.")
                .define("FluoriteBlockList", new ArrayList<>(Arrays.asList()));
        FLUORITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Fluorite at (make sure it is less than the maximum)")
                .defineInRange("FluoriteOreMin", 0, 0, 256);
        FLUORITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Fluorite at (make sure it is greater than the minimum)")
                .defineInRange("FluoriteOreMax", 100, 0, 256);
        FLUORITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, FluoriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("FluoriteOreGentype",false);
        FLUORITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Fluorite vein")
                .defineInRange("FluoriteOreSize", 15, 0, 256);
        FLUORITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Fluorite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("FluoriteOreCount", 4, 0, 256);
        FLUORITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Fluorite")
                .defineInRange("FluoriteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Uraninite Settings").push("UraniniteOre");
        URANINITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Uraninite Ore in (overworld / nether / end)")
                .define("UraniniteOreDimList", new ArrayList<>(Arrays.asList("end")));
        URANINITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use UraniniteBlockList to determine what blocks Uraninite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("UraniniteOreStoneSpecific",false);
        URANINITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Uraninite Ore in if UraniniteOreStoneSpecific is enabled.")
                .define("UraniniteBlockList", new ArrayList<>(Arrays.asList()));
        URANINITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Uraninite at (make sure it is less than the maximum)")
                .defineInRange("UraniniteOreMin", 0, 0, 256);
        URANINITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Uraninite at (make sure it is greater than the minimum)")
                .defineInRange("UraniniteOreMax", 100, 0, 256);
        URANINITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, UraniniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("UraniniteOreGentype",false);
        URANINITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Uraninite vein")
                .defineInRange("UraniniteOreSize", 12, 0, 256);
        URANINITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Uraninite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("UraniniteOreCount", 4, 0, 256);
        URANINITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Uraninite")
                .defineInRange("UraniniteOreHL", 4, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Stibnite Settings").push("StibniteOre");
        STIBNITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Stibnite Ore in (overworld / nether / end)")
                .define("StibniteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        STIBNITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use StibniteBlockList to determine what blocks Stibnite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("StibniteOreStoneSpecific",false);
        STIBNITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Stibnite Ore in if StibniteOreStoneSpecific is enabled.")
                .define("StibniteBlockList", new ArrayList<>(Arrays.asList()));
        STIBNITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Stibnite at (make sure it is less than the maximum)")
                .defineInRange("StibniteOreMin", 60, 0, 256);
        STIBNITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Stibnite at (make sure it is greater than the minimum)")
                .defineInRange("StibniteOreMax", 80, 0, 256);
        STIBNITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, StibniteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("StibniteOreGentype",false);
        STIBNITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Stibnite vein")
                .defineInRange("StibniteOreSize", 5, 0, 256);
        STIBNITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Stibnite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("StibniteOreCount", 5, 0, 256);
        STIBNITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Stibnite")
                .defineInRange("StibniteOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Xenotime Settings").push("XenotimeOre");
        XENOTIME_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Xenotime Ore in (overworld / nether / end)")
                .define("XenotimeOreDimList", new ArrayList<>(Arrays.asList("end")));
        XENOTIME_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use XenotimeBlockList to determine what blocks Xenotime Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("XenotimeOreStoneSpecific",false);
        XENOTIME_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Xenotime Ore in if XenotimeOreStoneSpecific is enabled.")
                .define("XenotimeBlockList", new ArrayList<>(Arrays.asList()));
        XENOTIME_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Xenotime at (make sure it is less than the maximum)")
                .defineInRange("XenotimeOreMin", 0, 0, 256);
        XENOTIME_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Xenotime at (make sure it is greater than the minimum)")
                .defineInRange("XenotimeOreMax", 100, 0, 256);
        XENOTIME_ORE_GENTYPE = COMMON_BUILDER.comment("If true, XenotimeOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("XenotimeOreGentype",false);
        XENOTIME_ORE_SIZE = COMMON_BUILDER.comment("Size of Xenotime vein")
                .defineInRange("XenotimeOreSize", 9, 0, 256);
        XENOTIME_ORE_COUNT = COMMON_BUILDER.comment("Number of Xenotime veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("XenotimeOreCount", 5, 0, 256);
        XENOTIME_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Xenotime")
                .defineInRange("XenotimeOreHL", 4, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Halite Settings").push("HaliteOre");
        HALITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Halite Ore in (overworld / nether / end)")
                .define("HaliteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        HALITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use HaliteBlockList to determine what blocks Halite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("HaliteOreStoneSpecific",false);
        HALITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Halite Ore in if HaliteOreStoneSpecific is enabled.")
                .define("HaliteBlockList", new ArrayList<>(Arrays.asList()));
        HALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Halite at (make sure it is less than the maximum)")
                .defineInRange("HaliteOreMin", 50, 0, 256);
        HALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Halite at (make sure it is greater than the minimum)")
                .defineInRange("HaliteOreMax", 100, 0, 256);
        HALITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, HaliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("HaliteOreGentype",true);
        HALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Halite vein")
                .defineInRange("HaliteOreSize", 24, 0, 256);
        HALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Halite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("HaliteOreCount", 2, 0, 256);
        HALITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Halite")
                .defineInRange("HaliteOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Pink_halite Settings").push("Pink_haliteOre");
        PINK_HALITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Pink_halite Ore in (overworld / nether / end)")
                .define("Pink_haliteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        PINK_HALITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use Pink_haliteBlockList to determine what blocks Pink_halite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("Pink_haliteOreStoneSpecific",false);
        PINK_HALITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Pink_halite Ore in if Pink_haliteOreStoneSpecific is enabled.")
                .define("Pink_haliteBlockList", new ArrayList<>(Arrays.asList()));
        PINK_HALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pink_halite at (make sure it is less than the maximum)")
                .defineInRange("Pink_haliteOreMin", 50, 0, 256);
        PINK_HALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pink_halite at (make sure it is greater than the minimum)")
                .defineInRange("Pink_haliteOreMax", 100, 0, 256);
        PINK_HALITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, Pink_haliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("Pink_haliteOreGentype",true);
        PINK_HALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Pink_halite vein")
                .defineInRange("Pink_haliteOreSize", 24, 0, 256);
        PINK_HALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Pink_halite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("Pink_haliteOreCount", 2, 0, 256);
        PINK_HALITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Pink_halite")
                .defineInRange("Pink_haliteOreHL", 0, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Interspinifex Settings").push("InterspinifexOre");
        INTERSPINIFEX_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Interspinifex Ore in (overworld / nether / end)")
                .define("InterspinifexOreDimList", new ArrayList<>(Arrays.asList()));
        INTERSPINIFEX_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use InterspinifexBlockList to determine what blocks Interspinifex Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("InterspinifexOreStoneSpecific",false);
        INTERSPINIFEX_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Interspinifex Ore in if InterspinifexOreStoneSpecific is enabled.")
                .define("InterspinifexBlockList", new ArrayList<>(Arrays.asList()));
        INTERSPINIFEX_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Interspinifex at (make sure it is less than the maximum)")
                .defineInRange("InterspinifexOreMin", 20, 0, 256);
        INTERSPINIFEX_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Interspinifex at (make sure it is greater than the minimum)")
                .defineInRange("InterspinifexOreMax", 40, 0, 256);
        INTERSPINIFEX_ORE_GENTYPE = COMMON_BUILDER.comment("If true, InterspinifexOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("InterspinifexOreGentype",false);
        INTERSPINIFEX_ORE_SIZE = COMMON_BUILDER.comment("Size of Interspinifex vein")
                .defineInRange("InterspinifexOreSize", 24, 0, 256);
        INTERSPINIFEX_ORE_COUNT = COMMON_BUILDER.comment("Number of Interspinifex veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("InterspinifexOreCount", 2, 0, 256);
        INTERSPINIFEX_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Interspinifex")
                .defineInRange("InterspinifexOreHL", 1, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Petalite Settings").push("PetaliteOre");
        PETALITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Petalite Ore in (overworld / nether / end)")
                .define("PetaliteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        PETALITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use PetaliteBlockList to determine what blocks Petalite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("PetaliteOreStoneSpecific",false);
        PETALITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Petalite Ore in if PetaliteOreStoneSpecific is enabled.")
                .define("PetaliteBlockList", new ArrayList<>(Arrays.asList()));
        PETALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Petalite at (make sure it is less than the maximum)")
                .defineInRange("PetaliteOreMin", 0, 0, 256);
        PETALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Petalite at (make sure it is greater than the minimum)")
                .defineInRange("PetaliteOreMax", 20, 0, 256);
        PETALITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, PetaliteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("PetaliteOreGentype",true);
        PETALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Petalite vein")
                .defineInRange("PetaliteOreSize", 20, 0, 256);
        PETALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Petalite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("PetaliteOreCount", 4, 0, 256);
        PETALITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Petalite")
                .defineInRange("PetaliteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Cobaltite Settings").push("CobaltiteOre");
        COBALTITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Cobaltite Ore in (overworld / nether / end)")
                .define("CobaltiteOreDimList", new ArrayList<>(Arrays.asList("nether")));
        COBALTITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use CobaltiteBlockList to determine what blocks Cobaltite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("CobaltiteOreStoneSpecific",false);
        COBALTITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Cobaltite Ore in if CobaltiteOreStoneSpecific is enabled.")
                .define("CobaltiteBlockList", new ArrayList<>(Arrays.asList()));
        COBALTITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cobaltite at (make sure it is less than the maximum)")
                .defineInRange("CobaltiteOreMin", 45, 0, 256);
        COBALTITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cobaltite at (make sure it is greater than the minimum)")
                .defineInRange("CobaltiteOreMax", 90, 0, 256);
        COBALTITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, CobaltiteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("CobaltiteOreGentype",true);
        COBALTITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Cobaltite vein")
                .defineInRange("CobaltiteOreSize", 24, 0, 256);
        COBALTITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Cobaltite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("CobaltiteOreCount", 2, 0, 256);
        COBALTITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Cobaltite")
                .defineInRange("CobaltiteOreHL", 3, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Cryolite Settings").push("CryoliteOre");
        CRYOLITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Cryolite Ore in (overworld / nether / end)")
                .define("CryoliteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        CRYOLITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use CryoliteBlockList to determine what blocks Cryolite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("CryoliteOreStoneSpecific",false);
        CRYOLITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Cryolite Ore in if CryoliteOreStoneSpecific is enabled.")
                .define("CryoliteBlockList", new ArrayList<>(Arrays.asList()));
        CRYOLITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cryolite at (make sure it is less than the maximum)")
                .defineInRange("CryoliteOreMin", 0, 0, 256);
        CRYOLITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cryolite at (make sure it is greater than the minimum)")
                .defineInRange("CryoliteOreMax", 20, 0, 256);
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
        PYRITE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Pyrite Ore in (overworld / nether / end)")
                .define("PyriteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        PYRITE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use PyriteBlockList to determine what blocks Pyrite Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("PyriteOreStoneSpecific",false);
        PYRITE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Pyrite Ore in if PyriteOreStoneSpecific is enabled.")
                .define("PyriteBlockList", new ArrayList<>(Arrays.asList()));
        PYRITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pyrite at (make sure it is less than the maximum)")
                .defineInRange("PyriteOreMin", 25, 0, 256);
        PYRITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pyrite at (make sure it is greater than the minimum)")
                .defineInRange("PyriteOreMax", 85, 0, 256);
        PYRITE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, PyriteOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("PyriteOreGentype",false);
        PYRITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Pyrite vein")
                .defineInRange("PyriteOreSize", 9, 0, 256);
        PYRITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Pyrite veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("PyriteOreCount", 3, 0, 256);
        PYRITE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Pyrite")
                .defineInRange("PyriteOreHL", 2, 0, 10);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Celestine Settings").push("CelestineOre");
        CELESTINE_ORE_DIMENSION_LIST = COMMON_BUILDER.comment("Dimension to generate Celestine Ore in (overworld / nether / end)")
                .define("CelestineOreDimList", new ArrayList<>(Arrays.asList("overworld")));
        CELESTINE_ORE_STONE_SPECIFIC = COMMON_BUILDER.comment("Use CelestineBlockList to determine what blocks Celestine Ore will spawn in. If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether.")
                .define("CelestineOreStoneSpecific",false);
        CELESTINE_BLOCK_LIST = COMMON_BUILDER.comment("Blocks to generate Celestine Ore in if CelestineOreStoneSpecific is enabled.")
                .define("CelestineBlockList", new ArrayList<>(Arrays.asList()));
        CELESTINE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Celestine at (make sure it is less than the maximum)")
                .defineInRange("CelestineOreMin", 20, 0, 256);
        CELESTINE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Celestine at (make sure it is greater than the minimum)")
                .defineInRange("CelestineOreMax", 50, 0, 256);
        CELESTINE_ORE_GENTYPE = COMMON_BUILDER.comment("If true, CelestineOreCount will switch to generate a vein 1 in X chunks instead of number of veins per chunk.")
                .define("CelestineOreGentype",true);
        CELESTINE_ORE_SIZE = COMMON_BUILDER.comment("Size of Celestine vein")
                .defineInRange("CelestineOreSize", 16, 0, 256);
        CELESTINE_ORE_COUNT = COMMON_BUILDER.comment("Number of Celestine veins to generate X veins per chunk or 1 vein in X chunks.")
                .defineInRange("CelestineOreCount", 3, 0, 256);
        CELESTINE_ORE_HL = COMMON_BUILDER.comment("Harvest Level of Celestine")
                .defineInRange("CelestineOreHL", 2, 0, 10);
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
