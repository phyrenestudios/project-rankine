package com.cannolicatfish.rankine.worldgen.treeGrowers;

import com.cannolicatfish.rankine.worldgen.features.RankineTreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class MagnoliaTreeGrower extends AbstractTreeGrower {
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomIn, boolean p_225546_2_) {
        return RankineTreeFeatures.MAGNOLIA_TREE;
    }
}
