package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;

import javax.annotation.Nullable;

public class RankinePolishedStoneBlock extends Block {
    public static final IntegerProperty MODE = IntegerProperty.create("mode", 0, 3);

    public RankinePolishedStoneBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(MODE, 0));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        ItemStack heldItem = context.getPlayer().getHeldItemOffhand();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(MODE, Math.min(BuildingToolItem.getBuildingMode(heldItem),3));
        }
        return this.getDefaultState();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MODE);
    }


}
