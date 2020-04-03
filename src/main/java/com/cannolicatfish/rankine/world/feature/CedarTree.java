package com.cannolicatfish.rankine.world.feature;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.trees.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.PineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Random;

public class CedarTree extends Tree {

    public static final TreeFeatureConfig CEDAR_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.CEDAR_LOG.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.CEDAR_LEAVES.getDefaultState()), new SpruceFoliagePlacer(2, 1))).baseHeight(8).heightRandA(3).trunkHeight(1).trunkHeightRandom(1).trunkTopOffsetRandom(2).ignoreVines().setSapling((net.minecraftforge.common.IPlantable)ModBlocks.CEDAR_SAPLING).build();

    public static final TreeFeatureConfig SMALL_SPRUCE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()), new PineFoliagePlacer(1, 0))).baseHeight(3).heightRandA(4).trunkTopOffset(1).foliageHeight(3).foliageHeightRandom(1).ignoreVines().setSapling((net.minecraftforge.common.IPlantable)Blocks.SPRUCE_SAPLING).build();



    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return Feature.NORMAL_TREE.withConfiguration(CEDAR_TREE_CONFIG); // fix
    }
}