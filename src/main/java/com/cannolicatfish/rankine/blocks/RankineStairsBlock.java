package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineStairsBlock extends StairBlock {
    public RankineStairsBlock(Properties properties) {
        super(Block.stateById(0), properties);
    }
}
