package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.ForagingRecipe;
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
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.TierSortingRegistry;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(Items.STONE_SHOVEL));
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
        int index = recipe.getOutputs().indexOf(Ingredient.EMPTY);
        if (index != -1) {
            DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
                p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
            });
            float percent = recipe.getChance(index);
            font.draw(stack,new TextComponent(df.format(percent*100)+"%"),100,20,0xFF0000);
        } else {
            font.draw(stack,new TextComponent("0.00%"),100,20,0xFF0000);
        }

        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ForagingRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> outputs = recipe.getOutputs();
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        int index = recipe.getOutputs().indexOf(Ingredient.EMPTY);
        if (index != -1) {
            builder.addSlot(RecipeIngredientRole.INPUT,78,20).addIngredients(recipe.getIngredients().get(0)).addTooltipCallback((recipeSlotView, tooltip) ->
                    tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_failure_chance")+df.format(recipe.getChance(index)*100)+"%").withStyle(ChatFormatting.RED)));
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT,78,20).addIngredients(recipe.getIngredients().get(0));
        }


        int count = 1;
        int ycount = 1;
        for (int i = 0; i < outputs.size(); i++) {
            if (count % 9 == 0) {
                count = 1;
                ycount++;
            }
            int x = (count*18);
            int y = 48 + (ycount*18);
            if (!outputs.get(i).isEmpty()) {
                int currentI = i;
                boolean enchanted = recipe.getEnchantments().get(currentI);
                if (enchanted) {
                    builder.addSlot(RecipeIngredientRole.OUTPUT,x,y).addIngredients(outputs.get(i))
                            .setBackground(enchantedDrawable, -1, -1)
                            .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_chance")+df.format(recipe.getChance(currentI)*100)+"%")))
                            .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_enchantment_required")).withStyle(ChatFormatting.LIGHT_PURPLE)));
                } else {
                    builder.addSlot(RecipeIngredientRole.OUTPUT,x,y).addIngredients(outputs.get(i))
                            .setBackground(slotDrawable, -1, -1)
                            .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_chance")+df.format(recipe.getChance(currentI)*100)+"%")));
                }
;
                count++;
            }


        }
    }
}
