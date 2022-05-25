package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.world.gen.MeteoriteFeatureConfig;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.OptionalInt;

public class RankineConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ProjectRankine.MODID);

    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_WORLD_REPLACER = CONFIGURED_FEATURES.register("world_replacer", () -> new ConfiguredFeature<>(RankineFeatures.WORLD_REPLACER.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_POST_WORLD_REPLACER = CONFIGURED_FEATURES.register("post_world_replacer", () -> new ConfiguredFeature<>(RankineFeatures.POST_WORLD_REPLACER.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_FLAT_BEDROCK = CONFIGURED_FEATURES.register("flat_bedrock", () -> new ConfiguredFeature<>(RankineFeatures.FLAT_BEDROCK_FEATURE.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_FUMAROLE = CONFIGURED_FEATURES.register("fumarole", () -> new ConfiguredFeature<>(RankineFeatures.FUMAROLE.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_INTRUSION = CONFIGURED_FEATURES.register("intrusion", () -> new ConfiguredFeature<>(RankineFeatures.INTRUSION.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_METEORITE = CONFIGURED_FEATURES.register("meteorite", () -> new ConfiguredFeature<>(RankineFeatures.METEORITE.get(), new MeteoriteFeatureConfig(RankineBlocks.METEORITE.get().defaultBlockState(), 1)));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_END_METEORITE = CONFIGURED_FEATURES.register("end_meteorite", () -> new ConfiguredFeature<>(RankineFeatures.END_METEORITE_FEATURE.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_ANTIMATTER_BLOB = CONFIGURED_FEATURES.register("antimatter_blob", () -> new ConfiguredFeature<>(RankineFeatures.ANTIMATTER_BLOB_FEATURE.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_WALL_MUSHROOMS = CONFIGURED_FEATURES.register("wall_mushrooms", () -> new ConfiguredFeature<>(RankineFeatures.WALL_MUSHROOMS.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_TINDER_CONK_MUSHROOM = CONFIGURED_FEATURES.register("tinder_conk_mushroom", () -> new ConfiguredFeature<>(RankineFeatures.TINDER_CONK.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.TINDER_CONK_MUSHROOM_BLOCK.get().defaultBlockState()))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_LIONS_MANE_MUSHROOM = CONFIGURED_FEATURES.register("lions_mane_mushroom", () -> new ConfiguredFeature<>(RankineFeatures.LIONS_MANE.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.LIONS_MANE_MUSHROOM_BLOCK.get().defaultBlockState()))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_TURKEY_TAIL_MUSHROOM = CONFIGURED_FEATURES.register("turkey_tail_mushroom", () -> new ConfiguredFeature<>(RankineFeatures.TURKEY_TAIL.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.TURKEY_TAIL_MUSHROOM_BLOCK.get().defaultBlockState()))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_HONEY_MUSHROOM = CONFIGURED_FEATURES.register("honey_mushroom", () -> new ConfiguredFeature<>(RankineFeatures.HONEY.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.HONEY_MUSHROOM_BLOCK.get().defaultBlockState()))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_SULFUR_SHELF_MUSHROOM = CONFIGURED_FEATURES.register("sulfur_shelf_mushroom", () -> new ConfiguredFeature<>(RankineFeatures.SULFUR_SHELF.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.SULFUR_SHELF_MUSHROOM_BLOCK.get().defaultBlockState()))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_CINNABAR_POLYPORE_MUSHROOM = CONFIGURED_FEATURES.register("cinnabar_polypore_mushroom", () -> new ConfiguredFeature<>(RankineFeatures.CINNABAR_POLYPORE.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.CINNABAR_POLYPORE_MUSHROOM_BLOCK.get().defaultBlockState()))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_ARTIST_CONK_MUSHROOM = CONFIGURED_FEATURES.register("artist_conk_mushroom", () -> new ConfiguredFeature<>(RankineFeatures.ARTIST_CONK.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.ARTIST_CONK_MUSHROOM_BLOCK.get().defaultBlockState()))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_OYSTER_MUSHROOM = CONFIGURED_FEATURES.register("oyster_mushroom", () -> new ConfiguredFeature<>(RankineFeatures.OYSTER.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.OYSTER_MUSHROOM_BLOCK.get().defaultBlockState()))));

    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_LILIES = CONFIGURED_FEATURES.register("patch_lilies", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.RED_LILY.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.ORANGE_LILY.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.WHITE_LILY.get()))))))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_MORNING_GLORIES = CONFIGURED_FEATURES.register("patch_morning_glories", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.PURPLE_MORNING_GLORY.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BLUE_MORNING_GLORY.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BLACK_MORNING_GLORY.get()))))))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_SAVANNA_FLOWERS = CONFIGURED_FEATURES.register("patch_savanna_flowers", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.GOLDENROD.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.VIOLET_BELLFLOWER.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.PINK_BELLFLOWER.get()))))))));

    //Berries
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_SAVANNA_PLANTS = CONFIGURED_FEATURES.register("patch_savanna_plants", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.ALOE_PLANT.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3))))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BANANA_YUCCA_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_PLAINS_PLANTS = CONFIGURED_FEATURES.register("patch_plains_plants", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(RankineFeatures.RANKINE_SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3))))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.STRAWBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_FOREST_PLANTS = CONFIGURED_FEATURES.register("patch_forest_plants", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(RankineFeatures.RANKINE_SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.ELDERBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3))))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(RankineFeatures.RANKINE_SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.POKEBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_TAIGA_PLANTS = CONFIGURED_FEATURES.register("patch_taiga_plants", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.RASPBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3))))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BLACKBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_MOUNTAIN_PLANTS = CONFIGURED_FEATURES.register("patch_mountain_plants", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.SNOWBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3))))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.STRAWBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_SWAMP_PLANTS = CONFIGURED_FEATURES.register("patch_swamp_plants", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(RankineFeatures.RANKINE_SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.CRANBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3))))));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_JUNGLE_PLANTS = CONFIGURED_FEATURES.register("patch_jungle_plants", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.PINEAPPLE_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3))))));

    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PATCH_COBBLES = CONFIGURED_FEATURES.register("patch_cobbles", () -> new ConfiguredFeature<>(RankineFeatures.PATCH_COBBLES.get(), new RandomPatchConfiguration(48, 7, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.PEGMATITE_COBBLE.get()))))));

    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_CEDAR_TREE = CONFIGURED_FEATURES.register("cedar_tree", () -> new ConfiguredFeature<>(RankineFeatures.CEDAR_TREE.get(), createRankineTree(RankineBlocks.CEDAR_LOG.get(), RankineBlocks.CEDAR_LEAVES.get(),8,5,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_EASTERN_HEMLOCK_TREE = CONFIGURED_FEATURES.register("eastern_hemlock_tree", () -> new ConfiguredFeature<>(RankineFeatures.EASTERN_HEMLOCK_TREE.get(), createRankineTree(RankineBlocks.EASTERN_HEMLOCK_LOG.get(), RankineBlocks.EASTERN_HEMLOCK_LEAVES.get(),15,10,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_WESTERN_HEMLOCK_TREE = CONFIGURED_FEATURES.register("western_hemlock_tree", () -> new ConfiguredFeature<>(RankineFeatures.WESTERN_HEMLOCK_TREE.get(), createRankineTree(RankineBlocks.WESTERN_HEMLOCK_LOG.get(), RankineBlocks.WESTERN_HEMLOCK_LEAVES.get(),25,10,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_BALSAM_FIR_TREE = CONFIGURED_FEATURES.register("balsam_fir_tree", () -> new ConfiguredFeature<>(RankineFeatures.BALSAM_FIR_TREE.get(), createRankineTree(RankineBlocks.BALSAM_FIR_LOG.get(), RankineBlocks.BALSAM_FIR_LEAVES.get(),10,10,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_SHORT_BALSAM_FIR_TREE = CONFIGURED_FEATURES.register("short_balsam_fir_tree", () -> new ConfiguredFeature<>(RankineFeatures.BALSAM_FIR_TREE.get(), createRankineTree(RankineBlocks.BALSAM_FIR_LOG.get(), RankineBlocks.BALSAM_FIR_LEAVES.get(),5,4,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_COCONUT_PALM_TREE = CONFIGURED_FEATURES.register("coconut_palm_tree", () -> new ConfiguredFeature<>(RankineFeatures.COCONUT_PALM_TREE.get(), createRankineTree(RankineBlocks.COCONUT_PALM_LOG.get(), RankineBlocks.COCONUT_PALM_LEAVES.get(),7,10,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_MODIFIED_BIRCH_TREE = CONFIGURED_FEATURES.register("modified_birch_tree", () -> new ConfiguredFeature<>(RankineFeatures.BIRCH_TREE.get(), createRankineTree(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES,6,6,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_BLACK_BIRCH_TREE = CONFIGURED_FEATURES.register("black_birch_tree", () -> new ConfiguredFeature<>(RankineFeatures.BIRCH_TREE.get(), createRankineTree(RankineBlocks.BLACK_BIRCH_LOG.get(), RankineBlocks.BLACK_BIRCH_LEAVES.get(),4,6,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_YELLOW_BIRCH_TREE = CONFIGURED_FEATURES.register("yellow_birch_tree", () -> new ConfiguredFeature<>(RankineFeatures.BIRCH_TREE.get(), createRankineTree(RankineBlocks.YELLOW_BIRCH_LOG.get(), RankineBlocks.YELLOW_BIRCH_LEAVES.get(),6,6,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_RED_BIRCH_TREE = CONFIGURED_FEATURES.register("red_birch_tree", () -> new ConfiguredFeature<>(RankineFeatures.BIRCH_TREE.get(), createRankineTree(RankineBlocks.RED_BIRCH_LOG.get(), RankineBlocks.RED_BIRCH_LEAVES.get(),4,3,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_MAGNOLIA_TREE = CONFIGURED_FEATURES.register("magnolia_tree", () -> new ConfiguredFeature<>(RankineFeatures.MAGNOLIA_TREE.get(), createRankineTree(RankineBlocks.MAGNOLIA_LOG.get(), RankineBlocks.MAGNOLIA_LEAVES.get(),4,2,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_ERYTHRINA_TREE = CONFIGURED_FEATURES.register("erythrina_tree", () -> new ConfiguredFeature<>(RankineFeatures.ERYTHRINA_TREE.get(), createRankineTree(RankineBlocks.ERYTHRINA_LOG.get(), RankineBlocks.ERYTHRINA_LEAVES.get(),6,3,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_HONEY_LOCUST_TREE = CONFIGURED_FEATURES.register("honey_locust_tree", () -> new ConfiguredFeature<>(RankineFeatures.HONEY_LOCUST_TREE.get(), createRankineTree(RankineBlocks.HONEY_LOCUST_LOG.get(), RankineBlocks.HONEY_LOCUST_LEAVES.get(),8,3,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_WEEPING_WILLOW_TREE = CONFIGURED_FEATURES.register("weeping_willow_tree", () -> new ConfiguredFeature<>(RankineFeatures.WEEPING_WILLOW_TREE.get(), createRankineTree(RankineBlocks.WEEPING_WILLOW_LOG.get(), RankineBlocks.WEEPING_WILLOW_LEAVES.get(),6,4,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_BLACK_WALNUT_TREE = CONFIGURED_FEATURES.register("black_walnut_tree", () -> new ConfiguredFeature<>(RankineFeatures.BLACK_WALNUT_TREE.get(), createRankineTree(RankineBlocks.BLACK_WALNUT_LOG.get(), RankineBlocks.BLACK_WALNUT_LEAVES.get(),10,5,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PINYON_PINE_TREE = CONFIGURED_FEATURES.register("pinyon_pine_tree", () -> new ConfiguredFeature<>(RankineFeatures.PINYON_PINE_TREE.get(), createRankineTree(RankineBlocks.PINYON_PINE_LOG.get(), RankineBlocks.PINYON_PINE_LEAVES.get(),6,4,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_JUNIPER_TREE = CONFIGURED_FEATURES.register("juniper_tree", () -> new ConfiguredFeature<>(RankineFeatures.JUNIPER_TREE.get(), createRankineTree(RankineBlocks.JUNIPER_LOG.get(), RankineBlocks.JUNIPER_LEAVES.get(),3,1,0).build()));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_PETRIFIED_CHORUS_TREE = CONFIGURED_FEATURES.register("petrified_chorus_tree", () -> new ConfiguredFeature<>(RankineFeatures.PETRIFIED_CHORUS_TREE.get(), createRankineTree(RankineBlocks.PETRIFIED_CHORUS_LOG.get(), Blocks.AIR,0,0,0).build()));

    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_MAPLE_TREE = CONFIGURED_FEATURES.register("maple_tree", () -> new ConfiguredFeature<>(RankineFeatures.MAPLE_TREE.get(), (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.MAPLE_LOG.get()), new FancyTrunkPlacer(8, 4, 0), BlockStateProvider.simple(RankineBlocks.MAPLE_LEAVES.get()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build())));

    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_SHARINGA_TREE = CONFIGURED_FEATURES.register("sharinga_tree", () -> new ConfiguredFeature<>(RankineFeatures.SHARINGA_TREE.get(), (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.SHARINGA_LOG.get()), new FancyTrunkPlacer(8, 4, 0), BlockStateProvider.simple(RankineBlocks.SHARINGA_LEAVES.get()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build())));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_CORK_OAK_TREE = CONFIGURED_FEATURES.register("cork_oak_tree", () -> new ConfiguredFeature<>(RankineFeatures.CORK_OAK_TREE.get(), (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.CORK_OAK_LOG.get()), new FancyTrunkPlacer(8, 4, 0), BlockStateProvider.simple(RankineBlocks.CORK_OAK_LEAVES.get()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build())));
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_CINNAMON_TREE = CONFIGURED_FEATURES.register("cinnamon_tree", () -> new ConfiguredFeature<>(RankineFeatures.CINNAMON_TREE.get(), (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.CINNAMON_LOG.get()), new ForkingTrunkPlacer(6, 2, 1), BlockStateProvider.simple(RankineBlocks.CINNAMON_LEAVES.get()), new AcaciaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build()));


    private static TreeConfiguration.TreeConfigurationBuilder createRankineTree(Block log, Block leaves, int height, int randA, int randB) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkPlacer(height, randA, randB), BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0), new TwoLayersFeatureSize(0, 0, 0));
    }

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

    // UNDERGROUND_ORES
//  public static final ConfiguredFeature<?, ?> ORE_ALLUVIUM =new ConfiguredFeature<>(Feature.DISK,new DiskConfiguration(RankineBlocks.ALLUVIUM.get().defaultBlockState(), UniformInt.of(1, 2), 1,
  //          Lists.newArrayList(Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState(), Blocks.SAND.defaultBlockState(), Blocks.GRAVEL.defaultBlockState())));


     */
}
