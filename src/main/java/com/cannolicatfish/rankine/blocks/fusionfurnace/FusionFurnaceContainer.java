package com.cannolicatfish.rankine.blocks.fusionfurnace;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.items.AlloyTemplateItemOld;
import com.cannolicatfish.rankine.items.BatteryItem;
import com.cannolicatfish.rankine.items.GasBottleItem;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.AbstractMap;

import static com.cannolicatfish.rankine.init.RankineBlocks.FUSION_FURNACE_CONTAINER;

public class FusionFurnaceContainer extends Container {
    private final IInventory furnaceInventory;
    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private final IIntArray data;

    public FusionFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        this(windowId,world,pos,playerInventory,player,new Inventory(7),new IntArray(4));



    }
    public FusionFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IInventory furnaceInventoryIn, IIntArray furnaceData) {
        super(FUSION_FURNACE_CONTAINER, windowId);
        tileEntity = world.getTileEntity(pos);
        assertInventorySize(furnaceInventoryIn, 7);
        assertIntArraySize(furnaceData, 4);
        this.playerEntity = player;
        this.furnaceInventory = furnaceInventoryIn;
        this.data = furnaceData;
        this.playerInventory = new InvWrapper(playerInventory);

        this.addSlot(new Slot(furnaceInventory, 0, 46, 17));
        this.addSlot(new Slot(furnaceInventory, 1, 64, 17));
        this.addSlot(new Slot(furnaceInventory, 2, 55,53));
        this.addSlot(new Slot(furnaceInventory, 3, 22, 35));
        this.addSlot(new Slot(furnaceInventory, 4, 110,24));
        this.addSlot(new Slot(furnaceInventory, 5, 110,50));
        this.addSlot(new Slot(furnaceInventory, 6, 138,35));


        layoutPlayerInventorySlots(8, 84);

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
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, RankineBlocks.FUSION_FURNACE.get());
    }

    public String getInputTankInfo() {
        FusionFurnaceTile fusionFurnaceTile = (FusionFurnaceTile) tileEntity;
        return fusionFurnaceTile.inputTank.getFluid().getFluid().getRegistryName() + " : " + fusionFurnaceTile.inputTank.getFluid().getAmount();
    }

    public FluidStack getInputTank() {
        FusionFurnaceTile fusionFurnaceTile = (FusionFurnaceTile) tileEntity;
        return fusionFurnaceTile.inputTank.getFluid();
    }

    public String getOutputTankInfo() {
        FusionFurnaceTile fusionFurnaceTile = (FusionFurnaceTile) tileEntity;
        return fusionFurnaceTile.outputTank.getFluid().getFluid().getRegistryName() + " : " + fusionFurnaceTile.outputTank.getFluid().getAmount();
    }

    //TODO Rewrite this
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 5) {
                if (!this.mergeItemStack(stack, 7, 43, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else if (index > 6) {
                if (!(stack.getItem() instanceof BatteryItem || stack.getItem() instanceof GasBottleItem || stack.getItem() instanceof GlassBottleItem)) {
                    if (!this.mergeItemStack(stack, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() instanceof BatteryItem) {
                    if (!this.mergeItemStack(stack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if ((stack.getItem() instanceof GasBottleItem || stack.getItem() instanceof GlassBottleItem)) {
                    if (!this.mergeItemStack(stack, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 34) {
                    if (!this.mergeItemStack(stack, 34, 43, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 43 && !this.mergeItemStack(stack, 7, 34, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 7, 43, false)) {
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
