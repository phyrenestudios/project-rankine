package com.cannolicatfish.rankine.blocks.mixingbarrel;

import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceTile;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.packets.FluidStackPacket;
import com.cannolicatfish.rankine.init.packets.RankinePacketHandler;
import com.cannolicatfish.rankine.recipe.MixingRecipe;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.cannolicatfish.rankine.init.RankineBlocks.MIXING_BARREL_CONTAINER;

public class MixingBarrelContainer extends AbstractContainerMenu {
    private final Container furnaceInventory;
    private BlockEntity tileEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;
    private Level world;
    private final ContainerData data;

    public MixingBarrelContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        this(windowId,world,pos,playerInventory,player,new SimpleContainer(5),new SimpleContainerData(4));



    }
    public MixingBarrelContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, Container furnaceInventoryIn, ContainerData furnaceData) {
        super(MIXING_BARREL_CONTAINER, windowId);
        tileEntity = world.getBlockEntity(pos);
        checkContainerSize(furnaceInventoryIn, 5);
        checkContainerDataCount(furnaceData, 4);
        this.playerEntity = player;
        this.data = furnaceData;
        this.furnaceInventory = furnaceInventoryIn;
        this.playerInventory = new InvWrapper(playerInventory);
        this.world = playerEntity.level;

        this.addSlot(new Slot(furnaceInventory, 0, 40, 16));
        this.addSlot(new Slot(furnaceInventory, 1, 66,16));
        this.addSlot(new Slot(furnaceInventory, 2, 92,16));
        this.addSlot(new Slot(furnaceInventory, 3, 118,16));
        this.addSlot(new Slot(furnaceInventory, 4, 79,53));

        layoutPlayerInventorySlots(10, 86);

        this.addDataSlots(furnaceData);
    }


    @OnlyIn(Dist.CLIENT)
    public int getCookTime(){
        return this.data.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public int getTotalCookTime(){
        return this.data.get(1);
    }

    @OnlyIn(Dist.CLIENT)
    public int getHeatStatus(){
        return this.data.get(2);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, RankineBlocks.MIXING_BARREL.get());
    }

    public FluidTank getInputTank() {
        MixingBarrelTile mixingBarrelTile = (MixingBarrelTile) tileEntity;
        return mixingBarrelTile.getInputTank();
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        MixingBarrelTile mixingBarrelTile = (MixingBarrelTile) tileEntity;
        RankinePacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(),new FluidStackPacket(mixingBarrelTile.getInputTank().getFluid(),tileEntity.getBlockPos(),true));
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 4) {
                if (!this.moveItemStackTo(stack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else if (index > 4) {
                if (!this.moveItemStackTo(stack, 0, 3, false)) {
                    return ItemStack.EMPTY;
                }
                else if (index < 32) {
                    if (!this.moveItemStackTo(stack, 32, 41, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 41 && !this.moveItemStackTo(stack, 6, 32, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.moveItemStackTo(stack, 5, 41, false)) {
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

    protected boolean hasRecipe(ItemStack stack) {
        for (MixingRecipe recipe : this.world.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.MIXING)) {
            for (Ingredient i : recipe.getIngredients()) {
                if (i.test(stack)) {
                    return true;
                }
            }
        }
        return false;
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
        return 4;
    }


    @OnlyIn(Dist.CLIENT)
    public int getCookProgressScaled(int pixels)
    {
        int i = getCookTime();
        int j = getTotalCookTime();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    public String[] getSlotPercentages() {
        int slot1 = furnaceInventory.getItem(0).getCount();
        int slot2 = furnaceInventory.getItem(1).getCount();
        int slot3 = furnaceInventory.getItem(2).getCount();
        int slot4 = furnaceInventory.getItem(3).getCount();
        float sum = slot1 + slot2 + slot3 + slot4;
        return new String[]{Math.round(slot1/sum * 100f) + "%", Math.round(slot2/sum * 100f)+ "%", Math.round(slot3/sum * 100f)+ "%", Math.round(slot4/sum * 100f)+ "%"};

    }


}

