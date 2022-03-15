package com.cannolicatfish.rankine.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SphereReplaceConfig;

import java.util.Random;

public class LandDiskFeature extends Feature<SphereReplaceConfig> {
    public LandDiskFeature(Codec<SphereReplaceConfig> codec) {
        super(codec);
        }

public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, SphereReplaceConfig config) {
    boolean flag = false;
    int i = config.radius.sample(rand);

    for(int j = pos.getX() - i; j <= pos.getX() + i; ++j) {
        for(int k = pos.getZ() - i; k <= pos.getZ() + i; ++k) {
            int l = j - pos.getX();
            int i1 = k - pos.getZ();
            if (l * l + i1 * i1 <= i * i) {
                for(int j1 = pos.getY() - config.halfHeight; j1 <= pos.getY() + config.halfHeight; ++j1) {
                    BlockPos blockpos = new BlockPos(j, j1, k);
                    Block block = reader.getBlockState(blockpos).getBlock();

                    for(BlockState blockstate : config.targets) {
                        if (blockstate.is(block)) {
                            reader.setBlock(blockpos, config.state, 2);
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    return flag;
    }
}
