package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.states.StoneBricksStates;
import com.cannolicatfish.rankine.blocks.states.TreeTapFluids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

public class RankineStoneBricksBlock extends Block {
    public static final EnumProperty<StoneBricksStates> BRICK_TYPE = EnumProperty.create("brick_type", StoneBricksStates.class);

    public RankineStoneBricksBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BRICK_TYPE, StoneBricksStates.BRICKS));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BRICK_TYPE, StoneBricksStates.BRICKS);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BRICK_TYPE);
    }


}
