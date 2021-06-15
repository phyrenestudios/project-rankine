package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.CrucibleRecipe;
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
import net.minecraft.util.text.StringTextComponent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CrucibleRecipeCategory implements IRecipeCategory<CrucibleRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "crucible");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public CrucibleRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(185, 146);
        localizedName = I18n.format("rankine.jei.crucible");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/crucible_jei.png"),
                0, 15, 180, 141);
        icon = guiHelper.createDrawableIngredient(new ItemStack(RankineBlocks.CRUCIBLE_BLOCK.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends CrucibleRecipe> getRecipeClass() {
        return CrucibleRecipe.class;
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
    public void draw(CrucibleRecipe recipe, MatrixStack ms, double mouseX, double mouseY) {
        FontRenderer font = Minecraft.getInstance().fontRenderer;
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();
    }

    @Override
    public void setIngredients(CrucibleRecipe recipe, IIngredients iIngredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for (Ingredient i : recipe.getCondensedIngredients()) {
            builder.add(Arrays.asList(i.getMatchingStacks()));
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, builder.build());
        iIngredients.setOutputs(VanillaTypes.ITEM, Arrays.asList(recipe.getPrimaryOutput(),recipe.getSecondaryOutput()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CrucibleRecipe recipe, IIngredients ingredients) {
        int index = 0;
        for (int i = 0; i < ingredients.getInputs(VanillaTypes.ITEM).size(); i++) {
            if (i < 4) {
                recipeLayout.getItemStacks().init(i, false, 14 + 45*i, 44);
            } else {
                int floor = Math.floorDiv(i - 4 ,10);
                recipeLayout.getItemStacks().init(i, true, ((i-4) - (10*floor)) * 18 + 2, 80 + (16*floor));
            }

            if (!ingredients.getInputs(VanillaTypes.ITEM).get(i).contains(ItemStack.EMPTY) && ingredients.getInputs(VanillaTypes.ITEM).get(i).stream().noneMatch((s) -> s.getItem() == RankineItems.ELEMENT.get())) {
                recipeLayout.getItemStacks().set(i, ingredients.getInputs(VanillaTypes.ITEM).get(i));
            }

            index = i;
        }
        /*
        int endIndex = ingredients.getInputs(VanillaTypes.ITEM).size();

        recipeLayout.getItemStacks().addTooltipCallback((i, b, stack, list) -> {
            if (i < endIndex) {
                list.add(new StringTextComponent("Min: " + Math.round(recipe.getMins().get(i) * 100) + "%"));
            }

        });
*/
        for (int i = 0; i < ingredients.getOutputs(VanillaTypes.ITEM).size(); i++) {
            List<ItemStack> stacks = ingredients.getOutputs(VanillaTypes.ITEM).get(i);
            recipeLayout.getItemStacks().init(index + i + 1, false, 69 + 25 * i, 10);
            recipeLayout.getItemStacks().set(index + i + 1, stacks);
        }
    }
}
