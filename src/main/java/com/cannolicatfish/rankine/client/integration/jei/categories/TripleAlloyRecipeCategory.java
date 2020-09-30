package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.recipe.ITripleAlloyRecipe;
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
import java.util.Collections;
import java.util.List;

public class TripleAlloyRecipeCategory implements IRecipeCategory<ITripleAlloyRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "triple_alloy");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public TripleAlloyRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(145, 120);
        localizedName = I18n.format("rankine.jei.triple_alloy");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/triple_alloy_jei.png"),
                0, 15, 140, 90);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.STAINLESS_STEEL_SHEETMETAL));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends ITripleAlloyRecipe> getRecipeClass() {
        return ITripleAlloyRecipe.class;
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
    public void draw(ITripleAlloyRecipe recipe, MatrixStack ms, double mouseX, double mouseY) {
        FontRenderer font = Minecraft.getInstance().fontRenderer;
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();
        String s = Math.round(recipe.getPrimary().getKey() * 100) + "-" + Math.round(recipe.getPrimary().getValue() * 100) + "%";
        font.drawString(ms, s, (float)(25  - font.getStringWidth(s) / 2), 36, 0x000000);
        String s2 = Math.round(recipe.getSecondary().getKey() * 100) + "-" + Math.round(recipe.getSecondary().getValue() * 100) + "%";
        font.drawString(ms, s2, (float)(75  - font.getStringWidth(s2) / 2), 36, 0x000000);
        String s3 = Math.round(recipe.getTertiary().getKey() * 100) + "-" + Math.round(recipe.getTertiary().getValue() * 100) + "%";
        font.drawString(ms, s3, (float)(125  - font.getStringWidth(s2) / 2), 36, 0x000000);
        if (recipe.getOther().getValue() != 0f)
        {
            String s4 = Math.round(recipe.getOther().getKey() * 100) + "-" + Math.round(recipe.getOther().getValue() * 100) + "%";
            font.drawString(ms, s4, (float)(75 - font.getStringWidth(s3) / 2), 66, 0x000000);
        }
    }

    @Override
    public void setIngredients(ITripleAlloyRecipe recipe, IIngredients iIngredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for (Ingredient i : recipe.getIngredients()) {
            builder.add(Arrays.asList(i.getMatchingStacks()));
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, builder.build());
        iIngredients.setOutputs(VanillaTypes.ITEM, Collections.singletonList(recipe.getRecipeOutput()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ITripleAlloyRecipe recipe, IIngredients ingredients) {
        int index = 0;/*
        int size = ingredients.getInputs(VanillaTypes.ITEM).size() - 3;
        int backup = ingredients.getInputs(VanillaTypes.ITEM).size() - 12;
        if (size <= 0)
        {
            size = 1;
        }
        if (size >= 9)
        {
            size = 8;
        }
        if (backup <= 0)
        {
            backup = 1;
        }*/
        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            if (index < 3)
            {
                recipeLayout.getItemStacks().init(index, true, 15 + index * 48, 47);
            } else {
                if (index < 11)
                {
                    recipeLayout.getItemStacks().init(index, true, (index - 3) * 18, 72);
                } else if (index < 19) {
                    recipeLayout.getItemStacks().init(index, true, (index - 11) * 18, 88);
                } else
                {
                    recipeLayout.getItemStacks().init(index, true, (index - 19) * 18, 104);
                }
            }
            if (o.get(0).getItem() == ModItems.CARBON_INGOT)
            {
                recipeLayout.getItemStacks().set(index, Arrays.asList(new ItemStack(ModItems.CARBON_INGOT),new ItemStack(ModItems.COKE),new ItemStack(ModItems.GRAPHITE)));
            } else
            {
                recipeLayout.getItemStacks().set(index, o);
            }
            index++;

        }

        for (int i = 0; i < ingredients.getOutputs(VanillaTypes.ITEM).size(); i++) {
            List<ItemStack> stacks = ingredients.getOutputs(VanillaTypes.ITEM).get(i);
            recipeLayout.getItemStacks().init(index + i, false, 63, 10);
            recipeLayout.getItemStacks().set(index + i, stacks);
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
