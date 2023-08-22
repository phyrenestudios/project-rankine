package com.cannolicatfish.rankine.worldgen.features;

import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.util.worldgen.RankineFeatureUtils;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RankineUndergroundFeatures {


    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_STONE_REPLACER = RankineFeatureUtils.createKey("stone_replacer");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_METEORITE = RankineFeatureUtils.createKey("overworld_meteorite");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, RankineUndergroundFeatures.OVERWORLD_STONE_REPLACER, RankineFeatures.OVERWORLD_STONE_REPLACER.get(), new NoneFeatureConfiguration());
        register(context, RankineUndergroundFeatures.OVERWORLD_METEORITE, RankineFeatures.OVERWORLD_METEORITE.get(), new NoneFeatureConfiguration());

    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey, F feature, FC configuration) {
        context.register(configuredFeatureKey, new ConfiguredFeature<>(feature, configuration));
    }

}
