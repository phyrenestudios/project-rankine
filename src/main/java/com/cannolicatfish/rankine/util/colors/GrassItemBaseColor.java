package com.cannolicatfish.rankine.util.colors;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import org.jetbrains.annotations.Nullable;

public class GrassItemBaseColor implements IItemColor {

    @Override
    public int getColor(ItemStack p_getColor_1_, int p_getColor_2_) {
        BlockState blockstate = ((BlockItem)p_getColor_1_.getItem()).getBlock().getDefaultState();
        return new GrassBlockBaseColor().getColor(blockstate, null, null, p_getColor_2_);
    }
}