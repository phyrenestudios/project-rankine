package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.recipes.RankineJEIRecipeTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.OldAlloyingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class InductionAlloyingRecipeCategory extends AlloyingRecipeCategory {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "induction_alloying");
    private final IGuiHelper guiHelper;
    public InductionAlloyingRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.guiHelper = guiHelper;
    }

    @Override
    public RecipeType<OldAlloyingRecipe> getRecipeType() {
        return RankineJEIRecipeTypes.ALLOYING_RECIPE_TYPE;
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(OldAlloyingRecipe recipe) {
        return UID;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.INDUCTION_FURNACE.get()));
    }
}