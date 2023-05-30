package com.cannolicatfish.rankine.util.worldgen;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.worldgen.placements.RankineTreePlacements;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class RankinePlacementUtils {
        public static void bootstrap(BootstapContext<PlacedFeature> context) {
            RankineTreePlacements.bootstrap(context);
        }

        public static ResourceKey<PlacedFeature> createKey(String name) {
            return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ProjectRankine.MODID, name));
        }

}
