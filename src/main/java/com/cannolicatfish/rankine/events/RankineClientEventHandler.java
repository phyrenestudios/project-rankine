package com.cannolicatfish.rankine.events;


import com.cannolicatfish.rankine.events.handlers.client.FOVUpdateHandler;
import com.cannolicatfish.rankine.events.handlers.client.ItemTooltipHandler;
import com.cannolicatfish.rankine.events.handlers.client.RecipesUpdateHandler;
import com.cannolicatfish.rankine.events.handlers.client.RenderBlockOverlayHandler;
import com.cannolicatfish.rankine.events.handlers.client.RenderOverlayHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
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
        RecipesUpdateHandler.onRecipesUpdated(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void fovUpdate(FOVUpdateEvent event) {
        FOVUpdateHandler.fovUpdate(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void renderOverlay(RenderGameOverlayEvent event) {
        RenderOverlayHandler.renderOverlay(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void renderOverlayBlockEvent(RenderBlockOverlayEvent event) {
        RenderBlockOverlayHandler.renderOverlayBlockEvent(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onTooltipCheck(ItemTooltipEvent event) {
        ItemTooltipHandler.onTooltipCheck(event);
    }


}