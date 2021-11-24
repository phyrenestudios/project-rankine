package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import org.jetbrains.annotations.Nullable;

public class GrassBlockBaseColor implements IBlockColor {
    @Override
    public int getColor(BlockState state, @Nullable IBlockDisplayReader p_getColor_2_, @Nullable BlockPos pos, int tint) {
        if (state.getBlock() instanceof GrassySoilBlock && state.get(GrassySoilBlock.DEAD)) {
            return p_getColor_2_ != null && pos != null ? BiomeColors.getGrassColor(p_getColor_2_, pos)+1000 : GrassColors.get(0.5D, 1.0D);
        } else {
            return p_getColor_2_ != null && pos != null ? BiomeColors.getGrassColor(p_getColor_2_, pos) : GrassColors.get(0.5D, 1.0D);
        }
    }
}