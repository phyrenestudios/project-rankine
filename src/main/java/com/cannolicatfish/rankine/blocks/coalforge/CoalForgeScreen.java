package com.cannolicatfish.rankine.blocks.coalforge;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CoalForgeScreen extends ContainerScreen<CoalForgeContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/coal_forge.png");
    public CoalForgeScreen(CoalForgeContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        //this.renderHoveredToolTip(mouseX, mouseY);
    }
/*
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawCenteredString(Minecraft.getInstance().fontRenderer, "Coal Forge", 92, 10, 0xffffff);
        /*this.addButton((new ImageButton(this.guiLeft + 53, this.guiTop + 6, 20, 18, 0, 0, 19, button, (p_214087_1_) -> {
            this.container.toggleRecipeLock();
        })));
    }*/

    @Override
    protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        //this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
        if(this.container.isBurning())
        {

            int k = this.container.getBurnLeftScaled(13);
            //this.blit(this.guiLeft + 11, this.guiTop + 21 + 12 - k, 176, 12 - k, 14, k + 1);

        }

        int l = this.container.getCookProgressScaled(24);
        //this.blit(this.guiLeft + 113, this.guiTop + 37, 176, 14, l + 1, 16);

    }



}


