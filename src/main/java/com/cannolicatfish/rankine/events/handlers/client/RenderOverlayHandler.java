package com.cannolicatfish.rankine.events.handlers.client;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class RenderOverlayHandler {

    private static final ResourceLocation TEXTURE = new ResourceLocation("rankine:textures/misc/parry_overlay.png");

    public static void renderOverlay( RenderGameOverlayEvent event) {
        // Return if the player is null.
        if(Minecraft.getInstance().player == null)
            return;
        // Return if the render is not for the helmet.
        if(event.getType() != RenderGameOverlayEvent.ElementType.HELMET)
            return;

        PlayerEntity player = Minecraft.getInstance().player;
        ItemStack stack = player.getHeldItemOffhand().getItem() instanceof KnifeItem ? player.getHeldItemOffhand() : ItemStack.EMPTY;
        World worldIn = player.getEntityWorld();

        if (!stack.isEmpty()) {
            int i = stack.getItem().getUseDuration(stack) - player.getItemInUseCount();
            if (i < (10 + EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.PREPARATION, stack))) {
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.defaultBlendFunc();
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableAlphaTest();
                Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
                tessellateLogic(event);
            }
        }

        Block bl = worldIn.getBlockState(new BlockPos(player.getPosX(), player.getPosYEye(), player.getPosZ())).getBlock();
        if (bl instanceof GasBlock) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableAlphaTest();
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("rankine:textures/block/"+bl.getRegistryName().getPath()+".png"));
            tessellateLogic(event);
        }
    }

    private static void tessellateLogic( RenderGameOverlayEvent event ) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0D, event.getWindow().getScaledHeight(), -90.0D).tex(0.0F, 1.0F).endVertex();
        bufferbuilder.pos(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), -90.0D).tex(1.0F, 1.0F).endVertex();
        bufferbuilder.pos(event.getWindow().getScaledWidth(), 0.0D, -90.0D).tex(1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0F, 0.0F).endVertex();
        tessellator.draw();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

}
