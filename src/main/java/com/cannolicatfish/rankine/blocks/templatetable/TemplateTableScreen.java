package com.cannolicatfish.rankine.blocks.templatetable;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.packets.FluidStackPacket;
import com.cannolicatfish.rankine.init.packets.RankinePacketHandler;
import com.cannolicatfish.rankine.init.packets.SelectAlloyPacket;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.MerchantScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.play.client.CSelectTradePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

public class TemplateTableScreen extends ContainerScreen<TemplateTableContainer> {
    private final ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/template_table.png");
    private static final ITextComponent field_243351_B = new TranslationTextComponent("rankine.alloys");
    private int selectedAlloyRecipe;
    private final AlloyButton[] field_214138_m = new AlloyButton[7];
    private static final ITextComponent field_243353_D = new TranslationTextComponent("rankine.deprecated");
    private int field_214139_n;
    private boolean isSelected;

    public TemplateTableScreen(TemplateTableContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.xSize = 276;
        this.playerInventoryTitleX = 107;
    }

    private void setSelectedAlloyRecipe() {
        this.container.setCurrentRecipeIndex(this.selectedAlloyRecipe);
        this.container.func_217046_g(this.selectedAlloyRecipe);
        RankinePacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(),new SelectAlloyPacket(this.selectedAlloyRecipe));
    }

    protected void init() {
        super.init();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int k = j + 16 + 2;

        for(int l = 0; l < 7; ++l) {
            this.field_214138_m[l] = this.addButton(new AlloyButton(i + 5, k, l, (p_214132_1_) -> {
                if (p_214132_1_ instanceof AlloyButton) {
                    this.selectedAlloyRecipe = ((AlloyButton)p_214132_1_).func_212937_a() + this.field_214139_n;
                    this.setSelectedAlloyRecipe();
                }

            }));
            k += 20;
        }

    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        /*int i = this.container.getMerchantLevel();
        if (i > 0 && i <= 5 && this.container.func_217042_i()) {
            ITextComponent itextcomponent = this.title.deepCopy().appendSibling(field_243352_C).appendSibling(new TranslationTextComponent("merchant.level." + i));
            int j = this.font.getStringPropertyWidth(itextcomponent);
            int k = 49 + this.xSize / 2 - j / 2;
            this.font.drawText(matrixStack, itextcomponent, (float)k, 6.0F, 4210752);
        } else {
            this.font.drawText(matrixStack, this.title, (float)(49 + this.xSize / 2 - this.font.getStringPropertyWidth(this.title) / 2), 6.0F, 4210752);
        }*/

        this.font.drawText(matrixStack, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 4210752);
        int l = this.font.getStringPropertyWidth(field_243351_B);
        this.font.drawText(matrixStack, field_243351_B, (float)(5 - l / 2 + 48), 6.0F, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(matrixStack, i, j, this.getBlitOffset(), 0.0F, 0.0F, this.xSize, this.ySize, 256, 512);
        List<AlloyingRecipe> alloyRecipes = this.container.getAlloyRecipes();
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



    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        List<AlloyingRecipe> alloyingRecipes = this.container.getAlloyRecipes();
        if (!alloyingRecipes.isEmpty()) {
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            int k = j + 16 + 1;
            int l = i + 5 + 5;
            RenderSystem.pushMatrix();
            RenderSystem.enableRescaleNormal();
            this.minecraft.getTextureManager().bindTexture(GUI);
            //this.func_238840_a_(matrixStack, i, j, alloyingRecipes);
            int i1 = 0;

            for(AlloyingRecipe alloy : alloyingRecipes) {
                if (this.checkSeven(alloyingRecipes.size()) && (i1 < this.field_214139_n || i1 >= 7 + this.field_214139_n)) {
                    ++i1;
                } else {
                    World worldIn = this.container.getWorld();
                    ItemStack itemstack = new ItemStack(RankineItems.ELEMENT.get());
                    this.itemRenderer.zLevel = 100.0F;
                    int j1 = k + 2;
                    int len = 0;
                    for (Ingredient ing : alloy.getIngredientsList(worldIn,true)) {
                        int index = (Math.round(worldIn.getGameTime() / 30f) % ing.getMatchingStacks().length);
                        this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(ing.getMatchingStacks()[index], l + len, j1);
                        this.itemRenderer.renderItemOverlays(this.font, ing.getMatchingStacks()[index], l + len, j1);
                        len += 8;
                    }
                    ItemStack itemstack1 = new ItemStack(RankineItems.ELEMENT.get());
                    ItemStack itemstack2 = new ItemStack(RankineItems.BUILDING_TOOL.get());
                    ItemStack itemstack3 = alloy.getRecipeOutput();

                    //this.renderItemOverlay(matrixStack, itemstack1, itemstack, l, j1);
                    /*if (!itemstack2.isEmpty()) {
                        this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(itemstack2, i + 5 + 35, j1);
                        this.itemRenderer.renderItemOverlays(this.font, itemstack2, i + 5 + 35, j1);
                    }*/

                    this.checkIfValid(matrixStack, alloy, i, j1);
                    this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(itemstack3, i + 5 + 68, j1);
                    this.itemRenderer.renderItemOverlays(this.font, itemstack3, i + 5 + 68, j1);
                    this.itemRenderer.zLevel = 0.0F;
                    k += 20;
                    ++i1;
                }
            }

            int k1 = this.selectedAlloyRecipe;
            AlloyingRecipe alloy1 = alloyingRecipes.get(k1);
            /*if (this.container.func_217042_i()) {
                this.func_238839_a_(matrixStack, i, j, alloy1);
            }*/

            /*if (alloy1.cannotMake(this.playerInventory,this.container.getWorld()) && this.isPointInRegion(215, 61, 22, 21, (double)mouseX, (double)mouseY)) {
                this.renderTooltip(matrixStack, field_243353_D, mouseX, mouseY);
            }*/

            for(AlloyButton alloyButton : this.field_214138_m) {
                if (alloyButton.isHovered()) {
                    alloyButton.renderToolTip(matrixStack, mouseX, mouseY);
                }

                alloyButton.visible = alloyButton.field_212938_a < this.container.getAlloyRecipes().size();
            }

            RenderSystem.popMatrix();
            RenderSystem.enableDepthTest();
        }

        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    private void renderItemOverlay(MatrixStack p_238841_1_, ItemStack p_238841_2_, ItemStack p_238841_3_, int p_238841_4_, int p_238841_5_) {
        this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(p_238841_2_, p_238841_4_, p_238841_5_);
        if (p_238841_3_.getCount() == p_238841_2_.getCount()) {
            this.itemRenderer.renderItemOverlays(this.font, p_238841_2_, p_238841_4_, p_238841_5_);
        } else {
            this.itemRenderer.renderItemOverlayIntoGUI(this.font, p_238841_3_, p_238841_4_, p_238841_5_, p_238841_3_.getCount() == 1 ? "1" : null);
            this.itemRenderer.renderItemOverlayIntoGUI(this.font, p_238841_2_, p_238841_4_ + 14, p_238841_5_, p_238841_2_.getCount() == 1 ? "1" : null);
            this.minecraft.getTextureManager().bindTexture(GUI);
            this.setBlitOffset(this.getBlitOffset() + 300);
            blit(p_238841_1_, p_238841_4_ + 7, p_238841_5_ + 12, this.getBlitOffset(), 0.0F, 176.0F, 9, 2, 256, 512);
            this.setBlitOffset(this.getBlitOffset() - 300);
        }

    }

    private boolean checkSeven(int p_214135_1_) {
        return p_214135_1_ > 7;
    }

    private void checkIfValid(MatrixStack p_238842_1_, AlloyingRecipe p_238842_2_, int p_238842_3_, int p_238842_4_) {
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bindTexture(GUI);
        if (p_238842_2_.cannotMake(this.playerInventory,this.container.getWorld())) {
            blit(p_238842_1_, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, this.getBlitOffset(), 25.0F, 171.0F, 10, 9, 256, 512);
        } else {
            blit(p_238842_1_, p_238842_3_ + 5 + 35 + 20, p_238842_4_ + 3, this.getBlitOffset(), 15.0F, 171.0F, 10, 9, 256, 512);
        }

    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int i = this.container.getAlloyRecipes().size();
        if (this.checkSeven(i)) {
            int j = i - 7;
            this.field_214139_n = (int)((double)this.field_214139_n - delta);
            this.field_214139_n = MathHelper.clamp(this.field_214139_n, 0, j);
        }

        return true;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.container.getAlloyRecipes().size();
        if (this.isSelected) {
            int j = this.guiTop + 18;
            int k = j + 139;
            int l = i - 7;
            float f = ((float)mouseY - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
            f = f * (float)l + 0.5F;
            this.field_214139_n = MathHelper.clamp((int)f, 0, l);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.isSelected = false;
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (this.checkSeven(this.container.getAlloyRecipes().size()) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 18) && mouseY <= (double)(j + 18 + 139 + 1)) {
            this.isSelected = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @OnlyIn(Dist.CLIENT)
    class AlloyButton extends Button {
        final int field_212938_a;

        public AlloyButton(int p_i50601_2_, int p_i50601_3_, int p_i50601_4_, Button.IPressable p_i50601_5_) {
            super(p_i50601_2_, p_i50601_3_, 89, 20, StringTextComponent.EMPTY, p_i50601_5_);
            this.field_212938_a = p_i50601_4_;
            this.visible = false;
        }

        public int func_212937_a() {
            return this.field_212938_a;
        }

        public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
            if (this.isHovered && TemplateTableScreen.this.container.getAlloyRecipes().size() > this.field_212938_a + TemplateTableScreen.this.field_214139_n) {
                /*if (mouseX < this.x + 20) {
                    ItemStack itemstack = TemplateTableScreen.this.container.getAlloyRecipes().get(this.field_212938_a + TemplateTableScreen.this.field_214139_n).getRecipeOutput();
                    TemplateTableScreen.this.renderTooltip(matrixStack, itemstack, mouseX, mouseY);
                } else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
                    ItemStack itemstack2 = TemplateTableScreen.this.container.getAlloyRecipes().get(this.field_212938_a + TemplateTableScreen.this.field_214139_n).getRecipeOutput();
                    if (!itemstack2.isEmpty()) {
                        TemplateTableScreen.this.renderTooltip(matrixStack, itemstack2, mouseX, mouseY);
                    }
                } else*/ if (mouseX > this.x + 65) {
                    ItemStack itemstack1 = TemplateTableScreen.this.container.getAlloyRecipes().get(this.field_212938_a + TemplateTableScreen.this.field_214139_n).getRecipeOutput();
                    TemplateTableScreen.this.renderTooltip(matrixStack, itemstack1, mouseX, mouseY);
                } else {
                    World worldIn = TemplateTableScreen.this.container.getWorld();
                    AlloyingRecipe alloy = TemplateTableScreen.this.container.getAlloyRecipes().get(this.field_212938_a + TemplateTableScreen.this.field_214139_n);
                    int mouseXsub = mouseX - this.x - 10;
                    List<Ingredient> e = alloy.getIngredientsList(worldIn,true);
                    int currentIndex = Math.floorDiv(mouseXsub,8);
                    if (e.size() - 1 >= currentIndex && currentIndex >= 0) {
                        int index = (Math.round(worldIn.getGameTime() / 30f) %  e.get(currentIndex).getMatchingStacks().length);
                        ItemStack itemstack = e.get(currentIndex).getMatchingStacks()[index];
                        TemplateTableScreen.this.renderTooltip(matrixStack, itemstack, mouseX, mouseY);
                    }


                }
            }

        }
    }

}
