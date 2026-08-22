package com.cannolicatfish.rankine.blocks.mtt;

import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;

public class MaterialTestingTableTile extends BlockEntity implements WorldlyContainer, MenuProvider {
    protected NonNullList<ItemStack> items = NonNullList.withSize(14, ItemStack.EMPTY);
    private static final int[] SLOTS_UP = new int[]{0,1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{2,3,4,5,6,7,8,9,10,11,12,13};
    public MaterialTestingTableTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.MATERIAL_TESTING_TABLE.get(), posIn, stateIn);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt,this.items, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        ContainerHelper.saveAllItems(compound, this.items, provider);
    }


    private final IItemHandler[] handlers = new IItemHandler[] {
            new SidedInvWrapper(this, Direction.UP),
            new SidedInvWrapper(this, Direction.DOWN),
            new SidedInvWrapper(this, Direction.NORTH)
    };

    // PORT-CAP: MaterialTestingTableTile -> getItemHandler
    @Nullable
    public IItemHandler getItemHandler(@Nullable Direction side) {
        if (this.remove || side == null) {
            return null;
        }
        if (side == Direction.UP)
            return handlers[0];
        else if (side == Direction.DOWN)
            return handlers[1];
        else
            return handlers[2];
    }

    // Port fix F24: this returned a literal, untranslated registry path (1.18.2 baseline did the
    // same: `new TextComponent(getType().getRegistryName().getPath())`) AND
    // MaterialTestingTableScreen#renderLabels never actually drew this component anywhere (it
    // fully overrides the method without calling super.renderLabels() or the baseline's
    // equivalent) -- both true in 1.18.2 too, so the MTT title bar was blank there as well. Fixed
    // for the release: a real translatable name, resolved via the block's own existing
    // "block.rankine.material_testing_table" lang key (same one every other block already gets
    // from the lang provider -- no new key, no lang churn).
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
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
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(stack, itemstack);
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
        return (stack.is(RankineTags.Items.MTT_TOOLS));
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }


}
