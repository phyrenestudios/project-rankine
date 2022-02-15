package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.AirDistillationRecipe;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.List;

public class AirDistillationRecipeCategory implements IRecipeCategory<AirDistillationRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "air_distillation");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public AirDistillationRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(185, 146);
        localizedName = I18n.format("rankine.jei.air_distillation");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/air_distillation_jei.png"),
                0, 15, 180, 141);
        icon = guiHelper.createDrawableIngredient(new ItemStack(RankineBlocks.DISTILLATION_TOWER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends AirDistillationRecipe> getRecipeClass() {
        return AirDistillationRecipe.class;
    }

    @Override
    public String getTitle() {
        return localizedName;
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
    public void draw(AirDistillationRecipe recipe, MatrixStack ms, double mouseX, double mouseY) {
        FontRenderer font = Minecraft.getInstance().fontRenderer;
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();

        int ymod = 0;
        if (!recipe.getBiomeString().isEmpty()) {
            StringBuilder str = new StringBuilder();
            str.append("Biomes: ");
            int count = 1;
            for (int i = 0; i < recipe.getBiomeString().size(); i++) {
                str.append(recipe.getBiomeString().get(i));
                count++;
                if (count == 3 || i == recipe.getBiomeString().size() - 1) {
                    font.drawString(ms, str.toString(), (float)(ymod >= 50 ? 32 : 0), ymod, 0x000000);
                    count = 0;
                    ymod += 10;
                    str = new StringBuilder();
                } else if (i != recipe.getBiomeString().size() - 1) {
                    str.append(", ");
                }
            }

        }

        ymod = 0;
        if (!recipe.getDims().isEmpty()) {
            StringBuilder str = new StringBuilder();
            str.append("Dims: ");
            int count = 1;
            for (int i = 0; i < recipe.getDims().size(); i++) {
                str.append(recipe.getDims().get(i));
                count++;
                if (count == 3 || i == recipe.getDims().size() - 1) {
                    font.drawString(ms, str.toString(), (float)(ymod >= 50 ? 32 : 0), ymod, 0x000000);
                    count = 0;
                    ymod += 10;
                    str = new StringBuilder();
                } else if (i != recipe.getDims().size() - 1) {
                    str.append(", ");
                }
            }

        }
    }

    @Override
    public void setIngredients(AirDistillationRecipe recipe, IIngredients iIngredients) {
        iIngredients.setOutputs(VanillaTypes.ITEM, recipe.getRecipeOutputsJEI());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AirDistillationRecipe recipe, IIngredients ingredients) {
        int index = 0;
        for (int i = 0; i < ingredients.getOutputs(VanillaTypes.ITEM).size(); i++) {
            List<ItemStack> stacks = ingredients.getOutputs(VanillaTypes.ITEM).get(i);
            int scale = Math.floorDiv(i,2);
            recipeLayout.getItemStacks().init(index, false, 85 - 44 * (i % 2), 122 - (8 * (i%2)) - (scale * 27));
            if (!stacks.contains(ItemStack.EMPTY) && stacks.stream().noneMatch((s) -> s.getItem() == RankineItems.ELEMENT.get())) {
                recipeLayout.getItemStacks().set(index, stacks);
            }
            index += 1;
        }

        int endIndex = index;
        recipeLayout.getItemStacks().addTooltipCallback((i, b, stack, list) -> {
            if (i <= endIndex) {
                list.add(new StringTextComponent("Chance: " + (recipe.getChances().get(i) * 100) + "%"));
            }

        });


    }
}
