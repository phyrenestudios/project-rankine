package com.cannolicatfish.rankine.blocks.crucible;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrucibleScreen extends AbstractContainerScreen<CrucibleContainer> {
    private ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/gui/crucible.png");
    public CrucibleScreen(CrucibleContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.getHeatStatus() > 0)
        {
            guiGraphics.blit(GUI, this.leftPos + 78, this.topPos + 77, 176, 29, 18, 4);
        }

        int l = this.menu.getCookProgressScaled(27);
        guiGraphics.blit(GUI, this.leftPos + 108, this.topPos + 44, 176, 0, 10, l + 1);

    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, "Crucible", 88, 6, 0xffffff);
    }


}
