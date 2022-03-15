package com.cannolicatfish.rankine.world.gen.placers;

import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.init.RankineBlockPlacerType;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class DoubleCropsBlockPlacer extends BlockPlacer {
    public static final Codec<DoubleCropsBlockPlacer> CODEC;
    public static final DoubleCropsBlockPlacer PLACER = new DoubleCropsBlockPlacer();

    protected BlockPlacerType<?> type() {
        return RankineBlockPlacerType.DOUBLE_CROP.get();
    }

    public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
        ((DoubleCropsBlock)state.getBlock()).placeAt(world, pos, 2);
    }

    static {
        CODEC = Codec.unit(() -> PLACER);
    }
}