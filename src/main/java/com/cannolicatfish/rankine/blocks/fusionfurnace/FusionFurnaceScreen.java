package com.cannolicatfish.rankine.blocks.fusionfurnace;

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

public class FusionFurnaceScreen extends AbstractContainerScreen<FusionFurnaceContainer> {
    private ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/gui/fusion_furnace.png");
    public FusionFurnaceScreen(FusionFurnaceContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        if (mouseX >= this.leftPos+4 && mouseX <= this.leftPos+19 && mouseY >= this.topPos+12 && mouseY <= this.topPos+75 && !this.menu.getInputTank().isEmpty()) {
            FluidStack fluidStack = this.menu.getInputTank().getFluid();
            guiGraphics.renderTooltip(this.font, Arrays.asList(Component.translatable(fluidStack.getTranslationKey()),Component.literal(fluidStack.getAmount()+"mb")), java.util.Optional.empty(), mouseX, mouseY);
        }
        else if (mouseX >= this.leftPos+156 && mouseX <= this.leftPos+171 && mouseY >= this.topPos+12 && mouseY <= this.topPos+75 && !this.menu.getOutputTank().isEmpty()) {
            FluidStack fluidStack = this.menu.getOutputTank().getFluid();
            guiGraphics.renderTooltip(this.font, Arrays.asList(Component.translatable(fluidStack.getTranslationKey()),Component.literal(fluidStack.getAmount()+"mb")), java.util.Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(Minecraft.getInstance().font, "Fusion Furnace", 75, 5, 0xffffff);
        if (!this.menu.getInputTank().isEmpty()) {
            drawFluidTank(guiGraphics,this.menu.getInputTank(),4,12); //60
        }

        if (!this.menu.getOutputTank().isEmpty()) {
            drawFluidTank(guiGraphics,this.menu.getOutputTank(),156,12);
        }

    }


    protected void drawFluidTank(GuiGraphics guiGraphics,FluidTank tankIn,int x,int y) {
        FluidStack input = tankIn.getFluid();
        IClientFluidTypeExtensions fluidExtensions = IClientFluidTypeExtensions.of(input.getFluid());
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidExtensions.getStillTexture());

        float amount = input.getAmount();
        float capacity = tankIn.getCapacity();
        float scale = amount / capacity;
        int fluidTankHeight = 64;
        int fluidAmount = (int) (scale * fluidTankHeight);


        Color color = new Color(fluidExtensions.getTintColor());
        guiGraphics.setColor(color.getRed(), color.getBlue(),
                color.getGreen(), color.getAlpha());

        guiGraphics.blit(x, y + (fluidTankHeight - fluidAmount),0,16, fluidAmount,sprite);
        guiGraphics.setColor(1f, 1f, 1f, 1f);

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.isBurning())
        {

            int k = this.menu.getBurnLeftScaled(13);
            guiGraphics.blit(GUI, this.leftPos + 55, this.topPos + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.menu.getCookProgressScaled(24);

        guiGraphics.blit(GUI, this.leftPos + 78, this.topPos + 35, 176, 14, l + 1, 16);

    }


}
