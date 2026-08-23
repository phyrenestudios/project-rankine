package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.fluids.RankineFluidType;
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
 * BUCKET_FILL/BUCKET_EMPTY sounds; carbon disulfide additionally used viscosity 6000. Those numbers
 * drove nothing in 1.18.2; port item F33 replaced them with real per-substance values because they
 * now drive movement (see the table below and {@link RankineFluidType}).
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

    // ---- Fluid types ----
    //
    // Port item F33 (Karl's decision 6, 2026-08-22): "make fluid movement realistic per each fluid".
    // viscosity and density are no longer inert metadata -- RankineFluidType#move derives horizontal
    // speed from the first and buoyancy from the second, so both are now REAL PHYSICAL VALUES for
    // the substance each fluid names, in the units FluidType.Properties documents:
    //
    //   viscosity: dynamic viscosity, water = 1000 (so 1 cP = 1 mPa*s = 1000 units), at ~20-25 C
    //   density:   kg/m^3, water = 1000, at ~20-25 C
    //
    // The comment on each row is the substance whose published room-temperature value was used.
    // Temperature stays at the FluidType default of 300 K for all 16: every one of these is a liquid
    // at room temperature (mercury included), so 300 K is both the 1.18.2 value and the realistic
    // one. See RankineFluidType for how the numbers become movement, and
    // RankineRegistryTests#rankineFluidPhysicalPropertiesArePinned for the pinned table.
    //
    // 1.18.2 declared all 16 at the FluidAttributes defaults (1000/1000) except carbon disulfide,
    // which the mod author set to viscosity 6000. That 6000 is DELIBERATELY NOT KEPT: CS2 is one of
    // the least viscous liquids in common use (0.363 cP, about a third of water), so 6000 -- which
    // would have made it the second-thickest fluid in the mod, thicker than red mud -- is exactly
    // the "obviously unrealistic declaration" this item exists to fix. It was inert in 1.18.2, where
    // viscosity drove nothing; it would not be inert now. Reported for the author to veto.

    public static final DeferredHolder<FluidType, FluidType> LIQUID_MERCURY_TYPE = fluidType("liquid_mercury", 1526, 13534); // Hg: 1.526 cP, 13.534 g/cm3 @ 25 C
    public static final DeferredHolder<FluidType, FluidType> SAP_TYPE = fluidType("sap", 6000, 1060); // tapped phloem sap, ~15% sugar
    public static final DeferredHolder<FluidType, FluidType> MAPLE_SAP_TYPE = fluidType("maple_sap", 1300, 1015); // RAW maple sap, 2-3% sugar -- see the note below
    public static final DeferredHolder<FluidType, FluidType> LATEX_TYPE = fluidType("latex", 15000, 980); // field natural-rubber latex, ~35% DRC; the only sub-water density
    public static final DeferredHolder<FluidType, FluidType> JUGLONE_TYPE = fluidType("juglone", 1500, 1030); // aqueous walnut-hull extract (juglone itself is a solid)
    public static final DeferredHolder<FluidType, FluidType> RESIN_TYPE = fluidType("resin", 5000000, 1070); // conifer oleoresin, ~5 Pa*s -- the thickest fluid in the mod
    public static final DeferredHolder<FluidType, FluidType> AQUA_REGIA_TYPE = fluidType("aqua_regia", 1500, 1245); // 1:3 conc. HNO3 : conc. HCl by volume
    public static final DeferredHolder<FluidType, FluidType> CARBON_DISULFIDE_TYPE = fluidType("carbon_disulfide", 363, 1263); // CS2: 0.363 cP, 1.263 g/cm3 @ 20 C -- the thinnest
    public static final DeferredHolder<FluidType, FluidType> HEXAFLUOROSILICIC_ACID_TYPE = fluidType("hexafluorosilicic_acid", 1800, 1320); // 35% aqueous H2SiF6
    public static final DeferredHolder<FluidType, FluidType> HYDROBROMIC_ACID_TYPE = fluidType("hydrobromic_acid", 1400, 1490); // 48% aqueous HBr
    public static final DeferredHolder<FluidType, FluidType> GRAY_MUD_TYPE = fluidType("gray_mud", 300000, 1500); // monazite acid-digestion residue slurry
    public static final DeferredHolder<FluidType, FluidType> RED_MUD_TYPE = fluidType("red_mud", 250000, 1450); // Bayer-process bauxite residue slurry
    public static final DeferredHolder<FluidType, FluidType> SULFURIC_ACID_TYPE = fluidType("sulfuric_acid", 24500, 1830); // 98% H2SO4: 24.5 cP, 1.83 g/cm3 @ 25 C
    public static final DeferredHolder<FluidType, FluidType> BLACK_LIQUOR_TYPE = fluidType("black_liquor", 3000, 1100); // weak kraft black liquor, ~15% solids
    public static final DeferredHolder<FluidType, FluidType> GREEN_LIQUOR_TYPE = fluidType("green_liquor", 2000, 1180); // Na2CO3/Na2S smelt solution
    public static final DeferredHolder<FluidType, FluidType> WHITE_LIQUOR_TYPE = fluidType("white_liquor", 1900, 1120); // NaOH/Na2S cooking liquor

    // maple_sap note. Karl's decision text names "maple syrup" as the example that should slow you
    // down, but the mod has no maple syrup FLUID: rankine:maple_sap is the raw sap a tree tap
    // collects, and MapleSapCauldronBlock#getOutput boils it into the maple syrup ITEM. Raw maple
    // sap is 2-3% sugar, i.e. essentially water (~1.3 cP), so the realistic value makes it feel like
    // water -- the letter of the example and the principle it illustrates point opposite ways here,
    // and the principle ("realistic per each fluid") won. The fluids that do deliver the syrup feel
    // are resin, gray/red mud, sulfuric acid, latex and sap. Flagged for the author: raising this
    // one number to ~180000 (66-Brix syrup) is the whole change if he wants the literal example.

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

    /**
     * The one place every Rankine {@link FluidType} is built.
     *
     * <p><b>Entity interaction (port fix F13).</b> 1.18.2 had no per-fluid entity behaviour to port:
     * Forge's {@code FluidAttributes} carried only colour/textures/sound/density/temperature/
     * viscosity/luminosity/rarity, and every one of these 16 fluids was registered with a plain
     * {@code FluidAttributes.builder(...)} (ProjectRankine.java:213-292 at 859f87773; only carbon
     * disulfide deviated, with {@code .viscosity(6000)}). Drowning, swimming, current-pushing,
     * boat floating, fire extinguishing, farmland hydration and mob path types were all hard-wired
     * to {@code FluidTags.WATER} / {@code FluidTags.LAVA} in vanilla 1.18.2, and none of these
     * fluids is in either tag — the baseline shipped no fluid tag files at all, and HEAD only adds
     * the six {@code c:} content tags in {@code RankineFluidTagsProvider}. So in 1.18.2 you walked
     * through maple sap as if it were air.
     *
     * <p>NeoForge 1.21.1 generalised all of that to {@link FluidType}, and the defaults are
     * water-like ({@code FluidType.Properties}: canPushEntity/canSwim/canDrown {@code true},
     * fallDistanceModifier {@code 0.5F}, pathType {@code WATER}, adjacentPathType
     * {@code WATER_BORDER}). Leaving them at their defaults is what made all 16 fluids drown, buoy
     * and push entities. Each override below restores the 1.18.2 outcome:
     *
     * <ul>
     *   <li>{@code canDrown(false)} — {@code CommonHooks.onLivingBreathe} (CommonHooks.java:1387)
     *       consults {@code canDrownInFluidType(getEyeInFluidType())}; true means air supply drains
     *       and the entity takes drown damage. 1.18.2 drained air only for {@code FluidTags.WATER}.</li>
     *   <li>{@code canPushEntity(false)} — {@code Entity#updateFluidHeightAndDoFluidPushing}
     *       (Entity.java:3193) applies a flow impulse for every fluid type whose
     *       {@code isPushedByFluid} is true. 1.18.2 pushed only for WATER and LAVA. The mod's own
     *       flow impulse is unaffected: {@code RankineFlowingFluidBlock#entityInside} calls
     *       {@code FluidHelper.handleFluidAcceleration(entity, 0.114D)} exactly as the baseline
     *       did, and that goes through the entity-level {@code isPushedByFluid()} flag, not this
     *       property. Only the extra NeoForge push is removed.</li>
     *   <li>{@code canSwim(false)} — {@code Entity#updateSwimming} (Entity.java:1248) and
     *       {@code isVisuallySwimming} (Entity.java:2288) put the entity into the swimming pose and
     *       let it sprint-swim in any fluid type that allows it. 1.18.2 gated both on
     *       {@code isInWater()}.</li>
     *   <li>{@code fallDistanceModifier} — {@code Entity#updateInWaterStateAndDoWaterCurrentPushing}
     *       (Entity.java:1261) multiplies {@code fallDistance} by this every tick for every
     *       non-vanilla fluid type it is touching; the 0.5F default silently halves fall damage.
     *       1.18.2 zeroed fall distance for WATER and left it untouched for everything else, so
     *       "no change" (1.0F) was F13's parity value. <b>F33 supersedes that</b> with
     *       {@link RankineFluidType#fallDistanceModifier(int)} — see its javadoc; a fluid at water's
     *       viscosity still gets exactly 1.0F, so the parity value survives at the reference point
     *       and only genuinely thick fluids cushion.</li>
     *   <li>{@code pathType(null)} / {@code adjacentPathType(null)} — null means "no override, use
     *       vanilla". {@code WalkNodeEvaluator#getPathTypeFromState} (WalkNodeEvaluator.java:522,
     *       544-546) then falls through to {@code isPathfindable} and
     *       {@code fluidstate.is(FluidTags.WATER) ? WATER : OPEN}, giving {@code OPEN} — which is
     *       what 1.18.2's evaluator produced for these blocks. The WATER/WATER_BORDER defaults make
     *       land mobs treat every Rankine pool as water to be avoided and aquatic mobs treat it as
     *       home.</li>
     * </ul>
     *
     * <p>Deliberately left at their defaults, because the default already equals the 1.18.2
     * outcome: {@code canExtinguish} (false — 1.18.2 cleared fire only in water),
     * {@code canConvertToSource} (false — the baseline never called {@code canMultiply()}),
     * {@code supportsBoating} (false — boats floated only on water), {@code canHydrate} (false —
     * farmland hydration read {@code FluidTags.WATER}), {@code lightLevel} 0, {@code temperature}
     * 300 and {@code rarity} COMMON (the 1.18.2 {@code FluidAttributes} defaults, unchanged by the
     * baseline). {@code motionScale} is left alone too: it is read only inside the
     * {@code isPushedByFluid} branch, which {@code canPushEntity(false)} now closes.
     *
     * <p><b>The residual F13 flagged is now the feature (F33).</b> {@code LivingEntity#travel}
     * (LivingEntity.java:2170-2171) applies water-style buoyancy and drag inside any non-lava fluid
     * type and no property reaches it — only {@code FluidType#move} does.
     * {@link RankineFluidType} overrides it with a parametrised copy of that very block, driven by
     * each fluid's declared viscosity and density. That is a deliberate feature per the mod author's
     * decision of 2026-08-22, not 1.18.2 parity; everything F13 set above is unchanged by it.
     */
    private static DeferredHolder<FluidType, FluidType> fluidType(String name, int viscosity, int density) {
        return FLUID_TYPES.register(name, () -> new RankineFluidType(FluidType.Properties.create()
                .density(density)
                .viscosity(viscosity)
                // Port fix F22 / finding #30: FluidType.getDescriptionId() defaults to
                // Util.makeDescriptionId("fluid_type", <registry key>) = "fluid_type.rankine.<name>"
                // (FluidType.java:146-152), which has no lang entry anywhere -- FluidStack#getHoverName
                // (buckets, tanks, this mod's own FusionFurnaceScreen/MixingBarrelScreen tooltips, JEI)
                // rendered the raw key. 1.18.2's FluidAttributes defaulted to "fluid.rankine.<name>"
                // (Util.makeDescriptionId("fluid", ...)) and RankineLangProvider already carries all 16
                // of those exact keys (still generated today), so pointing back at the old key format
                // is zero lang churn and keeps the ru_ru translation alive.
                .descriptionId("fluid." + ProjectRankine.MODID + "." + name)
                .canDrown(false)
                .canPushEntity(false)
                .canSwim(false)
                .fallDistanceModifier(RankineFluidType.fallDistanceModifier(viscosity))
                .pathType(null)
                .adjacentPathType(null)
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
