package com.cannolicatfish.rankine.events.handlers.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

public class RenderBlockOverlayHandler {
    public static void renderOverlayBlockEvent( RenderBlockOverlayEvent event ) {
        if (event.getOverlayType() != RenderBlockOverlayEvent.OverlayType.WATER)
            return;
        ResourceLocation registryName = event.getBlockForOverlay().getBlock().getRegistryName();
        if(registryName == null)
            return;
        if(!registryName.getNamespace().equals("rankine"))
            return;

        Minecraft minecraftIn = Minecraft.getInstance();
        // Null player check
        if(minecraftIn.player == null)
            return;
        RenderSystem.enableTexture();
        minecraftIn.getTextureManager().bindTexture(new ResourceLocation(
                "rankine:textures/block/" + event.getBlockForOverlay().getBlock().getRegistryName().getPath() +
                        "_overlay.png"));
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        float brightness = minecraftIn.player.getBrightness();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        // Commented out because it was never used
        // It was just causing unnecessary overhead
//        float f1 = 4.0F;
//        float f2 = -1.0F;
//        float f3 = 1.0F;
//        float f4 = -1.0F;
//        float f5 = 1.0F;
//        float f6 = -0.5F;
        float f7 = -minecraftIn.player.rotationYaw / 64.0F;
        float f8 = minecraftIn.player.rotationPitch / 64.0F;
        Matrix4f matrix4f = event.getMatrixStack().getLast().getMatrix();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        bufferbuilder.pos(matrix4f, -1.0F, -1.0F, -0.5F).color(brightness, brightness, brightness, 0.1F).tex(4.0F + f7, 4.0F + f8).endVertex();
        bufferbuilder.pos(matrix4f, 1.0F, -1.0F, -0.5F).color(brightness, brightness, brightness, 0.1F).tex(0.0F + f7, 4.0F + f8).endVertex();
        bufferbuilder.pos(matrix4f, 1.0F, 1.0F, -0.5F).color(brightness, brightness, brightness, 0.1F).tex(0.0F + f7, 0.0F + f8).endVertex();
        bufferbuilder.pos(matrix4f, -1.0F, 1.0F, -0.5F).color(brightness, brightness, brightness, 0.1F).tex(4.0F + f7, 0.0F + f8).endVertex();
        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
        RenderSystem.disableBlend();
        event.setCanceled(true);
    }
}
