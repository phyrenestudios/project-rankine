package com.cannolicatfish.rankine.items.indexer;

import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.cannolicatfish.rankine.init.RankineItems.ELEMENT_INDEXER_CONTAINER;

public class ElementIndexerContainer extends Container {
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private IItemHandler handler;
    private static final PeriodicTableUtils utils = new PeriodicTableUtils();
    private Item currentItem;

    public ElementIndexerContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        super(ELEMENT_INDEXER_CONTAINER, windowId);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.handler = new ElementIndexerItem.ElementIndexerIItemHandler();
        addSlot(new SlotItemHandler(this.handler, 0, 8, 8));
        layoutPlayerInventorySlots(8, 140);
    }

    public void onContainerClosed(PlayerEntity playerIn) {
        PlayerInventory playerinventory = playerIn.inventory;
        if (!playerinventory.getItemStack().isEmpty()) {
            playerIn.dropItem(playerinventory.getItemStack(), false);
            playerinventory.setItemStack(ItemStack.EMPTY);
        }
        if (playerinventory.getFirstEmptyStack() == -1)
        {
            playerIn.dropItem(this.handler.getStackInSlot(0), false);
        }  else {
            playerIn.addItemStackToInventory(this.handler.getStackInSlot(0));
        }

    }

    public ElementRecipe getSlotItem(World worldIn) {
        ItemStack stack = this.handler.getStackInSlot(0);

        if (utils.hasElementRecipe(stack, worldIn))
        {
            return utils.getElementRecipe(stack, worldIn);
        } else {
            return null;
        }
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index != 0) {
                if (handler.isItemValid(0,stack)) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.mergeItemStack(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.mergeItemStack(stack, 5, 28, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 5, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

}

