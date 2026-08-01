package com.cannolicatfish.rankine.init;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.config.ModConfig;

public class ClientConfig {

    public static class General {
        public final ModConfigSpec.BooleanValue GRASS_TEMP;
        public final ModConfigSpec.BooleanValue GRASS_NOISE;
        public General(ModConfigSpec.Builder b) {
            b.comment("Settings for general mechanics").push("general");

            GRASS_TEMP = b.comment("Enable grass coloring based of temperature. WIP")
                    .define("grassTempColor", false);
            GRASS_NOISE = b.comment("Enable grass color variation within a biome.")
                    .define("grassColorNoise", true);
        }


    }

    public static final ModConfigSpec CLIENT_CONFIG;
    public static final General GENERAL;

    static {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        GENERAL = new ClientConfig.General(BUILDER);

        CLIENT_CONFIG = BUILDER.build();
    }

}
