package com.cannolicatfish.rankine.blocks.mixingbarrel;


import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.MixingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.MIXING_BARREL_TILE;

public class MixingBarrelTile extends BlockEntity implements WorldlyContainer, MenuProvider {

    FluidTank inputTank = new FluidTank(8000);
    private static final int[] SLOTS_UP = new int[]{0,1,2,3};
    private static final int[] SLOTS_DOWN = new int[]{4};
    private static final int[] SLOTS_HORIZONTAL = new int[]{2,3};
    public MixingBarrelTile(BlockPos posIn, BlockState stateIn) {
        super(MIXING_BARREL_TILE, posIn, stateIn);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private int mixTime;
    private int mixTimeTotal;
    private int redstonePower = 0;
    private int needsRefresh = 0;
    private final ContainerData furnaceData = new ContainerData(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return MixingBarrelTile.this.mixTime;
                case 1:
                    return MixingBarrelTile.this.mixTimeTotal;
                case 2:
                    return MixingBarrelTile.this.redstonePower;
                case 3:
                    return MixingBarrelTile.this.needsRefresh;
                default:
                    return 0;
            }
        }

        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    MixingBarrelTile.this.mixTime = value;
                    break;
                case 1:
                    MixingBarrelTile.this.mixTimeTotal = value;
                    break;
                case 2:
                    MixingBarrelTile.this.redstonePower = value;
                    break;
                case 3:
                    MixingBarrelTile.this.needsRefresh = value;
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
        this.mixTime = nbt.getInt("MixTime");
        this.mixTimeTotal = nbt.getInt("MixTimeTotal");
        this.redstonePower = nbt.getInt("RedstonePower");
        this.needsRefresh = nbt.getInt("NeedsRefresh");
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.put("InputTank",this.inputTank.writeToNBT(new CompoundTag()));
        compound.putInt("MixTime", this.mixTime);
        compound.putInt("MixTimeTotal", this.mixTimeTotal);
        compound.putInt("RedstonePower", this.redstonePower);
        compound.putInt("NeedsRefresh", this.needsRefresh);
        ContainerHelper.saveAllItems(compound, this.items);

        return compound;
    }

    public void tick() {
        boolean flag = this.isRedstonePowered();
        boolean flag1 = this.getNeedsRefresh();
        boolean flag2 = false;

        if (!this.level.isClientSide) {
            if (!flag && flag1) {
                this.needsRefresh = 0;
                flag1 = false;
            }
            ItemStack[] inputs = new ItemStack[]{this.items.get(0), this.items.get(1), this.items.get(2), this.items.get(3)};
            if ((!this.getNeedsRefresh() && flag) && (!this.items.get(0).isEmpty() || !this.items.get(1).isEmpty() || !this.items.get(2).isEmpty() || !this.items.get(3).isEmpty())) {
                int angle = this.getBlockState().getValue(MixingBarrelBlock.ANGLE);
                if (angle == 3) {
                    level.setBlock(this.worldPosition, RankineBlocks.MIXING_BARREL.get().defaultBlockState().setValue(MixingBarrelBlock.ANGLE,0),3);
                } else {
                    level.setBlock(this.worldPosition, RankineBlocks.MIXING_BARREL.get().defaultBlockState().setValue(MixingBarrelBlock.ANGLE,angle+1),3);
                }
                List<LivingEntity> entitiesOnBlock = level.getEntitiesOfClass(LivingEntity.class, new AABB(worldPosition, worldPosition.above()).expandTowards(1, 1, 1), (e) -> e instanceof Mob || e instanceof Player);
                for (LivingEntity i : entitiesOnBlock) {
                    i.absMoveTo(i.getX(),i.getY()+2,i.getZ(),i.getYHeadRot() + 22.5F,i.getRotationVector().y);
                }

                this.needsRefresh = 1;


                MixingRecipe irecipe = this.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.MIXING, this, this.level).orElse(null);
                if (this.canMix(irecipe, this)) {
                    if (this.mixTime == 0) {
                        this.mixTimeTotal = getMixTime();
                    }
                    this.mixTime += getRedstonePower();
                    if (this.mixTime >= this.mixTimeTotal) {
                        if (!irecipe.getFluid().isEmpty()) {
                            inputTank.drain(irecipe.getOutputFluidReq(this), IFluidHandler.FluidAction.EXECUTE);
                        }
                        ItemStack smelting = irecipe.getMixingResult(this,level);
                        if (this.items.get(4).getCount() > 0) {
                            this.items.get(4).grow(smelting.getCount());
                        } else {
                            this.items.set(4, smelting);
                        }


                        this.mixTime = 0;
                        inputs[0].setCount(0);
                        inputs[1].setCount(0);
                        inputs[2].setCount(0);
                        inputs[3].setCount(0);
                        return;
                    }
                } else {
                    this.mixTime = 0;
                }
            }

            if (flag1 != this.getNeedsRefresh()) {
                flag2 = true;
            }
        }

        if (flag2) {
            this.setChanged();
        }

    }

    public FluidTank getInputTank() {
        return inputTank;
    }

    public int getMixTime() {
        MixingRecipe irecipe = this.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.MIXING, this, this.level).orElse(null);
        if (irecipe != null) {
            return irecipe.getOutputMixTime(this);
        } else {
            return 3200;
        }
    }

    private boolean isRedstonePowered() {
        return this.level.getBestNeighborSignal(worldPosition) > 0;
    }

    private int getRedstonePower() {
        return this.level.getBestNeighborSignal(worldPosition);
    }

    public boolean getNeedsRefresh() {
        return needsRefresh == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isMixing(MixingBarrelTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canMix(@Nullable MixingRecipe recipeIn, Container inv)
    {
        if (recipeIn == null)
        {
            return false;
        }
        else
        {
            ItemStack result = recipeIn.getMixingResult(inv,level);
            if(result.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack output = this.items.get(4);
                if(output.isEmpty()) {
                    this.mixTimeTotal = recipeIn.getOutputMixTime(inv);
                    return true;
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
    public Component getDisplayName() {
        return new TextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new MixingBarrelContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.furnaceData);
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
            this.mixTimeTotal = this.getMixTime();
            this.mixTime = 0;
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
