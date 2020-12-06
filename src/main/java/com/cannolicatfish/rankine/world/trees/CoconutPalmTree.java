package com.cannolicatfish.rankine.world.trees;

import com.cannolicatfish.rankine.init.ModFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;

public class CoconutPalmTree extends Tree {

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return Feature.TREE.withConfiguration(ModFeatures.COCONUT_PALM_TREE_CONFIG);
    }
}
