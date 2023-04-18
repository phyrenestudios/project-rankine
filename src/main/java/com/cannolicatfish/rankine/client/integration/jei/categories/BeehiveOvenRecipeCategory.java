package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.recipes.RankineJEIRecipeTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BeehiveOvenRecipeCategory implements IRecipeCategory<BeehiveOvenRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "beehive_oven");
    private final IGuiHelper guiHelper;
    private final IDrawable background;
    private final String localizedName;
    private final LoadingCache<Integer, IDrawableAnimated> cachedFlames;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
    private float multiplier;
    private final IDrawable firstTierBeehiveOven;
    private final IDrawable secondTierBeehiveOven;
    private final IDrawable thirdTierBeehiveOven;

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
        firstTierBeehiveOven = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/beeoven_jei.png"),201,31,55,17).build();
        secondTierBeehiveOven = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/beeoven_jei.png"),201,48,55,17).build();
        thirdTierBeehiveOven = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/beeoven_jei.png"),201,65,55,17).build();
        this.multiplier = 1f;
    }

    protected IDrawableAnimated getArrow(BeehiveOvenRecipe recipe) {
        int cookTime = Math.round(recipe.getMinCookTime() * multiplier);
        if (cookTime <= 0) {
            cookTime = 1600;
        }
        return this.cachedArrows.getUnchecked(cookTime);
    }

    protected void drawCookTime(BeehiveOvenRecipe recipe, PoseStack poseStack, int y) {
        int minCookTime = recipe.getMinCookTime();
        int maxCookTime = recipe.getMaxCookTime();
        if (minCookTime > 0 && maxCookTime > 0) {
            int minCookTimeSeconds = Math.round((minCookTime / 20f) * multiplier);
            int maxCookTimeSeconds = Math.round((maxCookTime / 20f) * multiplier);
            Component timeString = Component.literal(Component.translatable("gui.jei.category.smelting.time.seconds", minCookTimeSeconds).getString() + " - " + Component.translatable("gui.jei.category.smelting.time.seconds", maxCookTimeSeconds).getString());
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            fontRenderer.draw(poseStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
        }
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(BeehiveOvenRecipe recipe) {
        return UID;
    }

    @Override
    public RecipeType<BeehiveOvenRecipe> getRecipeType() {
        return RankineJEIRecipeTypes.BEEHIVE_OVEN_RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal(localizedName);
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
        int burnTime = Math.round(recipe.getMinCookTime() * multiplier);
        IDrawableAnimated flame = cachedFlames.getUnchecked(burnTime);
        flame.draw(stack, 20, 20);

        IDrawableAnimated arrow = getArrow(recipe);
        arrow.draw(stack, 55, 19);

        if (multiplier == 1f) {
            firstTierBeehiveOven.draw(stack, 0,36);
        } else if (multiplier == 0.5f) {
            secondTierBeehiveOven.draw(stack, 0,36);
        } else if (multiplier == 0.25f) {
            thirdTierBeehiveOven.draw(stack, 0,36);
        }

        drawCookTime(recipe, stack, 55);

        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public boolean handleInput(BeehiveOvenRecipe recipe, double mouseX, double mouseY, InputConstants.Key input) {
        if (mouseX > 88 && mouseX < 95 && input.getValue() == InputConstants.MOUSE_BUTTON_LEFT) {
            if (this.multiplier != 1f) {
                this.multiplier = 1f;
            }
        } else if (mouseX > 96 && mouseX < 103 && input.getValue() == InputConstants.MOUSE_BUTTON_LEFT) {
            if (this.multiplier != 0.5f) {
                this.multiplier = 0.5f;
            }
        } else      if (mouseX > 104 && mouseX < 111 && input.getValue() == InputConstants.MOUSE_BUTTON_LEFT) {
            if (this.multiplier != 0.25f) {
                this.multiplier = 0.25f;
            }
        }
        return IRecipeCategory.super.handleInput(recipe, mouseX, mouseY, input);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BeehiveOvenRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> ingredients = recipe.getIngredients();
        int posX = 20;

        for (Ingredient i : ingredients) {
            builder.addSlot(RecipeIngredientRole.INPUT,posX,1).addIngredients(i);
            posX += 18;
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT,92,20).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }
}

