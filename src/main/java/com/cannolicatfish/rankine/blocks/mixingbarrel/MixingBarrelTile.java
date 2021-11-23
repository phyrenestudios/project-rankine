package com.cannolicatfish.rankine.blocks.mixingbarrel;


import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.MixingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.MIXING_BARREL_TILE;

public class MixingBarrelTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    FluidTank inputTank = new FluidTank(8000);
    private static final int[] SLOTS_UP = new int[]{0,1};
    private static final int[] SLOTS_DOWN = new int[]{4, 5};
    private static final int[] SLOTS_HORIZONTAL = new int[]{2,3};
    public MixingBarrelTile() {
        super(MIXING_BARREL_TILE);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
    private int mixTime;
    private int mixTimeTotal;
    private int color = 16777215;
    private int redstonePower = 0;
    private final IIntArray furnaceData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return MixingBarrelTile.this.mixTime;
                case 1:
                    return MixingBarrelTile.this.mixTimeTotal;
                case 2:
                    return MixingBarrelTile.this.redstonePower;
                default:
                    return 0;
            }
        }

        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    MixingBarrelTile.this.mixTime = value;
                    break;
                case 1:
                    MixingBarrelTile.this.mixTimeTotal = value;
                    break;
                case 2:
                    MixingBarrelTile.this.redstonePower = value;
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
        this.mixTime = nbt.getInt("MixTime");
        this.mixTimeTotal = nbt.getInt("MixTimeTotal");
        this.color = nbt.getInt("color");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("MixTime", this.mixTime);
        compound.putInt("MixTimeTotal", this.mixTimeTotal);
        compound.putInt("color", this.color);
        ItemStackHelper.saveAllItems(compound, this.items);

        return compound;
    }

    public void tick() {
        boolean flag = this.isHeated(this.pos,this.world);
        boolean flag1 = this.isMixing();
        boolean flag2 = false;

        if (!this.world.isRemote) {
            if (!flag1) {
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(MixingBarrelBlock.FLUID, false), 3);
            } else if (this.getTileData().getInt("color") != this.color) {
                this.getTileData().putInt("color",this.color);
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(MixingBarrelBlock.FLUID, this.isMixing()), 3);
            }
            ItemStack[] inputs = new ItemStack[]{this.items.get(0), this.items.get(1), this.items.get(2), this.items.get(3)};
            if ((this.isMixing() && flag) || !this.items.get(0).isEmpty() && !this.items.get(1).isEmpty() && !this.items.get(2).isEmpty() && !this.items.get(3).isEmpty()) {
                MixingRecipe irecipe = this.world.getRecipeManager().getRecipe(RankineRecipeTypes.MIXING, this, this.world).orElse(null);
                if (this.canSmelt(irecipe, this)) {
                    if (this.mixTime == 0) {
                        this.mixTimeTotal = getMixTime();

                    }
                    ++this.mixTime;
                    if (this.mixTime >= this.mixTimeTotal) {
                        ItemStack smelting = irecipe.getMixingResult(this,world);
                        if (this.items.get(4).getCount() > 0) {
                            this.items.get(4).grow(smelting.getCount());
                        } else {
                            this.items.set(4, smelting);
                        }
                        this.mixTime = 0;
                        inputs[0].shrink(1);
                        inputs[1].shrink(1);
                        inputs[2].shrink(1);
                        inputs[3].shrink(1);
                        return;
                    }
                } else {
                    this.mixTime = 0;
                }
            } else if ((flag) && this.mixTime > 0) {
                this.mixTime = MathHelper.clamp(this.mixTime - 2, 0, this.mixTimeTotal);
            }

            if (flag1 != this.isMixing()) {
                flag2 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(MixingBarrelBlock.FLUID, this.isMixing()), 3);
            }
        }

        if (flag2) {
            this.markDirty();
        }

    }

    public int getMixTime() {
        MixingRecipe irecipe = this.world.getRecipeManager().getRecipe(RankineRecipeTypes.MIXING, this, this.world).orElse(null);
        if (irecipe != null) {
            return irecipe.getRecipeMixTime(this);
        } else {
            return 3200;
        }
    }

    private boolean isHeated(BlockPos pos, World worldIn) {
        List<BlockPos> positions = Arrays.asList(pos.down(),pos.east(),pos.north(),pos.west(),pos.south());
        for (BlockPos p : positions) {
            if (worldIn.getBlockState(p).getBlock() == Blocks.MAGMA_BLOCK || worldIn.getBlockState(p).getBlock() == Blocks.LAVA)
            {
                redstonePower = 1;
                return true;
            } else if (p == pos.down() && worldIn.getBlockState(p).getBlock() == Blocks.FIRE)
            {
                redstonePower = 1;
                return true;
            }
        }
        redstonePower = 0;
        return false;
    }

    public boolean isMixing()
    {
        return this.mixTime > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isMixing(MixingBarrelTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canSmelt(@Nullable MixingRecipe recipeIn, IInventory inv)
    {
        if (recipeIn == null)
        {
            return false;
        }
        else
        {
            ItemStack result = recipeIn.getMixingResult(inv,world);
            if(result.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack output = this.items.get(4);
                ItemStack secondary = this.items.get(5);
                if(output.isEmpty()) {
                    this.mixTimeTotal = recipeIn.getRecipeMixTime(inv);
                    return true;
                }

                if(!output.isItemEqual(result))
                {
                    return false;
                }
                int res = output.getCount() + result.getCount();
                if (ItemStack.areItemStackTagsEqual(output, result) && ItemStack.areItemsEqual(output, result)) {
                    this.mixTimeTotal = recipeIn.getRecipeMixTime(inv);
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
        return new MixingBarrelContainer(i, world, pos, playerInventory, playerEntity, this, this.furnaceData);
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
            this.mixTimeTotal = this.getMixTime();
            this.mixTime = 0;
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
