package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.states.StoneBricksStates;
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
        if (heldItem == Items.STONE_AXE) {
            return this.getDefaultState().with(BRICK_TYPE, StoneBricksStates.VERTICAL_SMALL);
        } else if (heldItem == Items.STONE_HOE) {
            return this.getDefaultState().with(BRICK_TYPE, StoneBricksStates.VERTICAL_LARGE);
        } else if (heldItem == Items.STONE_SWORD) {
            return this.getDefaultState().with(BRICK_TYPE, StoneBricksStates.SMALL);
        } else if (heldItem == Items.STONE_PICKAXE) {
            return this.getDefaultState().with(BRICK_TYPE, StoneBricksStates.SMALL);
        } else {
            return this.getDefaultState().with(BRICK_TYPE, StoneBricksStates.LARGE);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BRICK_TYPE);
    }


}
