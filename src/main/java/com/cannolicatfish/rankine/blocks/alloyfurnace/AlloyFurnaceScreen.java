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
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        //this.renderHoveredToolTip(mouseX, mouseY);
    }

    protected void func_238807_a_(MatrixStack p_238807_1_, @Nullable MapData p_238807_2_, boolean p_238807_3_, boolean p_238807_4_, boolean p_238807_5_, boolean p_238807_6_) {

    }

    @Override
    protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
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

        if (this.container.isRSPower())
        {
            this.blit(p_230450_1_, this.guiLeft + 101, this.guiTop + 48, 176, 31, 16, 16);
        }
    }

    @Override
    protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, "Alloy Furnace", 92, 10, 0xffffff);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot1() + "%", 42, 50, 0xffffff);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot2() +"%", 64, 50, 0xffffff);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, this.container.getPercentSlot3() + "%", 86, 50, 0xffffff);
    }
/*
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawCenteredString(Minecraft.getInstance().fontRenderer, "Alloy Furnace", 92, 10, 0xffffff);
        drawCenteredString(Minecraft.getInstance().fontRenderer, this.container.getPercentSlot1() + "%", 42, 50, 0xffffff);
        drawCenteredString(Minecraft.getInstance().fontRenderer, this.container.getPercentSlot2() +"%", 64, 50, 0xffffff);
        drawCenteredString(Minecraft.getInstance().fontRenderer, this.container.getPercentSlot3() + "%", 86, 50, 0xffffff);
        /*this.addButton((new ImageButton(this.guiLeft + 53, this.guiTop + 6, 20, 18, 0, 0, 19, button, (p_214087_1_) -> {
            this.container.toggleRecipeLock();
        })));
        }*/




}

