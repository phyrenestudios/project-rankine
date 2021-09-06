package com.cannolicatfish.rankine.util.colors;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import org.jetbrains.annotations.Nullable;

public class GrassBlockBaseColor implements IBlockColor {
    @Override
    public int getColor(BlockState p_getColor_1_, @Nullable IBlockDisplayReader p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_) {
        return p_getColor_2_ != null && p_getColor_3_ != null ? BiomeColors.getGrassColor(p_getColor_2_, p_getColor_3_) : GrassColors.get(0.5D, 1.0D);
    }
}