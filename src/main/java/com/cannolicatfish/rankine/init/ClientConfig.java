package com.cannolicatfish.rankine.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

public class ClientConfig {

    public static class General {
        public final ForgeConfigSpec.BooleanValue GRASS_TEMP;
        public General(ForgeConfigSpec.Builder b) {
            b.comment("Settings for general mechanics").push("general");

            GRASS_TEMP = b.comment("Enable grass coloring based of temperature. WIP")
                    .define("grassTempColor", true);
        }


    }

    public static final ForgeConfigSpec CLIENT_CONFIG;
    public static final General GENERAL;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        GENERAL = new ClientConfig.General(BUILDER);

        CLIENT_CONFIG = BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }

}
