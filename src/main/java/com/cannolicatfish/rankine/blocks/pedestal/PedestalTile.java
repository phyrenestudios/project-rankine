package com.cannolicatfish.rankine.blocks.pedestal;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PedestalTile extends ItemDisplayEntity implements Container {
    private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new InvWrapper(this));
    public ItemEntity entity;
    protected NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);

    public PedestalTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlocks.PEDESTAL_TILE, posIn, stateIn);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        ContainerHelper.loadAllItems(compound,this.items);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.items);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(this.getBlockPos(), this.getBlockPos().above(2));
    }

    public int getComparatorSignalLevel() {
        if (!this.isEmpty()) {
            ItemStack stack = this.items.get(0);
            switch (stack.getRarity()) {
                case EPIC:
                    return 15;
                case RARE:
                    return 11;
                case UNCOMMON:
                    return 7;
                case COMMON:
                default:
                    return 3;
            }
        }
        return 0;
    }


    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.items.get(0) == null || this.items.get(0).isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(0).isEmpty() ? ItemStack.EMPTY : this.items.get(0);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items, index, count);
    }

    @Override
    public void clearContent() {
        this.items.set(0, ItemStack.EMPTY);
        this.entity = null;
        this.setChanged();
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (!flag) {
            this.setChanged();
        }
        updateBlock();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, final @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        itemHandler.invalidate();
        super.invalidateCaps();
    }

    public void tick() {

    }
}