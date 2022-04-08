package com.cannolicatfish.rankine.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;

import java.util.Random;

public class LandDiskFeature extends Feature<DiskConfiguration> {
    public LandDiskFeature(Codec<DiskConfiguration> codec) {
        super(codec);
        }

    @Override
    public boolean place(FeaturePlaceContext<DiskConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();
        DiskConfiguration config = p_159749_.config();
    boolean flag = false;
    int i = config.radius().sample(rand);

    for(int j = pos.getX() - i; j <= pos.getX() + i; ++j) {
        for(int k = pos.getZ() - i; k <= pos.getZ() + i; ++k) {
            int l = j - pos.getX();
            int i1 = k - pos.getZ();
            if (l * l + i1 * i1 <= i * i) {
                for(int j1 = pos.getY() - config.halfHeight(); j1 <= pos.getY() + config.halfHeight(); ++j1) {
                    BlockPos blockpos = new BlockPos(j, j1, k);
                    Block block = reader.getBlockState(blockpos).getBlock();

                    for(BlockState blockstate : config.targets()) {
                        if (blockstate.is(block)) {
                            reader.setBlock(blockpos, config.state(), 2);
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
