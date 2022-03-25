package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankinePolishedStoneBlock extends Block {
    public static final IntegerProperty MODE = IntegerProperty.create("mode", 0, 3);

    public RankinePolishedStoneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MODE, 0));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack heldItem = context.getPlayer().getOffhandItem();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.defaultBlockState().setValue(MODE, Math.min(BuildingToolItem.getBuildingMode(heldItem),3));
        }
        return this.defaultBlockState();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MODE);
    }


}
