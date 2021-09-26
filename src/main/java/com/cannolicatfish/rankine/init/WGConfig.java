package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class WGConfig {


    static Predicate<Object> ELEMENT_VALIDATOR = o -> o instanceof String;

    public static class BiomeGen {
        public final ForgeConfigSpec.ConfigValue<List<? extends List<Object>>> BIOME_SETTINGS;
        private static final List<List<Object>> biomeSettings = new ArrayList<>();

        public final ForgeConfigSpec.ConfigValue<List<? extends List<Object>>> ORE_SETTINGS;
        private static final List<List<Object>> oreSettings = new ArrayList<>();

        public BiomeGen(ForgeConfigSpec.Builder b) {
            biomeSettings.add(Arrays.asList(Biome.Category.NONE.getName(),
                    Arrays.asList("rankine:silt","rankine:grassy_silty_loam","rankine:silty_clay_loam","rankine:grassy_silty_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","rankine:diabase|3|rankine:diabase|0.00","minecraft:stone|1|minecraft:gold_ore|0.01"),
                    Arrays.asList("rankine:troctolite","rankine:gabbro","rankine:tholeiitic_basalt")));
            biomeSettings.add(Arrays.asList(Biome.Category.MUSHROOM.getName(),
                    Arrays.asList("rankine:humus","rankine:grassy_humus","rankine:clay_loam","rankine:grassy_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","rankine:pegmatite|3|rankine:petalite_ore|0.02","minecraft:stone|1|minecraft:coal_ore|0.01"),
                    Arrays.asList("rankine:troctolite","rankine:gabbro","rankine:tholeiitic_basalt","rankine:marlstone")));
            biomeSettings.add(Arrays.asList(Biome.Category.OCEAN.getName(),
                    Arrays.asList("rankine:silt","rankine:grassy_silty_loam","rankine:silty_clay_loam","rankine:grassy_silty_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","rankine:diabase|3|rankine:diabase|0.00","minecraft:stone|1|minecraft:gold_ore|0.01"),
                    Arrays.asList("rankine:troctolite","rankine:gabbro","rankine:tholeiitic_basalt")));
            biomeSettings.add(Arrays.asList(Biome.Category.BEACH.getName(),
                    Arrays.asList("rankine:loamy_sand","rankine:grassy_loamy_sand","rankine:sandy_clay","rankine:grassy_sandy_clay"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","minecraft:granite|3|rankine:malachite_ore|0.02","minecraft:stone|1|minecraft:redstone_ore|0.01"),
                    Arrays.asList("rankine:troctolite","rankine:gabbro","rankine:chalk","rankine:marlstone")));
            biomeSettings.add(Arrays.asList(Biome.Category.RIVER.getName(),
                    Arrays.asList("rankine:clay_loam","rankine:grassy_clay_loam","rankine:clay_loam","rankine:grassy_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02"),
                    Arrays.asList("rankine:black_dacite","rankine:phyllite","rankine:shale")));
            biomeSettings.add(Arrays.asList(Biome.Category.SAVANNA.getName(),
                    Arrays.asList("rankine:silty_loam","rankine:grassy_silty_loam","rankine:silty_clay_loam","rankine:grassy_silty_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","rankine:norite|3|rankine:norite|0.00","minecraft:stone|1|minecraft:redstone_ore|0.01"),
                    Arrays.asList("rankine:rhyolite","rankine:red_dacite","minecraft:andesite","rankine:siltstone")));
            biomeSettings.add(Arrays.asList(Biome.Category.MESA.getName(),
                    Arrays.asList("rankine:silty_loam","rankine:grassy_silty_loam","rankine:silty_clay_loam","rankine:grassy_silty_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","rankine:red_porphyry|3|rankine:porphyry_copper|0.02","minecraft:stone|1|minecraft:gold_ore|0.01"),
                    Arrays.asList("rankine:rhyolite","rankine:red_dacite","rankine:quartzite","rankine:arkose")));
            biomeSettings.add(Arrays.asList(Biome.Category.DESERT.getName(),
                    Arrays.asList("rankine:loamy_sand","rankine:grassy_loamy_sand","rankine:silt","rankine:silt"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","rankine:red_porphyry|3|rankine:porphyry_copper|0.02","minecraft:stone|1|minecraft:lapis_ore|0.01"),
                    Arrays.asList("rankine:rhyolite","rankine:red_dacite","rankine:quartzite","rankine:itacolumite")));
            biomeSettings.add(Arrays.asList(Biome.Category.EXTREME_HILLS.getName(),
                    Arrays.asList("rankine:sandy_loam","rankine:grassy_sandy_loam","rankine:loamy_sand","rankine:grassy_loamy_sand"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","minecraft:stone|1|minecraft:emerald_ore|0.01","rankine:shonkinite|1|rankine:shonkinite|0.00","minecraft:diorite|2|rankine:ilmenite_ore|0.01"),
                    Arrays.asList("rankine:pyroxenite","rankine:mariposite","rankine:gneiss","rankine:anorthosite","rankine:hornblende_andesite")));
            biomeSettings.add(Arrays.asList(Biome.Category.FOREST.getName(),
                    Arrays.asList("rankine:loam","rankine:grassy_loam","rankine:clay_loam","rankine:grassy_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","minecraft:stone|1|minecraft:iron_ore|0.01","rankine:gray_granite|2|rankine:cassiterite_ore|0.02","rankine:granodiorite|1|rankine:magnetite_ore|0.02"),
                    Arrays.asList("rankine:black_marble","rankine:black_dacite","rankine:anorthosite","rankine:limestone")));
            biomeSettings.add(Arrays.asList(Biome.Category.TAIGA.getName(),
                    Arrays.asList("rankine:sandy_loam","rankine:grassy_sandy_loam","rankine:sandy_clay_loam","rankine:grassy_sandy_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","minecraft:stone|1|minecraft:iron_ore|0.01","minecraft:diorite|1|rankine:ilmenite_ore|0.01","minecraft:granite|2|rankine:malachite_ore|0.02"),
                    Arrays.asList("rankine:white_marble","rankine:black_dacite","rankine:anorthosite","rankine:dolostone")));
            biomeSettings.add(Arrays.asList(Biome.Category.ICY.getName(),
                    Arrays.asList("rankine:sandy_loam","rankine:grassy_sandy_loam","rankine:sandy_clay_loam","rankine:grassy_sandy_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","rankine:pegmatite|1|rankine:petalite_ore|0.02","rankine:granodiorite|2|rankine:magnetite_ore|0.02","minecraft:stone|1|minecraft:coal_ore|0.01"),
                    Arrays.asList("rankine:pyroxenite","rankine:comendite","minecraft:andesite","rankine:chalk")));
            biomeSettings.add(Arrays.asList(Biome.Category.JUNGLE.getName(),
                    Arrays.asList("rankine:humus","rankine:grassy_humus","rankine:clay_loam","rankine:grassy_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","rankine:pegmatite|3|rankine:petalite_ore|0.02","minecraft:stone|1|minecraft:coal_ore|0.01"),
                    Arrays.asList("rankine:comendite","rankine:mica_schist","rankine:slate","rankine:mudstone")));
            biomeSettings.add(Arrays.asList(Biome.Category.PLAINS.getName(),
                    Arrays.asList("rankine:loamy_sand","rankine:grassy_loamy_sand","rankine:sandy_clay_loam","rankine:grassy_sandy_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","minecraft:stone|1|minecraft:iron_ore|0.01","rankine:gray_granite|2|rankine:cassiterite_ore|0.02","minecraft:diorite|1|rankine:ilmenite_ore|0.01"),
                    Arrays.asList("rankine:rhyolite","rankine:anorthosite","rankine:limestone","rankine:siltstone")));
            biomeSettings.add(Arrays.asList(Biome.Category.SWAMP.getName(),
                    Arrays.asList("rankine:loam","rankine:grassy_loam","rankine:clay_loam","rankine:grassy_clay_loam"),
                    Arrays.asList("minecraft:air|10|minecraft:air|1.0","rankine:kimberlite|2|rankine:diamond_ore|0.02","rankine:shonkinite|2|rankine:magnetite_ore|0.02","minecraft:stone|2|minecraft:coal_ore|0.01"),
                    Arrays.asList("rankine:mica_schist","rankine:phyllite","rankine:slate","rankine:mudstone")));
            biomeSettings.add(Arrays.asList("minecraft:soul_sand_valley",
                    Arrays.asList(),
                    Arrays.asList(),
                    Arrays.asList("rankine:eclogite","rankine:blueschist")));
            biomeSettings.add(Arrays.asList("minecraft:basalt_deltas",
                    Arrays.asList(),
                    Arrays.asList(),
                    Arrays.asList("rankine:serpentinite","rankine:gray_marble")));
            biomeSettings.add(Arrays.asList("minecraft:crimson_forest",
                    Arrays.asList(),
                    Arrays.asList(),
                    Arrays.asList("rankine:purple_porphyry","rankine:rose_marble")));
            biomeSettings.add(Arrays.asList("minecraft:warped_forest",
                    Arrays.asList(),
                    Arrays.asList(),
                    Arrays.asList("rankine:greenschist","rankine:peridotite")));


            b.comment("Biome Feature Settings").push("biomeGen");
            BIOME_SETTINGS = b.comment("Custom generators",
                        "Syntax: [[List1], [List2], [List3], ...]",
                        "[ListX]: [Biome, [Soil], [Intrusion], [Layers]]",
                        "Biome: biome resource location",
                        "[Soil]: Soil1, Soil2, Grass1, Grass2",
                        "Soil1: resource location of the block to replace dirt",
                        "Soil2: resource location of the block to replace dirt",
                        "Grass1: resource location of the block to replace grass",
                        "Grass2: resource location of the block to replace grass",
                        "[Intrusion]: [Block|Weight|Ore|Chance]",
                        "Block: resource location of block to generate as an intrusion (use \"minecraft:air\" to not generate an intrusion)",
                        "Weight: weight of block to generate as an intrusion",
                        "Ore: resource location of an ore to generate in an intrusion",
                        "Chance: chance for an ore block to replace an intrusion block",
                        "[Layers]: Rock1, Rock2, Rock3, ...",
                        "RockX: resource locations of the blocks to use in stone layers. From bottom to top.",
                        "custom_generators = [",
                        "   [\"savanna\", \"rankine:silty_loam\", \"rankine:grassy_silty_loam\", \"rankine:silty_clay_loam\", \"rankine:grassy_silty_clay_loam\"],")
                    .defineList("biomeSettings", biomeSettings, (p) -> p instanceof List);



            oreSettings.add(Arrays.asList("rankine:stibnite_ore", Arrays.asList("all"),Arrays.asList("all"), 50, 75, 4, 10, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_tin_ore", Arrays.asList("all"),Arrays.asList("all"), 50, 75, 4, 12, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_lead_ore", Arrays.asList("all"),Arrays.asList("all"), 50, 75, 4, 10, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_silver_ore", Arrays.asList("all"),Arrays.asList("all"), 50, 75, 4, 10, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_bismuth_ore", Arrays.asList("all"),Arrays.asList("all"), 50, 75, 4, 8, 1.0));
            oreSettings.add(Arrays.asList("rankine:lignite_ore", Arrays.asList("all"),Arrays.asList("all"), 50, 75, 15, 2, 1.0));
            oreSettings.add(Arrays.asList("rankine:stibnite_ore", Arrays.asList("all"),Arrays.asList("all"), 75, 90, 4, 10, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_tin_ore", Arrays.asList("all"),Arrays.asList("all"), 75, 90, 4, 12, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_lead_ore", Arrays.asList("all"),Arrays.asList("all"), 75, 90, 4, 10, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_silver_ore", Arrays.asList("all"),Arrays.asList("all"), 75, 90, 4, 10, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_bismuth_ore", Arrays.asList("all"),Arrays.asList("all"), 75, 90, 4, 8, 1.0));
            oreSettings.add(Arrays.asList("rankine:lignite_ore", Arrays.asList("all"),Arrays.asList("all"), 75, 128, 15, 4, 1.0));

            oreSettings.add(Arrays.asList("rankine:native_gold_ore", Arrays.asList("all"),Arrays.asList("all"), 30, 75, 5, 10, 1.0));
            oreSettings.add(Arrays.asList("rankine:pyrite_ore", Arrays.asList("all"),Arrays.asList("all"), 30, 75, 5, 6, 1.0));
            oreSettings.add(Arrays.asList("rankine:subbituminous_ore", Arrays.asList("all"),Arrays.asList("all"), 20, 60, 15, 4, 1.0));
            oreSettings.add(Arrays.asList("rankine:bituminous_ore", Arrays.asList("all"),Arrays.asList("all"), 5, 25, 15, 2, 1.0));


            oreSettings.add(Arrays.asList("rankine:bauxite_ore", Arrays.asList("jungle","swamp","plains","forest","taiga"),Arrays.asList("all"), 30, 70, 30, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:celestine_ore", Arrays.asList("jungle","swamp","plains","forest","taiga"),Arrays.asList("all"), 30, 70, 20, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:sphalerite_ore", Arrays.asList("desert","mesa","savanna","plains"),Arrays.asList("all"), 30, 70, 30, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:pentlandite_ore", Arrays.asList("ocean","beach","mushroom","desert"),Arrays.asList("all"), 5, 40, 30, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:chalcocite_ore", Arrays.asList("all"),Arrays.asList("all"), 30, 70, 30, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:galena_ore", Arrays.asList("all"),Arrays.asList("all"), 20, 50, 30, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:pyrolusite_ore", Arrays.asList("swamp","desert"),Arrays.asList("all"), 20, 50, 30, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:magnesite_ore", Arrays.asList("all"),Arrays.asList("all"), 5, 30, 30, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:chromite_ore", Arrays.asList("all"),Arrays.asList("all"), 5, 30, 30, 1, 0.3));
            oreSettings.add(Arrays.asList("rankine:plumbago_ore", Arrays.asList("all"),Arrays.asList("all"), 5, 30, 15, 1, 0.3));
            oreSettings.add(Arrays.asList("rankine:lazurite_ore", Arrays.asList("all"),Arrays.asList("all"), 5, 30, 20, 1, 0.3));
            oreSettings.add(Arrays.asList("rankine:acanthite_ore", Arrays.asList("all"),Arrays.asList("all"), 20, 50, 30, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:cryolite_ore", Arrays.asList("extreme_hills","taiga"),Arrays.asList("all"), 20, 50, 30, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:halite_ore", Arrays.asList("river","ocean","desert","mesa"),Arrays.asList("all"), 20, 50, 15, 1, 1.0));
            oreSettings.add(Arrays.asList("rankine:cinnabar_ore", Arrays.asList("all"),Arrays.asList("all"), 5, 20, 20, 1, 1.0));


            oreSettings.add(Arrays.asList("rankine:native_sulfur_ore", Arrays.asList("nether"),Arrays.asList("all"), 0, 127, 4, 20, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_arsenic_ore", Arrays.asList("nether"),Arrays.asList("all"), 0, 127, 4, 15, 1.0));
            oreSettings.add(Arrays.asList("rankine:anthracite_ore", Arrays.asList("nether"),Arrays.asList("all"), 0, 127, 15, 15, 1.0));
            oreSettings.add(Arrays.asList("rankine:cobaltite_ore", Arrays.asList("nether"),Arrays.asList("all"), 30, 90, 20, 1, 1.0));
            oreSettings.add(Arrays.asList("rankine:wolframite_ore", Arrays.asList("nether"),Arrays.asList("all"), 30, 90, 20, 1, 1.0));
            oreSettings.add(Arrays.asList("rankine:ilmenite_ore", Arrays.asList("nether"),Arrays.asList("all"), 30, 90, 20, 1, 1.0));
            oreSettings.add(Arrays.asList("rankine:sperrylite_ore", Arrays.asList("nether"),Arrays.asList("all"), 30, 90, 20, 1, 1.0));
            oreSettings.add(Arrays.asList("rankine:coltan_ore", Arrays.asList("nether"),Arrays.asList("all"), 0, 40, 20, 1, 1.0));
            oreSettings.add(Arrays.asList("rankine:monazite_ore", Arrays.asList("nether"),Arrays.asList("all"), 0, 40, 20, 1, 1.0));


            oreSettings.add(Arrays.asList("rankine:native_gallium_ore", Arrays.asList("the_end"),Arrays.asList("all"), 10, 60, 4, 15, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_indium_ore", Arrays.asList("the_end"),Arrays.asList("all"), 10, 60, 4, 15, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_selenium_ore", Arrays.asList("the_end"),Arrays.asList("all"), 10, 60, 4, 15, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_tellurium_ore", Arrays.asList("the_end"),Arrays.asList("all"), 10, 60, 4, 15, 1.0));
            oreSettings.add(Arrays.asList("rankine:molybdenite_ore", Arrays.asList("the_end"),Arrays.asList("all"), 10, 60, 10, 3, 1.0));
            oreSettings.add(Arrays.asList("rankine:uraninite_ore", Arrays.asList("the_end"),Arrays.asList("all"), 10, 60, 10, 3, 1.0));
            oreSettings.add(Arrays.asList("rankine:xenotime_ore", Arrays.asList("the_end"),Arrays.asList("all"), 10, 60, 10, 3, 1.0));
            oreSettings.add(Arrays.asList("rankine:greenockite_ore", Arrays.asList("the_end"),Arrays.asList("all"), 10, 60, 10, 3, 1.0));

            oreSettings.add(Arrays.asList("rankine:bog_iron", Arrays.asList("swamp"),Arrays.asList("all"), 40, 60, 10, 2, 1.0));
            oreSettings.add(Arrays.asList("rankine:ironstone", Arrays.asList("desert","savanna","mesa"),Arrays.asList("all"), 40, 60, 10, 2, 1.0));
            oreSettings.add(Arrays.asList("rankine:phosphorite", Arrays.asList("all"),Arrays.asList("all"), 40, 70, 12, 1, 1.0));

            oreSettings.add(Arrays.asList("rankine:basaltic_tuff", Arrays.asList("ocean","beach","mushroom","none"),Arrays.asList("all"), 20, 50, 60, 1, 1.0));
            oreSettings.add(Arrays.asList("rankine:andesitic_tuff", Arrays.asList("extreme_hills"),Arrays.asList("all"), 70, 100, 60, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:rhyolitic_tuff", Arrays.asList("savanna","mesa","desert","plains"),Arrays.asList("all"), 10, 30, 60, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:komatiitic_tuff", Arrays.asList("minecraft:nether_wastes"),Arrays.asList("all"), 10, 30, 60, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:kimberlitic_tuff", Arrays.asList("all"),Arrays.asList("all"), 0, 20, 40, 1, 0.2));


            ORE_SETTINGS = b.comment("Ore Settings",
                    "[OreGen]: [Ore, [Biomes], [Stones], Min Height, Max Height, Size, Count, Chance]",
                    "Ore: resource loacation of the block to generate",
                    "[Biomes]: String list of biome resource loacations to generate in. Use \"all\" to generate in all biomes. Can use biome categories by using the category name: ex \"ocean\".",
                    "Min Height: Int to generate",
                    "Ore: resource loacation of the block to generate"
            )
                    .defineList("oreSettings", oreSettings, (p) -> p instanceof List);
            b.pop();

        }
    }

    public static class Misc {
        public final ForgeConfigSpec.IntValue BEDROCK_LAYERS;
        public final ForgeConfigSpec.BooleanValue FLAT_BEDROCK;
        public final ForgeConfigSpec.BooleanValue VANILLA_ORES;
        public final ForgeConfigSpec.BooleanValue RANKINE_FAUNA;
        public final ForgeConfigSpec.BooleanValue FIRE_CLAY_GEN;
        public final ForgeConfigSpec.BooleanValue EVAPORITE_GEN;
        public final ForgeConfigSpec.BooleanValue ALLUVIUM_GEN;
        public final ForgeConfigSpec.BooleanValue TUFF_GEN;
        public final ForgeConfigSpec.BooleanValue SECRET_GEN;
        public final ForgeConfigSpec.BooleanValue DARK_GRAVEL;
        public final ForgeConfigSpec.BooleanValue END_METEORITE_GEN;
        public final ForgeConfigSpec.BooleanValue METEORITE_GEN;
        public final ForgeConfigSpec.DoubleValue BIG_METEORITE_CHANCE;
        public final ForgeConfigSpec.IntValue METEORITE_SIZE;
        public final ForgeConfigSpec.IntValue METEORITE_CHANCE;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> ORE_STONES;


        public Misc(ForgeConfigSpec.Builder b) {
            b.comment("Here are miscellaneous worldgen features").push("misc");
                ORE_STONES = b.comment("Textures of blocks for ores.")
                        .defineList("oreTextures", Arrays.asList("minecraft:stone", "minecraft:granite", "minecraft:diorite", "minecraft:andesite", "minecraft:sandstone|minecraft:sandstone_bottom", "minecraft:red_sandstone|minecraft:red_sandstone_bottom", "minecraft:netherrack", "minecraft:blackstone", "minecraft:basalt|minecraft:basalt_top", "minecraft:end_stone", "minecraft:obsidian", "rankine:pegmatite", "rankine:gray_granite", "rankine:rhyolite", "rankine:comendite", "rankine:granodiorite", "rankine:red_porphyry", "rankine:purple_porphyry", "rankine:hornblende_andesite", "rankine:black_dacite", "rankine:red_dacite", "rankine:tholeiitic_basalt", "rankine:diabase","rankine:gabbro", "rankine:anorthosite", "rankine:peridotite", "rankine:troctolite", "rankine:kimberlite", "rankine:komatiite","rankine:shonkinite", "rankine:norite", "rankine:pyroxenite", "rankine:rose_marble", "rankine:white_marble", "rankine:gray_marble", "rankine:black_marble", "rankine:gneiss", "rankine:mica_schist", "rankine:phyllite", "rankine:slate", "rankine:quartzite","rankine:mariposite","rankine:eclogite","rankine:limestone", "rankine:dolostone", "rankine:chalk", "rankine:shale", "rankine:siltstone", "rankine:itacolumite", "rankine:arkose", "rankine:mudstone","rankine:serpentinite","rankine:eclogite", "rankine:marlstone", "rankine:soul_sandstone", "rankine:blueschist", "rankine:greenschist", "rankine:meteorite", "rankine:frozen_meteorite", "rankine:enstatite"), WGConfig.ELEMENT_VALIDATOR);

                FLAT_BEDROCK = b.comment("Generates with a flat bedrock layer (includes the Nether)")
                        .define("flatBedrock",false);
                SECRET_GEN = b.comment("Figure it out")
                        .define("secretGen",true);
                BEDROCK_LAYERS = b.comment("Layers of bedrock to generate if flatBedrock is true")
                        .defineInRange("bedrockLayers", 1, 0, 5);
                VANILLA_ORES = b.comment("Disable vanilla ores in the overworld")
                        .define("disableVanillaOres",true);
                RANKINE_FAUNA = b.comment("Enable/Disable Project Rankine trees and berry bushes in world.")
                        .define("generateFauna",true);
                FIRE_CLAY_GEN = b.comment("Enables the generation of fire clay disks in dirt.")
                        .define("generateFireClay",true);
                EVAPORITE_GEN = b.comment("Enables the generation of evaporite disks.")
                        .define("generateEvaporite",true);
                ALLUVIUM_GEN = b.comment("Enables the generation of alluvium disks.")
                        .define("generateAlluvium",true);
                TUFF_GEN = b.comment("Enables the generation of tuff in their respective stone.")
                        .define("generateTuff",true);
                DARK_GRAVEL = b.comment("Replace gravel in the nether with dark gravel")
                        .define("darkGravel",true);
                END_METEORITE_GEN = b.comment("Enable to generate meteorites in the end.")
                        .define("endMeteoriteGen",true);
                METEORITE_GEN = b.comment("Enable to generate meteorites in the overworld.")
                        .define("meteoriteGen",true);
                METEORITE_SIZE = b.comment("Size parameter for meteorites. Higher number is bigger.")
                        .defineInRange("meteoriteSize", 1, 0, 10);
                METEORITE_CHANCE = b.comment("The chance a meteroite will spawn in the Overworld. Higher numbers increase rarity.")
                        .defineInRange("meteoriteChance", 100, 0, 1000);
                BIG_METEORITE_CHANCE = b.comment("The chance a meteroite will be big.")
                        .defineInRange("meteoriteBigChance", 0.25, 0.00, 1.00);
            b.pop();
        }
    }

    public static class Layers {
        public final ForgeConfigSpec.DoubleValue LAYER_WIDTH;
        public final ForgeConfigSpec.IntValue NOISE_SCALE;
        public final ForgeConfigSpec.IntValue NOISE_OFFSET;
        public final ForgeConfigSpec.IntValue OVERWORLD_STONE_LAYERS;
        public final ForgeConfigSpec.IntValue NETHER_STONE_LAYERS;
        public final ForgeConfigSpec.IntValue END_STONE_LAYERS;

        public Layers(ForgeConfigSpec.Builder b) {
            b.comment("Settings for stone layering").push("layers");
                OVERWORLD_STONE_LAYERS = b.comment("Determines the type of stone layer generation in the Overworld. 0 is disabled (vanilla stone and other features will generate). 1 will only replace vanilla stone with Rankine stones. 2 will replace any block in the tag #minecraft:base_stone_overworld. 3 will replace anny block in the tag #forge:stone.")
                        .defineInRange("overworldLayerGen", 1, 0, 3);
                NETHER_STONE_LAYERS = b.comment("Determines the type of stone layer generation in the Nether. 0 is disabled (netherrack and other features). 1 will only replace netherrack with Rankine stones. 2 will replace any block in the tag #minecraft:base_stone_nether.")
                        .defineInRange("netherdLayerGen", 1, 0, 2);
                END_STONE_LAYERS = b.comment("Determines the type of stone layer generation in the End. 0 is disabled (end stone and other features). 1 will only replace end stone with Rankine stones. 2 will replace any block in the tag #forge:base_stone_end.")
                        .defineInRange("endLayerGen", 1, 0, 2);

                LAYER_WIDTH = b.comment("Determines the vertical spread of stone layers. 1.0 is flat, closer to 0.0 is more extreme, 0.0 will crash.")
                        .defineInRange("layerWidth", 0.2D, 0.0D, 1.0D);
                NOISE_SCALE = b.comment("This determines how wide stone layers generate. Smaller values means it will look more like bedrock. Default value is 125.")
                        .defineInRange("noiseScale", 125, 1, 1000);
                NOISE_OFFSET = b.comment("This determines how close the overlap of noise layers is. A value of 0 means all layers are shaped identically.")
                        .defineInRange("noiseOffset", 256, 0, 16*64);
            b.pop();
        }
    }

    public static class Intrusions {
        public final ForgeConfigSpec.ConfigValue<List<String>> OVERWORLD_INTRUSION_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> NETHER_INTRUSION_LIST;

        public final ForgeConfigSpec.DoubleValue OVERWORLD_INTRUSION_SHRINK;
        public final ForgeConfigSpec.DoubleValue NETHER_INTRUSION_SHRINK;
        public final ForgeConfigSpec.DoubleValue NETHER_INTRUSION_CHANCE;
        public final ForgeConfigSpec.DoubleValue INTERSPINIFEX_CHANCE;
        public final ForgeConfigSpec.IntValue OVERWORLD_INTRUSION_RADIUS;
        public final ForgeConfigSpec.IntValue NETHER_INTRUSION_RADIUS;


        public Intrusions(ForgeConfigSpec.Builder b) {
            b.comment("Settings for intrusions").push("intrusions");
            OVERWORLD_INTRUSION_LIST = b.comment("List of blocks to be generated as intrusions. Each block is followed by its weight.")
                    .define("overworldIntrusionList", new ArrayList<>(Arrays.asList("rankine:kimberlite","1")));
            OVERWORLD_INTRUSION_RADIUS = b.comment("Size of an intrusion")
                    .defineInRange("overworldIntrusionRadius", 5, 0, 15);
            OVERWORLD_INTRUSION_SHRINK = b.comment("Chance for an overworld intrusion to shift as it goes up. Values closer to 0 result in straighter intrusions")
                    .defineInRange("overworldIntrusionShift", 0.15D, 0.0D, 1.0D);

            NETHER_INTRUSION_LIST = b.comment("List of blocks to be generated as intrusions. Each block is followed by its weight.")
                    .define("netherIntrusionList", new ArrayList<>(Arrays.asList("rankine:pumice","1","rankine:scoria","1","rankine:komatiite","1")));
            NETHER_INTRUSION_RADIUS = b.comment("Maximum radius of an intrusion")
                    .defineInRange("netherIntrusionRadius", 5, 0, 15);
            NETHER_INTRUSION_SHRINK = b.comment("Chance for an nether intrusion to shift as it goes up. Values closer to 0 result in straighter intrusions")
                    .defineInRange("netherIntrusionShift", 0.15D, 0.0D, 1.0D);
            NETHER_INTRUSION_CHANCE = b.comment("Chance for nether intrusions to spawn")
                    .defineInRange("netherIntrusionChance", 0.10D, 0.00D, 1.00D);
            INTERSPINIFEX_CHANCE = b.comment("Chance for an komatiite intrusion block to be replaced by an interspinifex ore")
                    .defineInRange("interspinifexOreChance", 0.03D, 0.00D, 1.00D);
            b.pop();
        }
    }



    public static class Ores {

        public final ForgeConfigSpec.IntValue NATIVE_TIN_ORE_HL;
        public final ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_HL;
        public final ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_HL;
        public final ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_HL;
        public final ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_HL;
        public final ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_HL;
        public final ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_HL;
        public final ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_HL;
        public final ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_HL;
        public final ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_HL;
        public final ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_HL;
        public final ForgeConfigSpec.IntValue MALACHITE_ORE_HL;
        public final ForgeConfigSpec.IntValue CHALCOCITE_ORE_HL;
        public final ForgeConfigSpec.IntValue CASSITERITE_ORE_HL;
        public final ForgeConfigSpec.IntValue BAUXITE_ORE_HL;
        public final ForgeConfigSpec.IntValue SPHALERITE_ORE_HL;
        public final ForgeConfigSpec.IntValue CINNABAR_ORE_HL;
        public final ForgeConfigSpec.IntValue MAGNETITE_ORE_HL;
        public final ForgeConfigSpec.IntValue PENTLANDITE_ORE_HL;
        public final ForgeConfigSpec.IntValue MAGNESITE_ORE_HL;
        public final ForgeConfigSpec.IntValue GALENA_ORE_HL;
        public final ForgeConfigSpec.IntValue BISMUTHINITE_ORE_HL;
        public final ForgeConfigSpec.IntValue ACANTHITE_ORE_HL;
        public final ForgeConfigSpec.IntValue PYROLUSITE_ORE_HL;
        public final ForgeConfigSpec.IntValue CHROMITE_ORE_HL;
        public final ForgeConfigSpec.IntValue MOLYBDENITE_ORE_HL;
        public final ForgeConfigSpec.IntValue ILMENITE_ORE_HL;
        public final ForgeConfigSpec.IntValue WOLFRAMITE_ORE_HL;
        public final ForgeConfigSpec.IntValue RHENIITE_ORE_HL;
        public final ForgeConfigSpec.IntValue PLUMBAGO_ORE_HL;
        public final ForgeConfigSpec.IntValue SPERRYLITE_ORE_HL;
        public final ForgeConfigSpec.IntValue LIGNITE_ORE_HL;
        public final ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_HL;
        public final ForgeConfigSpec.IntValue BITUMINOUS_ORE_HL;
        public final ForgeConfigSpec.IntValue ANTHRACITE_ORE_HL;
        public final ForgeConfigSpec.IntValue LAZURITE_ORE_HL;
        public final ForgeConfigSpec.IntValue DIAMOND_ORE_HL;
        public final ForgeConfigSpec.IntValue GREENOCKITE_ORE_HL;
        public final ForgeConfigSpec.IntValue EMERALD_ORE_HL;
        public final ForgeConfigSpec.IntValue QUARTZ_ORE_HL;
        public final ForgeConfigSpec.IntValue URANINITE_ORE_HL;
        public final ForgeConfigSpec.IntValue STIBNITE_ORE_HL;
        public final ForgeConfigSpec.IntValue XENOTIME_ORE_HL;
        public final ForgeConfigSpec.IntValue COLTAN_ORE_HL;
        public final ForgeConfigSpec.IntValue HALITE_ORE_HL;
        public final ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_HL;
        public final ForgeConfigSpec.IntValue PETALITE_ORE_HL;
        public final ForgeConfigSpec.IntValue COBALTITE_ORE_HL;
        public final ForgeConfigSpec.IntValue CRYOLITE_ORE_HL;
        public final ForgeConfigSpec.IntValue PYRITE_ORE_HL;
        public final ForgeConfigSpec.IntValue CELESTINE_ORE_HL;
        public final ForgeConfigSpec.IntValue MONAZITE_ORE_HL;
        public final ForgeConfigSpec.IntValue KAMACITE_ORE_HL;
        public final ForgeConfigSpec.IntValue ANTITAENITE_ORE_HL;
        public final ForgeConfigSpec.IntValue TAENITE_ORE_HL;
        public final ForgeConfigSpec.IntValue TETRATAENITE_ORE_HL;
        public final ForgeConfigSpec.IntValue LONSDALEITE_ORE_HL;

        public Ores(ForgeConfigSpec.Builder b) {
            b.comment("Oregen Settings").push("oregen");
                NATIVE_TIN_ORE_HL = b.comment("Harvest Level of Native Tin Ore.")
                        .defineInRange("nativeTinOreHL", 0, 0, 4);
                NATIVE_GOLD_ORE_HL = b.comment("Harvest Level of Native Gold Ore.")
                        .defineInRange("nativeGoldOreHL", 0, 0, 4);
                NATIVE_LEAD_ORE_HL = b.comment("Harvest Level of Native Lead Ore.")
                        .defineInRange("nativeLeadOreHL", 1, 0, 4);
                NATIVE_SILVER_ORE_HL = b.comment("Harvest Level of Native Silver Ore.")
                        .defineInRange("nativeSilverOreHL", 0, 0, 4);
                NATIVE_ARSENIC_ORE_HL = b.comment("Harvest Level of Native Arsenic Ore.")
                        .defineInRange("nativeArsenicOreHL", 1, 0, 4);
                NATIVE_BISMUTH_ORE_HL = b.comment("Harvest Level of Native Bismuth Ore.")
                        .defineInRange("nativeBismuthOreHL", 1, 0, 4);
                NATIVE_SULFUR_ORE_HL = b.comment("Harvest Level of Native Sulfur Ore.")
                        .defineInRange("nativeSulfurOreHL", 1, 0, 4);
                NATIVE_GALLIUM_ORE_HL = b.comment("Harvest Level of Native Gallium Ore.")
                        .defineInRange("nativeGalliumOreHL", 1, 0, 4);
                NATIVE_INDIUM_ORE_HL = b.comment("Harvest Level of Native Indium Ore.")
                        .defineInRange("nativeIndiumOreHL", 1, 0, 4);
                NATIVE_TELLURIUM_ORE_HL = b.comment("Harvest Level of Native Tellurium Ore.")
                        .defineInRange("nativeTelluriumOreHL", 1, 0, 4);
                NATIVE_SELENIUM_ORE_HL = b.comment("Harvest Level of Native Selenium Ore.")
                        .defineInRange("nativeSeleniumOreHL", 1, 0, 4);
                MALACHITE_ORE_HL = b.comment("Harvest Level of Malachite Ore.")
                        .defineInRange("malachiteOreHL", 3, 0, 4);
                CHALCOCITE_ORE_HL = b.comment("Harvest Level of Chalcocite Ore.")
                        .defineInRange("chalcociteOreHL", 2, 0, 4);
                CASSITERITE_ORE_HL = b.comment("Harvest Level of Cassiterite Ore.")
                        .defineInRange("cassiteriteOreHL", 2, 0, 4);
                BAUXITE_ORE_HL = b.comment("Harvest Level of Bauxite Ore.")
                        .defineInRange("bauxiteOreHL", 2, 0, 4);
                SPHALERITE_ORE_HL = b.comment("Harvest Level of Sphalerite Ore.")
                        .defineInRange("sphaleriteOreHL", 2, 0, 4);
                CINNABAR_ORE_HL = b.comment("Harvest Level of Cinnabar Ore.")
                        .defineInRange("cinnabarOreHL", 2, 0, 4);
                MAGNETITE_ORE_HL = b.comment("Harvest Level of Magnetite Ore.")
                        .defineInRange("magnetiteOreHL", 4, 0, 4);
                PENTLANDITE_ORE_HL = b.comment("Harvest Level of Pentlandite Ore.")
                        .defineInRange("pentlanditeOreHL", 3, 0, 4);
                MAGNESITE_ORE_HL = b.comment("Harvest Level of Magnesite Ore.")
                        .defineInRange("magnesiteOreHL", 2, 0, 4);
                GALENA_ORE_HL = b.comment("Harvest Level of Galena Ore.")
                        .defineInRange("galenaOreHL", 2, 0, 4);
                BISMUTHINITE_ORE_HL = b.comment("Harvest Level of Bismuthinite Ore.")
                        .defineInRange("bismuthiniteOreHL", 3, 0, 4);
                ACANTHITE_ORE_HL = b.comment("Harvest Level of Acanthite Ore.")
                        .defineInRange("acanthiteOreHL", 3, 0, 4);
                PYROLUSITE_ORE_HL = b.comment("Harvest Level of Pyrolusite Ore.")
                        .defineInRange("pyrolusiteOreHL", 3, 0, 4);
                CHROMITE_ORE_HL = b.comment("Harvest Level of Chromite Ore.")
                        .defineInRange("chromiteOreHL", 3, 0, 4);
                MOLYBDENITE_ORE_HL = b.comment("Harvest Level of Molybdenite Ore.")
                        .defineInRange("molybdeniteOreHL", 4, 0, 4);
                ILMENITE_ORE_HL = b.comment("Harvest Level of Ilmenite Ore.")
                        .defineInRange("ilmeniteOreHL", 3, 0, 4);
                WOLFRAMITE_ORE_HL = b.comment("Harvest Level of Wolframite Ore.")
                        .defineInRange("wolframiteOreHL", 4, 0, 4);
                RHENIITE_ORE_HL = b.comment("Harvest Level of Rheniite Ore.")
                        .defineInRange("rheniiteOreHL", 4, 0, 4);
                PLUMBAGO_ORE_HL = b.comment("Harvest Level of Plumbago Ore.")
                        .defineInRange("plumbagoOreHL", 3, 0, 4);
                SPERRYLITE_ORE_HL = b.comment("Harvest Level of Sperrylite Ore.")
                        .defineInRange("sperryliteOreHL", 4, 0, 4);
                LIGNITE_ORE_HL = b.comment("Harvest Level of Lignite Ore.")
                        .defineInRange("ligniteOreHL", 1, 0, 4);
                SUBBITUMINOUS_ORE_HL = b.comment("Harvest Level of Subbituminous Ore.")
                        .defineInRange("subbituminousOreHL", 2, 0, 4);
                BITUMINOUS_ORE_HL = b.comment("Harvest Level of Bituminous Ore.")
                        .defineInRange("bituminousOreHL", 3, 0, 4);
                ANTHRACITE_ORE_HL = b.comment("Harvest Level of Anthracite Ore.")
                        .defineInRange("anthraciteOreHL", 4, 0, 4);
                LAZURITE_ORE_HL = b.comment("Harvest Level of Lazurite Ore.")
                        .defineInRange("lazuriteOreHL", 2, 0, 4);
                DIAMOND_ORE_HL = b.comment("Harvest Level of Diamond Ore.")
                        .defineInRange("diamondOreHL", 3, 0, 4);
                GREENOCKITE_ORE_HL = b.comment("Harvest Level of Greenockite Ore.")
                        .defineInRange("greenockiteOreHL", 4, 0, 4);
                EMERALD_ORE_HL = b.comment("Harvest Level of Emerald Ore.")
                        .defineInRange("emeraldOreHL", 3, 0, 4);
                QUARTZ_ORE_HL = b.comment("Harvest Level of Quartz Ore.")
                        .defineInRange("quartzOreHL", 2, 0, 4);
                URANINITE_ORE_HL = b.comment("Harvest Level of Uraninite Ore.")
                        .defineInRange("uraniniteOreHL", 4, 0, 4);
                STIBNITE_ORE_HL = b.comment("Harvest Level of Stibnite Ore.")
                        .defineInRange("stibniteOreHL", 0, 0, 4);
                XENOTIME_ORE_HL = b.comment("Harvest Level of Xenotime Ore.")
                        .defineInRange("xenotimeOreHL", 4, 0, 4);
                HALITE_ORE_HL = b.comment("Harvest Level of Halite Ore.")
                        .defineInRange("haliteOreHL", 0, 0, 4);
                INTERSPINIFEX_ORE_HL = b.comment("Harvest Level of Interspinifex Ore.")
                        .defineInRange("interspinifexOreHL", 3, 0, 4);
                PETALITE_ORE_HL = b.comment("Harvest Level of Petalite Ore.")
                        .defineInRange("petaliteOreHL", 3, 0, 4);
                COBALTITE_ORE_HL = b.comment("Harvest Level of Cobaltite Ore.")
                        .defineInRange("cobaltiteOreHL", 4, 0, 4);
                CRYOLITE_ORE_HL = b.comment("Harvest Level of Cryolite Ore.")
                        .defineInRange("cryoliteOreHL", 4, 0, 4);
                COLTAN_ORE_HL = b.comment("Harvest Level of Coltan Ore.")
                        .defineInRange("coltanOreHL", 4, 0, 4);
                PYRITE_ORE_HL = b.comment("Harvest Level of Pyrite Ore.")
                        .defineInRange("pyriteOreHL", 2, 0, 4);
                CELESTINE_ORE_HL = b.comment("Harvest Level of Celestine Ore.")
                        .defineInRange("celestineOreHL", 3, 0, 4);
                MONAZITE_ORE_HL = b.comment("Harvest Level of Monazite Ore.")
                        .defineInRange("monaziteOreHL", 4, 0, 4);
                KAMACITE_ORE_HL = b.comment("Harvest Level of Kamacite Ore.")
                        .defineInRange("kamaciteOreHL", 2, 0, 4);
                ANTITAENITE_ORE_HL = b.comment("Harvest Level of Antitaenite Ore.")
                        .defineInRange("antitaeniteOreHL", 2, 0, 4);
                TAENITE_ORE_HL = b.comment("Harvest Level of Taenite Ore.")
                        .defineInRange("taeniteOreHL", 2, 0, 4);
                TETRATAENITE_ORE_HL = b.comment("Harvest Level of Tetrataenite Ore.")
                        .defineInRange("tetrataeniteOreHL", 2, 0, 4);
                LONSDALEITE_ORE_HL = b.comment("Harvest Level of Lonsdaleite Ore.")
                        .defineInRange("lonsdaleiteOreHL", 5, 0, 5);
            b.pop();
        }
    }







    public static final ForgeConfigSpec COMMON_WGCONFIG;
    public static final Misc MISC;
    public static final BiomeGen BIOME_GEN;
    public static final Layers LAYERS;
    public static final Intrusions INTRUSIONS;
    public static final Ores ORES;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        MISC = new Misc(BUILDER);
        BIOME_GEN = new BiomeGen(BUILDER);
        LAYERS = new Layers(BUILDER);
        INTRUSIONS = new Intrusions(BUILDER);
        ORES = new Ores(BUILDER);

        COMMON_WGCONFIG = BUILDER.build();
    }


}
