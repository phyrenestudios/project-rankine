package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class CrushingRecipeCategory implements IRecipeCategory<CrushingRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "crushing");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public CrushingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(145, 140);
        localizedName = I18n.get("rankine.jei.crushing");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/crushing_jei.png"),
                0, 15, 140, 160);
        icon = guiHelper.createDrawableIngredient(new ItemStack(RankineItems.STEEL_HAMMER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends CrushingRecipe> getRecipeClass() {
        return CrushingRecipe.class;
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
    public void draw(CrushingRecipe recipe, PoseStack ms, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        //RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(ms, 0, 4);
        RenderSystem.disableBlend();
        //RenderSystem.disableAlphaTest();
        for (int i = 0; i < 6; i++) {
            String s = Math.round(recipe.getChances().get(i) * 100) + "%";
            int ymod = i > 2 ? 36 : 0;
            int xmod = i > 2 ? 45*(i-3) : 45*i;
            font.draw(ms, s, (float)(27 + xmod - font.width(s) / 2), 80 + ymod, 0x000000);
            String t = "+" + Math.round(recipe.getAdditional().get(i) * 100) + "%";
            font.draw(ms, t, (float)(26 + xmod - font.width(t) / 2), 88 + ymod, 0x00aa00);
        }

    }

    @Override
    public void setIngredients(CrushingRecipe recipe, IIngredients iIngredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for (Ingredient i : recipe.getIngredients()) {
            builder.add(Arrays.asList(i.getItems()));
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, builder.build());
        iIngredients.setOutputs(VanillaTypes.ITEM, recipe.getRecipeOutputsJEI());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CrushingRecipe recipe, IIngredients ingredients) {
        int index = 0;
        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            recipeLayout.getItemStacks().init(index, true, 63, 10);
            recipeLayout.getItemStacks().set(index, o);
            index++;
        }

        for (int i = 0; i < ingredients.getOutputs(VanillaTypes.ITEM).size(); i++) {
            List<ItemStack> stacks = ingredients.getOutputs(VanillaTypes.ITEM).get(i);
            int ymod = i > 2 ? 36 : 0;
            int xmod = i > 2 ? 45*(i-3) : 45*i;
            recipeLayout.getItemStacks().init(index + i, false, 18 + xmod, 60 + ymod);
            if (!stacks.contains(ItemStack.EMPTY) && stacks.stream().noneMatch((s) -> s.getItem() == RankineItems.ELEMENT.get())) {
                recipeLayout.getItemStacks().set(index + i, stacks);
            }

        }
    }
}