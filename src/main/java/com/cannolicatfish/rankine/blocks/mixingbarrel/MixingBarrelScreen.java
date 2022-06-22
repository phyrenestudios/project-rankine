package com.cannolicatfish.rankine.blocks.mixingbarrel;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.awt.*;
import java.util.Arrays;

public class MixingBarrelScreen extends AbstractContainerScreen<MixingBarrelContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/mixing_barrel.png");
    public MixingBarrelScreen(MixingBarrelContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
        if (mouseX >= this.leftPos+4 && mouseX <= this.leftPos+19 && mouseY >= this.topPos+12 && mouseY <= this.topPos+75 && !this.menu.getInputTank().isEmpty()) {
            FluidStack fluidStack = this.menu.getInputTank().getFluid();
            this.renderTooltip(matrixStack, Arrays.asList(new TranslatableComponent(fluidStack.getTranslationKey()),new TextComponent(fluidStack.getAmount()+"mb")), java.util.Optional.empty(), mouseX, mouseY, (this.font));
        }
    }

    @Override
    protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(p_230450_1_,relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.getHeatStatus() > 0)
        {
            this.blit(p_230450_1_,this.leftPos + 78, this.topPos + 77, 176, 29, 18, 4);
        }

        int l = this.menu.getCookProgressScaled(27);
        this.blit(p_230450_1_,this.leftPos + 108, this.topPos + 44, 176, 0, 10, l + 1);

    }

    @Override
    protected void renderLabels(PoseStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawCenteredString(p_230451_1_, Minecraft.getInstance().font, "Mixing Barrel", 88, 6, 0xffffff);
        if (!this.menu.getInputTank().isEmpty()) {
            drawFluidTank(p_230451_1_,this.menu.getInputTank(),4,12); //60
        }
        String[] percents = menu.getSlotPercentages();
        drawCenteredString(p_230451_1_, Minecraft.getInstance().font, percents[0], 48, 34, 0xffffff);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().font, percents[1], 74, 34, 0xffffff);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().font, percents[2], 100, 34, 0xffffff);
        drawCenteredString(p_230451_1_, Minecraft.getInstance().font, percents[3], 126, 34, 0xffffff);
    }

    protected void drawFluidTank(PoseStack ms,FluidTank tankIn,int x,int y) {
        FluidStack input = tankIn.getFluid();
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(input.getFluid().getAttributes().getStillTexture());

        float amount = input.getAmount();
        float capacity = tankIn.getCapacity();
        float scale = amount / capacity;
        int fluidTankHeight = 64;
        int fluidAmount = (int) (scale * fluidTankHeight);


        Color color = new Color(input.getFluid().getAttributes().getColor());
        RenderSystem.setShaderTexture(0, new ResourceLocation(sprite.getName().getNamespace(),"textures/"+sprite.getName().getPath()+".png"));
        RenderSystem.setShaderColor(color.getRed(), color.getBlue(),
                color.getGreen(), color.getAlpha());

        blit(ms,x, y + (fluidTankHeight - fluidAmount),0,16, fluidAmount,sprite);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

    }

}
