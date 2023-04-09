package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.MixingRecipe;
import com.mojang.blaze3d.platform.InputConstants;
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
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MixingRecipeCategory implements IRecipeCategory<MixingRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "mixing");
    private final IDrawable background;
    private final IDrawable slotDrawable;
    private final IGuiHelper guiHelper;

    private int totalCount = 1;

    public MixingRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/mixing_jei.png"), 0, 0, 180, 140)
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
    public Class<? extends MixingRecipe> getRecipeClass() {
        return MixingRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent(I18n.get("rankine.jei.mixing"));
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.MIXING_BARREL.get()));

    }

    @Override
    public boolean handleInput(MixingRecipe recipe, double mouseX, double mouseY, InputConstants.Key input) {
        if (mouseX >= 1 && mouseX <= 11 && mouseY >= 55 && mouseY <= 69  && input.getValue() == InputConstants.MOUSE_BUTTON_LEFT) {
            if (this.totalCount > 1) {
                this.totalCount--;
            }
        } else if (mouseX > 14 && mouseX < 24 && mouseY >= 55 && mouseY <= 69 && input.getValue() == InputConstants.MOUSE_BUTTON_LEFT) {
            if (this.totalCount < 64) {
                this.totalCount++;
            }
        }
        return IRecipeCategory.super.handleInput(recipe, mouseX, mouseY, input);
    }

    @Override
    public List<Component> getTooltipStrings(MixingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 58 && mouseX <= 79 && mouseY >= 27 && mouseY <= 48) {
            DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
                p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
            });
            List<Component> components = new ArrayList<>();
            components.add(new TranslatableComponent("rankine.jei.tooltip_mixing_time",recipe.getOutputMixTime(1)));
            components.add(new TranslatableComponent("rankine.jei.tooltip_total_mixing_time",recipe.getOutputMixTime(totalCount)));
            components.add(new TranslatableComponent("rankine.jei.tooltip_total_button_lever",df.format(recipe.getOutputMixTime(totalCount)/15)));

            return components;
        }
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }
    @Override
    public void draw(MixingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        List<Boolean> reqs = recipe.getRequired();
        List<Float> mins = recipe.getMins();
        List<Float> maxes = recipe.getMaxes();
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        Font font = Minecraft.getInstance().font;

        font.draw(stack,new TranslatableComponent("rankine.jei.tooltip_required"),120, 0, 0x000000);
        font.draw(stack,new TranslatableComponent("rankine.jei.tooltip_additional"),1, 96, 0x000000);
        font.draw(stack,new TranslatableComponent("rankine.jei.tooltip_total_ingredients",totalCount),1, 75, totalCount < (int) reqs.stream().filter(aBoolean -> aBoolean).count() ? 0xFF5555 : 0x000000);
        font.draw(stack,new TranslatableComponent("rankine.jei.tooltip_total_output",Math.round(totalCount*recipe.getMatScale())),1, 84, Math.round(totalCount*recipe.getMatScale()) > 64 || Math.round(totalCount*recipe.getMatScale()) < 1 ? 0xFF5555 : 0x000000);
        int count = 0;
        for (int i = 0; i < reqs.size(); i++) {
            if (reqs.get(i)) {
                String s = df.format(mins.get(i)*100) + "-" + df.format(maxes.get(i)*100)+"%";
                font.draw(stack,s,130, 16 + count * 18, 0x000000);
                count++;
            }
        }
        String mixTime = String.valueOf(recipe.getOutputMixTime(totalCount));
        font.draw(stack,mixTime,69 - font.width(mixTime) / 2.0F, 52, 0xAA0000);
        font.draw(stack,df.format(recipe.getOutputFluidReq(totalCount).getAmount())+"mb",130, 16 + count * 18, recipe.getOutputFluidReq(totalCount).getAmount() > 8000 ? 0xFF5555 : 0x000000);
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MixingRecipe recipe, IFocusGroup focuses) {
        Level level = Minecraft.getInstance().level;
        List<Ingredient> ingredients = recipe.getIngredients();
        List<Boolean> reqs = recipe.getRequired();
        List<Float> mins = recipe.getMins();
        List<Float> maxes = recipe.getMaxes();
        ItemStack output = recipe.getResultItem();
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });

        int rcount = 0;
        int nrcount = 0;


        builder.addSlot(RecipeIngredientRole.OUTPUT,5,31).addItemStack(output);

        for (int i = 0; i < ingredients.size(); i++) {
            float min = mins.get(i);
            float max = maxes.get(i);

            if (reqs.get(i)) {
                int x = 110;
                int y = 12 + rcount * 18;


                builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredients.get(i))
                        .setBackground(slotDrawable, -1, -1)
                        .addTooltipCallback(((recipeSlotView, tooltip) -> tooltip
                                .add(new TextComponent(Math.round(min * 100) + "%")
                                        .append(new TextComponent("-" + Math.round(max * 100) + "%"))
                                        .withStyle(ChatFormatting.GOLD))));
                rcount++;
            } else {
                if (!ingredients.get(i).equals(Ingredient.EMPTY)) {
                    int x = nrcount <= 9 ? ((nrcount)*18)  + 1 : ((nrcount-10)*18)  + 1;
                    int y = nrcount <= 9 ? 106 : 124;
                    builder.addSlot(RecipeIngredientRole.INPUT,x,y).addIngredients(ingredients.get(i))
                            .addTooltipCallback(((recipeSlotView, tooltip) -> tooltip
                                    .add(new TextComponent( Math.round(min * 100) + "%")
                                            .append(new TextComponent("-" + Math.round(max * 100) + "%"))
                                            .withStyle(ChatFormatting.GOLD))));
                    nrcount++;
                }
            }



        }

        if (!recipe.getFluid().isEmpty()) {
            int x = 110;
            int y = 12 + rcount * 18;

            builder.addSlot(RecipeIngredientRole.INPUT,x,y).addIngredients(ForgeTypes.FLUID_STACK,List.of(recipe.getFluidFilled())).setBackground(slotDrawable, -1, -1);
        }


    }
}
