package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.compatibility.TerraForged;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModFeatures;
import com.cannolicatfish.rankine.world.gen.feature.*;
import com.cannolicatfish.rankine.world.gen.placement.IntrusionPlacement;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class OreGen {

    private static List<ResourceLocation> getBiomeNamesFromCategory(List<Biome.Category> biomeCats, boolean include) {
        List<ResourceLocation> b = new ArrayList<>();
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (!biomeCats.isEmpty()) {
                for (Biome.Category cat : biomeCats) {
                    if (biome.getCategory() == cat && include){
                        b.add(biome.getRegistryName());
                    }
                    if (!include && biome.getCategory() != cat && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                        b.add(biome.getRegistryName());
                    }
                }
            }
            else if (!include && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                b.add(biome.getRegistryName());
            }
        }
        return b;
    }
/*
    private static void addCrystal() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.DESERT || biome.getCategory() == Biome.Category.MESA || biome.getCategory() == Biome.Category.SAVANNA) {
                biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, new CrystalFeature(NoFeatureConfig.field_236558_a_).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(6))));
            }
        }
    }
    }*/

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> flatBedrock() {
        if (Config.FLAT_BEDROCK.get())
        {
            return Arrays.asList(
                    new AbstractMap.SimpleEntry<>(ModFeatures.FLAT_BEDROCK,
                            getBiomeNamesFromCategory(Collections.emptyList(),false)),
                    new AbstractMap.SimpleEntry<>(ModFeatures.FLAT_BEDROCK_NETHER,
                            getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)));
        } else
        {
            return Collections.emptyList();
        }
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getUndergroundOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> OverworldFeatures = new ArrayList<>();

        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.GRAVEL_DISKS, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.OCEAN),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.SAND_DISKS, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.OCEAN),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.CLAY_DISKS, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.OCEAN),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ALLUVIUM, getBiomeNamesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.RIVER),true)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_INTRUSION, getBiomeNamesFromCategory(Collections.emptyList(),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.DEFAULT_STONE_GEN, getBiomeNamesFromCategory(Collections.emptyList(),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ANDESITIC_TUFF, getBiomeNamesFromCategory(Collections.emptyList(),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.RHYOLITIC_TUFF, getBiomeNamesFromCategory(Collections.emptyList(),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.THOLEIITIC_BASALTIC_TUFF, getBiomeNamesFromCategory(Collections.emptyList(),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.DACITIC_TUFF, getBiomeNamesFromCategory(Collections.emptyList(),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ANDESITE_VAR, getBiomeNamesFromCategory(Collections.emptyList(),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NODULE, getBiomeNamesFromCategory(Collections.emptyList(),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_IRONSTONE, getBiomeNamesFromCategory(Arrays.asList(Biome.Category.DESERT, Biome.Category.MESA), true)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_OPAL, getBiomeNamesFromCategory(Arrays.asList(Biome.Category.DESERT, Biome.Category.MESA), true)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PHOSPHORITE, getBiomeNamesFromCategory(Collections.emptyList(),false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CHALK, getBiomeNamesFromCategory(Collections.emptyList(),false)));


        if (Config.RANKINE_ORES_O.get()) {
            if (Config.MAGNETITE_ORE_OVERWORLD.get()) {
                if (Config.MAGNETITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MAGNETITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MAGNETITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.CASSITERITE_ORE_OVERWORLD.get()) {
                if (Config.CASSITERITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CASSITERITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CASSITERITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.MALACHITE_ORE_OVERWORLD.get()) {
                if (Config.MALACHITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MALACHITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MALACHITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.BAUXITE_ORE_OVERWORLD.get()) {
                if (Config.BAUXITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_BAUXITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_BAUXITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.SPHALERITE_ORE_OVERWORLD.get()) {
                if (Config.SPHALERITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_SPHALERITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_SPHALERITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.CINNABAR_ORE_OVERWORLD.get()) {
                if (Config.CINNABAR_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CINNABAR_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CINNABAR_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.PENTLANDITE_ORE_OVERWORLD.get()) {
                if (Config.PENTLANDITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PENTLANDITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PENTLANDITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.MAGNESITE_ORE_OVERWORLD.get()) {
                if (Config.MAGNESITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MAGNESITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MAGNESITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.GALENA_ORE_OVERWORLD.get()) {
                if (Config.GALENA_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_GALENA_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_GALENA_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.VANADINITE_ORE_OVERWORLD.get()) {
                if (Config.VANADINITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_VANADINITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_VANADINITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.BISMUTHINITE_ORE_OVERWORLD.get()) {
                if (Config.BISMUTHINITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_BISMUTHINITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_BISMUTHINITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.ACANTHITE_ORE_OVERWORLD.get()) {
                if (Config.ACANTHITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ACANTHITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ACANTHITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.PYROLUSITE_ORE_OVERWORLD.get()) {
                if (Config.PYROLUSITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PYROLUSITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PYROLUSITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.CHROMITE_ORE_OVERWORLD.get()) {
                if (Config.CHROMITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CHROMITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CHROMITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.MOLYBDENITE_ORE_OVERWORLD.get()) {
                if (Config.MOLYBDENITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MOLYBDENITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MOLYBDENITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.ILMENITE_ORE_OVERWORLD.get()) {
                if (Config.ILMENITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ILMENITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ILMENITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.COLUMBITE_ORE_OVERWORLD.get()) {
                if (Config.COLUMBITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_COLUMBITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_COLUMBITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.WOLFRAMITE_ORE_OVERWORLD.get()) {
                if (Config.WOLFRAMITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_WOLFRAMITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_WOLFRAMITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.TANTALITE_ORE_OVERWORLD.get()) {
                if (Config.TANTALITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_TANTALITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_TANTALITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.PLUMBAGO_ORE_OVERWORLD.get()) {
                if (Config.PLUMBAGO_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PLUMBAGO_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PLUMBAGO_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.MOISSANITE_ORE_OVERWORLD.get()) {
                if (Config.MOISSANITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MOISSANITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MOISSANITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.SPERRYLITE_ORE_OVERWORLD.get()) {
                if (Config.SPERRYLITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_SPERRYLITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_SPERRYLITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.LIGNITE_ORE_OVERWORLD.get()) {
                if (Config.LIGNITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_LIGNITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_LIGNITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.SUBBITUMINOUS_ORE_OVERWORLD.get()) {
                if (Config.SUBBITUMINOUS_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_SUBBITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_SUBBITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.BITUMINOUS_ORE_OVERWORLD.get()) {
                if (Config.BITUMINOUS_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_BITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_BITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.ANTHRACITE_ORE_OVERWORLD.get()) {
                if (Config.ANTHRACITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ANTHRACITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ANTHRACITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.LAZURITE_ORE_OVERWORLD.get()) {
                if (Config.LAZURITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_LAZURITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_LAZURITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.DIAMOND_ORE_OVERWORLD.get()) {
                if (Config.DIAMOND_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_DIAMOND_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_DIAMOND_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.GREENOCKITE_ORE_OVERWORLD.get()) {
                if (Config.GREENOCKITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_GREENOCKITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_GREENOCKITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.EMERALD_ORE_OVERWORLD.get()) {
                if (Config.EMERALD_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_EMERALD_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_EMERALD_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.AQUAMARINE_ORE_OVERWORLD.get()) {
                if (Config.AQUAMARINE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_AQUAMARINE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_AQUAMARINE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_COPPER_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_COPPER_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_COPPER_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_COPPER_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_TIN_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_TIN_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_TIN_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_TIN_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_GOLD_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_GOLD_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_GOLD_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_GOLD_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_ALUMINUM_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_ALUMINUM_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_ALUMINUM_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_ALUMINUM_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_LEAD_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_LEAD_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_LEAD_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_LEAD_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_SILVER_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_SILVER_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_SILVER_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_SILVER_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_ARSENIC_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_ARSENIC_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_ARSENIC_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_ARSENIC_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_BISMUTH_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_BISMUTH_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_BISMUTH_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_BISMUTH_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_SULFUR_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_SULFUR_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_SULFUR_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_SULFUR_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_GALLIUM_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_GALLIUM_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_GALLIUM_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_GALLIUM_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_INDIUM_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_INDIUM_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_INDIUM_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_INDIUM_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_TELLURIUM_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_TELLURIUM_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_TELLURIUM_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_TELLURIUM_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.NATIVE_SELENIUM_ORE_OVERWORLD.get()) {
                if (Config.NATIVE_SELENIUM_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_SELENIUM_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_SELENIUM_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.QUARTZ_ORE_OVERWORLD.get()) {
                if (Config.QUARTZ_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_QUARTZ_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_QUARTZ_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.OPAL_ORE_OVERWORLD.get()) {
                if (Config.OPAL_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_OPAL_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_OPAL_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.FLUORITE_ORE_OVERWORLD.get()) {
                if (Config.FLUORITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_FLUORITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_FLUORITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.URANINITE_ORE_OVERWORLD.get()) {
                if (Config.URANINITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_URANINITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_URANINITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.STIBNITE_ORE_OVERWORLD.get()) {
                if (Config.STIBNITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_STIBNITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_STIBNITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.XENOTIME_ORE_OVERWORLD.get()) {
                if (Config.XENOTIME_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_XENOTIME_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_XENOTIME_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.HALITE_ORE_OVERWORLD.get()) {
                if (Config.HALITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_HALITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.PINK_HALITE_ORE_OVERWORLD.get()) {
                if (Config.PINK_HALITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PINK_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PINK_HALITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.INTERSPINIFEX_ORE_OVERWORLD.get()) {
                if (Config.INTERSPINIFEX_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_INTERSPINIFEX_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_INTERSPINIFEX_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.PETALITE_ORE_OVERWORLD.get()) {
                if (Config.PETALITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PETALITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PETALITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.COBALTITE_ORE_OVERWORLD.get()) {
                if (Config.COBALTITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_COBALTITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_COBALTITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.MAJORITE_ORE_OVERWORLD.get()) {
                if (Config.MAJORITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MAJORITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MAJORITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.CELESTINE_ORE_OVERWORLD.get()) {
                if (Config.CELESTINE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CELESTINE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CELESTINE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.CRYOLITE_ORE_OVERWORLD.get()) {
                if (Config.CRYOLITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CRYOLITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CRYOLITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (Config.PYRITE_ORE_OVERWORLD.get()) {
                if (Config.PYRITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PYRITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PYRITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
        }
        return OverworldFeatures;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getNetherOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> NetherFeatures = new ArrayList<>();

        NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_INTRUSION, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)));
        NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.BLACK_SAND, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)));

        if (Config.RANKINE_ORES_N.get()) {
            if (Config.MAGNETITE_ORE_NETHER.get()) {
                if (Config.MAGNETITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MAGNETITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MAGNETITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.CASSITERITE_ORE_NETHER.get()) {
                if (Config.CASSITERITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_CASSITERITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_CASSITERITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.MALACHITE_ORE_NETHER.get()) {
                if (Config.MALACHITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MALACHITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MALACHITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.BAUXITE_ORE_NETHER.get()) {
                if (Config.BAUXITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_BAUXITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_BAUXITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.SPHALERITE_ORE_NETHER.get()) {
                if (Config.SPHALERITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_SPHALERITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_SPHALERITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.CINNABAR_ORE_NETHER.get()) {
                if (Config.CINNABAR_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_CINNABAR_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_CINNABAR_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.PENTLANDITE_ORE_NETHER.get()) {
                if (Config.PENTLANDITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PENTLANDITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PENTLANDITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.MAGNESITE_ORE_NETHER.get()) {
                if (Config.MAGNESITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MAGNESITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MAGNESITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.GALENA_ORE_NETHER.get()) {
                if (Config.GALENA_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_GALENA_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_GALENA_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.VANADINITE_ORE_NETHER.get()) {
                if (Config.VANADINITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_VANADINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_VANADINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.BISMUTHINITE_ORE_NETHER.get()) {
                if (Config.BISMUTHINITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_BISMUTHINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_BISMUTHINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.ACANTHITE_ORE_NETHER.get()) {
                if (Config.ACANTHITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_ACANTHITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_ACANTHITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.PYROLUSITE_ORE_NETHER.get()) {
                if (Config.PYROLUSITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PYROLUSITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PYROLUSITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.CHROMITE_ORE_NETHER.get()) {
                if (Config.CHROMITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_CHROMITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_CHROMITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.MOLYBDENITE_ORE_NETHER.get()) {
                if (Config.MOLYBDENITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MOLYBDENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MOLYBDENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.ILMENITE_ORE_NETHER.get()) {
                if (Config.ILMENITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_ILMENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_ILMENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.COLUMBITE_ORE_NETHER.get()) {
                if (Config.COLUMBITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_COLUMBITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_COLUMBITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.WOLFRAMITE_ORE_NETHER.get()) {
                if (Config.WOLFRAMITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_WOLFRAMITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_WOLFRAMITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.TANTALITE_ORE_NETHER.get()) {
                if (Config.TANTALITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_TANTALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_TANTALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.PLUMBAGO_ORE_NETHER.get()) {
                if (Config.PLUMBAGO_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PLUMBAGO_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PLUMBAGO_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.MOISSANITE_ORE_NETHER.get()) {
                if (Config.MOISSANITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MOISSANITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MOISSANITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.SPERRYLITE_ORE_NETHER.get()) {
                if (Config.SPERRYLITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_SPERRYLITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_SPERRYLITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.LIGNITE_ORE_NETHER.get()) {
                if (Config.LIGNITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_LIGNITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_LIGNITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.SUBBITUMINOUS_ORE_NETHER.get()) {
                if (Config.SUBBITUMINOUS_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_SUBBITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_SUBBITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.BITUMINOUS_ORE_NETHER.get()) {
                if (Config.BITUMINOUS_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_BITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_BITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.ANTHRACITE_ORE_NETHER.get()) {
                if (Config.ANTHRACITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_ANTHRACITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_ANTHRACITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.LAZURITE_ORE_NETHER.get()) {
                if (Config.LAZURITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_LAZURITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_LAZURITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.DIAMOND_ORE_NETHER.get()) {
                if (Config.DIAMOND_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_DIAMOND_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_DIAMOND_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.GREENOCKITE_ORE_NETHER.get()) {
                if (Config.GREENOCKITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_GREENOCKITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_GREENOCKITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.EMERALD_ORE_NETHER.get()) {
                if (Config.EMERALD_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_EMERALD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_EMERALD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.AQUAMARINE_ORE_NETHER.get()) {
                if (Config.AQUAMARINE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_AQUAMARINE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_AQUAMARINE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_COPPER_ORE_NETHER.get()) {
                if (Config.NATIVE_COPPER_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_COPPER_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_COPPER_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_TIN_ORE_NETHER.get()) {
                if (Config.NATIVE_TIN_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_TIN_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_TIN_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_GOLD_ORE_NETHER.get()) {
                if (Config.NATIVE_GOLD_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_GOLD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_GOLD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_ALUMINUM_ORE_NETHER.get()) {
                if (Config.NATIVE_ALUMINUM_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_ALUMINUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_ALUMINUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_LEAD_ORE_NETHER.get()) {
                if (Config.NATIVE_LEAD_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_LEAD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_LEAD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_SILVER_ORE_NETHER.get()) {
                if (Config.NATIVE_SILVER_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_SILVER_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_SILVER_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_ARSENIC_ORE_NETHER.get()) {
                if (Config.NATIVE_ARSENIC_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_ARSENIC_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_ARSENIC_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_BISMUTH_ORE_NETHER.get()) {
                if (Config.NATIVE_BISMUTH_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_BISMUTH_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_BISMUTH_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_SULFUR_ORE_NETHER.get()) {
                if (Config.NATIVE_SULFUR_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_SULFUR_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_SULFUR_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_GALLIUM_ORE_NETHER.get()) {
                if (Config.NATIVE_GALLIUM_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_GALLIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_GALLIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_INDIUM_ORE_NETHER.get()) {
                if (Config.NATIVE_INDIUM_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_INDIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_INDIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_TELLURIUM_ORE_NETHER.get()) {
                if (Config.NATIVE_TELLURIUM_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_TELLURIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_TELLURIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.NATIVE_SELENIUM_ORE_NETHER.get()) {
                if (Config.NATIVE_SELENIUM_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_SELENIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_NATIVE_SELENIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.QUARTZ_ORE_NETHER.get()) {
                if (Config.QUARTZ_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_QUARTZ_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_QUARTZ_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.OPAL_ORE_NETHER.get()) {
                if (Config.OPAL_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_OPAL_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_OPAL_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.FLUORITE_ORE_NETHER.get()) {
                if (Config.FLUORITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_FLUORITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_FLUORITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.URANINITE_ORE_NETHER.get()) {
                if (Config.URANINITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_URANINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_URANINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.STIBNITE_ORE_NETHER.get()) {
                if (Config.STIBNITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_STIBNITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_STIBNITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.XENOTIME_ORE_NETHER.get()) {
                if (Config.XENOTIME_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_XENOTIME_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_XENOTIME_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.HALITE_ORE_NETHER.get()) {
                if (Config.HALITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_HALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.PINK_HALITE_ORE_NETHER.get()) {
                if (Config.PINK_HALITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PINK_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PINK_HALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.INTERSPINIFEX_ORE_NETHER.get()) {
                if (Config.INTERSPINIFEX_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_INTERSPINIFEX_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_INTERSPINIFEX_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.PETALITE_ORE_NETHER.get()) {
                if (Config.PETALITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PETALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PETALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.COBALTITE_ORE_NETHER.get()) {
                if (Config.COBALTITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_COBALTITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_COBALTITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.MAJORITE_ORE_NETHER.get()) {
                if (Config.MAJORITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MAJORITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_MAJORITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.CELESTINE_ORE_NETHER.get()) {
                if (Config.CELESTINE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_CELESTINE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_CELESTINE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.CRYOLITE_ORE_NETHER.get()) {
                if (Config.CRYOLITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_CRYOLITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_CRYOLITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (Config.PYRITE_ORE_NETHER.get()) {
                if (Config.PYRITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PYRITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_ORE_PYRITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
        }
        return NetherFeatures;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getEndOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> EndFeatures = new ArrayList<>();

        if (Config.RANKINE_ORES_E.get()) {
            if (Config.MAGNETITE_ORE_END.get()) {
                if (Config.MAGNETITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MAGNETITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MAGNETITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.CASSITERITE_ORE_END.get()) {
                if (Config.CASSITERITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_CASSITERITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_CASSITERITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.MALACHITE_ORE_END.get()) {
                if (Config.MALACHITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MALACHITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MALACHITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.BAUXITE_ORE_END.get()) {
                if (Config.BAUXITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_BAUXITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_BAUXITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.SPHALERITE_ORE_END.get()) {
                if (Config.SPHALERITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_SPHALERITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_SPHALERITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.CINNABAR_ORE_END.get()) {
                if (Config.CINNABAR_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_CINNABAR_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_CINNABAR_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.PENTLANDITE_ORE_END.get()) {
                if (Config.PENTLANDITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PENTLANDITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PENTLANDITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.MAGNESITE_ORE_END.get()) {
                if (Config.MAGNESITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MAGNESITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MAGNESITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.GALENA_ORE_END.get()) {
                if (Config.GALENA_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_GALENA_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_GALENA_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.VANADINITE_ORE_END.get()) {
                if (Config.VANADINITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_VANADINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_VANADINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.BISMUTHINITE_ORE_END.get()) {
                if (Config.BISMUTHINITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_BISMUTHINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_BISMUTHINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.ACANTHITE_ORE_END.get()) {
                if (Config.ACANTHITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_ACANTHITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_ACANTHITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.PYROLUSITE_ORE_END.get()) {
                if (Config.PYROLUSITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PYROLUSITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PYROLUSITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.CHROMITE_ORE_END.get()) {
                if (Config.CHROMITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_CHROMITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_CHROMITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.MOLYBDENITE_ORE_END.get()) {
                if (Config.MOLYBDENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MOLYBDENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MOLYBDENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.ILMENITE_ORE_END.get()) {
                if (Config.ILMENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_ILMENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_ILMENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.COLUMBITE_ORE_END.get()) {
                if (Config.COLUMBITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_COLUMBITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_COLUMBITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.WOLFRAMITE_ORE_END.get()) {
                if (Config.WOLFRAMITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_WOLFRAMITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_WOLFRAMITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.TANTALITE_ORE_END.get()) {
                if (Config.TANTALITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_TANTALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_TANTALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.PLUMBAGO_ORE_END.get()) {
                if (Config.PLUMBAGO_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PLUMBAGO_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PLUMBAGO_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.MOISSANITE_ORE_END.get()) {
                if (Config.MOISSANITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MOISSANITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MOISSANITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.SPERRYLITE_ORE_END.get()) {
                if (Config.SPERRYLITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_SPERRYLITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_SPERRYLITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.LIGNITE_ORE_END.get()) {
                if (Config.LIGNITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_LIGNITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_LIGNITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.SUBBITUMINOUS_ORE_END.get()) {
                if (Config.SUBBITUMINOUS_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_SUBBITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_SUBBITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.BITUMINOUS_ORE_END.get()) {
                if (Config.BITUMINOUS_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_BITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_BITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.ANTHRACITE_ORE_END.get()) {
                if (Config.ANTHRACITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_ANTHRACITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_ANTHRACITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.LAZURITE_ORE_END.get()) {
                if (Config.LAZURITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_LAZURITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_LAZURITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.DIAMOND_ORE_END.get()) {
                if (Config.DIAMOND_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_DIAMOND_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_DIAMOND_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.GREENOCKITE_ORE_END.get()) {
                if (Config.GREENOCKITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_GREENOCKITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_GREENOCKITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.EMERALD_ORE_END.get()) {
                if (Config.EMERALD_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_EMERALD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_EMERALD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.AQUAMARINE_ORE_END.get()) {
                if (Config.AQUAMARINE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_AQUAMARINE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_AQUAMARINE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_COPPER_ORE_END.get()) {
                if (Config.NATIVE_COPPER_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_COPPER_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_COPPER_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_TIN_ORE_END.get()) {
                if (Config.NATIVE_TIN_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_TIN_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_TIN_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_GOLD_ORE_END.get()) {
                if (Config.NATIVE_GOLD_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_GOLD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_GOLD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_ALUMINUM_ORE_END.get()) {
                if (Config.NATIVE_ALUMINUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_ALUMINUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_ALUMINUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_LEAD_ORE_END.get()) {
                if (Config.NATIVE_LEAD_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_LEAD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_LEAD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_SILVER_ORE_END.get()) {
                if (Config.NATIVE_SILVER_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_SILVER_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_SILVER_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_ARSENIC_ORE_END.get()) {
                if (Config.NATIVE_ARSENIC_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_ARSENIC_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_ARSENIC_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_BISMUTH_ORE_END.get()) {
                if (Config.NATIVE_BISMUTH_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_BISMUTH_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_BISMUTH_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_SULFUR_ORE_END.get()) {
                if (Config.NATIVE_SULFUR_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_SULFUR_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_SULFUR_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_GALLIUM_ORE_END.get()) {
                if (Config.NATIVE_GALLIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_GALLIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_GALLIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_INDIUM_ORE_END.get()) {
                if (Config.NATIVE_INDIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_INDIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_INDIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_TELLURIUM_ORE_END.get()) {
                if (Config.NATIVE_TELLURIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_TELLURIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_TELLURIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.NATIVE_SELENIUM_ORE_END.get()) {
                if (Config.NATIVE_SELENIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_SELENIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_NATIVE_SELENIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.QUARTZ_ORE_END.get()) {
                if (Config.QUARTZ_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_QUARTZ_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_QUARTZ_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.OPAL_ORE_END.get()) {
                if (Config.OPAL_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_OPAL_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_OPAL_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.FLUORITE_ORE_END.get()) {
                if (Config.FLUORITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_FLUORITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_FLUORITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.URANINITE_ORE_END.get()) {
                if (Config.URANINITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_URANINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_URANINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.STIBNITE_ORE_END.get()) {
                if (Config.STIBNITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_STIBNITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_STIBNITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.XENOTIME_ORE_END.get()) {
                if (Config.XENOTIME_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_XENOTIME_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_XENOTIME_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.HALITE_ORE_END.get()) {
                if (Config.HALITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_HALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.PINK_HALITE_ORE_END.get()) {
                if (Config.PINK_HALITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PINK_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PINK_HALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.INTERSPINIFEX_ORE_END.get()) {
                if (Config.INTERSPINIFEX_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_INTERSPINIFEX_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_INTERSPINIFEX_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.PETALITE_ORE_END.get()) {
                if (Config.PETALITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PETALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PETALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.COBALTITE_ORE_END.get()) {
                if (Config.COBALTITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_COBALTITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_COBALTITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.MAJORITE_ORE_END.get()) {
                if (Config.MAJORITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MAJORITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_MAJORITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.CELESTINE_ORE_END.get()) {
                if (Config.CELESTINE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_CELESTINE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_CELESTINE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.CRYOLITE_ORE_END.get()) {
                if (Config.CRYOLITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_CRYOLITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_CRYOLITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (Config.PYRITE_ORE_END.get()) {
                if (Config.PYRITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PYRITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(ModFeatures.END_ORE_PYRITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
        }
        return EndFeatures;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void removeFeatures(final BiomeLoadingEvent event) {
        if (event.getName() != null) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).removeIf(featureSupplier -> featureSupplier.toString().contains("net.minecraft.util.registry.WorldSettingsImport"));
            //System.out.println(event.getName() + ": " + (event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addOreGenFeatures(final BiomeLoadingEvent event)
    {
        if (event.getName() != null) {
            // TO-DO: Re-add vanilla features to proper biomes?
            if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND)
            {
                List<ConfiguredFeature<?,?>> vanillaFeatures = Arrays.asList(Features.ORE_DIRT,Features.ORE_GRAVEL,Features.DISK_CLAY,Features.DISK_GRAVEL,Features.DISK_SAND);

                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> bedrock = flatBedrock();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : bedrock)
                {
                    if (entry.getValue().contains(event.getName()))
                    {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }

                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> ores = getUndergroundOreFeatures();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : ores)
                {
                    if (entry.getValue().contains(event.getName()))
                    {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
            }
            else if (event.getCategory() == Biome.Category.NETHER)
            {
                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> netherOreFeatures= getNetherOreFeatures();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : netherOreFeatures)
                {
                    if (entry.getValue().contains(event.getName()))
                    {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
            } else {
                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> endOreFeatures = getEndOreFeatures();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : endOreFeatures)
                {
                    if (entry.getValue().contains(event.getName()))
                    {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
            }




        }
    }
}

