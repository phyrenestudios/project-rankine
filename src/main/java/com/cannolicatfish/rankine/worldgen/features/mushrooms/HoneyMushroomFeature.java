package com.cannolicatfish.rankine.worldgen.features.mushrooms;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HoneyMushroomFeature extends AbstractWallMushroomFeature {

    public HoneyMushroomFeature(Codec<BlockPileConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    protected List<BlockPos> validDir(WorldGenLevel levelIn, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            boolean flag = true;
            if (dir.getAxis().equals(Direction.Axis.Y)) continue;
            List<BlockPos> blockList = Arrays.asList(pos, pos.above(), pos.above().relative(dir.getCounterClockWise()),pos.above().relative(dir.getClockWise()),pos.above().relative(dir));
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
