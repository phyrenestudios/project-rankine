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



    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void addFeaturesToBiomes(final BiomeLoadingEvent biome) {

        if (biome.getName() != null) {
            //Disable features
            disableGenerators(biome.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES), biome.getName(), Arrays.asList(Blocks.ANDESITE.defaultBlockState(),Blocks.DIORITE.defaultBlockState(),Blocks.GRANITE.defaultBlockState(),
                    Blocks.COPPER_ORE.defaultBlockState(),Blocks.IRON_ORE.defaultBlockState(),Blocks.COAL_ORE.defaultBlockState(),Blocks.GOLD_ORE.defaultBlockState(),Blocks.REDSTONE_ORE.defaultBlockState(),Blocks.LAPIS_ORE.defaultBlockState(),Blocks.DIAMOND_ORE.defaultBlockState(),Blocks.EMERALD_ORE.defaultBlockState(),
                    Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState(),Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(),Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(),Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState(),Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState(),Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState(),Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState(),Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState()));


            if (Config.MISC_WORLDGEN.BEDROCK_LAYERS.get() != 0) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_FLAT_BEDROCK.getHolder().get());
            }
            if (Config.MISC_WORLDGEN.LAYER_GEN.get() != 0) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_WORLD_REPLACER.getHolder().get());
            }
            biome.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, RankinePlacedFeatures.PLACED_POST_WORLD_REPLACER.getHolder().get());

            if (Config.MISC_WORLDGEN.METEORITE_CHANCE.get() > 0.0D && WorldgenUtils.isOverworld(biome)) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, RankinePlacedFeatures.PLACED_METEORITE.getHolder().get());
            }
            if (Config.MISC_WORLDGEN.FUMAROLE_GEN.get() > 0) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, RankinePlacedFeatures.PLACED_FUMAROLE.getHolder().get());
            }
            if (Config.MISC_WORLDGEN.INTRUSION_GEN.get()) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_INTRUSION.getHolder().get());
            }
            if (Config.MISC_WORLDGEN.MUSHROOMS.get() && WorldgenUtils.isOverworld(biome)) {
                biome.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RankinePlacedFeatures.PLACED_WALL_MUSHROOMS.getHolder().get());
            }
            if (Config.MISC_WORLDGEN.RANKINE_FLORA.get() && WorldgenUtils.isOverworld(biome)) {
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
            if (Config.MISC_WORLDGEN.RANKINE_TREES.get() && WorldgenUtils.isOverworld(biome)) {
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

            if (Config.MISC_WORLDGEN.COBBLES_GEN.get() && WorldgenUtils.isOverworld(biome)) {
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

