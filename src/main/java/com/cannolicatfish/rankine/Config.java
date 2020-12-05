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

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_MECHANICS = "mechanics";
    public static final String SUBCATEGORY_EVENTS = "event";
    public static final String SUBCATEGORY_ALLOYS = "alloys";
    public static final String SUBCATEGORY_WORLDGEN = "worldgen";
    public static final String SUBCATEGORY_OVERWORLD = "overworldOres";
    public static final String SUBCATEGORY_NETHER = "netherOres";
    public static final String SUBCATEGORY_END = "endOres";


    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;


    public static ForgeConfigSpec.BooleanValue MANDATORY_AXE;
    public static ForgeConfigSpec.BooleanValue STARTING_BOOK;
    public static ForgeConfigSpec.BooleanValue DISABLE_WOOD;
    public static ForgeConfigSpec.BooleanValue DISABLE_STONE;
    public static ForgeConfigSpec.BooleanValue DISABLE_IRON;
    public static ForgeConfigSpec.BooleanValue DISABLE_GOLD;
    public static ForgeConfigSpec.BooleanValue DISABLE_DIAMOND;
    public static ForgeConfigSpec.BooleanValue DISABLE_NETHERITE;

    public static ForgeConfigSpec.IntValue PISTON_STEAM_ENGINE_MAXPOWER;
    public static ForgeConfigSpec.IntValue PISTON_STEAM_ENGINE_GENERATE;
    public static ForgeConfigSpec.IntValue PISTON_STEAM_ENGINE_SEND;
    public static ForgeConfigSpec.IntValue PISTON_STEAM_ENGINE_TICKS;

    public static ForgeConfigSpec.IntValue STEAM_TURBINE_MAXPOWER;
    public static ForgeConfigSpec.IntValue STEAM_TURBINE_GENERATE;
    public static ForgeConfigSpec.IntValue STEAM_TURBINE_SEND;
    public static ForgeConfigSpec.IntValue STEAM_TURBINE_TICKS;

    public static ForgeConfigSpec.BooleanValue ALLOY_CORROSION;
    public static ForgeConfigSpec.BooleanValue ALLOY_HEAT;
    public static ForgeConfigSpec.BooleanValue ALLOY_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue ALLOY_WEAR_MINING_AMT;
    public static ForgeConfigSpec.DoubleValue ALLOY_WEAR_DAMAGE_AMT;
    public static ForgeConfigSpec.BooleanValue AMALGAM_EXTRAS;

    public static ForgeConfigSpec.DoubleValue T1_BEEHIVE_OVEN_CHANCE;
    public static ForgeConfigSpec.DoubleValue T2_BEEHIVE_OVEN_CHANCE;
    public static ForgeConfigSpec.DoubleValue T3_BEEHIVE_OVEN_CHANCE;

    public static ForgeConfigSpec.DoubleValue DIAMON_CHANCE;
    public static ForgeConfigSpec.DoubleValue ILMENITE_CHANCE;
    public static ForgeConfigSpec.DoubleValue INTERSPINIFEX_CHANCE;
    public static ForgeConfigSpec.DoubleValue GLOBAL_BREAK_EXHAUSTION;
    public static ForgeConfigSpec.BooleanValue FLAT_BEDROCK;
    public static ForgeConfigSpec.IntValue BEDROCK_LAYERS;
    public static ForgeConfigSpec.IntValue NOISE_SCALE;
    public static ForgeConfigSpec.IntValue NOISE_OFFSET;



    //public static ForgeConfigSpec.BooleanValue DEFAULT_ORE;

    public static ForgeConfigSpec.BooleanValue NATIVE_COPPER_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue NATIVE_TIN_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue NATIVE_LEAD_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue NATIVE_SILVER_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue NATIVE_ALUMINUM_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue NATIVE_GOLD_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue NATIVE_BISMUTH_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue STIBNITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue STIBNITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue HALITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue HALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue HALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue HALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue HALITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue PINK_HALITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PINK_HALITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue MALACHITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue CASSITERITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CASSITERITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue BAUXITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue BAUXITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue SPHALERITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue SPHALERITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue LIGNITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue LIGNITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue SUBBITUMINOUS_COAL_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_COAL_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_COAL_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_COAL_ORE_SIZE;
    public static ForgeConfigSpec.IntValue SUBBITUMINOUS_COAL_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue BITUMINOUS_COAL_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue BITUMINOUS_COAL_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue BITUMINOUS_COAL_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue BITUMINOUS_COAL_ORE_SIZE;
    public static ForgeConfigSpec.IntValue BITUMINOUS_COAL_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue MAGNETITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MAGNETITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue MAGNESITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MAGNESITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue PENTLANDITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PENTLANDITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue GALENA_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue GALENA_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue GALENA_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue GALENA_ORE_SIZE;
    public static ForgeConfigSpec.IntValue GALENA_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue ACANTHITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue ACANTHITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue PYROLUSITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PYROLUSITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue PETALITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PETALITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue CINNABAR_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CINNABAR_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue CHROMITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CHROMITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue CELESTINE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue CELESTINE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue VANADINITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue VANADINITE_ORE_COUNT;
    
    public static ForgeConfigSpec.BooleanValue LAZURITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue LAZURITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue EMERALD_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue EMERALD_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue AQUAMARINE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue AQUAMARINE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue MAJORITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MAJORITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue PLUMBAGO_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_SIZE;
    public static ForgeConfigSpec.IntValue PLUMBAGO_ORE_COUNT;


    //NETHER ORES
    public static ForgeConfigSpec.BooleanValue NATIVE_ARSENIC_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_COUNT;
    
    public static ForgeConfigSpec.BooleanValue NATIVE_SULFUR_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue ANTHRACITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue ANTHRACITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue COBALTITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue COBALTITE_ORE_COUNT;
    
    public static ForgeConfigSpec.BooleanValue BISMUTHINITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue BISMUTHINITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue ILMENITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue ILMENITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue WOLFRAMITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue SPERRYLITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue SPERRYLITE_ORE_COUNT;
    
    public static ForgeConfigSpec.BooleanValue MOISSANITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue MOISSANITE_ORE_COUNT;
    
    public static ForgeConfigSpec.BooleanValue GREENOCKITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue GREENOCKITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue COLUMBITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue COLUMBITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue TANTALITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue TANTALITE_ORE_COUNT;
    
    
    
    
    //END ORES
    public static ForgeConfigSpec.BooleanValue NATIVE_GALLIUM_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue NATIVE_INDIUM_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue NATIVE_SELENIUM_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue NATIVE_TELLURIUM_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue URANINITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue URANINITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue FLUORITE_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_SIZE;
    public static ForgeConfigSpec.IntValue FLUORITE_ORE_COUNT;

    public static ForgeConfigSpec.BooleanValue XENOTIME_ORE_ENABLED;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_SIZE;
    public static ForgeConfigSpec.IntValue XENOTIME_ORE_COUNT;


    
    



    static {

        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        setupFirstBlockConfig();
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Alloy settings").push(CATEGORY_MECHANICS);
        setupSecondBlockConfig();
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupFirstBlockConfig() {
        //General config
        COMMON_BUILDER.comment("General Mod Settings").push(SUBCATEGORY_EVENTS);

        STARTING_BOOK = COMMON_BUILDER.comment("Enables (not functional yet) the Rankine Journal (a guide to the mod)")
                .define("startingBook",true);
        MANDATORY_AXE = COMMON_BUILDER.comment("An axe is required to harvest logs")
                .define("axesOnly",false);
        DISABLE_WOOD = COMMON_BUILDER.comment("Disable the use of wooden tools (still allows crafting for other recipes). This is enabled by default.")
                .define("disableWood",true);
        DISABLE_STONE = COMMON_BUILDER.comment("Disable the use of stone tools (still allows crafting for other recipes). This is enabled by default.")
                .define("disableStone",true);
        DISABLE_IRON = COMMON_BUILDER.comment("Disable the use of iron tools (still allows crafting for other recipes). This is disabled by default.")
                .define("disableIron",false);
        DISABLE_GOLD = COMMON_BUILDER.comment("Disable the use of golden tools (still allows crafting for other recipes). This is disabled by default.")
                .define("disableGold",false);
        DISABLE_DIAMOND = COMMON_BUILDER.comment("Disable the use of diamond tools (still allows crafting for other recipes). This is disabled by default.")
                .define("disableDiamond",false);
        DISABLE_NETHERITE = COMMON_BUILDER.comment("Disable the use of netherite tools (still allows crafting for other recipes). This is disabled by default.")
                .define("disableNetherite",false);
        GLOBAL_BREAK_EXHAUSTION = COMMON_BUILDER.comment("Amount of additional exhaustion when breaking a block")
                .defineInRange("breakExhaustion", 0.00D, 0.00D, 1.00D);
        T1_BEEHIVE_OVEN_CHANCE = COMMON_BUILDER.comment("Chance on random tick for the beehive oven (T1) to cook a block")
                .defineInRange("breakExhaustion", 0.5D, 0.00D, 1.00D);
        T2_BEEHIVE_OVEN_CHANCE = COMMON_BUILDER.comment("Chance on random tick for the magnesium beehive oven (T2) to cook a block")
                .defineInRange("breakExhaustion", 0.75D, 0.00D, 1.00D);
        T3_BEEHIVE_OVEN_CHANCE = COMMON_BUILDER.comment("Chance on random tick for the zircon beehive oven (T3) to cook a block")
                .defineInRange("breakExhaustion", 1.0D, 0.00D, 1.00D);
        COMMON_BUILDER.pop();


        //Worldgen config
        COMMON_BUILDER.comment("Worldgen Settings").push(SUBCATEGORY_WORLDGEN);

        COMMON_BUILDER.comment("Bedrock Generation").push("bedrock");
        FLAT_BEDROCK = COMMON_BUILDER.comment("Generates with a flat bedrock layer (includes the Nether)")
                .define("flatBedrock",false);
        BEDROCK_LAYERS = COMMON_BUILDER.comment("Layers of bedrock to generate if flatBedrock is true")
                .defineInRange("bedrockLayers", 1, 0, 10);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Stone Layer Generation").push("layeringNoise");
        NOISE_SCALE = COMMON_BUILDER.comment("This determines how smooth stone layers generate. Larger values means smoother. Default value is 125.")
                .defineInRange("noiseScale", 125, 1, 1000);
        NOISE_OFFSET = COMMON_BUILDER.comment("This determines how close the overlap of noise layers is. A value of 0 means all layers are shaped identically. Default value is 256")
                .defineInRange("noiseOffset", 256, 0, 16*64);
        COMMON_BUILDER.pop();

            
        COMMON_BUILDER.comment("Overworld Ore Settings").push(SUBCATEGORY_OVERWORLD);

        COMMON_BUILDER.comment("Intrusion Ore Settings").push("intrusionOres");
        DIAMON_CHANCE = COMMON_BUILDER.comment("Chance for an kimberlite intrusion block to be replaced by a diamond ore")
                .defineInRange("diamondOreChance", 0.06D, 0.00D, 1.00D);
        ILMENITE_CHANCE = COMMON_BUILDER.comment("Chance for an kimberlite intrusion block to be replaced by an ilmenite ore")
                .defineInRange("ilmeniteOreChance", 0.003D, 0.00D, 1.00D);
        INTERSPINIFEX_CHANCE = COMMON_BUILDER.comment("Chance for an komatiite intrusion block to be replaced by an interspinifex ore")
                .defineInRange("interspinifexOreChance", 0.08D, 0.00D, 1.00D);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Copper Ore Settings").push("nativeCopperOre");
        NATIVE_COPPER_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Copper ore)")
                .define("nativeCopperOreEnabled",true);
        NATIVE_COPPER_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Copper ore at (make sure it is less than the maximum)")
                .defineInRange("nativeCopperOreMin", 55, 0, 256);
        NATIVE_COPPER_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Copper ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeCopperOreMax", 75, 0, 256);
        NATIVE_COPPER_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Copper ore vein")
                .defineInRange("nativeCopperOreSixe", 12, 0, 256);
        NATIVE_COPPER_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Copper ore veins to generate per chunk")
                .defineInRange("nativeCopperOreCount", 5, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Tn Ore Settings").push("nativeTinOre");
        NATIVE_TIN_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Tin ore)")
                .define("nativeTinOreEnabled",true);
        NATIVE_TIN_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Tin ore at (make sure it is less than the maximum)")
                .defineInRange("nativeTinOreMin", 55, 0, 256);
        NATIVE_TIN_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Tin ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeTinOreMax", 75, 0, 256);
        NATIVE_TIN_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Tin ore vein")
                .defineInRange("nativeTinOreSize", 12, 0, 256);
        NATIVE_TIN_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Tin ore veins to generate per chunk")
                .defineInRange("nativeTinOreCount", 4, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Lead Ore Settings").push("nativeLeadOre");
        NATIVE_LEAD_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Lead ore)")
                .define("nativeLeadOreEnabled",true);
        NATIVE_LEAD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Lead ore at (make sure it is less than the maximum)")
                .defineInRange("nativeLeadOreMin", 55, 0, 256);
        NATIVE_LEAD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Lead ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeLeadOreMax", 75, 0, 256);
        NATIVE_LEAD_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Lead ore vein")
                .defineInRange("nativeLeadOreSize", 12, 0, 256);
        NATIVE_LEAD_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Lead ore veins to generate per chunk")
                .defineInRange("nativeLeadOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Silver Ore Settings").push("nativeSilverOre");
        NATIVE_SILVER_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Silver ore)")
                .define("nativeSilverOreEnabled",true);
        NATIVE_SILVER_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Silver ore at (make sure it is less than the maximum)")
                .defineInRange("nativeSilverOreMin", 55, 0, 256);
        NATIVE_SILVER_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Silver ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeSilverOreMax", 75, 0, 256);
        NATIVE_SILVER_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Silver ore vein")
                .defineInRange("nativeSilverOreSize", 12, 0, 256);
        NATIVE_SILVER_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Silver ore veins to generate per chunk")
                .defineInRange("nativeSilverOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Aluminum Ore Settings").push("nativeAluminumOre");
        NATIVE_ALUMINUM_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Aluminum ore)")
                .define("nativeAluminumOreEnabled",true);
        NATIVE_ALUMINUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Aluminum ore at (make sure it is less than the maximum)")
                .defineInRange("nativeAluminumOreMin", 55, 0, 256);
        NATIVE_ALUMINUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Aluminum ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeAluminumOreMax", 75, 0, 256);
        NATIVE_ALUMINUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Aluminum ore vein")
                .defineInRange("nativeAluminumOreSize", 12, 0, 256);
        NATIVE_ALUMINUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Aluminum ore veins to generate per chunk")
                .defineInRange("nativeAluminumOreCount", 3, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Gold Ore Settings").push("nativeGoldOre");
        NATIVE_GOLD_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Gold ore)")
                .define("nativeGoldOreEnabled",true);
        NATIVE_GOLD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Gold ore at (make sure it is less than the maximum)")
                .defineInRange("nativeGoldOreMin", 10, 0, 256);
        NATIVE_GOLD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Gold ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeGoldOreMax", 75, 0, 256);
        NATIVE_GOLD_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Gold ore vein")
                .defineInRange("nativeGoldOreSize", 12, 0, 256);
        NATIVE_GOLD_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Gold ore veins to generate per chunk")
                .defineInRange("nativeGoldOreCount", 5, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Bismuth Ore Settings").push("nativeBismuthOre");
        NATIVE_BISMUTH_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Bismuth ore)")
                .define("nativeBismuthOreEnabled",true);
        NATIVE_BISMUTH_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Bismuth ore at (make sure it is less than the maximum)")
                .defineInRange("nativeBismuthOreMin", 10, 0, 256);
        NATIVE_BISMUTH_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Bismuth ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeBismuthOreMax", 40, 0, 256);
        NATIVE_BISMUTH_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Bismuth ore vein")
                .defineInRange("nativeBismuthOreSize", 12, 0, 256);
        NATIVE_BISMUTH_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Bismuth ore veins to generate per chunk")
                .defineInRange("nativeBismuthOreCount", 3, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Stibnite Ore Settings").push("stibniteOre");
        STIBNITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Stibnite ore)")
                .define("stibniteOreEnabled",true);
        STIBNITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Stibnite ore at (make sure it is less than the maximum)")
                .defineInRange("stibniteOreMin", 55, 0, 256);
        STIBNITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Stibnite ore at (make sure it is greater than the minimum)")
                .defineInRange("stibniteOreMax", 75, 0, 256);
        STIBNITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Stibnite ore vein")
                .defineInRange("stibniteOreSize", 12, 0, 256);
        STIBNITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Stibnite ore veins to generate per chunk")
                .defineInRange("stibniteOreCount", 3, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Halite Ore Settings").push("haliteOre");
        HALITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Halite ore)")
                .define("haliteOreEnabled",true);
        HALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Halite ore at (make sure it is less than the maximum)")
                .defineInRange("haliteOreMin", 50, 0, 256);
        HALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Halite ore at (make sure it is greater than the minimum)")
                .defineInRange("haliteOreMax", 70, 0, 256);
        HALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Halite ore vein")
                .defineInRange("haliteOreSize", 20, 0, 256);
        HALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Halite ore veins to generate per chunk")
                .defineInRange("haliteOreCount", 1, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Pink Halite Ore Settings").push("pinkHaliteOre");
        PINK_HALITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Pink Halite ore)")
                .define("pinkHaliteOreEnabled",true);
        PINK_HALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pink Halite ore at (make sure it is less than the maximum)")
                .defineInRange("pinkHaliteOreMin", 70, 0, 256);
        PINK_HALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pink Halite ore at (make sure it is greater than the minimum)")
                .defineInRange("pinkHaliteOreMax", 128, 0, 256);
        PINK_HALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Pink Halite ore vein")
                .defineInRange("pinkHaliteOreSize", 20, 0, 256);
        PINK_HALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Pink Halite ore veins to generate per chunk")
                .defineInRange("pinkHaliteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Malachite Ore Settings").push("malachiteOre");
        MALACHITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Malachite ore)")
                .define("malachiteOreEnabled",true);
        MALACHITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Malachite ore at (make sure it is less than the maximum)")
                .defineInRange("malachiteOreMin", 35, 0, 256);
        MALACHITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Malachite ore at (make sure it is greater than the minimum)")
                .defineInRange("malachiteOreMax", 60, 0, 256);
        MALACHITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Malachite ore vein")
                .defineInRange("malachiteOreSize", 20, 0, 256);
        MALACHITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Malachite ore veins to generate per chunk")
                .defineInRange("malachiteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Cassiterite Ore Settings").push("cassiteriteOre");
        CASSITERITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Cassiterite ore)")
                .define("cassiteriteOreEnabled",true);
        CASSITERITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cassiterite ore at (make sure it is less than the maximum)")
                .defineInRange("cassiteriteOreMin", 35, 0, 256);
        CASSITERITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cassiterite ore at (make sure it is greater than the minimum)")
                .defineInRange("cassiteriteOreMax", 60, 0, 256);
        CASSITERITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Cassiterite ore vein")
                .defineInRange("cassiteriteOreSize", 20, 0, 256);
        CASSITERITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Cassiterite ore veins to generate per chunk")
                .defineInRange("cassiteriteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Bauxite Ore Settings").push("bauxiteOre");
        BAUXITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Bauxite ore)")
                .define("bauxiteOreEnabled",true);
        BAUXITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Bauxite ore at (make sure it is less than the maximum)")
                .defineInRange("bauxiteOreMin", 35, 0, 256);
        BAUXITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Bauxite ore at (make sure it is greater than the minimum)")
                .defineInRange("bauxiteOreMax", 60, 0, 256);
        BAUXITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Bauxite ore vein")
                .defineInRange("bauxiteOreSize", 20, 0, 256);
        BAUXITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Bauxite ore veins to generate per chunk")
                .defineInRange("bauxiteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Sphalerite Ore Settings").push("sphaleriteOre");
        SPHALERITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Sphalerite ore)")
                .define("sphaleriteOreEnabled",true);
        SPHALERITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Sphalerite ore at (make sure it is less than the maximum)")
                .defineInRange("sphaleriteOreMin", 35, 0, 256);
        SPHALERITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Sphalerite ore at (make sure it is greater than the minimum)")
                .defineInRange("sphaleriteOreMax", 60, 0, 256);
        SPHALERITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Sphalerite ore vein")
                .defineInRange("sphaleriteOreSize", 20, 0, 256);
        SPHALERITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Sphalerite ore veins to generate per chunk")
                .defineInRange("sphaleriteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Lignite Ore Settings").push("ligniteOre");
        LIGNITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Lignite ore)")
                .define("ligniteOreEnabled",true);
        LIGNITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Lignite ore at (make sure it is less than the maximum)")
                .defineInRange("ligniteOreMin", 55, 0, 256);
        LIGNITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Lignite ore at (make sure it is greater than the minimum)")
                .defineInRange("ligniteOreMax", 75, 0, 256);
        LIGNITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Lignite ore vein")
                .defineInRange("ligniteOreSize", 20, 0, 256);
        LIGNITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Lignite ore veins to generate per chunk")
                .defineInRange("ligniteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Subbituminous Coal Ore Settings").push("subbituminousCoalOre");
        SUBBITUMINOUS_COAL_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Subbituminous Coal ore)")
                .define("subbituminousCoalOreEnabled",true);
        SUBBITUMINOUS_COAL_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Subbituminous Coal ore at (make sure it is less than the maximum)")
                .defineInRange("subbituminousCoalOreMin", 35, 0, 256);
        SUBBITUMINOUS_COAL_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Subbituminous Coal ore at (make sure it is greater than the minimum)")
                .defineInRange("subbituminousCoalOreMax", 55, 0, 256);
        SUBBITUMINOUS_COAL_ORE_SIZE = COMMON_BUILDER.comment("Size of Subbituminous Coal ore vein")
                .defineInRange("subbituminousCoalOreSize", 20, 0, 256);
        SUBBITUMINOUS_COAL_ORE_COUNT = COMMON_BUILDER.comment("Number of Subbituminous Coal ore veins to generate per chunk")
                .defineInRange("subbituminousCoalOreCount", 4, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Bituminous Coal Ore Settings").push("bituminousCoalOre");
        BITUMINOUS_COAL_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Bituminous Coal ore)")
                .define("bituminousCoalOreEnabled",true);
        BITUMINOUS_COAL_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Bituminous Coal ore at (make sure it is less than the maximum)")
                .defineInRange("bituminousCoalOreMin", 15, 0, 256);
        BITUMINOUS_COAL_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Bituminous Coal ore at (make sure it is greater than the minimum)")
                .defineInRange("bituminousCoalOreMax", 35, 0, 256);
        BITUMINOUS_COAL_ORE_SIZE = COMMON_BUILDER.comment("Size of Bituminous Coal ore vein")
                .defineInRange("bituminousCoalOreSize", 20, 0, 256);
        BITUMINOUS_COAL_ORE_COUNT = COMMON_BUILDER.comment("Number of Bituminous Coal ore veins to generate per chunk")
                .defineInRange("bituminousCoalOreCount", 4, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Magnetite Ore Settings").push("magnetiteOre");
        MAGNETITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Magnetite ore)")
                .define("magnetiteOreEnabled",true);
        MAGNETITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Magnetite ore at (make sure it is less than the maximum)")
                .defineInRange("magnetiteOreMin", 10, 0, 256);
        MAGNETITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Magnetite ore at (make sure it is greater than the minimum)")
                .defineInRange("magnetiteOreMax", 50, 0, 256);
        MAGNETITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Magnetite ore vein")
                .defineInRange("magnetiteOreSize", 25, 0, 256);
        MAGNETITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Magnetite ore veins to generate per chunk")
                .defineInRange("magnetiteOreCount", 3, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Magnesite Ore Settings").push("magnesiteOre");
        MAGNESITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Magnesite ore)")
                .define("magnesiteOreEnabled",true);
        MAGNESITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Magnesite ore at (make sure it is less than the maximum)")
                .defineInRange("magnesiteOreMin", 10, 0, 256);
        MAGNESITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Magnesite ore at (make sure it is greater than the minimum)")
                .defineInRange("magnesiteOreMax", 45, 0, 256);
        MAGNESITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Magnesite ore vein")
                .defineInRange("magnesiteOreSize", 20, 0, 256);
        MAGNESITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Magnesite ore veins to generate per chunk")
                .defineInRange("magnesiteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Pentlandite Ore Settings").push("pentlanditeOre");
        PENTLANDITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Pentlandite ore)")
                .define("pentlanditeOreEnabled",true);
        PENTLANDITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pentlandite ore at (make sure it is less than the maximum)")
                .defineInRange("pentlanditeOreMin", 10, 0, 256);
        PENTLANDITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pentlandite ore at (make sure it is greater than the minimum)")
                .defineInRange("pentlanditeOreMax", 45, 0, 256);
        PENTLANDITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Pentlandite ore vein")
                .defineInRange("pentlanditeOreSize", 20, 0, 256);
        PENTLANDITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Pentlandite ore veins to generate per chunk")
                .defineInRange("pentlanditeOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Galena Ore Settings").push("galenaOre");
        GALENA_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Galena ore)")
                .define("galenaOreEnabled",true);
        GALENA_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Galena ore at (make sure it is less than the maximum)")
                .defineInRange("galenaOreMin", 10, 0, 256);
        GALENA_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Galena ore at (make sure it is greater than the minimum)")
                .defineInRange("galenaOreMax", 45, 0, 256);
        GALENA_ORE_SIZE = COMMON_BUILDER.comment("Size of Galena ore vein")
                .defineInRange("galenaOreSize", 20, 0, 256);
        GALENA_ORE_COUNT = COMMON_BUILDER.comment("Number of Galena ore veins to generate per chunk")
                .defineInRange("galenaOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Acanthite Ore Settings").push("acanthiteOre");
        ACANTHITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Acanthite ore)")
                .define("acanthiteOreEnabled",true);
        ACANTHITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Acanthite ore at (make sure it is less than the maximum)")
                .defineInRange("acanthiteOreMin", 10, 0, 256);
        ACANTHITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Acanthite ore at (make sure it is greater than the minimum)")
                .defineInRange("acanthiteOreMax", 45, 0, 256);
        ACANTHITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Acanthite ore vein")
                .defineInRange("acanthiteOreSize", 15, 0, 256);
        ACANTHITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Acanthite ore veins to generate per chunk")
                .defineInRange("acanthiteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Pyrolusite Ore Settings").push("pyrolusiteOre");
        PYROLUSITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Pyrolusite ore)")
                .define("pyrolusiteOreEnabled",true);
        PYROLUSITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Pyrolusite ore at (make sure it is less than the maximum)")
                .defineInRange("pyrolusiteOreMin", 10, 0, 256);
        PYROLUSITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Pyrolusite ore at (make sure it is greater than the minimum)")
                .defineInRange("pyrolusiteOreMax", 45, 0, 256);
        PYROLUSITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Pyrolusite ore vein")
                .defineInRange("pyrolusiteOreSize", 15, 0, 256);
        PYROLUSITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Pyrolusite ore veins to generate per chunk")
                .defineInRange("pyrolusiteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Petalite Ore Settings").push("petaliteOre");
        PETALITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Petalite ore)")
                .define("petaliteOreEnabled",true);
        PETALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Petalite ore at (make sure it is less than the maximum)")
                .defineInRange("petaliteOreMin", 20, 0, 256);
        PETALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Petalite ore at (make sure it is greater than the minimum)")
                .defineInRange("petaliteOreMax", 45, 0, 256);
        PETALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Petalite ore vein")
                .defineInRange("petaliteOreSize", 8, 0, 256);
        PETALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Petalite ore veins to generate per chunk")
                .defineInRange("petaliteOreCount", 1, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Cinnabar Ore Settings (Only generates in igneous rocks)").push("cinnabarOre");
        CINNABAR_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Cinnabar ore)")
                .define("cinnabarOreEnabled",true);
        CINNABAR_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cinnabar ore at (make sure it is less than the maximum)")
                .defineInRange("cinnabarOreMin", 20, 0, 256);
        CINNABAR_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cinnabar ore at (make sure it is greater than the minimum)")
                .defineInRange("cinnabarOreMax", 45, 0, 256);
        CINNABAR_ORE_SIZE = COMMON_BUILDER.comment("Size of Cinnabar ore vein")
                .defineInRange("cinnabarOreSize", 20, 0, 256);
        CINNABAR_ORE_COUNT = COMMON_BUILDER.comment("Number of Cinnabar ore veins to generate per chunk")
                .defineInRange("cinnabarOreCount", 3, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Chromite Ore Settings (Only generates in igneous rocks)").push("chromiteOre");
        CHROMITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Chromite ore)")
                .define("chromiteOreEnabled",true);
        CHROMITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Chromite ore at (make sure it is less than the maximum)")
                .defineInRange("chromiteOreMin", 0, 0, 256);
        CHROMITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Chromite ore at (make sure it is greater than the minimum)")
                .defineInRange("chromiteOreMax", 15, 0, 256);
        CHROMITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Chromite ore vein")
                .defineInRange("chromiteOreSize", 10, 0, 256);
        CHROMITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Chromite ore veins to generate per chunk")
                .defineInRange("chromiteOreCount", 1, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Celestine Ore Settings (Only generates in sedimentary rocks)").push("celestineOre");
        CELESTINE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Celestine ore)")
                .define("celestineOreEnabled",true);
        CELESTINE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Celestine ore at (make sure it is less than the maximum)")
                .defineInRange("celestineOreMin", 50, 0, 256);
        CELESTINE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Celestine ore at (make sure it is greater than the minimum)")
                .defineInRange("celestineOreMax", 70, 0, 256);
        CELESTINE_ORE_SIZE = COMMON_BUILDER.comment("Size of Celestine ore vein")
                .defineInRange("celestineOreSize", 8, 0, 256);
        CELESTINE_ORE_COUNT = COMMON_BUILDER.comment("Number of Celestine ore veins to generate per chunk")
                .defineInRange("celestineOreCount", 1, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Vanadinite Ore Settings").push("vanadiniteOre");
        VANADINITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Vanadinite ore)")
                .define("vanadiniteOreEnabled",true);
        VANADINITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Vanadinite ore at (make sure it is less than the maximum)")
                .defineInRange("vanadiniteOreMin", 50, 0, 256);
        VANADINITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Vanadinite ore at (make sure it is greater than the minimum)")
                .defineInRange("vanadiniteOreMax", 70, 0, 256);
        VANADINITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Vanadinite ore vein")
                .defineInRange("vanadiniteOreSize", 8, 0, 256);
        VANADINITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Vanadinite ore veins to generate per chunk")
                .defineInRange("vanadiniteOreCount", 1, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Lazurite Ore Settings (Only generates in limestone)").push("lazuriteOre");
        LAZURITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Lazurite ore)")
                .define("lazuriteOreEnabled",true);
        LAZURITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Lazurite ore at (make sure it is less than the maximum)")
                .defineInRange("lazuriteOreMin", 40, 0, 256);
        LAZURITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Lazurite ore at (make sure it is greater than the minimum)")
                .defineInRange("lazuriteOreMax", 70, 0, 256);
        LAZURITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Lazurite ore vein")
                .defineInRange("lazuriteOreSize", 20, 0, 256);
        LAZURITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Lazurite ore veins to generate per chunk")
                .defineInRange("lazuriteOreCount", 3, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Emerald Ore Settings").push("emeraldOre");
        EMERALD_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Emerald ore)")
                .define("emeraldOreEnabled",true);
        EMERALD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Emerald ore at (make sure it is less than the maximum)")
                .defineInRange("emeraldOreMin", 10, 0, 256);
        EMERALD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Emerald ore at (make sure it is greater than the minimum)")
                .defineInRange("emeraldOreMax", 60, 0, 256);
        EMERALD_ORE_SIZE = COMMON_BUILDER.comment("Size of Emerald ore vein")
                .defineInRange("emeraldOreSize", 5, 0, 256);
        EMERALD_ORE_COUNT = COMMON_BUILDER.comment("Number of Emerald ore veins to generate per chunk")
                .defineInRange("emeraldOreCount", 3, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Aquamarine Ore Settings").push("aquamarineOre");
        AQUAMARINE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Aquamarine ore)")
                .define("aquamarineOreEnabled",true);
        AQUAMARINE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Aquamarine ore at (make sure it is less than the maximum)")
                .defineInRange("aquamarineOreMin", 10, 0, 256);
        AQUAMARINE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Aquamarine ore at (make sure it is greater than the minimum)")
                .defineInRange("aquamarineOreMax", 60, 0, 256);
        AQUAMARINE_ORE_SIZE = COMMON_BUILDER.comment("Size of Aquamarine ore vein")
                .defineInRange("aquamarineOreSize", 5, 0, 256);
        AQUAMARINE_ORE_COUNT = COMMON_BUILDER.comment("Number of Aquamarine ore veins to generate per chunk")
                .defineInRange("aquamarineOreCount", 3, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Majorite Ore Settings").push("majoriteOre");
        MAJORITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Majorite ore)")
                .define("majoriteOreEnabled",true);
        MAJORITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Majorite ore at (make sure it is less than the maximum)")
                .defineInRange("majoriteOreMin", 0, 0, 256);
        MAJORITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Majorite ore at (make sure it is greater than the minimum)")
                .defineInRange("majoriteOreMax", 15, 0, 256);
        MAJORITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Majorite ore vein")
                .defineInRange("majoriteOreSize", 5, 0, 256);
        MAJORITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Majorite ore veins to generate per chunk")
                .defineInRange("majoriteOreCount", 1, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Plumbago Ore Settings (Only generates in marble)").push("plumbagoOre");
        PLUMBAGO_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Plumbago ore)")
                .define("plumbagoOreEnabled",true);
        PLUMBAGO_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Plumbago ore at (make sure it is less than the maximum)")
                .defineInRange("plumbagoOreMin", 5, 0, 256);
        PLUMBAGO_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Plumbago ore at (make sure it is greater than the minimum)")
                .defineInRange("plumbagoOreMax", 25, 0, 256);
        PLUMBAGO_ORE_SIZE = COMMON_BUILDER.comment("Size of Plumbago ore vein")
                .defineInRange("plumbagoOreSize", 15, 0, 256);
        PLUMBAGO_ORE_COUNT = COMMON_BUILDER.comment("Number of Plumbago ore veins to generate per chunk")
                .defineInRange("plumbagoOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();


        COMMON_BUILDER.pop();


        //NETHER ORES
        COMMON_BUILDER.comment("Nether Ore Settings").push(SUBCATEGORY_NETHER);

        COMMON_BUILDER.comment("Native Arsenic Ore Settings").push("nativeArsenicOre");
        NATIVE_ARSENIC_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Arsenic ore)")
                .define("nativeArsenicOreEnabled",true);
        NATIVE_ARSENIC_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Arsenic ore at (make sure it is less than the maximum)")
                .defineInRange("nativeArsenicOreMin", 30, 0, 256);
        NATIVE_ARSENIC_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Arsenic ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeArsenicOreMax", 90, 0, 256);
        NATIVE_ARSENIC_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Arsenic ore vein")
                .defineInRange("nativeArsenicOreSize", 12, 0, 256);
        NATIVE_ARSENIC_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Arsenic ore veins to generate per chunk")
                .defineInRange("nativeArsenicOreCount", 8, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Sulfur Ore Settings").push("nativeSulfurOre");
        NATIVE_SULFUR_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Sulfur ore)")
                .define("nativeSulfurOreEnabled",true);
        NATIVE_SULFUR_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Sulfur ore at (make sure it is less than the maximum)")
                .defineInRange("nativeSulfurOreMin", 30, 0, 256);
        NATIVE_SULFUR_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Sulfur ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeSulfurOreMax", 90, 0, 256);
        NATIVE_SULFUR_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Sulfur ore vein")
                .defineInRange("nativeSulfurOreSize", 12, 0, 256);
        NATIVE_SULFUR_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Sulfur ore veins to generate per chunk")
                .defineInRange("nativeSulfurOreCount", 8, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Anthracite Ore Settings").push("anthraciteOre");
        ANTHRACITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Anthracite ore)")
                .define("anthraciteOreEnabled",true);
        ANTHRACITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Anthracite ore at (make sure it is less than the maximum)")
                .defineInRange("anthraciteOreMin", 30, 0, 256);
        ANTHRACITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Anthracite ore at (make sure it is greater than the minimum)")
                .defineInRange("anthraciteOreMax", 90, 0, 256);
        ANTHRACITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Anthracite ore vein")
                .defineInRange("anthraciteOreSize", 20, 0, 256);
        ANTHRACITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Anthracite ore veins to generate per chunk")
                .defineInRange("anthraciteOreCount", 8, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Cobaltite Ore Settings").push("cobaltiteOre");
        COBALTITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Cobaltite ore)")
                .define("cobaltiteOreEnabled",true);
        COBALTITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Cobaltite ore at (make sure it is less than the maximum)")
                .defineInRange("cobaltiteOreMin", 30, 0, 256);
        COBALTITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Cobaltite ore at (make sure it is greater than the minimum)")
                .defineInRange("cobaltiteOreMax", 90, 0, 256);
        COBALTITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Cobaltite ore vein")
                .defineInRange("cobaltiteOreSize", 10, 0, 256);
        COBALTITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Cobaltite ore veins to generate per chunk")
                .defineInRange("cobaltiteOreCount", 3, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Bismuthinite Ore Settings").push("bismuthiniteOre");
        BISMUTHINITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Bismuthinite ore)")
                .define("bismuthiniteOreEnabled",true);
        BISMUTHINITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Bismuthinite ore at (make sure it is less than the maximum)")
                .defineInRange("bismuthiniteOreMin", 90, 0, 256);
        BISMUTHINITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Bismuthinite ore at (make sure it is greater than the minimum)")
                .defineInRange("bismuthiniteOreMax", 128, 0, 256);
        BISMUTHINITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Bismuthinite ore vein")
                .defineInRange("bismuthiniteOreSize", 15, 0, 256);
        BISMUTHINITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Bismuthinite ore veins to generate per chunk")
                .defineInRange("bismuthiniteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Ilmenite Ore Settings").push("ilmeniteOre");
        ILMENITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Ilmenite ore)")
                .define("ilmeniteOreEnabled",true);
        ILMENITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Ilmenite ore at (make sure it is less than the maximum)")
                .defineInRange("ilmeniteOreMin", 90, 0, 256);
        ILMENITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Ilmenite ore at (make sure it is greater than the minimum)")
                .defineInRange("ilmeniteOreMax", 128, 0, 256);
        ILMENITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Ilmenite ore vein")
                .defineInRange("ilmeniteOreSize", 20, 0, 256);
        ILMENITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Ilmenite ore veins to generate per chunk")
                .defineInRange("ilmeniteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Wolframite Ore Settings").push("wolframiteOre");
        WOLFRAMITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Wolframite ore)")
                .define("wolframiteOreEnabled",true);
        WOLFRAMITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Wolframite ore at (make sure it is less than the maximum)")
                .defineInRange("wolframiteOreMin", 90, 0, 256);
        WOLFRAMITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Wolframite ore at (make sure it is greater than the minimum)")
                .defineInRange("wolframiteOreMax", 128, 0, 256);
        WOLFRAMITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Wolframite ore vein")
                .defineInRange("wolframiteOreSize", 20, 0, 256);
        WOLFRAMITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Wolframite ore veins to generate per chunk")
                .defineInRange("wolframiteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Sperrylite Ore Settings").push("sperryliteOre");
        SPERRYLITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Sperrylite ore)")
                .define("sperryliteOreEnabled",true);
        SPERRYLITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Sperrylite ore at (make sure it is less than the maximum)")
                .defineInRange("sperryliteOreMin", 90, 0, 256);
        SPERRYLITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Sperrylite ore at (make sure it is greater than the minimum)")
                .defineInRange("sperryliteOreMax", 128, 0, 256);
        SPERRYLITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Sperrylite ore vein")
                .defineInRange("sperryliteOreSize", 20, 0, 256);
        SPERRYLITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Sperrylite ore veins to generate per chunk")
                .defineInRange("sperryliteOreCount", 2, 0, 256);
        COMMON_BUILDER.pop();
        
        COMMON_BUILDER.comment("Moissanite Ore Settings").push("moissaniteOre");
        MOISSANITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Moissanite ore)")
                .define("moissaniteOreEnabled",true);
        MOISSANITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Moissanite ore at (make sure it is less than the maximum)")
                .defineInRange("moissaniteOreMin", 1, 0, 256);
        MOISSANITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Moissanite ore at (make sure it is greater than the minimum)")
                .defineInRange("moissaniteOreMax", 30, 0, 256);
        MOISSANITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Moissanite ore vein")
                .defineInRange("moissaniteOreSize", 12, 0, 256);
        MOISSANITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Moissanite ore veins to generate per chunk")
                .defineInRange("moissaniteOreCount", 1, 0, 256);
        COMMON_BUILDER.pop();
        
        COMMON_BUILDER.comment("Greenockite Ore Settings").push("greenockiteOre");
        GREENOCKITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Greenockite ore)")
                .define("greenockiteOreEnabled",true);
        GREENOCKITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Greenockite ore at (make sure it is less than the maximum)")
                .defineInRange("greenockiteOreMin", 1, 0, 256);
        GREENOCKITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Greenockite ore at (make sure it is greater than the minimum)")
                .defineInRange("greenockiteOreMax", 30, 0, 256);
        GREENOCKITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Greenockite ore vein")
                .defineInRange("greenockiteOreSize", 10, 0, 256);
        GREENOCKITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Greenockite ore veins to generate per chunk")
                .defineInRange("greenockiteOreCount", 1, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Columbite Ore Settings").push("columbiteOre");
        COLUMBITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Columbite ore)")
                .define("columbiteOreEnabled",true);
        COLUMBITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Columbite ore at (make sure it is less than the maximum)")
                .defineInRange("columbiteOreMin", 1, 0, 256);
        COLUMBITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Columbite ore at (make sure it is greater than the minimum)")
                .defineInRange("columbiteOreMax", 30, 0, 256);
        COLUMBITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Columbite ore vein")
                .defineInRange("columbiteOreSize", 10, 0, 256);
        COLUMBITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Columbite ore veins to generate per chunk")
                .defineInRange("columbiteOreCount", 1, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Tantalite Ore Settings").push("tantaliteOre");
        TANTALITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Tantalite ore)")
                .define("tantaliteOreEnabled",true);
        TANTALITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Tantalite ore at (make sure it is less than the maximum)")
                .defineInRange("tantaliteOreMin", 1, 0, 256);
        TANTALITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Tantalite ore at (make sure it is greater than the minimum)")
                .defineInRange("tantaliteOreMax", 30, 0, 256);
        TANTALITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Tantalite ore vein")
                .defineInRange("tantaliteOreSize", 10, 0, 256);
        TANTALITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Tantalite ore veins to generate per chunk")
                .defineInRange("tantaliteOreCount", 1, 0, 256);
        COMMON_BUILDER.pop();
        
        COMMON_BUILDER.pop();


        //END ORES
        COMMON_BUILDER.comment("End Ore Settings").push(SUBCATEGORY_END);
                
        COMMON_BUILDER.comment("Native Gallium Ore Settings").push("nativeGalliumOre");
        NATIVE_GALLIUM_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Gallium ore)")
                .define("nativeGalliumOreEnabled",true);
        NATIVE_GALLIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Gallium ore at (make sure it is less than the maximum)")
                .defineInRange("nativeGalliumOreMin", 40, 0, 256);
        NATIVE_GALLIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Gallium ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeGalliumOreMax", 100, 0, 256);
        NATIVE_GALLIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Gallium ore vein")
                .defineInRange("nativeGalliumOreSize", 12, 0, 256);
        NATIVE_GALLIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Gallium ore veins to generate per chunk")
                .defineInRange("nativeGalliumOreCount", 10, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Indium Ore Settings").push("nativeIndiumOre");
        NATIVE_INDIUM_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Indium ore)")
                .define("nativeIndiumOreEnabled",true);
        NATIVE_INDIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Indium ore at (make sure it is less than the maximum)")
                .defineInRange("nativeIndiumOreMin", 40, 0, 256);
        NATIVE_INDIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Indium ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeIndiumOreMax", 100, 0, 256);
        NATIVE_INDIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Indium ore vein")
                .defineInRange("nativeIndiumOreSize", 12, 0, 256);
        NATIVE_INDIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Indium ore veins to generate per chunk")
                .defineInRange("nativeIndiumOreCount", 10, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Selenium Ore Settings").push("nativeSeleniumOre");
        NATIVE_SELENIUM_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Selenium ore)")
                .define("nativeSeleniumOreEnabled",true);
        NATIVE_SELENIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Selenium ore at (make sure it is less than the maximum)")
                .defineInRange("nativeSeleniumOreMin", 40, 0, 256);
        NATIVE_SELENIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Selenium ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeSeleniumOreMax", 100, 0, 256);
        NATIVE_SELENIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Selenium ore vein")
                .defineInRange("nativeSeleniumOreSize", 12, 0, 256);
        NATIVE_SELENIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Selenium ore veins to generate per chunk")
                .defineInRange("nativeSeleniumOreCount", 10, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Native Tellurium Ore Settings").push("nativeTelluriumOre");
        NATIVE_TELLURIUM_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Native Tellurium ore)")
                .define("nativeTelluriumOreEnabled",true);
        NATIVE_TELLURIUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Tellurium ore at (make sure it is less than the maximum)")
                .defineInRange("nativeTelluriumOreMin", 40, 0, 256);
        NATIVE_TELLURIUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Tellurium ore at (make sure it is greater than the minimum)")
                .defineInRange("nativeTelluriumOreMax", 100, 0, 256);
        NATIVE_TELLURIUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Tellurium ore vein")
                .defineInRange("nativeTelluriumOreSize", 12, 0, 256);
        NATIVE_TELLURIUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Tellurium ore veins to generate per chunk")
                .defineInRange("nativeTelluriumOreCount", 10, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Uraninite Ore Settings").push("uraniniteOre");
        URANINITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Uraninite ore)")
                .define("uraniniteOreEnabled",true);
        URANINITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Uraninite ore at (make sure it is less than the maximum)")
                .defineInRange("uraniniteOreMin", 40, 0, 256);
        URANINITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Uraninite ore at (make sure it is greater than the minimum)")
                .defineInRange("uraniniteOreMax", 100, 0, 256);
        URANINITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Uraninite ore vein")
                .defineInRange("uraniniteOreSize", 20, 0, 256);
        URANINITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Uraninite ore veins to generate per chunk")
                .defineInRange("uraniniteOreCount", 8, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Fluorite Ore Settings").push("fluoriteOre");
        FLUORITE_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Fluorite ore)")
                .define("fluoriteOreEnabled",true);
        FLUORITE_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Fluorite ore at (make sure it is less than the maximum)")
                .defineInRange("fluoriteOreMin", 40, 0, 256);
        FLUORITE_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Fluorite ore at (make sure it is greater than the minimum)")
                .defineInRange("fluoriteOreMax", 100, 0, 256);
        FLUORITE_ORE_SIZE = COMMON_BUILDER.comment("Size of Fluorite ore vein")
                .defineInRange("fluoriteOreSize", 15, 0, 256);
        FLUORITE_ORE_COUNT = COMMON_BUILDER.comment("Number of Fluorite ore veins to generate per chunk")
                .defineInRange("fluoriteOreCount", 8, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Xenotime Ore Settings").push("xenotimeOre");
        XENOTIME_ORE_ENABLED = COMMON_BUILDER.comment("Enables (not functional yet) Xenotime ore)")
                .define("xenotimeOreEnabled",true);
        XENOTIME_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Xenotime ore at (make sure it is less than the maximum)")
                .defineInRange("xenotimeOreMin", 40, 0, 256);
        XENOTIME_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Xenotime ore at (make sure it is greater than the minimum)")
                .defineInRange("xenotimeOreMax", 100, 0, 256);
        XENOTIME_ORE_SIZE = COMMON_BUILDER.comment("Size of Xenotime ore vein")
                .defineInRange("xenotimeOreSize", 10, 0, 256);
        XENOTIME_ORE_COUNT = COMMON_BUILDER.comment("Number of Xenotime ore veins to generate per chunk")
                .defineInRange("xenotimeOreCount", 8, 0, 256);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.pop();









        COMMON_BUILDER.pop();
    }

    private static void setupSecondBlockConfig() {
        COMMON_BUILDER.comment("Alloy settings").push(SUBCATEGORY_ALLOYS);

        ALLOY_CORROSION = COMMON_BUILDER.comment("Enables (not functional yet) the corrosion negative modifier for alloy tools (chance to consume an extra point of durability in water and rain)")
                .define("alloyCorrosion",true);

        ALLOY_HEAT = COMMON_BUILDER.comment("Enables (not functional yet) the heat negative modifier for alloy tools (chance to consume an extra point of durability in hot environments and lava)")
                .define("alloyHeat",true);

        ALLOY_TOUGHNESS = COMMON_BUILDER.comment("Enables (not functional yet) the toughness negative modifier for alloy tools (chance to consume an extra point of durability)")
                .define("alloyToughness",true);

        ALLOY_WEAR_MINING_AMT = COMMON_BUILDER.comment("Modifies the severity of the wear effect on mining speed (ex. 0.25 means mining speed will be reduced to 75% of the original value as durability is lost)")
                .defineInRange("alloyWearMiningAmount", 0.25D, 0.00D, 0.99D);

        ALLOY_WEAR_DAMAGE_AMT = COMMON_BUILDER.comment("Modifies the severity of the wear effect on damage (ex. 0.25 means damage will be reduced to 75% of the original value as durability is lost)")
                .defineInRange("alloyWearDamageAmount", 0.25D, 0.00D, 0.99D);

        AMALGAM_EXTRAS = COMMON_BUILDER.comment("Enables (not functional yet) the disabled metals for amalgam alloy (Fe, Pt, W, Ta)")
                .define("amalgamExtras",false);

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
