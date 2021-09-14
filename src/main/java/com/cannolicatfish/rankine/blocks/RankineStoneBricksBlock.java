package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.states.StoneBricksStates;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;

import javax.annotation.Nullable;

public class RankineStoneBricksBlock extends Block {
    public static final EnumProperty<StoneBricksStates> BRICK_TYPE = EnumProperty.create("brick_type", StoneBricksStates.class);

    public RankineStoneBricksBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BRICK_TYPE, StoneBricksStates.LARGE));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Item heldItem = context.getPlayer().getHeldItemOffhand().getItem();
        if (heldItem == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(BRICK_TYPE, StoneBricksStates.VERTICAL_LARGE);
        } else {
            return this.getDefaultState().with(BRICK_TYPE, StoneBricksStates.LARGE);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BRICK_TYPE);
    }


}
