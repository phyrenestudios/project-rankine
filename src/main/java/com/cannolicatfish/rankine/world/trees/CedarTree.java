package com.cannolicatfish.rankine.world.trees;

import com.cannolicatfish.rankine.world.biome.RankineBiomeFeatures;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class CedarTree extends Tree {

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return new TreeFeature(TreeFeatureConfig::func_227338_a_).withConfiguration(RankineBiomeFeatures.CEDAR_TREE_CONFIG);
    }
}
