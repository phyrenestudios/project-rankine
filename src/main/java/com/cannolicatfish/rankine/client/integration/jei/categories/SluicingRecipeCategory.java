package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.SluicingRecipe;
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
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SluicingRecipeCategory implements IRecipeCategory<SluicingRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "sluicing");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SluicingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(145, 125);
        localizedName = I18n.get("rankine.jei.sluicing");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/sluicing_jei.png"),
                0, 15, 140, 120);
        icon = guiHelper.createDrawableIngredient(new ItemStack(RankineItems.STEEL_GOLD_PAN.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends SluicingRecipe> getRecipeClass() {
        return SluicingRecipe.class;
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
    public void draw(SluicingRecipe recipe, MatrixStack ms, double mouseX, double mouseY) {
        FontRenderer font = Minecraft.getInstance().font;
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();
    }

    @Override
    public void setIngredients(SluicingRecipe recipe, IIngredients iIngredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for (Ingredient i : recipe.getIngredients()) {
            builder.add(Arrays.asList(i.getItems()));
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, builder.build());
        ImmutableList.Builder<List<ItemStack>> builder2 = ImmutableList.builder();
        for (Ingredient i : recipe.getOutputs()) {
            builder2.add(Arrays.asList(i.getItems()));
        }
        iIngredients.setOutputLists(VanillaTypes.ITEM, builder2.build());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SluicingRecipe recipe, IIngredients ingredients) {
        //System.out.println(ingredients);
        //System.out.println(recipe.getOutputs());
        int index = 0, posY = 0;
        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            recipeLayout.getItemStacks().init(index, true, 63, 9 + posY);
            recipeLayout.getItemStacks().set(index, o);
            posY += 21;
            index++;
        }

        int reducer = 0;
        int ymod = -1;
        int outputcount = 0;
        for (List<ItemStack> o : ingredients.getOutputs(VanillaTypes.ITEM)) {
            if (outputcount % 6 == 0) {
                reducer = index - 3;
                ymod += 1;
            }
            recipeLayout.getItemStacks().init(index, true, (outputcount - reducer) * 18, 48 + ymod * 18);
            recipeLayout.getItemStacks().set(index, o);
            outputcount++;
            index++;
        }
        recipeLayout.getItemStacks().addTooltipCallback((i, b, stack, list) -> {
            DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
                p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
            });
            if (i != 0 && i != 1) {
                list.add(new StringTextComponent("Chance: " + df.format(recipe.getChance(i - 2) * 100) + "%"));
            }
        });

    }
}
