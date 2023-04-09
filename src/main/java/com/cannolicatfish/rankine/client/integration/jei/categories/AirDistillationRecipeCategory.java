package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.AirDistillationRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AirDistillationRecipeCategory implements IRecipeCategory<AirDistillationRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "air_distillation");
    private final IDrawable background;
    private final String localizedName;
    private final IGuiHelper guiHelper;
    public AirDistillationRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/air_distillation_jei.png"), 0, 0, 116, 136)
                .addPadding(0, 0, 0, 0)
                .build();
        localizedName = I18n.get("rankine.jei.air_distillation");
    }
    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return UID;
    }
    @SuppressWarnings("removal")
    @Override
    public Class<? extends AirDistillationRecipe> getRecipeClass() {
        return AirDistillationRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent(localizedName);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineBlocks.DISTILLATION_TOWER.get()));
    }

    @Override
    public List<Component> getTooltipStrings(AirDistillationRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 95 && mouseX <= 110 && mouseY >= 116 && mouseY <= 131) {
            List<Component> components = new ArrayList<>();
            if (recipe.getDims().isEmpty()) {
                components.add(new TextComponent(I18n.get("rankine.jei.tooltip_dimension_info") + (I18n.get("rankine.jei.tooltip_any"))));
            } else {
                if (!recipe.getDims().isEmpty()) {
                    components.add(new TextComponent(I18n.get("rankine.jei.tooltip_dimension_info") + recipe.getDims().toString()));
                }
            }
            if (recipe.getBiomes().isEmpty()) {
                components.add(new TextComponent(I18n.get("rankine.jei.tooltip_biomes_info") + (I18n.get("rankine.jei.tooltip_any"))));
            } else {
                if (!recipe.getBiomes().isEmpty()) {
                    components.add(new TextComponent(I18n.get("rankine.jei.tooltip_biomes_info") + recipe.getBiomes().toString()));
                }
            }
            return components;
        }
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AirDistillationRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> outputs = recipe.getRecipeOutputsJEI();
        int lcount = 0;
        int rcount = 0;
        int count = 0;
        for (ItemStack stack : outputs) {
            if (stack.getItem() != RankineItems.ELEMENT.get() && !stack.isEmpty()) {
                if (count % 2 == 0) {
                    builder.addSlot(RecipeIngredientRole.OUTPUT,31,113 - 23*(lcount)).addItemStack(stack);
                } else {
                    builder.addSlot(RecipeIngredientRole.OUTPUT,64,106 - 23*(rcount)).addItemStack(stack);
                }
            }
            if (count % 2 == 0) {
                lcount++;
            } else {
                rcount++;
            }
            count++;
        }
    }

    @Override
    public void draw(AirDistillationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {

        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }
}
