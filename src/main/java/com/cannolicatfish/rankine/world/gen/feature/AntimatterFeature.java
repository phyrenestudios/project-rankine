package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class AntimatterFeature extends Feature<NoFeatureConfig> {
    public AntimatterFeature(Codec<NoFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);

        if (rand.nextFloat()<0.1) {        //for (int i = 0; i <4; ++i) {
            int randX = chunk.getPos().getMinBlockX() + rand.nextInt(16);
            int randY = rand.nextInt(90);
            int randZ = chunk.getPos().getMaxBlockZ() + rand.nextInt(16);

            reader.setBlock(new BlockPos(randX, randY, randZ), RankineBlocks.ANTIMATTER.get().defaultBlockState(), 19);
            //}}

        }

        return true;
    }


}
