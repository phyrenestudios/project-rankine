package com.cannolicatfish.rankine.world.features;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankinePlacedFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
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
    private static List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> getTopLayernFeatures() {
        List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> topLayer = new ArrayList<>();
        //if (Config.MISC_WORLDGEN.METEORITE_GEN.get()) {

     //   if (Config.MISC_WORLDGEN.METEORITE_GEN.get()) {
    //        topLayer.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.METEORITE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
    //    }
    //    topLayer.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.POST_WORLD_REPLACER, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));



        //}

        return topLayer;
    }

    /*
    private static List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> getLocalModificationFeatures() {
        List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> LocalModifications = new ArrayList<>();


        return LocalModifications;
    }

     */

    private static List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> getVegetalDecorationFeatures() {
        List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> VegetalDecor = new ArrayList<>();

        //VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineVegetationPlacements.TREES_CEDAR, Collections.singletonList(ResourceLocation.tryParse("minecraft:taiga"))));

        /*
        if (Config.MISC_WORLDGEN.RANKINE_FLORA.get()) {
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.ELDERBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.FOREST, Biome.BiomeCategory.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.POKEBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.FOREST, Biome.BiomeCategory.TAIGA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.SNOWBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.EXTREME_HILLS, Biome.BiomeCategory.ICY),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BLUEBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.RIVER, Biome.BiomeCategory.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.RASPBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.FOREST),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BLACKBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.FOREST),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.CRANBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.SWAMP),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.STRAWBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.PINEAPPLE_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.JUNGLE),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BANANA_YUCCA_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.SAVANNA, Biome.BiomeCategory.DESERT, Biome.BiomeCategory.MESA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.ALOE_PLANT,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.SAVANNA, Biome.BiomeCategory.DESERT, Biome.BiomeCategory.MESA),true)));

            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.GOLDENROD_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.SAVANNA, Biome.BiomeCategory.DESERT, Biome.BiomeCategory.MESA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.RED_LILY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.SAVANNA, Biome.BiomeCategory.DESERT, Biome.BiomeCategory.MESA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.WHITE_LILY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.EXTREME_HILLS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.ORANGE_LILY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BLACK_MORNING_GLORY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.JUNGLE),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BLUE_MORNING_GLORY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.JUNGLE),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.PURPLE_MORNING_GLORY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.TAIGA),true)));
        }
        if (Config.MISC_WORLDGEN.MUSHROOMS.get()) {
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.MUSHROOM, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }
        if (Config.MISC_WORLDGEN.RANKINE_TREES.get()) {

            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.RICE_PLANT_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.RIVER, Biome.BiomeCategory.SWAMP), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.OAT_PLANT_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.PLAINS), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.MILLET_PLANT_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.SAVANNA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BARLEY_PLANT_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.PLAINS), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.RYE_PLANT_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.SAVANNA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.COTTON_PLANT_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.PLAINS), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.ASPARAGUS_PLANT_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.BEACH), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.CORN_PLANT_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.PLAINS), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.JUTE_PLANT_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.JUNGLE), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.SORGHUM_PLANT_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.SAVANNA), true)));
        }
        if (Config.MISC_WORLDGEN.COBBLES_GEN.get()) {
            //VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.COBBLE_PATCH, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }
        if (Config.MISC_WORLDGEN.RANKINE_TREES.get()) {
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.BALSAM_FIR_TREE,
                    Collections.singletonList(ResourceLocation.tryParse("minecraft:wooded_mountains"))));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.SHORT_BALSAM_FIR_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.EXTREME_HILLS), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.COCONUT_PALM_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.BEACH), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.BLACK_WALNUT_TREE,
                    Arrays.asList(ResourceLocation.tryParse("minecraft:dark_forest"),ResourceLocation.tryParse("minecraft:dark_forest_hills"))));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.RED_BIRCH_TREE,
                    Collections.singletonList(ResourceLocation.tryParse("minecraft:wooded_mountains"))));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.EASTERN_HEMLOCK_TREE,
                    Arrays.asList(ResourceLocation.tryParse("minecraft:giant_tree_taiga"),ResourceLocation.tryParse("minecraft:giant_spruce_taiga"))));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.WESTERN_HEMLOCK_TREE,
                    Arrays.asList(ResourceLocation.tryParse("minecraft:giant_tree_taiga_hills"),ResourceLocation.tryParse("minecraft:giant_spruce_taiga_hills"))));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.BLACK_BIRCH_TREE,
                    Arrays.asList(ResourceLocation.tryParse("minecraft:birch_forest"),ResourceLocation.tryParse("minecraft:tall_birch_forest"),ResourceLocation.tryParse("minecraft:tall_birch_hills"),ResourceLocation.tryParse("minecraft:birch_forest_hills"))));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.BLACK_BIRCH_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.RIVER), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.YELLOW_BIRCH_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.FOREST), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.MODIFIED_BIRCH_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.FOREST), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.PETRIFIED_CHORUS_TREE,
                    Arrays.asList(ResourceLocation.tryParse("minecraft:end_barrens"),ResourceLocation.tryParse("minecraft:end_midlands"),ResourceLocation.tryParse("minecraft:end_highlands"),ResourceLocation.tryParse("minecraft:small_end_islands"))));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.PETRIFIED_CHORUS_TREE,
                    Collections.singletonList(ResourceLocation.tryParse("minecraft:the_end"))));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.PINYON_PINE_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.SAVANNA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.JUNIPER_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.SAVANNA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.ERYTHRINA_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.MESA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.MAGNOLIA_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.RIVER), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.HONEY_LOCUST_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.PLAINS), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.WEEPING_WILLOW_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.SWAMP), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.MAPLE_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.FOREST), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.SHARINGA_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.JUNGLE), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.CORK_OAK_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.JUNGLE), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.CINNAMON_TREE,
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.JUNGLE), true)));
        }

         */

        return VegetalDecor;
    }


    private static List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> getAllUndDecFeatures() {
        List<AbstractMap.SimpleEntry<Holder<PlacedFeature>, List<ResourceLocation>>> AllOreFeatures = new ArrayList<>();
        if (Config.MISC_WORLDGEN.INTRUSION_GEN.get()) {
      //      AllOreFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.INTRUSION_FEATURE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }
        if (Config.MISC_WORLDGEN.COLUMN_GEN.get()) {
        //    AllOreFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.COLUMN_FEATURE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }

        return AllOreFeatures;
    }
    private static List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> getBedraockFeatures() {
        List<AbstractMap.SimpleEntry<Holder<PlacedFeature>, List<ResourceLocation>>> BedrockFeatures = new ArrayList<>();

   //     if (Config.MISC_WORLDGEN.FLAT_BEDROCK.get()) { BedrockFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.FLAT_BEDROCK, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.NETHER, Biome.BiomeCategory.THEEND), false))); }
   //     if (Config.MISC_WORLDGEN.FLAT_BEDROCK_NETHER.get()) { BedrockFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.FLAT_BEDROCK_NETHER, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.NETHER), true))); }

        return BedrockFeatures;
    }
    private static List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> getAllOreFeatures() {
        List<AbstractMap.SimpleEntry<Holder<PlacedFeature>, List<ResourceLocation>>> AllOreFeatures = new ArrayList<>();
        if (Config.MISC_WORLDGEN.LAYER_GEN.get() != 0) {
          //  AllOreFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.WORLD_REPLACER, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }

        //AllOreFeatures.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.WORLD_REPLACER_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        //AllOreFeatures.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.INTRUSION_FEATURE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));

      //  if (Config.MISC_WORLDGEN.WHITE_SAND_GEN.get()) { AllOreFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.WHITE_SAND, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.BEACH), true))); }
      //  if (Config.MISC_WORLDGEN.ALLUVIUM_GEN.get()) { AllOreFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.ALLUVIUM, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.OCEAN, Biome.BiomeCategory.RIVER), true))); }
      //  if (Config.MISC_WORLDGEN.EVAPORITE_GEN.get()) { AllOreFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.EVAPORITE, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.OCEAN, Biome.BiomeCategory.BEACH), false))); }

        return AllOreFeatures;
    }

    private static List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> getOverworldOreFeatures() {
        List<AbstractMap.SimpleEntry<Holder<PlacedFeature>, List<ResourceLocation>>> OverworldFeatures = new ArrayList<>();

     //   if (Config.MISC_WORLDGEN.FUMAROLE_GEN.get()) { OverworldFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.FUMAROLE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false))); }

        return OverworldFeatures;
    }

    private static List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> getNetherOreFeatures() {
        List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> NetherFeatures = new ArrayList<>();

     //   if (Config.MISC_WORLDGEN.FUMAROLE_GEN.get()) { NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.FUMAROLE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.BiomeCategory.NETHER), true))); }
     //   if (Config.MISC_WORLDGEN.BLACK_SAND_GEN.get()) { NetherFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.BLACK_SAND, WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.BiomeCategory.NETHER), true))); }

        return NetherFeatures;
    }

    private static List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> getEndOreFeatures() {
        List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> EndFeatures = new ArrayList<>();

        if (Config.MISC_WORLDGEN.END_METEORITE_GEN.get()) {
      //      EndFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.END_METEORITE, Arrays.asList(ResourceLocation.tryParse("minecraft:end_barrens"),ResourceLocation.tryParse("minecraft:small_end_islands"))));
        }
        if (Config.MISC_WORLDGEN.SECRET_GEN.get()) {
       //     EndFeatures.add(new AbstractMap.SimpleEntry<>(RankinePlacedFeatures.ANTIMATTER_BLOB, Arrays.asList(ResourceLocation.tryParse("minecraft:end_barrens"))));
        }

        return EndFeatures;
    }



    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void addFeaturesToBiomes(final BiomeLoadingEvent biome) {

        if (biome.getName() != null) {
            biome.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, RankinePlacedFeatures.PLACED_METEORITE.getHolder().get());

            if (biome.getCategory() == Biome.BiomeCategory.TAIGA) {
            //if (ForgeRegistries.BIOMES.tags().getTag(BiomeTags.IS_TAIGA).contains(ForgeRegistries.BIOMES.getValue(biome.getName()))) {
                //biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_CEDAR_TREE.getHolder().get());
            }
            //GenerationStep.Decoration ugDecorationStage = GenerationStep.Decoration.UNDERGROUND_ORES;
     /*
            List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> vegetalDecorationFeatures = getVegetalDecorationFeatures();
            for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : vegetalDecorationFeatures) {
                if (entry.getValue().contains(event.getName())) {
                    event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION.ordinal(), entry.getKey());
                }
            }
            //---Disable vanilla features---
            //final List<Holder<PlacedFeature>> ORES = event.getGeneration().getFeatures(ugDecorationStage);
            //disableGenerators(ORES, event.getName(), Arrays.asList(Blocks.DIRT.defaultBlockState(),Blocks.ANDESITE.defaultBlockState(),Blocks.DIORITE.defaultBlockState(),Blocks.GRANITE.defaultBlockState(),Blocks.INFESTED_STONE.defaultBlockState(),Blocks.IRON_ORE.defaultBlockState(),Blocks.COAL_ORE.defaultBlockState(),Blocks.GOLD_ORE.defaultBlockState(),Blocks.DIAMOND_ORE.defaultBlockState(),Blocks.EMERALD_ORE.defaultBlockState(),Blocks.LAPIS_ORE.defaultBlockState(),Blocks.REDSTONE_ORE.defaultBlockState()));
            //event.getGeneration().getFeatures(ugDecorationStage).removeIf(featureSupplier -> featureSupplier.toString().contains("net.minecraft.util.registry.WorldSettingsImport"));

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



/*
            for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : getBedraockFeatures()) {
                if (entry.getValue().contains(event.getName())) {
                    event.getGeneration().addFeature(ugDecorationStage.ordinal(),entry.getKey());
                }
            }
            for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : getAllOreFeatures()) {
                if (entry.getValue().contains(event.getName())) {
                    event.getGeneration().addFeature(ugDecorationStage.ordinal(),entry.getKey());
                }
            }

            if (event.getCategory() != Biome.BiomeCategory.NETHER && event.getCategory() != Biome.BiomeCategory.THEEND) {
                for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : getOverworldOreFeatures()) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().addFeature(ugDecorationStage.ordinal(),entry.getKey());
                    }
                }
                for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : OVERWORLD_FEATURES) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().addFeature(ugDecorationStage.ordinal(),entry.getKey());
                    }
                }
            } else if (event.getCategory() == Biome.BiomeCategory.NETHER) {
                for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : NETHER_FEATURES) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().addFeature(ugDecorationStage.ordinal(),entry.getKey());
                    }
                }
                for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : getNetherOreFeatures()) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().addFeature(ugDecorationStage.ordinal(),entry.getKey());
                    }
                }

            } else if (event.getCategory() == Biome.BiomeCategory.THEEND) {
                for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : getEndOreFeatures()) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().addFeature(ugDecorationStage.ordinal(),entry.getKey());
                    }
                }
                for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : END_FEATURES) {
                    if (entry.getValue().contains(event.getName())) {
                        event.getGeneration().addFeature(ugDecorationStage.ordinal(),entry.getKey());
                    }
                }
            }

            for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : getAllUndDecFeatures()) {
                if (entry.getValue().contains(event.getName())) {
                    event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION.ordinal(),entry.getKey());
                }
            }

            List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> localModificationFeatures = getLocalModificationFeatures();
            for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : localModificationFeatures) {
                if (entry.getValue().contains(event.getName())) {
                    event.getGeneration().withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS.ordinal(),entry.getKey());
                }
            }4






            List<AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>>> topLayernFeatures = getTopLayernFeatures();
            for (AbstractMap.SimpleEntry<Holder<PlacedFeature>,List<ResourceLocation>> entry : topLayernFeatures) {
                if (entry.getValue().contains(event.getName())) {
                    event.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION.ordinal(), entry.getKey());
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
                        ProjectRankine.LOGGER.debug("Removing {} from generation in {}.", ore, name);
                        drain.add(feature);
                    }
                })
        );
        features.removeAll(drain);
    }

    private static Optional<BlockState> findOreConfig(Holder<PlacedFeature> feature) {
        ConfiguredFeature<?,?> te = feature.value().feature().value();
        final Iterator<ConfiguredFeature<?, ?>> features = te.config().getFeatures().iterator();
        while (features.hasNext()) {
            final FeatureConfiguration config = features.next().config();
            if (config instanceof OreConfiguration) {
                return Optional.of(((OreConfiguration) config).targetStates.get(0).state);
            } else if (config instanceof SimpleBlockConfiguration) {
                return Optional.of((((SimpleBlockConfiguration) config).toPlace().getState(new Random(), BlockPos.ZERO)));
            } else if (config instanceof ReplaceBlockConfiguration) {
                return Optional.of(((ReplaceBlockConfiguration) config).targetStates.get(0).state);
            }
        }
        return Optional.empty();
    }
}

