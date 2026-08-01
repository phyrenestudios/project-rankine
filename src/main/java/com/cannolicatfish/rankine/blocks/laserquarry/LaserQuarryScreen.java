package com.cannolicatfish.rankine.blocks.laserquarry;
// PORT-TODO(init): LASER_QUARRY BlockEntityType/MenuType not registered — init agent must add or keep this class dormant
// The class below has been modernized to 1.21.1 NeoForge (GuiGraphics) but stays commented out to match its dormant 1.18 state.
/*
import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;


public class LaserQuarryScreen extends AbstractContainerScreen<LaserQuarryContainer> {
    private ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/gui/laser_quarry.png");
    public LaserQuarryScreen(LaserQuarryContainer screenContainer, Inventory inv, Component titleIn) {
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

        int l = this.menu.getCookProgressScaled(24);
        guiGraphics.blit(GUI, this.leftPos + 81, this.topPos + 50, 176, 14, l + 1, 16);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, "Laser Quarry", 92, 10, 0xffffff);
    }
}
*/
