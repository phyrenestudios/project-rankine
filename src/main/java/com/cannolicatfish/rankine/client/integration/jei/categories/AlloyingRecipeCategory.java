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
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.*;

public class AlloyingRecipeCategory implements IRecipeCategory<AlloyingRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "alloying");
    private final IDrawable background;
    private final IDrawable slotDrawable;
    private final IGuiHelper guiHelper;
    public AlloyingRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/alloy_jei.png"), 0, 0, 180, 140)
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
    public Class<? extends AlloyingRecipe> getRecipeClass() {
        return AlloyingRecipe.class;
    }

    @Override
    public Component getTitle() {
        return Component.literal(I18n.get("rankine.jei.alloying"));
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.ALLOY_FURNACE.get()));
    }

    private enum StatType {
        DURABILITY,
        MINING_SPEED,
        HARVEST_LEVEL,
        ENCHANTABILITY,
        ATTACK_DAMAGE,
        ATTACK_SPEED,
        CORROSION_RESISTANCE,
        HEAT_RESISTANCE,
        KNOCKBACK_RESISTANCE,
        TOUGHNESS
    }

    @Override
    public List<Component> getTooltipStrings(AlloyingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 76 && mouseX <= 97 && mouseY >= 27 && mouseY <= 48) {
            if (recipe.getEnchantments().size() == 0) {
                return List.of(new TranslatableComponent("rankine.jei.alloying_enchantments").withStyle(ChatFormatting.GOLD),Component.literal(I18n.get("rankine.jei.tooltip_none")));
            }
            List<Component> components = new ArrayList<>();
            components.add(new TranslatableComponent("rankine.jei.alloying_enchantments").withStyle(ChatFormatting.GOLD));
            components.add(new TranslatableComponent("rankine.jei.tooltip_starting_enchantability",String.valueOf(recipe.getMinEnchantability())).withStyle(ChatFormatting.AQUA));
            components.add(new TranslatableComponent("rankine.jei.tooltip_enchantability_interval",String.valueOf(recipe.getEnchantInterval())).withStyle(ChatFormatting.AQUA));
            components.add(Component.literal(I18n.get("rankine.jei.tooltip_max_level") + I18n.get("enchantment.level."+recipe.getMaxEnchantLevelIn())).withStyle(ChatFormatting.AQUA));
            List<String> enchList = recipe.getEnchantments();
            enchList.sort(Comparator.comparingInt(String::length).reversed());
            HashSet<String> enchants = new HashSet<>(enchList);
            for (String s : enchants) {
                if (ForgeRegistries.ENCHANTMENTS.containsKey(new ResourceLocation(s))) {
                    Holder<Enchantment> en = ForgeRegistries.ENCHANTMENTS.getHolder(new ResourceLocation(s)).orElse(null);
                    if (en != null && en.isBound()) {
                        components.add(Component.literal(I18n.get(en.value().getDescriptionId())));
                    }
                } else if (s.equals("rankine:random")) {
                    components.add(Component.literal(I18n.get("rankine.jei.tooltip_any")));
                }
            }
            return components;
        } else if (mouseX >= 40 && mouseX <= 61 && mouseY >= 27 && mouseY <= 48) {
            if (recipe.getBonusValues().stream().allMatch(aFloat -> aFloat == 0f)) {
                return List.of(new TranslatableComponent("rankine.jei.alloying_bonus_stats").withStyle(ChatFormatting.GOLD),Component.literal(I18n.get("rankine.jei.tooltip_none")));
            }
            List<Component> components = new ArrayList<>();
            components.add(new TranslatableComponent("rankine.jei.alloying_bonus_stats").withStyle(ChatFormatting.GOLD));
            for (int i = 0; i < recipe.getBonusValues().size(); i++) {
                if (i < AlloyingRecipeCategory.StatType.values().length) {
                    AlloyingRecipeCategory.StatType stat = AlloyingRecipeCategory.StatType.values()[i];
                    float bonusStat = recipe.getBonusStat(stat.ordinal());
                    if (bonusStat == 0f) {
                        continue;
                    }
                    boolean b = stat.equals(AlloyingRecipeCategory.StatType.CORROSION_RESISTANCE) || stat.equals(AlloyingRecipeCategory.StatType.HEAT_RESISTANCE) || stat.equals(AlloyingRecipeCategory.StatType.KNOCKBACK_RESISTANCE) || stat.equals(AlloyingRecipeCategory.StatType.TOUGHNESS);
                    boolean b1 = stat.equals(AlloyingRecipeCategory.StatType.DURABILITY) || stat.equals(AlloyingRecipeCategory.StatType.HARVEST_LEVEL) || stat.equals(AlloyingRecipeCategory.StatType.ENCHANTABILITY);

                    String statStr = "";
                    if (b) {
                        statStr = bonusStat*100 + "%";
                    } else if (b1) {
                        statStr = String.valueOf(Math.round(bonusStat));
                    } else {
                        statStr = String.valueOf(bonusStat);
                    }
                    components.add(Component.literal(new TranslatableComponent("block.rankine.material_testing_bench."+stat.toString().toLowerCase(Locale.ROOT)).getString() + ": +" + statStr));
                }

            }
            return components;
        }
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }

    @Override
    public void draw(AlloyingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Level level = Minecraft.getInstance().level;
        List<Ingredient> ingredients = recipe.getIngredientsList(level,true);
        DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        Font font = Minecraft.getInstance().font;

        font.draw(stack,new TranslatableComponent("rankine.jei.tooltip_required"),120, 0, 0x000000);
        font.draw(stack,new TranslatableComponent("rankine.jei.tooltip_additional"),1, 96, 0x000000);
        int count = 0;
        for (Ingredient i : ingredients) {
            Tuple<Float,Float> minMax = recipe.getMinMaxByElement(level,i.getItems()[0]);
            String s = df.format(minMax.getA()*100) + "-" + df.format(minMax.getB()*100)+"%";
            font.draw(stack,s,130, 16 + count * 18, 0x000000);
            count++;
        }

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


        builder.addSlot(RecipeIngredientRole.OUTPUT,5,31).addItemStack(output);

        for (Ingredient i : ingredients) {
            int x = 110;
            int y = 12 + rcount * 18;
            Tuple<Float,Float> minMax = recipe.getMinMaxByElement(level,i.getItems()[0]);
            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(i)
                    .setBackground(slotDrawable, -1, -1)
                    .addTooltipCallback(((recipeSlotView, tooltip) -> tooltip
                            .add(Component.literal(Math.round(minMax.getA() * 100) + "%")
                                    .append(Component.literal("-" + Math.round(minMax.getB() * 100) + "%"))
                                    .withStyle(ChatFormatting.GOLD))));
            rcount++;
        }

        int nrcount = 0;

        for (Ingredient i : groupedOptionals) {
            if (!i.equals(Ingredient.EMPTY)) {
                int x = nrcount <= 9 ? ((nrcount)*18)  + 1 : ((nrcount-10)*18)  + 1;
                int y = nrcount <= 9 ? 106 : 124;
                Tuple<Float,Float> minMax = recipe.getMinMaxByElement(level,i.getItems()[0]);
                builder.addSlot(RecipeIngredientRole.INPUT,x,y).addIngredients(i)
                        .addTooltipCallback(((recipeSlotView, tooltip) -> tooltip
                                .add(Component.literal( Math.round(minMax.getA() * 100) + "%")
                                        .append(Component.literal("-" + Math.round(minMax.getB() * 100) + "%"))
                                        .withStyle(ChatFormatting.GOLD))));
                nrcount++;
            }
        }
    }
}