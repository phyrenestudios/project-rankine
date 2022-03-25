package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class AntimatterFeature extends Feature<NoneFeatureConfiguration> {
    public AntimatterFeature(Codec<NoneFeatureConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();
        ChunkAccess chunk = reader.getChunk(pos);

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
