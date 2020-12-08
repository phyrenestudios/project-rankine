package com.cannolicatfish.rankine.blocks.inductionfurnace;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class InductionFurnaceScreen extends ContainerScreen<InductionFurnaceContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/induction_furnace.png");
    public InductionFurnaceScreen(InductionFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
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
        if(this.container.isBurning())
        {

            int k = this.container.getBurnLeftScaled(13);
            this.blit(p_230450_1_, this.guiLeft + 11, this.guiTop + 37 + 12 - k, 176, 12 - k, 14, k + 1);

        }

        int l = this.container.getCookProgressScaled(24);
        this.blit(p_230450_1_, this.guiLeft + 98, this.guiTop + 48, 176, 14, l + 1, 16);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, "Induction Furnace", 92, 10, 0xffffff);
        if (!this.container.isRecipeMode())
        {
            drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot1() + "%", 42, 21, 0xffffff);
            drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot2() +"%", 64, 21, 0xffffff);
            drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot3() +"%", 86, 21, 0xffffff);
            drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot4() + "%", 51, 74, 0xff55ff);
            drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot5() + "%", 75, 74, 0xff55ff);
        }
    }



}