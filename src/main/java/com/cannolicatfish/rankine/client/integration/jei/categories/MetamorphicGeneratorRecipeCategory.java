package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MetamorphicGeneratorRecipeCategory implements IRecipeCategory<RockGeneratorRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "metamorphic");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public MetamorphicGeneratorRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(145, 95);
        localizedName = I18n.get("rankine.jei.metamorphic");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/metamorphic_jei.png"),
                0, 15, 140, 90);
        icon = guiHelper.createDrawableIngredient(new ItemStack(RankineBlocks.SKARN.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends RockGeneratorRecipe> getRecipeClass() {
        return RockGeneratorRecipe.class;
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
        return icon;
    }

    @Override
    public void draw(RockGeneratorRecipe recipe, PoseStack ms, double mouseX, double mouseY) {
        //RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        RenderSystem.disableBlend();
        //RenderSystem.disableAlphaTest();
    }

    @Override
    public void setIngredients(RockGeneratorRecipe recipe, IIngredients iIngredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for (Ingredient i : recipe.getIngredients()) {
            builder.add(Arrays.asList(i.getItems()));
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, builder.build());
        iIngredients.setOutputs(VanillaTypes.ITEM, Collections.singletonList(recipe.getResultItem()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RockGeneratorRecipe recipe, IIngredients ingredients) {
        int index = 0, posY = 0;
        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            if (index == 0) {
                recipeLayout.getItemStacks().init(index, true, 38, 40);
            } else {
                recipeLayout.getItemStacks().init(index, true, 38, 58);
            }
            recipeLayout.getItemStacks().set(index, o);
            index++;
        }

        for (int i = 0; i < ingredients.getOutputs(VanillaTypes.ITEM).size(); i++) {
            List<ItemStack> stacks = ingredients.getOutputs(VanillaTypes.ITEM).get(i);
            recipeLayout.getItemStacks().init(index + i, false, 95, 40);
            recipeLayout.getItemStacks().set(index + i, stacks);
        }
    }
}
