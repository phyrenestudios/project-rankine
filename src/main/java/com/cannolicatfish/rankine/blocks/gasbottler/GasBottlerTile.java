package com.cannolicatfish.rankine.blocks.gasbottler;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;


public class GasBottlerTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{};
    private int cookTime;
    private int cookTimeTotal = Config.MACHINES.GAS_BOTTLER_SPEED.get();
    protected NonNullList<ItemStack> items = NonNullList.withSize(2,ItemStack.EMPTY);
    private final IIntArray towerData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return GasBottlerTile.this.cookTime;
                case 1:
                    return GasBottlerTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    GasBottlerTile.this.cookTime = value;
                    break;
                case 1:
                    GasBottlerTile.this.cookTimeTotal = value;
                    break;
            }
        }
        public int size() {
            return 2;
        }
    };

    public GasBottlerTile() {
        super(RankineTileEntities.GAS_CONDENSER.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt,this.items);
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.items);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        return compound;
    }

    @Override
    public void tick() {
        World worldIn = this.getWorld();
        BlockState BS = this.getBlockState();
        if (!worldIn.isRemote) {
            ItemStack input = this.items.get(0);
            ItemStack output = this.items.get(1);
            if (input.getItem().equals(Items.GLASS_BOTTLE) && worldIn.getBlockState(this.getPos().offset(BS.get(GasBottlerBlock.FACING))).getBlock() instanceof GasBlock && ResourceLocation.tryCreate(worldIn.getBlockState(this.getPos().offset(BS.get(GasBottlerBlock.FACING))).getBlock().getRegistryName().toString().replace("block","bottle")) != null) {
                Item OUT = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryCreate(worldIn.getBlockState(this.getPos().offset(BS.get(GasBottlerBlock.FACING))).getBlock().getRegistryName().toString().replace("block","bottle")));
                if ((output.getItem() == OUT && output.getCount() < 64) || output.isEmpty()) {
                    ++this.cookTime;
                    if (this.cookTime >= this.cookTimeTotal) {
                        if (output.getItem() == OUT && output.getCount() < 64) {
                            this.items.set(1, new ItemStack(OUT, output.getCount() + 1));
                            worldIn.removeBlock(this.getPos().offset(BS.get(GasBottlerBlock.FACING)), false);
                            input.shrink(1);
                        } else if (output.isEmpty()) {
                            this.items.set(1, new ItemStack(OUT, 1));
                            worldIn.removeBlock(this.getPos().offset(BS.get(GasBottlerBlock.FACING)), false);
                            input.shrink(1);
                        }
                        cookTime = 0;
                    }
                }
            } else if (cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }
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
        return new GasBottlerContainer(i, world, pos, playerInventory, playerEntity, this, this.towerData);
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
        switch (index) {
            case 0:
                return stack.getItem().equals(Items.GLASS_BOTTLE);
            default:
            case 1:
                return false;
        }
    }

    @Override
    public void clear() {
        this.items.clear();
    }


}
