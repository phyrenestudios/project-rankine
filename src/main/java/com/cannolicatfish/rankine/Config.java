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

    public static ForgeConfigSpec.DoubleValue GLOBAL_BREAK_EXHAUSTION;
    public static ForgeConfigSpec.BooleanValue FLAT_BEDROCK;
    public static ForgeConfigSpec.IntValue BEDROCK_LAYERS;
    public static ForgeConfigSpec.IntValue NOISE_SCALE;
    public static ForgeConfigSpec.IntValue NOISE_OFFSET;

    public static ForgeConfigSpec.IntValue XXX_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue XXX_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue XXX_ORE_SIZE;
    public static ForgeConfigSpec.IntValue XXX_ORE_COUNT;

    //public static ForgeConfigSpec.BooleanValue DEFAULT_ORE;

    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_COUNT;
    
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_TIN_ORE_COUNT;

    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_COUNT;

    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_COUNT;

    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_COUNT;

    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_SIZE;
    public static ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_COUNT;








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

        STARTING_BOOK = COMMON_BUILDER.comment("Enables the Rankine Journal (a guide to the mod)")
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

    //        FLAT_BEDROCK = COMMON_BUILDER.comment("Set to false to use custum ore generation parametersss below. This is true by default.")
    //                .define("defaultOre",true);

            COMMON_BUILDER.comment("Native Copper Ore Settings").push("nativeCopperOre");
                NATIVE_COPPER_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Copper ore at (make sure it is less than the maximum)")
                        .defineInRange("nativeCopperOreMin", 51, 0, 256);
                NATIVE_COPPER_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Copper ore at (make sure it is greater than the minimum)")
                        .defineInRange("nativeCopperOreMax", 128, 0, 256);
                NATIVE_COPPER_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Copper ore vein")
                        .defineInRange("nativeCopperOreSixe", 12, 0, 256);
                NATIVE_COPPER_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Copper ore veins to generate per chunk")
                        .defineInRange("nativeCopperOreCount", 5, 0, 256);
            COMMON_BUILDER.pop();

            COMMON_BUILDER.comment("Native Tn Ore Settings").push("nativeTinOre");
                NATIVE_TIN_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Tin ore at (make sure it is less than the maximum)")
                        .defineInRange("nativeTinOreMin", 51, 0, 256);
                NATIVE_TIN_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Tin ore at (make sure it is greater than the minimum)")
                        .defineInRange("nativeTinOreMax", 128, 0, 256);
                NATIVE_TIN_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Tin ore vein")
                        .defineInRange("nativeTinOreSize", 12, 0, 256);
                NATIVE_TIN_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Tin ore veins to generate per chunk")
                        .defineInRange("nativeTinOreCount", 4, 0, 256);
            COMMON_BUILDER.pop();

            COMMON_BUILDER.comment("Native Lead Ore Settings").push("nativeLeadOre");
                NATIVE_LEAD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Lead ore at (make sure it is less than the maximum)")
                        .defineInRange("nativeLeadOreMin", 51, 0, 256);
                NATIVE_LEAD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Lead ore at (make sure it is greater than the minimum)")
                        .defineInRange("nativeLeadOreMax", 128, 0, 256);
                NATIVE_LEAD_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Lead ore vein")
                        .defineInRange("nativeLeadOreSize", 12, 0, 256);
                NATIVE_LEAD_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Lead ore veins to generate per chunk")
                        .defineInRange("nativeLeadOreCount", 2, 0, 256);
            COMMON_BUILDER.pop();

            COMMON_BUILDER.comment("Native Silver Ore Settings").push("nativeSilverOre");
                NATIVE_SILVER_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Silver ore at (make sure it is less than the maximum)")
                        .defineInRange("nativeSilverOreMin", 51, 0, 256);
                NATIVE_SILVER_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Silver ore at (make sure it is greater than the minimum)")
                        .defineInRange("nativeSilverOreMax", 128, 0, 256);
                NATIVE_SILVER_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Silver ore vein")
                        .defineInRange("nativeSilverOreSize", 12, 0, 256);
                NATIVE_SILVER_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Silver ore veins to generate per chunk")
                        .defineInRange("nativeSilverOreCount", 2, 0, 256);
            COMMON_BUILDER.pop();

            COMMON_BUILDER.comment("Native Aluminum Ore Settings").push("nativeAluminumOre");
                NATIVE_ALUMINUM_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Aluminum ore at (make sure it is less than the maximum)")
                        .defineInRange("nativeAluminumOreMin", 51, 0, 256);
                NATIVE_ALUMINUM_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Aluminum ore at (make sure it is greater than the minimum)")
                        .defineInRange("nativeAluminumOreMax", 128, 0, 256);
                NATIVE_ALUMINUM_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Aluminum ore vein")
                        .defineInRange("nativeAluminumOreSize", 12, 0, 256);
                NATIVE_ALUMINUM_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Aluminum ore veins to generate per chunk")
                        .defineInRange("nativeAluminumOreCount", 3, 0, 256);
            COMMON_BUILDER.pop();

            COMMON_BUILDER.comment("Native Gold Ore Settings").push("nativeGoldOre");
                NATIVE_GOLD_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate Native Gold ore at (make sure it is less than the maximum)")
                        .defineInRange("nativeGoldOreMin", 15, 0, 256);
                NATIVE_GOLD_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate Native Gold ore at (make sure it is greater than the minimum)")
                        .defineInRange("nativeGoldOreMax", 128, 0, 256);
                NATIVE_GOLD_ORE_SIZE = COMMON_BUILDER.comment("Size of Native Gold ore vein")
                        .defineInRange("nativeGoldOreSize", 10, 0, 256);
                NATIVE_GOLD_ORE_COUNT = COMMON_BUILDER.comment("Number of Native Gold ore veins to generate per chunk")
                        .defineInRange("nativeGoldOreCount", 4, 0, 256);
            COMMON_BUILDER.pop();

            COMMON_BUILDER.comment("xxx Ore Settings").push("yyyOre");
                XXX_ORE_MIN_HEIGHT = COMMON_BUILDER.comment("Minimum height to generate xxx ore at (make sure it is less than the maximum)")
                        .defineInRange("yyyOreMin", 1, 0, 256);
                XXX_ORE_MAX_HEIGHT = COMMON_BUILDER.comment("Maximum height to generate xxx ore at (make sure it is greater than the minimum)")
                        .defineInRange("yyyOreMax", 1, 0, 256);
                XXX_ORE_SIZE = COMMON_BUILDER.comment("Size of xxx ore vein")
                        .defineInRange("yyyOreSize", 1, 0, 256);
                XXX_ORE_COUNT = COMMON_BUILDER.comment("Number of xxx ore veins to generate per chunk")
                        .defineInRange("yyyOreCount", 1, 0, 256);
            COMMON_BUILDER.pop();



        COMMON_BUILDER.pop();
    }

    private static void setupSecondBlockConfig() {
        COMMON_BUILDER.comment("Alloy settings").push(SUBCATEGORY_ALLOYS);

        ALLOY_CORROSION = COMMON_BUILDER.comment("Enables the corrosion negative modifier for alloy tools (chance to consume an extra point of durability in water and rain)")
                .define("alloyCorrosion",true);

        ALLOY_HEAT = COMMON_BUILDER.comment("Enables the heat negative modifier for alloy tools (chance to consume an extra point of durability in hot environments and lava)")
                .define("alloyHeat",true);

        ALLOY_TOUGHNESS = COMMON_BUILDER.comment("Enables the toughness negative modifier for alloy tools (chance to consume an extra point of durability)")
                .define("alloyToughness",true);

        ALLOY_WEAR_MINING_AMT = COMMON_BUILDER.comment("Modifies the severity of the wear effect on mining speed (ex. 0.25 means mining speed will be reduced to 75% of the original value as durability is lost)")
                .defineInRange("alloyWearMiningAmount", 0.25D, 0.00D, 0.99D);

        ALLOY_WEAR_DAMAGE_AMT = COMMON_BUILDER.comment("Modifies the severity of the wear effect on damage (ex. 0.25 means damage will be reduced to 75% of the original value as durability is lost)")
                .defineInRange("alloyWearDamageAmount", 0.25D, 0.00D, 0.99D);

        AMALGAM_EXTRAS = COMMON_BUILDER.comment("Enables the disabled metals for amalgam alloy (Fe, Pt, W, Ta)")
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
