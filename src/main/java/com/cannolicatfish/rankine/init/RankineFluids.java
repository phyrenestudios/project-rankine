package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Fluid registration, reworked for NeoForge 1.21.1 (ForgeFlowingFluid/FluidAttributes are GONE).
 * Registry names are IDENTICAL to 1.18.2 for world compatibility.
 *
 * All 1.18.2 FluidAttributes used default density/viscosity (1000/1000), color 0xFFFFFFFF and
 * BUCKET_FILL/BUCKET_EMPTY sounds; carbon disulfide additionally used viscosity 6000.
 *
 * PORT-TODO(client): register IClientFluidTypeExtensions for every FluidType below in
 * RegisterClientExtensionsEvent (mod bus, Dist.CLIENT), using the FLUID_STILL / FLUID_FLOWING /
 * OVERLAY ResourceLocation constants kept on the classes in com.cannolicatfish.rankine.fluids:
 *   LIQUID_MERCURY_TYPE         -> LiquidMercuryFluid        (rankine:block/liquid_mercury_still / _flow / _overlay)
 *   SAP_TYPE                    -> SapFluid                  (rankine:block/sap_still / _flow / _overlay)
 *   MAPLE_SAP_TYPE              -> MapleSapFluid             (rankine:block/maple_sap_still / _flow / _overlay)
 *   LATEX_TYPE                  -> LatexFluid                (rankine:block/latex_still / _flow / _overlay)
 *   JUGLONE_TYPE                -> JugloneFluid              (rankine:block/juglone_still / _flow / _overlay)
 *   RESIN_TYPE                  -> ResinFluid                (rankine:block/resin_still / _flow / _overlay)
 *   AQUA_REGIA_TYPE             -> AquaRegiaFluid            (rankine:block/aqua_regia_still / _flow / _overlay)
 *   CARBON_DISULFIDE_TYPE       -> CarbonDisulfideFluid      (rankine:block/carbon_disulfide_still / _flow / _overlay)
 *   HEXAFLUOROSILICIC_ACID_TYPE -> HexafluorosilicicAcidFluid(rankine:block/hexafluorosilicic_acid_still / _flow / _overlay)
 *   HYDROBROMIC_ACID_TYPE       -> HydrobromicAcidFluid      (rankine:block/hydrobromic_acid_still / _flow / _overlay)
 *   GRAY_MUD_TYPE               -> GrayMudFluid              (rankine:block/gray_mud_still / _flow / _overlay)
 *   RED_MUD_TYPE                -> RedMudFluid               (rankine:block/red_mud_still / _flow / _overlay)
 *   SULFURIC_ACID_TYPE          -> SulfuricAcidFluid         (rankine:block/sulfuric_acid_still / _flow / _overlay)
 *   BLACK_LIQUOR_TYPE           -> BlackLiquorFluid          (rankine:block/black_liquor_still / _flow / _overlay)
 *   GREEN_LIQUOR_TYPE           -> GreenLiquorFluid          (rankine:block/green_liquor_still / _flow / _overlay)
 *   WHITE_LIQUOR_TYPE           -> WhiteLiquorFluid          (rankine:block/white_liquor_still / _flow / _overlay)
 * (exact texture paths are the constants on those classes; tint color was 0xFFFFFFFF for all)
 */
