package com.cannolicatfish.rankine.blocks.templatetable;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.packets.SelectAlloyPacket;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collections;
import java.util.List;

public class TemplateTableScreen extends AbstractContainerScreen<TemplateTableContainer> {
    private final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/gui/template_table.png");
    private static final Component TRADES_LABEL = Component.translatable("rankine.alloys");
    private int selectedAlloyRecipe;
    private final AlloyButton[] tradeOfferButtons = new AlloyButton[7];
    private int scrollOff;
    private boolean isSelected;
    private static final int TEXTURE_WIDTH = 512;
    private static final int TEXTURE_HEIGHT = 256;
    private List<AlloyingRecipe> alloyRecipes = Collections.emptyList();

    public TemplateTableScreen(TemplateTableContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        this.imageWidth = 276;
        this.inventoryLabelX = 107;
    }



    private void setSelectedAlloyRecipe() {
        this.menu.setCurrentRecipeIndex(this.selectedAlloyRecipe);
        this.menu.tryMoveItems(this.selectedAlloyRecipe);
        // PORT-NOTE(network): original sent via RankinePacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), ...)
        // from client code, which was invalid; the payload is client -> server, so sendToServer is used.
        PacketDistributor.sendToServer(new SelectAlloyPacket(this.selectedAlloyRecipe));
    }

