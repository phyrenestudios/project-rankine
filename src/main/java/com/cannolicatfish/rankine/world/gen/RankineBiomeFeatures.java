package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.RankineCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.init.RankinePlacements;
import com.cannolicatfish.rankine.world.gen.feature.MeteoriteFeatureConfig;
import com.cannolicatfish.rankine.world.gen.feature.ReplacerFeatureConfig;
import com.cannolicatfish.rankine.world.gen.placers.CropsBlockPlacer;
import com.cannolicatfish.rankine.world.gen.placers.DoubleCropsBlockPlacer;
import com.cannolicatfish.rankine.world.gen.placers.RankineDoublePlantPlacer;
import com.cannolicatfish.rankine.world.gen.placers.TripleCropsBlockPlacer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import java.util.OptionalInt;
import java.util.Set;

public class RankineBiomeFeatures {

    public static final BaseTreeFeatureConfig HONEY_LOCUST_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.HONEY_LOCUST_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.HONEY_LOCUST_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(8, 3, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig WEEPING_WILLOW_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.WEEPING_WILLOW_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.WEEPING_WILLOW_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(8, 3, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig JUNIPER_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.JUNIPER_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.JUNIPER_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(3, 1, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig PINYON_PINE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.PINYON_PINE_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.PINYON_PINE_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(6, 4, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig RED_CEDAR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.CEDAR_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.CEDAR_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(8, 5, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig MAGNOLIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.MAGNOLIA_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.MAGNOLIA_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(4, 2, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig ERYTHRINA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.ERYTHRINA_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.ERYTHRINA_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(6, 3, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig BLACK_WALNUT_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BLACK_WALNUT_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.BLACK_WALNUT_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(10, 5, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig EASTERN_HEMLOCK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.EASTERN_HEMLOCK_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.EASTERN_HEMLOCK_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(15, 10, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig WESTERN_HEMLOCK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.WESTERN_HEMLOCK_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.WESTERN_HEMLOCK_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(25, 10, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(10, 10, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig SHORT_BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.BALSAM_FIR_LEAVES.get().defaultBlockState()), null, new StraightTrunkPlacer(5, 4, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig COCONUT_PALM_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.COCONUT_PALM_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.COCONUT_PALM_LEAVES.get().defaultBlockState()),null, new StraightTrunkPlacer(7, 10, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig RED_BIRCH_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.RED_BIRCH_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.RED_BIRCH_LEAVES.get().defaultBlockState()),null, new StraightTrunkPlacer(4, 3, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig BLACK_BIRCH_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BLACK_BIRCH_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.BLACK_BIRCH_LEAVES.get().defaultBlockState()),null, new StraightTrunkPlacer(4, 6, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig YELLOW_BIRCH_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.YELLOW_BIRCH_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(RankineBlocks.YELLOW_BIRCH_LEAVES.get().defaultBlockState()),null, new StraightTrunkPlacer(6, 6, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig BIRCH_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BIRCH_LOG.defaultBlockState()), new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.defaultBlockState()),null, new StraightTrunkPlacer(6, 6, 0), null)).ignoreVines().build();
    public static final BaseTreeFeatureConfig PETRIFIED_CHORUS_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.PETRIFIED_CHORUS_LOG.get().defaultBlockState()), null,null, null, null)).ignoreVines().build();



    public static final BaseTreeFeatureConfig CINNAMON_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.CINNAMON_LOG.get().defaultBlockState()),
            new SimpleBlockStateProvider(RankineBlocks.CINNAMON_LEAVES.get().defaultBlockState()),
            new AcaciaFoliagePlacer(FeatureSpread.of(1, 0), FeatureSpread.of(0, 0)),
            new ForkyTrunkPlacer(5, 2, 1),
            new TwoLayerFeature(1, 0, 2)))
            .ignoreVines()
            .build();
    public static final BaseTreeFeatureConfig MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.MAPLE_LOG.get().defaultBlockState()),
            new SimpleBlockStateProvider(RankineBlocks.MAPLE_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(FeatureSpread.of(3, 0), FeatureSpread.of(4, 0), 4),
            new FancyTrunkPlacer(8, 4, 0),
            new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))))
            .ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING)
            .build();

    public static final BaseTreeFeatureConfig CORK_OAK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.CORK_OAK_LOG.get().defaultBlockState()),
            new SimpleBlockStateProvider(RankineBlocks.CORK_OAK_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(FeatureSpread.of(3, 0), FeatureSpread.of(2, 0), 1),
            new FancyTrunkPlacer(6, 2, 5),
            new TwoLayerFeature(2, 0, 3, OptionalInt.of(4))))
            .ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING)
            .build();

    public static final BaseTreeFeatureConfig SHARINGA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(RankineBlocks.SHARINGA_LOG.get().defaultBlockState()),
            new SimpleBlockStateProvider(RankineBlocks.SHARINGA_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(FeatureSpread.of(2, 1), FeatureSpread.of(2, 0), 3),
            new FancyTrunkPlacer(8, 2, 3),
            new TwoLayerFeature(1, 0, 0, OptionalInt.of(4))))
            .ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING)
            .build();

    //public static final BYGAbstractTreeFeature<BYGTreeConfig> BAOBAB_TREE3 = createFeature("baobab_tree3", new BaobabTree3(BYGTreeConfig.CODEC.stable()));

    public static final BlockClusterFeatureConfig ELDERBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.ELDERBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig POKEBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.POKEBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig SNOWBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.SNOWBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig BLUEBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig RASPBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.RASPBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig BLACKBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BLACKBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig CRANBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.CRANBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), new RankineDoublePlantPlacer())).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig STRAWBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.STRAWBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig PINEAPPLE_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.PINEAPPLE_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND)).noProjection().build();
    public static final BlockClusterFeatureConfig BANANA_YUCCA_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BANANA_YUCCA_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND)).noProjection().build();
    public static final BlockClusterFeatureConfig ALOE_PLANT_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.ALOE_PLANT.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND)).noProjection().build();
    public static final BlockClusterFeatureConfig GOLDENROD_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.GOLDENROD.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig RED_LILY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.RED_LILY.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig WHITE_LILY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.WHITE_LILY.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig ORANGE_LILY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.ORANGE_LILY.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig PURPLE_MORNING_GLORY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.PURPLE_MORNING_GLORY.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig BLACK_MORNING_GLORY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BLACK_MORNING_GLORY.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();
    public static final BlockClusterFeatureConfig BLUE_MORNING_GLORY_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BLUE_MORNING_GLORY.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT)).noProjection().build();


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
        CFregister("cobble_patch",COBBLE_PATCH);
        CFregister("wall_mushrooms",MUSHROOMS);
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
        CFregister("flat_bedrock",FLAT_BEDROCK);
        CFregister("flat_bedrock_nether",FLAT_BEDROCK_NETHER);
        CFregister("ore_alluvium",ORE_ALLUVIUM);
        CFregister("ore_evaporite",ORE_EVAPORITE);
        CFregister("ore_intrusion", INTRUSION_FEATURE);
        CFregister("rock_gen", WORLD_REPLACER_GEN);
        CFregister("soil_gen", POST_WORLD_REPLACER_GEN);
        CFregister("tinder_conk_mushroom",TINDER_CONK_MUSHROOM);
        CFregister("lions_mane_mushroom",LIONS_MANE_MUSHROOM);
        CFregister("turkey_tail_mushroom",TURKEY_TAIL_MUSHROOM);
        CFregister("sulfur_shelf_mushroom",SULFUR_SHELF_MUSHROOM);
        CFregister("honey_mushroom",HONEY_MUSHROOM);
        CFregister("artist_conk_mushroom",ARTIST_CONK_MUSHROOM);
        CFregister("oyster_mushroom",OYSTER_MUSHROOM);
        CFregister("cinnabar_polypore_mushroom",CINNABAR_POLYPORE_MUSHROOM);
        CFregister("oat_plant_patch",OAT_PLANT_PATCH);
        CFregister("rice_plant_patch",RICE_PLANT_PATCH);
        CFregister("millet_plant_patch",MILLET_PLANT_PATCH);
        CFregister("rye_plant_patch",RYE_PLANT_PATCH);
        CFregister("barley_plant_patch",BARLEY_PLANT_PATCH);
        CFregister("cotton_plant_patch",COTTON_PLANT_PATCH);
        CFregister("asparagus_plant_patch",ASPARAGUS_PLANT_PATCH);
        CFregister("corn_plant_patch",CORN_PLANT_PATCH);
        CFregister("sorghum_plant_patch",SORGHUM_PLANT_PATCH);
        CFregister("jute_plant_patch",JUTE_PLANT_PATCH);

    }


    // LOCAL_MODIFICATIONS
    public static final ConfiguredFeature<?, ?> METEORITE = RankineFeatures.METEORITE_FEATURE.get().configured(new MeteoriteFeatureConfig(RankineBlocks.METEORITE.get().defaultBlockState(), 1))
            .decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.CHANCE.configured(new ChanceConfig(Config.MISC_WORLDGEN.METEORITE_CHANCE.get())));
    public static final ConfiguredFeature<?, ?> DISK_WHITE_SAND = RankineFeatures.LAND_DISK.get().configured(new SphereReplaceConfig(RankineBlocks.WHITE_SAND.get().defaultBlockState(), FeatureSpread.of(5, 4), 4, ImmutableList.of(Blocks.SAND.defaultBlockState()))).decorated(Features.Placements.HEIGHTMAP_SQUARE)
            .decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.CHANCE.configured(new ChanceConfig(3)));
    public static final ConfiguredFeature<?, ?> DISK_BLACK_SAND = RankineFeatures.LAND_DISK.get().configured(new SphereReplaceConfig(RankineBlocks.BLACK_SAND.get().defaultBlockState(), FeatureSpread.of(3, 4), 4, ImmutableList.of(Blocks.SOUL_SOIL.defaultBlockState(),Blocks.SOUL_SAND.defaultBlockState())))
            .decorated(Placement.COUNT_MULTILAYER.configured(new FeatureSpreadConfig(1)).decorated(Placement.CHANCE.configured(new ChanceConfig(3))));
    public static final ConfiguredFeature<?, ?> END_METEORITE = RankineFeatures.END_METEORITE_FEATURE.get().configured(new NoFeatureConfig());
    public static final ConfiguredFeature<?, ?> ANTIMATTER_BLOB = RankineFeatures.ANTIMATTER_BLOB_FEATURE.get().configured(new NoFeatureConfig());
    public static final ConfiguredFeature<?, ?> FUMAROLE = RankineFeatures.FUMAROLE_FEATURE.get().configured(new NoFeatureConfig())
            .decorated(Placement.CHANCE.configured(new ChanceConfig(40)));
    public static final ConfiguredFeature<?, ?> COLUMN = RankineFeatures.COLUMN_FEATURE.get().configured(new NoFeatureConfig())
            .decorated(Placement.CHANCE.configured(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> MUSHROOMS = RankineFeatures.WALL_MUSHROOMS.get().configured(new NoFeatureConfig())
            .decorated(Placement.CHANCE.configured(new ChanceConfig(3)));

    // VEGETAL_DECORATION
    public static final ConfiguredFeature<?, ?> ELDERBERRY_BUSH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.ELDERBERRY_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> POKEBERRY_BUSH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.POKEBERRY_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> SNOWBERRY_BUSH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.SNOWBERRY_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> BLUEBERRY_BUSH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.BLUEBERRY_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> RASPBERRY_BUSH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.RASPBERRY_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> BLACKBERRY_BUSH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.BLACKBERRY_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> CRANBERRY_BUSH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.CRANBERRY_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> STRAWBERRY_BUSH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.STRAWBERRY_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> PINEAPPLE_BUSH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.PINEAPPLE_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> BANANA_YUCCA_BUSH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.BANANA_YUCCA_BUSH_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> ALOE_PLANT = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.ALOE_PLANT_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> GOLDENROD_PATCH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.GOLDENROD_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> RED_LILY_PATCH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.RED_LILY_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> WHITE_LILY_PATCH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.WHITE_LILY_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> ORANGE_LILY_PATCH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.ORANGE_LILY_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> PURPLE_MORNING_GLORY_PATCH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.PURPLE_MORNING_GLORY_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> BLUE_MORNING_GLORY_PATCH= Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.BLUE_MORNING_GLORY_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> BLACK_MORNING_GLORY_PATCH = Feature.RANDOM_PATCH.configured(RankineBiomeFeatures.BLACK_MORNING_GLORY_PATCH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);


    public static final ConfiguredFeature<?, ?> TINDER_CONK_MUSHROOM = RankineFeatures.TINDER_CONK_FEATURE.get().configured(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(RankineBlocks.TINDER_CONK_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> LIONS_MANE_MUSHROOM = RankineFeatures.LIONS_MANE_FEATURE.get().configured(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(RankineBlocks.TINDER_CONK_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> TURKEY_TAIL_MUSHROOM = RankineFeatures.TURKEY_TAIL_FEATURE.get().configured(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(RankineBlocks.TURKEY_TAIL_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> HONEY_MUSHROOM = RankineFeatures.HONEY_FEATURE.get().configured(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(RankineBlocks.HONEY_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> SULFUR_SHELF_MUSHROOM = RankineFeatures.SULFUR_SHELF_FEATURE.get().configured(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(RankineBlocks.SULFUR_SHELF_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> CINNABAR_POLYPORE_MUSHROOM = RankineFeatures.CINNABAR_POLYPORE_FEATURE.get().configured(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(RankineBlocks.CINNABAR_POLYPORE_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> ARTIST_CONK_MUSHROOM = RankineFeatures.ARTIST_CONK_FEATURE.get().configured(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(RankineBlocks.ARTIST_CONK_MUSHROOM_BLOCK.get().defaultBlockState())));
    public static final ConfiguredFeature<?, ?> OYSTER_MUSHROOM = RankineFeatures.OYSTER_FEATURE.get().configured(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(RankineBlocks.OYSTER_MUSHROOM_BLOCK.get().defaultBlockState())));

    public static Set<Block> blks = ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT);
    public static final ConfiguredFeature<?, ?> OAT_PLANT_PATCH = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.OAT_PLANT.get().defaultBlockState().setValue(RankineCropsBlock.AGE,7)), CropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> MILLET_PLANT_PATCH = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.MILLET_PLANT.get().defaultBlockState().setValue(RankineCropsBlock.AGE,7)), CropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> RICE_PLANT_PATCH = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.RICE_PLANT.get().defaultBlockState().setValue(RankineCropsBlock.AGE,7)), CropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> RYE_PLANT_PATCH = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.RYE_PLANT.get().defaultBlockState().setValue(DoubleCropsBlock.AGE,7)), DoubleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> BARLEY_PLANT_PATCH = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.BARLEY_PLANT.get().defaultBlockState().setValue(DoubleCropsBlock.AGE,7)), DoubleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> COTTON_PLANT_PATCH = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.COTTON_PLANT.get().defaultBlockState().setValue(DoubleCropsBlock.AGE,7)), DoubleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> ASPARAGUS_PLANT_PATCH = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.ASPARAGUS_PLANT.get().defaultBlockState().setValue(DoubleCropsBlock.AGE,7)), DoubleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> CORN_PLANT_PATCH = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.CORN_PLANT.get().defaultBlockState().setValue(TripleCropsBlock.AGE,7)), TripleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> SORGHUM_PLANT_PATCH = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.SORGHUM_PLANT.get().defaultBlockState().setValue(TripleCropsBlock.AGE,7)), TripleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);
    public static final ConfiguredFeature<?, ?> JUTE_PLANT_PATCH = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.JUTE_PLANT.get().defaultBlockState().setValue(TripleCropsBlock.AGE,7)), TripleCropsBlockPlacer.PLACER)).tries(20).whitelist(blks).noProjection().build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);

    public static final ConfiguredFeature<?, ?> COBBLE_PATCH = RankineFeatures.COBBLE_PATCH.get().configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RankineBlocks.PEGMATITE_COBBLE.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(30).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.DIRT, Blocks.MYCELIUM, Blocks.SAND, Blocks.STONE)).noProjection().build())
            .decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.CHANCE.configured(new ChanceConfig(1)));



    // UNDERGROUND_ORES
    public static final ConfiguredFeature<?, ?> FLAT_BEDROCK = RankineFeatures.FLAT_BEDROCK_FEATURE.get().configured(
            new ReplacerFeatureConfig(Blocks.STONE.defaultBlockState(), Blocks.BEDROCK.defaultBlockState(), 0, Config.MISC_WORLDGEN.BEDROCK_LAYERS.get())).decorated(RankinePlacements.REPLACER_PLACEMENT.get().configured(IPlacementConfig.NONE));
    public static final ConfiguredFeature<?, ?> FLAT_BEDROCK_NETHER = RankineFeatures.FLAT_BEDROCK_FEATURE.get().configured(
            new ReplacerFeatureConfig(Blocks.NETHERRACK.defaultBlockState(), Blocks.BEDROCK.defaultBlockState(), 0, Config.MISC_WORLDGEN.BEDROCK_LAYERS.get())).decorated(RankinePlacements.REPLACER_PLACEMENT.get().configured(IPlacementConfig.NONE));
    public static final ConfiguredFeature<?, ?> ORE_ALLUVIUM = Feature.DISK.configured(new SphereReplaceConfig(RankineBlocks.ALLUVIUM.get().defaultBlockState(), FeatureSpread.of(1, 2), 1,
            Lists.newArrayList(Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState(), Blocks.SAND.defaultBlockState(), Blocks.GRAVEL.defaultBlockState()))).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE);
    public static final ConfiguredFeature<?, ?> ORE_EVAPORITE = Feature.DISK.configured(new SphereReplaceConfig(RankineBlocks.EVAPORITE.get().defaultBlockState(), FeatureSpread.of(1, 1), 1,
            Lists.newArrayList(Blocks.STONE.defaultBlockState(), Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState(), Blocks.SAND.defaultBlockState(), Blocks.GRAVEL.defaultBlockState()))).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE);

    public static final ConfiguredFeature<?, ?> INTRUSION_FEATURE = RankineFeatures.INTRUSION.get().configured(new NoFeatureConfig())
            .decorated(RankinePlacements.INTRUSION_PLACEMENT.get().configured(new ChanceConfig(1)));
    public static final ConfiguredFeature<?, ?> WORLD_REPLACER_GEN = RankineFeatures.WORLD_REPLACER_FEATURE.get().configured(new NoFeatureConfig())
            .decorated(RankinePlacements.REPLACER_PLACEMENT.get().configured(IPlacementConfig.NONE));
    public static final ConfiguredFeature<?, ?> POST_WORLD_REPLACER_GEN = RankineFeatures.POST_WORLD_REPLACER_FEATURE.get().configured(new NoFeatureConfig())
            .decorated(RankinePlacements.REPLACER_PLACEMENT.get().configured(IPlacementConfig.NONE));

}
