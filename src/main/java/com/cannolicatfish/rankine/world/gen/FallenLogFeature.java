package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.blocks.HollowLogBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class FallenLogFeature extends Feature<SimpleBlockConfiguration> {
    public FallenLogFeature(Codec<SimpleBlockConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel levelIn = context.level();
        RandomSource random = context.random();
        BlockState hollowLog = context.config().toPlace().getState(random, blockPos);

        int length = random.nextInt(3, 6);
        Direction.Axis axis = Direction.Axis.getRandom(random);
        hollowLog = hollowLog.setValue(HollowLogBlock.AXIS, axis);

        boolean validSpot = true;
        for (int i = 0; i < length; i++) {
            BlockPos b = blockPos.relative(axis, i);
            validSpot = validSpot && (levelIn.isEmptyBlock(b) || levelIn.getBlockState(b).is(Blocks.GRASS)) && levelIn.getBlockState(b.below()).isCollisionShapeFullBlock(levelIn,b.below());
        }
        if (validSpot) {
            Holder<Biome> biome = levelIn.getBiome(blockPos);
            for (int i = 0; i < length; i++) {
                BlockPos cur = blockPos.relative(axis, i);
                boolean isMossy = biome.value().modifiableBiomeInfo().getModifiedBiomeInfo().climateSettings().downfall() > 0.5 && random.nextFloat() < 0.6f && levelIn.isEmptyBlock(cur.above());
                levelIn.setBlock(cur, hollowLog.setValue(HollowLogBlock.MOSSY, isMossy), 3);
                if (isMossy) {
                    levelIn.setBlock(cur.above(), Blocks.MOSS_CARPET.defaultBlockState(), 3);
                }
            }
        }
        return validSpot;
    }

}