    protected void init() {
        super.init();
        this.alloyRecipes = this.menu.getAlloyRecipes();
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
    protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
        /*int i = this.container.getMerchantLevel();
        if (i > 0 && i <= 5 && this.container.showProgressBar()) {
            ITextComponent itextcomponent = this.title.deepCopy().appendSibling(LEVEL_SEPARATOR).appendSibling(new TranslationTextComponent("merchant.level." + i));
            int j = this.font.getStringPropertyWidth(itextcomponent);
            int k = 49 + this.xSize / 2 - j / 2;
            this.font.drawText(matrixStack, itextcomponent, (float)k, 6.0F, 4210752);
        } else {
            this.font.drawText(matrixStack, this.title, (float)(49 + this.xSize / 2 - this.font.getStringPropertyWidth(this.title) / 2), 6.0F, 4210752);
        }*/

        guiGraphics.drawString(this.font, "Template Table", this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
        int l = this.font.width(TRADES_LABEL);
        guiGraphics.drawString(this.font, TRADES_LABEL, 5 - l / 2 + 48, 6, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI, i, j, 0, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 512);
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



    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        if (!alloyRecipes.isEmpty()) {
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            int k = j + 16 + 1;
            int l = i + 5 + 5;
            //this.renderScroller(guiGraphics, i, j, alloyRecipes);
            int i1 = 0;

            for(AlloyingRecipe alloy : alloyRecipes) {
                if (this.checkSeven(alloyRecipes.size()) && (i1 < this.scrollOff || i1 >= 7 + this.scrollOff)) {
                    ++i1;
                } else {
                    Level worldIn = this.menu.getWorld();
                    ItemStack itemstack = new ItemStack(RankineItems.ELEMENT.get());
                    int j1 = k + 2;
                    int len = 0;
                    for (Ingredient ing : alloy.getIngredientsList(worldIn,true)) {
                        int index = (Math.round(worldIn.getGameTime() / 30f) % ing.getItems().length);
                        guiGraphics.renderFakeItem(ing.getItems()[index], l + len, j1);
                        guiGraphics.renderItemDecorations(this.font, ing.getItems()[index], l + len, j1);
                        len += 8;
                    }
                    ItemStack itemstack1 = new ItemStack(RankineItems.ELEMENT.get());
                    ItemStack itemstack2 = new ItemStack(RankineItems.BUILDING_TOOL.get());
                    ItemStack itemstack3 = alloy.getResultItem(worldIn.registryAccess());

                    //this.renderItemOverlay(guiGraphics, itemstack1, itemstack, l, j1);
                    if (!itemstack2.isEmpty()) {
                        guiGraphics.renderFakeItem(itemstack2, i + 5 + 35, j1);
                        guiGraphics.renderItemDecorations(this.font, itemstack2, i + 5 + 35, j1);
                    }

                    this.checkIfValid(guiGraphics, alloy, i, j1);
                    guiGraphics.renderFakeItem(itemstack3, i + 5 + 68, j1);
                    guiGraphics.renderItemDecorations(this.font, itemstack3, i + 5 + 68, j1);
                    k += 20;
                    ++i1;
                }
            }

            int k1 = this.selectedAlloyRecipe;
            AlloyingRecipe alloy1 = alloyRecipes.get(k1);
            /*if (this.container.showProgressBar()) {
                this.renderProgressBar(guiGraphics, i, j, alloy1);
            }*/

            /*if (alloy1.cannotMake(this.playerInventory,this.container.getWorld()) && this.isPointInRegion(215, 61, 22, 21, (double)mouseX, (double)mouseY)) {
                this.renderTooltip(guiGraphics, DEPRECATED_TOOLTIP, mouseX, mouseY);
            }*/

            for(AlloyButton alloyButton : this.tradeOfferButtons) {
                if (alloyButton != null) {
                    if (alloyButton.isActive()) {
                        alloyButton.renderToolTip(guiGraphics, mouseX, mouseY);
                    }
                    alloyButton.visible = alloyButton.index < this.alloyRecipes.size();
                }


            }

            RenderSystem.enableDepthTest();
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void renderItemOverlay(GuiGraphics guiGraphics, ItemStack p_238841_2_, ItemStack p_238841_3_, int p_238841_4_, int p_238841_5_) {
        guiGraphics.renderFakeItem(p_238841_2_, p_238841_4_, p_238841_5_);
        if (p_238841_3_.getCount() == p_238841_2_.getCount()) {
            guiGraphics.renderItemDecorations(this.font, p_238841_2_, p_238841_4_, p_238841_5_);
        } else {
            guiGraphics.renderItemDecorations(this.font, p_238841_3_, p_238841_4_, p_238841_5_, p_238841_3_.getCount() == 1 ? "1" : null);
            guiGraphics.renderItemDecorations(this.font, p_238841_2_, p_238841_4_ + 14, p_238841_5_, p_238841_2_.getCount() == 1 ? "1" : null);
            guiGraphics.blit(GUI, p_238841_4_ + 7, p_238841_5_ + 12, 300, 0.0F, 176.0F, 9, 2, 512, 256);
        }

    }

    private boolean checkSeven(int p_214135_1_) {
        return p_214135_1_ > 7;
    }


    private void checkIfValid(GuiGraphics guiGraphics, AlloyingRecipe p_238842_2_, int p_238842_3_, int p_238842_4_) {
        RenderSystem.enableBlend();
        /*if (p_238842_2_.cannotMake(this.inventory,this.menu.getWorld())) {
            guiGraphics.blit(GUI, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, 0, 25.0F, 171.0F, 10, 9, 512, 256);
        } else {
            guiGraphics.blit(GUI, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, 0, 15.0F, 171.0F, 10, 9, 512, 256);
        }*/
        guiGraphics.blit(GUI, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, 0, 15.0F, 171.0F, 10, 9, 512, 256);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int i = this.alloyRecipes.size();
        if (this.checkSeven(i)) {
            int j = i - 7;
            this.scrollOff = (int)((double)this.scrollOff - scrollY);
            this.scrollOff = Mth.clamp(this.scrollOff, 0, j);
        }

        return true;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.alloyRecipes.size();
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
        if (this.checkSeven(this.alloyRecipes.size()) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 18) && mouseY <= (double)(j + 18 + 139 + 1)) {
            this.isSelected = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @OnlyIn(Dist.CLIENT)
    class AlloyButton extends Button {
        final int index;

        public AlloyButton(int p_i50601_2_, int p_i50601_3_, int p_i50601_4_, Button.OnPress p_i50601_5_) {
            super(p_i50601_2_, p_i50601_3_, 89, 20, Component.empty(), p_i50601_5_, Button.DEFAULT_NARRATION);
            this.index = p_i50601_4_;
            this.visible = false;
        }

        public int getIndex() {
            return this.index;
        }

        public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
            if (this.isHovered() && TemplateTableScreen.this.alloyRecipes.size() > this.index + TemplateTableScreen.this.scrollOff) {
                /*if (mouseX < this.getX() + 20) {
                    ItemStack itemstack = TemplateTableScreen.this.container.getAlloyRecipes().get(this.index + TemplateTableScreen.this.scrollOff).getRecipeOutput();
                    TemplateTableScreen.this.renderTooltip(guiGraphics, itemstack, mouseX, mouseY);
                } else if (mouseX < this.getX() + 50 && mouseX > this.getX() + 30) {
                    ItemStack itemstack2 = TemplateTableScreen.this.container.getAlloyRecipes().get(this.index + TemplateTableScreen.this.scrollOff).getRecipeOutput();
                    if (!itemstack2.isEmpty()) {
                        TemplateTableScreen.this.renderTooltip(guiGraphics, itemstack2, mouseX, mouseY);
                    }
                } else*/ if (mouseX > this.getX() + 65) {
                    ItemStack itemstack1 = TemplateTableScreen.this.alloyRecipes.get(this.index + TemplateTableScreen.this.scrollOff).getResultItem(TemplateTableScreen.this.menu.getWorld().registryAccess());
                    guiGraphics.renderTooltip(TemplateTableScreen.this.font, itemstack1, mouseX, mouseY);
                } else {
                    Level worldIn = TemplateTableScreen.this.menu.getWorld();
                    AlloyingRecipe alloy = TemplateTableScreen.this.alloyRecipes.get(this.index + TemplateTableScreen.this.scrollOff);
                    int mouseXsub = mouseX - this.getX() - 10;
                    List<Ingredient> e = alloy.getIngredientsList(worldIn,true);
                    int currentIndex = Math.floorDiv(mouseXsub,8);
                    if (e.size() - 1 >= currentIndex && currentIndex >= 0) {
                        int index = (Math.round(worldIn.getGameTime() / 30f) %  e.get(currentIndex).getItems().length);
                        ItemStack itemstack = e.get(currentIndex).getItems()[index];
                        guiGraphics.renderTooltip(TemplateTableScreen.this.font, itemstack, mouseX, mouseY);
                    }


                }
            }

        }
    }

}
