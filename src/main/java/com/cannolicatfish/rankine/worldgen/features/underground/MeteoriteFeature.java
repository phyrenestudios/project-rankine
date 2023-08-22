package com.cannolicatfish.rankine.worldgen.features.underground;

import com.cannolicatfish.rankine.blocks.block_enums.MeteorBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MeteoriteFeature extends Feature<NoneFeatureConfiguration> {
    public MeteoriteFeature(Codec<NoneFeatureConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        WorldGenLevel levelIn = context.level();
        BlockPos posIn = context.origin();
        RandomSource rand = levelIn.getRandom();

        /*
        if (!reader.getBlockState(posShift.below()).is(RankineTags.Blocks.METEORITE_REPLACEABLE)) {
            return false;
        }
         */

        Block meteorBlock = (MeteorBlocks.values()[rand.nextInt(MeteorBlocks.values().length)]).getMeteorBlock();

        buildMeteor(levelIn, rand, posIn, meteorBlock);
        buildMeteor(levelIn, rand, posIn.offset(rand.nextInt(2)+1, rand.nextInt(3)-1, rand.nextInt(2)+1), meteorBlock);

        return true;
    }

    private void buildMeteor(WorldGenLevel reader, RandomSource rand, BlockPos pos, Block METEOR) {
        int j = 1 + rand.nextInt(2);
        int k = 1 + rand.nextInt(2);
        int l = 1 + rand.nextInt(2);
        float f = (float)(j + k + l) * 0.333F + 0.75F;
        for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-j, -k, -l), pos.offset(j, k, l))) {
            if (blockpos.distSqr(pos) <= (double)(f * f)) {
                reader.setBlock(blockpos.below(1),METEOR.defaultBlockState(), 2);
            }
        }
    }

}
