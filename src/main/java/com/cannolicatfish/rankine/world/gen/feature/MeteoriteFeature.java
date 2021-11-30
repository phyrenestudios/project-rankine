package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class MeteoriteFeature extends Feature<MeteoriteFeatureConfig> {
    public MeteoriteFeature(Codec<MeteoriteFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, MeteoriteFeatureConfig config) {

        if (!RankineTags.Blocks.METEORITE_REPLACEABLE.contains(reader.getBlockState(pos.down()).getBlock())) {
            return false;
        }
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


        if (rand.nextFloat() < Config.MISC_WORLDGEN.BIG_METEORITE_CHANCE.get().floatValue()) {
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

            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-5, -4, -5), pos.add(5, 2, 5))) {
                if (blockpos.distanceSq(pos.up(2)) <= 25.0) {
                    reader.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 4);
                }
            }
            buildMeteor(reader, rand, pos, ORE, TEKTITE);
            BlockPos newpos = pos.add(rand.nextInt(2)+1, rand.nextInt(3)-1, rand.nextInt(2)+1);
            buildMeteor(reader, rand, newpos, ORE, TEKTITE);



        } else {
            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -4, -4), pos.add(4, 6, 4))) {
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
        return true;
    }

    private void buildMeteor(ISeedReader reader, Random rand, BlockPos pos, BlockState ORE, BlockState TEKTITE) {
        int j = Config.MISC_WORLDGEN.METEORITE_SIZE.get() + rand.nextInt(2);
        int k = Config.MISC_WORLDGEN.METEORITE_SIZE.get() + rand.nextInt(2);
        int l = Config.MISC_WORLDGEN.METEORITE_SIZE.get() + rand.nextInt(2);
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

}
