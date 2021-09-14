package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.init.WGConfig;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.cannolicatfish.rankine.world.gen.feature.RankineOreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

@Mod.EventBusSubscriber
public class OreGen {

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> flatBedrock() {
        if (WGConfig.MISC.FLAT_BEDROCK.get()) {
            return Arrays.asList(
                    new AbstractMap.SimpleEntry<>(RankineFeatures.FLAT_BEDROCK,
                            WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(),false)),
                    new AbstractMap.SimpleEntry<>(RankineFeatures.FLAT_BEDROCK_NETHER,
                            WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)));
        } else {
            return Collections.emptyList();
        }
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getUndergroundOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?, ?>, List<ResourceLocation>>> OverworldFeatures = new ArrayList<>();

        if (WGConfig.MISC.VANILLA_ORES.get()) {
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.GRAVEL_DISKS, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.OCEAN), false)));
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.SAND_DISKS, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.OCEAN), false)));
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.CLAY_DISKS, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.OCEAN), false)));
        }
        if (WGConfig.MISC.ALLUVIUM_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ALLUVIUM, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.RIVER), true))); }
        if (WGConfig.MISC.EVAPORITE_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EVAPORITE, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.BEACH), false)));}
        //if (WGConfig.MISC.MUD_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.MUD_FEATURE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false))); }
        if (WGConfig.MISC.FIRE_CLAY_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.FIRE_CLAY, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false))); }

        if (WGConfig.LAYERS.OVERWORLD_STONE_LAYERS.get() != 0) {
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.OVERWORLD_STONE_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTRUSION, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.SILL_PHOSPHORITE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.SILL_IRONSTONE, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.MESA, Biome.Category.DESERT), true)));


        for (List<Object> L : WGConfig.BIOME_GEN.ORE_SETTINGS.get()) {
            Block oreBlock = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate((String) L.get(0)));
            List<String> biomeList = (List<String>) L.get(1);
            List<ResourceLocation> genBiomes = WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), true);
            if (biomeList.get(0).equals("all")) {
                genBiomes = WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false);
            } else {
                for (String b : biomeList) {
                    List<String> biomeName = Arrays.asList(b.split(":"));
                    if (biomeName.size() > 1) {
                        genBiomes.add(ResourceLocation.tryCreate(b));
                    } else {
                        genBiomes.addAll(WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.byName(b)), true));
                    }
                }
            }

            List<String> stoneList = (List<String>) L.get(2);

            int minHeight = (int) L.get(3);
            int maxeight = (int) L.get(4);
            int size = (int) L.get(5);
            int count = (int) L.get(6);
            float chance = (float)  (double) L.get(7);

            ConfiguredFeature<?,?> oreFeature = RankineFeatures.RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.ORE_FILLER, oreBlock.getDefaultState(), size, chance))
                    .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(minHeight, minHeight, maxeight))).square().count(count);

            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(oreFeature, genBiomes));
        }
        return OverworldFeatures;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getNetherOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> NetherFeatures = new ArrayList<>();
        NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.NETHER_ORE_INTRUSION, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
        if (WGConfig.LAYERS.NETHER_STONE_LAYERS.get() != 0) {
            NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.NETHER_STONE_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
        }
        return NetherFeatures;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getEndOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> EndFeatures = new ArrayList<>();

        if (WGConfig.LAYERS.END_STONE_LAYERS.get() != 0) {
            EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.END_STONE_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
        }

        /*
        if (WGConfig.ORES.RANKINE_ORES_E.get()) {
            if (WGConfig.ORES.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_TIN_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_TIN_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TIN_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_GOLD_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_GOLD_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GOLD_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_LEAD_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_LEAD_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_LEAD_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_SILVER_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_SILVER_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SILVER_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_ARSENIC_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_ARSENIC_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_ARSENIC_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_BISMUTH_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_BISMUTH_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_BISMUTH_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_SULFUR_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_SULFUR_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SULFUR_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_GALLIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_GALLIUM_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_GALLIUM_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_INDIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_INDIUM_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_INDIUM_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_TELLURIUM_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.NATIVE_SELENIUM_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.NATIVE_SELENIUM_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_NATIVE_SELENIUM_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MALACHITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MALACHITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MALACHITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MALACHITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.CASSITERITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.CASSITERITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.CASSITERITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CASSITERITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.BAUXITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.BAUXITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.BAUXITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BAUXITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.SPHALERITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.SPHALERITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.SPHALERITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPHALERITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.CINNABAR_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.CINNABAR_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.CINNABAR_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CINNABAR_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MAGNETITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MAGNETITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MAGNETITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNETITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PENTLANDITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PENTLANDITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PENTLANDITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MAGNESITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MAGNESITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MAGNESITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MAGNESITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.GALENA_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.GALENA_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.GALENA_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GALENA_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.BISMUTHINITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.BISMUTHINITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BISMUTHINITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.ACANTHITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.ACANTHITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.ACANTHITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ACANTHITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PYROLUSITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PYROLUSITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYROLUSITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.CHROMITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.CHROMITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.CHROMITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CHROMITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MOLYBDENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MOLYBDENITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MOLYBDENITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.ILMENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.ILMENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.ILMENITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ILMENITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.COLUMBITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.COLUMBITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.COLUMBITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COLUMBITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.WOLFRAMITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.WOLFRAMITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_WOLFRAMITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PLUMBAGO_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PLUMBAGO_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PLUMBAGO_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.SPERRYLITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.SPERRYLITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SPERRYLITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.LIGNITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.LIGNITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.LIGNITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LIGNITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.SUBBITUMINOUS_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.SUBBITUMINOUS_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_SUBBITUMINOUS_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.BITUMINOUS_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.BITUMINOUS_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_BITUMINOUS_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.ANTHRACITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.ANTHRACITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTHRACITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.LAZURITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.LAZURITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.LAZURITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LAZURITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.DIAMOND_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.DIAMOND_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.DIAMOND_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_DIAMOND_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.GREENOCKITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.GREENOCKITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_GREENOCKITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.EMERALD_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.EMERALD_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.EMERALD_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EMERALD_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.QUARTZ_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.QUARTZ_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.QUARTZ_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_QUARTZ_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.URANINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.URANINITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.URANINITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_URANINITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.STIBNITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.STIBNITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.STIBNITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_STIBNITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.XENOTIME_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.XENOTIME_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.XENOTIME_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_XENOTIME_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.HALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.HALITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.HALITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_HALITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.INTERSPINIFEX_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.INTERSPINIFEX_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTERSPINIFEX_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PETALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PETALITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PETALITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PETALITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.COBALTITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.COBALTITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.COBALTITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_COBALTITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.CRYOLITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.CRYOLITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.CRYOLITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CRYOLITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.PYRITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.PYRITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.PYRITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_PYRITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.CELESTINE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.CELESTINE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.CELESTINE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_CELESTINE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.MONAZITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.MONAZITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.MONAZITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_MONAZITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.KAMACITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.KAMACITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.KAMACITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_KAMACITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.ANTITAENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.ANTITAENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.ANTITAENITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ANTITAENITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.TAENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.TAENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.TAENITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TAENITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.TETRATAENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.TETRATAENITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.TETRATAENITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_TETRATAENITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
            if (WGConfig.ORES.LONSDALEITE_ORE_DIMENSION_LIST.get().contains("end")) {
                if (WGConfig.ORES.LONSDALEITE_ORE_GENTYPE.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_CHANCE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                } else {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_COUNT, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
                if (WGConfig.ORES.LONSDALEITE_ORE_MEGA_GEN.get()) {
                    EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_LONSDALEITE_MEGA, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
                }
            }
        }
        */
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
    public static void addOreGenFeatures(final BiomeLoadingEvent event) {
        if (event.getName() != null) {
            // TO-DO: Re-add vanilla features to proper biomes?
            if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND) {
                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> bedrock = flatBedrock();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : bedrock) {
                    if (entry.getValue().contains(event.getName())) {
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

