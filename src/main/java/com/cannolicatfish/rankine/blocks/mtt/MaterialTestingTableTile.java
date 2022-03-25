package com.cannolicatfish.rankine.blocks.mtt;

import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceContainer;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.items.BatteryItem;
import com.cannolicatfish.rankine.items.GasBottleItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;

import static com.cannolicatfish.rankine.init.RankineBlocks.MATERIAL_TESTING_TABLE_TILE;

public class MaterialTestingTableTile extends BlockEntity implements WorldlyContainer, MenuProvider {
    protected NonNullList<ItemStack> items = NonNullList.withSize(14, ItemStack.EMPTY);
    private static final int[] SLOTS_UP = new int[]{0,1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{2,3,4,5,6,7,8,9,10,11,12,13};
    public MaterialTestingTableTile(BlockPos posIn, BlockState stateIn) {
        super(MATERIAL_TESTING_TABLE_TILE, posIn, stateIn);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt,this.items);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        ContainerHelper.saveAllItems(compound, this.items);
        return compound;
    }


    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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
    public void setRemoved() {
        super.setRemoved();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new MaterialTestingTableContainer(i, level,worldPosition,playerInventory, playerEntity, this);
    }


    @Override
    public int[] getSlotsForFace(Direction side) {
        return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStackIn);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public int getContainerSize() {
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
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items, index, count);
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
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 0) {
            return AlloyCustomHelper.hasElement(stack.getItem())  || stack.getItem() instanceof IAlloyItem;
        }
        return (RankineTags.Items.MTT_TOOLS.contains(stack.getItem()));
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }


}
