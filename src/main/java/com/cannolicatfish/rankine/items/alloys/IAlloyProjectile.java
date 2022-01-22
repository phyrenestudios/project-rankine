package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IAlloyProjectile extends IAlloySpecialItem {

    @Override
    default void initStats(ItemStack stack, Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloyRecipe, @Nullable AlloyModifierRecipe alloyModifier) {

    }

    @Override
    default List<AlloyModifier.ModifierType> getDefaultStats() {
        return null;
    }

    default int getAlloyArrowDamage(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getInt("durability");
        } else {
            return 1;
        }
    }

/*
    default float createValueForProjectileDamage()
    {
        int hl = calcMiningLevel(elements,percents);
        float dmg = calcDamage(elements,percents);

        return 2f + 0.5f*hl + 0.5f*dmg;
    }

    default float createValueForProjectileVelocity()
    {
        int hl = calcMiningLevel(elements,percents);
        float as = calcAttackSpeed(elements,percents);
        float ms = calcMiningSpeed(elements,percents);

        return 2f + 0.5f*hl + 0.5f*dmg;
    }*/
}
