package com.cannolicatfish.rankine.world.grower;

import com.cannolicatfish.rankine.init.RankineConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class BlackBirchTreeGrower extends AbstractTreeGrower {
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomIn, boolean p_225546_2_) {
        return RankineConfiguredFeatures.CONFIGURED_BLACK_BIRCH_TREE.getKey();
    }
}
