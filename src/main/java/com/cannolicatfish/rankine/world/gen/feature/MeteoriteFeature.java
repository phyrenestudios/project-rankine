package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;
import java.util.function.Function;

public class MeteoriteFeature extends Feature<MeteoriteFeatureConfig> {
    public MeteoriteFeature(Codec<MeteoriteFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    public boolean func_230362_a_(ISeedReader worldIn, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random rand, BlockPos pos, MeteoriteFeatureConfig config) {
        while(true) {
            label48: {
                if (pos.getY() > 3) {
                    if (worldIn.isAirBlock(pos.down())) {
                        break label48;
                    }

                    Block block = worldIn.getBlockState(pos.down()).getBlock();
                    if (!isDirt(block) && !isStone(block)) {
                        break label48;
                    }
                }

                if (pos.getY() <= 3) {
                    return false;
                }

                BlockState ORE;
                float CHANCE = rand.nextFloat();
                if (CHANCE < 0.2F) {
                    ORE = ModBlocks.KAMACITE.getDefaultState();
                } else if (CHANCE < 0.4F) {
                    ORE = ModBlocks.ANTITAENITE.getDefaultState();
                } else if (CHANCE < 0.6F) {
                    ORE = ModBlocks.TAENITE.getDefaultState();
                } else if (CHANCE < 0.8F) {
                    ORE = ModBlocks.TETRATAENITE.getDefaultState();
                } else {
                    ORE = ModBlocks.METEORITE.getDefaultState();
                }

                //for(int i = 0; i1 >= 0 && i < 1; ++i) {
                int j = 1 + rand.nextInt(3);
                int k = 1 + rand.nextInt(3);
                int l = 1 + rand.nextInt(3);
                float f = (float)(j + k + l) * 0.333F + 0.75F;
                int AIR = 3 * (j + k + l)/3;
                for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-AIR, -k+1, -AIR), pos.add(AIR, AIR, AIR))) {
                    if (blockpos.distanceSq(pos) <= (double)(AIR * AIR)) {
                        if (worldIn.getBlockState(pos).getBlock() == Blocks.AIR) {
                            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 4);
                        }
                    }
                }
                for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-j, -k, -l), pos.add(j, k, l))) {
                    if (blockpos.distanceSq(pos) <= (double)(f * f)) {
                        if (rand.nextFloat() < 0.5F) {
                            worldIn.setBlockState(blockpos.down(3), ORE, 4);
                        } else {
                            worldIn.setBlockState(blockpos.down(3), ModBlocks.METEORITE.getDefaultState(), 4);
                        }
                    }
                }


                    //pos = pos.add(-(i1 + 1) + rand.nextInt(2 + i1 * 2), -rand.nextInt(2), -(i1 + 1) + rand.nextInt(2 + i1 * 2));
                //}

                return true;
            }

            pos = pos.down();
        }
    }
}
