package com.cannolicatfish.rankine.worldgen.features.misc.mushrooms;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TurkeyTailMushroomFeature extends AbstractWallMushroomFeature {

    public TurkeyTailMushroomFeature(Codec<BlockPileConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    protected List<BlockPos> validDir(WorldGenLevel levelIn, BlockPos pos) {
        RandomSource rand = levelIn.getRandom();
        for (Direction dir : Direction.values()) {
            boolean flag = true;
            if (dir.getAxis().equals(Direction.Axis.Y)) continue;
            List<BlockPos> blockList = Stream.of(build(pos,dir.getClockWise(), rand.nextInt(2)+1),
                    build(pos,dir.getCounterClockWise(), rand.nextInt(2)+1),
                    build(pos.above().relative(dir),dir.getClockWise(), rand.nextInt(2)+1),
                    build(pos.above().relative(dir),dir.getCounterClockWise(), rand.nextInt(2)+1),
                    build(pos.below().relative(dir),dir.getClockWise(), rand.nextInt(2)+1),
                    build(pos.below().relative(dir),dir.getCounterClockWise(), rand.nextInt(2)+1)
            ).flatMap(Collection::stream).collect(Collectors.toList());
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

    private static List<BlockPos> build(BlockPos pos, Direction dir, int length) {
        List<BlockPos> blockList = new ArrayList<>();
        for (int i = 0; i <= length; ++i) {
            blockList.add(pos.relative(dir,i));
        }
        return blockList;
    }
}
