package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.EvaporationRecipe;
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
import net.minecraftforge.fluids.FluidStack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EvaporationRecipeCategory implements IRecipeCategory<EvaporationRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "evaporation");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public EvaporationRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(185, 146);
        localizedName = I18n.get("rankine.jei.evaporation");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/evaporation_jei.png"),
                0, 15, 180, 141);
        icon = guiHelper.createDrawableIngredient(new ItemStack(RankineBlocks.EVAPORATION_TOWER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends EvaporationRecipe> getRecipeClass() {
        return EvaporationRecipe.class;
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
    public void draw(EvaporationRecipe recipe, MatrixStack ms, double mouseX, double mouseY) {
        FontRenderer font = Minecraft.getInstance().font;
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();
        String s = "Made in:";
        String large = recipe.isLarge() ? "Evaporation Tower" : "Evaporation Boiler";


        int ymod = 0;
        if (!recipe.getBiomeString().isEmpty()) {
            StringBuilder str = new StringBuilder();
            str.append("Biomes: ");
            int count = 1;
            for (int i = 0; i < recipe.getBiomeString().size(); i++) {
                str.append(recipe.getBiomeString().get(i));
                count++;
                if (count == 3 || i == recipe.getBiomeString().size() - 1) {
                    font.draw(ms, str.toString(), (float)(ymod >= 50 ? 32 : 0), ymod, 0x000000);
                    count = 0;
                    ymod += 10;
                    str = new StringBuilder();
                } else if (i != recipe.getBiomeString().size() - 1) {
                    str.append(", ");
                }
            }

        }
        int r = ymod + 10 >= 50 ? 32 : 0;
        font.draw(ms, s, (float)(r), ymod, 0x000000);
        ymod += 10;
        font.draw(ms, large, (float)(r), ymod, recipe.isLarge() ? 0xaa0000 : 0x00aa00);


    }

    @Override
    public void setIngredients(EvaporationRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.FLUID,recipe.getFluid());
        iIngredients.setOutputs(VanillaTypes.ITEM, recipe.getOutputs());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, EvaporationRecipe recipe, IIngredients ingredients) {
        //System.out.println(ingredients);
        //System.out.println(recipe.getOutputs());
        int index = 0, posX = 23;
        for (List<FluidStack> o : ingredients.getInputs(VanillaTypes.FLUID)) {
            recipeLayout.getFluidStacks().init(index, true, 5, 55);
            recipeLayout.getFluidStacks().set(index, o);
            index++;
        }

        int reducer = 0;
        int ymod = -1;
        int outputcount = 0;
        for (List<ItemStack> o : ingredients.getOutputs(VanillaTypes.ITEM)) {
            if (outputcount % 10 == 0) {
                reducer = index - 1;
                ymod += 1;
            }
            recipeLayout.getItemStacks().init(index, true, (outputcount - reducer) * 18, 80 + ymod * 18);
            recipeLayout.getItemStacks().set(index, o);
            outputcount++;
            index++;
        }
        recipeLayout.getItemStacks().addTooltipCallback((i, b, stack, list) -> {
            DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
                p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
            });
            if (i != 0) {
                list.add(new StringTextComponent("Chance: " + df.format(recipe.getChance(i - 1) * 100) + "%"));
            }
        });

    }
}
