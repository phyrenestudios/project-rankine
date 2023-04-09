package com.cannolicatfish.rankine.blocks.evaporationtower;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.recipe.EvaporationRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.EVAPORATION_TOWER_TILE;


public class EvaporationTowerTile extends BlockEntity implements WorldlyContainer, MenuProvider {

    private static final int[] SLOTS_UP = new int[]{};
    private static final int[] SLOTS_DOWN = new int[]{0};
    private static final int[] SLOTS_HORIZONTAL = new int[]{};
    private int cookTime;
    private int cookTimeTotal = 4000;
    protected NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);
    private final ContainerData towerData = new ContainerData(){
        public int get(int index) {
            switch(index) {
                case 0:
                    return EvaporationTowerTile.this.cookTime;
                case 1:
                    return EvaporationTowerTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }
        public void set(int index, int value) {
            switch(index) {
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

    public static void tick(Level levelIn, BlockPos posIn, BlockState stateIn, EvaporationTowerTile tile) {
        if (!levelIn.isClientSide) {
            ItemStack output = tile.items.get(0);
            int wallHeight = tile.structureHeight(levelIn, posIn);
            if (wallHeight > 0 && output.isEmpty()) {
                EvaporationRecipe recipe = tile.getEvaporationRecipe(levelIn, posIn);
                if (recipe != null) {
                    int processHeight = Math.min(wallHeight, fluidHeight(levelIn, posIn, recipe.getFluid().getFluid()));
                    if (processHeight > 0) {
                        tile.cookTimeTotal = Math.round(recipe.getProcessTime() * (1 - (processHeight / 21F)));
                        ++tile.cookTime;
                        if (tile.cookTime >= tile.cookTimeTotal) {
                            tile.items.set(0, recipe.getEvaporationResult(levelIn, levelIn.getBiome(posIn).value().getRegistryName()));
                            tile.cookTime = 0;
                            if (recipe.getConsumeFluid()) {
                                for (BlockPos b : fluidStructure(posIn)) {
                                    if (levelIn.getFluidState(b.above(processHeight)).is(recipe.getFluid().getFluid())) {
                                        levelIn.setBlockAndUpdate(b.above(processHeight), Blocks.AIR.defaultBlockState());
                                        break;
                                    }
                                }
                            }
                        }

                    }
                }
            }



        }

    }

    private EvaporationRecipe getEvaporationRecipe(Level levelIn, BlockPos posIn) {
        if (this.level != null) {
            for (EvaporationRecipe recipe : levelIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.EVAPORATION)) {
                if (!recipe.getEvaporationResult(levelIn, levelIn.getBiome(posIn).value().getRegistryName()).isEmpty() && recipe.fluidMatch(levelIn.getFluidState(posIn.above()).getType())) {
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

    public static List<BlockPos> wallStructure(BlockPos posIn) {
        return Arrays.asList(
                posIn.offset(3, 0, -1),
                posIn.offset(3, 0, 0),
                posIn.offset(3, 0, 1),
                posIn.offset(-3, 0, -1),
                posIn.offset(-3, 0, 0),
                posIn.offset(-3, 0, 1),
                posIn.offset(-1, 0, 3),
                posIn.offset(0, 0, 3),
                posIn.offset(1, 0, 3),
                posIn.offset(-1, 0, 3),
                posIn.offset(0, 0, 3),
                posIn.offset(2, 0, 2),
                posIn.offset(-2, 0, -2),
                posIn.offset(2, 0, -2),
                posIn.offset(-2, 0, 2));
    }

    public static List<BlockPos> fluidStructure(BlockPos posIn) {
        return Arrays.asList(
                posIn.offset(2, 0, -1),
                posIn.offset(2, 0, 0),
                posIn.offset(2, 0, 1),
                posIn.offset(-2, 0, -1),
                posIn.offset(-2, 0, 0),
                posIn.offset(-2, 0, 1),
                posIn.offset(-1, 0, 2),
                posIn.offset(0, 0, 2),
                posIn.offset(1, 0, 2),
                posIn.offset(-1, 0, -2),
                posIn.offset(0, 0, -2),
                posIn.offset(1, 0, -2),
                posIn.offset(1, 0, 0),
                posIn.offset(-1, 0, 0),
                posIn.offset(-1, 0, -1),
                posIn.offset(1, 0, 1),
                posIn.offset(-1, 0, 1),
                posIn.offset(1, 0, -1),
                posIn.offset(0, 0, 1),
                posIn.offset(0, 0, -1),
                posIn.offset(0, 0, 0));
    }

    public int structureHeight(Level levelIn, BlockPos posIn) {
        int height = 0;
        List<BlockPos> Base = Arrays.asList(
                posIn.offset(2, 0, -1),
                posIn.offset(2, 0, 0),
                posIn.offset(2, 0, 1),
                posIn.offset(-2, 0, -1),
                posIn.offset(-2, 0, 0),
                posIn.offset(-2, 0, 1),
                posIn.offset(-1, 0, 2),
                posIn.offset(0, 0, 2),
                posIn.offset(1, 0, 2),
                posIn.offset(-1, 0, 2),
                posIn.offset(0, 0, 2),
                posIn.offset(1, 0, 2),
                posIn.offset(1, 0, 0),
                posIn.offset(0, 0, 1),
                posIn.offset(-1, 0, 0),
                posIn.offset(0, 0, -1),
                posIn.offset(1, 0, 1),
                posIn.offset(1, 0, -1),
                posIn.offset(-1, 0, 1),
                posIn.offset(-1, 0, -1));
        for (BlockPos b : Base) {
            if (!levelIn.getBlockState(b).is(RankineTags.Blocks.HEATING_ELEMENTS)) {
                return 0;
            }
        }

        for (height = 0; height <= 20; ++height) {
            for (BlockPos b : wallStructure(posIn)) {
                if (!levelIn.getBlockState(b.above(height)).is(RankineTags.Blocks.SHEETMETAL)) {
                    return height-1;
                }
            }
        }
        return height;
    }

    private static int fluidHeight(Level levelIn, BlockPos posIn, Fluid fluidIn) {
        for (int height = 1; height <= 21; ++height) {
            boolean liquid = false;
            for (BlockPos b : fluidStructure(posIn)) {
                if (!levelIn.getFluidState(b.above(height)).is(fluidIn)) {
                    if (liquid) return height;
                } else {
                    liquid = true;
                }
            }
        }
        return 20;
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable(RankineBlocks.EVAPORATION_TOWER.get().getDescriptionId());
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
