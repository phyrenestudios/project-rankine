package com.cannolicatfish.rankine.world.gen.placers;

import com.cannolicatfish.rankine.blocks.plants.RankineDoublePlantBlock;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacerType;

import java.util.Random;

public class RankineDoublePlantPlacer extends BlockPlacer {
    public static final Codec<RankineDoublePlantPlacer> CODEC;
    public static final RankineDoublePlantPlacer PLACER = new RankineDoublePlantPlacer();

    protected BlockPlacerType<?> type() {
        return BlockPlacerType.DOUBLE_PLANT_PLACER;
    }

    public void place(LevelAccessor world, BlockPos pos, BlockState state, Random random) {
        ((RankineDoublePlantBlock)state.getBlock()).placeAt(world, pos, 2);
    }

    static {
        CODEC = Codec.unit(() -> {
            return PLACER;
        });
    }
}
