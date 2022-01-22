package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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

    @Override
    default boolean isAlloyInit(ItemStack stack) {
        return stack.getTag() != null && (!stack.getTag().getCompound("StoredAlloy").isEmpty() || !stack.getTag().getCompound("StoredAlloyStats").isEmpty());
    }

    default boolean needsRefresh(ItemStack stack) {
        return stack.getTag() != null && (!stack.getTag().getCompound("StoredAlloy").isEmpty() || !stack.getTag().getCompound("StoredAlloyStats").isEmpty()) && stack.getTag().getBoolean("RegenerateAlloy");
    }

    default void setRefresh(ItemStack stack) {
        if (stack.getTag() != null && (!stack.getTag().getCompound("StoredAlloy").isEmpty() || !stack.getTag().getCompound("StoredAlloyStats").isEmpty())) {
            stack.getTag().putBoolean("RegenerateAlloy",true);
        }
    }

    List<AlloyModifier.ModifierType> getDefaultStats();
}
