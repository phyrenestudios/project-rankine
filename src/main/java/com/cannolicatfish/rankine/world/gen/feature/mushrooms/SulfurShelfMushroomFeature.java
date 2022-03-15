package com.cannolicatfish.rankine.world.gen.feature.mushrooms;

import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SulfurShelfMushroomFeature extends Feature<BlockStateProvidingFeatureConfig> {


    public SulfurShelfMushroomFeature(Codec<BlockStateProvidingFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config) {
        return false;
    }

    public static boolean growMushroom(ISeedReader reader, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config, Direction dir) {
        boolean flag = true;
        for (BlockPos b : BlockPos.betweenClosed(pos.relative(dir.getClockWise(),2).below(),pos.relative(dir).relative(dir.getCounterClockWise(),2).above())) {
            if (!WorldgenUtils.isAirOrLeaves(reader,b)) {
                flag = false;
            }
        }

        if (flag) {
            BlockState state = config.stateProvider.getState(rand, pos);

            build(reader,pos.relative(dir),state,dir.getClockWise(), rand.nextInt(2)+1);
            build(reader,pos.relative(dir),state,dir.getCounterClockWise(), rand.nextInt(2)+1);
            build(reader,pos.above(),state,dir.getClockWise(), rand.nextInt(2)+1);
            build(reader,pos.above(),state,dir.getCounterClockWise(), rand.nextInt(2)+1);
            build(reader,pos.below(),state,dir.getClockWise(), rand.nextInt(2)+1);
            build(reader,pos.below(),state,dir.getCounterClockWise(), rand.nextInt(2)+1);

            return true;
        }
        return false;
    }

    private static void build(ISeedReader reader, BlockPos pos, BlockState state, Direction dir, int length) {
        for (int i = 0; i <= length; ++i) {
            reader.setBlock(pos.relative(dir,i),state,19);
        }
    }
}
