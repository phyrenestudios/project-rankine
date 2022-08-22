package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public class GlazedPorcelainBlock extends Block {
    public static final IntegerProperty MODE = IntegerProperty.create("mode", 0, 1);
    int alloyColor;

    public GlazedPorcelainBlock(int color) {
        super(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.0F, 3.0F));
        this.alloyColor = color;
        this.registerDefaultState(this.stateDefinition.any().setValue(MODE, 0));
    }

    public int getColor() {
        return this.alloyColor;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack heldItem = context.getPlayer().getOffhandItem();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.defaultBlockState().setValue(MODE, Math.min(BuildingToolItem.getBuildingMode(heldItem),1));
        }
        return this.defaultBlockState();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MODE);
    }

}
