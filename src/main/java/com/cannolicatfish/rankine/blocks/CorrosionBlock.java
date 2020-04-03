package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;

public class CorrosionBlock extends Block {
    public int type = 0;
    public static final IntegerProperty STAGE = IntegerProperty.create("stage",0,2);
    public CorrosionBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(STAGE,0));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
