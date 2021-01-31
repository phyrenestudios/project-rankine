package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class CrystalFeature extends Feature<NoFeatureConfig> {


    public CrystalFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos underBlock = pos.down();
        ChunkPos chunkpos = new ChunkPos(pos);
        if ((reader.getBlockState(underBlock).getBlock() == Blocks.SAND || reader.getBlockState(underBlock).getBlock() == Blocks.SANDSTONE ||
                reader.getBlockState(underBlock).getBlock() == RankineBlocks.TUFA_LIMESTONE.get() || reader.getBlockState(underBlock).getBlock() == RankineBlocks.RHYOLITE.get() || reader.getBlockState(underBlock).getBlock() == Blocks.STONE)
                && reader.isAirBlock(pos) && pos.getY() >= 48 && pos.getY() <= 64) {
            reader.setBlockState(pos, RankineBlocks.NITER.get().getDefaultState(), 2);
            return true;
        } else {
            return false;
        }
    }

}
