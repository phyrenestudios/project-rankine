package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.block.material.MaterialColor;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RankineWoodColors {
    public static final int CEDAR_WOOD = 7496010;


    public static MaterialColor getClosestMaterialColor(int color) {
        int colorR = (color >> 16) & 0xFF;
        int colorG = (color >> 8) & 0xFF;
        int colorB = color & 0xFF;
        Map<Double, MaterialColor> dists = Arrays.stream(MaterialColor.COLORS).filter(Objects::nonNull)
                .collect(Collectors.toMap(materialColor -> {
                    int col = materialColor.colorValue;
                    int colR = (col >> 16) & 0xFF;
                    int colG = (col >> 8) & 0xFF;
                    int colB = col & 0xFF;
                    return Math
                            .sqrt((colR - colorR) * (colR - colorR) + (colG - colorG) * (colG - colorG) + (colB - colorB) * (colB - colorB));
                }, materialColor -> materialColor, (a, b) -> {
                    ProjectRankine.LOGGER.warn("duplicate distance to MaterialColor: {}, {}", a, b);
                    return a;
                }));
        Double dist = dists.keySet().stream().sorted().findFirst()
                .orElseThrow(() -> new RuntimeException("failed to find MaterialColor"));
        return dists.get(dist);
    }
}
