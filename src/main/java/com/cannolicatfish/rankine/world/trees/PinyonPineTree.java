package com.cannolicatfish.rankine.world.trees;

import com.cannolicatfish.rankine.world.biome.RankineBiomeFeatures;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class PinyonPineTree extends Tree {

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        //return Feature.ACACIA_TREE.withConfiguration(RankineBiomeFeatures.PINYON_TREE_CONFIG);
        return randomIn.nextInt(10) == 0 ? Feature.field_236291_c_.withConfiguration(p_225546_2_ ? DefaultBiomeFeatures.FANCY_TREE_WITH_MORE_BEEHIVES_CONFIG : DefaultBiomeFeatures.FANCY_TREE_CONFIG) : Feature.field_236291_c_.withConfiguration(p_225546_2_ ? DefaultBiomeFeatures.OAK_TREE_WITH_MORE_BEEHIVES_CONFIG : DefaultBiomeFeatures.OAK_TREE_CONFIG);

    }
}
