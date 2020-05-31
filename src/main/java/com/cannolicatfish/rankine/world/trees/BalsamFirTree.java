package com.cannolicatfish.rankine.world.trees;

import com.cannolicatfish.rankine.world.biome.RankineBiomeFeatures;
import com.cannolicatfish.rankine.world.gen.feature.RankineFeatures;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class BalsamFirTree extends Tree {

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return RankineFeatures.BALSAM_FIR_TREE.withConfiguration(RankineBiomeFeatures.BALSAM_FIR_TREE_CONFIG);
    }
}
