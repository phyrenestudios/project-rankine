package com.cannolicatfish.rankine.world.gen.placers;

import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.init.RankineBlockPlacerType;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class TripleCropsBlockPlacer extends BlockPlacer {
    public static final Codec<TripleCropsBlockPlacer> CODEC;
    public static final TripleCropsBlockPlacer PLACER = new TripleCropsBlockPlacer();

    protected BlockPlacerType<?> type() {
        return RankineBlockPlacerType.TRIPLE_CROP.get();
    }

    public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
        ((TripleCropsBlock)state.getBlock()).placeAt(world, pos, 2);
    }

    static {
        CODEC = Codec.unit(() -> PLACER);
    }
}