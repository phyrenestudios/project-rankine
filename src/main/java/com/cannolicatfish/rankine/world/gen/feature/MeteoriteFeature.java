package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
        while(true) {
            label48: {
                if (pos.getY() > 3) {
                    if (reader.isAirBlock(pos.down())) {
                        break label48;
                    }

                    Block block = reader.getBlockState(pos.down()).getBlock();
                    if (!isDirt(block) && !isStone(block)) {
                        break label48;
                    }
                }

                if (pos.getY() <= 50) {
                    return false;
                }

                BlockState ORE;
                float CHANCE = rand.nextFloat();
                if (CHANCE < 0.25F) {
                    ORE = RankineBlocks.KAMACITE.get().getDefaultState();
                } else if (CHANCE < 0.50F) {
                    ORE = RankineBlocks.ANTITAENITE.get().getDefaultState();
                } else if (CHANCE < 0.75F) {
                    ORE = RankineBlocks.TAENITE.get().getDefaultState();
                } else {
                    ORE = RankineBlocks.TETRATAENITE.get().getDefaultState();
                }

                int j = 1 + rand.nextInt(2);
                int k = 1 + rand.nextInt(2);
                int l = 1 + rand.nextInt(2);
                float f = (float)(j + k + l) * 0.333F + 0.75F;
                for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-j, -k, -l), pos.add(j, k, l))) {
                    if (blockpos.distanceSq(pos) <= (double)(f * f)) {
                        if (rand.nextFloat() < 0.4F) {
                            reader.setBlockState(blockpos.down(1), ORE, 4);
                        } else {
                            reader.setBlockState(blockpos.down(1), RankineBlocks.METEORITE.get().getDefaultState(), 4);
                        }
                    }
                }
                return true;
            }

            pos = pos.down();
        }
    }
}
