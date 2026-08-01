package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.world.gen.MeteoriteFeatureConfig;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

import java.util.OptionalInt;

/**
 * ConfiguredFeature keys + datagen bootstrap.
 * PORT-NOTE: configured features are a datapack registry in 1.21 — these are no longer
 * DeferredRegister'd; the bootstrap below is wired into DatapackBuiltinEntriesProvider by the
 * datagen provider (registry path names are identical to 1.18.2).
 */
public class RankineConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_WORLD_REPLACER = createKey("world_replacer");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_POST_WORLD_REPLACER = createKey("post_world_replacer");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_FLAT_BEDROCK = createKey("flat_bedrock");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_FUMAROLE = createKey("fumarole");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_INTRUSION = createKey("intrusion");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_METEORITE = createKey("meteorite");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_END_METEORITE = createKey("end_meteorite");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_ANTIMATTER_BLOB = createKey("antimatter_blob");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_WALL_MUSHROOMS = createKey("wall_mushrooms");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_TINDER_CONK_MUSHROOM = createKey("tinder_conk_mushroom");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_LIONS_MANE_MUSHROOM = createKey("lions_mane_mushroom");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_TURKEY_TAIL_MUSHROOM = createKey("turkey_tail_mushroom");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_HONEY_MUSHROOM = createKey("honey_mushroom");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_SULFUR_SHELF_MUSHROOM = createKey("sulfur_shelf_mushroom");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_CINNABAR_POLYPORE_MUSHROOM = createKey("cinnabar_polypore_mushroom");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_ARTIST_CONK_MUSHROOM = createKey("artist_conk_mushroom");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_OYSTER_MUSHROOM = createKey("oyster_mushroom");

    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_LILIES = createKey("patch_lilies");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_MORNING_GLORIES = createKey("patch_morning_glories");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_SAVANNA_FLOWERS = createKey("patch_savanna_flowers");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_SAVANNA_PLANTS = createKey("patch_savanna_plants");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_PLAINS_PLANTS = createKey("patch_plains_plants");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_FOREST_PLANTS = createKey("patch_forest_plants");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_TAIGA_PLANTS = createKey("patch_taiga_plants");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_MOUNTAIN_PLANTS = createKey("patch_mountain_plants");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_SWAMP_PLANTS = createKey("patch_swamp_plants");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_JUNGLE_PLANTS = createKey("patch_jungle_plants");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PATCH_COBBLES = createKey("patch_cobbles");

    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_CEDAR_TREE = createKey("cedar_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_EASTERN_HEMLOCK_TREE = createKey("eastern_hemlock_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_WESTERN_HEMLOCK_TREE = createKey("western_hemlock_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_BALSAM_FIR_TREE = createKey("balsam_fir_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_SHORT_BALSAM_FIR_TREE = createKey("short_balsam_fir_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_COCONUT_PALM_TREE = createKey("coconut_palm_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_MODIFIED_BIRCH_TREE = createKey("modified_birch_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_BLACK_BIRCH_TREE = createKey("black_birch_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_YELLOW_BIRCH_TREE = createKey("yellow_birch_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_RED_BIRCH_TREE = createKey("red_birch_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_MAGNOLIA_TREE = createKey("magnolia_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_ERYTHRINA_TREE = createKey("erythrina_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_HONEY_LOCUST_TREE = createKey("honey_locust_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_WEEPING_WILLOW_TREE = createKey("weeping_willow_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_BLACK_WALNUT_TREE = createKey("black_walnut_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PINYON_PINE_TREE = createKey("pinyon_pine_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_JUNIPER_TREE = createKey("juniper_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PETRIFIED_CHORUS_TREE = createKey("petrified_chorus_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_MAPLE_TREE = createKey("maple_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_SHARINGA_TREE = createKey("sharinga_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_CORK_OAK_TREE = createKey("cork_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_CINNAMON_TREE = createKey("cinnamon_tree");

    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_CEDAR_FALLEN_LOG = createKey("cedar_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_EASTERN_HEMLOCK_FALLEN_LOG = createKey("eastern_hemlock_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_WESTERN_HEMLOCK_FALLEN_LOG = createKey("western_hemlock_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_BALSAM_FIR_FALLEN_LOG = createKey("balsam_fir_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_COCONUT_PALM_FALLEN_LOG = createKey("coconut_palm_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_BIRCH_FALLEN_LOG = createKey("birch_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_OAK_FALLEN_LOG = createKey("oak_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_DARK_OAK_FALLEN_LOG = createKey("dark_oak_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_SPRUCE_FALLEN_LOG = createKey("spruce_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_JUNGLE_FALLEN_LOG = createKey("jungle_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_ACACIA_FALLEN_LOG = createKey("acacia_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_BLACK_BIRCH_FALLEN_LOG = createKey("black_birch_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_YELLOW_BIRCH_FALLEN_LOG = createKey("yellow_birch_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_RED_BIRCH_FALLEN_LOG = createKey("red_birch_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_MAGNOLIA_FALLEN_LOG = createKey("magnolia_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_ERYTHRINA_FALLEN_LOG = createKey("erythrina_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_HONEY_LOCUST_FALLEN_LOG = createKey("honey_locust_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_WEEPING_WILLOW_FALLEN_LOG = createKey("weeping_willow_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_BLACK_WALNUT_FALLEN_LOG = createKey("black_walnut_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PINYON_PINE_FALLEN_LOG = createKey("pinyon_pine_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_JUNIPER_FALLEN_LOG = createKey("juniper_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_PETRIFIED_CHORUS_FALLEN_LOG = createKey("petrified_chorus_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_MAPLE_FALLEN_LOG = createKey("maple_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_SHARINGA_FALLEN_LOG = createKey("sharinga_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_CORK_OAK_FALLEN_LOG = createKey("cork_oak_fallen_log");
    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_CINNAMON_FALLEN_LOG = createKey("cinnamon_fallen_log");

    private static ResourceKey<ConfiguredFeature<?,?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context) {
        register(context, CONFIGURED_WORLD_REPLACER, RankineFeatures.WORLD_REPLACER.get(), new NoneFeatureConfiguration());
        register(context, CONFIGURED_POST_WORLD_REPLACER, RankineFeatures.POST_WORLD_REPLACER.get(), new NoneFeatureConfiguration());
        register(context, CONFIGURED_FLAT_BEDROCK, RankineFeatures.FLAT_BEDROCK_FEATURE.get(), new NoneFeatureConfiguration());
        register(context, CONFIGURED_FUMAROLE, RankineFeatures.FUMAROLE.get(), new NoneFeatureConfiguration());
        register(context, CONFIGURED_INTRUSION, RankineFeatures.INTRUSION.get(), new NoneFeatureConfiguration());
        register(context, CONFIGURED_METEORITE, RankineFeatures.METEORITE.get(), new MeteoriteFeatureConfig(RankineBlocks.METEORITE.get().defaultBlockState(), 1));
        register(context, CONFIGURED_END_METEORITE, RankineFeatures.END_METEORITE_FEATURE.get(), new NoneFeatureConfiguration());
        register(context, CONFIGURED_ANTIMATTER_BLOB, RankineFeatures.ANTIMATTER_BLOB_FEATURE.get(), new NoneFeatureConfiguration());
        register(context, CONFIGURED_WALL_MUSHROOMS, RankineFeatures.WALL_MUSHROOMS.get(), new NoneFeatureConfiguration());
        register(context, CONFIGURED_TINDER_CONK_MUSHROOM, RankineFeatures.TINDER_CONK.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.TINDER_CONK_MUSHROOM_BLOCK.get().defaultBlockState())));
        register(context, CONFIGURED_LIONS_MANE_MUSHROOM, RankineFeatures.LIONS_MANE.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.LIONS_MANE_MUSHROOM_BLOCK.get().defaultBlockState())));
        register(context, CONFIGURED_TURKEY_TAIL_MUSHROOM, RankineFeatures.TURKEY_TAIL.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.TURKEY_TAIL_MUSHROOM_BLOCK.get().defaultBlockState())));
        register(context, CONFIGURED_HONEY_MUSHROOM, RankineFeatures.HONEY.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.HONEY_MUSHROOM_BLOCK.get().defaultBlockState())));
        register(context, CONFIGURED_SULFUR_SHELF_MUSHROOM, RankineFeatures.SULFUR_SHELF.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.SULFUR_SHELF_MUSHROOM_BLOCK.get().defaultBlockState())));
        register(context, CONFIGURED_CINNABAR_POLYPORE_MUSHROOM, RankineFeatures.CINNABAR_POLYPORE.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.CINNABAR_POLYPORE_MUSHROOM_BLOCK.get().defaultBlockState())));
        register(context, CONFIGURED_ARTIST_CONK_MUSHROOM, RankineFeatures.ARTIST_CONK.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.ARTIST_CONK_MUSHROOM_BLOCK.get().defaultBlockState())));
        register(context, CONFIGURED_OYSTER_MUSHROOM, RankineFeatures.OYSTER.get(), new BlockPileConfiguration(BlockStateProvider.simple(RankineBlocks.OYSTER_MUSHROOM_BLOCK.get().defaultBlockState())));

        register(context, CONFIGURED_PATCH_LILIES, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.RED_LILY.get()))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.ORANGE_LILY.get()))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.WHITE_LILY.get()))))))));
        register(context, CONFIGURED_PATCH_MORNING_GLORIES, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.PURPLE_MORNING_GLORY.get()))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BLUE_MORNING_GLORY.get()))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BLACK_MORNING_GLORY.get()))))))));
        register(context, CONFIGURED_PATCH_SAVANNA_FLOWERS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.GOLDENROD.get()))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.VIOLET_BELLFLOWER.get()))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.PINK_BELLFLOWER.get()))))))));

        //Berries
        register(context, CONFIGURED_PATCH_SAVANNA_PLANTS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.ALOE_PLANT.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BANANA_YUCCA_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))))));
        register(context, CONFIGURED_PATCH_PLAINS_PLANTS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(RankineFeatures.RANKINE_SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.STRAWBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))))));
        register(context, CONFIGURED_PATCH_FOREST_PLANTS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(RankineFeatures.RANKINE_SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.ELDERBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(RankineFeatures.RANKINE_SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.POKEBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))))));
        register(context, CONFIGURED_PATCH_TAIGA_PLANTS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.RASPBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BLACKBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))))));
        register(context, CONFIGURED_PATCH_MOUNTAIN_PLANTS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.SNOWBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))),
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.STRAWBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3)))))))));
        register(context, CONFIGURED_PATCH_SWAMP_PLANTS, Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 5, 2, PlacementUtils.onlyWhenEmpty(RankineFeatures.RANKINE_SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.CRANBERRY_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3))))));
        register(context, CONFIGURED_PATCH_JUNGLE_PLANTS, Feature.RANDOM_PATCH, new RandomPatchConfiguration(48, 4, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.PINEAPPLE_BUSH.get().defaultBlockState().setValue(RankinePlantBlock.AGE, 3))))));

        register(context, CONFIGURED_PATCH_COBBLES, RankineFeatures.PATCH_COBBLES.get(), new RandomPatchConfiguration(48, 7, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.SHORT_GRASS.get())))));

        register(context, CONFIGURED_CEDAR_TREE, RankineFeatures.CEDAR_TREE.get(), createRankineTree(RankineBlocks.CEDAR.getLog(), RankineBlocks.CEDAR.getLeaves(),8,5,0).build());
        register(context, CONFIGURED_EASTERN_HEMLOCK_TREE, RankineFeatures.EASTERN_HEMLOCK_TREE.get(), createRankineTree(RankineBlocks.EASTERN_HEMLOCK.getLog(), RankineBlocks.EASTERN_HEMLOCK.getLeaves(),15,10,0).build());
        register(context, CONFIGURED_WESTERN_HEMLOCK_TREE, RankineFeatures.WESTERN_HEMLOCK_TREE.get(), createRankineTree(RankineBlocks.WESTERN_HEMLOCK.getLog(), RankineBlocks.WESTERN_HEMLOCK.getLeaves(),25,10,0).build());
        register(context, CONFIGURED_BALSAM_FIR_TREE, RankineFeatures.BALSAM_FIR_TREE.get(), createRankineTree(RankineBlocks.BALSAM_FIR.getLog(), RankineBlocks.BALSAM_FIR.getLeaves(),10,10,0).build());
        register(context, CONFIGURED_SHORT_BALSAM_FIR_TREE, RankineFeatures.BALSAM_FIR_TREE.get(), createRankineTree(RankineBlocks.BALSAM_FIR.getLog(), RankineBlocks.BALSAM_FIR.getLeaves(),5,4,0).build());
        register(context, CONFIGURED_COCONUT_PALM_TREE, RankineFeatures.COCONUT_PALM_TREE.get(), createRankineTree(RankineBlocks.COCONUT_PALM.getLog(), RankineBlocks.COCONUT_PALM.getLeaves(),7,10,0).build());
        register(context, CONFIGURED_MODIFIED_BIRCH_TREE, RankineFeatures.BIRCH_TREE.get(), createRankineTree(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES,6,6,0).build());
        register(context, CONFIGURED_BLACK_BIRCH_TREE, RankineFeatures.BIRCH_TREE.get(), createRankineTree(RankineBlocks.BLACK_BIRCH.getLog(), RankineBlocks.BLACK_BIRCH.getLeaves(),4,6,0).build());
        register(context, CONFIGURED_YELLOW_BIRCH_TREE, RankineFeatures.BIRCH_TREE.get(), createRankineTree(RankineBlocks.YELLOW_BIRCH.getLog(), RankineBlocks.YELLOW_BIRCH.getLeaves(),6,6,0).build());
        register(context, CONFIGURED_RED_BIRCH_TREE, RankineFeatures.BIRCH_TREE.get(), createRankineTree(RankineBlocks.RED_BIRCH.getLog(), RankineBlocks.RED_BIRCH.getLeaves(),4,3,0).build());
        register(context, CONFIGURED_MAGNOLIA_TREE, RankineFeatures.MAGNOLIA_TREE.get(), createRankineTree(RankineBlocks.MAGNOLIA.getLog(), RankineBlocks.MAGNOLIA.getLeaves(),4,2,0).build());
        register(context, CONFIGURED_ERYTHRINA_TREE, RankineFeatures.ERYTHRINA_TREE.get(), createRankineTree(RankineBlocks.ERYTHRINA.getLog(), RankineBlocks.ERYTHRINA.getLeaves(),6,3,0).build());
        register(context, CONFIGURED_HONEY_LOCUST_TREE, RankineFeatures.HONEY_LOCUST_TREE.get(), createRankineTree(RankineBlocks.HONEY_LOCUST.getLog(), RankineBlocks.HONEY_LOCUST.getLeaves(),8,3,0).build());
        register(context, CONFIGURED_WEEPING_WILLOW_TREE, RankineFeatures.WEEPING_WILLOW_TREE.get(), createRankineTree(RankineBlocks.WEEPING_WILLOW.getLog(), RankineBlocks.WEEPING_WILLOW.getLeaves(),6,4,0).build());
        register(context, CONFIGURED_BLACK_WALNUT_TREE, RankineFeatures.BLACK_WALNUT_TREE.get(), createRankineTree(RankineBlocks.BLACK_WALNUT.getLog(), RankineBlocks.BLACK_WALNUT.getLeaves(),10,5,0).build());
        register(context, CONFIGURED_PINYON_PINE_TREE, RankineFeatures.PINYON_PINE_TREE.get(), createRankineTree(RankineBlocks.PINYON_PINE.getLog(), RankineBlocks.PINYON_PINE.getLeaves(),6,4,0).build());
        register(context, CONFIGURED_JUNIPER_TREE, RankineFeatures.JUNIPER_TREE.get(), createRankineTree(RankineBlocks.JUNIPER.getLog(), RankineBlocks.JUNIPER.getLeaves(),3,1,0).build());
        register(context, CONFIGURED_PETRIFIED_CHORUS_TREE, RankineFeatures.PETRIFIED_CHORUS_TREE.get(), createRankineTree(RankineBlocks.PETRIFIED_CHORUS.getLog(), Blocks.AIR,0,0,0).build());
        register(context, CONFIGURED_MAPLE_TREE, RankineFeatures.MAPLE_TREE.get(), (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.MAPLE.getLog()), new FancyTrunkPlacer(8, 4, 0), BlockStateProvider.simple(RankineBlocks.MAPLE.getLeaves()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build()));
        register(context, CONFIGURED_SHARINGA_TREE, RankineFeatures.SHARINGA_TREE.get(), (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.SHARINGA.getLog()), new FancyTrunkPlacer(8, 4, 0), BlockStateProvider.simple(RankineBlocks.SHARINGA.getLeaves()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build()));
        register(context, CONFIGURED_CORK_OAK_TREE, RankineFeatures.CORK_OAK_TREE.get(), (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.CORK_OAK.getLog()), new FancyTrunkPlacer(8, 4, 0), BlockStateProvider.simple(RankineBlocks.CORK_OAK.getLeaves()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build()));
        register(context, CONFIGURED_CINNAMON_TREE, RankineFeatures.CINNAMON_TREE.get(), (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.CINNAMON.getLog()), new ForkingTrunkPlacer(6, 2, 1), BlockStateProvider.simple(RankineBlocks.CINNAMON.getLeaves()), new AcaciaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build());

        register(context, CONFIGURED_CEDAR_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.CEDAR.getHollowLog())));
        register(context, CONFIGURED_EASTERN_HEMLOCK_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.EASTERN_HEMLOCK.getHollowLog())));
        register(context, CONFIGURED_WESTERN_HEMLOCK_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.WESTERN_HEMLOCK.getHollowLog())));
        register(context, CONFIGURED_BALSAM_FIR_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BALSAM_FIR.getHollowLog())));
        register(context, CONFIGURED_COCONUT_PALM_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.COCONUT_PALM.getHollowLog())));
        register(context, CONFIGURED_BIRCH_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.HOLLOW_BIRCH_LOG.get())));
        register(context, CONFIGURED_OAK_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.HOLLOW_OAK_LOG.get())));
        register(context, CONFIGURED_DARK_OAK_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.HOLLOW_DARK_OAK_LOG.get())));
        register(context, CONFIGURED_SPRUCE_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.HOLLOW_SPRUCE_LOG.get())));
        register(context, CONFIGURED_JUNGLE_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.HOLLOW_JUNGLE_LOG.get())));
        register(context, CONFIGURED_ACACIA_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.HOLLOW_ACACIA_LOG.get())));
        register(context, CONFIGURED_BLACK_BIRCH_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BLACK_BIRCH.getHollowLog())));
        register(context, CONFIGURED_YELLOW_BIRCH_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.YELLOW_BIRCH.getHollowLog())));
        register(context, CONFIGURED_RED_BIRCH_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.RED_BIRCH.getHollowLog())));
        register(context, CONFIGURED_MAGNOLIA_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.MAGNOLIA.getHollowLog())));
        register(context, CONFIGURED_ERYTHRINA_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.ERYTHRINA.getHollowLog())));
        register(context, CONFIGURED_HONEY_LOCUST_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.HONEY_LOCUST.getHollowLog())));
        register(context, CONFIGURED_WEEPING_WILLOW_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.WEEPING_WILLOW.getHollowLog())));
        register(context, CONFIGURED_BLACK_WALNUT_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.BLACK_WALNUT.getHollowLog())));
        register(context, CONFIGURED_PINYON_PINE_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.PINYON_PINE.getHollowLog())));
        register(context, CONFIGURED_JUNIPER_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.JUNIPER.getHollowLog())));
        register(context, CONFIGURED_PETRIFIED_CHORUS_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.PETRIFIED_CHORUS.getHollowLog())));
        register(context, CONFIGURED_MAPLE_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.MAPLE.getHollowLog())));
        register(context, CONFIGURED_SHARINGA_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.SHARINGA.getHollowLog())));
        register(context, CONFIGURED_CORK_OAK_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.CORK_OAK.getHollowLog())));
        register(context, CONFIGURED_CINNAMON_FALLEN_LOG, RankineFeatures.FALLEN_LOG.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(RankineBlocks.CINNAMON.getHollowLog())));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?,?>> context, ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createRankineTree(Block log, Block leaves, int height, int randA, int randB) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkPlacer(height, randA, randB), BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0), new TwoLayersFeatureSize(0, 0, 0));
    }
}
