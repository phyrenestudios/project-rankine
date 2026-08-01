package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.events.handlers.client.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.FOVModifierEvent;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.client.event.RenderBlockOverlayEvent;
import net.neoforged.neoforge.client.event.RenderGameOverlayEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class RankineClientEventHandler {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        RecipesUpdatedHandler.onRecipesUpdated(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void fovUpdate(FOVModifierEvent event) {
        FOVModifierHandler.fovUpdate(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void renderGameOverlayEvent(RenderGameOverlayEvent event) {
        RenderGameOverlayHandler.renderOverlay(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void renderBlockOverlayEvent(RenderBlockOverlayEvent event) {
        RenderBlockOverlayHandler.renderOverlayBlockEvent(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onTooltipCheck(ItemTooltipEvent event) {
        ItemTooltipHandler.onTooltipCheck(event);
    }


}