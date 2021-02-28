package com.cannolicatfish.rankine.blocks.rankinebox;

import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerContainer;
import com.cannolicatfish.rankine.blocks.rankinebox.RankineBoxTile;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.AlloyTemplateItem;
import com.cannolicatfish.rankine.items.PowerCellItem;
import com.cannolicatfish.rankine.items.TransmuterItem;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.WeightedCollection;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

import java.util.Random;

import static com.cannolicatfish.rankine.init.RankineBlocks.RANKINE_BOX_TILE;

public class RankineBoxTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {
    private static final PeriodicTableUtils utils = PeriodicTableUtils.getInstance();
    private static final int[] SLOTS_UP = new int[]{0,1,2};
    private static final int[] SLOTS_DOWN = new int[]{3};
    private static final int[] SLOTS_HORIZONTAL = new int[]{0,1,2};
    private int cookTime;
    private int cookTimeTotal = Config.MACHINES.RANKINE_BOX_SPEED.get();
    private int direction = 0;
    private int powered = 0;
    protected NonNullList<ItemStack> items = NonNullList.withSize(4,ItemStack.EMPTY);
    private final IIntArray tileData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return RankineBoxTile.this.cookTime;
                case 1:
                    return RankineBoxTile.this.cookTimeTotal;
                case 2:
                    return RankineBoxTile.this.direction;
                case 3:
                    return RankineBoxTile.this.powered;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    RankineBoxTile.this.cookTime = value;
                    break;
                case 1:
                    RankineBoxTile.this.cookTimeTotal = value;
                    break;
                case 2:
                    RankineBoxTile.this.direction = value;
                    break;
                case 3:
                    RankineBoxTile.this.powered = value;
                    break;
            }
        }
        public int size() {
            return 4;
        }
    };

    public RankineBoxTile() {
        super(RANKINE_BOX_TILE);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt,this.items);
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        this.direction = nbt.getInt("Direction");
        this.powered = nbt.getInt("Powered");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        compound.putInt("Direction", this.direction);
        compound.putInt("Powered", this.powered);
        ItemStackHelper.saveAllItems(compound, this.items);

        return compound;
    }

    @Override
    public void tick() {
        World worldIn = this.getWorld();
        if (!worldIn.isRemote) {
            ItemStack input = this.items.get(0);
            ItemStack transmuter = this.items.get(1);
            ItemStack fuel = this.items.get(2);
            this.direction = getDirection(transmuter);
            PeriodicTableUtils.Element outputElement = utils.getAdjacentElement(utils.getElementFromIngotItem(input.getItem()),this.direction);
            this.powered = isPowered(fuel) ? 1 : 0;
            boolean ready = this.powered == 1 && outputElement != PeriodicTableUtils.Element.NONE && canSmelt(outputElement);
            if (ready)
            {
                this.cookTime += PowerCellItem.getTier(fuel);
                if (this.cookTime >= this.cookTimeTotal) {
                    ItemStack smelting = new ItemStack(utils.getElementIngot(outputElement));
                    if (this.items.get(3).getCount() > 0) {
                        this.items.get(3).grow(smelting.getCount());
                    } else {
                        this.items.set(3, smelting);
                    }
                    input.shrink(1);
                    transmuter.shrink(1);
                    cookTime = 0;
                }
            } else if (cookTime > 0)
            {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }
        }

    }

    private boolean isPowered(ItemStack fuel) {
        return PowerCellItem.getTier(fuel) > 0;
    }

    private boolean canSmelt(PeriodicTableUtils.Element element) {
        ItemStack stack = new ItemStack(utils.getElementIngot(element));
        if (stack.isEmpty()) {
            return false;
        } else {
            ItemStack itemstack1 = this.items.get(3);
            if (itemstack1.isEmpty()) {
                return true;
            } else if ((!itemstack1.isItemEqual(stack) && !itemstack1.isEmpty())) {
                return false;
            } else if (itemstack1.getCount() + stack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + stack.getCount() <= itemstack1.getMaxStackSize()) {
                return true;
            } else {
                return itemstack1.getCount() + stack.getCount() <= stack.getMaxStackSize();
            }
        }
    }

    private int getDirection(ItemStack transmuter) {
        if (transmuter.getItem() instanceof TransmuterItem) {
            if (transmuter.getItem() == RankineItems.UP_TRANSMUTER.get()) {
                return 3;
            } else if (transmuter.getItem() == RankineItems.RIGHT_TRANSMUTER.get()) {
                return 4;
            } else if (transmuter.getItem() == RankineItems.DOWN_TRANSMUTER.get()) {
                return 5;
            } else if (transmuter.getItem() == RankineItems.LEFT_TRANSMUTER.get()) {
                return 6;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new RankineBoxContainer(i, world, pos, playerInventory, playerEntity, this, this.tileData);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
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

        if (index == 0 && !flag) {
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
                return PeriodicTableUtils.getInstance().hasElement(stack.getItem());
            case 1:
                return stack.getItem() instanceof TransmuterItem;
            case 2:
                return stack.getItem() instanceof PowerCellItem;
            case 3:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void clear() {
        this.items.clear();
    }


}
