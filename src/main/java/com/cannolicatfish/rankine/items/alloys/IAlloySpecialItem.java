package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IAlloySpecialItem extends IAlloyItem {

    @Override
    default void createAlloyNBT(ItemStack stack, World worldIn, Map<ElementRecipe, Integer> elementMap, @Nullable ResourceLocation alloyRecipe, @Nullable String nameOverride) {
        IAlloyItem.super.createAlloyNBT(stack, worldIn, elementMap, alloyRecipe, nameOverride);
        initStats(elementMap,alloyRecipe);
    }

    void initStats(Map<ElementRecipe, Integer> elementMap, @Nullable ResourceLocation alloyRecipe);

    AlloyModifier getModifierForStat(AlloyModifier.ModifierType type);
}
