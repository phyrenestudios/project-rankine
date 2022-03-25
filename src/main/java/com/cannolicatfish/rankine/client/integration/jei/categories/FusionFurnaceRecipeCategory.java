package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.FusionFurnaceRecipe;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FusionFurnaceRecipeCategory implements IRecipeCategory<FusionFurnaceRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "fusion_furnace");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public FusionFurnaceRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(180, 90);
        localizedName = I18n.get("rankine.jei.fusion_furnace");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/fusion_furnace_jei.png"),
                0, 15, 175, 80);
        icon = guiHelper.createDrawableIngredient(new ItemStack(RankineItems.FUSION_FURNACE.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends FusionFurnaceRecipe> getRecipeClass() {
        return FusionFurnaceRecipe.class;
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
        return icon;
    }

    @Override
    public void draw(FusionFurnaceRecipe recipe, PoseStack ms, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        //RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        RenderSystem.disableBlend();
        //RenderSystem.disableAlphaTest();

    }

    @Override
    public void setIngredients(FusionFurnaceRecipe recipe, IIngredients iIngredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for (Ingredient i : recipe.getIngredients()) {
            builder.add(Arrays.asList(i.getItems()));
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, builder.build());
        iIngredients.setInput(VanillaTypes.FLUID,recipe.getFluidIn());
        iIngredients.setOutputs(VanillaTypes.ITEM, recipe.getRecipeOutputsJEI());
        iIngredients.setOutput(VanillaTypes.FLUID,recipe.getFluidOut());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FusionFurnaceRecipe recipe, IIngredients ingredients) {
        int index = 0;
        for (List<FluidStack> s : ingredients.getInputs(VanillaTypes.FLUID)) {
            if (s.size() > 0 && !s.get(0).isEmpty()) {
                recipeLayout.getFluidStacks().init(index,true,4,31);
                recipeLayout.getFluidStacks().set(index,s);
            }
            index++;
        }
        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            if (!o.contains(ItemStack.EMPTY) && o.stream().noneMatch((s) -> s.getItem() == RankineItems.ELEMENT.get())) {
                if (index == 1 || index == 2) {
                    recipeLayout.getItemStacks().init(index, true, 45 + 18*(index-1), 12);
                } else {
                    recipeLayout.getItemStacks().init(index, true, 21, 30);
                }

                recipeLayout.getItemStacks().set(index, o);
            }
            index++;
        }

        for (List<FluidStack> s : ingredients.getOutputs(VanillaTypes.FLUID)) {
            if (s.size() > 0 && !s.get(0).isEmpty()) {
                recipeLayout.getFluidStacks().init(index,true,156,31);
                recipeLayout.getFluidStacks().set(index,s);
            }

            index++;
        }

        for (int i = 0; i < ingredients.getOutputs(VanillaTypes.ITEM).size(); i++) {
            List<ItemStack> stacks = ingredients.getOutputs(VanillaTypes.ITEM).get(i);
            if (!stacks.contains(ItemStack.EMPTY) && stacks.stream().noneMatch((s) -> s.getItem() == RankineItems.ELEMENT.get())) {
                if (i == 0) {
                    recipeLayout.getItemStacks().init(index, false, 109, 19);
                } else if (i == 1) {
                    recipeLayout.getItemStacks().init(index, false, 109, 45);
                } else {
                    recipeLayout.getItemStacks().init(index, false, 137, 30);
                }


                recipeLayout.getItemStacks().set(index, stacks);
            }
            index++;
        }

        recipeLayout.getFluidStacks().addTooltipCallback((i, b, stack, list) -> {
            if (i == 0) {
                list.add(new TextComponent(recipe.getFluidIn().getAmount() + "mb"));
            } else {
                list.add(new TextComponent(recipe.getFluidOut().getAmount() + "mb"));
            }
        });
    }
}
