package com.cannolicatfish.rankine.blocks.laserquarry;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.PowerCellItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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

import javax.annotation.Nullable;


import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.LASER_QUARRY_TILE;


public class LaserQuarryTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    private static final int[] SLOTS_UP = new int[]{};
    private static final int[] SLOTS_DOWN = new int[]{0};
    private static final int[] SLOTS_HORIZONTAL = new int[]{};
    private int cookTime;
    private int cookTimeTotal = Config.MACHINES.LASER_QUARRY_SPEED.get();
    protected NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);
    private final IIntArray towerData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return LaserQuarryTile.this.cookTime;
                case 1:
                    return LaserQuarryTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    LaserQuarryTile.this.cookTime = value;
                    break;
                case 1:
                    LaserQuarryTile.this.cookTimeTotal = value;
                    break;
            }
        }
        public int size() {
            return 2;
        }
    };

    public LaserQuarryTile() {
        super(LASER_QUARRY_TILE);
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

    int i = 0;
    int x = 0;
    int y = 1;
    int z = 0;

    @Override
    public void tick() {
        World worldIn = this.getWorld();
        int maxRadius = checkStructure(pos,worldIn);
        System.out.print(maxRadius);
        if (!worldIn.isRemote && i <= maxRadius){
            if (worldIn.isBlockPowered(pos) && maxRadius > 0 && this.items.get(0).getItem() instanceof PowerCellItem) {
                ++this.cookTime;
                if (this.cookTime == this.cookTimeTotal) {
                    for (BlockPos TARGET_POS : BlockPos.getAllInBoxMutable(pos.add(-i,-y,-i), pos.add(i,-y,i))) {
                        Block TARGET_BLOCK = worldIn.getBlockState(TARGET_POS).getBlock();
                        boolean stop = false;
                        if (TARGET_BLOCK != Blocks.AIR && !TARGET_BLOCK.getTags().contains(new ResourceLocation("rankine:nonquarryable"))) {
                            for (int height = TARGET_POS.getY(); height <= this.pos.getY(); ++height) {
                                if (worldIn.getBlockState(new BlockPos(TARGET_POS.getX(), height, TARGET_POS.getZ())) == RankineBlocks.QUARRY_BARRIER.get().getDefaultState()) {
                                    stop = true;
                                    break;
                                }
                            }
                            if (!stop) {
                                worldIn.destroyBlock(TARGET_POS, false);
                            }
                        }
                        cookTime = 0;
                    }
                    ++y;
                    if (y >= pos.getY()) {
                        y = 1;
                        ++i;
                    }

                    /*BlockPos TARGET_POS = pos.add(x,-y,z);
                    Block TARGET_BLOCK = worldIn.getBlockState(TARGET_POS).getBlock();
                    if (TARGET_BLOCK == Blocks.AIR || TARGET_BLOCK.getTags().contains(new ResourceLocation("rankine:nonquarryable")) || TARGET_BLOCK.getTags().contains(new ResourceLocation("forge:ores"))) {
                        cookTime = cookTimeTotal - 1;
                    } else {
                        worldIn.destroyBlock(TARGET_POS,false);
                        cookTime = 0;
                    }
                    if (x == i) {
                        if (z == i) {
                            ++y;
                            if (y >= pos.getY()) {
                                y = 1;
                                ++i;
                            }
                            x = -i;
                            z = -i;
                        } else {
                            x = -i;
                            ++z;
                        }
                    } else {
                        ++x;
                    } */
                }
            } else if (cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }
        }
    }

    private int checkStructure(BlockPos pos, World worldIn) {
        List<BlockPos> Base = Arrays.asList(pos.add(1,0,0),pos.add(2,0,0),pos.add(0,0,1),pos.add(0,0,2),pos.add(-1,0,0),pos.add(-2,0,0),pos.add(0,0,-1),pos.add(0,0,-2),pos.add(1,0,1),pos.add(1,0,-1),pos.add(-1,0,1),pos.add(-1,0,-1));
        for(BlockPos b : Base) {
            if (worldIn.getBlockState(b) != RankineBlocks.STAINLESS_STEEL_SHEETMETAL.get().getDefaultState()) {
                return 0;
            }
        }
        BlockState lqb = RankineBlocks.LASER_PYLON_BASE.get().getDefaultState();
        BlockState lqt = RankineBlocks.LASER_PYLON_TOP.get().getDefaultState();
        for (int i = 1; i <= Config.MACHINES.LASER_QUARRY_RANGE.get(); ++i) {
            if (worldIn.getBlockState(pos.add(2,i,0)) == lqt && worldIn.getBlockState(pos.add(-2,i,0)) == lqt && worldIn.getBlockState(pos.add(0,i,-2)) == lqt && worldIn.getBlockState(pos.add(0,i,2)) == lqt) {
                return i;
            } else if (worldIn.getBlockState(pos.add(2,i,0)) != lqb && worldIn.getBlockState(pos.add(-2,i,0)) != lqb && worldIn.getBlockState(pos.add(0,i,-2)) != lqb && worldIn.getBlockState(pos.add(0,i,2)) != lqb) {
                return 0;
            }
        }
        return 0;
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
        return new LaserQuarryContainer(i, world, pos, playerInventory, playerEntity, this, this.towerData);
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
        return true;
    }

    @Override
    public void clear() {
        this.items.clear();
    }


}
