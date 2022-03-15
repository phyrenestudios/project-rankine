package com.cannolicatfish.rankine.util.colors;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class NonAlloyItemColor implements IItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
        return new NonAlloyBlockColor().getColor(blockstate, null, null, 0);
    }
}
