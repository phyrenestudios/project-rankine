package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.world.gen.feature.*;
import com.cannolicatfish.rankine.world.gen.placement.IntrusionPlacement;
import com.cannolicatfish.rankine.world.gen.placement.RankineDoublePlantPlacer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.*;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunkplacer.*;

import java.util.OptionalInt;
import java.util.Set;

public class RankineFeatures {

    public static void registerConfiguredFeatures() {
        //WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:animal_spawner", ANIMAL_SPAWNER);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:meteorite",METEORITE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:end_meteorite",END_METEORITE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:antimatter_blob",ANTIMATTER_BLOB);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:fumarole",FUMAROLE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:elderberry_bush",ELDERBERRY_BUSH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:elderberry_bush",POKEBERRY_BUSH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:snowberry_bush",SNOWBERRY_BUSH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:blueberry_bush",BLUEBERRY_BUSH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:raspberry_bush",RASPBERRY_BUSH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:blackberry_bush",BLACKBERRY_BUSH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:cranberry_bush",CRANBERRY_BUSH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:strawberry_bush",STRAWBERRY_BUSH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:pineapple_bush",PINEAPPLE_BUSH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:banana_yucca_bush",BANANA_YUCCA_BUSH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:aloe_plant",ALOE_PLANT);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:goldenrod_patch",GOLDENROD_PATCH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:black_morning_glory_patch",BLACK_MORNING_GLORY_PATCH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:blue_morning_glory_patch",BLUE_MORNING_GLORY_PATCH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:purple_morning_glory_patch",PURPLE_MORNING_GLORY_PATCH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:red_lily_patch",RED_LILY_PATCH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:orange_lily_patch",ORANGE_LILY_PATCH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:white_lily_patch",WHITE_LILY_PATCH);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:yellow_birch_tree",YELLOW_BIRCH_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:black_birch_tree",BLACK_BIRCH_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:eastern_hemlock_tree",EASTERN_HEMLOCK_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:cedar_tree",CEDAR_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:coconut_palm_tree",COCONUT_PALM_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:pinyon_pine_tree",PINYON_PINE_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:balsam_fir_tree",BALSAM_FIR_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:dead_balsam_fir_tree",DEAD_BALSAM_FIR_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:magnolia_tree",MAGNOLIA_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:juniper_tree",JUNIPER_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:erythrina_tree",ERYTHRINA_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:maple_tree",MAPLE_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:black_walnut_tree",BLACK_WALNUT_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:cork_oak_tree",CORK_OAK_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:sharinga_tree",SHARINGA_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:cinnamon_tree",CINNAMON_TREE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:flat_bedrock",FLAT_BEDROCK);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:flat_bedrock_nether",FLAT_BEDROCK_NETHER);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:ore_alluvium",ORE_ALLUVIUM);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:ore_evaporite",ORE_EVAPORITE);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:ore_intrusion",ORE_INTRUSION);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:nether_ore_intrusion",NETHER_ORE_INTRUSION);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:end_stone_gen",END_STONE_GEN);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:nether_stone_gen",NETHER_STONE_GEN);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:overworld_stone_gen",OVERWORLD_STONE_GEN);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:soil_gen",SOIL_GEN);
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE,"rankine:snow_gen",SNOW_GEN);





    }

    // FEATURE CONFIGS
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

    public static final BaseTreeFeatureConfig TALL_BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LEAVES.get().getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.create(2, 1), FeatureSpread.create(0, 2), FeatureSpread.create(1, 1)),
            new StraightTrunkPlacer(10, 4, 0),
            new TwoLayerFeature(2, 0, 0)))
            .setDecorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.getDefaultState()))))
            .build();

    public static final BaseTreeFeatureConfig LARGE_BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LEAVES.get().getDefaultState()),
            new MegaPineFoliagePlacer(FeatureSpread.create(0, 0), FeatureSpread.create(0, 0), FeatureSpread.create(3, 6)),
            new GiantTrunkPlacer(9, 4, 12),
            new TwoLayerFeature(1, 1, 2)))
            .setDecorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.getDefaultState()))))
            .build();

    public static final BaseTreeFeatureConfig EASTERN_HEMLOCK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.EASTERN_HEMLOCK_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.EASTERN_HEMLOCK_LEAVES.get().getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.create(2, 1), FeatureSpread.create(0, 2), FeatureSpread.create(1, 1)),
            new StraightTrunkPlacer(4, 4, 1),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig SMALL_EASTERN_HEMLOCK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.EASTERN_HEMLOCK_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.EASTERN_HEMLOCK_LEAVES.get().getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.create(2, 0), FeatureSpread.create(0, 0), FeatureSpread.create(0, 0)),
            new StraightTrunkPlacer(2, 1, 0),
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

    public static final BaseTreeFeatureConfig COCONUT_PALM_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.COCONUT_PALM_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(RankineBlocks.COCONUT_PALM_LEAVES.get().getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.create(1, 0), FeatureSpread.create(0, 0)),
            new ForkyTrunkPlacer(7, 4, 2),
            new TwoLayerFeature(1, 0, 2)))
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

    // BASE FEATURE/S
    public static final Feature<RankineOreFeatureConfig> RANKINE_ORE = new RankineOreFeature(RankineOreFeatureConfig.CODEC);
    public static final Feature<MeteoriteFeatureConfig> METEORITE_FEATURE = new MeteoriteFeature(MeteoriteFeatureConfig.CODEC);
    public static final Feature<NoFeatureConfig> END_METEORITE_FEATURE = new EndMeteoriteFeature(NoFeatureConfig.CODEC);
    public static final Feature<NoFeatureConfig> ANTIMATTER_BLOB_FEATURE = new AntimatterFeature(NoFeatureConfig.CODEC);
    public static final Feature<NoFeatureConfig> FUMAROLE_FEATURE = new FumaroleFeature(NoFeatureConfig.CODEC);
    public static final Feature<ReplacerFeatureConfig> FLAT_BEDROCK_FEATURE = new FlatBedrockFeature(ReplacerFeatureConfig.CODEC);
    public static final Feature<ReplacerFeatureConfig> INTRUSION = new IntrusionFeature(ReplacerFeatureConfig.CODEC);
    public static final Feature<ReplacerFeatureConfig> NETHER_INTRUSION = new NetherIntrusionFeature(ReplacerFeatureConfig.CODEC);
    public static final Feature<NoFeatureConfig> END_STONE_REPLACER = new EndStoneReplacerFeature(NoFeatureConfig.CODEC);
    public static final Feature<NoFeatureConfig> NETHER_STONE_REPLACER = new NetherStoneReplacerFeature(NoFeatureConfig.CODEC);
    public static final Feature<NoFeatureConfig> STONE_REPLACER = new StoneReplacerFeature(NoFeatureConfig.CODEC);
    public static final Feature<NoFeatureConfig> SOIL_REPLACER = new SoilReplacerFeature(NoFeatureConfig.CODEC);
    public static final Feature<NoFeatureConfig> SNOW_REPLACER = new SnowyPeaksFeature(NoFeatureConfig.CODEC);

    // BASE PLACEMENTS
    public static final Placement<NoPlacementConfig> REPLACER_PLACEMENT = new ReplacerPlacement(NoPlacementConfig.CODEC);
    public static final Placement<ChanceConfig> INTRUSION_PLACEMENT = new IntrusionPlacement(ChanceConfig.CODEC);

    // LOCAL_MODIFICATIONS
    public static final ConfiguredFeature<?, ?> METEORITE = METEORITE_FEATURE.withConfiguration(new MeteoriteFeatureConfig(RankineBlocks.METEORITE.get().getDefaultState(), 1))
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(WGConfig.MISC.METEORITE_CHANCE.get())));
    public static final ConfiguredFeature<?, ?> END_METEORITE = END_METEORITE_FEATURE.withConfiguration(new NoFeatureConfig());
    public static final ConfiguredFeature<?, ?> ANTIMATTER_BLOB = ANTIMATTER_BLOB_FEATURE.withConfiguration(new NoFeatureConfig());
    public static final ConfiguredFeature<?, ?> FUMAROLE = FUMAROLE_FEATURE.withConfiguration(new NoFeatureConfig())
            .withPlacement(Placement.CHANCE.configure(new ChanceConfig(40)));

    //public static final ConfiguredFeature<?, ?> ANIMAL_SPAWNER = new AnimalSpawnerFeature(NoFeatureConfig.CODEC).withConfiguration(new NoFeatureConfig())
      //      .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));

    // VEGETAL_DECORATION
    public static final ConfiguredFeature<?, ?> ELDERBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(ELDERBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> POKEBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(POKEBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> SNOWBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(SNOWBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> BLUEBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(BLUEBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> RASPBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(RASPBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> BLACKBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(BLACKBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> CRANBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(CRANBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> STRAWBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(STRAWBERRY_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> PINEAPPLE_BUSH = Feature.RANDOM_PATCH.withConfiguration(PINEAPPLE_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> BANANA_YUCCA_BUSH = Feature.RANDOM_PATCH.withConfiguration(BANANA_YUCCA_BUSH_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> ALOE_PLANT = Feature.RANDOM_PATCH.withConfiguration(ALOE_PLANT_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> GOLDENROD_PATCH = Feature.RANDOM_PATCH.withConfiguration(GOLDENROD_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> RED_LILY_PATCH = Feature.RANDOM_PATCH.withConfiguration(RED_LILY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> WHITE_LILY_PATCH = Feature.RANDOM_PATCH.withConfiguration(WHITE_LILY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> ORANGE_LILY_PATCH = Feature.RANDOM_PATCH.withConfiguration(ORANGE_LILY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> PURPLE_MORNING_GLORY_PATCH = Feature.RANDOM_PATCH.withConfiguration(PURPLE_MORNING_GLORY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> BLUE_MORNING_GLORY_PATCH= Feature.RANDOM_PATCH.withConfiguration(BLUE_MORNING_GLORY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);
    public static final ConfiguredFeature<?, ?> BLACK_MORNING_GLORY_PATCH = Feature.RANDOM_PATCH.withConfiguration(BLACK_MORNING_GLORY_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT);


    public static final ConfiguredFeature<?, ?> YELLOW_BIRCH_TREE = Feature.TREE.withConfiguration(YELLOW_BIRCH_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> BLACK_BIRCH_TREE = Feature.TREE.withConfiguration(BLACK_BIRCH_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> EASTERN_HEMLOCK_TREE = Feature.TREE.withConfiguration(LARGE_EASTERN_HEMLOCK_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> CEDAR_TREE = Feature.TREE.withConfiguration(CEDAR_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> COCONUT_PALM_TREE = Feature.TREE.withConfiguration(COCONUT_PALM_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> PINYON_PINE_TREE = Feature.TREE.withConfiguration(PINYON_PINE_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> BALSAM_FIR_TREE = Feature.TREE.withConfiguration(BALSAM_FIR_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> DEAD_BALSAM_FIR_TREE = Feature.TREE.withConfiguration(DEAD_BALSAM_FIR_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> MAGNOLIA_TREE = Feature.TREE.withConfiguration(MAGNOLIA_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(3)));
    public static final ConfiguredFeature<?, ?> JUNIPER_TREE = Feature.TREE.withConfiguration(JUNIPER_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> ERYTHRINA_TREE = Feature.TREE.withConfiguration(ERYTHRINA_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> MAPLE_TREE = Feature.TREE.withConfiguration(MAPLE_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(11)));
    public static final ConfiguredFeature<?, ?> BLACK_WALNUT_TREE = Feature.TREE.withConfiguration(BLACK_WALNUT_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> CORK_OAK_TREE = Feature.TREE.withConfiguration(CORK_OAK_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(3)));
    public static final ConfiguredFeature<?, ?> SHARINGA_TREE = Feature.TREE.withConfiguration(SHARINGA_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> CINNAMON_TREE = Feature.TREE.withConfiguration(CINNAMON_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1)));



    // UNDERGROUND_ORES


    public static final ConfiguredFeature<?, ?> FLAT_BEDROCK = FLAT_BEDROCK_FEATURE.withConfiguration(
            new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), Blocks.BEDROCK.getDefaultState(), 0, WGConfig.MISC.BEDROCK_LAYERS.get())).withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?, ?> FLAT_BEDROCK_NETHER = FLAT_BEDROCK_FEATURE.withConfiguration(
            new ReplacerFeatureConfig(Blocks.NETHERRACK.getDefaultState(), Blocks.BEDROCK.getDefaultState(), 0, WGConfig.MISC.BEDROCK_LAYERS.get())).withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?, ?> ORE_ALLUVIUM = Feature.DISK.withConfiguration(new SphereReplaceConfig(RankineBlocks.ALLUVIUM.get().getDefaultState(), FeatureSpread.create(1, 2), 1,
            Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);
    public static final ConfiguredFeature<?, ?> ORE_EVAPORITE = Feature.DISK.withConfiguration(new SphereReplaceConfig(RankineBlocks.EVAPORITE.get().getDefaultState(), FeatureSpread.create(1, 1), 1,
            Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);

    public static final ConfiguredFeature<?, ?> ORE_INTRUSION = INTRUSION.withConfiguration(
            new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), Blocks.AIR.getDefaultState(), 1, 256)).withPlacement(INTRUSION_PLACEMENT.configure(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> NETHER_ORE_INTRUSION = NETHER_INTRUSION.withConfiguration(
            new ReplacerFeatureConfig(Blocks.NETHERRACK.getDefaultState(), Blocks.AIR.getDefaultState(), 1, 256)).withPlacement(INTRUSION_PLACEMENT.configure(new ChanceConfig(2)));

    public static final ConfiguredFeature<?, ?> END_STONE_GEN = END_STONE_REPLACER.withConfiguration(new NoFeatureConfig())
            .withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?, ?> NETHER_STONE_GEN = NETHER_STONE_REPLACER.withConfiguration(new NoFeatureConfig())
            .withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?, ?> OVERWORLD_STONE_GEN = STONE_REPLACER.withConfiguration(new NoFeatureConfig())
            .withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?, ?> SOIL_GEN = SOIL_REPLACER.withConfiguration(new NoFeatureConfig())
            .withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?, ?> SNOW_GEN = SNOW_REPLACER.withConfiguration(new NoFeatureConfig())
            .withPlacement(REPLACER_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));


}

