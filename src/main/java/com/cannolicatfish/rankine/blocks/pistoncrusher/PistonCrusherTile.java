package com.cannolicatfish.rankine.blocks.pistoncrusher;


import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.recipe.PistonCrusherRecipes;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.PISTON_CRUSHER_TILE;

public class PistonCrusherTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {
    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{2,3,4};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};
    public PistonCrusherTile() {
        super(PISTON_CRUSHER_TILE);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 200;
    private final IIntArray furnaceData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return PistonCrusherTile.this.burnTime;
                case 1:
                    return PistonCrusherTile.this.currentBurnTime;
                case 2:
                    return PistonCrusherTile.this.cookTime;
                case 3:
                    return PistonCrusherTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }

        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    PistonCrusherTile.this.burnTime = value;
                    break;
                case 1:
                    PistonCrusherTile.this.currentBurnTime = value;
                    break;
                case 2:
                    PistonCrusherTile.this.cookTime = value;
                    break;
                case 3:
                    PistonCrusherTile.this.cookTimeTotal = value;
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
        this.burnTime = nbt.getInt("BurnTime");
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        this.currentBurnTime = ForgeHooks.getBurnTime(this.items.get(1));
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
            ItemStack input = this.items.get(0);
            ItemStack fuel = this.items.get(1);
            if ((this.isBurning() || !fuel.isEmpty() && !this.items.get(0).isEmpty())) {
                CrushingRecipe irecipe = this.world.getRecipeManager().getRecipe(RankineRecipeTypes.CRUSHING, this, this.world).orElse(null);
                if (!this.isBurning() && this.canSmelt(irecipe)) {
                    this.burnTime = ForgeHooks.getBurnTime(fuel);
                    this.currentBurnTime = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (fuel.hasContainerItem())
                            this.items.set(1, fuel.getContainerItem());
                        else
                        if (!fuel.isEmpty()) {
                            Item item = fuel.getItem();
                            fuel.shrink(1);
                            if (fuel.isEmpty()) {
                                this.items.set(1, fuel.getContainerItem());
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(irecipe)) {
                    cookTime++;
                    if (cookTime >= cookTimeTotal) {
                        List<ItemStack> results = irecipe.getResults(2,this.world);

                        for (int i = 0; i < results.size(); i++) {
                            if (this.items.get(2 + i).getCount() > 0) {
                                this.items.get(2 + i).grow(results.get(i).getCount());
                            } if (this.items.get(2 + i).getCount() <= 0) {
                                this.items.set(2 + i, results.get(i).copy());
                            }
                        }

                        input.shrink(1);
                        cookTime = 0;
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
    public static boolean isBurning(com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canSmelt(@Nullable CrushingRecipe recipeIn)
    {
        if (!this.items.get(0).isEmpty() && recipeIn != null) {
            List<ItemStack> itemstacks = recipeIn.getPossibleResults(2,this.world);
            if (itemstacks.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(2);
                ItemStack itemstack2 = this.items.get(3);
                ItemStack itemstack3 = this.items.get(4);
                if (itemstack1.isEmpty() && itemstack2.isEmpty() && itemstack3.isEmpty()) {
                    return true;
                } else if ((!itemstack1.isItemEqual(itemstacks.get(0)) && !itemstack1.isEmpty()) || (!itemstack2.isItemEqual(itemstacks.get(1)) && !itemstack2.isEmpty())
                        || (!itemstack3.isItemEqual(itemstacks.get(2)) && !itemstack3.isEmpty())) {
                    return false;
                } else if (itemstack1.getCount() + itemstacks.get(0).getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstacks.get(0).getCount() <= itemstack1.getMaxStackSize() &&
                        itemstack2.getCount() + itemstacks.get(1).getCount() <= this.getInventoryStackLimit() && itemstack2.getCount() + itemstacks.get(1).getCount() <= itemstack2.getMaxStackSize() &&
                        itemstack3.getCount() + itemstacks.get(2).getCount() <= this.getInventoryStackLimit() && itemstack3.getCount() + itemstacks.get(2).getCount() <= itemstack3.getMaxStackSize()) {
                    return true;
                } else {
                    return itemstack1.getCount() + itemstacks.get(0).getCount() <= itemstacks.get(0).getMaxStackSize() &&
                            itemstack2.getCount() + itemstacks.get(1).getCount() <= itemstacks.get(1).getMaxStackSize() &&
                            itemstack3.getCount() + itemstacks.get(2).getCount() <= itemstacks.get(2).getMaxStackSize();
                }
            }
        } else {
            return false;
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
        return new PistonCrusherContainer(i, world, pos, playerInventory, playerEntity, this, this.furnaceData);
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
            this.cookTimeTotal = 200;
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
                return !RankineRecipes.getCrushingOutputs(stack).getKey().isEmpty();
            case 1:
                return AbstractFurnaceTileEntity.isFuel(stack);
            case 2:
                return ItemStack.areItemsEqual(PistonCrusherRecipes.getInstance().getPrimaryResult(getStackInSlot(0)).getKey(), stack);
            case 3:
                return ItemStack.areItemsEqual(PistonCrusherRecipes.getInstance().getSecondaryResult(getStackInSlot(0)).getKey(), stack);
            default:
                return false;
        }
    }

    @Override
    public void clear() {
        this.items.clear();
    }
}
