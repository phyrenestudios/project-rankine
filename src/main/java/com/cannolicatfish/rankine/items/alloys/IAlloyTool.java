package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineDataComponents.AlloyStats;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

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

    /**
     * The mineable tag of this tool ("minecraft:mineable/pickaxe" etc). Used to build the vanilla
     * TOOL data component when stats are applied. Null for weapons without block-breaking rules.
     */
    @Nullable
    default TagKey<Block> getAlloyMineableTag() {
        return null;
    }

    @Override
    default void initStats(ItemStack stack, Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloyRecipe, @Nullable AlloyModifierRecipe alloyModifier) {
        // was NBT "StoredAlloyStats" {durability, miningSpeed, harvestLevel, enchantability,
        // attackDamage, attackSpeed, corrResist, heatResist, toughness}
        int harvestLevel = createValueForHarvestLevel(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.HARVEST_LEVEL));
        AlloyStats stats = new AlloyStats(
                createValueForDurability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.DURABILITY)),
                createValueForMiningSpeed(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.MINING_SPEED)),
                harvestLevel,
                createValueForEnchantability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ENCHANTABILITY)),
                createValueForAttackDamage(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ATTACK_DAMAGE)) + harvestLevel,
                createValueForAttackSpeed(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ATTACK_SPEED)),
                createValueForCorrosionResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.CORROSION_RESISTANCE)),
                createValueForHeatResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.HEAT_RESISTANCE)),
                createValueForToughness(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.TOUGHNESS)),
                0, 0, 0f, 0f);
        applyAlloyStats(stack, stats);
        applyAlloyToolComponent(stack, stats);
        // NOTE: attack damage/speed attribute modifiers are intentionally NOT baked into the
        // ATTRIBUTE_MODIFIERS component here — they are applied per-query by
        // events/handlers/common/ItemAttributeModifierHandler (ItemAttributeModifierEvent still
        // exists in NeoForge 21.1) via getAlloyAttackDamage/getAlloyAttackSpeed, exactly as in 1.18.2.
    }

    /**
     * Mirrors the dynamic mining stats into the vanilla TOOL component so anything reading
     * the component (e.g. ItemStack#getDestroySpeed default path) agrees with the overrides
     * the alloy tools keep. damage_per_block is 0 because alloy tools apply their own
     * durability loss in mineBlock via calcDurabilityLoss.
     */
    default void applyAlloyToolComponent(ItemStack stack, AlloyStats stats) {
        TagKey<Block> mineable = getAlloyMineableTag();
        if (mineable != null) {
            stack.set(DataComponents.TOOL, new Tool(List.of(
                    Tool.Rule.deniesDrops(getAlloyTier(stack).getIncorrectBlocksForDrops()),
                    Tool.Rule.minesAndDrops(mineable, stats.miningSpeed())),
                    1.0F, 0));
        }
    }

    @Override
    default void addAlloyInformation(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (this.isAlloyInit(stack)) {
            if (!Screen.hasShiftDown()) {
                tooltip.add((Component.literal("Hold shift for details...")).withStyle(ChatFormatting.GRAY));
            }
            if (Screen.hasShiftDown()) {
                if (IAlloyItem.getAlloyComposition(stack).isEmpty()) {
                    tooltip.add((Component.literal("Any Composition").withStyle(ChatFormatting.GOLD)));
                } else {
                    tooltip.add((Component.literal("Composition: " + IAlloyItem.getAlloyComposition(stack)).withStyle(ChatFormatting.GOLD)));
                }

                if (!IAlloyItem.getAlloyModifiers(stack).isEmpty()) {
                    tooltip.add((Component.literal("Modifier: " + (IAlloyItem.getAlloyModifiers(stack).get(0).name())).withStyle(ChatFormatting.AQUA)));
                }

                if (!this.needsRefresh(stack)) {

                    tooltip.add((Component.literal("Durability: " + (getAlloyDurability(stack) - stack.getDamageValue()) + "/" + getAlloyDurability(stack))).withStyle(ChatFormatting.DARK_GREEN));
                    tooltip.add((Component.literal("Tier: " + (getAlloyHarvestLevel(stack) + " (" + getAlloyTier(stack).toString() + ")"))).withStyle(ChatFormatting.GRAY));
                    tooltip.add((Component.literal("Mining Speed: " + df.format(getAlloyMiningSpeed(stack)))).withStyle(ChatFormatting.GRAY));
                    tooltip.add((Component.literal("Enchantability: " + getAlloyEnchantability(stack))).withStyle(ChatFormatting.GRAY));
                    if (Config.ALLOYS.ALLOY_CORROSION.get()) {
                        tooltip.add((Component.literal("Corrosion Resistance: " + (df.format(getCorrResist(stack) * 100)) + "%")).withStyle(ChatFormatting.GRAY));
                    }
                    if (Config.ALLOYS.ALLOY_HEAT.get()) {
                        tooltip.add((Component.literal("Heat Resistance: " + (df.format(getHeatResist(stack) * 100)) + "%")).withStyle(ChatFormatting.GRAY));
                    }
                    if (Config.ALLOYS.ALLOY_TOUGHNESS.get()) {
                        tooltip.add((Component.literal("Toughness: " + (df.format(getToughness(stack) * 100)) + "%")).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        }
    }

}
