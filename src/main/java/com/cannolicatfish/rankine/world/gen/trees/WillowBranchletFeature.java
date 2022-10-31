package com.cannolicatfish.rankine.world.gen.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class WillowBranchletFeature extends Feature<NoneFeatureConfiguration> {
    private static final Direction[] DIRECTIONS = Direction.values();

    public WillowBranchletFeature(Codec<NoneFeatureConfiguration> p_i232004_1_) {
        super(p_i232004_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();
        if (!reader.isEmptyBlock(pos)) {
            return false;
        } else {
            BlockState lvt_6_1_ = reader.getBlockState(pos.above());
            if (!lvt_6_1_.is(Blocks.NETHERRACK) && !lvt_6_1_.is(Blocks.NETHER_WART_BLOCK)) {
                return false;
            } else {
                this.placeRoofNetherWart(reader, rand, pos);
                this.placeRoofWeepingVines(reader, rand, pos);
                return true;
            }
        }
    }

    private void placeRoofNetherWart(LevelAccessor p_236428_1_, Random p_236428_2_, BlockPos p_236428_3_) {
        p_236428_1_.setBlock(p_236428_3_, Blocks.NETHER_WART_BLOCK.defaultBlockState(), 2);
        BlockPos.MutableBlockPos lvt_4_1_ = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos lvt_5_1_ = new BlockPos.MutableBlockPos();

        for(int lvt_6_1_ = 0; lvt_6_1_ < 200; ++lvt_6_1_) {
            lvt_4_1_.setWithOffset(p_236428_3_, p_236428_2_.nextInt(6) - p_236428_2_.nextInt(6), p_236428_2_.nextInt(2) - p_236428_2_.nextInt(5), p_236428_2_.nextInt(6) - p_236428_2_.nextInt(6));
            if (p_236428_1_.isEmptyBlock(lvt_4_1_)) {
                int lvt_7_1_ = 0;
                Direction[] var8 = DIRECTIONS;
                int var9 = var8.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    Direction lvt_11_1_ = var8[var10];
                    BlockState lvt_12_1_ = p_236428_1_.getBlockState(lvt_5_1_.setWithOffset(lvt_4_1_, lvt_11_1_));
                    if (lvt_12_1_.is(Blocks.NETHERRACK) || lvt_12_1_.is(Blocks.NETHER_WART_BLOCK)) {
                        ++lvt_7_1_;
                    }

                    if (lvt_7_1_ > 1) {
                        break;
                    }
                }

                if (lvt_7_1_ == 1) {
                    p_236428_1_.setBlock(lvt_4_1_, Blocks.NETHER_WART_BLOCK.defaultBlockState(), 2);
                }
            }
        }

    }

    private void placeRoofWeepingVines(LevelAccessor p_236429_1_, Random p_236429_2_, BlockPos p_236429_3_) {
        BlockPos.MutableBlockPos lvt_4_1_ = new BlockPos.MutableBlockPos();

        for(int lvt_5_1_ = 0; lvt_5_1_ < 100; ++lvt_5_1_) {
            lvt_4_1_.setWithOffset(p_236429_3_, p_236429_2_.nextInt(8) - p_236429_2_.nextInt(8), p_236429_2_.nextInt(2) - p_236429_2_.nextInt(7), p_236429_2_.nextInt(8) - p_236429_2_.nextInt(8));
            if (p_236429_1_.isEmptyBlock(lvt_4_1_)) {
                BlockState lvt_6_1_ = p_236429_1_.getBlockState(lvt_4_1_.above());
                if (lvt_6_1_.is(RankineBlocks.WEEPING_WILLOW.getLeaves())) {
                    int lvt_7_1_ = Mth.nextInt(p_236429_2_, 1, 8);
                    if (p_236429_2_.nextInt(6) == 0) {
                        lvt_7_1_ *= 2;
                    }

                    if (p_236429_2_.nextInt(5) == 0) {
                        lvt_7_1_ = 1;
                    }

                    //int lvt_8_1_ = true;
                    //int lvt_9_1_ = true;
                    placeWeepingVinesColumn(p_236429_1_, p_236429_2_, lvt_4_1_, lvt_7_1_, 17, 25);
                }
            }
        }

    }

    public static void placeWeepingVinesColumn(LevelAccessor p_236427_0_, Random p_236427_1_, BlockPos.MutableBlockPos p_236427_2_, int p_236427_3_, int p_236427_4_, int p_236427_5_) {
        for(int lvt_6_1_ = 0; lvt_6_1_ <= p_236427_3_; ++lvt_6_1_) {
            if (p_236427_0_.isEmptyBlock(p_236427_2_)) {
                if (lvt_6_1_ == p_236427_3_ || !p_236427_0_.isEmptyBlock(p_236427_2_.below())) {
                    p_236427_0_.setBlock(p_236427_2_, RankineBlocks.WILLOW_BRANCHLET.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Mth.nextInt(p_236427_1_, p_236427_4_, p_236427_5_)), 2);
                    break;
                }

                p_236427_0_.setBlock(p_236427_2_, RankineBlocks.WILLOW_BRANCHLET_PLANT.get().defaultBlockState(), 2);
            }

            p_236427_2_.move(Direction.DOWN);
        }

    }
}
