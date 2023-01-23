package com.cannolicatfish.rankine.blocks.batterycharger;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BatteryChargerScreen extends AbstractContainerScreen<BatteryChargerContainer>{
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/battery_charger.png");
    public BatteryChargerScreen(BatteryChargerContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(p_230450_1_,relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        int l = this.menu.getSignalPowerScaled(30);
        if (l > 0) {
            this.blit(p_230450_1_,this.leftPos + 10, this.topPos + 5, 179, 0, 16, 16);
        }
        this.blit(p_230450_1_,this.leftPos + 10, this.topPos + 25, 179, 16, 16, 30-l);
    }

    @Override
    protected void renderLabels(PoseStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawCenteredString(p_230451_1_, Minecraft.getInstance().font, "Battery Holder", 92, 10, 0xffffff);
    }
}