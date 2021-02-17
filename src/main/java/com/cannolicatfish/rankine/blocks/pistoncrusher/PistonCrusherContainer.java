package com.cannolicatfish.rankine.blocks.pistoncrusher;


import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.PistonCrusherRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
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

import static com.cannolicatfish.rankine.init.RankineBlocks.PISTON_CRUSHER_CONTAINER;

public class PistonCrusherContainer extends Container {
    private TileEntity tileEntity;
    private final IInventory furnaceInventory;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private final IIntArray data;
    protected final World world;

    public PistonCrusherContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        this(windowId,world,pos,playerInventory,player, new Inventory(5),new IntArray(4));
    }

    public PistonCrusherContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IInventory furnaceInventoryIn,  IIntArray furnaceData) {
        super(PISTON_CRUSHER_CONTAINER, windowId);
        tileEntity = world.getTileEntity(pos);
        assertInventorySize(furnaceInventoryIn, 5);
        assertIntArraySize(furnaceData, 4);
        this.data = furnaceData;
        this.playerEntity = player;
        this.furnaceInventory = furnaceInventoryIn;
        this.playerInventory = new InvWrapper(playerInventory);
        this.world = playerEntity.world;

        this.addSlot(new Slot(furnaceInventory, 0, 56, 31));
        this.addSlot(new Slot(furnaceInventory, 1, 10,37));
        this.addSlot(new Slot(furnaceInventory, 2, 124,17));
        this.addSlot(new Slot(furnaceInventory, 3, 149,17));
        this.addSlot(new Slot(furnaceInventory, 4, 136,42));

        layoutPlayerInventorySlots(10, 70);

        this.trackIntArray(furnaceData);

    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, RankineBlocks.PISTON_CRUSHER.get());
    }

    protected boolean hasRecipe(ItemStack stack) {
        return this.world.getRecipeManager().getRecipe(RankineRecipeTypes.CRUSHING, new Inventory(stack), this.world).isPresent();
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 2 || index == 3 || index == 4) {
                if (!this.mergeItemStack(stack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else if (index != 1 && index != 0) {
                if (hasRecipe(stack)) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (AbstractFurnaceTileEntity.isFuel(stack)) {
                    if (!this.mergeItemStack(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 32) {
                    if (!this.mergeItemStack(stack, 32, 41, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 41 && !this.mergeItemStack(stack, 5, 32, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 5, 41, false)) {
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