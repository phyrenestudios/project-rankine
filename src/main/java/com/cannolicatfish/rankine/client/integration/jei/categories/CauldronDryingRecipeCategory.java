package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.recipes.RankineCauldronDryingRecipe;
import com.cannolicatfish.rankine.client.integration.jei.recipes.RankineJEIRecipeTypes;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
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
import org.jetbrains.annotations.Nullable;

public class CauldronDryingRecipeCategory implements IRecipeCategory<RankineCauldronDryingRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "cauldron_drying");
    private final IDrawable background;
    private final String localizedName;
    private final IGuiHelper guiHelper;

    private final IDrawableStatic cauldronTransparentBackground;
    private final LoadingCache<Integer, IDrawableAnimated> cachedFlames;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    public CauldronDryingRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        localizedName = I18n.get("rankine.jei.cauldron_drying");
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/cauldron_drying_jei.png"), 0, 0, 138,62)
                .addPadding(0, 0, 0, 0)
                .build();

        cauldronTransparentBackground = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/cauldron_drying_icon.png"),0,0,14,14).setTextureSize(14,14).build();

        this.cachedFlames = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer burnTime) {
                        return guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/cauldron_drying_jei.png"), 232, 0, 14, 14)
                                .buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, true);
                    }
                });
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/cauldron_drying_jei.png"), 232, 14, 24, 17)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(RankineCauldronDryingRecipe recipe) {
        return UID;
    }

    @Override
    public RecipeType<RankineCauldronDryingRecipe> getRecipeType() {
        return RankineJEIRecipeTypes.CAULDRON_DRYING_RECIPE_TYPE;
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
        return cauldronTransparentBackground;
    }




    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RankineCauldronDryingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,40,5).addIngredient(ForgeTypes.FLUID_STACK, recipe.getInputFluid());
        builder.addSlot(RecipeIngredientRole.INPUT,44,48).addItemStack(recipe.getFluidHolderItem());
        builder.addSlot(RecipeIngredientRole.CATALYST,62,32).addItemStack(new ItemStack(Items.CAULDRON));
        builder.addSlot(RecipeIngredientRole.OUTPUT,6,30).addItemStack(recipe.getHolderOutputItem());
        if (recipe.getOutputItem() != null) {
            builder.addSlot(RecipeIngredientRole.OUTPUT,117,30).addItemStack(recipe.getOutputItem());
        }
        if (recipe.getOutputFluid() != null) {
            builder.addSlot(RecipeIngredientRole.OUTPUT,117,30).addIngredient(ForgeTypes.FLUID_STACK, recipe.getOutputFluid());
        }
    }

}
