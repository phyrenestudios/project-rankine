package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.cannolicatfish.rankine.world.gen.feature.RankineOreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
        if (Config.MISC.FLAT_BEDROCK.get()) {
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

        if (Config.MISC.ALLUVIUM_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_ALLUVIUM, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.RIVER), true))); }
        if (Config.MISC.EVAPORITE_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_EVAPORITE, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.BEACH), false))); }
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.OVERWORLD_STONE_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORE_INTRUSION, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        if (Config.MISC.FUMAROLE_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.FUMAROLE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false))); }

        return OverworldFeatures;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getNetherOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> NetherFeatures = new ArrayList<>();

        NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.NETHER_STONE_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
        NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.NETHER_ORE_INTRUSION, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true)));
        if (Config.MISC.EVAPORITE_GEN.get()) { NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.FUMAROLE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER), true))); }
        return NetherFeatures;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getEndOreFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> EndFeatures = new ArrayList<>();

        if (Config.MISC.END_METEORITE_GEN.get()) {
            EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.END_METEORITE, Arrays.asList(ResourceLocation.tryCreate("minecraft:end_barrens"),ResourceLocation.tryCreate("minecraft:small_end_islands"))));
        }
        if (Config.MISC.SECRET_GEN.get()) {
            EndFeatures.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ANTIMATTER_BLOB, Arrays.asList(ResourceLocation.tryCreate("minecraft:end_barrens"))));
        }

        return EndFeatures;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void addOreGenFeatures(final BiomeLoadingEvent event) {
        if (event.getName() != null) {
            final List<Supplier<ConfiguredFeature<?, ?>>> ORES = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);
            disableGenerators(ORES, event.getName(), Arrays.asList(Blocks.DIRT.getDefaultState(),Blocks.ANDESITE.getDefaultState(),Blocks.DIORITE.getDefaultState(),Blocks.GRANITE.getDefaultState(),Blocks.IRON_ORE.getDefaultState(),Blocks.COAL_ORE.getDefaultState(),Blocks.GOLD_ORE.getDefaultState(),Blocks.DIAMOND_ORE.getDefaultState(),Blocks.EMERALD_ORE.getDefaultState(),Blocks.LAPIS_ORE.getDefaultState(),Blocks.REDSTONE_ORE.getDefaultState()));
            //event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).removeIf(featureSupplier -> featureSupplier.toString().contains("net.minecraft.util.registry.WorldSettingsImport"));

            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> OVERWORLD_FEATURES = new ArrayList<>();
            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> NETHER_FEATURES = new ArrayList<>();
            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> END_FEATURES = new ArrayList<>();

            for (List<Object> L : Config.BIOME_GEN.ORE_SETTINGS.get()) {
                boolean inEnd = false;
                boolean inNether = false;
                boolean inOverworld = false;
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

        //    if (new AbstractMap.SimpleEntry<>(RankineFeatures.FUMAROLE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false))) {
       //         event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION.ordinal(),new AbstractMap.SimpleEntry<>(RankineFeatures.FUMAROLE, Arrays.asList(ResourceLocation.tryCreate("minecraft:end_barrens")))::getKey);
        //    }




        }
    }

    private static void disableGenerators(List<Supplier<ConfiguredFeature<?, ?>>> features, ResourceLocation name, List<BlockState> DISABLE) {
        final List<Supplier<ConfiguredFeature<?, ?>>> drain = new ArrayList<>();
        features.forEach(feature ->
                findOreConfig(feature.get()).ifPresent(ore -> {
                    if (DISABLE.contains(ore)) {
                        ProjectRankine.LOGGER.debug("Removing {} from generation in {}.", ore, name);
                        drain.add(feature);
                    }
                })
        );
        features.removeAll(drain);
    }

    private static Optional<BlockState> findOreConfig(ConfiguredFeature<?, ?> feature) {
        final Iterator<ConfiguredFeature<?, ?>> features = feature.config.getConfiguredFeatures().iterator();
        while (features.hasNext()) {
            final IFeatureConfig config = features.next().config;
            if (config instanceof OreFeatureConfig) {
                return Optional.of(((OreFeatureConfig) config).state);
            } else if (config instanceof BlockWithContextConfig) {
                return Optional.of((((BlockWithContextConfig) config).toPlace));
            } else if (config instanceof ReplaceBlockConfig) {
                return Optional.of(((ReplaceBlockConfig) config).state);
            }
        }
        return Optional.empty();
    }
}

