package com.cannolicatfish.rankine.blocks.alloyfurnace;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.storage.MapData;

import javax.annotation.Nullable;

public class AlloyFurnaceScreen extends ContainerScreen<AlloyFurnaceContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/alloy_furnace.png");
    public AlloyFurnaceScreen(AlloyFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
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
            this.blit(p_230450_1_, this.guiLeft + 11, this.guiTop + 21 + 12 - k, 176, 12 - k, 14, k + 1);

        }

        int l = this.container.getCookProgressScaled(24);
        this.blit(p_230450_1_, this.guiLeft + 98, this.guiTop + 32, 176, 14, l + 1, 16);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, "Alloy Furnace", 92, 10, 0xffffff);
        int ymod = 0;
        for (String s : this.container.getOutputString().getKey()) {
            drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, s, 92, 60 + ymod, this.container.getOutputString().getValue());
            ymod += 10;
        }

    }



}

