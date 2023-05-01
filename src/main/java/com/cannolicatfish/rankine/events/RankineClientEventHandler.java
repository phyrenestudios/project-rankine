package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.events.handlers.client.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProjectRankine.MODID)
public class RankineClientEventHandler {


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        RecipesUpdatedHandler.onRecipesUpdated(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void fovUpdate(ComputeFovModifierEvent event) {
        FOVModifierHandler.fovUpdate(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void renderGameOverlayEvent(RenderGuiOverlayEvent event) {
        RenderGameOverlayHandler.renderOverlay(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void renderBlockOverlayEvent(RenderBlockScreenEffectEvent event) {
        RenderBlockOverlayHandler.renderOverlayBlockEvent(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onTooltipCheck(ItemTooltipEvent event) {
        ItemTooltipHandler.onTooltipCheck(event);
    }


}