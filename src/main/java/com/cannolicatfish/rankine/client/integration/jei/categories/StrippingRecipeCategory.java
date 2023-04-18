package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.recipes.RankineJEIRecipeTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.recipe.StrippingRecipe;
import mezz.jei.api.constants.VanillaTypes;
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
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class StrippingRecipeCategory implements IRecipeCategory<StrippingRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "stripping");
    private final String localizedName;
    private final IDrawable background;
    private final IGuiHelper guiHelper;

    public StrippingRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        localizedName = I18n.get("rankine.jei.stripping");
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/stripping_jei.png"), 0, 0, 78, 28)
                .addPadding(1, 0, 1, 0)
                .build();
    }

    @Override
    public RecipeType<StrippingRecipe> getRecipeType() {
        return RankineJEIRecipeTypes.AXE_STRIPPING_RECIPE_TYPE;
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(StrippingRecipe recipe) {
        return UID;
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
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.INNER_BARK.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StrippingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,2,10).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.CATALYST,23,0).addIngredients(Ingredient.of(RankineTags.Items.AXES));
        builder.addSlot(RecipeIngredientRole.OUTPUT,60,10).addItemStack(recipe.getResult());
    }
}

