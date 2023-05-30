package com.cannolicatfish.rankine.worldgen;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FeatureGeneration {
/*
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void addFeaturesToBiomes(final BiomeLoadingEvent biome) {

        if (biome.getName() != null) {
            //Disable features
            if (Config.WORLDGEN.DISABLE_VANILLA_FEATURES.get()) disableGenerators(biome.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES), biome.getName(), Arrays.asList(Blocks.ANDESITE.defaultBlockState(),Blocks.DIORITE.defaultBlockState(),Blocks.GRANITE.defaultBlockState(),
                    Blocks.COPPER_ORE.defaultBlockState(),Blocks.IRON_ORE.defaultBlockState(),Blocks.COAL_ORE.defaultBlockState(),Blocks.GOLD_ORE.defaultBlockState(),Blocks.REDSTONE_ORE.defaultBlockState(),Blocks.LAPIS_ORE.defaultBlockState(),Blocks.DIAMOND_ORE.defaultBlockState(),Blocks.EMERALD_ORE.defaultBlockState(),
                    Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState(),Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(),Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(),Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState(),Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState(),Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState(),Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState(),Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState()));

            if (Config.WORLDGEN.BEDROCK_LAYERS.get() != 0) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_FLAT_BEDROCK.getHolder().get());
            }
            if (Config.WORLDGEN.LAYER_GEN.get() != 0) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_WORLD_REPLACER.getHolder().get());
            }

            if (Config.WORLDGEN.METEORITE_CHANCE.get() > 0 && WorldgenUtils.isOverworld(biome)) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, RankinePlacedFeatures.PLACED_METEORITE.getHolder().get());
            }
            if (Config.WORLDGEN.FUMAROLE_GEN.get() > 0 && (WorldgenUtils.isOverworld(biome) || biome.getCategory() == Biome.BiomeCategory.NETHER)) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, RankinePlacedFeatures.PLACED_FUMAROLE.getHolder().get());
            }
            if (Config.WORLDGEN.INTRUSION_GEN.get()) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_INTRUSION.getHolder().get());
            }
            if (Config.WORLDGEN.MUSHROOMS.get() && WorldgenUtils.isOverworld(biome)) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_WALL_MUSHROOMS.getHolder().get());
            }
            if (Config.WORLDGEN.RANKINE_FLORA.get() && WorldgenUtils.isOverworld(biome)) {
                if (biome.getCategory() == Biome.BiomeCategory.SAVANNA) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_SAVANNA_FLOWERS.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_SAVANNA_PLANTS.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.FOREST) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_LILIES.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_FOREST_PLANTS.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.JUNGLE) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_MORNING_GLORIES.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_JUNGLE_PLANTS.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.PLAINS) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_PLAINS_PLANTS.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.SWAMP) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_SWAMP_PLANTS.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.TAIGA) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_TAIGA_PLANTS.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.MOUNTAIN || biome.getCategory() == Biome.BiomeCategory.EXTREME_HILLS) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_MOUNTAIN_PLANTS.getHolder().get());
                }
            }
            if (Config.WORLDGEN.RANKINE_TREES.get() && WorldgenUtils.isOverworld(biome)) {
                if (biome.getName().toString().equals(Biomes.FOREST.location().toString()) || biome.getName().toString().equals(Biomes.FLOWER_FOREST.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_MAPLE_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_MAPLE_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_OAK_FALLEN_LOG.getHolder().get());
                } if (biome.getName().toString().equals(Biomes.DARK_FOREST.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BLACK_WALNUT_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BLACK_WALNUT_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_DARK_OAK_FALLEN_LOG.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.BEACH.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_COCONUT_PALM_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_COCONUT_PALM_FALLEN_LOG.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.GROVE.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_SNOWY_SHORT_BALSAM_FIR_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BALSAM_FIR_FALLEN_LOG.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.WINDSWEPT_FOREST.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BALSAM_FIR_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_SHORT_BALSAM_FIR_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_RED_BIRCH_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BALSAM_FIR_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_RED_BIRCH_FALLEN_LOG.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.BIRCH_FOREST.location().toString()) || biome.getName().toString().equals(Biomes.OLD_GROWTH_BIRCH_FOREST.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_YELLOW_BIRCH_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_MODIFIED_BIRCH_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BLACK_BIRCH_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_YELLOW_BIRCH_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BLACK_BIRCH_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BIRCH_FALLEN_LOG.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.TAIGA.location().toString()) || biome.getName().toString().equals(Biomes.SNOWY_TAIGA.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_EASTERN_HEMLOCK_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_EASTERN_HEMLOCK_FALLEN_LOG.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.OLD_GROWTH_SPRUCE_TAIGA.location().toString()) || biome.getName().toString().equals(Biomes.OLD_GROWTH_PINE_TAIGA.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_WESTERN_HEMLOCK_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_WESTERN_HEMLOCK_FALLEN_LOG.getHolder().get());
                }

                if (biome.getCategory() == Biome.BiomeCategory.TAIGA) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_CEDAR_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_CEDAR_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_SPRUCE_FALLEN_LOG.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.PLAINS) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_HONEY_LOCUST_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_HONEY_LOCUST_FALLEN_LOG.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.SAVANNA) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_JUNIPER_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PINYON_PINE_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_CORK_OAK_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_CORK_OAK_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_JUNIPER_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PINYON_PINE_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_ACACIA_FALLEN_LOG.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.MESA) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_ERYTHRINA_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_ERYTHRINA_FALLEN_LOG.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.SWAMP) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_WEEPING_WILLOW_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_WEEPING_WILLOW_FALLEN_LOG.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.RIVER) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BLACK_BIRCH_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_MAGNOLIA_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_MAGNOLIA_FALLEN_LOG.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.JUNGLE) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_SHARINGA_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_CINNAMON_TREE.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_SHARINGA_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_CINNAMON_FALLEN_LOG.getHolder().get());
                    if (Config.WORLDGEN.FALLEN_LOGS.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_JUNGLE_FALLEN_LOG.getHolder().get());
                }
            }

            if (Config.WORLDGEN.COBBLES_GEN.get() && WorldgenUtils.isOverworld(biome)) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, RankinePlacedFeatures.PLACED_PATCH_COBBLES.getHolder().get());
            }
            if (Config.WORLDGEN.END_METEORITE_GEN.get()) {
                if (biome.getName().toString().equals(Biomes.END_HIGHLANDS.location().toString()) || biome.getName().toString().equals(Biomes.END_MIDLANDS.location().toString()) || biome.getName().toString().equals(Biomes.END_BARRENS.location().toString()) || biome.getName().toString().equals(Biomes.SMALL_END_ISLANDS.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_END_METEORITE.getHolder().get());
                    if (Config.WORLDGEN.RANKINE_TREES.get()) biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PETRIFIED_CHORUS_TREE.getHolder().get());
                }
            }
            if (Config.WORLDGEN.ANTIMATTER_GEN.get()) {
                if (biome.getName().toString().equals(Biomes.END_HIGHLANDS.location().toString()) || biome.getName().toString().equals(Biomes.END_MIDLANDS.location().toString()) || biome.getName().toString().equals(Biomes.END_BARRENS.location().toString()) || biome.getName().toString().equals(Biomes.SMALL_END_ISLANDS.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_ANTIMATTER_BLOB.getHolder().get());
                }
            }


            //---Ore Settings---
            for (List<Object> L : Config.BIOME_GEN.ORE_SETTINGS.get()) {
                Block oreBlock = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse((String) L.get(0)));
                if (oreBlock == null) break;
                List<String> biomeList = (List<String>) L.get(1);
                List<ResourceLocation> genBiomes = WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), true);
                if (biomeList.get(0).isEmpty()) {
                    break;
                } else if (biomeList.get(0).equals("overworld")) {
                    genBiomes = WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.THEEND, Biome.BiomeCategory.NETHER), false);
                } else {
                    for (String b : biomeList) {
                        List<String> biomeName = Arrays.asList(b.split(":"));
                        if (biomeName.size() > 1) {
                            genBiomes.add(ResourceLocation.tryParse(b));
                        } else {
                            genBiomes.addAll(WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.byName(b)), true));
                        }
                    }
                }

                String veinType = (String) L.get(2);
                String distributionType = (String) L.get(3);
                int minHeight = (int) L.get(4);
                int maxHeight = (int) L.get(5);
                int size = (int) L.get(6);
                float density = (float) (double) L.get(7);
                int count = (int) L.get(8);
                float spawnChance = (float) (double) L.get(9);
                float discardChance = (float) (double) L.get(10);

                Holder<Feature<RankineOreFeatureConfig>> veinTypeFeature;
                Holder<PlacedFeature> oreFeature;

                if (veinType.equals("sphere")) {
                    veinTypeFeature = RankineFeatures.SPHERICAL_ORE_VEIN.getHolder().get();
                } else if (veinType.equals("disk")) {
                    veinTypeFeature = RankineFeatures.DISK_ORE_VEIN.getHolder().get();
                } else {
                    veinTypeFeature = RankineFeatures.DEFAULT_ORE_VEIN.getHolder().get();
                }

                if (distributionType.equals("triangle")) {
                    oreFeature = Holder.direct(new PlacedFeature(Holder.direct(new ConfiguredFeature<>(veinTypeFeature.value(),new RankineOreFeatureConfig(new TagMatchTest(RankineTags.Blocks.RANKINE_ORE_REPLACEABLES), oreBlock.defaultBlockState(), size, discardChance, density, spawnChance))),
                            List.of(CountPlacement.of(count), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)), BiomeFilter.biome())));
                } else {
                    oreFeature = Holder.direct(new PlacedFeature(Holder.direct(new ConfiguredFeature<>(veinTypeFeature.value(),new RankineOreFeatureConfig(new TagMatchTest(RankineTags.Blocks.RANKINE_ORE_REPLACEABLES), oreBlock.defaultBlockState(), size, discardChance, density, spawnChance))),
                            List.of(CountPlacement.of(count), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)), BiomeFilter.biome())));
                }

                if (genBiomes.contains(biome.getName())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, oreFeature);
                }



            }



        }

    }

    private static void disableGenerators(List<Holder<PlacedFeature>> features, ResourceLocation name, List<BlockState> DISABLE) {
        final List<Holder<PlacedFeature>> drain = new ArrayList<>();
        features.forEach(feature ->
                findOreConfig(feature).ifPresent(ore -> {
                    if (DISABLE.contains(ore)) {
                        //ProjectRankine.LOGGER.debug("Removing {} from generation in {}.", ore, name);
                        drain.add(feature);
                    }
                })
        );
        features.removeAll(drain);
    }

    private static Optional<BlockState> findOreConfig(Holder<PlacedFeature> feature) {
        ConfiguredFeature<?,?> te = feature.value().feature().value();
        final FeatureConfiguration config = te.config();
        if (config instanceof OreConfiguration) {
            return Optional.of(((OreConfiguration) config).targetStates.get(0).state);
        } else if (config instanceof SimpleBlockConfiguration) {
            return Optional.of((((SimpleBlockConfiguration) config).toPlace().getState(new Random(), BlockPos.ZERO)));
        } else if (config instanceof ReplaceBlockConfiguration) {
            return Optional.of(((ReplaceBlockConfiguration) config).targetStates.get(0).state);
        }
        return Optional.empty();
    }*/
}

