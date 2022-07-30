package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.blocks.HollowLogBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class FallenLogFeature extends Feature<NoneFeatureConfiguration> {
    public FallenLogFeature(Codec<NoneFeatureConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel levelIn = context.level();
        Random random = context.random();
        BlockState hollowLog = RankineBlocks.HOLLOW_EASTERN_HEMLOCK_LOG.get().defaultBlockState();

        int length = random.nextInt(3, 6);
        Direction.Axis axis = Direction.Axis.getRandom(random);
        hollowLog = hollowLog.setValue(HollowLogBlock.AXIS, axis);

        boolean validSpot = true;
        for (int i = 0; i < length; i++) {
            BlockPos b = blockPos.relative(axis, i);
            validSpot = validSpot && levelIn.isEmptyBlock(b) && levelIn.getBlockState(b.below()).is(BlockTags.DIRT);
        }
        if (validSpot) {
            for (int i = 0; i < length; i++) {
                BlockPos cur = blockPos.relative(axis, i);
                boolean isMossy = random.nextFloat() < 0.6f && levelIn.isEmptyBlock(cur.above());
                levelIn.setBlock(cur, hollowLog.setValue(HollowLogBlock.MOSSY, isMossy), 3);
                if (isMossy) {
                    levelIn.setBlock(cur.above(), Blocks.MOSS_CARPET.defaultBlockState(), 3);
                }
            }
        }
        return validSpot;
    }

}
