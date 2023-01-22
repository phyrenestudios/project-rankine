package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.TreetappingRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TreetappingRecipeCategory implements IRecipeCategory<TreetappingRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "treetapping");
    private final String localizedName;
    private final IDrawable background;
    private final IGuiHelper guiHelper;

    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    public TreetappingRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        localizedName = I18n.get("rankine.jei.treetapping");
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/treetapping_jei.png"), 0, 0, 78, 34)
                .addPadding(1, 0, 1, 0)
                .build();
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/treetapping_jei.png"), 232, 14, 24, 17)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }
    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return UID;
    }
    @SuppressWarnings("removal")
    @Override
    public Class<? extends TreetappingRecipe> getRecipeClass() {
        return TreetappingRecipe.class;
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
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.TREE_TAP.get()));
    }

    protected IDrawableAnimated getArrow(TreetappingRecipe recipe) {
        int cookTime = Math.round(recipe.getTapTime());
        if (cookTime <= 0) {
            cookTime = 1600;
        }
        return this.cachedArrows.getUnchecked(cookTime);
    }
    @Override
    public void draw(TreetappingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        if (recipe.getTapTime() > 0) {
            Font font = Minecraft.getInstance().font;
            font.draw(stack, Math.round(recipe.getTapTime()/20f)+"s", 59, 29, 0x7E7E7E);
        }
        IDrawableAnimated arrow = getArrow(recipe);
        arrow.draw(stack, 27, 10);
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public List<Component> getTooltipStrings(TreetappingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 24 && mouseX <= 37 && mouseY >= 20 && mouseY <= 33) {
            return List.of(new TextComponent("08:00 - 16:00"));
        }
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TreetappingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,2,10).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.CATALYST,23,0).addItemStack(RankineItems.TREE_TAP.get().getDefaultInstance());
        builder.addSlot(RecipeIngredientRole.OUTPUT,60,10).addIngredient(ForgeTypes.FLUID_STACK,recipe.getFilledResult()).addTooltipCallback(((recipeSlotView, tooltip) -> tooltip
                .add(new TextComponent(recipe.getResult().getAmount()+"mb"))));

        IRecipeCategory.super.setRecipe(builder, recipe, focuses);
    }
}

