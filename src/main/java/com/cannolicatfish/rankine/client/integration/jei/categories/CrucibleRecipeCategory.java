package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.CrucibleRecipe;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class CrucibleRecipeCategory implements IRecipeCategory<CrucibleRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "crucible");
    private final IDrawable background;
    private final IDrawable slotDrawable;
    private final IGuiHelper guiHelper;

    public CrucibleRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/crucible_jei.png"), 0, 0, 180, 140)
                .addPadding(1, 0, 0, 0)
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
    public Class<? extends CrucibleRecipe> getRecipeClass() {
        return CrucibleRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent(I18n.get("rankine.jei.crucible"));
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.CRUCIBLE.get()));
    }

    @Override
    public void draw(CrucibleRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        List<Boolean> reqs = recipe.getRequired();
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        Font font = Minecraft.getInstance().font;
        font.draw(stack,new TranslatableComponent("rankine.jei.tooltip_required"),120, 0, 0x000000);
        font.draw(stack,new TranslatableComponent("rankine.jei.crucible_tooltip_additional",recipe.getRequired().stream().filter(aBoolean -> aBoolean).count()),1, 96, 0x000000);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrucibleRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> ingredients = recipe.getCondensedIngredients();
        List<Boolean> reqs = recipe.getRequired();
        ItemStack output = recipe.getResultItem();
        int rcount = 0;
        int nrcount = 0;
        builder.addSlot(RecipeIngredientRole.OUTPUT,5,31).addItemStack(output);
        builder.addSlot(RecipeIngredientRole.OUTPUT,5,31).addItemStack(recipe.getSecondaryOutput());
        for (int i = 0; i < ingredients.size(); i++) {

            if (i < 3) {
                int x = 110;
                int y = 12 + rcount * 18;

                if (!ingredients.get(i).test(RankineItems.ELEMENT.get().getDefaultInstance())) {
                    builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredients.get(i))
                            .setBackground(slotDrawable, -1, -1);

                    rcount++;
                }

            } else {
                if (!ingredients.get(i).equals(Ingredient.EMPTY) && !ingredients.get(i).test(RankineItems.ELEMENT.get().getDefaultInstance())) {
                    int x = nrcount <= 9 ? ((nrcount)*18)  + 1 : ((nrcount-10)*18)  + 1;
                    int y = nrcount <= 9 ? 106 : 124;
                    builder.addSlot(RecipeIngredientRole.INPUT,x,y).addIngredients(ingredients.get(i));
                    nrcount++;
                }
            }


        }
    }
}
