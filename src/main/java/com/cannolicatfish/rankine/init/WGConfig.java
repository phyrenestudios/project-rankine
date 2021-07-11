package com.cannolicatfish.rankine.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber
public class WGConfig {

    public static class Misc {
        public final ForgeConfigSpec.IntValue BEDROCK_LAYERS;
        public final ForgeConfigSpec.BooleanValue FLAT_BEDROCK;
        public final ForgeConfigSpec.BooleanValue VANILLA_ORES;
        public final ForgeConfigSpec.BooleanValue RANKINE_FAUNA;
        public final ForgeConfigSpec.BooleanValue FIRE_CLAY_GEN;
        public final ForgeConfigSpec.BooleanValue MUD_GEN;
        public final ForgeConfigSpec.BooleanValue EVAPORITE_GEN;
        public final ForgeConfigSpec.BooleanValue ALLUVIUM_GEN;
        public final ForgeConfigSpec.BooleanValue TUFF_GEN;
        public final ForgeConfigSpec.BooleanValue METEORITE_GEN;
        public final ForgeConfigSpec.IntValue METEORITE_SIZE;
        public final ForgeConfigSpec.IntValue METEORITE_CHANCE;
        public final ForgeConfigSpec.DoubleValue SILL_CHANCE;
        public final ForgeConfigSpec.BooleanValue SILL_IRONSTONE_GEN;
        public final ForgeConfigSpec.BooleanValue SILL_PHOSPHORITE_GEN;

        public final ForgeConfigSpec.ConfigValue<List<String>> ORE_STONES;


        public Misc(ForgeConfigSpec.Builder b) {
            b.comment("Here are miscellaneous worldgen features").push("misc");
                ORE_STONES = b.comment("Textures of blocks for ores.")
                        .define("oreStones", Arrays.asList("minecraft:stone","minecraft:granite","minecraft:diorite","minecraft:andesite","minecraft:sandstone","minecraft:red_sandstone","minecraft:netherrack","minecraft:blackstone","minecraft:basalt_side","minecraft:end_stone","minecraft:obsidian","rankine:gray_granite","rankine:granodiorite","rankine:hornblende_andesite","rankine:tholeiitic_basalt","rankine:pyroxene_gabbro","rankine:anorthosite","rankine:rhyolite","rankine:comendite","rankine:red_dacite","rankine:black_dacite","rankine:red_porphyry","rankine:purple_porphyry","rankine:pegmatite","rankine:peridotite","rankine:troctolite","rankine:kimberlite","rankine:komatiite","rankine:pumice","rankine:scoria","rankine:white_marble","rankine:black_marble","rankine:rose_marble","rankine:gray_marble","rankine:gneiss","rankine:mica_schist","rankine:slate","rankine:phyllite","rankine:quartzite","rankine:mariposite","rankine:skarn","rankine:ringwoodite","rankine:wadsleyite","rankine:bridgmanite","rankine:ferropericlase","rankine:perovskite","rankine:tufa_limestone","rankine:dolostone","rankine:chalk","rankine:carbonaceous_shale","rankine:siltstone","rankine:quartz_sandstone","rankine:arkose_sandstone","rankine:mudstone","rankine:breccia","rankine:meteorite","rankine:enstatite"));

                FLAT_BEDROCK = b.comment("Generates with a flat bedrock layer (includes the Nether)")
                        .define("flatBedrock",false);
                BEDROCK_LAYERS = b.comment("Layers of bedrock to generate if flatBedrock is true")
                        .defineInRange("bedrockLayers", 1, 0, 5);
                VANILLA_ORES = b.comment("Disable vanilla ores in the overworld")
                        .define("disableVanillaOres",true);
                RANKINE_FAUNA = b.comment("Enable/Disable Project Rankine trees and berry bushes in world.")
                        .define("generateFauna",true);
                FIRE_CLAY_GEN = b.comment("Enables the generation of fire clay disks in dirt.")
                        .define("generateFireClay",true);
                MUD_GEN = b.comment("Enables the generation of mud in dirt.")
                        .define("generateMud",true);
                EVAPORITE_GEN = b.comment("Enables the generation of evaporite disks.")
                        .define("generateEvaporite",true);
                ALLUVIUM_GEN = b.comment("Enables the generation of alluvium disks.")
                        .define("generateAlluvium",true);
                TUFF_GEN = b.comment("Enables the generation of tuff in their respective stone.")
                        .define("generateTuff",true);
                METEORITE_GEN = b.comment("Enable to generate meteorites in the overworld.")
                        .define("meteoriteGen",true);
                METEORITE_SIZE = b.comment("Size parameter for meteorites. Higher number is bigger.")
                        .defineInRange("meteoriteSize", 1, 0, 10);
                METEORITE_CHANCE = b.comment("The chance a meteroite will spawn in the Overworld. Higher numbers increase rarity.")
                        .defineInRange("meteoriteChance", 150, 0, 1000);
                SILL_CHANCE = b.comment("Chance per chunk to generate a sill of ironstone or phosphorite.")
                        .defineInRange("sillChance", 0.2D, 0.0D, 1.0D);
                SILL_IRONSTONE_GEN = b.comment("Enables the generation of ironstone sills.")
                        .define("generateIronstone",true);
                SILL_PHOSPHORITE_GEN = b.comment("Enables the generation of phosphorite sills.")
                        .define("generatePhosphorite",true);
            b.pop();
        }
    }

    public static class Layers {
        public final ForgeConfigSpec.ConfigValue<List<String>> OCEAN_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> MUSHROOM_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> DESERT_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> MESA_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> SAVANNA_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> JUNGLE_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> PLAINS_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> SWAMP_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> FOREST_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> ICY_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> MOUNTAIN_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> RIVER_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> BEACH_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> OVERWORLD_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> NETHER_STONE_LIST;
        public final ForgeConfigSpec.ConfigValue<List<String>> END_STONE_LIST;

        public final ForgeConfigSpec.IntValue OCEAN_HEIGHT;
        public final ForgeConfigSpec.IntValue MUSHROOM_HEIGHT;
        public final ForgeConfigSpec.IntValue DESERT_HEIGHT;
        public final ForgeConfigSpec.IntValue MESA_HEIGHT;
        public final ForgeConfigSpec.IntValue SAVANNA_HEIGHT;
        public final ForgeConfigSpec.IntValue JUNGLE_HEIGHT;
        public final ForgeConfigSpec.IntValue PLAINS_HEIGHT;
        public final ForgeConfigSpec.IntValue SWAMP_HEIGHT;
        public final ForgeConfigSpec.IntValue FOREST_HEIGHT;
        public final ForgeConfigSpec.IntValue ICY_HEIGHT;
        public final ForgeConfigSpec.IntValue MOUNTAIN_HEIGHT;
        public final ForgeConfigSpec.IntValue RIVER_HEIGHT;
        public final ForgeConfigSpec.IntValue BEACH_HEIGHT;
        public final ForgeConfigSpec.IntValue OVERWORLD_HEIGHT;
        public final ForgeConfigSpec.IntValue NETHER_HEIGHT;
        public final ForgeConfigSpec.IntValue END_HEIGHT;

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

