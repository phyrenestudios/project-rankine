package com.cannolicatfish.rankine.worldgen.features;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.util.worldgen.RankineFeatureUtils;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;

public class RankineTreeFeatures {


    public static final ResourceKey<ConfiguredFeature<?, ?>> HONEY_LOCUST_TREE = RankineFeatureUtils.createKey("honey_locust_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR_TREE = RankineFeatureUtils.createKey("cedar_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EASTERN_HEMLOCK_TREE = RankineFeatureUtils.createKey("eastern_hemlock_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WESTERN_HEMLOCK_TREE = RankineFeatureUtils.createKey("western_hemlock_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BALSAM_FIR_TREE = RankineFeatureUtils.createKey("balsam_fir_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_BALSAM_FIR_TREE = RankineFeatureUtils.createKey("short_balsam_fir_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COCONUT_PALM_TREE = RankineFeatureUtils.createKey("coconut_palm_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MODIFIED_BIRCH_TREE = RankineFeatureUtils.createKey("modified_birch_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_BIRCH_TREE = RankineFeatureUtils.createKey("black_birch_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_BIRCH_TREE = RankineFeatureUtils.createKey("yellow_birch_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_BIRCH_TREE = RankineFeatureUtils.createKey("red_birch_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAGNOLIA_TREE = RankineFeatureUtils.createKey("magnolia_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ERYTHRINA_TREE = RankineFeatureUtils.createKey("erythrina_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WEEPING_WILLOW_TREE = RankineFeatureUtils.createKey("weeping_willow_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_WALNUT_TREE = RankineFeatureUtils.createKey("black_walnut_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINYON_PINE_TREE = RankineFeatureUtils.createKey("pinyon_pine_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JUNIPER_TREE = RankineFeatureUtils.createKey("juniper_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PETRIFIED_CHORUS_TREE = RankineFeatureUtils.createKey("petrified_chorus_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE = RankineFeatureUtils.createKey("maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHARINGA_TREE = RankineFeatureUtils.createKey("sharinga_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORK_OAK_TREE = RankineFeatureUtils.createKey("cork_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CINNAMON_TREE = RankineFeatureUtils.createKey("cinnamon_tree");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, RankineTreeFeatures.HONEY_LOCUST_TREE, RankineFeatures.HONEY_LOCUST_TREE.get(), createRankineTree(RankineBlocks.HONEY_LOCUST.getLog(), RankineBlocks.HONEY_LOCUST.getLeaves(),8,3,0).build());
        register(context, RankineTreeFeatures.CEDAR_TREE, RankineFeatures.CEDAR_TREE.get(), createRankineTree(RankineBlocks.CEDAR.getLog(), RankineBlocks.CEDAR.getLeaves(),8,5,0).build());
        register(context, RankineTreeFeatures.EASTERN_HEMLOCK_TREE, RankineFeatures.EASTERN_HEMLOCK_TREE.get(), createRankineTree(RankineBlocks.EASTERN_HEMLOCK.getLog(), RankineBlocks.EASTERN_HEMLOCK.getLeaves(),15,10,0).build());
        register(context, RankineTreeFeatures.WESTERN_HEMLOCK_TREE, RankineFeatures.WESTERN_HEMLOCK_TREE.get(), createRankineTree(RankineBlocks.WESTERN_HEMLOCK.getLog(), RankineBlocks.WESTERN_HEMLOCK.getLeaves(),25,10,0).build());
        register(context, RankineTreeFeatures.BALSAM_FIR_TREE, RankineFeatures.BALSAM_FIR_TREE.get(), createRankineTree(RankineBlocks.BALSAM_FIR.getLog(), RankineBlocks.BALSAM_FIR.getLeaves(),10,10,0).build());
        register(context, RankineTreeFeatures.SHORT_BALSAM_FIR_TREE, RankineFeatures.BALSAM_FIR_TREE.get(), createRankineTree(RankineBlocks.BALSAM_FIR.getLog(), RankineBlocks.BALSAM_FIR.getLeaves(),5,4,0).build());
        register(context, RankineTreeFeatures.COCONUT_PALM_TREE, RankineFeatures.COCONUT_PALM_TREE.get(), createRankineTree(RankineBlocks.COCONUT_PALM.getLog(), RankineBlocks.COCONUT_PALM.getLeaves(),7,10,0).build());
        register(context, RankineTreeFeatures.MODIFIED_BIRCH_TREE, RankineFeatures.BIRCH_TREE.get(), createRankineTree(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES,6,6,0).build());
        register(context, RankineTreeFeatures.BLACK_BIRCH_TREE, RankineFeatures.BIRCH_TREE.get(), createRankineTree(RankineBlocks.BLACK_BIRCH.getLog(), RankineBlocks.BLACK_BIRCH.getLeaves(),4,6,0).build());
        register(context, RankineTreeFeatures.YELLOW_BIRCH_TREE, RankineFeatures.BIRCH_TREE.get(), createRankineTree(RankineBlocks.YELLOW_BIRCH.getLog(), RankineBlocks.YELLOW_BIRCH.getLeaves(),6,6,0).build());
        register(context, RankineTreeFeatures.RED_BIRCH_TREE, RankineFeatures.BIRCH_TREE.get(), createRankineTree(RankineBlocks.RED_BIRCH.getLog(), RankineBlocks.RED_BIRCH.getLeaves(),4,3,0).build());
        register(context, RankineTreeFeatures.MAGNOLIA_TREE, RankineFeatures.MAGNOLIA_TREE.get(), createRankineTree(RankineBlocks.MAGNOLIA.getLog(), RankineBlocks.MAGNOLIA.getLeaves(),4,2,0).build());
        register(context, RankineTreeFeatures.ERYTHRINA_TREE, RankineFeatures.ERYTHRINA_TREE.get(), createRankineTree(RankineBlocks.ERYTHRINA.getLog(), RankineBlocks.ERYTHRINA.getLeaves(),6,3,0).build());
        register(context, RankineTreeFeatures.WEEPING_WILLOW_TREE, RankineFeatures.WEEPING_WILLOW_TREE.get(), createRankineTree(RankineBlocks.WEEPING_WILLOW.getLog(), RankineBlocks.WEEPING_WILLOW.getLeaves(),6,4,0).build());
        register(context, RankineTreeFeatures.BLACK_WALNUT_TREE, RankineFeatures.BLACK_WALNUT_TREE.get(), createRankineTree(RankineBlocks.BLACK_WALNUT.getLog(), RankineBlocks.BLACK_WALNUT.getLeaves(),10,5,0).build());
        register(context, RankineTreeFeatures.PINYON_PINE_TREE, RankineFeatures.PINYON_PINE_TREE.get(),  createRankineTree(RankineBlocks.PINYON_PINE.getLog(), RankineBlocks.PINYON_PINE.getLeaves(),6,4,0).build());
        register(context, RankineTreeFeatures.JUNIPER_TREE, RankineFeatures.JUNIPER_TREE.get(), createRankineTree(RankineBlocks.JUNIPER.getLog(), RankineBlocks.JUNIPER.getLeaves(),3,1,0).build());
        register(context, RankineTreeFeatures.PETRIFIED_CHORUS_TREE, RankineFeatures.PETRIFIED_CHORUS_TREE.get(), createRankineTree(RankineBlocks.PETRIFIED_CHORUS.getLog(), Blocks.AIR,0,0,0).build());
        register(context, RankineTreeFeatures.MAPLE_TREE, RankineFeatures.MAPLE_TREE.get(), new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.MAPLE.getLog()), new FancyTrunkPlacer(8, 4, 0), BlockStateProvider.simple(RankineBlocks.MAPLE.getLeaves()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build());
        register(context, RankineTreeFeatures.SHARINGA_TREE, RankineFeatures.SHARINGA_TREE.get(), new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.SHARINGA.getLog()), new FancyTrunkPlacer(8, 4, 0), BlockStateProvider.simple(RankineBlocks.SHARINGA.getLeaves()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build());
        register(context, RankineTreeFeatures.CORK_OAK_TREE, RankineFeatures.CORK_OAK_TREE.get(), new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.CORK_OAK.getLog()), new FancyTrunkPlacer(8, 4, 0), BlockStateProvider.simple(RankineBlocks.CORK_OAK.getLeaves()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build());
        register(context, RankineTreeFeatures.CINNAMON_TREE, RankineFeatures.CINNAMON_TREE.get(), new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RankineBlocks.CINNAMON.getLog()), new ForkingTrunkPlacer(6, 2, 1), BlockStateProvider.simple(RankineBlocks.CINNAMON.getLeaves()), new AcaciaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2)).ignoreVines().build());

    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey, F feature, FC configuration) {
        context.register(configuredFeatureKey, new ConfiguredFeature<>(feature, configuration));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createRankineTree(Block log, Block leaves, int height, int randA, int randB) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkPlacer(height, randA, randB), BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0), new TwoLayersFeatureSize(0, 0, 0));
    }
}
