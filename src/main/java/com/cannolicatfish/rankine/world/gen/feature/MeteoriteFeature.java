package com.cannolicatfish.rankine.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;
import java.util.function.Function;

public class MeteoriteFeature extends Feature<BlockBlobConfig> {
    public MeteoriteFeature(Codec<BlockBlobConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    public boolean func_230362_a_(ISeedReader worldIn, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random rand, BlockPos pos, BlockBlobConfig config) {
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

                int i1 = config.startRadius;

                for(int i = 0; i1 >= 0 && i < 3; ++i) {
                    int j = i1 + rand.nextInt(3);
                    int k = i1 + rand.nextInt(3);
                    int l = i1 + rand.nextInt(3);
                    float f = (float)(j + k + l) * 0.333F + 0.5F;

                    for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-j, -k, -l), pos.add(j, k, l))) {
                        if (blockpos.distanceSq(pos) <= (double)(f * f)) {
                            worldIn.setBlockState(blockpos, config.state, 4);
                        }
                    }

                    pos = pos.add(-(i1 + 1) + rand.nextInt(2 + i1 * 2), -rand.nextInt(2), -(i1 + 1) + rand.nextInt(2 + i1 * 2));
                }

                return true;
            }

            pos = pos.down();
        }
    }
}