public class RankineFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(net.minecraft.core.registries.Registries.FLUID, ProjectRankine.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, ProjectRankine.MODID);

    // ---- Fluid types (density/viscosity/sounds; render info is client-side, see PORT-TODO above) ----

    public static final DeferredHolder<FluidType, FluidType> LIQUID_MERCURY_TYPE = fluidType("liquid_mercury", 1000);
    public static final DeferredHolder<FluidType, FluidType> SAP_TYPE = fluidType("sap", 1000);
    public static final DeferredHolder<FluidType, FluidType> MAPLE_SAP_TYPE = fluidType("maple_sap", 1000);
    public static final DeferredHolder<FluidType, FluidType> LATEX_TYPE = fluidType("latex", 1000);
    public static final DeferredHolder<FluidType, FluidType> JUGLONE_TYPE = fluidType("juglone", 1000);
    public static final DeferredHolder<FluidType, FluidType> RESIN_TYPE = fluidType("resin", 1000);
    public static final DeferredHolder<FluidType, FluidType> AQUA_REGIA_TYPE = fluidType("aqua_regia", 1000);
    public static final DeferredHolder<FluidType, FluidType> CARBON_DISULFIDE_TYPE = fluidType("carbon_disulfide", 6000);
    public static final DeferredHolder<FluidType, FluidType> HEXAFLUOROSILICIC_ACID_TYPE = fluidType("hexafluorosilicic_acid", 1000);
    public static final DeferredHolder<FluidType, FluidType> HYDROBROMIC_ACID_TYPE = fluidType("hydrobromic_acid", 1000);
    public static final DeferredHolder<FluidType, FluidType> GRAY_MUD_TYPE = fluidType("gray_mud", 1000);
    public static final DeferredHolder<FluidType, FluidType> RED_MUD_TYPE = fluidType("red_mud", 1000);
    public static final DeferredHolder<FluidType, FluidType> SULFURIC_ACID_TYPE = fluidType("sulfuric_acid", 1000);
    public static final DeferredHolder<FluidType, FluidType> BLACK_LIQUOR_TYPE = fluidType("black_liquor", 1000);
    public static final DeferredHolder<FluidType, FluidType> GREEN_LIQUOR_TYPE = fluidType("green_liquor", 1000);
    public static final DeferredHolder<FluidType, FluidType> WHITE_LIQUOR_TYPE = fluidType("white_liquor", 1000);

    // ---- Fluids (registry names identical to 1.18.2) ----

    public static final DeferredHolder<Fluid, FlowingFluid> LIQUID_MERCURY = FLUIDS.register("liquid_mercury", () -> new BaseFlowingFluid.Source(liquidMercuryProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> LIQUID_MERCURY_FLOWING = FLUIDS.register("liquid_mercury_flowing", () -> new BaseFlowingFluid.Flowing(liquidMercuryProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> SAP = FLUIDS.register("sap", () -> new BaseFlowingFluid.Source(sapProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_SAP = FLUIDS.register("flowing_sap", () -> new BaseFlowingFluid.Flowing(sapProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> MAPLE_SAP = FLUIDS.register("maple_sap", () -> new BaseFlowingFluid.Source(mapleSapProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_MAPLE_SAP = FLUIDS.register("flowing_maple_sap", () -> new BaseFlowingFluid.Flowing(mapleSapProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> LATEX = FLUIDS.register("latex", () -> new BaseFlowingFluid.Source(latexProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_LATEX = FLUIDS.register("flowing_latex", () -> new BaseFlowingFluid.Flowing(latexProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> JUGLONE = FLUIDS.register("juglone", () -> new BaseFlowingFluid.Source(jugloneProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_JUGLONE = FLUIDS.register("flowing_juglone", () -> new BaseFlowingFluid.Flowing(jugloneProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> RESIN = FLUIDS.register("resin", () -> new BaseFlowingFluid.Source(resinProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_RESIN = FLUIDS.register("flowing_resin", () -> new BaseFlowingFluid.Flowing(resinProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> AQUA_REGIA = FLUIDS.register("aqua_regia", () -> new BaseFlowingFluid.Source(aquaRegiaProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_AQUA_REGIA = FLUIDS.register("flowing_aqua_regia", () -> new BaseFlowingFluid.Flowing(aquaRegiaProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> CARBON_DISULFIDE = FLUIDS.register("carbon_disulfide", () -> new BaseFlowingFluid.Source(carbonDisulfideProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_CARBON_DISULFIDE = FLUIDS.register("flowing_carbon_disulfide", () -> new BaseFlowingFluid.Flowing(carbonDisulfideProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> HEXAFLUOROSILICIC_ACID = FLUIDS.register("hexafluorosilicic_acid", () -> new BaseFlowingFluid.Source(hexafluorosilicicAcidProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_HEXAFLUOROSILICIC_ACID = FLUIDS.register("flowing_hexafluorosilicic_acid", () -> new BaseFlowingFluid.Flowing(hexafluorosilicicAcidProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> HYDROBROMIC_ACID = FLUIDS.register("hydrobromic_acid", () -> new BaseFlowingFluid.Source(hydrobromicAcidProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_HYDROBROMIC_ACID = FLUIDS.register("flowing_hydrobromic_acid", () -> new BaseFlowingFluid.Flowing(hydrobromicAcidProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> GRAY_MUD = FLUIDS.register("gray_mud", () -> new BaseFlowingFluid.Source(grayMudProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_GRAY_MUD = FLUIDS.register("flowing_gray_mud", () -> new BaseFlowingFluid.Flowing(grayMudProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> RED_MUD = FLUIDS.register("red_mud", () -> new BaseFlowingFluid.Source(redMudProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_RED_MUD = FLUIDS.register("flowing_red_mud", () -> new BaseFlowingFluid.Flowing(redMudProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> SULFURIC_ACID = FLUIDS.register("sulfuric_acid", () -> new BaseFlowingFluid.Source(sulfuricAcidProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_SULFURIC_ACID = FLUIDS.register("flowing_sulfuric_acid", () -> new BaseFlowingFluid.Flowing(sulfuricAcidProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> BLACK_LIQUOR = FLUIDS.register("black_liquor", () -> new BaseFlowingFluid.Source(blackLiquorProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_BLACK_LIQUOR = FLUIDS.register("flowing_black_liquor", () -> new BaseFlowingFluid.Flowing(blackLiquorProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> GREEN_LIQUOR = FLUIDS.register("green_liquor", () -> new BaseFlowingFluid.Source(greenLiquorProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_GREEN_LIQUOR = FLUIDS.register("flowing_green_liquor", () -> new BaseFlowingFluid.Flowing(greenLiquorProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> WHITE_LIQUOR = FLUIDS.register("white_liquor", () -> new BaseFlowingFluid.Source(whiteLiquorProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_WHITE_LIQUOR = FLUIDS.register("flowing_white_liquor", () -> new BaseFlowingFluid.Flowing(whiteLiquorProperties()));

    // ---- helpers ----

    private static DeferredHolder<FluidType, FluidType> fluidType(String name, int viscosity) {
        return FLUID_TYPES.register(name, () -> new FluidType(FluidType.Properties.create()
                .density(1000)
                .viscosity(viscosity)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));
    }

    private static BaseFlowingFluid.Properties liquidMercuryProperties() {
        return new BaseFlowingFluid.Properties(LIQUID_MERCURY_TYPE, LIQUID_MERCURY, LIQUID_MERCURY_FLOWING)
                .bucket(RankineItems.LIQUID_MERCURY_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.LIQUID_MERCURY.get());
    }

    private static BaseFlowingFluid.Properties sapProperties() {
        return new BaseFlowingFluid.Properties(SAP_TYPE, SAP, FLOWING_SAP)
                .bucket(RankineItems.SAP_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.SAP.get());
    }

    private static BaseFlowingFluid.Properties mapleSapProperties() {
        return new BaseFlowingFluid.Properties(MAPLE_SAP_TYPE, MAPLE_SAP, FLOWING_MAPLE_SAP)
                .bucket(RankineItems.MAPLE_SAP_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.MAPLE_SAP.get());
    }

    private static BaseFlowingFluid.Properties latexProperties() {
        return new BaseFlowingFluid.Properties(LATEX_TYPE, LATEX, FLOWING_LATEX)
                .bucket(RankineItems.LATEX_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.LATEX.get());
    }

    private static BaseFlowingFluid.Properties jugloneProperties() {
        return new BaseFlowingFluid.Properties(JUGLONE_TYPE, JUGLONE, FLOWING_JUGLONE)
                .bucket(RankineItems.JUGLONE_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.JUGLONE.get());
    }

    private static BaseFlowingFluid.Properties resinProperties() {
        return new BaseFlowingFluid.Properties(RESIN_TYPE, RESIN, FLOWING_RESIN)
                .bucket(RankineItems.RESIN_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.RESIN.get());
    }

    private static BaseFlowingFluid.Properties aquaRegiaProperties() {
        return new BaseFlowingFluid.Properties(AQUA_REGIA_TYPE, AQUA_REGIA, FLOWING_AQUA_REGIA)
                .bucket(RankineItems.AQUA_REGIA_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.AQUA_REGIA.get());
    }

    private static BaseFlowingFluid.Properties carbonDisulfideProperties() {
        return new BaseFlowingFluid.Properties(CARBON_DISULFIDE_TYPE, CARBON_DISULFIDE, FLOWING_CARBON_DISULFIDE)
                .bucket(RankineItems.CARBON_DISULFIDE_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.CARBON_DISULFIDE.get());
    }

    private static BaseFlowingFluid.Properties hexafluorosilicicAcidProperties() {
        return new BaseFlowingFluid.Properties(HEXAFLUOROSILICIC_ACID_TYPE, HEXAFLUOROSILICIC_ACID, FLOWING_HEXAFLUOROSILICIC_ACID)
                .bucket(RankineItems.HEXAFLUOROSILICIC_ACID_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.HEXAFLUOROSILICIC_ACID.get());
    }

    private static BaseFlowingFluid.Properties hydrobromicAcidProperties() {
        return new BaseFlowingFluid.Properties(HYDROBROMIC_ACID_TYPE, HYDROBROMIC_ACID, FLOWING_HYDROBROMIC_ACID)
                .bucket(RankineItems.HYDROBROMIC_ACID_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.HYDROBROMIC_ACID.get());
    }

    private static BaseFlowingFluid.Properties grayMudProperties() {
        return new BaseFlowingFluid.Properties(GRAY_MUD_TYPE, GRAY_MUD, FLOWING_GRAY_MUD)
                .bucket(RankineItems.GRAY_MUD_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.GRAY_MUD.get());
    }

    private static BaseFlowingFluid.Properties redMudProperties() {
        return new BaseFlowingFluid.Properties(RED_MUD_TYPE, RED_MUD, FLOWING_RED_MUD)
                .bucket(RankineItems.RED_MUD_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.RED_MUD.get());
    }

    private static BaseFlowingFluid.Properties sulfuricAcidProperties() {
        return new BaseFlowingFluid.Properties(SULFURIC_ACID_TYPE, SULFURIC_ACID, FLOWING_SULFURIC_ACID)
                .bucket(RankineItems.SULFURIC_ACID_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.SULFURIC_ACID.get());
    }

    private static BaseFlowingFluid.Properties blackLiquorProperties() {
        return new BaseFlowingFluid.Properties(BLACK_LIQUOR_TYPE, BLACK_LIQUOR, FLOWING_BLACK_LIQUOR)
                .bucket(RankineItems.BLACK_LIQUOR_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.BLACK_LIQUOR.get());
    }

    private static BaseFlowingFluid.Properties greenLiquorProperties() {
        return new BaseFlowingFluid.Properties(GREEN_LIQUOR_TYPE, GREEN_LIQUOR, FLOWING_GREEN_LIQUOR)
                .bucket(RankineItems.GREEN_LIQUOR_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.GREEN_LIQUOR.get());
    }

    private static BaseFlowingFluid.Properties whiteLiquorProperties() {
        return new BaseFlowingFluid.Properties(WHITE_LIQUOR_TYPE, WHITE_LIQUOR, FLOWING_WHITE_LIQUOR)
                .bucket(RankineItems.WHITE_LIQUOR_BUCKET)
                .block(() -> (LiquidBlock) RankineBlocks.WHITE_LIQUOR.get());
    }
}
