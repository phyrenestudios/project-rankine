package com.cannolicatfish.rankine.blocks.gasbottler;

import com.cannolicatfish.rankine.blocks.gases.GasBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;


public class GasBottlerTile extends BlockEntity implements WorldlyContainer, MenuProvider {

    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{};
    private int cookTime;
    private int cookTimeTotal = Config.MACHINES.GAS_BOTTLER_SPEED.get();
    protected NonNullList<ItemStack> items = NonNullList.withSize(2,ItemStack.EMPTY);
    private final ContainerData towerData = new ContainerData(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return GasBottlerTile.this.cookTime;
                case 1:
                    return GasBottlerTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    GasBottlerTile.this.cookTime = value;
                    break;
                case 1:
                    GasBottlerTile.this.cookTimeTotal = value;
                    break;
            }
        }
        public int getCount() {
            return 2;
        }
    };

    public GasBottlerTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.GAS_BOTTLER.get(), posIn, stateIn);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt,this.items, provider);
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        ContainerHelper.saveAllItems(compound, this.items, provider);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, GasBottlerTile tile) {
        Level worldIn = tile.getLevel();
        BlockState BS = tile.getBlockState();
        if (!worldIn.isClientSide) {
            ItemStack input = tile.items.get(0);
            ItemStack output = tile.items.get(1);
            if (input.getItem().equals(Items.GLASS_BOTTLE) && worldIn.getBlockState(tile.getBlockPos().relative(BS.getValue(BlockStateProperties.FACING))).getBlock() instanceof GasBlock && ResourceLocation.tryParse(BuiltInRegistries.BLOCK.getKey(worldIn.getBlockState(tile.getBlockPos().relative(BS.getValue(BlockStateProperties.FACING))).getBlock()).toString().replace("block","bottle")) != null) {
                Item OUT = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(BuiltInRegistries.BLOCK.getKey(worldIn.getBlockState(tile.getBlockPos().relative(BS.getValue(BlockStateProperties.FACING))).getBlock()).toString().replace("block","bottle")));
                if ((output.getItem() == OUT && output.getCount() < 64) || output.isEmpty()) {
                    ++tile.cookTime;
                    if (tile.cookTime >= tile.cookTimeTotal) {
                        if (output.getItem() == OUT && output.getCount() < 64) {
                            tile.items.set(1, new ItemStack(OUT, output.getCount() + 1));
                            worldIn.removeBlock(tile.getBlockPos().relative(BS.getValue(BlockStateProperties.FACING)), false);
                            input.shrink(1);
                        } else if (output.isEmpty()) {
                            tile.items.set(1, new ItemStack(OUT, 1));
                            worldIn.removeBlock(tile.getBlockPos().relative(BS.getValue(BlockStateProperties.FACING)), false);
                            input.shrink(1);
                        }
                        tile.cookTime = 0;
                    }
                }
            } else if (tile.cookTime > 0) {
                tile.cookTime = Mth.clamp(tile.cookTime - 2, 0, tile.cookTimeTotal);
            }
        }

    }

    private final IItemHandler[] handlers = new IItemHandler[] {
            new SidedInvWrapper(this, Direction.UP),
            new SidedInvWrapper(this, Direction.DOWN),
            new SidedInvWrapper(this, Direction.NORTH)
    };

    // PORT-CAP: GasBottlerTile -> getItemHandler
    @Nullable
    public IItemHandler getItemHandler(@Nullable Direction side) {
        if (this.remove || side == null) {
            return null;
        }
        if (side == Direction.UP) {
            return handlers[0];
        } else if (side == Direction.DOWN) {
            return handlers[1];
        } else {
            return handlers[2];
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(this.getType()).getPath());
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new GasBottlerContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.towerData);
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
        switch (index) {
            case 0:
                return stack.getItem().equals(Items.GLASS_BOTTLE);
            default:
            case 1:
                return false;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }


}
