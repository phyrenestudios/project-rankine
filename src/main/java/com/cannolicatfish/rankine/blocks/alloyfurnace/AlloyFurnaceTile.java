package com.cannolicatfish.rankine.blocks.alloyfurnace;

import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableContainer;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.AlloyTemplateItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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

import java.util.*;

import static com.cannolicatfish.rankine.init.RankineBlocks.ALLOY_FURNACE_TILE;

public class AlloyFurnaceTile extends BlockEntity implements WorldlyContainer, MenuProvider {

    private static final int[] SLOTS_UP = new int[]{6,7};
    private static final int[] SLOTS_EAST = new int[]{0,1};
    private static final int[] SLOTS_WEST = new int[]{2,3};
    private static final int[] SLOTS_BACK = new int[]{4,5};
    private static final int[] SLOTS_DOWN = new int[]{8};
    public AlloyFurnaceTile(BlockPos posIn, BlockState stateIn) {
        super(ALLOY_FURNACE_TILE,posIn,stateIn);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private boolean recipeMode = false;
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 800;
    private final ContainerData furnaceData = new ContainerData(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return AlloyFurnaceTile.this.burnTime;
                case 1:
                    return AlloyFurnaceTile.this.currentBurnTime;
                case 2:
                    return AlloyFurnaceTile.this.cookTime;
                case 3:
                    return AlloyFurnaceTile.this.cookTimeTotal;
                case 4:
                    return AlloyFurnaceTile.this.recipeMode ? 1 : 0;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    AlloyFurnaceTile.this.burnTime = value;
                    break;
                case 1:
                    AlloyFurnaceTile.this.currentBurnTime = value;
                    break;
                case 2:
                    AlloyFurnaceTile.this.cookTime = value;
                    break;
                case 3:
                    AlloyFurnaceTile.this.cookTimeTotal = value;
                    break;
                case 4:
                    AlloyFurnaceTile.this.recipeMode = value == 1;
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
        this.currentBurnTime = ForgeHooks.getBurnTime(this.items.get(6), RecipeType.SMELTING);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        ContainerHelper.saveAllItems(compound, this.items);

        return compound;
    }

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.level.isClientSide) {
            ItemStack[] inputs = new ItemStack[]{this.items.get(0), this.items.get(1), this.items.get(2),this.items.get(3),this.items.get(4),this.items.get(5)};
            ItemStack fuel = this.items.get(6);
            if ((this.isBurning() || !fuel.isEmpty() && !Arrays.stream(inputs).allMatch(ItemStack::isEmpty))) {
                AlloyingRecipe irecipe = this.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.ALLOYING, this, this.level).orElse(null);
                if (!this.isBurning() && this.canSmelt(irecipe,this)) {
                    this.burnTime = ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
                    this.currentBurnTime = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (fuel.hasContainerItem())
                            this.items.set(6, fuel.getContainerItem());
                        else
                        if (!fuel.isEmpty()) {
                            Item item = fuel.getItem();
                            fuel.shrink(1);
                            if (fuel.isEmpty()) {
                                this.items.set(6, fuel.getContainerItem());
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(irecipe,this)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        int[] x;
                        ItemStack output;

                        ItemStack smelting = ItemStack.EMPTY;
                        if (recipeMode)
                        {
                            ItemStack template = this.getItem(7);
                            output = AlloyTemplateItem.getResult(level,template).copy();

                            for (Map.Entry<Ingredient,Short> input : AlloyTemplateItem.getInputStacks(template).entrySet())
                            {
                                List<ItemStack> addIt = new ArrayList<>();
                                int count = input.getValue();
                                for (int i = 0; i < 6; i++) {
                                    if (input.getKey().test(inputs[i])) {
                                        addIt.add(inputs[i]);
                                    }
                                }



                                for (ItemStack s : addIt)
                                {
                                    int shramt = count - s.getCount();
                                    if (shramt > 0) {
                                        count = shramt;
                                        s.setCount(0);
                                    } else {
                                        s.shrink(count);
                                        break;
                                    }
                                }
                            }
                            smelting = output;
                            if (this.items.get(8).getCount() > 0) {
                                this.items.get(8).grow(smelting.getCount());
                            } else {
                                this.items.set(8, smelting);
                            }
                            smelting = ItemStack.EMPTY;
                        } else {
                            output = irecipe.generateResult(level,this,1).copy();
                            x = new int[]{inputs[0].getCount(),inputs[1].getCount(),inputs[2].getCount(),inputs[3].getCount(),inputs[4].getCount(),inputs[5].getCount()};
                            smelting = output;
                            if (this.items.get(8).getCount() > 0) {
                                this.items.get(8).grow(smelting.getCount());
                            } else {
                                this.items.set(8, smelting);
                            }
                            inputs[0].shrink(x[0]);
                            inputs[1].shrink(x[1]);
                            inputs[2].shrink(x[2]);
                            inputs[3].shrink(x[3]);
                            inputs[4].shrink(x[4]);
                            inputs[5].shrink(x[5]);
                            smelting = ItemStack.EMPTY;
                        }


                        this.cookTime = 0;
                        flag1 = true;
                        return;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = Mth.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
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
    public static boolean isBurning(AlloyFurnaceTile te)
    {
        return te.furnaceData.get(0) > 0;
    }


    private boolean canSmelt(@Nullable AlloyingRecipe recipeIn, Container inv)
    {
        if (recipeIn != null || inv.getItem(7).getItem() instanceof AlloyTemplateItem) {
            recipeMode = inv.getItem(7).getItem() instanceof AlloyTemplateItem;
            ItemStack template = inv.getItem(7);
            if (recipeMode) {
                if ((AlloyTemplateItem.getAlloyTier(template) & 1) != 1) {
                    return false;
                }
                for (Map.Entry<Ingredient,Short> input : AlloyTemplateItem.getInputStacks(template).entrySet())
                {
                    int count = input.getValue();
                    int itemCount = 0;
                    for (ItemStack stack : input.getKey().getItems()) {
                        itemCount += inv.countItem(stack.getItem());
                    }
                    if (itemCount < count) {
                        return false;
                    }
                }
            }
            ItemStack stack = recipeMode ? AlloyTemplateItem.getResult(level,template) : recipeIn.generateResult(level,inv,1);
            if (stack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(8);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if ((!itemstack1.sameItem(stack) || !ItemStack.tagMatches(stack,itemstack1)) && !itemstack1.isEmpty()) {
                    return false;
                } else if (itemstack1.getCount() + stack.getCount() <= this.getMaxStackSize() && itemstack1.getCount() + stack.getCount() <= itemstack1.getMaxStackSize()) {
                    return true;
                } else {
                    return itemstack1.getCount() + stack.getCount() <= stack.getMaxStackSize();
                }
            }

        } else {
            return false;
        }
    }


    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST, Direction.SOUTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            switch (facing)
            {
                case UP:
                    return handlers[0].cast();
                case DOWN:
                    return handlers[1].cast();
                case EAST:
                    return handlers[2].cast();
                case WEST:
                    return handlers[3].cast();
                default:
                    return handlers[4].cast();
            }
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
        return new AlloyFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.furnaceData);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        switch (side)
        {
            case UP:
                return SLOTS_UP;
            case DOWN:
                return SLOTS_DOWN;
            case EAST:
                return SLOTS_EAST;
            case WEST:
                return SLOTS_WEST;
            case NORTH:
            case SOUTH:
            default:
                return SLOTS_BACK;
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

        if (index >= 0 && index <= 5 && !flag) {
            this.cookTimeTotal = 800;
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
            case 4:
            case 5:
                return AlloyCustomHelper.hasElement(stack.getItem());
            case 6:
                return AbstractFurnaceBlockEntity.isFuel(stack);
            case 7:
                return stack.getItem() instanceof AlloyTemplateItem;
            case 8:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    public void setChanged() {
        super.setChanged();
    }
}

