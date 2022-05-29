package com.cannolicatfish.rankine.blocks.pistoncrusher;


import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelTile;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.PISTON_CRUSHER_TILE;

public class PistonCrusherTile extends BlockEntity implements WorldlyContainer, MenuProvider {
    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{2,3,4};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};
    public PistonCrusherTile(BlockPos posIn, BlockState stateIn) {
        super(PISTON_CRUSHER_TILE, posIn, stateIn);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 200;
    private final ContainerData furnaceData = new ContainerData(){
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

        public int getCount() {
            return 4;
        }
    };

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt,this.items);
        this.burnTime = nbt.getInt("BurnTime");
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        this.currentBurnTime = ForgeHooks.getBurnTime(this.items.get(1), RecipeType.SMELTING);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        ContainerHelper.saveAllItems(compound, this.items);
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, PistonCrusherTile tile) {
        boolean flag = tile.isBurning();
        boolean flag1 = false;
        if (tile.isBurning()) {
            --tile.burnTime;
        }

        if (!tile.level.isClientSide) {
            ItemStack input = tile.items.get(0);
            ItemStack fuel = tile.items.get(1);
            if ((tile.isBurning() || !fuel.isEmpty() && !tile.items.get(0).isEmpty())) {
                CrushingRecipe irecipe = tile.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.CRUSHING, tile, tile.level).orElse(null);
                if (!tile.isBurning() && tile.canSmelt(irecipe)) {
                    tile.burnTime = ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
                    tile.currentBurnTime = tile.burnTime;
                    if (tile.isBurning()) {
                        flag1 = true;
                        if (fuel.hasContainerItem())
                            tile.items.set(1, fuel.getContainerItem());
                        else
                        if (!fuel.isEmpty()) {
                            Item item = fuel.getItem();
                            fuel.shrink(1);
                            if (fuel.isEmpty()) {
                                tile.items.set(1, fuel.getContainerItem());
                            }
                        }
                    }
                }

                if (tile.isBurning() && tile.canSmelt(irecipe)) {
                    tile.cookTime++;
                    if (tile.cookTime >= tile.cookTimeTotal) {
                        List<ItemStack> results = irecipe.getResults(2,tile.level);

                        for (int i = 0; i < results.size(); i++) {
                            if (tile.items.get(2 + i).getCount() > 0) {
                                tile.items.get(2 + i).grow(results.get(i).getCount());
                            } if (tile.items.get(2 + i).getCount() <= 0) {
                                tile.items.set(2 + i, results.get(i).copy());
                            }
                        }

                        input.shrink(1);
                        tile.cookTime = 0;
                        return;
                        }
                    } else {
                        tile.cookTime = 0;
                    }
                } else if ((!tile.isBurning()) && tile.cookTime > 0) {
                    tile.cookTime = Mth.clamp(tile.cookTime - 2, 0, tile.cookTimeTotal);
                }

            if (flag != tile.isBurning()) {
                flag1 = true;
                tile.level.setBlock(tile.worldPosition, tile.level.getBlockState(tile.worldPosition).setValue(AbstractFurnaceBlock.LIT, tile.isBurning()), 3);
            }
        }

        if (flag1) {
            tile.setChanged();
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
            List<ItemStack> itemstacks = recipeIn.getPossibleResults(2,this.level);
            if (itemstacks.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(2);
                ItemStack itemstack2 = this.items.get(3);
                ItemStack itemstack3 = this.items.get(4);
                if (itemstack1.isEmpty() && itemstack2.isEmpty() && itemstack3.isEmpty()) {
                    return true;
                } else if ((!itemstack1.sameItem(itemstacks.get(0)) && !itemstack1.isEmpty()) || (!itemstack2.sameItem(itemstacks.get(1)) && !itemstack2.isEmpty())
                        || (!itemstack3.sameItem(itemstacks.get(2)) && !itemstack3.isEmpty())) {
                    return false;
                } else if (itemstack1.getCount() + itemstacks.get(0).getCount() <= this.getMaxStackSize() && itemstack1.getCount() + itemstacks.get(0).getCount() <= itemstack1.getMaxStackSize() &&
                        itemstack2.getCount() + itemstacks.get(1).getCount() <= this.getMaxStackSize() && itemstack2.getCount() + itemstacks.get(1).getCount() <= itemstack2.getMaxStackSize() &&
                        itemstack3.getCount() + itemstacks.get(2).getCount() <= this.getMaxStackSize() && itemstack3.getCount() + itemstacks.get(2).getCount() <= itemstack3.getMaxStackSize()) {
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
        return new PistonCrusherContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.furnaceData);
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
            this.cookTimeTotal = 200;
            this.cookTime = 0;
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
        CrushingRecipe recipe = this.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.CRUSHING, this, this.level).orElse(null);
        List<ItemStack> outputs = Arrays.asList(ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY);
        if (recipe != null) {
            outputs = recipe.getPossibleResults(3, this.level);
        }
        switch (index)
        {
            case 0:
                return true;
            case 1:
                return AbstractFurnaceBlockEntity.isFuel(stack);
            case 2:
                return ItemStack.isSame(outputs.get(0), stack);
            case 3:
                return ItemStack.isSame(outputs.get(1), stack);
            case 4:
                return ItemStack.isSame(outputs.get(2), stack);
            default:
                return false;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}
