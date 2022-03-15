package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
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
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ElementRecipeCategory implements IRecipeCategory<ElementRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "element");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public ElementRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(145, 125);
        localizedName = I18n.get("rankine.jei.element");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/element_jei.png"),
                0, 15, 140, 120);
        icon = guiHelper.createDrawableIngredient(new ItemStack(RankineItems.ELEMENT_INDEXER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends ElementRecipe> getRecipeClass() {
        return ElementRecipe.class;
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
    public void draw(ElementRecipe recipe, MatrixStack ms, double mouseX, double mouseY) {
        FontRenderer font = Minecraft.getInstance().font;
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();

        String name = recipe.getName().toUpperCase(Locale.ROOT);
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        float p = recipe.getElectrodePotential();
        String e = "Electrode Potential: " + (p > 0 ? "+" : "") + df.format(recipe.getElectrodePotential())+"V";

        if (recipe.getAtomicNumber() >= 0) {
            font.draw(ms,String.valueOf(recipe.getAtomicNumber()),60, 10,0x000000);
        }
        font.draw(ms,String.valueOf(recipe.getSymbol()),60, 20,0x000000);
        font.draw(ms,name,(float)(70 - font.width(name) / 2),40,0x000000);
        font.draw(ms,e,(float)(70 - font.width(e) / 2),50,0x000000);
        font.draw(ms,"Items:",(float)(70 - font.width("Items:") / 2),70,0x000000);
    }


    @Override
    public void setIngredients(ElementRecipe recipe, IIngredients iIngredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for (Ingredient i : recipe.getIngredients()) {
            builder.add(Arrays.asList(i.getItems()));
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, builder.build());
        iIngredients.setOutputs(VanillaTypes.ITEM, Collections.singletonList(recipe.getResultItem()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ElementRecipe recipe, IIngredients ingredients) {
        int index = 0, posX = 23;

        for (int i = 0; i < ingredients.getInputs(VanillaTypes.ITEM).size(); i++) {
            int floor = Math.floorDiv(i,7);
            recipeLayout.getItemStacks().init(index + i, true, (index + i - (6*floor)) * 18 + 13, 80 + (16*floor));
            recipeLayout.getItemStacks().set(index + i, ingredients.getInputs(VanillaTypes.ITEM).get(i));
        }
        recipeLayout.getItemStacks().addTooltipCallback((i, b, stack, list) -> {
            DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
                p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
            });
            list.add(new StringTextComponent("Material count: " + df.format(recipe.getMaterialCount(stack.getItem()))));
        });
    }
}
