package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineDataComponents.AlloyStats;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.alloys.AlloyEnchantmentUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public interface IAlloyShield extends IAlloyItem {
    @Override
    default void createAlloyNBT(ItemStack stack, Level worldIn, String composition, @Nullable ResourceLocation alloyRecipe, @Nullable String nameOverride) {
        stack.remove(RankineDataComponents.REGENERATE_ALLOY.get());
        List<ElementRecipe> elements = this.getElementRecipes(composition,worldIn);
        List<Integer> percents = this.getPercents(composition);

        int dur = 0;
        int ench = 0;
        float cr = 0;
        float hr = 0;
        float tough = 0;

        for (int i = 0; i < elements.size(); i++) {
            ElementRecipe element = elements.get(i);
            int percentage = percents.get(i);

            dur += element.getDurability(percentage);
            ench += element.getEnchantability(percentage);
            cr += element.getCorrosionResistance(percentage);
            hr += element.getHeatResistance(percentage);
            tough += element.getToughness(percentage);
        }



        if (alloyRecipe != null) {
            Optional<RecipeHolder<?>> opt = worldIn.getRecipeManager().byKey(alloyRecipe);
            if (opt.isPresent() && opt.get().value() instanceof AlloyingRecipe recipe) {
                dur += recipe.getBonusDurability();
                ench += recipe.getBonusEnchantability();
                cr += recipe.getBonusCorrosionResistance();
                hr += recipe.getBonusHeatResistance();
                tough += recipe.getBonusToughness();
            }
        }

        dur = Math.max(1,dur);
        ench = Math.max(0,ench);
        cr = Math.min(Math.max(0,cr),1);
        hr = Math.min(Math.max(0,hr),1);
        tough = Math.min(Math.max(-1,tough),1);

        // was NBT "StoredAlloy" {comp, recipe, durability, enchantability, corrResist, heatResist, toughness}
        stack.set(RankineDataComponents.ALLOY_COMPOSITION.get(), composition);
        if (alloyRecipe != null) {
            stack.set(RankineDataComponents.ALLOY_RECIPE.get(), alloyRecipe);
        } else {
            stack.remove(RankineDataComponents.ALLOY_RECIPE.get());
        }
        AlloyStats stats = new AlloyStats(dur, 0f, 0, ench, 0f, 0f,
                Math.round(cr*100)/100f, Math.round(hr*100)/100f, Math.round(tough*100)/100f,
                0, 0, 0f, 0f);
        stack.set(RankineDataComponents.ALLOY_STATS.get(), stats);
        stack.set(DataComponents.MAX_DAMAGE, dur);
        if (!stack.has(DataComponents.DAMAGE)) {
            stack.set(DataComponents.DAMAGE, 0);
        }

        if (nameOverride != null) {
            stack.set(RankineDataComponents.NAME_OVERRIDE.get(), nameOverride);
        }
    }

    default void applyAlloyEnchantments(ItemStack stack, Level worldIn) {
        int start = 10;
        int interval = 5;
        int maxLvl = 5;
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        Optional<RecipeHolder<?>> holder = rs != null ? worldIn.getRecipeManager().byKey(rs) : Optional.empty();
        if (holder.isPresent() && holder.get().value() instanceof AlloyingRecipe recipe) {
            start = recipe.getMinEnchantability();
            interval = recipe.getEnchantInterval();
            maxLvl = recipe.getMaxEnchantLevelIn();
            for (Holder<Enchantment> e: AlloyEnchantmentUtils.getAlloyEnchantments(recipe,stack,worldIn))
            {
                int enchLvl = Math.min(Math.floorDiv(Math.max(getAlloyEnchantability(stack) - start + interval,0),interval),maxLvl);
                if (enchLvl > 0 && EnchantmentHelper.getItemEnchantmentLevel(e,stack) == 0) {
                    stack.enchant(e,Math.min(e.value().getMaxLevel(),enchLvl));
                }
            }
        }
        for (Holder<Enchantment> e: AlloyEnchantmentUtils.getElementEnchantments(getElementRecipes(IAlloyItem.getAlloyComposition(stack),worldIn),getPercents(IAlloyItem.getAlloyComposition(stack)),stack,worldIn))
        {
            int enchLvl = Math.min(Math.floorDiv(Math.max(getAlloyEnchantability(stack) - start + interval,0),interval),maxLvl);
            if (enchLvl > 0 && EnchantmentHelper.getItemEnchantmentLevel(e,stack) == 0) {
                stack.enchant(e,Math.min(e.value().getMaxLevel(),enchLvl));
            }
        }

    }

    @Nullable
    default AlloyStats getShieldStats(ItemStack stack) {
        return stack.get(RankineDataComponents.ALLOY_STATS.get());
    }

    default int getAlloyDurability(ItemStack stack)
    {
        AlloyStats stats = getShieldStats(stack);
        return stats != null ? stats.durability() : 1;
    }

    default int getAlloyEnchantability(ItemStack stack) {
        AlloyStats stats = getShieldStats(stack);
        return stats != null ? stats.enchantability() : 1;
    }

    default float getCorrResist(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_CORROSION.get())
        {
            return 100;
        }
        AlloyStats stats = getShieldStats(stack);
        return stats != null ? stats.corrResist() : 0;
    }


    default float getHeatResist(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_HEAT.get())
        {
            return 100;
        }
        AlloyStats stats = getShieldStats(stack);
        return stats != null ? stats.heatResist() : 0;
    }

    default float getToughness(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_TOUGHNESS.get())
        {
            return 0;
        }
        AlloyStats stats = getShieldStats(stack);
        return stats != null ? stats.toughness() : 0;
    }

    default int calcDurabilityLoss(ItemStack stack, Level worldIn, LivingEntity entityLiving, boolean isEfficient)
    {
        boolean memory = false;
        RandomSource rand = worldIn.getRandom();
        int i = 1;
        if (rand.nextFloat() > getHeatResist(stack) && (entityLiving.isInLava() || entityLiving.getRemainingFireTicks() > 0 || worldIn.dimension() == Level.NETHER)) {
            i += Config.ALLOYS.ALLOY_HEAT_AMT.get();
            memory = true;
        }
        if ((rand.nextFloat() > getCorrResist(stack) && entityLiving.isInWaterOrRain()))
        {
            i += Config.ALLOYS.ALLOY_CORROSION_AMT.get();
        }
        if (!isEfficient)
        {
            i *= 2;
        }

        if (memory && RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.SHAPE_MEMORY, stack) >= 1) {
            stack.setDamageValue(Math.max(stack.getDamageValue() - i,0));
            i = 0;
        }
        return i;
    }
}
