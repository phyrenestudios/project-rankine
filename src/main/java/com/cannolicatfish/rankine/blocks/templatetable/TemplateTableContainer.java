package com.cannolicatfish.rankine.blocks.templatetable;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.items.AlloyTemplateItem;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
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

import static com.cannolicatfish.rankine.init.RankineBlocks.TEMPLATE_TABLE_CONTAINER;
public class TemplateTableContainer extends Container {

    private IItemHandler playerInventory;
    private World world;
    public final IInventory inputInventory = new Inventory(8) {
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
        this.world = player.world;
        this.addSlot(new Slot(inputInventory,0,8,54));
        this.addSlot(new Slot(inputInventory,1,8,73));
        this.addSlot(new Slot(inputInventory,2,26,54));
        this.addSlot(new Slot(inputInventory,3,26,73));
        this.addSlot(new Slot(inputInventory,4,44,54));
        this.addSlot(new Slot(inputInventory,5,44,73));
        this.addSlot(new Slot(inputInventory,6,17,19));
        this.addSlot(new Slot(inputInventory,7,35,19));
        this.addSlot(new Slot(outputInventory,0,144,36) {
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            public ItemStack onTake(PlayerEntity player, ItemStack stack) {
                TemplateTableContainer.this.inputInventory.decrStackSize(6,1);
                TemplateTableContainer.this.inputInventory.decrStackSize(7,1);
                TemplateTableContainer.this.updateRecipeResultSlot();

                stack.getItem().onCreated(stack, player.world, player);
                worldPosCallable.consume((p_216954_1_, p_216954_2_) -> {

                });
                return super.onTake(player, stack);
            }
        });
        this.playerInventory = new InvWrapper(playerInventory);
        layoutPlayerInventorySlots(8, 102);
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
                    if (!this.mergeItemStack(stack, 0, 6, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.getItem() == Items.PAPER) {
                    if (!this.mergeItemStack(stack, 6, 7, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.getItem() instanceof DyeItem) {
                    if (!this.mergeItemStack(stack, 7, 8, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 36) {
                    if (!this.mergeItemStack(stack, 36, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 45 && !this.mergeItemStack(stack, 10, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 9, 45, false)) {
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
    public AbstractMap.SimpleEntry<String[],Integer> getOutputString() {
        AlloyingRecipe recipe = world.getRecipeManager().getRecipe(RankineRecipeTypes.ALLOYING, this.inputInventory, world).orElse(null);
        if (recipe != null) {
            ItemStack stack = recipe.generateResult(this.inputInventory,3);
            INBT nbt = AlloyItem.getComposition(stack).getCompound(0).get("comp");
            if (nbt != null){
                return new AbstractMap.SimpleEntry<>(new String[]{new TranslationTextComponent(stack.getItem().getTranslationKey()).getString(),nbt.getString()},0x55FF55);
            } else {
                return new AbstractMap.SimpleEntry<>(new String[]{},0xffffff);
            }
        } else {
            String ret = RankineRecipes.generateAlloyString(this.inputInventory);
            return new AbstractMap.SimpleEntry<>(new String[]{"None", ret},0xFF5555);
        }
    }

    public void onCraftMatrixChanged(IInventory inventoryIn) {

        if (this.inputInventory.getStackInSlot(6).getItem() == Items.PAPER &&
                (this.inputInventory.getStackInSlot(7).getItem() instanceof DyeItem))
        {
            AlloyingRecipe recipeIn = this.world.getRecipeManager().getRecipe(RankineRecipeTypes.ALLOYING, this.inputInventory, this.world).orElse(null);
            //calcPercentages();
            if (recipeIn != null) {
                ItemStack recipeOutput = recipeIn.generateResult(this.inputInventory, 3);
                if (!recipeOutput.isEmpty()) {
                    ItemStack st = new ItemStack(RankineItems.ALLOY_TEMPLATE.get());
                    AlloyTemplateItem.addTemplate(st, recipeIn, this.inputInventory, (DyeItem) this.inputInventory.getStackInSlot(7).getItem());

                    this.outputInventory.setInventorySlotContents(0, st);
                    return;

                }
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
        return isWithinUsableDistance(this.worldPosCallable, playerIn, RankineBlocks.TEMPLATE_TABLE.get());
    }

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.worldPosCallable.consume((p_217068_2_, p_217068_3_) -> {
            this.clearContainer(playerIn, p_217068_2_, this.inputInventory);
        });
    }
}
