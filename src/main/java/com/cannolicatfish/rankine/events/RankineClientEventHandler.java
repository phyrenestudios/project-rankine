package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.events.handlers.client.FOVUpdateHandler;
import com.cannolicatfish.rankine.events.handlers.client.RecipesUpdateHandler;
import com.cannolicatfish.rankine.events.handlers.client.RenderBlockOverlayHandler;
import com.cannolicatfish.rankine.events.handlers.client.RenderOverlayHandler;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

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


}