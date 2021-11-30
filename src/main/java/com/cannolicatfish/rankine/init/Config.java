package com.cannolicatfish.rankine.init;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class Config {

    static Predicate<Object> ELEMENT_VALIDATOR = o -> o instanceof String;
    public static Predicate<Object> DoubleValidator = o -> o instanceof Double;

    public static class BlockProperties {

        public final ForgeConfigSpec.DoubleValue POLISHED_HARDNESS_MULT;
        public final ForgeConfigSpec.DoubleValue POLISHED_RESISTANCE_MULT;
        public final ForgeConfigSpec.DoubleValue BRICKS_HARDNESS_MULT;
        public final ForgeConfigSpec.DoubleValue BRICKS_RESISTANCE_MULT;

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
        public final ForgeConfigSpec.IntValue HEMATITE_ORE_HL;
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
        public final ForgeConfigSpec.IntValue BADDELEYITE_ORE_HL;
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

        public BlockProperties(ForgeConfigSpec.Builder b) {
            BRICKS_HARDNESS_MULT = b.comment("A multiplier to determine how much higher the bricks variant hardness is than the stone.")
                    .defineInRange("bricksHardnessMultiplier", 1.5D, 0.0D, 20.0D);
            BRICKS_RESISTANCE_MULT = b.comment("A multiplier to determine how much higher the bricks variant resistance is than the stone.")
                    .defineInRange("bricksResistanceMultiplier", 2.0D, 0.0D, 20.0D);
            POLISHED_HARDNESS_MULT = b.comment("A multiplier to determine how much higher the polished variant hardness is than the stone.")
                    .defineInRange("polishedHardnessMultiplier", 1.1D, 0.0D, 20.0D);
            POLISHED_RESISTANCE_MULT = b.comment("A multiplier to determine how much higher the polished variant resistance is than the stone.")
                    .defineInRange("polishedResistanceMultiplier", 1.5D, 0.0D, 20.0D);

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
            HEMATITE_ORE_HL = b.comment("Harvest Level of Hematite Ore.")
                    .defineInRange("hematiteOreHL", 2, 0, 4);
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
                    .defineInRange("plumbagoOreHL", 2, 0, 4);
            SPERRYLITE_ORE_HL = b.comment("Harvest Level of Sperrylite Ore.")
                    .defineInRange("sperryliteOreHL", 1, 0, 4);
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
            BADDELEYITE_ORE_HL = b.comment("Harvest Level of Baddeleyite Ore.")
                    .defineInRange("baddeleyiteOreHL", 4, 0, 4);
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


    public static class MiscWorldGen {
        public final ForgeConfigSpec.IntValue BEDROCK_LAYERS;
        public final ForgeConfigSpec.BooleanValue FLAT_BEDROCK;
        public final ForgeConfigSpec.BooleanValue FLAT_BEDROCK_NETHER;
        public final ForgeConfigSpec.BooleanValue DISABLE_VANILLA_FEATURES;
        public final ForgeConfigSpec.BooleanValue RANKINE_FLORA;
        public final ForgeConfigSpec.BooleanValue RANKINE_TREES;
        public final ForgeConfigSpec.BooleanValue EVAPORITE_GEN;
        public final ForgeConfigSpec.BooleanValue FUMAROLE_GEN;
        public final ForgeConfigSpec.BooleanValue ALLUVIUM_GEN;
        public final ForgeConfigSpec.BooleanValue WHITE_SAND_GEN;
        public final ForgeConfigSpec.BooleanValue BLACK_SAND_GEN;
        public final ForgeConfigSpec.BooleanValue SECRET_GEN;
        public final ForgeConfigSpec.BooleanValue REPLACE_VANILLA_ORES;
        public final ForgeConfigSpec.BooleanValue END_METEORITE_GEN;
        public final ForgeConfigSpec.BooleanValue METEORITE_GEN;
        public final ForgeConfigSpec.DoubleValue END_METEORITE_CHANCE;
        public final ForgeConfigSpec.DoubleValue BIG_METEORITE_CHANCE;
        public final ForgeConfigSpec.IntValue METEORITE_SIZE;
        public final ForgeConfigSpec.IntValue METEORITE_CHANCE;

        public final ForgeConfigSpec.DoubleValue LAYER_WIDTH;
        public final ForgeConfigSpec.IntValue NOISE_SCALE;
        public final ForgeConfigSpec.IntValue NOISE_OFFSET;

        public final ForgeConfigSpec.IntValue OVERWORLD_INTRUSION_RADIUS;
        public final ForgeConfigSpec.DoubleValue OVERWORLD_INTRUSION_SHRINK;
        public final ForgeConfigSpec.DoubleValue OVERWORLD_INTRUSION_SHIFT;
        public final ForgeConfigSpec.IntValue NETHER_INTRUSION_RADIUS;
        public final ForgeConfigSpec.DoubleValue NETHER_INTRUSION_SHRINK;
        public final ForgeConfigSpec.DoubleValue NETHER_INTRUSION_SHIFT;

        public MiscWorldGen(ForgeConfigSpec.Builder b) {
            b.comment("Here are miscellaneous worldgen features").push("misc");
            FLAT_BEDROCK = b.comment("Generates the Overworld with a flat bedrock layer.")
                    .define("flatBedrock",false);
            FLAT_BEDROCK_NETHER = b.comment("Generates the Nether with a flat bedrock layer.")
                    .define("flatBedrockNether",false);
            SECRET_GEN = b.comment("Figure it out")
                    .define("secretGen",true);
            REPLACE_VANILLA_ORES = b.comment("If enabled, replaces vanilla ores with the Rankine counterparts which will mimic stones around them.")
                    .define("replaceVanillaOres",true);
            BEDROCK_LAYERS = b.comment("Layers of bedrock to generate if flatBedrock is true")
                    .defineInRange("bedrockLayers", 1, 0, 5);
            DISABLE_VANILLA_FEATURES = b.comment("Disable vanilla features in the overworld. Works by replacing the listed blocks in #rankine:vanilla_override with stones")
                    .define("disableVanillaOres",true);
            RANKINE_FLORA = b.comment("Enable/Disable Project Rankine flowers and berry bushes in world.")
                    .define("generateFlora",true);
            RANKINE_TREES = b.comment("Enable/Disable Project Rankine trees in world.")
                    .define("generateTrees",true);
            EVAPORITE_GEN = b.comment("Enables the generation of evaporite disks.")
                    .define("generateEvaporite",true);
            FUMAROLE_GEN = b.comment("Enables the generation of fumaroles. More options to come.")
                    .define("generateFumaroles",true);
            ALLUVIUM_GEN = b.comment("Enables the generation of alluvium disks.")
                    .define("generateAlluvium",true);
            WHITE_SAND_GEN = b.comment("Enables the generation of white sand disks in beaches.")
                    .define("generateWhiteSand",true);
            BLACK_SAND_GEN = b.comment("Enables the generation of black sand disks in the Nether.")
                    .define("generateBlackSand",true);
            END_METEORITE_GEN = b.comment("Enable to generate meteorites in the end.")
                    .define("endMeteoriteGen",true);
            END_METEORITE_CHANCE = b.comment("The chance for an end meteroite.")
                    .defineInRange("endMeteoriteChance", 0.03, 0.00, 1.00);
            METEORITE_GEN = b.comment("Enable to generate meteorites in the overworld.")
                    .define("meteoriteGen",true);
            METEORITE_SIZE = b.comment("Size parameter for meteorites. Higher number is bigger.")
                    .defineInRange("meteoriteSize", 1, 0, 10);
            METEORITE_CHANCE = b.comment("The chance a meteroite will spawn in the Overworld. Higher numbers increase rarity.")
                    .defineInRange("meteoriteChance", 70, 0, 1000);
            BIG_METEORITE_CHANCE = b.comment("The chance a meteroite will be big.")
                    .defineInRange("meteoriteBigChance", 0.25, 0.00, 1.00);
            b.pop();

            b.comment("Settings for stone layering").push("layers");
            LAYER_WIDTH = b.comment("Determines the vertical spread of stone layers. 1.0 is flat, closer to 0.0 is more extreme, 0.0 will crash.")
                    .defineInRange("layerWidth", 0.15D, 0.0D, 1.0D);
            NOISE_SCALE = b.comment("This determines how wide stone layers generate. Smaller values means it will look more like bedrock. Default value is 125.")
                    .defineInRange("noiseScale", 110, 1, 1000);
            NOISE_OFFSET = b.comment("This determines how close the overlap of noise layers is. A value of 0 means all layers are shaped identically.")
                    .defineInRange("noiseOffset", 256, 0, 16*64);
            b.pop();

            b.comment("Settings for intrusions").push("intrusions");
            OVERWORLD_INTRUSION_RADIUS = b.comment("Size of an intrusion")
                    .defineInRange("overworldIntrusionRadius", 3, 0, 8);
            OVERWORLD_INTRUSION_SHRINK = b.comment("Chance for an overworld intrusion to shrink as it goes up. Values closer to 0 result in longer intrusions")
                    .defineInRange("overworldIntrusionShrink", 0.05D, 0.0D, 1.0D);
            OVERWORLD_INTRUSION_SHIFT = b.comment("Chance for an overworld intrusion to shift as it goes up. Values closer to 0 result in straighter intrusions")
                    .defineInRange("overworldIntrusionShift", 0.15D, 0.0D, 1.0D);

            NETHER_INTRUSION_RADIUS = b.comment("Maximum radius of an intrusion")
                    .defineInRange("netherIntrusionRadius", 3, 0, 8);
            NETHER_INTRUSION_SHRINK = b.comment("Chance for an nether intrusion to shift as it goes up. Values closer to 0 result in straighter intrusions")
                    .defineInRange("netherIntrusionShrink", 0.02D, 0.0D, 1.0D);
            NETHER_INTRUSION_SHIFT = b.comment("Chance for an overworld intrusion to shift as it goes up. Values closer to 0 result in straighter intrusions")
                    .defineInRange("netherIntrusionShift", 0.15D, 0.0D, 1.0D);
            b.pop();
        }
    }

    public static class Tools {
        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_MALLET;
        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_HOE;
        public final ForgeConfigSpec.BooleanValue DISABLE_STONE_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_STONE_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_STONE_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_STONE_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_STONE_HOE;
        public final ForgeConfigSpec.BooleanValue DISABLE_IRON_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_IRON_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_IRON_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_IRON_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_IRON_HOE;
        public final ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_HOE;
        public final ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_HOE;
        public final ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_HOE;

        public final ForgeConfigSpec.BooleanValue DISABLE_COMPASS;
        public final ForgeConfigSpec.BooleanValue DISABLE_CLOCK;
        public final ForgeConfigSpec.BooleanValue DISABLE_ALTIMETER;
        public final ForgeConfigSpec.BooleanValue DISABLE_THERMOMETER;
        public final ForgeConfigSpec.BooleanValue DISABLE_PHOTOMETER;
        public final ForgeConfigSpec.BooleanValue DISABLE_SPEEDOMETER;
        public final ForgeConfigSpec.BooleanValue DISABLE_BIOMETER;

        public Tools(ForgeConfigSpec.Builder b) {
            b.comment("Settings for tools").push("tools");

            b.comment("Rankine Tools").push("rankineTools");
            DISABLE_WOODEN_MALLET = b.comment("Disable the use of the wooden mallet (still allows crafting for other recipes). This is enabled by default for progression.")
                    .define("disableWoodenMallet", true);
            DISABLE_COMPASS = b.comment("Disable status bar message from compass.")
                    .define("disableCompass",false);
            DISABLE_CLOCK = b.comment("Disable status bar message from clock.")
                    .define("disableClock",false);
            DISABLE_ALTIMETER = b.comment("Disable status bar message from altimeter.")
                    .define("disableAltimeter",false);
            DISABLE_PHOTOMETER = b.comment("Disable status bar message from photometer.")
                    .define("disablePhotmeter",false);
            DISABLE_SPEEDOMETER = b.comment("Disable status bar message from speedometer.")
                    .define("disableSpeedometer",false);
            DISABLE_THERMOMETER = b.comment("Disable status bar message from thermometer.")
                    .define("disableThermometer",false);
            DISABLE_BIOMETER = b.comment("Disable status bar message from biometer.")
                    .define("disableBiometer",false);
            b.pop();

            b.comment("Vanilla Tools").push("vanillaTools");
            b.comment("Wooden Tools").push("woodenTools");
            DISABLE_WOODEN_SWORD = b.comment("Disable the use of the wooden sword (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableWoodenSword", false);
            DISABLE_WOODEN_AXE = b.comment("Disable the use of the wooden axe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableWoodenAxe", false);
            DISABLE_WOODEN_SHOVEL = b.comment("Disable the use of the wooden shovel (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableWoodenShovel", false);
            DISABLE_WOODEN_PICKAXE = b.comment("Disable the use of the wooden pickaxe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableWoodenPickaxe", false);
            DISABLE_WOODEN_HOE = b.comment("Disable the use of the wooden hoe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableWoodenHoe", false);
            b.pop();
            b.comment("Stone Tools").push("stoneTools");
            DISABLE_STONE_SWORD = b.comment("Disable the use of the stone sword (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableStoneSword", false);
            DISABLE_STONE_AXE = b.comment("Disable the use of the stone axe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableStoneAxe", false);
            DISABLE_STONE_SHOVEL = b.comment("Disable the use of the stone shovel (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableStoneShovel", false);
            DISABLE_STONE_PICKAXE = b.comment("Disable the use of the stone pickaxe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableStonePickaxe", false);
            DISABLE_STONE_HOE = b.comment("Disable the use of the stone hoe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableStoneHoe", false);
            b.pop();
            b.comment("Iron Tools").push("ironTools");
            DISABLE_IRON_SWORD = b.comment("Disable the use of the iron sword (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableIronSword", false);
            DISABLE_IRON_AXE = b.comment("Disable the use of the iron axe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableIronAxe", false);
            DISABLE_IRON_SHOVEL = b.comment("Disable the use of the iron shovel (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableIronShovel", false);
            DISABLE_IRON_PICKAXE = b.comment("Disable the use of the iron pickaxe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableIronPickaxe", false);
            DISABLE_IRON_HOE = b.comment("Disable the use of the iron hoe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableIronHoe", false);
            b.pop();
            b.comment("Gold Tools").push("goldTools");
            DISABLE_GOLDEN_SWORD = b.comment("Disable the use of the gold sword (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableGoldSword", false);
            DISABLE_GOLDEN_AXE = b.comment("Disable the use of the gold axe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableGoldAxe", false);
            DISABLE_GOLDEN_SHOVEL = b.comment("Disable the use of the gold shovel (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableGoldShovel", false);
            DISABLE_GOLDEN_PICKAXE = b.comment("Disable the use of the gold pickaxe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableGoldPickaxe", false);
            DISABLE_GOLDEN_HOE = b.comment("Disable the use of the gold hoe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableGoldHoe", false);
            b.pop();
            b.comment("Diamond Tools").push("diamondTools");
            DISABLE_DIAMOND_SWORD = b.comment("Disable the use of the diamond sword (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableDiamondSword", false);
            DISABLE_DIAMOND_AXE = b.comment("Disable the use of the diamond axe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableDiamondAxe", false);
            DISABLE_DIAMOND_SHOVEL = b.comment("Disable the use of the diamond shovel (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableDiamondShovel", false);
            DISABLE_DIAMOND_PICKAXE = b.comment("Disable the use of the diamond pickaxe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableDiamondPickaxe", false);
            DISABLE_DIAMOND_HOE = b.comment("Disable the use of the diamond hoe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableDiamondHoe", false);
            b.pop();
            b.comment("Netherite Tools").push("netheriteTools");
            DISABLE_NETHERITE_SWORD = b.comment("Disable the use of the netherite sword (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableNetheriteSword", false);
            DISABLE_NETHERITE_AXE = b.comment("Disable the use of the netherite axe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableNetheriteAxe", false);
            DISABLE_NETHERITE_SHOVEL = b.comment("Disable the use of the netherite shovel (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableNetheriteShovel", false);
            DISABLE_NETHERITE_PICKAXE = b.comment("Disable the use of the netherite pickaxe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableNetheritePickaxe", false);
            DISABLE_NETHERITE_HOE = b.comment("Disable the use of the netherite hoe (still allows crafting for other recipes). This is disabled by default for progression.")
                    .define("disableNetheriteHoe", false);
            b.pop();
            b.pop();
            b.pop();
        }
    }


    public static class General {
        public final ForgeConfigSpec.BooleanValue MOVEMENT_MODIFIERS;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_SAND;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_GRASS_PATH;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_BRICKS;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_ROMAN_CONCRETE;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_DIRT;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_POLISHED_STONE;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_WOODEN;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_CONCRETE;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_SNOW;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_MUD;

        public final ForgeConfigSpec.BooleanValue CROWBAR_FROM_ABOVE;
        public final ForgeConfigSpec.BooleanValue PLAYER_PRYING_ENCHANTMENT;

        public final ForgeConfigSpec.DoubleValue NUGGET_CHANCE;
        public final ForgeConfigSpec.IntValue NUGGET_DISTANCE;
        public final ForgeConfigSpec.IntValue PROSPECTING_STICK_RANGE;
        public final ForgeConfigSpec.IntValue ORE_DETECTOR_RANGE;
        public final ForgeConfigSpec.BooleanValue ORE_DETECTOR_MSG;
        public final ForgeConfigSpec.BooleanValue ROCK_DRILL;




        public final ForgeConfigSpec.IntValue MAX_TREE;
        public final ForgeConfigSpec.BooleanValue TREE_CHOPPING;
        public final ForgeConfigSpec.DoubleValue TREE_CHOP_SPEED;
        public final ForgeConfigSpec.DoubleValue LEAF_LITTER_GEN;
        public final ForgeConfigSpec.DoubleValue LEAF_LITTER_GEN_TREES;

        public final ForgeConfigSpec.IntValue PATH_CREATION_TIME;
        public final ForgeConfigSpec.BooleanValue PATH_CREATION;
        public final ForgeConfigSpec.BooleanValue COLOR_WORLD;
        public final ForgeConfigSpec.BooleanValue FUEL_VALUES;
        public final ForgeConfigSpec.BooleanValue FLINT_FIRE;
        public final ForgeConfigSpec.BooleanValue STUMP_CREATION;
        public final ForgeConfigSpec.BooleanValue STRIPPABLES_CORK;
        public final ForgeConfigSpec.BooleanValue STRIPPABLES_PAPER;
        public final ForgeConfigSpec.BooleanValue STRIPPABLES_CINNAMON;
        public final ForgeConfigSpec.BooleanValue STRIPPABLES_STICKS;
        public final ForgeConfigSpec.DoubleValue FLINT_FIRE_CHANCE;
        public final ForgeConfigSpec.DoubleValue FLINT_DROP_CHANCE;
        public final ForgeConfigSpec.DoubleValue FORAGING_CHANCE;
        public final ForgeConfigSpec.DoubleValue GRASS_GROW_CHANCE;
        public final ForgeConfigSpec.DoubleValue PODZOL_GROW_CHANCE;
        public final ForgeConfigSpec.BooleanValue MANDATORY_AXE;
        public final ForgeConfigSpec.BooleanValue REFRESH_ALLOYS;
        public final ForgeConfigSpec.BooleanValue STARTING_BOOK;
        public final ForgeConfigSpec.BooleanValue DISABLE_WATER;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> FUEL_VALUES_LIST;
        public final ForgeConfigSpec.BooleanValue PENDANT_CURSE;
        public final ForgeConfigSpec.BooleanValue VILLAGER_TRADES;
        public final ForgeConfigSpec.BooleanValue WANDERING_TRADE_SPECIAL;
        public final ForgeConfigSpec.DoubleValue ROCK_GENERATOR_REMOVAL_CHANCE;
        public final ForgeConfigSpec.BooleanValue IGNEOUS_COBBLE_GEN;
        public final ForgeConfigSpec.BooleanValue METAMORPHIC_STONE_GEN;
        public final ForgeConfigSpec.DoubleValue GLOBAL_BREAK_EXHAUSTION;
        public final ForgeConfigSpec.DoubleValue CHEESE_AGE_CHANCE;
        public final ForgeConfigSpec.DoubleValue ICE_BREAK;
        public final ForgeConfigSpec.DoubleValue GEODE_CHANCE;
        public final ForgeConfigSpec.IntValue HERBICIDE_RANGE;
        public final ForgeConfigSpec.IntValue TRAMPOLINE_SIZE;
        public final ForgeConfigSpec.IntValue FIRE_EXTINGUISHER_RANGE;
        public final ForgeConfigSpec.IntValue FORCE_BREAK;

        public General(ForgeConfigSpec.Builder b) {
            b.comment("Settings for general mechanics").push("general");

                b.comment("Miscellaneous").push("misc");
                    PATH_CREATION_TIME = b.comment("Roughly the time in seconds between chance for blocks to be changed to a path block when stepped on.")
                            .defineInRange("pathCreationTime", 10, 0, 600);
                    PATH_CREATION = b.comment("If enabled, walking on grass blocks, mycelium and podzol has a chance to create a path block underfoot.")
                            .define("pathCreation",true);
                    COLOR_WORLD = b.comment("If enabled, dyes can be used on blocks in-world to dye them (includes concrete, concrete powder, terracotta, glazed terracotta, stained glass, stained glass panes, leds, wool)")
                            .define("colorWorld",true);
                    STRIPPABLES_CINNAMON = b.comment("If enabled, cinnamon will drop from cinnamon trees when stripped.")
                            .define("strippablesCinnamon",true);
                    STRIPPABLES_CORK = b.comment("If enabled, cork will drop from cork oak trees when stripped.")
                            .define("strippablesCork",true);
                    STRIPPABLES_PAPER = b.comment("If enabled, paper will drop from birch trees when stripped (30% chance).")
                            .define("strippablesPaper",true);
                    STRIPPABLES_STICKS = b.comment("If enabled, sticks will drop from all other trees when stripped (30% chance).")
                            .define("strippablesSticks",true);
                    HERBICIDE_RANGE = b.comment("The radius at which herbicide will kill plants.")
                            .defineInRange("herbicideRange", 8, 0, 32);
                    ROCK_DRILL = b.comment("Enable the use of the rock drill.")
                            .define("rockDrill",true);
                    DISABLE_WATER = b.comment("No more infinite water")
                            .define("disableWater",true);
                    FUEL_VALUES_LIST = b.comment("List of blocks and their respective burn time. Works with tags. NOT IMPLEMENTED YET")
                            .defineList("fuelValues", Arrays.asList("#minecraft:oak_logs|410","#rankine:cedar_logs|410"), o -> o instanceof String);
                    FIRE_EXTINGUISHER_RANGE = b.comment("The range of the fire extinguisher.")
                            .defineInRange("fireExtinguisherRange", 16, 0, 64);
                    TRAMPOLINE_SIZE = b.comment("The maximum size of a trampoline. Jump factor depends on size. Set to 0 to have a fixed jump factor of 1.3 which is just enough to have the player gain height over time.")
                            .defineInRange("trampolineSize", 289, 0, 961);
                    CHEESE_AGE_CHANCE = b.comment("Chance for unaged cheese to age in a random tick.")
                            .defineInRange("cheeseAgeChance", 0.04D, 0.0D, 1.0D);
                    GEODE_CHANCE = b.comment("Chance for a geode to be found in stone.")
                            .defineInRange("geodeChance", 0.0005D, 0.0D, 1.0D);
                    STARTING_BOOK = b.comment("Enables the Rankine Journal (a guide to the mod, requires Patchouli)")
                            .define("startingBook",true);
                    REFRESH_ALLOYS = b.comment("If enabled, alloy-related content in the player's inventory will always refresh on world join. Useful for modifying element recipes and quickly determining changes.")
                            .define("refreshAlloys",false);
                    PENDANT_CURSE = b.comment("Causes Pendants to spawn in with Curse of Vanishing.")
                            .define("pendantCurse",true);
                    MANDATORY_AXE = b.comment("Makes axes required to harvest logs.")
                            .define("axesOnly",false);
                    CROWBAR_FROM_ABOVE = b.comment("Allows crowbars to move blocks below where the player is standing.")
                            .define("crowbarFromAbove",true);
                    FUEL_VALUES = b.comment("Change the fuel values of items for realism.")
                            .define("fuelValuesChange",true);
                    FLINT_DROP_CHANCE = b.comment("Chance for a stone block to drop a flint")
                            .defineInRange("flintDropChance", 0.15D, 0.00D, 1.00D);
                    FORAGING_CHANCE = b.comment("Chance for a dirt block to drop a vegetable/seed")
                            .defineInRange("foragingChance", 0.10D, 0.00D, 1.00D);
                    GRASS_GROW_CHANCE = b.comment("Chance for a grass block to grow something on a random tick")
                            .defineInRange("grassGrowChance", 0.005D, 0.00D, 1.00D);
                    PODZOL_GROW_CHANCE = b.comment("Chance for a podzol block to grow on grass")
                            .defineInRange("podzolGrowChance", 0.0005D, 0.00D, 1.00D);
                    ROCK_GENERATOR_REMOVAL_CHANCE = b.comment("Chance for a mineral block to be removed from any rock generator process.")
                            .defineInRange("rockGenRemovalChance", 0.01D, 0.00D, 1.00D);
                    IGNEOUS_COBBLE_GEN = b.comment("Change the output of a cobblestone generator and basalt generator to intrusive and extrusive igneous rocks respectively.")
                            .define("igneousGen",true);
                    METAMORPHIC_STONE_GEN = b.comment("Change the output of a stone generator from stone to metamorphic rocks.")
                            .define("igneousGen",true);
                    VILLAGER_TRADES = b.comment("Adds trades for Project Rankine to Villagers and the Wandering Trader.")
                            .define("villageTrades",true);
                    WANDERING_TRADE_SPECIAL = b.comment("Adds a trade to the Wandering Trader for a random tool which is not restricted by alloy constraints. May be unbalanced due to complete randomness.")
                            .define("wanderingSpecial",true);
                    GLOBAL_BREAK_EXHAUSTION = b.comment("Amount of additional exhaustion when breaking a block.")
                            .defineInRange("breakExhaustion", 0.00D, 0.00D, 1.00D);
                    ICE_BREAK = b.comment("Chance for ice to break when walking on it.")
                            .defineInRange("iceBreak", 0.002D, 0.0D, 1.0D);
                    FLINT_FIRE = b.comment("Enable the lighting of fires and certain machines using two flint.")
                            .define("flintFire",true);
                    FLINT_FIRE_CHANCE = b.comment("Chance for flint to be consumed when lighting a fire.")
                            .defineInRange("flintFireChance", 0.30D, 0.00D, 1.00D);
                b.pop();

                b.comment("Tree Configs").push("treeConfigs");
                    TREE_CHOPPING = b.comment("Enable full tree chopping using #rankine:tree_choppers")
                            .define("treeChopping",true);
                    MAX_TREE = b.comment("Maximum blocks to be considered a tree. Set to 0 to disable tree capitation.")
                        .defineInRange("maxTree", 256, 0, 1024);
                    TREE_CHOP_SPEED = b.comment("Speed factor for chopping trees after size is accounted for.")
                            .defineInRange("treeChopSpeedFactor", 0.7D, 0.0D, 2.0D);
                    LEAF_LITTER_GEN = b.comment("Chance for leaves to drop leaf litter on a random tick")
                            .defineInRange("leafLitterChance", 0.01D, 0.0D, 1.0D);
                    LEAF_LITTER_GEN_TREES = b.comment("Chance for leaves to drop leaf litter on break from chopping")
                            .defineInRange("leafLitterChanceChop", 0.1D, 0.0D, 1.0D);
                    STUMP_CREATION = b.comment("Creates stumps when tree chopping.")
                            .define("createStumps",true);
                    FORCE_BREAK = b.comment("The range to force break leaves.")
                            .defineInRange("forceBreakRange", 3, 0, 10);
                b.pop();

                b.comment("Movement speed modifiers").push("movementModifiers");
                    MOVEMENT_MODIFIERS = b.comment("Set to false to disable movement speed modifiers.")
                            .define("movementModifiersEnabled",true);
                    MOVEMENT_SAND = b.comment("Movement speed modifier for walking on Sand blocks.")
                            .defineInRange("movementSand", -0.02D, -1.0D, 1.0D);
                    MOVEMENT_BRICKS = b.comment("Movement speed modifier for walking on Brick / Stone Bricks and variants.")
                            .defineInRange("movementBricks", 0.05D, -1.0D, 1.0D);
                    MOVEMENT_GRASS_PATH = b.comment("Movement speed modifier for walking on Grass Paths.")
                            .defineInRange("movementGrassPath", 0.00D, -1.0D, 1.0D);
                    MOVEMENT_ROMAN_CONCRETE = b.comment("Movement speed modifier for walking on Roman Cooncrete.")
                            .defineInRange("movementRomanConcrete", 0.1D, -1.0D, 1.0D);
                    MOVEMENT_DIRT = b.comment("Movement speed modifier for walking on Dirt / Grass blocks.")
                            .defineInRange("movementDirt", -0.01D, -1.0D, 1.0D);
                    MOVEMENT_POLISHED_STONE = b.comment("Movement speed modifier for walking on #forge:polished_stone blocks.")
                            .defineInRange("movementPolishedStone", 0.00D, -1.0D, 1.0D);
                    MOVEMENT_WOODEN = b.comment("Movement speed modifier for walking on Planks and wooden variants.")
                            .defineInRange("movementWooden", 0.00D, -1.0D, 1.0D);
                    MOVEMENT_CONCRETE = b.comment("Movement speed modifier for walking on Concrete / Gravel Concrete.")
                            .defineInRange("movementConcrete", 0.05D, -1.0D, 1.0D);
                    MOVEMENT_SNOW = b.comment("Movement speed modifier for walking on Snow.")
                            .defineInRange("movementSnow", -0.02D, -1.0D, 1.0D);
                    MOVEMENT_MUD = b.comment("Movement speed modifier for walking on Mud.")
                            .defineInRange("movementMud", -0.02D, -1.0D, 1.0D);
                b.pop();

                b.comment("Ore Detection").push("oreDetection");
                    PROSPECTING_STICK_RANGE = b.comment("Number of blocks away that the Prospecting Stick can detect ore.")
                            .defineInRange("prospectingStickRange", 16, 0, 64);
                    ORE_DETECTOR_RANGE = b.comment("Number of blocks away that the Ore Detector can detect ore.")
                            .defineInRange("oreDetectorRange", 32, 0, 64);
                    ORE_DETECTOR_MSG = b.comment("Set to false to disable the ore detector from outputting the block found.")
                            .define("oreDetectorMessage",true);
                    NUGGET_CHANCE = b.comment("Chance for a block in #rankine:nugget_stones to drop a nugget of a nearby ore.")
                            .defineInRange("nuggetChance", 0.04D, 0.00D, 1.00D);
                    NUGGET_DISTANCE = b.comment("Distance from an ore block in which nuggets have a chance to drop from blocks.")
                            .defineInRange("nuggetRange", 9, 1, 64);
                b.pop();

                b.comment("Enchantments").push("enchantments");
                    PLAYER_PRYING_ENCHANTMENT = b.comment("Enables the Prying enchantment to work on players (when hit by crowbar, chance to drop held item).")
                            .define("playerPryingEnchantment",true);
                b.pop();


            b.pop();
        }
    }

    public static class Alloys {
        public final ForgeConfigSpec.BooleanValue ALLOY_CORROSION;
        public final ForgeConfigSpec.IntValue ALLOY_CORROSION_AMT;
        public final ForgeConfigSpec.BooleanValue ALLOY_HEAT;
        public final ForgeConfigSpec.IntValue ALLOY_HEAT_AMT;
        public final ForgeConfigSpec.BooleanValue ALLOY_TOUGHNESS;
        public final ForgeConfigSpec.DoubleValue ALLOY_WEAR_MINING_AMT;
        public final ForgeConfigSpec.DoubleValue ALLOY_WEAR_DAMAGE_AMT;

        public final ForgeConfigSpec.IntValue ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_TOUGHNESS;

        public Alloys(ForgeConfigSpec.Builder b) {
            b.comment("Settings for alloys and alloy tools").push("alloys");
                ALLOY_CORROSION = b.comment("Enables the corrosion negative modifier for alloy tools (chance to consume extra points of durability in water and rain)")
                        .define("alloyCorrosion",true);
                ALLOY_CORROSION_AMT = b.comment("If enabled, modifies the amount of durability damage taken in wet environments.")
                        .defineInRange("alloyCorrosionDmgAmount", 1, 1, 10);
                ALLOY_HEAT = b.comment("Enables the heat negative modifier for alloy tools (chance to consume extra points of durability in hot environments and lava)")
                        .define("alloyHeat",true);
                ALLOY_HEAT_AMT = b.comment("If enabled, modifies the amount of durability damage taken in hot environments.")
                        .defineInRange("alloyHeatDmgAmount", 1, 1, 10);
                ALLOY_TOUGHNESS = b.comment("Enables the toughness negative modifier for alloy tools (chance to consume/resist loss of an extra point of durability)")
                        .define("alloyToughness",true);
                ALLOY_WEAR_MINING_AMT = b.comment("Modifies the severity of the wear effect on mining speed (ex. 0.25 means mining speed will be reduced to 75% of the original value as durability is lost)")
                        .defineInRange("alloyWearMiningAmount", 0.25D, 0.00D, 0.99D);
                ALLOY_WEAR_DAMAGE_AMT = b.comment("Modifies the severity of the wear effect on damage (ex. 0.25 means damage will be reduced to 75% of the original value as durability is lost)")
                        .defineInRange("alloyWearDamageAmount", 0.25D, 0.00D, 0.99D);
                b.pop();
                b.comment("Custom Alloy Tool Properties").push("alloy");
                ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for the custom alloy tools.")
                        .defineInRange("alloyBonusDurability", 0, 0, 10000);
                ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for the custom alloy tools.")
                        .defineInRange("alloyBonusMiningSpeed", 0.0D, 0D, 20D);
                ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for the custom alloy tools.")
                        .defineInRange("alloyBonusHL", 0, 0, 10);
                ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for the custom alloy tools.")
                        .defineInRange("alloyBonusEnchantability", 0, 0, 40);
                ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for the custom alloy tools.")
                        .defineInRange("alloyBonusAttackSpeed", 0.0D, 0D, 4D);
                ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for the custom alloy tools.")
                        .defineInRange("alloyBonusDamage", 0.0D, 0D, 20D);
                ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for the custom alloy tools.")
                        .defineInRange("alloyBonusCorrResist", 0.0D, 0D, 1D);
                ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for the custom alloy tools.")
                        .defineInRange("alloyBonusHeatResist", 0.0D, 0D, 1D);
                ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for the custom alloy tools.")
                        .defineInRange("alloyBonusToughness", 0D, -1D, 1D);
                b.pop();

        }
    }

    public static class Machines {

        public final ForgeConfigSpec.IntValue GROUND_TAP_SPEED;
        public final ForgeConfigSpec.IntValue SEDIMENT_FAN_SPEED;

        public final ForgeConfigSpec.IntValue CHARCOAL_PIT_SPEED;
        public final ForgeConfigSpec.IntValue CHARCOAL_PIT_RADIUS;
        public final ForgeConfigSpec.IntValue CHARCOAL_PIT_HEIGHT;
        public final ForgeConfigSpec.IntValue EVAPORATION_TOWER_RANGE;
        public final ForgeConfigSpec.IntValue ALNICO_MAGNET_RANGE;
        public final ForgeConfigSpec.IntValue RARE_MAGNET_RANGE;
        public final ForgeConfigSpec.BooleanValue ELECTROMAGNET_MATERIAL_REQ;
        public final ForgeConfigSpec.IntValue BEEHIVE_OVEN_SKYLIGHT;
        public final ForgeConfigSpec.IntValue LASER_QUARRY_RANGE;
        public final ForgeConfigSpec.IntValue LASER_QUARRY_SPEED;
        public final ForgeConfigSpec.IntValue GYRATORY_CRUSHER_POWER;
        public final ForgeConfigSpec.IntValue INDUCTION_FURNACE_POWER;

        public final ForgeConfigSpec.IntValue AIR_DISTILLATION_SPEED;


        public Machines(ForgeConfigSpec.Builder b) {
            b.comment("Settings for machines").push("machines");
                b.comment("RSettings for the Distillation Tower").push("distillationTower");
                AIR_DISTILLATION_SPEED = b.comment("Processing speed of the air distillation tower")
                        .defineInRange("airDistillationSpeed", 100, 10, Integer.MAX_VALUE);
                b.pop();
                GROUND_TAP_SPEED = b.comment("The number of ticks it takes the Ground Tap to process")
                        .defineInRange("groundTapSpeed", 600, 0, 100000);
                SEDIMENT_FAN_SPEED = b.comment("The number of ticks it takes the Sediment Fan to process")
                        .defineInRange("sedimentFanSpeed", 300, 0, 100000);
                CHARCOAL_PIT_RADIUS = b.comment("Maximum radius the charcoal pit can convert logs.")
                        .defineInRange("charcoalPitRadius", 7, 3, 15);
                CHARCOAL_PIT_SPEED = b.comment("The number of random ticks it takes the Charcoal Pit to process")
                        .defineInRange("charcoalPitSpeed", 2, 1, 200);
                CHARCOAL_PIT_HEIGHT = b.comment("Maximum height a charcoal pile can be")
                        .defineInRange("charcoalPitHeight", 5, 1, 10);
                EVAPORATION_TOWER_RANGE = b.comment("Maximum height of the evaporation tower. Height affects yields. Set to 0 to disable functionality.")
                        .defineInRange("evaporationTowerHeight", 15, 0, 30);
                ALNICO_MAGNET_RANGE = b.comment("Range for the Alnico Magnet's pickup radius and the Alnico Electromagnet range.")
                        .defineInRange("alnicoMagnetRange",5,1,10);
                RARE_MAGNET_RANGE = b.comment("Range for the Rare Earth Magnet's pickup radius and the Rare Earth Electromagnet range.")
                        .defineInRange("rareEarthMagnetRange",10,1,15);
                ELECTROMAGNET_MATERIAL_REQ = b.comment("Require the material of the block to be Material.IRON in order for the electromagnet to pull the block. If disabled, it will pick up any block as long as it is not a FluidBlock, Tile Entity, or in the rankine:magnet_banned tag (these blocks are also banned if this value is true).")
                        .define("electromagnetMaterialReq",true);
                BEEHIVE_OVEN_SKYLIGHT = b.comment("If disabled, the beehive ovens will not require sky access.")
                        .defineInRange("beehiveOvenAirHeight", 16, 0, 400);
                LASER_QUARRY_RANGE = b.comment("Max range of the laser quarry. Larger numbers may cause lag. Set to 0 to disable functionality.")
                        .defineInRange("laserQuarryRange", 31, 0, 63);
                LASER_QUARRY_SPEED = b.comment("Max speed of the laser quarry in ticks.")
                        .defineInRange("laserQuarrySpeed", 20, 1, 300);
                GYRATORY_CRUSHER_POWER = b.comment("Defines the power requirement for one process in the gyratory crusher (multiplied by the tier of the current crushing head).")
                        .defineInRange("gyratoryCrusherPower", 1, 0, 10000);
                INDUCTION_FURNACE_POWER = b.comment("Defines the power requirement for one process in the induction furnace.")
                        .defineInRange("inductionFurnacePower", 16, 0, 10000);
            b.pop();
        }
    }

    public static class Gases {
        public final ForgeConfigSpec.BooleanValue GAS_MOVEMENT;
        public final ForgeConfigSpec.BooleanValue ENABLE_GAS_VENTS;
        public final ForgeConfigSpec.BooleanValue GAS_AFFECT_UNDEAD;
        public final ForgeConfigSpec.BooleanValue GAS_DISSIPATION;


        public Gases(ForgeConfigSpec.Builder b) {
            b.comment("Settings for Gases.").push("gases");
            GAS_MOVEMENT = b.comment("If enabled, gases will move on random tick and dissipate at or above y-level 95 (EXPERIMENTAL).")
                    .define("gasMovement", true);
            GAS_AFFECT_UNDEAD = b.comment("If enabled, gas effects will work against undead mobs.")
                    .define("gasAffectUndead", true);
            ENABLE_GAS_VENTS = b.comment("Enables blocks which emit gases on random tick.")
                    .define("enableGasVents", true);
            GAS_DISSIPATION = b.comment("Enables gas blocks to have a chance to remove themselves on random tick (based on the gas).")
                    .define("enableGasDissipation", true);

            b.pop();
        }
    }

    public static class HardMode {
        public final ForgeConfigSpec.BooleanValue WATER_REACTIVE;
        public final ForgeConfigSpec.BooleanValue RADIOACTIVE;


        public HardMode(ForgeConfigSpec.Builder b) {
            b.comment("Settings for Hard Mode mechanics (HIGHLY EXPERIMENTAL).").push("hardMode");
            WATER_REACTIVE = b.comment("If enabled, certain elements will react with water. Generally creates an explosion.")
                    .define("elementWaterReactive", false);
            RADIOACTIVE = b.comment("If enabled, certain elements will be radioactive which applies a radiation potion effect that causes damage over time.")
                    .define("elementRadioactive", false);
            b.pop();
        }
    }

    public static class BiomeGen {
        public final ForgeConfigSpec.ConfigValue<List<? extends List<Object>>> BIOME_SETTINGS;
        private static final List<List<Object>> biomeSettings = new ArrayList<>();

        public final ForgeConfigSpec.ConfigValue<List<? extends List<Object>>> ORE_SETTINGS;
        private static final List<List<Object>> oreSettings = new ArrayList<>();

        public BiomeGen(ForgeConfigSpec.Builder b) {
            biomeSettings.add(Arrays.asList("minecraft:soul_sand_valley",
                    Arrays.asList(),
                    Arrays.asList("minecraft:air|15|minecraft:air|0.0","rankine:scoria|1|rankine:scoria|0.0","rankine:pumice|1|rankine:pumice|0.0"),
                    Arrays.asList("rankine:eclogite","rankine:blueschist","rankine:honeystone"),
                    Arrays.asList(),
                    "rankine:dark_gravel",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList("minecraft:basalt_deltas",
                    Arrays.asList(),
                    Arrays.asList("minecraft:air|15|minecraft:air|0.0","rankine:scoria|1|rankine:scoria|0.0","rankine:pumice|1|rankine:pumice|0.0"),
                    Arrays.asList("rankine:pyroxenite","minecraft:basalt","rankine:gray_marble"),
                    Arrays.asList(),
                    "rankine:dark_gravel",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList("minecraft:crimson_forest",
                    Arrays.asList(),
                    Arrays.asList("minecraft:air|15|minecraft:air|0.0","rankine:scoria|1|rankine:scoria|0.0","rankine:pumice|1|rankine:pumice|0.0"),
                    Arrays.asList("rankine:purple_porphyry","rankine:komatiite","rankine:rose_marble"),
                    Arrays.asList(),
                    "rankine:dark_gravel",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList("minecraft:warped_forest",
                    Arrays.asList(),
                    Arrays.asList("minecraft:air|15|minecraft:air|0.0","rankine:scoria|1|rankine:scoria|0.0","rankine:pumice|1|rankine:pumice|0.0"),
                    Arrays.asList("rankine:serpentinite","rankine:greenschist","rankine:peridotite"),
                    Arrays.asList(),
                    "rankine:dark_gravel",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList("minecraft:nether_wastes",
                    Arrays.asList(),
                    Arrays.asList("minecraft:air|15|minecraft:air|0.0","rankine:scoria|1|rankine:scoria|0.0","rankine:pumice|1|rankine:pumice|0.0"),
                    Arrays.asList("minecraft:netherrack"),
                    Arrays.asList(),
                    "rankine:dark_gravel",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.THEEND.getName(),
                    Arrays.asList(),
                    Arrays.asList(),
                    Arrays.asList("minecraft:end_stone"),
                    Arrays.asList(),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.NONE.getName(),
                    Arrays.asList("rankine:sandy_loam_grass_block","rankine:sandy_loam","rankine:sandy_clay_loam","rankine:sandy_clay_loam_grass_block","rankine:sandy_clay_loam","rankine:sandy_clay"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","rankine:diabase|3|rankine:baddeleyite_ore|0.005","minecraft:stone|1|minecraft:gold_ore|0.01"),
                    Arrays.asList("rankine:troctolite","rankine:gabbro","rankine:tholeiitic_basalt"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.MUSHROOM.getName(),
                    Arrays.asList("rankine:humus_grass_block","rankine:humus","rankine:clay_loam","rankine:clay_loam_grass_block","rankine:clay_loam","rankine:clay_loam"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","rankine:pegmatite|3|rankine:petalite_ore|0.02","minecraft:stone|1|minecraft:coal_ore|0.01"),
                    Arrays.asList("rankine:troctolite","rankine:gabbro","rankine:tholeiitic_basalt","rankine:marlstone"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10","minecraft:red_mushroom|10","minecraft:brown_mushroom|10","rankine:crimson_clover|10"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.OCEAN.getName(),
                    Arrays.asList("rankine:sandy_clay_loam_grass_block","rankine:sandy_clay_loam","rankine:sandy_clay","rankine:silty_clay_loam_grass_block","rankine:silty_clay_loam","rankine:silty_clay"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","rankine:diabase|3|rankine:baddeleyite_ore|0.005","minecraft:stone|1|minecraft:gold_ore|0.01"),
                    Arrays.asList("rankine:troctolite","rankine:gabbro","rankine:tholeiitic_basalt"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.BEACH.getName(),
                    Arrays.asList("rankine:sandy_loam_grass_block","rankine:sandy_loam","rankine:sandy_clay_loam","rankine:sandy_clay_loam_grass_block","rankine:sandy_clay_loam","rankine:sandy_clay"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","minecraft:granite|3|rankine:malachite_ore|0.02","minecraft:stone|1|minecraft:redstone_ore|0.01"),
                    Arrays.asList("rankine:troctolite","rankine:gabbro","rankine:chalk","rankine:marlstone"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10","rankine:yellow_clover|10","rankine:stinging_nettle|1"),
                    "rankine:light_gravel",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.RIVER.getName(),
                    Arrays.asList("rankine:clay_loam_grass_block","rankine:clay_loam","rankine:silty_clay","rankine:silty_clay_loam_grass_block","rankine:silty_clay_loam","rankine:silty_clay"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015"),
                    Arrays.asList("rankine:black_dacite","rankine:phyllite","rankine:shale"),
                    Arrays.asList("rankine:short_grass|20","minecraft:grass|50","rankine:stinging_nettle|1"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.SAVANNA.getName(),
                    Arrays.asList("rankine:silty_loam_grass_block","rankine:silty_loam","rankine:silty_clay_loam","rankine:sandy_loam_grass_block","rankine:sandy_loam","rankine:sandy_clay_loam"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","rankine:norite|3|rankine:norite|0.00","minecraft:stone|1|minecraft:redstone_ore|0.01"),
                    Arrays.asList("rankine:rhyolite","rankine:red_dacite","minecraft:andesite","rankine:siltstone"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10","rankine:yellow_clover|10","rankine:white_clover|10","rankine:stinging_nettle|1"),
                    "rankine:light_gravel",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.MESA.getName(),
                    Arrays.asList("rankine:silty_loam_grass_block","rankine:silty_loam","rankine:silty_clay_loam","rankine:silty_loam_grass_block","rankine:silty_loam","rankine:silty_clay_loam"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","rankine:red_porphyry|3|rankine:porphyry_copper|0.02","minecraft:stone|1|minecraft:gold_ore|0.01"),
                    Arrays.asList("rankine:rhyolite","rankine:red_dacite","rankine:quartzite","rankine:arkose"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10","rankine:yellow_clover|10","rankine:stinging_nettle|1"),
                    "rankine:light_gravel",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.DESERT.getName(),
                    Arrays.asList("rankine:loamy_sand_grass_block","rankine:loamy_sand","rankine:loamy_sand","rankine:loamy_sand_grass_block","rankine:loamy_sand","rankine:loamy_sand"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","rankine:red_porphyry|3|rankine:porphyry_copper|0.02","minecraft:stone|1|minecraft:lapis_ore|0.01"),
                    Arrays.asList("rankine:rhyolite","rankine:red_dacite","rankine:quartzite","rankine:itacolumite"),
                    Arrays.asList("rankine:short_grass|30","minecraft:dead_bush|10","rankine:stinging_nettle|1"),
                    "rankine:light_gravel",
                    "rankine:desert_sand",
                    "rankine:desert_sandstone"));
            biomeSettings.add(Arrays.asList(Biome.Category.EXTREME_HILLS.getName(),
                    Arrays.asList("rankine:sandy_loam_grass_block","rankine:sandy_loam","rankine:sandy_clay_loam","rankine:loamy_sand_grass_block","rankine:loamy_sand","rankine:sandy_clay_loam"),
                    Arrays.asList("minecraft:air|6|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","minecraft:stone|1|minecraft:coal_ore|0.01","rankine:shonkinite|1|rankine:beryl_ore|0.01","minecraft:diorite|2|rankine:ilmenite_ore|0.01"),
                    Arrays.asList("rankine:black_dacite","rankine:mariposite","rankine:gneiss","rankine:anorthosite","rankine:hornblende_andesite"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10","rankine:red_clover|10","rankine:white_clover|10","rankine:stinging_nettle|1"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.FOREST.getName(),
                    Arrays.asList("rankine:loam_grass_block","rankine:loam","rankine:clay_loam","rankine:sandy_loam_grass_block","rankine:sandy_loam","rankine:sandy_clay_loam"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","minecraft:stone|1|minecraft:iron_ore|0.01","rankine:gray_granite|2|rankine:cassiterite_ore|0.02","rankine:granodiorite|1|rankine:magnetite_ore|0.02"),
                    Arrays.asList("rankine:black_marble","rankine:black_dacite","rankine:anorthosite","rankine:limestone"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10","rankine:yellow_clover|10","rankine:white_clover|10","rankine:stinging_nettle|1"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.TAIGA.getName(),
                    Arrays.asList("rankine:sandy_loam_grass_block","rankine:sandy_loam","rankine:sandy_clay_loam","rankine:loamy_sand_grass_block","rankine:loamy_sand","rankine:sandy_clay_loam"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","minecraft:stone|1|minecraft:iron_ore|0.01","minecraft:diorite|1|rankine:ilmenite_ore|0.01","minecraft:granite|2|rankine:malachite_ore|0.02"),
                    Arrays.asList("rankine:white_marble","rankine:black_dacite","rankine:anorthosite","rankine:dolostone"),
                    Arrays.asList("rankine:short_grass|70","minecraft:fern|10","rankine:red_clover|10","rankine:white_clover|10","rankine:stinging_nettle|1"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.ICY.getName(),
                    Arrays.asList("rankine:silty_loam_grass_block","rankine:silty_loam","rankine:silty_clay_loam","rankine:silty_clay_loam_grass_block","rankine:silty_clay_loam","rankine:silty_clay"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","rankine:pegmatite|1|rankine:petalite_ore|0.02","rankine:granodiorite|2|rankine:magnetite_ore|0.02","minecraft:stone|1|minecraft:redstone_ore|0.01"),
                    Arrays.asList("rankine:pyroxenite","rankine:comendite","minecraft:andesite","rankine:chalk"),
                    Arrays.asList("rankine:short_grass|70","minecraft:fern|10","rankine:red_clover|10","rankine:white_clover|10","rankine:stinging_nettle|1"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.JUNGLE.getName(),
                    Arrays.asList("rankine:humus_grass_block","rankine:humus","rankine:clay_loam","rankine:clay_loam_grass_block","rankine:clay_loam","rankine:clay_loam"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","rankine:pegmatite|3|rankine:petalite_ore|0.02","minecraft:stone|1|minecraft:coal_ore|0.01"),
                    Arrays.asList("rankine:comendite","rankine:mica_schist","rankine:slate","rankine:mudstone"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10","rankine:crimson_clover|10","rankine:yellow_clover|10","rankine:stinging_nettle|1"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.PLAINS.getName(),
                    Arrays.asList("rankine:sandy_loam_grass_block","rankine:sandy_loam","rankine:sandy_clay_loam","rankine:sandy_clay_loam_grass_block","rankine:sandy_clay_loam","rankine:clay_loam"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","minecraft:stone|1|minecraft:iron_ore|0.01","rankine:gray_granite|2|rankine:cassiterite_ore|0.02","minecraft:diorite|1|rankine:ilmenite_ore|0.01"),
                    Arrays.asList("rankine:rhyolite","rankine:anorthosite","rankine:limestone","rankine:siltstone"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10","rankine:crimson_clover|10","rankine:white_clover|10","rankine:stinging_nettle|1"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));
            biomeSettings.add(Arrays.asList(Biome.Category.SWAMP.getName(),
                    Arrays.asList("rankine:loam_grass_block","rankine:loam","rankine:clay_loam","rankine:humus_grass_block","rankine:humus","rankine:clay_loam"),
                    Arrays.asList("minecraft:air|8|minecraft:air|1.0","rankine:kimberlite|1|rankine:kimberlitic_diamond_ore|0.015","rankine:shonkinite|2|rankine:magnetite_ore|0.02","minecraft:stone|2|minecraft:coal_ore|0.01"),
                    Arrays.asList("rankine:mica_schist","rankine:phyllite","rankine:slate","rankine:mudstone"),
                    Arrays.asList("rankine:short_grass|70","minecraft:grass|10","rankine:red_clover|10","rankine:stinging_nettle|1"),
                    "minecraft:air",
                    "minecraft:air",
                    "minecraft:air"));



            b.comment("Biome Feature Settings").push("biomeGen");
            BIOME_SETTINGS = b.comment("Custom generations per biome or biome category. The defaults are created with biome categories for the overworld. Specific biomes can be used and should be put first in the list.",
                    "Syntax: [[List1], [List2], [List3], ...]",
                    "   [ListX]: [Biome, [Soils], [Intrusions], [Layers], [Vegetation], Gravel, Sand]",
                    "   Biome: biome resource location or category (ex: \"minecraft:nether_wastes\" or \"jungle\")",
                    "   [Soils]: O1, A1, B1, O2, A2, B2",
                    "       O1: resource location of the primary block to replace grass (ex: \"rankine:loam_grass_block\")",
                    "       A1: resource location of the primary block to replace dirt (ex: \"rankine:loam\")",
                    "       B1: resource location of the primary block to generate under dirt (ex: \"rankine:loam\")",
                    "       O2: resource location of the secondary block to replace grass (ex: \"rankine:loam_grass_block\")",
                    "       A2: resource location of the secondary block to replace dirt (ex: \"rankine:loam\")",
                    "       B2: resource location of the secondary block to generate under dirt (ex: \"rankine:loam\")",
                    "   [Intrusions]: [Block|Weight|Ore|Chance]",
                    "       Block: resource location of block to generate as an intrusion (ex: \"rankine:pegmatite\". Use \"minecraft:air\" to not generate an intrusion)",
                    "       Weight: weight of the intrusion to generate (ex: \"5\")",
                    "       Ore: resource location of an block to generate in an intrusion (ex: \"rankine:magnetite_ore\")",
                    "       Chance: chance for an ore block to replace an intrusion block (ex: \"0.03\")",
                    "   [Layers]: Rock1, Rock2, Rock3, ...",
                    "       RockX: resource locations of the blocks to use in stone layers. From bottom to top.",
                    "   [Vegetation]: [Block|Weight]",
                    "       Block: resource location of block to grow above grassy soils (ex: \"rankine:stinging_nettle\")",
                    "       Weight: weight of the block to generate (ex: \"2\")"
            ).defineList("biomeSettings", biomeSettings, (p) -> p instanceof List);
            b.pop();


            oreSettings.add(Arrays.asList("rankine:stibnite_ore", Arrays.asList("all"), "default", 40, 75, 4, 1.0D, 9, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_tin_ore", Arrays.asList("all"), "default", 40, 75, 4, 1.0D, 12, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_lead_ore", Arrays.asList("all"), "default", 40, 75, 4, 1.0D, 8, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_silver_ore", Arrays.asList("all"), "default", 40, 75, 4, 1.0D, 8, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_bismuth_ore", Arrays.asList("all"), "default", 40, 75, 4, 1.0D, 6, 1.0));
            oreSettings.add(Arrays.asList("rankine:lignite_ore", Arrays.asList("all"), "default", 40, 75, 15, 0.8D, 3, 1.0));
            oreSettings.add(Arrays.asList("rankine:stibnite_ore", Arrays.asList("all"), "default", 75, 128, 4, 1.0D, 9, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_tin_ore", Arrays.asList("all"), "default", 75, 128, 4, 1.0D, 12, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_lead_ore", Arrays.asList("all"), "default", 75, 128, 4, 1.0D, 8, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_silver_ore", Arrays.asList("all"), "default", 75, 128, 4, 1.0D, 8, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_bismuth_ore", Arrays.asList("all"), "default", 75, 128, 4, 1.0D, 6, 1.0));
            oreSettings.add(Arrays.asList("rankine:lignite_ore", Arrays.asList("all"), "default", 75, 128, 15, 0.8D, 3, 1.0));

            oreSettings.add(Arrays.asList("rankine:native_gold_ore", Arrays.asList("all"), "default", 20, 60, 5, 1.0D, 4, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_gold_ore", Arrays.asList("all"), "default", 60, 128, 5, 1.0D, 3, 1.0));
            oreSettings.add(Arrays.asList("rankine:pyrite_ore", Arrays.asList("all"), "default", 20, 60, 5, 1.0D, 4, 1.0));
            oreSettings.add(Arrays.asList("rankine:pyrite_ore", Arrays.asList("all"), "default", 60, 128, 5, 1.0D, 3, 1.0));


            oreSettings.add(Arrays.asList("rankine:subbituminous_ore", Arrays.asList("all"), "sphere", 20, 45, 4, 1.0D, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:bituminous_ore", Arrays.asList("all"), "sphere", 5, 25, 4, 1.0D, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:halite_ore", Arrays.asList("river","ocean","desert","mesa"), "default", 30, 60, 5, 1.0D, 2, 1.0));

            oreSettings.add(Arrays.asList("rankine:hematite_ore", Arrays.asList("all"), "sphere", 30, 60, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:hematite_ore", Arrays.asList("all"), "sphere", 70, 120, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:chalcocite_ore", Arrays.asList("all"), "sphere", 40, 60, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:chalcocite_ore", Arrays.asList("all"), "sphere", 70, 120, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:magnesite_ore", Arrays.asList("all"), "sphere", 10, 30, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:chromite_ore", Arrays.asList("all"), "sphere", 5, 20, 3, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:cryolite_ore", Arrays.asList("extreme_hills","taiga","icy"), "sphere", 5, 20, 3, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:bauxite_ore", Arrays.asList("jungle","swamp","plains","forest","taiga"), "sphere", 30, 60, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:celestine_ore", Arrays.asList("jungle","swamp","plains","forest","taiga"), "sphere", 10, 40, 3, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:sphalerite_ore", Arrays.asList("desert","mesa","savanna","plains"), "sphere", 30, 60, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:pentlandite_ore", Arrays.asList("ocean","beach","mushroom","desert","mesa"), "sphere", 5, 30, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:galena_ore", Arrays.asList("all"), "sphere", 10, 40, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:acanthite_ore", Arrays.asList("all"), "sphere", 10, 40, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:pyrolusite_ore", Arrays.asList("swamp","desert"), "sphere", 10, 40, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:plumbago_ore", Arrays.asList("all"), "sphere", 5, 30, 3, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:lazurite_ore", Arrays.asList("all"), "sphere", 10, 40, 3, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:cinnabar_ore", Arrays.asList("all"), "sphere", 5, 30, 4, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:sperrylite_ore", Arrays.asList("all"), "default", 5, 10, 10, 1.0D, 1, 1.0));





            oreSettings.add(Arrays.asList("rankine:bog_iron", Arrays.asList("swamp","jungle"), "disk", 30, 50, 4, 0.7D, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:ironstone", Arrays.asList("desert","savanna","mesa"), "disk", 30, 70, 4, 0.7D, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:kaolin", Arrays.asList("swamp","jungle","mushroom"), "disk", 40, 70, 4, 0.7D, 1, 0.4));
            oreSettings.add(Arrays.asList("rankine:fire_clay", Arrays.asList("all"), "disk", 50, 70, 4, 0.7D, 1, 0.6));
            oreSettings.add(Arrays.asList("rankine:phosphorite", Arrays.asList("all"), "disk", 20, 50, 4, 0.6D, 1, 0.3));
            oreSettings.add(Arrays.asList("rankine:phosphorite", Arrays.asList("extreme_hills"), "disk", 70, 90, 4, 0.6D, 1, 0.3));

            oreSettings.add(Arrays.asList("rankine:basaltic_tuff", Arrays.asList("ocean","beach","mushroom","none"), "disk", 20, 50, 6, 1.0D, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:andesitic_tuff", Arrays.asList("extreme_hills"), "disk", 70, 100, 6, 1.0D, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:rhyolitic_tuff", Arrays.asList("savanna","mesa","desert","plains"), "disk",  10, 30, 6, 1.0D, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:kimberlitic_tuff", Arrays.asList("all"), "disk", 0, 15, 4, 1.0D, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:komatiitic_tuff", Arrays.asList("minecraft:nether_wastes"), "disk",  10, 30, 6, 1.0D, 1, 0.2));

            oreSettings.add(Arrays.asList("minecraft:infested_stone", Arrays.asList("all"), "default", 10, 50, 4, 1.0D, 1, 1.0));

            oreSettings.add(Arrays.asList("rankine:native_sulfur_ore", Arrays.asList("nether"), "default", 20, 75, 4, 1.0D, 12, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_arsenic_ore", Arrays.asList("nether"), "default", 20, 75, 4, 1.0D, 12, 1.0));
            oreSettings.add(Arrays.asList("rankine:anthracite_ore", Arrays.asList("nether"), "sphere", 30, 90, 3, 0.6D, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:magnetite_ore", Arrays.asList("nether"), "sphere", 60, 120, 3, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:cobaltite_ore", Arrays.asList("nether"), "sphere", 60, 120, 3, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:wolframite_ore", Arrays.asList("nether"), "sphere", 60, 120, 3, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:ilmenite_ore", Arrays.asList("nether"), "sphere", 60, 120, 3, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:sperrylite_ore", Arrays.asList("nether"), "sphere", 90, 120, 2, 0.6D, 1, 0.1));
            oreSettings.add(Arrays.asList("rankine:coltan_ore", Arrays.asList("nether"), "sphere", 10, 40, 2, 0.5D, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:monazite_ore", Arrays.asList("nether"), "sphere", 10, 40, 2, 0.5D, 1, 0.2));
            oreSettings.add(Arrays.asList("rankine:interspinifex_ore", Arrays.asList("minecraft:crimson_forest"), "default", 50, 90, 6, 1.0D, 8, 1.0));


            oreSettings.add(Arrays.asList("rankine:native_gallium_ore", Arrays.asList("the_end"), "default", 10, 60, 4, 1.0D, 15, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_indium_ore", Arrays.asList("the_end"), "default", 10, 60, 4, 1.0D, 15, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_selenium_ore", Arrays.asList("the_end"), "default", 10, 60, 4, 1.0D, 15, 1.0));
            oreSettings.add(Arrays.asList("rankine:native_tellurium_ore", Arrays.asList("the_end"), "default", 10, 60, 4, 1.0D, 15, 1.0));
            oreSettings.add(Arrays.asList("rankine:molybdenite_ore", Arrays.asList("the_end"), "sphere", 10, 60, 3, 0.6D, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:uraninite_ore", Arrays.asList("the_end"), "sphere", 10, 60, 3, 0.6D, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:xenotime_ore", Arrays.asList("the_end"), "sphere", 10, 60, 3, 0.6D, 1, 0.5));
            oreSettings.add(Arrays.asList("rankine:greenockite_ore", Arrays.asList("the_end"), "sphere", 10, 60, 3, 0.6D, 1, 0.5));











            b.comment("Ore Feature Settings").push("oreGen");
            ORE_SETTINGS = b.comment("Ore Settings",
                    "[OreGen]: [Ore, [Biomes], Type, Min Height, Max Height, Size, Density, Count, Chance]",
                    "   Ore: resource loacation of the block to generate",
                    "   [Biomes]: String list of biome resource locations to generate in. Use \"all\" to generate in all biomes. Can use biome categories by using the category name: ex \"ocean\".",
                    "   Type: String type of vein to generate. Options include \"default\" (works like vanilla veins), \"sphere\" (generates veins more radially, like an explosion)",
                    "   Min Height: Int to generate",
                    "   Max Height: Int to generate",
                    "   Size: Int to generate",
                    "   Density: Double to determine the density of the ore vein",
                    "   Ore: resource loacation of the block to generate"
            ).defineList("oreSettings", oreSettings, (p) -> p instanceof List);
            b.pop();

        }
    }

    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final General GENERAL;
    public static final Tools TOOLS;
    public static final Machines MACHINES;
    public static final Alloys ALLOYS;
    public static final HardMode HARD_MODE;
    public static final Gases GASES;
    public static final MiscWorldGen MISC_WORLDGEN;
    public static final BiomeGen BIOME_GEN;
    public static final BlockProperties BLOCK_PROPERTIES;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        GENERAL = new General(BUILDER);
        TOOLS = new Tools(BUILDER);
        MACHINES = new Machines(BUILDER);
        ALLOYS = new Alloys(BUILDER);
        GASES = new Gases(BUILDER);
        BLOCK_PROPERTIES = new BlockProperties(BUILDER);
        MISC_WORLDGEN = new MiscWorldGen(BUILDER);
        BIOME_GEN = new BiomeGen(BUILDER);
        HARD_MODE = new HardMode(BUILDER);

        COMMON_CONFIG = BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }


}
