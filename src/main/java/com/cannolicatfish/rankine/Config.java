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

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue MANDATORY_AXE;
    public static ForgeConfigSpec.BooleanValue STARTING_BOOK;


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
        COMMON_BUILDER.comment("General Mod settings").push(SUBCATEGORY_EVENTS);

        MANDATORY_AXE = COMMON_BUILDER.comment("An axe is required to harvest logs")
                .define("axesOnly",false);

        STARTING_BOOK = COMMON_BUILDER.comment("Enables the Rankine Journal (a guide to the mod)")
                .define("startingBook",true);

        GLOBAL_BREAK_EXHAUSTION = COMMON_BUILDER.comment("Amount of additional exhaustion when breaking a block")
                .defineInRange("breakExhaustion", 0.00D, 0.00D, 1.00D);

        FLAT_BEDROCK = COMMON_BUILDER.comment("Generates with a flat bedrock layer (includes the Nether)")
                .define("flatBedrock",false);

        BEDROCK_LAYERS = COMMON_BUILDER.comment("Layers of bedrock to generate if flatBedrock is true")
                .defineInRange("bedrockLayers", 1, 0, 10);

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

        AMALGAM_EXTRAS = COMMON_BUILDER.comment("Enables the disabled elements for amalgam alloy (Fe, Pt, W, Ta)")
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
