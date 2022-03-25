package com.cannolicatfish.rankine.world.gen.placers;

import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.init.RankineBlockPlacerType;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacerType;

import java.util.Random;

public class TripleCropsBlockPlacer extends BlockPlacer {
    public static final Codec<TripleCropsBlockPlacer> CODEC;
    public static final TripleCropsBlockPlacer PLACER = new TripleCropsBlockPlacer();

    protected BlockPlacerType<?> type() {
        return RankineBlockPlacerType.TRIPLE_CROP.get();
    }

    public void place(LevelAccessor world, BlockPos pos, BlockState state, Random random) {
        ((TripleCropsBlock)state.getBlock()).placeAt(world, pos, 2);
    }

    static {
        CODEC = Codec.unit(() -> PLACER);
    }
}