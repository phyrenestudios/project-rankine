package com.cannolicatfish.rankine.events.handlers.client;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.gases.AbstractGasBlock;
import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

/**
 * 1.21.1: RenderGameOverlayEvent(ElementType.LAYER) is gone. Full-screen HUD overlays are now
 * registered GUI layers ({@link RegisterGuiLayersEvent} on the MOD bus) drawn through
 * {@link GuiGraphics}; the manual Tesselator quad becomes a blit stretched over the whole screen.
 * The two overlays keep their original order (parry first, then gas).
 *
 * <p><b>Port fix F23 / finding #26.</b> 1.18.2's RankineClientEventHandler subscribed to the
 * abstract base {@code RenderGameOverlayEvent}, so the listener ran for every layer's Pre AND
 * Post firing -- {@link VanillaGuiLayers} lists 25 named layers today, and 1.18.2's
 * ForgeIngameGui rendered a comparable number, so the 0.05-alpha quad was composited on the
 * order of 50 times per frame with standard SRC_ALPHA/ONE_MINUS_SRC_ALPHA blending. 1.21.1
 * registers exactly ONE gui layer per overlay, rendered once, at the same 0.05 alpha -- roughly
 * 1/50th the coverage, which reads as "nearly invisible" rather than the deliberate translucent
 * tint 1.18.2 produced. Fixed by literally reproducing the repeated-composite: the same low
 * per-pass alpha, blit {@link #HUD_OVERLAY_PASSES} times in one draw call, which is exact
 * accumulation math (1-(1-a)^N) rather than a single guessed replacement alpha.
 *
 * <p>Also moved from registering above {@link VanillaGuiLayers#CAMERA_OVERLAYS} (the FIRST
 * vanilla layer, meaning the hotbar/health/hunger/chat/etc. all drew on top of the overlay,
 * burying it under the HUD) to above {@link VanillaGuiLayers#SAVING_INDICATOR} (the LAST vanilla
 * layer), matching 1.18.2 firing after every layer including the last.
 */
@EventBusSubscriber(modid = ProjectRankine.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderGameOverlayHandler {

    private static final ResourceLocation PARRY_OVERLAY_LAYER = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "parry_overlay");
    private static final ResourceLocation GAS_OVERLAY_LAYER = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "gas_overlay");
    private static final ResourceLocation PARRY_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/misc/parry_overlay.png");

    // 25 named VanillaGuiLayers entries today x 2 (1.18.2's Forge fired both PreLayer and
    // PostLayer at ElementType.LAYER for every layer) = the ~50 passes finding #26 measured.
    private static final int HUD_OVERLAY_PASSES = 50;

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.SAVING_INDICATOR, PARRY_OVERLAY_LAYER, RenderGameOverlayHandler::renderParryOverlay);
        event.registerAbove(PARRY_OVERLAY_LAYER, GAS_OVERLAY_LAYER, RenderGameOverlayHandler::renderGasOverlay);
    }

    private static void renderParryOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack stack = player.getOffhandItem().getItem() instanceof KnifeItem ? player.getOffhandItem() : ItemStack.EMPTY;
        if (stack.isEmpty()) return;

        int i = stack.getUseDuration(player) - player.getUseItemRemainingTicks();
        if (i < (10 + RankineEnchantmentHelper.getLevel(player.level(), RankineEnchantments.PREPARATION, stack))) {
            drawFullscreen(guiGraphics, PARRY_TEXTURE);
        }
    }

    private static void renderGasOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        Level worldIn = player.getCommandSenderWorld();

        Block bl = worldIn.getBlockState(BlockPos.containing(player.getX(), player.getEyeY(), player.getZ())).getBlock();
        if (bl instanceof AbstractGasBlock) {
            ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(bl);
            if (blockId == null) return;
            drawFullscreen(guiGraphics, ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/block/" + blockId.getPath() + ".png"));
        }
    }

    private static void drawFullscreen(GuiGraphics guiGraphics, ResourceLocation texture) {
        int w = guiGraphics.guiWidth();
        int h = guiGraphics.guiHeight();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 0.05F);
        // Same per-pass alpha 1.18.2 used, composited HUD_OVERLAY_PASSES times to reproduce its
        // accumulated coverage -- see the class javadoc.
        for (int i = 0; i < HUD_OVERLAY_PASSES; i++) {
            guiGraphics.blit(texture, 0, 0, 0, 0.0F, 0.0F, w, h, w, h);
        }
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}
