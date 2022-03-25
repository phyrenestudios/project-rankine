package com.cannolicatfish.rankine.world.gen.feature.mushrooms;

import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.Random;

public class LionsManeMushroomFeature extends Feature<BlockPileConfiguration> {


    public LionsManeMushroomFeature(Codec<BlockPileConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockPileConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();
        BlockPileConfiguration config = p_159749_.config();
        return false;
    }

    public static boolean growMushroom(WorldGenLevel reader, Random rand, BlockPos pos, BlockPileConfiguration config, Direction dir) {
        boolean flag = true;
        for (BlockPos b : BlockPos.betweenClosed(pos.relative(dir.getClockWise()),pos.relative(dir).relative(dir.getCounterClockWise()).above())) {
            if (!WorldgenUtils.isAirOrLeaves(reader,b)) {
                flag = false;
            }
        }

        if (flag) {
            BlockState state = config.stateProvider.getState(rand, pos);
            Direction dir2 = rand.nextBoolean() ? dir.getClockWise() : dir.getCounterClockWise();

            reader.setBlock(pos, state, 3);
            reader.setBlock(pos.above(), state, 3);
            reader.setBlock(pos.relative(dir2), state, 3);
            reader.setBlock(pos.above().relative(dir2), state, 3);

            reader.setBlock(pos.relative(dir), state, 3);
            reader.setBlock(pos.above().relative(dir), state, 3);
            reader.setBlock(pos.relative(dir).relative(dir2), state, 3);
            reader.setBlock(pos.above().relative(dir).relative(dir2), state, 3);

            return true;
        }
        return false;
    }
}
