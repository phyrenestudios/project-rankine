package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.states.PlanksBuildingStates;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;

import javax.annotation.Nullable;

public class RankinePlanksBlock extends Block {
    public static final EnumProperty<PlanksBuildingStates> TYPE = EnumProperty.create("type", PlanksBuildingStates.class);

    public RankinePlanksBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(TYPE, PlanksBuildingStates.NORMAL));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Item heldItem = context.getPlayer().getHeldItemOffhand().getItem();
        if (heldItem == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(TYPE, PlanksBuildingStates.VERTICAL);
        } else {
            return this.getDefaultState().with(TYPE, PlanksBuildingStates.NORMAL);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }


}
