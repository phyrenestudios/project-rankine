package com.cannolicatfish.rankine.blocks.mtt;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineMenus;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MaterialTestingTableContainer extends AbstractContainerMenu {
    private static final PeriodicTableUtils utils = new PeriodicTableUtils();
    private IItemHandler playerInventory;
    private final Container inputInventory;
    private BlockEntity tileEntity;

    public MaterialTestingTableContainer(int id,  Level world, BlockPos pos, Inventory playerInventory, Player player) {
        this(id,world,pos,playerInventory,player, new SimpleContainer(14));
    }

    public MaterialTestingTableContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, Container inv) {
        super(RankineMenus.MATERIAL_TESTING_TABLE_CONTAINER.get(), windowId);
        tileEntity = world.getBlockEntity(pos);
        checkContainerSize(inv, 7);
        this.inputInventory = inv;
        this.addSlot(new Slot(inputInventory,0,8,10));

        this.addSlot(new Slot(inputInventory,1,202,144));

        this.addSlot(new Slot(inputInventory,2,233,11));
        this.addSlot(new Slot(inputInventory,3,233,29));
        this.addSlot(new Slot(inputInventory,4,233,47));
        this.addSlot(new Slot(inputInventory,5,233,65));
        this.addSlot(new Slot(inputInventory,6,233,83));
        this.addSlot(new Slot(inputInventory,7,233,101));
        this.addSlot(new Slot(inputInventory,8,233,119));
        this.addSlot(new Slot(inputInventory,9,233,137));
        this.addSlot(new Slot(inputInventory,10,233,155));
        this.addSlot(new Slot(inputInventory,11,233,173));
        this.addSlot(new Slot(inputInventory,12,233,191));
        this.addSlot(new Slot(inputInventory,13,233,209));
        this.playerInventory = new InvWrapper(playerInventory);
        layoutPlayerInventorySlots(36, 174);
    }


    @Override
    public ItemStack quickMoveStack(Player playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem())
        {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (!(index < 14)) {
                if (AlloyCustomHelper.hasElement(stack.getItem()) || stack.getItem() instanceof IAlloyItem) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_DURABILITY_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 2, 3, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_MINING_SPEED_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 3, 4, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_HARVEST_LEVEL_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 4, 5, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_ENCHANTABILITY_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 5, 6, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_ATTACK_DAMAGE_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 6, 7, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_ATTACK_SPEED_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 7, 8, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_CORROSION_RESISTANCE_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 8, 9, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_HEAT_RESISTANCE_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 9, 10, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_KNOCKBACK_RESISTANCE_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 10, 11, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_TOUGHNESS_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 11, 12, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_ENCHANTMENT_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 12, 13, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_EXAM_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 13, 14, false))
                    {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(RankineTags.Items.MTT_TOOLS)) {
                    if (!this.moveItemStackTo(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 41) {
                    if (!this.moveItemStackTo(stack, 41, 50, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 50 && !this.moveItemStackTo(stack, 14, 41, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 14, 50, false)) {
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

    public ElementRecipe getElementRecipeForSlotItem(Level worldIn) {
        ItemStack stack = this.inputInventory.getItem(0);

        if (utils.hasElementRecipe(stack, worldIn))
        {
            return utils.getElementRecipe(stack, worldIn);
        } else {
            return null;
        }
    }

    public AlloyingRecipe getAlloyRecipeForSlotItem(Level worldIn) {
        ItemStack stack = this.inputInventory.getItem(0);
        return utils.getAlloyRecipe(stack,worldIn);
    }

    public ItemStack getSlotItem(Level worldIn) {
        return this.inputInventory.getItem(0);
    }

    public int getToolItem(Level worldIn) {
        ItemStack stack = this.inputInventory.getItem(1);
        if (stack.is(RankineTags.Items.MTT_TOOLS)) {
            if (stack.is(RankineTags.Items.MTT_DURABILITY_TOOLS)) {
                return 0;
            } else if (stack.is(RankineTags.Items.MTT_MINING_SPEED_TOOLS)) {
                return 1;
            } else if (stack.is(RankineTags.Items.MTT_HARVEST_LEVEL_TOOLS)) {
                return 2;
            } else if (stack.is(RankineTags.Items.MTT_ENCHANTABILITY_TOOLS)) {
                return 3;
            } else if (stack.is(RankineTags.Items.MTT_ATTACK_DAMAGE_TOOLS)) {
                return 4;
            } else if (stack.is(RankineTags.Items.MTT_ATTACK_SPEED_TOOLS)) {
                return 5;
            } else if (stack.is(RankineTags.Items.MTT_CORROSION_RESISTANCE_TOOLS)) {
                return 6;
            } else if (stack.is(RankineTags.Items.MTT_HEAT_RESISTANCE_TOOLS)) {
                return 7;
            } else if (stack.is(RankineTags.Items.MTT_KNOCKBACK_RESISTANCE_TOOLS)) {
                return 8;
            } else if (stack.is(RankineTags.Items.MTT_TOUGHNESS_TOOLS)) {
                return 9;
            } else if (stack.is(RankineTags.Items.MTT_ENCHANTMENT_TOOLS)) {
                return 10;
            } else if (stack.is(RankineTags.Items.MTT_EXAM_TOOLS)) {
                return 11;
            }
        }
        return -1;
    }




    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerIn, RankineBlocks.MATERIAL_TESTING_TABLE.get());
    }

}
