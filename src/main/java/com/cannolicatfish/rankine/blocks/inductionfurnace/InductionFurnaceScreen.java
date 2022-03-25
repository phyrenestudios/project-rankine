package com.cannolicatfish.rankine.blocks.inductionfurnace;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class InductionFurnaceScreen extends AbstractContainerScreen<InductionFurnaceContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/induction_furnace.png");
    public InductionFurnaceScreen(InductionFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        GlStateManager._clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindForSetup(this.GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(p_230450_1_, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(p_230450_1_, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.isBurning())
        {

            int k = this.menu.getBurnLeftScaled(13);
            this.blit(p_230450_1_, this.leftPos + 10, this.topPos + 22 + 12 - k, 176, 12 - k, 14, k + 1);

        }

        int l = this.menu.getCookProgressScaled(24);
        this.blit(p_230450_1_, this.leftPos + 98, this.topPos + 32, 176, 14, l + 1, 16);
    }

    @Override
    protected void renderLabels(PoseStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawCenteredString(p_230451_1_, Minecraft.getInstance().font, "Induction Furnace", 92, 10, 0xffffff);
        int ymod = 0;
        for (String s : this.menu.getOutputString().getKey()) {
            drawCenteredString(p_230451_1_, Minecraft.getInstance().font, s, 92, 60 + ymod, this.menu.getOutputString().getValue());
            ymod += 10;
        }

    }



}
