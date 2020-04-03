package com.cannolicatfish.rankine.dimension;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.ObjectHolder;

public class ModDimensions {
    public static final ResourceLocation DIMENSION_ID = new ResourceLocation(ProjectRankine.MODID, "mantle");

    @ObjectHolder("rankine:mantle")
    public static ModDimension MANTLE;

    public static DimensionType MANTLE_DIMENSION;
}
