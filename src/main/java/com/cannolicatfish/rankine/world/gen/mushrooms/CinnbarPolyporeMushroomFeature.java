package com.cannolicatfish.rankine.world.gen.mushrooms;

import com.cannolicatfish.rankine.blocks.mushrooms.RankineWallMushroomBlock;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

import java.util.Random;

public class CinnbarPolyporeMushroomFeature extends Feature<BlockPileConfiguration> {


    public CinnbarPolyporeMushroomFeature(Codec<BlockPileConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockPileConfiguration> config) {
        WorldGenLevel reader = config.level();
        BlockPos pos = config.origin();
        Random rand = reader.getRandom();
        return growMushroom(reader, reader.getRandom(), pos, config.config());
    }

    public static boolean growMushroom(WorldGenLevel reader, Random rand, BlockPos pos, BlockPileConfiguration config) {
        boolean flag = true;
        Direction dir = reader.getBlockState(pos).getValue(RankineWallMushroomBlock.HORIZONTAL_FACING);
        for (BlockPos b : BlockPos.betweenClosed(pos.relative(dir.getClockWise()),pos.relative(dir).relative(dir.getCounterClockWise()))) {
            if (!WorldgenUtils.isAirOrLeaves(reader,b)) {
                flag = false;
            }
        }

        if (flag) {
            BlockState state = config.stateProvider.getState(rand, pos);
            reader.setBlock(pos, state, 3);
            reader.setBlock(pos.relative(dir.getCounterClockWise()), state, 3);
            reader.setBlock(pos.relative(dir.getClockWise()), state, 3);
            reader.setBlock(pos.relative(dir), state, 3);


            return true;
        }
        return false;
    }
}
