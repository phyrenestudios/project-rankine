package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class MetamorphicGeneratorRecipeCategory implements IRecipeCategory<RockGeneratorRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "metamorphic");
    private final String localizedName;
    private final IDrawable background;
    private final IGuiHelper guiHelper;

    public MetamorphicGeneratorRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        localizedName = I18n.get("rankine.jei.metamorphic");
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/metamorphic_jei.png"), 0, 0, 102, 64)
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
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.SKARN.get()));

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RockGeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,25,46).addIngredients(recipe.getFirstIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT,2,2).addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(Fluids.LAVA,1000));
        builder.addSlot(RecipeIngredientRole.INPUT,25,28).addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(Fluids.WATER,1000));
        builder.addSlot(RecipeIngredientRole.OUTPUT,80,27).addItemStack(recipe.getResultItem());
        IRecipeCategory.super.setRecipe(builder, recipe, focuses);
    }
}
