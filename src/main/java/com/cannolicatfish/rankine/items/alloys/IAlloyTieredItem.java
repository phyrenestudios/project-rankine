package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.alloys.AlloyEnchantmentUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public interface IAlloyTieredItem extends IAlloySpecialItem {
    
    List<AlloyModifier.ModifierType> STATS = Arrays.asList(AlloyModifier.ModifierType.DURABILITY, AlloyModifier.ModifierType.CORROSION_RESISTANCE, AlloyModifier.ModifierType.HEAT_RESISTANCE, AlloyModifier.ModifierType.TOUGHNESS);

    @Override
    default List<AlloyModifier.ModifierType> getDefaultStats() {
        return STATS;
    }

    @Override
    default void initStats(ItemStack stack, Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloyRecipe, @Nullable AlloyModifierRecipe alloyModifier) {
        CompoundNBT listnbt = new CompoundNBT();
        listnbt.putInt("durability",createValueForDurability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.DURABILITY)));
        listnbt.putInt("enchantability",createValueForEnchantability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ENCHANTABILITY)));
        listnbt.putFloat("corrResist",createValueForCorrosionResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.CORROSION_RESISTANCE)));
        listnbt.putFloat("heatResist",createValueForHeatResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.HEAT_RESISTANCE)));
        listnbt.putFloat("toughness",createValueForToughness(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.TOUGHNESS)));
        stack.getOrCreateTag().put("StoredAlloyStats", listnbt);
    }

    @Override
    default void addAlloyInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (this.isAlloyInit(stack)) {
            if (!Screen.hasShiftDown()) {
                tooltip.add((new StringTextComponent("Hold shift for details...")).mergeStyle(TextFormatting.GRAY));
            }
            if (Screen.hasShiftDown()) {
                if (IAlloyItem.getAlloyComposition(stack).isEmpty()) {
                    tooltip.add((new StringTextComponent("Any Composition").mergeStyle(TextFormatting.GOLD)));
                } else {
                    tooltip.add((new StringTextComponent("Composition: " + IAlloyItem.getAlloyComposition(stack)).mergeStyle(TextFormatting.GOLD)));
                }

                if (!IAlloyItem.getAlloyModifiers(stack).isEmpty()) {
                    tooltip.add((new StringTextComponent("Modifier: " + (IAlloyItem.getAlloyModifiers(stack).getCompound(0).getString("modifierName"))).mergeStyle(TextFormatting.AQUA)));
                } else {
                    tooltip.add((new StringTextComponent("No Modifiers Present").mergeStyle(TextFormatting.AQUA)));
                }

                if (!this.needsRefresh(stack)) {

                    tooltip.add((new StringTextComponent("Durability: " + (getAlloyDurability(stack) - stack.getDamage()) + "/" + getAlloyDurability(stack))).mergeStyle(TextFormatting.DARK_GREEN));
                    tooltip.add((new StringTextComponent("Enchantability: " + getAlloyEnchantability(stack))).mergeStyle(TextFormatting.GRAY));
                    if (Config.ALLOYS.ALLOY_CORROSION.get()) {
                        tooltip.add((new StringTextComponent("Corrosion Resistance: " + (df.format(getCorrResist(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                    }
                    if (Config.ALLOYS.ALLOY_HEAT.get()) {
                        tooltip.add((new StringTextComponent("Heat Resistance: " + (df.format(getHeatResist(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                    }
                    if (Config.ALLOYS.ALLOY_TOUGHNESS.get()) {
                        tooltip.add((new StringTextComponent("Toughness: " + (df.format(getToughness(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                    }
                }
            }
        }
    }


    default int createValueForDurability(Map<ElementRecipe,Integer> elementMap, @Nullable AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        int dur = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            dur += set.getKey().getDurability(set.getValue());
        }
        if (alloy != null) {
            dur += alloy.getBonusDurability();
        }

        if (modifier != null) {
            dur =  (int) Math.max(0,modifier.returnModification(dur));
        }
        return Math.max(1,dur);
    }

    default float createValueForMiningSpeed(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float ms = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            ms += set.getKey().getMiningSpeed(set.getValue());
        }
        if (alloy != null) {
            ms += alloy.getBonusMiningSpeed();
        }

        if (modifier != null) {
            ms =  Math.max(0,modifier.returnModification(ms));
        }
        return Math.round(ms*100)/100f;
    }


    default int createValueForEnchantability(Map<ElementRecipe, Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        int ench = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            ench += set.getKey().getEnchantability(set.getValue());
        }
        if (alloy != null) {
            ench += alloy.getBonusEnchantability();
        }

        if (modifier != null) {
            ench =  (int) Math.max(0,modifier.returnModification(ench));
        }
        return Math.max(1,ench);
    }

    default int createValueForHarvestLevel(Map<ElementRecipe, Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        int hlmin = 0;
        int hlmax = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            int hl = set.getKey().getMiningLevel(set.getValue());
            hlmin = Math.min(hl,hlmin);
            hlmax = Math.max(hl,hlmax);
        }
        if (alloy != null) {
            int hl = alloy.getBonusMiningLevel();
            hlmin = Math.min(hlmin,hlmin+hl);
            hlmax = Math.max(hlmax,hlmax+hl);
        }
        int hl = Math.max(hlmax + hlmin,0);
        if (modifier != null) {
            hl =  (int) Math.max(0,modifier.returnModification(hl));
        }
        return hl;
    }

    default float createValueForAttackSpeed(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float asmin = 0;
        float asmax = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            float as = set.getKey().getAttackSpeed(set.getValue());
            asmin = Math.min(as,asmin);
            asmax = Math.max(as,asmax);
        }
        if (alloy != null) {
            float as = alloy.getBonusAttackSpeed();
            asmin = Math.min(asmin,asmin+as);
            asmax = Math.max(asmax,asmax+as);
        }
        float as = asmax + asmin;
        if (modifier != null) {
            as =  Math.max(0,modifier.returnModification(as));
        }
        return Math.round(as*100)/100f;
    }

    default float createValueForAttackDamage(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float dmgmin = 0;
        float dmgmax = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            float dmg = set.getKey().getDamage(set.getValue());
            dmgmin = Math.min(dmg,dmgmin);
            dmgmax = Math.max(dmg,dmgmax);
        }
        if (alloy != null) {
            float dmg = alloy.getBonusDamage();
            dmgmin = Math.min(dmgmin,dmgmin+dmg);
            dmgmax = Math.max(dmgmax,dmgmax+dmg);
        }
        float dmg = dmgmax + dmgmin;
        if (modifier != null) {
            dmg = Math.max(0,modifier.returnModification(dmg));
        }
        return Math.round(dmg*100)/100f;
    }

    default float createValueForCorrosionResistance(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float cr = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            cr += set.getKey().getCorrosionResistance(set.getValue());
        }
        if (alloy != null) {
            cr += alloy.getBonusCorrosionResistance();
        }

        if (modifier != null) {
            cr =  Math.max(0,modifier.returnModification(cr));
        }
        return Math.round(Math.min(Math.max(0,cr),1)*100)/100f;
    }

    default float createValueForHeatResistance(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float hr = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            hr += set.getKey().getHeatResistance(set.getValue());
        }
        if (alloy != null) {
            hr += alloy.getBonusHeatResistance();
        }

        if (modifier != null) {
            hr =  Math.max(0,modifier.returnModification(hr));
        }
        return Math.round(Math.min(Math.max(0,hr),1)*100)/100f;
    }

    default float createValueForToughness(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float tough = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            tough += set.getKey().getToughness(set.getValue());
        }
        if (alloy != null) {
            tough += alloy.getBonusToughness();
        }

        if (modifier != null) {
            tough =  Math.max(0,modifier.returnModification(tough));
        }
        return Math.round(Math.min(Math.max(-1,tough),1)*100)/100f;
    }

    default float createValueForKnockbackResistance(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float kr = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            kr += set.getKey().getKnockbackResistance(set.getValue());
        }
        if (alloy != null) {
            kr += alloy.getBonusKnockbackResistance();
        }

        if (modifier != null) {
            kr =  Math.max(0,modifier.returnModification(kr));
        }
        return Math.round(Math.min(Math.max(0,kr),1)*100)/100f;
    }

    default int getAlloyDurability(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getInt("durability");
        } else {
            return 1;
        }

    }

    default float getAlloyMiningSpeed(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getFloat("miningSpeed");
        } else {
            return 0.1f;
        }

    }

    default int getAlloyEnchantability(ItemStack stack) {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getInt("enchantability");
        } else {
            return 1;
        }
    }

    default int getAlloyHarvestLevel(ItemStack stack) {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getInt("harvestLevel");
        } else {
            return 0;
        }
    }

    default float getAlloyAttackSpeed(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getFloat("attackSpeed");
        } else {
            return 0.1f;
        }

    }

    default float getAlloyAttackDamage(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getFloat("attackDamage");
        } else {
            return 0.1f;
        }

    }

    default float getCorrResist(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_CORROSION.get())
        {
            return 100;
        }
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getFloat("corrResist");
        } else {
            return 0;
        }

    }


    default float getHeatResist(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_HEAT.get())
        {
            return 100;
        }
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getFloat("heatResist");
        } else {
            return 0;
        }
    }

    default float getToughness(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_TOUGHNESS.get())
        {
            return 0;
        }
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getFloat("toughness");
        } else {
            return 0;
        }
    }

    default float getKnockbackResistance(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getInt("knockbackResist");
        } else {
            return 0;
        }

    }

    default int calcDurabilityLoss(ItemStack stack, World worldIn, LivingEntity entityLiving, boolean isEfficient)
    {
        Random rand = worldIn.getRandom();
        int i = 1;
        i += calcToughnessProc(stack,rand);
        i += calcCorrosionResistanceProc(stack,entityLiving,rand);
        int hr = calcHeatResistanceProc(stack,entityLiving,rand);
        i += hr;

        if (!isEfficient)
        {
            i *= 2;
        }

        if (hr > 0 && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SHAPE_MEMORY,stack) >= 1) {
            stack.setDamage(Math.max(stack.getDamage() - i,0));
            i = 0;
        }
        return i;
    }

    default int calcCorrosionResistanceProc(ItemStack stack, LivingEntity entity, Random random) {
        float corrResist = getCorrResist(stack);
        if ((random.nextFloat() > corrResist && entity.isWet())) {
            return Config.ALLOYS.ALLOY_CORROSION_AMT.get();
        }
        return 0;
    }

    default int calcHeatResistanceProc(ItemStack stack, LivingEntity entity, Random random) {
        float heatResist = getHeatResist(stack);
        if ((random.nextFloat() > heatResist &&  (entity.isInLava() || entity.getFireTimer() > 0 || entity.getEntityWorld().getDimensionKey() == World.THE_NETHER))) {
            return Config.ALLOYS.ALLOY_HEAT_AMT.get();
        }
        return 0;
    }

    default int calcToughnessProc(ItemStack stack, Random random) {
        float toughness = getToughness(stack);
        if (toughness > 0 && random.nextFloat() < toughness) {
            return -1;
        } else if (toughness < 0 && random.nextFloat() < Math.abs(toughness)) {
            return 1;
        }
        return 0;
    }

    default void applyAlloyEnchantments(ItemStack stack, World worldIn) {
        int start = 10;
        int interval = 5;
        int maxLvl = 5;
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null && worldIn.getRecipeManager().getRecipe(rs).isPresent()) {
            AlloyingRecipe recipe = (AlloyingRecipe) worldIn.getRecipeManager().getRecipe(rs).get();
            start = recipe.getMinEnchantability();
            interval = recipe.getEnchantInterval();
            maxLvl = recipe.getMaxEnchantLevelIn();
            for (Enchantment e: AlloyEnchantmentUtils.getAlloyEnchantments(recipe,stack,worldIn))
            {
                int enchLvl = Math.min(Math.floorDiv(Math.max(getAlloyEnchantability(stack) - start + interval,0),interval),maxLvl);
                if (enchLvl > 0 && EnchantmentHelper.getEnchantmentLevel(e,stack) == 0) {
                    stack.addEnchantment(e,Math.min(e.getMaxLevel(),enchLvl));
                }
            }
        }
        for (Enchantment e: AlloyEnchantmentUtils.getElementEnchantments(getElementRecipes(IAlloyItem.getAlloyComposition(stack),worldIn),getPercents(IAlloyItem.getAlloyComposition(stack)),stack,worldIn))
        {
            int enchLvl = Math.min(Math.floorDiv(Math.max(getAlloyEnchantability(stack) - start + interval,0),interval),maxLvl);
            if (enchLvl > 0 && EnchantmentHelper.getEnchantmentLevel(e,stack) == 0) {
                stack.addEnchantment(e,Math.min(e.getMaxLevel(),enchLvl));
            }
        }

    }


}
