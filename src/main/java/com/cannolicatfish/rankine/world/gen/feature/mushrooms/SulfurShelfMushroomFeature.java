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

public class SulfurShelfMushroomFeature extends Feature<BlockPileConfiguration> {


    public SulfurShelfMushroomFeature(Codec<BlockPileConfiguration> configFactoryIn) {
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

    private static void build(WorldGenLevel reader, BlockPos pos, BlockState state, Direction dir, int length) {
        for (int i = 0; i <= length; ++i) {
            reader.setBlock(pos.relative(dir,i),state,19);
        }
    }
}
