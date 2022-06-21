package com.cannolicatfish.rankine.events.handlers.client;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class RenderGameOverlayHandler {
    public static void renderOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.LAYER && Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            ItemStack stack = player.getOffhandItem().getItem() instanceof KnifeItem ? player.getOffhandItem() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                int i = stack.getItem().getUseDuration(stack) - player.getUseItemRemainingTicks();
                if (i < (10 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.PREPARATION,stack))) {
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    RenderSystem.disableDepthTest();
                    RenderSystem.depthMask(false);
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    //RenderSystem.disableAlphaTest();
                    Minecraft.getInstance().getTextureManager().bindForSetup(new ResourceLocation("rankine:textures/misc/parry_overlay.png"));
                    Tesselator tessellator = Tesselator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuilder();
                    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                    bufferbuilder.vertex(0.0D, (double)event.getWindow().getGuiScaledHeight(), -90.0D).uv(0.0F, 1.0F).endVertex();
                    bufferbuilder.vertex((double)event.getWindow().getGuiScaledWidth(), (double)event.getWindow().getGuiScaledHeight(), -90.0D).uv(1.0F, 1.0F).endVertex();
                    bufferbuilder.vertex((double)event.getWindow().getGuiScaledWidth(), 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
                    bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
                    tessellator.end();
                    RenderSystem.depthMask(true);
                    RenderSystem.enableDepthTest();
                    //RenderSystem.enableAlphaTest();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                }
            }
        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.LAYER && Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            Level worldIn = player.getCommandSenderWorld();

            Block bl = worldIn.getBlockState(new BlockPos(player.getX(),player.getEyeY(),player.getZ())).getBlock();
            if (bl instanceof GasBlock) {
                ResourceLocation gasBlock = new ResourceLocation("rankine:textures/block/"+bl.getRegistryName().getPath()+".png");

                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.05F);
                RenderSystem.setShaderTexture(0, gasBlock);
                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder bufferbuilder = tesselator.getBuilder();
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferbuilder.vertex(0.0D, (double)event.getWindow().getScreenHeight(), -90.0D).uv(0.0F, 1.0F).endVertex();
                bufferbuilder.vertex((double)event.getWindow().getScreenWidth(), (double)event.getWindow().getScreenHeight(), -90.0D).uv(1.0F, 1.0F).endVertex();
                bufferbuilder.vertex((double)event.getWindow().getScreenWidth(), 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
                bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
                tesselator.end();
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
