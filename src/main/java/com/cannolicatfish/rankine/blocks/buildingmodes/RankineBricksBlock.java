package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;

import javax.annotation.Nullable;

public class RankineBricksBlock extends Block {
    public static final EnumProperty<BricksBuildingStates> BRICK_TYPE = EnumProperty.create("brick_type", BricksBuildingStates.class);

    public RankineBricksBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BRICK_TYPE, BricksBuildingStates.NORMAL));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Item heldItem = context.getPlayer().getHeldItemOffhand().getItem();
        if (heldItem == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(BRICK_TYPE, BricksBuildingStates.VERTICAL_NORMAL);
        } else {
            return this.getDefaultState().with(BRICK_TYPE, BricksBuildingStates.NORMAL);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BRICK_TYPE);
    }


}
