package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.init.WGConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

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
        if (WGConfig.MISC.FLAT_BEDROCK.get())
        {
            return Arrays.asList(
                    new AbstractMap.SimpleEntry<>(RankineFeatures.FLAT_BEDROCK,
                            getBiomeNamesFromCategory(Collections.emptyList(),false)),
                    new AbstractMap.SimpleEntry<>(RankineFeatures.FLAT_BEDROCK_NETHER,
                            getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)));
        } else
        {
            return Collections.emptyList();
        }
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getUndergroundOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?, ?>, List<ResourceLocation>>> OverworldFeatures = new ArrayList<>();

        if (WGConfig.MISC.VANILLA_ORES.get()) {
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.GRAVEL_DISKS, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.OCEAN), false)));
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.SAND_DISKS, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.OCEAN), false)));
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.CLAY_DISKS, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.OCEAN), false)));
        }
        if (WGConfig.MISC.ALLUVIUM_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ALLUVIUM, getBiomeNamesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.RIVER), true))); }
        if (WGConfig.MISC.EVAPORITE_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EVAPORITE, getBiomeNamesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.BEACH), false)));}
        //if (WGConfig.MISC.MUD_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.MUD_FEATURE, getBiomeNamesFromCategory(Collections.emptyList(), false))); }
        if (WGConfig.MISC.FIRE_CLAY_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.FIRE_CLAY, getBiomeNamesFromCategory(Collections.emptyList(), false))); }
        if (WGConfig.LAYERS.OVERWORLD_STONE_LAYERS.get() != 0) {
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.OVERWORLD_STONE_GEN, getBiomeNamesFromCategory(Collections.emptyList(), false)));
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ANDESITE_VAR, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.EXTREME_HILLS), true)));
        }
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTRUSION, getBiomeNamesFromCategory(Collections.emptyList(), false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.SILL_PHOSPHORITE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.SILL_IRONSTONE, getBiomeNamesFromCategory(Arrays.asList(Biome.Category.MESA, Biome.Category.DESERT), true)));
        if (WGConfig.MISC.TUFF_GEN.get()) {
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ANDESITIC_TUFF, getBiomeNamesFromCategory(Collections.emptyList(), false)));
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.RHYOLITIC_TUFF, getBiomeNamesFromCategory(Collections.emptyList(), false)));
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.THOLEIITIC_BASALTIC_TUFF, getBiomeNamesFromCategory(Collections.emptyList(), false)));
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.DACITIC_TUFF, getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NODULE, getBiomeNamesFromCategory(Collections.emptyList(),false)));


        if (WGConfig.ORES.RANKINE_ORES_O.get()) {
            if (WGConfig.ORES.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_COPPER_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_COPPER_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_COPPER_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_COPPER_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_COPPER_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_TIN_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_TIN_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_GOLD_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_GOLD_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ALUMINUM_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ALUMINUM_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ALUMINUM_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_LEAD_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_LEAD_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_SILVER_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_SILVER_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_ARSENIC_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_ARSENIC_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_BISMUTH_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_BISMUTH_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_SULFUR_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_SULFUR_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_GALLIUM_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_GALLIUM_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_INDIUM_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_INDIUM_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.NATIVE_SELENIUM_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.NATIVE_SELENIUM_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.MALACHITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.MALACHITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.MALACHITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.CASSITERITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.CASSITERITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.CASSITERITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.BAUXITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.BAUXITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.BAUXITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.SPHALERITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.SPHALERITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.SPHALERITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.CINNABAR_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.CINNABAR_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.CINNABAR_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.MAGNETITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.MAGNETITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.MAGNETITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.PENTLANDITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.PENTLANDITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.MAGNESITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.MAGNESITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.MAGNESITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.GALENA_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.GALENA_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.GALENA_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.VANADINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.VANADINITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_VANADINITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_VANADINITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.VANADINITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_VANADINITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.BISMUTHINITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.BISMUTHINITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.ACANTHITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.ACANTHITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.ACANTHITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.PYROLUSITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.PYROLUSITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.CHROMITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.CHROMITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.CHROMITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.MOLYBDENITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.MOLYBDENITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.ILMENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.ILMENITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.ILMENITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.COLUMBITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.COLUMBITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.COLUMBITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.WOLFRAMITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.WOLFRAMITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.TANTALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.TANTALITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TANTALITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TANTALITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.TANTALITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TANTALITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.PLUMBAGO_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.PLUMBAGO_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.MOISSANITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.MOISSANITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOISSANITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOISSANITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.MOISSANITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOISSANITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.SPERRYLITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.SPERRYLITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.LIGNITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.LIGNITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.LIGNITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.SUBBITUMINOUS_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.SUBBITUMINOUS_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.BITUMINOUS_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.BITUMINOUS_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.ANTHRACITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.ANTHRACITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.LAZURITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.LAZURITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.LAZURITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.DIAMOND_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.DIAMOND_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.DIAMOND_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.GREENOCKITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.GREENOCKITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.EMERALD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.EMERALD_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.EMERALD_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.AQUAMARINE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_AQUAMARINE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_AQUAMARINE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.AQUAMARINE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_AQUAMARINE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.QUARTZ_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.QUARTZ_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.QUARTZ_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.OPAL_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.OPAL_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_OPAL_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_OPAL_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.OPAL_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_OPAL_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.MAJORITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.MAJORITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAJORITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAJORITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.MAJORITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAJORITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.FLUORITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.FLUORITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_FLUORITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_FLUORITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.FLUORITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_FLUORITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.URANINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.URANINITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.URANINITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.STIBNITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.STIBNITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.STIBNITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.XENOTIME_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.XENOTIME_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.XENOTIME_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.HALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.HALITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.HALITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.PINK_HALITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PINK_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PINK_HALITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.PINK_HALITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PINK_HALITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.INTERSPINIFEX_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.INTERSPINIFEX_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.PETALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.PETALITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.PETALITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.COBALTITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.COBALTITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.COBALTITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.CRYOLITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.CRYOLITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.CRYOLITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.PYRITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.PYRITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.PYRITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.CELESTINE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.CELESTINE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.CELESTINE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.MONAZITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.MONAZITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.MONAZITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.KAMACITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.KAMACITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.KAMACITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.ANTITAENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.ANTITAENITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.ANTITAENITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.TAENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.TAENITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.TAENITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.TETRATAENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.TETRATAENITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.TETRATAENITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
            if (WGConfig.ORES.LONSDALEITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                if (WGConfig.ORES.LONSDALEITE_ORE_GENTYPE.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_CHANCE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                } else {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_COUNT, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
                if (WGConfig.ORES.LONSDALEITE_ORE_MEGA_GEN.get()) {
                    OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_MEGA, getBiomeNamesFromCategory(Collections.emptyList(), false)));
                }
            }
        }
        return OverworldFeatures;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getNetherOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> NetherFeatures = new ArrayList<>();
        NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.NETHER_ORE_INTRUSION, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
        if (WGConfig.LAYERS.NETHER_STONE_LAYERS.get() != 0) {
            NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.NETHER_STONE_GEN, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
        }

        if (WGConfig.ORES.RANKINE_ORES_N.get()) {
            if (WGConfig.ORES.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_COPPER_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_COPPER_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_COPPER_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_COPPER_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_COPPER_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_TIN_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_TIN_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_GOLD_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_GOLD_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ALUMINUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ALUMINUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ALUMINUM_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_LEAD_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_LEAD_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_SILVER_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_SILVER_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_ARSENIC_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_ARSENIC_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_BISMUTH_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_BISMUTH_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_SULFUR_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_SULFUR_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_GALLIUM_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_GALLIUM_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_INDIUM_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_INDIUM_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.NATIVE_SELENIUM_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.NATIVE_SELENIUM_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.MALACHITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.MALACHITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.MALACHITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.CASSITERITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.CASSITERITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.CASSITERITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.BAUXITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.BAUXITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.BAUXITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.SPHALERITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.SPHALERITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.SPHALERITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.CINNABAR_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.CINNABAR_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.CINNABAR_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.MAGNETITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.MAGNETITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.MAGNETITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.PENTLANDITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.PENTLANDITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.MAGNESITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.MAGNESITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.MAGNESITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.GALENA_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.GALENA_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.GALENA_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.VANADINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.VANADINITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_VANADINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_VANADINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.VANADINITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_VANADINITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.BISMUTHINITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.BISMUTHINITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.ACANTHITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.ACANTHITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.ACANTHITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.PYROLUSITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.PYROLUSITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.CHROMITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.CHROMITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.CHROMITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.MOLYBDENITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.MOLYBDENITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.ILMENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.ILMENITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.ILMENITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.COLUMBITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.COLUMBITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.COLUMBITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.WOLFRAMITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.WOLFRAMITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.TANTALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.TANTALITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TANTALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TANTALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.TANTALITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TANTALITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.PLUMBAGO_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.PLUMBAGO_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.MOISSANITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.MOISSANITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOISSANITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOISSANITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.MOISSANITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOISSANITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.SPERRYLITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.SPERRYLITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.LIGNITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.LIGNITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.LIGNITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.SUBBITUMINOUS_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.SUBBITUMINOUS_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.BITUMINOUS_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.BITUMINOUS_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.ANTHRACITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.ANTHRACITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.LAZURITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.LAZURITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.LAZURITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.DIAMOND_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.DIAMOND_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.DIAMOND_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.GREENOCKITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.GREENOCKITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.EMERALD_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.EMERALD_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.EMERALD_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.AQUAMARINE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_AQUAMARINE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_AQUAMARINE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.AQUAMARINE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_AQUAMARINE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.QUARTZ_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.QUARTZ_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.QUARTZ_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.OPAL_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.OPAL_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_OPAL_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_OPAL_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.OPAL_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_OPAL_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.MAJORITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.MAJORITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAJORITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAJORITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.MAJORITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAJORITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.FLUORITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.FLUORITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_FLUORITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_FLUORITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.FLUORITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_FLUORITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.URANINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.URANINITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.URANINITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.STIBNITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.STIBNITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.STIBNITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.XENOTIME_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.XENOTIME_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.XENOTIME_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.HALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.HALITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.HALITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.PINK_HALITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PINK_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PINK_HALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.PINK_HALITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PINK_HALITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.INTERSPINIFEX_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.INTERSPINIFEX_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.PETALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.PETALITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.PETALITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.COBALTITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.COBALTITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.COBALTITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.CRYOLITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.CRYOLITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.CRYOLITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.PYRITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.PYRITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.PYRITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.CELESTINE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.CELESTINE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.CELESTINE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.MONAZITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.MONAZITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.MONAZITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.KAMACITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.KAMACITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.KAMACITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.ANTITAENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.ANTITAENITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.ANTITAENITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.TAENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.TAENITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.TAENITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.TETRATAENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.TETRATAENITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.TETRATAENITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
            if (WGConfig.ORES.LONSDALEITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                if (WGConfig.ORES.LONSDALEITE_ORE_GENTYPE.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                } else {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
                if (WGConfig.ORES.LONSDALEITE_ORE_MEGA_GEN.get()) {
                    NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
                }
            }
        }
        return NetherFeatures;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getEndOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> EndFeatures = new ArrayList<>();

        if (WGConfig.LAYERS.END_STONE_LAYERS.get() != 0) {
            EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.END_STONE_GEN, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
        }

        if (WGConfig.ORES.RANKINE_ORES_E.get()) {
            if (WGConfig.ORES.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_COPPER_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_COPPER_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_COPPER_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_COPPER_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_COPPER_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_TIN_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_TIN_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_GOLD_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_GOLD_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ALUMINUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ALUMINUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ALUMINUM_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_LEAD_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_LEAD_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_SILVER_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_SILVER_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_ARSENIC_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_ARSENIC_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_BISMUTH_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_BISMUTH_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_SULFUR_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_SULFUR_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_GALLIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_GALLIUM_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_INDIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_INDIUM_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_SELENIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_SELENIUM_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MALACHITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MALACHITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MALACHITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.CASSITERITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.CASSITERITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.CASSITERITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.BAUXITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.BAUXITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.BAUXITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.SPHALERITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.SPHALERITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.SPHALERITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.CINNABAR_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.CINNABAR_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.CINNABAR_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MAGNETITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MAGNETITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MAGNETITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PENTLANDITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PENTLANDITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MAGNESITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MAGNESITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MAGNESITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.GALENA_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.GALENA_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.GALENA_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.VANADINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.VANADINITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_VANADINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_VANADINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.VANADINITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_VANADINITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.BISMUTHINITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.BISMUTHINITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.ACANTHITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.ACANTHITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.ACANTHITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PYROLUSITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PYROLUSITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.CHROMITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.CHROMITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.CHROMITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MOLYBDENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MOLYBDENITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.ILMENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.ILMENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.ILMENITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.COLUMBITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.COLUMBITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.COLUMBITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.WOLFRAMITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.WOLFRAMITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.TANTALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.TANTALITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TANTALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TANTALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.TANTALITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TANTALITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PLUMBAGO_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PLUMBAGO_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MOISSANITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MOISSANITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOISSANITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOISSANITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MOISSANITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOISSANITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.SPERRYLITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.SPERRYLITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.LIGNITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.LIGNITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.LIGNITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.SUBBITUMINOUS_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.SUBBITUMINOUS_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.BITUMINOUS_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.BITUMINOUS_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.ANTHRACITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.ANTHRACITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.LAZURITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.LAZURITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.LAZURITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.DIAMOND_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.DIAMOND_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.DIAMOND_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.GREENOCKITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.GREENOCKITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.EMERALD_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.EMERALD_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.EMERALD_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.AQUAMARINE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_AQUAMARINE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_AQUAMARINE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.AQUAMARINE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_AQUAMARINE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.QUARTZ_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.QUARTZ_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.QUARTZ_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.OPAL_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.OPAL_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_OPAL_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_OPAL_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.OPAL_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_OPAL_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MAJORITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MAJORITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAJORITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAJORITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MAJORITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAJORITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.FLUORITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.FLUORITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_FLUORITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_FLUORITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.FLUORITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_FLUORITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.URANINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.URANINITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.URANINITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.STIBNITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.STIBNITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.STIBNITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.XENOTIME_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.XENOTIME_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.XENOTIME_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.HALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.HALITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.HALITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PINK_HALITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PINK_HALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PINK_HALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PINK_HALITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PINK_HALITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.INTERSPINIFEX_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.INTERSPINIFEX_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PETALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PETALITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PETALITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.COBALTITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.COBALTITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.COBALTITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.CRYOLITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.CRYOLITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.CRYOLITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PYRITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PYRITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PYRITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.CELESTINE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.CELESTINE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.CELESTINE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MONAZITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MONAZITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MONAZITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.KAMACITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.KAMACITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.KAMACITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.ANTITAENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.ANTITAENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.ANTITAENITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.TAENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.TAENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.TAENITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.TETRATAENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.TETRATAENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.TETRATAENITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.LONSDALEITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.LONSDALEITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_CHANCE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_COUNT, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.LONSDALEITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_MEGA, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
        }
        return EndFeatures;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void removeVanillaFeatures(final BiomeLoadingEvent event) {
        if (event.getName() != null) {
            if (WGConfig.MISC.VANILLA_ORES.get()) {
                event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).removeIf(featureSupplier -> featureSupplier.toString().contains("net.minecraft.util.registry.WorldSettingsImport"));
               // System.out.println(event.getName() + ": " + (event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)));
            }
            //if (Config.NETHER_FEATURES.get()) {
           //     List<ConfiguredFeature<?, ?>> netherFeatures = Arrays.asList(Features.ORE_BLACKSTONE, Features.ORE_QUARTZ_NETHER, Features.ORE_QUARTZ_DELTAS, Features.ORE_GOLD_NETHER, Features.ORE_GOLD_DELTAS);
          //      event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).removeAll(netherFeatures);
         //   }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addOreGenFeatures(final BiomeLoadingEvent event)
    {
        if (event.getName() != null) {
            // TO-DO: Re-add vanilla features to proper biomes?
            if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND)
            {

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

