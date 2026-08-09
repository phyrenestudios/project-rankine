package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.JEIRankinePlugin;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ElementRecipeCategory implements IRecipeCategory<ElementRecipe> {

    public static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "element");
    private final IDrawable background;
    private final String localizedName;

    private final IGuiHelper guiHelper;

    public ElementRecipeCategory(IGuiHelper guiHelper) {
        localizedName = I18n.get("rankine.jei.element");
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "textures/gui/element_jei.png"), 0, 0, 116, 112)
                .addPadding(0, 0, 0, 0)
                .build();
    }
    @Override
    public RecipeType<ElementRecipe> getRecipeType() {
        return JEIRankinePlugin.ELEMENT;
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
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.ELEMENT.get()));
    }

    @Override
    public void draw(ElementRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        String name = recipe.getName().toUpperCase(Locale.ROOT);
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });

        // dropShadow=false throughout: 1.18 drew all four with Font#draw, which has no shadow. The
        // two float-coordinate calls have no 5-arg overload, so they always passed the flag -- it was
        // just ported as `true`, which is what put a black ghost behind this black text.
        if (recipe.getAtomicNumber() >= 0) {
            guiGraphics.drawString(font,String.valueOf(recipe.getAtomicNumber()),48, 4,0x000000,false);
        }
        guiGraphics.drawString(font,String.valueOf(recipe.getSymbol()),48, 14,0x000000,false);
        guiGraphics.drawString(font,name,(float)(58 - font.width(name) / 2),40,0x000000,false);
        guiGraphics.drawString(font,"Items:",(float)(58 - font.width("Items:") / 2),66,0x000000,false);
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
                    .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(Component.literal(I18n.get("rankine.jei.tooltip_material_info") + df.format(val))));
        }
    }

}
