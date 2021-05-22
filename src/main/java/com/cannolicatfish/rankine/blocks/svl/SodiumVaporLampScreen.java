package com.cannolicatfish.rankine.blocks.svl;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class SodiumVaporLampScreen extends ContainerScreen<SodiumVaporLampContainer> {
    private int currentScroll = 100;

    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/sodium_vapor_lamp.png");
    public SodiumVaporLampScreen(SodiumVaporLampContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawString(p_230451_1_,Minecraft.getInstance().fontRenderer, "Sodium Vapor Lamp", 92, 10, 0xffffff);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(this.GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(matrixStack,relX, relY, 0, 0, this.xSize, this.ySize);
        this.blit(matrixStack, this.guiLeft + 48, this.guiTop + 22, 0, 254, this.currentScroll + 1, 2);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        this.currentScroll = this.currentScroll + (int) (delta);
        this.currentScroll = MathHelper.clamp(this.currentScroll, 1, 100);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }



}
