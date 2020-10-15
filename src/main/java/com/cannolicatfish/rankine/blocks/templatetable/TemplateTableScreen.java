package com.cannolicatfish.rankine.blocks.templatetable;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TemplateTableScreen extends ContainerScreen<TemplateTableContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/template_table.png");
    public TemplateTableScreen(TemplateTableContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.ySize = 182;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(this.GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(p_230450_1_, relX, relY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, "Template Table", 110, 10, 0xffffff);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot1() + "%", 16, 72, 0xffffff);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot2() +"%", 34, 72, 0xffffff);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot3() +"%", 52, 72, 0x00aa00);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot4() + "%", 70, 72, 0xff55ff);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot5() + "%", 88, 72, 0xff55ff);
    }



}
