package com.cannolicatfish.rankine.worldgen.features.mushrooms;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LionsManeMushroomFeature extends AbstractWallMushroomFeature {

    public LionsManeMushroomFeature(Codec<BlockPileConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    protected List<BlockPos> validDir(WorldGenLevel levelIn, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            boolean flag = true;
            if (dir.getAxis().equals(Direction.Axis.Y)) continue;
            Direction dir2 = levelIn.getRandom().nextBoolean() ? dir.getClockWise() : dir.getCounterClockWise();
            List<BlockPos> blockList = BlockPos.betweenClosedStream(pos,pos.above().relative(dir).relative(dir2)).collect(Collectors.toList());
            for (BlockPos b : blockList) {
                if (!TreeFeature.validTreePos(levelIn, b)) {
                    flag = false;
                    break;
                }
            }
            if (flag) return blockList;
        }
        return Collections.emptyList();
    }
}
