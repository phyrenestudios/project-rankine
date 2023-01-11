package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
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
import net.minecraftforge.common.TierSortingRegistry;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CrushingRecipeCategory implements IRecipeCategory<CrushingRecipe> {
    private final IDrawable background;
    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "crushing");
    private final IGuiHelper guiHelper;
    private final IDrawable slotDrawable;
    private final IDrawable guaranteedDrawable;
    private final IDrawable woodDrawable;
    private final IDrawable stoneDrawable;
    private final IDrawable ironDrawable;
    private final IDrawable diamondDrawable;
    private final IDrawable netheriteDrawable;
    public CrushingRecipeCategory(IGuiHelper helper) {
        this.guiHelper = helper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/crushing_jei.png"), 0, 0, 160, 140)
                .addPadding(1, 0, 0, 15)
                .build();
        slotDrawable = guiHelper.getSlotDrawable();
        guaranteedDrawable = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/slot_guaranteed.png"),0,0,18,18).setTextureSize(18,18).build();
        woodDrawable = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/slot_wood.png"),0,0,18,18).setTextureSize(18,18).build();
        stoneDrawable = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/slot_stone.png"),0,0,18,18).setTextureSize(18,18).build();
        ironDrawable = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/slot_iron.png"),0,0,18,18).setTextureSize(18,18).build();
        diamondDrawable = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/slot_diamond.png"),0,0,18,18).setTextureSize(18,18).build();
        netheriteDrawable = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/slot_netherite.png"),0,0,18,18).setTextureSize(18,18).build();
    }

    @Override
    public Component getTitle() {
        return new TextComponent(I18n.get("rankine.jei.crushing"));
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.STONE_HAMMER.get()));
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends CrushingRecipe> getRecipeClass() {
        return CrushingRecipe.class;
    }

    @Override
    public void draw(CrushingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        int index = recipe.getRecipeOutputs().indexOf(ItemStack.EMPTY);
        font.draw(stack,new TextComponent("<=" + recipe.getMaxRolls()).withStyle(ChatFormatting.DARK_AQUA),100,32,0xFF0000);

        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrushingRecipe recipe, IFocusGroup focuses) {
        NonNullList<Tuple<ItemStack, Tier>> guaranteedOutputs = recipe.getGuaranteedOutputs();
        List<Tier> currentTiers = Arrays.asList(Tiers.WOOD, Tiers.STONE, Tiers.IRON, Tiers.DIAMOND, Tiers.NETHERITE);
        for (Tier t : recipe.getTiers()) {
            if (!currentTiers.contains(t)) {
                currentTiers.add(t);
            }
        }

        List<ItemStack> outputs = recipe.getRecipeOutputs();
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        builder.addSlot(RecipeIngredientRole.INPUT,78,21).addIngredients(recipe.getIngredients().get(0)).addTooltipCallback((recipeSlotView, tooltip) ->
                tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_max_rolls")+recipe.getMaxRolls()).withStyle(ChatFormatting.DARK_AQUA)));


        int count = 1;
        for (Tuple<ItemStack,Tier> output : guaranteedOutputs) {
            int x = (count*18);
            int y = 48;
            if (output.getB().equals(Tiers.WOOD)) {
                builder.addSlot(RecipeIngredientRole.OUTPUT,x,y).addItemStack(output.getA())
                        .setBackground(guaranteedDrawable, -1, -1)
                        .addTooltipCallback(((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_guaranteed")).withStyle(ChatFormatting.GOLD))));
            } else {
                Tuple<ChatFormatting,IDrawable> format = colorForTier(output.getB());
                builder.addSlot(RecipeIngredientRole.OUTPUT,x,y).addItemStack(output.getA())
                        .setBackground(guaranteedDrawable, -1, -1)
                        .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_tier")+output.getB()).withStyle(format.getA())))
                        .addTooltipCallback(((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_guaranteed")).withStyle(ChatFormatting.GOLD))));
            }
            count++;
        }

        count = 1;
        int ycount = 1;
        for (int i = 0; i < outputs.size(); i++) {
            if (count % 9 == 0) {
                count = 1;
                ycount++;
            }
            int x = (count*18);
            int y = 56 + (ycount*18);
            ItemStack currentOutput = outputs.get(i);
            if (currentOutput.isEmpty()) {
                currentOutput = new ItemStack(Items.BARRIER).setHoverName(new TextComponent(I18n.get("rankine.jei.tooltip_nothing")));
            }
            int currentI = i;
            boolean limited = recipe.getRecipeConstants().get(currentI);
            Tuple<ChatFormatting,IDrawable> format = colorForTier(recipe.getTiers().get(currentI));
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.OUTPUT,x,y).addItemStack(currentOutput);

            slot.setBackground(format.getB(), -1, -1);
                    //.addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get("rankine.jei.tooltip_tier")+recipe.getTiers().get(currentI)).withStyle(format.getA())))
            for (Tier t : currentTiers) {
                float chance = recipe.getChance(t,currentI)*100;
                slot.addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(I18n.get(t.toString() + ": ")+df.format(chance)+"%").withStyle(chance <= 0 ? ChatFormatting.DARK_RED : colorForTier(t).getA())));
            }
            slot.addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent(limited ? I18n.get("rankine.jei.tooltip_limited") : I18n.get("rankine.jei.tooltip_nonlimited")).withStyle(limited ? ChatFormatting.RED : ChatFormatting.GREEN)));

            count++;


        }
    }

    private Tuple<ChatFormatting,IDrawable> colorForTier(Tier tier) {
        ResourceLocation tierLoc = TierSortingRegistry.getName(tier);
        if (tierLoc != null) {
            switch (tierLoc.getPath().toUpperCase(Locale.ROOT)) {
                case "WOOD":
                    return new Tuple<>(ChatFormatting.DARK_GREEN,woodDrawable);
                case "STONE":
                    return new Tuple<>(ChatFormatting.GRAY,stoneDrawable);
                case "IRON":
                    return new Tuple<>(ChatFormatting.WHITE,ironDrawable);
                case "DIAMOND":
                    return new Tuple<>(ChatFormatting.AQUA,diamondDrawable);
                case "NETHERITE":
                    return new Tuple<>(ChatFormatting.DARK_PURPLE,netheriteDrawable);
                default:
                    return new Tuple<>(ChatFormatting.LIGHT_PURPLE,guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID,"textures/gui/slot_"+tier.toString().toLowerCase(Locale.ROOT)+".png"),0,0,18,18).setTextureSize(18,18).build());
            }
        }
        return new Tuple<>(ChatFormatting.WHITE,slotDrawable);
    }
}
