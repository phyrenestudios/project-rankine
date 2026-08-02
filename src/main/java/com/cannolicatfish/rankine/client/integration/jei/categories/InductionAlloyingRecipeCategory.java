package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.JEIRankinePlugin;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class InductionAlloyingRecipeCategory extends AlloyingRecipeCategory {

    public static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "induction_alloying");
    private final IGuiHelper guiHelper;
    public InductionAlloyingRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.guiHelper = guiHelper;
    }

    @Override
    public RecipeType<AlloyingRecipe> getRecipeType() {
        return JEIRankinePlugin.INDUCTION_ALLOYING;
    }

    @Override
    public Component getTitle() {
        return Component.literal(I18n.get("rankine.jei.induction_alloying"));
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.INDUCTION_FURNACE.get()));
    }
}