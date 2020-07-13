package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;
import java.util.function.Function;

public class CrystalFeature extends Feature<NoFeatureConfig> {


    public CrystalFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos underBlock = pos.down();
        ChunkPos chunkpos = new ChunkPos(pos);
        if ((worldIn.getBlockState(underBlock).getBlock() == Blocks.SAND || worldIn.getBlockState(underBlock).getBlock() == Blocks.SANDSTONE ||
                worldIn.getBlockState(underBlock).getBlock() == ModBlocks.LIMESTONE || worldIn.getBlockState(underBlock).getBlock() == ModBlocks.RHYOLITE || worldIn.getBlockState(underBlock).getBlock() == Blocks.STONE)
                && worldIn.isAirBlock(pos) && pos.getY() >= 48 && pos.getY() <= 64) {
            worldIn.setBlockState(pos, ModBlocks.NITER.getDefaultState(), 2);
            return true;
        } else {
            return false;
        }
    }
}
