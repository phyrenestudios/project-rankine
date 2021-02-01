package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FireClayDiskFeature extends Feature<NoFeatureConfig> {
    public FireClayDiskFeature(Codec<NoFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int height = reader.getHeight(Heightmap.Type.OCEAN_FLOOR,pos.getX(),pos.getZ());

        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(pos.getX()-4,height-4,pos.getZ()-4), new BlockPos(pos.getX()+4,height+1,pos.getZ()+4))) {
            if (reader.getBlockState(blockpos) == Blocks.DIRT.getDefaultState() && blockpos.distanceSq(new BlockPos(pos.getX(), height, pos.getZ())) <= Math.pow(4 + 0.5, 2)) {
                reader.setBlockState(blockpos, RankineBlocks.FIRE_CLAY.get().getDefaultState(),2);
            }
        }
        return true;
    }
}
