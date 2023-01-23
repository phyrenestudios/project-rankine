package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.EvaporationRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EvaporationRecipeCategory implements IRecipeCategory<EvaporationRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "evaporation");
    private final IGuiHelper guiHelper;
    private final IDrawable background;
    private final IDrawable slotDrawable;

    public EvaporationRecipeCategory(IGuiHelper helper) {
        this.guiHelper = helper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/evaporation_jei.png"), 0, 0, 170, 140)
                .addPadding(1, 0, 0, 15)
                .build();
        slotDrawable = guiHelper.getSlotDrawable();
    }

    @Override
    public Component getTitle() {
        return new TextComponent(I18n.get("rankine.jei.evaporation"));
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends EvaporationRecipe> getRecipeClass() {
        return EvaporationRecipe.class;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.EVAPORATION_TOWER.get()));
    }

    @Override
    public void draw(EvaporationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public List<Component> getTooltipStrings(EvaporationRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX > 103 && mouseX < 120 && mouseY > 24 && mouseY < 41) {
            if (recipe.getBiomes().isEmpty() && recipe.getBiomeTags().isEmpty()) {
                return List.of(new TextComponent(I18n.get("rankine.jei.tooltip_biomes_info") + (I18n.get("rankine.jei.tooltip_any"))),
                        new TextComponent(I18n.get("rankine.jei.tooltip_biomes_tags_info") + (I18n.get("rankine.jei.tooltip_any"))));
            } else {
                List<Component> components = new ArrayList<>();
                if (!recipe.getBiomes().isEmpty()) {
                    components.add(new TextComponent(I18n.get("rankine.jei.tooltip_biomes_info") + recipe.getBiomes().toString()));
                }
                if (!recipe.getBiomeTags().isEmpty()) {
                    components.add(new TextComponent(I18n.get("rankine.jei.tooltip_biomes_tags_info") + recipe.getBiomeTags().toString()));
                }
                return components;
            }

        }
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, EvaporationRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> outputs = recipe.getOutputs();
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        builder.addSlot(RecipeIngredientRole.INPUT,57,20).addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(recipe.getFluid().getFluid(),1000));

        int count = 1;
        int ycount = 1;
        for (int i = 0; i < outputs.size(); i++) {
            if (count % 9 == 0) {
                count = 1;
                ycount++;
            }
            int x = (count*18);
            int y = 48 + (ycount*18);
            int currentI = i;
            Ingredient currentOutput = outputs.get(i);
            if (currentOutput.isEmpty()) {
                currentOutput = Ingredient.of(new ItemStack(Items.BARRIER).setHoverName(new TextComponent(I18n.get("rankine.jei.tooltip_nothing"))));
            }
            builder.addSlot(RecipeIngredientRole.OUTPUT,x,y).addIngredients(currentOutput)
                    .setBackground(slotDrawable, -1, -1)
                    .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_chance")+df.format(recipe.getChance(currentI)*100)+"%")));
            count++;
        }

    }
}
