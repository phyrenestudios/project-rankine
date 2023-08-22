package com.cannolicatfish.rankine.worldgen.placements;

import com.cannolicatfish.rankine.util.worldgen.RankinePlacementUtils;
import com.cannolicatfish.rankine.worldgen.features.RankineUndergroundFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RankineUndergroundPlacements {


    public static final ResourceKey<PlacedFeature> OVERWORLD_STONE_REPLACER = RankinePlacementUtils.createKey("stone_replacer");
    public static final ResourceKey<PlacedFeature> OVERWORLD_METEORITE = RankinePlacementUtils.createKey("overworld_meteorite");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, OVERWORLD_STONE_REPLACER, configuredFeatureGetter.getOrThrow(RankineUndergroundFeatures.OVERWORLD_STONE_REPLACER), Collections.emptyList());
        register(context, OVERWORLD_METEORITE, configuredFeatureGetter.getOrThrow(RankineUndergroundFeatures.OVERWORLD_METEORITE), Arrays.asList(RarityFilter.onAverageOnceEvery(10),InSquarePlacement.spread(),PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,BiomeFilter.biome()));
    }

    protected static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... modifiers) {
        register(context, placedFeatureKey, configuredFeature, List.of(modifiers));
    }

    protected static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        context.register(placedFeatureKey, new PlacedFeature(configuredFeature, modifiers));
    }
}
