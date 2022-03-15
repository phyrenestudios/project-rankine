package com.cannolicatfish.rankine.util.colors;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class GrassItemBaseColor implements IItemColor {

    @Override
    public int getColor(ItemStack stack, int color) {
        BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
        return new GrassBlockBaseColor().getColor(blockstate, null, null, color);
    }
}