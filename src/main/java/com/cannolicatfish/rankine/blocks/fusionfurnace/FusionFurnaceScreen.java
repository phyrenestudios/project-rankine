package com.cannolicatfish.rankine.blocks.fusionfurnace;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.gyratorycrusher.GyratoryCrusherContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.awt.*;
import java.util.Arrays;

public class FusionFurnaceScreen extends ContainerScreen<FusionFurnaceContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/fusion_furnace.png");
    public FusionFurnaceScreen(FusionFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
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
        else if (mouseX >= this.leftPos+156 && mouseX <= this.leftPos+171 && mouseY >= this.topPos+12 && mouseY <= this.topPos+75 && !this.menu.getOutputTank().isEmpty()) {
            FluidStack fluidStack = this.menu.getOutputTank().getFluid();
            this.renderWrappedToolTip(matrixStack, Arrays.asList(new TranslationTextComponent(fluidStack.getTranslationKey()),new StringTextComponent(fluidStack.getAmount()+"mb")), mouseX, mouseY, (this.font));
        }
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawString(p_230451_1_, Minecraft.getInstance().font, "Fusion Furnace", 75, 5, 0xffffff);
        if (!this.menu.getInputTank().isEmpty()) {
            drawFluidTank(p_230451_1_,this.menu.getInputTank(),4,12); //60
        }

        if (!this.menu.getOutputTank().isEmpty()) {
            drawFluidTank(p_230451_1_,this.menu.getOutputTank(),156,12);
        }

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

    @Override
    protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(p_230450_1_,relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.isBurning())
        {

            int k = this.menu.getBurnLeftScaled(13);
            this.blit(p_230450_1_,this.leftPos + 55, this.topPos + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.menu.getCookProgressScaled(24);

        this.blit(p_230450_1_,this.leftPos + 78, this.topPos + 35, 176, 14, l + 1, 16);
        
    }


}

