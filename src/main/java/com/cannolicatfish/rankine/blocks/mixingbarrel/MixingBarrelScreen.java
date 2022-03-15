package com.cannolicatfish.rankine.blocks.mixingbarrel;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.awt.*;
import java.util.Arrays;

public class MixingBarrelScreen extends ContainerScreen<MixingBarrelContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/mixing_barrel.png");
    public MixingBarrelScreen(MixingBarrelContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
        if (mouseX >= this.leftPos+4 && mouseX <= this.leftPos+19 && mouseY >= this.topPos+12 && mouseY <= this.topPos+75 && !this.menu.getInputTank().isEmpty()) {
            FluidStack fluidStack = this.menu.getInputTank().getFluid();
            this.renderWrappedToolTip(matrixStack, Arrays.asList(new TranslationTextComponent(fluidStack.getTranslationKey()),new StringTextComponent(fluidStack.getAmount()+"mb")), mouseX, mouseY, (this.font));
        }
    }

    @Override
    protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
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
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
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

    protected void drawFluidTank(MatrixStack ms,FluidTank tankIn,int x,int y) {
        GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
        FluidStack input = tankIn.getFluid();
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(input.getFluid().getAttributes().getStillTexture());

        float amount = input.getAmount();
        float capacity = tankIn.getCapacity();
        float scale = amount / capacity;
        int fluidTankHeight = 64;
        int fluidAmount = (int) (scale * fluidTankHeight);


        Color color = new Color(input.getFluid().getAttributes().getColor());
        //this.minecraft.getTextureManager().bindTexture(new ResourceLocation(sprite.getName().getNamespace(),"textures/"+sprite.getName().getPath()+".png"));
        this.minecraft.getTextureManager().bind(sprite.atlas().location());
        GlStateManager._color4f(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
        if (tankIn.getFluid().getFluid().equals(Fluids.WATER)) {
            GlStateManager._color4f(63,118,228,color.getAlpha());
        }
        blit(ms,x, y + (fluidTankHeight - fluidAmount),0,16, fluidAmount,sprite);

    }

}
