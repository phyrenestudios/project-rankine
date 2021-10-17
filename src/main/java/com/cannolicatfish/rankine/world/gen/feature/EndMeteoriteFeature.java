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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Arrays;
import java.util.Random;

public class EndMeteoriteFeature extends Feature<NoFeatureConfig> {
    public EndMeteoriteFeature(Codec<NoFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {


        if (rand.nextFloat() < 0.05) {
            IChunk chunk = reader.getChunk(pos);
            int randX = chunk.getPos().getXStart() + rand.nextInt(16);
            int randY = rand.nextInt(70) + 20;
            int randZ = chunk.getPos().getZEnd() + rand.nextInt(16);
            BlockPos POS = new BlockPos(randX, randY, randZ);


            int I1 = rand.nextInt(100)+2;
            int I2 = rand.nextInt(100)+2;
            int I3 = rand.nextInt(100)+2;
            double d1 = 1/((double) I1);
            double d2 = 1/((double) I2);
            double d3 = 1/((double) I3);
            double radius = Math.max(I1,Math.max(I2,I3));
            double size = rand.nextDouble();
            boolean frozen = rand.nextBoolean();
            BlockState BLOCK = Arrays.asList(RankineBlocks.METEORITE.get().getDefaultState(),RankineBlocks.ENSTATITE.get().getDefaultState()).get(rand.nextInt(2));
            BlockState TEKTITE = Arrays.asList(RankineBlocks.BLACK_TEKTITE.get().getDefaultState(),RankineBlocks.GRAY_TEKTITE.get().getDefaultState(),RankineBlocks.GREEN_TEKTITE.get().getDefaultState(),RankineBlocks.BROWN_TEKTITE.get().getDefaultState()).get(rand.nextInt(4));
            BlockState ORE = Arrays.asList(RankineBlocks.KAMACITE_ORE.get().getDefaultState(),RankineBlocks.ANTITAENITE_ORE.get().getDefaultState(),RankineBlocks.TAENITE_ORE.get().getDefaultState(),RankineBlocks.TETRATAENITE_ORE.get().getDefaultState()).get(rand.nextInt(4));



            for (BlockPos blockpos1 : BlockPos.getAllInBoxMutable(POS.add(-radius,-radius,-radius),POS.add(radius,radius,radius))) {
                if (frozen) {
                    if (d1 * Math.pow(blockpos1.getX() - POS.getX(), 2) + d2 * Math.pow(blockpos1.getY() - POS.getY(), 2) + d3 * Math.pow(blockpos1.getZ() - POS.getZ(), 2) <= size) {
                        if (d1 * Math.pow(blockpos1.getX() - POS.getX(), 2) + d2 * Math.pow(blockpos1.getY() - POS.getY(), 2) + d3 * Math.pow(blockpos1.getZ() - POS.getZ(), 2) > size - 0.1*size) {
                            BLOCK = RankineBlocks.FROZEN_METEORITE.get().getDefaultState();
                        }
                        float chance = rand.nextFloat();
                        if (chance < 0.02) {
                            this.setBlockState(reader, blockpos1, RankineBlocks.LONSDALEITE_ORE.get().getDefaultState().with(RankineOreBlock.TYPE,WorldgenUtils.ORE_STONES.indexOf(BLOCK.getBlock())));
                        } else if (chance < 0.07) {
                            this.setBlockState(reader, blockpos1, ORE.with(RankineOreBlock.TYPE,WorldgenUtils.ORE_STONES.indexOf(BLOCK.getBlock())));
                        } else if (chance < 0.2) {
                            this.setBlockState(reader, blockpos1, RankineBlocks.METEORIC_ICE.get().getDefaultState());
                        } else {
                            this.setBlockState(reader, blockpos1, BLOCK);
                        }
                    }
                } else {
                    if (d1 * Math.pow(blockpos1.getX() - POS.getX(), 2) + d2 * Math.pow(blockpos1.getY() - POS.getY(), 2) + d3 * Math.pow(blockpos1.getZ() - POS.getZ(), 2) <= size) {
                        float chance = rand.nextFloat();
                        if (chance < 0.02) {
                            this.setBlockState(reader, blockpos1, RankineBlocks.LONSDALEITE_ORE.get().getDefaultState().with(RankineOreBlock.TYPE,WorldgenUtils.ORE_STONES.indexOf(BLOCK.getBlock())));
                        } else if (chance < 0.07) {
                            this.setBlockState(reader, blockpos1, ORE.with(RankineOreBlock.TYPE,WorldgenUtils.ORE_STONES.indexOf(BLOCK.getBlock())));
                        } else if (chance < 0.2) {
                            this.setBlockState(reader, blockpos1, TEKTITE);
                        } else {
                            this.setBlockState(reader, blockpos1, BLOCK);
                        }
                    }
                }
            }
        }
        return true;
    }

}
