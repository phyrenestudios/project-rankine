package com.cannolicatfish.rankine.blocks.gyratorycrusher;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.BatteryItem;
import com.cannolicatfish.rankine.items.CrushingHeadItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.cannolicatfish.rankine.init.RankineBlocks.GYRATORY_CRUSHER_CONTAINER;

public class GyratoryCrusherContainer extends AbstractContainerMenu {
    private BlockEntity tileEntity;
    private final Container furnaceInventory;
    private Player playerEntity;
    private IItemHandler playerInventory;
    private final ContainerData data;
    protected final Level world;

    public GyratoryCrusherContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        this(windowId,world,pos,playerInventory,player, new SimpleContainer(9),new SimpleContainerData(5));
    }

    public GyratoryCrusherContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, Container furnaceInventoryIn,  ContainerData furnaceData) {
        super(GYRATORY_CRUSHER_CONTAINER, windowId);
        tileEntity = world.getBlockEntity(pos);
        checkContainerSize(furnaceInventoryIn, 9);
        checkContainerDataCount(furnaceData, 5);
        this.data = furnaceData;
        this.playerEntity = player;
        this.furnaceInventory = furnaceInventoryIn;
        this.playerInventory = new InvWrapper(playerInventory);
        this.world = playerEntity.level;

        this.addSlot(new Slot(furnaceInventory, 0, 56, 31));
        this.addSlot(new Slot(furnaceInventory, 1, 10,37));
        this.addSlot(new Slot(furnaceInventory, 2, 10,57));
        this.addSlot(new Slot(furnaceInventory, 3, 98,34));
        this.addSlot(new Slot(furnaceInventory, 4, 124,34));
        this.addSlot(new Slot(furnaceInventory, 5, 150,34));
        this.addSlot(new Slot(furnaceInventory, 6, 98,60));
        this.addSlot(new Slot(furnaceInventory, 7, 124,60));
        this.addSlot(new Slot(furnaceInventory, 8, 150,60));

        layoutPlayerInventorySlots(10, 86);

        this.addDataSlots(furnaceData);

    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, RankineBlocks.GYRATORY_CRUSHER.get());
    }

    protected boolean hasRecipe(ItemStack stack) {
        return this.world.getRecipeManager().getRecipeFor(RankineRecipeTypes.CRUSHING, new SimpleContainer(stack), this.world).isPresent();
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index >= 3 && index <= 8) {
                if (!this.moveItemStackTo(stack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else if (index != 1 && index != 0) {
                if (hasRecipe(stack)) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() instanceof BatteryItem) {
                    if (!this.moveItemStackTo(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() instanceof CrushingHeadItem) {
                    if (!this.moveItemStackTo(stack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 36) {
                    if (!this.moveItemStackTo(stack, 36, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 45 && !this.moveItemStackTo(stack, 8, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 9, 45, false)) {
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
        int i = this.data.get(1);
        if(i == 0) i = 200;
        return this.data.get(0) * pixels / i;
    }


    @OnlyIn(Dist.CLIENT)
    public int getCookProgressScaled(int pixels)
    {
        int i = this.data.get(2);
        int j = this.data.get(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning()
    {
        return this.data.get(0) > 0;
    }

}
