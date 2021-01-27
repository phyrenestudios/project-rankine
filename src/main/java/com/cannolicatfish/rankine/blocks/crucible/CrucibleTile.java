package com.cannolicatfish.rankine.blocks.crucible;


import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipes;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.CRUCIBLE_TILE;

public class CrucibleTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    private static final int[] SLOTS_UP = new int[]{0,1};
    private static final int[] SLOTS_DOWN = new int[]{4};
    private static final int[] SLOTS_HORIZONTAL = new int[]{2,3};
    public CrucibleTile() {
        super(CRUCIBLE_TILE);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private int cookTime;
    private int cookTimeTotal = 3200;
    private int heatPower = 0;
    private final IIntArray furnaceData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return CrucibleTile.this.cookTime;
                case 1:
                    return CrucibleTile.this.cookTimeTotal;
                case 2:
                    return CrucibleTile.this.heatPower;
                default:
                    return 0;
            }
        }

        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    CrucibleTile.this.cookTime = value;
                    break;
                case 1:
                    CrucibleTile.this.cookTimeTotal = value;
                    break;
                case 2:
                    CrucibleTile.this.heatPower = value;
            }
        }

        public int size() {
            return 3;
        }
    };

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
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        ItemStackHelper.saveAllItems(compound, this.items);

        return compound;
    }

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (!this.world.isRemote) {
            ItemStack[] inputs = new ItemStack[]{this.items.get(0), this.items.get(1), this.items.get(2), this.items.get(3)};
            if ((this.isBurning() || !this.items.get(0).isEmpty() && !this.items.get(1).isEmpty() && !this.items.get(2).isEmpty() && !this.items.get(3).isEmpty())) {
                if (this.isHeated(this.pos,this.world) && this.canSmelt()) {
                    ++this.cookTime;
                    if (Arrays.stream(inputs).anyMatch(itemStack -> itemStack.getItem() == RankineItems.BORAX.get()))
                    {
                        this.cookTime += 3;
                    }
                    if (this.cookTime >= this.cookTimeTotal) {
                        ItemStack smelting = RankineRecipes.returnCrucibleOutput(Arrays.asList(this.items.get(0).getItem(),this.items.get(1).getItem(),this.items.get(2).getItem(),this.items.get(3).getItem()));
                        if (this.items.get(4).getCount() > 0) {
                            this.items.get(4).grow(smelting.getCount());
                        } else {
                            this.items.set(4, smelting);
                        }
                        this.cookTime = 0;
                        inputs[0].shrink(1);
                        inputs[1].shrink(1);
                        inputs[2].shrink(1);
                        inputs[3].shrink(1);
                        return;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if ((!this.isBurning()) && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(CrucibleBlock.LEVEL, this.isBurning() ? checkResultingOutput() : 0), 3);
            }
        }

        if (flag1) {
            this.markDirty();
        }

    }

    private int checkResultingOutput() {
        ItemStack smelting = RankineRecipes.returnCrucibleOutput(Arrays.asList(this.items.get(0).getItem(),this.items.get(1).getItem(),this.items.get(2).getItem(),this.items.get(3).getItem()));
        if (smelting.getItem() == RankineItems.STEEL_ALLOY.get())
        {
            return 1;
        } else if (smelting.getItem() == Items.REDSTONE)
        {
            return 3;
        } else if (smelting.getItem() == Items.GLOWSTONE) {
            return 4;
        } else if (!smelting.isEmpty()) {
            return 2;
        } else {
            return 0;
        }
    }

    private boolean isHeated(BlockPos pos, World worldIn) {
        List<BlockPos> positions = Arrays.asList(pos.down(),pos.east(),pos.north(),pos.west(),pos.south());
        for (BlockPos p : positions) {
            if (worldIn.getBlockState(p).getBlock() == Blocks.MAGMA_BLOCK || worldIn.getBlockState(p).getBlock() == Blocks.LAVA)
            {
                heatPower = 1;
                return true;
            } else if (p == pos.down() && worldIn.getBlockState(p).getBlock() == Blocks.FIRE)
            {
                heatPower = 1;
                return true;
            }
        }
        heatPower = 0;
        return false;
    }

    public boolean isBurning()
    {
        return this.cookTime > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isBurning(CrucibleTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canSmelt()
    {
        if(this.items.get(0).isEmpty() || this.items.get(1).isEmpty() || this.items.get(2).isEmpty() || this.items.get(3).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack result = RankineRecipes.returnCrucibleOutput(Arrays.asList(this.items.get(0).getItem(),this.items.get(1).getItem(),this.items.get(2).getItem(),this.items.get(3).getItem()));
            if(result.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack output = this.items.get(4);
                if(output.isEmpty()) return true;

                if(!output.isItemEqual(result))
                {
                    return false;
                }
                int res = output.getCount() + result.getCount();
                if (ItemStack.areItemStackTagsEqual(output, result) && ItemStack.areItemsEqual(output, result)) {
                    return res <= 64 && res <= output.getMaxStackSize();
                } else {
                    return false;
                }
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
        return new CrucibleContainer(i, world, pos, playerInventory, playerEntity, this, this.furnaceData);
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
            this.cookTimeTotal = 3200;
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
                return stack.getItem().getTags().contains(new ResourceLocation("rankine:crucible_fluxes"));
            case 4:
                return ItemStack.areItemsEqual(RankineRecipes.returnCrucibleOutput(Arrays.asList(getStackInSlot(0).getItem(),getStackInSlot(1).getItem(),getStackInSlot(2).getItem(),getStackInSlot(3).getItem())), stack);
            default:
                return false;
        }
    }

    @Override
    public void clear() {
        this.items.clear();
    }
}
