package com.cannolicatfish.rankine.world.gen.placers;

import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.init.RankineBlockPlacerType;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacerType;

import java.util.Random;

public class DoubleCropsBlockPlacer extends BlockPlacer {
    public static final Codec<DoubleCropsBlockPlacer> CODEC;
    public static final DoubleCropsBlockPlacer PLACER = new DoubleCropsBlockPlacer();

    protected BlockPlacerType<?> type() {
        return RankineBlockPlacerType.DOUBLE_CROP.get();
    }

    public void place(LevelAccessor world, BlockPos pos, BlockState state, Random random) {
        ((DoubleCropsBlock)state.getBlock()).placeAt(world, pos, 2);
    }

    static {
        CODEC = Codec.unit(() -> PLACER);
    }
}