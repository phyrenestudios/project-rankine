package com.cannolicatfish.rankine.items.indexer;

import com.cannolicatfish.rankine.init.RankineMenus;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ElementIndexerContainer extends AbstractContainerMenu {
    private Player playerEntity;
    private IItemHandler playerInventory;
    private IItemHandler handler;
    private static final PeriodicTableUtils utils = new PeriodicTableUtils();
    private Item currentItem;

    public ElementIndexerContainer(int windowId, Inventory playerInventory, Player player) {
        super(RankineMenus.ELEMENT_INDEXER_CONTAINER.get(), windowId);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.handler = new ElementIndexerItem.ElementIndexerIItemHandler();
        addSlot(new SlotItemHandler(this.handler, 0, 8, 8));
        layoutPlayerInventorySlots(8, 140);
    }


    public void removed(Player playerIn) {
        Inventory playerinventory = playerIn.getInventory();
        /*if (!playerinventory.getCarried().isEmpty()) {
            playerIn.drop(playerinventory.getCarried(), false);
            playerinventory.setCarried(ItemStack.EMPTY);
        }*/
        if (playerinventory.getFreeSlot() == -1)
        {
            playerIn.drop(this.handler.getStackInSlot(0), false);
        }  else {
            playerIn.addItem(this.handler.getStackInSlot(0));
        }

    }

    public ElementRecipe getSlotItem(Level worldIn) {
        ItemStack stack = this.handler.getStackInSlot(0);

        if (utils.hasElementRecipe(stack, worldIn))
        {
            return utils.getElementRecipe(stack, worldIn);
        } else {
            return null;
        }
    }

    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if(slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index != 0) {
                if (handler.isItemValid(0,stack)) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(stack, 5, 28, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 5, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player playerIn) {
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

