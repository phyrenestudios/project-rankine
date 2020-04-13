package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.items.ModItems;
import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

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

    public Pair<ItemStack, Float[]> getPrimaryResult(ItemStack input1) {

        if ((input1.getItem() == ModItems.BLOOM_IRON)) {
            return new Pair<>(new ItemStack(ModItems.WROUGHT_IRON_INGOT, 1), new Float[]{1f, 0f});
        }
        return new Pair<>(ItemStack.EMPTY, new Float[]{1f,0f});
    }

    public Pair<ItemStack, Float> getSecondaryResult(ItemStack input) {
        float chance = 1.0f;
        if (input.getItem() == ModItems.BLOOM_IRON) {
            chance = 0.5f;
            return new Pair<>(new ItemStack(ModItems.SLAG, 1), chance);
        }
        return new Pair<>(ItemStack.EMPTY, chance);
    }
}
