package com.cannolicatfish.rankine.blocks.pedestal;

import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class PedestalTile extends ItemDisplayEntity implements Container {
    private final IItemHandler itemHandler = new InvWrapper(this);
    public ItemEntity entity;
    protected NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);

    public PedestalTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.PEDESTAL.get(), posIn, stateIn);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.loadAdditional(compound, provider);
        ContainerHelper.loadAllItems(compound,this.items, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        ContainerHelper.saveAllItems(compound, this.items, provider);
    }

    // PORT-TODO(client): BlockEntity#getRenderBoundingBox was removed in NeoForge 1.21 — the renderer
    // (client/renders/PedestalRenderer) must override BlockEntityRenderer#getRenderBoundingBox to return
    // new AABB(pos, pos.above(2)) so the displayed item renders when the block is off-screen.

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
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(stack, itemstack);
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

    // PORT-CAP: PedestalTile -> getItemHandler
    @Nullable
    public IItemHandler getItemHandler(@Nullable Direction side) {
        // Old getCapability returned the handler for every side, including null.
        return this.itemHandler;
    }

    public void tick() {

    }
}
