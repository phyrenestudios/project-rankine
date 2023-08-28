package com.cannolicatfish.rankine.worldgen.features.underground;

import com.cannolicatfish.rankine.blocks.block_enums.MeteorBlocks;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Arrays;
import java.util.List;

public class MeteoriteFeature extends Feature<NoneFeatureConfiguration> {
    public MeteoriteFeature(Codec<NoneFeatureConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        WorldGenLevel levelIn = context.level();
        BlockPos posIn = context.origin();
        RandomSource rand = levelIn.getRandom();
        BlockState target = levelIn.getBlockState(posIn.below());
        if (!target.is(RankineTags.Blocks.METEORITE_REPLACEABLE)) return false;

        List<Block> tektiteList = Arrays.asList(RankineBlocks.GREEN_TEKTITE.get(), RankineBlocks.BROWN_TEKTITE.get(), RankineBlocks.GRAY_TEKTITE.get(), RankineBlocks.BLACK_TEKTITE.get());
        Block tektite = tektiteList.get(rand.nextInt(tektiteList.size()));
        buildCrater(levelIn, posIn.above(4), 9, levelIn.getBlockState(posIn.below(5)), tektite, levelIn.getFluidState(posIn).is(FluidTags.WATER));
        MeteorBlocks meteorBlock = (MeteorBlocks.values()[rand.nextInt(MeteorBlocks.values().length)]);
        buildMeteor(levelIn, rand, posIn.below(3), meteorBlock.getMeteorBlock(), !meteorBlock.isMetalBased());
        buildMeteor(levelIn, rand, posIn.below(3).offset(rand.nextInt(2)+1, rand.nextInt(3)-1, rand.nextInt(2)+1), meteorBlock.getMeteorBlock(), !meteorBlock.isMetalBased());
        return true;
    }

    private void buildCrater(WorldGenLevel levelIn, BlockPos posIn, int radius, BlockState groundmass, Block tektite, boolean waterlog) {
        for (BlockPos blockpos : BlockPos.betweenClosed(posIn.offset(-radius, -radius, -radius), posIn.offset(radius, radius, radius))) {
            if (!levelIn.getBlockState(blockpos).is(RankineTags.Blocks.METEORITE_REPLACEABLE)) continue;

            if (blockpos.distSqr(posIn) > radius*radius) continue;
            if (blockpos.distSqr(posIn) > (radius-1)*(radius-1)) {
                float chance = levelIn.getRandom().nextFloat();
                if (chance < 0.3) {
                    levelIn.setBlock(blockpos, tektite.defaultBlockState(), 3);
                } else if (chance < 0.4) {
                    levelIn.setBlock(blockpos, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
                } else if (chance < 0.7) {
                    levelIn.setBlock(blockpos, groundmass, 3);
                }
            } else {
                levelIn.setBlock(blockpos, waterlog ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 2);
            }


        }
    }

    private void buildMeteor(WorldGenLevel reader, RandomSource rand, BlockPos pos, Block METEOR, boolean genLonsdaleite) {
        int j = 1 + rand.nextInt(2);
        int k = 1 + rand.nextInt(2);
        int l = 1 + rand.nextInt(2);
        float f = (float)(j + k + l) * 0.333F + 0.75F;
        for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-j, -k, -l), pos.offset(j, k, l))) {
            if (blockpos.distSqr(pos) <= (double)(f * f)) {
                if (genLonsdaleite && rand.nextFloat() < 0.03) {
                    reader.setBlock(blockpos.below(1), RankineBlocks.LONSDALEITE.get().defaultBlockState(), 2);
                } else {
                    reader.setBlock(blockpos.below(1), METEOR.defaultBlockState(), 2);
                }
            }
        }
    }

}
