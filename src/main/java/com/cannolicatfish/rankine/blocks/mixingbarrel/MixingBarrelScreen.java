package com.cannolicatfish.rankine.blocks.mixingbarrel;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

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
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
        if (mouseX >= this.guiLeft+4 && mouseX <= this.guiLeft+19 && mouseY >= this.guiTop+12 && mouseY <= this.guiTop+75 && !this.container.getInputTank().isEmpty()) {
            FluidStack fluidStack = this.container.getInputTank().getFluid();
            this.renderWrappedToolTip(matrixStack, Arrays.asList(new TranslationTextComponent(fluidStack.getTranslationKey()),new StringTextComponent(fluidStack.getAmount()+"mb")), mouseX, mouseY, (this.font));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(p_230450_1_,relX, relY, 0, 0, this.xSize, this.ySize);
        if(this.container.getHeatStatus() > 0)
        {
            this.blit(p_230450_1_,this.guiLeft + 78, this.guiTop + 77, 176, 29, 18, 4);
        }

        int l = this.container.getCookProgressScaled(27);
        this.blit(p_230450_1_,this.guiLeft + 108, this.guiTop + 44, 176, 0, 10, l + 1);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, "Mixing Barrel", 88, 6, 0xffffff);
        if (!this.container.getInputTank().isEmpty()) {
            drawFluidTank(p_230451_1_,this.container.getInputTank(),4,12); //60
        }
    }

    protected void drawFluidTank(MatrixStack ms, FluidTank tankIn, int x, int y) {
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

}
