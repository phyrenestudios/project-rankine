package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import com.cannolicatfish.rankine.init.ClientConfig;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.client.renderer.BiomeColors;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class GrassBlockBaseColor implements BlockColor {
    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter reader, @Nullable BlockPos pos, int tint) {
        if (ClientConfig.GENERAL.GRASS_TEMP.get()) {
            if (state.getBlock() instanceof GrassySoilBlock) {
                //if (state.getValue(GrassySoilBlock.DEAD)) {
                //    return reader != null && pos != null ? colorShift(BiomeColors.getAverageGrassColor(reader, pos), 20, (int) (-3*(Math.floor(pos.getY()/10)))+25, 0) : colorShift(GrassColor.get(0.5D, 1.0D), 20, 5, 0);
                //} else {
                    double noise = pos != null ? Biome.BIOME_INFO_NOISE.getValue(pos.getX()/20f, pos.getZ()/20f, false) : 0;
                    return reader != null && pos != null ? ClientConfig.GENERAL.GRASS_NOISE.get() ? colorShift(BiomeColors.getAverageGrassColor(reader, pos), noise > 0 ? 8 : 0, (int) (-3 * (Math.floor(pos.getY() / 10))) + 20, 0) : colorShift(BiomeColors.getAverageGrassColor(reader, pos),0, (int) (-3 * (Math.floor(pos.getY() / 10f))) + 20, 0) : GrassColor.get(0.5D, 1.0D);
                //}
            } else {
                return reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColor.get(0.5D, 1.0D);
            }
        } else {
            //if (state.getBlock() instanceof GrassySoilBlock && state.getValue(GrassySoilBlock.DEAD)) {
            //    return reader != null && pos != null ? colorShift(BiomeColors.getAverageGrassColor(reader, pos), 20, 5, 0) : colorShift(GrassColor.get(0.5D, 1.0D), 20, 5, 0);
            //} else {
                return reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColor.get(0.5D, 1.0D);
            //}
        }
    }

    private int colorShift(int color, int redShift, int greenShift, int blueShift) {
        float r = redShift;
        float g = greenShift;
        float b = blueShift;
        Color col = new Color(color);
        r += (col.getRed());
        g += (col.getGreen());
        b += (col.getBlue());
        int rgb = Math.round(Math.min(r,255));
        rgb = (rgb << 8) +  Math.round(Math.min(g,255));
        rgb = (rgb << 8) +  Math.round(Math.min(b,255));
        return rgb;
    }
}