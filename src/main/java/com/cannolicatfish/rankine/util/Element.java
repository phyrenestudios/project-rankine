package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.items.ModItems;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum Element {
    COPPER("Cu", 100, 1.0F, 10),
    IRON("Fe", 250, 2.0F, 14),
    GOLD("Au", 32, 0.0F, 22);

    public final String symbol;

    private Element(String symbol, int durabilityIn, float attackDamageIn, int enchantabilityIn)
    {
        this.symbol = symbol;
    }
}
