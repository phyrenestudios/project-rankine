package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface IAlloyTool extends IAlloyTieredItem {

    List<AlloyModifier.ModifierType> STATS = Arrays.asList(AlloyModifier.ModifierType.DURABILITY, AlloyModifier.ModifierType.ENCHANTABILITY, AlloyModifier.ModifierType.HARVEST_LEVEL,
            AlloyModifier.ModifierType.ATTACK_DAMAGE, AlloyModifier.ModifierType.ATTACK_SPEED, AlloyModifier.ModifierType.MINING_SPEED,
            AlloyModifier.ModifierType.CORROSION_RESISTANCE, AlloyModifier.ModifierType.HEAT_RESISTANCE, AlloyModifier.ModifierType.TOUGHNESS);

    @Override
    default List<AlloyModifier.ModifierType> getDefaultStats() {
        return STATS;
    }

    @Override
    default void initStats(ItemStack stack, Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloyRecipe, @Nullable AlloyModifierRecipe alloyModifier) {
        CompoundNBT listnbt = new CompoundNBT();
        listnbt.putInt("durability",createValueForDurability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.DURABILITY)));
        listnbt.putFloat("miningSpeed",createValueForMiningSpeed(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.MINING_SPEED)));
        listnbt.putInt("harvestLevel",createValueForHarvestLevel(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.HARVEST_LEVEL)));
        listnbt.putInt("enchantability",createValueForEnchantability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ENCHANTABILITY)));
        listnbt.putFloat("attackDamage",createValueForAttackDamage(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ATTACK_DAMAGE)));
        listnbt.putFloat("attackSpeed",createValueForAttackSpeed(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ATTACK_SPEED)));
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
                    tooltip.add((new StringTextComponent("Harvest Level: " + (getAlloyHarvestLevel(stack)))).mergeStyle(TextFormatting.GRAY));
                    tooltip.add((new StringTextComponent("Mining Speed: " + df.format(getAlloyMiningSpeed(stack)))).mergeStyle(TextFormatting.GRAY));
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

}
