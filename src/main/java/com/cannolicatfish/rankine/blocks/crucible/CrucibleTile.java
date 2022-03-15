package com.cannolicatfish.rankine.blocks.crucible;


import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.recipe.CrucibleRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.CRUCIBLE_TILE;

public class CrucibleTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    private static final int[] SLOTS_UP = new int[]{0,1};
    private static final int[] SLOTS_DOWN = new int[]{4, 5};
    private static final int[] SLOTS_HORIZONTAL = new int[]{2,3};
    public CrucibleTile() {
        super(CRUCIBLE_TILE);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
    private int cookTime;
    private int cookTimeTotal;
    private int color = 16777215;
    private int heatPower = 0;
    private final IIntArray furnaceData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
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

        public void set(int index, int value)
        {
            switch(index)
            {
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
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt,this.items);
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        this.color = nbt.getInt("color");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        compound.putInt("color", this.color);
        ItemStackHelper.saveAllItems(compound, this.items);

        return compound;
    }

    public void tick() {
        boolean flag = this.isHeated(this.worldPosition,this.level);
        boolean flag1 = this.isCooking();
        boolean flag2 = false;

        if (!this.level.isClientSide) {
            if (!flag1) {
                this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(CrucibleBlock.FLUID, false), 3);
            } else if (this.getTileData().getInt("color") != this.color) {
                this.getTileData().putInt("color",this.color);
                this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(CrucibleBlock.FLUID, this.isCooking()), 3);
            }
            ItemStack[] inputs = new ItemStack[]{this.items.get(0), this.items.get(1), this.items.get(2), this.items.get(3)};
            if ((this.isCooking() && flag) || !this.items.get(0).isEmpty() && !this.items.get(1).isEmpty() && !this.items.get(2).isEmpty() && !this.items.get(3).isEmpty()) {
                CrucibleRecipe irecipe = this.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.CRUCIBLE, this, this.level).orElse(null);
                if (this.canSmelt(irecipe, this)) {
                    if (this.cookTime == 0) {
                        this.cookTimeTotal = getCookTime();
                        this.color = irecipe.getColor();

                    }
                    ++this.cookTime;
                    if (this.cookTime >= this.cookTimeTotal) {
                        ItemStack smelting = irecipe.generateResult(this);
                        if (this.items.get(4).getCount() > 0) {
                            this.items.get(4).grow(smelting.getCount());
                        } else {
                            this.items.set(4, smelting);
                        }

                        ItemStack extra = irecipe.getSecondaryOutput();
                        if (this.items.get(5).getCount() > 0) {
                            this.items.get(5).grow(extra.getCount());
                        } else {
                            this.items.set(5, extra);
                        }

                        this.cookTime = 0;
                        inputs[0].shrink(1);
                        inputs[1].shrink(1);
                        inputs[2].shrink(1);
                        inputs[3].shrink(1);
                        return;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if ((flag) && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (flag1 != this.isCooking()) {
                flag2 = true;
                this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(CrucibleBlock.FLUID, this.isCooking()), 3);
            }
        }

        if (flag2) {
            this.setChanged();
        }

    }

    public int getCookTime() {
        CrucibleRecipe irecipe = this.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.CRUCIBLE, this, this.level).orElse(null);
        if (irecipe != null) {
            return irecipe.getRecipeCookTime(this);
        } else {
            return 3200;
        }
    }

    private boolean isHeated(BlockPos pos, World worldIn) {
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

    private boolean canSmelt(@Nullable CrucibleRecipe recipeIn, IInventory inv)
    {
        if (recipeIn == null)
        {
            return false;
        }
        else
        {
            ItemStack result = recipeIn.generateResult(inv);
            ItemStack sec = recipeIn.getSecondaryOutput();
            if(result.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack output = this.items.get(4);
                ItemStack secondary = this.items.get(5);
                if(output.isEmpty() && (secondary.isEmpty() || sec.isEmpty())) {
                    this.cookTimeTotal = recipeIn.getRecipeCookTime(inv);
                    return true;
                }

                if(!output.sameItem(result) || !secondary.sameItem(sec))
                {
                    return false;
                }
                int res = output.getCount() + result.getCount();
                int res2 = secondary.getCount() + sec.getCount();
                if (ItemStack.tagMatches(output, result) && ItemStack.isSame(output, result) && ItemStack.tagMatches(secondary, sec) && ItemStack.isSame(secondary, sec)) {
                    this.cookTimeTotal = recipeIn.getRecipeCookTime(inv);
                    return res <= 64 && res2 <= 64 && res <= output.getMaxStackSize() && res2 <= secondary.getMaxStackSize();
                } else {
                    return false;
                }
            }
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
            this.cookTimeTotal = this.getCookTime();
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
