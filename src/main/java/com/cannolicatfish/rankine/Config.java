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
    public static final String CATEGORY_POWER = "power";
    public static final String SUBCATEGORY_EVENTS = "event";
    public static final String SUBCATEGORY_PISTON_STEAM_ENGINE = "piston_steam_crusher";
    public static final String SUBCATEGORY_STEAM_TURBINE = "steam_turbine";

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
    static {

        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        setupFirstBlockConfig();
        COMMON_BUILDER.pop();

        /*
        COMMON_BUILDER.comment("Power settings").push(CATEGORY_POWER);

        setupSecondBlockConfig();
*/


        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupFirstBlockConfig() {
        COMMON_BUILDER.comment("General Mod settings").push(SUBCATEGORY_EVENTS);

        MANDATORY_AXE = COMMON_BUILDER.comment("An axe is required to harvest logs")
                .define("axesOnly",false);

        STARTING_BOOK = COMMON_BUILDER.comment("Enables the Rankine Journal (a guide to the mod)")
                .define("startingBook",true);

        COMMON_BUILDER.pop();
    }
/*
    private static void setupSecondBlockConfig() {
        COMMON_BUILDER.comment("General settings").push(SUBCATEGORY_PISTON_STEAM_ENGINE);

        PISTON_STEAM_ENGINE_MAXPOWER = COMMON_BUILDER.comment("Maximum power for the Piston Steam Engine generator")
                .defineInRange("maxPower", 100000, 0, Integer.MAX_VALUE);
        PISTON_STEAM_ENGINE_GENERATE = COMMON_BUILDER.comment("Power generation per diamond")
                .defineInRange("generate", 1000, 0, Integer.MAX_VALUE);
        PISTON_STEAM_ENGINE_SEND = COMMON_BUILDER.comment("Power generation to send per tick")
                .defineInRange("send", 100, 0, Integer.MAX_VALUE);
        PISTON_STEAM_ENGINE_TICKS = COMMON_BUILDER.comment("Ticks per diamond")
                .defineInRange("ticks", 20, 0, Integer.MAX_VALUE);

        COMMON_BUILDER.comment("General settings").push(SUBCATEGORY_STEAM_TURBINE);

        STEAM_TURBINE_MAXPOWER = COMMON_BUILDER.comment("Maximum power for the Steam Turbine generator")
                .defineInRange("maxPower", 100000, 0, Integer.MAX_VALUE);
        STEAM_TURBINE_GENERATE = COMMON_BUILDER.comment("Power generation per diamond")
                .defineInRange("generate", 1000, 0, Integer.MAX_VALUE);
        STEAM_TURBINE_SEND = COMMON_BUILDER.comment("Power generation to send per tick")
                .defineInRange("send", 100, 0, Integer.MAX_VALUE);
        STEAM_TURBINE_TICKS = COMMON_BUILDER.comment("Ticks per diamond")
                .defineInRange("ticks", 20, 0, Integer.MAX_VALUE);


        COMMON_BUILDER.pop();
    }
*/
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
