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
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
        if (mouseX >= this.guiLeft+4 && mouseX <= this.guiLeft+19 && mouseY >= this.guiTop+12 && mouseY <= this.guiTop+75 && !this.container.getInputTank().isEmpty()) {
            FluidStack fluidStack = this.container.getInputTank().getFluid();
            this.renderWrappedToolTip(matrixStack, Arrays.asList(new TranslationTextComponent(fluidStack.getTranslationKey()),new StringTextComponent(fluidStack.getAmount()+"mb")), mouseX, mouseY, (this.font));
        }
        else if (mouseX >= this.guiLeft+156 && mouseX <= this.guiLeft+171 && mouseY >= this.guiTop+12 && mouseY <= this.guiTop+75 && !this.container.getOutputTank().isEmpty()) {
            FluidStack fluidStack = this.container.getOutputTank().getFluid();
            this.renderWrappedToolTip(matrixStack, Arrays.asList(new TranslationTextComponent(fluidStack.getTranslationKey()),new StringTextComponent(fluidStack.getAmount()+"mb")), mouseX, mouseY, (this.font));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawString(p_230451_1_, Minecraft.getInstance().fontRenderer, "Fusion Furnace", 75, 5, 0xffffff);
        if (!this.container.getInputTank().isEmpty()) {
            drawFluidTank(p_230451_1_,this.container.getInputTank(),4,12); //60
        }

        if (!this.container.getOutputTank().isEmpty()) {
            drawFluidTank(p_230451_1_,this.container.getOutputTank(),156,12);
        }

    }


    protected void drawFluidTank(MatrixStack ms,FluidTank tankIn,int x,int y) {
        FluidStack input = tankIn.getFluid();
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(input.getFluid().getAttributes().getStillTexture());

        float amount = input.getAmount();
        float capacity = tankIn.getCapacity();
        float scale = amount / capacity;
        int fluidTankHeight = 64;
        int fluidAmount = (int) (scale * fluidTankHeight);


        //Color color = new Color(input.getFluid().getAttributes().getColor());
        this.minecraft.getTextureManager().bindTexture(new ResourceLocation(sprite.getName().getNamespace(),"textures/"+sprite.getName().getPath()+".png"));
        blit(ms,x, y,0,16, fluidAmount,sprite);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(p_230450_1_,relX, relY, 0, 0, this.xSize, this.ySize);
        if(this.container.isBurning())
        {

            int k = this.container.getBurnLeftScaled(13);
            this.blit(p_230450_1_,this.guiLeft + 55, this.guiTop + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.container.getCookProgressScaled(24);

        this.blit(p_230450_1_,this.guiLeft + 78, this.guiTop + 35, 176, 14, l + 1, 16);
        
    }


}

