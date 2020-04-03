package com.cannolicatfish.rankine.world.feature;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;

import javax.annotation.Nullable;
import java.util.Random;

public class PinyonPineTree extends Tree {
    public static final TreeFeatureConfig PINYON_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.PINYON_PINE_LOG.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.PINYON_PINE_LEAVES.getDefaultState()), new AcaciaFoliagePlacer(2, 0))).baseHeight(5).heightRandA(2).heightRandB(2).trunkHeight(0).ignoreVines().setSapling((net.minecraftforge.common.IPlantable)ModBlocks.PINYON_PINE_SAPLING).build();
    public static final TreeFeatureConfig JUNIPER_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.JUNIPER_LOG.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.JUNIPER_LEAVES.getDefaultState()), new AcaciaFoliagePlacer(1, 0))).baseHeight(1).heightRandA(1).heightRandB(0).trunkHeight(0).ignoreVines().setSapling((net.minecraftforge.common.IPlantable)ModBlocks.COCONUT_PALM_SAPLING).build();

    public PinyonPineTree() {

    }


    @Nullable
    @Override
    public ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return Feature.ACACIA_TREE.withConfiguration(PINYON_TREE_CONFIG);
    }
}
