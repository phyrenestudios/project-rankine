package com.cannolicatfish.rankine.world.gen.placement;

import com.cannolicatfish.rankine.blocks.plants.RankineDoublePlantBlock;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class RankineDoublePlantPlacer extends BlockPlacer {
    public static final Codec<RankineDoublePlantPlacer> CODEC;
    public static final RankineDoublePlantPlacer PLACER = new RankineDoublePlantPlacer();

    protected BlockPlacerType<?> getBlockPlacerType() {
        return BlockPlacerType.DOUBLE_PLANT;
    }

    public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
        ((RankineDoublePlantBlock)state.getBlock()).placeAt(world, pos, 2);
    }

    static {
        CODEC = Codec.unit(() -> {
            return PLACER;
        });
    }
}
