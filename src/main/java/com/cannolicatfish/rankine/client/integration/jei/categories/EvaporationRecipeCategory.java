package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.recipe.IEvaporationRecipe;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.List;

public class EvaporationRecipeCategory implements IRecipeCategory<IEvaporationRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "evaporation");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public EvaporationRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(145, 125);
        localizedName = I18n.format("rankine.jei.evaporation");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/evaporation_jei.png"),
                0, 15, 140, 120);
        icon = guiHelper.createDrawableIngredient(new ItemStack(RankineBlocks.EVAPORATION_TOWER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends IEvaporationRecipe> getRecipeClass() {
        return IEvaporationRecipe.class;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(IEvaporationRecipe recipe, MatrixStack ms, double mouseX, double mouseY) {
        FontRenderer font = Minecraft.getInstance().fontRenderer;
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();
    }

    @Override
    public void setIngredients(IEvaporationRecipe recipe, IIngredients iIngredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for (Ingredient i : recipe.getIngredients()) {
            builder.add(Arrays.asList(i.getMatchingStacks()));
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, builder.build());
        iIngredients.setOutputs(VanillaTypes.ITEM, recipe.getOutputs());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IEvaporationRecipe recipe, IIngredients ingredients) {
        //System.out.println(ingredients);
        //System.out.println(recipe.getOutputs());
        int index = 0, posX = 23;
        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            recipeLayout.getItemStacks().init(index, true, 63, 10);
            recipeLayout.getItemStacks().set(index, o);
            index++;
        }

        List<Float> weights = recipe.getWeights();
        Float wtotal = weights.get(weights.size() - 1);
        for (int i = 0; i < ingredients.getOutputs(VanillaTypes.ITEM).size(); i++) {
            if (index + i < 7)
            {
                recipeLayout.getItemStacks().init(index + i, true, (index + i) * 18, 48);
            } else if (index + i < 13) {
                recipeLayout.getItemStacks().init(index + i, true, (index + i - 6) * 18, 64);
            } else if (index + i < 20)
            {
                recipeLayout.getItemStacks().init(index + i, true, (index + i - 12) * 18, 80);
            } else
            {
                recipeLayout.getItemStacks().init(index + i, true, (index + i - 19) * 18, 96);
            }
            recipeLayout.getItemStacks().set(index + i, ingredients.getOutputs(VanillaTypes.ITEM).get(i));
            //Float weight = weights.get(i) - (i == 0 ? 0f : weights.get(i - 1));
        }

        int endIndex = index;
        ResourceLocation recipeId = recipe.getId();
        recipeLayout.getItemStacks().addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (slotIndex >= endIndex) {
                if (Minecraft.getInstance().gameSettings.advancedItemTooltips || Screen.hasShiftDown()) {
                    tooltip.add(new TranslationTextComponent("jei.tooltip.recipe.id", recipeId).mergeStyle(TextFormatting.DARK_GRAY));
                }
            }
        });
    }
}
