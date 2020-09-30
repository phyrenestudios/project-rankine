package com.cannolicatfish.rankine.blocks.templatetable;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.items.AlloyTemplate;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipesComplex;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Optional;

import static com.cannolicatfish.rankine.init.ModBlocks.TEMPLATE_TABLE_CONTAINER;
public class TemplateTableContainer extends Container {

    private IItemHandler playerInventory;
    private final CraftResultInventory resultInventory = new CraftResultInventory();
    public final IInventory inputInventory = new Inventory(7) {
        /**
         * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
         * it hasn't changed and skip it.
         */
        public void markDirty() {
            super.markDirty();
            TemplateTableContainer.this.onCraftMatrixChanged(this);
        }
    };
    public final IInventory outputInventory = new Inventory(1);
    private ItemStack recipeOutput;
    private PlayerEntity player;
    private final IWorldPosCallable worldPosCallable;

    public TemplateTableContainer(int id, PlayerInventory playerInventory, PlayerEntity player) {
        this(id,playerInventory,player, IWorldPosCallable.DUMMY);
    }

    public TemplateTableContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player, IWorldPosCallable wpos) {
        super(TEMPLATE_TABLE_CONTAINER,windowId);
        this.worldPosCallable = wpos;
        this.player = player;
        this.addSlot(new Slot(inputInventory,0,35,11));
        this.addSlot(new Slot(inputInventory,1,53,11));
        for (int i = 2; i < 7; i++)
        {
            this.addSlot(new Slot(inputInventory,i,8+18*(i - 2),46));
        }
        this.addSlot(new Slot(outputInventory,0,144,28) {
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            public ItemStack onTake(PlayerEntity player, ItemStack stack) {
                TemplateTableContainer.this.inputInventory.decrStackSize(0,1);
                TemplateTableContainer.this.inputInventory.decrStackSize(1,1);
                TemplateTableContainer.this.updateRecipeResultSlot();

                stack.getItem().onCreated(stack, player.world, player);
                worldPosCallable.consume((p_216954_1_, p_216954_2_) -> {

                });
                return super.onTake(player, stack);
            }
        });
        this.playerInventory = new InvWrapper(playerInventory);
        layoutPlayerInventorySlots(8, 70);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 7) {
                if (!this.mergeItemStack(stack, 8, 44, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else if (!(index < 7)) {
                if (!AlloyingRecipesComplex.getInstance().returnItemMaterial(stack).getKey().contains("none") && !AlloyingRecipesComplex.getInstance().returnItemMaterial(stack).getKey().contains("nope")) {
                    if (!this.mergeItemStack(stack, 2, 7, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.getItem() == Items.INK_SAC || itemstack.getItem() == Items.BLACK_DYE) {
                    if (!this.mergeItemStack(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.getItem() == Items.PAPER) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 35) {
                    if (!this.mergeItemStack(stack, 35, 44, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 44 && !this.mergeItemStack(stack, 8, 35, false)) {
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

    public void onCraftMatrixChanged(IInventory inventoryIn) {
        AbstractMap.SimpleEntry<ItemStack,int[]> recipeOutput = AlloyingRecipesComplex.getInstance().getAlloyResult(this.inputInventory.getStackInSlot(2),this.inputInventory.getStackInSlot(3),this.inputInventory.getStackInSlot(4));
        if (!recipeOutput.getKey().isEmpty() && this.inputInventory.getStackInSlot(0).getItem() == Items.PAPER &&
                (this.inputInventory.getStackInSlot(1).getItem() == Items.INK_SAC || this.inputInventory.getStackInSlot(1).getItem() == Items.BLACK_DYE))
        {
            ItemStack st = new ItemStack(ModItems.ALLOY_TEMPLATE);
            String temp = TemplateTableContainer.assembleTemplate(this.inputInventory.getStackInSlot(2),this.inputInventory.getStackInSlot(3),this.inputInventory.getStackInSlot(4));
            AlloyTemplate.addTemplate(st, temp,recipeOutput.getKey().getCount() + "x#" + recipeOutput.getKey().getTranslationKey(),
                    recipeOutput.getKey(), AlloyingRecipesComplex.getInstance().getComposition(this.inputInventory.getStackInSlot(2),this.inputInventory.getStackInSlot(3),this.inputInventory.getStackInSlot(4)),
                    this.inputInventory.getStackInSlot(2),this.inputInventory.getStackInSlot(3),this.inputInventory.getStackInSlot(4));

            this.outputInventory.setInventorySlotContents(0,st);

        } else if (!this.outputInventory.getStackInSlot(0).isEmpty())
        {
            this.outputInventory.setInventorySlotContents(0,ItemStack.EMPTY);
        }
        /*
        if (itemstack.getItem() != this.itemStackInput.getItem()) {
            this.itemStackInput = itemstack.copy();
            this.updateAvailableRecipes(inventoryIn, itemstack);
        }*/

    }

    private void updateRecipeResultSlot() {
        this.onCraftMatrixChanged(inputInventory);
        this.detectAndSendChanges();
    }

    public static String assembleTemplate(ItemStack... stacks)
    {
        PeriodicTableUtils utils = new PeriodicTableUtils();
        StringBuilder ret = new StringBuilder();
        for (ItemStack i : stacks)
        {
            if (i.isEmpty())
            {
                break;
            }
            if (!ret.toString().isEmpty())
            {
                ret.append("-");
            }
            AbstractMap.SimpleEntry<String,Integer> s = AlloyingRecipesComplex.getInstance().returnItemMaterial(i);
            ret.append(s.getValue()).append(utils.getElementByMaterial(s.getKey()));
        }
        return ret.toString();
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerIn, ModBlocks.TEMPLATE_TABLE);
    }

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.worldPosCallable.consume((p_217068_2_, p_217068_3_) -> {
            this.clearContainer(playerIn, p_217068_2_, this.inputInventory);
        });
    }
}
