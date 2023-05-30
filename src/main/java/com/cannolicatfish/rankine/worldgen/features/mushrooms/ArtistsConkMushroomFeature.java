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

public class ArtistsConkMushroomFeature extends AbstractWallMushroomFeature {

    public ArtistsConkMushroomFeature(Codec<BlockPileConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    protected List<BlockPos> validDir(WorldGenLevel levelIn, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            boolean flag = true;
            if (dir.getAxis().equals(Direction.Axis.Y)) continue;
            Direction dir2 = levelIn.getRandom().nextBoolean() ? dir.getClockWise() : dir.getCounterClockWise();
            List<BlockPos> blockList = Arrays.asList(pos, pos.relative(dir2), pos.relative(dir2.getOpposite()), pos.relative(dir2).relative(dir2), pos.relative(dir2).relative(dir.getOpposite()), pos.relative(dir), pos.relative(dir).relative(dir2));
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

