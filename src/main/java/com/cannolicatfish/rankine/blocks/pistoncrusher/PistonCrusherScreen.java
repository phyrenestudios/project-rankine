package com.cannolicatfish.rankine.blocks.pistoncrusher;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PistonCrusherScreen extends ContainerScreen<PistonCrusherContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/piston_crusher.png");
    public PistonCrusherScreen(PistonCrusherContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawString(p_230451_1_,Minecraft.getInstance().font, "Piston Crusher", 92, 10, 0xffffff);
    }

    @Override
    protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
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
