package com.cannolicatfish.rankine.util.colors;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class OrnamentColor implements BlockColor {
    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter reader, @Nullable BlockPos pos, int tint) {
        return reader != null && pos != null ? colorShift(pos) : 0;
    }

    private int colorShift(BlockPos pos) {
        float r = Math.abs((float) (255*Biome.BIOME_INFO_NOISE.getValue(pos.getX(), pos.getZ(), false)));
        float g =  Math.abs((float) (255*Biome.BIOME_INFO_NOISE.getValue(pos.getX()+pos.getY()*2, pos.getZ()+pos.getY()*2, false)));
        float b =  Math.abs((float) (255*Biome.BIOME_INFO_NOISE.getValue(pos.getX()+pos.getY()*4, pos.getZ()+pos.getY()*4, false)));
        Color col = new Color(0x656565);
        r += (col.getRed());
        g += (col.getGreen());
        b += (col.getBlue());
        int rgb = Math.round(Math.min(r,255));
        rgb = (rgb << 8) +  Math.round(Math.min(g,255));
        rgb = (rgb << 8) +  Math.round(Math.min(b,255));
        return rgb;
    }
}