package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Collections;

public class RankinePlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ProjectRankine.MODID);

    public static final RegistryObject<PlacedFeature> PLACED_METEORITE = PLACED_FEATURES.register("meteorite", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_METEORITE.getHolder().get(), Arrays.asList(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(Config.MISC_WORLDGEN.METEORITE_CHANCE.get()), BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_END_METEORITE = PLACED_FEATURES.register("end_meteorite", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_END_METEORITE.getHolder().get(), Collections.singletonList(BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_ANTIMATTER_BLOB = PLACED_FEATURES.register("antimatter_blob", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_ANTIMATTER_BLOB.getHolder().get(), Collections.singletonList(BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_WALL_MUSHROOMS = PLACED_FEATURES.register("wall_mushrooms", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_WALL_MUSHROOMS.getHolder().get(), Arrays.asList(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(3), BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_WORLD_REPLACER = PLACED_FEATURES.register("world_replacer", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_WORLD_REPLACER.getHolder().get(), Collections.emptyList()));
    public static final RegistryObject<PlacedFeature> PLACED_POST_WORLD_REPLACER = PLACED_FEATURES.register("pos_world_replacer", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_POST_WORLD_REPLACER.getHolder().get(), Collections.emptyList()));
    public static final RegistryObject<PlacedFeature> PLACED_FLAT_BEDROCK = PLACED_FEATURES.register("flat_bedrock", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_FLAT_BEDROCK.getHolder().get(), Collections.emptyList()));

    public static final RegistryObject<PlacedFeature> PLACED_PATCH_LILIES = PLACED_FEATURES.register("patch_lilies", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_LILIES.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_PATCH_MORNING_GLORIES = PLACED_FEATURES.register("patch_morning_glories", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_MORNING_GLORIES.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_PATCH_SAVANNA_FLOWERS = PLACED_FEATURES.register("patch_savanna_flowers", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_SAVANNA_FLOWERS.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_PATCH_SAVANNA_PLANTS = PLACED_FEATURES.register("patch_savanna_plants", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_SAVANNA_PLANTS.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_PATCH_FOREST_PLANTS = PLACED_FEATURES.register("patch_forest_plants", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_FOREST_PLANTS.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_PATCH_SWAMP_PLANTS = PLACED_FEATURES.register("patch_swamp_plants", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_SWAMP_PLANTS.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_PATCH_JUNGLE_PLANTS = PLACED_FEATURES.register("patch_jungle_plants", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_JUNGLE_PLANTS.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_PATCH_PLAINS_PLANTS = PLACED_FEATURES.register("patch_plains_plants", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_PLAINS_PLANTS.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_PATCH_MOUNTAIN_PLANTS = PLACED_FEATURES.register("patch_mountain_plants", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_MOUNTAIN_PLANTS.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_PATCH_TAIGA_PLANTS = PLACED_FEATURES.register("patch_taiga_plants", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_TAIGA_PLANTS.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PLACED_PATCH_COBBLES = PLACED_FEATURES.register("patch_cobbles", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PATCH_COBBLES.getHolder().get(), Arrays.asList(RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> PLACED_CEDAR_TREE = PLACED_FEATURES.register("cedar_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_CEDAR_TREE.getHolder().get(), VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))));
    public static final RegistryObject<PlacedFeature> PLACED_EASTERN_HEMLOCK_TREE = PLACED_FEATURES.register("eastern_hemlock_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_EASTERN_HEMLOCK_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_WESTERN_HEMLOCK_TREE = PLACED_FEATURES.register("western_hemlock_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_WESTERN_HEMLOCK_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_BALSAM_FIR_TREE = PLACED_FEATURES.register("balsam_fir_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_BALSAM_FIR_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_SHORT_BALSAM_FIR_TREE = PLACED_FEATURES.register("short_balsam_fir_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_SHORT_BALSAM_FIR_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_COCONUT_PALM_TREE = PLACED_FEATURES.register("coconut_palm_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_COCONUT_PALM_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_MODIFIED_BIRCH_TREE = PLACED_FEATURES.register("modified_birch_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_MODIFIED_BIRCH_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_BLACK_BIRCH_TREE = PLACED_FEATURES.register("black_birch_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_BLACK_BIRCH_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_YELLOW_BIRCH_TREE = PLACED_FEATURES.register("yellow_birch_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_YELLOW_BIRCH_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_RED_BIRCH_TREE = PLACED_FEATURES.register("red_birch_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_RED_BIRCH_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_MAGNOLIA_TREE = PLACED_FEATURES.register("magnolia_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_MAGNOLIA_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(6))));
    public static final RegistryObject<PlacedFeature> PLACED_ERYTHRINA_TREE = PLACED_FEATURES.register("erythrina_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_ERYTHRINA_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(6))));
    public static final RegistryObject<PlacedFeature> PLACED_HONEY_LOCUST_TREE = PLACED_FEATURES.register("honey_locust_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_HONEY_LOCUST_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_WEEPING_WILLOW_TREE = PLACED_FEATURES.register("weeping_willow_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_WEEPING_WILLOW_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(6))));
    public static final RegistryObject<PlacedFeature> PLACED_BLACK_WALNUT_TREE = PLACED_FEATURES.register("black_walnut_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_BLACK_WALNUT_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_PINYON_PINE_TREE = PLACED_FEATURES.register("pinyon_pine_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PINYON_PINE_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_JUNIPER_TREE = PLACED_FEATURES.register("juniper_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_JUNIPER_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_MAPLE_TREE = PLACED_FEATURES.register("maple_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_MAPLE_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_SHARINGA_TREE = PLACED_FEATURES.register("sharinga_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_SHARINGA_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_CORK_OAK_TREE = PLACED_FEATURES.register("cork_oak_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_CORK_OAK_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_CINNAMON_TREE = PLACED_FEATURES.register("cinnamon_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_CINNAMON_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));
    public static final RegistryObject<PlacedFeature> PLACED_PETRIFIED_CHORUS_TREE = PLACED_FEATURES.register("petrified_chorus_tree", () -> new PlacedFeature(RankineConfiguredFeatures.CONFIGURED_PETRIFIED_CHORUS_TREE.getHolder().get(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4))));



    public static void registerAllFeatures() {
/*
        MUSHROOM = registerPlacedFeature("mushroom",RankineConfiguredFeatures.MUSHROOMS, (PlacementModifier) Collections.emptyList());

        INTRUSION_FEATURE = registerPlacedFeature("intrusion",RankineConfiguredFeatures.INTRUSION_FEATURE,(PlacementModifier) Collections.emptyList());
        COLUMN_FEATURE = registerPlacedFeature("column",RankineConfiguredFeatures.COLUMN,(PlacementModifier) Collections.emptyList());
        FLAT_BEDROCK = registerPlacedFeature("flat_bedrock",RankineConfiguredFeatures.FLAT_BEDROCK,(PlacementModifier) Collections.emptyList());
        FLAT_BEDROCK_NETHER = registerPlacedFeature("flat_bedrock_nether",RankineConfiguredFeatures.FLAT_BEDROCK_NETHER,(PlacementModifier) Collections.emptyList());
        WHITE_SAND = registerPlacedFeature("white_sand",RankineConfiguredFeatures.DISK_WHITE_SAND,(PlacementModifier) Collections.emptyList());
        FUMAROLE = registerPlacedFeature("fumarole",RankineConfiguredFeatures.FUMAROLE,(PlacementModifier) Collections.emptyList());
        BLACK_SAND = registerPlacedFeature("black_sand",RankineConfiguredFeatures.DISK_BLACK_SAND,(PlacementModifier) Collections.emptyList());
        METEORITE = registerPlacedFeature("meteorite",RankineConfiguredFeatures.METEORITE,(PlacementModifier) Collections.emptyList());
        END_METEORITE = registerPlacedFeature("end_meteorite",RankineConfiguredFeatures.END_METEORITE,(PlacementModifier) Collections.emptyList());
        ANTIMATTER_BLOB = registerPlacedFeature("antimatter_blob",RankineConfiguredFeatures.ANTIMATTER_BLOB,(PlacementModifier) Collections.emptyList());


        EVAPORITE = registerPlacedFeature("evaporite",RankineConfiguredFeatures.ORE_EVAPORITE,(PlacementModifier) Collections.emptyList());
        ALLUVIUM = registerPlacedFeature("alluvium",RankineConfiguredFeatures.ORE_ALLUVIUM,(PlacementModifier) Collections.emptyList());

 */}

}
