package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineDataComponents.AlloyStats;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface IAlloySpecialItem extends IAlloyItem {

    List<AlloyModifier.ModifierType> STATS = Collections.emptyList();

    void initStats(ItemStack stack, Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloyRecipe, @Nullable AlloyModifierRecipe alloyModifier);

    default AlloyModifier getModifierForStat(AlloyModifierRecipe modifierRecipe, AlloyModifier.ModifierType type) {
        if (modifierRecipe == null) {
            return null;
        }
        for (AlloyModifier mod : modifierRecipe.getModifiers()) {
            if (mod.getType().equals(type)) {
                return mod;
            }
        }
        return null;
    }

    /**
     * Central helper: stores the computed alloy stats component and mirrors the durability into
     * the vanilla MAX_DAMAGE component so the stack is damageable/repairable like in 1.18.2
     * (old code overrode getMaxDamage(ItemStack) from NBT; the override is kept as well).
     */
    default void applyAlloyStats(ItemStack stack, AlloyStats stats) {
        stack.set(RankineDataComponents.ALLOY_STATS.get(), stats);
        if (stats.durability() > 0) {
            stack.set(DataComponents.MAX_DAMAGE, stats.durability());
            if (!stack.has(DataComponents.DAMAGE)) {
                stack.set(DataComponents.DAMAGE, 0);
            }
        }
    }

    @Nullable
    default AlloyStats getAlloyStats(ItemStack stack) {
        return stack.get(RankineDataComponents.ALLOY_STATS.get());
    }

    @Override
    default boolean isAlloyInit(ItemStack stack) {
        return stack.has(RankineDataComponents.ALLOY_COMPOSITION.get()) || stack.has(RankineDataComponents.ALLOY_RECIPE.get())
                || stack.has(RankineDataComponents.ALLOY_STATS.get());
    }

    @Override
    default boolean needsRefresh(ItemStack stack) {
        return this.isAlloyInit(stack) && stack.getOrDefault(RankineDataComponents.REGENERATE_ALLOY.get(), false);
    }

    @Override
    default void setRefresh(ItemStack stack) {
        if (this.isAlloyInit(stack)) {
            stack.set(RankineDataComponents.REGENERATE_ALLOY.get(), true);
        }
    }

    List<AlloyModifier.ModifierType> getDefaultStats();
}
