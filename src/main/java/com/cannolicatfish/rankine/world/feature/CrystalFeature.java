package com.cannolicatfish.rankine.world.feature;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;

import java.util.Random;
import java.util.function.Function;

public class CrystalFeature extends Feature<NoFeatureConfig> {


    public CrystalFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos underBlock = pos.down();
        if ((worldIn.getBlockState(underBlock).getBlock() == Blocks.SAND || worldIn.getBlockState(underBlock).getBlock() == Blocks.SANDSTONE ||
                worldIn.getBlockState(underBlock).getBlock() == ModBlocks.LIMESTONE || worldIn.getBlockState(underBlock).getBlock() == ModBlocks.RHYOLITE || worldIn.getBlockState(underBlock).getBlock() == Blocks.STONE)
                && worldIn.isAirBlock(pos) && pos.getY() >= 48 && pos.getY() <= 64) {
            worldIn.setBlockState(pos, ModBlocks.NITER.getDefaultState(), 2);
            return true;
        } else {
            return false;
        }
        // NEED TO CHANGE PLACEMENT TYPE SO THAT THIS WORKS
    }
}
