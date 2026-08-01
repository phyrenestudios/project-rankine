package com.cannolicatfish.rankine.blocks.mixingbarrel;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.awt.*;
import java.util.Arrays;

public class MixingBarrelScreen extends AbstractContainerScreen<MixingBarrelContainer> {
    private ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/gui/mixing_barrel.png");
    public MixingBarrelScreen(MixingBarrelContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        if (mouseX >= this.leftPos+4 && mouseX <= this.leftPos+19 && mouseY >= this.topPos+12 && mouseY <= this.topPos+75 && !this.menu.getInputTank().isEmpty()) {
            FluidStack fluidStack = this.menu.getInputTank().getFluid();
            guiGraphics.renderTooltip(this.font, Arrays.asList(Component.translatable(fluidStack.getDescriptionId()),Component.literal(fluidStack.getAmount()+"mb")), java.util.Optional.empty(), mouseX, mouseY);
        }
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
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, "Mixing Barrel", 88, 6, 0xffffff);
        if (!this.menu.getInputTank().isEmpty()) {
            drawFluidTank(guiGraphics,this.menu.getInputTank(),4,12); //60
        }
        String[] percents = menu.getSlotPercentages();
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, percents[0], 48, 34, 0xffffff);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, percents[1], 74, 34, 0xffffff);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, percents[2], 100, 34, 0xffffff);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, percents[3], 126, 34, 0xffffff);
    }

    protected void drawFluidTank(GuiGraphics guiGraphics, FluidTank tankIn, int x, int y) {
        FluidStack input = tankIn.getFluid();
        IClientFluidTypeExtensions fluidExtensions = IClientFluidTypeExtensions.of(input.getFluid());
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidExtensions.getStillTexture(input));

        float amount = input.getAmount();
        float capacity = tankIn.getCapacity();
        float scale = amount / capacity;
        int fluidTankHeight = 64;
        int fluidAmount = (int) (scale * fluidTankHeight);


        Color color = new Color(fluidExtensions.getTintColor(input), true);
        guiGraphics.blit(x, y + (fluidTankHeight - fluidAmount), 0, 16, fluidAmount, sprite,
                color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);

    }

}
