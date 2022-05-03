package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.MeteoriteFeatureConfig;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RankineConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ProjectRankine.MODID);

    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_METEORITE = CONFIGURED_FEATURES.register("meteorite", () -> new ConfiguredFeature<>(RankineFeatures.METEORITE.get(), new MeteoriteFeatureConfig(RankineBlocks.METEORITE.get().defaultBlockState(), 1)));




/*
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_CEDAR_TREE = CONFIGURED_FEATURES.register("cedar_tree", () -> new ConfiguredFeature<>(RankineFeatures.CEDAR_TREE.get(),
            (new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(RankineBlocks.CEDAR_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(8, 5, 0),
                    BlockStateProvider.simple(RankineBlocks.CEDAR_LEAVES.get().defaultBlockState()),
                    new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
                    new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
                    .ignoreVines()
                    .build()));


    public static final TreeConfiguration HONEY_LOCUST_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.HONEY_LOCUST_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(8, 3, 0),
            BlockStateProvider.simple(RankineBlocks.HONEY_LOCUST_LEAVES.get().defaultBlockState()),
            new AcaciaFoliagePlacer(UniformInt.of(1, 0), UniformInt.of(0, 0)),
            new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build();

    public static final TreeConfiguration WEEPING_WILLOW_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.WEEPING_WILLOW_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(8, 3, 0),
            BlockStateProvider.simple(RankineBlocks.WEEPING_WILLOW_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration JUNIPER_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.JUNIPER_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(3, 1, 0),
            BlockStateProvider.simple(RankineBlocks.JUNIPER_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration PINYON_PINE_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.PINYON_PINE_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(6, 4, 0),
            BlockStateProvider.simple(RankineBlocks.PINYON_PINE_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration CEDAR_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.CEDAR_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(8, 5, 0),
            BlockStateProvider.simple(RankineBlocks.CEDAR_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration MAGNOLIA_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.MAGNOLIA_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(4, 2, 0),
            BlockStateProvider.simple(RankineBlocks.MAGNOLIA_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration ERYTHRINA_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.ERYTHRINA_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(6, 3, 0),
            BlockStateProvider.simple(RankineBlocks.ERYTHRINA_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration BLACK_WALNUT_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.BLACK_WALNUT_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(10, 5, 0),
            BlockStateProvider.simple(RankineBlocks.BLACK_WALNUT_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration EASTERN_HEMLOCK_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.EASTERN_HEMLOCK_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(15, 10, 0),
            BlockStateProvider.simple(RankineBlocks.EASTERN_HEMLOCK_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration WESTERN_HEMLOCK_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.WESTERN_HEMLOCK_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(25, 10, 0),
            BlockStateProvider.simple(RankineBlocks.WESTERN_HEMLOCK_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration BALSAM_FIR_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.BALSAM_FIR_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(10, 10, 0),
            BlockStateProvider.simple(RankineBlocks.BALSAM_FIR_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration SHORT_BALSAM_FIR_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.BALSAM_FIR_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(5, 4, 0),
            BlockStateProvider.simple(RankineBlocks.BALSAM_FIR_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration COCONUT_PALM_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.COCONUT_PALM_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(7, 10, 0),
            BlockStateProvider.simple(RankineBlocks.COCONUT_PALM_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration RED_BIRCH_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.RED_BIRCH_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(4, 3, 0),
            BlockStateProvider.simple(RankineBlocks.RED_BIRCH_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration BLACK_BIRCH_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.BLACK_BIRCH_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(4, 6, 0),
            BlockStateProvider.simple(RankineBlocks.BLACK_BIRCH_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration YELLOW_BIRCH_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.YELLOW_BIRCH_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(6, 6, 0),
            BlockStateProvider.simple(RankineBlocks.YELLOW_BIRCH_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration BIRCH_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(Blocks.BIRCH_LOG.defaultBlockState()),
            new StraightTrunkPlacer(6, 6, 0),
            BlockStateProvider.simple(Blocks.BIRCH_LEAVES.defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration PETRIFIED_CHORUS_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.PETRIFIED_CHORUS_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(0,0, 0),
            BlockStateProvider.simple(Blocks.AIR.defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();



    public static final TreeConfiguration CINNAMON_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.CINNAMON_LOG.get().defaultBlockState()),
            new ForkingTrunkPlacer(5, 2, 1),
            BlockStateProvider.simple(RankineBlocks.CINNAMON_LEAVES.get().defaultBlockState()),
            new AcaciaFoliagePlacer(UniformInt.of(1, 0), UniformInt.of(0, 0)),
            new TwoLayersFeatureSize(1, 0, 2)))
            .ignoreVines()
            .build();

    public static final TreeConfiguration MAPLE_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.MAPLE_LOG.get().defaultBlockState()),
            new FancyTrunkPlacer(8, 4, 0),
            BlockStateProvider.simple(RankineBlocks.MAPLE_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(4, 0), 4),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration CORK_OAK_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.CORK_OAK_LOG.get().defaultBlockState()),
            new FancyTrunkPlacer(6, 2, 5),
            BlockStateProvider.simple(RankineBlocks.CORK_OAK_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(3, 0), UniformInt.of(2, 0), 1),
            new TwoLayersFeatureSize(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines()
            .build();

    public static final TreeConfiguration SHARINGA_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(RankineBlocks.SHARINGA_LOG.get().defaultBlockState()),
            new FancyTrunkPlacer(8, 2, 3),
            BlockStateProvider.simple(RankineBlocks.SHARINGA_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(UniformInt.of(2, 1), UniformInt.of(2, 0), 3),
            new TwoLayersFeatureSize(1, 0, 0, OptionalInt.of(4))))
            .ignoreVines()
            .build();


 */


    /*
    public static final RandomPatchConfiguration ELDERBERRY_BUSH_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.ELDERBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration POKEBERRY_BUSH_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.POKEBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration SNOWBERRY_BUSH_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.SNOWBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration BLUEBERRY_BUSH_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration RASPBERRY_BUSH_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.RASPBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration BLACKBERRY_BUSH_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.BLACKBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration CRANBERRY_BUSH_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.CRANBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration STRAWBERRY_BUSH_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.STRAWBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration PINEAPPLE_BUSH_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.PINEAPPLE_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND)).noProjection().build();
    public static final RandomPatchConfiguration BANANA_YUCCA_BUSH_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.BANANA_YUCCA_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND)).noProjection().build();
    public static final RandomPatchConfiguration ALOE_PLANT_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.ALOE_PLANT.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND)).noProjection().build();
    public static final RandomPatchConfiguration GOLDENROD_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.GOLDENROD.get().defaultBlockState()), DoublePlantPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration RED_LILY_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.RED_LILY.get().defaultBlockState()), DoublePlantPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration WHITE_LILY_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.WHITE_LILY.get().defaultBlockState()), DoublePlantPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration ORANGE_LILY_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.ORANGE_LILY.get().defaultBlockState()), DoublePlantPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration PURPLE_MORNING_GLORY_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.PURPLE_MORNING_GLORY.get().defaultBlockState()), DoublePlantPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration BLACK_MORNING_GLORY_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.BLACK_MORNING_GLORY.get().defaultBlockState()), DoublePlantPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final RandomPatchConfiguration BLUE_MORNING_GLORY_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.BLUE_MORNING_GLORY.get().defaultBlockState()), DoublePlantPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
*/

    // LOCAL_MODIFICATIONS
    /*
    public static final ConfiguredFeature<?, ?> DISK_WHITE_SAND = new ConfiguredFeature<>(RankineFeatures.LAND_DISK.get(),new DiskConfiguration(RankineBlocks.WHITE_SAND.get().defaultBlockState(), UniformInt.of(5, 4), 4, ImmutableList.of(Blocks.SAND.defaultBlockState())));
    public static final ConfiguredFeature<?, ?> DISK_BLACK_SAND = new ConfiguredFeature<>(RankineFeatures.LAND_DISK.get(),new DiskConfiguration(RankineBlocks.BLACK_SAND.get().defaultBlockState(), UniformInt.of(3, 4), 4, ImmutableList.of(Blocks.SOUL_SOIL.defaultBlockState(),Blocks.SOUL_SAND.defaultBlockState())));
    public static final ConfiguredFeature<?, ?> END_METEORITE = new ConfiguredFeature<>(RankineFeatures.END_METEORITE_FEATURE.get(),new NoneFeatureConfiguration());
    public static final ConfiguredFeature<?, ?> ANTIMATTER_BLOB = new ConfiguredFeature<>(RankineFeatures.ANTIMATTER_BLOB_FEATURE.get(),new NoneFeatureConfiguration());
    public static final ConfiguredFeature<?, ?> FUMAROLE = new ConfiguredFeature<>(RankineFeatures.FUMAROLE_FEATURE.get(),new NoneFeatureConfiguration());
    public static final ConfiguredFeature<?, ?> COLUMN = new ConfiguredFeature<>(RankineFeatures.COLUMN_FEATURE.get(),new NoneFeatureConfiguration());
    public static final ConfiguredFeature<?, ?> MUSHROOMS = new ConfiguredFeature<>(RankineFeatures.WALL_MUSHROOMS.get(),new NoneFeatureConfiguration());

    /* VEGETAL_DECORATION
    public static final ConfiguredFeature<?, ?> ELDERBERRY_BUSH = new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.ELDERBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> POKEBERRY_BUSH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.POKEBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> SNOWBERRY_BUSH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.SNOWBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> BLUEBERRY_BUSH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.BLUEBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> RASPBERRY_BUSH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.RASPBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> BLACKBERRY_BUSH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.BLACKBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> CRANBERRY_BUSH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.CRANBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> STRAWBERRY_BUSH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.STRAWBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> PINEAPPLE_BUSH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.PINEAPPLE_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> BANANA_YUCCA_BUSH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.BANANA_YUCCA_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> ALOE_PLANT =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.ALOE_PLANT_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> GOLDENROD_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.GOLDENROD_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> RED_LILY_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.RED_LILY_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> WHITE_LILY_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.WHITE_LILY_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> ORANGE_LILY_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.ORANGE_LILY_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> PURPLE_MORNING_GLORY_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.PURPLE_MORNING_GLORY_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> BLUE_MORNING_GLORY_PATCH=new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.BLUE_MORNING_GLORY_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> BLACK_MORNING_GLORY_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,RankineBiomeFeatures.BLACK_MORNING_GLORY_PATCH_CONFIG);

    public static final ConfiguredFeature<?, ?> TINDER_CONK_MUSHROOM = new ConfiguredFeature<>(RankineFeatures.TINDER_CONK_FEATURE.get(),new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.TINDER_CONK_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> LIONS_MANE_MUSHROOM = new ConfiguredFeature<>(RankineFeatures.LIONS_MANE_FEATURE.get(),new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.TINDER_CONK_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> TURKEY_TAIL_MUSHROOM = new ConfiguredFeature<>(RankineFeatures.TURKEY_TAIL_FEATURE.get(),new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.TURKEY_TAIL_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> HONEY_MUSHROOM = new ConfiguredFeature<>(RankineFeatures.HONEY_FEATURE.get(),new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.HONEY_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> SULFUR_SHELF_MUSHROOM = new ConfiguredFeature<>(RankineFeatures.SULFUR_SHELF_FEATURE.get(),new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.SULFUR_SHELF_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> CINNABAR_POLYPORE_MUSHROOM = new ConfiguredFeature<>(RankineFeatures.CINNABAR_POLYPORE_FEATURE.get(),new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.CINNABAR_POLYPORE_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> ARTIST_CONK_MUSHROOM = new ConfiguredFeature<>(RankineFeatures.ARTIST_CONK_FEATURE.get(),new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.ARTIST_CONK_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> OYSTER_MUSHROOM = new ConfiguredFeature<>(RankineFeatures.OYSTER_FEATURE.get(),new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.OYSTER_MUSHROOM_BLOCK.get().defaultBlockState())));

    public static Set<Block> blks = ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT);

     */
    /*
    public static final ConfiguredFeature<?, ?> OAT_PLANT_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.OAT_PLANT.get().defaultBlockState().setValue(RankineCropsBlock.AGE,7)), CropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build());
    public static final ConfiguredFeature<?, ?> MILLET_PLANT_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.MILLET_PLANT.get().defaultBlockState().setValue(RankineCropsBlock.AGE,7)), CropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build());
    public static final ConfiguredFeature<?, ?> RICE_PLANT_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.RICE_PLANT.get().defaultBlockState().setValue(RankineCropsBlock.AGE,7)), CropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build());
    public static final ConfiguredFeature<?, ?> RYE_PLANT_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.RYE_PLANT.get().defaultBlockState().setValue(DoubleCropsBlock.AGE,7)), DoubleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build());
    public static final ConfiguredFeature<?, ?> BARLEY_PLANT_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.BARLEY_PLANT.get().defaultBlockState().setValue(DoubleCropsBlock.AGE,7)), DoubleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build());
    public static final ConfiguredFeature<?, ?> COTTON_PLANT_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.COTTON_PLANT.get().defaultBlockState().setValue(DoubleCropsBlock.AGE,7)), DoubleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build());
    public static final ConfiguredFeature<?, ?> ASPARAGUS_PLANT_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.ASPARAGUS_PLANT.get().defaultBlockState().setValue(DoubleCropsBlock.AGE,7)), DoubleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build());
    public static final ConfiguredFeature<?, ?> CORN_PLANT_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.CORN_PLANT.get().defaultBlockState().setValue(TripleCropsBlock.AGE,7)), TripleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build());
    public static final ConfiguredFeature<?, ?> SORGHUM_PLANT_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.SORGHUM_PLANT.get().defaultBlockState().setValue(TripleCropsBlock.AGE,7)), TripleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build());
    public static final ConfiguredFeature<?, ?> JUTE_PLANT_PATCH =new ConfiguredFeature<>(Feature.RANDOM_PATCH,(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.JUTE_PLANT.get().defaultBlockState().setValue(TripleCropsBlock.AGE,7)), TripleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build());

    public static final ConfiguredFeature<?, ?> COBBLE_PATCH = RankineFeatures.COBBLE_PATCH.get(),(new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.PEGMATITE_COBBLE.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(30).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.MYCELIUM, Blocks.SAND, Blocks.STONE)).noProjection().build())
            .decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.CHANCE,new ChanceDecoratorConfiguration(1)));*/



    // UNDERGROUND_ORES
 //   public static final ConfiguredFeature<?, ?> FLAT_BEDROCK = new ConfiguredFeature<>(RankineFeatures.FLAT_BEDROCK_FEATURE.get(),
 //           new ReplacerFeatureConfig(Blocks.STONE.defaultBlockState(), Blocks.BEDROCK.defaultBlockState(), 0, Config.MISC_WORLDGEN.BEDROCK_LAYERS.get()));
  //  public static final ConfiguredFeature<?, ?> FLAT_BEDROCK_NETHER = new ConfiguredFeature<>(RankineFeatures.FLAT_BEDROCK_FEATURE.get(),
  //          new ReplacerFeatureConfig(Blocks.NETHERRACK.defaultBlockState(), Blocks.BEDROCK.defaultBlockState(), 0, Config.MISC_WORLDGEN.BEDROCK_LAYERS.get()));
  //  public static final ConfiguredFeature<?, ?> ORE_ALLUVIUM =new ConfiguredFeature<>(Feature.DISK,new DiskConfiguration(RankineBlocks.ALLUVIUM.get().defaultBlockState(), UniformInt.of(1, 2), 1,
  //          Lists.newArrayList(Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState(), Blocks.SAND.defaultBlockState(), Blocks.GRAVEL.defaultBlockState())));
   // public static final ConfiguredFeature<?, ?> ORE_EVAPORITE =new ConfiguredFeature<>(Feature.DISK,new DiskConfiguration(RankineBlocks.EVAPORITE.get().defaultBlockState(), UniformInt.of(1, 1), 1,
    //        Lists.newArrayList(Blocks.STONE.defaultBlockState(), Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState(), Blocks.SAND.defaultBlockState(), Blocks.GRAVEL.defaultBlockState())));

    //public static final ConfiguredFeature<?, ?> INTRUSION_FEATURE = new ConfiguredFeature<>(RankineFeatures.INTRUSION.get(), new NoneFeatureConfiguration());
    //public static final ConfiguredFeature<?, ?> WORLD_REPLACER_GEN = new ConfiguredFeature<>(RankineFeatures.WORLD_REPLACER_FEATURE.get(),new NoneFeatureConfiguration());
   // public static final ConfiguredFeature<?, ?> POST_WORLD_REPLACER_GEN = new ConfiguredFeature<>(RankineFeatures.POST_WORLD_REPLACER_FEATURE.get(),new NoneFeatureConfiguration());

}
