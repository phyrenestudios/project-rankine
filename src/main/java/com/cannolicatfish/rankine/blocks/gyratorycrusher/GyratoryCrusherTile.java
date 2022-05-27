package com.cannolicatfish.rankine.blocks.gyratorycrusher;

import com.cannolicatfish.rankine.blocks.groundtap.GroundTapTile;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.BatteryItem;
import com.cannolicatfish.rankine.items.CrushingHeadItem;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
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

import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.GYRATORY_CRUSHER_TILE;

public class GyratoryCrusherTile  extends BlockEntity implements WorldlyContainer, MenuProvider {
    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{3,4,5,6,7,8};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1,2};
    private final int powerCost = Config.MACHINES.GYRATORY_CRUSHER_POWER.get();
    public GyratoryCrusherTile(BlockPos posIn, BlockState stateIn) {
        super(GYRATORY_CRUSHER_TILE, posIn, stateIn);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 200;
    private int currentLevel = -1;
    private final ContainerData furnaceData = new ContainerData(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return GyratoryCrusherTile.this.burnTime;
                case 1:
                    return GyratoryCrusherTile.this.currentBurnTime;
                case 2:
                    return GyratoryCrusherTile.this.cookTime;
                case 3:
                    return GyratoryCrusherTile.this.cookTimeTotal;
                case 4:
                    return GyratoryCrusherTile.this.currentLevel;
                default:
                    return 0;
            }
        }

        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    GyratoryCrusherTile.this.burnTime = value;
                    break;
                case 1:
                    GyratoryCrusherTile.this.currentBurnTime = value;
                    break;
                case 2:
                    GyratoryCrusherTile.this.cookTime = value;
                    break;
                case 3:
                    GyratoryCrusherTile.this.cookTimeTotal = value;
                    break;
                case 4:
                    GyratoryCrusherTile.this.currentLevel = value;
                    break;
            }
        }

        public int getCount() {
            return 5;
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
        this.currentLevel = nbt.getInt("CurrentLevel");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        compound.putInt("CurrentLevel", this.currentLevel);
        ContainerHelper.saveAllItems(compound, this.items);
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, GyratoryCrusherTile tile) {
        boolean flag = tile.isBurning();
        boolean flag1 = false;
        if (tile.isBurning() && (!BatteryItem.hasPowerRequired(tile.items.get(1),tile.powerCost*tile.currentLevel))) {
            tile.burnTime--;
        }
        if (tile.currentLevel != CrushingHeadItem.getTier(tile.items.get(2))) {
            tile.currentLevel = CrushingHeadItem.getTier(tile.items.get(2));
        }
        if (!tile.level.isClientSide) {

            ItemStack input = tile.items.get(0);
            ItemStack battery = tile.items.get(1);
            ItemStack crusher = tile.items.get(2);
            if ((tile.isBurning() || !battery.isEmpty() && !input.isEmpty() && !crusher.isEmpty())) {
                CrushingRecipe irecipe = tile.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.CRUSHING, tile, tile.level).orElse(null);
                boolean canSmelt = tile.canSmelt(irecipe);
                if (input.isEmpty() || !canSmelt) {
                    tile.burnTime = 0;
                    tile.currentBurnTime = tile.burnTime;
                }
                if (!tile.isBurning() && canSmelt) {
                    tile.burnTime = BatteryItem.hasPowerRequired(battery,tile.powerCost*(tile.currentLevel+1)) ? 50 : 0;
                    tile.currentBurnTime = tile.burnTime;
                    tile.currentLevel = CrushingHeadItem.getTier(crusher);
                    if (tile.isBurning()) {
                        flag1 = true;
                    }
                }

                if (tile.isBurning() && canSmelt) {
                    tile.cookTime++;
                    if (tile.cookTime >= tile.cookTimeTotal) {
                        List<ItemStack> results = irecipe.getResults(tile.currentLevel,tile.level);

                        for (int i = 0; i < results.size(); i++) {
                            if (tile.items.get(3 + i).getCount() > 0) {
                                tile.items.get(3 + i).grow(results.get(i).getCount());
                            } if (tile.items.get(3 + i).getCount() <= 0) {
                                tile.items.set(3 + i, results.get(i).copy());
                            }
                        }

                        input.shrink(1);
                        tile.cookTime = 0;
                        battery.setDamageValue(battery.getDamageValue() + tile.powerCost*(tile.currentLevel+1));
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
    public static boolean isBurning(com.cannolicatfish.rankine.blocks.gyratorycrusher.GyratoryCrusherTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canSmelt(@Nullable CrushingRecipe recipeIn)
    {
        if (!this.items.get(0).isEmpty() && this.currentLevel >= 0 && recipeIn != null && BatteryItem.hasPowerRequired(this.items.get(1),powerCost*(currentLevel+1))) {
            List<ItemStack> itemstacks = recipeIn.getPossibleResults(this.currentLevel,this.level);
            if (itemstacks.isEmpty()) {
                return false;
            } else {
                for (int i = 0; i < this.currentLevel; i++) {
                    ItemStack itemstack = this.items.get(3 + i);
                    if ((!itemstack.sameItem(itemstacks.get(i)) && !itemstack.isEmpty())) {
                        return false;
                    } else if (itemstack.getCount() + itemstacks.get(i).getCount() > this.getMaxStackSize() && itemstack.getCount() + itemstacks.get(i).getCount() > itemstack.getMaxStackSize()) {
                        return false;
                    } else if (itemstack.getCount() + itemstacks.get(i).getCount() > itemstacks.get(i).getMaxStackSize()) {
                        return false;
                    }
                }
                return true;

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
        return new GyratoryCrusherContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.furnaceData);
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
        switch (index)
        {
            case 0:
                return true;
            case 1:
                return stack.getItem() instanceof BatteryItem;
            case 2:
                return stack.getItem() instanceof CrushingHeadItem;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            default:
                return false;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}