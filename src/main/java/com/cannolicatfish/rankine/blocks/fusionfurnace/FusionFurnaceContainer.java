package com.cannolicatfish.rankine.blocks.fusionfurnace;

import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.init.packets.FluidStackPacket;
import com.cannolicatfish.rankine.init.packets.RankinePacketHandler;
import com.cannolicatfish.rankine.items.BatteryItem;
import com.cannolicatfish.rankine.items.GasBottleItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.cannolicatfish.rankine.init.RankineBlocks.FUSION_FURNACE_CONTAINER;

public class FusionFurnaceContainer extends AbstractContainerMenu {
    private final Container furnaceInventory;
    private BlockEntity tileEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;
    private final ContainerData data;

    public FusionFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        this(windowId,world,pos,playerInventory,player,new SimpleContainer(7),new SimpleContainerData(4));



    }
    public FusionFurnaceContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, Container furnaceInventoryIn, ContainerData furnaceData) {
        super(FUSION_FURNACE_CONTAINER, windowId);
        tileEntity = world.getBlockEntity(pos);
        checkContainerSize(furnaceInventoryIn, 7);
        checkContainerDataCount(furnaceData, 4);
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

        this.addDataSlots(furnaceData);
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
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, RankineBlocks.FUSION_FURNACE.get());
    }

    public String getInputTankInfo() {
        FusionFurnaceTile fusionFurnaceTile = (FusionFurnaceTile) tileEntity;
        return fusionFurnaceTile.inputTank.getFluid().getFluid().getRegistryName() + " : " + fusionFurnaceTile.inputTank.getFluid().getAmount();
    }

    public FluidTank getInputTank() {
        FusionFurnaceTile fusionFurnaceTile = (FusionFurnaceTile) tileEntity;
        return fusionFurnaceTile.inputTank;
    }

    public String getOutputTankInfo() {
        FusionFurnaceTile fusionFurnaceTile = (FusionFurnaceTile) tileEntity;
        return fusionFurnaceTile.outputTank.getFluid().getFluid().getRegistryName() + " : " + fusionFurnaceTile.outputTank.getFluid().getAmount();
    }

    public FluidTank getOutputTank() {
        FusionFurnaceTile fusionFurnaceTile = (FusionFurnaceTile) tileEntity;
        return fusionFurnaceTile.outputTank;
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        FusionFurnaceTile fusionFurnaceTile = (FusionFurnaceTile) tileEntity;
        RankinePacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(),new FluidStackPacket(fusionFurnaceTile.inputTank.getFluid(),tileEntity.getBlockPos(),true));
        RankinePacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(),new FluidStackPacket(fusionFurnaceTile.outputTank.getFluid(),tileEntity.getBlockPos(), playerEntity.canBeCollidedWith()));
    }

    //TODO Rewrite this
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem())
        {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 5) {
                if (!this.moveItemStackTo(stack, 7, 43, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else if (index > 6) {
                if (!(stack.getItem() instanceof BatteryItem || stack.getItem() instanceof GasBottleItem || stack.getItem() instanceof BottleItem)) {
                    if (!this.moveItemStackTo(stack, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() instanceof BatteryItem) {
                    if (!this.moveItemStackTo(stack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if ((stack.getItem() instanceof GasBottleItem || stack.getItem() instanceof BottleItem)) {
                    if (!this.moveItemStackTo(stack, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 34) {
                    if (!this.moveItemStackTo(stack, 34, 43, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 43 && !this.moveItemStackTo(stack, 7, 34, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 7, 43, false)) {
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
