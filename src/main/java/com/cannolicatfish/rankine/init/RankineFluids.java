package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.registries.ObjectHolder;

public class RankineFluids {

    //public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ProjectRankine.MODID);
    //public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.FLUID_TYPES.get(), ProjectRankine.MODID);


    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:liquid_mercury")
    public static FlowingFluid LIQUID_MERCURY;
  
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:liquid_mercury_flowing")
    public static FlowingFluid LIQUID_MERCURY_FLOWING;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:sap")
    public static FlowingFluid SAP;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_sap")
    public static FlowingFluid FLOWING_SAP;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:maple_sap")
    public static FlowingFluid MAPLE_SAP;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_maple_sap")
    public static FlowingFluid FLOWING_MAPLE_SAP;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:latex")
    public static FlowingFluid LATEX;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_latex")
    public static FlowingFluid FLOWING_LATEX;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:resin")
    public static FlowingFluid RESIN;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_resin")
    public static FlowingFluid FLOWING_RESIN;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:juglone")
    public static FlowingFluid JUGLONE;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_juglone")
    public static FlowingFluid FLOWING_JUGLONE;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:aqua_regia")
    public static FlowingFluid AQUA_REGIA;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_aqua_regia")
    public static FlowingFluid FLOWING_AQUA_REGIA;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:carbon_disulfide")
    public static FlowingFluid CARBON_DISULFIDE;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_carbon_disulfide")
    public static FlowingFluid FLOWING_CARBON_DISULFIDE;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:hexafluorosilicic_acid")
    public static FlowingFluid HEXAFLUOROSILICIC_ACID;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_hexafluorosilicic_acid")
    public static FlowingFluid FLOWING_HEXAFLUOROSILICIC_ACID;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:hydrobromic_acid")
    public static FlowingFluid HYDROBROMIC_ACID;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_hydrobromic_acid")
    public static FlowingFluid FLOWING_HYDROBROMIC_ACID;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:gray_mud")
    public static FlowingFluid GRAY_MUD;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_gray_mud")
    public static FlowingFluid FLOWING_GRAY_MUD;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:red_mud")
    public static FlowingFluid RED_MUD;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_red_mud")
    public static FlowingFluid FLOWING_RED_MUD;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:sulfuric_acid")
    public static FlowingFluid SULFURIC_ACID;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_sulfuric_acid")
    public static FlowingFluid FLOWING_SULFURIC_ACID;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:black_liquor")
    public static FlowingFluid BLACK_LIQUOR;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_black_liquor")
    public static FlowingFluid FLOWING_BLACK_LIQUOR;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:green_liquor")
    public static FlowingFluid GREEN_LIQUOR;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_green_liquor")
    public static FlowingFluid FLOWING_GREEN_LIQUOR;

    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:white_liquor")
    public static FlowingFluid WHITE_LIQUOR;
    @ObjectHolder(registryName = ProjectRankine.MODID, value = "rankine:flowing_white_liquor")
    public static FlowingFluid FLOWING_WHITE_LIQUOR;
}
