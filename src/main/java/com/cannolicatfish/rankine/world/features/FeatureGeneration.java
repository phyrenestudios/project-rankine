package com.cannolicatfish.rankine.world.features;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankinePlacedFeatures;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber
public class FeatureGeneration {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void addFeaturesToBiomes(final BiomeLoadingEvent biome) {

        if (biome.getName() != null) {
            //Disable features
            disableGenerators(biome.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES), biome.getName(), Arrays.asList(Blocks.ANDESITE.defaultBlockState(),Blocks.DIORITE.defaultBlockState(),Blocks.GRANITE.defaultBlockState(),
                    Blocks.COPPER_ORE.defaultBlockState(),Blocks.IRON_ORE.defaultBlockState(),Blocks.COAL_ORE.defaultBlockState(),Blocks.GOLD_ORE.defaultBlockState(),Blocks.REDSTONE_ORE.defaultBlockState(),Blocks.LAPIS_ORE.defaultBlockState(),Blocks.DIAMOND_ORE.defaultBlockState(),Blocks.EMERALD_ORE.defaultBlockState(),
                    Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState(),Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(),Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(),Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState(),Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState(),Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState(),Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState(),Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState()));


            if (Config.WORLDGEN.BEDROCK_LAYERS.get() != 0) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_FLAT_BEDROCK.getHolder().get());
            }
            if (Config.WORLDGEN.LAYER_GEN.get() != 0) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_WORLD_REPLACER.getHolder().get());
            }
            biome.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, RankinePlacedFeatures.PLACED_POST_WORLD_REPLACER.getHolder().get());

            if (Config.WORLDGEN.METEORITE_CHANCE.get() > 0.0D && WorldgenUtils.isOverworld(biome)) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, RankinePlacedFeatures.PLACED_METEORITE.getHolder().get());
            }
            if (Config.WORLDGEN.FUMAROLE_GEN.get() > 0) {
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
                } if (biome.getName().toString().equals(Biomes.DARK_FOREST.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BLACK_WALNUT_TREE.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.GROVE.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_SNOWY_SHORT_BALSAM_FIR_TREE.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.WINDSWEPT_FOREST.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BALSAM_FIR_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_SHORT_BALSAM_FIR_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_RED_BIRCH_TREE.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.BIRCH_FOREST.location().toString()) || biome.getName().toString().equals(Biomes.OLD_GROWTH_BIRCH_FOREST.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_YELLOW_BIRCH_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_MODIFIED_BIRCH_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BLACK_BIRCH_TREE.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.TAIGA.location().toString()) || biome.getName().toString().equals(Biomes.SNOWY_TAIGA.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_EASTERN_HEMLOCK_TREE.getHolder().get());
                } else if (biome.getName().toString().equals(Biomes.OLD_GROWTH_SPRUCE_TAIGA.location().toString()) || biome.getName().toString().equals(Biomes.OLD_GROWTH_PINE_TAIGA.location().toString())) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_WESTERN_HEMLOCK_TREE.getHolder().get());
                }

                if (biome.getCategory() == Biome.BiomeCategory.TAIGA) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_CEDAR_TREE.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.PLAINS) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_HONEY_LOCUST_TREE.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.SAVANNA) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_JUNIPER_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PINYON_PINE_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_CORK_OAK_TREE.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.MESA) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_ERYTHRINA_TREE.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.SWAMP) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_WEEPING_WILLOW_TREE.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.BEACH) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_COCONUT_PALM_TREE.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.RIVER) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_BLACK_BIRCH_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_MAGNOLIA_TREE.getHolder().get());
                } else if (biome.getCategory() == Biome.BiomeCategory.JUNGLE) {
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_SHARINGA_TREE.getHolder().get());
                    biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_CINNAMON_TREE.getHolder().get());
                }
            }

            if (Config.WORLDGEN.COBBLES_GEN.get() && WorldgenUtils.isOverworld(biome)) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_PATCH_COBBLES.getHolder().get());
            }

     /*

          //  List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> OVERWORLD_FEATURES = new ArrayList<>();
          //  List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> NETHER_FEATURES = new ArrayList<>();
          //  List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> END_FEATURES = new ArrayList<>();

            //---Ore Settings---
            for (List<Object> L : Config.BIOME_GEN.ORE_SETTINGS.get()) {
                boolean inEnd = false;
                boolean inNether = false;
                boolean inOverworld = false;
                Block oreBlock = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse((String) L.get(0)));
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
                            genBiomes.add(ResourceLocation.tryParse(b));
                        } else {
                            genBiomes.addAll(WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.byName(b)), true));
                            for (ResourceLocation RS : genBiomes) {
                                Biome rsBiome = ForgeRegistries.BIOMES.getValue(RS);
                                if (rsBiome != null) {
                                    Biome.BiomeCategory biomeCat = Biome.getBiomeCategory(Holder.direct(rsBiome));
                                    if (biomeCat == Biome.BiomeCategory.THEEND) {
                                        inEnd = true;
                                    }
                                    if (biomeCat == Biome.BiomeCategory.NETHER) {
                                        inNether = true;
                                    }
                                    inOverworld = true;
                                }

                            }

                        }
                    }
                }

                String type = (String) L.get(2);
                int minHeight = (int) L.get(3);
                int maxHeight = (int) L.get(4);
                int size = (int) L.get(5);
                float density = (float) (double) L.get(6);
                int count = (int) L.get(7);
                float chance = (float) (double) L.get(8);
                Holder<PlacedFeature> oreFeature;

                /*
                if (type.equals("sphere")) {
                    oreFeature = Holder.direct(new PlacedFeature(Holder.direct(new ConfiguredFeature<>(RankineFeatures.SPHERE_ORE.get(),new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.ORE_FILLER, oreBlock.defaultBlockState(), size, density, chance))),
                        List.of(HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(minHeight), VerticalAnchor.absolute(maxHeight)))));
                } else if (type.equals("disk")) {
                    oreFeature = Holder.direct(new PlacedFeature(Holder.direct(new ConfiguredFeature<>(RankineFeatures.DISK_ORE.get(),new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.ORE_FILLER, oreBlock.defaultBlockState(), size, density, chance))),
                            List.of(HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(minHeight), VerticalAnchor.absolute(maxHeight)))));
                } else {
                    oreFeature = Holder.direct(new PlacedFeature(Holder.direct(new ConfiguredFeature<>(RankineFeatures.DEFAULT_ORE.get(),new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.ORE_FILLER, oreBlock.defaultBlockState(), size, density, chance))),
                            List.of(HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(minHeight), VerticalAnchor.absolute(maxHeight)))));
                }


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
            */



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
    }
}

