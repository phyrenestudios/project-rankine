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

public class ArtistsConkMushroomFeature extends Feature<BlockStateProvidingFeatureConfig> {


    public ArtistsConkMushroomFeature(Codec<BlockStateProvidingFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config) {
        return false;
    }

    public static boolean growMushroom(ISeedReader reader, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config, Direction dir) {
        boolean flag = true;
        for (BlockPos b : BlockPos.betweenClosed(pos.relative(dir.getClockWise()),pos.relative(dir).relative(dir.getCounterClockWise()))) {
            if (!WorldgenUtils.isAirOrLeaves(reader,b)) {
                flag = false;
            }
        }

        if (flag) {
            BlockState state = config.stateProvider.getState(rand, pos);
            Direction dir2 = rand.nextBoolean() ? dir.getClockWise() : dir.getCounterClockWise();

            reader.setBlock(pos, state, 3);
            reader.setBlock(pos.relative(dir2), state, 3);
            reader.setBlock(pos.relative(dir2.getOpposite()), state, 3);
            reader.setBlock(pos.relative(dir2).relative(dir2), state, 3);
            reader.setBlock(pos.relative(dir2).relative(dir.getOpposite()), state, 3);
            reader.setBlock(pos.relative(dir), state, 3);
            reader.setBlock(pos.relative(dir).relative(dir2), state, 3);

            return true;
        }
        return false;
    }
}
