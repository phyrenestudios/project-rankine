package com.cannolicatfish.rankine.items.indexer;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;

public class ElementIndexerScreen extends ContainerScreen<ElementIndexerContainer> {
    private int currentScroll = 100;
    private PeriodicTableUtils.Element element = null;
    private static final PeriodicTableUtils utils = new PeriodicTableUtils();
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/element_indexer.png");
    public ElementIndexerScreen(ElementIndexerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.ySize = 236;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }


    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {

        drawString(matrixStack,Minecraft.getInstance().fontRenderer,this.currentScroll + "%",140,12,0xffffff);
        if (element != this.container.getSlotItem())
        {
            element = this.container.getSlotItem();
        }
        if (element != null)
        {
            drawCenteredString(matrixStack,Minecraft.getInstance().fontRenderer,element.toString(),88,12,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Durability: " + utils.calcDurability(Collections.singletonList(element),Collections.singletonList(this.currentScroll)),12,30,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Harvest Level: "+ utils.calcMiningLevel(Collections.singletonList(element),Collections.singletonList(this.currentScroll)),12,42,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Mining Speed: "+ utils.calcMiningSpeed(Collections.singletonList(element),Collections.singletonList(this.currentScroll)),12,54,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Damage: "+ utils.calcDamage(Collections.singletonList(element),Collections.singletonList(this.currentScroll)),12,66,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Attack Speed: "+ utils.calcAttackSpeed(Collections.singletonList(element),Collections.singletonList(this.currentScroll)),12,78,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Enchantability: "+ utils.calcEnchantability(Collections.singletonList(element),Collections.singletonList(this.currentScroll)),12,90,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Corrosion Resistance: "+ utils.calcCorrResist(Collections.singletonList(element),Collections.singletonList(this.currentScroll)),12,102,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Heat Resistance: "+ utils.calcHeatResist(Collections.singletonList(element),Collections.singletonList(this.currentScroll)),12,114,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Toughness: "+ utils.calcToughness(Collections.singletonList(element),Collections.singletonList(this.currentScroll)),12,126,0xffffff);
        } else
        {
            //drawCenteredString(matrixStack,Minecraft.getInstance().fontRenderer,"MATERIALNAME",88,12,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Durability:",12,30,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Harvest Level:",12,42,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Mining Speed:",12,54,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Damage:",12,66,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Attack Speed:",12,78,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Enchantability:",12,90,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Corrosion Resistance:",12,102,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Heat Resistance:",12,114,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Toughness:",12,126,0xffffff);
        }

    }


    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(this.GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(matrixStack,relX, relY, 0, 0, this.xSize, this.ySize);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        this.currentScroll = this.currentScroll + (int) (delta);
        this.currentScroll = MathHelper.clamp(this.currentScroll, 0, 100);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
