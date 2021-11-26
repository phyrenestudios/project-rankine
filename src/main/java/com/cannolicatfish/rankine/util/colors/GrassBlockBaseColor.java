package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class GrassBlockBaseColor implements IBlockColor {
    @Override
    public int getColor(BlockState state, @Nullable IBlockDisplayReader reader, @Nullable BlockPos pos, int tint) {
        if (state.getBlock() instanceof GrassySoilBlock && state.get(GrassySoilBlock.DEAD)) {
            return reader != null && pos != null ? returnBlend(BiomeColors.getGrassColor(reader, pos),25) : returnBlend(GrassColors.get(0.5D, 1.0D),25);
        } else {
            return reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : GrassColors.get(0.5D, 1.0D);
        }
    }

    private int returnBlend(int color, int shift) {
        float r = shift;
        float g = 0;
        float b = 0;
        Color col = new Color(color);
        r += (col.getRed());
        g += (col.getGreen());
        b += (col.getBlue());
        int rgb = Math.round(Math.min(r,255));
        rgb = (rgb << 8) +  Math.round(g);
        rgb = (rgb << 8) +  Math.round(b);
        return rgb;
    }
}