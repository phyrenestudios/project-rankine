package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.item.ItemStack;

import java.util.AbstractMap;

public class FineryForgeRecipes {

    private static final FineryForgeRecipes INSTANCE = new FineryForgeRecipes();

    public static FineryForgeRecipes getInstance() {
        return INSTANCE;
    }

    public ItemStack getResult(ItemStack input1) {
        if ((input1.getItem() == ModItems.PIG_IRON_INGOT)) {
            return new ItemStack(ModItems.BLOOM_IRON, 1);
        }

        return ItemStack.EMPTY;
    }

    public AbstractMap.SimpleEntry<ItemStack, Float[]> getPrimaryResult(ItemStack input1) {

        if ((input1.getItem() == ModItems.BLOOM_IRON)) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.WROUGHT_IRON_INGOT, 1), new Float[]{1f, 0f});
        }
        return new AbstractMap.SimpleEntry<>(ItemStack.EMPTY, new Float[]{1f,0f});
    }

    public AbstractMap.SimpleEntry<ItemStack, Float> getSecondaryResult(ItemStack input) {
        float chance = 1.0f;
        if (input.getItem() == ModItems.BLOOM_IRON) {
            chance = 0.5f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.SLAG, 1), chance);
        }
        return new AbstractMap.SimpleEntry<>(ItemStack.EMPTY, chance);
    }
}
