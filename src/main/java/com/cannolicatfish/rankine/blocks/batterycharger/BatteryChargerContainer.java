package com.cannolicatfish.rankine.blocks.batterycharger;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.cannolicatfish.rankine.init.RankineBlocks.BATTERY_CHARGER_CONTAINER;

public class BatteryChargerContainer extends AbstractContainerMenu {
    private BlockEntity tileEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;
    private final ContainerData data;

    public BatteryChargerContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        this(windowId,world,pos,playerInventory,player,new SimpleContainer(6), new SimpleContainerData(2));

    }
    public BatteryChargerContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, Container furnaceInventoryIn, ContainerData towerData) {
        super(BATTERY_CHARGER_CONTAINER, windowId);
        tileEntity = world.getBlockEntity(pos);
        checkContainerSize(furnaceInventoryIn, 6);
        checkContainerDataCount(towerData, 2);
        this.playerEntity = player;
        this.data = towerData;
        this.playerInventory = new InvWrapper(playerInventory);

        this.addSlot(new Slot(furnaceInventoryIn, 0, 38, 25));
        this.addSlot(new Slot(furnaceInventoryIn, 1, 81, 25));
        this.addSlot(new Slot(furnaceInventoryIn, 2, 124, 25));
        this.addSlot(new Slot(furnaceInventoryIn, 3, 38, 45));
        this.addSlot(new Slot(furnaceInventoryIn, 4, 81, 45));
        this.addSlot(new Slot(furnaceInventoryIn, 5, 124, 45));

        layoutPlayerInventorySlots(8, 70);
        this.addDataSlots(towerData);
    }

    @OnlyIn(Dist.CLIENT)
    public int getSignalPower(){
        return this.data.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public int getSignalMultiplier(){
        return this.data.get(1);
    }


    @OnlyIn(Dist.CLIENT)
    public int getSignalPowerScaled(int pixels)
    {
        int i = getSignalPower();
        return i != 0 ? i * pixels / 15 : 0;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, RankineBlocks.BATTERY_CHARGER.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index <= 5) {
                if (!this.moveItemStackTo(stack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {
                if (index < 32) {
                    if (!this.moveItemStackTo(stack, 32, 41, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 41 && !this.moveItemStackTo(stack, 5, 32, false)) {
                    return ItemStack.EMPTY;
                }
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

    public int getOutputSlot() {
        return 0;
    }


}
