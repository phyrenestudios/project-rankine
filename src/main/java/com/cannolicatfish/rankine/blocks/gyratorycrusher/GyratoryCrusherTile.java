package com.cannolicatfish.rankine.blocks.gyratorycrusher;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.BatteryItem;
import com.cannolicatfish.rankine.items.CrushingHeadItem;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;

import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.GYRATORY_CRUSHER_TILE;

public class GyratoryCrusherTile  extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {
    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{3,4,5,6,7,8};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1,2};
    private final int powerCost = Config.MACHINES.GYRATORY_CRUSHER_POWER.get();
    public GyratoryCrusherTile() {
        super(GYRATORY_CRUSHER_TILE);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 200;
    private int currentLevel = -1;
    private final IIntArray furnaceData = new IIntArray(){
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
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt,this.items);
        this.burnTime = nbt.getInt("BurnTime");
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        this.currentBurnTime = ForgeHooks.getBurnTime(this.items.get(1));
        this.currentLevel = nbt.getInt("CurrentLevel");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        compound.putInt("CurrentLevel", this.currentLevel);
        ItemStackHelper.saveAllItems(compound, this.items);

        return compound;
    }

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning() && (!BatteryItem.hasPowerRequired(this.items.get(1),powerCost*currentLevel))) {
            burnTime--;
        }
        if (this.currentLevel != CrushingHeadItem.getTier(this.items.get(2))) {
            this.currentLevel = CrushingHeadItem.getTier(this.items.get(2));
        }
        if (!this.level.isClientSide) {

            ItemStack input = this.items.get(0);
            ItemStack battery = this.items.get(1);
            ItemStack crusher = this.items.get(2);
            if ((this.isBurning() || !battery.isEmpty() && !input.isEmpty() && !crusher.isEmpty())) {
                CrushingRecipe irecipe = this.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.CRUSHING, this, this.level).orElse(null);
                boolean canSmelt = this.canSmelt(irecipe);
                if (input.isEmpty() || !canSmelt) {
                    this.burnTime = 0;
                    this.currentBurnTime = this.burnTime;
                }
                if (!this.isBurning() && canSmelt) {
                    this.burnTime = BatteryItem.hasPowerRequired(battery,powerCost*(currentLevel+1)) ? 50 : 0;
                    this.currentBurnTime = this.burnTime;
                    this.currentLevel = CrushingHeadItem.getTier(crusher);
                    if (this.isBurning()) {
                        flag1 = true;
                    }
                }

                if (this.isBurning() && canSmelt) {
                    cookTime++;
                    if (cookTime >= cookTimeTotal) {
                        List<ItemStack> results = irecipe.getResults(this.currentLevel,this.level);

                        for (int i = 0; i < results.size(); i++) {
                            if (this.items.get(3 + i).getCount() > 0) {
                                this.items.get(3 + i).grow(results.get(i).getCount());
                            } if (this.items.get(3 + i).getCount() <= 0) {
                                this.items.set(3 + i, results.get(i).copy());
                            }
                        }

                        input.shrink(1);
                        cookTime = 0;
                        battery.setDamageValue(battery.getDamageValue() + powerCost*(currentLevel+1));
                        return;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if ((!this.isBurning()) && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(AbstractFurnaceBlock.LIT, this.isBurning()), 3);
            }
        }

        if (flag1) {
            this.setChanged();
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
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
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
        return ItemStackHelper.removeItem(this.items, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStackHelper.takeItem(this.items, index);
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
    public boolean stillValid(PlayerEntity player) {
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