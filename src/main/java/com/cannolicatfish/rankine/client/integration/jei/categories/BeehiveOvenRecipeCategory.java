package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BeehiveOvenRecipeCategory implements IRecipeCategory<BeehiveOvenRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "beeoven");
    private final IGuiHelper guiHelper;
    private final IDrawable background;
    private final String localizedName;
    private final LoadingCache<Integer, IDrawableAnimated> cachedFlames;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    public BeehiveOvenRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/beeoven_jei.png"), 0,0,116, 64)
                .build();
        localizedName = I18n.get("rankine.jei.beeoven");
        this.cachedFlames = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer burnTime) {
                        return guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/beeoven_jei.png"), 232, 0, 14, 14)
                                .buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, true);
                    }
                });
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/beeoven_jei.png"), 232, 14, 24, 17)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    protected IDrawableAnimated getArrow(BeehiveOvenRecipe recipe) {
        int cookTime = recipe.getMinCookTime();
        if (cookTime <= 0) {
            cookTime = 1600;
        }
        return this.cachedArrows.getUnchecked(cookTime);
    }

    protected void drawCookTime(BeehiveOvenRecipe recipe, PoseStack poseStack, int y) {
        int minCookTime = recipe.getMinCookTime();
        int maxCookTime = recipe.getMaxCookTime();
        if (minCookTime > 0 && maxCookTime > 0) {
            int minCookTimeSeconds = minCookTime / 20;
            int maxCookTimeSeconds = maxCookTime / 20;
            TextComponent timeString = new TextComponent(new TranslatableComponent("gui.jei.category.smelting.time.seconds", minCookTimeSeconds).getString() + " - " + new TranslatableComponent("gui.jei.category.smelting.time.seconds", maxCookTimeSeconds).getString());
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            fontRenderer.draw(poseStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
        }
    }

    @SuppressWarnings("removal")
    @Override
    public @NotNull ResourceLocation getUid() {
        return UID;
    }

    @SuppressWarnings("removal")
    @Override
    public @NotNull Class<? extends BeehiveOvenRecipe> getRecipeClass() {
        return BeehiveOvenRecipe.class;
    }

    @Override
    public @NotNull Component getTitle() {
        return new TextComponent(localizedName);
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.BEEHIVE_OVEN_PIT.get()));
    }

    @Override
    public void draw(BeehiveOvenRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        int burnTime = recipe.getMinCookTime();
        IDrawableAnimated flame = cachedFlames.getUnchecked(burnTime);
        flame.draw(stack, 20, 20);

        IDrawableAnimated arrow = getArrow(recipe);
        arrow.draw(stack, 55, 19);

        drawCookTime(recipe, stack, 55);

        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BeehiveOvenRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> ingredients = recipe.getIngredients();
        int posX = 20;

        for (Ingredient i : ingredients) {
            builder.addSlot(RecipeIngredientRole.INPUT,posX,1).addIngredients(i);
            posX += 18;
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT,92,20).addItemStack(recipe.getResultItem());
    }
}

