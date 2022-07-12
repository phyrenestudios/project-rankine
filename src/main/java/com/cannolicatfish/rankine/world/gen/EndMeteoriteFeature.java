package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Arrays;
import java.util.Random;

public class EndMeteoriteFeature extends Feature<NoneFeatureConfiguration> {
    public EndMeteoriteFeature(Codec<NoneFeatureConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();

        if (rand.nextFloat() < Config.WORLDGEN.END_METEORITE_CHANCE.get()) {
            ChunkAccess chunk = reader.getChunk(pos);
            int randX = chunk.getPos().getMinBlockX() + rand.nextInt(16);
            int randY = rand.nextInt(70) + 20;
            int randZ = chunk.getPos().getMaxBlockZ() + rand.nextInt(16);
            BlockPos POS = new BlockPos(randX, randY, randZ);


            int I1 = rand.nextInt(50)+30;
            int I2 = I1 + rand.nextInt(30)-15;
            int I3 = I1 + rand.nextInt(30)-15;
            double d1 = 1/((double) I1);
            double d2 = 1/((double) I2);
            double d3 = 1/((double) I3);
            double radius = Math.max(I1,Math.max(I2,I3));
            double size = rand.nextDouble();
            boolean frozen = rand.nextBoolean();
            BlockState BLOCK = Arrays.asList(RankineBlocks.METEORITE.get().defaultBlockState(),RankineBlocks.ENSTATITE_CHONDRITE.get().defaultBlockState()).get(rand.nextInt(2));
            BlockState TEKTITE = Arrays.asList(RankineBlocks.BLACK_TEKTITE.get().defaultBlockState(),RankineBlocks.GRAY_TEKTITE.get().defaultBlockState(),RankineBlocks.GREEN_TEKTITE.get().defaultBlockState(),RankineBlocks.BROWN_TEKTITE.get().defaultBlockState()).get(rand.nextInt(4));
            BlockState ORE = Arrays.asList(RankineBlocks.KAMACITE_ORE.get().defaultBlockState(),RankineBlocks.ANTITAENITE_ORE.get().defaultBlockState(),RankineBlocks.TAENITE_ORE.get().defaultBlockState(),RankineBlocks.TETRATAENITE_ORE.get().defaultBlockState()).get(rand.nextInt(4));



            for (BlockPos blockpos1 : BlockPos.betweenClosed(POS.offset(-radius,-radius,-radius),POS.offset(radius,radius,radius))) {
                if (frozen) {
                    if (d1 * Math.pow(blockpos1.getX() - POS.getX(), 2) + d2 * Math.pow(blockpos1.getY() - POS.getY(), 2) + d3 * Math.pow(blockpos1.getZ() - POS.getZ(), 2) <= size) {
                        if (d1 * Math.pow(blockpos1.getX() - POS.getX(), 2) + d2 * Math.pow(blockpos1.getY() - POS.getY(), 2) + d3 * Math.pow(blockpos1.getZ() - POS.getZ(), 2) > size - 0.1*size) {
                            BLOCK = RankineBlocks.FROZEN_METEORITE.get().defaultBlockState();
                        }
                        float chance = rand.nextFloat();
                        if (chance < 0.02) {
                            this.setBlock(reader, blockpos1, RankineBlocks.LONSDALEITE_ORE.get().defaultBlockState().setValue(RankineOreBlock.TYPE,WorldgenUtils.ORE_STONES.indexOf(BLOCK.getBlock())));
                        } else if (chance < 0.1) {
                            this.setBlock(reader, blockpos1, ORE.setValue(RankineOreBlock.TYPE,WorldgenUtils.ORE_STONES.indexOf(BLOCK.getBlock())));
                        } else if (chance < 0.5) {
                            this.setBlock(reader, blockpos1, RankineBlocks.METEORIC_ICE.get().defaultBlockState());
                        } else {
                            this.setBlock(reader, blockpos1, BLOCK);
                        }
                    }
                } else {
                    if (d1 * Math.pow(blockpos1.getX() - POS.getX(), 2) + d2 * Math.pow(blockpos1.getY() - POS.getY(), 2) + d3 * Math.pow(blockpos1.getZ() - POS.getZ(), 2) <= size) {
                        float chance = rand.nextFloat();
                        if (chance < 0.02) {
                            this.setBlock(reader, blockpos1, RankineBlocks.LONSDALEITE_ORE.get().defaultBlockState().setValue(RankineOreBlock.TYPE,WorldgenUtils.ORE_STONES.indexOf(BLOCK.getBlock())));
                        } else if (chance < 0.1) {
                            this.setBlock(reader, blockpos1, ORE.setValue(RankineOreBlock.TYPE,WorldgenUtils.ORE_STONES.indexOf(BLOCK.getBlock())));
                        } else if (chance < 0.5) {
                            this.setBlock(reader, blockpos1, TEKTITE);
                        } else {
                            this.setBlock(reader, blockpos1, BLOCK);
                        }
                    }
                }
            }
        }
        return true;
    }

}
