package com.cannolicatfish.rankine.world;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.world.gen.feature.MeteoriteFeatureConfig;
import com.cannolicatfish.rankine.world.gen.feature.ReplacerFeatureConfig;
import com.cannolicatfish.rankine.world.gen.feature.ReplacerPlacement;
import com.cannolicatfish.rankine.world.gen.placement.IntrusionPlacement;
import com.cannolicatfish.rankine.world.gen.placement.RankineDoublePlantPlacer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.*;

import java.util.OptionalInt;

public class RankineBiomeFeatures {

    public static final BaseTreeFeatureConfig TEST_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LOG.get().getDefaultState()), new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LEAVES.get().getDefaultState()), null, null, null)).setIgnoreVines().build();
    public static final BaseTreeFeatureConfig COCONUT_PALM_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.COCONUT_PALM_LOG.get().getDefaultState()), new SimpleBlockStateProvider(RankineBlocks.COCONUT_PALM_LEAVES.get().getDefaultState()),null, null, null)).setIgnoreVines().build();

    public static final BaseTreeFeatureConfig CEDAR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.CEDAR_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.CEDAR_LEAVES.get().getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.create(2, 1), FeatureSpread.create(0, 2), FeatureSpread.create(1, 1)),
            new StraightTrunkPlacer(7, 4, 1),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LEAVES.get().getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.create(1, 0), FeatureSpread.create(0, 2), FeatureSpread.create(1, 1)),
            new StraightTrunkPlacer(8, 5, 0),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig LARGE_EASTERN_HEMLOCK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.EASTERN_HEMLOCK_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.EASTERN_HEMLOCK_LEAVES.get().getDefaultState()),
            new MegaPineFoliagePlacer(FeatureSpread.create(0, 0), FeatureSpread.create(0, 0), FeatureSpread.create(5, 8)),
            new GiantTrunkPlacer(8, 2, 6),
            new TwoLayerFeature(1, 1, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig MAGNOLIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.MAGNOLIA_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.MAGNOLIA_LEAVES.get().getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.create(1, 0), FeatureSpread.create(0, 0)),
            new ForkyTrunkPlacer(4, 2, 2),
            new TwoLayerFeature(1, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig CINNAMON_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.CINNAMON_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.CINNAMON_LEAVES.get().getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.create(1, 0), FeatureSpread.create(0, 0)),
            new ForkyTrunkPlacer(5, 2, 1),
            new TwoLayerFeature(1, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig PINYON_PINE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.PINYON_PINE_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.PINYON_PINE_LEAVES.get().getDefaultState()),
            new DarkOakFoliagePlacer(FeatureSpread.create(0), FeatureSpread.create(0)),
            new DarkOakTrunkPlacer(5, 2, 2),
            new ThreeLayerFeature(1, 2, 1, 1, 2, OptionalInt.empty())))
            .setHeightmap(Heightmap.Type.MOTION_BLOCKING).setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig JUNIPER_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.JUNIPER_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.JUNIPER_LEAVES.get().getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.create(1, 0), FeatureSpread.create(1, 0)),
            new ForkyTrunkPlacer(1, 1, 1),
            new TwoLayerFeature(1, 1, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig ERYTHRINA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.ERYTHRINA_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.ERYTHRINA_LEAVES.get().getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.create(1, 0), FeatureSpread.create(0, 0)),
            new FancyTrunkPlacer(4, 4, 2),
            new ThreeLayerFeature(0, 0,0,0, 0, OptionalInt.of(4))))
            .setIgnoreVines()
            .build();




    //OTHER TREE CONFIGS
    public static final BaseTreeFeatureConfig DEAD_BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.create(1, 0), FeatureSpread.create(0, 2), FeatureSpread.create(1, 1)),
            new StraightTrunkPlacer(5, 2, 1),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.MAPLE_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.MAPLE_LEAVES.get().getDefaultState()),
            new FancyFoliagePlacer(FeatureSpread.create(3, 0), FeatureSpread.create(4, 0), 4),
            new FancyTrunkPlacer(8, 4, 0),
            new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))))
            .setIgnoreVines().setHeightmap(Heightmap.Type.MOTION_BLOCKING)
            .build();

    public static final BaseTreeFeatureConfig YELLOW_BIRCH_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.YELLOW_BIRCH_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.YELLOW_BIRCH_LEAVES.get().getDefaultState()),
            new FancyFoliagePlacer(FeatureSpread.create(2, 1), FeatureSpread.create(2, 0), 2),
            new FancyTrunkPlacer(6, 2, 4),
            new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))))
            .setIgnoreVines().setHeightmap(Heightmap.Type.MOTION_BLOCKING)
            .build();

    public static final BaseTreeFeatureConfig BLACK_BIRCH_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.BLACK_BIRCH_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.BLACK_BIRCH_LEAVES.get().getDefaultState()),
            new FancyFoliagePlacer(FeatureSpread.create(2, 1), FeatureSpread.create(2, 0), 1),
            new FancyTrunkPlacer(6, 2, 4),
            new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))))
            .setIgnoreVines().setHeightmap(Heightmap.Type.MOTION_BLOCKING)
            .build();

    public static final BaseTreeFeatureConfig PETRIFIED_CHORUS_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.PETRIFIED_CHORUS_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()),
            new FancyFoliagePlacer(FeatureSpread.create(0, 0), FeatureSpread.create(0, 0), 0),
            new FancyTrunkPlacer(6, 4, 5),
            new TwoLayerFeature(3, 0, 0, OptionalInt.of(5))))
            .setIgnoreVines().setHeightmap(Heightmap.Type.MOTION_BLOCKING)
            .build();

    public static final BaseTreeFeatureConfig BLACK_WALNUT_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.BLACK_WALNUT_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.BLACK_WALNUT_LEAVES.get().getDefaultState()),
            new FancyFoliagePlacer(FeatureSpread.create(2, 1), FeatureSpread.create(3, 0), 3),
            new FancyTrunkPlacer(8, 2, 5),
            new TwoLayerFeature(2, 0, 0, OptionalInt.of(4))))
            .setIgnoreVines().setHeightmap(Heightmap.Type.MOTION_BLOCKING)
            .build();

    public static final BaseTreeFeatureConfig CORK_OAK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.CORK_OAK_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.CORK_OAK_LEAVES.get().getDefaultState()),
            new FancyFoliagePlacer(FeatureSpread.create(3, 0), FeatureSpread.create(2, 0), 1),
            new FancyTrunkPlacer(6, 2, 5),
            new TwoLayerFeature(2, 0, 3, OptionalInt.of(4))))
            .setIgnoreVines().setHeightmap(Heightmap.Type.MOTION_BLOCKING)
            .build();

    public static final BaseTreeFeatureConfig SHARINGA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.SHARINGA_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.SHARINGA_LEAVES.get().getDefaultState()),
            new FancyFoliagePlacer(FeatureSpread.create(2, 1), FeatureSpread.create(2, 0), 3),
            new FancyTrunkPlacer(8, 2, 3),
            new TwoLayerFeature(1, 0, 0, OptionalInt.of(4))))
            .setIgnoreVines().setHeightmap(Heightmap.Type.MOTION_BLOCKING)
            .build();

    //public static final BYGAbstractTreeFeature<BYGTreeConfig> BAOBAB_TREE3 = createFeature("baobab_tree3", new BaobabTree3(BYGTreeConfig.CODEC.stable()));

    public static final BlockClusterFeatureConfig ELDERBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.ELDERBERRY_BUSH.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig POKEBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.POKEBERRY_BUSH.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig SNOWBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.SNOWBERRY_BUSH.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig BLUEBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BLUEBERRY_BUSH.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig RASPBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.RASPBERRY_BUSH.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig BLACKBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BLACKBERRY_BUSH.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig CRANBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.CRANBERRY_BUSH.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig STRAWBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.STRAWBERRY_BUSH.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig PINEAPPLE_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.PINEAPPLE_BUSH.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND)).preventProjection().build();
    public static final BlockClusterFeatureConfig BANANA_YUCCA_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BANANA_YUCCA_BUSH.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND)).preventProjection().build();
    public static final BlockClusterFeatureConfig ALOE_PLANT_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.ALOE_PLANT.get().getDefaultState().with(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND)).preventProjection().build();
    public static final BlockClusterFeatureConfig GOLDENROD_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.GOLDENROD.get().getDefaultState()), DoublePlantBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig RED_LILY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.RED_LILY.get().getDefaultState()), DoublePlantBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig WHITE_LILY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.WHITE_LILY.get().getDefaultState()), DoublePlantBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig ORANGE_LILY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.ORANGE_LILY.get().getDefaultState()), DoublePlantBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig PURPLE_MORNING_GLORY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.PURPLE_MORNING_GLORY.get().getDefaultState()), DoublePlantBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig BLACK_MORNING_GLORY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BLACK_MORNING_GLORY.get().getDefaultState()), DoublePlantBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();
    public static final BlockClusterFeatureConfig BLUE_MORNING_GLORY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BLUE_MORNING_GLORY.get().getDefaultState()), DoublePlantBlockPlacer.PLACER)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).preventProjection().build();

    private static <FC extends IFeatureConfig> void CFregister(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(ProjectRankine.MODID, name), configuredFeature);
    }
    //CONFIGURED FEATURES
    public static void registerConfiguredFeatures() {
        CFregister("disk_white_sand",DISK_WHITE_SAND);
        CFregister("disk_white_sand",DISK_BLACK_SAND);
        //CFregister("meteorite",METEORITE);
        CFregister("end_meteorite",END_METEORITE);
        CFregister("antimatter_blob",ANTIMATTER_BLOB);
        CFregister("fumarole",FUMAROLE);
        CFregister("column",COLUMN);
        CFregister("elderberry_bush",ELDERBERRY_BUSH);
        CFregister("elderberry_bush",POKEBERRY_BUSH);
        CFregister("snowberry_bush",SNOWBERRY_BUSH);
        CFregister("blueberry_bush",BLUEBERRY_BUSH);
        CFregister("raspberry_bush",RASPBERRY_BUSH);
        CFregister("blackberry_bush",BLACKBERRY_BUSH);
        CFregister("cranberry_bush",CRANBERRY_BUSH);
        CFregister("strawberry_bush",STRAWBERRY_BUSH);
        CFregister("pineapple_bush",PINEAPPLE_BUSH);
        CFregister("banana_yucca_bush",BANANA_YUCCA_BUSH);
        CFregister("aloe_plant",ALOE_PLANT);
        CFregister("goldenrod_patch",GOLDENROD_PATCH);
        CFregister("black_morning_glory_patch",BLACK_MORNING_GLORY_PATCH);
        CFregister("blue_morning_glory_patch",BLUE_MORNING_GLORY_PATCH);
        CFregister("purple_morning_glory_patch",PURPLE_MORNING_GLORY_PATCH);
        CFregister("red_lily_patch",RED_LILY_PATCH);
        CFregister("orange_lily_patch",ORANGE_LILY_PATCH);
        CFregister("white_lily_patch",WHITE_LILY_PATCH);
        CFregister("petrified_chorus_tree",PETRIFIED_CHORUS_TREE);
        CFregister("yellow_birch_tree",YELLOW_BIRCH_TREE);
        CFregister("black_birch_tree",BLACK_BIRCH_TREE);
        CFregister("eastern_hemlock_tree",EASTERN_HEMLOCK_TREE);
        CFregister("cedar_tree",CEDAR_TREE);
        CFregister("pinyon_pine_tree",PINYON_PINE_TREE);
        CFregister("balsam_fir_tree",BALSAM_FIR_TREE);
        CFregister("dead_balsam_fir_tree",DEAD_BALSAM_FIR_TREE);
        CFregister("magnolia_tree",MAGNOLIA_TREE);
        CFregister("juniper_tree",JUNIPER_TREE);
        CFregister("erythrina_tree",ERYTHRINA_TREE);
        CFregister("maple_tree",MAPLE_TREE);
        CFregister("black_walnut_tree",BLACK_WALNUT_TREE);
        CFregister("cork_oak_tree",CORK_OAK_TREE);
        CFregister("sharinga_tree",SHARINGA_TREE);
        CFregister("cinnamon_tree",CINNAMON_TREE);
        CFregister("flat_bedrock",FLAT_BEDROCK);
        CFregister("flat_bedrock_nether",FLAT_BEDROCK_NETHER);
        CFregister("ore_alluvium",ORE_ALLUVIUM);
        CFregister("ore_evaporite",ORE_EVAPORITE);
        CFregister("ore_intrusion", INTRUSION_FEATURE);
        CFregister("soil_gen", WORLD_REPLACER_GEN);
        CFregister("snow_gen",SNOW_GEN);
        //CFregister("test_tree",TEST_TREE_CF);

    }

    public static final Placement<NoPlacementConfig> REPLACER_PLACEMENT = new ReplacerPlacement(NoPlacementConfig.CODEC);
    public static final Placement<ChanceConfig> INTRUSION_PLACEMENT = new IntrusionPlacement(ChanceConfig.CODEC);


    // LOCAL_MODIFICATIONS
    public static final ConfiguredFeature<?, ?> METEORITE = RankineFeatures.METEORITE_FEATURE.get().withConfiguration(new MeteoriteFeatureConfig(RankineBlocks.METEORITE.get().getDefaultState(), 1))
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(Config.MISC_WORLDGEN.METEORITE_CHANCE.get())));
    public static final ConfiguredFeature<?, ?> DISK_WHITE_SAND = RankineFeatures.LAND_DISK.get().withConfiguration(new SphereReplaceConfig(RankineBlocks.WHITE_SAND.get().getDefaultState(), FeatureSpread.create(5, 4), 4, ImmutableList.of(Blocks.SAND.getDefaultState()))).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(3)));
    public static final ConfiguredFeature<?, ?> DISK_BLACK_SAND = RankineFeatures.LAND_DISK.get().withConfiguration(new SphereReplaceConfig(RankineBlocks.BLACK_SAND.get().getDefaultState(), FeatureSpread.create(3, 4), 4, ImmutableList.of(Blocks.SOUL_SOIL.getDefaultState(),Blocks.SOUL_SAND.getDefaultState())))
            .withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1)).withPlacement(Placement.CHANCE.configure(new ChanceConfig(3))));
    public static final ConfiguredFeature<?, ?> END_METEORITE = RankineFeatures.END_METEORITE_FEATURE.get().withConfiguration(new NoFeatureConfig());
    public static final ConfiguredFeature<?, ?> ANTIMATTER_BLOB = RankineFeatures.ANTIMATTER_BLOB_FEATURE.get().withConfiguration(new NoFeatureConfig());
    public static final ConfiguredFeature<?, ?> FUMAROLE = RankineFeatures.FUMAROLE_FEATURE.get().withConfiguration(new NoFeatureConfig())
            .withPlacement(Placement.CHANCE.configure(new ChanceConfig(40)));
    public static final ConfiguredFeature<?, ?> COLUMN = RankineFeatures.COLUMN_FEATURE.get().withConfiguration(new NoFeatureConfig())
            .withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));

    // VEGETAL_DECORATION
    public static final ConfiguredFeature<?, ?> ELDERBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.ELDERBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> POKEBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.POKEBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> SNOWBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.SNOWBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> BLUEBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.BLUEBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> RASPBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.RASPBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> BLACKBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.BLACKBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> CRANBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.CRANBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> STRAWBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.STRAWBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> PINEAPPLE_BUSH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.PINEAPPLE_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> BANANA_YUCCA_BUSH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.BANANA_YUCCA_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> ALOE_PLANT = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.ALOE_PLANT_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> GOLDENROD_PATCH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.GOLDENROD_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> RED_LILY_PATCH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.RED_LILY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> WHITE_LILY_PATCH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.WHITE_LILY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> ORANGE_LILY_PATCH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.ORANGE_LILY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> PURPLE_MORNING_GLORY_PATCH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.PURPLE_MORNING_GLORY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> BLUE_MORNING_GLORY_PATCH= Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.BLUE_MORNING_GLORY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> BLACK_MORNING_GLORY_PATCH = Feature.RANDOM_PATCH.withConfiguration(RankineBiomeFeatures.BLACK_MORNING_GLORY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);


    public static final ConfiguredFeature<?, ?> PETRIFIED_CHORUS_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.PETRIFIED_CHORUS_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> YELLOW_BIRCH_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.YELLOW_BIRCH_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> BLACK_BIRCH_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.BLACK_BIRCH_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> EASTERN_HEMLOCK_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.LARGE_EASTERN_HEMLOCK_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> CEDAR_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.CEDAR_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> PINYON_PINE_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.PINYON_PINE_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> DEAD_BALSAM_FIR_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.DEAD_BALSAM_FIR_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> MAGNOLIA_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.MAGNOLIA_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(3)));
    public static final ConfiguredFeature<?, ?> JUNIPER_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.JUNIPER_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> ERYTHRINA_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.ERYTHRINA_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> MAPLE_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.MAPLE_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(11)));
    public static final ConfiguredFeature<?, ?> BLACK_WALNUT_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.BLACK_WALNUT_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> CORK_OAK_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.CORK_OAK_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(3)));
    public static final ConfiguredFeature<?, ?> SHARINGA_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.SHARINGA_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> CINNAMON_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.CINNAMON_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));

    public static final ConfiguredFeature<?, ?> BALSAM_FIR_TREE = Feature.TREE.withConfiguration(RankineBiomeFeatures.BALSAM_FIR_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));

    // UNDERGROUND_ORES
    public static final ConfiguredFeature<?, ?> FLAT_BEDROCK = RankineFeatures.FLAT_BEDROCK_FEATURE.get().withConfiguration(
            new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), Blocks.BEDROCK.getDefaultState(), 0, Config.MISC_WORLDGEN.BEDROCK_LAYERS.get())).withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?, ?> FLAT_BEDROCK_NETHER = RankineFeatures.FLAT_BEDROCK_FEATURE.get().withConfiguration(
            new ReplacerFeatureConfig(Blocks.NETHERRACK.getDefaultState(), Blocks.BEDROCK.getDefaultState(), 0, Config.MISC_WORLDGEN.BEDROCK_LAYERS.get())).withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?, ?> ORE_ALLUVIUM = Feature.DISK.withConfiguration(new SphereReplaceConfig(RankineBlocks.ALLUVIUM.get().getDefaultState(), FeatureSpread.create(1, 2), 1,
            Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);
    public static final ConfiguredFeature<?, ?> ORE_EVAPORITE = Feature.DISK.withConfiguration(new SphereReplaceConfig(RankineBlocks.EVAPORITE.get().getDefaultState(), FeatureSpread.create(1, 1), 1,
            Lists.newArrayList(Blocks.STONE.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);

    public static final ConfiguredFeature<?, ?> INTRUSION_FEATURE = RankineFeatures.INTRUSION.get().withConfiguration(new NoFeatureConfig())
            .withPlacement(INTRUSION_PLACEMENT.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> WORLD_REPLACER_GEN = RankineFeatures.WORLD_REPLACER_FEATURE.get().withConfiguration(new NoFeatureConfig())
            .withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?, ?> SNOW_GEN = RankineFeatures.SNOW_REPLACER.get().withConfiguration(new NoFeatureConfig())
            .withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));

}
