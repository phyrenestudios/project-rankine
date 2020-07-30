package com.cannolicatfish.rankine.world.biome;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.world.gen.feature.RankineFeatures;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.*;
import net.minecraft.block.trees.BirchTree;
import net.minecraft.block.trees.OakTree;
import net.minecraft.block.trees.SpruceTree;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.AxisRotatingBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.ForestFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.PlainFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.*;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.CocoaTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.*;
import net.minecraftforge.common.IPlantable;

import java.util.OptionalInt;

public class RankineBiomeFeatures {

    //MOD TREES
    public static final BaseTreeFeatureConfig CEDAR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.CEDAR_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.CEDAR_LEAVES.getDefaultState()),
            new SpruceFoliagePlacer(2, 1, 0, 2, 1, 1),
            new StraightTrunkPlacer(6, 4, 1),
            new TwoLayerFeature(2, 0, 2)))
            .func_236700_a_()
            .build();

    public static final BaseTreeFeatureConfig BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.BALSAM_FIR_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.BALSAM_FIR_LEAVES.getDefaultState()),
            new SpruceFoliagePlacer(1, 0, 0, 2, 1, 1),
            new StraightTrunkPlacer(5, 2, 1),
            new TwoLayerFeature(2, 0, 2)))
            .func_236700_a_()
            .build();

    public static final BaseTreeFeatureConfig EASTERN_HEMLOCK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.EASTERN_HEMLOCK_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.EASTERN_HEMLOCK_LEAVES.getDefaultState()),
            new SpruceFoliagePlacer(2, 1, 0, 2, 1, 1),
            new StraightTrunkPlacer(4, 4, 1),
            new TwoLayerFeature(2, 0, 2)))
            .func_236700_a_()
            .build();

    public static final BaseTreeFeatureConfig MAGNOLIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.MAGNOLIA_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.MAGNOLIA_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(2, 0, 0, 0),
            new ForkyTrunkPlacer(3, 2, 2),
            new TwoLayerFeature(1, 0, 2)))
            .func_236700_a_()
            .build();

    public static final BaseTreeFeatureConfig PINYON_PINE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.PINYON_PINE_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.PINYON_PINE_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(2, 0, 0, 0),
            new ForkyTrunkPlacer(3, 2, 2),
            new TwoLayerFeature(1, 0, 2)))
            .func_236700_a_()
            .build();

    public static final BaseTreeFeatureConfig JUNIPER_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.JUNIPER_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.JUNIPER_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(1, 0, 0, 0),
            new ForkyTrunkPlacer(2, 0, 0),
            new TwoLayerFeature(1, 0, 2)))
            .func_236700_a_()
            .build();

    public static final BaseTreeFeatureConfig COCNUT_PALM_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.COCONUT_PALM_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.COCONUT_PALM_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(1, 0, 0, 0),
            new ForkyTrunkPlacer(10, 2, 2),
            new TwoLayerFeature(1, 0, 2)))
            .func_236700_a_()
            .build();

    //LARGE TREES
    public static final BaseTreeFeatureConfig LARGE_EASTERN_HEMLOCK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.EASTERN_HEMLOCK_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.EASTERN_HEMLOCK_LEAVES.getDefaultState()),
            new MegaPineFoliagePlacer(0, 0, 0, 0, 4, 13),
            new GiantTrunkPlacer(13, 2, 14),
            new TwoLayerFeature(1, 1, 2)))
            .func_236703_a_(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.getDefaultState()))))
            .build();

    //OTHER TREE CONFIGS
    public static final BaseTreeFeatureConfig DEAD_BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.BALSAM_FIR_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()),
            new SpruceFoliagePlacer(1, 0, 0, 2, 1, 1),
            new StraightTrunkPlacer(5, 2, 1),
            new TwoLayerFeature(2, 0, 2)))
            .func_236700_a_()
            .build();

    public static final BaseTreeFeatureConfig SMALL_SPRUCE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
            new SpruceFoliagePlacer(2, 1, 0, 2, 1, 1),
            new StraightTrunkPlacer(3, 1, 1),
            new TwoLayerFeature(2, 0, 2)))
            .func_236700_a_()
            .build();

    public static final BaseTreeFeatureConfig LAGOOAN_OAK = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.STRIPPED_DARK_OAK_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(2, 0, 1, 0),
            new ForkyTrunkPlacer(2, 1, 2),
            new TwoLayerFeature(1, 0, 2)))
            .func_236700_a_()
            .build();

    public static final BaseTreeFeatureConfig YELLOW_BIRCH_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.YELLOW_BIRCH_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0, 0, 0, 3),
            new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().build();



    //OTHER
    public static final BlockClusterFeatureConfig FERN_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.FERN.getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
    public static final BlockClusterFeatureConfig SWAMP_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.SWAMP_GRASS.getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
    public static final BlockClusterFeatureConfig DUCKWEED_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.DUCKWEED.getDefaultState()), new SimpleBlockPlacer())).tries(10).build();
    public static final BlockClusterFeatureConfig SHORT_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.SHORT_GRASS.getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
    public static final BlockClusterFeatureConfig CLOVER_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addWeightedBlockstate(ModBlocks.WHITE_CLOVER.getDefaultState(), 4).addWeightedBlockstate(ModBlocks.PURPLE_CLOVER.getDefaultState(), 1), new SimpleBlockPlacer())).tries(32).build();


    public static void addModStructures(Biome biomeIn) {
    }

    public static void addCedarForestTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.withConfiguration(BALSAM_FIR_TREE_CONFIG).withChance(0.05F), Feature.field_236291_c_.withConfiguration(SMALL_SPRUCE_CONFIG).withChance(0.05F), Feature.field_236291_c_.withConfiguration(EASTERN_HEMLOCK_TREE_CONFIG).withChance(0.1F)), Feature.field_236291_c_.withConfiguration(CEDAR_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(10, 0.2F, 3))));
    }

    public static void addPinyonJuniperWoodlandsTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.withConfiguration(JUNIPER_TREE_CONFIG).withChance(0.15F)), Feature.field_236291_c_.withConfiguration(PINYON_PINE_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(7, 0.2F, 3))));
    }

    public static void addDeadSwampVegetation(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.withConfiguration(BALSAM_FIR_TREE_CONFIG).withChance(0.1F), Feature.field_236291_c_.withConfiguration(EASTERN_HEMLOCK_TREE_CONFIG).withChance(0.1F)), Feature.field_236291_c_.withConfiguration(DEAD_BALSAM_FIR_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(4, 0.1F, 4))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(DefaultBiomeFeatures.BLUE_ORCHID_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(DefaultBiomeFeatures.DEFAULT_FLOWER_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(SWAMP_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(5))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(20))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.DEAD_BUSH_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(10))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.LILY_PAD_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(10))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DUCKWEED_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(10))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(8, 0.5F))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.configure(new HeightWithChanceConfig(8, 0.5F))));
    }

    public static void addHemlockGroveTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.withConfiguration(LARGE_EASTERN_HEMLOCK_TREE_CONFIG).withChance(0.1F), Feature.field_236291_c_.withConfiguration(BALSAM_FIR_TREE_CONFIG).withChance(0.1F), Feature.field_236291_c_.withConfiguration(YELLOW_BIRCH_TREE_CONFIG).withChance(0.1F)), Feature.field_236291_c_.withConfiguration(EASTERN_HEMLOCK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(12, 0.1F, 1))));
    }

    public static void addDenseFerns(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.LARGE_FERN_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(40))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(FERN_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(30))));
    }

    public static void addSaltSpikes(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, RankineFeatures.SPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(10))));
    }

    public static void addTropicsDecor(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.DESERT_WELL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(100))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(COCNUT_PALM_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(6, 0.15F, 4))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.2F)).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(2))));
    }

    public static void addLagoonDecor(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(MAGNOLIA_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(6, 0.1F, 4))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(LAGOOAN_OAK).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(6, 0.1F, 4))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(20))));
        biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(DefaultBiomeFeatures.DEFAULT_FLOWER_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(3))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(DefaultBiomeFeatures.LILY_OF_THE_VALLEY_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
    }

    public static void addHighlandDecor(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(SHORT_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(10))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(CLOVER_PATCH_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(4))));
    }

    public static void addCrackedCrustStuff(Biome biomeIn) {
        biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.2F)));
        biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CANYON, new ProbabilityConfig(0.05F)));
        biomeIn.addFeature(GenerationStage.Decoration.LAKES, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(10))));
        biomeIn.addFeature(GenerationStage.Decoration.LAKES, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(ModBlocks.LIQUID_MERCURY_BLOCK.getDefaultState())).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(80))));
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Feature.MONSTER_ROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.DUNGEONS.configure(new ChanceConfig(3))));

    }


    }