                OCEAN_STONE_LIST = b.comment("Blocks to generate in Ocean Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("oceanBlockList", new ArrayList<>(Arrays.asList("rankine:troctolite", "rankine:tholeiitic_basalt", "rankine:chalk", "rankine:slate", "rankine:breccia")));
                OCEAN_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("oceanHeight", 65, 0, 256);
                MUSHROOM_STONE_LIST = b.comment("Blocks to generate in Mushroom Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("mushroomBlockList", new ArrayList<>(Arrays.asList("rankine:troctolite", "rankine:tholeiitic_basalt", "rankine:chalk", "rankine:slate", "rankine:skarn", "rankine:anorthosite")));
                MUSHROOM_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("mushroomHeight", 60, 0, 256);
                DESERT_STONE_LIST = b.comment("Blocks to generate in Desert Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("desertBlockLiisst", new ArrayList<>(Arrays.asList("rankine:troctolite", "rankine:rhyolite", "rankine:gneiss", "rankine:red_dacite", "rankine:phyllite", "rankine:quartzite", "rankine:tufa_limestone", "rankine:quartz_sandstone")));
                DESERT_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("desertHeight", 65, 0, 256);
                MESA_STONE_LIST = b.comment("Blocks to generate in Mesa Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("mesaBlockList", new ArrayList<>(Arrays.asList("rankine:troctolite", "rankine:rhyolite", "rankine:gneiss", "rankine:red_dacite", "rankine:phyllite", "rankine:quartzite", "rankine:tufa_limestone", "rankine:arkose_sandstone")));
                MESA_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("mesaHeight", 65, 0, 256);
                MOUNTAIN_STONE_LIST = b.comment("Blocks to generate in Mountain Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("mountainBlockList", new ArrayList<>(Arrays.asList("rankine:peridotite", "rankine:comendite", "rankine:gneiss", "rankine:red_dacite", "rankine:phyllite", "rankine:mariposite", "rankine:dolostone", "minecraft:andesite", "rankine:hornblende_andesite")));
                MOUNTAIN_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("mountainHeight", 65, 0, 256);
                JUNGLE_STONE_LIST = b.comment("Blocks to generate in Jungle Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("jungleBlockList", new ArrayList<>(Arrays.asList("rankine:peridotite", "rankine:comendite", "rankine:mica_schist", "rankine:red_dacite", "rankine:slate", "rankine:black_marble", "rankine:dolostone", "rankine:anorthosite")));
                JUNGLE_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("jungleHeight", 65, 0, 256);
                BEACH_STONE_LIST = b.comment("Blocks to generate in Beach Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("beachBlockList", new ArrayList<>(Arrays.asList("rankine:peridotite", "rankine:comendite", "rankine:mica_schist", "rankine:black_dacite", "rankine:phyllite", "rankine:white_marble", "rankine:dolostone", "rankine:anorthosite")));
                BEACH_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("beachHeight", 65, 0, 256);
                FOREST_STONE_LIST = b.comment("Blocks to generate in Forest Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("forestBlockList", new ArrayList<>(Arrays.asList("rankine:peridotite", "rankine:comendite", "rankine:gneiss", "rankine:black_dacite", "rankine:phyllite", "rankine:black_marble", "rankine:dolostone", "rankine:anorthosite")));
                FOREST_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("forestHeight", 65, 0, 256);
                RIVER_STONE_LIST = b.comment("Blocks to generate in River Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("riverBlockList", new ArrayList<>(Arrays.asList("rankine:peridotite", "rankine:comendite", "rankine:mica_schist", "rankine:black_dacite", "rankine:phyllite", "rankine:white_marble", "rankine:tufa_limestone", "rankine:anorthosite")));
                RIVER_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("riverHeight", 65, 0, 256);
                PLAINS_STONE_LIST = b.comment("Blocks to generate in Plains Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("plainsBlockList", new ArrayList<>(Arrays.asList("rankine:peridotite", "rankine:comendite", "rankine:mica_schist", "rankine:black_dacite", "rankine:phyllite", "rankine:white_marble", "rankine:dolostone", "rankine:carbonaceous_shale")));
                PLAINS_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("plainsHeight", 65, 0, 256);
                ICY_STONE_LIST = b.comment("Blocks to generate in Icy Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("icyBlockList", new ArrayList<>(Arrays.asList("rankine:peridotite", "rankine:comendite", "rankine:mica_schist", "rankine:black_dacite", "rankine:phyllite", "rankine:black_marble", "rankine:dolostone", "rankine:carbonaceous_shale")));
                ICY_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("icyHeight", 65, 0, 256);
                SWAMP_STONE_LIST = b.comment("Blocks to generate in Swamp Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("swampBlockList", new ArrayList<>(Arrays.asList("rankine:peridotite", "rankine:comendite", "rankine:mica_schist", "rankine:red_dacite", "rankine:slate", "rankine:black_marble", "rankine:dolostone", "rankine:mudstone")));
                SWAMP_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("swampHeight", 65, 0, 256);
                SAVANNA_STONE_LIST = b.comment("Blocks to generate in Savanna Biomes. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("savannaBlockList", new ArrayList<>(Arrays.asList("rankine:troctolite", "rankine:rhyolite", "rankine:gneiss", "rankine:red_dacite", "rankine:phyllite", "rankine:white_marble", "rankine:tufa_limestone", "rankine:siltstone")));
                SAVANNA_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("savannaHeight", 50, 0, 256);
                OVERWORLD_STONE_LIST = b.comment("Blocks to generate in any other biome types not listed. Layers generate from bottom to top. Leave empty to leave it as vanilla stone.")
                        .define("defaultBlockList", new ArrayList<>(Arrays.asList("rankine:peridotite", "rankine:comendite", "rankine:mica_schist", "rankine:black_dacite", "rankine:phyllite", "rankine:white_marble", "rankine:dolostone", "rankine:anorthosite")));
                OVERWORLD_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("overworldHeight", 65, 0, 256);
                NETHER_STONE_LIST = b.comment("Blocks to generate in Nether layering. Layers generate from bottom to top. Leave empty to leave it as default gen.")
                        .define("netherStoneList", new ArrayList<>(Arrays.asList("rankine:black_sand","minecraft:blackstone","rankine:purple_porphyry","minecraft:netherrack","minecraft:netherrack","minecraft:netherrack","minecraft:netherrack","minecraft:netherrack","minecraft:netherrack","minecraft:netherrack","minecraft:netherrack","minecraft:netherrack","minecraft:netherrack","minecraft:netherrack","minecraft:netherrack","rankine:red_porphyry")));
                NETHER_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("netherHeight", 127, 0, 256);
                END_STONE_LIST = b.comment("Blocks to generate in End layering. Layers generate from bottom to top. Leave empty to leave it as default gen.")
                            .define("endStoneList", new ArrayList<>(Arrays.asList("rankine:enstatite","rankine:enstatite","rankine:enstatite","rankine:meteorite","rankine:meteorite","rankine:skarn","minecraft:end_stone")));
                END_HEIGHT = b.comment("Sets the average height of a biome type. The thickness of a layer is biome height / number of layers. Anything above this height will generally generate as the last layer.")
                        .defineInRange("endHeight", 60, 0, 256);

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

        public final ForgeConfigSpec.DoubleValue OVERWORLD_INTRUSION_CHANCE;
        public final ForgeConfigSpec.DoubleValue OVERWORLD_INTRUSION_SHRINK;
        public final ForgeConfigSpec.DoubleValue DIAMON_CHANCE;
        public final ForgeConfigSpec.DoubleValue ILMENITE_CHANCE;
        public final ForgeConfigSpec.DoubleValue PETALITE_CHANCE;
        public final ForgeConfigSpec.DoubleValue NETHER_INTRUSION_SHRINK;
        public final ForgeConfigSpec.DoubleValue NETHER_INTRUSION_CHANCE;
        public final ForgeConfigSpec.DoubleValue INTERSPINIFEX_CHANCE;
        public final ForgeConfigSpec.IntValue OVERWORLD_INTRUSION_RADIUS;
        public final ForgeConfigSpec.IntValue NETHER_INTRUSION_RADIUS;


        public Intrusions(ForgeConfigSpec.Builder b) {
            b.comment("Settings for intrusions").push("intrusions");
            OVERWORLD_INTRUSION_LIST = b.comment("List of blocks to be generated as intrusions. Each block is followed by its weight.")
                    .define("overworldIntrusionList", new ArrayList<>(Arrays.asList("rankine:kimberlite","1")));
            OVERWORLD_INTRUSION_CHANCE = b.comment("Chance for an overworld intrusion to spawn in a chunk. Set to 0 to disable.")
                    .defineInRange("overworldIntrusionChance", 0.5D, 0.0D, 1.0D);
            OVERWORLD_INTRUSION_RADIUS = b.comment("Size of an intrusion")
                    .defineInRange("overworldIntrusionRadius", 5, 0, 15);
            OVERWORLD_INTRUSION_SHRINK = b.comment("Chance for an overworld intrusion to shift as it goes up. Values closer to 0 result in straighter intrusions")
                    .defineInRange("overworldIntrusionShift", 0.15D, 0.0D, 1.0D);
            DIAMON_CHANCE = b.comment("Chance for an kimberlite intrusion block to be replaced by a diamond ore")
                    .defineInRange("diamondOreChance", 0.04D, 0.00D, 1.00D);
            ILMENITE_CHANCE = b.comment("Chance for an kimberlite intrusion block to be replaced by an ilmenite ore")
                    .defineInRange("ilmeniteOreChance", 0.005D, 0.00D, 1.00D);
            PETALITE_CHANCE = b.comment("Chance for an pegmatite intrusion block to be replaced by an petalite ore")
                    .defineInRange("petaliteOreChance", 0.02D, 0.00D, 1.00D);

            NETHER_INTRUSION_LIST = b.comment("List of blocks to be generated as intrusions. Each block is followed by its weight.")
                    .define("netherIntrusionList", new ArrayList<>(Arrays.asList("rankine:pumice","1","rankine:scoria","1","rankine:perovskite","1","rankine:bridgmanite","1","rankine:wadsleyite","1","rankine:komatiite","1","rankine:ferropericlase","1","rankine:ringwoodite","1")));
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
        public final ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_COPPER_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_COPPER_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_COPPER_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_COPPER_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_COPPER_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_COPPER_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_TIN_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_TIN_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_TIN_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_TIN_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_TIN_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_TIN_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_TIN_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_TIN_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_TIN_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_GOLD_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_GOLD_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_GOLD_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_GOLD_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_GOLD_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_GOLD_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_ALUMINUM_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_ALUMINUM_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_ALUMINUM_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_ALUMINUM_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_ALUMINUM_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_ALUMINUM_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_LEAD_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_LEAD_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_LEAD_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_LEAD_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_LEAD_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_LEAD_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SILVER_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_SILVER_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SILVER_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_SILVER_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_SILVER_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_SILVER_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_ARSENIC_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_ARSENIC_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_ARSENIC_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_ARSENIC_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_ARSENIC_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_ARSENIC_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_BISMUTH_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_BISMUTH_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_BISMUTH_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_BISMUTH_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_BISMUTH_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_BISMUTH_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SULFUR_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_SULFUR_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SULFUR_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_SULFUR_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_SULFUR_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_SULFUR_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_GALLIUM_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_GALLIUM_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_GALLIUM_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_GALLIUM_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_GALLIUM_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_GALLIUM_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_INDIUM_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_INDIUM_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_INDIUM_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_INDIUM_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_INDIUM_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_INDIUM_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_TELLURIUM_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_TELLURIUM_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_TELLURIUM_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_TELLURIUM_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_TELLURIUM_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_TELLURIUM_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SELENIUM_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue NATIVE_SELENIUM_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> NATIVE_SELENIUM_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue NATIVE_SELENIUM_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue NATIVE_SELENIUM_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue NATIVE_SELENIUM_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue MALACHITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> MALACHITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue MALACHITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> MALACHITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue MALACHITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue MALACHITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue MALACHITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue MALACHITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue MALACHITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue MALACHITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue MALACHITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue MALACHITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue CASSITERITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> CASSITERITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue CASSITERITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> CASSITERITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue CASSITERITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue CASSITERITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue CASSITERITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue CASSITERITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue CASSITERITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue CASSITERITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue CASSITERITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue CASSITERITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue BAUXITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> BAUXITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue BAUXITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> BAUXITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue BAUXITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue BAUXITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue BAUXITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue BAUXITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue BAUXITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue BAUXITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue BAUXITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue BAUXITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue SPHALERITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> SPHALERITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue SPHALERITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> SPHALERITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue SPHALERITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue SPHALERITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue SPHALERITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue SPHALERITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue SPHALERITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue SPHALERITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue SPHALERITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue SPHALERITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue CINNABAR_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> CINNABAR_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue CINNABAR_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> CINNABAR_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue CINNABAR_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue CINNABAR_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue CINNABAR_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue CINNABAR_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue CINNABAR_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue CINNABAR_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue CINNABAR_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue CINNABAR_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue MAGNETITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> MAGNETITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue MAGNETITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> MAGNETITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue MAGNETITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue MAGNETITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue MAGNETITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue MAGNETITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue MAGNETITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue MAGNETITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue MAGNETITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue MAGNETITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue PENTLANDITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> PENTLANDITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue PENTLANDITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> PENTLANDITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue PENTLANDITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue PENTLANDITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue PENTLANDITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue PENTLANDITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue PENTLANDITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue PENTLANDITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue PENTLANDITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue PENTLANDITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue MAGNESITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> MAGNESITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue MAGNESITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> MAGNESITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue MAGNESITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue MAGNESITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue MAGNESITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue MAGNESITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue MAGNESITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue MAGNESITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue MAGNESITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue MAGNESITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue GALENA_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> GALENA_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue GALENA_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> GALENA_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue GALENA_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue GALENA_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue GALENA_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue GALENA_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue GALENA_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue GALENA_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue GALENA_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue GALENA_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue VANADINITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> VANADINITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue VANADINITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> VANADINITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue VANADINITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue VANADINITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue VANADINITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue VANADINITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue VANADINITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue VANADINITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue VANADINITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue VANADINITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue BISMUTHINITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> BISMUTHINITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue BISMUTHINITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> BISMUTHINITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue BISMUTHINITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue BISMUTHINITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue BISMUTHINITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue BISMUTHINITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue BISMUTHINITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue BISMUTHINITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue BISMUTHINITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue BISMUTHINITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue ACANTHITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> ACANTHITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue ACANTHITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> ACANTHITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue ACANTHITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue ACANTHITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue ACANTHITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue ACANTHITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue ACANTHITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue ACANTHITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue ACANTHITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue ACANTHITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue PYROLUSITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> PYROLUSITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue PYROLUSITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> PYROLUSITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue PYROLUSITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue PYROLUSITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue PYROLUSITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue PYROLUSITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue PYROLUSITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue PYROLUSITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue PYROLUSITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue PYROLUSITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue CHROMITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> CHROMITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue CHROMITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> CHROMITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue CHROMITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue CHROMITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue CHROMITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue CHROMITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue CHROMITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue CHROMITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue CHROMITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue CHROMITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue MOLYBDENITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> MOLYBDENITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue MOLYBDENITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> MOLYBDENITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue MOLYBDENITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue MOLYBDENITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue MOLYBDENITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue MOLYBDENITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue MOLYBDENITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue MOLYBDENITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue MOLYBDENITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue MOLYBDENITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue ILMENITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> ILMENITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue ILMENITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> ILMENITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue ILMENITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue ILMENITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue ILMENITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue ILMENITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue ILMENITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue ILMENITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue ILMENITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue ILMENITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue COLUMBITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> COLUMBITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue COLUMBITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> COLUMBITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue COLUMBITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue COLUMBITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue COLUMBITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue COLUMBITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue COLUMBITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue COLUMBITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue COLUMBITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue COLUMBITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue WOLFRAMITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> WOLFRAMITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue WOLFRAMITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> WOLFRAMITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue WOLFRAMITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue WOLFRAMITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue WOLFRAMITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue WOLFRAMITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue WOLFRAMITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue WOLFRAMITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue WOLFRAMITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue WOLFRAMITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue RHENIITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> RHENIITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue RHENIITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> RHENIITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue RHENIITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue RHENIITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue RHENIITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue RHENIITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue RHENIITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue RHENIITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue RHENIITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue RHENIITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue TANTALITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> TANTALITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue TANTALITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> TANTALITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue TANTALITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue TANTALITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue TANTALITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue TANTALITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue TANTALITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue TANTALITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue TANTALITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue TANTALITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue PLUMBAGO_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> PLUMBAGO_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue PLUMBAGO_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> PLUMBAGO_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue PLUMBAGO_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue PLUMBAGO_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue PLUMBAGO_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue PLUMBAGO_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue PLUMBAGO_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue PLUMBAGO_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue PLUMBAGO_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue PLUMBAGO_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue MOISSANITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> MOISSANITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue MOISSANITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> MOISSANITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue MOISSANITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue MOISSANITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue MOISSANITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue MOISSANITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue MOISSANITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue MOISSANITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue MOISSANITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue MOISSANITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue SPERRYLITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> SPERRYLITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue SPERRYLITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> SPERRYLITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue SPERRYLITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue SPERRYLITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue SPERRYLITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue SPERRYLITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue SPERRYLITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue SPERRYLITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue SPERRYLITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue SPERRYLITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue LIGNITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> LIGNITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue LIGNITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> LIGNITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue LIGNITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue LIGNITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue LIGNITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue LIGNITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue LIGNITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue LIGNITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue LIGNITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue LIGNITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> SUBBITUMINOUS_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue SUBBITUMINOUS_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> SUBBITUMINOUS_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue SUBBITUMINOUS_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue SUBBITUMINOUS_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue SUBBITUMINOUS_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue BITUMINOUS_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> BITUMINOUS_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue BITUMINOUS_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> BITUMINOUS_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue BITUMINOUS_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue BITUMINOUS_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue BITUMINOUS_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue BITUMINOUS_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue BITUMINOUS_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue BITUMINOUS_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue BITUMINOUS_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue BITUMINOUS_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue ANTHRACITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> ANTHRACITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue ANTHRACITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> ANTHRACITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue ANTHRACITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue ANTHRACITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue ANTHRACITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue ANTHRACITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue ANTHRACITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue ANTHRACITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue ANTHRACITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue ANTHRACITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue LAZURITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> LAZURITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue LAZURITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> LAZURITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue LAZURITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue LAZURITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue LAZURITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue LAZURITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue LAZURITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue LAZURITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue LAZURITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue LAZURITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue DIAMOND_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> DIAMOND_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue DIAMOND_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> DIAMOND_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue DIAMOND_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue DIAMOND_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue DIAMOND_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue DIAMOND_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue DIAMOND_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue DIAMOND_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue DIAMOND_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue DIAMOND_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue GREENOCKITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> GREENOCKITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue GREENOCKITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> GREENOCKITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue GREENOCKITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue GREENOCKITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue GREENOCKITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue GREENOCKITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue GREENOCKITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue GREENOCKITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue GREENOCKITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue GREENOCKITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue EMERALD_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> EMERALD_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue EMERALD_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> EMERALD_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue EMERALD_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue EMERALD_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue EMERALD_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue EMERALD_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue EMERALD_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue EMERALD_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue EMERALD_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue EMERALD_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue AQUAMARINE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> AQUAMARINE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue AQUAMARINE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> AQUAMARINE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue AQUAMARINE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue AQUAMARINE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue AQUAMARINE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue AQUAMARINE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue AQUAMARINE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue AQUAMARINE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue AQUAMARINE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue AQUAMARINE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue QUARTZ_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> QUARTZ_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue QUARTZ_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> QUARTZ_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue QUARTZ_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue QUARTZ_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue QUARTZ_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue QUARTZ_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue QUARTZ_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue QUARTZ_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue QUARTZ_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue QUARTZ_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue OPAL_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> OPAL_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue OPAL_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> OPAL_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue OPAL_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue OPAL_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue OPAL_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue OPAL_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue OPAL_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue OPAL_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue OPAL_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue OPAL_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue MAJORITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> MAJORITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue MAJORITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> MAJORITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue MAJORITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue MAJORITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue MAJORITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue MAJORITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue MAJORITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue MAJORITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue MAJORITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue MAJORITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue FLUORITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> FLUORITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue FLUORITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> FLUORITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue FLUORITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue FLUORITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue FLUORITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue FLUORITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue FLUORITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue FLUORITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue FLUORITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue FLUORITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue URANINITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> URANINITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue URANINITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> URANINITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue URANINITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue URANINITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue URANINITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue URANINITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue URANINITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue URANINITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue URANINITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue URANINITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue STIBNITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> STIBNITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue STIBNITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> STIBNITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue STIBNITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue STIBNITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue STIBNITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue STIBNITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue STIBNITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue STIBNITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue STIBNITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue STIBNITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue XENOTIME_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> XENOTIME_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue XENOTIME_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> XENOTIME_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue XENOTIME_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue XENOTIME_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue XENOTIME_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue XENOTIME_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue XENOTIME_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue XENOTIME_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue XENOTIME_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue XENOTIME_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue HALITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> HALITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue HALITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> HALITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue HALITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue HALITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue HALITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue HALITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue HALITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue HALITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue HALITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue HALITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue PINK_HALITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> PINK_HALITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue PINK_HALITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> PINK_HALITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue PINK_HALITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue PINK_HALITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue PINK_HALITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue PINK_HALITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue PINK_HALITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue PINK_HALITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue PINK_HALITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue PINK_HALITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> INTERSPINIFEX_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue INTERSPINIFEX_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> INTERSPINIFEX_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue INTERSPINIFEX_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue INTERSPINIFEX_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue INTERSPINIFEX_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue PETALITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> PETALITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue PETALITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> PETALITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue PETALITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue PETALITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue PETALITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue PETALITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue PETALITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue PETALITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue PETALITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue PETALITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue COBALTITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> COBALTITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue COBALTITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> COBALTITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue COBALTITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue COBALTITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue COBALTITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue COBALTITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue COBALTITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue COBALTITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue COBALTITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue COBALTITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue CRYOLITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> CRYOLITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue CRYOLITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> CRYOLITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue CRYOLITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue CRYOLITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue CRYOLITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue CRYOLITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue CRYOLITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue CRYOLITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue CRYOLITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue CRYOLITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue PYRITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> PYRITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue PYRITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> PYRITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue PYRITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue PYRITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue PYRITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue PYRITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue PYRITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue PYRITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue PYRITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue PYRITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue CELESTINE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> CELESTINE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue CELESTINE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> CELESTINE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue CELESTINE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue CELESTINE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue CELESTINE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue CELESTINE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue CELESTINE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue CELESTINE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue CELESTINE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue CELESTINE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue MONAZITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> MONAZITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue MONAZITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> MONAZITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue MONAZITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue MONAZITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue MONAZITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue MONAZITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue MONAZITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue MONAZITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue MONAZITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue MONAZITE_ORE_MEGA_CHANCE;
        
        public final ForgeConfigSpec.IntValue KAMACITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> KAMACITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue KAMACITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> KAMACITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue KAMACITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue KAMACITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue KAMACITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue KAMACITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue KAMACITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue KAMACITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue KAMACITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue KAMACITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue ANTITAENITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> ANTITAENITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue ANTITAENITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> ANTITAENITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue ANTITAENITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue ANTITAENITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue ANTITAENITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue ANTITAENITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue ANTITAENITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue ANTITAENITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue ANTITAENITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue ANTITAENITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue TAENITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> TAENITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue TAENITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> TAENITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue TAENITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue TAENITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue TAENITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue TAENITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue TAENITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue TAENITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue TAENITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue TAENITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue TETRATAENITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> TETRATAENITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue TETRATAENITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> TETRATAENITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue TETRATAENITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue TETRATAENITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue TETRATAENITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue TETRATAENITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue TETRATAENITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue TETRATAENITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue TETRATAENITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue TETRATAENITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.IntValue LONSDALEITE_ORE_HL;
        public final ForgeConfigSpec.ConfigValue<List<String>> LONSDALEITE_ORE_DIMENSION_LIST;
        public final ForgeConfigSpec.BooleanValue LONSDALEITE_ORE_STONE_SPECIFIC;
        public final ForgeConfigSpec.ConfigValue<List<String>> LONSDALEITE_ORE_BLOCK_LIST;
        public final ForgeConfigSpec.IntValue LONSDALEITE_ORE_MIN_HEIGHT;
        public final ForgeConfigSpec.IntValue LONSDALEITE_ORE_MAX_HEIGHT;
        public final ForgeConfigSpec.BooleanValue LONSDALEITE_ORE_GENTYPE;
        public final ForgeConfigSpec.IntValue LONSDALEITE_ORE_CHANCE;
        public final ForgeConfigSpec.IntValue LONSDALEITE_ORE_SIZE;
        public final ForgeConfigSpec.BooleanValue LONSDALEITE_ORE_MEGA_GEN;
        public final ForgeConfigSpec.IntValue LONSDALEITE_ORE_MEGA_SIZE;
        public final ForgeConfigSpec.IntValue LONSDALEITE_ORE_MEGA_CHANCE;

        public final ForgeConfigSpec.BooleanValue RANKINE_ORES_O;
        public final ForgeConfigSpec.BooleanValue RANKINE_ORES_N;
        public final ForgeConfigSpec.BooleanValue RANKINE_ORES_E;


        public Ores(ForgeConfigSpec.Builder b) {
            b.comment("Oregen Settings").push("oregen");

            RANKINE_ORES_O = b.comment("Enable/Disable Project Rankine ores in world.")
                    .define("generateOverworldOres",true);
            RANKINE_ORES_N = b.comment("Enable/Disable Project Rankine ores in Nether.")
                    .define("generateNetherOres",true);
            RANKINE_ORES_E = b.comment("Enable/Disable Project Rankine ores in End.")
                    .define("generateEndOres",true);

            b.comment("Native Copper Ore Settings").push("nativeCopperOre");
            NATIVE_COPPER_ORE_HL = b.comment("Harvest Level of Native Copper Ore.")
                    .defineInRange("nativeCopperOreHL", 0, 0, 4);
            NATIVE_COPPER_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Copper Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Copper Ore generation.")
                    .define("nativeCopperOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            NATIVE_COPPER_ORE_STONE_SPECIFIC = b.comment("If true Native Copper Ore will only spawn in the blocks listed under nativeCopperOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeCopperOreDimList.")
                    .define("nativeCopperOreStoneSpecific",false);
            NATIVE_COPPER_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Copper Ore in if nativeCopperOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeCopperOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_COPPER_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Copper Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeCopperOreMin", 50, 0, 256);
            NATIVE_COPPER_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Copper Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeCopperOreMax", 85, 0, 256);
            NATIVE_COPPER_ORE_GENTYPE = b.comment("If false, nativeCopperOreChance will determine how many tries per chunk ore veins will generate. If true, nativeCopperOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeCopperOreGentype",false);
            NATIVE_COPPER_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeCopperOreGentype.")
                    .defineInRange("nativeCopperOreChance", 6, 0, 256);
            NATIVE_COPPER_ORE_SIZE = b.comment("The maximum size of Native Copper Ore vein")
                    .defineInRange("nativeCopperOreSize", 9, 0, 500);
            NATIVE_COPPER_ORE_MEGA_GEN = b.comment("If true, a mega Native Copper Ore vein will generate according to the parameters nativeCopperOreMegaVeinSize andnativeCopperOreMegaVeinChance")
                    .define("nativeCopperOreMegaVeins",false);
            NATIVE_COPPER_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Copper Ore vein.")
                    .defineInRange("nativeCopperOreMegaVeinSize", 0, 0, 500);
            NATIVE_COPPER_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Copper Ore vein will spawn.")
                    .defineInRange("nativeCopperOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Tin Ore Settings").push("nativeTinOre");
            NATIVE_TIN_ORE_HL = b.comment("Harvest Level of Native Tin Ore.")
                    .defineInRange("nativeTinOreHL", 0, 0, 4);
            NATIVE_TIN_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Tin Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Tin Ore generation.")
                    .define("nativeTinOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            NATIVE_TIN_ORE_STONE_SPECIFIC = b.comment("If true Native Tin Ore will only spawn in the blocks listed under nativeTinOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeTinOreDimList.")
                    .define("nativeTinOreStoneSpecific",false);
            NATIVE_TIN_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Tin Ore in if nativeTinOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeTinOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_TIN_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Tin Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeTinOreMin", 50, 0, 256);
            NATIVE_TIN_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Tin Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeTinOreMax", 85, 0, 256);
            NATIVE_TIN_ORE_GENTYPE = b.comment("If false, nativeTinOreChance will determine how many tries per chunk ore veins will generate. If true, nativeTinOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeTinOreGentype",false);
            NATIVE_TIN_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeTinOreGentype.")
                    .defineInRange("nativeTinOreChance", 6, 0, 256);
            NATIVE_TIN_ORE_SIZE = b.comment("The maximum size of Native Tin Ore vein")
                    .defineInRange("nativeTinOreSize", 9, 0, 500);
            NATIVE_TIN_ORE_MEGA_GEN = b.comment("If true, a mega Native Tin Ore vein will generate according to the parameters nativeTinOreMegaVeinSize andnativeTinOreMegaVeinChance")
                    .define("nativeTinOreMegaVeins",false);
            NATIVE_TIN_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Tin Ore vein.")
                    .defineInRange("nativeTinOreMegaVeinSize", 0, 0, 500);
            NATIVE_TIN_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Tin Ore vein will spawn.")
                    .defineInRange("nativeTinOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Gold Ore Settings").push("nativeGoldOre");
            NATIVE_GOLD_ORE_HL = b.comment("Harvest Level of Native Gold Ore.")
                    .defineInRange("nativeGoldOreHL", 0, 0, 4);
            NATIVE_GOLD_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Gold Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Gold Ore generation.")
                    .define("nativeGoldOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            NATIVE_GOLD_ORE_STONE_SPECIFIC = b.comment("If true Native Gold Ore will only spawn in the blocks listed under nativeGoldOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeGoldOreDimList.")
                    .define("nativeGoldOreStoneSpecific",false);
            NATIVE_GOLD_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Gold Ore in if nativeGoldOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeGoldOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_GOLD_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Gold Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeGoldOreMin", 20, 0, 256);
            NATIVE_GOLD_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Gold Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeGoldOreMax", 60, 0, 256);
            NATIVE_GOLD_ORE_GENTYPE = b.comment("If false, nativeGoldOreChance will determine how many tries per chunk ore veins will generate. If true, nativeGoldOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeGoldOreGentype",false);
            NATIVE_GOLD_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeGoldOreGentype.")
                    .defineInRange("nativeGoldOreChance", 4, 0, 256);
            NATIVE_GOLD_ORE_SIZE = b.comment("The maximum size of Native Gold Ore vein")
                    .defineInRange("nativeGoldOreSize", 8, 0, 500);
            NATIVE_GOLD_ORE_MEGA_GEN = b.comment("If true, a mega Native Gold Ore vein will generate according to the parameters nativeGoldOreMegaVeinSize andnativeGoldOreMegaVeinChance")
                    .define("nativeGoldOreMegaVeins",false);
            NATIVE_GOLD_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Gold Ore vein.")
                    .defineInRange("nativeGoldOreMegaVeinSize", 0, 0, 500);
            NATIVE_GOLD_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Gold Ore vein will spawn.")
                    .defineInRange("nativeGoldOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Aluminum Ore Settings").push("nativeAluminumOre");
            NATIVE_ALUMINUM_ORE_HL = b.comment("Harvest Level of Native Aluminum Ore.")
                    .defineInRange("nativeAluminumOreHL", 0, 0, 4);
            NATIVE_ALUMINUM_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Aluminum Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Aluminum Ore generation.")
                    .define("nativeAluminumOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            NATIVE_ALUMINUM_ORE_STONE_SPECIFIC = b.comment("If true Native Aluminum Ore will only spawn in the blocks listed under nativeAluminumOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeAluminumOreDimList.")
                    .define("nativeAluminumOreStoneSpecific",false);
            NATIVE_ALUMINUM_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Aluminum Ore in if nativeAluminumOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeAluminumOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_ALUMINUM_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Aluminum Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeAluminumOreMin", 50, 0, 256);
            NATIVE_ALUMINUM_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Aluminum Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeAluminumOreMax", 85, 0, 256);
            NATIVE_ALUMINUM_ORE_GENTYPE = b.comment("If false, nativeAluminumOreChance will determine how many tries per chunk ore veins will generate. If true, nativeAluminumOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeAluminumOreGentype",false);
            NATIVE_ALUMINUM_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeAluminumOreGentype.")
                    .defineInRange("nativeAluminumOreChance", 6, 0, 256);
            NATIVE_ALUMINUM_ORE_SIZE = b.comment("The maximum size of Native Aluminum Ore vein")
                    .defineInRange("nativeAluminumOreSize", 9, 0, 500);
            NATIVE_ALUMINUM_ORE_MEGA_GEN = b.comment("If true, a mega Native Aluminum Ore vein will generate according to the parameters nativeAluminumOreMegaVeinSize andnativeAluminumOreMegaVeinChance")
                    .define("nativeAluminumOreMegaVeins",false);
            NATIVE_ALUMINUM_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Aluminum Ore vein.")
                    .defineInRange("nativeAluminumOreMegaVeinSize", 0, 0, 500);
            NATIVE_ALUMINUM_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Aluminum Ore vein will spawn.")
                    .defineInRange("nativeAluminumOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Lead Ore Settings").push("nativeLeadOre");
            NATIVE_LEAD_ORE_HL = b.comment("Harvest Level of Native Lead Ore.")
                    .defineInRange("nativeLeadOreHL", 0, 0, 4);
            NATIVE_LEAD_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Lead Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Lead Ore generation.")
                    .define("nativeLeadOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            NATIVE_LEAD_ORE_STONE_SPECIFIC = b.comment("If true Native Lead Ore will only spawn in the blocks listed under nativeLeadOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeLeadOreDimList.")
                    .define("nativeLeadOreStoneSpecific",false);
            NATIVE_LEAD_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Lead Ore in if nativeLeadOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeLeadOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_LEAD_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Lead Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeLeadOreMin", 50, 0, 256);
            NATIVE_LEAD_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Lead Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeLeadOreMax", 85, 0, 256);
            NATIVE_LEAD_ORE_GENTYPE = b.comment("If false, nativeLeadOreChance will determine how many tries per chunk ore veins will generate. If true, nativeLeadOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeLeadOreGentype",false);
            NATIVE_LEAD_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeLeadOreGentype.")
                    .defineInRange("nativeLeadOreChance", 5, 0, 256);
            NATIVE_LEAD_ORE_SIZE = b.comment("The maximum size of Native Lead Ore vein")
                    .defineInRange("nativeLeadOreSize", 9, 0, 500);
            NATIVE_LEAD_ORE_MEGA_GEN = b.comment("If true, a mega Native Lead Ore vein will generate according to the parameters nativeLeadOreMegaVeinSize andnativeLeadOreMegaVeinChance")
                    .define("nativeLeadOreMegaVeins",false);
            NATIVE_LEAD_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Lead Ore vein.")
                    .defineInRange("nativeLeadOreMegaVeinSize", 0, 0, 500);
            NATIVE_LEAD_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Lead Ore vein will spawn.")
                    .defineInRange("nativeLeadOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Silver Ore Settings").push("nativeSilverOre");
            NATIVE_SILVER_ORE_HL = b.comment("Harvest Level of Native Silver Ore.")
                    .defineInRange("nativeSilverOreHL", 0, 0, 4);
            NATIVE_SILVER_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Silver Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Silver Ore generation.")
                    .define("nativeSilverOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            NATIVE_SILVER_ORE_STONE_SPECIFIC = b.comment("If true Native Silver Ore will only spawn in the blocks listed under nativeSilverOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeSilverOreDimList.")
                    .define("nativeSilverOreStoneSpecific",false);
            NATIVE_SILVER_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Silver Ore in if nativeSilverOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeSilverOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_SILVER_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Silver Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeSilverOreMin", 50, 0, 256);
            NATIVE_SILVER_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Silver Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeSilverOreMax", 85, 0, 256);
            NATIVE_SILVER_ORE_GENTYPE = b.comment("If false, nativeSilverOreChance will determine how many tries per chunk ore veins will generate. If true, nativeSilverOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeSilverOreGentype",false);
            NATIVE_SILVER_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeSilverOreGentype.")
                    .defineInRange("nativeSilverOreChance", 4, 0, 256);
            NATIVE_SILVER_ORE_SIZE = b.comment("The maximum size of Native Silver Ore vein")
                    .defineInRange("nativeSilverOreSize", 9, 0, 500);
            NATIVE_SILVER_ORE_MEGA_GEN = b.comment("If true, a mega Native Silver Ore vein will generate according to the parameters nativeSilverOreMegaVeinSize andnativeSilverOreMegaVeinChance")
                    .define("nativeSilverOreMegaVeins",false);
            NATIVE_SILVER_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Silver Ore vein.")
                    .defineInRange("nativeSilverOreMegaVeinSize", 0, 0, 500);
            NATIVE_SILVER_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Silver Ore vein will spawn.")
                    .defineInRange("nativeSilverOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Arsenic Ore Settings").push("nativeArsenicOre");
            NATIVE_ARSENIC_ORE_HL = b.comment("Harvest Level of Native Arsenic Ore.")
                    .defineInRange("nativeArsenicOreHL", 2, 0, 4);
            NATIVE_ARSENIC_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Arsenic Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Arsenic Ore generation.")
                    .define("nativeArsenicOreDimList", new ArrayList<>(Arrays.asList("nether")));
            NATIVE_ARSENIC_ORE_STONE_SPECIFIC = b.comment("If true Native Arsenic Ore will only spawn in the blocks listed under nativeArsenicOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeArsenicOreDimList.")
                    .define("nativeArsenicOreStoneSpecific",false);
            NATIVE_ARSENIC_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Arsenic Ore in if nativeArsenicOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeArsenicOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_ARSENIC_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Arsenic Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeArsenicOreMin", 45, 0, 256);
            NATIVE_ARSENIC_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Arsenic Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeArsenicOreMax", 90, 0, 256);
            NATIVE_ARSENIC_ORE_GENTYPE = b.comment("If false, nativeArsenicOreChance will determine how many tries per chunk ore veins will generate. If true, nativeArsenicOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeArsenicOreGentype",false);
            NATIVE_ARSENIC_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeArsenicOreGentype.")
                    .defineInRange("nativeArsenicOreChance", 12, 0, 256);
            NATIVE_ARSENIC_ORE_SIZE = b.comment("The maximum size of Native Arsenic Ore vein")
                    .defineInRange("nativeArsenicOreSize", 6, 0, 500);
            NATIVE_ARSENIC_ORE_MEGA_GEN = b.comment("If true, a mega Native Arsenic Ore vein will generate according to the parameters nativeArsenicOreMegaVeinSize andnativeArsenicOreMegaVeinChance")
                    .define("nativeArsenicOreMegaVeins",false);
            NATIVE_ARSENIC_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Arsenic Ore vein.")
                    .defineInRange("nativeArsenicOreMegaVeinSize", 70, 0, 500);
            NATIVE_ARSENIC_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Arsenic Ore vein will spawn.")
                    .defineInRange("nativeArsenicOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Bismuth Ore Settings").push("nativeBismuthOre");
            NATIVE_BISMUTH_ORE_HL = b.comment("Harvest Level of Native Bismuth Ore.")
                    .defineInRange("nativeBismuthOreHL", 0, 0, 4);
            NATIVE_BISMUTH_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Bismuth Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Bismuth Ore generation.")
                    .define("nativeBismuthOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            NATIVE_BISMUTH_ORE_STONE_SPECIFIC = b.comment("If true Native Bismuth Ore will only spawn in the blocks listed under nativeBismuthOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeBismuthOreDimList.")
                    .define("nativeBismuthOreStoneSpecific",false);
            NATIVE_BISMUTH_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Bismuth Ore in if nativeBismuthOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeBismuthOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_BISMUTH_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Bismuth Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeBismuthOreMin", 50, 0, 256);
            NATIVE_BISMUTH_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Bismuth Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeBismuthOreMax", 85, 0, 256);
            NATIVE_BISMUTH_ORE_GENTYPE = b.comment("If false, nativeBismuthOreChance will determine how many tries per chunk ore veins will generate. If true, nativeBismuthOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeBismuthOreGentype",false);
            NATIVE_BISMUTH_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeBismuthOreGentype.")
                    .defineInRange("nativeBismuthOreChance", 3, 0, 256);
            NATIVE_BISMUTH_ORE_SIZE = b.comment("The maximum size of Native Bismuth Ore vein")
                    .defineInRange("nativeBismuthOreSize", 6, 0, 500);
            NATIVE_BISMUTH_ORE_MEGA_GEN = b.comment("If true, a mega Native Bismuth Ore vein will generate according to the parameters nativeBismuthOreMegaVeinSize andnativeBismuthOreMegaVeinChance")
                    .define("nativeBismuthOreMegaVeins",false);
            NATIVE_BISMUTH_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Bismuth Ore vein.")
                    .defineInRange("nativeBismuthOreMegaVeinSize", 0, 0, 500);
            NATIVE_BISMUTH_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Bismuth Ore vein will spawn.")
                    .defineInRange("nativeBismuthOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Sulfur Ore Settings").push("nativeSulfurOre");
            NATIVE_SULFUR_ORE_HL = b.comment("Harvest Level of Native Sulfur Ore.")
                    .defineInRange("nativeSulfurOreHL", 2, 0, 4);
            NATIVE_SULFUR_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Sulfur Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Sulfur Ore generation.")
                    .define("nativeSulfurOreDimList", new ArrayList<>(Arrays.asList("nether")));
            NATIVE_SULFUR_ORE_STONE_SPECIFIC = b.comment("If true Native Sulfur Ore will only spawn in the blocks listed under nativeSulfurOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeSulfurOreDimList.")
                    .define("nativeSulfurOreStoneSpecific",false);
            NATIVE_SULFUR_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Sulfur Ore in if nativeSulfurOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeSulfurOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_SULFUR_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Sulfur Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeSulfurOreMin", 45, 0, 256);
            NATIVE_SULFUR_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Sulfur Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeSulfurOreMax", 90, 0, 256);
            NATIVE_SULFUR_ORE_GENTYPE = b.comment("If false, nativeSulfurOreChance will determine how many tries per chunk ore veins will generate. If true, nativeSulfurOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeSulfurOreGentype",false);
            NATIVE_SULFUR_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeSulfurOreGentype.")
                    .defineInRange("nativeSulfurOreChance", 12, 0, 256);
            NATIVE_SULFUR_ORE_SIZE = b.comment("The maximum size of Native Sulfur Ore vein")
                    .defineInRange("nativeSulfurOreSize", 6, 0, 500);
            NATIVE_SULFUR_ORE_MEGA_GEN = b.comment("If true, a mega Native Sulfur Ore vein will generate according to the parameters nativeSulfurOreMegaVeinSize andnativeSulfurOreMegaVeinChance")
                    .define("nativeSulfurOreMegaVeins",false);
            NATIVE_SULFUR_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Sulfur Ore vein.")
                    .defineInRange("nativeSulfurOreMegaVeinSize", 70, 0, 500);
            NATIVE_SULFUR_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Sulfur Ore vein will spawn.")
                    .defineInRange("nativeSulfurOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Gallium Ore Settings").push("nativeGalliumOre");
            NATIVE_GALLIUM_ORE_HL = b.comment("Harvest Level of Native Gallium Ore.")
                    .defineInRange("nativeGalliumOreHL", 4, 0, 4);
            NATIVE_GALLIUM_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Gallium Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Gallium Ore generation.")
                    .define("nativeGalliumOreDimList", new ArrayList<>(Arrays.asList("end")));
            NATIVE_GALLIUM_ORE_STONE_SPECIFIC = b.comment("If true Native Gallium Ore will only spawn in the blocks listed under nativeGalliumOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeGalliumOreDimList.")
                    .define("nativeGalliumOreStoneSpecific",false);
            NATIVE_GALLIUM_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Gallium Ore in if nativeGalliumOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeGalliumOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_GALLIUM_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Gallium Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeGalliumOreMin", 10, 0, 256);
            NATIVE_GALLIUM_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Gallium Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeGalliumOreMax", 60, 0, 256);
            NATIVE_GALLIUM_ORE_GENTYPE = b.comment("If false, nativeGalliumOreChance will determine how many tries per chunk ore veins will generate. If true, nativeGalliumOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeGalliumOreGentype",false);
            NATIVE_GALLIUM_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeGalliumOreGentype.")
                    .defineInRange("nativeGalliumOreChance", 6, 0, 256);
            NATIVE_GALLIUM_ORE_SIZE = b.comment("The maximum size of Native Gallium Ore vein")
                    .defineInRange("nativeGalliumOreSize", 7, 0, 500);
            NATIVE_GALLIUM_ORE_MEGA_GEN = b.comment("If true, a mega Native Gallium Ore vein will generate according to the parameters nativeGalliumOreMegaVeinSize andnativeGalliumOreMegaVeinChance")
                    .define("nativeGalliumOreMegaVeins",false);
            NATIVE_GALLIUM_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Gallium Ore vein.")
                    .defineInRange("nativeGalliumOreMegaVeinSize", 0, 0, 500);
            NATIVE_GALLIUM_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Gallium Ore vein will spawn.")
                    .defineInRange("nativeGalliumOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Indium Ore Settings").push("nativeIndiumOre");
            NATIVE_INDIUM_ORE_HL = b.comment("Harvest Level of Native Indium Ore.")
                    .defineInRange("nativeIndiumOreHL", 4, 0, 4);
            NATIVE_INDIUM_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Indium Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Indium Ore generation.")
                    .define("nativeIndiumOreDimList", new ArrayList<>(Arrays.asList("end")));
            NATIVE_INDIUM_ORE_STONE_SPECIFIC = b.comment("If true Native Indium Ore will only spawn in the blocks listed under nativeIndiumOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeIndiumOreDimList.")
                    .define("nativeIndiumOreStoneSpecific",false);
            NATIVE_INDIUM_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Indium Ore in if nativeIndiumOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeIndiumOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_INDIUM_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Indium Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeIndiumOreMin", 10, 0, 256);
            NATIVE_INDIUM_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Indium Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeIndiumOreMax", 60, 0, 256);
            NATIVE_INDIUM_ORE_GENTYPE = b.comment("If false, nativeIndiumOreChance will determine how many tries per chunk ore veins will generate. If true, nativeIndiumOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeIndiumOreGentype",false);
            NATIVE_INDIUM_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeIndiumOreGentype.")
                    .defineInRange("nativeIndiumOreChance", 6, 0, 256);
            NATIVE_INDIUM_ORE_SIZE = b.comment("The maximum size of Native Indium Ore vein")
                    .defineInRange("nativeIndiumOreSize", 7, 0, 500);
            NATIVE_INDIUM_ORE_MEGA_GEN = b.comment("If true, a mega Native Indium Ore vein will generate according to the parameters nativeIndiumOreMegaVeinSize andnativeIndiumOreMegaVeinChance")
                    .define("nativeIndiumOreMegaVeins",false);
            NATIVE_INDIUM_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Indium Ore vein.")
                    .defineInRange("nativeIndiumOreMegaVeinSize", 0, 0, 500);
            NATIVE_INDIUM_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Indium Ore vein will spawn.")
                    .defineInRange("nativeIndiumOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Tellurium Ore Settings").push("nativeTelluriumOre");
            NATIVE_TELLURIUM_ORE_HL = b.comment("Harvest Level of Native Tellurium Ore.")
                    .defineInRange("nativeTelluriumOreHL", 4, 0, 4);
            NATIVE_TELLURIUM_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Tellurium Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Tellurium Ore generation.")
                    .define("nativeTelluriumOreDimList", new ArrayList<>(Arrays.asList("end")));
            NATIVE_TELLURIUM_ORE_STONE_SPECIFIC = b.comment("If true Native Tellurium Ore will only spawn in the blocks listed under nativeTelluriumOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeTelluriumOreDimList.")
                    .define("nativeTelluriumOreStoneSpecific",false);
            NATIVE_TELLURIUM_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Tellurium Ore in if nativeTelluriumOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeTelluriumOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_TELLURIUM_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Tellurium Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeTelluriumOreMin", 10, 0, 256);
            NATIVE_TELLURIUM_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Tellurium Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeTelluriumOreMax", 60, 0, 256);
            NATIVE_TELLURIUM_ORE_GENTYPE = b.comment("If false, nativeTelluriumOreChance will determine how many tries per chunk ore veins will generate. If true, nativeTelluriumOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeTelluriumOreGentype",false);
            NATIVE_TELLURIUM_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeTelluriumOreGentype.")
                    .defineInRange("nativeTelluriumOreChance", 6, 0, 256);
            NATIVE_TELLURIUM_ORE_SIZE = b.comment("The maximum size of Native Tellurium Ore vein")
                    .defineInRange("nativeTelluriumOreSize", 7, 0, 500);
            NATIVE_TELLURIUM_ORE_MEGA_GEN = b.comment("If true, a mega Native Tellurium Ore vein will generate according to the parameters nativeTelluriumOreMegaVeinSize andnativeTelluriumOreMegaVeinChance")
                    .define("nativeTelluriumOreMegaVeins",false);
            NATIVE_TELLURIUM_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Tellurium Ore vein.")
                    .defineInRange("nativeTelluriumOreMegaVeinSize", 0, 0, 500);
            NATIVE_TELLURIUM_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Tellurium Ore vein will spawn.")
                    .defineInRange("nativeTelluriumOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Native Selenium Ore Settings").push("nativeSeleniumOre");
            NATIVE_SELENIUM_ORE_HL = b.comment("Harvest Level of Native Selenium Ore.")
                    .defineInRange("nativeSeleniumOreHL", 4, 0, 4);
            NATIVE_SELENIUM_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Native Selenium Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Native Selenium Ore generation.")
                    .define("nativeSeleniumOreDimList", new ArrayList<>(Arrays.asList("end")));
            NATIVE_SELENIUM_ORE_STONE_SPECIFIC = b.comment("If true Native Selenium Ore will only spawn in the blocks listed under nativeSeleniumOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in nativeSeleniumOreDimList.")
                    .define("nativeSeleniumOreStoneSpecific",false);
            NATIVE_SELENIUM_ORE_BLOCK_LIST = b.comment("Blocks to generate Native Selenium Ore in if nativeSeleniumOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("nativeSeleniumOreBlockList", new ArrayList<>(Arrays.asList()));
            NATIVE_SELENIUM_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Native Selenium Ore at (make sure it is less than the maximum)")
                    .defineInRange("nativeSeleniumOreMin", 10, 0, 256);
            NATIVE_SELENIUM_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Native Selenium Ore at (make sure it is greater than the minimum)")
                    .defineInRange("nativeSeleniumOreMax", 60, 0, 256);
            NATIVE_SELENIUM_ORE_GENTYPE = b.comment("If false, nativeSeleniumOreChance will determine how many tries per chunk ore veins will generate. If true, nativeSeleniumOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("nativeSeleniumOreGentype",false);
            NATIVE_SELENIUM_ORE_CHANCE = b.comment("Count / chance number to be used depending on nativeSeleniumOreGentype.")
                    .defineInRange("nativeSeleniumOreChance", 6, 0, 256);
            NATIVE_SELENIUM_ORE_SIZE = b.comment("The maximum size of Native Selenium Ore vein")
                    .defineInRange("nativeSeleniumOreSize", 7, 0, 500);
            NATIVE_SELENIUM_ORE_MEGA_GEN = b.comment("If true, a mega Native Selenium Ore vein will generate according to the parameters nativeSeleniumOreMegaVeinSize andnativeSeleniumOreMegaVeinChance")
                    .define("nativeSeleniumOreMegaVeins",false);
            NATIVE_SELENIUM_ORE_MEGA_SIZE = b.comment("The maximum size of mega Native Selenium Ore vein.")
                    .defineInRange("nativeSeleniumOreMegaVeinSize", 0, 0, 500);
            NATIVE_SELENIUM_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Native Selenium Ore vein will spawn.")
                    .defineInRange("nativeSeleniumOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Malachite Ore Settings").push("malachiteOre");
            MALACHITE_ORE_HL = b.comment("Harvest Level of Malachite Ore.")
                    .defineInRange("malachiteOreHL", 2, 0, 4);
            MALACHITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Malachite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Malachite Ore generation.")
                    .define("malachiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            MALACHITE_ORE_STONE_SPECIFIC = b.comment("If true Malachite Ore will only spawn in the blocks listed under malachiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in malachiteOreDimList.")
                    .define("malachiteOreStoneSpecific",false);
            MALACHITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Malachite Ore in if malachiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("malachiteOreBlockList", new ArrayList<>(Arrays.asList()));
            MALACHITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Malachite Ore at (make sure it is less than the maximum)")
                    .defineInRange("malachiteOreMin", 40, 0, 256);
            MALACHITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Malachite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("malachiteOreMax", 60, 0, 256);
            MALACHITE_ORE_GENTYPE = b.comment("If false, malachiteOreChance will determine how many tries per chunk ore veins will generate. If true, malachiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("malachiteOreGentype",true);
            MALACHITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on malachiteOreGentype.")
                    .defineInRange("malachiteOreChance", 3, 0, 256);
            MALACHITE_ORE_SIZE = b.comment("The maximum size of Malachite Ore vein")
                    .defineInRange("malachiteOreSize", 24, 0, 500);
            MALACHITE_ORE_MEGA_GEN = b.comment("If true, a mega Malachite Ore vein will generate according to the parameters malachiteOreMegaVeinSize andmalachiteOreMegaVeinChance")
                    .define("malachiteOreMegaVeins",false);
            MALACHITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Malachite Ore vein.")
                    .defineInRange("malachiteOreMegaVeinSize", 50, 0, 500);
            MALACHITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Malachite Ore vein will spawn.")
                    .defineInRange("malachiteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Cassiterite Ore Settings").push("cassiteriteOre");
            CASSITERITE_ORE_HL = b.comment("Harvest Level of Cassiterite Ore.")
                    .defineInRange("cassiteriteOreHL", 2, 0, 4);
            CASSITERITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Cassiterite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Cassiterite Ore generation.")
                    .define("cassiteriteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            CASSITERITE_ORE_STONE_SPECIFIC = b.comment("If true Cassiterite Ore will only spawn in the blocks listed under cassiteriteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in cassiteriteOreDimList.")
                    .define("cassiteriteOreStoneSpecific",false);
            CASSITERITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Cassiterite Ore in if cassiteriteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("cassiteriteOreBlockList", new ArrayList<>(Arrays.asList()));
            CASSITERITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Cassiterite Ore at (make sure it is less than the maximum)")
                    .defineInRange("cassiteriteOreMin", 40, 0, 256);
            CASSITERITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Cassiterite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("cassiteriteOreMax", 60, 0, 256);
            CASSITERITE_ORE_GENTYPE = b.comment("If false, cassiteriteOreChance will determine how many tries per chunk ore veins will generate. If true, cassiteriteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("cassiteriteOreGentype",true);
            CASSITERITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on cassiteriteOreGentype.")
                    .defineInRange("cassiteriteOreChance", 3, 0, 256);
            CASSITERITE_ORE_SIZE = b.comment("The maximum size of Cassiterite Ore vein")
                    .defineInRange("cassiteriteOreSize", 24, 0, 500);
            CASSITERITE_ORE_MEGA_GEN = b.comment("If true, a mega Cassiterite Ore vein will generate according to the parameters cassiteriteOreMegaVeinSize andcassiteriteOreMegaVeinChance")
                    .define("cassiteriteOreMegaVeins",false);
            CASSITERITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Cassiterite Ore vein.")
                    .defineInRange("cassiteriteOreMegaVeinSize", 50, 0, 500);
            CASSITERITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Cassiterite Ore vein will spawn.")
                    .defineInRange("cassiteriteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Bauxite Ore Settings").push("bauxiteOre");
            BAUXITE_ORE_HL = b.comment("Harvest Level of Bauxite Ore.")
                    .defineInRange("bauxiteOreHL", 2, 0, 4);
            BAUXITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Bauxite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Bauxite Ore generation.")
                    .define("bauxiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            BAUXITE_ORE_STONE_SPECIFIC = b.comment("If true Bauxite Ore will only spawn in the blocks listed under bauxiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in bauxiteOreDimList.")
                    .define("bauxiteOreStoneSpecific",false);
            BAUXITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Bauxite Ore in if bauxiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("bauxiteOreBlockList", new ArrayList<>(Arrays.asList()));
            BAUXITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Bauxite Ore at (make sure it is less than the maximum)")
                    .defineInRange("bauxiteOreMin", 40, 0, 256);
            BAUXITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Bauxite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("bauxiteOreMax", 60, 0, 256);
            BAUXITE_ORE_GENTYPE = b.comment("If false, bauxiteOreChance will determine how many tries per chunk ore veins will generate. If true, bauxiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("bauxiteOreGentype",true);
            BAUXITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on bauxiteOreGentype.")
                    .defineInRange("bauxiteOreChance", 3, 0, 256);
            BAUXITE_ORE_SIZE = b.comment("The maximum size of Bauxite Ore vein")
                    .defineInRange("bauxiteOreSize", 24, 0, 500);
            BAUXITE_ORE_MEGA_GEN = b.comment("If true, a mega Bauxite Ore vein will generate according to the parameters bauxiteOreMegaVeinSize andbauxiteOreMegaVeinChance")
                    .define("bauxiteOreMegaVeins",false);
            BAUXITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Bauxite Ore vein.")
                    .defineInRange("bauxiteOreMegaVeinSize", 50, 0, 500);
            BAUXITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Bauxite Ore vein will spawn.")
                    .defineInRange("bauxiteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Sphalerite Ore Settings").push("sphaleriteOre");
            SPHALERITE_ORE_HL = b.comment("Harvest Level of Sphalerite Ore.")
                    .defineInRange("sphaleriteOreHL", 2, 0, 4);
            SPHALERITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Sphalerite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Sphalerite Ore generation.")
                    .define("sphaleriteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            SPHALERITE_ORE_STONE_SPECIFIC = b.comment("If true Sphalerite Ore will only spawn in the blocks listed under sphaleriteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in sphaleriteOreDimList.")
                    .define("sphaleriteOreStoneSpecific",false);
            SPHALERITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Sphalerite Ore in if sphaleriteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("sphaleriteOreBlockList", new ArrayList<>(Arrays.asList()));
            SPHALERITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Sphalerite Ore at (make sure it is less than the maximum)")
                    .defineInRange("sphaleriteOreMin", 40, 0, 256);
            SPHALERITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Sphalerite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("sphaleriteOreMax", 60, 0, 256);
            SPHALERITE_ORE_GENTYPE = b.comment("If false, sphaleriteOreChance will determine how many tries per chunk ore veins will generate. If true, sphaleriteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("sphaleriteOreGentype",true);
            SPHALERITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on sphaleriteOreGentype.")
                    .defineInRange("sphaleriteOreChance", 3, 0, 256);
            SPHALERITE_ORE_SIZE = b.comment("The maximum size of Sphalerite Ore vein")
                    .defineInRange("sphaleriteOreSize", 24, 0, 500);
            SPHALERITE_ORE_MEGA_GEN = b.comment("If true, a mega Sphalerite Ore vein will generate according to the parameters sphaleriteOreMegaVeinSize andsphaleriteOreMegaVeinChance")
                    .define("sphaleriteOreMegaVeins",false);
            SPHALERITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Sphalerite Ore vein.")
                    .defineInRange("sphaleriteOreMegaVeinSize", 50, 0, 500);
            SPHALERITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Sphalerite Ore vein will spawn.")
                    .defineInRange("sphaleriteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Cinnabar Ore Settings").push("cinnabarOre");
            CINNABAR_ORE_HL = b.comment("Harvest Level of Cinnabar Ore.")
                    .defineInRange("cinnabarOreHL", 3, 0, 4);
            CINNABAR_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Cinnabar Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Cinnabar Ore generation.")
                    .define("cinnabarOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            CINNABAR_ORE_STONE_SPECIFIC = b.comment("If true Cinnabar Ore will only spawn in the blocks listed under cinnabarOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in cinnabarOreDimList.")
                    .define("cinnabarOreStoneSpecific",false);
            CINNABAR_ORE_BLOCK_LIST = b.comment("Blocks to generate Cinnabar Ore in if cinnabarOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("cinnabarOreBlockList", new ArrayList<>(Arrays.asList()));
            CINNABAR_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Cinnabar Ore at (make sure it is less than the maximum)")
                    .defineInRange("cinnabarOreMin", 40, 0, 256);
            CINNABAR_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Cinnabar Ore at (make sure it is greater than the minimum)")
                    .defineInRange("cinnabarOreMax", 60, 0, 256);
            CINNABAR_ORE_GENTYPE = b.comment("If false, cinnabarOreChance will determine how many tries per chunk ore veins will generate. If true, cinnabarOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("cinnabarOreGentype",false);
            CINNABAR_ORE_CHANCE = b.comment("Count / chance number to be used depending on cinnabarOreGentype.")
                    .defineInRange("cinnabarOreChance", 1, 0, 256);
            CINNABAR_ORE_SIZE = b.comment("The maximum size of Cinnabar Ore vein")
                    .defineInRange("cinnabarOreSize", 24, 0, 500);
            CINNABAR_ORE_MEGA_GEN = b.comment("If true, a mega Cinnabar Ore vein will generate according to the parameters cinnabarOreMegaVeinSize andcinnabarOreMegaVeinChance")
                    .define("cinnabarOreMegaVeins",false);
            CINNABAR_ORE_MEGA_SIZE = b.comment("The maximum size of mega Cinnabar Ore vein.")
                    .defineInRange("cinnabarOreMegaVeinSize", 50, 0, 500);
            CINNABAR_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Cinnabar Ore vein will spawn.")
                    .defineInRange("cinnabarOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Magnetite Ore Settings").push("magnetiteOre");
            MAGNETITE_ORE_HL = b.comment("Harvest Level of Magnetite Ore.")
                    .defineInRange("magnetiteOreHL", 3, 0, 4);
            MAGNETITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Magnetite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Magnetite Ore generation.")
                    .define("magnetiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            MAGNETITE_ORE_STONE_SPECIFIC = b.comment("If true Magnetite Ore will only spawn in the blocks listed under magnetiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in magnetiteOreDimList.")
                    .define("magnetiteOreStoneSpecific",false);
            MAGNETITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Magnetite Ore in if magnetiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("magnetiteOreBlockList", new ArrayList<>(Arrays.asList()));
            MAGNETITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Magnetite Ore at (make sure it is less than the maximum)")
                    .defineInRange("magnetiteOreMin", 30, 0, 256);
            MAGNETITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Magnetite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("magnetiteOreMax", 50, 0, 256);
            MAGNETITE_ORE_GENTYPE = b.comment("If false, magnetiteOreChance will determine how many tries per chunk ore veins will generate. If true, magnetiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("magnetiteOreGentype",false);
            MAGNETITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on magnetiteOreGentype.")
                    .defineInRange("magnetiteOreChance", 1, 0, 256);
            MAGNETITE_ORE_SIZE = b.comment("The maximum size of Magnetite Ore vein")
                    .defineInRange("magnetiteOreSize", 24, 0, 500);
            MAGNETITE_ORE_MEGA_GEN = b.comment("If true, a mega Magnetite Ore vein will generate according to the parameters magnetiteOreMegaVeinSize andmagnetiteOreMegaVeinChance")
                    .define("magnetiteOreMegaVeins",false);
            MAGNETITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Magnetite Ore vein.")
                    .defineInRange("magnetiteOreMegaVeinSize", 50, 0, 500);
            MAGNETITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Magnetite Ore vein will spawn.")
                    .defineInRange("magnetiteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Pentlandite Ore Settings").push("pentlanditeOre");
            PENTLANDITE_ORE_HL = b.comment("Harvest Level of Pentlandite Ore.")
                    .defineInRange("pentlanditeOreHL", 3, 0, 4);
            PENTLANDITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Pentlandite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Pentlandite Ore generation.")
                    .define("pentlanditeOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            PENTLANDITE_ORE_STONE_SPECIFIC = b.comment("If true Pentlandite Ore will only spawn in the blocks listed under pentlanditeOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in pentlanditeOreDimList.")
                    .define("pentlanditeOreStoneSpecific",false);
            PENTLANDITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Pentlandite Ore in if pentlanditeOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("pentlanditeOreBlockList", new ArrayList<>(Arrays.asList()));
            PENTLANDITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Pentlandite Ore at (make sure it is less than the maximum)")
                    .defineInRange("pentlanditeOreMin", 20, 0, 256);
            PENTLANDITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Pentlandite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("pentlanditeOreMax", 40, 0, 256);
            PENTLANDITE_ORE_GENTYPE = b.comment("If false, pentlanditeOreChance will determine how many tries per chunk ore veins will generate. If true, pentlanditeOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("pentlanditeOreGentype",true);
            PENTLANDITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on pentlanditeOreGentype.")
                    .defineInRange("pentlanditeOreChance", 3, 0, 256);
            PENTLANDITE_ORE_SIZE = b.comment("The maximum size of Pentlandite Ore vein")
                    .defineInRange("pentlanditeOreSize", 24, 0, 500);
            PENTLANDITE_ORE_MEGA_GEN = b.comment("If true, a mega Pentlandite Ore vein will generate according to the parameters pentlanditeOreMegaVeinSize andpentlanditeOreMegaVeinChance")
                    .define("pentlanditeOreMegaVeins",false);
            PENTLANDITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Pentlandite Ore vein.")
                    .defineInRange("pentlanditeOreMegaVeinSize", 50, 0, 500);
            PENTLANDITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Pentlandite Ore vein will spawn.")
                    .defineInRange("pentlanditeOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Magnesite Ore Settings").push("magnesiteOre");
            MAGNESITE_ORE_HL = b.comment("Harvest Level of Magnesite Ore.")
                    .defineInRange("magnesiteOreHL", 3, 0, 4);
            MAGNESITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Magnesite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Magnesite Ore generation.")
                    .define("magnesiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            MAGNESITE_ORE_STONE_SPECIFIC = b.comment("If true Magnesite Ore will only spawn in the blocks listed under magnesiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in magnesiteOreDimList.")
                    .define("magnesiteOreStoneSpecific",false);
            MAGNESITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Magnesite Ore in if magnesiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("magnesiteOreBlockList", new ArrayList<>(Arrays.asList()));
            MAGNESITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Magnesite Ore at (make sure it is less than the maximum)")
                    .defineInRange("magnesiteOreMin", 20, 0, 256);
            MAGNESITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Magnesite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("magnesiteOreMax", 40, 0, 256);
            MAGNESITE_ORE_GENTYPE = b.comment("If false, magnesiteOreChance will determine how many tries per chunk ore veins will generate. If true, magnesiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("magnesiteOreGentype",true);
            MAGNESITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on magnesiteOreGentype.")
                    .defineInRange("magnesiteOreChance", 3, 0, 256);
            MAGNESITE_ORE_SIZE = b.comment("The maximum size of Magnesite Ore vein")
                    .defineInRange("magnesiteOreSize", 24, 0, 500);
            MAGNESITE_ORE_MEGA_GEN = b.comment("If true, a mega Magnesite Ore vein will generate according to the parameters magnesiteOreMegaVeinSize andmagnesiteOreMegaVeinChance")
                    .define("magnesiteOreMegaVeins",false);
            MAGNESITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Magnesite Ore vein.")
                    .defineInRange("magnesiteOreMegaVeinSize", 50, 0, 500);
            MAGNESITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Magnesite Ore vein will spawn.")
                    .defineInRange("magnesiteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Galena Ore Settings").push("galenaOre");
            GALENA_ORE_HL = b.comment("Harvest Level of Galena Ore.")
                    .defineInRange("galenaOreHL", 3, 0, 4);
            GALENA_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Galena Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Galena Ore generation.")
                    .define("galenaOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            GALENA_ORE_STONE_SPECIFIC = b.comment("If true Galena Ore will only spawn in the blocks listed under galenaOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in galenaOreDimList.")
                    .define("galenaOreStoneSpecific",false);
            GALENA_ORE_BLOCK_LIST = b.comment("Blocks to generate Galena Ore in if galenaOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("galenaOreBlockList", new ArrayList<>(Arrays.asList()));
            GALENA_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Galena Ore at (make sure it is less than the maximum)")
                    .defineInRange("galenaOreMin", 20, 0, 256);
            GALENA_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Galena Ore at (make sure it is greater than the minimum)")
                    .defineInRange("galenaOreMax", 40, 0, 256);
            GALENA_ORE_GENTYPE = b.comment("If false, galenaOreChance will determine how many tries per chunk ore veins will generate. If true, galenaOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("galenaOreGentype",true);
            GALENA_ORE_CHANCE = b.comment("Count / chance number to be used depending on galenaOreGentype.")
                    .defineInRange("galenaOreChance", 3, 0, 256);
            GALENA_ORE_SIZE = b.comment("The maximum size of Galena Ore vein")
                    .defineInRange("galenaOreSize", 24, 0, 500);
            GALENA_ORE_MEGA_GEN = b.comment("If true, a mega Galena Ore vein will generate according to the parameters galenaOreMegaVeinSize andgalenaOreMegaVeinChance")
                    .define("galenaOreMegaVeins",false);
            GALENA_ORE_MEGA_SIZE = b.comment("The maximum size of mega Galena Ore vein.")
                    .defineInRange("galenaOreMegaVeinSize", 50, 0, 500);
            GALENA_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Galena Ore vein will spawn.")
                    .defineInRange("galenaOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Vanadinite Ore Settings").push("vanadiniteOre");
            VANADINITE_ORE_HL = b.comment("Harvest Level of Vanadinite Ore.")
                    .defineInRange("vanadiniteOreHL", 3, 0, 4);
            VANADINITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Vanadinite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Vanadinite Ore generation.")
                    .define("vanadiniteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            VANADINITE_ORE_STONE_SPECIFIC = b.comment("If true Vanadinite Ore will only spawn in the blocks listed under vanadiniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in vanadiniteOreDimList.")
                    .define("vanadiniteOreStoneSpecific",false);
            VANADINITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Vanadinite Ore in if vanadiniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("vanadiniteOreBlockList", new ArrayList<>(Arrays.asList()));
            VANADINITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Vanadinite Ore at (make sure it is less than the maximum)")
                    .defineInRange("vanadiniteOreMin", 0, 0, 256);
            VANADINITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Vanadinite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("vanadiniteOreMax", 20, 0, 256);
            VANADINITE_ORE_GENTYPE = b.comment("If false, vanadiniteOreChance will determine how many tries per chunk ore veins will generate. If true, vanadiniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("vanadiniteOreGentype",true);
            VANADINITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on vanadiniteOreGentype.")
                    .defineInRange("vanadiniteOreChance", 5, 0, 256);
            VANADINITE_ORE_SIZE = b.comment("The maximum size of Vanadinite Ore vein")
                    .defineInRange("vanadiniteOreSize", 24, 0, 500);
            VANADINITE_ORE_MEGA_GEN = b.comment("If true, a mega Vanadinite Ore vein will generate according to the parameters vanadiniteOreMegaVeinSize andvanadiniteOreMegaVeinChance")
                    .define("vanadiniteOreMegaVeins",false);
            VANADINITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Vanadinite Ore vein.")
                    .defineInRange("vanadiniteOreMegaVeinSize", 50, 0, 500);
            VANADINITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Vanadinite Ore vein will spawn.")
                    .defineInRange("vanadiniteOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.comment("Bismuthinite Ore Settings").push("bismuthiniteOre");
            BISMUTHINITE_ORE_HL = b.comment("Harvest Level of Bismuthinite Ore.")
                    .defineInRange("bismuthiniteOreHL", 3, 0, 4);
            BISMUTHINITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Bismuthinite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Bismuthinite Ore generation.")
                    .define("bismuthiniteOreDimList", new ArrayList<>(Arrays.asList("nether")));
            BISMUTHINITE_ORE_STONE_SPECIFIC = b.comment("If true Bismuthinite Ore will only spawn in the blocks listed under bismuthiniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in bismuthiniteOreDimList.")
                    .define("bismuthiniteOreStoneSpecific",false);
            BISMUTHINITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Bismuthinite Ore in if bismuthiniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("bismuthiniteOreBlockList", new ArrayList<>(Arrays.asList()));
            BISMUTHINITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Bismuthinite Ore at (make sure it is less than the maximum)")
                    .defineInRange("bismuthiniteOreMin", 45, 0, 256);
            BISMUTHINITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Bismuthinite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("bismuthiniteOreMax", 90, 0, 256);
            BISMUTHINITE_ORE_GENTYPE = b.comment("If false, bismuthiniteOreChance will determine how many tries per chunk ore veins will generate. If true, bismuthiniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("bismuthiniteOreGentype",true);
            BISMUTHINITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on bismuthiniteOreGentype.")
                    .defineInRange("bismuthiniteOreChance", 3, 0, 256);
            BISMUTHINITE_ORE_SIZE = b.comment("The maximum size of Bismuthinite Ore vein")
                    .defineInRange("bismuthiniteOreSize", 24, 0, 500);
            BISMUTHINITE_ORE_MEGA_GEN = b.comment("If true, a mega Bismuthinite Ore vein will generate according to the parameters bismuthiniteOreMegaVeinSize andbismuthiniteOreMegaVeinChance")
                    .define("bismuthiniteOreMegaVeins",false);
            BISMUTHINITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Bismuthinite Ore vein.")
                    .defineInRange("bismuthiniteOreMegaVeinSize", 50, 0, 500);
            BISMUTHINITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Bismuthinite Ore vein will spawn.")
                    .defineInRange("bismuthiniteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Acanthite Ore Settings").push("acanthiteOre");
            ACANTHITE_ORE_HL = b.comment("Harvest Level of Acanthite Ore.")
                    .defineInRange("acanthiteOreHL", 3, 0, 4);
            ACANTHITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Acanthite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Acanthite Ore generation.")
                    .define("acanthiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            ACANTHITE_ORE_STONE_SPECIFIC = b.comment("If true Acanthite Ore will only spawn in the blocks listed under acanthiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in acanthiteOreDimList.")
                    .define("acanthiteOreStoneSpecific",false);
            ACANTHITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Acanthite Ore in if acanthiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("acanthiteOreBlockList", new ArrayList<>(Arrays.asList()));
            ACANTHITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Acanthite Ore at (make sure it is less than the maximum)")
                    .defineInRange("acanthiteOreMin", 20, 0, 256);
            ACANTHITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Acanthite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("acanthiteOreMax", 40, 0, 256);
            ACANTHITE_ORE_GENTYPE = b.comment("If false, acanthiteOreChance will determine how many tries per chunk ore veins will generate. If true, acanthiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("acanthiteOreGentype",true);
            ACANTHITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on acanthiteOreGentype.")
                    .defineInRange("acanthiteOreChance", 3, 0, 256);
            ACANTHITE_ORE_SIZE = b.comment("The maximum size of Acanthite Ore vein")
                    .defineInRange("acanthiteOreSize", 24, 0, 500);
            ACANTHITE_ORE_MEGA_GEN = b.comment("If true, a mega Acanthite Ore vein will generate according to the parameters acanthiteOreMegaVeinSize andacanthiteOreMegaVeinChance")
                    .define("acanthiteOreMegaVeins",false);
            ACANTHITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Acanthite Ore vein.")
                    .defineInRange("acanthiteOreMegaVeinSize", 50, 0, 500);
            ACANTHITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Acanthite Ore vein will spawn.")
                    .defineInRange("acanthiteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Pyrolusite Ore Settings").push("pyrolusiteOre");
            PYROLUSITE_ORE_HL = b.comment("Harvest Level of Pyrolusite Ore.")
                    .defineInRange("pyrolusiteOreHL", 3, 0, 4);
            PYROLUSITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Pyrolusite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Pyrolusite Ore generation.")
                    .define("pyrolusiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            PYROLUSITE_ORE_STONE_SPECIFIC = b.comment("If true Pyrolusite Ore will only spawn in the blocks listed under pyrolusiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in pyrolusiteOreDimList.")
                    .define("pyrolusiteOreStoneSpecific",false);
            PYROLUSITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Pyrolusite Ore in if pyrolusiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("pyrolusiteOreBlockList", new ArrayList<>(Arrays.asList()));
            PYROLUSITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Pyrolusite Ore at (make sure it is less than the maximum)")
                    .defineInRange("pyrolusiteOreMin", 20, 0, 256);
            PYROLUSITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Pyrolusite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("pyrolusiteOreMax", 40, 0, 256);
            PYROLUSITE_ORE_GENTYPE = b.comment("If false, pyrolusiteOreChance will determine how many tries per chunk ore veins will generate. If true, pyrolusiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("pyrolusiteOreGentype",true);
            PYROLUSITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on pyrolusiteOreGentype.")
                    .defineInRange("pyrolusiteOreChance", 3, 0, 256);
            PYROLUSITE_ORE_SIZE = b.comment("The maximum size of Pyrolusite Ore vein")
                    .defineInRange("pyrolusiteOreSize", 24, 0, 500);
            PYROLUSITE_ORE_MEGA_GEN = b.comment("If true, a mega Pyrolusite Ore vein will generate according to the parameters pyrolusiteOreMegaVeinSize andpyrolusiteOreMegaVeinChance")
                    .define("pyrolusiteOreMegaVeins",false);
            PYROLUSITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Pyrolusite Ore vein.")
                    .defineInRange("pyrolusiteOreMegaVeinSize", 50, 0, 500);
            PYROLUSITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Pyrolusite Ore vein will spawn.")
                    .defineInRange("pyrolusiteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Chromite Ore Settings").push("chromiteOre");
            CHROMITE_ORE_HL = b.comment("Harvest Level of Chromite Ore.")
                    .defineInRange("chromiteOreHL", 4, 0, 4);
            CHROMITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Chromite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Chromite Ore generation.")
                    .define("chromiteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            CHROMITE_ORE_STONE_SPECIFIC = b.comment("If true Chromite Ore will only spawn in the blocks listed under chromiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in chromiteOreDimList.")
                    .define("chromiteOreStoneSpecific",false);
            CHROMITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Chromite Ore in if chromiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("chromiteOreBlockList", new ArrayList<>(Arrays.asList()));
            CHROMITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Chromite Ore at (make sure it is less than the maximum)")
                    .defineInRange("chromiteOreMin", 0, 0, 256);
            CHROMITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Chromite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("chromiteOreMax", 20, 0, 256);
            CHROMITE_ORE_GENTYPE = b.comment("If false, chromiteOreChance will determine how many tries per chunk ore veins will generate. If true, chromiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("chromiteOreGentype",true);
            CHROMITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on chromiteOreGentype.")
                    .defineInRange("chromiteOreChance", 4, 0, 256);
            CHROMITE_ORE_SIZE = b.comment("The maximum size of Chromite Ore vein")
                    .defineInRange("chromiteOreSize", 24, 0, 500);
            CHROMITE_ORE_MEGA_GEN = b.comment("If true, a mega Chromite Ore vein will generate according to the parameters chromiteOreMegaVeinSize andchromiteOreMegaVeinChance")
                    .define("chromiteOreMegaVeins",false);
            CHROMITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Chromite Ore vein.")
                    .defineInRange("chromiteOreMegaVeinSize", 50, 0, 500);
            CHROMITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Chromite Ore vein will spawn.")
                    .defineInRange("chromiteOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.comment("Molybdenite Ore Settings").push("molybdeniteOre");
            MOLYBDENITE_ORE_HL = b.comment("Harvest Level of Molybdenite Ore.")
                    .defineInRange("molybdeniteOreHL", 4, 0, 4);
            MOLYBDENITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Molybdenite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Molybdenite Ore generation.")
                    .define("molybdeniteOreDimList", new ArrayList<>(Arrays.asList("end")));
            MOLYBDENITE_ORE_STONE_SPECIFIC = b.comment("If true Molybdenite Ore will only spawn in the blocks listed under molybdeniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in molybdeniteOreDimList.")
                    .define("molybdeniteOreStoneSpecific",false);
            MOLYBDENITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Molybdenite Ore in if molybdeniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("molybdeniteOreBlockList", new ArrayList<>(Arrays.asList()));
            MOLYBDENITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Molybdenite Ore at (make sure it is less than the maximum)")
                    .defineInRange("molybdeniteOreMin", 10, 0, 256);
            MOLYBDENITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Molybdenite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("molybdeniteOreMax", 60, 0, 256);
            MOLYBDENITE_ORE_GENTYPE = b.comment("If false, molybdeniteOreChance will determine how many tries per chunk ore veins will generate. If true, molybdeniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("molybdeniteOreGentype",false);
            MOLYBDENITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on molybdeniteOreGentype.")
                    .defineInRange("molybdeniteOreChance", 6, 0, 256);
            MOLYBDENITE_ORE_SIZE = b.comment("The maximum size of Molybdenite Ore vein")
                    .defineInRange("molybdeniteOreSize", 6, 0, 500);
            MOLYBDENITE_ORE_MEGA_GEN = b.comment("If true, a mega Molybdenite Ore vein will generate according to the parameters molybdeniteOreMegaVeinSize andmolybdeniteOreMegaVeinChance")
                    .define("molybdeniteOreMegaVeins",false);
            MOLYBDENITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Molybdenite Ore vein.")
                    .defineInRange("molybdeniteOreMegaVeinSize", 0, 0, 500);
            MOLYBDENITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Molybdenite Ore vein will spawn.")
                    .defineInRange("molybdeniteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Ilmenite Ore Settings").push("ilmeniteOre");
            ILMENITE_ORE_HL = b.comment("Harvest Level of Ilmenite Ore.")
                    .defineInRange("ilmeniteOreHL", 4, 0, 4);
            ILMENITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Ilmenite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Ilmenite Ore generation.")
                    .define("ilmeniteOreDimList", new ArrayList<>(Arrays.asList("nether")));
            ILMENITE_ORE_STONE_SPECIFIC = b.comment("If true Ilmenite Ore will only spawn in the blocks listed under ilmeniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in ilmeniteOreDimList.")
                    .define("ilmeniteOreStoneSpecific",false);
            ILMENITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Ilmenite Ore in if ilmeniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("ilmeniteOreBlockList", new ArrayList<>(Arrays.asList()));
            ILMENITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Ilmenite Ore at (make sure it is less than the maximum)")
                    .defineInRange("ilmeniteOreMin", 90, 0, 256);
            ILMENITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Ilmenite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("ilmeniteOreMax", 127, 0, 256);
            ILMENITE_ORE_GENTYPE = b.comment("If false, ilmeniteOreChance will determine how many tries per chunk ore veins will generate. If true, ilmeniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("ilmeniteOreGentype",true);
            ILMENITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on ilmeniteOreGentype.")
                    .defineInRange("ilmeniteOreChance", 3, 0, 256);
            ILMENITE_ORE_SIZE = b.comment("The maximum size of Ilmenite Ore vein")
                    .defineInRange("ilmeniteOreSize", 24, 0, 500);
            ILMENITE_ORE_MEGA_GEN = b.comment("If true, a mega Ilmenite Ore vein will generate according to the parameters ilmeniteOreMegaVeinSize andilmeniteOreMegaVeinChance")
                    .define("ilmeniteOreMegaVeins",false);
            ILMENITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Ilmenite Ore vein.")
                    .defineInRange("ilmeniteOreMegaVeinSize", 50, 0, 500);
            ILMENITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Ilmenite Ore vein will spawn.")
                    .defineInRange("ilmeniteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Columbite Ore Settings").push("columbiteOre");
            COLUMBITE_ORE_HL = b.comment("Harvest Level of Columbite Ore.")
                    .defineInRange("columbiteOreHL", 4, 0, 4);
            COLUMBITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Columbite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Columbite Ore generation.")
                    .define("columbiteOreDimList", new ArrayList<>(Arrays.asList("nether")));
            COLUMBITE_ORE_STONE_SPECIFIC = b.comment("If true Columbite Ore will only spawn in the blocks listed under columbiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in columbiteOreDimList.")
                    .define("columbiteOreStoneSpecific",false);
            COLUMBITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Columbite Ore in if columbiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("columbiteOreBlockList", new ArrayList<>(Arrays.asList()));
            COLUMBITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Columbite Ore at (make sure it is less than the maximum)")
                    .defineInRange("columbiteOreMin", 10, 0, 256);
            COLUMBITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Columbite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("columbiteOreMax", 45, 0, 256);
            COLUMBITE_ORE_GENTYPE = b.comment("If false, columbiteOreChance will determine how many tries per chunk ore veins will generate. If true, columbiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("columbiteOreGentype",true);
            COLUMBITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on columbiteOreGentype.")
                    .defineInRange("columbiteOreChance", 3, 0, 256);
            COLUMBITE_ORE_SIZE = b.comment("The maximum size of Columbite Ore vein")
                    .defineInRange("columbiteOreSize", 24, 0, 500);
            COLUMBITE_ORE_MEGA_GEN = b.comment("If true, a mega Columbite Ore vein will generate according to the parameters columbiteOreMegaVeinSize andcolumbiteOreMegaVeinChance")
                    .define("columbiteOreMegaVeins",false);
            COLUMBITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Columbite Ore vein.")
                    .defineInRange("columbiteOreMegaVeinSize", 50, 0, 500);
            COLUMBITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Columbite Ore vein will spawn.")
                    .defineInRange("columbiteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Wolframite Ore Settings").push("wolframiteOre");
            WOLFRAMITE_ORE_HL = b.comment("Harvest Level of Wolframite Ore.")
                    .defineInRange("wolframiteOreHL", 4, 0, 4);
            WOLFRAMITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Wolframite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Wolframite Ore generation.")
                    .define("wolframiteOreDimList", new ArrayList<>(Arrays.asList("nether")));
            WOLFRAMITE_ORE_STONE_SPECIFIC = b.comment("If true Wolframite Ore will only spawn in the blocks listed under wolframiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in wolframiteOreDimList.")
                    .define("wolframiteOreStoneSpecific",false);
            WOLFRAMITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Wolframite Ore in if wolframiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("wolframiteOreBlockList", new ArrayList<>(Arrays.asList()));
            WOLFRAMITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Wolframite Ore at (make sure it is less than the maximum)")
                    .defineInRange("wolframiteOreMin", 90, 0, 256);
            WOLFRAMITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Wolframite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("wolframiteOreMax", 127, 0, 256);
            WOLFRAMITE_ORE_GENTYPE = b.comment("If false, wolframiteOreChance will determine how many tries per chunk ore veins will generate. If true, wolframiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("wolframiteOreGentype",true);
            WOLFRAMITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on wolframiteOreGentype.")
                    .defineInRange("wolframiteOreChance", 3, 0, 256);
            WOLFRAMITE_ORE_SIZE = b.comment("The maximum size of Wolframite Ore vein")
                    .defineInRange("wolframiteOreSize", 24, 0, 500);
            WOLFRAMITE_ORE_MEGA_GEN = b.comment("If true, a mega Wolframite Ore vein will generate according to the parameters wolframiteOreMegaVeinSize andwolframiteOreMegaVeinChance")
                    .define("wolframiteOreMegaVeins",false);
            WOLFRAMITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Wolframite Ore vein.")
                    .defineInRange("wolframiteOreMegaVeinSize", 50, 0, 500);
            WOLFRAMITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Wolframite Ore vein will spawn.")
                    .defineInRange("wolframiteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Rheniite Ore Settings").push("rheniiteOre");
            RHENIITE_ORE_HL = b.comment("Harvest Level of Rheniite Ore.")
                    .defineInRange("rheniiteOreHL", 4, 0, 4);
            RHENIITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Rheniite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Rheniite Ore generation.")
                    .define("rheniiteOreDimList", new ArrayList<>(Arrays.asList("end")));
            RHENIITE_ORE_STONE_SPECIFIC = b.comment("If true Rheniite Ore will only spawn in the blocks listed under rheniiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in rheniiteOreDimList.")
                    .define("rheniiteOreStoneSpecific",false);
            RHENIITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Rheniite Ore in if rheniiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("rheniiteOreBlockList", new ArrayList<>(Arrays.asList()));
            RHENIITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Rheniite Ore at (make sure it is less than the maximum)")
                    .defineInRange("rheniiteOreMin", 10, 0, 256);
            RHENIITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Rheniite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("rheniiteOreMax", 60, 0, 256);
            RHENIITE_ORE_GENTYPE = b.comment("If false, rheniiteOreChance will determine how many tries per chunk ore veins will generate. If true, rheniiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("rheniiteOreGentype",false);
            RHENIITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on rheniiteOreGentype.")
                    .defineInRange("rheniiteOreChance", 1, 0, 256);
            RHENIITE_ORE_SIZE = b.comment("The maximum size of Rheniite Ore vein")
                    .defineInRange("rheniiteOreSize", 5, 0, 500);
            RHENIITE_ORE_MEGA_GEN = b.comment("If true, a mega Rheniite Ore vein will generate according to the parameters rheniiteOreMegaVeinSize andrheniiteOreMegaVeinChance")
                    .define("rheniiteOreMegaVeins",false);
            RHENIITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Rheniite Ore vein.")
                    .defineInRange("rheniiteOreMegaVeinSize", 50, 0, 500);
            RHENIITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Rheniite Ore vein will spawn.")
                    .defineInRange("rheniiteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Tantalite Ore Settings").push("tantaliteOre");
            TANTALITE_ORE_HL = b.comment("Harvest Level of Tantalite Ore.")
                    .defineInRange("tantaliteOreHL", 4, 0, 4);
            TANTALITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Tantalite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Tantalite Ore generation.")
                    .define("tantaliteOreDimList", new ArrayList<>(Arrays.asList("nether")));
            TANTALITE_ORE_STONE_SPECIFIC = b.comment("If true Tantalite Ore will only spawn in the blocks listed under tantaliteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in tantaliteOreDimList.")
                    .define("tantaliteOreStoneSpecific",false);
            TANTALITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Tantalite Ore in if tantaliteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("tantaliteOreBlockList", new ArrayList<>(Arrays.asList()));
            TANTALITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Tantalite Ore at (make sure it is less than the maximum)")
                    .defineInRange("tantaliteOreMin", 10, 0, 256);
            TANTALITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Tantalite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("tantaliteOreMax", 45, 0, 256);
            TANTALITE_ORE_GENTYPE = b.comment("If false, tantaliteOreChance will determine how many tries per chunk ore veins will generate. If true, tantaliteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("tantaliteOreGentype",true);
            TANTALITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on tantaliteOreGentype.")
                    .defineInRange("tantaliteOreChance", 3, 0, 256);
            TANTALITE_ORE_SIZE = b.comment("The maximum size of Tantalite Ore vein")
                    .defineInRange("tantaliteOreSize", 24, 0, 500);
            TANTALITE_ORE_MEGA_GEN = b.comment("If true, a mega Tantalite Ore vein will generate according to the parameters tantaliteOreMegaVeinSize andtantaliteOreMegaVeinChance")
                    .define("tantaliteOreMegaVeins",false);
            TANTALITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Tantalite Ore vein.")
                    .defineInRange("tantaliteOreMegaVeinSize", 50, 0, 500);
            TANTALITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Tantalite Ore vein will spawn.")
                    .defineInRange("tantaliteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Plumbago Ore Settings").push("plumbagoOre");
            PLUMBAGO_ORE_HL = b.comment("Harvest Level of Plumbago Ore.")
                    .defineInRange("plumbagoOreHL", 3, 0, 4);
            PLUMBAGO_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Plumbago Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Plumbago Ore generation.")
                    .define("plumbagoOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            PLUMBAGO_ORE_STONE_SPECIFIC = b.comment("If true Plumbago Ore will only spawn in the blocks listed under plumbagoOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in plumbagoOreDimList.")
                    .define("plumbagoOreStoneSpecific",false);
            PLUMBAGO_ORE_BLOCK_LIST = b.comment("Blocks to generate Plumbago Ore in if plumbagoOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("plumbagoOreBlockList", new ArrayList<>(Arrays.asList()));
            PLUMBAGO_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Plumbago Ore at (make sure it is less than the maximum)")
                    .defineInRange("plumbagoOreMin", 0, 0, 256);
            PLUMBAGO_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Plumbago Ore at (make sure it is greater than the minimum)")
                    .defineInRange("plumbagoOreMax", 20, 0, 256);
            PLUMBAGO_ORE_GENTYPE = b.comment("If false, plumbagoOreChance will determine how many tries per chunk ore veins will generate. If true, plumbagoOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("plumbagoOreGentype",true);
            PLUMBAGO_ORE_CHANCE = b.comment("Count / chance number to be used depending on plumbagoOreGentype.")
                    .defineInRange("plumbagoOreChance", 1, 0, 256);
            PLUMBAGO_ORE_SIZE = b.comment("The maximum size of Plumbago Ore vein")
                    .defineInRange("plumbagoOreSize", 12, 0, 500);
            PLUMBAGO_ORE_MEGA_GEN = b.comment("If true, a mega Plumbago Ore vein will generate according to the parameters plumbagoOreMegaVeinSize andplumbagoOreMegaVeinChance")
                    .define("plumbagoOreMegaVeins",false);
            PLUMBAGO_ORE_MEGA_SIZE = b.comment("The maximum size of mega Plumbago Ore vein.")
                    .defineInRange("plumbagoOreMegaVeinSize", 40, 0, 500);
            PLUMBAGO_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Plumbago Ore vein will spawn.")
                    .defineInRange("plumbagoOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.comment("Moissanite Ore Settings").push("moissaniteOre");
            MOISSANITE_ORE_HL = b.comment("Harvest Level of Moissanite Ore.")
                    .defineInRange("moissaniteOreHL", 3, 0, 4);
            MOISSANITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Moissanite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Moissanite Ore generation.")
                    .define("moissaniteOreDimList", new ArrayList<>(Arrays.asList("nether")));
            MOISSANITE_ORE_STONE_SPECIFIC = b.comment("If true Moissanite Ore will only spawn in the blocks listed under moissaniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in moissaniteOreDimList.")
                    .define("moissaniteOreStoneSpecific",false);
            MOISSANITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Moissanite Ore in if moissaniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("moissaniteOreBlockList", new ArrayList<>(Arrays.asList()));
            MOISSANITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Moissanite Ore at (make sure it is less than the maximum)")
                    .defineInRange("moissaniteOreMin", 45, 0, 256);
            MOISSANITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Moissanite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("moissaniteOreMax", 90, 0, 256);
            MOISSANITE_ORE_GENTYPE = b.comment("If false, moissaniteOreChance will determine how many tries per chunk ore veins will generate. If true, moissaniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("moissaniteOreGentype",true);
            MOISSANITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on moissaniteOreGentype.")
                    .defineInRange("moissaniteOreChance", 2, 0, 256);
            MOISSANITE_ORE_SIZE = b.comment("The maximum size of Moissanite Ore vein")
                    .defineInRange("moissaniteOreSize", 24, 0, 500);
            MOISSANITE_ORE_MEGA_GEN = b.comment("If true, a mega Moissanite Ore vein will generate according to the parameters moissaniteOreMegaVeinSize andmoissaniteOreMegaVeinChance")
                    .define("moissaniteOreMegaVeins",false);
            MOISSANITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Moissanite Ore vein.")
                    .defineInRange("moissaniteOreMegaVeinSize", 50, 0, 500);
            MOISSANITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Moissanite Ore vein will spawn.")
                    .defineInRange("moissaniteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Sperrylite Ore Settings").push("sperryliteOre");
            SPERRYLITE_ORE_HL = b.comment("Harvest Level of Sperrylite Ore.")
                    .defineInRange("sperryliteOreHL", 4, 0, 4);
            SPERRYLITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Sperrylite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Sperrylite Ore generation.")
                    .define("sperryliteOreDimList", new ArrayList<>(Arrays.asList("nether")));
            SPERRYLITE_ORE_STONE_SPECIFIC = b.comment("If true Sperrylite Ore will only spawn in the blocks listed under sperryliteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in sperryliteOreDimList.")
                    .define("sperryliteOreStoneSpecific",false);
            SPERRYLITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Sperrylite Ore in if sperryliteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("sperryliteOreBlockList", new ArrayList<>(Arrays.asList()));
            SPERRYLITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Sperrylite Ore at (make sure it is less than the maximum)")
                    .defineInRange("sperryliteOreMin", 90, 0, 256);
            SPERRYLITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Sperrylite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("sperryliteOreMax", 127, 0, 256);
            SPERRYLITE_ORE_GENTYPE = b.comment("If false, sperryliteOreChance will determine how many tries per chunk ore veins will generate. If true, sperryliteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("sperryliteOreGentype",true);
            SPERRYLITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on sperryliteOreGentype.")
                    .defineInRange("sperryliteOreChance", 3, 0, 256);
            SPERRYLITE_ORE_SIZE = b.comment("The maximum size of Sperrylite Ore vein")
                    .defineInRange("sperryliteOreSize", 24, 0, 500);
            SPERRYLITE_ORE_MEGA_GEN = b.comment("If true, a mega Sperrylite Ore vein will generate according to the parameters sperryliteOreMegaVeinSize andsperryliteOreMegaVeinChance")
                    .define("sperryliteOreMegaVeins",false);
            SPERRYLITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Sperrylite Ore vein.")
                    .defineInRange("berrylikeOreMegaVeinSize", 50, 0, 500);
            SPERRYLITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Sperrylite Ore vein will spawn.")
                    .defineInRange("sperryliteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Lignite Ore Settings").push("ligniteOre");
            LIGNITE_ORE_HL = b.comment("Harvest Level of Lignite Ore.")
                    .defineInRange("ligniteOreHL", 0, 0, 4);
            LIGNITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Lignite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Lignite Ore generation.")
                    .define("ligniteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            LIGNITE_ORE_STONE_SPECIFIC = b.comment("If true Lignite Ore will only spawn in the blocks listed under ligniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in ligniteOreDimList.")
                    .define("ligniteOreStoneSpecific",false);
            LIGNITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Lignite Ore in if ligniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("ligniteOreBlockList", new ArrayList<>(Arrays.asList()));
            LIGNITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Lignite Ore at (make sure it is less than the maximum)")
                    .defineInRange("ligniteOreMin", 50, 0, 256);
            LIGNITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Lignite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("ligniteOreMax", 80, 0, 256);
            LIGNITE_ORE_GENTYPE = b.comment("If false, ligniteOreChance will determine how many tries per chunk ore veins will generate. If true, ligniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("ligniteOreGentype",true);
            LIGNITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on ligniteOreGentype.")
                    .defineInRange("ligniteOreChance", 1, 0, 256);
            LIGNITE_ORE_SIZE = b.comment("The maximum size of Lignite Ore vein")
                    .defineInRange("ligniteOreSize", 24, 0, 500);
            LIGNITE_ORE_MEGA_GEN = b.comment("If true, a mega Lignite Ore vein will generate according to the parameters ligniteOreMegaVeinSize andligniteOreMegaVeinChance")
                    .define("ligniteOreMegaVeins",false);
            LIGNITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Lignite Ore vein.")
                    .defineInRange("ligniteOreMegaVeinSize", 50, 0, 500);
            LIGNITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Lignite Ore vein will spawn.")
                    .defineInRange("ligniteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Subbituminous Ore Settings").push("subbituminousOre");
            SUBBITUMINOUS_ORE_HL = b.comment("Harvest Level of Subbituminous Ore.")
                    .defineInRange("subbituminousOreHL", 2, 0, 4);
            SUBBITUMINOUS_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Subbituminous Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Subbituminous Ore generation.")
                    .define("subbituminousOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            SUBBITUMINOUS_ORE_STONE_SPECIFIC = b.comment("If true Subbituminous Ore will only spawn in the blocks listed under subbituminousOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in subbituminousOreDimList.")
                    .define("subbituminousOreStoneSpecific",false);
            SUBBITUMINOUS_ORE_BLOCK_LIST = b.comment("Blocks to generate Subbituminous Ore in if subbituminousOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("subbituminousOreBlockList", new ArrayList<>(Arrays.asList()));
            SUBBITUMINOUS_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Subbituminous Ore at (make sure it is less than the maximum)")
                    .defineInRange("subbituminousOreMin", 30, 0, 256);
            SUBBITUMINOUS_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Subbituminous Ore at (make sure it is greater than the minimum)")
                    .defineInRange("subbituminousOreMax", 50, 0, 256);
            SUBBITUMINOUS_ORE_GENTYPE = b.comment("If false, subbituminousOreChance will determine how many tries per chunk ore veins will generate. If true, subbituminousOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("subbituminousOreGentype",true);
            SUBBITUMINOUS_ORE_CHANCE = b.comment("Count / chance number to be used depending on subbituminousOreGentype.")
                    .defineInRange("subbituminousOreChance", 1, 0, 256);
            SUBBITUMINOUS_ORE_SIZE = b.comment("The maximum size of Subbituminous Ore vein")
                    .defineInRange("subbituminousOreSize", 24, 0, 500);
            SUBBITUMINOUS_ORE_MEGA_GEN = b.comment("If true, a mega Subbituminous Ore vein will generate according to the parameters subbituminousOreMegaVeinSize andsubbituminousOreMegaVeinChance")
                    .define("subbituminousOreMegaVeins",false);
            SUBBITUMINOUS_ORE_MEGA_SIZE = b.comment("The maximum size of mega Subbituminous Ore vein.")
                    .defineInRange("subbituminousOreMegaVeinSize", 50, 0, 500);
            SUBBITUMINOUS_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Subbituminous Ore vein will spawn.")
                    .defineInRange("subbituminousOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Bituminous Ore Settings").push("bituminousOre");
            BITUMINOUS_ORE_HL = b.comment("Harvest Level of Bituminous Ore.")
                    .defineInRange("bituminousOreHL", 3, 0, 4);
            BITUMINOUS_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Bituminous Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Bituminous Ore generation.")
                    .define("bituminousOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            BITUMINOUS_ORE_STONE_SPECIFIC = b.comment("If true Bituminous Ore will only spawn in the blocks listed under bituminousOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in bituminousOreDimList.")
                    .define("bituminousOreStoneSpecific",false);
            BITUMINOUS_ORE_BLOCK_LIST = b.comment("Blocks to generate Bituminous Ore in if bituminousOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("bituminousOreBlockList", new ArrayList<>(Arrays.asList()));
            BITUMINOUS_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Bituminous Ore at (make sure it is less than the maximum)")
                    .defineInRange("bituminousOreMin", 0, 0, 256);
            BITUMINOUS_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Bituminous Ore at (make sure it is greater than the minimum)")
                    .defineInRange("bituminousOreMax", 30, 0, 256);
            BITUMINOUS_ORE_GENTYPE = b.comment("If false, bituminousOreChance will determine how many tries per chunk ore veins will generate. If true, bituminousOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("bituminousOreGentype",true);
            BITUMINOUS_ORE_CHANCE = b.comment("Count / chance number to be used depending on bituminousOreGentype.")
                    .defineInRange("bituminousOreChance", 1, 0, 256);
            BITUMINOUS_ORE_SIZE = b.comment("The maximum size of Bituminous Ore vein")
                    .defineInRange("bituminousOreSize", 24, 0, 500);
            BITUMINOUS_ORE_MEGA_GEN = b.comment("If true, a mega Bituminous Ore vein will generate according to the parameters bituminousOreMegaVeinSize andbituminousOreMegaVeinChance")
                    .define("bituminousOreMegaVeins",false);
            BITUMINOUS_ORE_MEGA_SIZE = b.comment("The maximum size of mega Bituminous Ore vein.")
                    .defineInRange("bituminousOreMegaVeinSize", 50, 0, 500);
            BITUMINOUS_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Bituminous Ore vein will spawn.")
                    .defineInRange("bituminousOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Anthracite Ore Settings").push("anthraciteOre");
            ANTHRACITE_ORE_HL = b.comment("Harvest Level of Anthracite Ore.")
                    .defineInRange("anthraciteOreHL", 4, 0, 4);
            ANTHRACITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Anthracite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Anthracite Ore generation.")
                    .define("anthraciteOreDimList", new ArrayList<>(Arrays.asList("nether")));
            ANTHRACITE_ORE_STONE_SPECIFIC = b.comment("If true Anthracite Ore will only spawn in the blocks listed under anthraciteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in anthraciteOreDimList.")
                    .define("anthraciteOreStoneSpecific",false);
            ANTHRACITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Anthracite Ore in if anthraciteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("anthraciteOreBlockList", new ArrayList<>(Arrays.asList()));
            ANTHRACITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Anthracite Ore at (make sure it is less than the maximum)")
                    .defineInRange("anthraciteOreMin", 45, 0, 256);
            ANTHRACITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Anthracite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("anthraciteOreMax", 90, 0, 256);
            ANTHRACITE_ORE_GENTYPE = b.comment("If false, anthraciteOreChance will determine how many tries per chunk ore veins will generate. If true, anthraciteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("anthraciteOreGentype",true);
            ANTHRACITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on anthraciteOreGentype.")
                    .defineInRange("anthraciteOreChance", 3, 0, 256);
            ANTHRACITE_ORE_SIZE = b.comment("The maximum size of Anthracite Ore vein")
                    .defineInRange("anthraciteOreSize", 24, 0, 500);
            ANTHRACITE_ORE_MEGA_GEN = b.comment("If true, a mega Anthracite Ore vein will generate according to the parameters anthraciteOreMegaVeinSize andanthraciteOreMegaVeinChance")
                    .define("anthraciteOreMegaVeins",false);
            ANTHRACITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Anthracite Ore vein.")
                    .defineInRange("anthraciteOreMegaVeinSize", 50, 0, 500);
            ANTHRACITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Anthracite Ore vein will spawn.")
                    .defineInRange("anthraciteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Lazurite Ore Settings").push("lazuriteOre");
            LAZURITE_ORE_HL = b.comment("Harvest Level of Lazurite Ore.")
                    .defineInRange("lazuriteOreHL", 2, 0, 4);
            LAZURITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Lazurite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Lazurite Ore generation.")
                    .define("lazuriteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            LAZURITE_ORE_STONE_SPECIFIC = b.comment("If true Lazurite Ore will only spawn in the blocks listed under lazuriteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in lazuriteOreDimList.")
                    .define("lazuriteOreStoneSpecific",false);
            LAZURITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Lazurite Ore in if lazuriteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("lazuriteOreBlockList", new ArrayList<>(Arrays.asList()));
            LAZURITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Lazurite Ore at (make sure it is less than the maximum)")
                    .defineInRange("lazuriteOreMin", 10, 0, 256);
            LAZURITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Lazurite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("lazuriteOreMax", 50, 0, 256);
            LAZURITE_ORE_GENTYPE = b.comment("If false, lazuriteOreChance will determine how many tries per chunk ore veins will generate. If true, lazuriteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("lazuriteOreGentype",true);
            LAZURITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on lazuriteOreGentype.")
                    .defineInRange("lazuriteOreChance", 2, 0, 256);
            LAZURITE_ORE_SIZE = b.comment("The maximum size of Lazurite Ore vein")
                    .defineInRange("lazuriteOreSize", 16, 0, 500);
            LAZURITE_ORE_MEGA_GEN = b.comment("If true, a mega Lazurite Ore vein will generate according to the parameters lazuriteOreMegaVeinSize andlazuriteOreMegaVeinChance")
                    .define("lazuriteOreMegaVeins",false);
            LAZURITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Lazurite Ore vein.")
                    .defineInRange("lazuriteOreMegaVeinSize", 50, 0, 500);
            LAZURITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Lazurite Ore vein will spawn.")
                    .defineInRange("lazuriteOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Diamond Ore Settings").push("diamondOre");
            DIAMOND_ORE_HL = b.comment("Harvest Level of Diamond Ore.")
                    .defineInRange("diamondOreHL", 4, 0, 4);
            DIAMOND_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Diamond Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Diamond Ore generation.")
                    .define("diamondOreDimList", new ArrayList<>(Arrays.asList("")));
            DIAMOND_ORE_STONE_SPECIFIC = b.comment("If true Diamond Ore will only spawn in the blocks listed under diamondOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in diamondOreDimList.")
                    .define("diamondOreStoneSpecific",false);
            DIAMOND_ORE_BLOCK_LIST = b.comment("Blocks to generate Diamond Ore in if diamondOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("diamondOreBlockList", new ArrayList<>(Arrays.asList()));
            DIAMOND_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Diamond Ore at (make sure it is less than the maximum)")
                    .defineInRange("diamondOreMin", 40, 0, 256);
            DIAMOND_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Diamond Ore at (make sure it is greater than the minimum)")
                    .defineInRange("diamondOreMax", 60, 0, 256);
            DIAMOND_ORE_GENTYPE = b.comment("If false, diamondOreChance will determine how many tries per chunk ore veins will generate. If true, diamondOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("diamondOreGentype",true);
            DIAMOND_ORE_CHANCE = b.comment("Count / chance number to be used depending on diamondOreGentype.")
                    .defineInRange("diamondOreChance", 3, 0, 256);
            DIAMOND_ORE_SIZE = b.comment("The maximum size of Diamond Ore vein")
                    .defineInRange("diamondOreSize", 24, 0, 500);
            DIAMOND_ORE_MEGA_GEN = b.comment("If true, a mega Diamond Ore vein will generate according to the parameters diamondOreMegaVeinSize anddiamondOreMegaVeinChance")
                    .define("diamondOreMegaVeins",false);
            DIAMOND_ORE_MEGA_SIZE = b.comment("The maximum size of mega Diamond Ore vein.")
                    .defineInRange("diamondOreMegaVeinSize", 50, 0, 500);
            DIAMOND_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Diamond Ore vein will spawn.")
                    .defineInRange("diamondOreMegaVeinChance", 40, 0, 500);
            b.pop();

            b.comment("Greenockite Ore Settings").push("greenockiteOre");
            GREENOCKITE_ORE_HL = b.comment("Harvest Level of Greenockite Ore.")
                    .defineInRange("greenockiteOreHL", 4, 0, 4);
            GREENOCKITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Greenockite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Greenockite Ore generation.")
                    .define("greenockiteOreDimList", new ArrayList<>(Arrays.asList("end")));
            GREENOCKITE_ORE_STONE_SPECIFIC = b.comment("If true Greenockite Ore will only spawn in the blocks listed under greenockiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in greenockiteOreDimList.")
                    .define("greenockiteOreStoneSpecific",false);
            GREENOCKITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Greenockite Ore in if greenockiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("greenockiteOreBlockList", new ArrayList<>(Arrays.asList()));
            GREENOCKITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Greenockite Ore at (make sure it is less than the maximum)")
                    .defineInRange("greenockiteOreMin", 10, 0, 256);
            GREENOCKITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Greenockite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("greenockiteOreMax", 60, 0, 256);
            GREENOCKITE_ORE_GENTYPE = b.comment("If false, greenockiteOreChance will determine how many tries per chunk ore veins will generate. If true, greenockiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("greenockiteOreGentype",false);
            GREENOCKITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on greenockiteOreGentype.")
                    .defineInRange("greenockiteOreChance", 6, 0, 256);
            GREENOCKITE_ORE_SIZE = b.comment("The maximum size of Greenockite Ore vein")
                    .defineInRange("greenockiteOreSize", 6, 0, 500);
            GREENOCKITE_ORE_MEGA_GEN = b.comment("If true, a mega Greenockite Ore vein will generate according to the parameters greenockiteOreMegaVeinSize andgreenockiteOreMegaVeinChance")
                    .define("greenockiteOreMegaVeins",false);
            GREENOCKITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Greenockite Ore vein.")
                    .defineInRange("greenockiteOreMegaVeinSize", 70, 0, 500);
            GREENOCKITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Greenockite Ore vein will spawn.")
                    .defineInRange("greenockiteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Emerald Ore Settings").push("emeraldOre");
            EMERALD_ORE_HL = b.comment("Harvest Level of Emerald Ore.")
                    .defineInRange("emeraldOreHL", 3, 0, 4);
            EMERALD_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Emerald Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Emerald Ore generation.")
                    .define("emeraldOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            EMERALD_ORE_STONE_SPECIFIC = b.comment("If true Emerald Ore will only spawn in the blocks listed under emeraldOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in emeraldOreDimList.")
                    .define("emeraldOreStoneSpecific",false);
            EMERALD_ORE_BLOCK_LIST = b.comment("Blocks to generate Emerald Ore in if emeraldOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("emeraldOreBlockList", new ArrayList<>(Arrays.asList()));
            EMERALD_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Emerald Ore at (make sure it is less than the maximum)")
                    .defineInRange("emeraldOreMin", 10, 0, 256);
            EMERALD_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Emerald Ore at (make sure it is greater than the minimum)")
                    .defineInRange("emeraldOreMax", 50, 0, 256);
            EMERALD_ORE_GENTYPE = b.comment("If false, emeraldOreChance will determine how many tries per chunk ore veins will generate. If true, emeraldOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("emeraldOreGentype",false);
            EMERALD_ORE_CHANCE = b.comment("Count / chance number to be used depending on emeraldOreGentype.")
                    .defineInRange("emeraldOreChance", 2, 0, 256);
            EMERALD_ORE_SIZE = b.comment("The maximum size of Emerald Ore vein")
                    .defineInRange("emeraldOreSize", 5, 0, 500);
            EMERALD_ORE_MEGA_GEN = b.comment("If true, a mega Emerald Ore vein will generate according to the parameters emeraldOreMegaVeinSize andemeraldOreMegaVeinChance")
                    .define("emeraldOreMegaVeins",false);
            EMERALD_ORE_MEGA_SIZE = b.comment("The maximum size of mega Emerald Ore vein.")
                    .defineInRange("emeraldOreMegaVeinSize", 70, 0, 500);
            EMERALD_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Emerald Ore vein will spawn.")
                    .defineInRange("emeraldOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Aquamarine Ore Settings").push("aquamarineOre");
            AQUAMARINE_ORE_HL = b.comment("Harvest Level of Aquamarine Ore.")
                    .defineInRange("aquamarineOreHL", 3, 0, 4);
            AQUAMARINE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Aquamarine Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Aquamarine Ore generation.")
                    .define("aquamarineOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            AQUAMARINE_ORE_STONE_SPECIFIC = b.comment("If true Aquamarine Ore will only spawn in the blocks listed under aquamarineOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in aquamarineOreDimList.")
                    .define("aquamarineOreStoneSpecific",false);
            AQUAMARINE_ORE_BLOCK_LIST = b.comment("Blocks to generate Aquamarine Ore in if aquamarineOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("aquamarineOreBlockList", new ArrayList<>(Arrays.asList()));
            AQUAMARINE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Aquamarine Ore at (make sure it is less than the maximum)")
                    .defineInRange("aquamarineOreMin", 10, 0, 256);
            AQUAMARINE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Aquamarine Ore at (make sure it is greater than the minimum)")
                    .defineInRange("aquamarineOreMax", 50, 0, 256);
            AQUAMARINE_ORE_GENTYPE = b.comment("If false, aquamarineOreChance will determine how many tries per chunk ore veins will generate. If true, aquamarineOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("aquamarineOreGentype",true);
            AQUAMARINE_ORE_CHANCE = b.comment("Count / chance number to be used depending on aquamarineOreGentype.")
                    .defineInRange("aquamarineOreChance", 2, 0, 256);
            AQUAMARINE_ORE_SIZE = b.comment("The maximum size of Aquamarine Ore vein")
                    .defineInRange("aquamarineOreSize", 5, 0, 500);
            AQUAMARINE_ORE_MEGA_GEN = b.comment("If true, a mega Aquamarine Ore vein will generate according to the parameters aquamarineOreMegaVeinSize andaquamarineOreMegaVeinChance")
                    .define("aquamarineOreMegaVeins",false);
            AQUAMARINE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Aquamarine Ore vein.")
                    .defineInRange("aquamarineOreMegaVeinSize", 70, 0, 500);
            AQUAMARINE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Aquamarine Ore vein will spawn.")
                    .defineInRange("aquamarineOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Quartz Ore Settings").push("quartzOre");
            QUARTZ_ORE_HL = b.comment("Harvest Level of Quartz Ore.")
                    .defineInRange("quartzOreHL", 2, 0, 4);
            QUARTZ_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Quartz Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Quartz Ore generation.")
                    .define("quartzOreDimList", new ArrayList<>(Arrays.asList("nether")));
            QUARTZ_ORE_STONE_SPECIFIC = b.comment("If true Quartz Ore will only spawn in the blocks listed under quartzOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in quartzOreDimList.")
                    .define("quartzOreStoneSpecific",false);
            QUARTZ_ORE_BLOCK_LIST = b.comment("Blocks to generate Quartz Ore in if quartzOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("quartzOreBlockList", new ArrayList<>(Arrays.asList()));
            QUARTZ_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Quartz Ore at (make sure it is less than the maximum)")
                    .defineInRange("quartzOreMin", 0, 0, 256);
            QUARTZ_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Quartz Ore at (make sure it is greater than the minimum)")
                    .defineInRange("quartzOreMax", 127, 0, 256);
            QUARTZ_ORE_GENTYPE = b.comment("If false, quartzOreChance will determine how many tries per chunk ore veins will generate. If true, quartzOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("quartzOreGentype",false);
            QUARTZ_ORE_CHANCE = b.comment("Count / chance number to be used depending on quartzOreGentype.")
                    .defineInRange("quartzOreChance", 5, 0, 256);
            QUARTZ_ORE_SIZE = b.comment("The maximum size of Quartz Ore vein")
                    .defineInRange("quartzOreSize", 24, 0, 500);
            QUARTZ_ORE_MEGA_GEN = b.comment("If true, a mega Quartz Ore vein will generate according to the parameters quartzOreMegaVeinSize andquartzOreMegaVeinChance")
                    .define("quartzOreMegaVeins",false);
            QUARTZ_ORE_MEGA_SIZE = b.comment("The maximum size of mega Quartz Ore vein.")
                    .defineInRange("quartzOreMegaVeinSize", 70, 0, 500);
            QUARTZ_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Quartz Ore vein will spawn.")
                    .defineInRange("quartzOreMegaVeinChance", 4, 0, 500);
            b.pop();

            b.comment("Opal Ore Settings").push("opalOre");
            OPAL_ORE_HL = b.comment("Harvest Level of Opal Ore.")
                    .defineInRange("opalOreHL", 3, 0, 4);
            OPAL_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Opal Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Opal Ore generation.")
                    .define("opalOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            OPAL_ORE_STONE_SPECIFIC = b.comment("If true Opal Ore will only spawn in the blocks listed under opalOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in opalOreDimList.")
                    .define("opalOreStoneSpecific",false);
            OPAL_ORE_BLOCK_LIST = b.comment("Blocks to generate Opal Ore in if opalOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("opalOreBlockList", new ArrayList<>(Arrays.asList()));
            OPAL_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Opal Ore at (make sure it is less than the maximum)")
                    .defineInRange("opalOreMin", 10, 0, 256);
            OPAL_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Opal Ore at (make sure it is greater than the minimum)")
                    .defineInRange("opalOreMax", 50, 0, 256);
            OPAL_ORE_GENTYPE = b.comment("If false, opalOreChance will determine how many tries per chunk ore veins will generate. If true, opalOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("opalOreGentype",false);
            OPAL_ORE_CHANCE = b.comment("Count / chance number to be used depending on opalOreGentype.")
                    .defineInRange("opalOreChance", 2, 0, 256);
            OPAL_ORE_SIZE = b.comment("The maximum size of Opal Ore vein")
                    .defineInRange("opalOreSize", 5, 0, 500);
            OPAL_ORE_MEGA_GEN = b.comment("If true, a mega Opal Ore vein will generate according to the parameters opalOreMegaVeinSize andopalOreMegaVeinChance")
                    .define("opalOreMegaVeins",false);
            OPAL_ORE_MEGA_SIZE = b.comment("The maximum size of mega Opal Ore vein.")
                    .defineInRange("opalOreMegaVeinSize", 70, 0, 500);
            OPAL_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Opal Ore vein will spawn.")
                    .defineInRange("opalOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Majorite Ore Settings").push("majoriteOre");
            MAJORITE_ORE_HL = b.comment("Harvest Level of Majorite Ore.")
                    .defineInRange("majoriteOreHL", 3, 0, 4);
            MAJORITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Majorite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Majorite Ore generation.")
                    .define("majoriteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            MAJORITE_ORE_STONE_SPECIFIC = b.comment("If true Majorite Ore will only spawn in the blocks listed under majoriteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in majoriteOreDimList.")
                    .define("majoriteOreStoneSpecific",false);
            MAJORITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Majorite Ore in if majoriteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("majoriteOreBlockList", new ArrayList<>(Arrays.asList()));
            MAJORITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Majorite Ore at (make sure it is less than the maximum)")
                    .defineInRange("majoriteOreMin", 10, 0, 256);
            MAJORITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Majorite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("majoriteOreMax", 50, 0, 256);
            MAJORITE_ORE_GENTYPE = b.comment("If false, majoriteOreChance will determine how many tries per chunk ore veins will generate. If true, majoriteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("majoriteOreGentype",false);
            MAJORITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on majoriteOreGentype.")
                    .defineInRange("majoriteOreChance", 2, 0, 256);
            MAJORITE_ORE_SIZE = b.comment("The maximum size of Majorite Ore vein")
                    .defineInRange("majoriteOreSize", 5, 0, 500);
            MAJORITE_ORE_MEGA_GEN = b.comment("If true, a mega Majorite Ore vein will generate according to the parameters majoriteOreMegaVeinSize andmajoriteOreMegaVeinChance")
                    .define("majoriteOreMegaVeins",false);
            MAJORITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Majorite Ore vein.")
                    .defineInRange("majoriteOreMegaVeinSize", 70, 0, 500);
            MAJORITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Majorite Ore vein will spawn.")
                    .defineInRange("majoriteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Fluorite Ore Settings").push("fluoriteOre");
            FLUORITE_ORE_HL = b.comment("Harvest Level of Fluorite Ore.")
                    .defineInRange("fluoriteOreHL", 4, 0, 4);
            FLUORITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Fluorite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Fluorite Ore generation.")
                    .define("fluoriteOreDimList", new ArrayList<>(Arrays.asList("end")));
            FLUORITE_ORE_STONE_SPECIFIC = b.comment("If true Fluorite Ore will only spawn in the blocks listed under fluoriteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in fluoriteOreDimList.")
                    .define("fluoriteOreStoneSpecific",false);
            FLUORITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Fluorite Ore in if fluoriteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("fluoriteOreBlockList", new ArrayList<>(Arrays.asList()));
            FLUORITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Fluorite Ore at (make sure it is less than the maximum)")
                    .defineInRange("fluoriteOreMin", 10, 0, 256);
            FLUORITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Fluorite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("fluoriteOreMax", 60, 0, 256);
            FLUORITE_ORE_GENTYPE = b.comment("If false, fluoriteOreChance will determine how many tries per chunk ore veins will generate. If true, fluoriteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("fluoriteOreGentype",true);
            FLUORITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on fluoriteOreGentype.")
                    .defineInRange("fluoriteOreChance", 7, 0, 256);
            FLUORITE_ORE_SIZE = b.comment("The maximum size of Fluorite Ore vein")
                    .defineInRange("fluoriteOreSize", 9, 0, 500);
            FLUORITE_ORE_MEGA_GEN = b.comment("If true, a mega Fluorite Ore vein will generate according to the parameters fluoriteOreMegaVeinSize andfluoriteOreMegaVeinChance")
                    .define("fluoriteOreMegaVeins",false);
            FLUORITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Fluorite Ore vein.")
                    .defineInRange("fluoriteOreMegaVeinSize", 70, 0, 500);
            FLUORITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Fluorite Ore vein will spawn.")
                    .defineInRange("fluoriteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Uraninite Ore Settings").push("uraniniteOre");
            URANINITE_ORE_HL = b.comment("Harvest Level of Uraninite Ore.")
                    .defineInRange("uraniniteOreHL", 4, 0, 4);
            URANINITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Uraninite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Uraninite Ore generation.")
                    .define("uraniniteOreDimList", new ArrayList<>(Arrays.asList("end")));
            URANINITE_ORE_STONE_SPECIFIC = b.comment("If true Uraninite Ore will only spawn in the blocks listed under uraniniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in uraniniteOreDimList.")
                    .define("uraniniteOreStoneSpecific",false);
            URANINITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Uraninite Ore in if uraniniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("uraniniteOreBlockList", new ArrayList<>(Arrays.asList()));
            URANINITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Uraninite Ore at (make sure it is less than the maximum)")
                    .defineInRange("uraniniteOreMin", 10, 0, 256);
            URANINITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Uraninite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("uraniniteOreMax", 60, 0, 256);
            URANINITE_ORE_GENTYPE = b.comment("If false, uraniniteOreChance will determine how many tries per chunk ore veins will generate. If true, uraniniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("uraniniteOreGentype",false);
            URANINITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on uraniniteOreGentype.")
                    .defineInRange("uraniniteOreChance", 7, 0, 256);
            URANINITE_ORE_SIZE = b.comment("The maximum size of Uraninite Ore vein")
                    .defineInRange("uraniniteOreSize", 5, 0, 500);
            URANINITE_ORE_MEGA_GEN = b.comment("If true, a mega Uraninite Ore vein will generate according to the parameters uraniniteOreMegaVeinSize anduraniniteOreMegaVeinChance")
                    .define("uraniniteOreMegaVeins",false);
            URANINITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Uraninite Ore vein.")
                    .defineInRange("uraniniteOreMegaVeinSize", 70, 0, 500);
            URANINITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Uraninite Ore vein will spawn.")
                    .defineInRange("uraniniteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Stibnite Ore Settings").push("stibniteOre");
            STIBNITE_ORE_HL = b.comment("Harvest Level of Stibnite Ore.")
                    .defineInRange("stibniteOreHL", 0, 0, 4);
            STIBNITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Stibnite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Stibnite Ore generation.")
                    .define("stibniteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            STIBNITE_ORE_STONE_SPECIFIC = b.comment("If true Stibnite Ore will only spawn in the blocks listed under stibniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in stibniteOreDimList.")
                    .define("stibniteOreStoneSpecific",false);
            STIBNITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Stibnite Ore in if stibniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("stibniteOreBlockList", new ArrayList<>(Arrays.asList()));
            STIBNITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Stibnite Ore at (make sure it is less than the maximum)")
                    .defineInRange("stibniteOreMin", 50, 0, 256);
            STIBNITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Stibnite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("stibniteOreMax", 85, 0, 256);
            STIBNITE_ORE_GENTYPE = b.comment("If false, stibniteOreChance will determine how many tries per chunk ore veins will generate. If true, stibniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("stibniteOreGentype",false);
            STIBNITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on stibniteOreGentype.")
                    .defineInRange("stibniteOreChance", 3, 0, 256);
            STIBNITE_ORE_SIZE = b.comment("The maximum size of Stibnite Ore vein")
                    .defineInRange("stibniteOreSize", 5, 0, 500);
            STIBNITE_ORE_MEGA_GEN = b.comment("If true, a mega Stibnite Ore vein will generate according to the parameters stibniteOreMegaVeinSize andstibniteOreMegaVeinChance")
                    .define("stibniteOreMegaVeins",false);
            STIBNITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Stibnite Ore vein.")
                    .defineInRange("stibniteOreMegaVeinSize", 70, 0, 500);
            STIBNITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Stibnite Ore vein will spawn.")
                    .defineInRange("stibniteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Xenotime Ore Settings").push("xenotimeOre");
            XENOTIME_ORE_HL = b.comment("Harvest Level of Xenotime Ore.")
                    .defineInRange("xenotimeOreHL", 4, 0, 4);
            XENOTIME_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Xenotime Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Xenotime Ore generation.")
                    .define("xenotimeOreDimList", new ArrayList<>(Arrays.asList("end")));
            XENOTIME_ORE_STONE_SPECIFIC = b.comment("If true Xenotime Ore will only spawn in the blocks listed under xenotimeOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in xenotimeOreDimList.")
                    .define("xenotimeOreStoneSpecific",false);
            XENOTIME_ORE_BLOCK_LIST = b.comment("Blocks to generate Xenotime Ore in if xenotimeOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("xenotimeOreBlockList", new ArrayList<>(Arrays.asList()));
            XENOTIME_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Xenotime Ore at (make sure it is less than the maximum)")
                    .defineInRange("xenotimeOreMin", 10, 0, 256);
            XENOTIME_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Xenotime Ore at (make sure it is greater than the minimum)")
                    .defineInRange("xenotimeOreMax", 60, 0, 256);
            XENOTIME_ORE_GENTYPE = b.comment("If false, xenotimeOreChance will determine how many tries per chunk ore veins will generate. If true, xenotimeOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("xenotimeOreGentype",false);
            XENOTIME_ORE_CHANCE = b.comment("Count / chance number to be used depending on xenotimeOreGentype.")
                    .defineInRange("xenotimeOreChance", 6, 0, 256);
            XENOTIME_ORE_SIZE = b.comment("The maximum size of Xenotime Ore vein")
                    .defineInRange("xenotimeOreSize", 7, 0, 500);
            XENOTIME_ORE_MEGA_GEN = b.comment("If true, a mega Xenotime Ore vein will generate according to the parameters xenotimeOreMegaVeinSize andxenotimeOreMegaVeinChance")
                    .define("xenotimeOreMegaVeins",false);
            XENOTIME_ORE_MEGA_SIZE = b.comment("The maximum size of mega Xenotime Ore vein.")
                    .defineInRange("xenotimeOreMegaVeinSize", 70, 0, 500);
            XENOTIME_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Xenotime Ore vein will spawn.")
                    .defineInRange("xenotimeOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Halite Ore Settings").push("haliteOre");
            HALITE_ORE_HL = b.comment("Harvest Level of Halite Ore.")
                    .defineInRange("haliteOreHL", 0, 0, 4);
            HALITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Halite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Halite Ore generation.")
                    .define("haliteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            HALITE_ORE_STONE_SPECIFIC = b.comment("If true Halite Ore will only spawn in the blocks listed under haliteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in haliteOreDimList.")
                    .define("haliteOreStoneSpecific",false);
            HALITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Halite Ore in if haliteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("haliteOreBlockList", new ArrayList<>(Arrays.asList()));
            HALITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Halite Ore at (make sure it is less than the maximum)")
                    .defineInRange("haliteOreMin", 50, 0, 256);
            HALITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Halite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("haliteOreMax", 80, 0, 256);
            HALITE_ORE_GENTYPE = b.comment("If false, haliteOreChance will determine how many tries per chunk ore veins will generate. If true, haliteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("haliteOreGentype",true);
            HALITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on haliteOreGentype.")
                    .defineInRange("haliteOreChance", 2, 0, 256);
            HALITE_ORE_SIZE = b.comment("The maximum size of Halite Ore vein")
                    .defineInRange("haliteOreSize", 16, 0, 500);
            HALITE_ORE_MEGA_GEN = b.comment("If true, a mega Halite Ore vein will generate according to the parameters haliteOreMegaVeinSize andhaliteOreMegaVeinChance")
                    .define("haliteOreMegaVeins",false);
            HALITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Halite Ore vein.")
                    .defineInRange("haliteOreMegaVeinSize", 70, 0, 500);
            HALITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Halite Ore vein will spawn.")
                    .defineInRange("haliteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Pink Halite Ore Settings").push("pinkHaliteOre");
            PINK_HALITE_ORE_HL = b.comment("Harvest Level of Pink Halite Ore.")
                    .defineInRange("pinkHaliteOreHL", 0, 0, 4);
            PINK_HALITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Pink Halite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Pink Halite Ore generation.")
                    .define("pinkHaliteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            PINK_HALITE_ORE_STONE_SPECIFIC = b.comment("If true Pink Halite Ore will only spawn in the blocks listed under pinkHaliteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in pinkHaliteOreDimList.")
                    .define("pinkHaliteOreStoneSpecific",false);
            PINK_HALITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Pink Halite Ore in if pinkHaliteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("pinkHaliteOreBlockList", new ArrayList<>(Arrays.asList()));
            PINK_HALITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Pink Halite Ore at (make sure it is less than the maximum)")
                    .defineInRange("pinkHaliteOreMin", 50, 0, 256);
            PINK_HALITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Pink Halite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("pinkHaliteOreMax", 80, 0, 256);
            PINK_HALITE_ORE_GENTYPE = b.comment("If false, pinkHaliteOreChance will determine how many tries per chunk ore veins will generate. If true, pinkHaliteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("pinkHaliteOreGentype",true);
            PINK_HALITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on pinkHaliteOreGentype.")
                    .defineInRange("pinkHaliteOreChance", 2, 0, 256);
            PINK_HALITE_ORE_SIZE = b.comment("The maximum size of Pink Halite Ore vein")
                    .defineInRange("pinkHaliteOreSize", 16, 0, 500);
            PINK_HALITE_ORE_MEGA_GEN = b.comment("If true, a mega Pink Halite Ore vein will generate according to the parameters pinkHaliteOreMegaVeinSize andpinkHaliteOreMegaVeinChance")
                    .define("pinkHaliteOreMegaVeins",false);
            PINK_HALITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Pink Halite Ore vein.")
                    .defineInRange("pinkHaliteOreMegaVeinSize", 70, 0, 500);
            PINK_HALITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Pink Halite Ore vein will spawn.")
                    .defineInRange("pinkHaliteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Interspinifex Ore Settings").push("interspinifexOre");
            INTERSPINIFEX_ORE_HL = b.comment("Harvest Level of Interspinifex Ore.")
                    .defineInRange("interspinifexOreHL", 3, 0, 4);
            INTERSPINIFEX_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Interspinifex Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Interspinifex Ore generation.")
                    .define("interspinifexOreDimList", new ArrayList<>(Arrays.asList()));
            INTERSPINIFEX_ORE_STONE_SPECIFIC = b.comment("If true Interspinifex Ore will only spawn in the blocks listed under interspinifexOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in interspinifexOreDimList.")
                    .define("interspinifexOreStoneSpecific",false);
            INTERSPINIFEX_ORE_BLOCK_LIST = b.comment("Blocks to generate Interspinifex Ore in if interspinifexOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("interspinifexOreBlockList", new ArrayList<>(Arrays.asList()));
            INTERSPINIFEX_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Interspinifex Ore at (make sure it is less than the maximum)")
                    .defineInRange("interspinifexOreMin", 40, 0, 256);
            INTERSPINIFEX_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Interspinifex Ore at (make sure it is greater than the minimum)")
                    .defineInRange("interspinifexOreMax", 60, 0, 256);
            INTERSPINIFEX_ORE_GENTYPE = b.comment("If false, interspinifexOreChance will determine how many tries per chunk ore veins will generate. If true, interspinifexOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("interspinifexOreGentype",true);
            INTERSPINIFEX_ORE_CHANCE = b.comment("Count / chance number to be used depending on interspinifexOreGentype.")
                    .defineInRange("interspinifexOreChance", 3, 0, 256);
            INTERSPINIFEX_ORE_SIZE = b.comment("The maximum size of Interspinifex Ore vein")
                    .defineInRange("interspinifexOreSize", 24, 0, 500);
            INTERSPINIFEX_ORE_MEGA_GEN = b.comment("If true, a mega Interspinifex Ore vein will generate according to the parameters interspinifexOreMegaVeinSize andinterspinifexOreMegaVeinChance")
                    .define("interspinifexOreMegaVeins",false);
            INTERSPINIFEX_ORE_MEGA_SIZE = b.comment("The maximum size of mega Interspinifex Ore vein.")
                    .defineInRange("interspinifexOreMegaVeinSize", 70, 0, 500);
            INTERSPINIFEX_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Interspinifex Ore vein will spawn.")
                    .defineInRange("interspinifexOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Petalite Ore Settings").push("petaliteOre");
            PETALITE_ORE_HL = b.comment("Harvest Level of Petalite Ore.")
                    .defineInRange("petaliteOreHL", 4, 0, 4);
            PETALITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Petalite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Petalite Ore generation.")
                    .define("petaliteOreDimList", new ArrayList<>(Arrays.asList()));
            PETALITE_ORE_STONE_SPECIFIC = b.comment("If true Petalite Ore will only spawn in the blocks listed under petaliteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in petaliteOreDimList.")
                    .define("petaliteOreStoneSpecific",false);
            PETALITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Petalite Ore in if petaliteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("petaliteOreBlockList", new ArrayList<>(Arrays.asList()));
            PETALITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Petalite Ore at (make sure it is less than the maximum)")
                    .defineInRange("petaliteOreMin", 40, 0, 256);
            PETALITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Petalite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("petaliteOreMax", 60, 0, 256);
            PETALITE_ORE_GENTYPE = b.comment("If false, petaliteOreChance will determine how many tries per chunk ore veins will generate. If true, petaliteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("petaliteOreGentype",true);
            PETALITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on petaliteOreGentype.")
                    .defineInRange("petaliteOreChance", 3, 0, 256);
            PETALITE_ORE_SIZE = b.comment("The maximum size of Petalite Ore vein")
                    .defineInRange("petaliteOreSize", 24, 0, 500);
            PETALITE_ORE_MEGA_GEN = b.comment("If true, a mega Petalite Ore vein will generate according to the parameters petaliteOreMegaVeinSize andpetaliteOreMegaVeinChance")
                    .define("petaliteOreMegaVeins",false);
            PETALITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Petalite Ore vein.")
                    .defineInRange("petaliteOreMegaVeinSize", 70, 0, 500);
            PETALITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Petalite Ore vein will spawn.")
                    .defineInRange("petaliteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Cobaltite Ore Settings").push("cobaltiteOre");
            COBALTITE_ORE_HL = b.comment("Harvest Level of Cobaltite Ore.")
                    .defineInRange("cobaltiteOreHL", 4, 0, 4);
            COBALTITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Cobaltite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Cobaltite Ore generation.")
                    .define("cobaltiteOreDimList", new ArrayList<>(Arrays.asList("nether")));
            COBALTITE_ORE_STONE_SPECIFIC = b.comment("If true Cobaltite Ore will only spawn in the blocks listed under cobaltiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in cobaltiteOreDimList.")
                    .define("cobaltiteOreStoneSpecific",false);
            COBALTITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Cobaltite Ore in if cobaltiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("cobaltiteOreBlockList", new ArrayList<>(Arrays.asList()));
            COBALTITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Cobaltite Ore at (make sure it is less than the maximum)")
                    .defineInRange("cobaltiteOreMin", 45, 0, 256);
            COBALTITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Cobaltite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("cobaltiteOreMax", 90, 0, 256);
            COBALTITE_ORE_GENTYPE = b.comment("If false, cobaltiteOreChance will determine how many tries per chunk ore veins will generate. If true, cobaltiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("cobaltiteOreGentype",true);
            COBALTITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on cobaltiteOreGentype.")
                    .defineInRange("cobaltiteOreChance", 3, 0, 256);
            COBALTITE_ORE_SIZE = b.comment("The maximum size of Cobaltite Ore vein")
                    .defineInRange("cobaltiteOreSize", 24, 0, 500);
            COBALTITE_ORE_MEGA_GEN = b.comment("If true, a mega Cobaltite Ore vein will generate according to the parameters cobaltiteOreMegaVeinSize andcobaltiteOreMegaVeinChance")
                    .define("cobaltiteOreMegaVeins",false);
            COBALTITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Cobaltite Ore vein.")
                    .defineInRange("cobaltiteOreMegaVeinSize", 70, 0, 500);
            COBALTITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Cobaltite Ore vein will spawn.")
                    .defineInRange("cobaltiteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Cryolite Ore Settings").push("cryoliteOre");
            CRYOLITE_ORE_HL = b.comment("Harvest Level of Cryolite Ore.")
                    .defineInRange("cryoliteOreHL", 4, 0, 4);
            CRYOLITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Cryolite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Cryolite Ore generation.")
                    .define("cryoliteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            CRYOLITE_ORE_STONE_SPECIFIC = b.comment("If true Cryolite Ore will only spawn in the blocks listed under cryoliteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in cryoliteOreDimList.")
                    .define("cryoliteOreStoneSpecific",false);
            CRYOLITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Cryolite Ore in if cryoliteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("cryoliteOreBlockList", new ArrayList<>(Arrays.asList()));
            CRYOLITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Cryolite Ore at (make sure it is less than the maximum)")
                    .defineInRange("cryoliteOreMin", 0, 0, 256);
            CRYOLITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Cryolite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("cryoliteOreMax", 20, 0, 256);
            CRYOLITE_ORE_GENTYPE = b.comment("If false, cryoliteOreChance will determine how many tries per chunk ore veins will generate. If true, cryoliteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("cryoliteOreGentype",true);
            CRYOLITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on cryoliteOreGentype.")
                    .defineInRange("cryoliteOreChance", 4, 0, 256);
            CRYOLITE_ORE_SIZE = b.comment("The maximum size of Cryolite Ore vein")
                    .defineInRange("cryoliteOreSize", 24, 0, 500);
            CRYOLITE_ORE_MEGA_GEN = b.comment("If true, a mega Cryolite Ore vein will generate according to the parameters cryoliteOreMegaVeinSize andcryoliteOreMegaVeinChance")
                    .define("cryoliteOreMegaVeins",false);
            CRYOLITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Cryolite Ore vein.")
                    .defineInRange("cryoliteOreMegaVeinSize", 50, 0, 500);
            CRYOLITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Cryolite Ore vein will spawn.")
                    .defineInRange("cryoliteOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.comment("Pyrite Ore Settings").push("pyriteOre");
            PYRITE_ORE_HL = b.comment("Harvest Level of Pyrite Ore.")
                    .defineInRange("pyriteOreHL", 2, 0, 4);
            PYRITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Pyrite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Pyrite Ore generation.")
                    .define("pyriteOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            PYRITE_ORE_STONE_SPECIFIC = b.comment("If true Pyrite Ore will only spawn in the blocks listed under pyriteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in pyriteOreDimList.")
                    .define("pyriteOreStoneSpecific",false);
            PYRITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Pyrite Ore in if pyriteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("pyriteOreBlockList", new ArrayList<>(Arrays.asList()));
            PYRITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Pyrite Ore at (make sure it is less than the maximum)")
                    .defineInRange("pyriteOreMin", 10, 0, 256);
            PYRITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Pyrite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("pyriteOreMax", 50, 0, 256);
            PYRITE_ORE_GENTYPE = b.comment("If false, pyriteOreChance will determine how many tries per chunk ore veins will generate. If true, pyriteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("pyriteOreGentype",false);
            PYRITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on pyriteOreGentype.")
                    .defineInRange("pyriteOreChance", 4, 0, 256);
            PYRITE_ORE_SIZE = b.comment("The maximum size of Pyrite Ore vein")
                    .defineInRange("pyriteOreSize", 6, 0, 500);
            PYRITE_ORE_MEGA_GEN = b.comment("If true, a mega Pyrite Ore vein will generate according to the parameters pyriteOreMegaVeinSize andpyriteOreMegaVeinChance")
                    .define("pyriteOreMegaVeins",false);
            PYRITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Pyrite Ore vein.")
                    .defineInRange("pyriteOreMegaVeinSize", 70, 0, 500);
            PYRITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Pyrite Ore vein will spawn.")
                    .defineInRange("pyriteOreMegaVeinChance", 20, 0, 500);
            b.pop();

            b.comment("Celestine Ore Settings").push("celestineOre");
            CELESTINE_ORE_HL = b.comment("Harvest Level of Celestine Ore.")
                    .defineInRange("celestineOreHL", 3, 0, 4);
            CELESTINE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Celestine Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Celestine Ore generation.")
                    .define("celestineOreDimList", new ArrayList<>(Arrays.asList("overworld")));
            CELESTINE_ORE_STONE_SPECIFIC = b.comment("If true Celestine Ore will only spawn in the blocks listed under celestineOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in celestineOreDimList.")
                    .define("celestineOreStoneSpecific",false);
            CELESTINE_ORE_BLOCK_LIST = b.comment("Blocks to generate Celestine Ore in if celestineOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("celestineOreBlockList", new ArrayList<>(Arrays.asList()));
            CELESTINE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Celestine Ore at (make sure it is less than the maximum)")
                    .defineInRange("celestineOreMin", 0, 0, 256);
            CELESTINE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Celestine Ore at (make sure it is greater than the minimum)")
                    .defineInRange("celestineOreMax", 20, 0, 256);
            CELESTINE_ORE_GENTYPE = b.comment("If false, celestineOreChance will determine how many tries per chunk ore veins will generate. If true, celestineOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("celestineOreGentype",true);
            CELESTINE_ORE_CHANCE = b.comment("Count / chance number to be used depending on celestineOreGentype.")
                    .defineInRange("celestineOreChance", 5, 0, 256);
            CELESTINE_ORE_SIZE = b.comment("The maximum size of Celestine Ore vein")
                    .defineInRange("celestineOreSize", 16, 0, 500);
            CELESTINE_ORE_MEGA_GEN = b.comment("If true, a mega Celestine Ore vein will generate according to the parameters celestineOreMegaVeinSize andcelestineOreMegaVeinChance")
                    .define("celestineOreMegaVeins",false);
            CELESTINE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Celestine Ore vein.")
                    .defineInRange("celestineOreMegaVeinSize", 50, 0, 500);
            CELESTINE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Celestine Ore vein will spawn.")
                    .defineInRange("celestineOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.comment("Monazite Ore Settings").push("monaziteOre");
            MONAZITE_ORE_HL = b.comment("Harvest Level of Monazite Ore.")
                    .defineInRange("monaziteOreHL", 4, 0, 4);
            MONAZITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Monazite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Monazite Ore generation.")
                    .define("monaziteOreDimList", new ArrayList<>(Arrays.asList("nether")));
            MONAZITE_ORE_STONE_SPECIFIC = b.comment("If true Monazite Ore will only spawn in the blocks listed under monaziteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in monaziteOreDimList.")
                    .define("monaziteOreStoneSpecific",false);
            MONAZITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Monazite Ore in if monaziteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("monaziteOreBlockList", new ArrayList<>(Arrays.asList()));
            MONAZITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Monazite Ore at (make sure it is less than the maximum)")
                    .defineInRange("monaziteOreMin", 10, 0, 256);
            MONAZITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Monazite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("monaziteOreMax", 40, 0, 256);
            MONAZITE_ORE_GENTYPE = b.comment("If false, monaziteOreChance will determine how many tries per chunk ore veins will generate. If true, monaziteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("monaziteOreGentype",false);
            MONAZITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on monaziteOreGentype.")
                    .defineInRange("monaziteOreChance", 3, 0, 256);
            MONAZITE_ORE_SIZE = b.comment("The maximum size of Monazite Ore vein")
                    .defineInRange("monaziteOreSize", 7, 0, 500);
            MONAZITE_ORE_MEGA_GEN = b.comment("If true, a mega Monazite Ore vein will generate according to the parameters monaziteOreMegaVeinSize andmonaziteOreMegaVeinChance")
                    .define("monaziteOreMegaVeins",false);
            MONAZITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Monazite Ore vein.")
                    .defineInRange("monaziteOreMegaVeinSize", 50, 0, 500);
            MONAZITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Monazite Ore vein will spawn.")
                    .defineInRange("monaziteOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.comment("Kamacite Ore Settings").push("kamaciteOre");
            KAMACITE_ORE_HL = b.comment("Harvest Level of Kamacite Ore.")
                    .defineInRange("kamaciteOreHL", 2, 0, 4);
            KAMACITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Kamacite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Kamacite Ore generation.")
                    .define("kamaciteOreDimList", new ArrayList<>(Arrays.asList("end")));
            KAMACITE_ORE_STONE_SPECIFIC = b.comment("If true Kamacite Ore will only spawn in the blocks listed under kamaciteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in kamaciteOreDimList.")
                    .define("kamaciteOreStoneSpecific",true);
            KAMACITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Kamacite Ore in if kamaciteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("kamaciteOreBlockList", new ArrayList<>(Arrays.asList("B#rankine:meteorite","B#rankine:enstatite")));
            KAMACITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Kamacite Ore at (make sure it is less than the maximum)")
                    .defineInRange("kamaciteOreMin", 0, 0, 256);
            KAMACITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Kamacite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("kamaciteOreMax", 40, 0, 256);
            KAMACITE_ORE_GENTYPE = b.comment("If false, kamaciteOreChance will determine how many tries per chunk ore veins will generate. If true, kamaciteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("kamaciteOreGentype",false);
            KAMACITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on kamaciteOreGentype.")
                    .defineInRange("kamaciteOreChance", 5, 0, 256);
            KAMACITE_ORE_SIZE = b.comment("The maximum size of Kamacite Ore vein")
                    .defineInRange("kamaciteOreSize", 10, 0, 500);
            KAMACITE_ORE_MEGA_GEN = b.comment("If true, a mega Kamacite Ore vein will generate according to the parameters kamaciteOreMegaVeinSize andkamaciteOreMegaVeinChance")
                    .define("kamaciteOreMegaVeins",false);
            KAMACITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Kamacite Ore vein.")
                    .defineInRange("kamaciteOreMegaVeinSize", 50, 0, 500);
            KAMACITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Kamacite Ore vein will spawn.")
                    .defineInRange("kamaciteOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.comment("Antitaenite Ore Settings").push("antitaeniteOre");
            ANTITAENITE_ORE_HL = b.comment("Harvest Level of Antitaenite Ore.")
                    .defineInRange("antitaeniteOreHL", 2, 0, 4);
            ANTITAENITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Antitaenite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Antitaenite Ore generation.")
                    .define("antitaeniteOreDimList", new ArrayList<>(Arrays.asList("end")));
            ANTITAENITE_ORE_STONE_SPECIFIC = b.comment("If true Antitaenite Ore will only spawn in the blocks listed under antitaeniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in antitaeniteOreDimList.")
                    .define("antitaeniteOreStoneSpecific",true);
            ANTITAENITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Antitaenite Ore in if antitaeniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("antitaeniteOreBlockList", new ArrayList<>(Arrays.asList("B#rankine:meteorite","B#rankine:enstatite")));
            ANTITAENITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Antitaenite Ore at (make sure it is less than the maximum)")
                    .defineInRange("antitaeniteOreMin", 0, 0, 256);
            ANTITAENITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Antitaenite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("antitaeniteOreMax", 40, 0, 256);
            ANTITAENITE_ORE_GENTYPE = b.comment("If false, antitaeniteOreChance will determine how many tries per chunk ore veins will generate. If true, antitaeniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("antitaeniteOreGentype",false);
            ANTITAENITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on antitaeniteOreGentype.")
                    .defineInRange("antitaeniteOreChance", 5, 0, 256);
            ANTITAENITE_ORE_SIZE = b.comment("The maximum size of Antitaenite Ore vein")
                    .defineInRange("antitaeniteOreSize", 10, 0, 500);
            ANTITAENITE_ORE_MEGA_GEN = b.comment("If true, a mega Antitaenite Ore vein will generate according to the parameters antitaeniteOreMegaVeinSize andantitaeniteOreMegaVeinChance")
                    .define("antitaeniteOreMegaVeins",false);
            ANTITAENITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Antitaenite Ore vein.")
                    .defineInRange("antitaeniteOreMegaVeinSize", 50, 0, 500);
            ANTITAENITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Antitaenite Ore vein will spawn.")
                    .defineInRange("antitaeniteOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.comment("taenite Ore Settings").push("taeniteOre");
            TAENITE_ORE_HL = b.comment("Harvest Level of taenite Ore.")
                    .defineInRange("taeniteOreHL", 2, 0, 4);
            TAENITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate taenite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable taenite Ore generation.")
                    .define("taeniteOreDimList", new ArrayList<>(Arrays.asList("end")));
            TAENITE_ORE_STONE_SPECIFIC = b.comment("If true taenite Ore will only spawn in the blocks listed under taeniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in taeniteOreDimList.")
                    .define("taeniteOreStoneSpecific",true);
            TAENITE_ORE_BLOCK_LIST = b.comment("Blocks to generate taenite Ore in if taeniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("taeniteOreBlockList", new ArrayList<>(Arrays.asList("B#rankine:meteorite","B#rankine:enstatite")));
            TAENITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate taenite Ore at (make sure it is less than the maximum)")
                    .defineInRange("taeniteOreMin", 0, 0, 256);
            TAENITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate taenite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("taeniteOreMax", 40, 0, 256);
            TAENITE_ORE_GENTYPE = b.comment("If false, taeniteOreChance will determine how many tries per chunk ore veins will generate. If true, taeniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("taeniteOreGentype",false);
            TAENITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on taeniteOreGentype.")
                    .defineInRange("taeniteOreChance", 5, 0, 256);
            TAENITE_ORE_SIZE = b.comment("The maximum size of taenite Ore vein")
                    .defineInRange("taeniteOreSize", 10, 0, 500);
            TAENITE_ORE_MEGA_GEN = b.comment("If true, a mega taenite Ore vein will generate according to the parameters taeniteOreMegaVeinSize andtaeniteOreMegaVeinChance")
                    .define("taeniteOreMegaVeins",false);
            TAENITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega taenite Ore vein.")
                    .defineInRange("taeniteOreMegaVeinSize", 50, 0, 500);
            TAENITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega taenite Ore vein will spawn.")
                    .defineInRange("taeniteOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.comment("Tetrataenite Ore Settings").push("tetrataeniteOre");
            TETRATAENITE_ORE_HL = b.comment("Harvest Level of Tetrataenite Ore.")
                    .defineInRange("tetrataeniteOreHL", 2, 0, 4);
            TETRATAENITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Tetrataenite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Tetrataenite Ore generation.")
                    .define("tetrataeniteOreDimList", new ArrayList<>(Arrays.asList("end")));
            TETRATAENITE_ORE_STONE_SPECIFIC = b.comment("If true Tetrataenite Ore will only spawn in the blocks listed under tetrataeniteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in tetrataeniteOreDimList.")
                    .define("tetrataeniteOreStoneSpecific",true);
            TETRATAENITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Tetrataenite Ore in if tetrataeniteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("tetrataeniteOreBlockList", new ArrayList<>(Arrays.asList("B#rankine:meteorite","B#rankine:enstatite")));
            TETRATAENITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Tetrataenite Ore at (make sure it is less than the maximum)")
                    .defineInRange("tetrataeniteOreMin", 0, 0, 256);
            TETRATAENITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Tetrataenite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("tetrataeniteOreMax", 40, 0, 256);
            TETRATAENITE_ORE_GENTYPE = b.comment("If false, tetrataeniteOreChance will determine how many tries per chunk ore veins will generate. If true, tetrataeniteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("tetrataeniteOreGentype",false);
            TETRATAENITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on tetrataeniteOreGentype.")
                    .defineInRange("tetrataeniteOreChance", 5, 0, 256);
            TETRATAENITE_ORE_SIZE = b.comment("The maximum size of Tetrataenite Ore vein")
                    .defineInRange("tetrataeniteOreSize", 10, 0, 500);
            TETRATAENITE_ORE_MEGA_GEN = b.comment("If true, a mega Tetrataenite Ore vein will generate according to the parameters tetrataeniteOreMegaVeinSize andtetrataeniteOreMegaVeinChance")
                    .define("tetrataeniteOreMegaVeins",false);
            TETRATAENITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Tetrataenite Ore vein.")
                    .defineInRange("tetrataeniteOreMegaVeinSize", 50, 0, 500);
            TETRATAENITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Tetrataenite Ore vein will spawn.")
                    .defineInRange("tetrataeniteOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.comment("Lonsdaleite Ore Settings").push("lonsdaleiteOre");
            LONSDALEITE_ORE_HL = b.comment("Harvest Level of Lonsdaleite Ore.")
                    .defineInRange("lonsdaleiteOreHL", 4, 0, 4);
            LONSDALEITE_ORE_DIMENSION_LIST = b.comment("Dimensions to generate Lonsdaleite Ore in (supports overworld / nether / end). The same spawning parameters are used if multiple dimenstions are listed. Leave empty to disable Lonsdaleite Ore generation.")
                    .define("lonsdaleiteOreDimList", new ArrayList<>(Arrays.asList("end")));
            LONSDALEITE_ORE_STONE_SPECIFIC = b.comment("If true Lonsdaleite Ore will only spawn in the blocks listed under lonsdaleiteOreBlockList If false it will generate in blocks with the tag #minecraft:base_stone_overworld or #minecraft:base_stone_nether or #forge:base_stone_end depending on the dimensions listed in lonsdaleiteOreDimList.")
                    .define("lonsdaleiteOreStoneSpecific",true);
            LONSDALEITE_ORE_BLOCK_LIST = b.comment("Blocks to generate Lonsdaleite Ore in if lonsdaleiteOreStoneSpecific is enabled. The entries can be either individual blocks or tags. Use B#<modname:block_name> for blocks and T#<modname::tag_name> for tags (Ex: B#rankine:slate and T#forge:stones/gabbro). Rankine stones and some modded stones support background stone imitation.")
                    .define("lonsdaleiteOreBlockList", new ArrayList<>(Arrays.asList("B#rankine:meteorite","B#rankine:enstatite")));
            LONSDALEITE_ORE_MIN_HEIGHT = b.comment("Minimum height to generate Lonsdaleite Ore at (make sure it is less than the maximum)")
                    .defineInRange("lonsdaleiteOreMin", 0, 0, 256);
            LONSDALEITE_ORE_MAX_HEIGHT = b.comment("Maximum height to generate Lonsdaleite Ore at (make sure it is greater than the minimum)")
                    .defineInRange("lonsdaleiteOreMax", 40, 0, 256);
            LONSDALEITE_ORE_GENTYPE = b.comment("If false, lonsdaleiteOreChance will determine how many tries per chunk ore veins will generate. If true, lonsdaleiteOreChance will determine 1 in how many chunks a vein will spawn.")
                    .define("lonsdaleiteOreGentype",false);
            LONSDALEITE_ORE_CHANCE = b.comment("Count / chance number to be used depending on lonsdaleiteOreGentype.")
                    .defineInRange("lonsdaleiteOreChance", 3, 0, 256);
            LONSDALEITE_ORE_SIZE = b.comment("The maximum size of Lonsdaleite Ore vein")
                    .defineInRange("lonsdaleiteOreSize", 5, 0, 500);
            LONSDALEITE_ORE_MEGA_GEN = b.comment("If true, a mega Lonsdaleite Ore vein will generate according to the parameters lonsdaleiteOreMegaVeinSize andlonsdaleiteOreMegaVeinChance")
                    .define("lonsdaleiteOreMegaVeins",false);
            LONSDALEITE_ORE_MEGA_SIZE = b.comment("The maximum size of mega Lonsdaleite Ore vein.")
                    .defineInRange("lonsdaleiteOreMegaVeinSize", 50, 0, 500);
            LONSDALEITE_ORE_MEGA_CHANCE = b.comment("Determines 1 in how many chunks a mega Lonsdaleite Ore vein will spawn.")
                    .defineInRange("lonsdaleiteOreMegaVeinChance", 80, 0, 500);
            b.pop();

            b.pop();
        }
    }







    public static final ForgeConfigSpec COMMON_WGCONFIG;
    public static final Misc MISC;
    public static final Layers LAYERS;
    public static final Intrusions INTRUSIONS;
    public static final Ores ORES;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        MISC = new Misc(BUILDER);
        LAYERS = new Layers(BUILDER);
        INTRUSIONS = new Intrusions(BUILDER);
        ORES = new Ores(BUILDER);

        COMMON_WGCONFIG = BUILDER.build();
    }


}
