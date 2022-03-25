package com.cannolicatfish.rankine.blocks.templatetable;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.packets.FluidStackPacket;
import com.cannolicatfish.rankine.init.packets.RankinePacketHandler;
import com.cannolicatfish.rankine.init.packets.SelectAlloyPacket;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.List;

public class TemplateTableScreen extends AbstractContainerScreen<TemplateTableContainer> {
    private final ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/template_table.png");
    private static final Component TRADES_LABEL = new TranslatableComponent("rankine.alloys");
    private int selectedAlloyRecipe;
    private final AlloyButton[] tradeOfferButtons = new AlloyButton[7];
    private static final Component DEPRECATED_TOOLTIP = new TranslatableComponent("rankine.deprecated");
    private int scrollOff;
    private boolean isSelected;

    public TemplateTableScreen(TemplateTableContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        this.imageWidth = 276;
        this.inventoryLabelX = 107;
    }

    private void setSelectedAlloyRecipe() {
        this.menu.setCurrentRecipeIndex(this.selectedAlloyRecipe);
        this.menu.tryMoveItems(this.selectedAlloyRecipe);
        RankinePacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(),new SelectAlloyPacket(this.selectedAlloyRecipe));
    }

    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int k = j + 16 + 2;

        /*for(int l = 0; l < 7; ++l) {
            this.tradeOfferButtons[l] = this.addButton(new AlloyButton(i + 5, k, l, (p_214132_1_) -> {
                if (p_214132_1_ instanceof AlloyButton) {
                    /*this.selectedAlloyRecipe = ((AlloyButton)p_214132_1_).getIndex() + this.scrollOff;
                    this.setSelectedAlloyRecipe();
                }

            }));
            k += 20;
        }*/

    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        /*int i = this.container.getMerchantLevel();
        if (i > 0 && i <= 5 && this.container.showProgressBar()) {
            ITextComponent itextcomponent = this.title.deepCopy().appendSibling(LEVEL_SEPARATOR).appendSibling(new TranslationTextComponent("merchant.level." + i));
            int j = this.font.getStringPropertyWidth(itextcomponent);
            int k = 49 + this.xSize / 2 - j / 2;
            this.font.drawText(matrixStack, itextcomponent, (float)k, 6.0F, 4210752);
        } else {
            this.font.drawText(matrixStack, this.title, (float)(49 + this.xSize / 2 - this.font.getStringPropertyWidth(this.title) / 2), 6.0F, 4210752);
        }*/

        this.font.draw(matrixStack, "Template Table", (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
        int l = this.font.width(TRADES_LABEL);
        this.font.draw(matrixStack, TRADES_LABEL, (float)(5 - l / 2 + 48), 6.0F, 4210752);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindForSetup(GUI);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        blit(matrixStack, i, j, this.getBlitOffset(), 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 512);
        List<AlloyingRecipe> alloyRecipes = this.menu.getAlloyRecipes();
        if (!alloyRecipes.isEmpty()) {
            int k = this.selectedAlloyRecipe;
            if (k < 0 || k >= alloyRecipes.size()) {
                return;
            }

            AlloyingRecipe alloy = alloyRecipes.get(k);
            /*if (alloy.cannotMake(this.playerInventory,this.container.getWorld())) {
                this.minecraft.getTextureManager().bindTexture(GUI);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                blit(matrixStack, this.guiLeft + 215, this.guiTop + 61, this.getBlitOffset(), 311.0F, 0.0F, 28, 21, 256, 512);
            }*/
        }
    }



    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        List<AlloyingRecipe> alloyingRecipes = this.menu.getAlloyRecipes();
        if (!alloyingRecipes.isEmpty()) {
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            int k = j + 16 + 1;
            int l = i + 5 + 5;
            //RenderSystem.pushMatrix();
            //RenderSystem.enableRescaleNormal();
            this.minecraft.getTextureManager().bindForSetup(GUI);
            //this.renderScroller(matrixStack, i, j, alloyingRecipes);
            int i1 = 0;

            for(AlloyingRecipe alloy : alloyingRecipes) {
                if (this.checkSeven(alloyingRecipes.size()) && (i1 < this.scrollOff || i1 >= 7 + this.scrollOff)) {
                    ++i1;
                } else {
                    Level worldIn = this.menu.getWorld();
                    ItemStack itemstack = new ItemStack(RankineItems.ELEMENT.get());
                    this.itemRenderer.blitOffset = 100.0F;
                    int j1 = k + 2;
                    int len = 0;
                    for (Ingredient ing : alloy.getIngredientsList(worldIn,true)) {
                        int index = (Math.round(worldIn.getGameTime() / 30f) % ing.getItems().length);
                        this.itemRenderer.renderAndDecorateFakeItem(ing.getItems()[index], l + len, j1);
                        this.itemRenderer.renderGuiItemDecorations(this.font, ing.getItems()[index], l + len, j1);
                        len += 8;
                    }
                    ItemStack itemstack1 = new ItemStack(RankineItems.ELEMENT.get());
                    ItemStack itemstack2 = new ItemStack(RankineItems.BUILDING_TOOL.get());
                    ItemStack itemstack3 = alloy.getResultItem();

                    //this.renderItemOverlay(matrixStack, itemstack1, itemstack, l, j1);
                    /*if (!itemstack2.isEmpty()) {
                        this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(itemstack2, i + 5 + 35, j1);
                        this.itemRenderer.renderItemOverlays(this.font, itemstack2, i + 5 + 35, j1);
                    }*/

                    this.checkIfValid(matrixStack, alloy, i, j1);
                    this.itemRenderer.renderAndDecorateFakeItem(itemstack3, i + 5 + 68, j1);
                    this.itemRenderer.renderGuiItemDecorations(this.font, itemstack3, i + 5 + 68, j1);
                    this.itemRenderer.blitOffset = 0.0F;
                    k += 20;
                    ++i1;
                }
            }

            int k1 = this.selectedAlloyRecipe;
            AlloyingRecipe alloy1 = alloyingRecipes.get(k1);
            /*if (this.container.showProgressBar()) {
                this.renderProgressBar(matrixStack, i, j, alloy1);
            }*/

            /*if (alloy1.cannotMake(this.playerInventory,this.container.getWorld()) && this.isPointInRegion(215, 61, 22, 21, (double)mouseX, (double)mouseY)) {
                this.renderTooltip(matrixStack, DEPRECATED_TOOLTIP, mouseX, mouseY);
            }*/

            for(AlloyButton alloyButton : this.tradeOfferButtons) {
                if (alloyButton.isHovered()) {
                    alloyButton.renderToolTip(matrixStack, mouseX, mouseY);
                }

                alloyButton.visible = alloyButton.index < this.menu.getAlloyRecipes().size();
            }

            //RenderSystem.popMatrix();
            RenderSystem.enableDepthTest();
        }

        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    private void renderItemOverlay(PoseStack p_238841_1_, ItemStack p_238841_2_, ItemStack p_238841_3_, int p_238841_4_, int p_238841_5_) {
        this.itemRenderer.renderAndDecorateFakeItem(p_238841_2_, p_238841_4_, p_238841_5_);
        if (p_238841_3_.getCount() == p_238841_2_.getCount()) {
            this.itemRenderer.renderGuiItemDecorations(this.font, p_238841_2_, p_238841_4_, p_238841_5_);
        } else {
            this.itemRenderer.renderGuiItemDecorations(this.font, p_238841_3_, p_238841_4_, p_238841_5_, p_238841_3_.getCount() == 1 ? "1" : null);
            this.itemRenderer.renderGuiItemDecorations(this.font, p_238841_2_, p_238841_4_ + 14, p_238841_5_, p_238841_2_.getCount() == 1 ? "1" : null);
            this.minecraft.getTextureManager().bindForSetup(GUI);
            this.setBlitOffset(this.getBlitOffset() + 300);
            blit(p_238841_1_, p_238841_4_ + 7, p_238841_5_ + 12, this.getBlitOffset(), 0.0F, 176.0F, 9, 2, 256, 512);
            this.setBlitOffset(this.getBlitOffset() - 300);
        }

    }

    private boolean checkSeven(int p_214135_1_) {
        return p_214135_1_ > 7;
    }

    private void checkIfValid(PoseStack p_238842_1_, AlloyingRecipe p_238842_2_, int p_238842_3_, int p_238842_4_) {
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bindForSetup(GUI);
        /*if (p_238842_2_.cannotMake(this.inventory,this.menu.getWorld())) {
            blit(p_238842_1_, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, this.getBlitOffset(), 25.0F, 171.0F, 10, 9, 256, 512);
        } else {
            blit(p_238842_1_, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, this.getBlitOffset(), 15.0F, 171.0F, 10, 9, 256, 512);
        }*/
        blit(p_238842_1_, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, this.getBlitOffset(), 15.0F, 171.0F, 10, 9, 256, 512);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int i = this.menu.getAlloyRecipes().size();
        if (this.checkSeven(i)) {
            int j = i - 7;
            this.scrollOff = (int)((double)this.scrollOff - delta);
            this.scrollOff = Mth.clamp(this.scrollOff, 0, j);
        }

        return true;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.menu.getAlloyRecipes().size();
        if (this.isSelected) {
            int j = this.topPos + 18;
            int k = j + 139;
            int l = i - 7;
            float f = ((float)mouseY - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
            f = f * (float)l + 0.5F;
            this.scrollOff = Mth.clamp((int)f, 0, l);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.isSelected = false;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        if (this.checkSeven(this.menu.getAlloyRecipes().size()) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 18) && mouseY <= (double)(j + 18 + 139 + 1)) {
            this.isSelected = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @OnlyIn(Dist.CLIENT)
    class AlloyButton extends Button {
        final int index;

        public AlloyButton(int p_i50601_2_, int p_i50601_3_, int p_i50601_4_, Button.OnPress p_i50601_5_) {
            super(p_i50601_2_, p_i50601_3_, 89, 20, TextComponent.EMPTY, p_i50601_5_);
            this.index = p_i50601_4_;
            this.visible = false;
        }

        public int getIndex() {
            return this.index;
        }

        public void renderToolTip(PoseStack matrixStack, int mouseX, int mouseY) {
            if (this.isHovered && TemplateTableScreen.this.menu.getAlloyRecipes().size() > this.index + TemplateTableScreen.this.scrollOff) {
                /*if (mouseX < this.x + 20) {
                    ItemStack itemstack = TemplateTableScreen.this.container.getAlloyRecipes().get(this.index + TemplateTableScreen.this.scrollOff).getRecipeOutput();
                    TemplateTableScreen.this.renderTooltip(matrixStack, itemstack, mouseX, mouseY);
                } else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
                    ItemStack itemstack2 = TemplateTableScreen.this.container.getAlloyRecipes().get(this.index + TemplateTableScreen.this.scrollOff).getRecipeOutput();
                    if (!itemstack2.isEmpty()) {
                        TemplateTableScreen.this.renderTooltip(matrixStack, itemstack2, mouseX, mouseY);
                    }
                } else*/ if (mouseX > this.x + 65) {
                    ItemStack itemstack1 = TemplateTableScreen.this.menu.getAlloyRecipes().get(this.index + TemplateTableScreen.this.scrollOff).getResultItem();
                    TemplateTableScreen.this.renderTooltip(matrixStack, itemstack1, mouseX, mouseY);
                } else {
                    Level worldIn = TemplateTableScreen.this.menu.getWorld();
                    AlloyingRecipe alloy = TemplateTableScreen.this.menu.getAlloyRecipes().get(this.index + TemplateTableScreen.this.scrollOff);
                    int mouseXsub = mouseX - this.x - 10;
                    List<Ingredient> e = alloy.getIngredientsList(worldIn,true);
                    int currentIndex = Math.floorDiv(mouseXsub,8);
                    if (e.size() - 1 >= currentIndex && currentIndex >= 0) {
                        int index = (Math.round(worldIn.getGameTime() / 30f) %  e.get(currentIndex).getItems().length);
                        ItemStack itemstack = e.get(currentIndex).getItems()[index];
                        TemplateTableScreen.this.renderTooltip(matrixStack, itemstack, mouseX, mouseY);
                    }


                }
            }

        }
    }

}
