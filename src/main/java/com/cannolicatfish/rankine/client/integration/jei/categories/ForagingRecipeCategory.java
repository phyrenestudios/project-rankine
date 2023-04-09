package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.recipe.ForagingRecipe;
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
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ForagingRecipeCategory implements IRecipeCategory<ForagingRecipe> {
    private final IDrawable background;
    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "foraging");
    private final IGuiHelper guiHelper;
    private final IDrawable slotDrawable;
    private final IDrawable enchantedDrawable;
    public ForagingRecipeCategory(IGuiHelper helper) {
        this.guiHelper = helper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/foraging_jei.png"), 0, 0, 170, 140)
                .addPadding(1, 0, 0, 15)
                .build();
        slotDrawable = guiHelper.getSlotDrawable();
        enchantedDrawable = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/slot_enchanted.png"),0,0,18,18).setTextureSize(18,18).build();
    }

    @Override
    public Component getTitle() {
        return new TextComponent(I18n.get("rankine.jei.foraging"));
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.BRONZE_HOE.get()));
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends ForagingRecipe> getRecipeClass() {
        return ForagingRecipe.class;
    }

    @Override
    public void draw(ForagingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public List<Component> getTooltipStrings(ForagingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX > 103 && mouseX < 120 && mouseY > 24 && mouseY < 41) {
            if (recipe.isAllBiomes()) {
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
    public void setRecipe(IRecipeLayoutBuilder builder, ForagingRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> outputs = recipe.getOutputs();
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        builder.addSlot(RecipeIngredientRole.INPUT,78,20).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.CATALYST,37,20).addIngredients(Ingredient.of(RankineLists.FORAGING_TOOLS.stream().map(Item::getDefaultInstance)));

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
            boolean enchanted = recipe.getEnchantments().get(currentI);
            if (enchanted) {
                builder.addSlot(RecipeIngredientRole.OUTPUT,x,y).addIngredients(currentOutput)
                        .setBackground(enchantedDrawable, -1, -1)
                        .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_chance")+df.format(recipe.getChance(currentI)*100)+"%")))
                        .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_enchantment_required")).withStyle(ChatFormatting.LIGHT_PURPLE)));
            } else {
                builder.addSlot(RecipeIngredientRole.OUTPUT,x,y).addIngredients(currentOutput)
                        .setBackground(slotDrawable, -1, -1)
                        .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_chance")+df.format(recipe.getChance(currentI)*100)+"%")));
            }
            ;
            count++;


        }
    }
}
