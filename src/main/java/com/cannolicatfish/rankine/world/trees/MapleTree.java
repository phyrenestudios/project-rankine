package com.cannolicatfish.rankine.world.trees;

import com.cannolicatfish.rankine.world.gen.RankineBiomeFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class MapleTree extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random randomIn, boolean p_225546_2_) {
        return Feature.TREE.configured(RankineBiomeFeatures.MAPLE_TREE_CONFIG);
    }
}
