package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RankinePlacedFeatures {
    public static final PlacementModifier[] RANKINE_TREE_CHANCE_PLACEMENT = new PlacementModifier[]{PlacementUtils.HEIGHTMAP, RarityFilter.onAverageOnceEvery(2)};

    public static Holder<PlacedFeature> BALSAM_FIR_TREE;
    public static Holder<PlacedFeature> SHORT_BALSAM_FIR_TREE;
    public static Holder<PlacedFeature> COCONUT_PALM_TREE;
    public static Holder<PlacedFeature> BLACK_WALNUT_TREE;
    public static Holder<PlacedFeature> RED_BIRCH_TREE;
    public static Holder<PlacedFeature> EASTERN_HEMLOCK_TREE;
    public static Holder<PlacedFeature> WESTERN_HEMLOCK_TREE;
    public static Holder<PlacedFeature> BLACK_BIRCH_TREE;
    public static Holder<PlacedFeature> YELLOW_BIRCH_TREE;
    public static Holder<PlacedFeature> MODIFIED_BIRCH_TREE;
    public static Holder<PlacedFeature> PETRIFIED_CHORUS_TREE;
    public static Holder<PlacedFeature> PINYON_PINE_TREE;
    public static Holder<PlacedFeature> JUNIPER_TREE;
    public static Holder<PlacedFeature> ERYTHRINA_TREE;
    public static Holder<PlacedFeature> MAGNOLIA_TREE;
    public static Holder<PlacedFeature> RED_CEDAR_TREE;
    public static Holder<PlacedFeature> HONEY_LOCUST_TREE;
    public static Holder<PlacedFeature> WEEPING_WILLOW_TREE;
    public static Holder<PlacedFeature> MAPLE_TREE;
    public static Holder<PlacedFeature> SHARINGA_TREE;
    public static Holder<PlacedFeature> CORK_OAK_TREE;
    public static Holder<PlacedFeature> CINNAMON_TREE;

    public static Holder<PlacedFeature> MUSHROOM;

    public static Holder<PlacedFeature> INTRUSION_FEATURE;
    public static Holder<PlacedFeature> COLUMN_FEATURE;
    public static Holder<PlacedFeature> FLAT_BEDROCK;
    public static Holder<PlacedFeature> FLAT_BEDROCK_NETHER;
    public static Holder<PlacedFeature> WORLD_REPLACER;
    public static Holder<PlacedFeature> POST_WORLD_REPLACER;
    public static Holder<PlacedFeature> WHITE_SAND;
    public static Holder<PlacedFeature> ALLUVIUM;
    public static Holder<PlacedFeature> EVAPORITE;
    public static Holder<PlacedFeature> FUMAROLE;
    public static Holder<PlacedFeature> BLACK_SAND;
    public static Holder<PlacedFeature> METEORITE;
    public static Holder<PlacedFeature> END_METEORITE;
    public static Holder<PlacedFeature> ANTIMATTER_BLOB;

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(registryName, Holder.direct(feature), placementModifiers);
    }



    public static void registerAllFeatures() {
        BALSAM_FIR_TREE = registerPlacedFeature("balsam_fir_tree",RankineConfiguredFeatures.BALSAM_FIR_TREE,RANKINE_TREE_CHANCE_PLACEMENT);
        SHORT_BALSAM_FIR_TREE = registerPlacedFeature("short_balsam_fir_tree",RankineConfiguredFeatures.SHORT_BALSAM_FIR_TREE,RANKINE_TREE_CHANCE_PLACEMENT);
        COCONUT_PALM_TREE = registerPlacedFeature("coconut_palm_tree",RankineConfiguredFeatures.COCONUT_PALM_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        BLACK_WALNUT_TREE = registerPlacedFeature("black_walnut_tree",RankineConfiguredFeatures.BLACK_WALNUT_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        RED_BIRCH_TREE = registerPlacedFeature("red_birch_tree",RankineConfiguredFeatures.RED_BIRCH_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        EASTERN_HEMLOCK_TREE = registerPlacedFeature("eastern_hemlock_tree",RankineConfiguredFeatures.EASTERN_HEMLOCK_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        WESTERN_HEMLOCK_TREE = registerPlacedFeature("western_hemlock_tree",RankineConfiguredFeatures.WESTERN_HEMLOCK_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        BLACK_BIRCH_TREE = registerPlacedFeature("black_birch_tree",RankineConfiguredFeatures.BLACK_BIRCH_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        YELLOW_BIRCH_TREE = registerPlacedFeature("yellow_birch_tree",RankineConfiguredFeatures.YELLOW_BIRCH_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        MODIFIED_BIRCH_TREE = registerPlacedFeature("modified_birch_tree",RankineConfiguredFeatures.MODIFIED_BIRCH_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        PETRIFIED_CHORUS_TREE = registerPlacedFeature("petrified_chorus_tree",RankineConfiguredFeatures.PETRIFIED_CHORUS_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        PINYON_PINE_TREE = registerPlacedFeature("pinyon_pine_tree",RankineConfiguredFeatures.PINYON_PINE_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        JUNIPER_TREE = registerPlacedFeature("juniper_tree",RankineConfiguredFeatures.JUNIPER_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        ERYTHRINA_TREE = registerPlacedFeature("erythrina_tree",RankineConfiguredFeatures.ERYTHRINA_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        MAGNOLIA_TREE = registerPlacedFeature("magnolia_tree",RankineConfiguredFeatures.MAGNOLIA_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        RED_CEDAR_TREE = registerPlacedFeature("red_cedar_tree",RankineConfiguredFeatures.RED_CEDAR_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        HONEY_LOCUST_TREE = registerPlacedFeature("honey_locust_tree",RankineConfiguredFeatures.HONEY_LOCUST_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        WEEPING_WILLOW_TREE = registerPlacedFeature("weeping_willow_tree",RankineConfiguredFeatures.WEEPING_WILLOW_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        MAPLE_TREE = registerPlacedFeature("maple_tree",RankineConfiguredFeatures.MAPLE_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        SHARINGA_TREE = registerPlacedFeature("sharinga_tree",RankineConfiguredFeatures.SHARINGA_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        CORK_OAK_TREE = registerPlacedFeature("cork_oak_tree",RankineConfiguredFeatures.CORK_OAK_TREE, RANKINE_TREE_CHANCE_PLACEMENT);
        CINNAMON_TREE = registerPlacedFeature("cinnamon_tree",RankineConfiguredFeatures.CINNAMON_TREE, RANKINE_TREE_CHANCE_PLACEMENT);

        MUSHROOM = registerPlacedFeature("mushroom",RankineConfiguredFeatures.MUSHROOMS, (PlacementModifier) Collections.emptyList());

        INTRUSION_FEATURE = registerPlacedFeature("intrusion",RankineConfiguredFeatures.INTRUSION_FEATURE,(PlacementModifier) Collections.emptyList());
        COLUMN_FEATURE = registerPlacedFeature("column",RankineConfiguredFeatures.COLUMN,(PlacementModifier) Collections.emptyList());
        FLAT_BEDROCK = registerPlacedFeature("flat_bedrock",RankineConfiguredFeatures.FLAT_BEDROCK,(PlacementModifier) Collections.emptyList());
        FLAT_BEDROCK_NETHER = registerPlacedFeature("flat_bedrock_nether",RankineConfiguredFeatures.FLAT_BEDROCK_NETHER,(PlacementModifier) Collections.emptyList());
        WORLD_REPLACER = registerPlacedFeature("world_replacer_feature",RankineConfiguredFeatures.WORLD_REPLACER_GEN,(PlacementModifier) Collections.emptyList());
        POST_WORLD_REPLACER = registerPlacedFeature("post_world_replacer_feature",RankineConfiguredFeatures.POST_WORLD_REPLACER_GEN,(PlacementModifier) Collections.emptyList());
        WHITE_SAND = registerPlacedFeature("white_sand",RankineConfiguredFeatures.DISK_WHITE_SAND,(PlacementModifier) Collections.emptyList());
        EVAPORITE = registerPlacedFeature("evaporite",RankineConfiguredFeatures.ORE_EVAPORITE,(PlacementModifier) Collections.emptyList());
        ALLUVIUM = registerPlacedFeature("alluvium",RankineConfiguredFeatures.ORE_ALLUVIUM,(PlacementModifier) Collections.emptyList());
        FUMAROLE = registerPlacedFeature("fumarole",RankineConfiguredFeatures.FUMAROLE,(PlacementModifier) Collections.emptyList());
        BLACK_SAND = registerPlacedFeature("black_sand",RankineConfiguredFeatures.DISK_BLACK_SAND,(PlacementModifier) Collections.emptyList());
        METEORITE = registerPlacedFeature("meteorite",RankineConfiguredFeatures.METEORITE,(PlacementModifier) Collections.emptyList());
        END_METEORITE = registerPlacedFeature("end_meteorite",RankineConfiguredFeatures.END_METEORITE,(PlacementModifier) Collections.emptyList());
        ANTIMATTER_BLOB = registerPlacedFeature("antimatter_blob",RankineConfiguredFeatures.ANTIMATTER_BLOB,(PlacementModifier) Collections.emptyList());

    }

}
