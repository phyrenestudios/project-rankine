package com.cannolicatfish.rankine.world.grower;

import com.cannolicatfish.rankine.init.RankineConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Random;

public class HoneyLocustTreeGrower extends AbstractTreeGrower {
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random randomIn, boolean p_225546_2_) {
        return RankineConfiguredFeatures.CONFIGURED_HONEY_LOCUST_TREE.getHolder().get();
    }
}
