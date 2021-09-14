package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;

import javax.annotation.Nullable;

public class RankinePolishedStoneBlock extends Block {
    public static final EnumProperty<PolishedStonesBuildingStates> POLISH_TYPE = EnumProperty.create("polish_type", PolishedStonesBuildingStates.class);

    public RankinePolishedStoneBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(POLISH_TYPE, PolishedStonesBuildingStates.NORMAL));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Item heldItem = context.getPlayer().getHeldItemOffhand().getItem();
        if (heldItem == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(POLISH_TYPE, PolishedStonesBuildingStates.OFFSET);
        } else if (heldItem == RankineItems.FIRE_CLAY_BALL.get()) {
            return this.getDefaultState().with(POLISH_TYPE, PolishedStonesBuildingStates.VERTICAL_OFFSET);
        } else {
            return this.getDefaultState().with(POLISH_TYPE, PolishedStonesBuildingStates.NORMAL);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POLISH_TYPE);
    }


}
