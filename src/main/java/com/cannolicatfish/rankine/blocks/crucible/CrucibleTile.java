package com.cannolicatfish.rankine.blocks.crucible;


import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.recipe.CrucibleRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class CrucibleTile extends BlockEntity implements WorldlyContainer, MenuProvider {

    private static final int[] SLOTS_UP = new int[]{0,1};
    private static final int[] SLOTS_DOWN = new int[]{4, 5};
    private static final int[] SLOTS_HORIZONTAL = new int[]{2,3};
    public CrucibleTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.CRUCIBLE.get(), posIn, stateIn);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
    private int cookTime;
    private int cookTimeTotal;
    private int color = 16777215;
    private int heatPower = 0;
    private final ContainerData furnaceData = new ContainerData(){
        public int get(int index) {
            switch(index) {
                case 0:
                    return CrucibleTile.this.cookTime;
                case 1:
                    return CrucibleTile.this.cookTimeTotal;
                case 2:
                    return CrucibleTile.this.heatPower;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    CrucibleTile.this.cookTime = value;
                    break;
                case 1:
                    CrucibleTile.this.cookTimeTotal = value;
                    break;
                case 2:
                    CrucibleTile.this.heatPower = value;
            }
        }

        public int getCount() {
            return 3;
        }
    };

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt,this.items, provider);
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        this.color = nbt.getInt("color");
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        compound.putInt("color", this.color);
        ContainerHelper.saveAllItems(compound, this.items, provider);
    }

    public static void tick(Level level, BlockPos posIn, BlockState bs, CrucibleTile tile) {
        if (level.isClientSide) return;
        if (!tile.isHeated(posIn,level)) return;

        if (!tile.isCooking()) {
            level.setBlock(posIn, bs.setValue(CrucibleBlock.FLUID, false), 3);
        } else if (tile.getPersistentData().getInt("color") != tile.color) {
            tile.getPersistentData().putInt("color",tile.color);
            level.setBlock(posIn, bs.setValue(CrucibleBlock.FLUID, tile.isCooking()), 3);
        }

        if (tile.items.get(0).isEmpty() || tile.items.get(1).isEmpty() || tile.items.get(2).isEmpty() || tile.items.get(3).isEmpty()) return;
        CrucibleRecipe irecipe = level.getRecipeManager().getRecipeFor(RankineRecipeTypes.CRUCIBLE.get(), new RecipeWrapper(new InvWrapper(tile)), level).map(RecipeHolder::value).orElse(null);
        if (!tile.canSmelt(irecipe, tile)) {
            tile.cookTime = 0;
            return;
        }
        if (tile.cookTime == 0) {
            tile.cookTimeTotal = tile.getCookTime();
            tile.color = irecipe.getColor();
        }
        ++tile.cookTime;
        if (tile.cookTime >= tile.cookTimeTotal) {
            tile.cookTime = 0;

            ItemStack smelting = irecipe.generateResult(new RecipeWrapper(new InvWrapper(tile)));
            if (tile.items.get(4).getCount() > 0) {
                tile.items.get(4).grow(smelting.getCount());
            } else {
                tile.items.set(4, smelting);
            }

            ItemStack extra = irecipe.getSecondaryOutput();
            if (tile.items.get(5).getCount() > 0) {
                tile.items.get(5).grow(extra.getCount());
            } else {
                tile.items.set(5, extra);
            }

            tile.items.get(0).shrink(1);
            tile.items.get(1).shrink(1);
            tile.items.get(2).shrink(1);
            tile.items.get(3).shrink(1);
            return;
        }

  //      if (tile.cookTime > 0) {
    //        tile.cookTime = Mth.clamp(tile.cookTime - 2, 0, tile.cookTimeTotal);
      //  }

        if (tile.isCooking() != tile.isCooking()) {
            level.setBlock(posIn, bs.setValue(CrucibleBlock.FLUID, tile.isCooking()), 3);
            tile.setChanged();
        }

    }

    public int getCookTime() {
        CrucibleRecipe irecipe = this.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.CRUCIBLE.get(), new RecipeWrapper(new InvWrapper(this)), this.level).map(RecipeHolder::value).orElse(null);
        if (irecipe != null) {
            return irecipe.getRecipeCookTime(new RecipeWrapper(new InvWrapper(this)));
        } else {
            return 3200;
        }
    }

    private boolean isHeated(BlockPos pos, Level worldIn) {
        List<BlockPos> positions = Arrays.asList(pos.below(),pos.east(),pos.north(),pos.west(),pos.south());
        for (BlockPos p : positions) {
            if (worldIn.getBlockState(p).is(RankineTags.Blocks.HEAT_SOURCES)) {
                heatPower = 1;
                return true;
            }
        }
        heatPower = 0;
        return false;
    }

    public boolean isCooking()
    {
        return this.cookTime > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isCooking(CrucibleTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canSmelt(@Nullable CrucibleRecipe recipeIn, Container inv) {
        if (recipeIn == null) return false;

        ItemStack resultPrimary = recipeIn.generateResult(new RecipeWrapper(new InvWrapper(inv)));
        ItemStack resultSecondary = recipeIn.getSecondaryOutput();
        if (resultPrimary.isEmpty()) return false;
        ItemStack primary = this.items.get(4);
        ItemStack secondary = this.items.get(5);
        if (primary.isEmpty() && (secondary.isEmpty() || resultSecondary.isEmpty())) {
            this.cookTimeTotal = recipeIn.getRecipeCookTime(new RecipeWrapper(new InvWrapper(inv)));
            return true;
        }
        int res = primary.getCount() + resultPrimary.getCount();
        int res2 = secondary.getCount() + resultSecondary.getCount();
        if ((primary.isEmpty() || ItemStack.isSameItemSameComponents(primary, resultPrimary)) && (secondary.isEmpty() || ItemStack.isSameItemSameComponents(secondary, resultSecondary))) {
            this.cookTimeTotal = recipeIn.getRecipeCookTime(new RecipeWrapper(new InvWrapper(inv)));
            return res <= 64 && res2 <= 64 && res <= primary.getMaxStackSize() && res2 <= secondary.getMaxStackSize();
        }

        return false;
    }

    private final IItemHandler[] handlers = new IItemHandler[] {
            new SidedInvWrapper(this, Direction.UP),
            new SidedInvWrapper(this, Direction.DOWN),
            new SidedInvWrapper(this, Direction.NORTH)
    };

    // PORT-CAP: CrucibleTile -> getItemHandler
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
        return new CrucibleContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.furnaceData);
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
            this.cookTimeTotal = this.getCookTime();
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
        switch (index)
        {
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}
