package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.recipe.SluicingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.Util;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class SluicingRecipeCategory implements IRecipeCategory<SluicingRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "sluicing");
    private final IDrawable background;
    private final IGuiHelper guiHelper;
    private final IDrawable slotDrawable;

    public SluicingRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/sluicing_jei.png"), 0, 0, 170, 140)
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
    public Class<? extends SluicingRecipe> getRecipeClass() {
        return SluicingRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent(I18n.get("rankine.jei.sluicing"));
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RankineItems.STEEL_GOLD_PAN.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SluicingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.CATALYST,37,20).addIngredients(Ingredient.of(RankineTags.Items.GOLD_PANS));
        List<Ingredient> outputs = recipe.getOutputs();
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        builder.addSlot(RecipeIngredientRole.INPUT,78,20).addIngredients(recipe.getIngredients().get(0));

        int count = 1;
        int ycount = 1;
        for (int i = 0; i < outputs.size(); i++) {
            if (count % 9 == 0) {
                count = 1;
                ycount++;
            }
            int x = (count * 18);
            int y = 48 + (ycount * 18);

            int currentI = i;
            Ingredient currentOutput = outputs.get(i);
            if (currentOutput.isEmpty()) {
                currentOutput = Ingredient.of(new ItemStack(Items.BARRIER).setHoverName(new TextComponent(I18n.get("rankine.jei.tooltip_nothing"))));
            }
            builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addIngredients(currentOutput)
                    .setBackground(slotDrawable, -1, -1)
                    .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_chance") + df.format(recipe.getChance(currentI) * 100) + "%")));

            count++;
        }
    }
}
