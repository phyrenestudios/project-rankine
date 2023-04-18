package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.recipes.BatteryRecipe;
import com.cannolicatfish.rankine.client.integration.jei.recipes.RankineJEIRecipeTypes;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.items.RTGItem;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BatteryRecipeCategory implements IRecipeCategory<BatteryRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "battery");
    private final IDrawable background;
    private final String localizedName;
    private final IGuiHelper guiHelper;

    private final IDrawableStatic chargeTransparentBackground;

    private final LoadingCache<Integer, IDrawableAnimated> cachedCharges;

    public BatteryRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        Minecraft minecraft = Minecraft.getInstance();
        int backgroundHeight = 54;
        localizedName = I18n.get("rankine.jei.battery");
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/battery_jei.png"), 0, 0, 150,backgroundHeight)
                .addPadding(0, 0, 0, 0)
                .build();

        chargeTransparentBackground = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/charge_icon.png"),0,0,14,14).setTextureSize(14,14).build();

        this.cachedCharges = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer burnTime) {
                        return guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/battery_jei.png"), 238, 0, 18, 18)
                                .buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, true);
                    }
                });
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(BatteryRecipe recipe) {
        return UID;
    }

    @Override
    public RecipeType<BatteryRecipe> getRecipeType() {
        return RankineJEIRecipeTypes.BATTERY_RECIPE_TYPE;
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
        return chargeTransparentBackground;
    }

    @Override
    public void draw(BatteryRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        int burnTime = recipe.getBatteryCharge();
        IDrawableAnimated flame = cachedCharges.getUnchecked(burnTime);
        flame.draw(poseStack, 1, 0);
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        boolean rtg = recipe.getInputs().stream().findFirst().isPresent() && recipe.getInputs().stream().findFirst().get().getItem() instanceof RTGItem;
        int count = 0;
        for (Component c : createChargeCountText(rtg, burnTime)) {
            font.draw(poseStack, c, 22, 17 + count, 0xFF808080);
            count += 10;
        }

    }

    @Override
    public List<Component> getTooltipStrings(BatteryRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX > 22 && mouseY >= 17 && mouseY <= 25) {
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable("rankine.jei.info_battery_fusion",Math.floorDiv(recipe.getBatteryCharge(), Config.MACHINES.FUSION_FURNACE_POWER.get())));
            components.add(Component.translatable("rankine.jei.info_battery_induction",Math.floorDiv(recipe.getBatteryCharge(), Config.MACHINES.INDUCTION_FURNACE_POWER.get())));
            return components;
        }
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BatteryRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,1,17).addItemStacks(recipe.getInputs());
    }

    private static List<Component> createChargeCountText(boolean rtg, int charge) {
        List<Component> components = new ArrayList<>();
        components.add(Component.translatable("rankine.jei.info_battery_energy",charge).withStyle(ChatFormatting.BLACK));
        if (rtg) {
            components.add(Component.translatable("rankine.jei.info_battery_rtg").withStyle(ChatFormatting.DARK_RED));
        } else {
            components.add(Component.translatable("rankine.jei.info_battery_rechargeable").withStyle(ChatFormatting.DARK_GREEN));
        }
        return components;
    }
}
