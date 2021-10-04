package com.cannolicatfish.rankine.blocks.mtt;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
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
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.cannolicatfish.rankine.init.RankineBlocks.MATERIAL_TESTING_TABLE_CONTAINER;

public class MaterialTestingTableContainer extends Container {
    private static final PeriodicTableUtils utils = new PeriodicTableUtils();
    private IItemHandler playerInventory;
    private World world;
    public final IInventory inputInventory = new Inventory(3) {
        public void markDirty() {
            super.markDirty();
            MaterialTestingTableContainer.this.onCraftMatrixChanged(this);
        }
    };
    public final IInventory outputInventory = new Inventory(1);
    private ItemStack recipeOutput;
    private PlayerEntity player;
    private final IWorldPosCallable worldPosCallable;

    public MaterialTestingTableContainer(int id, PlayerInventory playerInventory, PlayerEntity player) {
        this(id,playerInventory,player, IWorldPosCallable.DUMMY);
    }

    public MaterialTestingTableContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player, IWorldPosCallable wpos) {
        super(MATERIAL_TESTING_TABLE_CONTAINER,windowId);
        this.worldPosCallable = wpos;
        this.player = player;
        this.world = player.world;
        this.addSlot(new Slot(inputInventory,0,8,10));

        this.addSlot(new Slot(inputInventory,1,202,144));
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
            if (!(index < 2)) {
                if (PeriodicTableUtils.getInstance().hasElement(stack.getItem())) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.getItem().isIn(RankineTags.Items.MTT_TOOLS)) {
                    if (!this.mergeItemStack(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.getItem() instanceof DyeItem) {
                    if (!this.mergeItemStack(stack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 29) {
                    if (!this.mergeItemStack(stack, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 38 && !this.mergeItemStack(stack, 2, 29, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 2, 38, false)) {
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

    public int getToolItem(World worldIn) {
        ItemStack stack = this.inputInventory.getStackInSlot(1);
        if (stack.getItem().isIn(RankineTags.Items.MTT_TOOLS)) {
            if (stack.getItem().isIn(RankineTags.Items.MTT_DURABILITY_TOOLS)) {
                return 0;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_MINING_SPEED_TOOLS)) {
                return 1;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_HARVEST_LEVEL_TOOLS)) {
                return 2;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_ENCHANTABILITY_TOOLS)) {
                return 3;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_ATTACK_DAMAGE_TOOLS)) {
                return 4;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_ATTACK_SPEED_TOOLS)) {
                return 5;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_CORROSION_RESISTANCE_TOOLS)) {
                return 6;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_HEAT_RESISTANCE_TOOLS)) {
                return 7;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_KNOCKBACK_RESISTANCE_TOOLS)) {
                return 8;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_TOUGHNESS_TOOLS)) {
                return 9;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_ENCHANTMENT_TOOLS)) {
                return 10;
            } else if (stack.getItem().isIn(RankineTags.Items.MTT_EXAM_TOOLS)) {
                return 11;
            }
        }
        return -1;
    }




    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerIn, RankineBlocks.MATERIAL_TESTING_TABLE.get());
    }

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.worldPosCallable.consume((p_217068_2_, p_217068_3_) -> {
            this.clearContainer(playerIn, p_217068_2_, this.inputInventory);
        });
    }
}
