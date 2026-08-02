package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.events.handlers.client.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

/**
 * Client game-bus dispatcher.
 * 1.21.1: @OnlyIn(Dist.CLIENT) on the individual methods is replaced by the class-level
 * {@code value = Dist.CLIENT} on @EventBusSubscriber. The old RenderGameOverlayEvent subscription
 * moved to a registered GUI layer — see {@link RenderGameOverlayHandler}, which subscribes to
 * RegisterGuiLayersEvent on the MOD bus itself.
 * The renderBlockOverlayEvent method was non-static in 1.18.2 (and therefore never fired on the
 * static subscriber); it is static now so the overlay actually works.
 */
@EventBusSubscriber(modid = ProjectRankine.MODID, value = Dist.CLIENT)
public class RankineClientEventHandler {

    @SubscribeEvent
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        RecipesUpdatedHandler.onRecipesUpdated(event);
    }

    @SubscribeEvent
    public static void fovUpdate(ComputeFovModifierEvent event) {
        FOVModifierHandler.fovUpdate(event);
    }

    @SubscribeEvent
    public static void renderBlockOverlayEvent(RenderBlockScreenEffectEvent event) {
        RenderBlockOverlayHandler.renderOverlayBlockEvent(event);
    }

    @SubscribeEvent
    public static void onTooltipCheck(ItemTooltipEvent event) {
        ItemTooltipHandler.onTooltipCheck(event);
    }


}
