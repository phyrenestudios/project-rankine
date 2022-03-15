package com.cannolicatfish.rankine.blocks.alloyfurnace;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.items.AlloyTemplateItem;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.AbstractMap;
import java.util.Map;

import static com.cannolicatfish.rankine.init.RankineBlocks.ALLOY_FURNACE_CONTAINER;

public class AlloyFurnaceContainer extends Container {
    private final IInventory furnaceInventory;
    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private final IIntArray data;

    public AlloyFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        this(windowId,world,pos,playerInventory,player,new Inventory(9),new IntArray(5));



    }
    public AlloyFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IInventory furnaceInventoryIn,  IIntArray furnaceData) {
        super(ALLOY_FURNACE_CONTAINER, windowId);
        tileEntity = world.getBlockEntity(pos);
        checkContainerSize(furnaceInventoryIn, 9);
        checkContainerDataCount(furnaceData, 5);
        this.playerEntity = player;
        this.furnaceInventory = furnaceInventoryIn;
        this.data = furnaceData;
        this.playerInventory = new InvWrapper(playerInventory);

        this.addSlot(new Slot(furnaceInventory, 0, 33, 23));
        this.addSlot(new Slot(furnaceInventory, 1, 33, 41));
        this.addSlot(new Slot(furnaceInventory, 2, 51,23));
        this.addSlot(new Slot(furnaceInventory, 3, 51, 41));
        this.addSlot(new Slot(furnaceInventory, 4, 69,23));
        this.addSlot(new Slot(furnaceInventory, 5, 69, 41));
        this.addSlot(new Slot(furnaceInventory, 6, 10,37));
        this.addSlot(new Slot(furnaceInventory, 7, 134,7));
        this.addSlot(new Slot(furnaceInventory, 8, 134,31));


        layoutPlayerInventorySlots(8, 86);

        this.addDataSlots(furnaceData);
    }

    @OnlyIn(Dist.CLIENT)
    public AbstractMap.SimpleEntry<String[],Integer> getOutputString() {
        if (furnaceInventory.getItem(7).getItem() instanceof AlloyTemplateItem) {
            ItemStack template = furnaceInventory.getItem(7);
            boolean correctInputs = (AlloyTemplateItem.getAlloyTier(template) & 1) == 1;
            if (correctInputs) {
                Map<ElementRecipe,Integer> map = AlloyTemplateItem.getElementList(this.playerEntity.level,template);
                for (Map.Entry<ElementRecipe,Integer> entry : map.entrySet())
                {
                    if (countMaterial(furnaceInventory,entry.getKey()) < entry.getValue()) {
                        correctInputs = false;
                    }
                }
            }
            return new AbstractMap.SimpleEntry<>(new String[]{template.getItem().getName(template).getString(),
                    AlloyTemplateItem.getAlloyComp(template)},
                    correctInputs ? 0x55FF55 : 0xFF5555);
        }
        String ret = RankineRecipes.generateAlloyString(furnaceInventory);
        return new AbstractMap.SimpleEntry<>(ret.isEmpty() ? new String[]{""} : new String[]{"",ret},0xFFFFFF);
    }

    private int countMaterial(IInventory inv, ElementRecipe element) {
        int i = 0;

        for(int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack itemstack = inv.getItem(j);
            int co = element.getMaterialCount(itemstack.getItem());
            if (co > 0) {
                i += co * itemstack.getCount();
            }
        }

        return i;
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
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, RankineBlocks.ALLOY_FURNACE.get());
    }

    //TO-DO: REDO +3
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem())
        {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 8) {
                if (!this.moveItemStackTo(stack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else if (index > 8) {
                if (AlloyCustomHelper.hasElement(itemstack.getItem())) {
                    if (!this.moveItemStackTo(stack, 0, 6, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (AbstractFurnaceTileEntity.isFuel(stack)) {
                    if (!this.moveItemStackTo(stack, 6, 7, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() == RankineItems.ALLOY_TEMPLATE.get()) {
                    if (!this.moveItemStackTo(stack, 7, 8, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 36) {
                    if (!this.moveItemStackTo(stack, 36, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 45 && !this.moveItemStackTo(stack, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 9, 45, false)) {
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
