package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;

public class ExtrusiveGeneratorRecipeCategory implements IRecipeCategory<RockGeneratorRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "extrusive_igneous");
    private final IDrawable background;
    private final String localizedName;
    private final IGuiHelper guiHelper;

    public ExtrusiveGeneratorRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        localizedName = I18n.get("rankine.jei.extrusive_igneous");
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/extrusive_igneous_jei.png"), 0, 0, 119, 68)
                .addPadding(1, 0, 1, 0)
                .build();
    }
    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return UID;
    }
    @SuppressWarnings("removal")
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
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(Items.BASALT));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RockGeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,7,32).addItemStack(new ItemStack(Items.BLUE_ICE));
        builder.addSlot(RecipeIngredientRole.INPUT,43,32).addItemStack(new ItemStack(Items.SOUL_SOIL));
        builder.addSlot(RecipeIngredientRole.INPUT,25,50).addIngredients(recipe.getFirstIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT,2,2).addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(Fluids.LAVA,1000));
        builder.addSlot(RecipeIngredientRole.OUTPUT,97,31).addItemStack(recipe.getResultItem());
        IRecipeCategory.super.setRecipe(builder, recipe, focuses);
    }
}
