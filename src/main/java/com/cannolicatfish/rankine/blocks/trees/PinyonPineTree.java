package com.cannolicatfish.rankine.blocks.trees;

import com.cannolicatfish.rankine.world.biome.ModBiomeFeatures;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class PinyonPineTree extends Tree {
    public final LogBlock log;
    public final LeavesBlock leaf;
    public final SaplingBlock sapling;

    public PinyonPineTree(LogBlock log, LeavesBlock leaf, SaplingBlock sapling) {
        this.log = log;
        this.leaf = leaf;
        this.sapling = sapling;
    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return Feature.ACACIA_TREE.withConfiguration(ModBiomeFeatures.PINYON_TREE_CONFIG);
    }
}
