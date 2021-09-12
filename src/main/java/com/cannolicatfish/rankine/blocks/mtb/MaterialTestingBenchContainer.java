package com.cannolicatfish.rankine.blocks.mtb;

import com.cannolicatfish.rankine.blocks.mtb.MaterialTestingBenchContainer;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.items.AlloyTemplateItem;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.INBT;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.AbstractMap;

import static com.cannolicatfish.rankine.init.RankineBlocks.MATERIAL_TESTING_BENCH_CONTAINER;

public class MaterialTestingBenchContainer extends Container {
    private static final PeriodicTableUtils utils = new PeriodicTableUtils();
    private IItemHandler playerInventory;
    private World world;
    public final IInventory inputInventory = new Inventory(3) {
        public void markDirty() {
            super.markDirty();
            MaterialTestingBenchContainer.this.onCraftMatrixChanged(this);
        }
    };
    public final IInventory outputInventory = new Inventory(1);
    private ItemStack recipeOutput;
    private PlayerEntity player;
    private final IWorldPosCallable worldPosCallable;

    public MaterialTestingBenchContainer(int id, PlayerInventory playerInventory, PlayerEntity player) {
        this(id,playerInventory,player, IWorldPosCallable.DUMMY);
    }

    public MaterialTestingBenchContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player, IWorldPosCallable wpos) {
        super(MATERIAL_TESTING_BENCH_CONTAINER,windowId);
        this.worldPosCallable = wpos;
        this.player = player;
        this.world = player.world;
        this.addSlot(new Slot(inputInventory,0,8,10));

        this.addSlot(new Slot(inputInventory,1,202,74));
        this.addSlot(new Slot(inputInventory,2,202,92));
        this.addSlot(new Slot(outputInventory,0,202,144) {
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            public ItemStack onTake(PlayerEntity player, ItemStack stack) {
                MaterialTestingBenchContainer.this.inputInventory.decrStackSize(1,1);
                MaterialTestingBenchContainer.this.inputInventory.decrStackSize(2,1);
                MaterialTestingBenchContainer.this.updateRecipeResultSlot();

                stack.getItem().onCreated(stack, player.world, player);
                worldPosCallable.consume((p_216954_1_, p_216954_2_) -> {

                });
                return super.onTake(player, stack);
            }
        });
        this.playerInventory = new InvWrapper(playerInventory);
        layoutPlayerInventorySlots(36, 174);
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
            if (index == 9) {
                if (!this.mergeItemStack(stack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else if (!(index < 8)) {
                if (PeriodicTableUtils.getInstance().hasElement(stack.getItem())) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.getItem() == Items.PAPER) {
                    if (!this.mergeItemStack(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.getItem() instanceof DyeItem) {
                    if (!this.mergeItemStack(stack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 31) {
                    if (!this.mergeItemStack(stack, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 40 && !this.mergeItemStack(stack, 5, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 4, 40, false)) {
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

    public ElementRecipe getSlotItem(World worldIn) {
        ItemStack stack = this.inputInventory.getStackInSlot(0);

        if (utils.hasElementRecipe(stack, worldIn))
        {
            return utils.getElementRecipe(stack, worldIn);
        } else {
            return null;
        }
    }

    public void onCraftMatrixChanged(IInventory inventoryIn) {

        if (this.inputInventory.getStackInSlot(1).getItem() == Items.PAPER &&
                (this.inputInventory.getStackInSlot(2).getItem() instanceof DyeItem))
        {
            ElementRecipe recipeIn = this.world.getRecipeManager().getRecipe(RankineRecipeTypes.ELEMENT, this.inputInventory, this.world).orElse(null);
            //calcPercentages();
            if (recipeIn != null) {
                /*ItemStack recipeOutput = recipeIn.generateResult(this.world,this.inputInventory, 3);
                if (!recipeOutput.isEmpty()) {
                    ItemStack st = new ItemStack(RankineItems.ALLOY_TEMPLATE.get());
                    AlloyTemplateItem.addTemplate(world,st, recipeIn, this.inputInventory, (DyeItem) this.inputInventory.getStackInSlot(7).getItem());

                    this.outputInventory.setInventorySlotContents(0, st);
                    return;

                }*/
            }

        }
        if (!this.outputInventory.getStackInSlot(0).isEmpty()) {
            this.outputInventory.setInventorySlotContents(0,ItemStack.EMPTY);
        }
    }


    private void updateRecipeResultSlot() {
        this.onCraftMatrixChanged(inputInventory);
        this.detectAndSendChanges();
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerIn, RankineBlocks.MATERIAL_TESTING_BENCH.get());
    }

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.worldPosCallable.consume((p_217068_2_, p_217068_3_) -> {
            this.clearContainer(playerIn, p_217068_2_, this.inputInventory);
        });
    }
}
