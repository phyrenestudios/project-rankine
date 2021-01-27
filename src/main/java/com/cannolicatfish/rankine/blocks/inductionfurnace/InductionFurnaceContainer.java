package com.cannolicatfish.rankine.blocks.inductionfurnace;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.items.TripleAlloyTemplateItem;
import com.cannolicatfish.rankine.recipe.AlloyRecipeHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.cannolicatfish.rankine.init.RankineBlocks.INDUCTION_FURNACE_CONTAINER;

public class InductionFurnaceContainer extends Container {
    private final IInventory furnaceInventory;
    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private final IIntArray data;

    public InductionFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        this(windowId, world, pos, playerInventory, player, new Inventory(8), new IntArray(10));


    }

    public InductionFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IInventory furnaceInventoryIn, IIntArray furnaceData) {
        super(INDUCTION_FURNACE_CONTAINER, windowId);
        tileEntity = world.getTileEntity(pos);
        assertInventorySize(furnaceInventoryIn, 8);
        assertIntArraySize(furnaceData, 10);
        this.playerEntity = player;
        this.furnaceInventory = furnaceInventoryIn;
        this.data = furnaceData;
        this.playerInventory = new InvWrapper(playerInventory);

        this.addSlot(new Slot(furnaceInventory, 0, 33, 33));
        this.addSlot(new Slot(furnaceInventory, 1, 55, 33));
        this.addSlot(new Slot(furnaceInventory, 2, 77, 33));
        this.addSlot(new Slot(furnaceInventory, 3, 44, 55));
        this.addSlot(new Slot(furnaceInventory, 4, 67, 55));

        this.addSlot(new Slot(furnaceInventory, 5, 10, 53));
        this.addSlot(new Slot(furnaceInventory, 6, 134, 23));
        this.addSlot(new Slot(furnaceInventory, 7, 134, 47));


        layoutPlayerInventorySlots(8, 86);

        this.trackIntArray(furnaceData);
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnTime() {
        return this.data.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentBurnTime() {
        return this.data.get(1);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookTime() {
        return this.data.get(2);
    }

    @OnlyIn(Dist.CLIENT)
    public int getTotalCookTime() {
        return this.data.get(3);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isRecipeMode() {
        return this.data.get(9) == 1;
    }

    ;

    @OnlyIn(Dist.CLIENT)
    public String getPercentSlot1() {
        return Integer.toString(this.data.get(4));
    }

    @OnlyIn(Dist.CLIENT)
    public String getPercentSlot2() {
        return Integer.toString(this.data.get(5));
    }

    @OnlyIn(Dist.CLIENT)
    public String getPercentSlot3() {
        return Integer.toString(this.data.get(6));
    }

    @OnlyIn(Dist.CLIENT)
    public String getPercentSlot4() {
        return Integer.toString(this.data.get(7));
    }

    @OnlyIn(Dist.CLIENT)
    public String getPercentSlot5() {
        return Integer.toString(this.data.get(8));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, RankineBlocks.INDUCTION_FURNACE.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 7) {
                if (!this.mergeItemStack(stack, 8, 44, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else if (index != 0 && index != 1 && index != 2 && index != 3 && index != 4 && index != 5) {
                if (!AlloyRecipeHelper.getInstance().returnItemMaterial(stack).getKey().contains("none") && !AlloyRecipeHelper.getInstance().returnItemMaterial(stack).getKey().contains("nope")) {
                    if (!this.mergeItemStack(stack, 0, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (AbstractFurnaceTileEntity.isFuel(stack)) {
                    if (!this.mergeItemStack(stack, 5, 6, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() instanceof TripleAlloyTemplateItem) {
                    if (!this.mergeItemStack(stack, 6, 7, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 35) {
                    if (!this.mergeItemStack(stack, 35, 44, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 44 && !this.mergeItemStack(stack, 8, 44, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 8, 44, false)) {
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

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
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
    public int getBurnLeftScaled(int pixels) {
        int i = getCurrentBurnTime();
        if (i == 0) i = 200;
        return getBurnTime() * pixels / i;
    }


    @OnlyIn(Dist.CLIENT)
    public int getCookProgressScaled(int pixels) {
        int i = getCookTime();
        int j = getTotalCookTime();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() {
        return getBurnTime() > 0;
    }
}


