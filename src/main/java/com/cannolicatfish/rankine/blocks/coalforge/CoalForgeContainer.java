package com.cannolicatfish.rankine.blocks.coalforge;

import com.cannolicatfish.rankine.init.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.cannolicatfish.rankine.init.ModBlocks.COAL_FORGE_CONTAINER;

public class CoalForgeContainer extends Container {
    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private final IIntArray data;

    public CoalForgeContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        this(windowId,world,pos,playerInventory,player,new IntArray(5));



    }
    public CoalForgeContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IIntArray furnaceData) {
        super(COAL_FORGE_CONTAINER, windowId);
        tileEntity = world.getTileEntity(pos);
        assertIntArraySize(furnaceData, 5);
        this.playerEntity = player;
        this.data = furnaceData;
        this.playerInventory = new InvWrapper(playerInventory);

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new SlotItemHandler(h, 0, 39, 37));
            addSlot(new SlotItemHandler(h, 1, 88,37));
            addSlot(new SlotItemHandler(h, 2, 146,11));
            addSlot(new SlotItemHandler(h, 3, 10,37));
            addSlot(new SlotItemHandler(h, 4, 146,37));
        });

        layoutPlayerInventorySlots(8, 70);

        this.trackIntArray(furnaceData);
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnTime(){
        return this.data.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentBurnTime(){
        return this.data.get(1);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookTime(){
        return this.data.get(2);
    }

    @OnlyIn(Dist.CLIENT)
    public int getTotalCookTime(){
        return this.data.get(3);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ModBlocks.COAL_FORGE);
    }
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

            if(index == 3)
            {
                if(!this.mergeItemStack(stack1, 4, 40, true)) return ItemStack.EMPTY;
                slot.onSlotChange(stack1, stack);
            }
            else if(index != 2 && index != 1 && index != 0)
            {
                Slot slot1 = (Slot)this.inventorySlots.get(index + 1);

                if(!slot1.getStack().isEmpty())
                {
                    if(!this.mergeItemStack(stack1, 0, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                    else if(CoalForgeTile.isFuel(stack1))
                    {
                        if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
                    }
                    else if(CoalForgeTile.isFuel(stack1))
                    {
                        if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
                    }
                    else if(CoalForgeTile.isFuel(stack1))
                    {
                        if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
                    }
                    else if(index >= 4 && index < 31)
                    {
                        if(!this.mergeItemStack(stack1, 31, 40, false)) return ItemStack.EMPTY;
                    }
                    else if(index >= 31 && index < 40 && !this.mergeItemStack(stack1, 4, 31, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if(!this.mergeItemStack(stack1, 4, 40, false))
            {
                return ItemStack.EMPTY;
            }
            if(stack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();

            }
            if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, stack1);
        }
        return stack;
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
        return 2;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled(int pixels)
    {
        int i = getCurrentBurnTime();
        if(i == 0) i = 200;
        return getBurnTime() * pixels / i;
    }


    @OnlyIn(Dist.CLIENT)
    public int getCookProgressScaled(int pixels)
    {
        int i = getCookTime();
        int j = getTotalCookTime();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning()
    {
        return getBurnTime() > 0;
    }


}
