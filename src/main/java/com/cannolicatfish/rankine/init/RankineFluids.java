package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ProjectRankine.MODID);


    public static final RegistryObject<FlowingFluid> LIQUID_MERCURY = FLUIDS.register("liquid_mercury_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.MERCURY_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_LIQUID_MERCURY = FLUIDS.register("liquid_mercury_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.MERCURY_FLUID_PROPERTIES));

    private static final ForgeFlowingFluid.Properties MERCURY_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.LIQUID_MERCURY_FLUID_TYPE, LIQUID_MERCURY, FLOWING_LIQUID_MERCURY)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.LIQUID_MERCURY)
            .bucket(RankineItems.LIQUID_MERCURY_BUCKET);

    public static final RegistryObject<FlowingFluid> SAP = FLUIDS.register("sap_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.SAP_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_SAP = FLUIDS.register("sap_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.SAP_PROPERTIES));

    private static final ForgeFlowingFluid.Properties SAP_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.SAP_FLUID_TYPE, SAP, FLOWING_SAP)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.SAP)
            .bucket(RankineItems.SAP_BUCKET);

    public static final RegistryObject<FlowingFluid> MAPLE_SAP = FLUIDS.register("maple_sap_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.MAPLE_SAP_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_MAPLE_SAP = FLUIDS.register("maple_sap_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.MAPLE_SAP_PROPERTIES));

    private static final ForgeFlowingFluid.Properties MAPLE_SAP_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.MAPLE_SAP_FLUID_TYPE, MAPLE_SAP, FLOWING_MAPLE_SAP)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.MAPLE_SAP)
            .bucket(RankineItems.MAPLE_SAP_BUCKET);

    public static final RegistryObject<FlowingFluid> LATEX = FLUIDS.register("latex_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.LATEX_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_LATEX = FLUIDS.register("latex_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.LATEX_PROPERTIES));

    private static final ForgeFlowingFluid.Properties LATEX_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.LATEX_FLUID_TYPE, LATEX, FLOWING_LATEX)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.LATEX)
            .bucket(RankineItems.LATEX_BUCKET);

    public static final RegistryObject<FlowingFluid> RESIN = FLUIDS.register("resin_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.RESIN_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_RESIN = FLUIDS.register("resin_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.RESIN_PROPERTIES));

    private static final ForgeFlowingFluid.Properties RESIN_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.RESIN_FLUID_TYPE, RESIN, FLOWING_RESIN)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.RESIN)
            .bucket(RankineItems.RESIN_BUCKET);

    public static final RegistryObject<FlowingFluid> JUGLONE = FLUIDS.register("juglone_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.JUGLONE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_JUGLONE = FLUIDS.register("juglone_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.JUGLONE_PROPERTIES));

    private static final ForgeFlowingFluid.Properties JUGLONE_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.JUGLONE_FLUID_TYPE, JUGLONE, FLOWING_JUGLONE)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.JUGLONE)
            .bucket(RankineItems.JUGLONE_BUCKET);

    public static final RegistryObject<FlowingFluid> AQUA_REGIA = FLUIDS.register("aqua_regia_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.AQUA_REGIA_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_AQUA_REGIA = FLUIDS.register("aqua_regia_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.AQUA_REGIA_PROPERTIES));

    private static final ForgeFlowingFluid.Properties AQUA_REGIA_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.AQUA_REGIA_FLUID_TYPE, AQUA_REGIA, FLOWING_AQUA_REGIA)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.AQUA_REGIA)
            .bucket(RankineItems.AQUA_REGIA_BUCKET);

    public static final RegistryObject<FlowingFluid> CARBON_DISULFIDE = FLUIDS.register("carbon_disulfide_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.CARBON_DISULFIDE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_CARBON_DISULFIDE = FLUIDS.register("carbon_disulfide_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.CARBON_DISULFIDE_PROPERTIES));

    private static final ForgeFlowingFluid.Properties CARBON_DISULFIDE_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.CARBON_DISULFIDE_FLUID_TYPE, CARBON_DISULFIDE, FLOWING_CARBON_DISULFIDE)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.CARBON_DISULFIDE)
            .bucket(RankineItems.CARBON_DISULFIDE_BUCKET);

    public static final RegistryObject<FlowingFluid> HEXAFLUOROSILICIC_ACID = FLUIDS.register("hexafluorosilicic_acid_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.HEXAFLUOROSILICIC_ACID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_HEXAFLUOROSILICIC_ACID = FLUIDS.register("hexafluorosilicic_acid_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.HEXAFLUOROSILICIC_ACID_PROPERTIES));

    private static final ForgeFlowingFluid.Properties HEXAFLUOROSILICIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.HEXAFLUOROSILICIC_ACID_FLUID_TYPE, HEXAFLUOROSILICIC_ACID, FLOWING_HEXAFLUOROSILICIC_ACID)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.HEXAFLUOROSILICIC_ACID)
            .bucket(RankineItems.HEXAFLUOROSILICIC_ACID_BUCKET);

    public static final RegistryObject<FlowingFluid> HYDROBROMIC_ACID = FLUIDS.register("hydrobromic_acid_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.HYDROBROMIC_ACID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_HYDROBROMIC_ACID = FLUIDS.register("hydrobromic_acid_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.HYDROBROMIC_ACID_PROPERTIES));

    private static final ForgeFlowingFluid.Properties HYDROBROMIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.HYDROBROMIC_ACID_FLUID_TYPE, HYDROBROMIC_ACID, FLOWING_HYDROBROMIC_ACID)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.HYDROBROMIC_ACID)
            .bucket(RankineItems.HYDROBROMIC_ACID_BUCKET);

    public static final RegistryObject<FlowingFluid> GRAY_MUD = FLUIDS.register("gray_mud_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.GRAY_MUD_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_GRAY_MUD = FLUIDS.register("gray_mud_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.GRAY_MUD_PROPERTIES));

    private static final ForgeFlowingFluid.Properties GRAY_MUD_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.GRAY_MUD_FLUID_TYPE, GRAY_MUD, FLOWING_GRAY_MUD)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.GRAY_MUD)
            .bucket(RankineItems.GRAY_MUD_BUCKET);


    public static final RegistryObject<FlowingFluid> RED_MUD = FLUIDS.register("red_mud_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.RED_MUD_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_RED_MUD = FLUIDS.register("red_mud_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.RED_MUD_PROPERTIES));

    private static final ForgeFlowingFluid.Properties RED_MUD_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.RED_MUD_FLUID_TYPE, RED_MUD, FLOWING_RED_MUD)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.RED_MUD)
            .bucket(RankineItems.RED_MUD_BUCKET);

    public static final RegistryObject<FlowingFluid> SULFURIC_ACID = FLUIDS.register("sulfuric_acid_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.SULFURIC_ACID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_SULFURIC_ACID = FLUIDS.register("sulfuric_acid_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.SULFURIC_ACID_PROPERTIES));

    private static final ForgeFlowingFluid.Properties SULFURIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.SULFURIC_ACID_FLUID_TYPE, SULFURIC_ACID, FLOWING_SULFURIC_ACID)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.SULFURIC_ACID)
            .bucket(RankineItems.SULFURIC_ACID_BUCKET);

    public static final RegistryObject<FlowingFluid> BLACK_LIQUOR = FLUIDS.register("black_liquor_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.BLACK_LIQUOR_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_BLACK_LIQUOR = FLUIDS.register("black_liquor_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.BLACK_LIQUOR_PROPERTIES));

    private static final ForgeFlowingFluid.Properties BLACK_LIQUOR_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.BLACK_LIQUOR_FLUID_TYPE, BLACK_LIQUOR, FLOWING_BLACK_LIQUOR)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.BLACK_LIQUOR)
            .bucket(RankineItems.BLACK_LIQUOR_BUCKET);

    public static final RegistryObject<FlowingFluid> GREEN_LIQUOR = FLUIDS.register("green_liquor_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.GREEN_LIQUOR_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_GREEN_LIQUOR = FLUIDS.register("green_liquor_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.GREEN_LIQUOR_PROPERTIES));

    private static final ForgeFlowingFluid.Properties GREEN_LIQUOR_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.GREEN_LIQUOR_FLUID_TYPE, GREEN_LIQUOR, FLOWING_GREEN_LIQUOR)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.GREEN_LIQUOR)
            .bucket(RankineItems.GREEN_LIQUOR_BUCKET);

    public static final RegistryObject<FlowingFluid> WHITE_LIQUOR = FLUIDS.register("white_liquor_source",
            () -> new ForgeFlowingFluid.Source(RankineFluids.WHITE_LIQUOR_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_WHITE_LIQUOR = FLUIDS.register("white_liquor_flowing",
            () -> new ForgeFlowingFluid.Flowing(RankineFluids.WHITE_LIQUOR_PROPERTIES));

    private static final ForgeFlowingFluid.Properties WHITE_LIQUOR_PROPERTIES = new ForgeFlowingFluid.Properties(
            RankineFluidTypes.WHITE_LIQUOR_FLUID_TYPE, WHITE_LIQUOR, FLOWING_WHITE_LIQUOR)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(RankineBlocks.WHITE_LIQUOR)
            .bucket(RankineItems.WHITE_LIQUOR_BUCKET);
}
