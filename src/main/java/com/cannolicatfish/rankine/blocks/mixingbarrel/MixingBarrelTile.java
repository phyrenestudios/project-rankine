package com.cannolicatfish.rankine.blocks.mixingbarrel;


import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTileEntities;
import com.cannolicatfish.rankine.recipe.MixingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.List;

public class MixingBarrelTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    FluidTank inputTank = new FluidTank(8000);
    private static final int[] SLOTS_UP = new int[]{0,1,2,3};
    private static final int[] SLOTS_DOWN = new int[]{4};
    private static final int[] SLOTS_HORIZONTAL = new int[]{2,3};
    public MixingBarrelTile() {
        super(RankineTileEntities.MIXING_BARREL.get());
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private int mixTime;
    private int mixTimeTotal;
    private int redstonePower = 0;
    private int needsRefresh = 0;
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
                case 3:
                    return MixingBarrelTile.this.needsRefresh;
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
                    break;
                case 3:
                    MixingBarrelTile.this.needsRefresh = value;
                    break;
            }
        }

        public int size() {
            return 4;
        }
    };

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt,this.items);
        this.inputTank = this.inputTank.readFromNBT(nbt.getCompound("InputTank"));
        this.mixTime = nbt.getInt("MixTime");
        this.mixTimeTotal = nbt.getInt("MixTimeTotal");
        this.redstonePower = nbt.getInt("RedstonePower");
        this.needsRefresh = nbt.getInt("NeedsRefresh");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("InputTank",this.inputTank.writeToNBT(new CompoundNBT()));
        compound.putInt("MixTime", this.mixTime);
        compound.putInt("MixTimeTotal", this.mixTimeTotal);
        compound.putInt("RedstonePower", this.redstonePower);
        compound.putInt("NeedsRefresh", this.needsRefresh);
        ItemStackHelper.saveAllItems(compound, this.items);

        return compound;
    }

    public void tick() {
        boolean flag = this.isRedstonePowered();
        boolean flag1 = this.getNeedsRefresh();
        boolean flag2 = false;

        if (!this.world.isRemote) {
            if (!flag && flag1) {
                this.needsRefresh = 0;
                flag1 = false;
            }
            ItemStack[] inputs = new ItemStack[]{this.items.get(0), this.items.get(1), this.items.get(2), this.items.get(3)};
            if ((!this.getNeedsRefresh() && flag) && (!this.items.get(0).isEmpty() || !this.items.get(1).isEmpty() || !this.items.get(2).isEmpty() || !this.items.get(3).isEmpty())) {
                int angle = this.getBlockState().get(MixingBarrelBlock.ANGLE);
                if (angle == 3) {
                    world.setBlockState(this.pos, RankineBlocks.MIXING_BARREL.get().getDefaultState().with(MixingBarrelBlock.ANGLE,0),3);
                } else {
                    world.setBlockState(this.pos, RankineBlocks.MIXING_BARREL.get().getDefaultState().with(MixingBarrelBlock.ANGLE,angle+1),3);
                }
                List<LivingEntity> entitiesOnBlock = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos, pos.up()).expand(1, 1, 1), (e) -> e instanceof MobEntity || e instanceof PlayerEntity);
                for (LivingEntity i : entitiesOnBlock) {
                    i.setPositionAndRotation(i.getPosX(),i.getPosY()+2,i.getPosZ(),i.getRotationYawHead() + 22.5F,i.getPitchYaw().y);
                }

                this.needsRefresh = 1;


                MixingRecipe irecipe = this.world.getRecipeManager().getRecipe(RankineRecipeTypes.MIXING, this, this.world).orElse(null);
                if (this.canMix(irecipe, this)) {
                    if (this.mixTime == 0) {
                        this.mixTimeTotal = getMixTime();
                    }
                    this.mixTime += getRedstonePower();
                    if (this.mixTime >= this.mixTimeTotal) {
                        if (!irecipe.getFluid().isEmpty()) {
                            inputTank.drain(irecipe.getOutputFluidReq(this), IFluidHandler.FluidAction.EXECUTE);
                        }
                        ItemStack smelting = irecipe.getMixingResult(this,world);
                        if (this.items.get(4).getCount() > 0) {
                            this.items.get(4).grow(smelting.getCount());
                        } else {
                            this.items.set(4, smelting);
                        }


                        this.mixTime = 0;
                        inputs[0].setCount(0);
                        inputs[1].setCount(0);
                        inputs[2].setCount(0);
                        inputs[3].setCount(0);
                        return;
                    }
                } else {
                    this.mixTime = 0;
                }
            }

            if (flag1 != this.getNeedsRefresh()) {
                flag2 = true;
            }
        }

        if (flag2) {
            this.markDirty();
        }

    }

    public FluidTank getInputTank() {
        return inputTank;
    }

    public int getMixTime() {
        MixingRecipe irecipe = this.world.getRecipeManager().getRecipe(RankineRecipeTypes.MIXING, this, this.world).orElse(null);
        if (irecipe != null) {
            return irecipe.getOutputMixTime(this);
        } else {
            return 3200;
        }
    }

    private boolean isRedstonePowered() {
        return this.world.getRedstonePowerFromNeighbors(pos) > 0;
    }

    private int getRedstonePower() {
        return this.world.getRedstonePowerFromNeighbors(pos);
    }

    public boolean getNeedsRefresh() {
        return needsRefresh == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isMixing(MixingBarrelTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canMix(@Nullable MixingRecipe recipeIn, IInventory inv)
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
                if(output.isEmpty()) {
                    this.mixTimeTotal = recipeIn.getOutputMixTime(inv);
                    return true;
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
