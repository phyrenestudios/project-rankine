package com.cannolicatfish.rankine.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;

/**
 * A {@link CraftingContainer} that is pre-filled with an existing stack list.
 *
 * PORT-NOTE (1.21.1): {@code CraftingContainer} is now an interface; its former class body lives in
 * {@link TransientCraftingContainer}, which already exposes a constructor taking the backing
 * {@link NonNullList} and whose method bodies are identical to the ones this class used to duplicate
 * (getContainerSize/isEmpty/getItem/removeItem/removeItemNoUpdate/setItem/setChanged/stillValid/
 * clearContent/getWidth/getHeight/fillStackedContents). Extending it therefore preserves behavior 1:1
 * while also supplying the interface's new {@code getItems()} (and hence {@code asCraftInput()}),
 * which 1.21.1 recipe matching requires — the 1.18 version inherited an empty list there.
 */
public class CraftingInventoryFilled extends TransientCraftingContainer {

    public CraftingInventoryFilled(AbstractContainerMenu eventHandlerIn, int width, int height, NonNullList<ItemStack> stackListIn) {
        super(eventHandlerIn, width, height, stackListIn);
    }
}
