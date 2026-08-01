package com.cannolicatfish.rankine.blocks.laserquarry;
// PORT-TODO(init): LASER_QUARRY BlockEntityType/MenuType not registered — init agent must add or keep this class dormant
// PORT-TODO(init): depends on unregistered blocks (RankineBlocks.QUARRY_BARRIER, LASER_PYLON_BASE, LASER_PYLON_TOP,
// STAINLESS_STEEL_SHEETMETAL is registered) and missing config values (Config.MACHINES.LASER_QUARRY_SPEED / LASER_QUARRY_RANGE);
// the class below has been modernized to 1.21.1 NeoForge (1.16-era code updated) but stays commented out to match its dormant 1.18 state.
/*
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.items.BatteryItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class LaserQuarryTile extends BlockEntity implements WorldlyContainer, MenuProvider {

    private static final TagKey<Block> NONQUARRYABLE = TagKey.create(Registries.BLOCK, ResourceLocation.parse("rankine:nonquarryable"));
    private static final int[] SLOTS_UP = new int[]{};
    private static final int[] SLOTS_DOWN = new int[]{0};
    private static final int[] SLOTS_HORIZONTAL = new int[]{};
    private int cookTime;
    private int cookTimeTotal = Config.MACHINES.LASER_QUARRY_SPEED.get(); // PORT-TODO(init): LASER_QUARRY_SPEED missing from Config.MACHINES
    protected NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);
    private final ContainerData towerData = new ContainerData(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return LaserQuarryTile.this.cookTime;
                case 1:
                    return LaserQuarryTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    LaserQuarryTile.this.cookTime = value;
                    break;
                case 1:
                    LaserQuarryTile.this.cookTimeTotal = value;
                    break;
            }
        }
        public int getCount() {
            return 2;
        }
    };

    public LaserQuarryTile(BlockPos posIn, BlockState stateIn) {
        // PORT-TODO(init): LASER_QUARRY BlockEntityType/MenuType not registered — init agent must add or keep this class dormant
        super(RankineBlockEntityTypes.LASER_QUARRY.get(), posIn, stateIn);
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

    int i = 0;
    int x = 0;
    int y = 1;
    int z = 0;

    public static void tick(Level worldIn, BlockPos pos, BlockState bs, LaserQuarryTile tile) {
        int maxRadius = tile.checkStructure(pos,worldIn);
        if (!worldIn.isClientSide && tile.i <= maxRadius){
            if (worldIn.hasNeighborSignal(pos) && maxRadius > 0 && tile.items.get(0).getItem() instanceof BatteryItem) {
                ++tile.cookTime;
                if (tile.cookTime == tile.cookTimeTotal) {
                    for (BlockPos TARGET_POS : BlockPos.betweenClosed(pos.offset(-tile.i,-tile.y,-tile.i), pos.offset(tile.i,-tile.y,tile.i))) {
                        Block TARGET_BLOCK = worldIn.getBlockState(TARGET_POS).getBlock();
                        boolean stop = false;
                        if (TARGET_BLOCK != Blocks.AIR && !worldIn.getBlockState(TARGET_POS).is(NONQUARRYABLE)) {
                            for (int height = TARGET_POS.getY(); height <= pos.getY(); ++height) {
                                if (worldIn.getBlockState(new BlockPos(TARGET_POS.getX(), height, TARGET_POS.getZ())) == RankineBlocks.QUARRY_BARRIER.get().defaultBlockState()) {
                                    stop = true;
                                    break;
                                }
                            }
                            if (!stop) {
                                worldIn.destroyBlock(TARGET_POS, false);
                            }
                        }
                        tile.cookTime = 0;
                    }
                    ++tile.y;
                    if (tile.y >= pos.getY()) {
                        tile.y = 1;
                        ++tile.i;
                    }
                }
            } else if (tile.cookTime > 0) {
                tile.cookTime = Mth.clamp(tile.cookTime - 2, 0, tile.cookTimeTotal);
            }
        }
    }

    private int checkStructure(BlockPos pos, Level worldIn) {
        List<BlockPos> Base = Arrays.asList(pos.offset(1,0,0),pos.offset(2,0,0),pos.offset(0,0,1),pos.offset(0,0,2),pos.offset(-1,0,0),pos.offset(-2,0,0),pos.offset(0,0,-1),pos.offset(0,0,-2),pos.offset(1,0,1),pos.offset(1,0,-1),pos.offset(-1,0,1),pos.offset(-1,0,-1));
        for(BlockPos b : Base) {
            if (worldIn.getBlockState(b) != RankineBlocks.STAINLESS_STEEL_SHEETMETAL.get().defaultBlockState()) {
                return 0;
            }
        }
        BlockState lqb = RankineBlocks.LASER_PYLON_BASE.get().defaultBlockState();
        BlockState lqt = RankineBlocks.LASER_PYLON_TOP.get().defaultBlockState();
        for (int i = 1; i <= Config.MACHINES.LASER_QUARRY_RANGE.get(); ++i) { // PORT-TODO(init): LASER_QUARRY_RANGE missing from Config.MACHINES
            if (worldIn.getBlockState(pos.offset(2,i,0)) == lqt && worldIn.getBlockState(pos.offset(-2,i,0)) == lqt && worldIn.getBlockState(pos.offset(0,i,-2)) == lqt && worldIn.getBlockState(pos.offset(0,i,2)) == lqt) {
                return i;
            } else if (worldIn.getBlockState(pos.offset(2,i,0)) != lqb && worldIn.getBlockState(pos.offset(-2,i,0)) != lqb && worldIn.getBlockState(pos.offset(0,i,-2)) != lqb && worldIn.getBlockState(pos.offset(0,i,2)) != lqb) {
                return 0;
            }
        }
        return 0;
    }

    private final IItemHandler[] handlers = new IItemHandler[] {
            new SidedInvWrapper(this, Direction.UP),
            new SidedInvWrapper(this, Direction.DOWN),
            new SidedInvWrapper(this, Direction.NORTH)
    };

    // PORT-CAP: LaserQuarryTile -> getItemHandler
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
        return new LaserQuarryContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.towerData);
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
        if (index == 0) {
            return stack.getItem() instanceof BatteryItem;
        }
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }


}
*/
