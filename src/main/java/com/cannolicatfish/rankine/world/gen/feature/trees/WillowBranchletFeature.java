package com.cannolicatfish.rankine.world.gen.feature.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class WillowBranchletFeature extends Feature<NoFeatureConfig> {
    private static final Direction[] field_236426_a_ = Direction.values();

    public WillowBranchletFeature(Codec<NoFeatureConfig> p_i232004_1_) {
        super(p_i232004_1_);
    }

    public boolean generate(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {
        if (!p_241855_1_.isAirBlock(p_241855_4_)) {
            return false;
        } else {
            BlockState lvt_6_1_ = p_241855_1_.getBlockState(p_241855_4_.up());
            if (!lvt_6_1_.matchesBlock(Blocks.NETHERRACK) && !lvt_6_1_.matchesBlock(Blocks.NETHER_WART_BLOCK)) {
                return false;
            } else {
                this.func_236428_a_(p_241855_1_, p_241855_3_, p_241855_4_);
                this.func_236429_b_(p_241855_1_, p_241855_3_, p_241855_4_);
                return true;
            }
        }
    }

    private void func_236428_a_(IWorld p_236428_1_, Random p_236428_2_, BlockPos p_236428_3_) {
        p_236428_1_.setBlockState(p_236428_3_, Blocks.NETHER_WART_BLOCK.getDefaultState(), 2);
        BlockPos.Mutable lvt_4_1_ = new BlockPos.Mutable();
        BlockPos.Mutable lvt_5_1_ = new BlockPos.Mutable();

        for(int lvt_6_1_ = 0; lvt_6_1_ < 200; ++lvt_6_1_) {
            lvt_4_1_.setAndOffset(p_236428_3_, p_236428_2_.nextInt(6) - p_236428_2_.nextInt(6), p_236428_2_.nextInt(2) - p_236428_2_.nextInt(5), p_236428_2_.nextInt(6) - p_236428_2_.nextInt(6));
            if (p_236428_1_.isAirBlock(lvt_4_1_)) {
                int lvt_7_1_ = 0;
                Direction[] var8 = field_236426_a_;
                int var9 = var8.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    Direction lvt_11_1_ = var8[var10];
                    BlockState lvt_12_1_ = p_236428_1_.getBlockState(lvt_5_1_.setAndMove(lvt_4_1_, lvt_11_1_));
                    if (lvt_12_1_.matchesBlock(Blocks.NETHERRACK) || lvt_12_1_.matchesBlock(Blocks.NETHER_WART_BLOCK)) {
                        ++lvt_7_1_;
                    }

                    if (lvt_7_1_ > 1) {
                        break;
                    }
                }

                if (lvt_7_1_ == 1) {
                    p_236428_1_.setBlockState(lvt_4_1_, Blocks.NETHER_WART_BLOCK.getDefaultState(), 2);
                }
            }
        }

    }

    private void func_236429_b_(IWorld p_236429_1_, Random p_236429_2_, BlockPos p_236429_3_) {
        BlockPos.Mutable lvt_4_1_ = new BlockPos.Mutable();

        for(int lvt_5_1_ = 0; lvt_5_1_ < 100; ++lvt_5_1_) {
            lvt_4_1_.setAndOffset(p_236429_3_, p_236429_2_.nextInt(8) - p_236429_2_.nextInt(8), p_236429_2_.nextInt(2) - p_236429_2_.nextInt(7), p_236429_2_.nextInt(8) - p_236429_2_.nextInt(8));
            if (p_236429_1_.isAirBlock(lvt_4_1_)) {
                BlockState lvt_6_1_ = p_236429_1_.getBlockState(lvt_4_1_.up());
                if (lvt_6_1_.matchesBlock(RankineBlocks.WEEPING_WILLOW_LEAVES.get())) {
                    int lvt_7_1_ = MathHelper.nextInt(p_236429_2_, 1, 8);
                    if (p_236429_2_.nextInt(6) == 0) {
                        lvt_7_1_ *= 2;
                    }

                    if (p_236429_2_.nextInt(5) == 0) {
                        lvt_7_1_ = 1;
                    }

                    //int lvt_8_1_ = true;
                    //int lvt_9_1_ = true;
                    func_236427_a_(p_236429_1_, p_236429_2_, lvt_4_1_, lvt_7_1_, 17, 25);
                }
            }
        }

    }

    public static void func_236427_a_(IWorld p_236427_0_, Random p_236427_1_, BlockPos.Mutable p_236427_2_, int p_236427_3_, int p_236427_4_, int p_236427_5_) {
        for(int lvt_6_1_ = 0; lvt_6_1_ <= p_236427_3_; ++lvt_6_1_) {
            if (p_236427_0_.isAirBlock(p_236427_2_)) {
                if (lvt_6_1_ == p_236427_3_ || !p_236427_0_.isAirBlock(p_236427_2_.down())) {
                    p_236427_0_.setBlockState(p_236427_2_, (BlockState) RankineBlocks.WILLOW_BRANCHLET.get().getDefaultState().with(AbstractTopPlantBlock.AGE, MathHelper.nextInt(p_236427_1_, p_236427_4_, p_236427_5_)), 2);
                    break;
                }

                p_236427_0_.setBlockState(p_236427_2_, RankineBlocks.WILLOW_BRANCHLET_PLANT.get().getDefaultState(), 2);
            }

            p_236427_2_.move(Direction.DOWN);
        }

    }
}
