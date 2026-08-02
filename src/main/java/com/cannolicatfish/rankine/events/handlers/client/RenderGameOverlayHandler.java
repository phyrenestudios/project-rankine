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
 */
@EventBusSubscriber(modid = ProjectRankine.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderGameOverlayHandler {

    private static final ResourceLocation PARRY_OVERLAY_LAYER = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "parry_overlay");
    private static final ResourceLocation GAS_OVERLAY_LAYER = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "gas_overlay");
    private static final ResourceLocation PARRY_TEXTURE = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/misc/parry_overlay.png");

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, PARRY_OVERLAY_LAYER, RenderGameOverlayHandler::renderParryOverlay);
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
        guiGraphics.blit(texture, 0, 0, 0, 0.0F, 0.0F, w, h, w, h);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}
