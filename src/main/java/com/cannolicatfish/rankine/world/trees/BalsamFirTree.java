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

public class BalsamFirTree extends Tree {
    public final LogBlock log;
    public final LeavesBlock leaf;
    public final SaplingBlock sapling;

    public BalsamFirTree(LogBlock log, LeavesBlock leaf, SaplingBlock sapling) {
        this.log = log;
        this.leaf = leaf;
        this.sapling = sapling;
    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return new TreeFeature(TreeFeatureConfig::func_227338_a_).withConfiguration(RankineBiomeFeatures.BALSAM_FIR_TREE_CONFIG);
    }
}
