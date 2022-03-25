package com.cannolicatfish.rankine.world.gen.placers;

import com.cannolicatfish.rankine.blocks.plants.RankineCropsBlock;
import com.cannolicatfish.rankine.init.RankineBlockPlacerType;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacerType;

import java.util.Random;

public class CropsBlockPlacer extends BlockPlacer {
    public static final Codec<CropsBlockPlacer> CODEC;
    public static final CropsBlockPlacer PLACER = new CropsBlockPlacer();

    protected BlockPlacerType<?> type() {
        return RankineBlockPlacerType.SINGLE_CROP.get();
    }

    public void place(LevelAccessor world, BlockPos pos, BlockState state, Random random) {
        ((RankineCropsBlock)state.getBlock()).placeAt(world, pos, 2);
    }

    static {
        CODEC = Codec.unit(() -> PLACER);
    }
}