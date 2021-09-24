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
import java.util.function.Supplier;

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
        if (WGConfig.MISC.FIRE_CLAY_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.FIRE_CLAY, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false))); }

        if (WGConfig.LAYERS.OVERWORLD_STONE_LAYERS.get() != 0) {
            OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.OVERWORLD_STONE_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTRUSION, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));

        return OverworldFeatures;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getNetherOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> NetherFeatures = new ArrayList<>();
        if (WGConfig.LAYERS.NETHER_STONE_LAYERS.get() != 0) {
            NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.NETHER_STONE_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
        }
        NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.NETHER_ORE_INTRUSION, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
        return NetherFeatures;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getEndOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> EndFeatures = new ArrayList<>();

        if (WGConfig.MISC.END_METEORITE_GEN.get()) {
            EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.END_METEORITE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
            EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ANTIMATTER_BLOB, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND), true)));
        }

        return EndFeatures;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addOreGenFeatures(final BiomeLoadingEvent event) {
        if (event.getName() != null) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).removeIf(featureSupplier -> featureSupplier.toString().contains("net.minecraft.util.registry.WorldSettingsImport"));

            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> OVERWORLD_FEATURES = new ArrayList<>();
            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> NETHER_FEATURES = new ArrayList<>();
            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> END_FEATURES = new ArrayList<>();

            for (List<Object> L : WGConfig.BIOME_GEN.ORE_SETTINGS.get()) {
                Boolean inEnd = false;
                Boolean inNether = false;
                Boolean inOverworld = false;
                Block oreBlock = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate((String) L.get(0)));
                List<String> biomeList = (List<String>) L.get(1);
                List<ResourceLocation> genBiomes = WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), true);
                if (biomeList.get(0).isEmpty()) {
                    break;
                } else if (biomeList.get(0).equals("all")) {
                    genBiomes = WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false);
                    inOverworld = true;
                } else {
                    for (String b : biomeList) {
                        List<String> biomeName = Arrays.asList(b.split(":"));
                        if (biomeName.size() > 1) {
                            genBiomes.add(ResourceLocation.tryCreate(b));
                        } else {
                            genBiomes.addAll(WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.byName(b)), true));
                            for (ResourceLocation RS : genBiomes) {
                                Biome.Category biomeCat = ForgeRegistries.BIOMES.getValue(RS).getCategory();
                                if (biomeCat == Biome.Category.THEEND) {
                                    inEnd = true;
                                }
                                if (biomeCat == Biome.Category.NETHER) {
                                    inNether = true;
                                }
                                inOverworld = true;
                            }

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

                if (inEnd) {
                    END_FEATURES.add(new AbstractMap.SimpleEntry<>(oreFeature, genBiomes));
                }
                if (inNether) {
                    NETHER_FEATURES.add(new AbstractMap.SimpleEntry<>(oreFeature, genBiomes));
                }
                if (inOverworld) {
                    OVERWORLD_FEATURES.add(new AbstractMap.SimpleEntry<>(oreFeature, genBiomes));
                }

            }












            // TO-DO: Re-add vanilla features to proper biomes?
            if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND) {
                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> bedrock = flatBedrock();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : bedrock) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }

                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> ores = getUndergroundOreFeatures();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : ores) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : OVERWORLD_FEATURES) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
            } else if (event.getCategory() == Biome.Category.NETHER) {
                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> netherOreFeatures= getNetherOreFeatures();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : netherOreFeatures) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : NETHER_FEATURES) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
            } else if (event.getCategory() == Biome.Category.THEEND) {
                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> endOreFeatures = getEndOreFeatures();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : endOreFeatures) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : END_FEATURES) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
            }




        }
    }
}

