package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;

public interface IAlloyTieredItem extends IAlloySpecialItem {

    @Override
    default void initStats(Map<ElementRecipe, Integer> elementMap, @Nullable ResourceLocation alloyRecipe) {

    }

    int createValueForDurability(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier);

    int createValueForEnchantability(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier);

    float createValueForCorrosionResistance(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier);

    float createValueForHeatResistance(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier);

    float createValueForToughness(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier);
}
