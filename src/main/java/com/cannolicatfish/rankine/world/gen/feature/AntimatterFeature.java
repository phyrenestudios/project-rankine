package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.WGConfig;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);

        if (rand.nextFloat()<0.05) {        //for (int i = 0; i <4; ++i) {
            int randX = chunk.getPos().getXStart() + rand.nextInt(16);
            int randY = rand.nextInt(90);
            int randZ = chunk.getPos().getZEnd() + rand.nextInt(16);

            reader.setBlockState(new BlockPos(randX, randY, randZ), RankineBlocks.ANTIMATTER.get().getDefaultState(), 19);
            //}}

        }

        return true;
    }


}
