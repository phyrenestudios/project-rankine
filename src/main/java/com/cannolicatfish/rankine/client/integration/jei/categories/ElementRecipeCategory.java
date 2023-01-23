package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ElementRecipeCategory implements IRecipeCategory<ElementRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "element");
    private final IDrawable background;
    private final String localizedName;

    private final IGuiHelper guiHelper;

    public ElementRecipeCategory(IGuiHelper guiHelper) {
        localizedName = I18n.get("rankine.jei.element");
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/element_jei.png"), 0, 0, 116, 112)
                .addPadding(0, 0, 0, 0)
                .build();
    }
    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return UID;
    }
    @SuppressWarnings("removal")
    @Override
    public Class<? extends ElementRecipe> getRecipeClass() {
        return ElementRecipe.class;
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
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.ELEMENT.get()));
    }

    @Override
    public void draw(ElementRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack ms, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        String name = recipe.getName().toUpperCase(Locale.ROOT);
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });

        if (recipe.getAtomicNumber() >= 0) {
            font.draw(ms,String.valueOf(recipe.getAtomicNumber()),48, 4,0x000000);
        }
        font.draw(ms,String.valueOf(recipe.getSymbol()),48, 14,0x000000);
        font.draw(ms,name,(float)(58 - font.width(name) / 2),40,0x000000);
        font.draw(ms,"Items:",(float)(58 - font.width("Items:") / 2),66,0x000000);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ElementRecipe recipe, IFocusGroup focuses) {
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            int floor = Math.floorDiv(i,7);
            int val = recipe.getValues().get(i);
            builder.addSlot(RecipeIngredientRole.OUTPUT,(i - (6*floor)) * 18 + 4, 76 + (16*floor)).addIngredients(recipe.getIngredients().get(i))
                    .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_material_info") + df.format(val))));
        }
        IRecipeCategory.super.setRecipe(builder, recipe, focuses);
    }

}
