package com.cannolicatfish.rankine.blocks.batterycharger;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BatteryChargerScreen extends AbstractContainerScreen<BatteryChargerContainer>{
    private ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/gui/battery_charger.png");
    public BatteryChargerScreen(BatteryChargerContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
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

        int l = this.menu.getSignalPowerScaled(30);
        if (l > 0) {
            guiGraphics.blit(GUI, this.leftPos + 10, this.topPos + 5, 179, 0, 16, 16);
        }
        guiGraphics.blit(GUI, this.leftPos + 10, this.topPos + 25, 179, 16, 16, 30-l);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, "Battery Holder", 92, 10, 0xffffff);
    }
}
