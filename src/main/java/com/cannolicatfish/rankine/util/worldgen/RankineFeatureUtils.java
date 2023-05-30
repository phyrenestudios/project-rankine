package com.cannolicatfish.rankine.util.worldgen;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.worldgen.features.RankineTreeFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class RankineFeatureUtils {
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RankineTreeFeatures.bootstrap(context);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ProjectRankine.MODID, name));
    }
}
