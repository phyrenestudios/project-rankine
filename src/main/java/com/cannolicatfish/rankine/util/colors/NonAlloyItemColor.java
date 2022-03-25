package com.cannolicatfish.rankine.util.colors;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class NonAlloyItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
        return new NonAlloyBlockColor().getColor(blockstate, null, null, 0);
    }
}
