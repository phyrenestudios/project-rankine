package com.cannolicatfish.rankine.blocks.crucible;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineMenus;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.CrucibleRecipe;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CrucibleContainer extends AbstractContainerMenu {
    private final Container furnaceInventory;
    private BlockEntity tileEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;
    private Level world;
    private final ContainerData data;

    public CrucibleContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        this(windowId,world,pos,playerInventory,player,new SimpleContainer(6),new SimpleContainerData(3));



    }
    public CrucibleContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, Container furnaceInventoryIn, ContainerData furnaceData) {
        super(RankineMenus.CRUCIBLE_CONTAINER.get(), windowId);
        tileEntity = world.getBlockEntity(pos);
        checkContainerSize(furnaceInventoryIn, 6);
        checkContainerDataCount(furnaceData, 3);
        this.playerEntity = player;
        this.data = furnaceData;
        this.furnaceInventory = furnaceInventoryIn;
        this.playerInventory = new InvWrapper(playerInventory);
        this.world = playerEntity.level;

        this.addSlot(new Slot(furnaceInventory, 0, 53, 23));
        this.addSlot(new Slot(furnaceInventory, 1, 71,16));
        this.addSlot(new Slot(furnaceInventory, 2, 88,16));
        this.addSlot(new Slot(furnaceInventory, 3, 106,23));
        this.addSlot(new Slot(furnaceInventory, 4, 79,53));
        this.addSlot(new Slot(furnaceInventory, 5, 49,53));

        layoutPlayerInventorySlots(8, 84);

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
        return stillValid(ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, RankineBlocks.CRUCIBLE_BLOCK.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 4 || index == 5) {
                if (!this.moveItemStackTo(stack, 6, 42, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else if (index > 5) {
                if (hasRecipe(stack)) {
                    if (!this.moveItemStackTo(stack, 0, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index < 33) {
                    if (!this.moveItemStackTo(stack, 33, 42, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 42 && !this.moveItemStackTo(stack, 6, 33, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.moveItemStackTo(stack, 6, 42, false)) {
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
        for (CrucibleRecipe recipe : this.world.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.CRUCIBLE.get())) {
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



}

