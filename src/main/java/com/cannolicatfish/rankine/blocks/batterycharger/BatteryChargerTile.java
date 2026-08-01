package com.cannolicatfish.rankine.blocks.batterycharger;

import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import com.cannolicatfish.rankine.items.BatteryItem;
import com.cannolicatfish.rankine.items.RTGItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DaylightDetectorBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;


public class BatteryChargerTile extends BlockEntity implements WorldlyContainer, MenuProvider {

    private static final int[] SLOTS_UP = new int[]{0,1,2,3,4,5};
    private static final int[] SLOTS_DOWN = new int[]{0,1,2,3,4,5};
    private static final int[] SLOTS_HORIZONTAL = new int[]{0,1,2,3,4,5};

    private int signalPower;

    private int signalMultiplier;

    protected NonNullList<ItemStack> items = NonNullList.withSize(6,ItemStack.EMPTY);
    private final ContainerData holderData = new ContainerData(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return BatteryChargerTile.this.signalPower;
                case 1:
                    return BatteryChargerTile.this.signalMultiplier;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    BatteryChargerTile.this.signalPower = value;
                    break;
                case 1:
                    BatteryChargerTile.this.signalMultiplier = value;
                    break;
            }
        }
        public int getCount() {
            return 2;
        }
    };

    public BatteryChargerTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.BATTERY_CHARGER.get(), posIn, stateIn);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt,this.items, provider);
        this.signalPower = nbt.getInt("SignalPower");
        this.signalMultiplier = nbt.getInt("SignalMultiplier");
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        ContainerHelper.saveAllItems(compound, this.items, provider);
        compound.putInt("SignalPower", this.signalPower);
        compound.putInt("SignalMultiplier", this.signalMultiplier);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BatteryChargerTile tile) {
        if (level != null) {
            tile.signalPower = 0;
            tile.signalMultiplier = 1;
            for (Direction d : Direction.values()) {
                BlockState bs = level.getBlockState(pos.relative(d));
                if (!d.equals(Direction.UP) && !d.equals(Direction.DOWN) && bs.getBlock() instanceof LightningRodBlock && bs.hasProperty(LightningRodBlock.POWERED) && bs.getValue(LightningRodBlock.POWERED)) {
                    tile.signalMultiplier += 32;
                }
                if (d.equals(Direction.UP) && bs.getBlock() instanceof DaylightDetectorBlock && bs.hasProperty(DaylightDetectorBlock.POWER)) {
                    tile.signalPower = bs.getValue(DaylightDetectorBlock.POWER);
                }
            }
            if (tile.signalPower > 0) {

                if (level.getDayTime() % 400 == 0 || tile.signalMultiplier > 1) {
                    for (ItemStack stack : tile.items) {
                        if (stack.getItem() instanceof BatteryItem && !(stack.getItem() instanceof RTGItem) && stack.getDamageValue() != 0) {
                            stack.setDamageValue(Math.max(0,stack.getDamageValue() - tile.signalPower * tile.signalMultiplier));
                        }
                    }
                }

            }
        }

    }

    private final IItemHandler[] handlers = new IItemHandler[] {
            new SidedInvWrapper(this, Direction.UP),
            new SidedInvWrapper(this, Direction.DOWN),
            new SidedInvWrapper(this, Direction.NORTH)
    };

    // PORT-CAP: BatteryChargerTile -> getItemHandler
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

    @Override
    public Component getDisplayName() {
        return Component.literal(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(this.getType()).getPath());
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new BatteryChargerContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.holderData);
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
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStackIn);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        if (stack.getItem() instanceof BatteryItem && !(stack.getItem() instanceof RTGItem)) {
            return stack.getDamageValue() == 0;
        }
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

        if (index == 0 && !flag) {
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
        return stack.getItem() instanceof BatteryItem;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }


}
