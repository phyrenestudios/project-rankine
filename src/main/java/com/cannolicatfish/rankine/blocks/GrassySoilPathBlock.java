package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GrassySoilPathBlock extends GrassPathBlock {
    public Block SOIL;

    public GrassySoilPathBlock(Block Soil, Properties builder) {
        super(builder);
        this.SOIL = Soil;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        worldIn.setBlockState(pos, nudgeEntitiesWithNewState(state, SOIL.getDefaultState(), worldIn, pos));
    }
}
