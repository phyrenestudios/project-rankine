package com.cannolicatfish.rankine.items.elements;

import com.cannolicatfish.rankine.blocks.gases.GasBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public interface Reactive {

    boolean canBreakBlocks();

    boolean reactsInHand();

    int explosionRadius();

    List<Fluid> getFluidReactionList();

    ItemStack stackOnInteraction();

    GasBlock gasOnInteraction();
}
