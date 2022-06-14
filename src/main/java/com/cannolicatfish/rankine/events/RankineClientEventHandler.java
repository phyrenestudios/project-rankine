package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.events.handlers.client.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
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