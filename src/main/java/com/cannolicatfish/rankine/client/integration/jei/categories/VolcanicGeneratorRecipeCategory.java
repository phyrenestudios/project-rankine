package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.JEIRankinePlugin;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

public class VolcanicGeneratorRecipeCategory implements IRecipeCategory<RockGeneratorRecipe> {

    public static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "volcanic");
    private final String localizedName;
    private final IDrawable background;
    private final IGuiHelper guiHelper;

    public VolcanicGeneratorRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        localizedName = I18n.get("rankine.jei.volcanic");
        background = guiHelper.drawableBuilder(ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/gui/volcanic_jei.png"), 0, 0, 102, 64)
                .addPadding(1, 0, 1, 0)
                .build();
    }
    @Override
    public RecipeType<RockGeneratorRecipe> getRecipeType() {
        return JEIRankinePlugin.VOLCANIC;
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
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(Items.OBSIDIAN));

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RockGeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,25,46).addIngredients(recipe.getFirstIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT,2,2).addIngredient(NeoForgeTypes.FLUID_STACK, new FluidStack(Fluids.WATER,1000));
        builder.addSlot(RecipeIngredientRole.INPUT,25,28).addIngredient(NeoForgeTypes.FLUID_STACK, new FluidStack(Fluids.LAVA,1000));
        builder.addSlot(RecipeIngredientRole.OUTPUT,80,27).addItemStack(recipe.getResultItem());
    }
}
