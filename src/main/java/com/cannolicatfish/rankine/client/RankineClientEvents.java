package com.cannolicatfish.rankine.client;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.client.renders.AlloyArrowRenderer;
import com.cannolicatfish.rankine.client.renders.BalloonRenderer;
import com.cannolicatfish.rankine.client.renders.CannonballRenderer;
import com.cannolicatfish.rankine.client.renders.CarcassRenderer;
import com.cannolicatfish.rankine.client.renders.EnderballRenderer;
import com.cannolicatfish.rankine.client.renders.PedestalRenderer;
import com.cannolicatfish.rankine.client.renders.RankineBoatRenderer;
import com.cannolicatfish.rankine.client.renders.ReactiveItemRenderer;
import com.cannolicatfish.rankine.client.renders.RopeCoilArrowRenderer;
import com.cannolicatfish.rankine.client.renders.SpearEntityRenderer;
import com.cannolicatfish.rankine.fluids.AquaRegiaFluid;
import com.cannolicatfish.rankine.fluids.BlackLiquorFluid;
import com.cannolicatfish.rankine.fluids.CarbonDisulfideFluid;
import com.cannolicatfish.rankine.fluids.GrayMudFluid;
import com.cannolicatfish.rankine.fluids.GreenLiquorFluid;
import com.cannolicatfish.rankine.fluids.HexafluorosilicicAcidFluid;
import com.cannolicatfish.rankine.fluids.HydrobromicAcidFluid;
import com.cannolicatfish.rankine.fluids.JugloneFluid;
import com.cannolicatfish.rankine.fluids.LatexFluid;
import com.cannolicatfish.rankine.fluids.LiquidMercuryFluid;
import com.cannolicatfish.rankine.fluids.MapleSapFluid;
import com.cannolicatfish.rankine.fluids.RedMudFluid;
import com.cannolicatfish.rankine.fluids.ResinFluid;
import com.cannolicatfish.rankine.fluids.SapFluid;
import com.cannolicatfish.rankine.fluids.SulfuricAcidFluid;
import com.cannolicatfish.rankine.fluids.WhiteLiquorFluid;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineFluids;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.util.colors.AlloyBlockColor;
import com.cannolicatfish.rankine.util.colors.AlloyItemColor;
import com.cannolicatfish.rankine.util.colors.CrucibleColor;
import com.cannolicatfish.rankine.util.colors.GrassBlockBaseColor;
import com.cannolicatfish.rankine.util.colors.GrassItemBaseColor;
import com.cannolicatfish.rankine.util.colors.LeavesBlockBaseColor;
import com.cannolicatfish.rankine.util.colors.LeavesItemBaseColor;
import com.cannolicatfish.rankine.util.colors.NonAlloyBlockColor;
import com.cannolicatfish.rankine.util.colors.NonAlloyItemColor;
import com.cannolicatfish.rankine.util.colors.OrnamentColor;
import com.cannolicatfish.rankine.util.colors.SGVDItemColor;
import com.cannolicatfish.rankine.util.colors.TemplateItemColor;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Client-only registry events ported from the 1.18.2 {@code ProjectRankine.RegistryEvents} inner class
 * (item/block colors, entity renderers) plus the client fluid extensions and wood sheet registration that
 * used to happen in {@code ClientProxy}/{@code ProjectRankine.ClientSetup}.
 *
 * NOTE: item property overrides (throwing pose, building mode, gas held) and menu screens live in
 * {@link ClientSetup}, owned by another agent — not duplicated here.
 */
