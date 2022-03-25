package com.cannolicatfish.rankine.util.colors;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class GrassItemBaseColor implements ItemColor {

    @Override
    public int getColor(ItemStack stack, int color) {
        BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
        return new GrassBlockBaseColor().getColor(blockstate, null, null, color);
    }
}