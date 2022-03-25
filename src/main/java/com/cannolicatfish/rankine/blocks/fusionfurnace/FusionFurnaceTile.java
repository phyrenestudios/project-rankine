package com.cannolicatfish.rankine.blocks.fusionfurnace;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.BatteryItem;
import com.cannolicatfish.rankine.items.CrushingHeadItem;
import com.cannolicatfish.rankine.items.GasBottleItem;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.recipe.FusionFurnaceRecipe;
import com.cannolicatfish.rankine.recipe.FusionFurnaceRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.BottleItem;
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
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.FUSION_FURNACE_TILE;

public class FusionFurnaceTile extends BlockEntity implements WorldlyContainer, MenuProvider {
    FluidTank inputTank = new FluidTank(64000);
    FluidTank outputTank = new FluidTank(64000);
    protected NonNullList<ItemStack> items = NonNullList.withSize(7, ItemStack.EMPTY);
    private static final int[] SLOTS_UP = new int[]{0,1,2,3};
    private static final int[] SLOTS_DOWN = new int[]{3,4,5,6};
    private static final int[] SLOTS_HORIZONTAL = new int[]{2,3};
    private final int powerCost = Config.MACHINES.GYRATORY_CRUSHER_POWER.get();
    public FusionFurnaceTile(BlockPos posIn, BlockState stateIn) {
        super(FUSION_FURNACE_TILE, posIn, stateIn);
    }
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 400;
    private final ContainerData furnaceData = new ContainerData(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return FusionFurnaceTile.this.burnTime;
                case 1:
                    return FusionFurnaceTile.this.currentBurnTime;
                case 2:
                    return FusionFurnaceTile.this.cookTime;
                case 3:
                    return FusionFurnaceTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }

        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    FusionFurnaceTile.this.burnTime = value;
                    break;
                case 1:
                    FusionFurnaceTile.this.currentBurnTime = value;
                    break;
                case 2:
                    FusionFurnaceTile.this.cookTime = value;
                    break;
                case 3:
                    FusionFurnaceTile.this.cookTimeTotal = value;
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
        this.inputTank = this.inputTank.readFromNBT(nbt.getCompound("InputTank"));
        this.outputTank = this.outputTank.readFromNBT(nbt.getCompound("OutputTank"));
        this.burnTime = nbt.getInt("BurnTime");
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        this.currentBurnTime = ForgeHooks.getBurnTime(this.items.get(1), RecipeType.SMELTING);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        ContainerHelper.saveAllItems(compound, this.items);
        compound.put("InputTank",this.inputTank.writeToNBT(new CompoundTag()));
        compound.put("OutputTank",this.outputTank.writeToNBT(new CompoundTag()));
        return compound;
    }

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning() && (!BatteryItem.hasPowerRequired(this.items.get(2),powerCost))) {
            burnTime--;
        }
        if (!this.level.isClientSide) {

            ItemStack[] inputs = new ItemStack[]{this.items.get(0), this.items.get(1), this.items.get(3)};
            ItemStack battery = this.items.get(2);
            if ((this.isBurning() || !battery.isEmpty() && !Arrays.stream(inputs).allMatch(ItemStack::isEmpty))) {
                FusionFurnaceRecipe irecipe = getFusionFurnaceRecipe();
                boolean canSmelt = this.canSmelt(irecipe);
                if (!canSmelt) {
                    this.burnTime = 0;
                    this.currentBurnTime = this.burnTime;
                }
                if (!this.isBurning() && canSmelt) {
                    this.burnTime = BatteryItem.hasPowerRequired(battery,powerCost) ? 50 : 0;
                    this.currentBurnTime = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                    }
                }

                if (this.isBurning() && canSmelt) {
                    cookTime++;
                    if (cookTime >= cookTimeTotal) {
                        if (!this.items.get(0).isEmpty() && irecipe.getIngredient1().test(this.items.get(0)) || irecipe.getIngredient2().test(this.items.get(0))) {
                            this.items.get(0).shrink(1);
                        }
                        if (!this.items.get(1).isEmpty() && irecipe.getIngredient1().test(this.items.get(1)) || irecipe.getIngredient2().test(this.items.get(1))) {
                            this.items.get(1).shrink(1);
                        }
                        if (!irecipe.getGasIn().isEmpty()) {
                            this.items.get(3).shrink(irecipe.getGasIn().getCount());
                        }
                        List<ItemStack> results = irecipe.getResults();
                        if (!results.get(0).isEmpty()) {
                            if (this.items.get(4).getCount() > 0) {
                                this.items.get(4).grow(results.get(0).getCount());
                            } if (this.items.get(4).getCount() <= 0) {
                                this.items.set(4, results.get(0).copy());
                            }
                        }
                        if (!results.get(1).isEmpty()) {
                            if (this.items.get(5).getCount() > 0) {
                                this.items.get(5).grow(results.get(1).getCount());
                            } if (this.items.get(5).getCount() <= 0) {
                                this.items.set(5, results.get(1).copy());
                            }
                        }

                        if (!results.get(2).isEmpty()) {
                            if (this.items.get(6).getCount() > 0) {
                                this.items.get(6).grow(results.get(2).getCount());
                            } if (this.items.get(6).getCount() <= 0) {
                                this.items.set(6, results.get(2).copy());
                            }
                        }

                        if (!irecipe.getFluidIn().isEmpty()) {
                            inputTank.drain(irecipe.getFluidIn(), IFluidHandler.FluidAction.EXECUTE);
                        }

                        if (!irecipe.getFluidOut().isEmpty()) {
                            outputTank.fill(irecipe.getFluidOut(), IFluidHandler.FluidAction.EXECUTE);
                        }
                        cookTime = 0;
                        battery.setDamageValue(battery.getDamageValue() + powerCost);
                        return;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if ((!this.isBurning()) && this.cookTime > 0) {
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

    private FusionFurnaceRecipe getFusionFurnaceRecipe() {
        if (this.level != null) {
            for (FusionFurnaceRecipe recipe : this.level.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.FUSION_FURNACE)) {
                if (recipe.matchesRecipe(this,this.inputTank,this.outputTank,this.level)){
                    return recipe;
                }
            }
        }
        return null;
    }

    public FluidTank getInputTank() {
        return inputTank;
    }

    public FluidTank getOutputTank() {
        return outputTank;
    }

    public boolean isBurning()
    {
        return this.burnTime > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isBurning(com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canSmelt(@Nullable FusionFurnaceRecipe recipeIn)
    {
        if (recipeIn != null && BatteryItem.hasPowerRequired(this.items.get(2),powerCost)) {
            List<ItemStack> itemstacks = recipeIn.getResults();
            if (itemstacks.isEmpty()) {
                return false;
            } else {
                for (int i = 0; i < 3; i++) {
                    ItemStack itemstack = this.items.get(4 + i);
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
        return new FusionFurnaceContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.furnaceData);
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
            this.cookTimeTotal = 400;
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
                return true;
            case 2:
                return stack.getItem() instanceof BatteryItem;
            case 3:
            case 6:
                return stack.getItem() instanceof BottleItem || stack.getItem() instanceof GasBottleItem;
            case 4:
            case 5:

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
