package com.cannolicatfish.rankine.blocks.alloyfurnace;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceContainer> {
    private ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/gui/alloy_furnace.png");
    public AlloyFurnaceScreen(AlloyFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.isBurning())
        {

            int k = this.menu.getBurnLeftScaled(13);
            guiGraphics.blit(GUI, this.leftPos + 11, this.topPos + 21 + 12 - k, 176, 12 - k, 14, k + 1);

        }

        int l = this.menu.getCookProgressScaled(24);
        guiGraphics.blit(GUI, this.leftPos + 98, this.topPos + 32, 176, 14, l + 1, 16);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, "Alloy Furnace", 92, 10, 0xffffff);
        int ymod = 0;
        for (String s : this.menu.getOutputString().getKey()) {
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, s, 92, 60 + ymod, this.menu.getOutputString().getValue());
            ymod += 10;
        }

    }



}
