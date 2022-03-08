package com.cannolicatfish.rankine.blocks.alloyfurnace;

import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableContainer;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.AlloyTemplateItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;

import java.util.*;

import static com.cannolicatfish.rankine.init.RankineBlocks.ALLOY_FURNACE_TILE;

public class AlloyFurnaceTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    private static final int[] SLOTS_UP = new int[]{6,7};
    private static final int[] SLOTS_EAST = new int[]{0,1};
    private static final int[] SLOTS_WEST = new int[]{2,3};
    private static final int[] SLOTS_BACK = new int[]{4,5};
    private static final int[] SLOTS_DOWN = new int[]{8};
    public AlloyFurnaceTile() {
        super(ALLOY_FURNACE_TILE);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private boolean recipeMode = false;
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 800;
    private final IIntArray furnaceData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return AlloyFurnaceTile.this.burnTime;
                case 1:
                    return AlloyFurnaceTile.this.currentBurnTime;
                case 2:
                    return AlloyFurnaceTile.this.cookTime;
                case 3:
                    return AlloyFurnaceTile.this.cookTimeTotal;
                case 4:
                    return AlloyFurnaceTile.this.recipeMode ? 1 : 0;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    AlloyFurnaceTile.this.burnTime = value;
                    break;
                case 1:
                    AlloyFurnaceTile.this.currentBurnTime = value;
                    break;
                case 2:
                    AlloyFurnaceTile.this.cookTime = value;
                    break;
                case 3:
                    AlloyFurnaceTile.this.cookTimeTotal = value;
                    break;
                case 4:
                    AlloyFurnaceTile.this.recipeMode = value == 1;
                    break;
            }
        }
        public int size() {
            return 5;
        }
    };

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt,this.items);
        this.burnTime = nbt.getInt("BurnTime");
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        this.currentBurnTime = ForgeHooks.getBurnTime(this.items.get(6));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        ItemStackHelper.saveAllItems(compound, this.items);

        return compound;
    }

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.world.isRemote) {
            ItemStack[] inputs = new ItemStack[]{this.items.get(0), this.items.get(1), this.items.get(2),this.items.get(3),this.items.get(4),this.items.get(5)};
            ItemStack fuel = this.items.get(6);
            if ((this.isBurning() || !fuel.isEmpty() && !Arrays.stream(inputs).allMatch(ItemStack::isEmpty))) {
                AlloyingRecipe irecipe = this.world.getRecipeManager().getRecipe(RankineRecipeTypes.ALLOYING, this, this.world).orElse(null);
                if (!this.isBurning() && this.canSmelt(irecipe,this)) {
                    this.burnTime = ForgeHooks.getBurnTime(fuel);
                    this.currentBurnTime = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (fuel.hasContainerItem())
                            this.items.set(6, fuel.getContainerItem());
                        else
                        if (!fuel.isEmpty()) {
                            Item item = fuel.getItem();
                            fuel.shrink(1);
                            if (fuel.isEmpty()) {
                                this.items.set(6, fuel.getContainerItem());
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(irecipe,this)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        int[] x;
                        ItemStack output;

                        ItemStack smelting = ItemStack.EMPTY;
                        if (recipeMode)
                        {
                            ItemStack template = this.getStackInSlot(7);
                            output = AlloyTemplateItem.getResult(world,template).copy();

                            for (Map.Entry<Ingredient,Short> input : AlloyTemplateItem.getInputStacks(template).entrySet())
                            {
                                List<ItemStack> addIt = new ArrayList<>();
                                int count = input.getValue();
                                for (int i = 0; i < 6; i++) {
                                    if (input.getKey().test(inputs[i])) {
                                        addIt.add(inputs[i]);
                                    }
                                }



                                for (ItemStack s : addIt)
                                {
                                    int shramt = count - s.getCount();
                                    if (shramt > 0) {
                                        count = shramt;
                                        s.setCount(0);
                                    } else {
                                        s.shrink(count);
                                        break;
                                    }
                                }
                            }
                            smelting = output;
                            if (this.items.get(8).getCount() > 0) {
                                this.items.get(8).grow(smelting.getCount());
                            } else {
                                this.items.set(8, smelting);
                            }
                            smelting = ItemStack.EMPTY;
                        } else {
                            output = irecipe.generateResult(world,this,1).copy();
                            x = new int[]{inputs[0].getCount(),inputs[1].getCount(),inputs[2].getCount(),inputs[3].getCount(),inputs[4].getCount(),inputs[5].getCount()};
                            smelting = output;
                            if (this.items.get(8).getCount() > 0) {
                                this.items.get(8).grow(smelting.getCount());
                            } else {
                                this.items.set(8, smelting);
                            }
                            inputs[0].shrink(x[0]);
                            inputs[1].shrink(x[1]);
                            inputs[2].shrink(x[2]);
                            inputs[3].shrink(x[3]);
                            inputs[4].shrink(x[4]);
                            inputs[5].shrink(x[5]);
                            smelting = ItemStack.EMPTY;
                        }


                        this.cookTime = 0;
                        flag1 = true;
                        return;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, this.isBurning()), 3);
            }
        }

        if (flag1) {
            this.markDirty();
        }

    }


    public boolean isBurning()
    {
        return this.burnTime > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isBurning(AlloyFurnaceTile te)
    {
        return te.furnaceData.get(0) > 0;
    }


    private boolean canSmelt(@Nullable AlloyingRecipe recipeIn, IInventory inv)
    {
        if (recipeIn != null || inv.getStackInSlot(7).getItem() instanceof AlloyTemplateItem) {
            recipeMode = inv.getStackInSlot(7).getItem() instanceof AlloyTemplateItem;
            ItemStack template = inv.getStackInSlot(7);
            if (recipeMode) {
                if ((AlloyTemplateItem.getAlloyTier(template) & 1) != 1) {
                    return false;
                }
                for (Map.Entry<Ingredient,Short> input : AlloyTemplateItem.getInputStacks(template).entrySet())
                {
                    int count = input.getValue();
                    int itemCount = 0;
                    for (ItemStack stack : input.getKey().getMatchingStacks()) {
                        itemCount += inv.count(stack.getItem());
                    }
                    if (itemCount < count) {
                        return false;
                    }
                }
            }
            ItemStack stack = recipeMode ? AlloyTemplateItem.getResult(world,template) : recipeIn.generateResult(world,inv,1);
            if (stack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(8);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if ((!itemstack1.isItemEqual(stack) || !ItemStack.areItemStackTagsEqual(stack,itemstack1)) && !itemstack1.isEmpty()) {
                    return false;
                } else if (itemstack1.getCount() + stack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + stack.getCount() <= itemstack1.getMaxStackSize()) {
                    return true;
                } else {
                    return itemstack1.getCount() + stack.getCount() <= stack.getMaxStackSize();
                }
            }

        } else {
            return false;
        }
    }


    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST, Direction.SOUTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            switch (facing)
            {
                case UP:
                    return handlers[0].cast();
                case DOWN:
                    return handlers[1].cast();
                case EAST:
                    return handlers[2].cast();
                case WEST:
                    return handlers[3].cast();
                default:
                    return handlers[4].cast();
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void remove() {
        super.remove();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new AlloyFurnaceContainer(i, world, pos, playerInventory, playerEntity, this, this.furnaceData);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        switch (side)
        {
            case UP:
                return SLOTS_UP;
            case DOWN:
                return SLOTS_DOWN;
            case EAST:
                return SLOTS_EAST;
            case WEST:
                return SLOTS_WEST;
            case NORTH:
            case SOUTH:
            default:
                return SLOTS_BACK;
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index >= 0 && index <= 5 && !flag) {
            this.cookTimeTotal = 800;
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        switch (index)
        {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return AlloyCustomHelper.hasElement(stack.getItem());
            case 6:
                return AbstractFurnaceTileEntity.isFuel(stack);
            case 7:
                return stack.getItem() instanceof AlloyTemplateItem;
            case 8:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    public void markDirty() {
        super.markDirty();
    }
}

