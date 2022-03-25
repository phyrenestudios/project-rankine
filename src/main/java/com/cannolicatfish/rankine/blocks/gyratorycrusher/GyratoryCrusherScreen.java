package com.cannolicatfish.rankine.blocks.gyratorycrusher;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.gyratorycrusher.GyratoryCrusherContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class GyratoryCrusherScreen extends AbstractContainerScreen<GyratoryCrusherContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/gyratory_crusher.png");
    public GyratoryCrusherScreen(GyratoryCrusherContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawString(p_230451_1_, Minecraft.getInstance().font, "Gyratory Crusher", 80, 10, 0xffffff);
    }

    @Override
    protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        GlStateManager._clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindForSetup(GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(p_230450_1_,relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.isBurning())
        {

            int k = this.menu.getBurnLeftScaled(13);
            this.blit(p_230450_1_,this.leftPos + 11, this.topPos + 21 + 12 - k, 180, 12 - k, 14, k + 1);
        }

        int l = this.menu.getCookProgressScaled(24);
        if (l % 2 == 0)
        {
            this.blit(p_230450_1_,this.leftPos + 56, this.topPos + 8, 180, 31, 16, 22);
        }

        this.blit(p_230450_1_,this.leftPos + 55, this.topPos + 58, 180, 14, l + 1, 16);
    }



}

