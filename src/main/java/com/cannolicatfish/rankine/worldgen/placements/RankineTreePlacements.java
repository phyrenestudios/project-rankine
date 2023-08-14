package com.cannolicatfish.rankine.worldgen.placements;

import com.cannolicatfish.rankine.util.worldgen.RankinePlacementUtils;
import com.cannolicatfish.rankine.worldgen.features.RankineTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

public class RankineTreePlacements {


    public static final ResourceKey<PlacedFeature> PLACED_HONEY_LOCUST_TREE = RankinePlacementUtils.createKey("honey_locust_tree");
    public static final ResourceKey<PlacedFeature> PLACED_CEDAR_TREE = RankinePlacementUtils.createKey("cedar_locust_tree");



    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, PLACED_HONEY_LOCUST_TREE, configuredFeatureGetter.getOrThrow(RankineTreeFeatures.HONEY_LOCUST_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(6)));
        register(context, PLACED_CEDAR_TREE, configuredFeatureGetter.getOrThrow(RankineTreeFeatures.CEDAR_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2)));
    }

    protected static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... modifiers) {
        register(context, placedFeatureKey, configuredFeature, List.of(modifiers));
    }

    protected static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        context.register(placedFeatureKey, new PlacedFeature(configuredFeature, modifiers));
    }
}
