package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class InductionAlloyingRecipeCategory implements IRecipeCategory<AlloyingRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "induction_alloying");
    private final IDrawable background;
    private final IDrawable slotDrawable;
    private final IGuiHelper guiHelper;
    public InductionAlloyingRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/alloy_jei.png"), 0, 0, 180, 120)
                .addPadding(1, 0, 0, 15)
                .build();
        slotDrawable = guiHelper.getSlotDrawable();
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends AlloyingRecipe> getRecipeClass() {
        return AlloyingRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent(I18n.get("rankine.jei.induction_alloying"));
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.INDUCTION_FURNACE.get()));
    }

    @Override
    public void draw(AlloyingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyingRecipe recipe, IFocusGroup focuses) {
        Level level = Minecraft.getInstance().level;
        List<Ingredient> ingredients = recipe.getIngredientsList(level,true);
        List<Ingredient> groupedOptionals = recipe.getIngredientsGroupedByMinMaxList(level);
        ItemStack output = recipe.getResultItem();
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });

        int rcount = 0;

        builder.addSlot(RecipeIngredientRole.OUTPUT,84,6).addItemStack(output);

        for (Ingredient i : ingredients) {
            int x = (16 + (rcount) * 32);
            int y = 40;
            Tuple<Float,Float> minMax = recipe.getMinMaxByElement(level,i.getItems()[0]);
            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(i)
                    .setBackground(slotDrawable, -1, -1)
                    .addTooltipCallback(((recipeSlotView, tooltip) -> tooltip
                            .add(new TextComponent(Math.round(minMax.getA() * 100) + "%")
                                    .append(new TextComponent("-" + Math.round(minMax.getB() * 100) + "%"))
                                    .withStyle(ChatFormatting.GOLD))));
            rcount++;
        }

        int nrcount = 0;

        for (Ingredient i : groupedOptionals) {
            if (!i.equals(Ingredient.EMPTY)) {
                int x = nrcount <= 9 ? ((nrcount)*18) : ((nrcount-9)*18);
                int y = nrcount <= 9 ? 86 : 104;
                Tuple<Float,Float> minMax = recipe.getMinMaxByElement(level,i.getItems()[0]);
                builder.addSlot(RecipeIngredientRole.INPUT,x,y).addIngredients(i)
                        .addTooltipCallback(((recipeSlotView, tooltip) -> tooltip
                                .add(new TextComponent( Math.round(minMax.getA() * 100) + "%")
                                        .append(new TextComponent("-" + Math.round(minMax.getB() * 100) + "%"))
                                        .withStyle(ChatFormatting.GOLD))));
                nrcount++;
            }
        }
    }
}