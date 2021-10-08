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

public class EndMeteoriteFeature extends Feature<NoFeatureConfig> {
    public EndMeteoriteFeature(Codec<NoFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {


        if (rand.nextFloat() < 0.1) {
            IChunk chunk = reader.getChunk(pos);
            int randX = chunk.getPos().getXStart() + rand.nextInt(16);
            int randY = rand.nextInt(70) + 20;
            int randZ = chunk.getPos().getZEnd() + rand.nextInt(16);
            BlockPos POS = new BlockPos(randX, randY, randZ);

            int I1 = rand.nextInt(5)+4;
            int I2 = rand.nextInt(5)+4;
            int I3 = rand.nextInt(5)+4;

            for (BlockPos blockpos1 : BlockPos.getProximitySortedBoxPositionsIterator(POS,I1+1,I2+1,I3+1)) {
                if (Math.pow(blockpos1.getX()/I1,2) + Math.pow(blockpos1.getY()/I2,2) + Math.pow(blockpos1.getZ()/I3,2) <= 5) {
                    break;
                }

                BlockState bs = reader.getBlockState(blockpos1);
                //if (bs.matchesBlock(block)) {
                this.setBlockState(reader, blockpos1, RankineBlocks.METEORITE.get().getDefaultState());
                //lag = true;
                //}

            }
        }
        return true;
    }






/*

        BlockState ORE;
        float CHANCE1 = rand.nextFloat();
        if (CHANCE1 < 0.25F) {
            ORE = RankineBlocks.KAMACITE_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(RankineBlocks.METEORITE.get()));
        } else if (CHANCE1 < 0.50F) {
            ORE = RankineBlocks.ANTITAENITE_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(RankineBlocks.METEORITE.get()));
        } else if (CHANCE1 < 0.75F) {
            ORE = RankineBlocks.TAENITE_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(RankineBlocks.METEORITE.get()));
        } else {
            ORE = RankineBlocks.TETRATAENITE_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(RankineBlocks.METEORITE.get()));
        }

        if (rand.nextFloat() < WGConfig.MISC.BIG_METEORITE_CHANCE.get().floatValue()) {
            BlockState TEKTITE;
            float CHANCE2 = rand.nextFloat();
            if (CHANCE2 < 0.25F) {
                TEKTITE = RankineBlocks.GREEN_TEKTITE.get().getDefaultState();
            } else if (CHANCE2 < 0.50F) {
                TEKTITE = RankineBlocks.BROWN_TEKTITE.get().getDefaultState();
            } else if (CHANCE2 < 0.75F) {
                TEKTITE = RankineBlocks.GRAY_TEKTITE.get().getDefaultState();
            } else {
                TEKTITE = RankineBlocks.BLACK_TEKTITE.get().getDefaultState();
            }

            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-5, -5, -5), pos.add(5, 0, 5))) {
                if (blockpos.distanceSq(pos.up(2)) <= 25.0) {
                    reader.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 4);
                }
            }
            buildMeteor(reader, rand, pos, ORE, TEKTITE);
            BlockPos newpos = pos.add(rand.nextInt(2)+1, rand.nextInt(3)-1, rand.nextInt(2)+1);
            buildMeteor(reader, rand, newpos, ORE, TEKTITE);



        } else {
            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
                if (blockpos.distanceSq(pos) <= (2.75D)) {
                    if (rand.nextFloat() < 0.3F) {
                        reader.setBlockState(blockpos.down(1), ORE, 4);
                    } else {
                        reader.setBlockState(blockpos.down(1), RankineBlocks.METEORITE.get().getDefaultState(), 4);
                    }
                } else if (blockpos.distanceSq(pos.up(2)) <= (16.0D)) {
                    reader.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 4);
                }
            }
        }

 */


    /*
    private void buildMeteor(ISeedReader reader, Random rand, BlockPos pos, BlockState ORE, BlockState TEKTITE) {
        int j = WGConfig.MISC.METEORITE_SIZE.get() + rand.nextInt(2);
        int k = WGConfig.MISC.METEORITE_SIZE.get() + rand.nextInt(2);
        int l = WGConfig.MISC.METEORITE_SIZE.get() + rand.nextInt(2);
        float f = (float)(j + k + l) * 0.333F + 0.75F;
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-j, -k, -l), pos.add(j, k, l))) {
            if (blockpos.distanceSq(pos) <= (double)(f * f)) {
                if (rand.nextFloat() < 0.2F) {
                    reader.setBlockState(blockpos.down(1), TEKTITE, 4);
                } else if (rand.nextFloat() < 0.4F) {
                    reader.setBlockState(blockpos.down(1), ORE, 4);
                } else {
                    reader.setBlockState(blockpos.down(1), RankineBlocks.METEORITE.get().getDefaultState(), 4);
                }
            }
        }
    }

     */




}
