package com.cannolicatfish.rankine.world.trees;

import com.cannolicatfish.rankine.init.ModFeatures;
import com.cannolicatfish.rankine.world.biome.RankineBiomeFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class EasternHemlockTree extends Tree {

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return Feature.TREE.withConfiguration(ModFeatures.EASTERN_HEMLOCK_TREE_CONFIG);
    }
}
