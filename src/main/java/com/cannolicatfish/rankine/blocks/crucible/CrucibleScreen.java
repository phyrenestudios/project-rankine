package com.cannolicatfish.rankine.blocks.crucible;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class CrucibleScreen extends AbstractContainerScreen<CrucibleContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/crucible.png");
    public CrucibleScreen(CrucibleContainer container, Inventory inv, Component name) {
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
        this.minecraft.getTextureManager().bindForSetup(GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(p_230450_1_,relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.getHeatStatus() > 0)
        {
            this.blit(p_230450_1_,this.leftPos + 78, this.topPos + 77, 176, 29, 18, 4);
        }

        int l = this.menu.getCookProgressScaled(27);
        this.blit(p_230450_1_,this.leftPos + 108, this.topPos + 44, 176, 0, 10, l + 1);

    }

    @Override
    protected void renderLabels(PoseStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawCenteredString(p_230451_1_, Minecraft.getInstance().font, "Crucible", 88, 6, 0xffffff);
    }


}
