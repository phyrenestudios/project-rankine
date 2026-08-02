package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.JEIRankinePlugin;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

public class SedimentaryGeneratorRecipeCategory implements IRecipeCategory<RockGeneratorRecipe> {

    public static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "sedimentary");
    private final IDrawable background;
    private final String localizedName;
    private final IGuiHelper guiHelper;


    public SedimentaryGeneratorRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        localizedName = I18n.get("rankine.jei.sedimentary");
        background = guiHelper.drawableBuilder(ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/gui/sedimentary_jei.png"), 0, 0, 97, 49)
                .addPadding(1, 0, 1, 0)
                .build();
    }
    @Override
    public RecipeType<RockGeneratorRecipe> getRecipeType() {
        return JEIRankinePlugin.SEDIMENTARY;
    }

    @Override
    public Component getTitle() {
        return Component.literal(localizedName);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.BRECCIA.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RockGeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,2,2).addIngredients(recipe.getFirstIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT,24,28).addIngredient(NeoForgeTypes.FLUID_STACK, new FluidStack(Fluids.WATER,1000));
        builder.addSlot(RecipeIngredientRole.OUTPUT,75,27).addItemStack(recipe.getResultItem());
    }
}