@EventBusSubscriber(modid = ProjectRankine.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RankineClientEvents {

    @SubscribeEvent
    public static void onItemColorRegistry(final RegisterColorHandlersEvent.Item event) {
        for (Block b : RankineLists.GRASS_BLOCKS) {
            event.register(new GrassItemBaseColor(), b.asItem());
        }
        for (Block b : Stream.of(RankineLists.GLAZED_PORCELAIN_BLOCKS, RankineLists.ALLOY_POLES, RankineLists.ALLOY_BARS, RankineLists.ALLOY_LADDERS, RankineLists.SHEETMETALS, RankineLists.ALLOY_SHEETMETALS).flatMap(Collection::stream).collect(Collectors.toList())) {
            event.register(new NonAlloyItemColor(), b);
        }
        event.register(new GrassItemBaseColor(), RankineItems.SHORT_GRASS.get());
        event.register(new GrassItemBaseColor(), RankineItems.SOD_BLOCK.get());

        event.register(new LeavesItemBaseColor(), RankineBlocks.BIRCH_LEAF_LITTER.get(), RankineBlocks.SPRUCE_LEAF_LITTER.get(), RankineBlocks.ACACIA_LEAF_LITTER.get(), RankineBlocks.JUNGLE_LEAF_LITTER.get(), RankineBlocks.DARK_OAK_LEAF_LITTER.get(), RankineBlocks.OAK_LEAF_LITTER.get());

        event.register(new AlloyItemColor(),
                RankineItems.ALLOY_BLOCK.get(), RankineItems.ALLOY_GEAR.get(), RankineItems.ALLOY_DUST.get(),
                RankineItems.ALLOY_INGOT.get(), RankineItems.ALLOY_NUGGET.get(), RankineItems.ALLOY_PLATE.get(),
                RankineItems.ALLOY_ROD.get(), RankineItems.ALLOY_WIRE.get(), RankineItems.ALLOY_PICKAXE.get(),
                RankineItems.ALLOY_AXE.get(), RankineItems.ALLOY_SHOVEL.get(), RankineItems.ALLOY_SWORD.get(),
                RankineItems.ALLOY_HOE.get(), RankineItems.ALLOY_SPEAR.get(), RankineItems.ALLOY_HAMMER.get(),
                RankineItems.ALLOY_KNIFE.get(), RankineItems.ALLOY_CROWBAR.get(), RankineItems.ALLOY_BLUNDERBUSS.get(),
                RankineItems.ALLOY_HELMET.get(), RankineItems.ALLOY_CHESTPLATE.get(), RankineItems.ALLOY_LEGGINGS.get(),
                RankineItems.ALLOY_BOOTS.get(), RankineItems.ALLOY_ARROW.get());

        event.register(new TemplateItemColor(), RankineItems.ALLOY_TEMPLATE.get());
        event.register(new SGVDItemColor(), RankineItems.SHULKER_GAS_VACUUM.get());
    }

    @SubscribeEvent
    public static void onBlockColorRegistry(final RegisterColorHandlersEvent.Block event) {
        event.register(new CrucibleColor(), RankineBlocks.CRUCIBLE_BLOCK.get());
        for (Block b : RankineLists.GRASS_BLOCKS) {
            event.register(new GrassBlockBaseColor(), b);
        }
        event.register(new AlloyBlockColor(), RankineBlocks.ALLOY_BLOCK.get());
        event.register(new OrnamentColor(), RankineBlocks.ORNAMENT.get());
        event.register(new GrassBlockBaseColor(), RankineBlocks.SHORT_GRASS.get());
        event.register(new GrassBlockBaseColor(), RankineBlocks.SOD_BLOCK.get());
        event.register(new LeavesBlockBaseColor(), RankineBlocks.BIRCH_LEAF_LITTER.get(), RankineBlocks.SPRUCE_LEAF_LITTER.get(), RankineBlocks.ACACIA_LEAF_LITTER.get(), RankineBlocks.JUNGLE_LEAF_LITTER.get(), RankineBlocks.DARK_OAK_LEAF_LITTER.get(), RankineBlocks.OAK_LEAF_LITTER.get());

        for (Block b : Stream.of(RankineLists.GLAZED_PORCELAIN_BLOCKS, RankineLists.ALLOY_POLES, RankineLists.ALLOY_BARS, RankineLists.ALLOY_LADDERS, RankineLists.SHEETMETALS, RankineLists.ALLOY_SHEETMETALS).flatMap(Collection::stream).collect(Collectors.toList())) {
            event.register(new NonAlloyBlockColor(), b);
        }
    }

    @SubscribeEvent
    public static void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(RankineEntityTypes.ALLOY_SPEAR.get(), SpearEntityRenderer.instance);
        event.registerEntityRenderer(RankineEntityTypes.REACTIVE_ITEM.get(), ReactiveItemRenderer::new);
        event.registerEntityRenderer(RankineEntityTypes.RANKINE_BOAT.get(), RankineBoatRenderer.instance);
        event.registerEntityRenderer(RankineEntityTypes.ROPE_COIL_ARROW.get(), RopeCoilArrowRenderer.instance);
        event.registerEntityRenderer(RankineEntityTypes.ALLOY_ARROW.get(), AlloyArrowRenderer.instance);
        event.registerEntityRenderer(RankineEntityTypes.CANNONBALL.get(), CannonballRenderer::new);
        event.registerEntityRenderer(RankineEntityTypes.ENDERBALL.get(), EnderballRenderer::new);
        event.registerEntityRenderer(RankineEntityTypes.CARCASS.get(), CarcassRenderer::new);
        event.registerEntityRenderer(RankineEntityTypes.BALLOON.get(), BalloonRenderer::new);

        // Moved here from RankineBlockEntityTypes#registerBlockEntityRenders (see PORT-TODO(client) there).
        event.registerBlockEntityRenderer(RankineBlockEntityTypes.RANKINE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(RankineBlockEntityTypes.PEDESTAL.get(), PedestalRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Rankine's boat has 29 wood skins but one shared geometry; vanilla only bakes boat layers for its own
        // Boat.Type values, so we register our own layer here (see PORT-TODO in RankineBoatRenderer).
        event.registerLayerDefinition(RankineBoatRenderer.RANKINE_BOAT_LAYER, BoatModel::createBodyModel);
    }

    @SubscribeEvent
    public static void registerClientExtensions(final RegisterClientExtensionsEvent event) {
        // Worn alloy armor: supplies the alloy colour to HumanoidArmorLayer's dyeable layer, which
        // NeoForge otherwise reads from DataComponents.DYED_COLOR (a component alloy items never
        // write). See AlloyArmorClientExtensions.
        event.registerItem(AlloyArmorClientExtensions.INSTANCE,
                RankineItems.ALLOY_HELMET.get(), RankineItems.ALLOY_CHESTPLATE.get(),
                RankineItems.ALLOY_LEGGINGS.get(), RankineItems.ALLOY_BOOTS.get());

        // All 1.18.2 FluidAttributes used tint 0xFFFFFFFF; still/flowing/overlay textures come from the
        // constants on each fluid's texture-holder class in com.cannolicatfish.rankine.fluids.
        event.registerFluidType(fluidExtensions(LiquidMercuryFluid.FLUID_STILL, LiquidMercuryFluid.FLUID_FLOWING, LiquidMercuryFluid.OVERLAY), RankineFluids.LIQUID_MERCURY_TYPE);
        event.registerFluidType(fluidExtensions(SapFluid.FLUID_STILL, SapFluid.FLUID_FLOWING, SapFluid.OVERLAY), RankineFluids.SAP_TYPE);
        event.registerFluidType(fluidExtensions(MapleSapFluid.FLUID_STILL, MapleSapFluid.FLUID_FLOWING, MapleSapFluid.OVERLAY), RankineFluids.MAPLE_SAP_TYPE);
        event.registerFluidType(fluidExtensions(LatexFluid.FLUID_STILL, LatexFluid.FLUID_FLOWING, LatexFluid.OVERLAY), RankineFluids.LATEX_TYPE);
        event.registerFluidType(fluidExtensions(JugloneFluid.FLUID_STILL, JugloneFluid.FLUID_FLOWING, JugloneFluid.OVERLAY), RankineFluids.JUGLONE_TYPE);
        event.registerFluidType(fluidExtensions(ResinFluid.FLUID_STILL, ResinFluid.FLUID_FLOWING, ResinFluid.OVERLAY), RankineFluids.RESIN_TYPE);
        event.registerFluidType(fluidExtensions(AquaRegiaFluid.FLUID_STILL, AquaRegiaFluid.FLUID_FLOWING, AquaRegiaFluid.OVERLAY), RankineFluids.AQUA_REGIA_TYPE);
        event.registerFluidType(fluidExtensions(CarbonDisulfideFluid.FLUID_STILL, CarbonDisulfideFluid.FLUID_FLOWING, CarbonDisulfideFluid.OVERLAY), RankineFluids.CARBON_DISULFIDE_TYPE);
        event.registerFluidType(fluidExtensions(HexafluorosilicicAcidFluid.FLUID_STILL, HexafluorosilicicAcidFluid.FLUID_FLOWING, HexafluorosilicicAcidFluid.OVERLAY), RankineFluids.HEXAFLUOROSILICIC_ACID_TYPE);
        event.registerFluidType(fluidExtensions(HydrobromicAcidFluid.FLUID_STILL, HydrobromicAcidFluid.FLUID_FLOWING, HydrobromicAcidFluid.OVERLAY), RankineFluids.HYDROBROMIC_ACID_TYPE);
        event.registerFluidType(fluidExtensions(GrayMudFluid.FLUID_STILL, GrayMudFluid.FLUID_FLOWING, GrayMudFluid.OVERLAY), RankineFluids.GRAY_MUD_TYPE);
        event.registerFluidType(fluidExtensions(RedMudFluid.FLUID_STILL, RedMudFluid.FLUID_FLOWING, RedMudFluid.OVERLAY), RankineFluids.RED_MUD_TYPE);
        event.registerFluidType(fluidExtensions(SulfuricAcidFluid.FLUID_STILL, SulfuricAcidFluid.FLUID_FLOWING, SulfuricAcidFluid.OVERLAY), RankineFluids.SULFURIC_ACID_TYPE);
        event.registerFluidType(fluidExtensions(BlackLiquorFluid.FLUID_STILL, BlackLiquorFluid.FLUID_FLOWING, BlackLiquorFluid.OVERLAY), RankineFluids.BLACK_LIQUOR_TYPE);
        event.registerFluidType(fluidExtensions(GreenLiquorFluid.FLUID_STILL, GreenLiquorFluid.FLUID_FLOWING, GreenLiquorFluid.OVERLAY), RankineFluids.GREEN_LIQUOR_TYPE);
        event.registerFluidType(fluidExtensions(WhiteLiquorFluid.FLUID_STILL, WhiteLiquorFluid.FLUID_FLOWING, WhiteLiquorFluid.OVERLAY), RankineFluids.WHITE_LIQUOR_TYPE);
    }

    private static IClientFluidTypeExtensions fluidExtensions(ResourceLocation still, ResourceLocation flowing, ResourceLocation overlay) {
        return new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return still;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowing;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return overlay;
            }

            @Override
            public int getTintColor() {
                return 0xFFFFFFFF;
            }
        };
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        // Former ProjectRankine#ClientSetup body (item properties/menu screens now live in ClientSetup.java).
        event.enqueueWork(() -> {
            for (RankineWood wood : RankineLists.RANKINE_WOODS) {
                Sheets.addWoodType(wood.getWoodType());
            }
        });
    }
}
