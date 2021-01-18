package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RankineStairs extends StairsBlock {
    public RankineStairs(BlockState state, Properties properties) {
        super(state, properties);
    }
    public RankineStairs(Double modifier, BlockState state, Properties properties) {
        super(state, properties);
    }
}
