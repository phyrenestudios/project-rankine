package com.cannolicatfish.rankine.events.handlers.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import org.joml.Matrix4f;

/**
 * 1.21.1 notes:
 * - RenderBlockOverlayEvent -> RenderBlockScreenEffectEvent (same OverlayType enum, now nested there).
 * - com.mojang.math.Matrix4f -> org.joml.Matrix4f.
 * - RenderSystem.enableTexture() and BufferBuilder#begin/end/endVertex are gone: buffers come from
 *   Tesselator#begin and are drawn with BufferUploader.drawWithShader(builder.buildOrThrow()),
 *   vertices use addVertex/setUv/setColor (see vanilla ScreenEffectRenderer#renderFluid).
 * - Block#getRegistryName() -> BuiltInRegistries.BLOCK.getKey(block).
 */
public class RenderBlockOverlayHandler {
    public static void renderOverlayBlockEvent(RenderBlockScreenEffectEvent event) {
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(event.getBlockState().getBlock());
        if (event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.WATER && blockId != null && blockId.getNamespace().equals("rankine")) {
            Minecraft minecraftIn = Minecraft.getInstance();
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("rankine", "textures/block/" + blockId.getPath() + "_overlay.png");
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.setShaderTexture(0, texture);
            float f = minecraftIn.player.getBrightness();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            float f7 = -minecraftIn.player.getYRot() / 64.0F;
            float f8 = minecraftIn.player.getXRot() / 64.0F;
            Matrix4f matrix4f = event.getPoseStack().last().pose();
            BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferbuilder.addVertex(matrix4f, -1.0F, -1.0F, -0.5F).setUv(4.0F + f7, 4.0F + f8).setColor(f, f, f, 0.1F);
            bufferbuilder.addVertex(matrix4f, 1.0F, -1.0F, -0.5F).setUv(0.0F + f7, 4.0F + f8).setColor(f, f, f, 0.1F);
            bufferbuilder.addVertex(matrix4f, 1.0F, 1.0F, -0.5F).setUv(0.0F + f7, 0.0F + f8).setColor(f, f, f, 0.1F);
            bufferbuilder.addVertex(matrix4f, -1.0F, 1.0F, -0.5F).setUv(4.0F + f7, 0.0F + f8).setColor(f, f, f, 0.1F);
            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
            RenderSystem.disableBlend();
            event.setCanceled(true);
        }
    }
}
