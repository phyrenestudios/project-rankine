package com.cannolicatfish.rankine.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.core.NonNullList;

public class CraftingInventoryFilled extends CraftingContainer {
    private final NonNullList<ItemStack> stackList;
    private final int width;
    private final int height;
    private final AbstractContainerMenu eventHandler;

    public CraftingInventoryFilled(AbstractContainerMenu eventHandlerIn, int width, int height, NonNullList<ItemStack> stackListIn) {
        super(eventHandlerIn, width, height);
        this.stackList = stackListIn;
        this.eventHandler = eventHandlerIn;
        this.width = width;
        this.height = height;
    }

    public int getContainerSize() {
        return this.stackList.size();
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.stackList) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getItem(int index) {
        return index >= this.getContainerSize() ? ItemStack.EMPTY : this.stackList.get(index);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.stackList, index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack removeItem(int index, int count) {
        ItemStack itemstack = ContainerHelper.removeItem(this.stackList, index, count);
        if (!itemstack.isEmpty()) {
            this.eventHandler.slotsChanged(this);
        }

        return itemstack;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int index, ItemStack stack) {
        this.stackList.set(index, stack);
        this.eventHandler.slotsChanged(this);
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void setChanged() {
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean stillValid(Player player) {
        return true;
    }

    public void clearContent() {
        this.stackList.clear();
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void fillStackedContents(StackedContents helper) {
        for(ItemStack itemstack : this.stackList) {
            helper.accountSimpleStack(itemstack);
        }

    }
}
