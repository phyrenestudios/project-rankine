package com.cannolicatfish.rankine.blocks.evaporationtower;

import com.cannolicatfish.rankine.blocks.distillationtower.DistillationTowerTile;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.recipe.CrucibleRecipe;
import com.cannolicatfish.rankine.recipe.EvaporationRecipe;
import com.cannolicatfish.rankine.util.WeightedCollection;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.cannolicatfish.rankine.init.RankineBlocks.EVAPORATION_TOWER_TILE;


public class EvaporationTowerTile extends BlockEntity implements WorldlyContainer, MenuProvider {

    private static final int[] SLOTS_UP = new int[]{};
    private static final int[] SLOTS_DOWN = new int[]{0};
    private static final int[] SLOTS_HORIZONTAL = new int[]{};
    private int cookTime;
    private int cookTimeTotal = 6400;
    protected NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);
    private final ContainerData towerData = new ContainerData(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return EvaporationTowerTile.this.cookTime;
                case 1:
                    return EvaporationTowerTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    EvaporationTowerTile.this.cookTime = value;
                    break;
                case 1:
                    EvaporationTowerTile.this.cookTimeTotal = value;
                    break;
            }
        }
        public int getCount() {
            return 2;
        }
    };

    public EvaporationTowerTile(BlockPos posIn, BlockState stateIn) {
        super(EVAPORATION_TOWER_TILE, posIn, stateIn);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt,this.items);
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.items);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, EvaporationTowerTile tile) {
        Level worldIn = tile.getLevel();
        if (!worldIn.isClientSide) {
            ItemStack output = tile.items.get(0);
            BlockPos p = tile.getBlockPos();
            EvaporationRecipe recipe = tile.getEvaporationRecipe(p.above());
            if (recipe != null) {
                if (tile.cookTimeTotal != recipe.getTime()) {
                    tile.cookTimeTotal = recipe.getTime();
                }
                if (recipe.isLarge()) {
                    int h = tile.checkStructure(p, worldIn, worldIn.getBlockState(p.above()).getBlock());
                    if (h > 0 && output.isEmpty()) {
                        ++tile.cookTime;
                        if (tile.cookTime >= tile.cookTimeTotal / h) {
                            tile.items.set(0, recipe.getEvaporationResult(worldIn,worldIn.getBiome(p).value().getRegistryName()));
                            tile.cookTime = 0;
                        }
                    } else if (tile.cookTime > 0) {
                        tile.cookTime = Mth.clamp(tile.cookTime - 2, 0, tile.cookTimeTotal);
                    }
                } else {
                    if (tile.boilerStructure(p, worldIn) && output.isEmpty()) {
                        ++tile.cookTime;
                        if (tile.cookTime >= tile.cookTimeTotal) {
                            worldIn.setBlock(p.above(), Blocks.AIR.defaultBlockState(), 3);
                            tile.items.set(0, recipe.getEvaporationResult(worldIn,worldIn.getBiome(p).value().getRegistryName()));
                            tile.cookTime = 0;
                        }
                    } else if (tile.cookTime > 0) {
                        tile.cookTime = Mth.clamp(tile.cookTime - 2, 0, tile.cookTimeTotal);
                    }
                }
            }
        }

    }

    private EvaporationRecipe getEvaporationRecipe(BlockPos pos) {
        if (this.level != null) {
            for (EvaporationRecipe recipe : this.level.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.EVAPORATION)) {
                if (!recipe.getEvaporationResult(this.level,level.getBiome(pos).value().getRegistryName()).isEmpty() && recipe.fluidMatch(this.level.getFluidState(pos).getType())){
                    return recipe;
                }
            }
        }
        return null;
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

    private boolean boilerStructure(BlockPos pos, Level worldIn) {
        if (!worldIn.isClientSide) {
            for (BlockPos p : Arrays.asList(pos.north(), pos.east(), pos.south(), pos.west(), pos.above().north(), pos.above().east(), pos.above().west(), pos.above().south())) {
                if (!worldIn.getBlockState(p).is(RankineTags.Blocks.SHEETMETAL)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private int checkStructure(BlockPos pos, Level worldIn, Block fluid) {
        int height = 0;
        if (!worldIn.isClientSide) {
            List<BlockPos> Base = Arrays.asList(
                    pos.offset(2, 0, -1),
                    pos.offset(2, 0, 0),
                    pos.offset(2, 0, 1),
                    pos.offset(-2, 0, -1),
                    pos.offset(-2, 0, 0),
                    pos.offset(-2, 0, 1),
                    pos.offset(-1, 0, 2),
                    pos.offset(0, 0, 2),
                    pos.offset(1, 0, 2),
                    pos.offset(-1, 0, 2),
                    pos.offset(0, 0, 2),
                    pos.offset(1, 0, 2),
                    pos.offset(1, 0, 0),
                    pos.offset(0, 0, 1),
                    pos.offset(-1, 0, 0),
                    pos.offset(0, 0, -1),
                    pos.offset(1, 0, 1),
                    pos.offset(1, 0, -1),
                    pos.offset(-1, 0, 1),
                    pos.offset(-1, 0, -1));
            for (BlockPos b : Base) {
                if (worldIn.getBlockState(b) != Blocks.MAGMA_BLOCK.defaultBlockState()) {
                    return 0;
                }
            }
            for (int i = 1; i <= Config.MACHINES.EVAPORATION_TOWER_RANGE.get(); ++i) {
                if (worldIn.getBlockState(pos.offset(3, i, -1)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(3, i, 0)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(3, i, 1)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(-3, i, -1)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(-3, i, 0)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(-3, i, 1)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(-1, i, 3)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(0, i, 3)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(1, i, 3)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(-1, i, 3)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(0, i, 3)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(2, i, 2)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(-2, i, -2)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(2, i, -2)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(-2, i, 2)).is(RankineTags.Blocks.SHEETMETAL)
                        && worldIn.getBlockState(pos.offset(2, i, -1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(2, i, 0)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(2, i, 1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-2, i, -1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-2, i, 0)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-2, i, 1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-1, i, 2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(0, i, 2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(1, i, 2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-1, i, -2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(0, i, -2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(1, i, -2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(1, i, 0)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-1, i, 0)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-1, i, -1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(1, i, 1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-1, i, 1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(1, i, -1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(0, i, 1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(0, i, -1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(0, i, 0)).getBlock() == fluid) {
                    height = i;
                }
            }
        }
        return height;
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new EvaporationTowerContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.towerData);
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
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
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
        return false;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }


}
