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
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos underBlock = pos.down();
        ChunkPos chunkpos = new ChunkPos(pos);
        if ((reader.getBlockState(underBlock).getBlock() == Blocks.SAND || reader.getBlockState(underBlock).getBlock() == Blocks.SANDSTONE ||
                reader.getBlockState(underBlock).getBlock() == ModBlocks.LIMESTONE || reader.getBlockState(underBlock).getBlock() == ModBlocks.RHYOLITE || reader.getBlockState(underBlock).getBlock() == Blocks.STONE)
                && reader.isAirBlock(pos) && pos.getY() >= 48 && pos.getY() <= 64) {
            reader.setBlockState(pos, ModBlocks.NITER.getDefaultState(), 2);
            return true;
        } else {
            return false;
        }
    }

}
