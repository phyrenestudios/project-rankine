package com.cannolicatfish.rankine.world.biome;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.ObjectHolder;

public class ModBiomes  {
    public static final ResourceLocation BIOME_ID = new ResourceLocation(ProjectRankine.MODID, "mantle");
    public static final ResourceLocation PINYON_JUNIPER_WOODS = new ResourceLocation(ProjectRankine.MODID,"pinyon_juniper_woodlands");

    @ObjectHolder("rankine:mantle")
    public static Biome MANTLE;

    @ObjectHolder("rankine:pinyon_juniper_woodlands")
    public static Biome PINYON_JUNIPER_WOODLANDS;

    @ObjectHolder("rankine:cedar_forest")
    public static Biome CEDAR_FOREST;

    @ObjectHolder("rankine:shoal")
    public static Biome SHOAL;

    @ObjectHolder("rankine:highland_plateau")
    public static Biome HIGHLAND_PLATEAU;

    @ObjectHolder("rankine:felsenmeer")
    public static Biome FELSENMEER;

    @ObjectHolder("rankine:dead_swamp")
    public static Biome DEAD_SWAMP;
}
